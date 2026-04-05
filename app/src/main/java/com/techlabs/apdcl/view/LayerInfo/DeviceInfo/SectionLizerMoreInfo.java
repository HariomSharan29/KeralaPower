package com.techlabs.apdcl.view.LayerInfo.DeviceInfo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;

import org.json.JSONObject;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayWithIW;
import org.osmdroid.views.overlay.infowindow.MarkerInfoWindow;

public class SectionLizerMoreInfo extends MarkerInfoWindow {

    JsonObject object = new JsonObject();
    SectionLizerDialog sectionLizerDialog;

    public SectionLizerMoreInfo(int layoutResId, MapView mapView) {
        super(layoutResId, mapView);
    }

    @Override
    public void onOpen(Object item) {
        OverlayWithIW overlay = (OverlayWithIW) item;
        ((Marker) overlay).getIcon().setTint(Color.GREEN);

        sectionLizerDialog = new SectionLizerDialog(mMapView.getContext());
        String st1 = overlay.getSubDescription();
        String ReplaceString = st1.replace("<br>", "\",\"");
        String ReplaceStr = ReplaceString.replace("=", "\":\"");
        String st = ReplaceStr.replaceAll("^\"|,\"$", "}");
        String str = st.replaceFirst("", "{\"");

        TextView DeviceNumberTv = (TextView) mView.findViewById(R.id.networkId_tv);
        TextView IdTV = (TextView) mView.findViewById(R.id.sectionId_tv);
        TextView FeederIdTV = (TextView) mView.findViewById(R.id.cableId_tv);
        TextView MoreInfoBtn = (TextView) mView.findViewById(R.id.moreInfo_btn);
        TextView CloseInfoBtn = (TextView) mView.findViewById(R.id.closeInfo_btn);
        LinearLayout Infolayout = (LinearLayout) mView.findViewById(R.id.main_layout);
        Infolayout.setVisibility(View.VISIBLE);

        MoreInfoBtn.setOnClickListener(view -> {
            sectionLizerDialog.show();
        });

        CloseInfoBtn.setOnClickListener(view -> {
//            ((Marker) overlay).getIcon().setTint(Color.BLACK);
            close();
        });

        try {
            JSONObject jsonObject = new JSONObject(str);
            String Devices = jsonObject.getString("\nDeviceNumber");
            String NetworkId = jsonObject.getString("\nEquipmentId");
            String SectionId = jsonObject.getString("\nNetworkId");
            object.addProperty("DeviceNumber", jsonObject.getString("\nDeviceNumber"));
            object.addProperty("DeviceType", jsonObject.getString("\nDeviceType"));

            if (!Devices.equals("null")) {
                DeviceNumberTv.setText(Devices);
                DeviceNumberTv.setVisibility(View.VISIBLE);
            } else {
                DeviceNumberTv.setVisibility(View.GONE);
            }

            if (!NetworkId.equals("null")) {
                int index = 0;
                StringBuffer finalString = new StringBuffer();
                while (index < NetworkId.length()) {
                    finalString.append(NetworkId.substring(index, Math.min(index + 28, NetworkId.length())) + "\n");
                    index += 28;
                }
                IdTV.setText(finalString);
                IdTV.setVisibility(View.VISIBLE);
            } else {
                IdTV.setVisibility(View.GONE);
            }

            if (!SectionId.equals("null")) {
                int index = 0;
                StringBuffer finalString = new StringBuffer();
                while (index < SectionId.length()) {
                    finalString.append(SectionId.substring(index, Math.min(index + 28, SectionId.length())) + "\n");
                    index += 28;
                }
                FeederIdTV.setText(finalString);
                FeederIdTV.setVisibility(View.VISIBLE);
            } else {
                FeederIdTV.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose() {

    }

    private class SectionLizerDialog extends Dialog {

        Context mainContext;
        LinearLayout BtnLayout;
        TextView BreakerTvBtn;
        TextView NodeTvBtn;
        TextView CableTvBtn;
        LinearLayout CableInfoLayout;
        LinearLayout OverHeadInfoLayout;
        LinearLayout UnbalanceInfoLayout;
        LinearLayout NodeInfoLayout;
        LinearLayout BreakerInfoLayout;
        LinearLayout SectionInfoLayout;

        TextView SectionTv;
        CheckBox APhase;
        CheckBox BPhase;
        CheckBox CPhase;
        TextView ZoneTv;

        TextView SecIdTv;
        TextView SecNumberTv;
        TextView SecStatusTv;
        CheckBox SecReversibleChk;
        TextView SecLocationTv;
        TextView SecNormalInfeedTv;
        CheckBox SecLockedChk;
        TextView SecStateTv;
        TextView SecPhaseTv;
        TextView SecCountToLockOutTv;
        TextView SecDeadLineThreSholdTv;
        TextView SecResetTv;

        TextView SecMeterLocationTv;
        TextView SecLoadModelAKwTv;
        CheckBox SecConnectedChk;
        TextView SecKWTv;
        CheckBox SecTotalChk;

        TextView SecVal1A;
        TextView SecVal2A;
        TextView SecVal3A;
        TextView SecVal1B;
        TextView SecVal2B;
        TextView SecVal3B;
        TextView SecVal1C;
        TextView SecVal2C;
        TextView SecVal3C;


        TextView cTypesTv;
        TextView CableNumberTv;
        TextView CableStatusTv;
        TextView CableLengthTv;
        TextView CableIdTv;
        TextView NbPhaseCableTv;
        TextView CableCondTempTv;
        TextView CableTypeTv;
        TextView CableCondMaterialTv;
        TextView CableConductorSizeTv;
        TextView CableInsulationTv;

        TextView oTypesTv;
        TextView OverHeadNumberTv;
        TextView OverHeadStatusTv;
        TextView OverHeadLengthTv;
        TextView OverHeadLineIdTv;
        TextView OverHeadPositiveFirstTv;
        TextView OverHeadPositiveSecondTv;
        TextView OverHeadZeroFirstTv;
        TextView OverHeadZeroSecondTv;
        TextView UnTypesTv;
        TextView UnNumberTv;
        TextView UnStatusTv;
        TextView UnLengthTv;
        TextView UnLineIdTv;

        TextView FromIdTv;
        TextView FromXTv;
        TextView FromYTv;
        TextView ToIdTv;
        TextView ToXTv;
        TextView ToYTv;
        CheckBox xyChk;
        ImageView Img;
        TextView HeaderTv;
        JsonObject requestObject = new JsonObject();

        public SectionLizerDialog(@NonNull Context context) {
            super(context);
            this.mainContext = context;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.breaker_moreinfo_layout);
            View MainLayoutBackGround = getWindow().getDecorView().getRootView();
            MainLayoutBackGround.setBackground(getContext().getDrawable(R.drawable.pop_layout_background));

            BtnLayout = (LinearLayout) findViewById(R.id.btn_layout);
            BtnLayout.setBackground(getContext().getDrawable(R.drawable.background_layout));
            BreakerTvBtn = (TextView) findViewById(R.id.breaker_btn);
            BreakerTvBtn.setText("Sectlizar");
            CableTvBtn = (TextView) findViewById(R.id.cable_btn);
            CableTvBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            CableTvBtn.setTextColor(getContext().getColor(R.color.white));
            NodeTvBtn = (TextView) findViewById(R.id.node_btn);
            NodeTvBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
            NodeTvBtn.setTextColor(getContext().getColor(R.color.white));
            CableInfoLayout = (LinearLayout) findViewById(R.id.cable_info_layout);
            OverHeadInfoLayout = (LinearLayout) findViewById(R.id.overheadInfo_layout);
            UnbalanceInfoLayout = (LinearLayout) findViewById(R.id.unbalance_info_layout);
            NodeInfoLayout = (LinearLayout) findViewById(R.id.nodeInfo_layout);
            BreakerInfoLayout = (LinearLayout) findViewById(R.id.breaker_info_layout);
            SectionInfoLayout = findViewById(R.id.Section_info_layout);
            SectionInfoLayout.setVisibility(View.VISIBLE);
            BreakerInfoLayout.setVisibility(View.GONE);

            SectionTv = (TextView) findViewById(R.id.section_tv);
            APhase = (CheckBox) findViewById(R.id.a_chk_box);
            BPhase = (CheckBox) findViewById(R.id.b_chk_box);
            CPhase = (CheckBox) findViewById(R.id.c_chk_box);
            ZoneTv = (TextView) findViewById(R.id.zone_tv);

            SecIdTv = (TextView) findViewById(R.id.Sectiontye_tv);
            SecNumberTv = (TextView) findViewById(R.id.Sectionnumber_tv);
            SecStatusTv = (TextView) findViewById(R.id.Sectionstatus_tv);
            SecReversibleChk = (CheckBox) findViewById(R.id.Sectionreversible_chk);
            SecLocationTv = (TextView) findViewById(R.id.Sectionlength_tv);
            SecNormalInfeedTv = (TextView) findViewById(R.id.normalInfeedTv);
            SecNormalInfeedTv.setText("Default");
            SecLockedChk = (CheckBox) findViewById(R.id.locker_chk);
            SecStateTv = (TextView) findViewById(R.id.Sectionstate_tv);
            SecPhaseTv = (TextView) findViewById(R.id.phaseTv);
            SecCountToLockOutTv = (TextView) findViewById(R.id.secCountLookoutTv);
            SecDeadLineThreSholdTv = (TextView) findViewById(R.id.deadlineThresholdTV);
            SecResetTv = (TextView) findViewById(R.id.resetTimeTv);

            SecMeterLocationTv = (TextView) findViewById(R.id.locationTv);
            SecLoadModelAKwTv = (TextView) findViewById(R.id.SectionLoadModel_tv);
            SecConnectedChk = (CheckBox) findViewById(R.id.Sectionconnected_chk);
            SecKWTv = (TextView) findViewById(R.id.Sectionkw_kvar_tv);
            SecTotalChk = (CheckBox) findViewById(R.id.Sectiontotal_chk);

            SecVal1A = (TextView) findViewById(R.id.val1A);
            SecVal2A = (TextView) findViewById(R.id.val2A);
            SecVal3A = (TextView) findViewById(R.id.val3A);

            SecVal1B = (TextView) findViewById(R.id.val1B);
            SecVal2B = (TextView) findViewById(R.id.val2B);
            SecVal3B = (TextView) findViewById(R.id.val3B);

            SecVal1C = (TextView) findViewById(R.id.val1C);
            SecVal2C = (TextView) findViewById(R.id.val2C);
            SecVal3C = (TextView) findViewById(R.id.val3C);

            cTypesTv = findViewById(R.id.type_tv);
            CableNumberTv = findViewById(R.id.cnumber_tv);
            CableStatusTv = findViewById(R.id.cstatus_tv);
            CableLengthTv = findViewById(R.id.clength_tv);
            CableIdTv = findViewById(R.id.ccable_id_tv);
            NbPhaseCableTv = findViewById(R.id.cnb_cable_phase_tv);
            CableCondTempTv = findViewById(R.id.condTemp_tv);
            CableTypeTv = findViewById(R.id.cableType_tv);
            CableCondMaterialTv = findViewById(R.id.conductorMaterial_tv);
            CableConductorSizeTv = findViewById(R.id.conductorSize_tv);
            CableInsulationTv = findViewById(R.id.insulationType_tv);

            oTypesTv = findViewById(R.id.overheadTye_tv);
            OverHeadNumberTv = findViewById(R.id.overheadNumber_tv);
            OverHeadStatusTv = findViewById(R.id.overheadStatus_tv);
            OverHeadLengthTv = findViewById(R.id.overheadLength_tv);
            OverHeadLineIdTv = findViewById(R.id.overheadLengthId_tv);
            OverHeadPositiveFirstTv = findViewById(R.id.positiveSequenceFirstTv);
            OverHeadPositiveSecondTv = findViewById(R.id.positiveSequenceSecondTv);
            OverHeadZeroFirstTv = findViewById(R.id.zeroSequenceFirstTv);
            OverHeadZeroSecondTv = findViewById(R.id.zeroSequenceSecondTv);

            UnTypesTv = findViewById(R.id.untye_tv);
            UnNumberTv = findViewById(R.id.unnumber_tv);
            UnStatusTv = findViewById(R.id.unstatus_tv);
            UnLengthTv = findViewById(R.id.unlength_tv);
            UnLineIdTv = findViewById(R.id.unbalanceLineId_tv);

            FromIdTv = findViewById(R.id.id_fromNodes_tv);
            FromXTv = findViewById(R.id.x_fromNodes_tv);
            FromYTv = findViewById(R.id.y_fromNodes_tv);
            ToIdTv = findViewById(R.id.id_toNode_tv);
            ToXTv = findViewById(R.id.x_toNode_tv);
            ToYTv = findViewById(R.id.y_toNodes_tv);
            xyChk = findViewById(R.id.cor_type_chk);
            Img = findViewById(R.id.imgClose);
            HeaderTv = findViewById(R.id.header_title);
            HeaderTv.setText("SectionLizer");

            Img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            BreakerTvBtn.setOnClickListener(view -> {
                BreakerTvBtn.setBackground(getContext().getDrawable(R.drawable.pop_btn_background));
                BreakerTvBtn.setTextColor(getContext().getColor(R.color.black));
                CableTvBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
                CableTvBtn.setTextColor(getContext().getColor(R.color.white));
                NodeTvBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
                NodeTvBtn.setTextColor(getContext().getColor(R.color.white));
                SectionInfoLayout.setVisibility(View.VISIBLE);
                CableInfoLayout.setVisibility(View.GONE);
                OverHeadInfoLayout.setVisibility(View.GONE);
                UnbalanceInfoLayout.setVisibility(View.GONE);
                NodeInfoLayout.setVisibility(View.GONE);
            });

            CableTvBtn.setOnClickListener(view -> {
                /*ArcConfiguration configuration = new ArcConfiguration(mainContext);
                configuration.setLoaderStyle(SimpleArcLoader.STYLE.SIMPLE_ARC);
                configuration.setColors(new int[]{mainContext.getColor(R.color.blue)});
                configuration.setAnimationSpeedWithIndex(SimpleArcLoader.SPEED_MEDIUM);
                configuration.setText("Please wait..");
                SimpleArcDialog simpleArcDialog = new SimpleArcDialog(mainContext);
                simpleArcDialog.setConfiguration(configuration);
                simpleArcDialog.setCancelable(false);
                simpleArcDialog.show();*/
                CableTvBtn.setBackground(getContext().getDrawable(R.drawable.pop_btn_background));
                CableTvBtn.setTextColor(getContext().getColor(R.color.black));
                NodeTvBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
                NodeTvBtn.setTextColor(getContext().getColor(R.color.white));
                BreakerTvBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
                BreakerTvBtn.setTextColor(getContext().getColor(R.color.white));
                SectionInfoLayout.setVisibility(View.GONE);
                NodeInfoLayout.setVisibility(View.GONE);

                if (CableTvBtn.getText().toString().equals("Cable")) {
                    CableInfoLayout.setVisibility(View.VISIBLE);
                    OverHeadInfoLayout.setVisibility(View.GONE);
                    UnbalanceInfoLayout.setVisibility(View.GONE);
                    UnbalanceInfoLayout.setVisibility(View.GONE);
                } else if (CableTvBtn.getText().toString().equals("Balance")) {
                    OverHeadInfoLayout.setVisibility(View.VISIBLE);
                    CableInfoLayout.setVisibility(View.GONE);
                    UnbalanceInfoLayout.setVisibility(View.GONE);
                } else {
                    UnbalanceInfoLayout.setVisibility(View.VISIBLE);
                    OverHeadInfoLayout.setVisibility(View.GONE);
                    CableInfoLayout.setVisibility(View.GONE);
                    UnbalanceInfoLayout.setVisibility(View.GONE);
                }
                /*ApiInterface apiInterface = RetrofitClient1.getClient().create(ApiInterface.class);
                Call<JsonObject> call = apiInterface.SendJsonRequestWithBody("androidLayerApiInfo/", requestObject);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject jsonObject1 = response.body();
                        if (response.code() == 200) {
                            simpleArcDialog.dismiss();
                            try {
                                JSONObject jsonObject2 = new JSONObject(jsonObject1.toString());
                                JSONArray jsonArray = new JSONArray();
                                if (jsonObject2 != null && !jsonObject2.toString().trim().equals("null") && !jsonObject2.toString().trim().isEmpty()) {
                                    jsonArray = jsonObject2.getJSONArray("output");

                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject = jsonArray.getJSONObject(0);

                                    if (CableTvBtn.getText().toString().equals("Cable")) {
                                        if (!jsonObject.getString("DeviceType").isEmpty() && !jsonObject.getString("DeviceType").equals("null") && jsonObject.getString("DeviceType") != null) {
                                            cTypesTv.setText(jsonObject.getString("DeviceType"));
                                        } else {
                                            cTypesTv.setText("Not Available");
                                        }

                                        if (!jsonObject.getString("DeviceNumber").isEmpty() && !jsonObject.getString("DeviceNumber").equals("null") && jsonObject.getString("DeviceNumber") != null) {
                                            CableNumberTv.setText(jsonObject.getString("DeviceNumber"));
                                        } else {
                                            CableNumberTv.setText("Not Available");
                                        }

                                        if (!jsonObject.getString("Status").isEmpty() && !jsonObject.getString("Status").equals("null") && jsonObject.getString("Status") != null) {
//                                            CableStatusTv.setText(jsonObject.getString("Status"));
                                            if (jsonObject.getString("Status").equals("0")) {
                                                CableStatusTv.setText("Connected");
                                            } else if (jsonObject.getString("Status").equals("1")) {
                                                CableStatusTv.setText("DisConnected");
                                            } else {
                                                CableStatusTv.setText("By Passed");
                                            }
                                        } else {
                                            CableStatusTv.setText("Not Available");
                                        }

                                        if (!jsonObject.getString("Length").isEmpty() && !jsonObject.getString("Length").equals("null") && jsonObject.getString("Length") != null) {
                                            CableLengthTv.setText(jsonObject.getString("Length") + " " + "m");
                                        } else {
                                            CableLengthTv.setText("Not Available");
                                        }

                                        if (!jsonObject.getString("CableId").isEmpty() && !jsonObject.getString("CableId").equals("null") && jsonObject.getString("CableId") != null) {
                                            CableIdTv.setText(jsonObject.getString("CableId"));
                                        } else {
                                            CableIdTv.setText("Not Available");
                                        }

                                        if (!jsonObject.getString("NumberOfCableInParallel").isEmpty() && !jsonObject.getString("NumberOfCableInParallel").equals("null") && jsonObject.getString("NumberOfCableInParallel") != null) {
                                            NbPhaseCableTv.setText(jsonObject.getString("NumberOfCableInParallel"));
                                        } else {
                                            NbPhaseCableTv.setText("Not Available");
                                        }

                                        if (!jsonObject.getString("OperatingTemperature").isEmpty() && !jsonObject.getString("OperatingTemperature").equals("null") && jsonObject.getString("OperatingTemperature") != null) {
                                            CableCondTempTv.setText(jsonObject.getString("OperatingTemperature") + "°F");
                                        } else {
                                            CableCondTempTv.setText("Not Available");
                                        }

                                        if (!jsonObject.getString("CableType").isEmpty() && !jsonObject.getString("CableType").equals("null") && jsonObject.getString("CableType") != null) {
                                            CableTypeTv.setText(jsonObject.getString("CableType") + " " + "c");
                                        } else {
                                            CableTypeTv.setText("Not Available");
                                        }

                                        if (!jsonObject.getString("MaterialID").isEmpty() && !jsonObject.getString("MaterialID").equals("null") && jsonObject.getString("MaterialID") != null) {
                                            CableCondMaterialTv.setText(jsonObject.getString("MaterialID"));
                                        } else {
                                            CableCondMaterialTv.setText("Not Available");
                                        }

                                        if (!jsonObject.getString("Size_mm2").isEmpty() && !jsonObject.getString("Size_mm2").equals("null") && jsonObject.getString("Size_mm2") != null) {
                                            CableConductorSizeTv.setText(jsonObject.getString("Size_mm2") + " " + "mm²");
                                        } else {
                                            CableConductorSizeTv.setText("Not Available");
                                        }

                                        if (!jsonObject.getString("InsulationMaterialID").isEmpty() && !jsonObject.getString("InsulationMaterialID").equals("null") && jsonObject.getString("InsulationMaterialID") != null) {
                                            CableInsulationTv.setText(jsonObject.getString("InsulationMaterialID"));
                                        } else {
                                            CableInsulationTv.setText("Not Available");
                                        }
                                    } else if (CableTvBtn.getText().toString().equals("Balance")) {
                                        if (!jsonObject.getString("DeviceType").isEmpty() && !jsonObject.getString("DeviceType").equals("null") && jsonObject.getString("DeviceType") != null) {
                                            oTypesTv.setText(jsonObject.getString("DeviceType"));
                                        } else {
                                            oTypesTv.setText("Not Available");
                                        }

                                        if (!jsonObject.getString("DeviceNumber").isEmpty() && !jsonObject.getString("DeviceNumber").equals("null") && jsonObject.getString("DeviceNumber") != null) {
                                            OverHeadNumberTv.setText(jsonObject.getString("DeviceNumber"));
                                        } else {
                                            OverHeadNumberTv.setText("Not Available");
                                        }

                                        if (!jsonObject.getString("Status").isEmpty() && !jsonObject.getString("Status").equals("null") && jsonObject.getString("Status") != null) {
//                                            OverHeadStatusTv.setText(jsonObject.getString("Status"));
                                            if (jsonObject.getString("Status").equals("0")) {
                                                OverHeadStatusTv.setText("Connected");
                                            } else if (jsonObject.getString("Status").equals("1")) {
                                                OverHeadStatusTv.setText("DisConnected");
                                            } else {
                                                OverHeadStatusTv.setText("By Passed");
                                            }
                                        } else {
                                            OverHeadStatusTv.setText("Not Available");
                                        }

                                        if (!jsonObject.getString("Length").isEmpty() && !jsonObject.getString("Length").equals("null") && jsonObject.getString("Length") != null) {
                                            OverHeadLengthTv.setText(jsonObject.getString("Length") + " " + "ft");
                                        } else {
                                            OverHeadLengthTv.setText("Not Available");
                                        }

                                        if (!jsonObject.getString("LineId").isEmpty() && !jsonObject.getString("LineId").equals("null") && jsonObject.getString("LineId") != null) {
                                            OverHeadLineIdTv.setText(jsonObject.getString("LineId"));
                                        } else {
                                            OverHeadLineIdTv.setText("Not Available");
                                        }

                                        if (!jsonObject.getString("PositiveSequenceResistance").isEmpty() && !jsonObject.getString("PositiveSequenceResistance").equals("null") && jsonObject.getString("PositiveSequenceResistance") != null) {
                                            OverHeadPositiveFirstTv.setText(jsonObject.getString("PositiveSequenceResistance") + " " + "R + jXΩ/km");
                                        } else {
                                            OverHeadPositiveFirstTv.setText("Not Available");
                                        }

                                        if (!jsonObject.getString("PositiveSequenceReactance").isEmpty() && !jsonObject.getString("PositiveSequenceReactance").equals("null") && jsonObject.getString("PositiveSequenceReactance") != null) {
                                            OverHeadPositiveSecondTv.setText(jsonObject.getString("PositiveSequenceReactance") + " " + "G + jBµS/km");
                                        } else {
                                            OverHeadPositiveSecondTv.setText("Not Available");
                                        }

                                        if (!jsonObject.getString("ZeroSequenceResistance").isEmpty() && !jsonObject.getString("ZeroSequenceResistance").equals("null") && jsonObject.getString("ZeroSequenceResistance") != null) {
                                            OverHeadZeroFirstTv.setText(jsonObject.getString("ZeroSequenceResistance") + " " + "R + jXΩ/km");
                                        } else {
                                            OverHeadZeroFirstTv.setText("Not Available");
                                        }

                                        if (!jsonObject.getString("ZeroSequenceReactance").isEmpty() && !jsonObject.getString("ZeroSequenceReactance").equals("null") && jsonObject.getString("ZeroSequenceReactance") != null) {
                                            OverHeadZeroSecondTv.setText(jsonObject.getString("ZeroSequenceReactance") + " " + "G + jBµS/km");
                                        } else {
                                            OverHeadZeroSecondTv.setText("Not Available");
                                        }
                                    } else {
                                        if (!jsonObject.getString("DeviceType").isEmpty() && !jsonObject.getString("DeviceType").equals("null") && jsonObject.getString("DeviceType") != null) {
                                            UnTypesTv.setText(jsonObject.getString("DeviceType"));
                                        } else {
                                            UnTypesTv.setText("Not Available");
                                        }

                                        if (!jsonObject.getString("DeviceNumber").isEmpty() && !jsonObject.getString("DeviceNumber").equals("null") && jsonObject.getString("DeviceNumber") != null) {
                                            UnNumberTv.setText(jsonObject.getString("DeviceNumber"));
                                        } else {
                                            UnNumberTv.setText("Not Available");
                                        }

                                        if (!jsonObject.getString("Status").isEmpty() && !jsonObject.getString("Status").equals("null") && jsonObject.getString("Status") != null) {
//                                            UnStatusTv.setText(jsonObject.getString("Status"));
                                            if (jsonObject.getString("Status").equals("0")) {
                                                UnStatusTv.setText("Connected");
                                            } else if (jsonObject.getString("Status").equals("1")) {
                                                UnStatusTv.setText("DisConnected");
                                            } else {
                                                UnStatusTv.setText("By Passed");
                                            }
                                        } else {
                                            UnStatusTv.setText("Not Available");
                                        }

                                        if (!jsonObject.getString("Length").isEmpty() && !jsonObject.getString("Length").equals("null") && jsonObject.getString("Length") != null) {
                                            UnLengthTv.setText(jsonObject.getString("Length") + " " + "ft");
                                        } else {
                                            UnLengthTv.setText("Not Available");
                                        }

                                        if (!jsonObject.getString("LineId").isEmpty() && !jsonObject.getString("LineId").equals("null") && jsonObject.getString("LineId") != null) {
                                            UnLineIdTv.setText(jsonObject.getString("LineId"));
                                        } else {
                                            UnLineIdTv.setText("Not Available");
                                        }
                                    }

                                } else {
                                    Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {

                            }
                        } else {
                            simpleArcDialog.dismiss();
                            final Dialog dialog = new Dialog(getContext());
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.dialog_demo);
                            TextView dialog_info = dialog.findViewById(R.id.dialog_info);
                            TextView header = dialog.findViewById(R.id.header);
                            header.setText(response.message() + " - " + response.code());
                            dialog_info.setText(getContext().getString(R.string.error_msg));
                            Button Accept = dialog.findViewById(R.id.dialog_ok);
                            Accept.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            Button Cancel = dialog.findViewById(R.id.dialog_cancel);
//                            Cancel.setOnClickListener(v -> );
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.setCancelable(false);
                            dialog.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        simpleArcDialog.dismiss();
                        final Dialog dialog = new Dialog(getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_demo);
                        TextView dialog_info = dialog.findViewById(R.id.dialog_info);
                        TextView header = dialog.findViewById(R.id.header);
                        header.setText(getContext().getString(R.string.error));
                        dialog_info.setText(getContext().getString(R.string.error_msg));
                        Button Accept = dialog.findViewById(R.id.dialog_ok);
                        Accept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //onRefresh();
                                dialog.dismiss();
                            }
                        });
                        Button Cancel = dialog.findViewById(R.id.dialog_cancel);
                        Cancel.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onClick(View v) {
//                                .finishAffinity();
                            }
                        });
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setCancelable(false);
                        dialog.show();
                    }
                });*/

            });

            NodeTvBtn.setOnClickListener(view -> {
                NodeTvBtn.setBackground(getContext().getDrawable(R.drawable.pop_btn_background));
                NodeTvBtn.setTextColor(getContext().getColor(R.color.black));
                CableTvBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
                CableTvBtn.setTextColor(getContext().getColor(R.color.white));
                BreakerTvBtn.setBackground(getContext().getDrawable(R.drawable.background_layout));
                BreakerTvBtn.setTextColor(getContext().getColor(R.color.white));
                NodeInfoLayout.setVisibility(View.VISIBLE);
                SectionInfoLayout.setVisibility(View.GONE);
                CableInfoLayout.setVisibility(View.GONE);
                OverHeadInfoLayout.setVisibility(View.GONE);
                UnbalanceInfoLayout.setVisibility(View.GONE);

            });

        }
    }

}
