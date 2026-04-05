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

public class OverheadSnippet extends Dialog {

    private Context context;
    private SnipetLayoutBinding binding;
    private String NetworkId;
    private String SectionId;
    private String lineId;
    private String Length;
    private JsonObject jsonObject;
    private String voltage;

    public OverheadSnippet(@NonNull Context context, String networkId, String sectionId, String lineId, String length, String voltage, JsonObject jsonObject) {
        super(context);
        this.context = context;
        this.NetworkId = networkId;
        this.SectionId = sectionId;
        this.lineId = lineId;
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

        binding.featuresTv.setText("OverHead Line Balanced");
        binding.cableTV.setText("Line ID");

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

        if (!lineId.isEmpty()) {
            binding.cableID.setText(lineId);
        } else {
            binding.cableID.setText(lineId);
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
            OverHeadMoreInfoDialog overHeadMoreInfoDialog = new OverHeadMoreInfoDialog(context,NetworkId, jsonObject,this);
            overHeadMoreInfoDialog.show();
        });
    }

}
