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

public class CableSnippet extends Dialog {

    private Context context;
    private SnipetLayoutBinding binding;
    private String NetworkId;
    private String SectionId;
    private String CableId;
    private String Length;
    private JsonObject jsonObject;
    private String voltage;

    public CableSnippet(@NonNull Context context, String networkId, String sectionId, String cableId, String length, String voltage, JsonObject jsonObject) {
        super(context);
        this.context = context;
        this.NetworkId = networkId;
        this.SectionId = sectionId;
        this.CableId = cableId;
        this.Length = length;
        this.jsonObject = jsonObject;
        this.voltage = voltage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SnipetLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View MainLayoutBackGround = getWindow().getDecorView().getRootView();
        MainLayoutBackGround.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        MainLayoutBackGround.setBackgroundResource(R.drawable.pop_background);

        if (!NetworkId.isEmpty()) {
            binding.networkId.setText(NetworkId);
        } else {
            binding.networkId.setText("");
        }

        if (!SectionId.isEmpty()) {
            binding.sectionId.setText(SectionId);
        } else {
            binding.sectionId.setText("");
        }

        if (!CableId.isEmpty()) {
            binding.cableID.setText(CableId);
        } else {
            binding.cableID.setText(CableId);
        }

        if (!Length.isEmpty()) {
            binding.lengthId.setText(Length + " " + "m");
        } else {
            binding.lengthId.setText("");
        }

        binding.imgClose.setOnClickListener(view -> {
            dismiss();
        });

        binding.moreBtn.setOnClickListener(view -> {
            CableMoreInfoDialog cableMoreInfoDialog = new CableMoreInfoDialog(context, NetworkId, voltage, jsonObject,this);
            cableMoreInfoDialog.show();
        });
    }

}
