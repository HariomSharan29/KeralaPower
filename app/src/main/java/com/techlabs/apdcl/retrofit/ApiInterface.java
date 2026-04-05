package com.techlabs.apdcl.retrofit;

import com.google.gson.JsonObject;
import com.techlabs.apdcl.models.ConsumerModel;
import com.techlabs.apdcl.models.DashBoardModel;
import com.techlabs.apdcl.models.EquipmentModel;
import com.techlabs.apdcl.models.Line.Cable;
import com.techlabs.apdcl.models.Line.Overhead;
import com.techlabs.apdcl.models.Line.Unbalanced;
import com.techlabs.apdcl.models.LoginModel;
import com.techlabs.apdcl.models.Logout;
import com.techlabs.apdcl.models.NetworkResponse;
import com.techlabs.apdcl.models.PhaseStatus;
import com.techlabs.apdcl.models.ProjectModel;
import com.techlabs.apdcl.models.ShuntReactorModel;
import com.techlabs.apdcl.models.SelectedFeedersModel;
import com.techlabs.apdcl.models.Topology;
import com.techlabs.apdcl.models.analysis.AnalysisInformationModel;
import com.techlabs.apdcl.models.analysis.FaultFlowDetailedModel;
import com.techlabs.apdcl.models.analysis.LoadAllocationModel;
import com.techlabs.apdcl.models.analysis.LoadFlowEdtModel;
import com.techlabs.apdcl.models.analysis.LoadFlowModel;
import com.techlabs.apdcl.models.analysis.ShortCircuitAnalysisModel;
import com.techlabs.apdcl.models.analysis.ShortCircuitBoxModel;
import com.techlabs.apdcl.models.analysis.ShortCircuitDetailedModel;
import com.techlabs.apdcl.models.analysis.ShortCircuitModel;
import com.techlabs.apdcl.models.dashboard.DatabaseModel;
import com.techlabs.apdcl.models.dashboard.NetworkIDModel;
import com.techlabs.apdcl.models.del.DeleteSectionModel;
import com.techlabs.apdcl.models.del.UpdateDeviceModel;
import com.techlabs.apdcl.models.device.Battery;
import com.techlabs.apdcl.models.device.Breaker;
import com.techlabs.apdcl.models.device.DashboardModel;
import com.techlabs.apdcl.models.device.Fuse;
import com.techlabs.apdcl.models.device.PhotoVoltaic;
import com.techlabs.apdcl.models.device.ShuntCapacitor;
import com.techlabs.apdcl.models.device.SpotLoad;
import com.techlabs.apdcl.models.device.Switch;
import com.techlabs.apdcl.models.device.Transformer;
import com.techlabs.apdcl.models.device.Wind;
import com.techlabs.apdcl.models.loadflow.LoadFlowBoxData;
import com.techlabs.apdcl.models.nsc.NewConnectionModel;
import com.techlabs.apdcl.models.report.AbnormalReport;
import com.techlabs.apdcl.models.report.DetailedReport;
import com.techlabs.apdcl.models.report.OverLoadConductorReport;
import com.techlabs.apdcl.models.report.OverLoadLineAndCablesReport;
import com.techlabs.apdcl.models.report.OverLoadTransformerReport;
import com.techlabs.apdcl.models.report.TransformerReport;
import com.techlabs.apdcl.models.trace.Tracing;
import com.techlabs.apdcl.models.zoom.ZoomToLayer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    @POST("AdminPanel/user/loginNew/")
    Call<LoginModel> getLogin(@Body JsonObject jsonObject);

    @POST("AdminPanel/user/logout/")
    Call<Logout> logout(@Body JsonObject jsonObject);

    @GET("Project/")
    Call<ProjectModel> getProject(@Header("Authorization") String accessToken, @Query("user_id") String userId);

    @POST("networklist/")
    Call<NetworkResponse> getNetwork(@Header("Authorization") String accessToken);

    @POST("LayermodelInfo/")
    Call<Cable> getCableData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("LayermodelInfo/")
    Call<Overhead> getOverheadData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("LayermodelInfo/")
    Call<Unbalanced> getUnbalancedData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("LayermodelInfo/")
    Call<Breaker> getBreakerData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("LayermodelInfo/")
    Call<Fuse> getFuseData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("LayermodelInfo/")
    Call<SpotLoad> getSpotLoadData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("LayermodelInfo/")
    Call<Switch> getSwitchData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("LayermodelInfo/")
    Call<Transformer> getTransformerData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("LayermodelInfo/")
    Call<ShuntCapacitor> getShuntCapacitorData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("networkzoomtolayer/")
    Call<ZoomToLayer> getNetworkZoomToLayer(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("LayermodelInfo/")
    Call<DashboardModel> getSourceData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("EquipmentInfo/")
    Call<EquipmentModel> getEquipmentData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("EquipmentInfo/")
    Call<ShortCircuitAnalysisModel> getShortCircuitCalData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("Selected_Feeder/")
    Call<SelectedFeedersModel> getSelectedFeeder(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("trace/")
    Call<Tracing> getTracingData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("loadflowbox/")
    Call<LoadFlowBoxData> getLoadFlowBoxData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("topology/")
    Call<Topology> getTopologyData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("report/")
    Call<TransformerReport> getTransformerReport(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("report/")
    Call<AbnormalReport> getAbnormalReport(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("report/")
    Call<DetailedReport> getDetailedReport(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("report/")
    Call<OverLoadConductorReport> getOverLoadConductorReport(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("report/")
    Call<OverLoadLineAndCablesReport> getOverLoadLineAndCablesReport(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("report/")
    Call<OverLoadTransformerReport> getOverLoadTransformerReport(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("shortcircuit/")
    Call<ShortCircuitModel> ShortCircuit(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("loadflow/")
    Call<LoadFlowModel> LoadFlow(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("loadflowedit/")
    Call<LoadFlowEdtModel> LoadFlowEdt(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("shortcircuitbox/")
    Call<ShortCircuitBoxModel> ShortCircuitBox(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("report/")
    Call<ShortCircuitDetailedModel> ShortCircuitDetailed(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("report/")
    Call<FaultFlowDetailedModel> FaultFlowDetailed(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("report/")
    Call<ResponseBody> downloadExcel(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("EquipmentInfo/")
    Call<EquipmentModel> getEquipmentSurveyData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("EquipmentInfo/")
    Call<PhaseStatus> getPhaseStatus(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST
    Call<JsonObject> getNetworkData(@Url String url, @Header("Authorization") String accessToken, @Body JsonObject requestData);

    @POST("dashboard/")
    Call<DashBoardModel> getDashboardData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("networklist/")
    Call<NewConnectionModel> getNewConnectionData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("dashboard/")
    Call<DatabaseModel> DatabaseList(@Header("Authorization") String accessToken, @Body JsonObject body);

    @POST("dashboard/")
    Call<NetworkIDModel> NetworkList(@Header("Authorization") String accessToken, @Body JsonObject body);

    @POST("DeviceUpdate/")
    Call<UpdateDeviceModel> deviceUpdate(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("deletefeature/")
    Call<DeleteSectionModel> deleteSection(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("loadallocation/")
    Call<LoadAllocationModel> LoadAllocation(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("analysis_information/")
    Call<AnalysisInformationModel> AnalysisInformatio(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("EquipmentInfo/")
    Call<ConsumerModel> consumerData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("LayermodelInfo/")
    Call<ShuntReactorModel> getReactorData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("LayermodelInfo/")
    Call<Battery> getBatteryData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("LayermodelInfo/")
    Call<Wind> getWindData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);

    @POST("LayermodelInfo/")
    Call<PhotoVoltaic> getPhotoVoltaicData(@Header("Authorization") String accessToken, @Body JsonObject jsonObject);
}
