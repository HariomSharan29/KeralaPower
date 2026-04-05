package com.techlabs.apdcl.view.LayerInfo.LineInfo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.databinding.SnipetLayoutBinding;

public class UnbalanceSnippet extends Dialog {

    private Context context;
    private SnipetLayoutBinding binding;
    private String NetworkId;
    private String SectionId;
    private String CableId;
    private String Length;
    private JsonObject jsonObject;

    public UnbalanceSnippet(@NonNull Context context, String networkId, String sectionId, String cableId, String length, JsonObject jsonObject) {
        super(context);
        this.context = context;
        this.NetworkId = networkId;
        this.SectionId = sectionId;
        this.CableId = cableId;
        this.Length = length;
        this.jsonObject = jsonObject;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SnipetLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View MainLayoutBackGround = getWindow().getDecorView().getRootView();
        MainLayoutBackGround.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        MainLayoutBackGround.setBackgroundResource(R.drawable.pop_background);

        binding.featuresTv.setText("Overhead Line Unbalanced");
        binding.cableID.setText("Line ID");

        if (!NetworkId.isEmpty()) {
            binding.networkId.setText(NetworkId);
        } else {
            binding.networkId.setText("");
        }

        if (!SectionId.isEmpty()) {
            binding.networkId.setText(SectionId);
        } else {
            binding.networkId.setText("");
        }

        if (!CableId.isEmpty()) {
            binding.cableID.setText(CableId.toString());
        } else {
            binding.cableID.setText("");
        }

        if (!Length.isEmpty()) {
            binding.lengthId.setText(Length);
        } else {
            binding.lengthId.setText("");
        }

        binding.imgClose.setOnClickListener(view -> {
            dismiss();
        });

        binding.moreBtn.setOnClickListener(view -> {
            UnbalanceMoreInfoDialog unbalanceMoreInfoDialog = new UnbalanceMoreInfoDialog(context,NetworkId, jsonObject);
            unbalanceMoreInfoDialog.show();
        });
    }

}
