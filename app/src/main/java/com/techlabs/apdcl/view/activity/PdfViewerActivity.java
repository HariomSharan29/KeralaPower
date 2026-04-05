package com.techlabs.apdcl.view.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techlabs.apdcl.Utils.custom.PdfPageAdapter;
import com.techlabs.apdcl.Utils.custom.PdfAdapter;
import com.techlabs.apdcl.databinding.ActivityPdfViewerBinding;

import java.util.ArrayList;
import java.util.List;

public class PdfViewerActivity extends AppCompatActivity {
    private ActivityPdfViewerBinding binding;
    private final List<Uri> pdfUris = new ArrayList<>();
    private PdfAdapter adapter;
    private boolean isPdfViewVisible = false;

    private final ActivityResultLauncher<Intent> openFolderLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri treeUri = result.getData().getData();
                    if (treeUri != null) {
                        getContentResolver().takePersistableUriPermission(treeUri,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        saveFolderUri(treeUri);
                        loadPdfsFromFolder(treeUri);
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityPdfViewerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.toolbar.setTitle("Analysis Reports");
        setSupportActionBar(binding.toolbar);

        setupListRecycler();

        String savedUri = getSharedPreferences("pdf_prefs", MODE_PRIVATE)
                .getString("folder_uri", null);

        if (savedUri != null) {
            loadPdfsFromFolder(Uri.parse(savedUri));
        } else {
            openFolderPicker();
        }

        handleBackPress();
    }

    private void setupListRecycler() {
        adapter = new PdfAdapter(this, pdfUris, this::openPdf);
        binding.pdfRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.pdfRecyclerView.setAdapter(adapter);
    }

    private void handleBackPress() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isPdfViewVisible) {
                    exitPdfViewer();
                } else {
                    setEnabled(false);
                    onBackPressed();
                }
            }
        });

    }

    private void openFolderPicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        openFolderLauncher.launch(intent);
    }

    private void loadPdfsFromFolder(Uri treeUri) {
        pdfUris.clear();
        Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
                treeUri, DocumentsContract.getTreeDocumentId(treeUri));

        try (android.database.Cursor cursor = getContentResolver().query(
                childrenUri,
                new String[]{
                        DocumentsContract.Document.COLUMN_DOCUMENT_ID,
                        DocumentsContract.Document.COLUMN_DISPLAY_NAME,
                        DocumentsContract.Document.COLUMN_MIME_TYPE,
                        DocumentsContract.Document.COLUMN_FLAGS
                },
                null, null, null)) {

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String docId = cursor.getString(0);
                    String mime = cursor.getString(2);
                    if (docId != null && (docId.contains("trash") || docId.contains("::"))) {
                        continue;
                    }

                    if ("application/pdf".equals(mime)) {
                        Uri fileUri = DocumentsContract.buildDocumentUriUsingTree(treeUri, docId);
                        pdfUris.add(fileUri);
                    }
                }
            }

            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to load PDFs", Toast.LENGTH_SHORT).show();
        }
    }

    private void openPdf(Uri uri) {
        try {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            ParcelFileDescriptor fd = getContentResolver().openFileDescriptor(uri, "r");
            PdfRenderer renderer = new PdfRenderer(fd);

            PdfPageAdapter pageAdapter = new PdfPageAdapter(this, renderer);

            binding.toolbar.setVisibility(View.GONE);

            binding.pdfRecyclerView.setLayoutManager(
                    new LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            );
            binding.pdfRecyclerView.setAdapter(pageAdapter);

            isPdfViewVisible = true;

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to open PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private void exitPdfViewer() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding.toolbar.setVisibility(View.VISIBLE);
        setupListRecycler();
        isPdfViewVisible = false;
    }

    private void saveFolderUri(Uri uri) {
        getSharedPreferences("pdf_prefs", MODE_PRIVATE)
                .edit()
                .putString("folder_uri", uri.toString())
                .apply();
    }

}