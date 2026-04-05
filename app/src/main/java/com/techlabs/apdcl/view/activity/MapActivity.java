package com.techlabs.apdcl.view.activity;

import com.google.gson.reflect.TypeToken;
import com.techlabs.apdcl.Utils.custom.ErrorPdfLogger;
import static com.techlabs.apdcl.Utils.Config.networkIdList;
import static com.techlabs.apdcl.Utils.ResponseDataUtils.addPaddingToBitmap;
import static com.techlabs.apdcl.Utils.ResponseDataUtils.changeBgTransparentBitmapColor;
import static com.techlabs.apdcl.Utils.ResponseDataUtils.changeBitmapColor;
import static com.techlabs.apdcl.Utils.ResponseDataUtils.changeSourceColor;
import static com.techlabs.apdcl.Utils.ResponseDataUtils.drawableToBitmap;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.techlabs.apdcl.R;
import com.techlabs.apdcl.Utils.AddDevice;
import com.techlabs.apdcl.Utils.Args;
import com.techlabs.apdcl.Utils.BitmapImg;
import com.techlabs.apdcl.Utils.Config;
import com.techlabs.apdcl.Utils.LoadAllocationArgument;
import com.techlabs.apdcl.Utils.LoadFlowArgument;
import com.techlabs.apdcl.Utils.custom.ItemViewHelper;
import com.techlabs.apdcl.Utils.MapLayer;
import com.techlabs.apdcl.Utils.OTFToBitmapConverter;
import com.techlabs.apdcl.Utils.PrefManager;
import com.techlabs.apdcl.Utils.ProgressBarLayout;
import com.techlabs.apdcl.Utils.ResponseDataUtils;
import com.techlabs.apdcl.Utils.ShortCircuitArgument;
import com.techlabs.apdcl.Utils.UTMConversion;
import com.techlabs.apdcl.adapters.SelectedFeedersAdapter;
import com.techlabs.apdcl.databinding.ActivityMapBinding;
import com.techlabs.apdcl.databinding.FeasibilityBinding;
import com.techlabs.apdcl.databinding.NodePopLayoutBinding;
import com.techlabs.apdcl.models.ConsumerModel;
import com.techlabs.apdcl.models.Continent;
import com.techlabs.apdcl.models.DType;
import com.techlabs.apdcl.models.DeviceName;
import com.techlabs.apdcl.models.Topology;
import com.techlabs.apdcl.models.dashboard.DatabaseModel;
import com.techlabs.apdcl.models.analysis.LoadAllocationModel;
import com.techlabs.apdcl.models.analysis.LoadFlowEdtModel;
import com.techlabs.apdcl.models.analysis.LoadFlowModel;
import com.techlabs.apdcl.models.analysis.ShortCircuitModel;
import com.techlabs.apdcl.models.del.DeleteSectionModel;
import com.techlabs.apdcl.models.nsc.NewConnectionModel;
import com.techlabs.apdcl.models.trace.Tracing;
import com.techlabs.apdcl.models.zoom.ZoomToLayer;
import com.techlabs.apdcl.retrofit.ApiInterface;
import com.techlabs.apdcl.retrofit.RetrofitClient;
import com.techlabs.apdcl.view.LayerInfo.DeviceInfo.BreakerSnippet;
import com.techlabs.apdcl.view.LayerInfo.DeviceInfo.BatterySnippet;
import com.techlabs.apdcl.view.LayerInfo.DeviceInfo.FuseSnippet;
import com.techlabs.apdcl.view.LayerInfo.DeviceInfo.PhotoVoltaicSnippet;
import com.techlabs.apdcl.view.LayerInfo.DeviceInfo.RecloserSnippet;
import com.techlabs.apdcl.view.LayerInfo.DeviceInfo.SectionLizerMoreInfo;
import com.techlabs.apdcl.view.LayerInfo.DeviceInfo.ShuntCapacitorSnippet;
import com.techlabs.apdcl.view.LayerInfo.DeviceInfo.ShuntReactorSnippet;
import com.techlabs.apdcl.view.LayerInfo.DeviceInfo.SourceDialog;
import com.techlabs.apdcl.view.LayerInfo.DeviceInfo.SpotLoadSnippet;
import com.techlabs.apdcl.view.LayerInfo.DeviceInfo.SwitchSnippet;
import com.techlabs.apdcl.view.LayerInfo.DeviceInfo.TransformerSnippet;
import com.techlabs.apdcl.view.LayerInfo.DeviceInfo.WindSnippet;
import com.techlabs.apdcl.view.LayerInfo.LineInfo.CableSnippet;
import com.techlabs.apdcl.view.LayerInfo.LineInfo.OverheadSnippet;
import com.techlabs.apdcl.view.LayerInfo.LineInfo.UnbalanceSnippet;
import com.techlabs.apdcl.view.fragment.LoadAllocation;
import com.techlabs.apdcl.view.fragment.LoadFlow;
import com.techlabs.apdcl.view.fragment.LoadFlowBox;
import com.techlabs.apdcl.view.fragment.ShortCircuit;
import com.techlabs.apdcl.view.fragment.ShortCircuitBox;
import com.techlabs.apdcl.view.newConnection.SectionDeviceDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.bonuspack.kml.KmlFeature;
import org.osmdroid.bonuspack.kml.KmlLineString;
import org.osmdroid.bonuspack.kml.KmlPlacemark;
import org.osmdroid.bonuspack.kml.KmlPoint;
import org.osmdroid.bonuspack.kml.KmlPolygon;
import org.osmdroid.bonuspack.kml.KmlTrack;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.FolderOverlay;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Stack;
import java.util.function.BiConsumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener, LoadFlowArgument, MapEventsReceiver, ShortCircuitArgument, LoadAllocationArgument, AddDevice {

    private ActivityMapBinding binding;
    private KmlDocument CableKml = null;
    private KmlDocument OverHeadKml = null;
    private KmlDocument UnBalencedKml = null;
    private KmlDocument sectionNodeKml = null;
    private KmlDocument BreakarKml = null;
    private KmlDocument SwitchKml = null;
    private KmlDocument ReclosuerKml = null;
    private KmlDocument FuseKml = null;
    private KmlDocument TransformerKml = null;
    private KmlDocument ShuntCapacitorKml = null;
    private KmlDocument SpotloadKml = null;
    //Add New
    private KmlDocument RecloserKml = null;
    private KmlDocument PhotoVoltaicKml = null;
    private KmlDocument BatteryKml = null;
    private KmlDocument WindKml = null;
    private KmlDocument ReactorKml = null;

    private KmlDocument NodeKml = null;
    private KmlDocument sourceKml = null;
    private GeoPoint sourcePoint;
    private FolderOverlay CableFolderOverLay = null;
    private FolderOverlay OverheadFolderOverLay = null;
    private FolderOverlay UnBalanceFolderOverLay = null;
    private FolderOverlay sectionFolderOverLay = null;
    private FolderOverlay substationOverLay = null;
    private FolderOverlay CircuitBreakerOverLay = null;
    private FolderOverlay SectionLizerOverLay = null;
    private FolderOverlay DistributionTransferOverLay = null;
    private FolderOverlay FuseOverLay = null;
    private FolderOverlay SwitchOverLay = null;
    private FolderOverlay ReclosureOverLay = null;
    private FolderOverlay ShuntCapacitorOverLay = null;
    private FolderOverlay SpotLoadOverLay = null;
    private FolderOverlay SourceOverLay = null;
    private FolderOverlay RecloserOverLay = null;
    private FolderOverlay PhotoVoltaicOverLay = null;
    private FolderOverlay BatteryOverLay = null;
    private FolderOverlay WindOverLay = null;
    private FolderOverlay ReactorOverLay = null;
    private FolderOverlay nodeOverLay = null;
    private JSONObject CaObject;
    private JSONObject OhObject;
    private JSONObject UnBalObject;
    private JSONObject SecNodeObject;
    private MapController mc;
    private JSONObject object = new JSONObject();
    private ArrayList<DeviceName> mList = new ArrayList<>();
    private FiltersAdapter adapters;
    private ArrayList<Continent> continentList = new ArrayList<Continent>();
    private ExpandableDeviceAdapter adapter;
    private String networkId = null;
    private String nodeId = null;
    private List<String> CaSectionList = new ArrayList<>();
    private List<String> OhSectionList = new ArrayList<>();
    private List<String> spLineSectionList = new ArrayList<>();
    private List<Polyline> CaPolylineList = new ArrayList<>();
    private Map<String, Polyline> CaSectionId = new HashMap<>();
    private List<Polyline> ohPolylineList = new ArrayList<>();
    private Map<String, Polyline> OhSectionId = new HashMap<>();
    private List<Polyline> unBalPolylineList = new ArrayList<>();
    private Map<String, Polyline> UnBalSectionId = new HashMap<>();
    private List<Polyline> secNodeList = new ArrayList<>();
    private final Map<String, Polyline> secNodeSectionId = new HashMap<>();
    private Map<String, Marker> breakerSectionId = new HashMap<>();
    private List<Marker> breakerList = new ArrayList<>();
    private Map<String, Marker> transformerSectionId = new HashMap<>();
    private List<Marker> transformerList = new ArrayList<>();
    private Map<String, Marker> fuseSectionId = new HashMap<>();
    private List<Marker> fuseList = new ArrayList<>();
    private Map<String, Marker> switchSectionId = new HashMap<>();
    private List<Marker> switchedList = new ArrayList<>();
    private Map<String, Marker> reclosureSectionId = new HashMap<>();
    private List<Marker> reclosureList = new ArrayList<>();
    private Map<String, Marker> capacitorSectionId = new HashMap<>();
    private List<Marker> capacitorList = new ArrayList<>();
    private Map<String, Marker> spotloadSectionId = new HashMap<>();
    private List<Marker> spotLoadList = new ArrayList<>();
    private LoadFlow loadFlow;
    private ShortCircuit shortCircuit;
    private String overVoltageColors = null;
    private String underVoltageColors = null;
    private String overloadColors = null;
    private String ratingColors = null;
    private static final int REQUEST_CODE_CHILD_ACTIVITY = 123;
    private PrefManager prefManager;
    private List<GeoPoint> newConnectionGeoPoint = new ArrayList<>();
    private Marker selectedNode;
    private Map<String, Polyline> loadFlowOverLoadSectionID = new HashMap<>();
    private Map<String, Marker> loadFlowOverLoadDeviceID = new HashMap<>();
    private Map<String, Polyline> loadFlowOverVoltageSectionID = new HashMap<>();
    private Map<String, Marker> loadFlowOverVoltageDeviceID = new HashMap<>();
    private Map<String, Polyline> loadFlowUnderVoltageSectionID = new HashMap<>();
    private Map<String, Marker> loadFlowUnderVoltageDeviceID = new HashMap<>();
    private Map<String, Polyline> shortCircuitRatingSectionID = new HashMap<>();
    private Map<String, Marker> shortCircuitRatingDeviceID = new HashMap<>();
    private Map<String, Polyline> shortCircuitOverLoadSectionID = new HashMap<>();
    private Map<String, Marker> shortCircuitOverLoadDeviceID = new HashMap<>();
    private Map<String, Polyline> shortCircuitOverVoltageSectionID = new HashMap<>();
    private Map<String, Marker> shortCircuitOverVoltageDeviceID = new HashMap<>();
    private Map<String, Polyline> shortCircuitUnderVoltageSectionID = new HashMap<>();
    private Map<String, Marker> shortCircuitUnderVoltageDeviceID = new HashMap<>();
    private Marker previousSelectedDevice;
    private String deviceType;
    private String sectionID;
    private Polyline previousSelectedSection;
    private String sectionType;
    private Marker previousLoadFlowDevice;
    private String loadFlowDeviceType;
    private String loadFlowDeviceId;
    private Polyline loadFlowPreviousSelectedSection;
    private String loadFlowSectionId;
    private Marker shortCircuitPreviousDevice;
    private String shortCircuitDeviceType;
    private String shortCircuitDeviceId;
    private Polyline shortCircuitPreviousSelectedSection;
    private String shortCircuitSectionId;
    private List<String> loadFlowOverVoltageSectionId = new ArrayList<>();
    private List<String> loadFlowUnderVoltageSectionId = new ArrayList<>();
    private List<String> loadFlowOverLoadSectionId = new ArrayList<>();
    private List<String> shortCircuitRatingSectionId = new ArrayList<>();
    private List<String> shortCircuitOverVoltageSectionId = new ArrayList<>();
    private List<String> shortCircuitUnderVoltageSectionId = new ArrayList<>();
    private List<String> shortCircuitOverLoadSectionId = new ArrayList<>();
    private Boolean isTracing = false;
    private Intent intent;
    private String DelDeviceNumber;
    private Map<String, Marker> DelDevice = new HashMap<>();
    private String DelSectionId;
    private Map<String, Polyline> DelSection = new HashMap<>();
    private ProgressBarLayout progressBarLayout;
    private int delay = 200;
    private int overVolatgeCount;
    private int overLoadCount;
    private int underVolatgeCount;
    private int ratingCount;
    private List<String> loadFlowList = new ArrayList<>();
    private List<String> shortCircuitList = new ArrayList<>();
    private ArrayList<String> selectedFeeder = new ArrayList<>();
    private boolean isBounding = false;
    private boolean isTopology = false;
    private String selectedNodeID;
    private List<GeoPoint> newSectionGeoPointList = new ArrayList<>();
    private String nodeIdX;
    private String nodeIdY;
    private String defaultVoltage;
    private String selectedNodeVoltage;
    private List<Marker> vertexList = new ArrayList<>();
    private List<Polyline> newPolyLineList = new ArrayList<>();
    private List<GeoPoint> coordinateList = new ArrayList<>();
    private Map<String, Polyline> polylineMap;
    private SectionDeviceDialog sectionDialog;
    private PopupMenu popupMenu;
    private boolean isrEnabled = false;
    private NetworkLoaderDialog networkLoaderDialog;
    private SelectedFeedersAdapter rvAdapter;
    private final ArrayList<Object> feederNavigationItems = new ArrayList<>();
    private final ArrayList<String> checkedFeederIds = new ArrayList<>();
    private final HashSet<String> nestedExpandedKeys = new HashSet<>();
    private List<Marker> shuntReactorList = new ArrayList<>();
    private Map<String, Marker> shuntReactorSectionId = new HashMap<>();
    private final RecyclerView.RecycledViewPool drawerRecycledViewPool = new RecyclerView.RecycledViewPool();
    final boolean[] isExpanded = {false};
    GeoPoint center;

    @SuppressLint({"ClickableViewAccessibility", "MissingInflatedId", "WrongViewCast", "NonConstantResourceId", "UseCompatLoadingForDrawables", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll()
                .permitUnbufferedIo()
                .build();
        StrictMode.setThreadPolicy(policy);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        setStatusBarTransparent(this);
        polylineMap = new HashMap<>();
        progressBarLayout = binding.progressBarLayout;
        progressBarLayout.startAnimation(delay);
        progressBarLayout.setVisibility(View.GONE);
        prefManager = new PrefManager(MapActivity.this);
        Config.isLoadFlow = false;
        Config.isShortCircuit = false;
        Config.isLoadAllocation = false;
        Config.isPhaseColor = false;
        Config.isSystemVoltageColor = false;
        Config.isLayerColor = true;
        binding.map.setTileSource(TileSourceFactory.MAPNIK);
        binding.map.getTileProvider().clearTileCache();
        binding.map.setBuiltInZoomControls(false);
        binding.map.setMultiTouchControls(true);
        binding.map.setUseDataConnection(true);
        binding.map.getOverlayManager().getTilesOverlay().setLoadingBackgroundColor(Color.TRANSPARENT);
        binding.map.getOverlayManager().getTilesOverlay().setLoadingLineColor(Color.TRANSPARENT);
        binding.map.getController().setZoom(14);
        mc = (MapController) binding.map.getController();
        mc.animateTo(center);
        mc.setZoom(18);
        binding.map.invalidate();
        intent = getIntent();
        popupMenu = new PopupMenu(MapActivity.this, binding.analysisImgBtn);
        popupMenu.getMenuInflater().inflate(R.menu.analysis_menu, popupMenu.getMenu());
        rvAdapter = new SelectedFeedersAdapter(this, ResponseDataUtils.NetworkList);
        checkedFeederIds.addAll(rvAdapter.getSelectedItems());
        getFeederNavigationData();
        updateNavLoadButton();


        if (prefManager.getUserType().contains("View")) {
            binding.analysisImgBtn.setVisibility(View.GONE);
            binding.deleteBtn.setVisibility(View.GONE);
            binding.newConnectionLoadFlowStatusImgBtn.setVisibility(View.GONE);
            binding.drawLine.setVisibility(View.GONE);
            binding.deleteBtn.setVisibility(View.GONE);
            binding.reloadNetwork.setVisibility(View.VISIBLE);
        } else if (prefManager.getUserType().contains("Edit")) {
            binding.analysisImgBtn.setVisibility(View.VISIBLE);
            binding.drawLine.setVisibility(View.VISIBLE);
            binding.deleteBtn.setVisibility(View.VISIBLE);
            binding.reloadNetwork.setVisibility(View.GONE);
            binding.newConnectionLoadFlowStatusImgBtn.setVisibility(View.VISIBLE);
            binding.newConnectionLoadFlowStatusImgBtn.setOnClickListener(v -> {
                ArrayList<String> networkIDs = intent.getStringArrayListExtra("NetworkId");
                if (networkIDs != null && !networkIDs.isEmpty()) {
                    binding.newConnectionLoadFlowStatusImgBtn.setColorFilter(Color.GREEN);
                    showFeasibilityDialog(MapActivity.this, networkIDs, new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            binding.newConnectionLoadFlowStatusImgBtn.setColorFilter(getColor(R.color.blue));
                        }
                    });
                }
            });
        } else if (prefManager.getUserType().contains("Analysis")) {
            binding.drawLine.setVisibility(View.GONE);
            binding.deleteBtn.setVisibility(View.GONE);
            binding.analysisImgBtn.setVisibility(View.VISIBLE);
            binding.deleteBtn.setVisibility(View.GONE);
            binding.reloadNetwork.setVisibility(View.VISIBLE);
            binding.newConnectionLoadFlowStatusImgBtn.setVisibility(View.GONE);
        } else if (prefManager.getType().contains("Admin")) {
            binding.drawLine.setVisibility(View.GONE);
            binding.deleteBtn.setVisibility(View.GONE);
            binding.analysisImgBtn.setVisibility(View.VISIBLE);
            binding.deleteBtn.setVisibility(View.GONE);
            binding.reloadNetwork.setVisibility(View.VISIBLE);
            binding.newConnectionLoadFlowStatusImgBtn.setVisibility(View.GONE);
        }

        binding.mapLayer.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, v);
            popupMenu.getMenuInflater().inflate(R.menu.pop_menu, popupMenu.getMenu());
            popupMenu.setForceShowIcon(true);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.noMap_menu:
                            menuItem.setChecked(!menuItem.isChecked());
                            binding.map.setTileSource(TileSourceFactory.OPEN_SEAMAP);
                            binding.map.invalidate();
                            return true;

                        case R.id.openStreetMap_menu:
                            menuItem.setChecked(!menuItem.isChecked());
                            binding.map.setTileSource(MapLayer.Map());
                            binding.map.invalidate();
                            return true;

                        case R.id.googleMap_menu:
                            menuItem.setChecked(!menuItem.isChecked());
                            binding.map.setTileSource(MapLayer.Map());
                            binding.map.invalidate();
                            return true;

                        case R.id.hybridMap_menu:
                            menuItem.setChecked(!menuItem.isChecked());
                            binding.map.setTileSource(MapLayer.Map());
                            binding.map.invalidate();
                            return true;

                        default:
                            return false;
                    }
                }
            });
            popupMenu.show();
        });

        binding.colorCodeView.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, v);
            popupMenu.getMenuInflater().inflate(R.menu.color_code_menu, popupMenu.getMenu());
            popupMenu.setForceShowIcon(true);
            if (Config.isLoadFlow) {
                MenuItem menuItem1 = popupMenu.getMenu().getItem(0);
                menuItem1.setVisible(true);
                menuItem1.setTitle("OverVoltage" + " (" + overVolatgeCount + ")");
                Drawable icon1 = menuItem1.getIcon();
                if (overVoltageColors != null) {
                    if (icon1 != null) {
                        icon1.mutate();
                        icon1.setColorFilter(Color.parseColor(overVoltageColors), PorterDuff.Mode.SRC_IN);
                    }
                }

                MenuItem menuItem2 = popupMenu.getMenu().getItem(1);
                menuItem2.setVisible(true);
                menuItem2.setTitle("UnderVoltage" + " (" + underVolatgeCount + ")");
                Drawable icon2 = menuItem2.getIcon();
                if (underVoltageColors != null) {
                    if (icon2 != null) {
                        icon2.mutate();
                        icon2.setColorFilter(Color.parseColor(underVoltageColors), PorterDuff.Mode.SRC_IN);
                    }
                }

                MenuItem menuItem3 = popupMenu.getMenu().getItem(2);
                menuItem3.setVisible(true);
                menuItem3.setTitle("Overload" + " (" + overLoadCount + ")");
                Drawable icon3 = menuItem3.getIcon();
                if (overloadColors != null) {
                    if (icon3 != null) {
                        icon3.mutate();
                        icon3.setColorFilter(Color.parseColor(overloadColors), PorterDuff.Mode.SRC_IN);
                    }
                }

                MenuItem menuItem4 = popupMenu.getMenu().getItem(3);
                menuItem4.setVisible(false);
                MenuItem menuItem5 = popupMenu.getMenu().getItem(4);
                menuItem5.setVisible(false);
                MenuItem menuItem6 = popupMenu.getMenu().getItem(5);
                menuItem6.setVisible(false);
                MenuItem menuItem7 = popupMenu.getMenu().getItem(6);
                menuItem7.setVisible(false);
                MenuItem menuItem8 = popupMenu.getMenu().getItem(7);
                menuItem8.setVisible(false);
                MenuItem menuItem9 = popupMenu.getMenu().getItem(8);
                menuItem9.setVisible(false);
                MenuItem menuItem10 = popupMenu.getMenu().getItem(9);
                menuItem10.setVisible(false);
                MenuItem menuItem11 = popupMenu.getMenu().getItem(10);
                menuItem11.setVisible(false);
                MenuItem menuItem12 = popupMenu.getMenu().getItem(11);
                menuItem12.setVisible(false);
                MenuItem menuItem13 = popupMenu.getMenu().getItem(12);
                menuItem13.setVisible(false);
                MenuItem menuItem14 = popupMenu.getMenu().getItem(13);
                menuItem14.setVisible(false);
                MenuItem menuItem15 = popupMenu.getMenu().getItem(14);
                menuItem15.setVisible(false);
                MenuItem menuItem16 = popupMenu.getMenu().getItem(15);
                menuItem16.setVisible(false);
            } else if (Config.isShortCircuit) {
                MenuItem menuItem1 = popupMenu.getMenu().getItem(0);
                menuItem1.setVisible(true);
                menuItem1.setTitle("OverVoltage" + " (" + overVolatgeCount + ")");
                Drawable icon1 = menuItem1.getIcon();
                if (overVoltageColors != null) {
                    if (icon1 != null) {
                        icon1.mutate();
                        icon1.setColorFilter(Color.parseColor(overVoltageColors), PorterDuff.Mode.SRC_IN);
                    }
                }

                MenuItem menuItem2 = popupMenu.getMenu().getItem(1);
                menuItem2.setVisible(true);
                menuItem2.setTitle("UnderVoltage" + " (" + underVolatgeCount + ")");
                Drawable icon2 = menuItem2.getIcon();
                if (underVoltageColors != null) {
                    if (icon2 != null) {
                        icon2.mutate();
                        icon2.setColorFilter(Color.parseColor(underVoltageColors), PorterDuff.Mode.SRC_IN);
                    }
                }

                MenuItem menuItem3 = popupMenu.getMenu().getItem(2);
                menuItem3.setVisible(true);
                menuItem3.setTitle("Overload" + " (" + overLoadCount + ")");
                Drawable icon3 = menuItem3.getIcon();
                if (overloadColors != null) {
                    if (icon3 != null) {
                        icon3.mutate();
                        icon3.setColorFilter(Color.parseColor(overloadColors), PorterDuff.Mode.SRC_IN);
                    }
                }

                MenuItem menuItem4 = popupMenu.getMenu().getItem(3);
                menuItem4.setVisible(true);
                menuItem4.setTitle("Rating" + " (" + ratingCount + ")");
                Drawable icon4 = menuItem4.getIcon();
                if (ratingColors != null) {
                    if (icon4 != null) {
                        icon4.mutate();
                        icon4.setColorFilter(Color.parseColor(ratingColors), PorterDuff.Mode.SRC_IN);
                    }
                }

                MenuItem menuItem5 = popupMenu.getMenu().getItem(4);
                menuItem5.setVisible(false);
                MenuItem menuItem6 = popupMenu.getMenu().getItem(5);
                menuItem6.setVisible(false);
                MenuItem menuItem7 = popupMenu.getMenu().getItem(6);
                menuItem7.setVisible(false);
                MenuItem menuItem8 = popupMenu.getMenu().getItem(7);
                menuItem8.setVisible(false);
                MenuItem menuItem9 = popupMenu.getMenu().getItem(8);
                menuItem9.setVisible(false);
                MenuItem menuItem10 = popupMenu.getMenu().getItem(9);
                menuItem10.setVisible(false);
                MenuItem menuItem11 = popupMenu.getMenu().getItem(10);
                menuItem11.setVisible(false);
                MenuItem menuItem12 = popupMenu.getMenu().getItem(11);
                menuItem12.setVisible(false);
                MenuItem menuItem13 = popupMenu.getMenu().getItem(12);
                menuItem13.setVisible(false);
                MenuItem menuItem14 = popupMenu.getMenu().getItem(13);
                menuItem14.setVisible(false);
                MenuItem menuItem15 = popupMenu.getMenu().getItem(14);
                menuItem15.setVisible(false);
                MenuItem menuItem16 = popupMenu.getMenu().getItem(15);
                menuItem16.setVisible(false);
            } else if (Config.isSystemVoltageColor) {
                MenuItem menuItem1 = popupMenu.getMenu().getItem(0);
                menuItem1.setVisible(true);
                menuItem1.setTitle("11 KV and above");
                Drawable icon1 = menuItem1.getIcon();
                if (icon1 != null) {
                    icon1.mutate();
                    icon1.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem2 = popupMenu.getMenu().getItem(1);
                menuItem2.setVisible(true);
                menuItem2.setTitle("Less than 11 KV");
                Drawable icon2 = menuItem2.getIcon();
                if (icon2 != null) {
                    icon2.mutate();
                    icon2.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem3 = popupMenu.getMenu().getItem(2);
                menuItem3.setVisible(false);
                menuItem3.setTitle("132 Kv");
                Drawable icon3 = menuItem3.getIcon();
                if (icon3 != null) {
                    icon3.mutate();
                    icon3.setColorFilter(Color.parseColor("#ff0000"), PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem4 = popupMenu.getMenu().getItem(3);
                menuItem4.setVisible(false);
                menuItem4.setTitle("100 Kv");
                Drawable icon4 = menuItem4.getIcon();
                if (icon4 != null) {
                    icon4.mutate();
                    icon4.setColorFilter(Color.parseColor("#e506bc"), PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem5 = popupMenu.getMenu().getItem(4);
                menuItem5.setVisible(false);
                menuItem5.setTitle("66 Kv");
                Drawable icon5 = menuItem5.getIcon();
                if (icon5 != null) {
                    icon5.mutate();
                    icon5.setColorFilter(Color.parseColor("#ac6969"), PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem6 = popupMenu.getMenu().getItem(5);
                menuItem6.setVisible(false);
                menuItem6.setTitle("33 Kv");
                Drawable icon6 = menuItem6.getIcon();
                if (icon6 != null) {
                    icon6.mutate();
                    icon6.setColorFilter(Color.parseColor("#ffff00"), PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem7 = popupMenu.getMenu().getItem(6);
                menuItem7.setVisible(false);
                menuItem7.setTitle("22 Kv");
                Drawable icon7 = menuItem7.getIcon();
                if (icon7 != null) {
                    icon7.mutate();
                    icon7.setColorFilter(Color.parseColor("#6a09e0"), PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem8 = popupMenu.getMenu().getItem(7);
                menuItem8.setVisible(false);
                menuItem8.setTitle("11 Kv");
                Drawable icon8 = menuItem8.getIcon();
                if (icon8 != null) {
                    icon8.mutate();
                    icon8.setColorFilter(Color.parseColor("#0000ff"), PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem9 = popupMenu.getMenu().getItem(8);
                menuItem9.setVisible(false);
                menuItem9.setTitle("6.50 Kv");
                Drawable icon9 = menuItem9.getIcon();
                if (icon9 != null) {
                    icon9.mutate();
                    icon9.setColorFilter(Color.parseColor("#f1e2d5"), PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem10 = popupMenu.getMenu().getItem(9);
                menuItem10.setVisible(false);
                menuItem10.setTitle("0.433 Kv");
                Drawable icon10 = menuItem10.getIcon();
                if (icon10 != null) {
                    icon10.mutate();
                    icon10.setColorFilter(Color.parseColor("#83ca02"), PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem11 = popupMenu.getMenu().getItem(10);
                menuItem11.setVisible(false);
                menuItem11.setTitle("0.415 Kv");
                Drawable icon11 = menuItem11.getIcon();
                if (icon11 != null) {
                    icon11.mutate();
                    icon11.setColorFilter(Color.parseColor("#ffa500"), PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem12 = popupMenu.getMenu().getItem(11);
                menuItem12.setVisible(false);
                menuItem12.setTitle("0.240 Kv");
                Drawable icon12 = menuItem12.getIcon();
                if (icon12 != null) {
                    icon12.mutate();
                    icon12.setColorFilter(Color.parseColor("#0a93ab"), PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem13 = popupMenu.getMenu().getItem(12);
                menuItem13.setVisible(false);
                menuItem13.setTitle("0.24 Kv");
                Drawable icon13 = menuItem13.getIcon();
                if (icon13 != null) {
                    icon13.mutate();
                    icon13.setColorFilter(Color.parseColor("#c433ff"), PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem14 = popupMenu.getMenu().getItem(13);
                menuItem14.setVisible(false);
                menuItem14.setTitle("0.23 Kv");
                Drawable icon14 = menuItem14.getIcon();
                if (icon14 != null) {
                    icon14.mutate();
                    icon14.setColorFilter(Color.parseColor("#700328"), PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem15 = popupMenu.getMenu().getItem(14);
                menuItem15.setVisible(false);
                menuItem15.setTitle("0.11 Kv");
                Drawable icon15 = menuItem15.getIcon();
                if (icon15 != null) {
                    icon15.mutate();
                    icon15.setColorFilter(Color.parseColor("#008000"), PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem16 = popupMenu.getMenu().getItem(15);
                menuItem16.setVisible(false);
                menuItem16.setTitle("DEFAULT");
                Drawable icon16 = menuItem16.getIcon();
                if (icon16 != null) {
                    icon16.mutate();
                    icon16.setColorFilter(Color.parseColor("#2C3335"), PorterDuff.Mode.SRC_IN);
                }

            } else if (Config.isPhaseColor) {
                MenuItem menuItem1 = popupMenu.getMenu().getItem(0);
                menuItem1.setVisible(true);
                menuItem1.setTitle("ABC");
                Drawable icon1 = menuItem1.getIcon();
                if (icon1 != null) {
                    icon1.mutate();
                    icon1.setColorFilter(Color.parseColor("#ffaa10"), PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem2 = popupMenu.getMenu().getItem(1);
                menuItem2.setVisible(true);
                menuItem2.setTitle("A");
                Drawable icon2 = menuItem2.getIcon();
                if (icon2 != null) {
                    icon2.mutate();
                    icon2.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem3 = popupMenu.getMenu().getItem(2);
                menuItem3.setVisible(true);
                menuItem3.setTitle("B");
                Drawable icon3 = menuItem3.getIcon();
                if (icon3 != null) {
                    icon3.mutate();
                    icon3.setColorFilter(Color.parseColor("#ff0000"), PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem4 = popupMenu.getMenu().getItem(3);
                menuItem4.setVisible(true);
                menuItem4.setTitle("C");
                Drawable icon4 = menuItem4.getIcon();
                if (icon4 != null) {
                    icon4.mutate();
                    icon4.setColorFilter(Color.parseColor("#0000ff"), PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem5 = popupMenu.getMenu().getItem(4);
                menuItem5.setVisible(true);
                menuItem5.setTitle("AB");
                Drawable icon5 = menuItem5.getIcon();
                if (icon5 != null) {
                    icon5.mutate();
                    icon5.setColorFilter(Color.parseColor("#008000"), PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem6 = popupMenu.getMenu().getItem(5);
                menuItem6.setVisible(true);
                menuItem6.setTitle("BC");
                Drawable icon6 = menuItem6.getIcon();
                if (icon6 != null) {
                    icon6.mutate();
                    icon6.setColorFilter(Color.parseColor("#a52a2a"), PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem7 = popupMenu.getMenu().getItem(6);
                menuItem7.setVisible(true);
                menuItem7.setTitle("AC");
                Drawable icon7 = menuItem7.getIcon();
                if (icon7 != null) {
                    icon7.mutate();
                    icon7.setColorFilter(Color.parseColor("#c50ceb"), PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem8 = popupMenu.getMenu().getItem(7);
                menuItem8.setVisible(false);
                MenuItem menuItem9 = popupMenu.getMenu().getItem(8);
                menuItem9.setVisible(false);
                MenuItem menuItem10 = popupMenu.getMenu().getItem(9);
                menuItem10.setVisible(false);
                MenuItem menuItem11 = popupMenu.getMenu().getItem(10);
                menuItem11.setVisible(false);
                MenuItem menuItem12 = popupMenu.getMenu().getItem(11);
                menuItem12.setVisible(false);
                MenuItem menuItem13 = popupMenu.getMenu().getItem(12);
                menuItem13.setVisible(false);
                MenuItem menuItem14 = popupMenu.getMenu().getItem(13);
                menuItem14.setVisible(false);
                MenuItem menuItem15 = popupMenu.getMenu().getItem(14);
                menuItem15.setVisible(false);
                MenuItem menuItem16 = popupMenu.getMenu().getItem(15);
                menuItem16.setVisible(false);
            } else if (Config.isLayerColor) {
                MenuItem menuItem1 = popupMenu.getMenu().getItem(0);
                menuItem1.setVisible(true);
                menuItem1.setTitle("Cable");
                Drawable icon1 = menuItem1.getIcon();
                if (icon1 != null) {
                    icon1.mutate();
                    icon1.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem2 = popupMenu.getMenu().getItem(1);
                menuItem2.setVisible(true);
                menuItem2.setTitle("OverHead");
                Drawable icon2 = menuItem2.getIcon();
                if (icon2 != null) {
                    icon2.mutate();
                    icon2.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem3 = popupMenu.getMenu().getItem(2);
                menuItem3.setVisible(true);
                menuItem3.setTitle("UnBalanced");
                Drawable icon3 = menuItem3.getIcon();
                if (icon3 != null) {
                    icon3.mutate();
                    icon3.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
                }

                MenuItem menuItem4 = popupMenu.getMenu().getItem(3);
                menuItem4.setVisible(false);
                MenuItem menuItem5 = popupMenu.getMenu().getItem(4);
                menuItem5.setVisible(false);
                MenuItem menuItem6 = popupMenu.getMenu().getItem(5);
                menuItem6.setVisible(false);
                MenuItem menuItem7 = popupMenu.getMenu().getItem(6);
                menuItem7.setVisible(false);
                MenuItem menuItem8 = popupMenu.getMenu().getItem(7);
                menuItem8.setVisible(false);
                MenuItem menuItem9 = popupMenu.getMenu().getItem(8);
                menuItem9.setVisible(false);
                MenuItem menuItem10 = popupMenu.getMenu().getItem(9);
                menuItem10.setVisible(false);
                MenuItem menuItem11 = popupMenu.getMenu().getItem(10);
                menuItem11.setVisible(false);
                MenuItem menuItem12 = popupMenu.getMenu().getItem(11);
                menuItem12.setVisible(false);
                MenuItem menuItem13 = popupMenu.getMenu().getItem(12);
                menuItem13.setVisible(false);
                MenuItem menuItem14 = popupMenu.getMenu().getItem(13);
                menuItem14.setVisible(false);
                MenuItem menuItem15 = popupMenu.getMenu().getItem(14);
                menuItem15.setVisible(false);
                MenuItem menuItem16 = popupMenu.getMenu().getItem(15);
                menuItem16.setVisible(false);
            }
            popupMenu.show();
        });

//        binding.reloadNetwork.setOnClickListener(v -> applyCheckedFeeders());
        binding.navLoadBtn.setOnClickListener(v -> applyCheckedFeeders());

        binding.map.addMapListener(new MapListener() {
            @Override
            public boolean onScroll(ScrollEvent event) {
                handleCenterUTM();
                return false;
            }

            @Override
            public boolean onZoom(ZoomEvent event) {
                handleCenterUTM();
                return false;
            }
        });

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        binding.search.setSearchableInfo(searchManager.getSearchableInfo(MapActivity.this.getComponentName()));
        binding.search.setIconifiedByDefault(false);
        binding.search.setOnQueryTextListener(this);
        binding.search.setOnCloseListener(this);
        @SuppressLint("DiscouragedApi")
        int id = binding.search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) binding.search.findViewById(id);
        textView.setTextColor(Color.BLACK);
        textView.setHintTextColor(Color.BLACK);
        binding.navigationmenu.post(() -> {
            int indicatorStart = (int) (8 * getResources().getDisplayMetrics().density);
            int indicatorEnd = (int) (30 * getResources().getDisplayMetrics().density);
            binding.navigationmenu.setIndicatorBoundsRelative(indicatorStart, indicatorEnd);
        });
        /*if (!TextUtils.isEmpty(prefManager.getName())) {
            String[] parts = prefManager.getName().trim().toLowerCase(Locale.ROOT).split("\\s+");
            StringBuilder formattedName = new StringBuilder();
            for (String part : parts) {
                if (part.isEmpty()) {
                    continue;
                }
                if (formattedName.length() > 0) {
                    formattedName.append(" ");
                }
                formattedName.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
            }
            binding.navUserName.setText(formattedName.toString());
        } else {
            binding.navUserName.setText(getString(R.string.utilitynet_app));
        }*/
        try {
            binding.navVersion.setText("Version " + getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            binding.navVersion.setText("Version");
        }

        binding.drawerBtn.setColorFilter(getColor(R.color.blue));
        binding.searchIcon.setColorFilter(getColor(R.color.blue));
        binding.zoomOut.setColorFilter(getColor(R.color.blue));
        binding.zoomIn.setColorFilter(getColor(R.color.blue));
        binding.downTracing.setColorFilter(getColor(R.color.blue));
        binding.upTracing.setColorFilter(getColor(R.color.blue));
        binding.locationMarker.setColorFilter(getColor(R.color.blue));
        binding.analysisImgBtn.setColorFilter(getColor(R.color.blue));
        binding.mapLayer.setColorFilter(getColor(R.color.blue));
        binding.reportBtn.setColorFilter(getColor(R.color.blue));
        binding.reportBtn.setVisibility(View.GONE);
        binding.newConnectionLoadFlowStatusImgBtn.setColorFilter(getColor(R.color.blue));
        binding.deleteBtn.setColorFilter(getColor(R.color.blue));
        binding.colorCodeView.setColorFilter(getColor(R.color.blue));
        binding.deviceColor.setColorFilter(getColor(R.color.blue));
        binding.drawLine.setColorFilter(getColor(R.color.blue));
        binding.toolsToggleBtn.setColorFilter(getColor(R.color.white));
        binding.reloadNetwork.setVisibility(View.GONE);
        binding.navLogo.setOnClickListener(v -> startActivity(new Intent(MapActivity.this, Profile.class)));

        binding.drawerBtn.setOnClickListener(v -> {
            if (!binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.rLayout.animate()
                        .translationX(100f)
                        .scaleX(0.92f)
                        .scaleY(0.92f)
                        .setDuration(350)
                        .setInterpolator(new OvershootInterpolator())
                        .start();

                binding.drawerLayout.post(() -> {
                    binding.drawerLayout.openDrawer(GravityCompat.START);
                    View drawerView = binding.drawerLayout.findViewById(R.id.navigationmenu);
                    if (drawerView != null) {
                        drawerView.setAlpha(0f);
                        drawerView.setTranslationX(-50f);
                        drawerView.animate()
                                .alpha(1f)
                                .translationX(0f)
                                .setDuration(300)
                                .setInterpolator(new DecelerateInterpolator())
                                .start();
                    }
                });
            } else {
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                binding.rLayout.animate()
                        .translationX(0f)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(300)
                        .setInterpolator(new DecelerateInterpolator())
                        .start();
            }
        });

        binding.drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {

            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                float scale = 1 - (0.08f * (float) Math.sin(slideOffset * Math.PI / 2));
                float translationX = 100f * slideOffset;
                binding.rLayout.setTranslationX(translationX);
                binding.rLayout.setScaleX(scale);
                binding.rLayout.setScaleY(scale);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                binding.rLayout.animate()
                        .translationX(0f)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(250)
                        .setInterpolator(new DecelerateInterpolator())
                        .start();

                View menuView = binding.navigationmenu;
                menuView.animate()
                        .alpha(0f)
                        .translationY(-30f)
                        .setDuration(200)
                        .withEndAction(() -> menuView.setVisibility(View.INVISIBLE))
                        .start();
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                binding.rLayout.animate()
                        .translationX(100f)
                        .scaleX(0.92f)
                        .scaleY(0.92f)
                        .setDuration(250)
                        .setInterpolator(new DecelerateInterpolator())
                        .start();

                View menuView = binding.navigationmenu;
                menuView.setVisibility(View.VISIBLE);
                menuView.setAlpha(0f);
                menuView.setTranslationY(-50f);
                menuView.animate()
                        .alpha(1f)
                        .translationY(0f)
                        .setDuration(400)
                        .setInterpolator(new OvershootInterpolator())
                        .start();
            }
        });

        if (Objects.equals(intent.getStringExtra("Type"), "NSC")) {
            if (intent.getStringArrayListExtra("NetworkId") != null && !Objects.requireNonNull(intent.getStringArrayListExtra("NetworkId")).isEmpty()) {
                if (!intent.getStringArrayListExtra("NetworkId").get(0).trim().isEmpty()) {
                    if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(MapActivity.this)) {
                        getNetworkNCData(intent.getStringArrayListExtra("NetworkId").get(0).trim());
                        getConsumerData();
                    } else {
                        final Dialog dialog = new Dialog(MapActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.no_internet_dialog);
                        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(MapActivity.this.getDrawable(R.drawable.pop_background));
                        LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.animation_view);
                        Button RetryBtn = dialog.findViewById(R.id.btnDialog);
                        lottieAnimationView.playAnimation();
                        RetryBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(MapActivity.this)) {
                                    intent.getStringArrayListExtra("NetworkId").get(0).trim();
                                    if (!intent.getStringArrayListExtra("NetworkId").get(0).trim().isEmpty()) {
                                        getNetworkNCData(intent.getStringArrayListExtra("NetworkId").get(0).trim());
                                    }
                                    dialog.dismiss();
                                }
                            }
                        });
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setCancelable(false);
                        dialog.show();
                    }
                }
            }
        } else {
            if (intent.getStringArrayListExtra("NetworkId") != null && !intent.getStringArrayListExtra("NetworkId").isEmpty()) {
                if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(MapActivity.this)) {
                    JsonArray jsonArray = new Gson().toJsonTree(intent.getStringArrayListExtra("NetworkId")).getAsJsonArray();
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<String>>() {
                    }.getType();
                    selectedFeeder = gson.fromJson(jsonArray, listType);
                    if (selectedFeeder != null) {
                        for (String feeder : selectedFeeder) {
                            if (feeder != null && !feeder.trim().isEmpty() && !checkedFeederIds.contains(feeder.trim())) {
                                checkedFeederIds.add(feeder.trim());
                            }
                            if (feeder != null && !feeder.trim().isEmpty() && !rvAdapter.getSelectedItems().contains(feeder.trim())) {
                                rvAdapter.addFeeder(feeder.trim());
                            }
                        }
                        updateNavLoadButton();
                    }
                    if (selectedFeeder != null && !selectedFeeder.isEmpty() && selectedFeeder.get(0) != null) {
                        getNetworkData(selectedFeeder.get(0).trim());
                    }
                } else {
                    final Dialog dialog = new Dialog(MapActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.no_internet_dialog);
                    Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(MapActivity.this.getDrawable(R.drawable.pop_background));
                    LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.animation_view);
                    Button RetryBtn = dialog.findViewById(R.id.btnDialog);
                    lottieAnimationView.playAnimation();
                    RetryBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(MapActivity.this)) {
                                if (selectedFeeder != null && !selectedFeeder.isEmpty() && selectedFeeder.get(0) != null) {
                                    getNetworkData(intent.getStringArrayListExtra("NetworkId").get(0).trim());
                                }
                                dialog.dismiss();
                            }
                        }
                    });
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    dialog.show();
                }
            }
        }

        binding.toolsToggleBtn.setOnClickListener(v -> {
            if (isExpanded[0]) {
                binding.toolsContainer.animate()
                        .alpha(0f)
                        .scaleY(0.8f)
                        .translationY(-binding.toolsContainer.getHeight() / 3f)
                        .setDuration(300)
                        .setInterpolator(new AccelerateDecelerateInterpolator())
                        .withEndAction(() -> binding.toolsContainer.setVisibility(View.GONE))
                        .start();

                binding.toolsToggleBtn.animate()
                        .rotation(0f)
                        .setDuration(250)
                        .setInterpolator(new DecelerateInterpolator())
                        .start();

                isExpanded[0] = false;

            } else {
                binding.toolsContainer.setVisibility(View.VISIBLE);
                binding.toolsContainer.setAlpha(0f);
                binding.toolsContainer.setScaleY(0.8f);
                binding.toolsContainer.setTranslationY(-binding.toolsContainer.getHeight() / 3f);

                binding.toolsContainer.animate()
                        .alpha(1f)
                        .scaleY(1f)
                        .translationY(0f)
                        .setDuration(300)
                        .setInterpolator(new OvershootInterpolator()) // adds bounce feel
                        .start();

                binding.toolsToggleBtn.animate()
                        .rotation(180f)
                        .setDuration(250)
                        .setInterpolator(new OvershootInterpolator())
                        .start();

                isExpanded[0] = true;
            }
        });

        binding.zoomIn.setOnClickListener(view -> {
            binding.map.getController().zoomIn();
            binding.map.invalidate();
        });

        binding.zoomOut.setOnClickListener(view -> {
            binding.map.getController().zoomOut();
            binding.map.invalidate();
        });

        binding.upTracing.setOnClickListener(view -> {
            if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(MapActivity.this)) {
                getUpTracingData();
            } else {
                final Dialog dialog = new Dialog(MapActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.no_internet_dialog);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(MapActivity.this.getDrawable(R.drawable.pop_background));
                LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.animation_view);
                Button RetryBtn = dialog.findViewById(R.id.btnDialog);
                lottieAnimationView.playAnimation();
                RetryBtn.setOnClickListener(view1 -> {
                    if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(MapActivity.this)) {
                        getUpTracingData();
                        dialog.dismiss();
                    }
                });
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.show();
            }
        });
        binding.upTracing.setOnClickListener(view -> {
            if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(MapActivity.this)) {
                getUpTracingData();
            } else {
                final Dialog dialog = new Dialog(MapActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.no_internet_dialog);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(MapActivity.this.getDrawable(R.drawable.pop_background));
                LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.animation_view);
                Button RetryBtn = dialog.findViewById(R.id.btnDialog);
                lottieAnimationView.playAnimation();
                RetryBtn.setOnClickListener(view2 -> {
                    if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(MapActivity.this)) {
                        getUpTracingData();
                        dialog.dismiss();
                    }
                });
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.show();
            }
        });

        binding.downTracing.setOnClickListener(view -> {
            if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(MapActivity.this)) {
                getDownTracingData();
            } else {
                final Dialog dialog = new Dialog(MapActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.no_internet_dialog);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(MapActivity.this.getDrawable(R.drawable.pop_background));
                LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.animation_view);
                Button RetryBtn = dialog.findViewById(R.id.btnDialog);
                lottieAnimationView.playAnimation();
                RetryBtn.setOnClickListener(view3 -> {
                    if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(MapActivity.this)) {
                        getDownTracingData();
                        dialog.dismiss();
                    }
                });
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.show();
            }
        });

        binding.locationMarker.setOnClickListener(view -> {
            if (CableKml != null) {
                BoundingBox boundingBox = CableKml.mKmlRoot.getBoundingBox();
                binding.map.zoomToBoundingBox(boundingBox, true);
                binding.map.getBoundingBox().getCenter();
                binding.map.getController().setZoom(7.0);
                binding.map.getController().setCenter(boundingBox.getCenter());
                binding.map.zoomToBoundingBox(boundingBox.increaseByScale(1.3f), true);
                binding.map.invalidate();
            } else if (OverHeadKml != null) {
                BoundingBox boundingBox = OverHeadKml.mKmlRoot.getBoundingBox();
                binding.map.zoomToBoundingBox(boundingBox, true);
                binding.map.getBoundingBox().getCenter();
                binding.map.getController().setZoom(7.0);
                binding.map.getController().setCenter(boundingBox.getCenter());
                binding.map.zoomToBoundingBox(boundingBox.increaseByScale(1.3f), true);
                binding.map.invalidate();
            } else {
                mc.animateTo(sourcePoint, 25.0, 0L);
                binding.map.getController().setCenter(sourcePoint);
                binding.map.invalidate();
            }
        });

        binding.map.setMapListener(new MapListener() {
            @Override
            public boolean onScroll(ScrollEvent event) {
                return true;
            }

            @Override
            public boolean onZoom(ZoomEvent event) {

                if (binding.map.getZoomLevel() > 24) {
                    AddDevices();
                }

                if (binding.map.getZoomLevel() > 20) {
                    AddNodes();
                }

                if (binding.map.getZoomLevel() < 24) {
                    RemoveDevices();
                }

                if (binding.map.getZoomLevel() < 20) {
                    RemoveNodes();
                }
                binding.map.isShown();
                return true;
            }
        });


        binding.searchIcon.setOnClickListener(v -> {
            int fullWidth = ((View) binding.searchView.getParent()).getWidth() - binding.searchIcon.getWidth();

            if (!isExpanded[0]) {
                binding.searchView.setVisibility(View.VISIBLE);
                ValueAnimator animator = ValueAnimator.ofInt(0, fullWidth);
                animator.setDuration(600);
                animator.setInterpolator(new OvershootInterpolator(1.2f));
                animator.addUpdateListener(animation -> {
                    int val = (Integer) animation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = binding.searchView.getLayoutParams();
                    layoutParams.width = val;
                    binding.searchView.setLayoutParams(layoutParams);
                });

                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        binding.searchView.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.showSoftInput(binding.searchView, InputMethodManager.SHOW_IMPLICIT);
                        }
                    }
                });

                animator.start();
                isExpanded[0] = true;

            } else {
                ValueAnimator animator = ValueAnimator.ofInt(binding.searchView.getWidth(), 0);
                animator.setDuration(600);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.addUpdateListener(animation -> {
                    int val = (Integer) animation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = binding.searchView.getLayoutParams();
                    layoutParams.width = val;
                    binding.searchView.setLayoutParams(layoutParams);
                });
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        binding.searchView.setVisibility(View.INVISIBLE);
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(binding.searchView.getWindowToken(), 0);
                        }
                    }
                });
                animator.start();

                isExpanded[0] = false;
            }
        });
        binding.searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(binding.searchView.getWindowToken(), 0);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.analysisImgBtn.setOnClickListener(v -> {

            if (prefManager.getUserType().contains("Edit")) {
                popupMenu.getMenu().findItem(R.id.analysis_loadflow).setVisible(false);
                popupMenu.getMenu().findItem(R.id.analysis_shortcircuit).setVisible(false);
                popupMenu.getMenu().findItem(R.id.analysis_loadAllocation).setVisible(false);
                popupMenu.getMenu().findItem(R.id.feasibility_after).setVisible(true);
                popupMenu.getMenu().findItem(R.id.feasibility_before).setVisible(true);
                popupMenu.getMenu().findItem(R.id.feasibility_after).setEnabled(isrEnabled);
            } else {
                popupMenu.getMenu().findItem(R.id.analysis_loadflow).setVisible(true);
                popupMenu.getMenu().findItem(R.id.analysis_shortcircuit).setVisible(true);
                popupMenu.getMenu().findItem(R.id.analysis_loadAllocation).setVisible(false);
                popupMenu.getMenu().findItem(R.id.feasibility_before).setVisible(false);
                popupMenu.getMenu().findItem(R.id.feasibility_after).setVisible(false);
                popupMenu.getMenu().findItem(R.id.feasibility_after).setEnabled(false);
            }

            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.analysis_loadflow:
                        if (intent.getStringArrayListExtra("NetworkId") != null) {
                            loadFlow = new LoadFlow(MapActivity.this);
                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList("Network", intent.getStringArrayListExtra("NetworkId"));
                            loadFlow.setArguments(bundle);
                            loadFlow.show(getSupportFragmentManager(), loadFlow.getTag());
                            return true;
                        }

                    case R.id.analysis_shortcircuit:
                        if (intent.getStringArrayListExtra("NetworkId") != null) {
                            shortCircuit = new ShortCircuit(MapActivity.this);
                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList("Network", intent.getStringArrayListExtra("NetworkId"));
                            bundle.putString("Index", "0");
                            bundle.putString("NodeId", null);
                            shortCircuit.setArguments(bundle);
                            shortCircuit.show(getSupportFragmentManager(), shortCircuit.getTag());
                            return true;
                        }

                    case R.id.analysis_loadAllocation:
                        if (intent.getStringArrayListExtra("NetworkId") != null) {
                            LoadAllocation loadAllocation = new LoadAllocation(MapActivity.this);
                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList("Network", intent.getStringArrayListExtra("NetworkId"));
                            loadAllocation.setArguments(bundle);
                            loadAllocation.show(getSupportFragmentManager(), loadAllocation.getTag());
                            return true;
                        }
                        break;

                    case R.id.feasibility_before:
                        if (prefManager.getUserType().contains("Edit")) {
                            LoadFlowEdt(intent.getStringArrayListExtra("NetworkId"), prefManager.getUserName(), "before", intent.getStringExtra("NearstConsumerNo"));
                        }
                        return true;

                    case R.id.feasibility_after:
                        if (prefManager.getUserType().contains("Edit")) {
                            LoadFlowEdt(intent.getStringArrayListExtra("NetworkId"), prefManager.getUserName(), "After", intent.getStringExtra("NearstConsumerNo"));
                        }
                        return true;

                    default:
                        return false;
                }
                return false;
            });

            popupMenu.show();
        });

        binding.reportBtn.setOnClickListener(view -> {
            if (Config.isLoadFlow) {
                if (intent.getStringArrayListExtra("NetworkId") != null) {
                    Intent intent1 = new Intent(this, Reports.class);
                    intent1.putStringArrayListExtra("NetworkId", intent.getStringArrayListExtra("NetworkId"));
                    intent1.putExtra("Type", "loadflow");
                    startActivityForResult(intent1, REQUEST_CODE_CHILD_ACTIVITY);
                }
            } else if (Config.isShortCircuit) {
                if (intent.getStringArrayListExtra("NetworkId") != null) {
                    Intent intent1 = new Intent(this, Reports.class);
                    intent1.putStringArrayListExtra("NetworkId", intent.getStringArrayListExtra("NetworkId"));
                    intent1.putExtra("Type", "shortcircuit");
                    startActivityForResult(intent1, REQUEST_CODE_CHILD_ACTIVITY);
                }
            } else if (Config.isLoadAllocation) {
                if (intent.getStringArrayListExtra("NetworkId") != null) {
                    Intent intent1 = new Intent(this, Reports.class);
                    intent1.putStringArrayListExtra("NetworkId", intent.getStringArrayListExtra("NetworkId"));
                    intent1.putExtra("Type", "loadallocation");
                    startActivityForResult(intent1, REQUEST_CODE_CHILD_ACTIVITY);
                }

            } else {
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Analysis Failed!", Snackbar.LENGTH_LONG);
                snack.show();
            }
        });

        binding.deleteBtn.setOnClickListener(v -> {
            new AlertDialog.Builder(MapActivity.this)
                    .setTitle("Delete Section")
                    .setMessage("Are you sure you want to delete this section?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        String deviceType = sectionType != null ? sectionType : "1";
                        String deviceDeviceType = "5";
                        Log.d("DeleteDebug", "Selected Device Number: " + DelDeviceNumber);
                        if (breakerSectionId.containsKey(DelDeviceNumber)) {
                            deviceDeviceType = "8"; // Circuit Breaker
                        } else if (transformerSectionId.containsKey(DelDeviceNumber)) {
                            deviceDeviceType = "5"; // Transformer
                        } else if (fuseSectionId.containsKey(DelDeviceNumber)) {
                            deviceDeviceType = "14"; // Fuse
                        } else if (switchSectionId.containsKey(DelDeviceNumber)) {
                            deviceDeviceType = "13"; // Switch
                        } else if (capacitorSectionId.containsKey(DelDeviceNumber)) {
                            deviceDeviceType = "17"; // Shunt Capacitor
                        } else if (spotloadSectionId.containsKey(DelDeviceNumber)) {
                            deviceDeviceType = "20"; // SpotLoad
                        }

                        String nodeID = nodeId != null ? nodeId : selectedNodeID != null ? selectedNodeID : "";

                        if (DelSectionId != null && !DelSectionId.isEmpty()) {
                            deleteSection(DelSectionId, intent.getStringArrayListExtra("NetworkId").get(0).trim(), nodeID, deviceType);
                        } else if (DelDeviceNumber != null && !DelDeviceNumber.isEmpty()) {
                            deleteDevice(DelDeviceNumber, intent.getStringArrayListExtra("NetworkId").get(0).trim(), nodeID, deviceDeviceType);
                        } else {
                            Snackbar.make(binding.getRoot(), "Please Select Device!", Snackbar.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        });

        binding.deviceColor.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, v);
            popupMenu.getMenuInflater().inflate(R.menu.device_color_menu, popupMenu.getMenu());
            popupMenu.setForceShowIcon(true);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.default_color:
                            menuItem.setChecked(!menuItem.isChecked());
                            progressBarLayout.setProcessText("Set Default Colors...");
                            progressBarLayout.setVisibility(View.VISIBLE);
                            Config.isLoadFlow = false;
                            Config.isShortCircuit = false;
                            Config.isLoadAllocation = false;
                            Config.isPhaseColor = false;
                            Config.isSystemVoltageColor = false;
                            Config.isLayerColor = false;
                            binding.colorCodeView.setVisibility(View.GONE);
                            new DefaultColor().execute();
                            return true;

                        case R.id.layer_color:
                            menuItem.setChecked(!menuItem.isChecked());
                            progressBarLayout.setProcessText("Set Layer Colors...");
                            progressBarLayout.setVisibility(View.VISIBLE);
                            Config.isLoadFlow = false;
                            Config.isShortCircuit = false;
                            Config.isLoadAllocation = false;
                            Config.isPhaseColor = false;
                            Config.isSystemVoltageColor = false;
                            Config.isLayerColor = true;
                            binding.colorCodeView.setVisibility(View.VISIBLE);
                            new LayerColor().execute();
                            return true;

                        case R.id.phase_color:
                            menuItem.setChecked(!menuItem.isChecked());
                            progressBarLayout.setProcessText("Set Phase Colors...");
                            progressBarLayout.setVisibility(View.VISIBLE);
                            Config.isLoadFlow = false;
                            Config.isShortCircuit = false;
                            Config.isLoadAllocation = false;
                            Config.isPhaseColor = true;
                            Config.isSystemVoltageColor = false;
                            Config.isLayerColor = false;
                            binding.colorCodeView.setVisibility(View.VISIBLE);
                            new PhaseColor().execute();
                            return true;

                        case R.id.system_color:
                            menuItem.setChecked(!menuItem.isChecked());
                            progressBarLayout.setProcessText("Set System Voltage By Colors...");
                            progressBarLayout.setVisibility(View.VISIBLE);
                            Config.isLoadFlow = false;
                            Config.isShortCircuit = false;
                            Config.isLoadAllocation = false;
                            Config.isPhaseColor = false;
                            Config.isSystemVoltageColor = true;
                            Config.isLayerColor = false;
                            binding.colorCodeView.setVisibility(View.VISIBLE);
                            new SystemVoltage().execute();
                            return true;

                        default:
                            return false;
                    }
                }
            });
            popupMenu.show();
        });

        binding.drawLine.setOnClickListener(v -> {
            if (intent.getStringArrayListExtra("NetworkId").get(0).trim() != null && selectedNodeID != null && newSectionGeoPointList != null && nodeIdX != null && nodeIdY != null && !newSectionGeoPointList.isEmpty() && newSectionGeoPointList.size() > 1) {
                String voltage = !TextUtils.isEmpty(selectedNodeVoltage) ? selectedNodeVoltage : defaultVoltage;
                sectionDialog = new SectionDeviceDialog(MapActivity.this, newSectionGeoPointList, intent.getStringArrayListExtra("NetworkId").get(0).trim(), selectedNodeID, intent.getStringExtra("ApplicationID"), nodeIdX, nodeIdY, voltage);
                sectionDialog.setCancelable(false);
                sectionDialog.show();
            }
        });

        MapEventsOverlay evOverlay = new MapEventsOverlay(this, this);
        binding.map.getOverlays().add(evOverlay);

        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(MapActivity.this)) {
            getConsumerData();
        } else {
            Snackbar.make(binding.getRoot(), "No Internet Connection", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(MapActivity.this)) {
                        getConsumerData();
                    }
                }
            }).show();
        }
    }

    private void getNetworkNCData(String feederId) {
        progressBarLayout.setProcessText("Reading Files...");
        progressBarLayout.setVisibility(View.VISIBLE);
        new Thread(() -> {
            try {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("NetworkName", feederId);
                jsonObject.addProperty("UserType", prefManager.getUserType());
                jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
                String url = "networkdata/";
                String AccessToken = prefManager.getAccessToken();
                ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
                Call<JsonObject> call = apiInterface.getNetworkData(url, "Bearer " + AccessToken, jsonObject);
                call.enqueue(new Callback<JsonObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                        if (response.code() == 200) {
                            ErrorPdfLogger.logApiSuccess(MapActivity.this, "POST", "_NC/networkdata/", "HTTP " + response.code() + " MAP: " + response.message()
                                    + "NetworkName : " + feederId
                                    + "UserType : " + prefManager.getUserType()
                                    + "CYMDBNET : " + prefManager.getDBName()
                                    + "url : " +"_NC/networkdata/"
                                    + "AccessToken : " +prefManager.getAccessToken());
                            try {
                                progressBarLayout.setVisibility(View.GONE);
                                JsonObject jsonObject1 = response.body();
                                while (!ResponseDataUtils.NetworkList.isEmpty()) {
                                    ResponseDataUtils.NetworkList.clear();
                                }
                                assert jsonObject1 != null;
                                JSONObject jsonObject2 = new JSONObject(jsonObject1.toString());
                                new AddGeoJsonData(binding.map, jsonObject2).execute();
                                binding.map.postDelayed(() -> {handleCenterUTM();}, 500);
                            } catch (JSONException e) {
                                ErrorPdfLogger.logCrash(MapActivity.this,e);
                                e.printStackTrace();
                            }
                        } else {
                            ErrorPdfLogger.logApiError(MapActivity.this, "POST", "_NC/networkdata/", "HTTP " + response.code() + " NC_DATA: " + response.message()
                                    + "NetworkName : " + feederId
                                    + "UserType : " + prefManager.getUserType()
                                    + "CYMDBNET : " + prefManager.getDBName()
                                    + "url : " + prefManager.getProjectName()+"_NC/networkdata/"
                                    + "AccessToken : " +prefManager.getAccessToken());
                            progressBarLayout.setVisibility(View.GONE);
                            @SuppressLint("InflateParams")
                            View layout = LayoutInflater.from(MapActivity.this).inflate(R.layout.toast_layout, null);
                            TextView Ok = layout.findViewById(R.id.okBtn);
                            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                            TextView header = layout.findViewById(R.id.headerTv);
                            @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                            TextView description = layout.findViewById(R.id.descripTv);
                            header.setText(response.message() + " - " + response.code());
                            description.setText(getString(R.string.error_msg));
                            Ok.setOnClickListener(v -> {
                                getNetworkNCData(feederId);
                            });
                            Toast toast = new Toast(MapActivity.this);
                            toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                        ErrorPdfLogger.logApiFailure(MapActivity.this, "POST", "_NC/networkdata/", t);
                        progressBarLayout.setVisibility(View.GONE);
                        @SuppressLint("InflateParams")
                        View layout = LayoutInflater.from(MapActivity.this).inflate(R.layout.toast_layout, null);
                        TextView Ok = layout.findViewById(R.id.okBtn);
                        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                        TextView header = layout.findViewById(R.id.headerTv);
                        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                        TextView description = layout.findViewById(R.id.descripTv);
                        header.setText(getString(R.string.error));
                        description.setText(getString(R.string.error_msg));
                        Ok.setOnClickListener(v -> {
                            getNetworkNCData(feederId);
                        });
                        Toast toast = new Toast(MapActivity.this);
                        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();
                    }
                });
            } catch (Exception e) {
                ErrorPdfLogger.logCrash(MapActivity.this,e);
                Log.d("Exception", e.getLocalizedMessage());;
            }
        }).start();
    }

    private void getConsumerData() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Type", "ConsumerClass");
        jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<ConsumerModel> call = apiInterface.consumerData("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<ConsumerModel>() {
            @Override
            public void onResponse(Call<ConsumerModel> call, Response<ConsumerModel> response) {
                if (response.code() == 200) {
                    ConsumerModel consumerModel = response.body();
                    if (consumerModel.getConsumerClassID() != null && !consumerModel.getConsumerClassID().isEmpty()) {
                        prefManager.saveConsumerClasses(consumerModel.getConsumerClassID());
                    }
                } else {
                    Snackbar.make(binding.getRoot(), response.code() + getString(R.string.error_msg), Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getConsumerData();
                        }
                    }).show();
                }
            }

            @Override
            public void onFailure(Call<ConsumerModel> call, Throwable t) {
                Snackbar.make(binding.getRoot(), getString(R.string.error_msg), Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getConsumerData();
                    }
                }).show();
            }
        });
    }
    private void handleCenterUTM() {
        GeoPoint center = (GeoPoint) binding.map.getMapCenter();
        double lat = center.getLatitude();
        double lon = center.getLongitude();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressBarLayout != null) {
            progressBarLayout.stopAnimation();
        }
    }

    @Override
    public void addDevice(JsonObject jsonObject) {
        if (intent.getStringExtra("Type").equals("NSC")) {
            if (jsonObject.has("isCancel")) {
                Args.setNSCSectionValidate(false);
                Args.setNSCIsSpotloadValidate(false);
                Args.setNSCIsTransformerValidate(false);
                clearVertexNode();
            } else {
                Args.setNSCSectionValidate(false);
                Args.setNSCIsSpotloadValidate(false);
                Args.setNSCIsTransformerValidate(false);
                addNewNSCSectionDevices(sectionType, jsonObject);
            }
        }
    }

    @Override
    public void onJsonObjectReceived(JsonObject jsonObject, JsonObject dashBoardJsonObject, List<String> list) {
        try {
            loadFlow.dismiss();
            JSONObject jsonObject2 = new JSONObject(jsonObject.toString());
            if (jsonObject2.has("isLoadFlow")) {
                progressBarLayout.setProcessText("Load-Flow Analysis Run...");
                progressBarLayout.setVisibility(View.VISIBLE);
                Config.isLoadFlow = jsonObject2.getBoolean("isLoadFlow");
                Config.isShortCircuit = jsonObject2.getBoolean("isLoadFlow");
                binding.reportBtn.setVisibility(View.GONE);
                CancelAnalysis();
            } else {
                LoadFlowAnalysis(jsonObject);
                loadFlowList = list;
            }
        } catch (Exception e) {
            Log.d("Exception", Objects.requireNonNull(e.getLocalizedMessage()));
        }
    }

    @Override
    public void onShortCircuitArgReceived(JsonObject jsonObject, List<String> list) {
        if (jsonObject.has("ShortCircuit")) {
            shortCircuit.dismiss();
            Config.isShortCircuit = false;
            Config.isLoadFlow = false;
            binding.reportBtn.setVisibility(View.GONE);
            CancelAnalysis();
        } else {
            shortCircuit.dismiss();
            ShortCircuitAnalysis(jsonObject);
            Log.d("shortCircuitParameter", jsonObject.toString());
            shortCircuitList = list;
        }
    }

    @Override
    public void onLoadAllocationArgReceived(JsonObject jsonObject) {
        if (jsonObject.has("LoadAllocation")) {
            binding.reportBtn.setVisibility(View.GONE);
        } else {
            LoadAllocationAnalysis(jsonObject);
        }
    }

    @Override
    public boolean onClose() {
        adapter.filterData("");
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.filterData(query);
        if (adapter != null) {
            expandAll();
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (adapter != null) {
            adapter.filterData(query);
        }
        expandAll();
        return false;
    }

    private void expandAll() {
        int count = adapter.getGroupCount();
        if (binding.navigationmenu.getExpandableListAdapter() != null) {
            for (int i = 0; i < count; i++) {
                binding.navigationmenu.expandGroup(i);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHILD_ACTIVITY && resultCode == RESULT_OK) {
            if (data != null) {
                if (data.getStringExtra("DeviceNo") != null && !data.getStringExtra("DeviceNo").isEmpty() && data.getStringExtra("DeviceType") != null && !data.getStringExtra("DeviceType").isEmpty() && prefManager.getUserType() != null) {
                    getDevices(data.getStringExtra("DeviceNo"), data.getStringExtra("DeviceType"), prefManager.getUserType());
                }
            }
        }
    }

    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed() {
        networkIdList.clear();
        if (Config.isLoadFlow) {
            Config.isLoadFlow = false;
        }
        if (Config.isShortCircuit) {
            Config.isShortCircuit = false;
        }
        super.onBackPressed();
    }

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
        return false;
    }

    @Override
    public boolean longPressHelper(GeoPoint p) {
        if (Objects.requireNonNull(intent.getStringExtra("Type")).contains("NSC")) {
            if (!newSectionGeoPointList.isEmpty() && !coordinateList.isEmpty() && selectedNodeID != null) {
                newSectionGeoPointList.add(p);
                coordinateList.add(p);
                addNewSections(coordinateList);
            } else {
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Please select any node!", Snackbar.LENGTH_LONG);
                snack.show();
            }
            return true;
        }
        return false;
    }

    private void addNewSectionDevices(JsonObject jsonObject) {
        progressBarLayout.setProcessText("Add New Section");
        progressBarLayout.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.getNetworkData("NewConnectionData/", "Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.code() == 200) {
                    ErrorPdfLogger.logNewConnection(MapActivity.this, "POST", "NewConnectionData/", "HTTP " + response.code() + " MAP: " + response.message()
                            + "UserType : " + prefManager.getUserType()
                            + "CYMDBNET : " + prefManager.getDBName()
                            + "url : " + prefManager.getProjectName()+"NewConnectionData/"
                            + "AccessToken : " +prefManager.getAccessToken());
                    try {
                        progressBarLayout.setVisibility(View.GONE);
                        JsonObject responseData = response.body();
                        assert responseData != null;
                        JSONObject responseData1 = new JSONObject(responseData.toString());

                        if (!responseData1.getJSONObject("output").getJSONObject("cables_data2").getJSONArray("features").toString().contains("[]")) {
                            CableKml = new KmlDocument();
                            CableKml.parseGeoJSON(String.valueOf(responseData1.getJSONObject("output").getJSONObject("cables_data2")));
                            KmlFeature.Styler styler = new MyKmlStyler(Color.BLUE, binding.map);
                            CableFolderOverLay = (FolderOverlay) CableKml.mKmlRoot.buildOverlay(binding.map, null, null, CableKml);

                            //Add Cable
                            KmlDocument kmlDocument = new KmlDocument();
                            kmlDocument.parseGeoJSON(String.valueOf(responseData1.getJSONObject("output").getJSONObject("cables_data2")));
                            FolderOverlay newSection = (FolderOverlay) kmlDocument.mKmlRoot.buildOverlay(binding.map, null, styler, kmlDocument);
                            binding.map.getOverlays().add(newSection);
                            binding.map.invalidate();
                        }

                        if (!responseData1.getJSONObject("output").getJSONObject("oh_data2").getJSONArray("features").toString().contains("[]")) {
                            OverHeadKml = new KmlDocument();
                            OverHeadKml.parseGeoJSON(String.valueOf(responseData1.getJSONObject("output").getJSONObject("oh_data2")));
                            KmlFeature.Styler styler = new OverHeadKmlStyler(Color.BLUE, binding.map);
                            OverheadFolderOverLay = (FolderOverlay) OverHeadKml.mKmlRoot.buildOverlay(binding.map, null, styler, OverHeadKml);

                            //Add Overhead
                            KmlDocument kmlDocument = new KmlDocument();
                            kmlDocument.parseGeoJSON(String.valueOf(responseData1.getJSONObject("output").getJSONObject("oh_data2")));
                            FolderOverlay newSection = (FolderOverlay) kmlDocument.mKmlRoot.buildOverlay(binding.map, null, styler, kmlDocument);
                            binding.map.getOverlays().add(newSection);
                            binding.map.invalidate();
                        }

                        if (!responseData1.getJSONObject("output").getJSONObject("ohunbal_data2").getJSONArray("features").toString().contains("[]")) {
                            KmlDocument UnBalanceKml = new KmlDocument();
                            UnBalanceKml.parseGeoJSON(String.valueOf(responseData1.getJSONObject("output").getJSONObject("ohunbal_data2")));
                            KmlFeature.Styler styler = new UnbalanceKmlStyler(Color.BLACK, binding.map);
                            UnBalanceFolderOverLay = (FolderOverlay) UnBalanceKml.mKmlRoot.buildOverlay(binding.map, null, styler, UnBalanceKml);

                            FolderOverlay newSection = (FolderOverlay) UnBalanceKml.mKmlRoot.buildOverlay(binding.map, null, styler, UnBalanceKml);
                            binding.map.getOverlays().add(newSection);
                            binding.map.invalidate();
                        }

                        if (!responseData1.getJSONObject("output").getJSONObject("dt_data2").getJSONArray("features").toString().contains("[]")) {
                            KmlDocument DistributionTransferKml = new KmlDocument();
                            DistributionTransferKml.parseGeoJSON(responseData1.getJSONObject("output").getJSONObject("dt_data2").toString());
                            KmlFeature.Styler styler = new DistributionTransferKmlStyler(Color.BLUE, binding.map);
                            DistributionTransferOverLay = (FolderOverlay) DistributionTransferKml.mKmlRoot.buildOverlay(binding.map, null, styler, DistributionTransferKml);

                        }

                        if (!responseData1.getJSONObject("output").getJSONObject("spotload2").getJSONArray("features").toString().contains("[]")) {
                            KmlDocument SpotLoadKml = new KmlDocument();
                            SpotLoadKml.parseGeoJSON(responseData1.getJSONObject("output").getJSONObject("spotload2").toString());
                            KmlFeature.Styler styler = new SpotLoadKmlStyler(Color.BLUE, binding.map);
                            SpotLoadOverLay = (FolderOverlay) SpotLoadKml.mKmlRoot.buildOverlay(binding.map, null, styler, SpotLoadKml);
                        }

                        if (!responseData1.getJSONObject("output").getJSONObject("node_data2").getJSONArray("features").toString().contains("[]")) {
                            KmlDocument nodeKml = new KmlDocument();
                            nodeKml.parseGeoJSON(responseData1.getJSONObject("output").getJSONObject("node_data2").toString());
                            KmlFeature.Styler styler = new NodeKmlStyler(Color.BLUE, binding.map, polylineMap);
                            nodeOverLay = (FolderOverlay) nodeKml.mKmlRoot.buildOverlay(binding.map, null, styler, nodeKml);

                            FolderOverlay newNode = (FolderOverlay) nodeKml.mKmlRoot.buildOverlay(binding.map, null, styler, nodeKml);
                            binding.map.getOverlays().add(newNode);
                        }

                        clearVertexNode();
                        binding.map.invalidate();

                    } catch (Exception e) {
                        ErrorPdfLogger.logNewConnection(MapActivity.this,e);
                        clearVertexNode();
                        Log.d("Exception", e.getLocalizedMessage());;
                    }
                } else {
                    ErrorPdfLogger.logNewConnection(MapActivity.this, "POST", "NewConnectionData/", "HTTP " + response.code() + " Map: " + response.message()
                            + "UserType : " + prefManager.getUserType()
                            + "CYMDBNET : " + prefManager.getDBName()
                            + "url : " + prefManager.getProjectName()+"NewConnectionData/"
                            + "AccessToken : " +prefManager.getAccessToken());
                    progressBarLayout.setVisibility(View.GONE);
                    clearVertexNode();
                    @SuppressLint("InflateParams") View layout = LayoutInflater.from(MapActivity.this).inflate(R.layout.toast_layout, null);
                    TextView Ok = layout.findViewById(R.id.okBtn);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView header = layout.findViewById(R.id.headerTv);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView description = layout.findViewById(R.id.descripTv);
                    header.setText(response.message() + " - " + response.code());
                    description.setText(getString(R.string.error_msg));
                    Ok.setOnClickListener(v -> {
                        addNewSectionDevices(jsonObject);
                    });
                    Toast toast = new Toast(MapActivity.this);
                    toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                ErrorPdfLogger.logNewConnection(MapActivity.this, "POST", "NewConnectionData/", Log.getStackTraceString(t));
                progressBarLayout.setVisibility(View.GONE);
                clearVertexNode();
                @SuppressLint("InflateParams") View layout = LayoutInflater.from(MapActivity.this).inflate(R.layout.toast_layout, null);
                TextView Ok = layout.findViewById(R.id.okBtn);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView header = layout.findViewById(R.id.headerTv);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView description = layout.findViewById(R.id.descripTv);
                header.setText(getString(R.string.error));
                description.setText(getString(R.string.error_msg));
                Ok.setOnClickListener(v -> {
                    addNewSectionDevices(jsonObject);
                });
                Toast toast = new Toast(MapActivity.this);
                toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        });

    }

    private void addNewNSCSectionDevices(String sectionType, JsonObject jsonObject) {
        progressBarLayout.setProcessText("Add New Section");
        progressBarLayout.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call = apiInterface.getNetworkData("NewConnectionData/", "Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @SuppressLint({"SetTextI18n", "SuspiciousIndentation"})
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                if (response.code() == 200) {
                    try {
                        ErrorPdfLogger.logNewConnection(MapActivity.this, "POST", "/NewConnectionData/", "SectionType : " + sectionType);
                        progressBarLayout.setVisibility(View.GONE);
                        JsonObject responseData = response.body();
                        assert responseData != null;
                        JSONObject responseData1 = new JSONObject(responseData.toString());

                        if (!responseData1.getJSONObject("output").getJSONObject("cables_data2").getJSONArray("features").toString().contains("[]")) {
                            JSONObject cableObject = responseData1.getJSONObject("output").getJSONObject("cables_data2");
                            for (int i = 0; i < cableObject.getJSONArray("features").length(); i++) {
                                JSONObject geometry = cableObject.getJSONArray("features").getJSONObject(i).getJSONObject("geometry");
                                if ("LineString".equalsIgnoreCase(geometry.getString("type"))) {
                                    for (int j = 0; j < geometry.getJSONArray("coordinates").length(); j++) {
                                        JSONArray point = geometry.getJSONArray("coordinates").getJSONArray(j);
                                        GeoPoint latLon = UTMConversion.convert(point.getDouble(0), point.getDouble(1));
                                        geometry.getJSONArray("coordinates").put(j, new JSONArray().put(latLon.getLongitude()).put(latLon.getLatitude()));
                                    }
                                } else if ("MultiLineString".equalsIgnoreCase(geometry.getString("type"))) {
                                    for (int j = 0; j < geometry.getJSONArray("coordinates").length(); j++) {
                                        JSONArray line = geometry.getJSONArray("coordinates").getJSONArray(j);
                                        for (int k = 0; k < line.length(); k++) {
                                            JSONArray point = line.getJSONArray(k);
                                            GeoPoint latLon = UTMConversion.convert(point.getDouble(0), point.getDouble(1));
                                            line.put(k, new JSONArray().put(latLon.getLongitude()).put(latLon.getLatitude()));
                                        }
                                    }
                                }
                            }
                            KmlFeature.Styler styler = new MyKmlStyler(Color.BLUE, binding.map);
                            if (CableKml != null) {
                                CableKml.parseGeoJSON(String.valueOf(responseData1.getJSONObject("output").getJSONObject("cables_data2")));
                                FolderOverlay folderOverlay = (FolderOverlay) CableKml.mKmlRoot.buildOverlay(binding.map, null, styler, CableKml);
                                CableFolderOverLay.add(folderOverlay);
                                binding.map.invalidate();
                            } else {
                                CableKml = new KmlDocument();
                                CableKml.parseGeoJSON(String.valueOf(responseData1.getJSONObject("output").getJSONObject("cables_data2")));
                                CableFolderOverLay = (FolderOverlay) CableKml.mKmlRoot.buildOverlay(binding.map, null, styler, CableKml);
                                binding.map.getOverlays().add(CableFolderOverLay);
                                binding.map.invalidate();
                            }

                            boolean isCable = false;

                            for (int i = 0; i < continentList.size(); i++) {
                                if (continentList.get(i).getName().contains("Cable")) {
                                    DType type = new DType(responseData1.getJSONObject("output").getJSONObject("cables_data2").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("SectionId"), Integer.parseInt(responseData1.getJSONObject("output").getJSONObject("cables_data2").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("DeviceType")));
                                    continentList.get(i).getDeviceList().add(type);
                                    continentList.get(i).setName("Cable" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                    isCable = true;
                                }
                            }

                            if (!isCable) {
                                ArrayList<DType> list = new ArrayList<>();
                                DType type = new DType(responseData1.getJSONObject("output").getJSONObject("cables_data2").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("SectionId"), Integer.parseInt(responseData1.getJSONObject("output").getJSONObject("cables_data2").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("DeviceType")));
                                list.add(type);
                                Continent continent = new Continent("Cable" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }

                            adapter = new ExpandableDeviceAdapter(MapActivity.this, continentList);
                            binding.navigationmenu.setAdapter(adapter);

                        }

                        if (!responseData1.getJSONObject("output").getJSONObject("oh_data2").getJSONArray("features").toString().contains("[]")) {
                            JSONObject ohObject = responseData1.getJSONObject("output").getJSONObject("oh_data2");
                            for (int i = 0; i < ohObject.getJSONArray("features").length(); i++) {
                                JSONObject geometry = ohObject.getJSONArray("features").getJSONObject(i).getJSONObject("geometry");
                                if ("LineString".equalsIgnoreCase(geometry.getString("type"))) {
                                    for (int j = 0; j < geometry.getJSONArray("coordinates").length(); j++) {
                                        JSONArray point = geometry.getJSONArray("coordinates").getJSONArray(j);
                                        GeoPoint latLon = UTMConversion.convert(point.getDouble(0), point.getDouble(1));
                                        geometry.getJSONArray("coordinates").put(j, new JSONArray().put(latLon.getLongitude()).put(latLon.getLatitude()));
                                    }
                                } else if ("MultiLineString".equalsIgnoreCase(geometry.getString("type"))) {
                                    for (int j = 0; j < geometry.getJSONArray("coordinates").length(); j++) {
                                        JSONArray line = geometry.getJSONArray("coordinates").getJSONArray(j);
                                        for (int k = 0; k < line.length(); k++) {
                                            JSONArray point = line.getJSONArray(k);
                                            GeoPoint latLon = UTMConversion.convert(point.getDouble(0), point.getDouble(1));
                                            line.put(k, new JSONArray().put(latLon.getLongitude()).put(latLon.getLatitude()));
                                        }
                                    }
                                }
                            }
                            KmlFeature.Styler styler = new OverHeadKmlStyler(Color.BLUE, binding.map);
                            if (OverHeadKml != null) {
                                OverHeadKml.parseGeoJSON(String.valueOf(responseData1.getJSONObject("output").getJSONObject("oh_data2")));
                                FolderOverlay folderOverlay = (FolderOverlay) OverHeadKml.mKmlRoot.buildOverlay(binding.map, null, styler, OverHeadKml);
                                OverheadFolderOverLay.add(folderOverlay);
                                binding.map.invalidate();
                            } else {
                                OverHeadKml = new KmlDocument();
                                OverHeadKml.parseGeoJSON(String.valueOf(responseData1.getJSONObject("output").getJSONObject("oh_data2")));
                                OverheadFolderOverLay = (FolderOverlay) OverHeadKml.mKmlRoot.buildOverlay(binding.map, null, styler, OverHeadKml);
                                binding.map.getOverlays().add(OverheadFolderOverLay);
                                binding.map.invalidate();
                            }

                            boolean isOverhead = false;

                            for (int i = 0; i < continentList.size(); i++) {
                                if (continentList.get(i).getName().contains("Overhead Balance")) {
                                    DType type = new DType(responseData1.getJSONObject("output").getJSONObject("oh_data2").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("SectionId"), Integer.parseInt(responseData1.getJSONObject("output").getJSONObject("oh_data2").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("DeviceType")));
                                    continentList.get(i).getDeviceList().add(type);
                                    continentList.get(i).setName("Overhead Balance" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                    isOverhead = true;
                                }
                            }

                            if (!isOverhead) {
                                ArrayList<DType> list = new ArrayList<>();
                                DType type = new DType(responseData1.getJSONObject("output").getJSONObject("oh_data2").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("SectionId"), Integer.parseInt(responseData1.getJSONObject("output").getJSONObject("oh_data2").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("DeviceType")));
                                list.add(type);
                                Continent continent = new Continent("Overhead Balance" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }

                            adapter = new ExpandableDeviceAdapter(MapActivity.this, continentList);
                            binding.navigationmenu.setAdapter(adapter);

                        }

                        if (!responseData1.getJSONObject("output").getJSONObject("ohunbal_data2").getJSONArray("features").toString().contains("[]")) {
                            JSONObject unbalanceObject = responseData1.getJSONObject("output").getJSONObject("ohunbal_data2");
                            for (int i = 0; i < unbalanceObject.getJSONArray("features").length(); i++) {
                                JSONObject geometry = unbalanceObject.getJSONArray("features").getJSONObject(i).getJSONObject("geometry");
                                if ("LineString".equalsIgnoreCase(geometry.getString("type"))) {
                                    for (int j = 0; j < geometry.getJSONArray("coordinates").length(); j++) {
                                        JSONArray point = geometry.getJSONArray("coordinates").getJSONArray(j);
                                        GeoPoint latLon = UTMConversion.convert(point.getDouble(0), point.getDouble(1));
                                        geometry.getJSONArray("coordinates").put(j, new JSONArray().put(latLon.getLongitude()).put(latLon.getLatitude()));
                                    }
                                } else if ("MultiLineString".equalsIgnoreCase(geometry.getString("type"))) {
                                    for (int j = 0; j < geometry.getJSONArray("coordinates").length(); j++) {
                                        JSONArray line = geometry.getJSONArray("coordinates").getJSONArray(j);
                                        for (int k = 0; k < line.length(); k++) {
                                            JSONArray point = line.getJSONArray(k);
                                            GeoPoint latLon = UTMConversion.convert(point.getDouble(0), point.getDouble(1));
                                            line.put(k, new JSONArray().put(latLon.getLongitude()).put(latLon.getLatitude()));
                                        }
                                    }
                                }
                            }

                            if (UnBalencedKml != null) {
                                UnBalencedKml.parseGeoJSON(String.valueOf(responseData1.getJSONObject("output").getJSONObject("ohunbal_data2")));
                                KmlFeature.Styler styler = new UnbalanceKmlStyler(Color.BLACK, binding.map);
                                FolderOverlay folderOverlay = (FolderOverlay) UnBalencedKml.mKmlRoot.buildOverlay(binding.map, null, styler, UnBalencedKml);
                                UnBalanceFolderOverLay.add(folderOverlay);
                                binding.map.invalidate();
                            } else {
                                UnBalencedKml = new KmlDocument();
                                UnBalencedKml.parseGeoJSON(String.valueOf(responseData1.getJSONObject("output").getJSONObject("ohunbal_data2")));
                                KmlFeature.Styler styler = new UnbalanceKmlStyler(Color.BLACK, binding.map);
                                UnBalanceFolderOverLay = (FolderOverlay) UnBalencedKml.mKmlRoot.buildOverlay(binding.map, null, styler, UnBalencedKml);
                                binding.map.getOverlays().add(UnBalanceFolderOverLay);
                                binding.map.invalidate();
                            }

                            boolean isUnBalenced = false;

                            for (int i = 0; i < continentList.size(); i++) {
                                if (continentList.get(i).getName().contains("Unbalance")) {
                                    DType type = new DType(responseData1.getJSONObject("output").getJSONObject("ohunbal_data2").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("SectionId"), Integer.parseInt(responseData1.getJSONObject("output").getJSONObject("ohunbal_data2").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("DeviceType")));
                                    continentList.get(i).getDeviceList().add(type);
                                    continentList.get(i).setName("Unbalance" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                    isUnBalenced = true;
                                }
                            }

                            if (!isUnBalenced) {
                                ArrayList<DType> list = new ArrayList<>();
                                DType type = new DType(responseData1.getJSONObject("output").getJSONObject("ohunbal_data2").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("SectionId"), Integer.parseInt(responseData1.getJSONObject("output").getJSONObject("ohunbal_data2").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("DeviceType")));
                                list.add(type);
                                Continent continent = new Continent("Unbalance" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }

                            adapter = new ExpandableDeviceAdapter(MapActivity.this, continentList);
                            binding.navigationmenu.setAdapter(adapter);

                        }

                        if (!responseData1.getJSONObject("output").getJSONObject("dt_data2").getJSONArray("features").toString().contains("[]")) {
                            JSONObject dtObject = responseData1.getJSONObject("output").getJSONObject("dt_data2");
                            for (int i = 0; i < dtObject.getJSONArray("features").length(); i++) {
                                JSONObject geometry = dtObject.getJSONArray("features").getJSONObject(i).getJSONObject("geometry");
                                if ("Point".equalsIgnoreCase(geometry.getString("type"))) {
                                    JSONArray point = geometry.getJSONArray("coordinates");
                                    GeoPoint latLon = UTMConversion.convert(point.getDouble(0), point.getDouble(1));
                                    geometry.put("coordinates", new JSONArray().put(latLon.getLongitude()).put(latLon.getLatitude()));
                                }
                            }
                            if (TransformerKml != null) {
                                TransformerKml.parseGeoJSON(responseData1.getJSONObject("output").getJSONObject("dt_data2").toString());
                                KmlFeature.Styler styler = new DistributionTransferKmlStyler(Color.BLUE, binding.map);
                                FolderOverlay folderOverlay = (FolderOverlay) TransformerKml.mKmlRoot.buildOverlay(binding.map, null, styler, TransformerKml);
                                DistributionTransferOverLay.add(folderOverlay);
                            } else {
                                TransformerKml = new KmlDocument();
                                TransformerKml.parseGeoJSON(responseData1.getJSONObject("output").getJSONObject("dt_data2").toString());
                                KmlFeature.Styler styler = new DistributionTransferKmlStyler(Color.BLUE, binding.map);
                                DistributionTransferOverLay = (FolderOverlay) TransformerKml.mKmlRoot.buildOverlay(binding.map, null, styler, TransformerKml);
                            }

                            boolean isTransformer = false;

                            for (int i = 0; i < continentList.size(); i++) {
                                if (continentList.get(i).getName().contains("Two-Winding Transformer")) {
                                    DType type = new DType(responseData1.getJSONObject("output").getJSONObject("dt_data2").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(responseData1.getJSONObject("output").getJSONObject("dt_data2").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("DeviceType")));
                                    continentList.get(i).getDeviceList().add(type);
                                    continentList.get(i).setName("Two-Winding Transformer" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                    isTransformer = true;
                                }
                            }

                            if (!isTransformer) {
                                ArrayList<DType> list = new ArrayList<>();
                                DType type = new DType(responseData1.getJSONObject("output").getJSONObject("dt_data2").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(responseData1.getJSONObject("output").getJSONObject("dt_data2").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("DeviceType")));
                                list.add(type);
                                Continent continent = new Continent("Two-Winding Transformer" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }

                            adapter = new ExpandableDeviceAdapter(MapActivity.this, continentList);
                            binding.navigationmenu.setAdapter(adapter);
                        }

                        if (!responseData1.getJSONObject("output").getJSONObject("spotload2").getJSONArray("features").toString().contains("[]")) {
                            JSONObject spotloadObject = responseData1.getJSONObject("output").getJSONObject("spotload2");
                            for (int i = 0; i < spotloadObject.getJSONArray("features").length(); i++) {
                                JSONObject geometry = spotloadObject.getJSONArray("features").getJSONObject(i).getJSONObject("geometry");
                                if ("Point".equalsIgnoreCase(geometry.getString("type"))) {
                                    JSONArray point = geometry.getJSONArray("coordinates");
                                    GeoPoint latLon = UTMConversion.convert(point.getDouble(0), point.getDouble(1));
                                    geometry.put("coordinates", new JSONArray().put(latLon.getLongitude()).put(latLon.getLatitude()));
                                }
                            }
                            if (SpotloadKml != null) {
                                SpotloadKml.parseGeoJSON(responseData1.getJSONObject("output").getJSONObject("spotload2").toString());
                                KmlFeature.Styler styler = new SpotLoadKmlStyler(Color.BLUE, binding.map);
                                FolderOverlay folderOverlay = (FolderOverlay) SpotloadKml.mKmlRoot.buildOverlay(binding.map, null, styler, SpotloadKml);
                                SpotLoadOverLay.add(folderOverlay);
                            } else {
                                SpotloadKml = new KmlDocument();
                                SpotloadKml.parseGeoJSON(responseData1.getJSONObject("output").getJSONObject("spotload2").toString());
                                KmlFeature.Styler styler = new SpotLoadKmlStyler(Color.BLUE, binding.map);
                                SpotLoadOverLay = (FolderOverlay) SpotloadKml.mKmlRoot.buildOverlay(binding.map, null, styler, SpotloadKml);
                            }

                            boolean isSpotload = false;

                            for (int i = 0; i < continentList.size(); i++) {
                                if (continentList.get(i).getName().contains("SpotLoad")) {
                                    DType type = new DType(responseData1.getJSONObject("output").getJSONObject("spotload2").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(responseData1.getJSONObject("output").getJSONObject("spotload2").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("DeviceType")));
                                    continentList.get(i).getDeviceList().add(type);
                                    continentList.get(i).setName("SpotLoad" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                    isSpotload = true;
                                }
                            }

                            if (!isSpotload) {
                                ArrayList<DType> list = new ArrayList<>();
                                DType type = new DType(responseData1.getJSONObject("output").getJSONObject("spotload2").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(responseData1.getJSONObject("output").getJSONObject("spotload2").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("DeviceType")));
                                list.add(type);
                                Continent continent = new Continent("SpotLoad" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }

                            adapter = new ExpandableDeviceAdapter(MapActivity.this, continentList);
                            binding.navigationmenu.setAdapter(adapter);
                            isrEnabled = true;
                            MenuItem afterItem = popupMenu.getMenu().findItem(R.id.feasibility_after);
                            if (afterItem != null) {
                                afterItem.setEnabled(true).setVisible(true);
                            }
                        }

                        if (!responseData1.getJSONObject("output").getJSONObject("sectionnodev").getJSONArray("features").toString().contains("[]")) {
                            JSONObject sectionNodeObject = responseData1.getJSONObject("output").getJSONObject("sectionnodev");
                            for (int i = 0; i < sectionNodeObject.getJSONArray("features").length(); i++) {
                                JSONObject geometry = sectionNodeObject.getJSONArray("features").getJSONObject(i).getJSONObject("geometry");
                                if ("LineString".equalsIgnoreCase(geometry.getString("type"))) {
                                    for (int j = 0; j < geometry.getJSONArray("coordinates").length(); j++) {
                                        JSONArray point = geometry.getJSONArray("coordinates").getJSONArray(j);
                                        GeoPoint latLon = UTMConversion.convert(point.getDouble(0), point.getDouble(1));
                                        geometry.getJSONArray("coordinates").put(j, new JSONArray().put(latLon.getLongitude()).put(latLon.getLatitude()));
                                    }
                                } else if ("MultiLineString".equalsIgnoreCase(geometry.getString("type"))) {
                                    for (int j = 0; j < geometry.getJSONArray("coordinates").length(); j++) {
                                        JSONArray line = geometry.getJSONArray("coordinates").getJSONArray(j);
                                        for (int k = 0; k < line.length(); k++) {
                                            JSONArray point = line.getJSONArray(k);
                                            GeoPoint latLon = UTMConversion.convert(point.getDouble(0), point.getDouble(1));
                                            line.put(k, new JSONArray().put(latLon.getLongitude()).put(latLon.getLatitude()));
                                        }
                                    }
                                }
                            }

                            KmlFeature.Styler styler = new SectionNodeKmlStyler(Color.BLACK, binding.map);
                            if (sectionNodeKml != null) {
                                sectionNodeKml.parseGeoJSON(sectionNodeObject.toString());
                                FolderOverlay folderOverlay = (FolderOverlay) sectionNodeKml.mKmlRoot.buildOverlay(binding.map, null, styler, sectionNodeKml);
                                if (sectionFolderOverLay != null) {
                                    sectionFolderOverLay.add(folderOverlay);
                                } else {
                                    sectionFolderOverLay = new FolderOverlay();
                                    sectionFolderOverLay.add(folderOverlay);
                                    binding.map.getOverlays().add(sectionFolderOverLay);
                                }

                                if (SecNodeObject == null) {
                                    SecNodeObject = new JSONObject(sectionNodeObject.toString());
                                } else {
                                    JSONArray existingFeatures = SecNodeObject.optJSONArray("features");
                                    JSONArray newFeatures = sectionNodeObject.optJSONArray("features");
                                    if (existingFeatures != null && newFeatures != null) {
                                        for (int i = 0; i < newFeatures.length(); i++) {
                                            existingFeatures.put(newFeatures.get(i));
                                        }
                                    }
                                }
                            } else {
                                SecNodeObject = new JSONObject(sectionNodeObject.toString());
                                sectionNodeKml = new KmlDocument();
                                sectionNodeKml.parseGeoJSON(SecNodeObject.toString());
                                sectionFolderOverLay = (FolderOverlay) sectionNodeKml.mKmlRoot.buildOverlay(binding.map, null, styler, sectionNodeKml);
                                binding.map.getOverlays().add(sectionFolderOverLay);
                            }

                            JSONArray sectionFeatures = sectionNodeObject.optJSONArray("features");
                            if (sectionFeatures != null) {
                                for (int i = 0; i < sectionFeatures.length(); i++) {
                                    JSONObject properties = sectionFeatures.optJSONObject(i) != null
                                            ? sectionFeatures.optJSONObject(i).optJSONObject("properties")
                                            : null;
                                    String deviceNumber = properties != null ? properties.optString("DeviceNumber") : null;
                                    if (!TextUtils.isEmpty(deviceNumber) && !spLineSectionList.contains(deviceNumber)) {
                                        spLineSectionList.add(deviceNumber);
                                    }
                                }
                            }
                            binding.map.invalidate();
                        }

                        if (!responseData1.getJSONObject("output").getJSONObject("node_data2").getJSONArray("features").toString().contains("[]")) {
                            JSONObject nodeObject = responseData1.getJSONObject("output").getJSONObject("node_data2");
                            for (int i = 0; i < nodeObject.getJSONArray("features").length(); i++) {
                                JSONObject geometry = nodeObject.getJSONArray("features").getJSONObject(i).getJSONObject("geometry");
                                if ("Point".equalsIgnoreCase(geometry.getString("type"))) {
                                    JSONArray point = geometry.getJSONArray("coordinates");
                                    GeoPoint latLon = UTMConversion.convert(point.getDouble(0), point.getDouble(1));
                                    geometry.put("coordinates", new JSONArray().put(latLon.getLongitude()).put(latLon.getLatitude()));
                                }
                            }
                            if (NodeKml != null) {
                                NodeKml.parseGeoJSON(responseData1.getJSONObject("output").getJSONObject("node_data2").toString());
                                KmlFeature.Styler styler = new NodeKmlStyler(Color.BLUE, binding.map, polylineMap);
                                FolderOverlay folderOverlay = (FolderOverlay) NodeKml.mKmlRoot.buildOverlay(binding.map, null, styler, NodeKml);
                                nodeOverLay.add(folderOverlay);
                                binding.map.invalidate();
                            } else {
                                NodeKml = new KmlDocument();
                                NodeKml.parseGeoJSON(responseData1.getJSONObject("output").getJSONObject("node_data2").toString());
                                KmlFeature.Styler styler = new NodeKmlStyler(Color.BLUE, binding.map, polylineMap);
                                nodeOverLay = (FolderOverlay) NodeKml.mKmlRoot.buildOverlay(binding.map, null, styler, NodeKml);
                                binding.map.getOverlays().add(nodeOverLay);
                                binding.map.invalidate();
                            }
                        }

                        clearVertexNode();
                        binding.map.invalidate();

                    } catch (Exception e) {
                        ErrorPdfLogger.logNewConnection(MapActivity.this, e);
                        Log.d("Exception", e.getLocalizedMessage());
                        clearVertexNode();
                        isrEnabled = false;
                        popupMenu.getMenu().findItem(R.id.feasibility_after).setEnabled(false);
                        popupMenu.getMenu().findItem(R.id.feasibility_after).setVisible(false);

                    }
                } else {
                    ErrorPdfLogger.logNewConnection(
                            MapActivity.this,
                            "POST",
                            "/NewConnectionData/",
                            "HTTP " + response.code() + " : " + response.message()
                    );
                    isrEnabled = false;
                    popupMenu.getMenu().findItem(R.id.feasibility_after).setEnabled(false);
                    popupMenu.getMenu().findItem(R.id.feasibility_after).setVisible(false);

                    progressBarLayout.setVisibility(View.GONE);
                    clearVertexNode();
                    @SuppressLint("InflateParams")
                    View layout = LayoutInflater.from(MapActivity.this).inflate(R.layout.toast_layout, null);
                    TextView Ok = layout.findViewById(R.id.okBtn);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                    TextView header = layout.findViewById(R.id.headerTv);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                    TextView description = layout.findViewById(R.id.descripTv);
                    header.setText(response.message() + " - " + response.code());
                    description.setText(getString(R.string.error_msg));
                    Ok.setOnClickListener(v -> {
                        addNewSectionDevices(jsonObject);
                    });
                    Toast toast = new Toast(MapActivity.this);
                    toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }

            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                ErrorPdfLogger.logNewConnection(
                        MapActivity.this,
                        "POST",
                        "/NewConnectionData/",
                        Log.getStackTraceString(t)
                );
                isrEnabled = false;
                popupMenu.getMenu().findItem(R.id.feasibility_after).setEnabled(false);
                popupMenu.getMenu().findItem(R.id.feasibility_after).setVisible(false);

                progressBarLayout.setVisibility(View.GONE);
                clearVertexNode();
                @SuppressLint("InflateParams")
                View layout = LayoutInflater.from(MapActivity.this).inflate(R.layout.toast_layout, null);
                TextView Ok = layout.findViewById(R.id.okBtn);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView header = layout.findViewById(R.id.headerTv);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                TextView description = layout.findViewById(R.id.descripTv);
                header.setText(getString(R.string.error));
                description.setText(getString(R.string.error_msg));
                Ok.setOnClickListener(v -> {
                    addNewSectionDevices(jsonObject);
                });
                Toast toast = new Toast(MapActivity.this);
                toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        });
    }

    private void deleteSection(String delDeviceNumber, String networkID, String nodeID, String deviceType) {
        progressBarLayout.setProcessText("Delete Section...");
        progressBarLayout.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        JsonArray devicesArray = new JsonArray();
        JsonObject deviceObject = new JsonObject();
        deviceObject.addProperty("DeviceNumber", delDeviceNumber);
        deviceObject.addProperty("DeviceType", deviceType);
        deviceObject.addProperty("SectionId", delDeviceNumber);
        deviceObject.addProperty("NodeId", nodeID);
        deviceObject.addProperty("NetworkId", networkID);
        deviceObject.addProperty("CYMDBNET", prefManager.getDBName());
        devicesArray.add(deviceObject);
        jsonObject.add("devices", devicesArray);
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<DeleteSectionModel> call = apiInterface.deleteSection("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<DeleteSectionModel>() {
            @Override
            public void onResponse(@NonNull Call<DeleteSectionModel> call, @NonNull Response<DeleteSectionModel> response) {
                if (response.code() == 200) {
                    try {
                        progressBarLayout.setVisibility(View.GONE);
                        DeleteSectionModel deleteSectionModel = response.body();
                        assert deleteSectionModel != null;
                        if (deleteSectionModel.getMessage() != null && deleteSectionModel.getMessage().contains("Feature Deleted Successfully")) {
                            ErrorPdfLogger.logNewConnection(MapActivity.this, "POST", "/deletefeature/", "Feature Deleted Successfully"
                                    + "DeviceNumber : " + delDeviceNumber
                                    + "DeviceType : " + deviceType
                                    + "NetworkId : " + networkID
                                    + "NodeId : " + nodeID
                            );
                            if (intent.getStringArrayListExtra("NetworkId") != null) {
                                if (DelSection.get(delDeviceNumber) != null) {
                                    Overlay overlayToRemove = DelSection.get(delDeviceNumber);
                                    binding.map.getOverlays().remove(overlayToRemove);

                                    if (CaSectionId.containsKey(delDeviceNumber) && CableFolderOverLay != null) {
                                        removeOverlay(CableFolderOverLay, overlayToRemove);
                                        CaSectionId.remove(delDeviceNumber);
                                        CaSectionList.remove(delDeviceNumber);
                                        CaPolylineList.remove(overlayToRemove);
                                    } else if (OhSectionId.containsKey(delDeviceNumber) && OverheadFolderOverLay != null) {
                                        removeOverlay(OverheadFolderOverLay, overlayToRemove);
                                        OhSectionId.remove(delDeviceNumber);
                                        OhSectionList.remove(delDeviceNumber);
                                        ohPolylineList.remove(overlayToRemove);
                                    } else if (UnBalSectionId.containsKey(delDeviceNumber) && UnBalanceFolderOverLay != null) {
                                        removeOverlay(UnBalanceFolderOverLay, overlayToRemove);
                                        UnBalSectionId.remove(delDeviceNumber);
                                        unBalPolylineList.remove(overlayToRemove);
                                    }

                                    if (nodeID != null && !nodeID.isEmpty()) {
                                        boolean nodeRemoved = false;

                                        List<FolderOverlay> foldersToSearch = new ArrayList<>();
                                        if (sectionFolderOverLay != null)
                                            foldersToSearch.add(sectionFolderOverLay);
                                        if (nodeOverLay != null) foldersToSearch.add(nodeOverLay);

                                        for (FolderOverlay folder : foldersToSearch) {
                                            Stack<FolderOverlay> stack = new Stack<>();
                                            stack.push(folder);

                                            while (!stack.isEmpty()) {
                                                FolderOverlay currentFolder = stack.pop();
                                                List<Overlay> overlays = new ArrayList<>(currentFolder.getItems());

                                                for (Overlay overlay : overlays) {
                                                    if (overlay instanceof Marker) {
                                                        Marker nodeMarker = (Marker) overlay;
                                                        String markerId = nodeMarker.getId() != null ? nodeMarker.getId() : "";
                                                        Object relatedObject = nodeMarker.getRelatedObject();
                                                        String relatedObjectId = relatedObject != null ? relatedObject.toString() : "";

                                                        if (nodeID.equals(markerId) || nodeID.equals(relatedObjectId)) {
                                                            boolean isNodeShared = secNodeSectionId.entrySet().stream()
                                                                    .anyMatch(entry -> entry.getValue().equals(nodeID) && !entry.getKey().equals(delDeviceNumber));

                                                            if (!isNodeShared) {
                                                                currentFolder.getItems().remove(nodeMarker);
                                                                binding.map.getOverlays().remove(nodeMarker);
                                                                secNodeList.remove(nodeMarker);
                                                                nodeRemoved = true;
                                                                break;
                                                            } else {
                                                                Log.d("MapActivity", "NodeID " + nodeID + " is shared with other sections, not removing Marker");
                                                                nodeRemoved = false;
                                                                break;
                                                            }
                                                        }
                                                    } else if (overlay instanceof FolderOverlay) {
                                                        stack.push((FolderOverlay) overlay);
                                                    }
                                                }

                                                if (nodeRemoved) break;
                                            }

                                            if (nodeRemoved) break;
                                        }

                                        secNodeSectionId.entrySet().removeIf(entry -> entry.getKey().equals(delDeviceNumber) && entry.getValue().equals(nodeID));
                                    }

                                    loadFlowOverLoadSectionId.remove(delDeviceNumber);
                                    loadFlowOverVoltageSectionId.remove(delDeviceNumber);
                                    loadFlowUnderVoltageSectionId.remove(delDeviceNumber);
                                    shortCircuitRatingSectionId.remove(delDeviceNumber);
                                    shortCircuitOverLoadSectionId.remove(delDeviceNumber);
                                    shortCircuitOverVoltageSectionId.remove(delDeviceNumber);
                                    shortCircuitUnderVoltageSectionId.remove(delDeviceNumber);

                                    if (previousSelectedSection != null && previousSelectedSection.equals(DelSection.get(delDeviceNumber))) {
                                        previousSelectedSection = null;
                                        sectionID = null;
                                        sectionType = null;
                                    }

                                    if (loadFlowPreviousSelectedSection != null && loadFlowPreviousSelectedSection.equals(DelSection.get(delDeviceNumber))) {
                                        loadFlowPreviousSelectedSection = null;
                                        loadFlowSectionId = null;
                                    }

                                    if (shortCircuitPreviousSelectedSection != null && shortCircuitPreviousSelectedSection.equals(DelSection.get(delDeviceNumber))) {
                                        shortCircuitPreviousSelectedSection = null;
                                        shortCircuitSectionId = null;
                                    }

                                    updateContinentList(delDeviceNumber, deviceType);
                                    DelSection.remove(delDeviceNumber);
                                    DelSectionId = null;
                                    binding.map.invalidate();
                                    binding.map.postInvalidate();
                                }
                                Snackbar.make(binding.getRoot(), "Section delete successfully", Snackbar.LENGTH_SHORT).show();
                            } else {
                                Snackbar.make(binding.getRoot(), deleteSectionModel.getMessage(), Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        ErrorPdfLogger.logNewConnection(MapActivity.this, e);
                        Log.e("MapActivity", "Exception in deleteSection: " + e.getLocalizedMessage());
                    }
                } else {
                    progressBarLayout.setVisibility(View.GONE);
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), response.message() + " - " + response.code(), Snackbar.LENGTH_SHORT);
                    snack.show();
                    ErrorPdfLogger.logNewConnection(
                            MapActivity.this,
                            "POST",
                            "/deletefeature/",
                            "HTTP " + response.code() + " : " + response.message()
                                    + "SectionId : " + delDeviceNumber
                                    + "NetworkId : " + networkID
                                    + "NodeId : " + nodeID
                    );
                }
            }

            @Override
            public void onFailure(@NonNull Call<DeleteSectionModel> call, @NonNull Throwable t) {
                progressBarLayout.setVisibility(View.GONE);
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_msg), Snackbar.LENGTH_SHORT);
                snack.show();
                ErrorPdfLogger.logNewConnection(
                        MapActivity.this,
                        "POST",
                        "/deletefeature/",
                        Log.getStackTraceString(t)
                );
                Log.e("MapActivity", "Failed to delete section: " + t.getLocalizedMessage());
            }
        });
    }

    private boolean removeOverlay(FolderOverlay folder, Overlay overlayToRemove) {
        for (Overlay overlay : folder.getItems()) {
            if (overlay.equals(overlayToRemove)) {
                return folder.getItems().remove(overlayToRemove);
            }
            if (overlay instanceof FolderOverlay) {
                if (removeOverlay((FolderOverlay) overlay, overlayToRemove)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void deleteDevice(String delDeviceNumber, String networkID, String nodeID, String deviceType) {
        progressBarLayout.setProcessText("Delete Device...");
        progressBarLayout.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        JsonArray devicesArray = new JsonArray();
        JsonObject deviceObject = new JsonObject();
        deviceObject.addProperty("DeviceNumber", delDeviceNumber);
        deviceObject.addProperty("DeviceType", deviceType);
        deviceObject.addProperty("SectionId", delDeviceNumber);
        deviceObject.addProperty("NodeId", nodeID);
        deviceObject.addProperty("NetworkId", networkID);
        deviceObject.addProperty("CYMDBNET", prefManager.getDBName());
        devicesArray.add(deviceObject);
        jsonObject.add("devices", devicesArray);
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<DeleteSectionModel> call = apiInterface.deleteSection("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<DeleteSectionModel>() {
            @Override
            public void onResponse(@NonNull Call<DeleteSectionModel> call, @NonNull Response<DeleteSectionModel> response) {
                if (response.code() == 200) {
                    try {
                        progressBarLayout.setVisibility(View.GONE);
                        DeleteSectionModel deleteDeviceModel = response.body();
                        assert deleteDeviceModel != null;
                        if (deleteDeviceModel.getMessage() != null && deleteDeviceModel.getMessage().contains("Feature Deleted Successfully")) {
                            ErrorPdfLogger.logNewConnection(MapActivity.this, "POST", "/deletefeature/", "Feature Deleted Successfully"
                                    + "\nDeviceNumber : " + delDeviceNumber
                                    + "\nDeviceType : " + deviceType
                                    + "\nNetworkId : " + networkID
                                    + "\nNodeId : " + nodeID
                            );

                            if (intent.getStringArrayListExtra("NetworkId") != null) {
                                if (DelDevice.get(delDeviceNumber) != null) {
                                    binding.map.getOverlays().remove(DelDevice.get(delDeviceNumber));

                                    switch (deviceType) {
                                        case "8":
                                            if (CircuitBreakerOverLay != null) {
                                                CircuitBreakerOverLay.getItems().remove(DelDevice.get(delDeviceNumber));
                                                breakerSectionId.remove(delDeviceNumber);
                                                breakerList.remove(DelDevice.get(delDeviceNumber));
                                            }
                                            break;
                                        case "5":
                                            if (DistributionTransferOverLay != null) {
                                                DistributionTransferOverLay.getItems().remove(DelDevice.get(delDeviceNumber));
                                                transformerSectionId.remove(delDeviceNumber);
                                                transformerList.remove(DelDevice.get(delDeviceNumber));
                                            }
                                            break;
                                        case "14":
                                            if (FuseOverLay != null) {
                                                FuseOverLay.getItems().remove(DelDevice.get(delDeviceNumber));
                                                fuseSectionId.remove(delDeviceNumber);
                                                fuseList.remove(DelDevice.get(delDeviceNumber));
                                            }
                                            break;
                                        case "13":
                                            if (SwitchOverLay != null) {
                                                SwitchOverLay.getItems().remove(DelDevice.get(delDeviceNumber));
                                                switchSectionId.remove(delDeviceNumber);
                                                switchedList.remove(DelDevice.get(delDeviceNumber));
                                            }
                                            break;
                                        case "17":
                                            if (ShuntCapacitorOverLay != null) {
                                                ShuntCapacitorOverLay.getItems().remove(DelDevice.get(delDeviceNumber));
                                                capacitorSectionId.remove(delDeviceNumber);
                                                capacitorList.remove(DelDevice.get(delDeviceNumber));
                                            }
                                            break;
                                        case "20":
                                            if (SpotLoadOverLay != null) {
                                                SpotLoadOverLay.getItems().remove(DelDevice.get(delDeviceNumber));
                                                spotloadSectionId.remove(delDeviceNumber);
                                                spotLoadList.remove(DelDevice.get(delDeviceNumber));
                                            }
                                            break;
                                    }
                                    updateContinentList(delDeviceNumber, deviceType);

                                    if (previousSelectedDevice != null && previousSelectedDevice.equals(DelDevice.get(delDeviceNumber))) {
                                        previousSelectedDevice = null;
                                        sectionType = null;
                                    }
                                    DelDevice.remove(delDeviceNumber);
                                    DelDeviceNumber = null;

                                    binding.map.invalidate();
                                    binding.map.postInvalidate();
                                }
                                Snackbar.make(binding.getRoot(), "Device delete successfully", Snackbar.LENGTH_SHORT).show();
                            }

                        } else {
                            Snackbar.make(binding.getRoot(), deleteDeviceModel.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        ErrorPdfLogger.logNewConnection(MapActivity.this, e);
                        Log.d("Exception", e.getLocalizedMessage());;
                    }
                } else {
                    progressBarLayout.setVisibility(View.GONE);
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), response.message() + " - " + response.code(), Snackbar.LENGTH_SHORT);
                    snack.show();
                    ErrorPdfLogger.logNewConnection(
                            MapActivity.this,
                            "POST",
                            "/deletefeature/",
                            "HTTP " + response.code() + " : " + response.message()
                                    + "\nSectionId : " + delDeviceNumber
                                    + "\nNetworkId : " + networkID
                                    + "\nNodeId : " + nodeID
                    );
                }
            }

            @Override
            public void onFailure(@NonNull Call<DeleteSectionModel> call, @NonNull Throwable t) {
                progressBarLayout.setVisibility(View.GONE);
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_msg), Snackbar.LENGTH_SHORT);
                snack.show();
                ErrorPdfLogger.logNewConnection(
                        MapActivity.this,
                        "POST",
                        "/deletefeature/",
                        Log.getStackTraceString(t)
                );
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void LoadFlowEdt(ArrayList<String> networkId, String userName, String state, String nearstConsumerNo) {
        progressBarLayout.setProcessText("Load-Flow Analysis Run...");
        progressBarLayout.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new Gson().toJsonTree(networkId).getAsJsonArray();
        jsonObject.add("NetworkId", jsonArray);
        jsonObject.addProperty("Username", userName);
        jsonObject.addProperty("State", state);
        jsonObject.addProperty("CustomerNo", nearstConsumerNo);
        jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<LoadFlowEdtModel> call = apiInterface.LoadFlowEdt("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<LoadFlowEdtModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<LoadFlowEdtModel> call, @NonNull Response<LoadFlowEdtModel> response) {
                if (response.code() == 200) {
                    ErrorPdfLogger.logApiSuccess(MapActivity.this, "POST", "loadflowedit/", "HTTP " + response.code() + " LoadFlowEdit: " + response.message()
                            + "NetworkId : " + jsonArray
                            + "UserName : " + userName
                            + "State : " + state
                            + "CustomerNo : " + nearstConsumerNo
                            + "CYMDBNET : " + prefManager.getDBName()
                            + "AccessToken : " +prefManager.getAccessToken());
                    try {
                        progressBarLayout.setVisibility(View.GONE);

                        ArrayList<String> networkIDs = intent.getStringArrayListExtra("NetworkId");

                        if (state.equalsIgnoreCase("After")) {
                            if (networkIDs != null && !networkIDs.isEmpty()) {
                                showFeasibilityDialog(MapActivity.this, networkIDs, new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {

                                    }
                                });
                            } else {
                                Toast.makeText(MapActivity.this, "No Network ID found!", Toast.LENGTH_SHORT).show();
                            }
                        } else if (state.equalsIgnoreCase("Before")) {
                            if (networkIDs != null && !networkIDs.isEmpty()) {
                                showFeasibilityDialog(MapActivity.this, networkIDs, new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {

                                    }
                                });
                            } else {
                                Toast.makeText(MapActivity.this, "No Network ID found!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        ErrorPdfLogger.logCrash(MapActivity.this,e);
                        e.getLocalizedMessage();
                    }
                } else {
                    ErrorPdfLogger.logApiSuccess(MapActivity.this, "POST", "loadflowedit/", "HTTP " + response.code() + " : " + response.message()
                            + "NetworkId : " + jsonArray
                            + "UserName : " + userName
                            + "State : " + state
                            + "CustomerNo : " + nearstConsumerNo
                            + "CYMDBNET : " + prefManager.getDBName()
                            + "AccessToken : " +prefManager.getAccessToken());
                    progressBarLayout.setVisibility(View.GONE);
                    @SuppressLint("InflateParams") View layout = LayoutInflater.from(MapActivity.this).inflate(R.layout.toast_layout, null);
                    TextView Ok = layout.findViewById(R.id.okBtn);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView header = layout.findViewById(R.id.headerTv);
                    @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView description = layout.findViewById(R.id.descripTv);
                    header.setText(response.message() + " - " + response.code());
                    description.setText(getString(R.string.error_msg));
                    Ok.setOnClickListener(v -> {
                        LoadFlowEdt(networkId, userName, state, nearstConsumerNo);
                    });
                    Toast toast = new Toast(MapActivity.this);
                    toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<LoadFlowEdtModel> call, @NonNull Throwable t) {
                ErrorPdfLogger.logApiFailure(MapActivity.this, "POST", "loadflowedit/", t);
                progressBarLayout.setVisibility(View.GONE);
                @SuppressLint("InflateParams") View layout = LayoutInflater.from(MapActivity.this).inflate(R.layout.toast_layout, null);
                TextView Ok = layout.findViewById(R.id.okBtn);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView header = layout.findViewById(R.id.headerTv);
                @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView description = layout.findViewById(R.id.descripTv);
                header.setText(getString(R.string.error));
                description.setText(getString(R.string.error_msg));
                Ok.setOnClickListener(v -> {
                    LoadFlowEdt(networkId, userName, state, nearstConsumerNo);
                });
                Toast toast = new Toast(MapActivity.this);
                toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    private void showFeasibilityDialog(Context context, ArrayList<String> networkID, DialogInterface.OnDismissListener onDismissListener) {
        PrefManager prefManager = new PrefManager(context);
        new Thread(() -> {
            FeasibilityBinding binding = FeasibilityBinding.inflate(LayoutInflater.from(context));
            ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("UserType", prefManager.getType());
            jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
            jsonObject.addProperty("DatabaseType", "NewConnection");
            Call<NewConnectionModel> call = apiInterface.getNewConnectionData("Bearer " + prefManager.getAccessToken(), jsonObject);
            call.enqueue(new Callback<NewConnectionModel>() {
                @Override
                public void onResponse(@NonNull Call<NewConnectionModel> call, @NonNull Response<NewConnectionModel> response) {
                    if (response.code() == 200) {
                        ErrorPdfLogger.logApiSuccess(MapActivity.this, "POST", "MSEDCL_NC/networklist/", "HTTP " + response.code() + " MAP_Feasibility: " + response.message()
                                + "AccessToken : " +prefManager.getAccessToken());
                        NewConnectionModel newConnectionModel = response.body();
                        if (newConnectionModel != null && newConnectionModel.getOutput() != null) {
                            for (NewConnectionModel.Output output : newConnectionModel.getOutput()) {
                                if (output.getFeederid().contains(networkID.get(0))) {
                                    binding.applicationId.setText((output.getApplicationID() != null ? output.getApplicationID() : "").toString());
                                    binding.dtLoading.setText(output.getDtgisid() != null ? output.getDtgisid() : "");
                                    binding.vr.setText(output.getBeforePercentageVR() != null ? output.getBeforePercentageVR().toString() : "");
                                    binding.vrR.setText(output.getAfterPercentageVR() != null ? output.getAfterPercentageVR().toString() : "");
                                    binding.dtLoading.setText(output.getBeforeDTLoading() != null ? output.getBeforeDTLoading().toString() : "");
                                    binding.dtAloading.setText(output.getAfterDTLoading() != null ? output.getAfterDTLoading().toString() : "");
                                    binding.feasibility.setText(output.getFeasibility() != null ? output.getFeasibility() : "");
                                }
                            }
                        }
                    } else if (response.code() == 401) {
                        ErrorPdfLogger.logApiError(MapActivity.this, "POST", "MSEDCL_NC/networklist/", "HTTP " + response.code() + " MapFeasibility: " + response.message()
                                + "AccessToken : " +prefManager.getAccessToken());
                        prefManager.setIsUserLogin(false);
                        context.startActivity(new Intent(context, LoginActivity.class));
                    } else {
                        ErrorPdfLogger.logApiError(MapActivity.this, "POST", "MSEDCL_NC/networklist/", "HTTP " + response.code() + " MapFeasibility: " + response.message()
                                + "AccessToken : " +prefManager.getAccessToken());
                        Toast.makeText(context, response.message() + " - " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<NewConnectionModel> call, @NonNull Throwable t) {
                    ErrorPdfLogger.logApiFailure(MapActivity.this, "POST", "MSEDCL_NC/networklist/", t);
                    Toast.makeText(context, context.getString(R.string.error_msg), Toast.LENGTH_SHORT).show();
                }
            });
            runOnUiThread(() -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(binding.getRoot());
                builder.setCancelable(true);

                final AlertDialog dialog = builder.create();

                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }

                binding.getRoot().setOnTouchListener(new View.OnTouchListener() {
                    private float initialX, initialY;
                    private float initialTouchX, initialTouchY;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();

                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                initialX = params.x;
                                initialY = params.y;
                                initialTouchX = event.getRawX();
                                initialTouchY = event.getRawY();
                                return true;

                            case MotionEvent.ACTION_MOVE:
                                params.x = (int) (initialX + (event.getRawX() - initialTouchX));
                                params.y = (int) (initialY + (event.getRawY() - initialTouchY));
                                dialog.getWindow().setAttributes(params);
                                return true;

                            default:
                                return false;
                        }
                    }
                });

                dialog.setCanceledOnTouchOutside(true);

                if (onDismissListener != null) {
                    dialog.setOnDismissListener(onDismissListener);
                }

                dialog.show();
            });
        }).start();
    }

    private void AddDevices() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (ReactorOverLay != null && !binding.map.getOverlays().contains(ReactorOverLay)) {
                    binding.map.getOverlayManager().add(ReactorOverLay);
                }

                if (CircuitBreakerOverLay != null && !binding.map.getOverlays().contains(CircuitBreakerOverLay)) {
                    binding.map.getOverlayManager().add(CircuitBreakerOverLay);
                }

                if (DistributionTransferOverLay != null && !binding.map.getOverlays().contains(DistributionTransferOverLay)) {
                    binding.map.getOverlayManager().add(DistributionTransferOverLay);
                }

                if (SectionLizerOverLay != null && !binding.map.getOverlays().contains(SectionLizerOverLay)) {
                    binding.map.getOverlayManager().add(SectionLizerOverLay);
                }

                if (FuseOverLay != null && !binding.map.getOverlays().contains(FuseOverLay)) {
                    binding.map.getOverlayManager().add(FuseOverLay);
                }

                if (SwitchOverLay != null && !binding.map.getOverlays().contains(SwitchOverLay)) {
                    binding.map.getOverlayManager().add(SwitchOverLay);
                }

                if (ShuntCapacitorOverLay != null && !binding.map.getOverlays().contains(ShuntCapacitorOverLay)) {
                    binding.map.getOverlayManager().add(ShuntCapacitorOverLay);
                }

                if (SpotLoadOverLay != null && !binding.map.getOverlays().contains(SpotLoadOverLay)) {
                    binding.map.getOverlayManager().add(SpotLoadOverLay);
                }

                if (nodeOverLay != null && !binding.map.getOverlays().contains(nodeOverLay)) {
                    binding.map.getOverlayManager().add(nodeOverLay);
                }

                if (ReclosureOverLay != null && !binding.map.getOverlays().contains(ReclosureOverLay)) {
                    binding.map.getOverlayManager().add(ReclosureOverLay);
                }

                if (UnBalanceFolderOverLay != null && !binding.map.getOverlays().contains(UnBalanceFolderOverLay)) {
                    binding.map.getOverlayManager().add(UnBalanceFolderOverLay);
                }

                if (PhotoVoltaicOverLay != null && !binding.map.getOverlays().contains(PhotoVoltaicOverLay)) {
                    binding.map.getOverlayManager().add(PhotoVoltaicOverLay);
                }

                if (WindOverLay != null && !binding.map.getOverlays().contains(WindOverLay)) {
                    binding.map.getOverlayManager().add(WindOverLay);
                }

                if (BatteryOverLay != null && !binding.map.getOverlays().contains(BatteryOverLay)) {
                    binding.map.getOverlayManager().add(BatteryOverLay);
                }
            }
        });
    }

    private void RemoveDevices() {
        AsyncTask.execute(() -> {
            if (binding.map.getOverlays().contains(CircuitBreakerOverLay)) {
                binding.map.getOverlayManager().remove(CircuitBreakerOverLay);
            }

            if (binding.map.getOverlays().contains(DistributionTransferOverLay)) {
                binding.map.getOverlayManager().remove(DistributionTransferOverLay);
            }

            if (binding.map.getOverlays().contains(SectionLizerOverLay)) {
                binding.map.getOverlayManager().remove(SectionLizerOverLay);
            }

            if (binding.map.getOverlays().contains(FuseOverLay)) {
                binding.map.getOverlayManager().remove(FuseOverLay);
            }

            if (binding.map.getOverlays().contains(SwitchOverLay)) {
                binding.map.getOverlayManager().remove(SwitchOverLay);
            }

            if (binding.map.getOverlays().contains(ShuntCapacitorOverLay)) {
                binding.map.getOverlayManager().remove(ShuntCapacitorOverLay);
            }

            if (binding.map.getOverlays().contains(ReclosureOverLay)) {
                binding.map.getOverlayManager().remove(ReclosureOverLay);
            }

            if (binding.map.getOverlays().contains(SpotLoadOverLay)) {
                binding.map.getOverlayManager().remove(SpotLoadOverLay);
            }

            if (binding.map.getOverlays().contains(PhotoVoltaicOverLay)) {
                binding.map.getOverlayManager().remove(PhotoVoltaicOverLay);
            }

            if (binding.map.getOverlays().contains(WindOverLay)) {
                binding.map.getOverlayManager().remove(WindOverLay);
            }

            if (binding.map.getOverlays().contains(BatteryOverLay)) {
                binding.map.getOverlayManager().remove(BatteryOverLay);
            }

            if (binding.map.getOverlays().contains(ReactorOverLay)) {
                binding.map.getOverlayManager().remove(ReactorOverLay);
            }
        });
    }

    private void RemoveNodes() {
        AsyncTask.execute(() -> {
            if (binding.map.getOverlays().contains(nodeOverLay)) {
                binding.map.getOverlayManager().remove(nodeOverLay);
            }
        });
    }

    private void AddNodes() {
        AsyncTask.execute(() -> {
            if (nodeOverLay != null && !binding.map.getOverlays().contains(nodeOverLay)) {
                binding.map.getOverlayManager().add(nodeOverLay);
            }
        });
    }

    private void loadFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        clearBackStack();
        fragmentManager.beginTransaction().replace(R.id.bottomContainer, fragment, tag).addToBackStack(null).commit();
    }

    @SuppressLint("SetTextI18n")
    public void getNetworkData(String feederId) {
        progressBarLayout.setProcessText("Reading Files...");
        progressBarLayout.setVisibility(View.VISIBLE);
        new Thread(() -> {
            try {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("NetworkName", feederId);
                jsonObject.addProperty("UserType", prefManager.getUserType());
                jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
                String AccessToken = prefManager.getAccessToken();
                ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
                Call<JsonObject> call = apiInterface.getNetworkData("networkdata/", "Bearer " + AccessToken, jsonObject);
                call.enqueue(new Callback<JsonObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                        if (response.code() == 200) {
                            /*ErrorPdfLogger.logApiSuccess(MapActivity.this, "POST", "networkdata/", "HTTP " + response.code() + " MAP: " + response.message()
                                    + "NetworkName : " + feederId
                                    + "UserType : " + prefManager.getUserType()
                                    + "CYMDBNET : " + prefManager.getDBName()
                                    + "url : " + prefManager.getProjectName()+"networkdata/"
                                    + "AccessToken : " +prefManager.getAccessToken());*/
                            try {
                                progressBarLayout.setVisibility(View.GONE);
                                JsonObject jsonObject1 = response.body();
                                while (!ResponseDataUtils.NetworkList.isEmpty()) {
                                    ResponseDataUtils.NetworkList.clear();
                                }

                                assert jsonObject1 != null;
                                JSONObject jsonObject2 = new JSONObject(jsonObject1.toString());
                                new AddGeoJsonData(binding.map, jsonObject2).execute();
                                binding.map.postDelayed(() -> {handleCenterUTM();}, 500);
                                if (!selectedFeeder.isEmpty()) {
                                    selectedFeeder.remove(feederId);
                                }
                            } catch (JSONException e) {
                                ErrorPdfLogger.logCrash(MapActivity.this,e);
                                Log.d("Exception", Objects.requireNonNull(e.getLocalizedMessage()));
                            }
                        } else {
                            ErrorPdfLogger.logApiError(MapActivity.this, "POST", "networkdata/", "HTTP " + response.code() + " Map: " + response.message()
                                    + "NetworkName : " + feederId
                                    + "UserType : " + prefManager.getUserType()
                                    + "CYMDBNET : " + prefManager.getDBName()
                                    + "url : " + prefManager.getProjectName()+"networkdata/"
                                    + "AccessToken : " +prefManager.getAccessToken());
                            progressBarLayout.setVisibility(View.GONE);
                            @SuppressLint("InflateParams") View layout = LayoutInflater.from(MapActivity.this).inflate(R.layout.toast_layout, null);
                            TextView Ok = layout.findViewById(R.id.okBtn);
                            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView header = layout.findViewById(R.id.headerTv);
                            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView description = layout.findViewById(R.id.descripTv);
                            header.setText(response.message() + " - " + response.code());
                            description.setText(getString(R.string.error_msg));
                            Ok.setOnClickListener(v -> {
                                getNetworkData(feederId);
                            });
                            Toast toast = new Toast(MapActivity.this);
                            toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                        ErrorPdfLogger.logApiFailure(MapActivity.this, "POST", "networkdata/", t);
                        progressBarLayout.setVisibility(View.GONE);
                        @SuppressLint("InflateParams") View layout = LayoutInflater.from(MapActivity.this).inflate(R.layout.toast_layout, null);
                        TextView Ok = layout.findViewById(R.id.okBtn);
                        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView header = layout.findViewById(R.id.headerTv);
                        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView description = layout.findViewById(R.id.descripTv);
                        header.setText(getString(R.string.error));
                        description.setText(getString(R.string.error_msg));
                        Ok.setOnClickListener(v -> {
                            getNetworkData(feederId);
                        });
                        Toast toast = new Toast(MapActivity.this);
                        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();
                    }
                });
            } catch (Exception e) {
                ErrorPdfLogger.logCrash(MapActivity.this,e);
                Log.d("Exception", e.getLocalizedMessage());
            }
        }).start();
    }

    @SuppressLint("StaticFieldLeak")
    private class AddGeoJsonData extends AsyncTask<Void, Void, FolderOverlay> {
        private MapView mMapView;
        private JSONObject jsonObject;

        public AddGeoJsonData(MapView mMapView, JSONObject jsonObject) {
            this.mMapView = mMapView;
            this.jsonObject = jsonObject;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarLayout.setVisibility(View.VISIBLE);
            progressBarLayout.setProcessText("Loading Networks...");
        }

        @Override
        protected FolderOverlay doInBackground(Void... voids) {
            try {
                if (!jsonObject.toString().isEmpty() && !jsonObject.toString().trim().equals("null")) {
                    object = jsonObject.getJSONObject("output");
                    JSONObject[] layers = new JSONObject[]{
                            object.optJSONObject("HEADNODE"),
                            object.optJSONObject("cables_data2"),
                            object.optJSONObject("oh_data2"),
                            object.optJSONObject("ohunbal_data2"),
                            object.optJSONObject("sectionnodev"),
                            object.optJSONObject("reclosuredev"),
                            object.optJSONObject("cb_data2"),
                            object.optJSONObject("dt_data2"),
                            object.optJSONObject("fuse_data2"),
                            object.optJSONObject("switch_data2"),
                            object.optJSONObject("shunt_capacitor2"),
                            object.optJSONObject("spotload2"),
                            object.optJSONObject("node_data2"),
                            //Need add new devices
                            object.optJSONObject("reclosuredev"),
                            object.optJSONObject("photovoltaic"),
                            object.optJSONObject("battery"),
                            object.optJSONObject("ShuntReactorModel"),
                            object.optJSONObject("wind"),
                    };

                    for (JSONObject layer : layers) {
                        if (layer == null) {
                            continue;
                        }
                        JSONArray features = layer.optJSONArray("features");
                        if (features == null) {
                            continue;
                        }
                        for (int i = 0; i < features.length(); i++) {
                            JSONObject feature = features.optJSONObject(i);
                            if (feature == null) {
                                continue;
                            }
                            JSONObject geometry = feature.optJSONObject("geometry");
                            if (geometry == null) {
                                continue;
                            }
                            String type = geometry.optString("type");
                            if ("Point".equals(type)) {
                                JSONArray coord = geometry.optJSONArray("coordinates");
                                if (coord == null || coord.length() < 2) {
                                    continue;
                                }
                                double easting = coord.optDouble(0);
                                double northing = coord.optDouble(1);
                                GeoPoint latLon = UTMConversion.convert(easting, northing);
                                JSONArray newCoords = new JSONArray();
                                newCoords.put(latLon.getLongitude());
                                newCoords.put(latLon.getLatitude());
                                geometry.put("coordinates", newCoords);
                            } else if ("LineString".equals(type)) {
                                JSONArray coords = geometry.optJSONArray("coordinates");
                                if (coords == null) {
                                    continue;
                                }
                                JSONArray newCoords = new JSONArray();
                                for (int k = 0; k < coords.length(); k++) {
                                    JSONArray point = coords.optJSONArray(k);
                                    if (point == null || point.length() < 2) {
                                        continue;
                                    }
                                    double easting = point.optDouble(0);
                                    double northing = point.optDouble(1);
                                    GeoPoint latLon = UTMConversion.convert(easting, northing);
                                    JSONArray p = new JSONArray();
                                    p.put(latLon.getLongitude());
                                    p.put(latLon.getLatitude());
                                    newCoords.put(p);
                                }
                                geometry.put("coordinates", newCoords);
                            } else if ("MultiLineString".equals(type)) {
                                JSONArray lines = geometry.optJSONArray("coordinates");
                                if (lines == null) {
                                    continue;
                                }
                                JSONArray newLines = new JSONArray();
                                for (int k = 0; k < lines.length(); k++) {
                                    JSONArray line = lines.optJSONArray(k);
                                    if (line == null) {
                                        continue;
                                    }
                                    JSONArray newLine = new JSONArray();
                                    for (int j = 0; j < line.length(); j++) {
                                        JSONArray point = line.optJSONArray(j);
                                        if (point == null || point.length() < 2) {
                                            continue;
                                        }
                                        double easting = point.optDouble(0);
                                        double northing = point.optDouble(1);
                                        GeoPoint latLon = UTMConversion.convert(easting, northing);
                                        JSONArray p = new JSONArray();
                                        p.put(latLon.getLongitude());
                                        p.put(latLon.getLatitude());
                                        newLine.put(p);
                                    }
                                    newLines.put(newLine);
                                }
                                geometry.put("coordinates", newLines);
                            }
                        }
                    }
                    if (!object.getJSONObject("cables_data2").getJSONArray("features").toString().contains("[]") || !object.getJSONObject("oh_data2").getJSONArray("features").toString().contains("[]") || !object.getJSONObject("oh_data2").getJSONArray("features").toString().contains("[]") || !object.getJSONObject("HEADNODE").getJSONArray("features").toString().contains("[]")) {

                        if (!object.getJSONObject("HEADNODE").getJSONArray("features").toString().equals("[]")) {
                            if (sourceKml != null) {
                                sourceKml.parseGeoJSON(object.getJSONObject("HEADNODE").toString());
                                KmlFeature.Styler styler = new SourceKmlStyler(Color.BLUE, binding.map);
                                FolderOverlay folderOverlay = (FolderOverlay) sourceKml.mKmlRoot.buildOverlay(binding.map, null, styler, sourceKml);
                                SourceOverLay.add(folderOverlay);
                                double fromY = Double.parseDouble(object.getJSONObject("HEADNODE").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("fromy"));
                                double fromX = Double.parseDouble(object.getJSONObject("HEADNODE").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("fromX"));
                                GeoPoint latLon = UTMConversion.convert(fromX, fromY);
                                sourcePoint = new GeoPoint(latLon.getLatitude(), latLon.getLongitude());

                                for (int i = 0; i < continentList.size(); i++) {
                                    if (continentList.get(i).getName().contains("Feeder ID")) {
                                        for (int j = 0; j < object.getJSONObject("HEADNODE").getJSONArray("features").length(); j++) {
                                            DType type = new DType(object.getJSONObject("HEADNODE").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("NetworkId"), 43);
                                            continentList.get(i).getDeviceList().add(type);
                                            continentList.get(i).setName("Feeder ID" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                        }
                                    }
                                }
                            } else {
                                sourceKml = new KmlDocument();
                                sourceKml.parseGeoJSON(object.getJSONObject("HEADNODE").toString());
                                KmlFeature.Styler styler = new SourceKmlStyler(Color.BLUE, binding.map);
                                SourceOverLay = (FolderOverlay) sourceKml.mKmlRoot.buildOverlay(binding.map, null, styler, sourceKml);

                                double fromY = Double.parseDouble(object.getJSONObject("HEADNODE").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("fromy"));
                                double fromX = Double.parseDouble(object.getJSONObject("HEADNODE").getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("fromX"));
                                GeoPoint latLon = UTMConversion.convert(fromX, fromY);
                                sourcePoint = new GeoPoint(latLon.getLatitude(), latLon.getLongitude());

                                ArrayList<DType> list = new ArrayList<>();
                                DType type;
                                for (int i = 0; i < object.getJSONObject("HEADNODE").getJSONArray("features").length(); i++) {
                                    type = new DType(object.getJSONObject("HEADNODE").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("NetworkId"), 43);
                                    list.add(type);
                                    DeviceName deviceName = new DeviceName(object.getJSONObject("HEADNODE").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("NetworkId"), "43");
                                    mList.add(deviceName);
                                }
                                Continent continent = new Continent("Feeder ID" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }
                        }

                        if (!object.getJSONObject("cables_data2").getJSONArray("features").toString().equals("[]")) {
                            if (CableKml != null) {
                                CableKml.parseGeoJSON(object.getJSONObject("cables_data2").toString());
                                KmlFeature.Styler styler = new MyKmlStyler(Color.BLUE, binding.map);

                                FolderOverlay folderOverlay = (FolderOverlay) CableKml.mKmlRoot.buildOverlay(binding.map, null, styler, CableKml);
                                CableFolderOverLay.add(folderOverlay);

                                for (int i = 0; i < continentList.size(); i++) {
                                    if (continentList.get(i).getName().contains("Cable")) {
                                        for (int j = 0; j < object.getJSONObject("cables_data2").getJSONArray("features").length(); j++) {
                                            DType type = new DType(object.getJSONObject("cables_data2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("SectionId"), Integer.parseInt(object.getJSONObject("cables_data2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceType")));
                                            continentList.get(i).getDeviceList().add(type);
                                            continentList.get(i).setName("Cable" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                        }
                                    }
                                }

                                for (int j = 0; j < object.getJSONObject("cables_data2").getJSONArray("features").length(); j++) {
                                    CaObject.getJSONArray("features").put(object.getJSONObject("cables_data2").getJSONArray("features").get(j));
                                }

                            } else {
                                CaObject = new JSONObject(object.getJSONObject("cables_data2").toString());
                                CableKml = new KmlDocument();
                                CableKml.parseGeoJSON(object.getJSONObject("cables_data2").toString());
                                KmlFeature.Styler styler = new MyKmlStyler(Color.BLUE, binding.map);
                                CableFolderOverLay = (FolderOverlay) CableKml.mKmlRoot.buildOverlay(binding.map, null, styler, CableKml);

                                ArrayList<DType> list = new ArrayList<>();
                                DType type;
                                DeviceName deviceName;
                                for (int i = 0; i < object.getJSONObject("cables_data2").getJSONArray("features").length(); i++) {
                                    type = new DType(object.getJSONObject("cables_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("cables_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType")));
                                    list.add(type);
                                    deviceName = new DeviceName(object.getJSONObject("cables_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), object.getJSONObject("cables_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType"));
                                    mList.add(deviceName);
                                }
                                Continent continent = new Continent("Cable" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }
                        }

                        if (!object.getJSONObject("oh_data2").getJSONArray("features").toString().equals("[]")) {
                            if (OverHeadKml != null) {
                                OverHeadKml.parseGeoJSON(object.getJSONObject("oh_data2").toString());
                                KmlFeature.Styler styler = new OverHeadKmlStyler(Color.BLUE, binding.map);
                                FolderOverlay folderOverlay = (FolderOverlay) OverHeadKml.mKmlRoot.buildOverlay(binding.map, null, styler, OverHeadKml);
                                OverheadFolderOverLay.add(folderOverlay);

                                for (int i = 0; i < continentList.size(); i++) {
                                    if (continentList.get(i).getName().contains("Overhead Balance")) {
                                        for (int j = 0; j < object.getJSONObject("oh_data2").getJSONArray("features").length(); j++) {
                                            DType type = new DType(object.getJSONObject("oh_data2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("SectionId"), Integer.parseInt(object.getJSONObject("oh_data2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceType")));
                                            continentList.get(i).getDeviceList().add(type);
                                            continentList.get(i).setName("Overhead Balance" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                        }
                                    }
                                }

                                for (int j = 0; j < object.getJSONObject("oh_data2").getJSONArray("features").length(); j++) {
                                    OhObject.getJSONArray("features").put(object.getJSONObject("oh_data2").getJSONArray("features").get(j));
                                }

                            } else {
                                OhObject = new JSONObject(object.getJSONObject("oh_data2").toString());
                                OverHeadKml = new KmlDocument();
                                OverHeadKml.parseGeoJSON(object.getJSONObject("oh_data2").toString());
                                KmlFeature.Styler styler = new OverHeadKmlStyler(Color.BLUE, binding.map);
                                OverheadFolderOverLay = (FolderOverlay) OverHeadKml.mKmlRoot.buildOverlay(binding.map, null, styler, OverHeadKml);

                                ArrayList<DType> list = new ArrayList<>();
                                DType type;
                                for (int i = 0; i < object.getJSONObject("oh_data2").getJSONArray("features").length(); i++) {
                                    type = new DType(object.getJSONObject("oh_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("oh_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType")));
                                    list.add(type);
                                    DeviceName deviceName = new DeviceName(object.getJSONObject("oh_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), object.getJSONObject("oh_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType"));
                                    mList.add(deviceName);
                                    //OhSectionList.add(OhObject.getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("SectionId"));
                                }
                                Continent continent = new Continent("Overhead Balance" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }

                        }

                        if (!object.getJSONObject("ohunbal_data2").getJSONArray("features").toString().equals("[]")) {
                            if (UnBalencedKml != null) {
                                UnBalencedKml.parseGeoJSON(object.getJSONObject("ohunbal_data2").toString());
                                KmlFeature.Styler styler = new UnbalanceKmlStyler(Color.BLACK, binding.map);
                                FolderOverlay folderOverlay = (FolderOverlay) UnBalencedKml.mKmlRoot.buildOverlay(binding.map, null, styler, UnBalencedKml);
                                UnBalanceFolderOverLay.add(folderOverlay);

                                for (int i = 0; i < continentList.size(); i++) {
                                    if (continentList.get(i).getName().contains("Unbalance")) {
                                        for (int j = 0; j < object.getJSONObject("ohunbal_data2").getJSONArray("features").length(); j++) {
                                            DType type = new DType(object.getJSONObject("ohunbal_data2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("SectionId"), Integer.parseInt(object.getJSONObject("ohunbal_data2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceType")));
                                            continentList.get(i).getDeviceList().add(type);
                                            continentList.get(i).setName("Unbalance" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                        }
                                    }
                                }

                                for (int j = 0; j < object.getJSONObject("ohunbal_data2").getJSONArray("features").length(); j++) {
                                    UnBalObject.getJSONArray("features").put(object.getJSONObject("ohunbal_data2").getJSONArray("features").get(j));
                                }
                            } else {
                                UnBalObject = new JSONObject(object.getJSONObject("ohunbal_data2").toString());
                                UnBalencedKml = new KmlDocument();
                                UnBalencedKml.parseGeoJSON(object.getJSONObject("ohunbal_data2").toString());
                                KmlFeature.Styler styler = new UnbalanceKmlStyler(Color.BLACK, binding.map);
                                UnBalanceFolderOverLay = (FolderOverlay) UnBalencedKml.mKmlRoot.buildOverlay(binding.map, null, styler, UnBalencedKml);

                                ArrayList<DType> list = new ArrayList<>();
                                DType type;
                                for (int i = 0; i < object.getJSONObject("ohunbal_data2").getJSONArray("features").length(); i++) {
                                    type = new DType(object.getJSONObject("ohunbal_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("ohunbal_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType")));
                                    list.add(type);
                                    DeviceName deviceName = new DeviceName(object.getJSONObject("ohunbal_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), object.getJSONObject("ohunbal_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType"));
                                    mList.add(deviceName);
                                }
                                Continent continent = new Continent("Unbalance" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }

                        }

                        if (!object.getJSONObject("sectionnodev").getJSONArray("features").toString().equals("[]")) {
                            if (sectionNodeKml != null) {
                                sectionNodeKml.parseGeoJSON(String.valueOf(object.getJSONObject("sectionnodev")));
                                KmlFeature.Styler styler = new SectionNodeKmlStyler(Color.BLACK, binding.map);

                                FolderOverlay folderOverlay = (FolderOverlay) sectionNodeKml.mKmlRoot.buildOverlay(binding.map, null, styler, sectionNodeKml);
                                sectionFolderOverLay.add(folderOverlay);
                                for (int j = 0; j < object.getJSONObject("sectionnodev").getJSONArray("features").length(); j++) {
                                    SecNodeObject.getJSONArray("features").put(object.getJSONObject("sectionnodev").getJSONArray("features").get(j));
                                }

                                for (int i = 0; i < SecNodeObject.getJSONArray("features").length(); i++) {
                                    spLineSectionList.add(SecNodeObject.getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"));
                                }
                            } else {
                                SecNodeObject = new JSONObject(object.getJSONObject("sectionnodev").toString());
                                sectionNodeKml = new KmlDocument();
                                sectionNodeKml.parseGeoJSON(String.valueOf(SecNodeObject));
                                KmlFeature.Styler styler = new SectionNodeKmlStyler(Color.BLACK, binding.map);
                                sectionFolderOverLay = (FolderOverlay) sectionNodeKml.mKmlRoot.buildOverlay(binding.map, null, styler, sectionNodeKml);
                                for (int i = 0; i < SecNodeObject.getJSONArray("features").length(); i++) {
                                    spLineSectionList.add(SecNodeObject.getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"));
                                }
                            }
                        }

                        if (!object.getJSONObject("reclosuredev").getJSONArray("features").toString().equals("[]")) {
                            if (ReclosuerKml != null) {
                                ReclosuerKml.parseGeoJSON(object.getJSONObject("reclosuredev").toString());
                                KmlFeature.Styler styler = new ReclouserKmlStyler(Color.BLACK, binding.map);
                                FolderOverlay folderOverlay = (FolderOverlay) ReclosuerKml.mKmlRoot.buildOverlay(binding.map, null, styler, ReclosuerKml);
                                ReclosureOverLay.add(folderOverlay);

                                for (int i = 0; i < continentList.size(); i++) {
                                    if (continentList.get(i).getName().contains("Recloser")) {
                                        for (int j = 0; j < object.getJSONObject("reclosuredev").getJSONArray("features").length(); j++) {
                                            DType type = new DType(object.getJSONObject("reclosuredev").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("reclosuredev").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceType")));
                                            continentList.get(i).getDeviceList().add(type);
                                            continentList.get(i).setName("Recloser" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                        }
                                    }
                                }
                            } else {
                                ReclosuerKml = new KmlDocument();
                                ReclosuerKml.parseGeoJSON(object.getJSONObject("reclosuredev").toString());
                                KmlFeature.Styler styler = new ReclouserKmlStyler(Color.BLACK, binding.map);
                                ReclosureOverLay = (FolderOverlay) ReclosuerKml.mKmlRoot.buildOverlay(binding.map, null, styler, ReclosuerKml);

                                ArrayList<DType> list = new ArrayList<>();
                                DType type;
                                for (int i = 0; i < object.getJSONObject("reclosuredev").getJSONArray("features").length(); i++) {
                                    type = new DType(object.getJSONObject("reclosuredev").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("reclosuredev").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType")));
                                    list.add(type);
                                    DeviceName deviceName = new DeviceName(object.getJSONObject("reclosuredev").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), object.getJSONObject("reclosuredev").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType"));
                                    mList.add(deviceName);
                                }
                                Continent continent = new Continent("Recloser" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }
                        }

                        if (object.optJSONObject("cb_data2") != null && !object.getJSONObject("cb_data2").getJSONArray("features").toString().equals("[]")) {
                            if (BreakarKml != null) {
                                BreakarKml.parseGeoJSON(object.getJSONObject("cb_data2").toString());
                                KmlFeature.Styler styler = new circuitBreakerKmlStyler(Color.BLUE, binding.map);
                                FolderOverlay folderOverlay = (FolderOverlay) BreakarKml.mKmlRoot.buildOverlay(binding.map, null, styler, BreakarKml);
                                CircuitBreakerOverLay.add(folderOverlay);

                                for (int i = 0; i < continentList.size(); i++) {
                                    if (continentList.get(i).getName().contains("Breaker")) {
                                        for (int j = 0; j < object.getJSONObject("cb_data2").getJSONArray("features").length(); j++) {
                                            DType type = new DType(object.getJSONObject("cb_data2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("cb_data2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceType")));
                                            continentList.get(i).getDeviceList().add(type);
                                            continentList.get(i).setName("Breaker" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                        }

                                    }
                                }
                            } else {
                                BreakarKml = new KmlDocument();
                                BreakarKml.parseGeoJSON(object.getJSONObject("cb_data2").toString());
                                KmlFeature.Styler styler = new circuitBreakerKmlStyler(Color.BLUE, binding.map);
                                CircuitBreakerOverLay = (FolderOverlay) BreakarKml.mKmlRoot.buildOverlay(binding.map, null, styler, BreakarKml);

                                ArrayList<DType> list = new ArrayList<>();
                                DType type;
                                for (int i = 0; i < object.getJSONObject("cb_data2").getJSONArray("features").length(); i++) {
                                    type = new DType(object.getJSONObject("cb_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("cb_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType")));
                                    list.add(type);
                                    DeviceName deviceName = new DeviceName(object.getJSONObject("cb_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), object.getJSONObject("cb_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType"));
                                    mList.add(deviceName);
                                }
                                Continent continent = new Continent("Breaker" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }
                        }

                        if (object.optJSONObject("dt_data2") != null && !object.getJSONObject("dt_data2").getJSONArray("features").toString().equals("[]")) {
                            if (TransformerKml != null) {
                                TransformerKml.parseGeoJSON(object.getJSONObject("dt_data2").toString());
                                KmlFeature.Styler styler = new DistributionTransferKmlStyler(Color.BLUE, binding.map);
                                FolderOverlay folderOverlay = (FolderOverlay) TransformerKml.mKmlRoot.buildOverlay(binding.map, null, styler, TransformerKml);
                                DistributionTransferOverLay.add(folderOverlay);

                                for (int i = 0; i < continentList.size(); i++) {
                                    if (continentList.get(i).getName().contains("Two-Winding Transformer")) {
                                        for (int j = 0; j < object.getJSONObject("dt_data2").getJSONArray("features").length(); j++) {
                                            DType type = new DType(object.getJSONObject("dt_data2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("dt_data2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceType")));
                                            continentList.get(i).getDeviceList().add(type);
                                            continentList.get(i).setName("Two-Winding Transformer" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                        }
                                    }
                                }
                            } else {
                                TransformerKml = new KmlDocument();
                                TransformerKml.parseGeoJSON(object.getJSONObject("dt_data2").toString());
                                KmlFeature.Styler styler = new DistributionTransferKmlStyler(Color.BLUE, binding.map);
                                DistributionTransferOverLay = (FolderOverlay) TransformerKml.mKmlRoot.buildOverlay(binding.map, null, styler, TransformerKml);

                                ArrayList<DType> list = new ArrayList<>();
                                DType type;
                                for (int i = 0; i < object.getJSONObject("dt_data2").getJSONArray("features").length(); i++) {
                                    type = new DType(object.getJSONObject("dt_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("dt_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType")));
                                    list.add(type);
                                    DeviceName deviceName = new DeviceName(object.getJSONObject("dt_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), object.getJSONObject("dt_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType"));
                                    mList.add(deviceName);
                                }
                                Continent continent = new Continent("Two-Winding Transformer" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }

                        }

                        if (!object.getJSONObject("fuse_data2").getJSONArray("features").toString().equals("[]")) {
                            if (FuseKml != null) {
                                FuseKml.parseGeoJSON(object.getJSONObject("fuse_data2").toString());
                                KmlFeature.Styler styler = new FuseKmlStyler(Color.BLUE, binding.map);
                                FolderOverlay folderOverlay = (FolderOverlay) FuseKml.mKmlRoot.buildOverlay(binding.map, null, styler, FuseKml);
                                FuseOverLay.add(folderOverlay);

                                for (int i = 0; i < continentList.size(); i++) {
                                    if (continentList.get(i).getName().contains("Fuse")) {
                                        for (int j = 0; j < object.getJSONObject("fuse_data2").getJSONArray("features").length(); j++) {
                                            DType type = new DType(object.getJSONObject("fuse_data2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("fuse_data2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceType")));
                                            continentList.get(i).getDeviceList().add(type);
                                            continentList.get(i).setName("Fuse" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                        }
                                    }
                                }
                            } else {
                                FuseKml = new KmlDocument();
                                FuseKml.parseGeoJSON(object.getJSONObject("fuse_data2").toString());
                                KmlFeature.Styler styler = new FuseKmlStyler(Color.BLUE, binding.map);
                                FuseOverLay = (FolderOverlay) FuseKml.mKmlRoot.buildOverlay(binding.map, null, styler, FuseKml);

                                ArrayList<DType> list = new ArrayList<>();
                                DType type;
                                for (int i = 0; i < object.getJSONObject("fuse_data2").getJSONArray("features").length(); i++) {
                                    type = new DType(object.getJSONObject("fuse_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("fuse_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType")));
                                    list.add(type);
                                    DeviceName deviceName = new DeviceName(object.getJSONObject("fuse_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), object.getJSONObject("fuse_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType"));
                                    mList.add(deviceName);
                                }
                                Continent continent = new Continent("Fuse" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }
                        }

                        if (!object.getJSONObject("switch_data2").getJSONArray("features").toString().equals("[]")) {
                            if (SwitchKml != null) {
                                SwitchKml.parseGeoJSON(object.getJSONObject("switch_data2").toString());
                                KmlFeature.Styler styler = new SwitchKmlStyler(Color.BLACK, binding.map);
                                FolderOverlay folderOverlay = (FolderOverlay) SwitchKml.mKmlRoot.buildOverlay(binding.map, null, styler, SwitchKml);
                                SwitchOverLay.add(folderOverlay);

                                for (int i = 0; i < continentList.size(); i++) {
                                    if (continentList.get(i).getName().contains("Switch")) {
                                        for (int j = 0; j < object.getJSONObject("switch_data2").getJSONArray("features").length(); j++) {
                                            DType type = new DType(object.getJSONObject("switch_data2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("switch_data2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceType")));
                                            continentList.get(i).getDeviceList().add(type);
                                            continentList.get(i).setName("Switch" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                        }
                                    }
                                }
                            } else {
                                SwitchKml = new KmlDocument();
                                SwitchKml.parseGeoJSON(object.getJSONObject("switch_data2").toString());
                                KmlFeature.Styler styler = new SwitchKmlStyler(Color.BLACK, binding.map);
                                SwitchOverLay = (FolderOverlay) SwitchKml.mKmlRoot.buildOverlay(binding.map, null, styler, SwitchKml);

                                ArrayList<DType> list = new ArrayList<>();
                                DType type;
                                for (int i = 0; i < object.getJSONObject("switch_data2").getJSONArray("features").length(); i++) {
                                    type = new DType(object.getJSONObject("switch_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("switch_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType")));
                                    list.add(type);
                                    DeviceName deviceName = new DeviceName(object.getJSONObject("switch_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), object.getJSONObject("switch_data2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType"));
                                    mList.add(deviceName);
                                }
                                Continent continent = new Continent("Switch" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }
                        }

                        if (!object.getJSONObject("shunt_capacitor2").getJSONArray("features").toString().equals("[]")) {
                            if (ShuntCapacitorKml != null) {
                                ShuntCapacitorKml.parseGeoJSON(object.getJSONObject("shunt_capacitor2").toString());
                                KmlFeature.Styler styler = new ShuntCapacitorKmlStyler(Color.BLUE, binding.map);
                                FolderOverlay folderOverlay = (FolderOverlay) ShuntCapacitorKml.mKmlRoot.buildOverlay(binding.map, null, styler, ShuntCapacitorKml);
                                ShuntCapacitorOverLay.add(folderOverlay);

                                for (int i = 0; i < continentList.size(); i++) {
                                    if (continentList.get(i).getName().contains("Shunt Capacitor")) {
                                        for (int j = 0; j < object.getJSONObject("shunt_capacitor2").getJSONArray("features").length(); j++) {
                                            DType type = new DType(object.getJSONObject("shunt_capacitor2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("shunt_capacitor2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceType")));
                                            continentList.get(i).getDeviceList().add(type);
                                            continentList.get(i).setName("Shunt Capacitor" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                        }

                                    }
                                }
                            } else {
                                ShuntCapacitorKml = new KmlDocument();
                                ShuntCapacitorKml.parseGeoJSON(object.getJSONObject("shunt_capacitor2").toString());
                                KmlFeature.Styler styler = new ShuntCapacitorKmlStyler(Color.BLUE, binding.map);
                                ShuntCapacitorOverLay = (FolderOverlay) ShuntCapacitorKml.mKmlRoot.buildOverlay(binding.map, null, styler, ShuntCapacitorKml);

                                ArrayList<DType> list = new ArrayList<>();
                                DType type;
                                for (int i = 0; i < object.getJSONObject("shunt_capacitor2").getJSONArray("features").length(); i++) {
                                    type = new DType(object.getJSONObject("shunt_capacitor2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("shunt_capacitor2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType")));
                                    list.add(type);
                                    DeviceName deviceName = new DeviceName(object.getJSONObject("shunt_capacitor2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), object.getJSONObject("shunt_capacitor2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType"));
                                    mList.add(deviceName);
                                }
                                Continent continent = new Continent("Shunt Capacitor" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }
                        }

                        if (!object.getJSONObject("spotload2").getJSONArray("features").toString().equals("[]")) {
                            if (SpotloadKml != null) {
                                SpotloadKml.parseGeoJSON(object.getJSONObject("spotload2").toString());
                                KmlFeature.Styler styler = new SpotLoadKmlStyler(Color.BLUE, binding.map);
                                FolderOverlay folderOverlay = (FolderOverlay) SpotloadKml.mKmlRoot.buildOverlay(binding.map, null, styler, SpotloadKml);
                                SpotLoadOverLay.add(folderOverlay);

                                for (int i = 0; i < continentList.size(); i++) {
                                    if (continentList.get(i).getName().contains("SpotLoad")) {
                                        for (int j = 0; j < object.getJSONObject("spotload2").getJSONArray("features").length(); j++) {
                                            DType type = new DType(object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceType")));
                                            continentList.get(i).getDeviceList().add(type);
                                            continentList.get(i).setName("SpotLoad" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                        }
                                    }
                                }
                            } else {
                                SpotloadKml = new KmlDocument();
                                SpotloadKml.parseGeoJSON(object.getJSONObject("spotload2").toString());
                                KmlFeature.Styler styler = new SpotLoadKmlStyler(Color.BLUE, binding.map);
                                SpotLoadOverLay = (FolderOverlay) SpotloadKml.mKmlRoot.buildOverlay(binding.map, null, styler, SpotloadKml);

                                ArrayList<DType> list = new ArrayList<>();
                                DType type;
                                for (int i = 0; i < object.getJSONObject("spotload2").getJSONArray("features").length(); i++) {
                                    type = new DType(object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType")));
                                    list.add(type);
                                    DeviceName deviceName = new DeviceName(object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType"));
                                    mList.add(deviceName);
                                }
                                Continent continent = new Continent("SpotLoad" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }
                        }

                        //New Devicea Add

                        if (!object.getJSONObject("reclosuredev").getJSONArray("features").toString().equals("[]")) {
                            if (RecloserKml != null) {
                                RecloserKml.parseGeoJSON(object.getJSONObject("reclosuredev").toString());
                                KmlFeature.Styler styler = new RecloserKmlStyler(Color.BLACK, binding.map);
                                FolderOverlay folderOverlay = (FolderOverlay) RecloserKml.mKmlRoot.buildOverlay(binding.map, null, styler, RecloserKml);
                                RecloserOverLay.add(folderOverlay);
                                for (int i = 0; i < continentList.size(); i++) {
                                    if (continentList.get(i).getName().contains("Reclosure")) {
                                        for (int j = 0; j < object.getJSONObject("reclosuredev").getJSONArray("features").length(); j++) {
                                            DType type = new DType(object.getJSONObject("reclosuredev").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("reclosuredev").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceType")));
                                            continentList.get(i).getDeviceList().add(type);
                                            continentList.get(i).setName("Reclosure" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                        }
                                    }
                                }
                            } else {
                                RecloserKml = new KmlDocument();
                                RecloserKml.parseGeoJSON(object.getJSONObject("reclosuredev").toString());
                                KmlFeature.Styler styler = new RecloserKmlStyler(Color.BLACK, binding.map);
                                RecloserOverLay = (FolderOverlay) RecloserKml.mKmlRoot.buildOverlay(binding.map, null, styler, RecloserKml);

                                ArrayList<DType> list = new ArrayList<>();
                                DType type;
                                for (int i = 0; i < object.getJSONObject("reclosuredev").getJSONArray("features").length(); i++) {
                                    type = new DType(object.getJSONObject("reclosuredev").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("reclosuredev").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType")));
                                    list.add(type);
                                    DeviceName deviceName = new DeviceName(object.getJSONObject("reclosuredev").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), object.getJSONObject("reclosuredev").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType"));
                                    mList.add(deviceName);
                                }
                                Continent continent = new Continent("Reclosure" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }
                        }

                        if (!object.getJSONObject("photovoltaic").getJSONArray("features").toString().equals("[]")) {
                            if (PhotoVoltaicKml != null) {
                                PhotoVoltaicKml.parseGeoJSON(object.getJSONObject("photovoltaic").toString());
                                KmlFeature.Styler styler = new PhotoVoltaicKmlStyler(Color.BLACK, binding.map);
                                FolderOverlay folderOverlay = (FolderOverlay) PhotoVoltaicKml.mKmlRoot.buildOverlay(binding.map, null, styler, PhotoVoltaicKml);
                                PhotoVoltaicOverLay.add(folderOverlay);
                                for (int i = 0; i < continentList.size(); i++) {
                                    if (continentList.get(i).getName().contains("PhotoVoltic")) {
                                        for (int j = 0; j < object.getJSONObject("photovoltaic").getJSONArray("features").length(); j++) {
                                            DType type = new DType(object.getJSONObject("photovoltaic").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("photovoltaic").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceType")));
                                            continentList.get(i).getDeviceList().add(type);
                                            continentList.get(i).setName("PhotoVoltic" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                        }
                                    }
                                }
                            } else {
                                PhotoVoltaicKml = new KmlDocument();
                                PhotoVoltaicKml.parseGeoJSON(object.getJSONObject("photovoltaic").toString());
                                KmlFeature.Styler styler = new PhotoVoltaicKmlStyler(Color.BLACK, binding.map);
                                PhotoVoltaicOverLay = (FolderOverlay) PhotoVoltaicKml.mKmlRoot.buildOverlay(binding.map, null, styler, PhotoVoltaicKml);

                                ArrayList<DType> list = new ArrayList<>();
                                DType type;
                                for (int i = 0; i < object.getJSONObject("photovoltaic").getJSONArray("features").length(); i++) {
                                    type = new DType(object.getJSONObject("photovoltaic").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("photovoltaic").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType")));
                                    list.add(type);
                                    DeviceName deviceName = new DeviceName(object.getJSONObject("photovoltaic").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), object.getJSONObject("photovoltaic").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType"));
                                    mList.add(deviceName);
                                }
                                Continent continent = new Continent("PhotoVoltaic" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }
                        }

                        if (!object.getJSONObject("battery").getJSONArray("features").toString().equals("[]")) {
                            if (BatteryKml != null) {
                                BatteryKml.parseGeoJSON(object.getJSONObject("battery").toString());
                                KmlFeature.Styler styler = new BattryKmlStyler(Color.BLACK, binding.map);
                                FolderOverlay folderOverlay = (FolderOverlay) BatteryKml.mKmlRoot.buildOverlay(binding.map, null, styler, BatteryKml);
                                BatteryOverLay.add(folderOverlay);
                                for (int i = 0; i < continentList.size(); i++) {
                                    if (continentList.get(i).getName().contains("Battery")) {
                                        for (int j = 0; j < object.getJSONObject("battery").getJSONArray("features").length(); j++) {
                                            DType type = new DType(object.getJSONObject("battery").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("battery").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceType")));
                                            continentList.get(i).getDeviceList().add(type);
                                            continentList.get(i).setName("Battery" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                        }
                                    }
                                }
                            } else {
                                BatteryKml = new KmlDocument();
                                BatteryKml.parseGeoJSON(object.getJSONObject("battery").toString());
                                KmlFeature.Styler styler = new BattryKmlStyler(Color.BLACK, binding.map);
                                BatteryOverLay = (FolderOverlay) BatteryKml.mKmlRoot.buildOverlay(binding.map, null, styler, BatteryKml);

                                ArrayList<DType> list = new ArrayList<>();
                                DType type;
                                for (int i = 0; i < object.getJSONObject("battery").getJSONArray("features").length(); i++) {
                                    type = new DType(object.getJSONObject("battery").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("battery").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType")));
                                    list.add(type);
                                    DeviceName deviceName = new DeviceName(object.getJSONObject("battery").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), object.getJSONObject("battery").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType"));
                                    mList.add(deviceName);
                                }
                                Continent continent = new Continent("Battery" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }
                        }

                        if (!object.getJSONObject("wind").getJSONArray("features").toString().equals("[]")) {
                            if (WindKml != null) {
                                WindKml.parseGeoJSON(object.getJSONObject("wind").toString());
                                KmlFeature.Styler styler = new WindKmlStyler(Color.BLACK, binding.map);
                                FolderOverlay folderOverlay = (FolderOverlay) WindKml.mKmlRoot.buildOverlay(binding.map, null, styler, WindKml);
                                WindOverLay.add(folderOverlay);
                                for (int i = 0; i < continentList.size(); i++) {
                                    if (continentList.get(i).getName().contains("Wind")) {
                                        for (int j = 0; j < object.getJSONObject("wind").getJSONArray("features").length(); j++) {
                                            DType type = new DType(object.getJSONObject("wind").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("wind").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceType")));
                                            continentList.get(i).getDeviceList().add(type);
                                            continentList.get(i).setName("Wind" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                        }
                                    }
                                }
                            } else {
                                WindKml = new KmlDocument();
                                WindKml.parseGeoJSON(object.getJSONObject("wind").toString());
                                KmlFeature.Styler styler = new WindKmlStyler(Color.BLACK, binding.map);
                                WindOverLay = (FolderOverlay) WindKml.mKmlRoot.buildOverlay(binding.map, null, styler, WindKml);

                                ArrayList<DType> list = new ArrayList<>();
                                DType type;
                                for (int i = 0; i < object.getJSONObject("wind").getJSONArray("features").length(); i++) {
                                    type = new DType(object.getJSONObject("wind").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("wind").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType")));
                                    list.add(type);
                                    DeviceName deviceName = new DeviceName(object.getJSONObject("wind").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), object.getJSONObject("wind").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType"));
                                    mList.add(deviceName);
                                }
                                Continent continent = new Continent("Wind" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }
                        }

                        if (!object.getJSONObject("Reactor").getJSONArray("features").toString().equals("[]")) {
                            if (ReactorKml != null) {
                                ReactorKml.parseGeoJSON(object.getJSONObject("Reactor").toString());
                                KmlFeature.Styler styler = new ShuntReactorKmlStyler(Color.BLACK, binding.map);
                                FolderOverlay folderOverlay = (FolderOverlay) ReactorKml.mKmlRoot.buildOverlay(binding.map, null, styler, ReactorKml);
                                ReactorOverLay.add(folderOverlay);
                                for (int i = 0; i < continentList.size(); i++) {
                                    if (continentList.get(i).getName().contains("Reactor")) {
                                        for (int j = 0; j < object.getJSONObject("Reactor").getJSONArray("features").length(); j++) {
                                            DType type = new DType(object.getJSONObject("Reactor").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("Reactor").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceType")));
                                            continentList.get(i).getDeviceList().add(type);
                                            continentList.get(i).setName("Reactor" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                        }
                                    }
                                }
                            } else {
                                ReactorKml = new KmlDocument();
                                ReactorKml.parseGeoJSON(object.getJSONObject("Reactor").toString());
                                KmlFeature.Styler styler = new ShuntReactorKmlStyler(Color.BLACK, binding.map);
                                ReactorOverLay = (FolderOverlay) ReactorKml.mKmlRoot.buildOverlay(binding.map, null, styler, ReactorKml);

                                ArrayList<DType> list = new ArrayList<>();
                                DType type;
                                for (int i = 0; i < object.getJSONObject("Reactor").getJSONArray("features").length(); i++) {
                                    type = new DType(object.getJSONObject("Reactor").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("Reactor").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType")));
                                    list.add(type);
                                    DeviceName deviceName = new DeviceName(object.getJSONObject("Reactor").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), object.getJSONObject("Reactor").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType"));
                                    mList.add(deviceName);
                                }
                                Continent continent = new Continent("Reactor" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }
                        }

                      /*  if (!object.getJSONObject("battery").getJSONArray("features").toString().equals("[]")) {
                            if (SpotloadKml != null) {
                                SpotloadKml.parseGeoJSON(object.getJSONObject("battery").toString());
                                KmlFeature.Styler styler = new SpotLoadKmlStyler(Color.BLUE, binding.map);
                                FolderOverlay folderOverlay = (FolderOverlay) SpotloadKml.mKmlRoot.buildOverlay(binding.map, null, styler, SpotloadKml);
                                SpotLoadOverLay.add(folderOverlay);

                                for (int i = 0; i < continentList.size(); i++) {
                                    if (continentList.get(i).getName().contains("SpotLoad")) {
                                        for (int j = 0; j < object.getJSONObject("spotload2").getJSONArray("features").length(); j++) {
                                            DType type = new DType(object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceType")));
                                            continentList.get(i).getDeviceList().add(type);
                                            continentList.get(i).setName("SpotLoad" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                        }
                                    }
                                }
                            } else {
                                SpotloadKml = new KmlDocument();
                                SpotloadKml.parseGeoJSON(object.getJSONObject("spotload2").toString());
                                KmlFeature.Styler styler = new SpotLoadKmlStyler(Color.BLUE, binding.map);
                                SpotLoadOverLay = (FolderOverlay) SpotloadKml.mKmlRoot.buildOverlay(binding.map, null, styler, SpotloadKml);

                                ArrayList<DType> list = new ArrayList<>();
                                DType type;
                                for (int i = 0; i < object.getJSONObject("spotload2").getJSONArray("features").length(); i++) {
                                    type = new DType(object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType")));
                                    list.add(type);
                                    DeviceName deviceName = new DeviceName(object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType"));
                                    mList.add(deviceName);
                                }
                                Continent continent = new Continent("SpotLoad" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }
                        }

                        if (!object.getJSONObject("photovoltaic").getJSONArray("features").toString().equals("[]")) {
                            if (SpotloadKml != null) {
                                SpotloadKml.parseGeoJSON(object.getJSONObject("spotload2").toString());
                                KmlFeature.Styler styler = new SpotLoadKmlStyler(Color.BLUE, binding.map);
                                FolderOverlay folderOverlay = (FolderOverlay) SpotloadKml.mKmlRoot.buildOverlay(binding.map, null, styler, SpotloadKml);
                                SpotLoadOverLay.add(folderOverlay);

                                for (int i = 0; i < continentList.size(); i++) {
                                    if (continentList.get(i).getName().contains("SpotLoad")) {
                                        for (int j = 0; j < object.getJSONObject("spotload2").getJSONArray("features").length(); j++) {
                                            DType type = new DType(object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceType")));
                                            continentList.get(i).getDeviceList().add(type);
                                            continentList.get(i).setName("SpotLoad" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                        }
                                    }
                                }
                            } else {
                                SpotloadKml = new KmlDocument();
                                SpotloadKml.parseGeoJSON(object.getJSONObject("spotload2").toString());
                                KmlFeature.Styler styler = new SpotLoadKmlStyler(Color.BLUE, binding.map);
                                SpotLoadOverLay = (FolderOverlay) SpotloadKml.mKmlRoot.buildOverlay(binding.map, null, styler, SpotloadKml);

                                ArrayList<DType> list = new ArrayList<>();
                                DType type;
                                for (int i = 0; i < object.getJSONObject("spotload2").getJSONArray("features").length(); i++) {
                                    type = new DType(object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType")));
                                    list.add(type);
                                    DeviceName deviceName = new DeviceName(object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType"));
                                    mList.add(deviceName);
                                }
                                Continent continent = new Continent("SpotLoad" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }
                        }

                        if (!object.getJSONObject("reclosuredev").getJSONArray("features").toString().equals("[]")) {
                            if (SpotloadKml != null) {
                                SpotloadKml.parseGeoJSON(object.getJSONObject("spotload2").toString());
                                KmlFeature.Styler styler = new SpotLoadKmlStyler(Color.BLUE, binding.map);
                                FolderOverlay folderOverlay = (FolderOverlay) SpotloadKml.mKmlRoot.buildOverlay(binding.map, null, styler, SpotloadKml);
                                SpotLoadOverLay.add(folderOverlay);

                                for (int i = 0; i < continentList.size(); i++) {
                                    if (continentList.get(i).getName().contains("SpotLoad")) {
                                        for (int j = 0; j < object.getJSONObject("spotload2").getJSONArray("features").length(); j++) {
                                            DType type = new DType(object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("DeviceType")));
                                            continentList.get(i).getDeviceList().add(type);
                                            continentList.get(i).setName("SpotLoad" + " " + "(" + continentList.get(i).getDeviceList().size() + ")");
                                        }
                                    }
                                }
                            } else {
                                SpotloadKml = new KmlDocument();
                                SpotloadKml.parseGeoJSON(object.getJSONObject("spotload2").toString());
                                KmlFeature.Styler styler = new SpotLoadKmlStyler(Color.BLUE, binding.map);
                                SpotLoadOverLay = (FolderOverlay) SpotloadKml.mKmlRoot.buildOverlay(binding.map, null, styler, SpotloadKml);

                                ArrayList<DType> list = new ArrayList<>();
                                DType type;
                                for (int i = 0; i < object.getJSONObject("spotload2").getJSONArray("features").length(); i++) {
                                    type = new DType(object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), Integer.parseInt(object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType")));
                                    list.add(type);
                                    DeviceName deviceName = new DeviceName(object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber"), object.getJSONObject("spotload2").getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceType"));
                                    mList.add(deviceName);
                                }
                                Continent continent = new Continent("SpotLoad" + " " + "(" + list.size() + ")", list);
                                continentList.add(continent);
                            }
                        }*/


                        if (!object.getJSONObject("node_data2").getJSONArray("features").toString().equals("[]")) {
                            if (NodeKml != null) {
                                NodeKml.parseGeoJSON(object.getJSONObject("node_data2").toString());
                                KmlFeature.Styler styler = new NodeKmlStyler(Color.BLUE, binding.map, polylineMap);
                                FolderOverlay folderOverlay = (FolderOverlay) NodeKml.mKmlRoot.buildOverlay(binding.map, null, styler, NodeKml);
                                nodeOverLay.add(folderOverlay);
                            } else {
                                NodeKml = new KmlDocument();
                                NodeKml.parseGeoJSON(object.getJSONObject("node_data2").toString());
                                KmlFeature.Styler styler = new NodeKmlStyler(Color.BLUE, binding.map, polylineMap);
                                nodeOverLay = (FolderOverlay) NodeKml.mKmlRoot.buildOverlay(binding.map, null, styler, NodeKml);
                            }
                        }


                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (CableFolderOverLay != null) {
                return CableFolderOverLay;
            } else if (OverheadFolderOverLay != null) {
                return OverheadFolderOverLay;
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(FolderOverlay folderOverlay) {
            super.onPostExecute(folderOverlay);
            progressBarLayout.setVisibility(View.GONE);

            try {
                ArrayList<BoundingBox> bboxes = new ArrayList<>();

                if (CableKml != null && CableKml.mKmlRoot != null && CableKml.mKmlRoot.getBoundingBox() != null) {
                    bboxes.add(CableKml.mKmlRoot.getBoundingBox());
                }
                if (OverHeadKml != null && OverHeadKml.mKmlRoot != null && OverHeadKml.mKmlRoot.getBoundingBox() != null) {
                    bboxes.add(OverHeadKml.mKmlRoot.getBoundingBox());
                }
                if (sourceKml != null && sourceKml.mKmlRoot != null && sourceKml.mKmlRoot.getBoundingBox() != null) {
                    bboxes.add(sourceKml.mKmlRoot.getBoundingBox());
                }
                if (CableFolderOverLay != null) {
                    binding.map.getOverlays().add(CableFolderOverLay);
                }

                if (OverheadFolderOverLay != null) {
                    binding.map.getOverlays().add(OverheadFolderOverLay);
                }

                if (UnBalanceFolderOverLay != null) {
                    binding.map.getOverlays().add(UnBalanceFolderOverLay);
                }

                if (sectionFolderOverLay != null) {
                    binding.map.getOverlays().add(sectionFolderOverLay);
                }

                if (substationOverLay != null) {
                    binding.map.getOverlays().add(substationOverLay);

                }

                if (SourceOverLay != null) {
                    binding.map.getOverlays().add(SourceOverLay);
                }

                if (mList != null && !mList.isEmpty()) {
                    adapters = new FiltersAdapter(MapActivity.this, R.layout.activity_map, R.id.first_tv, mList);
                    binding.searchView.setAdapter(adapters);
                }

                if (continentList != null && !continentList.isEmpty()) {
                    adapter = new ExpandableDeviceAdapter(MapActivity.this, continentList);
                    binding.navigationmenu.setAdapter(adapter);
                }

                if (!intent.getStringExtra("Type").equals("NSC")) {
                    if (!isBounding) {
                        if (CableKml != null) {
                            BoundingBox boundingBox = CableKml.mKmlRoot.getBoundingBox();
                            mMapView.zoomToBoundingBox(boundingBox, true);
                            mMapView.getBoundingBox().getCenter();
                            mMapView.getController().setZoom(7.0);
                            mMapView.getController().setCenter(boundingBox.getCenter());
                            mMapView.zoomToBoundingBox(boundingBox.increaseByScale(1.3f), true);
                            isBounding = true;
                            mMapView.invalidate();
                        } else if (OverHeadKml != null) {
                            BoundingBox boundingBox = OverHeadKml.mKmlRoot.getBoundingBox();
                            mMapView.zoomToBoundingBox(boundingBox, true);
                            mMapView.getBoundingBox().getCenter();
                            mMapView.getController().setZoom(7.0);
                            mMapView.getController().setCenter(boundingBox.getCenter());
                            mMapView.zoomToBoundingBox(boundingBox.increaseByScale(1.3f), true);
                            isBounding = true;
                            mMapView.invalidate();
                        }
                    } else {
                        binding.map.invalidate();
                    }
                } else {
                    if (intent.getStringExtra("NearstConsumerNo") != null && !Objects.requireNonNull(intent.getStringExtra("NearstConsumerNo")).isEmpty() && intent.getStringExtra("DeviceType") != null && !Objects.requireNonNull(intent.getStringExtra("DeviceType")).isEmpty()) {
                        getDeviceLocation(intent.getStringExtra("NearstConsumerNo"), "42", prefManager.getUserType());
                    }
                }

                if (!selectedFeeder.isEmpty()) {
                    getNetworkData(selectedFeeder.get(0));
                }

                if (!isTopology) {
                    if (intent.getStringArrayListExtra("NetworkId") != null) {
                        JsonArray jsonArray = new Gson().toJsonTree(intent.getStringArrayListExtra("NetworkId")).getAsJsonArray();
                        getTopology(jsonArray);
                        isTopology = true;
                    }
                }

                if (!intent.getStringExtra("Type").equals("NSC")) {
                    if (intent.getStringExtra("NearstConsumerNo") != null && !Objects.requireNonNull(intent.getStringExtra("NearstConsumerNo")).isEmpty() && intent.getStringExtra("DeviceType") != null && !Objects.requireNonNull(intent.getStringExtra("DeviceType")).isEmpty()) {
                        getDevices(intent.getStringExtra("NearstConsumerNo"), Objects.requireNonNull(intent.getStringExtra("DeviceType")), prefManager.getUserType());
                    }
                }


                /*if (!bboxes.isEmpty()) {
                    BoundingBox dataBounds = new BoundingBox(
                            bboxes.get(0).getLatNorth(),
                            bboxes.get(0).getLonEast(),
                            bboxes.get(0).getLatSouth(),
                            bboxes.get(0).getLonWest()
                    );

                    binding.map.setScrollableAreaLimitDouble(dataBounds.increaseByScale(3.0f));

                    binding.map.zoomToBoundingBox(dataBounds.increaseByScale(1.2f), true);

                    binding.map.setMinZoomLevel(10.0);
                    binding.map.setMaxZoomLevel(29.0);

                    binding.map.invalidate();
                }*/

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    private void updateContinentList(String deviceNumber, String deviceType) {
        String continentName = getContinentNameForDeviceType(deviceType);
        if (continentName == null) {
            Log.e("MapActivity", "No continent found for device type: " + deviceType);
            return;
        }

        for (Continent continent : continentList) {
            if (continent.getName().contains(continentName)) {
                Iterator<DType> iterator = continent.getDeviceList().iterator();
                while (iterator.hasNext()) {
                    DType dType = iterator.next();
                    if (dType.getName().equals(deviceNumber)) {
                        iterator.remove();
                        break;
                    }
                }
                continent.setName(continentName + " (" + continent.getDeviceList().size() + ")");
                break;
            }
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private String getContinentNameForDeviceType(String deviceType) {
        switch (deviceType) {
            case "1":
                return "Cable";
            case "2":
                return "Overhead Balance";
            case "3":
                return "Unbalance";
            case "8":
                return "Breaker";
            case "5":
                return "Two-Winding Transformer";
            case "14":
                return "Fuse";
            case "13":
                return "Switch";
            case "17":
                return "Shunt Capacitor";
            case "20":
                return "SpotLoad";
            case "43":
                return "Feeder ID";
            default:
                return null;
        }
    }

    private void loadSubstation(JSONObject jsonObject) {
        try {
            Polygon polygon = new Polygon();
            List<GeoPoint> coordinatesPoint = new ArrayList<>();
            polygon.setSubDescription(Polygon.class.getCanonicalName());
            polygon.setFillColor(0x12121212);
            polygon.setVisible(true);
            polygon.setStrokeColor(Color.BLUE);
            polygon.setStrokeWidth(4);

            for (int i = 0; i < jsonObject.getJSONArray("features").getJSONObject(0).getJSONObject("geometry").getJSONArray("coordinates").length(); i++) {
                double x = (double) jsonObject.getJSONArray("features").getJSONObject(0).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(i).get(1);
                double y = (double) jsonObject.getJSONArray("features").getJSONObject(0).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(i).get(0);
                coordinatesPoint.add(new GeoPoint(x, y));
            }

            addNewSections(coordinatesPoint);

        } catch (Exception e) {
            e.getLocalizedMessage();
        }

    }

    public void clearBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = fragmentManager.getBackStackEntryAt(0);
            fragmentManager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    private void CancelAnalysis() {
        if (CaPolylineList != null && !CaPolylineList.isEmpty()) {
            for (int j = 0; j < CaPolylineList.size(); j++) {
                CaPolylineList.get(j).getPaint().setColor(Color.RED);
            }
        }

        if (ohPolylineList != null && !ohPolylineList.isEmpty()) {
            for (int j = 0; j < ohPolylineList.size(); j++) {
                ohPolylineList.get(j).getPaint().setColor(Color.BLUE);
            }
        }

        if (unBalPolylineList != null && !unBalPolylineList.isEmpty()) {
            for (int j = 0; j < unBalPolylineList.size(); j++) {
                unBalPolylineList.get(j).getPaint().setColor(Color.BLACK);
            }
        }

        if (breakerList != null && !breakerList.isEmpty()) {
            for (int i = 0; i < breakerList.size(); i++) {
                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                Bitmap bitmap = drawableToBitmap(drawable);
                breakerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 95)));
            }
        }

        if (transformerList != null && !transformerList.isEmpty()) {
            for (int i = 0; i < transformerList.size(); i++) {
                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                Bitmap bitmap = drawableToBitmap(drawable);
                transformerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 93)));
            }
        }

        if (fuseList != null && !fuseList.isEmpty()) {
            for (int i = 0; i < fuseList.size(); i++) {
                Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                fuseList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 0)));
            }
        }

        if (switchedList != null && !switchedList.isEmpty()) {
            for (int i = 0; i < switchedList.size(); i++) {
                Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                int paddingLeft = 0; // Left padding in pixels
                int paddingTop = 0; // Top padding in pixels
                int paddingRight = 0; // Right padding in pixels
                int paddingBottom = 12; // Bottom padding in pixels
                int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                Canvas canva = new Canvas(paddedBitmap);
                canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                switchedList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.BLACK), 0, 0)));
            }
        }

        if (capacitorList != null && !capacitorList.isEmpty()) {
            for (int i = 0; i < capacitorList.size(); i++) {
                capacitorList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.BLACK), 90, 0)));
            }
        }

        if (spotLoadList != null && !spotLoadList.isEmpty()) {
            for (int i = 0; i < spotLoadList.size(); i++) {
                int paddingPx = 0;
                Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                        BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                Canvas canvas1 = new Canvas(paddedBitmap);
                canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                spotLoadList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.BLACK), 0, 0)));
            }
        }
        progressBarLayout.setVisibility(View.GONE);
        binding.map.invalidate();
    }

    private void LoadFlowAnalysis(JsonObject jsonObject) {
        progressBarLayout.setProcessText("Load-Flow Analysis Run...");
        progressBarLayout.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
                    Call<LoadFlowModel> call = apiInterface.LoadFlow("Bearer " + prefManager.getAccessToken(), jsonObject);
                    call.enqueue(new Callback<LoadFlowModel>() {
                        @Override
                        public void onResponse(@NonNull Call<LoadFlowModel> call, @NonNull Response<LoadFlowModel> response) {
                            if (response.code() == 200) {
                                ErrorPdfLogger.logAnalysis(MapActivity.this, "POST", "loadflow/", "Success:" + response.code() + " LoadFlowAnalysis: " + response.message()
                                        + "jsonObject : " + jsonObject
                                        + "CYMDBNET : " + prefManager.getDBName()
                                        + "AccessToken : " +prefManager.getAccessToken());
                                try {
                                    binding.reportBtn.setVisibility(View.GONE);
                                    progressBarLayout.setVisibility(View.GONE);
                                    LoadFlowModel loadFlowModel = response.body();
                                    assert loadFlowModel != null;
                                    if (loadFlowModel.getOutput().getStatus().contains("Success")) {
                                        overVolatgeCount = loadFlowModel.getOutput().getOverVoltage().size();
                                        underVolatgeCount = loadFlowModel.getOutput().getUndervoltage().size();
                                        overLoadCount = loadFlowModel.getOutput().getOverload().size();

                                        if (CaPolylineList != null && !CaPolylineList.isEmpty()) {
                                            for (int j = 0; j < CaPolylineList.size(); j++) {
                                                CaPolylineList.get(j).getPaint().setColor(Color.BLACK);
                                            }
                                        }

                                        if (ohPolylineList != null && !ohPolylineList.isEmpty()) {
                                            for (int j = 0; j < ohPolylineList.size(); j++) {
                                                ohPolylineList.get(j).getPaint().setColor(Color.BLACK);
                                            }
                                        }

                                        if (unBalPolylineList != null && !unBalPolylineList.isEmpty()) {
                                            for (int j = 0; j < unBalPolylineList.size(); j++) {
                                                unBalPolylineList.get(j).getPaint().setColor(Color.BLACK);
                                            }
                                        }
                                        new LoadFlowAnalysis(response.body()).execute();
                                        List<LoadFlowModel.Output.Message> messageList = loadFlowModel.getOutput().getMessage();
                                        if (messageList != null && !messageList.isEmpty()) {
                                            runOnUiThread(() -> {
                                                LinearLayout logPanel = findViewById(R.id.log_panel);
                                                LinearLayout tableLayout = findViewById(R.id.tableLayout);
                                                ImageView closeBtn = findViewById(R.id.closeBtn);

                                                logPanel.setVisibility(View.VISIBLE);
                                                tableLayout.removeAllViews();
                                                closeBtn.setOnClickListener(v -> logPanel.setVisibility(View.GONE));

                                                for (LoadFlowModel.Output.Message msg : messageList) {
                                                    TableRow row = new TableRow(MapActivity.this);

                                                    TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(
                                                            TableLayout.LayoutParams.MATCH_PARENT,
                                                            TableLayout.LayoutParams.WRAP_CONTENT
                                                    );
                                                    rowParams.setMargins(0, 1, 0, 0);
                                                    row.setLayoutParams(rowParams);

                                                    row.addView(createTextView(msg.getSeverity()));
                                                    row.addView(createTextView(String.valueOf(msg.getCode())));
                                                    row.addView(createTextView(msg.getText()));

                                                    tableLayout.addView(row);
                                                }

                                            });
                                        }

                                    } else {
                                        ErrorPdfLogger.logAnalysis(MapActivity.this, "POST", "loadflow/", "Error:" + response.code() + " LoadFlowAnalysis: " + response.message()
                                                + "jsonObject : " + jsonObject
                                                + "CYMDBNET : " + prefManager.getDBName()
                                                + "AccessToken : " +prefManager.getAccessToken());
                                        Snackbar.make(binding.getRoot(), "There is a technical issue. Please check the license.", Snackbar.LENGTH_SHORT)
                                                .setAction("Retry", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        LoadFlowAnalysis(jsonObject);
                                                    }
                                                }).show();
                                    }

                                } catch (Exception e) {
                                    ErrorPdfLogger.logAnalysis(MapActivity.this,e);
                                    Config.isLoadFlow = false;
                                    binding.reportBtn.setVisibility(View.GONE);
                                    progressBarLayout.setVisibility(View.GONE);
                                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                                }
                            } else {
                                ErrorPdfLogger.logAnalysis(MapActivity.this, "POST", "loadflow/", "Error:" + response.code() + " LoadFlowAnalysis: " + response.message()
                                        + "jsonObject : " + jsonObject
                                        + "CYMDBNET : " + prefManager.getDBName()
                                        + "AccessToken : " +prefManager.getAccessToken());
                                Config.isLoadFlow = false;
                                binding.reportBtn.setVisibility(View.GONE);
                                progressBarLayout.setVisibility(View.GONE);
                                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), response.message() + " - " + response.code(), Snackbar.LENGTH_SHORT);
                                snack.show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<LoadFlowModel> call, @NonNull Throwable t) {
                            ErrorPdfLogger.logAnalysis(MapActivity.this, "POST", "loadflow/", Log.getStackTraceString(t));
                            Config.isLoadFlow = false;
                            binding.reportBtn.setVisibility(View.GONE);
                            progressBarLayout.setVisibility(View.GONE);
                            Snackbar.make(binding.getRoot(), getString(R.string.error_msg), Snackbar.LENGTH_SHORT)
                                    .setAction("Retry", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            LoadFlowAnalysis(jsonObject);
                                        }
                                    }).show();
                        }
                    });
                } catch (Exception e) {
                    progressBarLayout.setVisibility(View.GONE);
                    Log.d("Exception", Objects.requireNonNull(e.getLocalizedMessage()));
                }
            }
        }).start();
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(16, 8, 16, 8);
        textView.setBackgroundResource(R.drawable.lf_bg);
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        textView.setLayoutParams(params);
        textView.setTextSize(12);
        textView.setTextColor(Color.BLACK);
        textView.setTypeface(null, Typeface.NORMAL);
        textView.setHorizontallyScrolling(true);
        return textView;
    }

    @SuppressLint("SetTextI18n")
    private void ShortCircuitAnalysis(JsonObject jsonObject) {
        progressBarLayout.setProcessText("Short Circuit Analysis Run..");
        progressBarLayout.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<ShortCircuitModel> call = apiInterface.ShortCircuit("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<ShortCircuitModel>() {
            @Override
            public void onResponse(@NonNull Call<ShortCircuitModel> call, @NonNull Response<ShortCircuitModel> response) {
                if (response.code() == 200) {
                    ErrorPdfLogger.logAnalysis(MapActivity.this, "POST", "shortcircuit/", "Success" + response.code() + " ShortCircuitAnalysis: " + response.message()
                            + "jsonObject : " + jsonObject
                            + "CYMDBNET : " + prefManager.getDBName()
                            + "AccessToken : " +prefManager.getAccessToken());
                    try {
                        progressBarLayout.setVisibility(View.GONE);
                        ShortCircuitModel shortCircuitModel = response.body();
                        assert shortCircuitModel != null;
                        if (shortCircuitModel.getOutput().getStatus().contains("Success")) {
                            overVolatgeCount = shortCircuitModel.getOutput().getOverVoltage().size();
                            underVolatgeCount = shortCircuitModel.getOutput().getUndervoltage().size();
                            overLoadCount = shortCircuitModel.getOutput().getOverload().size();
                            ratingCount = shortCircuitModel.getOutput().getShortCircuitRating().size();

                            if (CaPolylineList != null && !CaPolylineList.isEmpty()) {
                                for (int j = 0; j < CaPolylineList.size(); j++) {
                                    CaPolylineList.get(j).getPaint().setColor(Color.BLACK);
                                }
                            }

                            if (ohPolylineList != null && !ohPolylineList.isEmpty()) {
                                for (int j = 0; j < ohPolylineList.size(); j++) {
                                    ohPolylineList.get(j).getPaint().setColor(Color.BLACK);
                                }
                            }

                            if (unBalPolylineList != null && !unBalPolylineList.isEmpty()) {
                                for (int j = 0; j < unBalPolylineList.size(); j++) {
                                    unBalPolylineList.get(j).getPaint().setColor(Color.BLACK);
                                }
                            }

                            new ShortCircuitAnalysis(response.body()).execute();
                            List<ShortCircuitModel.Output.Message> messageList = shortCircuitModel.getOutput().getMessage();
                            if (messageList != null && !messageList.isEmpty()) {
                                runOnUiThread(() -> {
                                    LinearLayout logPanel = findViewById(R.id.log_panel);
                                    LinearLayout tableLayout = findViewById(R.id.tableLayout);
                                    ImageView closeBtn = findViewById(R.id.closeBtn);

                                    logPanel.setVisibility(View.VISIBLE);
                                    tableLayout.removeAllViews();
                                    closeBtn.setOnClickListener(v -> logPanel.setVisibility(View.GONE));

                                    for (ShortCircuitModel.Output.Message msg : messageList) {
                                        TableRow row = new TableRow(MapActivity.this);

                                        TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(
                                                TableLayout.LayoutParams.MATCH_PARENT,
                                                TableLayout.LayoutParams.WRAP_CONTENT
                                        );
                                        rowParams.setMargins(0, 1, 0, 0);
                                        row.setLayoutParams(rowParams);

                                        row.addView(createTextView(msg.getSeverity()));
                                        row.addView(createTextView(String.valueOf(msg.getCode())));
                                        row.addView(createTextView(msg.getMessages()));

                                        tableLayout.addView(row);
                                    }

                                });
                            }
                        } else {
                            ErrorPdfLogger.logAnalysis(MapActivity.this, "POST", "shortcircuit/", "Error:" + response.code() + " ShortCircutAnalysis: " + response.message()
                                    + "jsonObject : " + jsonObject
                                    + "CYMDBNET : " + prefManager.getDBName()
                                    + "AccessToken : " +prefManager.getAccessToken());
                            Snackbar.make(binding.getRoot(), "There is a technical issue. Please check the license.", Snackbar.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        ErrorPdfLogger.logAnalysis(MapActivity.this,e);
                        progressBarLayout.setVisibility(View.GONE);
                        Log.d("Exception", e.getLocalizedMessage());
                    }
                } else {
                    progressBarLayout.setVisibility(View.GONE);
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), response.message() + " - " + response.code(), Snackbar.LENGTH_LONG);
                    snack.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ShortCircuitModel> call, @NonNull Throwable t) {
                ErrorPdfLogger.logAnalysis(MapActivity.this, "POST", "shortcircuit/ (Failure)", Log.getStackTraceString(t));
                progressBarLayout.setVisibility(View.GONE);
                Snackbar.make(binding.getRoot(), getString(R.string.error_msg), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ShortCircuitAnalysis(jsonObject);
                            }
                        }).show();
            }
        });
    }

    private void LoadAllocationAnalysis(JsonObject jsonObject) {
        progressBarLayout.setProcessText("Load Allocation Analysis Run...");
        progressBarLayout.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        Call<LoadAllocationModel> call = apiInterface.LoadAllocation("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<LoadAllocationModel>() {
            @Override
            public void onResponse(@NonNull Call<LoadAllocationModel> call, @NonNull Response<LoadAllocationModel> response) {
                if (response.code() == 200) {
                    ErrorPdfLogger.logAnalysis(MapActivity.this, "POST", "loadallocation/", "Success " + response.code() + " LoadAllocationAnalysis: " + response.message()
                            + "jsonObject : " + jsonObject
                            + "CYMDBNET : " + prefManager.getDBName()
                            + "AccessToken : " +prefManager.getAccessToken());
                    progressBarLayout.setVisibility(View.GONE);
                    if (CaPolylineList != null && !CaPolylineList.isEmpty()) {
                        for (int j = 0; j < CaPolylineList.size(); j++) {
                            CaPolylineList.get(j).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (ohPolylineList != null && !ohPolylineList.isEmpty()) {
                        for (int j = 0; j < ohPolylineList.size(); j++) {
                            ohPolylineList.get(j).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (unBalPolylineList != null && !unBalPolylineList.isEmpty()) {
                        for (int j = 0; j < unBalPolylineList.size(); j++) {
                            unBalPolylineList.get(j).getPaint().setColor(Color.BLACK);
                        }
                    }
                    new LoadAllocationAnalysis(response.body()).execute();

                } else {
                    ErrorPdfLogger.logAnalysis(MapActivity.this, "POST", "loadallocation/", "Error: " + response.code() + " LoadAllocationAnalysis: " + response.message()
                            + "jsonObject : " + jsonObject
                            + "CYMDBNET : " + prefManager.getDBName()
                            + "AccessToken : " +prefManager.getAccessToken());
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), response.message() + " - " + response.code(), Snackbar.LENGTH_LONG);
                    snack.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoadAllocationModel> call, @NonNull Throwable t) {
                ErrorPdfLogger.logAnalysis(MapActivity.this, "POST", "loadallocation/", Log.getStackTraceString(t));
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_msg), Snackbar.LENGTH_LONG);
                snack.show();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadFlowAnalysis extends AsyncTask<Void, Void, String> {

        private LoadFlowModel loadFlowModel;

        public LoadFlowAnalysis(LoadFlowModel loadFlowModel) {
            this.loadFlowModel = loadFlowModel;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarLayout.setVisibility(View.VISIBLE);
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(Void... voids) {
            String Status = null;
            try {
                Status = "";
                if (loadFlowModel.getOutput().getOverloadColor() != null) {
                    overloadColors = loadFlowModel.getOutput().getOverloadColor();
                }

                if (loadFlowModel.getOutput().getOverVoltageColor() != null) {
                    overVoltageColors = loadFlowModel.getOutput().getOverVoltageColor();
                }

                if (loadFlowModel.getOutput().getUndervoltageColor() != null) {
                    underVoltageColors = loadFlowModel.getOutput().getUndervoltageColor();
                }

                Config.isLoadFlow = true;
                Config.isShortCircuit = false;
                Config.isLoadAllocation = false;

                if (loadFlowModel.getOutput().getOverVoltage() != null && !loadFlowModel.getOutput().getOverVoltage().isEmpty()) {
                    Config.isLoadFlow = true;
                    Config.isShortCircuit = false;
                    Config.isLoadAllocation = false;
                    Status = "Load Flow Complete!";
                    for (int i = 0; i < loadFlowModel.getOutput().getOverVoltage().size(); i++) {
                        loadFlowOverVoltageSectionId.add(loadFlowModel.getOutput().getOverVoltage().get(i).getId());
                        if (CaSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId()) != null) {
                            Objects.requireNonNull(CaSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId())).getPaint().setColor(Color.parseColor(loadFlowModel.getOutput().getOverVoltageColor()));
                            loadFlowOverVoltageSectionID.put(loadFlowModel.getOutput().getOverVoltage().get(i).getId(), CaSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId()));
                        }

                        if (OhSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId()) != null) {
                            Objects.requireNonNull(OhSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId())).getPaint().setColor(Color.parseColor(loadFlowModel.getOutput().getOverVoltageColor()));
                            loadFlowOverVoltageSectionID.put(loadFlowModel.getOutput().getOverVoltage().get(i).getId(), OhSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId()));
                        }

                        if (UnBalSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId()) != null) {
                            Objects.requireNonNull(UnBalSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId())).getPaint().setColor(Color.parseColor(loadFlowModel.getOutput().getOverVoltageColor()));
                            loadFlowOverVoltageSectionID.put(loadFlowModel.getOutput().getOverVoltage().get(i).getId(), UnBalSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId()));
                        }

                        if (breakerSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId()) != null) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            Objects.requireNonNull(breakerSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(loadFlowModel.getOutput().getOverVoltageColor())), 90, 0)));
                            loadFlowOverVoltageDeviceID.put(loadFlowModel.getOutput().getOverVoltage().get(i).getId(), breakerSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId()));
                        }

                        if (transformerSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId()) != null) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            Objects.requireNonNull(transformerSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(loadFlowModel.getOutput().getOverVoltageColor())), 0, 90)));
                            loadFlowOverVoltageDeviceID.put(loadFlowModel.getOutput().getOverVoltage().get(i).getId(), transformerSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId()));
                        }

                        if (fuseSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId()) != null) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                            Objects.requireNonNull(fuseSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(loadFlowModel.getOutput().getOverVoltageColor())), 0, 90)));
                            loadFlowOverVoltageDeviceID.put(loadFlowModel.getOutput().getOverVoltage().get(i).getId(), fuseSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId()));
                        }

                        if (switchSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId()) != null) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                            int paddingLeft = 0; // Left padding in pixels
                            int paddingTop = 0; // Top padding in pixels
                            int paddingRight = 0; // Right padding in pixels
                            int paddingBottom = 12; // Bottom padding in pixels
                            int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                            int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                            Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                            Canvas canva = new Canvas(paddedBitmap);
                            canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                            Objects.requireNonNull(switchSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(loadFlowModel.getOutput().getOverVoltageColor())), 90, 0)));
                            loadFlowOverVoltageDeviceID.put(loadFlowModel.getOutput().getOverVoltage().get(i).getId(), switchSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId()));
                        }

                        if (capacitorSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId()) != null) {
                            Objects.requireNonNull(capacitorSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(loadFlowModel.getOutput().getOverVoltageColor())), 90, 0)));
                            loadFlowOverVoltageDeviceID.put(loadFlowModel.getOutput().getOverVoltage().get(i).getId(), capacitorSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId()));
                        }

                        if (spotloadSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId()) != null) {
                            if (spLineSectionList.contains(loadFlowModel.getOutput().getOverVoltage().get(i).getId())) {
                                int paddingPx = 0;
                                Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                        BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                                Canvas canvas1 = new Canvas(paddedBitmap);
                                canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                                Objects.requireNonNull(spotloadSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(loadFlowModel.getOutput().getOverVoltageColor())), 0, 70)));
                                loadFlowOverVoltageDeviceID.put(loadFlowModel.getOutput().getOverVoltage().get(i).getId(), spotloadSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId()));
                            } else {
                                Objects.requireNonNull(spotloadSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(loadFlowModel.getOutput().getOverVoltageColor())), 0, 35)));
                                loadFlowOverVoltageDeviceID.put(loadFlowModel.getOutput().getOverVoltage().get(i).getId(), spotloadSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId()));
                            }
                        }

                        if (!secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId()) != null) {
                                Objects.requireNonNull(secNodeSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId())).getPaint().setColor(Color.parseColor(loadFlowModel.getOutput().getOverVoltageColor()));
                                loadFlowOverVoltageSectionID.put(loadFlowModel.getOutput().getOverVoltage().get(i).getId(), secNodeSectionId.get(loadFlowModel.getOutput().getOverVoltage().get(i).getId()));
                            }
                        }
                    }
                } else {
                    Status = "No Data";
                }

                if (loadFlowModel.getOutput().getUndervoltage() != null && !loadFlowModel.getOutput().getUndervoltage().isEmpty()) {
                    Config.isLoadFlow = true;
                    Config.isShortCircuit = false;
                    Config.isLoadAllocation = false;
                    Status = "Load Flow Complete!";
                    for (int i = 0; i < loadFlowModel.getOutput().getUndervoltage().size(); i++) {
                        loadFlowUnderVoltageSectionId.add(loadFlowModel.getOutput().getUndervoltage().get(i).getId());
                        if (CaSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId()) != null) {
                            Objects.requireNonNull(CaSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId())).getPaint().setColor(Color.parseColor(loadFlowModel.getOutput().getUndervoltageColor()));
                            loadFlowUnderVoltageSectionID.put(loadFlowModel.getOutput().getUndervoltage().get(i).getId(), CaSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId()));
                        }

                        if (OhSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId()) != null) {
                            Objects.requireNonNull(OhSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId())).getPaint().setColor(Color.parseColor(loadFlowModel.getOutput().getUndervoltageColor()));
                            loadFlowUnderVoltageSectionID.put(loadFlowModel.getOutput().getUndervoltage().get(i).getId(), OhSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId()));
                        }

                        if (UnBalSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId()) != null) {
                            Objects.requireNonNull(UnBalSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId())).getPaint().setColor(Color.parseColor(loadFlowModel.getOutput().getUndervoltageColor()));
                            loadFlowUnderVoltageSectionID.put(loadFlowModel.getOutput().getUndervoltage().get(i).getId(), UnBalSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId()));
                        }

                        if (breakerSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId()) != null) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            Objects.requireNonNull(breakerSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(loadFlowModel.getOutput().getUndervoltageColor())), 90, 0)));
                            loadFlowUnderVoltageDeviceID.put(loadFlowModel.getOutput().getUndervoltage().get(i).getId(), breakerSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId()));
                        }

                        if (transformerSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId()) != null) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            Objects.requireNonNull(transformerSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(loadFlowModel.getOutput().getUndervoltageColor())), 0, 90)));
                            loadFlowUnderVoltageDeviceID.put(loadFlowModel.getOutput().getUndervoltage().get(i).getId(), transformerSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId()));
                        }

                        if (fuseSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId()) != null) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                            Objects.requireNonNull(fuseSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(loadFlowModel.getOutput().getUndervoltageColor())), 0, 90)));
                            loadFlowUnderVoltageDeviceID.put(loadFlowModel.getOutput().getUndervoltage().get(i).getId(), fuseSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId()));
                        }

                        if (switchSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId()) != null) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                            int paddingLeft = 0; // Left padding in pixels
                            int paddingTop = 0; // Top padding in pixels
                            int paddingRight = 0; // Right padding in pixels
                            int paddingBottom = 12; // Bottom padding in pixels
                            int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                            int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                            Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                            Canvas canva = new Canvas(paddedBitmap);
                            canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                            Objects.requireNonNull(switchSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(loadFlowModel.getOutput().getUndervoltageColor())), 90, 0)));
                            loadFlowUnderVoltageDeviceID.put(loadFlowModel.getOutput().getUndervoltage().get(i).getId(), switchSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId()));
                        }

                        if (capacitorSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId()) != null) {
                            Objects.requireNonNull(capacitorSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(loadFlowModel.getOutput().getUndervoltageColor())), 90, 0)));
                            loadFlowUnderVoltageDeviceID.put(loadFlowModel.getOutput().getUndervoltage().get(i).getId(), capacitorSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId()));
                        }

                        if (spotloadSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId()) != null) {
                            if (spLineSectionList.contains(loadFlowModel.getOutput().getUndervoltage().get(i).getId())) {
                                int paddingPx = 0;
                                Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                        BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                                Canvas canvas1 = new Canvas(paddedBitmap);
                                canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                                Objects.requireNonNull(spotloadSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(loadFlowModel.getOutput().getUndervoltageColor())), 0, 0)));
                                loadFlowUnderVoltageDeviceID.put(loadFlowModel.getOutput().getUndervoltage().get(i).getId(), spotloadSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId()));
                            } else {
                                Objects.requireNonNull(spotloadSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(loadFlowModel.getOutput().getUndervoltageColor())), 0, 70)));
                                loadFlowUnderVoltageDeviceID.put(loadFlowModel.getOutput().getUndervoltage().get(i).getId(), spotloadSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId()));
                            }
                        }

                        if (!secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId()) != null) {
                                Objects.requireNonNull(secNodeSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId())).getPaint().setColor(Color.parseColor(loadFlowModel.getOutput().getUndervoltageColor()));
                                loadFlowUnderVoltageSectionID.put(loadFlowModel.getOutput().getUndervoltage().get(i).getId(), secNodeSectionId.get(loadFlowModel.getOutput().getUndervoltage().get(i).getId()));
                            }
                        }

                    }
                } else {
                    Status = "No Data";
                }

                if (loadFlowModel.getOutput().getOverload() != null && loadFlowModel.getOutput().getOverload().size() > 0) {
                    Config.isLoadFlow = true;
                    Config.isShortCircuit = false;
                    Config.isLoadAllocation = false;
                    Status = "Load Flow Complete!";
                    for (int i = 0; i < loadFlowModel.getOutput().getOverload().size(); i++) {
                        loadFlowOverLoadSectionId.add(loadFlowModel.getOutput().getOverload().get(i).getId());
                        if (CaSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId()) != null) {
                            Objects.requireNonNull(CaSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId())).getPaint().setColor(Color.parseColor(loadFlowModel.getOutput().getOverloadColor()));
                            loadFlowOverLoadSectionID.put(loadFlowModel.getOutput().getOverload().get(i).getId(), CaSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId()));
                        }

                        if (OhSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId()) != null) {
                            Objects.requireNonNull(OhSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId())).getPaint().setColor(Color.parseColor(loadFlowModel.getOutput().getOverloadColor()));
                            loadFlowOverLoadSectionID.put(loadFlowModel.getOutput().getOverload().get(i).getId(), OhSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId()));
                        }

                        if (UnBalSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId()) != null) {
                            Objects.requireNonNull(UnBalSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId())).getPaint().setColor(Color.parseColor(loadFlowModel.getOutput().getOverloadColor()));
                            loadFlowOverLoadSectionID.put(loadFlowModel.getOutput().getOverload().get(i).getId(), UnBalSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId()));
                        }

                        if (breakerSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId()) != null) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            Objects.requireNonNull(breakerSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(loadFlowModel.getOutput().getOverloadColor())), 90, 0)));
                            loadFlowOverLoadDeviceID.put(loadFlowModel.getOutput().getOverload().get(i).getId(), breakerSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId()));
                        }

                        if (transformerSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId()) != null) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            Objects.requireNonNull(transformerSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(loadFlowModel.getOutput().getOverloadColor())), 0, 90)));
                            loadFlowOverLoadDeviceID.put(loadFlowModel.getOutput().getOverload().get(i).getId(), transformerSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId()));
                        }

                        if (fuseSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId()) != null) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                            Objects.requireNonNull(fuseSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(loadFlowModel.getOutput().getOverloadColor())), 0, 90)));
                            loadFlowOverLoadDeviceID.put(loadFlowModel.getOutput().getOverload().get(i).getId(), fuseSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId()));
                        }

                        if (switchSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId()) != null) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                            int paddingLeft = 0; // Left padding in pixels
                            int paddingTop = 0; // Top padding in pixels
                            int paddingRight = 0; // Right padding in pixels
                            int paddingBottom = 12; // Bottom padding in pixels
                            int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                            int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                            Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                            Canvas canva = new Canvas(paddedBitmap);
                            canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                            Objects.requireNonNull(switchSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(loadFlowModel.getOutput().getOverloadColor())), 90, 0)));
                            loadFlowOverLoadDeviceID.put(loadFlowModel.getOutput().getOverload().get(i).getId(), switchSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId()));
                        }

                        if (capacitorSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId()) != null) {
                            Objects.requireNonNull(capacitorSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(loadFlowModel.getOutput().getOverloadColor())), 90, 0)));
                            loadFlowOverLoadDeviceID.put(loadFlowModel.getOutput().getOverload().get(i).getId(), capacitorSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId()));
                        }

                        if (spotloadSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId()) != null) {
                            if (spLineSectionList.contains(loadFlowModel.getOutput().getOverload().get(i).getId())) {
                                int paddingPx = 0;
                                Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                        BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                                Canvas canvas1 = new Canvas(paddedBitmap);
                                canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                                Objects.requireNonNull(spotloadSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(loadFlowModel.getOutput().getOverloadColor())), 0, 0)));
                                loadFlowOverLoadDeviceID.put(loadFlowModel.getOutput().getOverload().get(i).getId(), spotloadSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId()));
                            } else {
                                Objects.requireNonNull(spotloadSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(loadFlowModel.getOutput().getOverloadColor())), 0, 70)));
                                loadFlowOverLoadDeviceID.put(loadFlowModel.getOutput().getOverload().get(i).getId(), spotloadSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId()));
                            }
                        }

                        if (!secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId()) != null) {
                                Objects.requireNonNull(secNodeSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId())).getPaint().setColor(Color.parseColor(loadFlowModel.getOutput().getOverloadColor()));
                                loadFlowOverLoadSectionID.put(loadFlowModel.getOutput().getOverload().get(i).getId(), secNodeSectionId.get(loadFlowModel.getOutput().getOverload().get(i).getId()));
                            }
                        }
                    }
                } else {
                    Status = "No Data";
                }

            } catch (Exception e) {
                Config.isLoadFlow = false;
                Status = "Load Flow Failed!";
                e.getLocalizedMessage();
            }
            return Status;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBarLayout.setVisibility(View.GONE);
            if (s.equals("Load Flow Complete!")) {
                Config.isLoadFlow = true;
                Config.isShortCircuit = false;
                Config.isLoadAllocation = false;
                binding.reportBtn.setVisibility(View.VISIBLE);
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), s, Snackbar.LENGTH_SHORT);
                snack.show();
            } else if (s.equals("No Data")) {
                Config.isLoadFlow = true;
                Config.isShortCircuit = false;
                Config.isLoadAllocation = false;
                binding.reportBtn.setVisibility(View.VISIBLE);
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Load Flow Run!", Snackbar.LENGTH_SHORT);
                snack.show();
            } else {
                Config.isLoadFlow = false;
                Config.isShortCircuit = false;
                Config.isLoadAllocation = false;
                binding.reportBtn.setVisibility(View.GONE);
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Load Flow Failed! Please Try Again Later", Snackbar.LENGTH_LONG);
                snack.show();
            }
            binding.map.invalidate();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ShortCircuitAnalysis extends AsyncTask<Void, Void, String> {

        private ShortCircuitModel shortCircuitModels;

        public ShortCircuitAnalysis(ShortCircuitModel shortCircuitModels) {
            this.shortCircuitModels = shortCircuitModels;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarLayout.setVisibility(View.VISIBLE);
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(Void... voids) {
            String Status;
            try {
                Status = "";

                if (shortCircuitModels.getOutput().getShortCircuitRatingColor() != null) {
                    ratingColors = shortCircuitModels.getOutput().getShortCircuitRatingColor();
                }

                if (shortCircuitModels.getOutput().getOverVoltageColor() != null) {
                    overVoltageColors = shortCircuitModels.getOutput().getOverVoltageColor();
                }

                if (shortCircuitModels.getOutput().getOverloadColor() != null) {
                    overloadColors = shortCircuitModels.getOutput().getOverloadColor();
                }

                if (shortCircuitModels.getOutput().getUndervoltageColor() != null) {
                    underVoltageColors = shortCircuitModels.getOutput().getUndervoltageColor();
                }

                if (shortCircuitModels.getOutput().getShortCircuitRating() != null && !shortCircuitModels.getOutput().getShortCircuitRating().isEmpty() && shortCircuitModels.getOutput().getShortCircuitRating().size() > 0) {
                    Config.isShortCircuit = true;
                    Config.isLoadFlow = false;
                    Config.isLoadAllocation = false;
                    Status = "Short Circuit Analysis Complete";
                    for (int i = 0; i < shortCircuitModels.getOutput().getShortCircuitRating().size(); i++) {
                        shortCircuitRatingSectionId.add(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId());
                        if (CaSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId()) != null) {
                            Objects.requireNonNull(CaSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId())).getPaint().setColor(Color.parseColor(shortCircuitModels.getOutput().getShortCircuitRatingColor()));
                            shortCircuitRatingSectionID.put(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId(), CaSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId()));
                        }

                        if (OhSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId()) != null) {
                            Objects.requireNonNull(OhSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId())).getPaint().setColor(Color.parseColor(shortCircuitModels.getOutput().getShortCircuitRatingColor()));
                            shortCircuitRatingSectionID.put(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId(), OhSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId()));
                        }

                        if (UnBalSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId()) != null) {
                            Objects.requireNonNull(UnBalSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId())).getPaint().setColor(Color.parseColor(shortCircuitModels.getOutput().getShortCircuitRatingColor()));
                            shortCircuitRatingSectionID.put(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId(), UnBalSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId()));
                        }

                        if (breakerSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId()) != null) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            Objects.requireNonNull(breakerSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(shortCircuitModels.getOutput().getShortCircuitRatingColor())), 90, 0)));
                            shortCircuitRatingDeviceID.put(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId(), breakerSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId()));
                        }

                        if (transformerSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId()) != null) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            Objects.requireNonNull(transformerSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(shortCircuitModels.getOutput().getShortCircuitRatingColor())), 0, 90)));
                            shortCircuitRatingDeviceID.put(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId(), transformerSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId()));
                        }

                        if (fuseSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId()) != null) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                            Objects.requireNonNull(fuseSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(shortCircuitModels.getOutput().getShortCircuitRatingColor())), 0, 90)));
                            shortCircuitRatingDeviceID.put(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId(), fuseSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId()));
                        }

                        if (switchSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId()) != null) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                            int paddingLeft = 0; // Left padding in pixels
                            int paddingTop = 0; // Top padding in pixels
                            int paddingRight = 0; // Right padding in pixels
                            int paddingBottom = 12; // Bottom padding in pixels
                            int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                            int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                            Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                            Canvas canva = new Canvas(paddedBitmap);
                            canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                            Objects.requireNonNull(switchSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(shortCircuitModels.getOutput().getShortCircuitRatingColor())), 90, 0)));
                            shortCircuitRatingDeviceID.put(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId(), switchSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId()));
                        }

                        if (capacitorSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId()) != null) {
                            Objects.requireNonNull(capacitorSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(shortCircuitModels.getOutput().getShortCircuitRatingColor())), 90, 0)));
                            shortCircuitRatingDeviceID.put(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId(), capacitorSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId()));
                        }

                        if (spotloadSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId()) != null) {
                            if (spLineSectionList.contains(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId())) {
                                int paddingPx = 0;
                                Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                        BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                                Canvas canvas1 = new Canvas(paddedBitmap);
                                canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                                Objects.requireNonNull(spotloadSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(shortCircuitModels.getOutput().getShortCircuitRatingColor())), 0, 0)));
                                shortCircuitRatingDeviceID.put(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId(), spotloadSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId()));
                            } else {
                                Objects.requireNonNull(spotloadSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(shortCircuitModels.getOutput().getShortCircuitRatingColor())), 0, 70)));
                                shortCircuitRatingDeviceID.put(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId(), spotloadSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId()));
                            }
                        }

                        if (!secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId()) != null) {
                                Objects.requireNonNull(secNodeSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId())).getPaint().setColor(Color.parseColor(shortCircuitModels.getOutput().getShortCircuitRatingColor()));
                                shortCircuitRatingSectionID.put(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId(), secNodeSectionId.get(shortCircuitModels.getOutput().getShortCircuitRating().get(i).getId()));
                            }
                        }
                    }
                } else {
                    Status = "No Data";
                }

                if (shortCircuitModels.getOutput().getOverVoltage() != null && !shortCircuitModels.getOutput().getOverVoltage().isEmpty() && shortCircuitModels.getOutput().getOverVoltage().size() > 0) {
                    Config.isShortCircuit = true;
                    Config.isLoadFlow = false;
                    Config.isLoadAllocation = false;
                    Status = "Short Circuit Analysis Complete";
                    for (int i = 0; i < shortCircuitModels.getOutput().getOverVoltage().size(); i++) {
                        shortCircuitOverVoltageSectionId.add(shortCircuitModels.getOutput().getOverVoltage().get(i).getId());
                        if (CaSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId()) != null) {
                            Objects.requireNonNull(CaSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId())).getPaint().setColor(Color.parseColor(shortCircuitModels.getOutput().getOverVoltageColor()));
                            shortCircuitOverVoltageSectionID.put(shortCircuitModels.getOutput().getOverVoltage().get(i).getId(), CaSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId()));
                        }

                        if (OhSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId()) != null) {
                            Objects.requireNonNull(OhSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId())).getPaint().setColor(Color.parseColor(shortCircuitModels.getOutput().getOverVoltageColor()));
                            shortCircuitOverVoltageSectionID.put(shortCircuitModels.getOutput().getOverVoltage().get(i).getId(), OhSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId()));
                        }

                        if (UnBalSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId()) != null) {
                            Objects.requireNonNull(UnBalSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId())).getPaint().setColor(Color.parseColor(shortCircuitModels.getOutput().getOverVoltageColor()));
                            shortCircuitOverVoltageSectionID.put(shortCircuitModels.getOutput().getOverVoltage().get(i).getId(), UnBalSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId()));
                        }

                        if (breakerSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId()) != null) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            Objects.requireNonNull(breakerSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(shortCircuitModels.getOutput().getOverVoltageColor())), 90, 0)));
                            shortCircuitOverVoltageDeviceID.put(shortCircuitModels.getOutput().getOverVoltage().get(i).getId(), breakerSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId()));
                        }

                        if (transformerSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId()) != null) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            Objects.requireNonNull(transformerSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(shortCircuitModels.getOutput().getOverVoltageColor())), 0, 90)));
                            shortCircuitOverVoltageDeviceID.put(shortCircuitModels.getOutput().getOverVoltage().get(i).getId(), transformerSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId()));
                        }

                        if (fuseSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId()) != null) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                            Objects.requireNonNull(fuseSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(shortCircuitModels.getOutput().getOverVoltageColor())), 0, 90)));
                            shortCircuitOverVoltageDeviceID.put(shortCircuitModels.getOutput().getOverVoltage().get(i).getId(), fuseSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId()));
                        }

                        if (switchSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId()) != null) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                            int paddingLeft = 0; // Left padding in pixels
                            int paddingTop = 0; // Top padding in pixels
                            int paddingRight = 0; // Right padding in pixels
                            int paddingBottom = 12; // Bottom padding in pixels
                            int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                            int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                            Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                            Canvas canva = new Canvas(paddedBitmap);
                            canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                            Objects.requireNonNull(switchSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(shortCircuitModels.getOutput().getOverVoltageColor())), 0, 70)));
                            shortCircuitOverVoltageDeviceID.put(shortCircuitModels.getOutput().getOverVoltage().get(i).getId(), switchSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId()));
                        }

                        if (capacitorSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId()) != null) {
                            Objects.requireNonNull(capacitorSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(shortCircuitModels.getOutput().getOverVoltageColor())), 90, 0)));
                            shortCircuitOverVoltageDeviceID.put(shortCircuitModels.getOutput().getOverVoltage().get(i).getId(), capacitorSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId()));
                        }

                        if (spotloadSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId()) != null) {
                            if (spLineSectionList.contains(shortCircuitModels.getOutput().getOverVoltage().get(i).getId())) {
                                int paddingPx = 0;
                                Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                        BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                                Canvas canvas1 = new Canvas(paddedBitmap);
                                canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                                Objects.requireNonNull(spotloadSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(shortCircuitModels.getOutput().getOverVoltageColor())), 0, 0)));
                                shortCircuitOverVoltageDeviceID.put(shortCircuitModels.getOutput().getOverVoltage().get(i).getId(), spotloadSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId()));
                            } else {
                                Objects.requireNonNull(spotloadSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(shortCircuitModels.getOutput().getOverVoltageColor())), 0, 70)));
                                shortCircuitOverVoltageDeviceID.put(shortCircuitModels.getOutput().getOverVoltage().get(i).getId(), spotloadSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId()));
                            }
                        }

                        if (!secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId()) != null) {
                                Objects.requireNonNull(secNodeSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId())).getPaint().setColor(Color.parseColor(shortCircuitModels.getOutput().getOverVoltageColor()));
                                shortCircuitOverVoltageSectionID.put(shortCircuitModels.getOutput().getOverVoltage().get(i).getId(), secNodeSectionId.get(shortCircuitModels.getOutput().getOverVoltage().get(i).getId()));
                            }
                        }

                    }
                } else {
                    Status = "No Data";
                }

                if (shortCircuitModels.getOutput().getOverload() != null && !shortCircuitModels.getOutput().getOverload().isEmpty() && shortCircuitModels.getOutput().getOverload().size() > 0) {
                    Config.isShortCircuit = true;
                    Config.isLoadFlow = false;
                    Config.isLoadAllocation = false;
                    Status = "Short Circuit Analysis Complete";
                    for (int i = 0; i < shortCircuitModels.getOutput().getOverload().size(); i++) {
                        shortCircuitOverLoadSectionId.add(shortCircuitModels.getOutput().getOverload().get(i).getId());
                        if (CaSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId()) != null) {
                            Objects.requireNonNull(CaSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId())).getPaint().setColor(Color.parseColor(shortCircuitModels.getOutput().getOverloadColor()));
                            shortCircuitOverLoadSectionID.put(shortCircuitModels.getOutput().getOverload().get(i).getId(), CaSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId()));
                        }

                        if (OhSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId()) != null) {
                            Objects.requireNonNull(OhSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId())).getPaint().setColor(Color.parseColor(shortCircuitModels.getOutput().getOverloadColor()));
                            shortCircuitOverLoadSectionID.put(shortCircuitModels.getOutput().getOverload().get(i).getId(), OhSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId()));
                        }

                        if (UnBalSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId()) != null) {
                            Objects.requireNonNull(UnBalSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId())).getPaint().setColor(Color.parseColor(shortCircuitModels.getOutput().getOverloadColor()));
                            shortCircuitOverLoadSectionID.put(shortCircuitModels.getOutput().getOverload().get(i).getId(), UnBalSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId()));
                        }

                        if (breakerSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId()) != null) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            Objects.requireNonNull(breakerSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(shortCircuitModels.getOutput().getOverloadColor())), 90, 0)));
                            shortCircuitOverLoadDeviceID.put(shortCircuitModels.getOutput().getOverload().get(i).getId(), breakerSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId()));
                        }

                        if (transformerSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId()) != null) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            Objects.requireNonNull(transformerSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(shortCircuitModels.getOutput().getOverloadColor())), 0, 90)));
                            shortCircuitOverLoadDeviceID.put(shortCircuitModels.getOutput().getOverload().get(i).getId(), breakerSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId()));
                        }

                        if (fuseSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId()) != null) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                            Objects.requireNonNull(fuseSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(shortCircuitModels.getOutput().getOverloadColor())), 0, 90)));
                            shortCircuitOverLoadDeviceID.put(shortCircuitModels.getOutput().getOverload().get(i).getId(), fuseSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId()));
                        }

                        if (switchSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId()) != null) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                            int paddingLeft = 0; // Left padding in pixels
                            int paddingTop = 0; // Top padding in pixels
                            int paddingRight = 0; // Right padding in pixels
                            int paddingBottom = 12; // Bottom padding in pixels
                            int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                            int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                            Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                            Canvas canva = new Canvas(paddedBitmap);
                            canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                            Objects.requireNonNull(switchSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(shortCircuitModels.getOutput().getOverloadColor())), 90, 0)));
                            shortCircuitOverLoadDeviceID.put(shortCircuitModels.getOutput().getOverload().get(i).getId(), switchSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId()));
                        }

                        if (capacitorSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId()) != null) {
                            Objects.requireNonNull(capacitorSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(shortCircuitModels.getOutput().getOverloadColor())), 90, 0)));
                            shortCircuitOverLoadDeviceID.put(shortCircuitModels.getOutput().getOverload().get(i).getId(), capacitorSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId()));
                        }

                        if (spotloadSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId()) != null) {
                            if (spLineSectionList.contains(shortCircuitModels.getOutput().getOverload().get(i).getId())) {
                                int paddingPx = 0;
                                Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                        BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                                Canvas canvas1 = new Canvas(paddedBitmap);
                                canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                                Objects.requireNonNull(spotloadSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(shortCircuitModels.getOutput().getOverloadColor())), 0, 0)));
                                shortCircuitOverLoadDeviceID.put(shortCircuitModels.getOutput().getOverload().get(i).getId(), spotloadSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId()));
                            } else {
                                Objects.requireNonNull(spotloadSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(shortCircuitModels.getOutput().getOverloadColor())), 0, 70)));
                                shortCircuitOverLoadDeviceID.put(shortCircuitModels.getOutput().getOverload().get(i).getId(), spotloadSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId()));
                            }
                        }

                        if (!secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId()) != null) {
                                Objects.requireNonNull(secNodeSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId())).getPaint().setColor(Color.parseColor(shortCircuitModels.getOutput().getOverloadColor()));
                                shortCircuitOverLoadSectionID.put(shortCircuitModels.getOutput().getOverload().get(i).getId(), secNodeSectionId.get(shortCircuitModels.getOutput().getOverload().get(i).getId()));
                            }
                        }

                    }
                } else {
                    Status = "No Data";
                }

                if (shortCircuitModels.getOutput().getUndervoltage() != null && !shortCircuitModels.getOutput().getUndervoltage().isEmpty() && shortCircuitModels.getOutput().getUndervoltage().size() > 0) {
                    Config.isShortCircuit = true;
                    Config.isLoadFlow = false;
                    Config.isLoadAllocation = false;
                    Status = "Short Circuit Analysis Complete";
                    for (int i = 0; i < shortCircuitModels.getOutput().getUndervoltage().size(); i++) {
                        shortCircuitUnderVoltageSectionId.add(shortCircuitModels.getOutput().getUndervoltage().get(i).getId());
                        if (CaSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId()) != null) {
                            Objects.requireNonNull(CaSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId())).getPaint().setColor(Color.parseColor(shortCircuitModels.getOutput().getUndervoltageColor()));
                            shortCircuitUnderVoltageSectionID.put(shortCircuitModels.getOutput().getUndervoltage().get(i).getId(), CaSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId()));
                        }

                        if (OhSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId()) != null) {
                            Objects.requireNonNull(OhSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId())).getPaint().setColor(Color.parseColor(shortCircuitModels.getOutput().getUndervoltageColor()));
                            shortCircuitUnderVoltageSectionID.put(shortCircuitModels.getOutput().getUndervoltage().get(i).getId(), OhSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId()));
                        }

                        if (UnBalSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId()) != null) {
                            Objects.requireNonNull(UnBalSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId())).getPaint().setColor(Color.parseColor(shortCircuitModels.getOutput().getUndervoltageColor()));
                            shortCircuitUnderVoltageSectionID.put(shortCircuitModels.getOutput().getUndervoltage().get(i).getId(), UnBalSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId()));
                        }

                        if (breakerSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId()) != null) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            Objects.requireNonNull(breakerSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(shortCircuitModels.getOutput().getUndervoltageColor())), 90, 0)));
                            shortCircuitUnderVoltageDeviceID.put(shortCircuitModels.getOutput().getUndervoltage().get(i).getId(), breakerSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId()));
                        }

                        if (transformerSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId()) != null) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            Objects.requireNonNull(transformerSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(shortCircuitModels.getOutput().getUndervoltageColor())), 0, 90)));
                            shortCircuitUnderVoltageDeviceID.put(shortCircuitModels.getOutput().getUndervoltage().get(i).getId(), transformerSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId()));
                        }

                        if (fuseSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId()) != null) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                            Objects.requireNonNull(fuseSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(shortCircuitModels.getOutput().getUndervoltageColor())), 0, 90)));
                            shortCircuitUnderVoltageDeviceID.put(shortCircuitModels.getOutput().getUndervoltage().get(i).getId(), fuseSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId()));
                        }

                        if (switchSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId()) != null) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                            int paddingLeft = 0; // Left padding in pixels
                            int paddingTop = 0; // Top padding in pixels
                            int paddingRight = 0; // Right padding in pixels
                            int paddingBottom = 12; // Bottom padding in pixels
                            int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                            int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                            Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                            Canvas canva = new Canvas(paddedBitmap);
                            canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                            Objects.requireNonNull(switchSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(shortCircuitModels.getOutput().getUndervoltageColor())), 90, 0)));
                            shortCircuitUnderVoltageDeviceID.put(shortCircuitModels.getOutput().getUndervoltage().get(i).getId(), switchSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId()));
                        }

                        if (capacitorSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId()) != null) {
                            Objects.requireNonNull(capacitorSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(shortCircuitModels.getOutput().getUndervoltageColor())), 90, 0)));
                            shortCircuitUnderVoltageDeviceID.put(shortCircuitModels.getOutput().getUndervoltage().get(i).getId(), capacitorSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId()));
                        }

                        if (spotloadSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId()) != null) {
                            if (spLineSectionList.contains(shortCircuitModels.getOutput().getUndervoltage().get(i).getId())) {
                                int paddingPx = 0;
                                Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                        BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                                Canvas canvas1 = new Canvas(paddedBitmap);
                                canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                                Objects.requireNonNull(spotloadSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(shortCircuitModels.getOutput().getUndervoltageColor())), 0, 0)));
                                shortCircuitUnderVoltageDeviceID.put(shortCircuitModels.getOutput().getUndervoltage().get(i).getId(), spotloadSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId()));
                            } else {
                                Objects.requireNonNull(spotloadSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(shortCircuitModels.getOutput().getUndervoltageColor())), 0, 70)));
                                shortCircuitUnderVoltageDeviceID.put(shortCircuitModels.getOutput().getUndervoltage().get(i).getId(), spotloadSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId()));
                            }
                        }

                        if (!secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId()) != null) {
                                Objects.requireNonNull(secNodeSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId())).getPaint().setColor(Color.parseColor(shortCircuitModels.getOutput().getUndervoltageColor()));
                                shortCircuitUnderVoltageSectionID.put(shortCircuitModels.getOutput().getUndervoltage().get(i).getId(), secNodeSectionId.get(shortCircuitModels.getOutput().getUndervoltage().get(i).getId()));
                            }
                        }
                    }
                } else {
                    Status = "No Data";
                }

            } catch (Exception e) {
                Config.isShortCircuit = false;
                Status = "Short Circuit Analysis Failed";
                e.printStackTrace();
            }
            return Status;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBarLayout.setVisibility(View.GONE);
            if (s.equals("Short Circuit Analysis Complete")) {
                Config.isShortCircuit = true;
                Config.isLoadFlow = false;
                Config.isLoadAllocation = false;
                binding.reportBtn.setVisibility(View.VISIBLE);
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), s, Snackbar.LENGTH_LONG);
                snack.show();
            } else if (s.equals("No Data")) {
                Config.isShortCircuit = true;
                Config.isLoadFlow = false;
                Config.isLoadAllocation = false;
                binding.reportBtn.setVisibility(View.VISIBLE);
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Short Circuit Analysis Run!", Snackbar.LENGTH_SHORT);
                snack.show();
            } else {
                Config.isShortCircuit = false;
                Config.isLoadFlow = false;
                Config.isLoadAllocation = false;
                binding.reportBtn.setVisibility(View.GONE);
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Load Flow Failed! Please Try Again Later", Snackbar.LENGTH_LONG);
                snack.show();
            }
            binding.map.invalidate();
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class LoadAllocationAnalysis extends AsyncTask<Void, Void, String> {

        private LoadAllocationModel loadAllocationModel;

        public LoadAllocationAnalysis(LoadAllocationModel loadAllocationModel) {
            this.loadAllocationModel = loadAllocationModel;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(Void... voids) {
            String Status;
            try {
                Status = "";

                if (loadAllocationModel.getOutput().getOverVoltageColor() != null) {
                    overVoltageColors = loadAllocationModel.getOutput().getOverVoltageColor();
                }

                if (loadAllocationModel.getOutput().getOverloadColor() != null) {
                    overloadColors = loadAllocationModel.getOutput().getOverloadColor();
                }

                if (loadAllocationModel.getOutput().getUndervoltageColor() != null) {
                    underVoltageColors = loadAllocationModel.getOutput().getUndervoltageColor();
                }

                if (loadAllocationModel.getOutput().getOverVoltage() != null && !loadAllocationModel.getOutput().getOverVoltage().isEmpty() && loadAllocationModel.getOutput().getOverVoltage().size() > 0) {
                    Config.isLoadAllocation = true;
                    Config.isLoadFlow = false;
                    Config.isShortCircuit = false;
                    Status = "Load Allocation Analysis Complete";
                    for (int i = 0; i < loadAllocationModel.getOutput().getOverVoltage().size(); i++) {

                        if (CaSectionId.get(loadAllocationModel.getOutput().getOverVoltage()) != null) {
                            Objects.requireNonNull(CaSectionId.get(loadAllocationModel.getOutput().getOverVoltage())).getPaint().setColor(Color.parseColor(loadAllocationModel.getOutput().getOverVoltageColor()));
                        }

                        if (OhSectionId.get(loadAllocationModel.getOutput().getOverVoltage()) != null) {
                            Objects.requireNonNull(OhSectionId.get(loadAllocationModel.getOutput().getOverVoltage())).getPaint().setColor(Color.parseColor(loadAllocationModel.getOutput().getOverVoltageColor()));
                        }

                        if (UnBalSectionId.get(loadAllocationModel.getOutput().getOverVoltage()) != null) {
                            Objects.requireNonNull(UnBalSectionId.get(loadAllocationModel.getOutput().getOverVoltage())).getPaint().setColor(Color.parseColor(loadAllocationModel.getOutput().getOverVoltageColor()));
                        }

                        if (breakerSectionId.get(loadAllocationModel.getOutput().getOverVoltage()) != null) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            Objects.requireNonNull(breakerSectionId.get(loadAllocationModel.getOutput().getOverVoltage())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(loadAllocationModel.getOutput().getOverVoltageColor())), 0, 0)));
                        }

                        if (transformerSectionId.get(loadAllocationModel.getOutput().getOverVoltage()) != null) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            Objects.requireNonNull(transformerSectionId.get(loadAllocationModel.getOutput().getOverVoltage())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(loadAllocationModel.getOutput().getOverVoltageColor())), 93, 0)));
                        }

                        if (fuseSectionId.get(loadAllocationModel.getOutput().getOverVoltage()) != null) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                            Objects.requireNonNull(fuseSectionId.get(loadAllocationModel.getOutput().getOverVoltage())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(loadAllocationModel.getOutput().getOverVoltageColor())), 0, 0)));
                        }

                        if (switchSectionId.get(loadAllocationModel.getOutput().getOverVoltage()) != null) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                            int paddingLeft = 0; // Left padding in pixels
                            int paddingTop = 0; // Top padding in pixels
                            int paddingRight = 0; // Right padding in pixels
                            int paddingBottom = 12; // Bottom padding in pixels
                            int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                            int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                            Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                            Canvas canva = new Canvas(paddedBitmap);
                            canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                            Objects.requireNonNull(switchSectionId.get(loadAllocationModel.getOutput().getOverVoltage())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(loadAllocationModel.getOutput().getOverVoltageColor())), 0, 0)));
                        }

                        if (capacitorSectionId.get(loadAllocationModel.getOutput().getOverVoltage()) != null) {
                            Objects.requireNonNull(capacitorSectionId.get(loadAllocationModel.getOutput().getOverVoltage())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(loadAllocationModel.getOutput().getOverVoltageColor())), 90, 0)));
                        }

                        if (spotloadSectionId.get(loadAllocationModel.getOutput().getOverVoltage()) != null) {
                            if (spLineSectionList.contains(loadAllocationModel.getOutput().getOverVoltage())) {
                                int paddingPx = 0;
                                Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                        BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                                Canvas canvas1 = new Canvas(paddedBitmap);
                                canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                                Objects.requireNonNull(spotloadSectionId.get(loadAllocationModel.getOutput().getOverVoltage())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(loadAllocationModel.getOutput().getOverVoltageColor())), 0, 0)));
                            } else {
                                Objects.requireNonNull(spotloadSectionId.get(loadAllocationModel.getOutput().getOverVoltage())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(loadAllocationModel.getOutput().getOverVoltageColor())), 0, 35)));
                            }
                        }

                        if (!secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadAllocationModel.getOutput().getOverVoltage()) != null) {
                                Objects.requireNonNull(secNodeSectionId.get(loadAllocationModel.getOutput().getOverVoltage())).getPaint().setColor(Color.parseColor(loadAllocationModel.getOutput().getOverVoltageColor()));
                            }
                        }
                    }
                }

                if (loadAllocationModel.getOutput().getOverload() != null && !loadAllocationModel.getOutput().getOverload().isEmpty() && loadAllocationModel.getOutput().getOverload().size() > 0) {
                    Config.isLoadAllocation = true;
                    Config.isLoadFlow = false;
                    Config.isShortCircuit = false;
                    Status = "Load Allocation Analysis Complete";
                    for (int i = 0; i < loadAllocationModel.getOutput().getOverload().size(); i++) {

                        if (CaSectionId.get(loadAllocationModel.getOutput().getOverload()) != null) {
                            Objects.requireNonNull(CaSectionId.get(loadAllocationModel.getOutput().getOverload())).getPaint().setColor(Color.parseColor(loadAllocationModel.getOutput().getOverloadColor()));
                        }

                        if (OhSectionId.get(loadAllocationModel.getOutput().getOverload()) != null) {
                            Objects.requireNonNull(OhSectionId.get(loadAllocationModel.getOutput().getOverload())).getPaint().setColor(Color.parseColor(loadAllocationModel.getOutput().getOverloadColor()));
                        }

                        if (UnBalSectionId.get(loadAllocationModel.getOutput().getOverload()) != null) {
                            Objects.requireNonNull(UnBalSectionId.get(loadAllocationModel.getOutput().getOverload())).getPaint().setColor(Color.parseColor(loadAllocationModel.getOutput().getOverloadColor()));
                        }

                        if (breakerSectionId.get(loadAllocationModel.getOutput().getOverload()) != null) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            Objects.requireNonNull(breakerSectionId.get(loadAllocationModel.getOutput().getOverload())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(loadAllocationModel.getOutput().getOverloadColor())), 0, 0)));
                        }

                        if (transformerSectionId.get(loadAllocationModel.getOutput().getOverload()) != null) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            Objects.requireNonNull(transformerSectionId.get(loadAllocationModel.getOutput().getOverload())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(loadAllocationModel.getOutput().getOverloadColor())), 93, 0)));
                        }

                        if (fuseSectionId.get(loadAllocationModel.getOutput().getOverload()) != null) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                            Objects.requireNonNull(fuseSectionId.get(loadAllocationModel.getOutput().getOverload())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(loadAllocationModel.getOutput().getOverloadColor())), 0, 0)));
                        }

                        if (switchSectionId.get(loadAllocationModel.getOutput().getOverload()) != null) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                            int paddingLeft = 0; // Left padding in pixels
                            int paddingTop = 0; // Top padding in pixels
                            int paddingRight = 0; // Right padding in pixels
                            int paddingBottom = 12; // Bottom padding in pixels
                            int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                            int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                            Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                            Canvas canva = new Canvas(paddedBitmap);
                            canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                            Objects.requireNonNull(switchSectionId.get(loadAllocationModel.getOutput().getOverload())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(loadAllocationModel.getOutput().getOverloadColor())), 0, 0)));
                        }

                        if (capacitorSectionId.get(loadAllocationModel.getOutput().getOverload()) != null) {
                            Objects.requireNonNull(capacitorSectionId.get(loadAllocationModel.getOutput().getOverload())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(loadAllocationModel.getOutput().getOverloadColor())), 90, 0)));
                        }

                        if (spotloadSectionId.get(loadAllocationModel.getOutput().getOverload()) != null) {
                            if (spLineSectionList.contains(loadAllocationModel.getOutput().getOverload())) {
                                int paddingPx = 0;
                                Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                        BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                                Canvas canvas1 = new Canvas(paddedBitmap);
                                canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                                Objects.requireNonNull(spotloadSectionId.get(loadAllocationModel.getOutput().getOverload())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(loadAllocationModel.getOutput().getOverloadColor())), 0, 0)));
                            } else {
                                Objects.requireNonNull(spotloadSectionId.get(loadAllocationModel.getOutput().getOverload())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(loadAllocationModel.getOutput().getOverloadColor())), 0, 35)));
                            }
                        }

                        if (!secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadAllocationModel.getOutput().getOverload()) != null) {
                                Objects.requireNonNull(secNodeSectionId.get(loadAllocationModel.getOutput().getOverload())).getPaint().setColor(Color.parseColor(loadAllocationModel.getOutput().getOverloadColor()));
                            }
                        }
                    }
                }

                if (loadAllocationModel.getOutput().getUndervoltage() != null && !loadAllocationModel.getOutput().getUndervoltage().isEmpty() && loadAllocationModel.getOutput().getUndervoltage().size() > 0) {
                    Config.isLoadAllocation = true;
                    Config.isLoadFlow = false;
                    Config.isShortCircuit = false;
                    Status = "Load Allocation Analysis Complete";
                    for (int i = 0; i < loadAllocationModel.getOutput().getUndervoltage().size(); i++) {

                        if (CaSectionId.get(loadAllocationModel.getOutput().getUndervoltage()) != null) {
                            Objects.requireNonNull(CaSectionId.get(loadAllocationModel.getOutput().getUndervoltage())).getPaint().setColor(Color.parseColor(loadAllocationModel.getOutput().getUndervoltageColor()));
                        }

                        if (OhSectionId.get(loadAllocationModel.getOutput().getUndervoltage()) != null) {
                            Objects.requireNonNull(OhSectionId.get(loadAllocationModel.getOutput().getUndervoltage())).getPaint().setColor(Color.parseColor(loadAllocationModel.getOutput().getUndervoltageColor()));
                        }

                        if (UnBalSectionId.get(loadAllocationModel.getOutput().getUndervoltage()) != null) {
                            Objects.requireNonNull(UnBalSectionId.get(loadAllocationModel.getOutput().getUndervoltage())).getPaint().setColor(Color.parseColor(loadAllocationModel.getOutput().getUndervoltageColor()));
                        }

                        if (breakerSectionId.get(loadAllocationModel.getOutput().getUndervoltage()) != null) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            Objects.requireNonNull(breakerSectionId.get(loadAllocationModel.getOutput().getUndervoltage())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(loadAllocationModel.getOutput().getUndervoltageColor())), 0, 0)));
                        }

                        if (transformerSectionId.get(loadAllocationModel.getOutput().getUndervoltage()) != null) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            Objects.requireNonNull(transformerSectionId.get(loadAllocationModel.getOutput().getUndervoltage())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(loadAllocationModel.getOutput().getUndervoltageColor())), 93, 0)));
                        }

                        if (fuseSectionId.get(loadAllocationModel.getOutput().getUndervoltage()) != null) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                            Objects.requireNonNull(fuseSectionId.get(loadAllocationModel.getOutput().getUndervoltage())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(loadAllocationModel.getOutput().getUndervoltageColor())), 0, 0)));
                        }

                        if (switchSectionId.get(loadAllocationModel.getOutput().getUndervoltage()) != null) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                            int paddingLeft = 0; // Left padding in pixels
                            int paddingTop = 0; // Top padding in pixels
                            int paddingRight = 0; // Right padding in pixels
                            int paddingBottom = 12; // Bottom padding in pixels
                            int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                            int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                            Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                            Canvas canva = new Canvas(paddedBitmap);
                            canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                            Objects.requireNonNull(switchSectionId.get(loadAllocationModel.getOutput().getUndervoltage())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(loadAllocationModel.getOutput().getUndervoltageColor())), 0, 0)));
                        }

                        if (capacitorSectionId.get(loadAllocationModel.getOutput().getUndervoltage()) != null) {
                            Objects.requireNonNull(capacitorSectionId.get(loadAllocationModel.getOutput().getUndervoltage())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(loadAllocationModel.getOutput().getUndervoltageColor())), 90, 0)));
                        }

                        if (spotloadSectionId.get(loadAllocationModel.getOutput().getUndervoltage()) != null) {
                            if (spLineSectionList.contains(loadAllocationModel.getOutput().getUndervoltage())) {
                                int paddingPx = 0;
                                Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                        BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                                Canvas canvas1 = new Canvas(paddedBitmap);
                                canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                                Objects.requireNonNull(spotloadSectionId.get(loadAllocationModel.getOutput().getUndervoltage())).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(loadAllocationModel.getOutput().getUndervoltageColor())), 0, 0)));
                            } else {
                                spotloadSectionId.get(loadAllocationModel.getOutput().getUndervoltage()).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(loadAllocationModel.getOutput().getUndervoltageColor())), 0, 35)));
                            }
                        }

                        if (!secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadAllocationModel.getOutput().getUndervoltage()) != null) {
                                Objects.requireNonNull(secNodeSectionId.get(loadAllocationModel.getOutput().getUndervoltage())).getPaint().setColor(Color.parseColor(loadAllocationModel.getOutput().getUndervoltageColor()));
                            }
                        }
                    }
                }

            } catch (Exception e) {
                Config.isLoadAllocation = false;
                Status = "Load Allocation Analysis Failed";
                e.printStackTrace();
            }
            return Status;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("Load Allocation Analysis Complete")) {
                Config.isLoadAllocation = true;
                binding.reportBtn.setVisibility(View.VISIBLE);
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), s, Snackbar.LENGTH_LONG);
                snack.show();
            } else {
                Config.isLoadAllocation = false;
                binding.reportBtn.setVisibility(View.GONE);
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), s + " " + "Please Try Again Later", Snackbar.LENGTH_LONG);
                snack.show();
            }
            binding.map.invalidate();
        }

    }

    @SuppressLint("SetTextI18n")
    private void getUpTracingData() {
        if (networkId != null && nodeId != null) {
            progressBarLayout.setProcessText("Tracing UpStreams...");
            progressBarLayout.setVisibility(View.VISIBLE);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("NetworkId", networkId);
            jsonObject.addProperty("Nodeid", nodeId);
            jsonObject.addProperty("Direction", "Upstream");
            jsonObject.addProperty("UserType", prefManager.getUserType());
            jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
            ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
            Call<Tracing> call = apiInterface.getTracingData("Bearer " + prefManager.getAccessToken(), jsonObject);
            call.enqueue(new Callback<Tracing>() {
                @Override
                public void onResponse(@NonNull Call<Tracing> call, @NonNull Response<Tracing> response) {
                    if (response.code() == 200) {
                        ErrorPdfLogger.logApiSuccess(MapActivity.this, "POST", "trace/", "HTTP " + response.code() + " UpStream: " + response.message()
                                + "NetworkId : " + networkId
                                + "Nodeid : " + nodeId
                                + "Direction : " + "Upstream"
                                + "UserType : " +  prefManager.getUserType()
                                + "CYMDBNET : " +  prefManager.getDBName()
                                + "AccessToken : " +prefManager.getAccessToken());
                        try {
                            progressBarLayout.setVisibility(View.GONE);
                            Tracing tracing = response.body();
                            new TraceDevices(response.body()).execute();
                        } catch (Exception e) {
                            ErrorPdfLogger.logCrash(MapActivity.this,e);
                            e.printStackTrace();
                        }
                    } else if (response.code() == 401) {
                        ErrorPdfLogger.logApiError(MapActivity.this, "POST", "trace/", "HTTP " + response.code() + " UpStream: " + response.message()
                                + "NetworkId : " + networkId
                                + "Nodeid : " + nodeId
                                + "Direction : " + "Upstream"
                                + "UserType : " +  prefManager.getUserType()
                                + "CYMDBNET : " +  prefManager.getDBName()
                                + "AccessToken : " +prefManager.getAccessToken());
                        prefManager.setIsUserLogin(false);
                        startActivity(new Intent(MapActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        ErrorPdfLogger.logApiError(MapActivity.this, "POST", "trace/", "HTTP " + response.code() + " UpStream: " + response.message());
                        progressBarLayout.setVisibility(View.GONE);
                        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), response.message() + " - " + response.code(), Snackbar.LENGTH_SHORT);
                        snack.show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Tracing> call, @NonNull Throwable t) {
                    ErrorPdfLogger.logApiFailure(MapActivity.this, "POST", "trace/", t);
                    progressBarLayout.setVisibility(View.GONE);
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_msg), Snackbar.LENGTH_SHORT);
                    snack.show();
                }
            });
        } else {
            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Please select any network!", Snackbar.LENGTH_LONG);
            snack.show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void getDownTracingData() {
        if (networkId != null && nodeId != null) {
            progressBarLayout.setVisibility(View.VISIBLE);
            progressBarLayout.setProcessText("Tracing DownStreams...");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("NetworkId", networkId);
            jsonObject.addProperty("Nodeid", nodeId);
            jsonObject.addProperty("Direction", "Downstream");
            jsonObject.addProperty("UserType", prefManager.getUserType());
            jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
            ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
            Call<Tracing> call = apiInterface.getTracingData("Bearer " + prefManager.getAccessToken(), jsonObject);
            call.enqueue(new Callback<Tracing>() {
                @Override
                public void onResponse(@NonNull Call<Tracing> call, @NonNull Response<Tracing> response) {
                    if (response.code() == 200) {
                        ErrorPdfLogger.logApiSuccess(MapActivity.this, "POST", "trace/", "HTTP " + response.code() + " DownStream: " + response.message()
                                + "NetworkId : " + networkId
                                + "Nodeid : " + nodeId
                                + "Direction : " + "Downstream"
                                + "UserType : " +  prefManager.getUserType()
                                + "CYMDBNET : " +  prefManager.getDBName()
                                + "AccessToken : " + prefManager.getAccessToken());
                        try {
                            progressBarLayout.setVisibility(View.GONE);
                            Tracing tracing = response.body();
                            new TraceDevices(response.body()).execute();
                        } catch (Exception e) {
                            ErrorPdfLogger.logCrash(MapActivity.this,e);
                            e.printStackTrace();
                        }
                    } else if (response.code() == 401) {
                        ErrorPdfLogger.logApiError(MapActivity.this, "POST", "trace/", "HTTP " + response.code() + " DownStream: " + response.message()
                                + "NetworkId : " + networkId
                                + "Nodeid : " + nodeId
                                + "Direction : " + "Downstream"
                                + "UserType : " +  prefManager.getUserType()
                                + "CYMDBNET : " +  prefManager.getDBName()
                                + "AccessToken : " + prefManager.getAccessToken());
                        prefManager.setIsUserLogin(false);
                        startActivity(new Intent(MapActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        ErrorPdfLogger.logApiError(MapActivity.this, "POST", "trace/", "HTTP " + response.code() + " DownStream: " + response.message()
                                + "NetworkId : " + networkId
                                + "Nodeid : " + nodeId
                                + "Direction : " + "Downstream"
                                + "UserType : " +  prefManager.getUserType()
                                + "CYMDBNET : " +  prefManager.getDBName()
                                + "AccessToken : " + prefManager.getAccessToken());
                        progressBarLayout.setVisibility(View.GONE);
                        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), response.message() + " - " + response.code(), Snackbar.LENGTH_SHORT);
                        snack.show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Tracing> call, @NonNull Throwable t) {
                    ErrorPdfLogger.logApiFailure(MapActivity.this, "POST", "trace/", t);
                    progressBarLayout.setVisibility(View.GONE);
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_msg), Snackbar.LENGTH_SHORT);
                    snack.show();
                }
            });
        } else {
            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Please select any network!", Snackbar.LENGTH_LONG);
            snack.show();
        }
    }

    private void clearVertexNode() {
        if (selectedNode != null) {
            @SuppressLint("UseCompatLoadingForDrawables")
            Drawable drawable = getResources().getDrawable(R.drawable.dot);
            Bitmap bitmap = drawableToBitmap(drawable);
            selectedNode.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), changeBgTransparentBitmapColor(bitmap, Color.BLACK)));
            selectedNode = null;
        }

        if (vertexList != null && !vertexList.isEmpty()) {
            for (int i = 0; i < vertexList.size(); i++) {
                binding.map.getOverlays().remove(vertexList.get(i));
                binding.map.invalidate();
            }
            vertexList.clear();
        }

        coordinateList.clear();
        newSectionGeoPointList.clear();

        if (newPolyLineList != null && !newPolyLineList.isEmpty()) {
            for (int i = 0; i < newPolyLineList.size(); i++) {
                binding.map.getOverlays().remove(newPolyLineList.get(i));
                binding.map.invalidate();
            }
        }

        selectedNodeID = null;
    }

    @SuppressLint("StaticFieldLeak")
    private class TraceDevices extends AsyncTask<Void, Void, String> {

        private Tracing tracing;

        public TraceDevices(Tracing tracing) {
            this.tracing = tracing;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarLayout.setVisibility(View.VISIBLE);
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(Void... voids) {
            try {

                if (Config.isLoadFlow) {
                    if (CaPolylineList != null && !CaPolylineList.isEmpty()) {
                        for (int j = 0; j < CaPolylineList.size(); j++) {
                            CaPolylineList.get(j).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (ohPolylineList != null && !ohPolylineList.isEmpty()) {
                        for (int j = 0; j < ohPolylineList.size(); j++) {
                            ohPolylineList.get(j).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (unBalPolylineList != null && !unBalPolylineList.isEmpty()) {
                        for (int j = 0; j < unBalPolylineList.size(); j++) {
                            unBalPolylineList.get(j).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (secNodeList != null && !secNodeList.isEmpty()) {
                        for (int j = 0; j < secNodeList.size(); j++) {
                            secNodeList.get(j).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (breakerList != null && !breakerList.isEmpty()) {
                        for (int i = 0; i < breakerList.size(); i++) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            breakerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 95)));
                        }
                    }

                    if (transformerList != null && !transformerList.isEmpty()) {
                        for (int i = 0; i < transformerList.size(); i++) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            transformerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 93)));
                        }
                    }

                    if (fuseList != null && !fuseList.isEmpty()) {
                        for (int i = 0; i < fuseList.size(); i++) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                            fuseList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 0)));
                        }
                    }

                    if (switchedList != null && !switchedList.isEmpty()) {
                        for (int i = 0; i < switchedList.size(); i++) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                            int paddingLeft = 0; // Left padding in pixels
                            int paddingTop = 0; // Top padding in pixels
                            int paddingRight = 0; // Right padding in pixels
                            int paddingBottom = 12; // Bottom padding in pixels
                            int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                            int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                            Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                            Canvas canva = new Canvas(paddedBitmap);
                            canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                            switchedList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.BLACK), 0, 0)));
                        }
                    }

                    if (capacitorList != null && !capacitorList.isEmpty()) {
                        for (int i = 0; i < capacitorList.size(); i++) {
                            capacitorList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.BLACK), 90, 0)));
                        }
                    }

                    if (spotLoadList != null && !spotLoadList.isEmpty()) {
                        for (int i = 0; i < spotLoadList.size(); i++) {
                            int paddingPx = 0;
                            Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                    BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                            Canvas canvas1 = new Canvas(paddedBitmap);
                            canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                            spotLoadList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.BLACK), 0, 0)));
                        }
                    }

                    if (loadFlowOverVoltageSectionId != null && overVoltageColors != null && !loadFlowOverVoltageSectionId.isEmpty()) {
                        for (int i = 0; i < loadFlowOverVoltageSectionId.size(); i++) {

                            if (CaSectionId.containsKey(loadFlowOverVoltageSectionId.get(i))) {
                                Objects.requireNonNull(CaSectionId.get(loadFlowOverVoltageSectionId.get(i))).setColor(Color.parseColor(overVoltageColors));
                            }

                            if (OhSectionId.containsKey(loadFlowOverVoltageSectionId.get(i))) {
                                Objects.requireNonNull(OhSectionId.get(loadFlowOverVoltageSectionId.get(i))).setColor(Color.parseColor(overVoltageColors));
                            }

                            if (UnBalSectionId.containsKey(loadFlowOverVoltageSectionId.get(i))) {
                                Objects.requireNonNull(UnBalSectionId.get(loadFlowOverVoltageSectionId.get(i))).setColor(Color.parseColor(overVoltageColors));
                            }

                            if (breakerSectionId.containsKey(loadFlowOverVoltageSectionId.get(i))) {
                                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                                Bitmap bitmap = drawableToBitmap(drawable);
                                Objects.requireNonNull(breakerSectionId.get(loadFlowOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 0, 95)));
                            }

                            if (transformerSectionId.containsKey(loadFlowOverVoltageSectionId.get(i))) {
                                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                                Bitmap bitmap = drawableToBitmap(drawable);
                                Objects.requireNonNull(transformerSectionId.get(loadFlowOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 0, 93)));
                            }

                            if (fuseSectionId.containsKey(loadFlowOverVoltageSectionId.get(i))) {
                                Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                                Objects.requireNonNull(fuseSectionId.get(loadFlowOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 0, 0)));
                            }

                            if (switchSectionId.containsKey(loadFlowOverVoltageSectionId.get(i))) {
                                Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                                int paddingLeft = 0; // Left padding in pixels
                                int paddingTop = 0; // Top padding in pixels
                                int paddingRight = 0; // Right padding in pixels
                                int paddingBottom = 12; // Bottom padding in pixels
                                int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                                int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                                Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                                Canvas canva = new Canvas(paddedBitmap);
                                canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                                Objects.requireNonNull(switchSectionId.get(loadFlowOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(overVoltageColors)), 0, 0)));
                            }

                            if (capacitorSectionId.containsKey(loadFlowOverVoltageSectionId.get(i))) {
                                Objects.requireNonNull(capacitorSectionId.get(loadFlowOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overVoltageColors)), 90, 0)));
                            }

                            if (spotloadSectionId.containsKey(loadFlowOverVoltageSectionId.get(i))) {
                                if (spLineSectionList.contains(loadFlowOverVoltageSectionId.get(i))) {
                                    int paddingPx = 0;
                                    Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                            BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                                    Canvas canvas1 = new Canvas(paddedBitmap);
                                    canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                                    Objects.requireNonNull(spotloadSectionId.get(loadFlowOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(overVoltageColors)), 0, 0)));
                                } else {
                                    Objects.requireNonNull(spotloadSectionId.get(loadFlowOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(overVoltageColors)), 0, 35)));
                                }
                            }

                        }
                    }

                    if (loadFlowUnderVoltageSectionId != null && underVoltageColors != null && !loadFlowUnderVoltageSectionId.isEmpty()) {
                        for (int i = 0; i < loadFlowUnderVoltageSectionId.size(); i++) {

                            if (CaSectionId.containsKey(loadFlowUnderVoltageSectionId.get(i))) {
                                Objects.requireNonNull(CaSectionId.get(loadFlowUnderVoltageSectionId.get(i))).setColor(Color.parseColor(underVoltageColors));
                            }

                            if (OhSectionId.containsKey(loadFlowUnderVoltageSectionId.get(i))) {
                                Objects.requireNonNull(OhSectionId.get(loadFlowUnderVoltageSectionId.get(i))).setColor(Color.parseColor(underVoltageColors));
                            }

                            if (UnBalSectionId.containsKey(loadFlowUnderVoltageSectionId.get(i))) {
                                Objects.requireNonNull(UnBalSectionId.get(loadFlowUnderVoltageSectionId.get(i))).setColor(Color.parseColor(underVoltageColors));
                            }

                            if (breakerSectionId.containsKey(loadFlowUnderVoltageSectionId.get(i))) {
                                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                                Bitmap bitmap = drawableToBitmap(drawable);
                                Objects.requireNonNull(breakerSectionId.get(loadFlowUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 0, 95)));
                            }

                            if (transformerSectionId.containsKey(loadFlowUnderVoltageSectionId.get(i))) {
                                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                                Bitmap bitmap = drawableToBitmap(drawable);
                                Objects.requireNonNull(transformerSectionId.get(loadFlowUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 0, 93)));
                            }

                            if (fuseSectionId.containsKey(loadFlowUnderVoltageSectionId.get(i))) {
                                Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                                Objects.requireNonNull(fuseSectionId.get(loadFlowUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 0, 0)));
                            }

                            if (switchSectionId.containsKey(loadFlowUnderVoltageSectionId.get(i))) {
                                Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                                int paddingLeft = 0; // Left padding in pixels
                                int paddingTop = 0; // Top padding in pixels
                                int paddingRight = 0; // Right padding in pixels
                                int paddingBottom = 12; // Bottom padding in pixels
                                int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                                int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                                Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                                Canvas canva = new Canvas(paddedBitmap);
                                canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                                Objects.requireNonNull(switchSectionId.get(loadFlowUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(underVoltageColors)), 0, 0)));
                            }

                            if (capacitorSectionId.containsKey(loadFlowUnderVoltageSectionId.get(i))) {
                                Objects.requireNonNull(capacitorSectionId.get(loadFlowUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(underVoltageColors)), 90, 0)));
                            }

                            if (spotloadSectionId.containsKey(loadFlowUnderVoltageSectionId.get(i))) {
                                if (spLineSectionList.contains(loadFlowUnderVoltageSectionId.get(i))) {
                                    int paddingPx = 0;
                                    Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                            BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                                    Canvas canvas1 = new Canvas(paddedBitmap);
                                    canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                                    Objects.requireNonNull(spotloadSectionId.get(loadFlowUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(underVoltageColors)), 0, 0)));
                                } else {
                                    Objects.requireNonNull(spotloadSectionId.get(loadFlowUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(underVoltageColors)), 0, 35)));
                                }
                            }

                        }
                    }

                    if (loadFlowOverLoadSectionId != null && overloadColors != null && !loadFlowOverLoadSectionId.isEmpty()) {
                        for (int i = 0; i < loadFlowOverLoadSectionId.size(); i++) {

                            if (CaSectionId.containsKey(loadFlowOverLoadSectionId.get(i))) {
                                Objects.requireNonNull(CaSectionId.get(loadFlowOverLoadSectionId.get(i))).setColor(Color.parseColor(overloadColors));
                            }

                            if (OhSectionId.containsKey(loadFlowOverLoadSectionId.get(i))) {
                                Objects.requireNonNull(OhSectionId.get(loadFlowOverLoadSectionId.get(i))).setColor(Color.parseColor(overloadColors));
                            }

                            if (UnBalSectionId.containsKey(loadFlowOverLoadSectionId.get(i))) {
                                Objects.requireNonNull(UnBalSectionId.get(loadFlowOverLoadSectionId.get(i))).setColor(Color.parseColor(overloadColors));
                            }

                            if (breakerSectionId.containsKey(loadFlowOverLoadSectionId.get(i))) {
                                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                                Bitmap bitmap = drawableToBitmap(drawable);
                                Objects.requireNonNull(breakerSectionId.get(loadFlowOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 0, 95)));
                            }

                            if (transformerSectionId.containsKey(loadFlowOverLoadSectionId.get(i))) {
                                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                                Bitmap bitmap = drawableToBitmap(drawable);
                                Objects.requireNonNull(transformerSectionId.get(loadFlowOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 0, 93)));
                            }

                            if (fuseSectionId.containsKey(loadFlowOverLoadSectionId.get(i))) {
                                Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                                Objects.requireNonNull(fuseSectionId.get(loadFlowOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 0, 0)));
                            }

                            if (switchSectionId.containsKey(loadFlowOverLoadSectionId.get(i))) {
                                Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                                int paddingLeft = 0; // Left padding in pixels
                                int paddingTop = 0; // Top padding in pixels
                                int paddingRight = 0; // Right padding in pixels
                                int paddingBottom = 12; // Bottom padding in pixels
                                int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                                int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                                Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                                Canvas canva = new Canvas(paddedBitmap);
                                canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                                Objects.requireNonNull(switchSectionId.get(loadFlowOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(overloadColors)), 0, 0)));
                            }

                            if (capacitorSectionId.containsKey(loadFlowOverLoadSectionId.get(i))) {
                                Objects.requireNonNull(capacitorSectionId.get(loadFlowOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overloadColors)), 90, 0)));
                            }

                            if (spotloadSectionId.containsKey(loadFlowOverLoadSectionId.get(i))) {
                                if (spLineSectionList.contains(loadFlowOverLoadSectionId.get(i))) {
                                    int paddingPx = 0;
                                    Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                            BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                                    Canvas canvas1 = new Canvas(paddedBitmap);
                                    canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                                    Objects.requireNonNull(spotloadSectionId.get(loadFlowOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(overloadColors)), 0, 0)));
                                } else {
                                    Objects.requireNonNull(spotloadSectionId.get(loadFlowOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(overloadColors)), 0, 35)));
                                }
                            }

                        }
                    }
                } else if (Config.isShortCircuit) {
                    if (CaPolylineList != null && !CaPolylineList.isEmpty()) {
                        for (int j = 0; j < CaPolylineList.size(); j++) {
                            CaPolylineList.get(j).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (ohPolylineList != null && !ohPolylineList.isEmpty()) {
                        for (int j = 0; j < ohPolylineList.size(); j++) {
                            ohPolylineList.get(j).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (unBalPolylineList != null && !unBalPolylineList.isEmpty()) {
                        for (int j = 0; j < unBalPolylineList.size(); j++) {
                            unBalPolylineList.get(j).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (secNodeList != null && !secNodeList.isEmpty()) {
                        for (int j = 0; j < secNodeList.size(); j++) {
                            secNodeList.get(j).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (breakerList != null && !breakerList.isEmpty()) {
                        for (int i = 0; i < breakerList.size(); i++) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            breakerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 95)));
                        }
                    }

                    if (transformerList != null && !transformerList.isEmpty()) {
                        for (int i = 0; i < transformerList.size(); i++) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            transformerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 93)));
                        }
                    }

                    if (fuseList != null && !fuseList.isEmpty()) {
                        for (int i = 0; i < fuseList.size(); i++) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                            fuseList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 0)));
                        }
                    }

                    if (switchedList != null && !switchedList.isEmpty()) {
                        for (int i = 0; i < switchedList.size(); i++) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                            int paddingLeft = 0; // Left padding in pixels
                            int paddingTop = 0; // Top padding in pixels
                            int paddingRight = 0; // Right padding in pixels
                            int paddingBottom = 12; // Bottom padding in pixels
                            int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                            int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                            Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                            Canvas canva = new Canvas(paddedBitmap);
                            canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                            switchedList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.BLACK), 0, 0)));
                        }
                    }

                    if (capacitorList != null && !capacitorList.isEmpty()) {
                        for (int i = 0; i < capacitorList.size(); i++) {
                            capacitorList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.BLACK), 90, 0)));
                        }
                    }

                    if (spotLoadList != null && !spotLoadList.isEmpty()) {
                        for (int i = 0; i < spotLoadList.size(); i++) {
                            int paddingPx = 0;
                            Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                    BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                            Canvas canvas1 = new Canvas(paddedBitmap);
                            canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                            spotLoadList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.BLACK), 0, 0)));
                        }
                    }

                    if (shortCircuitRatingSectionId != null && ratingColors != null && !shortCircuitRatingSectionId.isEmpty()) {
                        for (int i = 0; i < shortCircuitRatingSectionId.size(); i++) {

                            if (CaSectionId.containsKey(shortCircuitRatingSectionId.get(i))) {
                                Objects.requireNonNull(CaSectionId.get(shortCircuitRatingSectionId.get(i))).setColor(Color.parseColor(ratingColors));
                            }

                            if (OhSectionId.containsKey(shortCircuitRatingSectionId.get(i))) {
                                Objects.requireNonNull(OhSectionId.get(shortCircuitRatingSectionId.get(i))).setColor(Color.parseColor(ratingColors));
                            }

                            if (UnBalSectionId.containsKey(shortCircuitRatingSectionId.get(i))) {
                                Objects.requireNonNull(UnBalSectionId.get(shortCircuitRatingSectionId.get(i))).setColor(Color.parseColor(ratingColors));
                            }

                            if (breakerSectionId.containsKey(shortCircuitRatingSectionId.get(i))) {
                                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                                Bitmap bitmap = drawableToBitmap(drawable);
                                Objects.requireNonNull(breakerSectionId.get(shortCircuitRatingSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(ratingColors)), 0, 95)));
                            }

                            if (transformerSectionId.containsKey(shortCircuitRatingSectionId.get(i))) {
                                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                                Bitmap bitmap = drawableToBitmap(drawable);
                                Objects.requireNonNull(transformerSectionId.get(shortCircuitRatingSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(ratingColors)), 0, 93)));
                            }

                            if (fuseSectionId.containsKey(shortCircuitRatingSectionId.get(i))) {
                                Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                                Objects.requireNonNull(fuseSectionId.get(shortCircuitRatingSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(ratingColors)), 0, 0)));
                            }

                            if (switchSectionId.containsKey(shortCircuitRatingSectionId.get(i))) {
                                Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                                int paddingLeft = 0; // Left padding in pixels
                                int paddingTop = 0; // Top padding in pixels
                                int paddingRight = 0; // Right padding in pixels
                                int paddingBottom = 12; // Bottom padding in pixels
                                int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                                int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                                Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                                Canvas canva = new Canvas(paddedBitmap);
                                canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                                Objects.requireNonNull(switchSectionId.get(shortCircuitRatingSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(ratingColors)), 0, 0)));
                            }

                            if (capacitorSectionId.containsKey(shortCircuitRatingSectionId.get(i))) {
                                Objects.requireNonNull(capacitorSectionId.get(shortCircuitRatingSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(ratingColors)), 90, 0)));
                            }

                            if (spotloadSectionId.containsKey(shortCircuitRatingSectionId.get(i))) {
                                if (spLineSectionList.contains(shortCircuitRatingSectionId.get(i))) {
                                    int paddingPx = 0;
                                    Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                            BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                                    Canvas canvas1 = new Canvas(paddedBitmap);
                                    canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                                    Objects.requireNonNull(spotloadSectionId.get(shortCircuitRatingSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(ratingColors)), 0, 0)));
                                } else {
                                    Objects.requireNonNull(spotloadSectionId.get(shortCircuitRatingSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(ratingColors)), 0, 35)));
                                }
                            }

                        }
                    }

                    if (shortCircuitOverVoltageSectionId != null && overVoltageColors != null && !shortCircuitOverVoltageSectionId.isEmpty()) {
                        for (int i = 0; i < shortCircuitOverVoltageSectionId.size(); i++) {

                            if (CaSectionId.containsKey(shortCircuitOverVoltageSectionId.get(i))) {
                                Objects.requireNonNull(CaSectionId.get(shortCircuitOverVoltageSectionId.get(i))).setColor(Color.parseColor(overVoltageColors));
                            }

                            if (OhSectionId.containsKey(shortCircuitOverVoltageSectionId.get(i))) {
                                Objects.requireNonNull(OhSectionId.get(shortCircuitOverVoltageSectionId.get(i))).setColor(Color.parseColor(overVoltageColors));
                            }

                            if (UnBalSectionId.containsKey(shortCircuitOverVoltageSectionId.get(i))) {
                                Objects.requireNonNull(UnBalSectionId.get(shortCircuitOverVoltageSectionId.get(i))).setColor(Color.parseColor(overVoltageColors));
                            }

                            if (breakerSectionId.containsKey(shortCircuitOverVoltageSectionId.get(i))) {
                                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                                Bitmap bitmap = drawableToBitmap(drawable);
                                Objects.requireNonNull(breakerSectionId.get(shortCircuitOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 0, 95)));
                            }

                            if (transformerSectionId.containsKey(shortCircuitOverVoltageSectionId.get(i))) {
                                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                                Bitmap bitmap = drawableToBitmap(drawable);
                                Objects.requireNonNull(transformerSectionId.get(shortCircuitOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 0, 93)));
                            }

                            if (fuseSectionId.containsKey(shortCircuitOverVoltageSectionId.get(i))) {
                                Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                                Objects.requireNonNull(fuseSectionId.get(shortCircuitOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 0, 0)));
                            }

                            if (switchSectionId.containsKey(shortCircuitOverVoltageSectionId.get(i))) {
                                Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                                int paddingLeft = 0; // Left padding in pixels
                                int paddingTop = 0; // Top padding in pixels
                                int paddingRight = 0; // Right padding in pixels
                                int paddingBottom = 12; // Bottom padding in pixels
                                int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                                int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                                Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                                Canvas canva = new Canvas(paddedBitmap);
                                canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                                Objects.requireNonNull(switchSectionId.get(shortCircuitOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(overVoltageColors)), 0, 0)));
                            }

                            if (capacitorSectionId.containsKey(shortCircuitOverVoltageSectionId.get(i))) {
                                Objects.requireNonNull(capacitorSectionId.get(shortCircuitOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overVoltageColors)), 90, 0)));
                            }

                            if (spotloadSectionId.containsKey(shortCircuitOverVoltageSectionId.get(i))) {
                                if (spLineSectionList.contains(shortCircuitOverVoltageSectionId.get(i))) {
                                    int paddingPx = 0;
                                    Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                            BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                                    Canvas canvas1 = new Canvas(paddedBitmap);
                                    canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                                    Objects.requireNonNull(spotloadSectionId.get(shortCircuitOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(overVoltageColors)), 0, 0)));
                                } else {
                                    Objects.requireNonNull(spotloadSectionId.get(shortCircuitOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(overVoltageColors)), 0, 35)));
                                }
                            }

                        }
                    }

                    if (shortCircuitUnderVoltageSectionId != null && underVoltageColors != null && !shortCircuitUnderVoltageSectionId.isEmpty()) {
                        for (int i = 0; i < shortCircuitUnderVoltageSectionId.size(); i++) {

                            if (CaSectionId.containsKey(shortCircuitUnderVoltageSectionId.get(i))) {
                                Objects.requireNonNull(CaSectionId.get(shortCircuitUnderVoltageSectionId.get(i))).setColor(Color.parseColor(underVoltageColors));
                            }

                            if (OhSectionId.containsKey(shortCircuitUnderVoltageSectionId.get(i))) {
                                Objects.requireNonNull(OhSectionId.get(shortCircuitUnderVoltageSectionId.get(i))).setColor(Color.parseColor(underVoltageColors));
                            }

                            if (UnBalSectionId.containsKey(shortCircuitUnderVoltageSectionId.get(i))) {
                                Objects.requireNonNull(UnBalSectionId.get(shortCircuitUnderVoltageSectionId.get(i))).setColor(Color.parseColor(underVoltageColors));
                            }

                            if (breakerSectionId.containsKey(shortCircuitUnderVoltageSectionId.get(i))) {
                                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                                Bitmap bitmap = drawableToBitmap(drawable);
                                Objects.requireNonNull(breakerSectionId.get(shortCircuitUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 0, 95)));
                            }

                            if (transformerSectionId.containsKey(shortCircuitUnderVoltageSectionId.get(i))) {
                                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                                Bitmap bitmap = drawableToBitmap(drawable);
                                Objects.requireNonNull(transformerSectionId.get(shortCircuitUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 0, 93)));
                            }

                            if (fuseSectionId.containsKey(shortCircuitUnderVoltageSectionId.get(i))) {
                                Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                                Objects.requireNonNull(fuseSectionId.get(shortCircuitUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 0, 0)));
                            }

                            if (switchSectionId.containsKey(shortCircuitUnderVoltageSectionId.get(i))) {
                                Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                                int paddingLeft = 0; // Left padding in pixels
                                int paddingTop = 0; // Top padding in pixels
                                int paddingRight = 0; // Right padding in pixels
                                int paddingBottom = 12; // Bottom padding in pixels
                                int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                                int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                                Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                                Canvas canva = new Canvas(paddedBitmap);
                                canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                                Objects.requireNonNull(switchSectionId.get(shortCircuitUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(underVoltageColors)), 0, 0)));
                            }

                            if (capacitorSectionId.containsKey(shortCircuitUnderVoltageSectionId.get(i))) {
                                Objects.requireNonNull(capacitorSectionId.get(shortCircuitUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(underVoltageColors)), 90, 0)));
                            }

                            if (spotloadSectionId.containsKey(shortCircuitUnderVoltageSectionId.get(i))) {
                                if (spLineSectionList.contains(shortCircuitUnderVoltageSectionId.get(i))) {
                                    int paddingPx = 0;
                                    Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                            BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                                    Canvas canvas1 = new Canvas(paddedBitmap);
                                    canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                                    Objects.requireNonNull(spotloadSectionId.get(shortCircuitUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(underVoltageColors)), 0, 0)));
                                } else {
                                    Objects.requireNonNull(spotloadSectionId.get(shortCircuitUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(underVoltageColors)), 0, 35)));
                                }
                            }

                        }
                    }

                    if (shortCircuitOverLoadSectionId != null && overloadColors != null && !shortCircuitOverLoadSectionId.isEmpty()) {
                        for (int i = 0; i < shortCircuitOverLoadSectionId.size(); i++) {

                            if (CaSectionId.containsKey(shortCircuitOverLoadSectionId.get(i))) {
                                Objects.requireNonNull(CaSectionId.get(shortCircuitOverLoadSectionId.get(i))).setColor(Color.parseColor(overloadColors));
                            }

                            if (OhSectionId.containsKey(shortCircuitOverLoadSectionId.get(i))) {
                                Objects.requireNonNull(OhSectionId.get(shortCircuitOverLoadSectionId.get(i))).setColor(Color.parseColor(overloadColors));
                            }

                            if (UnBalSectionId.containsKey(shortCircuitOverLoadSectionId.get(i))) {
                                Objects.requireNonNull(UnBalSectionId.get(shortCircuitOverLoadSectionId.get(i))).setColor(Color.parseColor(overloadColors));
                            }

                            if (breakerSectionId.containsKey(shortCircuitOverLoadSectionId.get(i))) {
                                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                                Bitmap bitmap = drawableToBitmap(drawable);
                                Objects.requireNonNull(breakerSectionId.get(shortCircuitOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 0, 95)));
                            }

                            if (transformerSectionId.containsKey(shortCircuitOverLoadSectionId.get(i))) {
                                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                                Bitmap bitmap = drawableToBitmap(drawable);
                                Objects.requireNonNull(transformerSectionId.get(shortCircuitOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 0, 93)));
                            }

                            if (fuseSectionId.containsKey(shortCircuitOverLoadSectionId.get(i))) {
                                Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                                Objects.requireNonNull(fuseSectionId.get(shortCircuitOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 0, 0)));
                            }

                            if (switchSectionId.containsKey(shortCircuitOverLoadSectionId.get(i))) {
                                Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                                int paddingLeft = 0; // Left padding in pixels
                                int paddingTop = 0; // Top padding in pixels
                                int paddingRight = 0; // Right padding in pixels
                                int paddingBottom = 12; // Bottom padding in pixels
                                int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                                int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                                Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                                Canvas canva = new Canvas(paddedBitmap);
                                canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                                Objects.requireNonNull(switchSectionId.get(shortCircuitOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(overloadColors)), 0, 0)));
                            }

                            if (capacitorSectionId.containsKey(shortCircuitOverLoadSectionId.get(i))) {
                                Objects.requireNonNull(capacitorSectionId.get(shortCircuitOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overloadColors)), 90, 0)));
                            }

                            if (spotloadSectionId.containsKey(shortCircuitOverLoadSectionId.get(i))) {
                                if (spLineSectionList.contains(shortCircuitOverLoadSectionId.get(i))) {
                                    int paddingPx = 0;
                                    Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                            BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                                    Canvas canvas1 = new Canvas(paddedBitmap);
                                    canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                                    Objects.requireNonNull(spotloadSectionId.get(shortCircuitOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(overloadColors)), 0, 0)));
                                } else {
                                    Objects.requireNonNull(spotloadSectionId.get(shortCircuitOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(overloadColors)), 0, 35)));
                                }
                            }
                        }
                    }
                } else {
                    if (CaPolylineList != null && !CaPolylineList.isEmpty()) {
                        for (int j = 0; j < CaPolylineList.size(); j++) {
                            CaPolylineList.get(j).getPaint().setColor(Color.RED);
                        }
                    }

                    if (ohPolylineList != null && !ohPolylineList.isEmpty()) {
                        for (int j = 0; j < ohPolylineList.size(); j++) {
                            ohPolylineList.get(j).getPaint().setColor(Color.BLUE);
                        }
                    }

                    if (unBalPolylineList != null && !unBalPolylineList.isEmpty()) {
                        for (int j = 0; j < unBalPolylineList.size(); j++) {
                            unBalPolylineList.get(j).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (secNodeList != null && !secNodeList.isEmpty()) {
                        for (int j = 0; j < secNodeList.size(); j++) {
                            secNodeList.get(j).getPaint().setColor(Color.BLACK);
                        }
                    }
                }

                if (tracing.getOutput() != null && tracing.getOutput().size() > 0 && !tracing.getOutput().isEmpty()) {
                    isTracing = true;

                    for (int i = 0; i < tracing.getOutput().size(); i++) {

                        if (CaSectionId.get(tracing.getOutput().get(i)) != null) {
                            Objects.requireNonNull(CaSectionId.get(tracing.getOutput().get(i))).setColor(Color.GREEN);
                        }

                        if (OhSectionId.get(tracing.getOutput().get(i)) != null) {
                            Objects.requireNonNull(OhSectionId.get(tracing.getOutput().get(i))).setColor(Color.GREEN);
                        }

                        if (UnBalSectionId.get(tracing.getOutput().get(i)) != null) {
                            Objects.requireNonNull(UnBalSectionId.get(tracing.getOutput().get(i))).setColor(Color.GREEN);
                        }

                        if (breakerSectionId.get(tracing.getOutput().get(i)) != null) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            Objects.requireNonNull(breakerSectionId.get(tracing.getOutput().get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.GREEN), 0, 95)));
                        }

                        if (transformerSectionId.get(tracing.getOutput().get(i)) != null) {
                            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                            Bitmap bitmap = drawableToBitmap(drawable);
                            Objects.requireNonNull(transformerSectionId.get(tracing.getOutput().get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.GREEN), 0, 93)));
                        }

                        if (fuseSectionId.get(tracing.getOutput().get(i)) != null) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                            Objects.requireNonNull(fuseSectionId.get(tracing.getOutput().get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.GREEN), 0, 0)));
                        }

                        if (switchSectionId.get(tracing.getOutput().get(i)) != null) {
                            Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                            int paddingLeft = 0; // Left padding in pixels
                            int paddingTop = 0; // Top padding in pixels
                            int paddingRight = 0; // Right padding in pixels
                            int paddingBottom = 12; // Bottom padding in pixels
                            int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                            int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                            Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                            Canvas canva = new Canvas(paddedBitmap);
                            canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                            Objects.requireNonNull(switchSectionId.get(tracing.getOutput().get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.GREEN), 0, 0)));
                        }

                        if (capacitorSectionId.get(tracing.getOutput().get(i)) != null) {
                            Objects.requireNonNull(capacitorSectionId.get(tracing.getOutput().get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.GREEN), 90, 0)));
                        }

                        if (spotloadSectionId.get(tracing.getOutput().get(i)) != null) {
                            if (spLineSectionList.contains(tracing.getOutput().get(i))) {
                                int paddingPx = 0;
                                Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                        BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                                Canvas canvas1 = new Canvas(paddedBitmap);
                                canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                                Objects.requireNonNull(spotloadSectionId.get(tracing.getOutput().get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.GREEN), 0, 0)));
                            } else {
                                Objects.requireNonNull(spotloadSectionId.get(tracing.getOutput().get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.GREEN), 0, 35)));
                            }
                        }

                        if (!secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(tracing.getOutput().get(i)) != null) {
                                Objects.requireNonNull(secNodeSectionId.get(tracing.getOutput().get(i))).getPaint().setColor(Color.GREEN);
                            }
                        }

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBarLayout.setVisibility(View.GONE);
            binding.map.invalidate();
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class DefaultColor extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarLayout.setVisibility(View.VISIBLE);
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(Void... voids) {
            try {
                for (int i = 0; i < CaPolylineList.size(); i++) {
                    CaPolylineList.get(i).setColor(Color.BLACK);
                }

                for (int i = 0; i < ohPolylineList.size(); i++) {
                    ohPolylineList.get(i).setColor(Color.BLACK);
                }

                for (int i = 0; i < unBalPolylineList.size(); i++) {
                    unBalPolylineList.get(i).setColor(Color.BLACK);
                }

                for (int i = 0; i < secNodeList.size(); i++) {
                    secNodeList.get(i).setColor(Color.BLACK);
                }

                for (int i = 0; i < breakerList.size(); i++) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    breakerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 90, 0)));
                }

                for (int i = 0; i < transformerList.size(); i++) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    transformerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 90)));
                }

                for (int i = 0; i < fuseList.size(); i++) {
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                    fuseList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 90)));
                }

                for (int i = 0; i < switchedList.size(); i++) {
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                    int paddingLeft = 0; // Left padding in pixels
                    int paddingTop = 0; // Top padding in pixels
                    int paddingRight = 0; // Right padding in pixels
                    int paddingBottom = 12; // Bottom padding in pixels
                    int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                    int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                    Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                    Canvas canva = new Canvas(paddedBitmap);
                    canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                    switchedList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.BLACK), 90, 0)));
                }

                for (int i = 0; i < capacitorList.size(); i++) {
                    capacitorList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.BLACK), 0, 0)));
                }

                for (int i = 0; i < spotLoadList.size(); i++) {
                    int paddingPx = 0;
                    Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                            BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                    Canvas canvas1 = new Canvas(paddedBitmap);
                    canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                    spotLoadList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.BLACK), 0, 70)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBarLayout.setVisibility(View.GONE);
            binding.map.invalidate();
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class LayerColor extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarLayout.setVisibility(View.VISIBLE);
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(Void... voids) {
            try {
                for (int i = 0; i < CaPolylineList.size(); i++) {
                    CaPolylineList.get(i).setColor(Color.RED);
                }

                for (int i = 0; i < ohPolylineList.size(); i++) {
                    ohPolylineList.get(i).setColor(Color.BLUE);
                }

                for (int i = 0; i < unBalPolylineList.size(); i++) {
                    unBalPolylineList.get(i).setColor(Color.BLACK);
                }

                for (int i = 0; i < secNodeList.size(); i++) {
                    secNodeList.get(i).setColor(Color.BLACK);
                }

                for (int i = 0; i < breakerList.size(); i++) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    breakerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 90, 0)));
                }

                for (int i = 0; i < transformerList.size(); i++) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    transformerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 90)));
                }

                for (int i = 0; i < fuseList.size(); i++) {
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                    fuseList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 90)));
                }

                for (int i = 0; i < switchedList.size(); i++) {
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                    int paddingLeft = 0; // Left padding in pixels
                    int paddingTop = 0; // Top padding in pixels
                    int paddingRight = 0; // Right padding in pixels
                    int paddingBottom = 12; // Bottom padding in pixels
                    int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                    int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                    Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                    Canvas canva = new Canvas(paddedBitmap);
                    canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                    switchedList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.BLACK), 90, 0)));
                }

                for (int i = 0; i < capacitorList.size(); i++) {
                    capacitorList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.BLACK), 0, 0)));
                }

                for (int i = 0; i < spotLoadList.size(); i++) {
                    int paddingPx = 0;
                    Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                            BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                    Canvas canvas1 = new Canvas(paddedBitmap);
                    canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                    spotLoadList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.BLACK), 0, 70)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBarLayout.setVisibility(View.GONE);
            binding.map.invalidate();
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class PhaseColor extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarLayout.setVisibility(View.VISIBLE);
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(Void... voids) {
            try {
                for (int i = 0; i < CaPolylineList.size(); i++) {
                    String str = CaPolylineList.get(i).getSubDescription();
                    String ReplaceString = str.replace("<br>", "\",\"");
                    String ReplaceStr = ReplaceString.replace("=", "\":\"");
                    String st = ReplaceStr.replaceAll("^\"|,\"$", "}");
                    String str1 = st.replaceFirst("", "{\"");

                    JSONObject jsonObject = new JSONObject(str1);

                    if (jsonObject.getString("\nPhase").equals("1")) {
                        CaPolylineList.get(i).setColor(Color.parseColor("#000000"));
                    } else if (jsonObject.getString("\nPhase").equals("2")) {
                        CaPolylineList.get(i).setColor(Color.parseColor("#ff0000"));
                    } else if (jsonObject.getString("\nPhase").equals("3")) {
                        CaPolylineList.get(i).setColor(Color.parseColor("#0000ff"));
                    } else if (jsonObject.getString("\nPhase").equals("4")) {
                        CaPolylineList.get(i).setColor(Color.parseColor("#008000"));
                    } else if (jsonObject.getString("\nPhase").equals("5")) {
                        CaPolylineList.get(i).setColor(Color.parseColor("#c50ceb"));
                    } else if (jsonObject.getString("\nPhase").equals("6")) {
                        CaPolylineList.get(i).setColor(Color.parseColor("#a52a2a"));
                    } else {
                        CaPolylineList.get(i).setColor(Color.parseColor("#ffaa10"));
                    }
                }

                for (int i = 0; i < ohPolylineList.size(); i++) {
                    String str = ohPolylineList.get(i).getSubDescription();
                    String ReplaceString = str.replace("<br>", "\",\"");
                    String ReplaceStr = ReplaceString.replace("=", "\":\"");
                    String st = ReplaceStr.replaceAll("^\"|,\"$", "}");
                    String str1 = st.replaceFirst("", "{\"");

                    JSONObject jsonObject = new JSONObject(str1);

                    if (jsonObject.getString("\nPhase").equals("1")) {
                        ohPolylineList.get(i).setColor(Color.parseColor("#000000"));
                    } else if (jsonObject.getString("\nPhase").equals("2")) {
                        ohPolylineList.get(i).setColor(Color.parseColor("#ff0000"));
                    } else if (jsonObject.getString("\nPhase").equals("3")) {
                        ohPolylineList.get(i).setColor(Color.parseColor("#0000ff"));
                    } else if (jsonObject.getString("\nPhase").equals("4")) {
                        ohPolylineList.get(i).setColor(Color.parseColor("#008000"));
                    } else if (jsonObject.getString("\nPhase").equals("5")) {
                        ohPolylineList.get(i).setColor(Color.parseColor("#c50ceb"));
                    } else if (jsonObject.getString("\nPhase").equals("6")) {
                        ohPolylineList.get(i).setColor(Color.parseColor("#a52a2a"));
                    } else {
                        ohPolylineList.get(i).setColor(Color.parseColor("#ffaa10"));
                    }
                }

                for (int i = 0; i < unBalPolylineList.size(); i++) {
                    String str = unBalPolylineList.get(i).getSubDescription();
                    String ReplaceString = str.replace("<br>", "\",\"");
                    String ReplaceStr = ReplaceString.replace("=", "\":\"");
                    String st = ReplaceStr.replaceAll("^\"|,\"$", "}");
                    String str1 = st.replaceFirst("", "{\"");
                    JSONObject jsonObject = new JSONObject(str1);

                    if (jsonObject.getString("\nPhase").equals("1")) {
                        unBalPolylineList.get(i).setColor(Color.parseColor("#000000"));
                    } else if (jsonObject.getString("\nPhase").equals("2")) {
                        unBalPolylineList.get(i).setColor(Color.parseColor("#ff0000"));
                    } else if (jsonObject.getString("\nPhase").equals("3")) {
                        unBalPolylineList.get(i).setColor(Color.parseColor("#0000ff"));
                    } else if (jsonObject.getString("\nPhase").equals("4")) {
                        unBalPolylineList.get(i).setColor(Color.parseColor("#008000"));
                    } else if (jsonObject.getString("\nPhase").equals("5")) {
                        unBalPolylineList.get(i).setColor(Color.parseColor("#c50ceb"));
                    } else if (jsonObject.getString("\nPhase").equals("6")) {
                        unBalPolylineList.get(i).setColor(Color.parseColor("#a52a2a"));
                    } else {
                        unBalPolylineList.get(i).setColor(Color.parseColor("#ffaa10"));
                    }

                }

                for (int i = 0; i < secNodeList.size(); i++) {
                    String str = secNodeList.get(i).getSubDescription();
                    String ReplaceString = str.replace("<br>", "\",\"");
                    String ReplaceStr = ReplaceString.replace("=", "\":\"");
                    String st = ReplaceStr.replaceAll("^\"|,\"$", "}");
                    String str1 = st.replaceFirst("", "{\"");
                    JSONObject jsonObject = new JSONObject(str1);

                    if (jsonObject.getString("\nPhase").equals("1")) {
                        secNodeList.get(i).setColor(Color.parseColor("#000000"));
                    } else if (jsonObject.getString("\nPhase").equals("2")) {
                        secNodeList.get(i).setColor(Color.parseColor("#ff0000"));
                    } else if (jsonObject.getString("\nPhase").equals("3")) {
                        secNodeList.get(i).setColor(Color.parseColor("#0000ff"));
                    } else if (jsonObject.getString("\nPhase").equals("4")) {
                        secNodeList.get(i).setColor(Color.parseColor("#008000"));
                    } else if (jsonObject.getString("\nPhase").equals("5")) {
                        secNodeList.get(i).setColor(Color.parseColor("#c50ceb"));
                    } else if (jsonObject.getString("\nPhase").equals("6")) {
                        secNodeList.get(i).setColor(Color.parseColor("#a52a2a"));
                    } else {
                        secNodeList.get(i).setColor(Color.parseColor("#ffaa10"));
                    }
                }

                for (int i = 0; i < breakerList.size(); i++) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                    Bitmap bitmap = drawableToBitmap(drawable);

                    String str = breakerList.get(i).getSubDescription();
                    String ReplaceString = str.replace("<br>", "\",\"");
                    String ReplaceStr = ReplaceString.replace("=", "\":\"");
                    String st = ReplaceStr.replaceAll("^\"|,\"$", "}");
                    String str1 = st.replaceFirst("", "{\"");
                    JSONObject jsonObject = new JSONObject(str1);

                    if (jsonObject.getString("\nPhase").equals("1")) {
                        breakerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#000000")), 90, 0)));
                    } else if (jsonObject.getString("\nPhase").equals("2")) {
                        breakerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#ff0000")), 90, 0)));
                    } else if (jsonObject.getString("\nPhase").equals("3")) {
                        breakerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#0000ff")), 90, 0)));
                    } else if (jsonObject.getString("\nPhase").equals("4")) {
                        breakerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#008000")), 90, 0)));
                    } else if (jsonObject.getString("\nPhase").equals("5")) {
                        breakerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#c50ceb")), 90, 0)));
                    } else if (jsonObject.getString("\nPhase").equals("6")) {
                        breakerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#a52a2a")), 90, 0)));
                    } else {
                        breakerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#ffaa10")), 90, 0)));
                    }
                }

                for (int i = 0; i < transformerList.size(); i++) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                    Bitmap bitmap = drawableToBitmap(drawable);

                    String str = transformerList.get(i).getSubDescription();
                    String ReplaceString = str.replace("<br>", "\",\"");
                    String ReplaceStr = ReplaceString.replace("=", "\":\"");
                    String st = ReplaceStr.replaceAll("^\"|,\"$", "}");
                    String str1 = st.replaceFirst("", "{\"");
                    JSONObject jsonObject = new JSONObject(str1);

                    if (jsonObject.getString("\nPhase").equals("1")) {
                        transformerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#000000")), 0, 90)));
                    } else if (jsonObject.getString("\nPhase").equals("2")) {
                        transformerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#ff0000")), 0, 90)));
                    } else if (jsonObject.getString("\nPhase").equals("3")) {
                        transformerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#0000ff")), 0, 90)));
                    } else if (jsonObject.getString("\nPhase").equals("4")) {
                        transformerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#008000")), 0, 90)));
                    } else if (jsonObject.getString("\nPhase").equals("5")) {
                        transformerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#c50ceb")), 0, 90)));
                    } else if (jsonObject.getString("\nPhase").equals("6")) {
                        transformerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#a52a2a")), 0, 90)));
                    } else {
                        transformerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#ffaa10")), 0, 90)));
                    }
                }

                for (int i = 0; i < fuseList.size(); i++) {
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                    String str = fuseList.get(i).getSubDescription();
                    String ReplaceString = str.replace("<br>", "\",\"");
                    String ReplaceStr = ReplaceString.replace("=", "\":\"");
                    String st = ReplaceStr.replaceAll("^\"|,\"$", "}");
                    String str1 = st.replaceFirst("", "{\"");
                    JSONObject jsonObject = new JSONObject(str1);

                    if (jsonObject.getString("\nPhase").equals("1")) {
                        fuseList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#000000")), 0, 90)));
                    } else if (jsonObject.getString("\nPhase").equals("2")) {
                        fuseList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#ff0000")), 0, 90)));
                    } else if (jsonObject.getString("\nPhase").equals("3")) {
                        fuseList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#0000ff")), 0, 90)));
                    } else if (jsonObject.getString("\nPhase").equals("4")) {
                        fuseList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#008000")), 0, 90)));
                    } else if (jsonObject.getString("\nPhase").equals("5")) {
                        fuseList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#c50ceb")), 0, 90)));
                    } else if (jsonObject.getString("\nPhase").equals("6")) {
                        fuseList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#a52a2a")), 0, 90)));
                    } else {
                        fuseList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#ffaa10")), 0, 90)));
                    }
                }

                for (int i = 0; i < switchedList.size(); i++) {
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                    int paddingLeft = 0; // Left padding in pixels
                    int paddingTop = 0; // Top padding in pixels
                    int paddingRight = 0; // Right padding in pixels
                    int paddingBottom = 12; // Bottom padding in pixels
                    int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                    int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                    Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                    Canvas canva = new Canvas(paddedBitmap);
                    canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);

                    String str = switchedList.get(i).getSubDescription();
                    String ReplaceString = str.replace("<br>", "\",\"");
                    String ReplaceStr = ReplaceString.replace("=", "\":\"");
                    String st = ReplaceStr.replaceAll("^\"|,\"$", "}");
                    String str1 = st.replaceFirst("", "{\"");
                    JSONObject jsonObject = new JSONObject(str1);

                    if (jsonObject.getString("\nPhase").equals("1")) {
                        switchedList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor("#000000")), 90, 0)));
                    } else if (jsonObject.getString("\nPhase").equals("2")) {
                        switchedList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor("#ff0000")), 90, 0)));
                    } else if (jsonObject.getString("\nPhase").equals("3")) {
                        switchedList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor("#0000ff")), 90, 0)));
                    } else if (jsonObject.getString("\nPhase").equals("4")) {
                        switchedList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor("#008000")), 90, 0)));
                    } else if (jsonObject.getString("\nPhase").equals("5")) {
                        switchedList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor("#c50ceb")), 90, 0)));
                    } else if (jsonObject.getString("\nPhase").equals("6")) {
                        switchedList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor("#a52a2a")), 90, 0)));
                    } else {
                        switchedList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor("#ffaa10")), 90, 0)));
                    }

                }

                for (int i = 0; i < capacitorList.size(); i++) {
                    String str = capacitorList.get(i).getSubDescription();
                    String ReplaceString = str.replace("<br>", "\",\"");
                    String ReplaceStr = ReplaceString.replace("=", "\":\"");
                    String st = ReplaceStr.replaceAll("^\"|,\"$", "}");
                    String str1 = st.replaceFirst("", "{\"");
                    JSONObject jsonObject = new JSONObject(str1);

                    if (jsonObject.getString("\nPhase").equals("1")) {
                        capacitorList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor("#000000")), 0, 0)));
                    } else if (jsonObject.getString("\nPhase").equals("2")) {
                        capacitorList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor("#ff0000")), 0, 0)));
                    } else if (jsonObject.getString("\nPhase").equals("3")) {
                        capacitorList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor("#0000ff")), 0, 0)));
                    } else if (jsonObject.getString("\nPhase").equals("4")) {
                        capacitorList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor("#008000")), 0, 0)));
                    } else if (jsonObject.getString("\nPhase").equals("5")) {
                        capacitorList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor("#c50ceb")), 0, 0)));
                    } else if (jsonObject.getString("\nPhase").equals("6")) {
                        capacitorList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor("#a52a2a")), 0, 0)));
                    } else {
                        capacitorList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor("#ffaa10")), 0, 0)));
                    }
                }

                for (int i = 0; i < spotLoadList.size(); i++) {
                    int paddingPx = 0;
                    Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                            BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                    Canvas canvas1 = new Canvas(paddedBitmap);
                    canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);

                    String str = spotLoadList.get(i).getSubDescription();
                    String ReplaceString = str.replace("<br>", "\",\"");
                    String ReplaceStr = ReplaceString.replace("=", "\":\"");
                    String st = ReplaceStr.replaceAll("^\"|,\"$", "}");
                    String str1 = st.replaceFirst("", "{\"");
                    JSONObject jsonObject = new JSONObject(str1);

                    if (jsonObject.getString("\nPhase").equals("1")) {
                        spotLoadList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor("#000000")), 0, 70)));
                    } else if (jsonObject.getString("\nPhase").equals("2")) {
                        spotLoadList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor("#ff0000")), 0, 70)));
                    } else if (jsonObject.getString("\nPhase").equals("3")) {
                        spotLoadList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor("#0000ff")), 0, 70)));
                    } else if (jsonObject.getString("\nPhase").equals("4")) {
                        spotLoadList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor("#008000")), 0, 70)));
                    } else if (jsonObject.getString("\nPhase").equals("5")) {
                        spotLoadList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor("#c50ceb")), 0, 70)));
                    } else if (jsonObject.getString("\nPhase").equals("6")) {
                        spotLoadList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor("#a52a2a")), 0, 70)));
                    } else {
                        spotLoadList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor("#ffaa10")), 0, 70)));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBarLayout.setVisibility(View.GONE);
            binding.map.invalidate();
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class SystemVoltage extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarLayout.setVisibility(View.VISIBLE);
        }

        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(Void... voids) {
            try {
                for (int i = 0; i < CaPolylineList.size(); i++) {

                    String str = CaPolylineList.get(i).getSubDescription();
                    String ReplaceString = str.replace("<br>", "\",\"");
                    String ReplaceStr = ReplaceString.replace("=", "\":\"");
                    String st = ReplaceStr.replaceAll("^\"|,\"$", "}");
                    String str1 = st.replaceFirst("", "{\"");
                    JSONObject jsonObject = new JSONObject(str1);

                    if (jsonObject.getString("\nVoltage").equals("11")) {
                        CaPolylineList.get(i).setColor(Color.RED);
                    } else {
                        CaPolylineList.get(i).setColor(Color.BLUE);
                    }

                }

                for (int i = 0; i < ohPolylineList.size(); i++) {
                    String str = ohPolylineList.get(i).getSubDescription();
                    String ReplaceString = str.replace("<br>", "\",\"");
                    String ReplaceStr = ReplaceString.replace("=", "\":\"");
                    String st = ReplaceStr.replaceAll("^\"|,\"$", "}");
                    String str1 = st.replaceFirst("", "{\"");
                    JSONObject jsonObject = new JSONObject(str1);

                    if (jsonObject.getString("\nVoltage").equals("11")) {
                        ohPolylineList.get(i).setColor(Color.RED);
                    } else {
                        ohPolylineList.get(i).setColor(Color.BLUE);
                    }

                }

                for (int i = 0; i < unBalPolylineList.size(); i++) {

                    String str = unBalPolylineList.get(i).getSubDescription();
                    String ReplaceString = str.replace("<br>", "\",\"");
                    String ReplaceStr = ReplaceString.replace("=", "\":\"");
                    String st = ReplaceStr.replaceAll("^\"|,\"$", "}");
                    String str1 = st.replaceFirst("", "{\"");
                    JSONObject jsonObject = new JSONObject(str1);

                    if (jsonObject.getString("\nVoltage").equals("11")) {
                        unBalPolylineList.get(i).setColor(Color.RED);
                    } else {
                        unBalPolylineList.get(i).setColor(Color.BLUE);
                    }

                }

                for (int i = 0; i < secNodeList.size(); i++) {

                    String str = secNodeList.get(i).getSubDescription();
                    String ReplaceString = str.replace("<br>", "\",\"");
                    String ReplaceStr = ReplaceString.replace("=", "\":\"");
                    String st = ReplaceStr.replaceAll("^\"|,\"$", "}");
                    String str1 = st.replaceFirst("", "{\"");
                    JSONObject jsonObject = new JSONObject(str1);

                    if (jsonObject.getString("\nVoltage").equals("11")) {
                        secNodeList.get(i).setColor(Color.RED);
                    } else {
                        secNodeList.get(i).setColor(Color.BLUE);
                    }

                }

                for (int i = 0; i < breakerList.size(); i++) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                    Bitmap bitmap = drawableToBitmap(drawable);

                    String str = breakerList.get(i).getSubDescription();
                    String ReplaceString = str.replace("<br>", "\",\"");
                    String ReplaceStr = ReplaceString.replace("=", "\":\"");
                    String st = ReplaceStr.replaceAll("^\"|,\"$", "}");
                    String str1 = st.replaceFirst("", "{\"");
                    JSONObject jsonObject = new JSONObject(str1);

                    if (jsonObject.getString("\nVoltage").equals("11")) {
                        breakerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.RED), 90, 0)));
                    } else {
                        breakerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLUE), 90, 0)));
                    }

                }

                for (int i = 0; i < transformerList.size(); i++) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                    Bitmap bitmap = drawableToBitmap(drawable);

                    String str = transformerList.get(i).getSubDescription();
                    String ReplaceString = str.replace("<br>", "\",\"");
                    String ReplaceStr = ReplaceString.replace("=", "\":\"");
                    String st = ReplaceStr.replaceAll("^\"|,\"$", "}");
                    String str1 = st.replaceFirst("", "{\"");
                    JSONObject jsonObject = new JSONObject(str1);

                    if (jsonObject.getString("\nVoltage").equals("11")) {
                        transformerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.RED), 0, 90)));
                    } else {
                        transformerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLUE), 0, 90)));
                    }

                }

                for (int i = 0; i < fuseList.size(); i++) {
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                    String str = fuseList.get(i).getSubDescription();
                    String ReplaceString = str.replace("<br>", "\",\"");
                    String ReplaceStr = ReplaceString.replace("=", "\":\"");
                    String st = ReplaceStr.replaceAll("^\"|,\"$", "}");
                    String str1 = st.replaceFirst("", "{\"");
                    JSONObject jsonObject = new JSONObject(str1);

                    if (jsonObject.getString("\nVoltage").equals("11")) {
                        fuseList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.RED), 0, 90)));
                    } else {
                        fuseList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLUE), 0, 90)));
                    }

                }

                for (int i = 0; i < switchedList.size(); i++) {
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                    int paddingLeft = 0; // Left padding in pixels
                    int paddingTop = 0; // Top padding in pixels
                    int paddingRight = 0; // Right padding in pixels
                    int paddingBottom = 12; // Bottom padding in pixels
                    int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                    int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                    Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                    Canvas canva = new Canvas(paddedBitmap);
                    canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);

                    String str = switchedList.get(i).getSubDescription();
                    String ReplaceString = str.replace("<br>", "\",\"");
                    String ReplaceStr = ReplaceString.replace("=", "\":\"");
                    String st = ReplaceStr.replaceAll("^\"|,\"$", "}");
                    String str1 = st.replaceFirst("", "{\"");
                    JSONObject jsonObject = new JSONObject(str1);

                    if (jsonObject.getString("\nVoltage").equals("11")) {
                        switchedList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.RED), 90, 0)));
                    } else {
                        switchedList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.BLUE), 90, 0)));
                    }

                }

            } catch (Exception e) {
                Log.d("Exception", e.getLocalizedMessage());;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBarLayout.setVisibility(View.GONE);
            binding.map.invalidate();
        }

    }

    private void getTopology(JsonArray jsonArray) {
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("NetworkId", jsonArray);
        jsonObject.addProperty("UserType", prefManager.getUserType());
        jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
        Call<Topology> call = apiInterface.getTopologyData("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<Topology>() {
            @Override
            public void onResponse(@NonNull Call<Topology> call, @NonNull Response<Topology> response) {
                if (response.code() == 200) {
                    ErrorPdfLogger.logApiSuccess(MapActivity.this, "POST", "topology/", "HTTP " + response.code() + " Topology: " + response.message()
                            + "NetworkId : " + jsonArray
                            + "UserType : " +  prefManager.getUserType()
                            + "CYMDBNET : " +  prefManager.getDBName()
                            + "AccessToken : " + prefManager.getAccessToken());
                    try {
                        isTopology = true;
                        Topology topology = response.body();
                        assert topology != null;
                        if (topology.getOutput().getDisconnected() != null) {
                            topology.getOutput().getDisconnected().size();
                            ArrayList<DType> list = new ArrayList<>();
                            DType type;
                            for (int i = 0; i < topology.getOutput().getDisconnected().size(); i++) {
                                type = new DType(topology.getOutput().getDisconnected().get(i), 40);
                                list.add(type);
                            }
                            Continent continent = new Continent("disconnected" + " " + "(" + list.size() + ")", list);
                            continentList.add(continent);
                        }

                        if (topology.getOutput().getInterconnection() != null) {
                            topology.getOutput().getInterconnection().size();
                            ArrayList<DType> list = new ArrayList<>();
                            DType type;
                            for (int i = 0; i < topology.getOutput().getInterconnection().size(); i++) {
                                type = new DType(topology.getOutput().getInterconnection().get(i), 41);
                                list.add(type);
                            }
                            Continent continent = new Continent("Interconnection" + " " + "(" + list.size() + ")", list);
                            continentList.add(continent);
                        }

                        if (topology.getOutput().getIsolated() != null) {
                            topology.getOutput().getIsolated().size();
                            ArrayList<DType> list = new ArrayList<>();
                            DType type;
                            for (int i = 0; i < topology.getOutput().getIsolated().size(); i++) {
                                type = new DType(topology.getOutput().getIsolated().get(i), 40);
                                list.add(type);
                            }
                            Continent continent = new Continent("Isolated" + " " + "(" + list.size() + ")", list);
                            continentList.add(continent);
                        }

                        if (topology.getOutput().getLoop() != null) {
                            topology.getOutput().getLoop().size();
                            ArrayList<DType> list = new ArrayList<>();
                            DType type;
                            for (int i = 0; i < topology.getOutput().getLoop().size(); i++) {
                                type = new DType(topology.getOutput().getLoop().get(i), 41);
                                list.add(type);
                            }
                            Continent continent = new Continent("Loop" + " " + "(" + list.size() + ")", list);
                            continentList.add(continent);
                        }

                        if (topology.getOutput().getSourcenode() != null) {
                            topology.getOutput().getSourcenode().size();
                            ArrayList<DType> list = new ArrayList<>();
                            DType type;
                            for (int i = 0; i < topology.getOutput().getSourcenode().size(); i++) {
                                type = new DType(topology.getOutput().getSourcenode().get(i), 41);
                                list.add(type);
                            }
                            Continent continent = new Continent("source Node" + " " + "(" + list.size() + ")", list);
                            continentList.add(continent);
                        }
                        adapter = new ExpandableDeviceAdapter(MapActivity.this, continentList);
                        binding.navigationmenu.setAdapter(adapter);
                    } catch (Exception e) {
                        ErrorPdfLogger.logCrash(MapActivity.this,e);
                        Log.d("Exception", Objects.requireNonNull(e.getLocalizedMessage()));
                    }
                } else if (response.code() == 401) {
                    ErrorPdfLogger.logApiError(MapActivity.this, "POST", "topology/", "HTTP " + response.code() + " Topology: " + response.message()
                            + "NetworkId : " + jsonArray
                            + "UserType : " +  prefManager.getUserType()
                            + "CYMDBNET : " +  prefManager.getDBName()
                            + "AccessToken : " + prefManager.getAccessToken());
                    prefManager.setIsUserLogin(false);
                    startActivity(new Intent(MapActivity.this, LoginActivity.class));
                    finish();
                } else {
                    ErrorPdfLogger.logApiError(MapActivity.this, "POST", "topology/", "HTTP " + response.code() + " Topology: " + response.message()
                            + "NetworkId : " + jsonArray
                            + "UserType : " +  prefManager.getUserType()
                            + "CYMDBNET : " +  prefManager.getDBName()
                            + "AccessToken : " + prefManager.getAccessToken());
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), response.message() + " - " + response.code(), Snackbar.LENGTH_LONG);
                    snack.show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Topology> call, @NonNull Throwable t) {
                ErrorPdfLogger.logApiFailure(MapActivity.this, "POST", "topology/", t);
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_msg), Snackbar.LENGTH_LONG);
                snack.show();
            }
        });
    }

    private class ExpandableDeviceAdapter extends BaseExpandableListAdapter {

        private final Context context;
        private final ArrayList<Continent> originalList;
        private final ArrayList<NavigationSection> navigationSections = new ArrayList<>();
        private final HashSet<String> expandedKeys = new HashSet<>();

        public ExpandableDeviceAdapter(Context context, ArrayList<Continent> continentList) {
            this.context = context;
            this.originalList = new ArrayList<Continent>();
            this.originalList.addAll(continentList);
            buildSections(continentList);
        }

        @Override
        public int getGroupCount() {
            return navigationSections.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return navigationSections.get(groupPosition).getChildren().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return navigationSections.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return navigationSections.get(groupPosition).getChildren().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            NavigationSection navigationSection = (NavigationSection) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.row_first, null);
            }
            TextView listTitleTextView = convertView.findViewById(R.id.first_tv);
            TextView countTextView = convertView.findViewById(R.id.first_count_tv);
            listTitleTextView.setTypeface(null, Typeface.BOLD);
            listTitleTextView.setTextSize(16);
            listTitleTextView.setTextColor(MapActivity.this.getColor(R.color.blue));
            listTitleTextView.setText(navigationSection.getTitle());
            countTextView.setText("(" + navigationSection.getChildren().size() + ")");
            countTextView.setVisibility(View.VISIBLE);
            return convertView;
        }

        @SuppressLint({"InflateParams", "UseCompatLoadingForDrawables"})
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.row_second, null);
            }
            TextView tv = convertView.findViewById(R.id.row_second_tv);
            TextView dTypeTv = convertView.findViewById(R.id.dType_tv);
            ImageView img = convertView.findViewById(R.id.arro_imageview);
            ItemViewHelper recyclerView = convertView.findViewById(R.id.main_recyclervie);
            View childContainer = convertView.findViewById(R.id.child_row_container);
            View expandableLayout = convertView.findViewById(R.id.expandable_layout);
            Object child = getChild(groupPosition, childPosition);

            tv.setTypeface(null, Typeface.BOLD);
            recyclerView.setNestedScrollingEnabled(true);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(null);
            recyclerView.setRecycledViewPool(drawerRecycledViewPool);
            if (recyclerView.getLayoutManager() == null) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
            recyclerView.setMaxHeightPx((int) (280 * context.getResources().getDisplayMetrics().density));
            int rowIndent = (int) (18 * context.getResources().getDisplayMetrics().density);
            int nestedIndent = (int) (14 * context.getResources().getDisplayMetrics().density);
            childContainer.setPadding(rowIndent, childContainer.getPaddingTop(), childContainer.getPaddingRight(), childContainer.getPaddingBottom());
            expandableLayout.setPadding(nestedIndent, expandableLayout.getPaddingTop(), expandableLayout.getPaddingRight(), expandableLayout.getPaddingBottom());

            if (child instanceof DType) {
                DType type = (DType) child;
                tv.setText(type.getName());
                dTypeTv.setText(String.valueOf(type.getType()));
                img.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                childContainer.setOnClickListener(view -> openNavigationItem(type.getName(), String.valueOf(type.getType())));
            } else if (child instanceof FeederTreeNode) {
                FeederTreeNode feederTreeNode = (FeederTreeNode) child;
                String key = groupPosition + "_" + childPosition + "_" + feederTreeNode.getTitle();
                tv.setText(feederTreeNode.getTitle());
                dTypeTv.setText("");
                img.setVisibility(View.VISIBLE);
                img.setRotation(expandedKeys.contains(key) ? 90f : 0f);
                recyclerView.setAdapter(new NestedDeviceAdapter(new ArrayList<>(feederTreeNode.getChildren()), key));
                recyclerView.setVisibility(expandedKeys.contains(key) ? View.VISIBLE : View.GONE);
                childContainer.setOnClickListener(view -> {
                    if (!feederTreeNode.isChildrenLoaded()) {
                        getFeederGroupData(feederTreeNode, () -> {
                            recyclerView.setAdapter(new NestedDeviceAdapter(new ArrayList<>(feederTreeNode.getChildren()), key));
                            expandedKeys.add(key);
                            recyclerView.setVisibility(View.VISIBLE);
                            img.animate().rotation(90f).setDuration(180).start();
                        });
                    } else if (expandedKeys.contains(key)) {
                        expandedKeys.remove(key);
                        recyclerView.setVisibility(View.GONE);
                        img.animate().rotation(0f).setDuration(180).start();
                    } else {
                        expandedKeys.add(key);
                        recyclerView.setVisibility(View.VISIBLE);
                        img.animate().rotation(90f).setDuration(180).start();
                    }
                });
            } else {
                Continent continent = (Continent) child;
                String key = groupPosition + "_" + childPosition;
                boolean expanded = expandedKeys.contains(key);
                tv.setText(continent.getName());
                dTypeTv.setText("");
                img.setVisibility(View.VISIBLE);
                img.setRotation(expanded ? 90f : 0f);
                recyclerView.setAdapter(new NestedDeviceAdapter(new ArrayList<>(continent.getDeviceList()), key));
                recyclerView.setVisibility(expanded ? View.VISIBLE : View.GONE);
                childContainer.setOnClickListener(view -> {
                    if (expandedKeys.contains(key)) {
                        expandedKeys.remove(key);
                        recyclerView.setVisibility(View.GONE);
                        img.animate().rotation(0f).setDuration(180).start();
                    } else {
                        expandedKeys.add(key);
                        recyclerView.setVisibility(View.VISIBLE);
                        img.animate().rotation(90f).setDuration(180).start();
                    }
                });
            }

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        public void filterData(String query) {
            query = query.toLowerCase();
            ArrayList<Continent> filteredList = new ArrayList<>();
            if (query.isEmpty()) {
                filteredList.addAll(originalList);
            } else {
                for (Continent continent : originalList) {
                    ArrayList<DType> deviceList = continent.getDeviceList();
                    ArrayList<DType> filteredDevices = new ArrayList<DType>();
                    if (continent.getName().toLowerCase().contains(query)) {
                        filteredDevices.addAll(deviceList);
                    } else {
                        for (DType device : deviceList) {
                            if (device.getName().toLowerCase().contains(query)) {
                                filteredDevices.add(device);
                            }
                        }
                    }
                    if (filteredDevices.size() > 0) {
                        filteredList.add(new Continent(continent.getName(), filteredDevices));
                    }
                }
            }
            expandedKeys.clear();
            nestedExpandedKeys.clear();
            buildSections(filteredList);
            notifyDataSetChanged();
        }

        private void buildSections(ArrayList<Continent> flatList) {
            navigationSections.clear();

            ArrayList<DType> networkItems = new ArrayList<>();
            ArrayList<Object> topologyItems = new ArrayList<>();
            ArrayList<Object> deviceItems = new ArrayList<>();

            for (Continent continent : flatList) {
                if (continent == null || continent.getName() == null) {
                    continue;
                }

                if (continent.getName().contains("Feeder ID")) {
                    networkItems.addAll(continent.getDeviceList());
                } else if (continent.getName().toLowerCase().contains("disconnected")
                        || continent.getName().contains("Interconnection")
                        || continent.getName().contains("Isolated")
                        || continent.getName().contains("Loop")
                        || continent.getName().toLowerCase().contains("source node")) {
                    topologyItems.add(new Continent(continent.getName(), new ArrayList<>(continent.getDeviceList())));
                } else {
                    deviceItems.add(new Continent(continent.getName(), new ArrayList<>(continent.getDeviceList())));
                }
            }

            ArrayList<Object> networkChildren = new ArrayList<>();
            if (!feederNavigationItems.isEmpty()) {
                networkChildren.addAll(feederNavigationItems);
            } else {
                networkChildren.addAll(networkItems);
            }
            navigationSections.add(new NavigationSection("Feeder", networkChildren));
            navigationSections.add(new NavigationSection("Devices", deviceItems));
            navigationSections.add(new NavigationSection("Topology", topologyItems));
        }
    }

    private class NestedDeviceAdapter extends RecyclerView.Adapter<NestedDeviceAdapter.NestedDeviceViewHolder> {

        private final ArrayList<Object> deviceList = new ArrayList<>();
        private final String parentKey;

        NestedDeviceAdapter(ArrayList<?> deviceList, String parentKey) {
            this.deviceList.addAll(deviceList);
            this.parentKey = parentKey;
        }

        void updateItems(ArrayList<?> items) {
            deviceList.clear();
            deviceList.addAll(items);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public NestedDeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_nested_item, parent, false);
            return new NestedDeviceViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NestedDeviceViewHolder holder, int position) {
            Object item = deviceList.get(position);
            holder.checkBox.setOnCheckedChangeListener(null);
            holder.nestedRecyclerView.setVisibility(View.GONE);
            holder.arrowImageView.setVisibility(View.GONE);
            holder.checkBox.setVisibility(View.GONE);
            holder.lineContainer.setVisibility(View.VISIBLE);

            if (item instanceof DType) {
                DType type = (DType) item;
                holder.title.setText(type.getName());
                holder.title.setTextColor(MapActivity.this.getColor(R.color.black));
                holder.title.setTextSize(13);
                holder.itemView.setOnClickListener(view -> openNavigationItem(type.getName(), String.valueOf(type.getType())));
            } else if (item instanceof FeederTreeNode) {
                FeederTreeNode feederTreeNode = (FeederTreeNode) item;
                holder.lineContainer.setVisibility(View.GONE);
                if (feederTreeNode.isLeaf()) {
                    holder.title.setText(feederTreeNode.getNetworkId());
                    holder.title.setTextColor(rvAdapter.getSelectedItems().contains(feederTreeNode.getNetworkId()) ? MapActivity.this.getColor(R.color.green) : MapActivity.this.getColor(R.color.black));
                    holder.title.setTextSize(8);
                    holder.checkBox.setVisibility(View.GONE);
                    holder.checkBox.setChecked(checkedFeederIds.contains(feederTreeNode.getNetworkId()));
                    holder.itemView.setOnClickListener(view -> holder.checkBox.performClick());
                    holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (isChecked) {
                            if (!checkedFeederIds.contains(feederTreeNode.getNetworkId())) {
                                if (checkedFeederIds.size() >= 3) {
                                    buttonView.setChecked(false);
                                    Snackbar.make(findViewById(android.R.id.content), "Please you can select 3 feeder maximum !", Snackbar.LENGTH_LONG).show();
                                } else {
                                    checkedFeederIds.add(feederTreeNode.getNetworkId());
                                    updateNavLoadButton();
                                }
                            }
                        } else {
                            checkedFeederIds.remove(feederTreeNode.getNetworkId());
                            updateNavLoadButton();
                        }
                    });
                } else {
                    String key = parentKey + "_" + position + "_" + feederTreeNode.getTitle();
                    boolean expanded = nestedExpandedKeys.contains(key);
                    holder.title.setText(feederTreeNode.getTitle());
                    holder.title.setTextColor(MapActivity.this.getColor(R.color.black));
                    if ("Area".equalsIgnoreCase(feederTreeNode.getTitle())) {
                        holder.title.setTextSize(13);
                    } else if ("Group1".equals(feederTreeNode.getGroupType())) {
                        holder.title.setTextSize(12);
                    } else if ("Group2".equals(feederTreeNode.getGroupType())) {
                        holder.title.setTextSize(10);
                    } else {
                        holder.title.setTextSize(13);
                    }
                    holder.arrowImageView.setVisibility(View.VISIBLE);
                    holder.arrowImageView.setRotation(expanded ? 90f : 0f);
                    holder.nestedRecyclerView.setAdapter(new NestedDeviceAdapter(new ArrayList<>(feederTreeNode.getChildren()), key));
                    holder.nestedRecyclerView.setVisibility(expanded ? View.VISIBLE : View.GONE);
                    holder.itemView.setOnClickListener(view -> {
                        if (!feederTreeNode.isChildrenLoaded()) {
                            getFeederGroupData(feederTreeNode, () -> {
                                holder.nestedRecyclerView.setAdapter(new NestedDeviceAdapter(new ArrayList<>(feederTreeNode.getChildren()), key));
                                nestedExpandedKeys.add(key);
                                holder.nestedRecyclerView.setVisibility(View.VISIBLE);
                                holder.arrowImageView.animate().rotation(90f).setDuration(180).start();
                            });
                        } else if (nestedExpandedKeys.contains(key)) {
                            nestedExpandedKeys.remove(key);
                            holder.nestedRecyclerView.setVisibility(View.GONE);
                            holder.arrowImageView.animate().rotation(0f).setDuration(180).start();
                        } else {
                            nestedExpandedKeys.add(key);
                            holder.nestedRecyclerView.setVisibility(View.VISIBLE);
                            holder.arrowImageView.animate().rotation(90f).setDuration(180).start();
                        }
                    });
                }
            }
        }

        @Override
        public int getItemCount() {
            return deviceList.size();
        }

        class NestedDeviceViewHolder extends RecyclerView.ViewHolder {
            private final TextView title;
            private final ImageView arrowImageView;
            private final androidx.appcompat.widget.AppCompatCheckBox checkBox;
            private final ItemViewHelper nestedRecyclerView;
            private final View lineContainer;

            NestedDeviceViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.nested_item_tv);
                arrowImageView = itemView.findViewById(R.id.nested_arrow_img);
                checkBox = itemView.findViewById(R.id.nested_checkbox);
                nestedRecyclerView = itemView.findViewById(R.id.nested_recycler_view);
                lineContainer = itemView.findViewById(R.id.nested_line_container);
                nestedRecyclerView.setNestedScrollingEnabled(true);
                nestedRecyclerView.setHasFixedSize(true);
                nestedRecyclerView.setItemAnimator(null);
                nestedRecyclerView.setRecycledViewPool(drawerRecycledViewPool);
                if (nestedRecyclerView.getLayoutManager() == null) {
                    nestedRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
                }
                nestedRecyclerView.setMaxHeightPx((int) (300 * itemView.getContext().getResources().getDisplayMetrics().density));
            }
        }
    }

    private static class NavigationSection {
        private final String title;
        private final ArrayList<Object> children;

        NavigationSection(String title, ArrayList<Object> children) {
            this.title = title;
            this.children = children;
        }

        String getTitle() {
            return title;
        }

        ArrayList<Object> getChildren() {
            return children;
        }
    }

    private static class FeederTreeNode {
        private final String title;
        private final ArrayList<Object> children;
        private final String networkId;
        private final String groupType;
        private final String groupValue;
        private final String group3Value;
        private final String group2Value;
        private final String group1Value;
        private boolean childrenLoaded;

        FeederTreeNode(String title, ArrayList<Object> children) {
            this.title = title;
            this.children = children;
            this.networkId = null;
            this.groupType = null;
            this.groupValue = null;
            this.group3Value = null;
            this.group2Value = null;
            this.group1Value = null;
            this.childrenLoaded = true;
        }

        FeederTreeNode(String title, String groupType, String groupValue) {
            this.title = title;
            this.children = new ArrayList<>();
            this.networkId = null;
            this.groupType = groupType;
            this.groupValue = groupValue;
            this.group3Value = null;
            this.group2Value = null;
            this.group1Value = null;
            this.childrenLoaded = false;
        }

        FeederTreeNode(String title, String groupType, String groupValue, String group3Value, String group2Value, String group1Value) {
            this.title = title;
            this.children = new ArrayList<>();
            this.networkId = null;
            this.groupType = groupType;
            this.groupValue = groupValue;
            this.group3Value = group3Value;
            this.group2Value = group2Value;
            this.group1Value = group1Value;
            this.childrenLoaded = false;
        }

        FeederTreeNode(String networkId) {
            this.title = networkId;
            this.children = new ArrayList<>();
            this.networkId = networkId;
            this.groupType = null;
            this.groupValue = null;
            this.group3Value = null;
            this.group2Value = null;
            this.group1Value = null;
            this.childrenLoaded = true;
        }

        String getTitle() {
            return title;
        }

        ArrayList<Object> getChildren() {
            return children;
        }

        String getNetworkId() {
            return networkId;
        }

        String getGroupType() {
            return groupType;
        }

        String getGroupValue() {
            return groupValue;
        }

        String getGroup3Value() {
            return group3Value;
        }

        String getGroup2Value() {
            return group2Value;
        }

        String getGroup1Value() {
            return group1Value;
        }

        boolean isChildrenLoaded() {
            return childrenLoaded;
        }

        void setChildrenLoaded(boolean childrenLoaded) {
            this.childrenLoaded = childrenLoaded;
        }

        boolean isLeaf() {
            return networkId != null;
        }
    }

    private void getFeederNavigationData() {
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("NetworkId", new JsonArray());
        jsonObject.addProperty("UserType", prefManager.getUserType());
        jsonObject.addProperty("Project", prefManager.getProjectName());
        jsonObject.addProperty("Group5", "");
        jsonObject.addProperty("Group4", "");
        jsonObject.addProperty("Group3", "");
        jsonObject.addProperty("Group2", "");
        jsonObject.addProperty("Group1", "");
        jsonObject.addProperty("DashBoardType", "Database");
        jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
        jsonObject.addProperty("Mode", "Mobile");
        Call<DatabaseModel> call = apiInterface.DatabaseList("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<DatabaseModel>() {
            @Override
            public void onResponse(@NonNull Call<DatabaseModel> call, @NonNull Response<DatabaseModel> response) {
                if (response.code() == 200 && response.body() != null && response.body().getOutput() != null) {
                    buildFeederNavigationTree(response.body().getOutput());
                    if (adapter != null) {
                        adapter.filterData(binding.search.getQuery() != null ? binding.search.getQuery().toString() : "");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DatabaseModel> call, @NonNull Throwable t) {
                ErrorPdfLogger.logApiFailure(MapActivity.this, "POST", "dashboard/", t);
            }
        });
    }

    private void buildFeederNavigationTree(DatabaseModel.Output output) {
        feederNavigationItems.clear();
        if (output.getGroup1All() == null || output.getGroup1All().isEmpty()) {
            return;
        }

        ArrayList<String> loadedFeeders = new ArrayList<>(rvAdapter.getSelectedItems());
        if (!loadedFeeders.isEmpty()) {
            ArrayList<Object> loadedFeederItems = new ArrayList<>();
            for (String feederId : loadedFeeders) {
                if (feederId != null && !feederId.trim().isEmpty()) {
                    loadedFeederItems.add(new FeederTreeNode(feederId.trim()));
                }
            }
            if (!loadedFeederItems.isEmpty()) {
                feederNavigationItems.add(new FeederTreeNode("Loaded Feeder", loadedFeederItems));
            }
        }

        ArrayList<Object> areaItems = new ArrayList<>();
        for (DatabaseModel.Group1All item : output.getGroup1All()) {
            if (item != null && item.getGroup1() != null && !item.getGroup1().trim().isEmpty()) {
                boolean exists = false;
                for (Object child : areaItems) {
                    if (child instanceof FeederTreeNode && ((FeederTreeNode) child).getGroupValue().equalsIgnoreCase(item.getGroup1().trim())) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    areaItems.add(new FeederTreeNode(item.getGroup1().trim(), "Group1", item.getGroup1().trim(), item.getGroup3(), null, item.getGroup1().trim()));
                }
            }
        }
        if (!areaItems.isEmpty()) {
            feederNavigationItems.add(new FeederTreeNode("Area", areaItems));
        }
    }

    private void getFeederGroupData(FeederTreeNode feederTreeNode, Runnable onLoaded) {
        ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("NetworkId", new JsonArray());
        jsonObject.addProperty("UserType", prefManager.getUserType());
        jsonObject.addProperty("Project", prefManager.getProjectName());
        jsonObject.addProperty("Group5", "");
        jsonObject.addProperty("Group4", "");
        jsonObject.addProperty("Group3", feederTreeNode.getGroup3Value() != null ? feederTreeNode.getGroup3Value() : "");
        jsonObject.addProperty("Group2", "Group2".equals(feederTreeNode.getGroupType()) ? feederTreeNode.getGroupValue() : feederTreeNode.getGroup2Value() != null ? feederTreeNode.getGroup2Value() : "");
        jsonObject.addProperty("Group1", "Group1".equals(feederTreeNode.getGroupType()) ? feederTreeNode.getGroupValue() : feederTreeNode.getGroup1Value() != null ? feederTreeNode.getGroup1Value() : "");
        jsonObject.addProperty("DashBoardType", "Group");
        jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
        jsonObject.addProperty("Mode", "Mobile");
        Call<DatabaseModel> call = apiInterface.DatabaseList("Bearer " + prefManager.getAccessToken(), jsonObject);
        call.enqueue(new Callback<DatabaseModel>() {
            @Override
            public void onResponse(@NonNull Call<DatabaseModel> call, @NonNull Response<DatabaseModel> response) {
                if (response.code() == 200 && response.body() != null && response.body().getOutput() != null) {
                    feederTreeNode.getChildren().clear();
                    if ("Group1".equals(feederTreeNode.getGroupType())) {
                        if (response.body().getOutput().getNetworkNameAll() != null) {
                            for (DatabaseModel.NetworkNameAll item : response.body().getOutput().getNetworkNameAll()) {
                                String voltage = item != null ? item.getGroup2() : null;
                                if (voltage != null && !voltage.trim().isEmpty()) {
                                    boolean exists = false;
                                    for (Object child : feederTreeNode.getChildren()) {
                                        if (child instanceof FeederTreeNode && ((FeederTreeNode) child).getGroupValue().equalsIgnoreCase(voltage.trim())) {
                                            exists = true;
                                            break;
                                        }
                                    }
                                    if (!exists) {
                                        feederTreeNode.getChildren().add(new FeederTreeNode("Voltage level: " + voltage.trim(), "Group2", voltage.trim(), feederTreeNode.getGroup3Value(), voltage.trim(), feederTreeNode.getGroupValue()));
                                    }
                                }
                            }
                        }
                    } else if ("Group2".equals(feederTreeNode.getGroupType())) {
                        if (response.body().getOutput().getNetworkNameAll() != null) {
                            for (DatabaseModel.NetworkNameAll item : response.body().getOutput().getNetworkNameAll()) {
                                if (item != null
                                        && item.getNetworkId() != null
                                        && !item.getNetworkId().trim().isEmpty()
                                        && item.getGroup1() != null
                                        && feederTreeNode.getGroup1Value() != null
                                        && item.getGroup1().trim().equalsIgnoreCase(feederTreeNode.getGroup1Value())
                                        && item.getGroup2() != null
                                        && item.getGroup2().trim().equalsIgnoreCase(feederTreeNode.getGroupValue())) {
                                    feederTreeNode.getChildren().add(new FeederTreeNode(item.getNetworkId().trim()));
                                }
                            }
                        }
                    }
                    feederTreeNode.setChildrenLoaded(true);
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                    if (onLoaded != null) {
                        onLoaded.run();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DatabaseModel> call, @NonNull Throwable t) {
                ErrorPdfLogger.logApiFailure(MapActivity.this, "POST", "dashboard/", t);
            }
        });
    }

    private void applyCheckedFeeders() {
        ArrayList<String> loadedFeeders = new ArrayList<>(rvAdapter.getSelectedItems());
        ArrayList<String> feedersToLoad = new ArrayList<>();
        ArrayList<String> feedersToUnload = new ArrayList<>();

        if (checkedFeederIds.size() > 3) {
            Snackbar.make(findViewById(android.R.id.content), "Please you can select 3 feeder maximum !", Snackbar.LENGTH_LONG).show();
            return;
        }

        for (String checkedFeeder : checkedFeederIds) {
            if (!loadedFeeders.contains(checkedFeeder)) {
                feedersToLoad.add(checkedFeeder);
            }
        }

        for (String loadedFeeder : loadedFeeders) {
            if (!checkedFeederIds.contains(loadedFeeder)) {
                feedersToUnload.add(loadedFeeder);
            }
        }

        if (feedersToLoad.isEmpty() && feedersToUnload.isEmpty()) {
            for (String checkedFeeder : checkedFeederIds) {
                if (loadedFeeders.contains(checkedFeeder)) {
                    feedersToUnload.add(checkedFeeder);
                }
            }
            if (feedersToUnload.isEmpty()) {
                Snackbar.make(findViewById(android.R.id.content), "No feeder changes selected!", Snackbar.LENGTH_LONG).show();
                return;
            }
        }

        for (String feederId : feedersToUnload) {
            removeFeeder(feederId);
            rvAdapter.removeFeeder(feederId);
        }

        if (!feedersToLoad.isEmpty()) {
            selectedFeeder.clear();
            selectedFeeder.addAll(feedersToLoad);
            for (String feederId : feedersToLoad) {
                if (!rvAdapter.getSelectedItems().contains(feederId)) {
                    rvAdapter.addFeeder(feederId);
                }
            }
            getNetworkData(selectedFeeder.get(0).trim());
        }

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        updateNavLoadButton();

        binding.drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void updateNavLoadButton() {
        if (binding == null) {
            return;
        }
        if (!checkedFeederIds.isEmpty()) {
            binding.navLoadBtn.setVisibility(View.GONE);
            binding.navLoadBtn.setText("Load / Unload Network (" + checkedFeederIds.size() + ")");
        } else {
            binding.navLoadBtn.setVisibility(View.GONE);
        }
    }

    private void openNavigationItem(String deviceNumber, String deviceType) {
        if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(MapActivity.this)) {
            if (!deviceNumber.trim().isEmpty() && !deviceNumber.trim().equals("null") && !deviceType.trim().isEmpty() && !deviceType.trim().equals("null") && prefManager.getUserType() != null) {
                getDevices(deviceNumber.trim(), deviceType, prefManager.getUserType());
            } else {
                Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Not Found LatLon", Snackbar.LENGTH_LONG);
                snack.show();
            }
        } else {
            final Dialog dialog = new Dialog(MapActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.no_internet_dialog);
            dialog.getWindow().setBackgroundDrawable(MapActivity.this.getDrawable(R.drawable.pop_background));
            LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.animation_view);
            Button RetryBtn = dialog.findViewById(R.id.btnDialog);
            lottieAnimationView.playAnimation();
            RetryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(MapActivity.this)) {
                        if (prefManager.getUserType() != null) {
                            binding.drawerLayout.closeDrawer(GravityCompat.START);
                            getDevices(deviceNumber.trim(), deviceType, prefManager.getUserType());
                            dialog.dismiss();
                        } else {
                            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "UserType null ", Snackbar.LENGTH_INDEFINITE);
                            snack.show();
                        }
                    }
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    public void getDevices(String deviceNumber, String num, String userType) {
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        progressBarLayout.setProcessText("Device Zoom To Center...");
        progressBarLayout.setVisibility(View.VISIBLE);
        if (num.equalsIgnoreCase("1")) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            try {
                progressBarLayout.setVisibility(View.GONE);
                int layerSize = CaObject.getJSONArray("features").length();
                for (int i = 0; i < layerSize; i++) {
                    if (CaObject.getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber").equalsIgnoreCase(deviceNumber)) {
                        int coordinateSize = CaObject.getJSONArray("features").getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates").length();
                        List<GeoPoint> geoPointList = new ArrayList<>();

                        for (int j = 0; j < coordinateSize; j++) {
                            geoPointList.add(new GeoPoint(CaObject.getJSONArray("features").getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(j).getDouble(1), CaObject.getJSONArray("features").getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(j).getDouble(0)));
                        }

                        BoundingBox boundingBox1 = BoundingBox.fromGeoPoints(geoPointList);
                        binding.map.zoomToBoundingBox(boundingBox1, true);
                        binding.map.invalidate();
                        geoPointList.clear();
                    }
                }
                if (CaSectionId.get(deviceNumber) != null) {
                    if (Config.isLoadFlow) {
                        highlightLoadFlowSection(CaSectionId.get(deviceNumber), deviceNumber);
                    } else if (Config.isShortCircuit) {
                        highlightShortCircuitSection(CaSectionId.get(deviceNumber), deviceNumber);
                    } else {
                        highlightSection(CaSectionId.get(deviceNumber), "1");
                    }
                }
            } catch (Exception e) {
                e.getLocalizedMessage();
            }

        } else if (num.equalsIgnoreCase("2")) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            try {
                progressBarLayout.setVisibility(View.GONE);
                int layerSize = OhObject.getJSONArray("features").length();
                for (int i = 0; i < layerSize; i++) {
                    if (OhObject.getJSONArray("features").getJSONObject(i).getJSONObject("properties").getString("DeviceNumber").equalsIgnoreCase(deviceNumber)) {
                        int coordinateSize = OhObject.getJSONArray("features").getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates").length();
                        List<GeoPoint> geoPointList = new ArrayList<>();

                        for (int j = 0; j < coordinateSize; j++) {
                            geoPointList.add(new GeoPoint(OhObject.getJSONArray("features").getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(j).getDouble(1), OhObject.getJSONArray("features").getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(j).getDouble(0)));
                        }

                        BoundingBox boundingBox1 = BoundingBox.fromGeoPoints(geoPointList);
                        binding.map.zoomToBoundingBox(boundingBox1, true);
                        binding.map.getBoundingBox().getCenter();
                        binding.map.getController().setCenter(boundingBox1.getCenter());
                        binding.map.zoomToBoundingBox(boundingBox1.increaseByScale(1.5f), true);
                        binding.map.invalidate();
                        geoPointList.clear();

                        if (OhSectionId.get(deviceNumber) != null) {
                            if (Config.isLoadFlow) {
                                highlightLoadFlowSection(OhSectionId.get(deviceNumber), deviceNumber);
                            } else if (Config.isShortCircuit) {
                                highlightShortCircuitSection(OhSectionId.get(deviceNumber), deviceNumber);
                            } else {
                                highlightSection(OhSectionId.get(deviceNumber), "2");
                            }
                        }

                    }
                }
            } catch (Exception e) {
                ErrorPdfLogger.logCrash(MapActivity.this,e);
                e.getLocalizedMessage();
            }
        } else {
            progressBarLayout.setVisibility(View.GONE);
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            getDeviceLocation(deviceNumber, num, userType);
        }
    }

    private void getDeviceLocation(String deviceNumber, String deviceType, String userType) {
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("Id", deviceNumber);
            jsonObject.addProperty("DeviceType", deviceType);
            jsonObject.addProperty("UserType", userType);
            jsonObject.addProperty("CYMDBNET", prefManager.getDBName());
            String AccessToken = prefManager.getAccessToken();
            ApiInterface apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
            Call<ZoomToLayer> call = apiInterface.getNetworkZoomToLayer("Bearer " + AccessToken, jsonObject);
            call.enqueue(new Callback<ZoomToLayer>() {
                @Override
                public void onResponse(@NonNull Call<ZoomToLayer> call, @NonNull Response<ZoomToLayer> response) {
                    if (response.code() == 200) {
                        ErrorPdfLogger.logApiSuccess(MapActivity.this, "POST", "/networkzoomtolayer/", response.message()
                                + "Id : " + deviceNumber
                                + "DeviceType : " + deviceType
                                + "UserType : " + userType
                                + "CYMDBNET : " + prefManager.getDBName()
                        );
                        try {
                            ZoomToLayer zoomToLayer = response.body();
                            if (zoomToLayer != null && zoomToLayer.getOutput() != null && !zoomToLayer.getOutput().isEmpty() && zoomToLayer.getOutput().get(0).getX() != null && zoomToLayer.getOutput().get(0).getY() != null) {
                                Double x = zoomToLayer.getOutput().get(0).getX();
                                Double y = zoomToLayer.getOutput().get(0).getY();
                                if (x != 0 && y != 0) {
                                    if (zoomToLayer.getOutput().get(0).getDeviceType() != null && !zoomToLayer.getOutput().get(0).getDeviceType().toString().isEmpty() && zoomToLayer.getOutput().get(0).getDeviceNumber() != null && !zoomToLayer.getOutput().get(0).getDeviceNumber().isEmpty()) {
                                        if (Config.isLoadFlow) {
                                            if (zoomToLayer.getOutput().get(0).getDeviceType().toString().contains("8")) {
                                                if (breakerSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()) != null) {
                                                    highlightLoadFlowDevice(breakerSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()), zoomToLayer.getOutput().get(0).getDeviceType().toString(), zoomToLayer.getOutput().get(0).getDeviceNumber());
                                                }
                                            } else if (zoomToLayer.getOutput().get(0).getDeviceType().toString().contains("10")) {
                                                if (reclosureSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()) != null) {
                                                    highlightLoadFlowDevice(reclosureSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()), zoomToLayer.getOutput().get(0).getDeviceType().toString(), zoomToLayer.getOutput().get(0).getDeviceNumber());
                                                }
                                            } else if (zoomToLayer.getOutput().get(0).getDeviceType().toString().contains("5")) {
                                                if (transformerSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()) != null) {
                                                    highlightLoadFlowDevice(transformerSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()), zoomToLayer.getOutput().get(0).getDeviceType().toString(), zoomToLayer.getOutput().get(0).getDeviceNumber());
                                                }
                                            } else if (zoomToLayer.getOutput().get(0).getDeviceType().toString().contains("14")) {
                                                if (fuseSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()) != null) {
                                                    highlightLoadFlowDevice(fuseSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()), zoomToLayer.getOutput().get(0).getDeviceType().toString(), zoomToLayer.getOutput().get(0).getDeviceNumber());
                                                }
                                            } else if (zoomToLayer.getOutput().get(0).getDeviceType().toString().contains("13")) {
                                                if (switchSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()) != null) {
                                                    highlightLoadFlowDevice(switchSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()), zoomToLayer.getOutput().get(0).getDeviceType().toString(), zoomToLayer.getOutput().get(0).getDeviceNumber());
                                                }
                                            } else if (zoomToLayer.getOutput().get(0).getDeviceType().toString().contains("61")) {
                                                if (capacitorSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()) != null) {
                                                    highlightLoadFlowDevice(capacitorSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()), zoomToLayer.getOutput().get(0).getDeviceType().toString(), zoomToLayer.getOutput().get(0).getDeviceNumber());
                                                }
                                            } else if (zoomToLayer.getOutput().get(0).getDeviceType().toString().contains("20")) {
                                                if (spotloadSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()) != null) {
                                                    highlightLoadFlowDevice(spotloadSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()), zoomToLayer.getOutput().get(0).getDeviceType().toString(), zoomToLayer.getOutput().get(0).getDeviceNumber());
                                                }
                                            }
                                        } else if (Config.isShortCircuit) {
                                            if (zoomToLayer.getOutput().get(0).getDeviceType().toString().contains("8")) {
                                                if (breakerSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()) != null) {
                                                    highlightShortCircuitDevice(breakerSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()), zoomToLayer.getOutput().get(0).getDeviceType().toString(), zoomToLayer.getOutput().get(0).getDeviceNumber());
                                                }
                                            } else if (zoomToLayer.getOutput().get(0).getDeviceType().toString().contains("10")) {
                                                if (reclosureSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()) != null) {
                                                    highlightShortCircuitDevice(reclosureSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()), zoomToLayer.getOutput().get(0).getDeviceType().toString(), zoomToLayer.getOutput().get(0).getDeviceNumber());
                                                }
                                            } else if (zoomToLayer.getOutput().get(0).getDeviceType().toString().contains("5")) {
                                                if (transformerSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()) != null) {
                                                    highlightShortCircuitDevice(transformerSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()), zoomToLayer.getOutput().get(0).getDeviceType().toString(), zoomToLayer.getOutput().get(0).getDeviceNumber());
                                                }
                                            } else if (zoomToLayer.getOutput().get(0).getDeviceType().toString().contains("14")) {
                                                if (fuseSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()) != null) {
                                                    highlightShortCircuitDevice(fuseSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()), zoomToLayer.getOutput().get(0).getDeviceType().toString(), zoomToLayer.getOutput().get(0).getDeviceNumber());
                                                }
                                            } else if (zoomToLayer.getOutput().get(0).getDeviceType().toString().contains("13")) {
                                                if (switchSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()) != null) {
                                                    highlightShortCircuitDevice(switchSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()), zoomToLayer.getOutput().get(0).getDeviceType().toString(), zoomToLayer.getOutput().get(0).getDeviceNumber());
                                                }
                                            } else if (zoomToLayer.getOutput().get(0).getDeviceType().toString().contains("61")) {
                                                if (capacitorSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()) != null) {
                                                    highlightShortCircuitDevice(capacitorSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()), zoomToLayer.getOutput().get(0).getDeviceType().toString(), zoomToLayer.getOutput().get(0).getDeviceNumber());
                                                }
                                            } else if (zoomToLayer.getOutput().get(0).getDeviceType().toString().contains("20")) {
                                                if (spotloadSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()) != null) {
                                                    highlightShortCircuitDevice(spotloadSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()), zoomToLayer.getOutput().get(0).getDeviceType().toString(), zoomToLayer.getOutput().get(0).getDeviceNumber());
                                                }
                                            }
                                        } else {
                                            if (zoomToLayer.getOutput().get(0).getDeviceType().toString().contains("8")) {
                                                if (breakerSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()) != null) {
                                                    highlightDevice(breakerSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()), zoomToLayer.getOutput().get(0).getDeviceType().toString(), zoomToLayer.getOutput().get(0).getDeviceNumber());
                                                }
                                            } else if (zoomToLayer.getOutput().get(0).getDeviceType().toString().contains("10")) {
                                                if (reclosureSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()) != null) {
                                                    highlightDevice(reclosureSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()), zoomToLayer.getOutput().get(0).getDeviceType().toString(), zoomToLayer.getOutput().get(0).getDeviceNumber());
                                                }
                                            } else if (zoomToLayer.getOutput().get(0).getDeviceType().toString().contains("5")) {
                                                if (transformerSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()) != null) {
                                                    highlightDevice(transformerSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()), zoomToLayer.getOutput().get(0).getDeviceType().toString(), zoomToLayer.getOutput().get(0).getDeviceNumber());
                                                }
                                            } else if (zoomToLayer.getOutput().get(0).getDeviceType().toString().contains("14")) {
                                                if (fuseSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()) != null) {
                                                    highlightDevice(fuseSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()), zoomToLayer.getOutput().get(0).getDeviceType().toString(), zoomToLayer.getOutput().get(0).getDeviceNumber());
                                                }
                                            } else if (zoomToLayer.getOutput().get(0).getDeviceType().toString().contains("13")) {
                                                if (switchSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()) != null) {
                                                    highlightDevice(switchSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()), zoomToLayer.getOutput().get(0).getDeviceType().toString(), zoomToLayer.getOutput().get(0).getDeviceNumber());
                                                }
                                            } else if (zoomToLayer.getOutput().get(0).getDeviceType().toString().contains("61")) {
                                                if (capacitorSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()) != null) {
                                                    highlightDevice(capacitorSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()), zoomToLayer.getOutput().get(0).getDeviceType().toString(), zoomToLayer.getOutput().get(0).getDeviceNumber());
                                                }
                                            } else if (zoomToLayer.getOutput().get(0).getDeviceType().toString().contains("20")) {
                                                if (spotloadSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()) != null) {
                                                    highlightDevice(spotloadSectionId.get(zoomToLayer.getOutput().get(0).getDeviceNumber()), zoomToLayer.getOutput().get(0).getDeviceType().toString(), zoomToLayer.getOutput().get(0).getDeviceNumber());
                                                }
                                            }
                                        }
                                    }
                                    GeoPoint geoPoint = UTMConversion.convert(x, y);
                                    mc.animateTo(geoPoint, 27.0, 0L);
                                    binding.map.getController().setCenter(geoPoint);
                                    binding.map.invalidate();
                                } else {
                                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Not Found LatLon!", Snackbar.LENGTH_LONG);
                                    snack.show();
                                }
                            }
                        } catch (Exception e) {
                            ErrorPdfLogger.logCrash(MapActivity.this,e);
                            e.printStackTrace();
                        }
                    } else {
                        ErrorPdfLogger.logApiError(
                                MapActivity.this,
                                "POST",
                                "/deletefeature/",
                                "HTTP " + response.code() + " : " + response.message()
                                        + "DeviceNumber : " + deviceNumber
                                        + "DeviceType : " + deviceType
                                        + "UserType : " + userType
                                        + "CYMDBNET : " + prefManager.getDBName()
                        );
                        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), response.message() + " - " + response.code(), Snackbar.LENGTH_LONG);
                        snack.show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ZoomToLayer> call, @NonNull Throwable t) {
                    ErrorPdfLogger.logApiFailure(
                            MapActivity.this,
                            "POST",
                            "/networkzoomtolayer/",
                            t
                    );
                    Log.e("MapActivity", "Failed: " + t.getLocalizedMessage());
                    Snackbar snack = Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_msg), Snackbar.LENGTH_LONG);
                    snack.show();
                }
            });
        } catch (Exception e) {
            e.getLocalizedMessage();
        }

    }

    private class FiltersAdapter extends ArrayAdapter<DeviceName> {

        private Context context;
        private List<DeviceName> items, tempItems, suggestions;

        public FiltersAdapter(Context context, int resource, int textViewResourceId, List<DeviceName> items) {
            super(context, resource, textViewResourceId, items);
            this.context = context;
            this.items = items;
            tempItems = new ArrayList<DeviceName>(items);
            suggestions = new ArrayList<DeviceName>();
        }

        @NonNull
        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View view = convertView;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.row_first, parent, false);
            }
            DeviceName people = items.get(position);
            if (people != null) {
                TextView lblName = (TextView) view.findViewById(R.id.first_tv);
                if (lblName != null) lblName.setText(people.getName());

                assert lblName != null;
                lblName.setOnClickListener(view1 -> {
                    if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getContext())) {
                        if (!people.getName().isEmpty() && !people.getName().equals("null") && people.getType() != null && !String.valueOf(people.getType()).isEmpty() && prefManager.getUserType() != null) {
                            getDevices(people.getName(), String.valueOf(people.getType()), prefManager.getUserType());
                            binding.searchView.setVisibility(View.GONE);
                            binding.searchView.getText().clear();
                        } else {
                            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "No Found Devices!", Snackbar.LENGTH_LONG);
                            snack.show();
                        }
                    } else {
                        final Dialog dialog = new Dialog(getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.no_internet_dialog);
                        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(getContext().getDrawable(R.drawable.pop_background));
                        LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.animation_view);
                        Button RetryBtn = dialog.findViewById(R.id.btnDialog);
                        lottieAnimationView.playAnimation();
                        RetryBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (ResponseDataUtils.checkInternetConnectionAndInternetAccess(getContext())) {
                                    dialog.dismiss();
                                }
                            }
                        });
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setCancelable(false);
                        dialog.show();
                    }
                });

            }
            return view;
        }

        @NonNull
        @Override
        public Filter getFilter() {
            return nameFilter;
        }

        Filter nameFilter = new Filter() {
            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((DeviceName) resultValue).getName();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    suggestions.clear();
                    for (DeviceName people : tempItems) {
                        if (people.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            suggestions.add(people);
                        }
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                    return filterResults;
                } else {
                    return new FilterResults();
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                List<DeviceName> filterList = (ArrayList<DeviceName>) results.values;
                if (results != null && results.count > 0) {
                    clear();
                    for (DeviceName people : filterList) {
                        add(people);
                        notifyDataSetChanged();
                    }
                }
            }
        };
    }

    public class MyKmlStyler implements KmlFeature.Styler {

        private int mColor;
        private MapView mapView;

        public MyKmlStyler(int mColor, MapView mapView) {
            this.mColor = mColor;
            this.mapView = mapView;
        }

        @Override
        public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {
            try {
                CaSectionId.put(kmlPlacemark.getExtendedData("DeviceNumber"), polyline);
                CaPolylineList.add(polyline);
                polyline.setRelatedObject(kmlPlacemark);
                polyline.getPaint().setStrokeJoin(Paint.Join.ROUND);
                polyline.setColor(Color.RED);
                polyline.setWidth(3.5f);
                polyline.setEnabled(true);
                polyline.setVisible(true);
                polyline.setGeodesic(true);
                polyline.setDensityMultiplier(7.5f);

                polyline.getOutlinePaint().setPathEffect(new DashPathEffect(new float[]{10, 20}, Path.Direction.CW.ordinal()));

                polyline.setOnClickListener((polyline1, mapView1, eventPos) -> {

                    networkId = kmlPlacemark.getExtendedData("NetworkId");
                    nodeId = kmlPlacemark.getExtendedData("ToNodeId");

                    if (prefManager.getUserType().contains("Edit")) {
                        DelSectionId = kmlPlacemark.getExtendedData("SectionId");
                        DelSection.put(kmlPlacemark.getExtendedData("SectionId"), polyline);
                    }

                    if (Config.isLoadFlow) {
                        highlightLoadFlowSection(polyline, kmlPlacemark.getExtendedData("SectionId"));
                        LoadFlowBox loadFlowBox = new LoadFlowBox(mapView.getContext(), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"), loadFlowList, kmlPlacemark.getExtendedData("CableId"));
                        loadFragment(loadFlowBox, "loadFlowBoxTag");
                        if (isTracing) {
                            ReSetColor();
                        }
                    } else if (Config.isShortCircuit) {
                        highlightShortCircuitSection(polyline, kmlPlacemark.getExtendedData("SectionId"));
                        ShortCircuitBox shortCircuitBox = new ShortCircuitBox(mapView.getContext(), shortCircuitList, kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"));
                        loadFragment(shortCircuitBox, "shortCircuitBoxTag");
                        if (isTracing) {
                            ReSetColor();
                        }
                    } else if (Config.isLoadAllocation) {
                        highlightSection(polyline, "1");
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                        jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                        CableSnippet cableSnipet = new CableSnippet(MapActivity.this, kmlPlacemark.getExtendedData("NetworkId"), kmlPlacemark.getExtendedData("SectionId"), kmlPlacemark.getExtendedData("CableId"), kmlPlacemark.getExtendedData("Length"), kmlPlacemark.getExtendedData("Voltage"), jsonObject);
                        cableSnipet.show();
                        if (isTracing) {
                            ReSetColor();
                        }
                    } else {
                        highlightSection(polyline, "1");
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                        jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                        CableSnippet cableSnipet = new CableSnippet(MapActivity.this, kmlPlacemark.getExtendedData("NetworkId"), kmlPlacemark.getExtendedData("SectionId"), kmlPlacemark.getExtendedData("CableId"), kmlPlacemark.getExtendedData("Length"), kmlPlacemark.getExtendedData("Voltage"), jsonObject);
                        cableSnipet.show();
                        if (isTracing) {
                            ReSetColor();
                        }
                    }
                    binding.map.invalidate();
                    return true;
                });

            } catch (Exception e) {
                ErrorPdfLogger.logCrash(MapActivity.this,e);
                e.printStackTrace();
            }
        }

        @Override
        public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

        }

        @Override
        public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {

        }

        @Override
        public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {

        }

        @Override
        public void onFeature(Overlay overlay, KmlFeature kmlFeature) {

        }
    }

    public class substationKmlStyler implements KmlFeature.Styler {

        private int mColor;
        private MapView mapView;

        public substationKmlStyler(int mColor, MapView mapView) {
            this.mColor = mColor;
            this.mapView = mapView;
        }

        @Override
        public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {

        }

        @Override
        public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

            polygon.setFillColor(Color.BLUE);

        }

        @Override
        public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {

        }

        @Override
        public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {

        }

        @Override
        public void onFeature(Overlay overlay, KmlFeature kmlFeature) {

        }
    }

    public class OverHeadKmlStyler implements KmlFeature.Styler {

        private int mColor;
        private MapView mapView;

        public OverHeadKmlStyler(int mColor, MapView mapView) {
            this.mColor = mColor;
            this.mapView = mapView;
        }

        @Override
        public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {
            try {
                OhSectionId.put(kmlPlacemark.getExtendedData("DeviceNumber"), polyline);
                ohPolylineList.add(polyline);
                polyline.setRelatedObject(kmlPlacemark);
                polyline.getPaint().setStrokeJoin(Paint.Join.ROUND);
                polyline.setColor(Color.BLUE);
                polyline.setWidth(3.5f);
                polyline.setGeodesic(true);
                polyline.setDensityMultiplier(7.5f);

                polyline.setOnClickListener(new Polyline.OnClickListener() {
                    @Override
                    public boolean onClick(Polyline polyline, MapView mapView, GeoPoint eventPos) {

                        networkId = kmlPlacemark.getExtendedData("NetworkId");
                        nodeId = kmlPlacemark.getExtendedData("ToNodeId");

                        if (prefManager.getUserType().contains("Edit")) {
                            DelSectionId = kmlPlacemark.getExtendedData("SectionId");
                            DelSection.put(kmlPlacemark.getExtendedData("SectionId"), polyline);
                        }

                        if (Config.isLoadFlow) {
                            highlightLoadFlowSection(polyline, kmlPlacemark.getExtendedData("SectionId"));
                            LoadFlowBox loadFlowBox = new LoadFlowBox(mapView.getContext(), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"), loadFlowList, kmlPlacemark.getExtendedData("LineId"));
                            loadFragment(loadFlowBox, "loadFlowBoxTag");
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else if (Config.isShortCircuit) {
                            highlightShortCircuitSection(polyline, kmlPlacemark.getExtendedData("SectionId"));
                            ShortCircuitBox shortCircuitBox = new ShortCircuitBox(mapView.getContext(), shortCircuitList, kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"));
                            loadFragment(shortCircuitBox, "shortCircuitBoxTag");
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else if (Config.isLoadAllocation) {
                            highlightSection(polyline, "2");
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                            jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                            OverheadSnippet overheadSnippet = new OverheadSnippet(MapActivity.this, kmlPlacemark.getExtendedData("NetworkId"), kmlPlacemark.getExtendedData("SectionId"), kmlPlacemark.getExtendedData("LineId"), kmlPlacemark.getExtendedData("Length"), kmlPlacemark.getExtendedData("Voltage"), jsonObject);
                            overheadSnippet.show();
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else {
                            highlightSection(polyline, "2");
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                            jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                            OverheadSnippet overheadSnippet = new OverheadSnippet(MapActivity.this, kmlPlacemark.getExtendedData("NetworkId"), kmlPlacemark.getExtendedData("SectionId"), kmlPlacemark.getExtendedData("LineId"), kmlPlacemark.getExtendedData("Length"), kmlPlacemark.getExtendedData("Voltage"), jsonObject);
                            overheadSnippet.show();
                            if (isTracing) {
                                ReSetColor();
                            }
                        }

                        binding.map.invalidate();
                        return true;
                    }
                });
            } catch (Exception e) {
                ErrorPdfLogger.logCrash(MapActivity.this,e);
                Log.d("Exception", e.getLocalizedMessage());
            }
        }

        @Override
        public void onFeature(Overlay overlay, KmlFeature kmlFeature) {

        }

        @Override
        public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {

        }

        @Override
        public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

        }

        @Override
        public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {

        }
    }

    public class UnbalanceKmlStyler implements KmlFeature.Styler {

        private int mColor;
        private MapView mapView;

        public UnbalanceKmlStyler(int mColor, MapView mapView) {
            this.mColor = mColor;
            this.mapView = mapView;
        }

        @Override
        public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {
            try {
                UnBalSectionId.put(kmlPlacemark.getExtendedData("SectionId"), polyline);
                unBalPolylineList.add(polyline);
                polyline.setRelatedObject(kmlPlacemark);
                Paint p = new Paint();
                p.setAlpha(12);
                p.setStrokeCap(Paint.Cap.SQUARE);
                p.setStyle(Paint.Style.STROKE);
                p.setStrokeCap(Paint.Cap.BUTT);
                polyline.getPaint().set(p);
                polyline.setColor(Color.DKGRAY);
                polyline.setWidth(3.5f);
                polyline.setDensityMultiplier(7.5f);

                polyline.setOnClickListener(new Polyline.OnClickListener() {
                    @Override
                    public boolean onClick(Polyline polyline, MapView mapView, GeoPoint eventPos) {
                        networkId = kmlPlacemark.getExtendedData("NetworkId");
                        nodeId = kmlPlacemark.getExtendedData("ToNodeId");

                        if (Config.isLoadFlow) {
                            highlightLoadFlowSection(polyline, kmlPlacemark.getExtendedData("SectionId"));
                            LoadFlowBox loadFlowBox = new LoadFlowBox(mapView.getContext(), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"), loadFlowList, "");
                            loadFragment(loadFlowBox, "loadFlowBoxTag");
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else if (Config.isShortCircuit) {
                            highlightShortCircuitSection(polyline, kmlPlacemark.getExtendedData("SectionId"));
                            ShortCircuitBox shortCircuitBox = new ShortCircuitBox(mapView.getContext(), shortCircuitList, kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"));
                            loadFragment(shortCircuitBox, "shortCircuitBoxTag");
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else if (Config.isLoadAllocation) {
                            highlightSection(polyline, "24");
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                            jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                            UnbalanceSnippet unbalanceSnippet = new UnbalanceSnippet(MapActivity.this, kmlPlacemark.getExtendedData("NetworkId"), kmlPlacemark.getExtendedData("SectionId"), kmlPlacemark.getExtendedData("LineId"), kmlPlacemark.getExtendedData("Length"), jsonObject);
                            unbalanceSnippet.show();
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else {
                            highlightSection(polyline, "24");
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                            jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                            UnbalanceSnippet unbalanceSnippet = new UnbalanceSnippet(MapActivity.this, kmlPlacemark.getExtendedData("NetworkId"), kmlPlacemark.getExtendedData("SectionId"), kmlPlacemark.getExtendedData("LineId"), kmlPlacemark.getExtendedData("Length"), jsonObject);
                            unbalanceSnippet.show();
                            if (isTracing) {
                                ReSetColor();
                            }
                        }
                        binding.map.invalidate();
                        return true;

                    }
                });
            } catch (Exception e) {
                ErrorPdfLogger.logCrash(MapActivity.this,e);
                e.printStackTrace();
            }
        }

        @Override
        public void onFeature(Overlay overlay, KmlFeature kmlFeature) {

        }

        @Override
        public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {

        }

        @Override
        public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

        }

        @Override
        public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {

        }
    }

    public class SectionNodeKmlStyler implements KmlFeature.Styler {

        private int mColor;
        private MapView mapView;

        public SectionNodeKmlStyler(int mColor, MapView mapView) {
            this.mColor = mColor;
            this.mapView = mapView;
        }

        @Override
        public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {
            polyline.setColor(Color.BLACK);
            polyline.setWidth(3.0f);
            secNodeList.add(polyline);
            polyline.setRelatedObject(kmlPlacemark);
            secNodeSectionId.put(kmlPlacemark.getExtendedData("DeviceNumber"), polyline);
        }

        @Override
        public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {

        }

        @Override
        public void onFeature(Overlay overlay, KmlFeature kmlFeature) {

        }

        @Override
        public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

        }

        @Override
        public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {

        }
    }

    public class circuitBreakerKmlStyler implements KmlFeature.Styler {

        private int mColor;
        private MapView mapView;

        public circuitBreakerKmlStyler(int mColor, MapView mapView) {
            this.mColor = mColor;
            this.mapView = mapView;
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {
            try {
                int i = kmlPlacemark.mGeometry.mCoordinates.size() - 1;
                GeoPoint geoPoint = new GeoPoint(kmlPoint.mCoordinates.get(i).getLatitude(), kmlPoint.mCoordinates.get(i).getLongitude());
                marker.setPosition(geoPoint);
                marker.setRelatedObject(kmlPlacemark);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);

                breakerSectionId.put(kmlPlacemark.getExtendedData("DeviceNumber"), marker);
                breakerList.add(marker);

                if (kmlPlacemark.getExtendedData("ClosedPhase").equalsIgnoreCase("7")) {
                    Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                    Bitmap bites = drawableToBitmap(drawable);
                    Bitmap customizedBitmap = changeBitmapColor(bites, Color.BLACK);
                    marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(customizedBitmap, 0, 90)));
//                    marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                    marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_X_l"))));
                   /* marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_Y_l")),
                            Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l"))));*/
//                    marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                } else {
                    Drawable drawable = getResources().getDrawable(R.drawable.openbreaker);
                    Bitmap bites = drawableToBitmap(drawable);
                    Bitmap customizedBitmap = changeBitmapColor(bites, Color.BLACK);
                    marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), addPaddingToBitmap(customizedBitmap, 0, 0)));
                    marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                }

                marker.setOnMarkerClickListener((marker1, mapView) -> {

                    networkId = kmlPlacemark.getExtendedData("NetworkId");
                    nodeId = kmlPlacemark.getExtendedData("ToNodeId");

                    if (prefManager.getUserType().contains("Edit")) {
                        DelDeviceNumber = kmlPlacemark.getExtendedData("DeviceNumber");
                        DelDevice.put(kmlPlacemark.getExtendedData("DeviceNumber"), marker);
                    }

                    if (Config.isLoadFlow) {
                        highlightLoadFlowDevice(marker1, "8", kmlPlacemark.getExtendedData("DeviceNumber"));
                        LoadFlowBox loadFlowBox = new LoadFlowBox(mapView.getContext(), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"), loadFlowList, kmlPlacemark.getExtendedData("EquipmentId"));
                        loadFragment(loadFlowBox, "loadFlowBoxTag");
                        if (isTracing) {
                            ReSetColor();
                        }
                    } else if (Config.isShortCircuit) {
                        highlightShortCircuitDevice(marker1, "8", kmlPlacemark.getExtendedData("DeviceNumber"));
                        ShortCircuitBox shortCircuitBox = new ShortCircuitBox(mapView.getContext(), shortCircuitList, kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"));
                        loadFragment(shortCircuitBox, "shortCircuitBoxTag");
                        if (isTracing) {
                            ReSetColor();
                        }
                    } else {
                        highlightDevice(marker1, "8", kmlPlacemark.getExtendedData("DeviceNumber"));
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                        jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                        BreakerSnippet breakerSnippet = new BreakerSnippet(MapActivity.this, kmlPlacemark.getExtendedData("SectionId"), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("EquipmentId"), kmlPlacemark.getExtendedData("NetworkId"), kmlPlacemark.getExtendedData("DeviceType"), kmlPlacemark.getExtendedData("Voltage"), jsonObject);
                        breakerSnippet.show();
                        if (isTracing) {
                            ReSetColor();
                        }
                    }
                    binding.map.invalidate();
                    return true;
                });

                if (CaSectionList.contains(kmlPlacemark.getExtendedData("SectionId"))) {
                    int CaSize = CaObject.getJSONArray("features").length();
                    for (int j = 0; j < CaSize; j++) {
                        if (CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String fromY = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(0));
                            String fromX = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(1));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(fromX), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.valueOf(fromY), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                        } else if (CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && !kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String toY = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(0));
                            String toX = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(1));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(toX), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(toY)));
                        }
                    }
                } else if (OhSectionList.contains(kmlPlacemark.getExtendedData("SectionId"))) {
                    int OhSize = OhObject.getJSONArray("features").length();
                    for (int k = 0; k < OhSize; k++) {
                        if (OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String fromY = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(0));
                            String fromX = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(1));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(fromX), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.valueOf(fromY), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                        } else if (OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && !kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String toX = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(1));
                            String toY = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(0));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(toX), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(toY)));
                        }
                    }
                }
            } catch (Exception e) {
                ErrorPdfLogger.logCrash(MapActivity.this,e);
                Log.d("Exception", e.getLocalizedMessage());
            }
        }

        @Override
        public void onFeature(Overlay overlay, KmlFeature kmlFeature) {

        }

        @Override
        public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {

        }

        @Override
        public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

        }

        @Override
        public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {

        }
    }

    public class SectionLizerKmlStyler implements KmlFeature.Styler {

        private int mColor;
        private MapView mapView;

        public SectionLizerKmlStyler(int mColor, MapView mapView) {
            this.mColor = mColor;
            this.mapView = mapView;
        }

        @Override
        public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {
            try {
                int i = kmlPlacemark.mGeometry.mCoordinates.size() - 1;
                GeoPoint geoPoint = new GeoPoint(kmlPoint.mCoordinates.get(i).getLatitude(), kmlPoint.mCoordinates.get(i).getLongitude());
                marker.setPosition(geoPoint);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);

                String text = "⌼";
                int textSize = 25;
                int textColor = Color.BLACK;
                int backgroundColor = Color.TRANSPARENT;
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setTextSize(textSize);
                paint.setColor(textColor);
                paint.setTextAlign(Paint.Align.CENTER);
                Rect textBounds = new Rect();
                paint.getTextBounds(text, 0, text.length(), textBounds);
                int width = textBounds.width() + 16; // add some padding to the width
                int height = textBounds.height() + 16; // add some padding to the height
                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                canvas.drawColor(backgroundColor);
                canvas.drawText(text, width / 2f, height / 2f + textBounds.height() / 2f, paint);
                Bitmap bitmap1 = ResponseDataUtils.RotateMyBitmap(bitmap, 0);
                marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), bitmap1));

                marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker, MapView mapView) {
                        if (Config.isLoadFlow) {
                            LoadFlowBox loadFlowBox = new LoadFlowBox(mapView.getContext(), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"), loadFlowList, "");
                            loadFragment(loadFlowBox, "loadFlowBoxTag");
                        } else {
                            marker.setInfoWindow(new SectionLizerMoreInfo(R.layout.markerinfo__layout, mapView));
                            marker.showInfoWindow();
                            marker.getIcon().setTint(Color.GREEN);
                        }
                        return true;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {

        }

        @Override
        public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

        }

        @Override
        public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {

        }

        @Override
        public void onFeature(Overlay overlay, KmlFeature kmlFeature) {

        }
    }

    public class DistributionTransferKmlStyler implements KmlFeature.Styler {

        private int mColor;
        private MapView mapView;

        public DistributionTransferKmlStyler(int mColor, MapView mapView) {
            this.mColor = mColor;
            this.mapView = mapView;
        }

        @Override
        public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {
            try {
                String fromX = kmlPlacemark.getExtendedData("FromNode_X_l");
                String fromY = kmlPlacemark.getExtendedData("FromNode_Y_l");
                String toX = kmlPlacemark.getExtendedData("ToNode_X_l");
                String toY = kmlPlacemark.getExtendedData("ToNode_Y_l");
                int i = kmlPlacemark.mGeometry.mCoordinates.size() - 1;
                GeoPoint geoPoint = new GeoPoint(kmlPoint.mCoordinates.get(i).getLatitude(), kmlPoint.mCoordinates.get(i).getLongitude());
                marker.setPosition(geoPoint);
                marker.setRelatedObject(kmlPlacemark);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
                transformerSectionId.put(kmlPlacemark.getExtendedData("DeviceNumber"), marker);
                transformerList.add(marker);

                Bitmap customizedBitmap = null;
                if (kmlPlacemark.getExtendedData("Status").equalsIgnoreCase("0")) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                    Bitmap bites = drawableToBitmap(drawable);
                    customizedBitmap = changeBitmapColor(bites, Color.BLACK);
//                    marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), ResponseDataUtils.addPaddingToBitmap(customizedBitmap, 0, 93)));
                    marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), addPaddingToBitmap(customizedBitmap, 90, 0)));
//                    marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(fromX), Double.valueOf(toX), Double.valueOf(fromY), Double.valueOf(toY))  );
                    //correct direction
                    marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.parseDouble(fromY), Double.parseDouble(toY), Double.parseDouble(fromX), Double.parseDouble(toX)));
//                    marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
//                    marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(fromY), Double.valueOf(fromX), Double.valueOf(fromY), Double.valueOf(toX)));
                } else {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.opentransformer);
                    Bitmap bites = drawableToBitmap(drawable);
                    customizedBitmap = changeBitmapColor(bites, Color.BLACK);
                    marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), addPaddingToBitmap(customizedBitmap, 0, 0)));
                    marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.parseDouble(fromX), Double.parseDouble(toX), Double.valueOf(fromY), Double.valueOf(toY)));

//                    marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                }

                marker.setOnMarkerClickListener((marker1, mapView) -> {

                    networkId = kmlPlacemark.getExtendedData("NetworkId");
                    nodeId = kmlPlacemark.getExtendedData("ToNodeId");

                    if (prefManager.getUserType().contains("Edit")) {
                        DelDeviceNumber = kmlPlacemark.getExtendedData("DeviceNumber");
                        DelDevice.put(kmlPlacemark.getExtendedData("DeviceNumber"), marker1);
                    }
                    if (Config.isLoadFlow) {
                        highlightLoadFlowDevice(marker1, "5", kmlPlacemark.getExtendedData("DeviceNumber"));
                        LoadFlowBox loadFlowBox = new LoadFlowBox(mapView.getContext(), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"), loadFlowList, kmlPlacemark.getExtendedData("EquipmentId"));
                        loadFragment(loadFlowBox, "loadFlowBoxTag");
                        if (isTracing) {
                            ReSetColor();
                        }
                    } else if (Config.isShortCircuit) {
                        highlightShortCircuitDevice(marker1, "5", kmlPlacemark.getExtendedData("DeviceNumber"));
                        ShortCircuitBox shortCircuitBox = new ShortCircuitBox(mapView.getContext(), shortCircuitList, kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"));
                        loadFragment(shortCircuitBox, "shortCircuitBoxTag");
                        if (isTracing) {
                            ReSetColor();
                        }
                    } else {
                        highlightDevice(marker1, "5", kmlPlacemark.getExtendedData("DeviceNumber"));
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                        jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                        TransformerSnippet transformerSnippet = new TransformerSnippet(MapActivity.this, kmlPlacemark.getExtendedData("SectionId"), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("EquipmentId"), kmlPlacemark.getExtendedData("NetworkId"), kmlPlacemark.getExtendedData("DeviceType"), kmlPlacemark.getExtendedData("Voltage"), jsonObject);
                        transformerSnippet.show();
                        if (isTracing) {
                            ReSetColor();
                        }
                    }
                    binding.map.invalidate();
                    return true;
                });

                if (CaSectionList.contains(kmlPlacemark.getExtendedData("SectionId"))) {
                    int y = CaObject.getJSONArray("features").length();
                    for (int j = 0; j < y; j++) {
                        if (CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && kmlPlacemark.getExtendedData("Location").equals("2")) {
                            fromY = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(0));
                            fromX = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(1));
//                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(fromX), Double.valueOf(toX), Double.valueOf(fromY), Double.valueOf(toY)));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(fromY), Double.valueOf(toY), Double.valueOf(fromX), Double.valueOf(toX)));
                        } else if (CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && !kmlPlacemark.getExtendedData("Location").equals("2")) {
                            toY = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(0));
                            toX = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(1));
                            marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), addPaddingToBitmap(customizedBitmap, 0, 90)));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(fromY), Double.valueOf(toY), Double.valueOf(fromX), Double.valueOf(toX)));
//                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(toX), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(toY)));
                        }
                    }
                } else if (OhSectionList.contains(kmlPlacemark.getExtendedData("SectionId"))) {
                    int OhSize = OhObject.getJSONArray("features").length();
                    for (int k = 0; k < OhSize; k++) {
                        if (OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && kmlPlacemark.getExtendedData("Location").equals("2")) {
                            fromY = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(0));
                            fromX = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(1));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(fromY), Double.valueOf(toY), Double.valueOf(fromX), Double.valueOf(toX)));
//                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(fromX), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.valueOf(fromY), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                        } else if (OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && !kmlPlacemark.getExtendedData("Location").equals("2")) {
                            toX = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(1));
                            toY = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(0));
                            marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), addPaddingToBitmap(customizedBitmap, 0, 80)));
//                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(toX), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(toY)));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(fromY), Double.valueOf(toY), Double.valueOf(fromX), Double.valueOf(toX)));
                        }
                    }
                }

            } catch (Exception e) {
                ErrorPdfLogger.logCrash(MapActivity.this,e);
                Log.d("DtException", Objects.requireNonNull(e.getLocalizedMessage()));
            }
        }

        @Override
        public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {
        }

        @Override
        public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {
        }

        @Override
        public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {
        }

        @Override
        public void onFeature(Overlay overlay, KmlFeature kmlFeature) {
        }
    }

    public class FuseKmlStyler implements KmlFeature.Styler {

        private int mColor;
        private MapView mapView;

        public FuseKmlStyler(int mColor, MapView mapView) {
            this.mColor = mColor;
            this.mapView = mapView;
        }

        @Override
        public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {
            try {
                int i = kmlPlacemark.mGeometry.mCoordinates.size() - 1;
                GeoPoint geoPoint = new GeoPoint(kmlPoint.mCoordinates.get(i).getLatitude(), kmlPoint.mCoordinates.get(i).getLongitude());
                marker.setPosition(geoPoint);
                marker.setRelatedObject(kmlPlacemark);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
                fuseSectionId.put(kmlPlacemark.getExtendedData("DeviceNumber"), marker);
                fuseList.add(marker);
                Bitmap FuseBitmap = null;
                if (kmlPlacemark.getExtendedData("ClosedPhase").equalsIgnoreCase("7")) {
                    FuseBitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                    int paddingTop = 0; // set your desired padding value
                    Bitmap output = Bitmap.createBitmap(FuseBitmap.getWidth(), FuseBitmap.getHeight() + paddingTop, Bitmap.Config.ARGB_8888);
                    Canvas canvas1 = new Canvas(output);
                    canvas1.drawBitmap(FuseBitmap, 0, paddingTop, null);
                    marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(FuseBitmap, Color.BLACK), 0, 90)));
                } else {
                    FuseBitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "X", 90f, Color.BLACK);
                    int paddingLeft = 0; // Left padding in pixels
                    int paddingTop = 0; // Top padding in pixels
                    int paddingRight = 0; // Right padding in pixels
                    int paddingBottom = 20; // Bottom padding in pixels
                    int newWidth = FuseBitmap.getWidth() + paddingLeft + paddingRight;
                    int newHeight = FuseBitmap.getHeight() + paddingTop + paddingBottom;
                    Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, FuseBitmap.getConfig());
                    Canvas canva = new Canvas(paddedBitmap);
                    canva.drawBitmap(FuseBitmap, paddingLeft, paddingTop, null);
                    marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.BLACK), 0, 95)));
                }

//                marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_X_l"))));

                marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker, MapView mapView) {

                        networkId = kmlPlacemark.getExtendedData("NetworkId");
                        nodeId = kmlPlacemark.getExtendedData("ToNodeId");

                        if (prefManager.getUserType().contains("Edit")) {
                            DelDeviceNumber = kmlPlacemark.getExtendedData("DeviceNumber");
                            DelDevice.put(kmlPlacemark.getExtendedData("DeviceNumber"), marker);
                        }

                        if (Config.isLoadFlow) {
                            highlightLoadFlowDevice(marker, "14", kmlPlacemark.getExtendedData("DeviceNumber"));
                            LoadFlowBox loadFlowBox = new LoadFlowBox(mapView.getContext(), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"), loadFlowList, kmlPlacemark.getExtendedData("EquipmentId"));
                            loadFragment(loadFlowBox, "loadFlowBoxTag");
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else if (Config.isShortCircuit) {
                            highlightShortCircuitDevice(marker, "14", kmlPlacemark.getExtendedData("DeviceNumber"));
                            ShortCircuitBox shortCircuitBox = new ShortCircuitBox(mapView.getContext(), shortCircuitList, kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"));
                            loadFragment(shortCircuitBox, "shortCircuitBoxTag");
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else if (Config.isLoadAllocation) {
                            highlightDevice(marker, "14", kmlPlacemark.getExtendedData("DeviceNumber"));
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                            jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                            FuseSnippet fuseSnippet = new FuseSnippet(MapActivity.this, kmlPlacemark.getExtendedData("SectionId"), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("EquipmentId"), kmlPlacemark.getExtendedData("NetworkId"), kmlPlacemark.getExtendedData("DeviceType"), kmlPlacemark.getExtendedData("Voltage"), jsonObject);
                            fuseSnippet.show();
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else {
                            highlightDevice(marker, "14", kmlPlacemark.getExtendedData("DeviceNumber"));
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                            jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                            FuseSnippet fuseSnippet = new FuseSnippet(MapActivity.this, kmlPlacemark.getExtendedData("SectionId"), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("EquipmentId"), kmlPlacemark.getExtendedData("NetworkId"), kmlPlacemark.getExtendedData("DeviceType"), kmlPlacemark.getExtendedData("Voltage"), jsonObject);
                            fuseSnippet.show();
                            if (isTracing) {
                                ReSetColor();
                            }
                        }
                        binding.map.invalidate();
                        return true;
                    }
                });

                if (CaSectionList.contains(kmlPlacemark.getExtendedData("SectionId"))) {
                    int CaSize = CaObject.getJSONArray("features").length();
                    for (int j = 0; j < CaSize; j++) {
                        if (CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String fromY = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(0));
                            String fromX = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(1));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(fromX), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.valueOf(fromY), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                        } else if (CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && !kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String toY = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(0));
                            String toX = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(1));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(toX), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(toY)));
                        }
                    }
                } else if (OhSectionList.contains(kmlPlacemark.getExtendedData("SectionId"))) {
                    int OhSize = OhObject.getJSONArray("features").length();
                    for (int k = 0; k < OhSize; k++) {
                        if (OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String fromY = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(0));
                            String fromX = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(1));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(fromX), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.valueOf(fromY), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                        } else if (OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && !kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String toX = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(1));
                            String toY = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(0));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(toX), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(toY)));
                        }
                    }
                }
            } catch (Exception e) {
                ErrorPdfLogger.logCrash(MapActivity.this,e);
                Log.d("Main", e.getMessage());
            }
        }

        @Override
        public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {

        }

        @Override
        public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

        }

        @Override
        public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {

        }

        @Override
        public void onFeature(Overlay overlay, KmlFeature kmlFeature) {

        }
    }

    public class SwitchKmlStyler implements KmlFeature.Styler {

        private int mColor;
        private MapView mapView;

        public SwitchKmlStyler(int mColor, MapView mapView) {
            this.mColor = mColor;
            this.mapView = mapView;
        }

        @Override
        public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {
            try {
                int i = kmlPlacemark.mGeometry.mCoordinates.size() - 1;
                GeoPoint geoPoint = new GeoPoint(kmlPoint.mCoordinates.get(i).getLatitude(), kmlPoint.mCoordinates.get(i).getLongitude());
                marker.setPosition(geoPoint);
                marker.setRelatedObject(kmlPlacemark);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
                switchSectionId.put(kmlPlacemark.getExtendedData("DeviceNumber"), marker);
                switchedList.add(marker);

                Bitmap SwitchBitmap = null;
                if (kmlPlacemark.getExtendedData("ClosedPhase").equalsIgnoreCase("7")) {
                    SwitchBitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                    int paddingLeft = 0; // Left padding in pixels
                    int paddingTop = 0; // Top padding in pixels
                    int paddingRight = 0; // Right padding in pixels
                    int paddingBottom = 12; // Bottom padding in pixels
                    int newWidth = SwitchBitmap.getWidth() + paddingLeft + paddingRight;
                    int newHeight = SwitchBitmap.getHeight() + paddingTop + paddingBottom;
                    Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, SwitchBitmap.getConfig());
                    Canvas canva = new Canvas(paddedBitmap);
                    canva.drawBitmap(SwitchBitmap, paddingLeft, paddingTop, null);
                    marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.BLACK), 90, 0)));
                } else {
                    SwitchBitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "N", 60f, Color.BLACK);
                    int paddingLeft = 0; // Left padding in pixels
                    int paddingTop = 0; // Top padding in pixels
                    int paddingRight = 0; // Right padding in pixels
                    int paddingBottom = 24; // Bottom padding in pixels
                    int newWidth = SwitchBitmap.getWidth() + paddingLeft + paddingRight;
                    int newHeight = SwitchBitmap.getHeight() + paddingTop + paddingBottom;
                    Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, SwitchBitmap.getConfig());
                    Canvas canva = new Canvas(paddedBitmap);
                    canva.drawBitmap(SwitchBitmap, paddingLeft, paddingTop, null);
                    marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.BLACK), 0, 90)));
                }

//                marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_X_l"))));

//                marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_X_l")),
//                        Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_X_l"))));

                marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker, MapView mapView) {

                        networkId = kmlPlacemark.getExtendedData("NetworkId");
                        nodeId = kmlPlacemark.getExtendedData("ToNodeId");

                        if (prefManager.getUserType().contains("Edit")) {
                            DelDeviceNumber = kmlPlacemark.getExtendedData("DeviceNumber");
                            DelDevice.put(kmlPlacemark.getExtendedData("DeviceNumber"), marker);
                        }

                        if (Config.isLoadFlow) {
                            highlightLoadFlowDevice(marker, "13", kmlPlacemark.getExtendedData("DeviceNumber"));
                            LoadFlowBox loadFlowBox = new LoadFlowBox(mapView.getContext(), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"), loadFlowList, kmlPlacemark.getExtendedData("EquipmentId"));
                            loadFragment(loadFlowBox, "loadFlowBoxTag");
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else if (Config.isShortCircuit) {
                            highlightShortCircuitDevice(marker, "13", kmlPlacemark.getExtendedData("DeviceNumber"));
                            ShortCircuitBox shortCircuitBox = new ShortCircuitBox(mapView.getContext(), shortCircuitList, kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"));
                            loadFragment(shortCircuitBox, "shortCircuitBoxTag");
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else if (Config.isLoadAllocation) {
                            highlightDevice(marker, "13", kmlPlacemark.getExtendedData("DeviceNumber"));
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                            jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                            SwitchSnippet switchSnippet = new SwitchSnippet(MapActivity.this, kmlPlacemark.getExtendedData("SectionId"), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("EquipmentId"), kmlPlacemark.getExtendedData("NetworkId"), kmlPlacemark.getExtendedData("DeviceType"), kmlPlacemark.getExtendedData("Voltage"), jsonObject);
                            switchSnippet.show();
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else {
                            highlightDevice(marker, "13", kmlPlacemark.getExtendedData("DeviceNumber"));
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                            jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                            SwitchSnippet switchSnippet = new SwitchSnippet(MapActivity.this, kmlPlacemark.getExtendedData("SectionId"), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("EquipmentId"), kmlPlacemark.getExtendedData("NetworkId"), kmlPlacemark.getExtendedData("DeviceType"), kmlPlacemark.getExtendedData("Voltage"), jsonObject);
                            switchSnippet.show();
                            if (isTracing) {
                                ReSetColor();
                            }
                        }
                        binding.map.invalidate();
                        return true;
                    }
                });

                if (CaSectionList.contains(kmlPlacemark.getExtendedData("SectionId"))) {
                    int CaSize = CaObject.getJSONArray("features").length();
                    for (int j = 0; j < CaSize; j++) {
                        if (CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String fromY = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(0));
                            String fromX = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(1));
//                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_X_l"))));
//                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(fromX), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.valueOf(fromY), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(fromY), Double.valueOf(fromX), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l")), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l"))));

                            Log.d("SwitchCableFrom", kmlPlacemark.getExtendedData("DeviceNumber"));
                        } else if (CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && !kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String toY = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(0));
                            String toX = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(1));
                            marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), addPaddingToBitmap(SwitchBitmap, 0, 80)));
//                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(toX), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(toY)));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(toY), Double.valueOf(toX)));
                            Log.d("SwitchCableTo", kmlPlacemark.getExtendedData("DeviceNumber"));
                        }
                    }
                } else if (OhSectionList.contains(kmlPlacemark.getExtendedData("SectionId"))) {
                    int OhSize = OhObject.getJSONArray("features").length();
                    for (int k = 0; k < OhSize; k++) {
                        if (OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String fromY = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(0));
                            String fromX = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(1));
//                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(fromX), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.valueOf(fromY), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.parseDouble(fromY), Double.parseDouble(fromX), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l")), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l"))));
                            Log.d("SwitchOverheadFrom", kmlPlacemark.getExtendedData("DeviceNumber"));
                        } else if (OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && !kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String toX = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(1));
                            String toY = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(0));
//                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(toX), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(toY)));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(toY), Double.valueOf(toX)));
                            Log.d("SwitchOverheadTo", kmlPlacemark.getExtendedData("DeviceNumber"));
                        }
                    }
                }

            } catch (Exception e) {
                ErrorPdfLogger.logCrash(MapActivity.this,e);
                Log.d("Main", Objects.requireNonNull(e.getMessage()));
            }
        }

        @Override
        public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {

        }

        @Override
        public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

        }

        @Override
        public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {

        }

        @Override
        public void onFeature(Overlay overlay, KmlFeature kmlFeature) {

        }
    }



    public class ReclouserKmlStyler implements KmlFeature.Styler {

        private int mColor;
        private MapView mapView;

        public ReclouserKmlStyler(int mColor, MapView mapView) {
            this.mColor = mColor;
            this.mapView = mapView;
        }

        @Override
        public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {
            try {
                int i = kmlPlacemark.mGeometry.mCoordinates.size() - 1;
                GeoPoint geoPoint = new GeoPoint(kmlPoint.mCoordinates.get(i).getLatitude(), kmlPoint.mCoordinates.get(i).getLongitude());
                marker.setPosition(geoPoint);
                marker.setRelatedObject(kmlPlacemark);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
                reclosureSectionId.put(kmlPlacemark.getExtendedData("DeviceNumber"), marker);
                reclosureList.add(marker);

                if (kmlPlacemark.getExtendedData("ClosedPhase").equalsIgnoreCase("7")) {
                    Drawable drawable = getResources().getDrawable(R.drawable.reclosed);
                    Bitmap bites = drawableToBitmap(drawable);
                    Bitmap customizedBitmap = changeBitmapColor(bites, Color.BLACK);
                    marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(customizedBitmap, 0, 90)));
//                    marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                    marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_X_l"))));
                   /* marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_Y_l")),
                            Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l"))));*/
//                    marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                } else {
                    Drawable drawable = getResources().getDrawable(R.drawable.reclosed);
                    Bitmap bites = drawableToBitmap(drawable);
                    Bitmap customizedBitmap = changeBitmapColor(bites, Color.BLACK);
                    marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), addPaddingToBitmap(customizedBitmap, 0, 0)));
                    marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                }

                marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker, MapView mapView) {

                        networkId = kmlPlacemark.getExtendedData("NetworkId");
                        nodeId = kmlPlacemark.getExtendedData("ToNodeId");

                        if (Config.isLoadFlow) {
                            highlightLoadFlowDevice(marker, "10", kmlPlacemark.getExtendedData("DeviceNumber"));
                            LoadFlowBox loadFlowBox = new LoadFlowBox(mapView.getContext(), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"), loadFlowList, kmlPlacemark.getExtendedData("EquipmentId"));
                            loadFragment(loadFlowBox, "loadFlowBoxTag");
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else if (Config.isShortCircuit) {
                            highlightShortCircuitDevice(marker, "10", kmlPlacemark.getExtendedData("DeviceNumber"));
                            ShortCircuitBox shortCircuitBox = new ShortCircuitBox(mapView.getContext(), shortCircuitList, kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"));
                            loadFragment(shortCircuitBox, "shortCircuitBoxTag");
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else if (Config.isLoadAllocation) {
                            highlightDevice(marker, "10", kmlPlacemark.getExtendedData("DeviceNumber"));
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                            jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                            RecloserSnippet reclosureSnippet = new RecloserSnippet(MapActivity.this, kmlPlacemark.getExtendedData("SectionId"), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("EquipmentId"), kmlPlacemark.getExtendedData("NetworkId"), kmlPlacemark.getExtendedData("DeviceType"), kmlPlacemark.getExtendedData("Voltage"), jsonObject);
                            reclosureSnippet.show();
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else {
                            highlightDevice(marker, "10", kmlPlacemark.getExtendedData("DeviceNumber"));
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                            jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                            RecloserSnippet reclosureSnippet = new RecloserSnippet(MapActivity.this, kmlPlacemark.getExtendedData("SectionId"), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("EquipmentId"), kmlPlacemark.getExtendedData("NetworkId"), kmlPlacemark.getExtendedData("DeviceType"), kmlPlacemark.getExtendedData("Voltage"), jsonObject);
                            reclosureSnippet.show();
                            if (isTracing) {
                                ReSetColor();
                            }
                        }
                        binding.map.invalidate();
                        return true;
                    }
                });

                if (CaSectionList.contains(kmlPlacemark.getExtendedData("SectionId"))) {
                    int CaSize = CaObject.getJSONArray("features").length();
                    for (int j = 0; j < CaSize; j++) {
                        if (CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String fromY = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(0));
                            String fromX = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(1));
//                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_X_l"))));
//                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(fromX), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.valueOf(fromY), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(fromY), Double.valueOf(fromX), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l")), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l"))));

                            Log.d("ReclosureCableFrom", kmlPlacemark.getExtendedData("DeviceNumber"));
                        } else if (CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && !kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String toY = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(0));
                            String toX = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(1));
                            //marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), addPaddingToBitmap(ReclosureBitmap, 0, 80)));
//                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(toX), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(toY)));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(toY), Double.valueOf(toX)));
                            Log.d("ReclosureCableTo", kmlPlacemark.getExtendedData("DeviceNumber"));
                        }
                    }
                } else if (OhSectionList.contains(kmlPlacemark.getExtendedData("SectionId"))) {
                    int OhSize = OhObject.getJSONArray("features").length();
                    for (int k = 0; k < OhSize; k++) {
                        if (OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String fromY = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(0));
                            String fromX = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(1));
//                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(fromX), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.valueOf(fromY), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.parseDouble(fromY), Double.parseDouble(fromX), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l")), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l"))));
                            Log.d("ReclosureOverheadFrom", kmlPlacemark.getExtendedData("DeviceNumber"));
                        } else if (OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && !kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String toX = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(1));
                            String toY = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(0));
//                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(toX), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(toY)));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(toY), Double.valueOf(toX)));
                            Log.d("ReclosureOverheadTo", kmlPlacemark.getExtendedData("DeviceNumber"));
                        }
                    }
                }

            } catch (Exception e) {
                ErrorPdfLogger.logCrash(MapActivity.this,e);
                Log.d("Main", Objects.requireNonNull(e.getMessage()));
            }
        }

        @Override
        public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {

        }

        @Override
        public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

        }

        @Override
        public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {

        }

        @Override
        public void onFeature(Overlay overlay, KmlFeature kmlFeature) {

        }
    }

    public class ShuntCapacitorKmlStyler implements KmlFeature.Styler {

        private int mColor;
        private MapView mapView;

        public ShuntCapacitorKmlStyler(int mColor, MapView mapView) {
            this.mColor = mColor;
            this.mapView = mapView;
        }

        @Override
        public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {
            try {
                int i = kmlPlacemark.mGeometry.mCoordinates.size() - 1;
                GeoPoint geoPoint = new GeoPoint(kmlPoint.mCoordinates.get(i).getLatitude(), kmlPoint.mCoordinates.get(i).getLongitude());
                marker.setPosition(geoPoint);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
                capacitorSectionId.put(kmlPlacemark.getExtendedData("DeviceNumber"), marker);
                capacitorList.add(marker);

                if (spLineSectionList.contains(kmlPlacemark.getExtendedData("DeviceNumber"))) {
                    marker.setRotation((float) ResponseDataUtils.calculate(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l")), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l"))) - 90);
                    marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.BLACK), 0, 0)));
                } else {
                    marker.setRotation((float) ResponseDataUtils.calculate(Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                    marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.BLACK), 0, 0)));
                }

                marker.setOnMarkerClickListener((marker1, mapView) -> {
                    if (Config.isLoadFlow) {
                        highlightLoadFlowDevice(marker1, "61", kmlPlacemark.getExtendedData("DeviceNumber"));
                        LoadFlowBox loadFlowBox = new LoadFlowBox(mapView.getContext(), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"), loadFlowList, kmlPlacemark.getExtendedData("EquipmentId"));
                        loadFragment(loadFlowBox, "loadFlowBoxTag");
                        if (isTracing) {
                            ReSetColor();
                        }
                    } else if (Config.isShortCircuit) {
                        highlightShortCircuitDevice(marker1, "61", kmlPlacemark.getExtendedData("DeviceNumber"));
                        ShortCircuitBox shortCircuitBox = new ShortCircuitBox(mapView.getContext(), shortCircuitList, kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"));
                        loadFragment(shortCircuitBox, "shortCircuitBoxTag");
                        if (isTracing) {
                            ReSetColor();
                        }
                    } else if (Config.isLoadAllocation) {
                        highlightDevice(marker1, "61", kmlPlacemark.getExtendedData("DeviceNumber"));
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                        jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                        ShuntCapacitorSnippet shuntCapacitorSnippet = new ShuntCapacitorSnippet(MapActivity.this, kmlPlacemark.getExtendedData("SectionId"), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("EquipmentId"), kmlPlacemark.getExtendedData("NetworkId"), kmlPlacemark.getExtendedData("DeviceType"), jsonObject);
                        shuntCapacitorSnippet.show();
                        if (isTracing) {
                            ReSetColor();
                        }
                    } else {
                        highlightDevice(marker1, "61", kmlPlacemark.getExtendedData("DeviceNumber"));
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                        jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                        ShuntCapacitorSnippet shuntCapacitorSnippet = new ShuntCapacitorSnippet(MapActivity.this, kmlPlacemark.getExtendedData("SectionId"), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("EquipmentId"), kmlPlacemark.getExtendedData("NetworkId"), kmlPlacemark.getExtendedData("DeviceType"), jsonObject);
                        shuntCapacitorSnippet.show();
                        if (isTracing) {
                            ReSetColor();
                        }
                    }
                    binding.map.invalidate();
                    return true;
                });

                if (CaSectionList.contains(kmlPlacemark.getExtendedData("SectionId"))) {
                    int CaSize = CaObject.getJSONArray("features").length();
                    for (int j = 0; j < CaSize; j++) {
                        if (CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && kmlPlacemark.getExtendedData("Location").equals("2")) {
                            Log.d("CaFromSP", kmlPlacemark.getExtendedData("DeviceNumber"));
                            String fromY = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(0));
                            String fromX = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(1));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(fromX), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.valueOf(fromY), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                        } else if (CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && !kmlPlacemark.getExtendedData("Location").equals("2")) {
                            Log.d("CaToSP", kmlPlacemark.getExtendedData("DeviceNumber"));
                            String toY = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(0));
                            String toX = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(1));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(toX), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(toY)));
                        }
                    }
                } else if (OhSectionList.contains(kmlPlacemark.getExtendedData("SectionId"))) {
                    int OhSize = OhObject.getJSONArray("features").length();
                    for (int k = 0; k < OhSize; k++) {
                        if (OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && kmlPlacemark.getExtendedData("Location").equals("2")) {
                            Log.d("OhFromSP", kmlPlacemark.getExtendedData("DeviceNumber"));
                            String fromY = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(0));
                            String fromX = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(1));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(fromX), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.valueOf(fromY), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                        } else if (OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && !kmlPlacemark.getExtendedData("Location").equals("2")) {
                            Log.d("OhToSP", kmlPlacemark.getExtendedData("DeviceNumber"));
                            String toX = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(1));
                            String toY = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(0));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(toX), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(toY)));
                        }
                    }
                }

            } catch (Exception e) {
                Log.d("Main", e.getMessage());
            }
        }

        @Override
        public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {

        }

        @Override
        public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {
        }

        @Override
        public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {
        }

        @Override
        public void onFeature(Overlay overlay, KmlFeature kmlFeature) {
        }

    }

    public class SpotLoadKmlStyler implements KmlFeature.Styler {

        private int mColor;
        private MapView mapView;

        public SpotLoadKmlStyler(int mColor, MapView mapView) {
            this.mColor = mColor;
            this.mapView = mapView;
        }

        @Override
        public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {
            try {
                int i = kmlPlacemark.mGeometry.mCoordinates.size() - 1;
                GeoPoint geoPoint = new GeoPoint(kmlPoint.mCoordinates.get(i).getLatitude(), kmlPoint.mCoordinates.get(i).getLongitude());
                marker.setPosition(geoPoint);
                marker.setRelatedObject(kmlPlacemark);
                spotloadSectionId.put(kmlPlacemark.getExtendedData("DeviceNumber"), marker);
                spotLoadList.add(marker);

                int paddingPx = 0;
                Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                        BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                Canvas canvas1 = new Canvas(paddedBitmap);
                canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);

                if (spLineSectionList.contains(kmlPlacemark.getExtendedData("DeviceNumber"))) {
                    marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.BLACK), 0, 0)));
//                    marker.setRotation((float) ResponseDataUtils.calculateAngles(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l")), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l"))) - 90);
                    marker.setRotation((float) ResponseDataUtils.calculateAngles(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l")), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l"))));
                } else {
                    marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.BLACK), 0, 70)));
//                    marker.setRotation((float) ResponseDataUtils.calculateAngles(Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_X_l"))));
                    //some Point work correct
//                    marker.setRotation((float) ResponseDataUtils.CalculateAngel(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l"))));

                    //Dt Correct Angel Calculation
//                marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.parseDouble(fromY),
//                Double.parseDouble(toY), Double.parseDouble(fromX), Double.parseDouble(toX)));

//                    marker.setRotation((float) ResponseDataUtils.calgles(Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
//                    marker.setRotation((float) ResponseDataUtils.calculateAngles(Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                    marker.setRotation((float) ResponseDataUtils.calculateAngles(Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                }

                marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker, MapView mapView) {

                        networkId = kmlPlacemark.getExtendedData("NetworkId");
                        nodeId = kmlPlacemark.getExtendedData("ToNodeId");

                        if (prefManager.getUserType().contains("Edit")) {
                            DelDeviceNumber = kmlPlacemark.getExtendedData("DeviceNumber");
                            DelDevice.put(kmlPlacemark.getExtendedData("DeviceNumber"), marker);
                        }
                        if (Config.isLoadFlow) {
                            highlightLoadFlowDevice(marker, "20", kmlPlacemark.getExtendedData("DeviceNumber"));
                            LoadFlowBox loadFlowBox = new LoadFlowBox(mapView.getContext(), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"), loadFlowList, "");
                            loadFragment(loadFlowBox, "loadFlowBoxTag");
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else if (Config.isShortCircuit) {
                            highlightShortCircuitDevice(marker, "20", kmlPlacemark.getExtendedData("DeviceNumber"));
                            ShortCircuitBox shortCircuitBox = new ShortCircuitBox(mapView.getContext(), shortCircuitList, kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"));
                            loadFragment(shortCircuitBox, "shortCircuitBoxTag");
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else if (Config.isLoadAllocation) {
                            highlightDevice(marker, "20", kmlPlacemark.getExtendedData("DeviceNumber"));
                           /* if (spLineSectionList.contains(kmlPlacemark.getExtendedData("DeviceNumber"))) {
                                marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), ResponseDataUtils.addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.GREEN), 0, 0)));
                                if (!secNodeSectionId.isEmpty()) {
                                    if (secNodeSectionId.get(kmlPlacemark.getExtendedData("DeviceNumber")) != null) {
                                        secNodeSectionId.get(kmlPlacemark.getExtendedData("DeviceNumber")).getPaint().setColor(Color.GREEN);
                                    }
                                }
                            } else {
                                marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), ResponseDataUtils.addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.GREEN), 0, 35)));
                                if (!secNodeSectionId.isEmpty()) {
                                    if (secNodeSectionId.get(kmlPlacemark.getExtendedData("DeviceNumber")) != null) {
                                        secNodeSectionId.get(kmlPlacemark.getExtendedData("DeviceNumber")).getPaint().setColor(Color.GREEN);
                                    }
                                }
                            }*/
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                            jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                            SpotLoadSnippet spotLoadSnippet = new SpotLoadSnippet(MapActivity.this, kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("NetworkId"), kmlPlacemark.getExtendedData("ConnectedKVA"), kmlPlacemark.getExtendedData("KWHUsage"), kmlPlacemark.getExtendedData("DeviceType"), jsonObject);
                            spotLoadSnippet.show();
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else {
                            highlightDevice(marker, "20", kmlPlacemark.getExtendedData("DeviceNumber"));
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                            jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                            SpotLoadSnippet spotLoadSnippet = new SpotLoadSnippet(MapActivity.this, kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("NetworkId"), kmlPlacemark.getExtendedData("ConnectedKVA"), kmlPlacemark.getExtendedData("Customers"), kmlPlacemark.getExtendedData("DeviceType"), jsonObject);
                            spotLoadSnippet.show();
                            if (isTracing) {
                                ReSetColor();
                            }
                        }
                        binding.map.invalidate();
                        return true;
                    }
                });

                if (CaSectionList.contains(kmlPlacemark.getExtendedData("SectionId"))) {
                    int CaSize = CaObject.getJSONArray("features").length();
                    for (int j = 0; j < CaSize; j++) {
                        if (CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String fromY = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(0));
                            String fromX = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(1));
//                            marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), paddedBitmap));

                            marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.BLACK), 0, 70)));
                            marker.setRotation((float) ResponseDataUtils.calculateAngles(Double.parseDouble(fromX), Double.parseDouble(fromY), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l")))); // -90
                        } else if (CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && !kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String toY = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(0));
                            String toX = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(1));
//                            marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), paddedBitmap));
                            marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.BLACK), 0, 70)));
//                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(toX), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(toY)) - 90);
                            marker.setRotation((float) ResponseDataUtils.calculateAngles(Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.parseDouble(toX), Double.parseDouble(toY)));
                        }
                    }
                } else if (OhSectionList.contains(kmlPlacemark.getExtendedData("SectionId"))) {
                    int OhSize = OhObject.getJSONArray("features").length();
                    for (int k = 0; k < OhSize; k++) {
                        if (OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String fromY = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(0));
                            String fromX = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(1));
//                            marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), paddedBitmap));
                            marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.BLACK), 0, 70)));
                            marker.setRotation((float) ResponseDataUtils.calculateAngles(Double.valueOf(fromX), Double.valueOf(fromY), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                        } else if (OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && !kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String toX = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(1));
                            String toY = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(0));
//                            marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), paddedBitmap));
                            marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.BLACK), 0, 70)));
                            marker.setRotation((float) ResponseDataUtils.calculateAngles(Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.parseDouble(toX), Double.parseDouble(toY)));
                        }
                    }
                }

            } catch (Exception e) {
                ErrorPdfLogger.logCrash(MapActivity.this,e);
                Log.d("Main", e.getMessage());
            }
        }

        @Override
        public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {

        }

        @Override
        public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

        }

        @Override
        public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {

        }

        @Override
        public void onFeature(Overlay overlay, KmlFeature kmlFeature) {

        }
    }

    public class SourceKmlStyler implements KmlFeature.Styler {

        private int mColor;
        private MapView mapView;

        public SourceKmlStyler(int mColor, MapView mapView) {
            this.mColor = mColor;
            this.mapView = mapView;
        }

        @Override
        public void onFeature(Overlay overlay, KmlFeature kmlFeature) {

        }

        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {
            try {
                int i = kmlPlacemark.mGeometry.mCoordinates.size() - 1;
                GeoPoint geoPoint = new GeoPoint(kmlPoint.mCoordinates.get(i).getLatitude(), kmlPoint.mCoordinates.get(i).getLongitude());
                marker.setPosition(geoPoint);
                marker.setRelatedObject(kmlPlacemark);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);

                Drawable drawable = getResources().getDrawable(R.drawable.source);
                Bitmap bites = drawableToBitmap(drawable);

                Bitmap customizedBitmap = changeSourceColor(bites, Color.BLACK);

                marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), addPaddingToBitmap(customizedBitmap, 60, 0)));

                marker.setOnMarkerClickListener((marker1, mapView1) -> {
                    SourceDialog sourceDialog = new SourceDialog(MapActivity.this, getSupportFragmentManager(), getLifecycle(), kmlPlacemark.getExtendedData("NetworkId"), kmlPlacemark.getExtendedData("NodeId"), kmlPlacemark.getExtendedData("UTMX"), kmlPlacemark.getExtendedData("UTMY"), kmlPlacemark.getExtendedData("fromX"), kmlPlacemark.getExtendedData("fromy"));
                    sourceDialog.show();
                    return true;
                });

            } catch (Exception e) {
                ErrorPdfLogger.logCrash(MapActivity.this,e);
                Log.d("Exception", e.getLocalizedMessage());
            }
        }

        @Override
        public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {
        }

        @Override
        public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

        }

        @Override
        public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {

        }
    }

    //Add new Devices KML Styler

    public class RecloserKmlStyler implements KmlFeature.Styler {

        private int mColor;
        private MapView mapView;

        public RecloserKmlStyler(int mColor, MapView mapView) {
            this.mColor = mColor;
            this.mapView = mapView;
        }

        @Override
        public void onFeature(Overlay overlay, KmlFeature kmlFeature) {

        }

        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {

        }

        @Override
        public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {
        }

        @Override
        public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

        }

        @Override
        public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {

        }
    }

    public class BattryKmlStyler implements KmlFeature.Styler {

        private int mColor;
        private MapView mapView;

        public BattryKmlStyler(int mColor, MapView mapView) {
            this.mColor = mColor;
            this.mapView = mapView;
        }

        @Override
        public void onFeature(Overlay overlay, KmlFeature kmlFeature) {

        }

        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {
            try {
                int i = kmlPlacemark.mGeometry.mCoordinates.size() - 1;
                GeoPoint geoPoint = new GeoPoint(kmlPoint.mCoordinates.get(i).getLatitude(), kmlPoint.mCoordinates.get(i).getLongitude());
                marker.setPosition(geoPoint);
                marker.setRelatedObject(kmlPlacemark);
                marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(),
                        addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Battery(), Color.BLACK), 0, 35)));

                marker.setOnMarkerClickListener((clickedMarker, clickedMapView) -> {
                    networkId = kmlPlacemark.getExtendedData("NetworkId");
                    nodeId = kmlPlacemark.getExtendedData("ToNodeId");

                    if (prefManager.getUserType().contains("Edit")) {
                        DelDeviceNumber = kmlPlacemark.getExtendedData("DeviceNumber");
                        DelDevice.put(kmlPlacemark.getExtendedData("DeviceNumber"), clickedMarker);
                    }

                    if (Config.isLoadFlow) {
                        highlightLoadFlowDevice(clickedMarker, kmlPlacemark.getExtendedData("DeviceType"), kmlPlacemark.getExtendedData("DeviceNumber"));
                        LoadFlowBox loadFlowBox = new LoadFlowBox(clickedMapView.getContext(), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"), loadFlowList, "");
                        loadFragment(loadFlowBox, "loadFlowBoxTag");
                    } else if (Config.isShortCircuit) {
                        highlightShortCircuitDevice(clickedMarker, kmlPlacemark.getExtendedData("DeviceType"), kmlPlacemark.getExtendedData("DeviceNumber"));
                        ShortCircuitBox shortCircuitBox = new ShortCircuitBox(clickedMapView.getContext(), shortCircuitList, kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"));
                        loadFragment(shortCircuitBox, "shortCircuitBoxTag");
                    } else {
                        highlightDevice(clickedMarker, kmlPlacemark.getExtendedData("DeviceType"), kmlPlacemark.getExtendedData("DeviceNumber"));
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                        jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                        BatterySnippet batterySnippet = new BatterySnippet(MapActivity.this,
                                kmlPlacemark.getExtendedData("SectionId"),
                                kmlPlacemark.getExtendedData("DeviceNumber"),
                                kmlPlacemark.getExtendedData("EquipmentId"),
                                kmlPlacemark.getExtendedData("NetworkId"),
                                kmlPlacemark.getExtendedData("DeviceType"),
                                jsonObject);
                        batterySnippet.show();
                    }

                    if (isTracing) {
                        ReSetColor();
                    }
                    binding.map.invalidate();
                    return true;
                });
            } catch (Exception e) {
                ErrorPdfLogger.logCrash(MapActivity.this, e);
                Log.d("Exception", e.getLocalizedMessage());
            }
        }

        @Override
        public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {
        }

        @Override
        public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

        }

        @Override
        public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {

        }
    }

    public class PhotoVoltaicKmlStyler implements KmlFeature.Styler {

        private int mColor;
        private MapView mapView;

        public PhotoVoltaicKmlStyler(int mColor, MapView mapView) {
            this.mColor = mColor;
            this.mapView = mapView;
        }

        @Override
        public void onFeature(Overlay overlay, KmlFeature kmlFeature) {

        }

        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {
            try {
                int i = kmlPlacemark.mGeometry.mCoordinates.size() - 1;
                GeoPoint geoPoint = new GeoPoint(kmlPoint.mCoordinates.get(i).getLatitude(), kmlPoint.mCoordinates.get(i).getLongitude());
                marker.setPosition(geoPoint);
                marker.setRelatedObject(kmlPlacemark);
                marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(),
                        addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.PhotoVoltaic(), Color.BLACK), 0, 35)));

                marker.setOnMarkerClickListener((clickedMarker, clickedMapView) -> {
                    networkId = kmlPlacemark.getExtendedData("NetworkId");
                    nodeId = kmlPlacemark.getExtendedData("ToNodeId");

                    if (prefManager.getUserType().contains("Edit")) {
                        DelDeviceNumber = kmlPlacemark.getExtendedData("DeviceNumber");
                        DelDevice.put(kmlPlacemark.getExtendedData("DeviceNumber"), clickedMarker);
                    }

                    if (Config.isLoadFlow) {
                        highlightLoadFlowDevice(clickedMarker, kmlPlacemark.getExtendedData("DeviceType"), kmlPlacemark.getExtendedData("DeviceNumber"));
                        LoadFlowBox loadFlowBox = new LoadFlowBox(clickedMapView.getContext(), kmlPlacemark.getExtendedData("DeviceNumber"),
                                kmlPlacemark.getExtendedData("DeviceType"), loadFlowList, "");
                        loadFragment(loadFlowBox, "loadFlowBoxTag");
                    } else if (Config.isShortCircuit) {
                        highlightShortCircuitDevice(clickedMarker, kmlPlacemark.getExtendedData("DeviceType"), kmlPlacemark.getExtendedData("DeviceNumber"));
                        ShortCircuitBox shortCircuitBox = new ShortCircuitBox(clickedMapView.getContext(), shortCircuitList,
                                kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"));
                        loadFragment(shortCircuitBox, "shortCircuitBoxTag");
                    } else {
                        highlightDevice(clickedMarker, kmlPlacemark.getExtendedData("DeviceType"), kmlPlacemark.getExtendedData("DeviceNumber"));
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                        jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                        PhotoVoltaicSnippet photoVoltaicSnippet = new PhotoVoltaicSnippet(MapActivity.this,
                                kmlPlacemark.getExtendedData("SectionId"),
                                kmlPlacemark.getExtendedData("DeviceNumber"),
                                kmlPlacemark.getExtendedData("EquipmentId"),
                                kmlPlacemark.getExtendedData("NetworkId"),
                                kmlPlacemark.getExtendedData("DeviceType"),
                                jsonObject);
                        photoVoltaicSnippet.show();
                    }

                    if (isTracing) {
                        ReSetColor();
                    }
                    binding.map.invalidate();
                    return true;
                });
            } catch (Exception e) {
                ErrorPdfLogger.logCrash(MapActivity.this,e);
                Log.d("Exception", e.getLocalizedMessage());
            }
        }

        @Override
        public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {
        }

        @Override
        public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

        }

        @Override
        public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {

        }
    }

    public class WindKmlStyler implements KmlFeature.Styler {

        private int mColor;
        private MapView mapView;

        public WindKmlStyler(int mColor, MapView mapView) {
            this.mColor = mColor;
            this.mapView = mapView;
        }

        @Override
        public void onFeature(Overlay overlay, KmlFeature kmlFeature) {

        }

        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {
            try {
                int i = kmlPlacemark.mGeometry.mCoordinates.size() - 1;
                GeoPoint geoPoint = new GeoPoint(kmlPoint.mCoordinates.get(i).getLatitude(), kmlPoint.mCoordinates.get(i).getLongitude());
                marker.setPosition(geoPoint);
                marker.setRelatedObject(kmlPlacemark);
                marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(),
                        addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Wind(), Color.BLACK), 0, 35)));

                marker.setOnMarkerClickListener((clickedMarker, clickedMapView) -> {
                    networkId = kmlPlacemark.getExtendedData("NetworkId");
                    nodeId = kmlPlacemark.getExtendedData("ToNodeId");

                    if (prefManager.getUserType().contains("Edit")) {
                        DelDeviceNumber = kmlPlacemark.getExtendedData("DeviceNumber");
                        DelDevice.put(kmlPlacemark.getExtendedData("DeviceNumber"), clickedMarker);
                    }

                    if (Config.isLoadFlow) {
                        highlightLoadFlowDevice(clickedMarker, kmlPlacemark.getExtendedData("DeviceType"), kmlPlacemark.getExtendedData("DeviceNumber"));
                        LoadFlowBox loadFlowBox = new LoadFlowBox(clickedMapView.getContext(), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"), loadFlowList, "");
                        loadFragment(loadFlowBox, "loadFlowBoxTag");
                    } else if (Config.isShortCircuit) {
                        highlightShortCircuitDevice(clickedMarker, kmlPlacemark.getExtendedData("DeviceType"), kmlPlacemark.getExtendedData("DeviceNumber"));
                        ShortCircuitBox shortCircuitBox = new ShortCircuitBox(clickedMapView.getContext(), shortCircuitList, kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"));
                        loadFragment(shortCircuitBox, "shortCircuitBoxTag");
                    } else {
                        highlightDevice(clickedMarker, kmlPlacemark.getExtendedData("DeviceType"), kmlPlacemark.getExtendedData("DeviceNumber"));
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                        jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                        WindSnippet windSnippet = new WindSnippet(MapActivity.this,
                                kmlPlacemark.getExtendedData("SectionId"),
                                kmlPlacemark.getExtendedData("DeviceNumber"),
                                kmlPlacemark.getExtendedData("EquipmentId"),
                                kmlPlacemark.getExtendedData("NetworkId"),
                                kmlPlacemark.getExtendedData("DeviceType"),
                                jsonObject);
                        windSnippet.show();
                    }

                    if (isTracing) {
                        ReSetColor();
                    }
                    binding.map.invalidate();
                    return true;
                });
            } catch (Exception e) {
                ErrorPdfLogger.logCrash(MapActivity.this,e);
                Log.d("Exception", e.getLocalizedMessage());
            }
        }

        @Override
        public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {
        }

        @Override
        public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

        }

        @Override
        public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {

        }
    }

    public class ShuntReactorKmlStyler implements KmlFeature.Styler {

        private int mColor;
        private MapView mapView;

        public ShuntReactorKmlStyler(int mColor, MapView mapView) {
            this.mColor = mColor;
            this.mapView = mapView;
        }

        @Override
        public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {
            try {
                int i = kmlPlacemark.mGeometry.mCoordinates.size() - 1;
                GeoPoint geoPoint = new GeoPoint(kmlPoint.mCoordinates.get(i).getLatitude(), kmlPoint.mCoordinates.get(i).getLongitude());
                marker.setPosition(geoPoint);
                marker.setRelatedObject(kmlPlacemark);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);

                shuntReactorSectionId.put(kmlPlacemark.getExtendedData("DeviceNumber"), marker);
                shuntReactorList.add(marker);

                Bitmap reactorBitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "SR", 65f, Color.BLACK);

                int paddingLeft = 0;
                int paddingTop = 0;
                int paddingRight = 0;
                int paddingBottom = 20;

                int newWidth = reactorBitmap.getWidth() + paddingLeft + paddingRight;
                int newHeight = reactorBitmap.getHeight() + paddingTop + paddingBottom;

                Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, reactorBitmap.getConfig());
                Canvas canvas = new Canvas(paddedBitmap);
                canvas.drawBitmap(reactorBitmap, paddingLeft, paddingTop, null);

                marker.setIcon(new BitmapDrawable(
                        mapView.getContext().getResources(),
                        addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.BLACK), 0, 10)
                ));

                marker.setRotation((float) ResponseDataUtils.CalculateAng(
                        Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_Y_l")),
                        Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_Y_l")),
                        Double.parseDouble(kmlPlacemark.getExtendedData("FromNode_X_l")),
                        Double.parseDouble(kmlPlacemark.getExtendedData("ToNode_X_l"))
                ));

                marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker, MapView mapView) {

                        networkId = kmlPlacemark.getExtendedData("NetworkId");
                        nodeId = kmlPlacemark.getExtendedData("ToNodeId");

                        if (prefManager.getUserType().contains("Edit")) {
                            DelDeviceNumber = kmlPlacemark.getExtendedData("DeviceNumber");
                            DelDevice.put(kmlPlacemark.getExtendedData("DeviceNumber"), marker);
                        }

                        if (Config.isLoadFlow) {
                            highlightLoadFlowDevice(marker, "18", kmlPlacemark.getExtendedData("DeviceNumber"));
                            LoadFlowBox loadFlowBox = new LoadFlowBox(mapView.getContext(), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"), loadFlowList, kmlPlacemark.getExtendedData("EquipmentId"));
                            loadFragment(loadFlowBox, "loadFlowBoxTag");
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else if (Config.isShortCircuit) {
                            highlightShortCircuitDevice(marker, "18", kmlPlacemark.getExtendedData("DeviceNumber"));
                            ShortCircuitBox shortCircuitBox = new ShortCircuitBox(mapView.getContext(), shortCircuitList, kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("DeviceType"));
                            loadFragment(shortCircuitBox, "shortCircuitBoxTag");
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else if (Config.isLoadAllocation) {
                            highlightDevice(marker, "18", kmlPlacemark.getExtendedData("DeviceNumber"));
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                            jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                            ShuntReactorSnippet shuntReactorSnippet = new ShuntReactorSnippet(MapActivity.this, kmlPlacemark.getExtendedData("SectionId"), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("EquipmentId"), kmlPlacemark.getExtendedData("NetworkId"), kmlPlacemark.getExtendedData("DeviceType"), jsonObject);
                            shuntReactorSnippet.show();
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else {
                            highlightDevice(marker, "18", kmlPlacemark.getExtendedData("DeviceNumber"));
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("DeviceNumber", kmlPlacemark.getExtendedData("DeviceNumber"));
                            jsonObject.addProperty("DeviceType", kmlPlacemark.getExtendedData("DeviceType"));
                            ShuntReactorSnippet shuntReactorSnippet = new ShuntReactorSnippet(MapActivity.this, kmlPlacemark.getExtendedData("SectionId"), kmlPlacemark.getExtendedData("DeviceNumber"), kmlPlacemark.getExtendedData("EquipmentId"), kmlPlacemark.getExtendedData("NetworkId"), kmlPlacemark.getExtendedData("DeviceType"), jsonObject);
                            shuntReactorSnippet.show();
                            if (isTracing) {
                                ReSetColor();
                            }
                        }

                        binding.map.invalidate();
                        return true;
                    }
                });

                if (CaSectionList.contains(kmlPlacemark.getExtendedData("SectionId"))) {
                    int CaSize = CaObject.getJSONArray("features").length();
                    for (int j = 0; j < CaSize; j++) {
                        if (CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String fromY = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(0));
                            String fromX = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(1));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(fromX), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.valueOf(fromY), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                        } else if (CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && !kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String toY = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(0));
                            String toX = String.valueOf(CaObject.getJSONArray("features").getJSONObject(j).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(1));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(toX), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(toY)));
                        }
                    }
                } else if (OhSectionList.contains(kmlPlacemark.getExtendedData("SectionId"))) {
                    int OhSize = OhObject.getJSONArray("features").length();
                    for (int k = 0; k < OhSize; k++) {
                        if (OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String fromY = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(0));
                            String fromX = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() - 2).get(1));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(fromX), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_X_l")), Double.valueOf(fromY), Double.valueOf(kmlPlacemark.getExtendedData("ToNode_Y_l"))));
                        } else if (OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("properties").getString("SectionId").equals(kmlPlacemark.getExtendedData("SectionId")) && OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").length() > 2 && !kmlPlacemark.getExtendedData("Location").equals("2")) {
                            String toX = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(1));
                            String toY = String.valueOf(OhObject.getJSONArray("features").getJSONObject(k).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(1).get(0));
                            marker.setRotation((float) ResponseDataUtils.CalculateAng(Double.valueOf(kmlPlacemark.getExtendedData("FromNode_X_l")), Double.valueOf(toX), Double.valueOf(kmlPlacemark.getExtendedData("FromNode_Y_l")), Double.valueOf(toY)));
                        }
                    }
                }

            } catch (Exception e) {
                ErrorPdfLogger.logCrash(MapActivity.this, e);
                Log.d("Main", e.getMessage());
            }
        }

        @Override
        public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {
        }

        @Override
        public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {
        }

        @Override
        public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {
        }

        @Override
        public void onFeature(Overlay overlay, KmlFeature kmlFeature) {
        }
    }

/*
    public class NodeKmlStyler implements KmlFeature.Styler {

        private int mColor;
        private MapView mapView;

        public NodeKmlStyler(int mColor, MapView mapView) {
            this.mColor = mColor;
            this.mapView = mapView;
        }


        @Override
        public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {
            try {
                int i = kmlPlacemark.mGeometry.mCoordinates.size() - 1;
                GeoPoint geoPoint = new GeoPoint(kmlPoint.mCoordinates.get(i).getLatitude(), kmlPoint.mCoordinates.get(i).getLongitude());
                marker.setPosition(geoPoint);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.dot);
                Bitmap bitmap = drawableToBitmap(drawable);
                marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), bitmap));

                marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker, MapView mapView) {
                        networkId = kmlPlacemark.getExtendedData("NetworkId");
                        nodeId = kmlPlacemark.getExtendedData("NodeId");

                        networkId = kmlPlacemark.getExtendedData("NetworkId");
                        nodeId = kmlPlacemark.getExtendedData("NodeId");
                        selectedNodeID = kmlPlacemark.getExtendedData("NodeId");

                        nodeIdX = kmlPlacemark.getExtendedData("X");
                        nodeIdY = kmlPlacemark.getExtendedData("Y");

                        if (newSectionGeoPointList.isEmpty() && coordinateList.isEmpty()) {
                            newSectionGeoPointList.add(new GeoPoint(Double.parseDouble(kmlPlacemark.getExtendedData("fromy")), Double.parseDouble(kmlPlacemark.getExtendedData("fromX"))));
                            coordinateList.add(new GeoPoint(Double.parseDouble(kmlPlacemark.getExtendedData("fromy")), Double.parseDouble(kmlPlacemark.getExtendedData("fromX"))));
                        } else {
                            newSectionGeoPointList.clear();
                            coordinateList.clear();
                            newSectionGeoPointList.add(new GeoPoint(Double.parseDouble(kmlPlacemark.getExtendedData("fromy")), Double.parseDouble(kmlPlacemark.getExtendedData("fromX"))));
                            coordinateList.add(new GeoPoint(Double.parseDouble(kmlPlacemark.getExtendedData("fromy")), Double.parseDouble(kmlPlacemark.getExtendedData("fromX"))));
                        }

                        //nsc
//                        fromNodeX = Double.parseDouble(kmlPlacemark.getExtendedData("fromy"));
//                        fromNodeY = Double.parseDouble(kmlPlacemark.getExtendedData("fromX"));

                        newConnectionGeoPoint.add(new GeoPoint(Double.parseDouble(kmlPlacemark.getExtendedData("fromy")), Double.parseDouble(kmlPlacemark.getExtendedData("fromX"))));
                        fromNodeID = kmlPlacemark.getExtendedData("NodeId");
                        selectedNode = marker;

                        if (prefManager.getUserType().contains("Analysis")) {
                            NodeSelected nodeSelected = new NodeSelected(mapView.getContext(), kmlPlacemark.getExtendedData("NodeId"), marker);
                            nodeSelected.show();
                        } else if (prefManager.getUserType().contains("Admin")) {
                            NodeSelected nodeSelected = new NodeSelected(mapView.getContext(), kmlPlacemark.getExtendedData("NodeId"), marker);
                            nodeSelected.show();
                        }

                        if (Config.isLoadFlow) {
                            highlightLoadFlowDevice(marker, "99", kmlPlacemark.getExtendedData("NodeId"));
                            LoadFlowBox loadFlowBox = new LoadFlowBox(mapView.getContext(), kmlPlacemark.getExtendedData("NodeId"), "41", loadFlowList, kmlPlacemark.getExtendedData("NodeId"));
                            loadFragment(loadFlowBox, "loadFlowBoxTag");
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else if (Config.isShortCircuit) {
                            highlightShortCircuitDevice(marker, "99", kmlPlacemark.getExtendedData("NodeId"));
                            ShortCircuitBox shortCircuitBox = new ShortCircuitBox(mapView.getContext(), shortCircuitList, kmlPlacemark.getExtendedData("NodeId"), "41");
                            loadFragment(shortCircuitBox, "shortCircuitBoxTag");
                            if (isTracing) {
                                ReSetColor();
                            }
                        } else {
                            highlightDevice(marker, "99", kmlPlacemark.getExtendedData("NodeId"));
                            if (isTracing) {
                                ReSetColor();
                            }
                        }

                        binding.map.invalidate();
                        return true;
                    }
                });

            } catch (Exception e) {
                Log.d("Main", e.getMessage());
            }
        }

        @Override
        public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {

        }

        @Override
        public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {

        }

        @Override
        public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {

        }

        @Override
        public void onFeature(Overlay overlay, KmlFeature kmlFeature) {

        }

    }
*/
    public class NodeKmlStyler implements KmlFeature.Styler {
        private int mColor;
        private MapView mapView;
        private Map<String, Polyline> polylineMap;


        @SuppressWarnings("unchecked")
        public NodeKmlStyler(int mColor, MapView mapView, Map<String, Polyline> polylineMap) {
            this.mColor = mColor;
            this.mapView = mapView;
            this.polylineMap = polylineMap;
        }

        @Override
        public void onPoint(Marker marker, KmlPlacemark kmlPlacemark, KmlPoint kmlPoint) {
            try {
                int i = kmlPlacemark.mGeometry.mCoordinates.size() - 1;
                GeoPoint geoPoint = new GeoPoint(kmlPoint.mCoordinates.get(i).getLatitude(), kmlPoint.mCoordinates.get(i).getLongitude());
                marker.setPosition(geoPoint);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = mapView.getContext().getResources().getDrawable(R.drawable.dot);
                Bitmap bitmap = drawableToBitmap(drawable);
                marker.setIcon(new BitmapDrawable(mapView.getContext().getResources(), bitmap));

                if (prefManager.getUserType() != null && prefManager.getUserType().contains("Edit")) {
                    marker.setDraggable(true);
                }

                String markerNodeId = kmlPlacemark.getExtendedData("NodeId");
                marker.setId(markerNodeId);

                marker.setOnMarkerClickListener((marker1, mapView1) -> {
                    networkId = kmlPlacemark.getExtendedData("NetworkId");
                    nodeId = kmlPlacemark.getExtendedData("NodeId");
                    String toNodeId = kmlPlacemark.getExtendedData("ToNodeId");
                    if (toNodeId != null && toNodeId.trim().isEmpty()) {
                        toNodeId = null;
                    }
                    selectedNodeID = toNodeId != null ? toNodeId : kmlPlacemark.getExtendedData("NodeId");
                    nodeIdX = toNodeId != null ? kmlPlacemark.getExtendedData("ToNode_X_l") : kmlPlacemark.getExtendedData("X");
                    if (nodeIdX == null || nodeIdX.trim().isEmpty()) {
                        nodeIdX = kmlPlacemark.getExtendedData("fromX");
                    }
                    nodeIdY = toNodeId != null ? kmlPlacemark.getExtendedData("ToNode_Y_l") : kmlPlacemark.getExtendedData("Y");
                    if (nodeIdY == null || nodeIdY.trim().isEmpty()) {
                        nodeIdY = kmlPlacemark.getExtendedData("fromy");
                    }
                    selectedNodeVoltage = null;
                    for (Polyline polyline : CaSectionId.values()) {
                        if (polyline != null && polyline.getRelatedObject() instanceof KmlPlacemark) {
                            KmlPlacemark linePlacemark = (KmlPlacemark) polyline.getRelatedObject();
                            String lineFromNodeId = linePlacemark.getExtendedData("FromNodeId");
                            String lineToNodeId = linePlacemark.getExtendedData("ToNodeId");
                            String lineVoltage = linePlacemark.getExtendedData("Voltage");
                            if (selectedNodeID != null && !TextUtils.isEmpty(lineVoltage) && (selectedNodeID.equals(lineToNodeId) || selectedNodeID.equals(lineFromNodeId))) {
                                selectedNodeVoltage = lineVoltage;
                                break;
                            }
                        }
                    }
                    if (TextUtils.isEmpty(selectedNodeVoltage)) {
                        for (Polyline polyline : OhSectionId.values()) {
                            if (polyline != null && polyline.getRelatedObject() instanceof KmlPlacemark) {
                                KmlPlacemark linePlacemark = (KmlPlacemark) polyline.getRelatedObject();
                                String lineFromNodeId = linePlacemark.getExtendedData("FromNodeId");
                                String lineToNodeId = linePlacemark.getExtendedData("ToNodeId");
                                String lineVoltage = linePlacemark.getExtendedData("Voltage");
                                if (selectedNodeID != null && !TextUtils.isEmpty(lineVoltage) && (selectedNodeID.equals(lineToNodeId) || selectedNodeID.equals(lineFromNodeId))) {
                                    selectedNodeVoltage = lineVoltage;
                                    break;
                                }
                            }
                        }
                    }
                    if (TextUtils.isEmpty(selectedNodeVoltage)) {
                        for (Polyline polyline : UnBalSectionId.values()) {
                            if (polyline != null && polyline.getRelatedObject() instanceof KmlPlacemark) {
                                KmlPlacemark linePlacemark = (KmlPlacemark) polyline.getRelatedObject();
                                String lineFromNodeId = linePlacemark.getExtendedData("FromNodeId");
                                String lineToNodeId = linePlacemark.getExtendedData("ToNodeId");
                                String lineVoltage = linePlacemark.getExtendedData("Voltage");
                                if (selectedNodeID != null && !TextUtils.isEmpty(lineVoltage) && (selectedNodeID.equals(lineToNodeId) || selectedNodeID.equals(lineFromNodeId))) {
                                    selectedNodeVoltage = lineVoltage;
                                    break;
                                }
                            }
                        }
                    }
                    if (TextUtils.isEmpty(selectedNodeVoltage)) {
                        selectedNodeVoltage = kmlPlacemark.getExtendedData("Voltage");
                    }
                    if (TextUtils.isEmpty(selectedNodeVoltage)) {
                        for (Marker transformerMarker : transformerSectionId.values()) {
                            if (transformerMarker != null && transformerMarker.getRelatedObject() instanceof KmlPlacemark) {
                                KmlPlacemark transformerPlacemark = (KmlPlacemark) transformerMarker.getRelatedObject();
                                String transformerFromNodeId = transformerPlacemark.getExtendedData("FromNodeId");
                                String transformerToNodeId = transformerPlacemark.getExtendedData("ToNodeId");
                                String transformerVoltage = transformerPlacemark.getExtendedData("Voltage");
                                if (selectedNodeID != null && !TextUtils.isEmpty(transformerVoltage) &&
                                        (selectedNodeID.equals(transformerToNodeId) || selectedNodeID.equals(transformerFromNodeId))) {
                                    selectedNodeVoltage = transformerVoltage;
                                    break;
                                }
                            }
                        }
                    }
                    newSectionGeoPointList.clear();
                    coordinateList.clear();
                    newSectionGeoPointList.add(marker1.getPosition());
                    coordinateList.add(marker1.getPosition());

                    newConnectionGeoPoint.add(new GeoPoint(Double.parseDouble(kmlPlacemark.getExtendedData("fromy")), Double.parseDouble(kmlPlacemark.getExtendedData("fromX"))));
                    selectedNode = marker1;

                    if (prefManager.getUserType().contains("Analysis") || prefManager.getUserType().contains("Admin")) {
                        NodeSelected nodeSelected = new NodeSelected(mapView.getContext(), markerNodeId, marker1);
                        nodeSelected.show();
                    }

                    if (Config.isLoadFlow) {
                        highlightLoadFlowDevice(marker1, "99", markerNodeId);
                        LoadFlowBox loadFlowBox = new LoadFlowBox(mapView.getContext(), markerNodeId, "41", loadFlowList, markerNodeId);
                        loadFragment(loadFlowBox, "loadFlowBoxTag");
                        if (isTracing) {
                            ReSetColor();
                        }
                    } else if (Config.isShortCircuit) {
                        highlightShortCircuitDevice(marker1, "99", markerNodeId);
                        ShortCircuitBox shortCircuitBox = new ShortCircuitBox(mapView.getContext(), shortCircuitList, markerNodeId, "41");
                        loadFragment(shortCircuitBox, "shortCircuitBoxTag");
                        if (isTracing) {
                            ReSetColor();
                        }
                    } else {
                        highlightDevice(marker1, "99", markerNodeId);
                        if (isTracing) {
                            ReSetColor();
                        }
                    }

                    Projection projection = mapView.getProjection();
                    Point screenPoint = projection.toPixels(marker1.getPosition(), null);
                    int screenX = screenPoint.x;
                    int screenY = screenPoint.y;
                    Log.d("Projection", "Marker clicked at screen: X=" + screenX + ", Y=" + screenY);

                    mapView.invalidate();
                    return true;
                });

                marker.setOnMarkerDragListener(new Marker.OnMarkerDragListener() {
                    @Override
                    public void onMarkerDragStart(Marker marker) {
                    }

                    @Override
                    public void onMarkerDrag(Marker marker) {
                        mapView.invalidate();
                    }

                    @Override
                    public void onMarkerDragEnd(Marker marker) {
                        GeoPoint newPosition = marker.getPosition();
                        String nodeId = marker.getId();

                        kmlPlacemark.setExtendedData("fromX", String.valueOf(newPosition.getLongitude()));
                        kmlPlacemark.setExtendedData("fromy", String.valueOf(newPosition.getLatitude()));
                        kmlPoint.mCoordinates.get(i).setLatitude(newPosition.getLatitude());
                        kmlPoint.mCoordinates.get(i).setLongitude(newPosition.getLongitude());

                        newSectionGeoPointList.clear();
                        coordinateList.clear();
                        newSectionGeoPointList.add(newPosition);
                        coordinateList.add(newPosition);

                        updateConnectedPolyLines(nodeId, newPosition);
                        updateConnectedDevices(nodeId, newPosition);

                        mapView.invalidate();
                    }
                });

            } catch (Exception e) {
                ErrorPdfLogger.logCrash(MapActivity.this,e);
                Log.d("Main", e.getMessage());
            }
        }

        private void updateConnectedPolyLines(String nodeId, GeoPoint newPosition) {
            Polyline startPolyline = polylineMap.get(nodeId + "_start");
            Polyline endPolyline = polylineMap.get(nodeId + "_end");

            if (startPolyline != null) {
                List<GeoPoint> points = startPolyline.getPoints();
                points.set(0, newPosition);
                startPolyline.setPoints(points);

                KmlPlacemark polylinePlacemark = (KmlPlacemark) startPolyline.getRelatedObject();
                if (polylinePlacemark != null) {
                    polylinePlacemark.setExtendedData("fromX", String.valueOf(newPosition.getLongitude()));
                    polylinePlacemark.setExtendedData("fromy", String.valueOf(newPosition.getLatitude()));

                    KmlLineString lineString = (KmlLineString) polylinePlacemark.mGeometry;
                    if (lineString != null && !lineString.mCoordinates.isEmpty()) {
                        lineString.mCoordinates.get(0).setLatitude(newPosition.getLatitude());
                        lineString.mCoordinates.get(0).setLongitude(newPosition.getLongitude());
                    }
                }
            }

            if (endPolyline != null) {
                List<GeoPoint> points = endPolyline.getPoints();
                points.set(points.size() - 1, newPosition);
                endPolyline.setPoints(points);

                KmlPlacemark polylinePlacemark = (KmlPlacemark) endPolyline.getRelatedObject();
                if (polylinePlacemark != null) {
                    polylinePlacemark.setExtendedData("toX", String.valueOf(newPosition.getLongitude()));
                    polylinePlacemark.setExtendedData("toY", String.valueOf(newPosition.getLatitude()));

                    KmlLineString lineString = (KmlLineString) polylinePlacemark.mGeometry;
                    if (lineString != null && !lineString.mCoordinates.isEmpty()) {
                        lineString.mCoordinates.get(lineString.mCoordinates.size() - 1).setLatitude(newPosition.getLatitude());
                        lineString.mCoordinates.get(lineString.mCoordinates.size() - 1).setLongitude(newPosition.getLongitude());
                    }
                }
            }
        }

        @SuppressWarnings("unchecked")
        private void updateConnectedDevices(String nodeId, GeoPoint newPosition) {
            BiConsumer<Map<String, Marker>, String> updateDeviceMarker = (deviceMap, deviceType) -> {
                for (Map.Entry<String, Marker> entry : deviceMap.entrySet()) {
                    Marker deviceMarker = entry.getValue();
                    Object relatedObject = deviceMarker.getRelatedObject();
                    if (!(relatedObject instanceof KmlPlacemark)) {
                        Log.w("NodeKml", deviceType + " marker's related object is not a KmlPlacemark: " + (relatedObject != null ? relatedObject.getClass().getSimpleName() : "null") + ", Marker ID: " + entry.getKey());
                        continue;
                    }

                    KmlPlacemark devicePlacemark = (KmlPlacemark) relatedObject;
                    String fromNodeId = devicePlacemark.getExtendedData("FromNodeId");
                    String toNodeId = devicePlacemark.getExtendedData("ToNodeId");
                    if (nodeId.equals(fromNodeId) || nodeId.equals(toNodeId)) {
                        double fromX = parseDoubleSafe(devicePlacemark.getExtendedData("FromNode_X_l"));
                        double fromY = parseDoubleSafe(devicePlacemark.getExtendedData("FromNode_Y_l"));
                        double toX = parseDoubleSafe(devicePlacemark.getExtendedData("ToNode_X_l"));
                        double toY = parseDoubleSafe(devicePlacemark.getExtendedData("ToNode_Y_l"));

                        if (Double.isNaN(fromX) || Double.isNaN(fromY) || Double.isNaN(toX) || Double.isNaN(toY)) {
                            Log.w("NodeKml", "Invalid node coordinates for " + deviceType);
                            continue;
                        }

                        GeoPoint currentPos = deviceMarker.getPosition();
                        double distanceTotal = Math.hypot(toX - fromX, toY - fromY);
                        double distanceFromFromNode = Math.hypot(currentPos.getLongitude() - fromX, currentPos.getLatitude() - fromY);
                        double t = (distanceTotal > 0) ? distanceFromFromNode / distanceTotal : 0;

                        if (nodeId.equals(fromNodeId)) {
                            fromX = newPosition.getLongitude();
                            fromY = newPosition.getLatitude();
                            devicePlacemark.setExtendedData("FromNode_X_l", String.valueOf(fromX));
                            devicePlacemark.setExtendedData("FromNode_Y_l", String.valueOf(fromY));
                        } else {
                            toX = newPosition.getLongitude();
                            toY = newPosition.getLatitude();
                            devicePlacemark.setExtendedData("ToNode_X_l", String.valueOf(toX));
                            devicePlacemark.setExtendedData("ToNode_Y_l", String.valueOf(toY));
                        }

                        double newX = fromX + t * (toX - fromX);
                        double newY = fromY + t * (toY - fromY);
                        GeoPoint newDevicePos = new GeoPoint(newY, newX);
                        deviceMarker.setPosition(newDevicePos);

                        KmlPoint kmlPoint = (KmlPoint) devicePlacemark.mGeometry;
                        if (kmlPoint != null && !kmlPoint.mCoordinates.isEmpty()) {
                            kmlPoint.mCoordinates.get(0).setLatitude(newY);
                            kmlPoint.mCoordinates.get(0).setLongitude(newX);
                        }
                    }
                }
            };

            updateDeviceMarker.accept(breakerSectionId, "Breaker");
            updateDeviceMarker.accept(transformerSectionId, "Transformer");
            updateDeviceMarker.accept(fuseSectionId, "Fuse");
            updateDeviceMarker.accept(switchSectionId, "Switch");
            updateDeviceMarker.accept(capacitorSectionId, "Capacitor");
            updateDeviceMarker.accept(spotloadSectionId, "Spotload");

            BiConsumer<Map<String, Polyline>, String> updateLine = (lineMap, lineType) -> {
                for (Map.Entry<String, Polyline> entry : lineMap.entrySet()) {
                    Polyline line = entry.getValue();
                    Object relatedObject = line.getRelatedObject();
                    if (!(relatedObject instanceof KmlPlacemark)) {
                        Log.w("NodeKml", lineType + " polyline's related object is not a KmlPlacemark: " + (relatedObject != null ? relatedObject.getClass().getSimpleName() : "null") + ", Polyline ID: " + entry.getKey());
                        continue;
                    }

                    KmlPlacemark linePlacemark = (KmlPlacemark) relatedObject;
                    String fromNodeId = linePlacemark.getExtendedData("FromNodeId");
                    String toNodeId = linePlacemark.getExtendedData("ToNodeId");

                    if (nodeId.equals(fromNodeId) || nodeId.equals(toNodeId)) {
                        List<GeoPoint> points = line.getPoints();
                        KmlLineString lineString = (KmlLineString) linePlacemark.mGeometry;
                        if (lineString == null || lineString.mCoordinates.isEmpty()) {
                            Log.w("NodeKml", "Invalid KmlLineString for " + lineType + ", Polyline ID: " + entry.getKey());
                            continue;
                        }

                        if (nodeId.equals(fromNodeId)) {
                            points.set(0, newPosition);
                            linePlacemark.setExtendedData("fromX", String.valueOf(newPosition.getLongitude()));
                            linePlacemark.setExtendedData("fromy", String.valueOf(newPosition.getLatitude()));
                            lineString.mCoordinates.get(0).setLatitude(newPosition.getLatitude());
                            lineString.mCoordinates.get(0).setLongitude(newPosition.getLongitude());
                        } else {
                            points.set(points.size() - 1, newPosition);
                            linePlacemark.setExtendedData("toX", String.valueOf(newPosition.getLongitude()));
                            linePlacemark.setExtendedData("toY", String.valueOf(newPosition.getLatitude()));
                            lineString.mCoordinates.get(lineString.mCoordinates.size() - 1).setLatitude(newPosition.getLatitude());
                            lineString.mCoordinates.get(lineString.mCoordinates.size() - 1).setLongitude(newPosition.getLongitude());
                        }

                        line.setPoints(points);
                    }
                }
            };

            updateLine.accept(OhSectionId, "Overhead");
            updateLine.accept(UnBalSectionId, "Unbalance");
            updateLine.accept(secNodeSectionId, "SectionNode");
            updateLine.accept(CaSectionId, "Cable");
        }

        private double parseDoubleSafe(String value) {
            try {
                return Double.parseDouble(value);
            } catch (Exception e) {
                return Double.NaN;
            }
        }

        @Override
        public void onLineString(Polyline polyline, KmlPlacemark kmlPlacemark, KmlLineString kmlLineString) {
        }

        @Override
        public void onPolygon(Polygon polygon, KmlPlacemark kmlPlacemark, KmlPolygon kmlPolygon) {
        }

        @Override
        public void onTrack(Polyline polyline, KmlPlacemark kmlPlacemark, KmlTrack kmlTrack) {
        }

        @Override
        public void onFeature(Overlay overlay, KmlFeature kmlFeature) {
        }
    }

    private void ReSetColor() {
        if (Config.isLoadFlow) {
            isTracing = false;
            if (CaPolylineList != null && !CaPolylineList.isEmpty()) {
                for (int j = 0; j < CaPolylineList.size(); j++) {
                    CaPolylineList.get(j).getPaint().setColor(Color.BLACK);
                }
            }

            if (ohPolylineList != null && !ohPolylineList.isEmpty()) {
                for (int j = 0; j < ohPolylineList.size(); j++) {
                    ohPolylineList.get(j).getPaint().setColor(Color.BLACK);
                }
            }

            if (unBalPolylineList != null && !unBalPolylineList.isEmpty()) {
                for (int j = 0; j < unBalPolylineList.size(); j++) {
                    unBalPolylineList.get(j).getPaint().setColor(Color.BLACK);
                }
            }

            if (secNodeList != null && !secNodeList.isEmpty()) {
                for (int j = 0; j < secNodeList.size(); j++) {
                    secNodeList.get(j).getPaint().setColor(Color.BLACK);
                }
            }

            if (breakerList != null && !breakerList.isEmpty()) {
                for (int i = 0; i < breakerList.size(); i++) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    breakerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 95)));
                }
            }

            if (transformerList != null && !transformerList.isEmpty()) {
                for (int i = 0; i < transformerList.size(); i++) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    transformerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 93)));
                }
            }

            if (fuseList != null && !fuseList.isEmpty()) {
                for (int i = 0; i < fuseList.size(); i++) {
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                    fuseList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 0)));
                }
            }

            if (switchedList != null && !switchedList.isEmpty()) {
                for (int i = 0; i < switchedList.size(); i++) {
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                    int paddingLeft = 0; // Left padding in pixels
                    int paddingTop = 0; // Top padding in pixels
                    int paddingRight = 0; // Right padding in pixels
                    int paddingBottom = 12; // Bottom padding in pixels
                    int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                    int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                    Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                    Canvas canva = new Canvas(paddedBitmap);
                    canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                    switchedList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.BLACK), 0, 0)));
                }
            }

            if (capacitorList != null && !capacitorList.isEmpty()) {
                for (int i = 0; i < capacitorList.size(); i++) {
                    capacitorList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.BLACK), 90, 0)));
                }
            }

            if (spotLoadList != null && !spotLoadList.isEmpty()) {
                for (int i = 0; i < spotLoadList.size(); i++) {
                    int paddingPx = 0;
                    Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                            BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                    Canvas canvas1 = new Canvas(paddedBitmap);
                    canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                    spotLoadList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.BLACK), 0, 0)));
                }
            }

            if (loadFlowOverVoltageSectionId != null && overVoltageColors != null && !loadFlowOverVoltageSectionId.isEmpty()) {
                for (int i = 0; i < loadFlowOverVoltageSectionId.size(); i++) {

                    if (CaSectionId.containsKey(loadFlowOverVoltageSectionId.get(i))) {
                        Objects.requireNonNull(CaSectionId.get(loadFlowOverVoltageSectionId.get(i))).setColor(Color.parseColor(overVoltageColors));
                    }

                    if (OhSectionId.containsKey(loadFlowOverVoltageSectionId.get(i))) {
                        Objects.requireNonNull(OhSectionId.get(loadFlowOverVoltageSectionId.get(i))).setColor(Color.parseColor(overVoltageColors));
                    }

                    if (UnBalSectionId.containsKey(loadFlowOverVoltageSectionId.get(i))) {
                        Objects.requireNonNull(UnBalSectionId.get(loadFlowOverVoltageSectionId.get(i))).setColor(Color.parseColor(overVoltageColors));
                    }

                    if (breakerSectionId.containsKey(loadFlowOverVoltageSectionId.get(i))) {
                        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                        Bitmap bitmap = drawableToBitmap(drawable);
                        Objects.requireNonNull(breakerSectionId.get(loadFlowOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 0, 95)));
                    }

                    if (transformerSectionId.containsKey(loadFlowOverVoltageSectionId.get(i))) {
                        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                        Bitmap bitmap = drawableToBitmap(drawable);
                        Objects.requireNonNull(transformerSectionId.get(loadFlowOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 0, 93)));
                    }

                    if (fuseSectionId.containsKey(loadFlowOverVoltageSectionId.get(i))) {
                        Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                        Objects.requireNonNull(fuseSectionId.get(loadFlowOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 0, 0)));
                    }

                    if (switchSectionId.containsKey(loadFlowOverVoltageSectionId.get(i))) {
                        Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                        int paddingLeft = 0; // Left padding in pixels
                        int paddingTop = 0; // Top padding in pixels
                        int paddingRight = 0; // Right padding in pixels
                        int paddingBottom = 12; // Bottom padding in pixels
                        int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                        int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                        Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                        Canvas canva = new Canvas(paddedBitmap);
                        canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                        Objects.requireNonNull(switchSectionId.get(loadFlowOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(overVoltageColors)), 0, 0)));
                    }

                    if (capacitorSectionId.containsKey(loadFlowOverVoltageSectionId.get(i))) {
                        Objects.requireNonNull(capacitorSectionId.get(loadFlowOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overVoltageColors)), 90, 0)));
                    }

                    if (spotloadSectionId.containsKey(loadFlowOverVoltageSectionId.get(i))) {
                        if (spLineSectionList.contains(loadFlowOverVoltageSectionId.get(i))) {
                            int paddingPx = 0;
                            Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                    BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                            Canvas canvas1 = new Canvas(paddedBitmap);
                            canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                            Objects.requireNonNull(spotloadSectionId.get(loadFlowOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(overVoltageColors)), 0, 0)));
                        } else {
                            Objects.requireNonNull(spotloadSectionId.get(loadFlowOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(overVoltageColors)), 0, 35)));
                        }
                    }

                }
            }

            if (loadFlowUnderVoltageSectionId != null && underVoltageColors != null && !loadFlowUnderVoltageSectionId.isEmpty()) {
                for (int i = 0; i < loadFlowUnderVoltageSectionId.size(); i++) {

                    if (CaSectionId.containsKey(loadFlowUnderVoltageSectionId.get(i))) {
                        Objects.requireNonNull(CaSectionId.get(loadFlowUnderVoltageSectionId.get(i))).setColor(Color.parseColor(underVoltageColors));
                    }

                    if (OhSectionId.containsKey(loadFlowUnderVoltageSectionId.get(i))) {
                        Objects.requireNonNull(OhSectionId.get(loadFlowUnderVoltageSectionId.get(i))).setColor(Color.parseColor(underVoltageColors));
                    }

                    if (UnBalSectionId.containsKey(loadFlowUnderVoltageSectionId.get(i))) {
                        Objects.requireNonNull(UnBalSectionId.get(loadFlowUnderVoltageSectionId.get(i))).setColor(Color.parseColor(underVoltageColors));
                    }

                    if (breakerSectionId.containsKey(loadFlowUnderVoltageSectionId.get(i))) {
                        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                        Bitmap bitmap = drawableToBitmap(drawable);
                        Objects.requireNonNull(breakerSectionId.get(loadFlowUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 0, 95)));
                    }

                    if (transformerSectionId.containsKey(loadFlowUnderVoltageSectionId.get(i))) {
                        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                        Bitmap bitmap = drawableToBitmap(drawable);
                        Objects.requireNonNull(transformerSectionId.get(loadFlowUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 0, 93)));
                    }

                    if (fuseSectionId.containsKey(loadFlowUnderVoltageSectionId.get(i))) {
                        Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                        Objects.requireNonNull(fuseSectionId.get(loadFlowUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 0, 0)));
                    }

                    if (switchSectionId.containsKey(loadFlowUnderVoltageSectionId.get(i))) {
                        Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                        int paddingLeft = 0; // Left padding in pixels
                        int paddingTop = 0; // Top padding in pixels
                        int paddingRight = 0; // Right padding in pixels
                        int paddingBottom = 12; // Bottom padding in pixels
                        int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                        int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                        Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                        Canvas canva = new Canvas(paddedBitmap);
                        canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                        Objects.requireNonNull(switchSectionId.get(loadFlowUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(underVoltageColors)), 0, 0)));
                    }

                    if (capacitorSectionId.containsKey(loadFlowUnderVoltageSectionId.get(i))) {
                        Objects.requireNonNull(capacitorSectionId.get(loadFlowUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(underVoltageColors)), 90, 0)));
                    }

                    if (spotloadSectionId.containsKey(loadFlowUnderVoltageSectionId.get(i))) {
                        if (spLineSectionList.contains(loadFlowUnderVoltageSectionId.get(i))) {
                            int paddingPx = 0;
                            Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                    BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                            Canvas canvas1 = new Canvas(paddedBitmap);
                            canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                            Objects.requireNonNull(spotloadSectionId.get(loadFlowUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(underVoltageColors)), 0, 0)));
                        } else {
                            Objects.requireNonNull(spotloadSectionId.get(loadFlowUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(underVoltageColors)), 0, 35)));
                        }
                    }

                }
            }

            if (loadFlowOverLoadSectionId != null && overloadColors != null && !loadFlowOverLoadSectionId.isEmpty()) {
                for (int i = 0; i < loadFlowOverLoadSectionId.size(); i++) {

                    if (CaSectionId.containsKey(loadFlowOverLoadSectionId.get(i))) {
                        Objects.requireNonNull(CaSectionId.get(loadFlowOverLoadSectionId.get(i))).setColor(Color.parseColor(overloadColors));
                    }

                    if (OhSectionId.containsKey(loadFlowOverLoadSectionId.get(i))) {
                        Objects.requireNonNull(OhSectionId.get(loadFlowOverLoadSectionId.get(i))).setColor(Color.parseColor(overloadColors));
                    }

                    if (UnBalSectionId.containsKey(loadFlowOverLoadSectionId.get(i))) {
                        Objects.requireNonNull(UnBalSectionId.get(loadFlowOverLoadSectionId.get(i))).setColor(Color.parseColor(overloadColors));
                    }

                    if (breakerSectionId.containsKey(loadFlowOverLoadSectionId.get(i))) {
                        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                        Bitmap bitmap = drawableToBitmap(drawable);
                        Objects.requireNonNull(breakerSectionId.get(loadFlowOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 0, 95)));
                    }

                    if (transformerSectionId.containsKey(loadFlowOverLoadSectionId.get(i))) {
                        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                        Bitmap bitmap = drawableToBitmap(drawable);
                        Objects.requireNonNull(transformerSectionId.get(loadFlowOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 0, 93)));
                    }

                    if (fuseSectionId.containsKey(loadFlowOverLoadSectionId.get(i))) {
                        Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                        Objects.requireNonNull(fuseSectionId.get(loadFlowOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 0, 0)));
                    }

                    if (switchSectionId.containsKey(loadFlowOverLoadSectionId.get(i))) {
                        Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                        int paddingLeft = 0; // Left padding in pixels
                        int paddingTop = 0; // Top padding in pixels
                        int paddingRight = 0; // Right padding in pixels
                        int paddingBottom = 12; // Bottom padding in pixels
                        int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                        int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                        Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                        Canvas canva = new Canvas(paddedBitmap);
                        canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                        Objects.requireNonNull(switchSectionId.get(loadFlowOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(overloadColors)), 0, 0)));
                    }

                    if (capacitorSectionId.containsKey(loadFlowOverLoadSectionId.get(i))) {
                        Objects.requireNonNull(capacitorSectionId.get(loadFlowOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overloadColors)), 90, 0)));
                    }

                    if (spotloadSectionId.containsKey(loadFlowOverLoadSectionId.get(i))) {
                        if (spLineSectionList.contains(loadFlowOverLoadSectionId.get(i))) {
                            int paddingPx = 0;
                            Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                    BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                            Canvas canvas1 = new Canvas(paddedBitmap);
                            canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                            Objects.requireNonNull(spotloadSectionId.get(loadFlowOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(overloadColors)), 0, 0)));
                        } else {
                            Objects.requireNonNull(spotloadSectionId.get(loadFlowOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(overloadColors)), 0, 35)));
                        }
                    }

                }
            }

        } else if (Config.isShortCircuit) {
            isTracing = false;
            if (CaPolylineList != null && !CaPolylineList.isEmpty()) {
                for (int j = 0; j < CaPolylineList.size(); j++) {
                    CaPolylineList.get(j).getPaint().setColor(Color.BLACK);
                }
            }

            if (ohPolylineList != null && !ohPolylineList.isEmpty()) {
                for (int j = 0; j < ohPolylineList.size(); j++) {
                    ohPolylineList.get(j).getPaint().setColor(Color.BLACK);
                }
            }

            if (unBalPolylineList != null && !unBalPolylineList.isEmpty()) {
                for (int j = 0; j < unBalPolylineList.size(); j++) {
                    unBalPolylineList.get(j).getPaint().setColor(Color.BLACK);
                }
            }

            if (secNodeList != null && !secNodeList.isEmpty()) {
                for (int j = 0; j < secNodeList.size(); j++) {
                    secNodeList.get(j).getPaint().setColor(Color.BLACK);
                }
            }

            if (breakerList != null && !breakerList.isEmpty()) {
                for (int i = 0; i < breakerList.size(); i++) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    breakerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 95)));
                }
            }

            if (transformerList != null && !transformerList.isEmpty()) {
                for (int i = 0; i < transformerList.size(); i++) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    transformerList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 93)));
                }
            }

            if (fuseList != null && !fuseList.isEmpty()) {
                for (int i = 0; i < fuseList.size(); i++) {
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                    fuseList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 0)));
                }
            }

            if (switchedList != null && !switchedList.isEmpty()) {
                for (int i = 0; i < switchedList.size(); i++) {
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                    int paddingLeft = 0; // Left padding in pixels
                    int paddingTop = 0; // Top padding in pixels
                    int paddingRight = 0; // Right padding in pixels
                    int paddingBottom = 12; // Bottom padding in pixels
                    int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                    int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                    Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                    Canvas canva = new Canvas(paddedBitmap);
                    canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                    switchedList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.BLACK), 0, 0)));
                }
            }

            if (capacitorList != null && !capacitorList.isEmpty()) {
                for (int i = 0; i < capacitorList.size(); i++) {
                    capacitorList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.BLACK), 90, 0)));
                }
            }

            if (spotLoadList != null && !spotLoadList.isEmpty()) {
                for (int i = 0; i < spotLoadList.size(); i++) {
                    int paddingPx = 0;
                    Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                            BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                    Canvas canvas1 = new Canvas(paddedBitmap);
                    canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                    spotLoadList.get(i).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.BLACK), 0, 0)));
                }
            }

            if (shortCircuitRatingSectionId != null && ratingColors != null && !shortCircuitRatingSectionId.isEmpty()) {
                for (int i = 0; i < shortCircuitRatingSectionId.size(); i++) {

                    if (CaSectionId.containsKey(shortCircuitRatingSectionId.get(i))) {
                        Objects.requireNonNull(CaSectionId.get(shortCircuitRatingSectionId.get(i))).setColor(Color.parseColor(ratingColors));
                    }

                    if (OhSectionId.containsKey(shortCircuitRatingSectionId.get(i))) {
                        Objects.requireNonNull(OhSectionId.get(shortCircuitRatingSectionId.get(i))).setColor(Color.parseColor(ratingColors));
                    }

                    if (UnBalSectionId.containsKey(shortCircuitRatingSectionId.get(i))) {
                        Objects.requireNonNull(UnBalSectionId.get(shortCircuitRatingSectionId.get(i))).setColor(Color.parseColor(ratingColors));
                    }

                    if (breakerSectionId.containsKey(shortCircuitRatingSectionId.get(i))) {
                        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                        Bitmap bitmap = drawableToBitmap(drawable);
                        Objects.requireNonNull(breakerSectionId.get(shortCircuitRatingSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(ratingColors)), 0, 95)));
                    }

                    if (transformerSectionId.containsKey(shortCircuitRatingSectionId.get(i))) {
                        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                        Bitmap bitmap = drawableToBitmap(drawable);
                        Objects.requireNonNull(transformerSectionId.get(shortCircuitRatingSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(ratingColors)), 0, 93)));
                    }

                    if (fuseSectionId.containsKey(shortCircuitRatingSectionId.get(i))) {
                        Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                        Objects.requireNonNull(fuseSectionId.get(shortCircuitRatingSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(ratingColors)), 0, 0)));
                    }

                    if (switchSectionId.containsKey(shortCircuitRatingSectionId.get(i))) {
                        Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                        int paddingLeft = 0; // Left padding in pixels
                        int paddingTop = 0; // Top padding in pixels
                        int paddingRight = 0; // Right padding in pixels
                        int paddingBottom = 12; // Bottom padding in pixels
                        int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                        int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                        Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                        Canvas canva = new Canvas(paddedBitmap);
                        canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                        Objects.requireNonNull(switchSectionId.get(shortCircuitRatingSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(ratingColors)), 0, 0)));
                    }

                    if (capacitorSectionId.containsKey(shortCircuitRatingSectionId.get(i))) {
                        Objects.requireNonNull(capacitorSectionId.get(shortCircuitRatingSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(ratingColors)), 90, 0)));
                    }

                    if (spotloadSectionId.containsKey(shortCircuitRatingSectionId.get(i))) {
                        if (spLineSectionList.contains(shortCircuitRatingSectionId.get(i))) {
                            int paddingPx = 0;
                            Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                    BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                            Canvas canvas1 = new Canvas(paddedBitmap);
                            canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                            Objects.requireNonNull(spotloadSectionId.get(shortCircuitRatingSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(ratingColors)), 0, 0)));
                        } else {
                            Objects.requireNonNull(spotloadSectionId.get(shortCircuitRatingSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(ratingColors)), 0, 35)));
                        }
                    }

                }
            }

            if (shortCircuitOverVoltageSectionId != null && overVoltageColors != null && !shortCircuitOverVoltageSectionId.isEmpty()) {
                for (int i = 0; i < shortCircuitOverVoltageSectionId.size(); i++) {

                    if (CaSectionId.containsKey(shortCircuitOverVoltageSectionId.get(i))) {
                        Objects.requireNonNull(CaSectionId.get(shortCircuitOverVoltageSectionId.get(i))).setColor(Color.parseColor(overVoltageColors));
                    }

                    if (OhSectionId.containsKey(shortCircuitOverVoltageSectionId.get(i))) {
                        Objects.requireNonNull(OhSectionId.get(shortCircuitOverVoltageSectionId.get(i))).setColor(Color.parseColor(overVoltageColors));
                    }

                    if (UnBalSectionId.containsKey(shortCircuitOverVoltageSectionId.get(i))) {
                        Objects.requireNonNull(UnBalSectionId.get(shortCircuitOverVoltageSectionId.get(i))).setColor(Color.parseColor(overVoltageColors));
                    }

                    if (breakerSectionId.containsKey(shortCircuitOverVoltageSectionId.get(i))) {
                        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                        Bitmap bitmap = drawableToBitmap(drawable);
                        Objects.requireNonNull(breakerSectionId.get(shortCircuitOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 0, 95)));
                    }

                    if (transformerSectionId.containsKey(shortCircuitOverVoltageSectionId.get(i))) {
                        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                        Bitmap bitmap = drawableToBitmap(drawable);
                        Objects.requireNonNull(transformerSectionId.get(shortCircuitOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 0, 93)));
                    }

                    if (fuseSectionId.containsKey(shortCircuitOverVoltageSectionId.get(i))) {
                        Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                        Objects.requireNonNull(fuseSectionId.get(shortCircuitOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 0, 0)));
                    }

                    if (switchSectionId.containsKey(shortCircuitOverVoltageSectionId.get(i))) {
                        Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                        int paddingLeft = 0; // Left padding in pixels
                        int paddingTop = 0; // Top padding in pixels
                        int paddingRight = 0; // Right padding in pixels
                        int paddingBottom = 12; // Bottom padding in pixels
                        int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                        int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                        Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                        Canvas canva = new Canvas(paddedBitmap);
                        canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                        Objects.requireNonNull(switchSectionId.get(shortCircuitOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(overVoltageColors)), 0, 0)));
                    }

                    if (capacitorSectionId.containsKey(shortCircuitOverVoltageSectionId.get(i))) {
                        Objects.requireNonNull(capacitorSectionId.get(shortCircuitOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overVoltageColors)), 90, 0)));
                    }

                    if (spotloadSectionId.containsKey(shortCircuitOverVoltageSectionId.get(i))) {
                        if (spLineSectionList.contains(shortCircuitOverVoltageSectionId.get(i))) {
                            int paddingPx = 0;
                            Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                    BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                            Canvas canvas1 = new Canvas(paddedBitmap);
                            canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                            Objects.requireNonNull(spotloadSectionId.get(shortCircuitOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(overVoltageColors)), 0, 0)));
                        } else {
                            Objects.requireNonNull(spotloadSectionId.get(shortCircuitOverVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(overVoltageColors)), 0, 35)));
                        }
                    }

                }
            }

            if (shortCircuitUnderVoltageSectionId != null && underVoltageColors != null && !shortCircuitUnderVoltageSectionId.isEmpty()) {
                for (int i = 0; i < shortCircuitUnderVoltageSectionId.size(); i++) {

                    if (CaSectionId.containsKey(shortCircuitUnderVoltageSectionId.get(i))) {
                        Objects.requireNonNull(CaSectionId.get(shortCircuitUnderVoltageSectionId.get(i))).setColor(Color.parseColor(underVoltageColors));
                    }

                    if (OhSectionId.containsKey(shortCircuitUnderVoltageSectionId.get(i))) {
                        Objects.requireNonNull(OhSectionId.get(shortCircuitUnderVoltageSectionId.get(i))).setColor(Color.parseColor(underVoltageColors));
                    }

                    if (UnBalSectionId.containsKey(shortCircuitUnderVoltageSectionId.get(i))) {
                        Objects.requireNonNull(UnBalSectionId.get(shortCircuitUnderVoltageSectionId.get(i))).setColor(Color.parseColor(underVoltageColors));
                    }

                    if (breakerSectionId.containsKey(shortCircuitUnderVoltageSectionId.get(i))) {
                        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                        Bitmap bitmap = drawableToBitmap(drawable);
                        Objects.requireNonNull(breakerSectionId.get(shortCircuitUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 0, 95)));
                    }

                    if (transformerSectionId.containsKey(shortCircuitUnderVoltageSectionId.get(i))) {
                        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                        Bitmap bitmap = drawableToBitmap(drawable);
                        Objects.requireNonNull(transformerSectionId.get(shortCircuitUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 0, 93)));
                    }

                    if (fuseSectionId.containsKey(shortCircuitUnderVoltageSectionId.get(i))) {
                        Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                        Objects.requireNonNull(fuseSectionId.get(shortCircuitUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 0, 0)));
                    }

                    if (switchSectionId.containsKey(shortCircuitUnderVoltageSectionId.get(i))) {
                        Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                        int paddingLeft = 0; // Left padding in pixels
                        int paddingTop = 0; // Top padding in pixels
                        int paddingRight = 0; // Right padding in pixels
                        int paddingBottom = 12; // Bottom padding in pixels
                        int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                        int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                        Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                        Canvas canva = new Canvas(paddedBitmap);
                        canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                        Objects.requireNonNull(switchSectionId.get(shortCircuitUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(underVoltageColors)), 0, 0)));
                    }

                    if (capacitorSectionId.containsKey(shortCircuitUnderVoltageSectionId.get(i))) {
                        Objects.requireNonNull(capacitorSectionId.get(shortCircuitUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(underVoltageColors)), 90, 0)));
                    }

                    if (spotloadSectionId.containsKey(shortCircuitUnderVoltageSectionId.get(i))) {
                        if (spLineSectionList.contains(shortCircuitUnderVoltageSectionId.get(i))) {
                            int paddingPx = 0;
                            Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                    BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                            Canvas canvas1 = new Canvas(paddedBitmap);
                            canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                            Objects.requireNonNull(spotloadSectionId.get(shortCircuitUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(underVoltageColors)), 0, 0)));
                        } else {
                            Objects.requireNonNull(spotloadSectionId.get(shortCircuitUnderVoltageSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(underVoltageColors)), 0, 35)));
                        }
                    }

                }
            }

            if (shortCircuitOverLoadSectionId != null && overloadColors != null && !shortCircuitOverLoadSectionId.isEmpty()) {
                for (int i = 0; i < shortCircuitOverLoadSectionId.size(); i++) {

                    if (CaSectionId.containsKey(shortCircuitOverLoadSectionId.get(i))) {
                        Objects.requireNonNull(CaSectionId.get(shortCircuitOverLoadSectionId.get(i))).setColor(Color.parseColor(overloadColors));
                    }

                    if (OhSectionId.containsKey(shortCircuitOverLoadSectionId.get(i))) {
                        Objects.requireNonNull(OhSectionId.get(shortCircuitOverLoadSectionId.get(i))).setColor(Color.parseColor(overloadColors));
                    }

                    if (UnBalSectionId.containsKey(shortCircuitOverLoadSectionId.get(i))) {
                        Objects.requireNonNull(UnBalSectionId.get(shortCircuitOverLoadSectionId.get(i))).setColor(Color.parseColor(overloadColors));
                    }

                    if (breakerSectionId.containsKey(shortCircuitOverLoadSectionId.get(i))) {
                        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                        Bitmap bitmap = drawableToBitmap(drawable);
                        Objects.requireNonNull(breakerSectionId.get(shortCircuitOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 0, 95)));
                    }

                    if (transformerSectionId.containsKey(shortCircuitOverLoadSectionId.get(i))) {
                        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                        Bitmap bitmap = drawableToBitmap(drawable);
                        Objects.requireNonNull(transformerSectionId.get(shortCircuitOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 0, 93)));
                    }

                    if (fuseSectionId.containsKey(shortCircuitOverLoadSectionId.get(i))) {
                        Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                        Objects.requireNonNull(fuseSectionId.get(shortCircuitOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 0, 0)));
                    }

                    if (switchSectionId.containsKey(shortCircuitOverLoadSectionId.get(i))) {
                        Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                        int paddingLeft = 0; // Left padding in pixels
                        int paddingTop = 0; // Top padding in pixels
                        int paddingRight = 0; // Right padding in pixels
                        int paddingBottom = 12; // Bottom padding in pixels
                        int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                        int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                        Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                        Canvas canva = new Canvas(paddedBitmap);
                        canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                        Objects.requireNonNull(switchSectionId.get(shortCircuitOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(overloadColors)), 0, 0)));
                    }

                    if (capacitorSectionId.containsKey(shortCircuitOverLoadSectionId.get(i))) {
                        Objects.requireNonNull(capacitorSectionId.get(shortCircuitOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overloadColors)), 90, 0)));
                    }

                    if (spotloadSectionId.containsKey(shortCircuitOverLoadSectionId.get(i))) {
                        if (spLineSectionList.contains(shortCircuitOverLoadSectionId.get(i))) {
                            int paddingPx = 0;
                            Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                                    BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                            Canvas canvas1 = new Canvas(paddedBitmap);
                            canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                            Objects.requireNonNull(spotloadSectionId.get(shortCircuitOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(overloadColors)), 0, 0)));
                        } else {
                            Objects.requireNonNull(spotloadSectionId.get(shortCircuitOverLoadSectionId.get(i))).setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Spotload(), Color.parseColor(overloadColors)), 0, 35)));
                        }
                    }
                }
            }
        } else {
            isTracing = false;
            if (CaPolylineList != null && !CaPolylineList.isEmpty()) {
                for (int j = 0; j < CaPolylineList.size(); j++) {
                    CaPolylineList.get(j).getPaint().setColor(Color.RED);
                }
            }

            if (ohPolylineList != null && !ohPolylineList.isEmpty()) {
                for (int j = 0; j < ohPolylineList.size(); j++) {
                    ohPolylineList.get(j).getPaint().setColor(Color.BLUE);
                }
            }

            if (unBalPolylineList != null && !unBalPolylineList.isEmpty()) {
                for (int j = 0; j < unBalPolylineList.size(); j++) {
                    unBalPolylineList.get(j).getPaint().setColor(Color.BLACK);
                }
            }

            if (secNodeList != null && !secNodeList.isEmpty()) {
                for (int j = 0; j < secNodeList.size(); j++) {
                    secNodeList.get(j).getPaint().setColor(Color.BLACK);
                }
            }
        }
    }

    private void highlightDevice(Marker marker, String type, String ID) {
        try {
            if (previousSelectedDevice != null && deviceType != null && sectionID != null) {
                previousSelectedDevice.closeInfoWindow();
                if (deviceType.contains("8")) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(sectionID) != null) {
                            secNodeSectionId.get(sectionID).getPaint().setColor(Color.BLACK);
                            sectionID = null;
                        }
                    }
                    previousSelectedDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 90)));
                    previousSelectedDevice = null;
                    deviceType = null;
                } else if (deviceType.contains("10")) {
                    @SuppressLint("UseCompatLoadingForDrawables")
                    Drawable drawable = getResources().getDrawable(R.drawable.reclosed);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(sectionID) != null) {
                            secNodeSectionId.get(sectionID).getPaint().setColor(Color.BLACK);
                            sectionID = null;
                        }
                    }
                    previousSelectedDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 90)));
                    previousSelectedDevice = null;
                    deviceType = null;
                } else if (deviceType.contains("5")) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(sectionID) != null) {
                            secNodeSectionId.get(sectionID).getPaint().setColor(Color.BLACK);
                            sectionID = null;
                        }
                    }
                    previousSelectedDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 90, 0)));
                    previousSelectedDevice = null;
                    deviceType = null;
                } else if (deviceType.contains("14")) {
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(sectionID) != null) {
                            secNodeSectionId.get(sectionID).getPaint().setColor(Color.BLACK);
                            sectionID = null;
                        }
                    }
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                    previousSelectedDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 90)));
                    previousSelectedDevice = null;
                    deviceType = null;
                } else if (deviceType.contains("13")) {
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                    int paddingLeft = 0; // Left padding in pixels
                    int paddingTop = 0; // Top padding in pixels
                    int paddingRight = 0; // Right padding in pixels
                    int paddingBottom = 12; // Bottom padding in pixels
                    int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                    int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                    Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                    Canvas canva = new Canvas(paddedBitmap);
                    canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(sectionID) != null) {
                            secNodeSectionId.get(sectionID).getPaint().setColor(Color.BLACK);
                            sectionID = null;
                        }
                    }
                    previousSelectedDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.BLACK), 90, 0)));
                    previousSelectedDevice = null;
                    deviceType = null;
                } else if (deviceType.contains("61")) {
                    if (secNodeSectionId.containsKey(sectionID)) {
                        previousSelectedDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.BLACK), 0, 0)));
                        previousSelectedDevice = null;
                        deviceType = null;
                    } else {
                        previousSelectedDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.BLACK), 90, 0)));
                        previousSelectedDevice = null;
                        deviceType = null;
                    }
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(sectionID) != null) {
                            secNodeSectionId.get(sectionID).getPaint().setColor(Color.BLACK);
                            sectionID = null;
                        }
                    }
                } else if (deviceType.contains("20")) {
                    int paddingPx = 0;
                    Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                            BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                    Canvas canvas1 = new Canvas(paddedBitmap);
                    canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(sectionID) != null) {
                            secNodeSectionId.get(sectionID).getPaint().setColor(Color.BLACK);
                            sectionID = null;
                        }
                    }

                    if (spLineSectionList.contains(sectionID)) {
                        previousSelectedDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.BLACK), 0, 0)));
                        previousSelectedDevice = null;
                        deviceType = null;
                    } else {
                        previousSelectedDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.BLACK), 0, 70)));
                        previousSelectedDevice = null;
                        deviceType = null;
                    }

                } else if (deviceType.contains("99")) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.dot);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    previousSelectedDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), changeBgTransparentBitmapColor(bitmap, Color.BLACK)));
                    previousSelectedDevice = null;
                    deviceType = null;
                }
            }

            if (previousSelectedSection != null && sectionType != null) {
                if (sectionType.contains("1")) {
                    previousSelectedSection.setColor(Color.RED);
                    previousSelectedSection = null;
                    sectionType = null;
                } else if (sectionType.contains("2")) {
                    previousSelectedSection.setColor(Color.BLUE);
                    previousSelectedSection = null;
                    sectionType = null;
                } else if (sectionType.contains("23")) {
                    previousSelectedSection.setColor(Color.BLACK);
                    previousSelectedSection = null;
                    sectionType = null;
                }
            }

            if (type.contains("8")) {
                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                Bitmap bitmap = drawableToBitmap(drawable);
                marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.GREEN), 0, 90)));
                if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                    if (secNodeSectionId.get(ID) != null) {
                        secNodeSectionId.get(ID).getPaint().setColor(Color.GREEN);
                    }
                }
            } else if (type.contains("10")) {
                @SuppressLint("UseCompatLoadingForDrawables")
                Drawable drawable = getResources().getDrawable(R.drawable.reclosed);
                Bitmap bitmap = drawableToBitmap(drawable);
                marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.GREEN), 0, 90)));
                if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                    if (secNodeSectionId.get(ID) != null) {
                        secNodeSectionId.get(ID).getPaint().setColor(Color.GREEN);
                    }
                }
            } else if (type.contains("5")) {
                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                Bitmap bitmap = drawableToBitmap(drawable);
                marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.GREEN), 90, 0)));
                if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                    if (secNodeSectionId.get(ID) != null) {
                        secNodeSectionId.get(ID).getPaint().setColor(Color.GREEN);
                    }
                }
            } else if (type.contains("14")) {
                Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.GREEN), 0, 90)));
                if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                    if (secNodeSectionId.get(ID) != null) {
                        secNodeSectionId.get(ID).getPaint().setColor(Color.GREEN);
                    }
                }
            } else if (type.contains("13")) {
                Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                int paddingLeft = 0; // Left padding in pixels
                int paddingTop = 0; // Top padding in pixels
                int paddingRight = 0; // Right padding in pixels
                int paddingBottom = 12; // Bottom padding in pixels
                int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                Canvas canva = new Canvas(paddedBitmap);
                canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.GREEN), 90, 0)));
                if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                    if (secNodeSectionId.get(ID) != null) {
                        secNodeSectionId.get(ID).getPaint().setColor(Color.GREEN);
                    }
                }
            } else if (type.contains("61")) {
                if (secNodeSectionId.containsKey(ID)) {
                    marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.GREEN), 0, 0)));
                } else {
                    marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.GREEN), 90, 0)));
                }
                if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                    if (secNodeSectionId.get(ID) != null) {
                        secNodeSectionId.get(ID).getPaint().setColor(Color.GREEN);
                    }
                }
            } else if (type.contains("20")) {
                int paddingPx = 0;
                Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                        BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                Canvas canvas1 = new Canvas(paddedBitmap);
                canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);

                if (spLineSectionList.contains(ID)) {
                    marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.GREEN), 0, 0)));
                } else {
                    marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.GREEN), 0, 70)));
                }

                if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                    if (secNodeSectionId.get(ID) != null) {
                        secNodeSectionId.get(ID).getPaint().setColor(Color.GREEN);
                    }
                }
            } else if (type.contains("99")) {
                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable1 = getResources().getDrawable(R.drawable.node);
                Bitmap bitmap1 = drawableToBitmap(drawable1);
                marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), changeBgTransparentBitmapColor(bitmap1, Color.GREEN)));
            }

            previousSelectedDevice = marker;
            sectionID = ID;
            deviceType = type;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void highlightSection(Polyline polyline, String type) {
        try {
            if (previousSelectedSection != null && sectionType != null) {
                if (sectionType.contains("1")) {
                    previousSelectedSection.setColor(Color.RED);
                    previousSelectedSection = null;
                    sectionType = null;
                } else if (sectionType.contains("2")) {
                    previousSelectedSection.setColor(Color.BLUE);
                    previousSelectedSection = null;
                    sectionType = null;
                } else if (sectionType.contains("23")) {
                    previousSelectedSection.setColor(Color.BLACK);
                    previousSelectedSection = null;
                    sectionType = null;
                }
            }

            if (previousSelectedDevice != null && deviceType != null) {
                previousSelectedDevice.closeInfoWindow();
                if (deviceType.contains("8")) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    if (sectionID != null && !sectionID.isEmpty()) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(sectionID) != null) {
                                secNodeSectionId.get(sectionID).getPaint().setColor(Color.BLACK);
                                sectionID = null;
                            }
                        }
                    }
                    previousSelectedDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 90)));
                    previousSelectedDevice = null;
                    deviceType = null;
                } else if (deviceType.contains("10")) {
                    @SuppressLint("UseCompatLoadingForDrawables")
                    Drawable drawable = getResources().getDrawable(R.drawable.reclosed);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    if (sectionID != null && !sectionID.isEmpty()) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(sectionID) != null) {
                                secNodeSectionId.get(sectionID).getPaint().setColor(Color.BLACK);
                                sectionID = null;
                            }
                        }
                    }
                    previousSelectedDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 90)));
                    previousSelectedDevice = null;
                    deviceType = null;
                } else if (deviceType.contains("5")) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    if (sectionID != null && !sectionID.isEmpty()) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(sectionID) != null) {
                                secNodeSectionId.get(sectionID).getPaint().setColor(Color.BLACK);
                                sectionID = null;
                            }
                        }
                    }
                    previousSelectedDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 90, 0)));
                    previousSelectedDevice = null;
                    deviceType = null;
                } else if (deviceType.contains("14")) {
                    if (sectionID != null && !sectionID.isEmpty()) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(sectionID) != null) {
                                secNodeSectionId.get(sectionID).getPaint().setColor(Color.BLACK);
                                sectionID = null;
                            }
                        }
                    }
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                    previousSelectedDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 90)));
                    previousSelectedDevice = null;
                    deviceType = null;
                } else if (deviceType.contains("13")) {
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                    int paddingLeft = 0; // Left padding in pixels
                    int paddingTop = 0; // Top padding in pixels
                    int paddingRight = 0; // Right padding in pixels
                    int paddingBottom = 12; // Bottom padding in pixels
                    int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                    int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                    Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                    Canvas canva = new Canvas(paddedBitmap);
                    canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                    if (sectionID != null && !sectionID.isEmpty()) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(sectionID) != null) {
                                secNodeSectionId.get(sectionID).getPaint().setColor(Color.BLACK);
                                sectionID = null;
                            }
                        }
                    }
                    previousSelectedDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.BLACK), 90, 0)));
                    previousSelectedDevice = null;
                    deviceType = null;
                } else if (deviceType.contains("61")) {
                    if (sectionID != null && !sectionID.isEmpty()) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(sectionID) != null) {
                                secNodeSectionId.get(sectionID).getPaint().setColor(Color.BLACK);
                                sectionID = null;
                            }
                        }
                    }
                    previousSelectedDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.BLACK), 90, 0)));
                    previousSelectedDevice = null;
                    deviceType = null;
                } else if (deviceType.contains("20")) {
                    int paddingPx = 0;
                    Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                            BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                    Canvas canvas1 = new Canvas(paddedBitmap);
                    canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                    if (sectionID != null && !sectionID.isEmpty()) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(sectionID) != null) {
                                secNodeSectionId.get(sectionID).getPaint().setColor(Color.BLACK);
                                sectionID = null;
                            }
                        }
                    }

                    if (spLineSectionList.contains(sectionID)) {
                        previousSelectedDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.BLACK), 0, 0)));
                        previousSelectedDevice = null;
                        deviceType = null;
                    } else {
                        previousSelectedDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.BLACK), 0, 70)));
                        previousSelectedDevice = null;
                        deviceType = null;
                    }
                } else if (deviceType.contains("99")) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.dot);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    previousSelectedDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), changeBgTransparentBitmapColor(bitmap, Color.BLACK)));
                    previousSelectedDevice = null;
                    deviceType = null;
                }
            }

            polyline.setColor(Color.GREEN);
            previousSelectedSection = polyline;
            sectionType = type;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void highlightLoadFlowDevice(Marker marker, String type, String ID) {
        try {
            if (previousLoadFlowDevice != null && loadFlowDeviceType != null && loadFlowDeviceId != null) {
                previousLoadFlowDevice.closeInfoWindow();
                if (loadFlowDeviceType.contains("8")) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                            secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }
                    previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 90)));

                    if (loadFlowOverVoltageDeviceID.containsKey(loadFlowDeviceId) && overVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(sectionID) != null) {
                                secNodeSectionId.get(sectionID).getPaint().setColor(Color.parseColor(overVoltageColors));
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 90, 0)));
                    }

                    if (loadFlowUnderVoltageDeviceID.containsKey(loadFlowDeviceId) && underVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(sectionID) != null) {
                                secNodeSectionId.get(sectionID).getPaint().setColor(Color.parseColor(underVoltageColors));
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 90, 0)));
                    }

                    if (loadFlowOverLoadDeviceID.containsKey(loadFlowDeviceId) && overloadColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(sectionID) != null) {
                                secNodeSectionId.get(sectionID).getPaint().setColor(Color.parseColor(overloadColors));
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 90, 0)));
                    }

                    previousLoadFlowDevice = null;
                    loadFlowDeviceType = null;
                    loadFlowDeviceId = null;

                } else if (loadFlowDeviceType.contains("5")) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                            secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }
                    previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 90, 0)));

                    if (loadFlowOverVoltageDeviceID.containsKey(loadFlowDeviceId) && overVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(overVoltageColors));
                                loadFlowDeviceId = null;
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 0, 90)));
                    }

                    if (loadFlowUnderVoltageDeviceID.containsKey(loadFlowDeviceId) && underVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(underVoltageColors));
                                loadFlowDeviceId = null;
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 0, 90)));
                    }

                    if (loadFlowOverLoadDeviceID.containsKey(loadFlowDeviceId) && overloadColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(overloadColors));
                                loadFlowDeviceId = null;
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 0, 90)));
                    }

                    previousLoadFlowDevice = null;
                    loadFlowDeviceType = null;
                    loadFlowDeviceId = null;

                } else if (loadFlowDeviceType.contains("14")) {
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                    previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 90)));
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                            secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (loadFlowOverVoltageDeviceID.containsKey(loadFlowDeviceId) && overVoltageColors != null) {
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 0, 90)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(overVoltageColors));
                            }
                        }
                    }

                    if (loadFlowUnderVoltageDeviceID.containsKey(loadFlowDeviceId) && underVoltageColors != null) {
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 0, 90)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(underVoltageColors));
                            }
                        }
                    }

                    if (loadFlowOverLoadDeviceID.containsKey(loadFlowDeviceId) && overloadColors != null) {
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 0, 90)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(overloadColors));
                            }
                        }
                    }

                    previousLoadFlowDevice = null;
                    loadFlowDeviceType = null;
                    loadFlowDeviceId = null;
                } else if (loadFlowDeviceType.contains("13")) {
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                    int paddingLeft = 0; // Left padding in pixels
                    int paddingTop = 0; // Top padding in pixels
                    int paddingRight = 0; // Right padding in pixels
                    int paddingBottom = 12; // Bottom padding in pixels
                    int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                    int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                    Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                    Canvas canva = new Canvas(paddedBitmap);
                    canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                            secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }
                    previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.BLACK), 90, 0)));

                    if (loadFlowOverVoltageDeviceID.containsKey(loadFlowDeviceId) && overVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(overVoltageColors));
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(overVoltageColors)), 90, 0)));
                    }

                    if (loadFlowUnderVoltageDeviceID.containsKey(loadFlowDeviceId) && underVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(underVoltageColors));
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(underVoltageColors)), 90, 0)));
                    }

                    if (loadFlowOverLoadDeviceID.containsKey(loadFlowDeviceId) && overloadColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(overloadColors));
                                loadFlowDeviceId = null;
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(overloadColors)), 90, 0)));
                    }

                    previousLoadFlowDevice = null;
                    loadFlowDeviceType = null;
                    loadFlowDeviceId = null;
                } else if (loadFlowDeviceType.contains("61")) {
                    if (secNodeSectionId.containsKey(loadFlowDeviceId)) {
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.BLACK), 0, 0)));
                    } else {
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.BLACK), 90, 0)));
                    }

                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                            secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (loadFlowOverVoltageDeviceID.containsKey(loadFlowDeviceId) && overVoltageColors != null) {
                        if (secNodeSectionId.containsKey(loadFlowDeviceId)) {
                            previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overVoltageColors)), 0, 0)));
                        } else {
                            previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overVoltageColors)), 90, 0)));
                        }

                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(overVoltageColors));
                            }
                        }
                    }

                    if (loadFlowUnderVoltageDeviceID.containsKey(loadFlowDeviceId) && underVoltageColors != null) {
                        if (secNodeSectionId.containsKey(loadFlowDeviceId)) {
                            previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(underVoltageColors)), 0, 0)));
                        } else {
                            previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(underVoltageColors)), 90, 0)));
                        }

                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(underVoltageColors));
                            }
                        }
                    }

                    if (loadFlowOverLoadDeviceID.containsKey(loadFlowDeviceId) && overloadColors != null) {
                        if (secNodeSectionId.containsKey(loadFlowDeviceId)) {
                            previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overloadColors)), 0, 0)));
                        } else {
                            previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overloadColors)), 90, 0)));
                        }

                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(overloadColors));
                            }
                        }
                    }

                    previousLoadFlowDevice = null;
                    loadFlowDeviceType = null;
                    loadFlowDeviceId = null;

                } else if (loadFlowDeviceType.contains("20")) {
                    int paddingPx = 0;
                    Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                            BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                    Canvas canvas1 = new Canvas(paddedBitmap);
                    canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                            secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }
                    previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.BLACK), 0, 70)));

                    if (loadFlowOverVoltageDeviceID.containsKey(loadFlowDeviceId) && overVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(overVoltageColors));
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(overVoltageColors)), 0, 70)));
                    }

                    if (loadFlowUnderVoltageDeviceID.containsKey(loadFlowDeviceId) && underVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(underVoltageColors));
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(underVoltageColors)), 0, 70)));
                    }

                    if (loadFlowOverLoadDeviceID.containsKey(loadFlowDeviceId) && overloadColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(overloadColors));
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(overloadColors)), 0, 70)));
                    }

                    previousLoadFlowDevice = null;
                    loadFlowDeviceType = null;
                    loadFlowDeviceId = null;

                } else if (loadFlowDeviceType.contains("99")) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.dot);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), changeBgTransparentBitmapColor(bitmap, Color.BLACK)));
                    previousLoadFlowDevice = null;
                    loadFlowDeviceType = null;
                    loadFlowDeviceId = null;
                }
            }

            if (loadFlowPreviousSelectedSection != null && loadFlowSectionId != null) {
                loadFlowPreviousSelectedSection.closeInfoWindow();
                loadFlowPreviousSelectedSection.setColor(Color.BLACK);

                if (loadFlowOverVoltageSectionID.containsKey(loadFlowSectionId) && overVoltageColors != null) {
                    loadFlowPreviousSelectedSection.setColor(Color.parseColor(overVoltageColors));
                }

                if (loadFlowUnderVoltageSectionID.containsKey(loadFlowSectionId) && underVoltageColors != null) {
                    loadFlowPreviousSelectedSection.setColor(Color.parseColor(underVoltageColors));
                }

                if (loadFlowOverLoadSectionID.containsKey(loadFlowSectionId) && overloadColors != null) {
                    loadFlowPreviousSelectedSection.setColor(Color.parseColor(overloadColors));
                }

                loadFlowPreviousSelectedSection = null;
                loadFlowSectionId = null;
            }

            if (type.contains("8")) {
                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                Bitmap bitmap = drawableToBitmap(drawable);
                if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                    if (secNodeSectionId.get(ID) != null) {
                        secNodeSectionId.get(ID).getPaint().setColor(Color.parseColor("#0ABDE3"));
                    }
                }
                marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#0ABDE3")), 90, 0)));
            } else if (type.contains("5")) {
                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                Bitmap bitmap = drawableToBitmap(drawable);
                marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#0ABDE3")), 0, 90)));
                if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                    if (secNodeSectionId.get(ID) != null) {
                        secNodeSectionId.get(ID).getPaint().setColor(Color.parseColor("#0ABDE3"));
                    }
                }
            } else if (type.contains("14")) {
                Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#0ABDE3")), 0, 90)));
                if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                    if (secNodeSectionId.get(ID) != null) {
                        secNodeSectionId.get(ID).getPaint().setColor(Color.parseColor("#0ABDE3"));
                    }
                }
            } else if (type.contains("13")) {
                Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                int paddingLeft = 0; // Left padding in pixels
                int paddingTop = 0; // Top padding in pixels
                int paddingRight = 0; // Right padding in pixels
                int paddingBottom = 12; // Bottom padding in pixels
                int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                Canvas canva = new Canvas(paddedBitmap);
                canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor("#0ABDE3")), 90, 0)));
                if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                    if (secNodeSectionId.get(ID) != null) {
                        secNodeSectionId.get(ID).getPaint().setColor(Color.parseColor("#0ABDE3"));
                    }
                }
            } else if (type.contains("61")) {
                if (secNodeSectionId.containsKey(ID)) {
                    marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor("#0ABDE3")), 0, 0)));
                } else {
                    marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor("#0ABDE3")), 90, 0)));
                }

                if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                    if (secNodeSectionId.get(ID) != null) {
                        secNodeSectionId.get(ID).getPaint().setColor(Color.parseColor("#0ABDE3"));
                    }
                }
            } else if (type.contains("20")) {
                int paddingPx = 0;
                Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                        BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                Canvas canvas1 = new Canvas(paddedBitmap);
                canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor("#0ABDE3")), 0, 70)));
                if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                    if (secNodeSectionId.get(ID) != null) {
                        secNodeSectionId.get(ID).getPaint().setColor(Color.parseColor("#0ABDE3"));
                    }
                }
            } else if (type.contains("99")) {
                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable1 = getResources().getDrawable(R.drawable.node);
                Bitmap bitmap1 = drawableToBitmap(drawable1);
                marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), changeBgTransparentBitmapColor(bitmap1, Color.parseColor("#0ABDE3"))));
            }

            previousLoadFlowDevice = marker;
            loadFlowDeviceType = type;
            loadFlowDeviceId = ID;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void highlightLoadFlowSection(Polyline polyline, String sectionId) {
        try {
            if (loadFlowPreviousSelectedSection != null && loadFlowSectionId != null) {
                loadFlowPreviousSelectedSection.closeInfoWindow();
                loadFlowPreviousSelectedSection.setColor(Color.BLACK);

                if (loadFlowOverVoltageSectionID.containsKey(loadFlowSectionId) && overVoltageColors != null) {
                    loadFlowPreviousSelectedSection.setColor(Color.parseColor(overVoltageColors));
                }

                if (loadFlowUnderVoltageSectionID.containsKey(loadFlowSectionId) && underVoltageColors != null) {
                    loadFlowPreviousSelectedSection.setColor(Color.parseColor(underVoltageColors));
                }

                if (loadFlowOverLoadSectionID.containsKey(loadFlowSectionId) && overloadColors != null) {
                    loadFlowPreviousSelectedSection.setColor(Color.parseColor(overloadColors));
                }

                loadFlowPreviousSelectedSection = null;
                loadFlowSectionId = null;
            }

            if (previousLoadFlowDevice != null && loadFlowDeviceType != null && loadFlowDeviceId != null) {
                previousLoadFlowDevice.closeInfoWindow();
                if (loadFlowDeviceType.contains("8")) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                            secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }
                    previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 90, 0)));

                    if (loadFlowOverVoltageDeviceID.containsKey(loadFlowDeviceId) && overVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(sectionID) != null) {
                                secNodeSectionId.get(sectionID).getPaint().setColor(Color.parseColor(overVoltageColors));
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 90, 0)));
                    }

                    if (loadFlowUnderVoltageDeviceID.containsKey(loadFlowDeviceId) && underVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(sectionID) != null) {
                                secNodeSectionId.get(sectionID).getPaint().setColor(Color.parseColor(underVoltageColors));
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 90, 0)));
                    }

                    if (loadFlowOverLoadDeviceID.containsKey(loadFlowDeviceId) && overloadColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(sectionID) != null) {
                                secNodeSectionId.get(sectionID).getPaint().setColor(Color.parseColor(overloadColors));
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 90, 0)));
                    }

                    previousLoadFlowDevice = null;
                    loadFlowDeviceType = null;
                    loadFlowDeviceId = null;

                } else if (loadFlowDeviceType.contains("5")) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                            secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }
                    previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 90)));

                    if (loadFlowOverVoltageDeviceID.containsKey(loadFlowDeviceId) && overVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(overVoltageColors));
                                loadFlowDeviceId = null;
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 0, 90)));
                    }

                    if (loadFlowUnderVoltageDeviceID.containsKey(loadFlowDeviceId) && underVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(underVoltageColors));
                                loadFlowDeviceId = null;
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 0, 90)));
                    }

                    if (loadFlowOverLoadDeviceID.containsKey(loadFlowDeviceId) && overloadColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(overloadColors));
                                loadFlowDeviceId = null;
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 0, 90)));
                    }

                    previousLoadFlowDevice = null;
                    loadFlowDeviceType = null;
                    loadFlowDeviceId = null;

                } else if (loadFlowDeviceType.contains("14")) {
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                    previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 90)));
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                            secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (loadFlowOverVoltageDeviceID.containsKey(loadFlowDeviceId) && overVoltageColors != null) {
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 0, 90)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(overVoltageColors));
                            }
                        }
                    }

                    if (loadFlowUnderVoltageDeviceID.containsKey(loadFlowDeviceId) && underVoltageColors != null) {
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 0, 90)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(underVoltageColors));
                            }
                        }
                    }

                    if (loadFlowOverLoadDeviceID.containsKey(loadFlowDeviceId) && overloadColors != null) {
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 0, 90)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(overloadColors));
                            }
                        }
                    }

                    previousLoadFlowDevice = null;
                    loadFlowDeviceType = null;
                    loadFlowDeviceId = null;
                } else if (loadFlowDeviceType.contains("13")) {
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                    int paddingLeft = 0; // Left padding in pixels
                    int paddingTop = 0; // Top padding in pixels
                    int paddingRight = 0; // Right padding in pixels
                    int paddingBottom = 12; // Bottom padding in pixels
                    int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                    int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                    Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                    Canvas canva = new Canvas(paddedBitmap);
                    canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                            secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }
                    previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.BLACK), 90, 0)));

                    if (loadFlowOverVoltageDeviceID.containsKey(loadFlowDeviceId) && overVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(overVoltageColors));
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(overVoltageColors)), 90, 0)));
                    }

                    if (loadFlowUnderVoltageDeviceID.containsKey(loadFlowDeviceId) && underVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(underVoltageColors));
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(underVoltageColors)), 90, 0)));
                    }

                    if (loadFlowOverLoadDeviceID.containsKey(loadFlowDeviceId) && overloadColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(overloadColors));
                                loadFlowDeviceId = null;
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(overloadColors)), 90, 0)));
                    }

                    previousLoadFlowDevice = null;
                    loadFlowDeviceType = null;
                    loadFlowDeviceId = null;
                } else if (loadFlowDeviceType.contains("61")) {
                    if (secNodeSectionId.containsKey(loadFlowDeviceId)) {
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.BLACK), 0, 0)));
                    } else {
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.BLACK), 90, 0)));
                    }

                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                            secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (loadFlowOverVoltageDeviceID.containsKey(loadFlowDeviceId) && overVoltageColors != null) {
                        if (secNodeSectionId.containsKey(loadFlowDeviceId)) {
                            previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overVoltageColors)), 0, 0)));
                        } else {
                            previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overVoltageColors)), 90, 0)));
                        }

                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(overVoltageColors));
                            }
                        }
                    }

                    if (loadFlowUnderVoltageDeviceID.containsKey(loadFlowDeviceId) && underVoltageColors != null) {
                        if (secNodeSectionId.containsKey(loadFlowDeviceId)) {
                            previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(underVoltageColors)), 0, 0)));
                        } else {
                            previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(underVoltageColors)), 90, 0)));
                        }

                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(underVoltageColors));
                            }
                        }
                    }

                    if (loadFlowOverLoadDeviceID.containsKey(loadFlowDeviceId) && overloadColors != null) {
                        if (secNodeSectionId.containsKey(loadFlowDeviceId)) {
                            previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overloadColors)), 0, 0)));
                        } else {
                            previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overloadColors)), 90, 0)));
                        }

                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(overloadColors));
                            }
                        }
                    }

                    previousLoadFlowDevice = null;
                    loadFlowDeviceType = null;
                    loadFlowDeviceId = null;

                } else if (loadFlowDeviceType.contains("20")) {
                    int paddingPx = 0;
                    Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                            BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                    Canvas canvas1 = new Canvas(paddedBitmap);
                    canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                            secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }
                    previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.BLACK), 0, 70)));

                    if (loadFlowOverVoltageDeviceID.containsKey(loadFlowDeviceId) && overVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(overVoltageColors));
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(overVoltageColors)), 0, 70)));
                    }

                    if (loadFlowUnderVoltageDeviceID.containsKey(loadFlowDeviceId) && underVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(underVoltageColors));
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(underVoltageColors)), 0, 70)));
                    }

                    if (loadFlowOverLoadDeviceID.containsKey(loadFlowDeviceId) && overloadColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(loadFlowDeviceId) != null) {
                                secNodeSectionId.get(loadFlowDeviceId).getPaint().setColor(Color.parseColor(overloadColors));
                            }
                        }
                        previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(overloadColors)), 0, 70)));
                    }

                    previousLoadFlowDevice = null;
                    loadFlowDeviceType = null;
                    loadFlowDeviceId = null;

                } else if (loadFlowDeviceType.contains("99")) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.dot);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    previousLoadFlowDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), changeBgTransparentBitmapColor(bitmap, Color.BLACK)));
                    previousLoadFlowDevice = null;
                    loadFlowDeviceType = null;
                    loadFlowDeviceId = null;
                }
            }

            polyline.setColor(Color.parseColor("#0ABDE3"));
            loadFlowPreviousSelectedSection = polyline;
            loadFlowSectionId = sectionId;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void highlightShortCircuitDevice(Marker marker, String type, String ID) {
        try {
            if (shortCircuitPreviousDevice != null && shortCircuitDeviceType != null && shortCircuitDeviceId != null) {
                if (shortCircuitDeviceType.contains("8")) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                            secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }
                    shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 90, 0)));

                    if (shortCircuitRatingDeviceID.containsKey(shortCircuitDeviceId) && ratingColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(ratingColors));
                            }
                        }
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(ratingColors)), 90, 0)));
                    }

                    if (shortCircuitOverVoltageDeviceID.containsKey(shortCircuitDeviceId) && overVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overVoltageColors));
                            }
                        }
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 90, 0)));
                    }

                    if (shortCircuitUnderVoltageDeviceID.containsKey(shortCircuitDeviceId) && underVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(underVoltageColors));
                            }
                        }
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 90, 0)));
                    }

                    if (shortCircuitOverLoadDeviceID.containsKey(shortCircuitDeviceId) && overloadColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overloadColors));
                            }
                        }
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 90, 0)));
                    }

                    shortCircuitPreviousDevice = null;
                    shortCircuitDeviceType = null;
                    shortCircuitDeviceId = null;

                } else if (shortCircuitDeviceType.contains("5")) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                            secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }
                    shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 90)));

                    if (shortCircuitRatingDeviceID.containsKey(shortCircuitDeviceId) && ratingColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(ratingColors));
                            }
                        }
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(ratingColors)), 0, 90)));
                    }

                    if (shortCircuitOverVoltageDeviceID.containsKey(shortCircuitDeviceId) && overVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overVoltageColors));
                            }
                        }
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 0, 90)));
                    }

                    if (shortCircuitUnderVoltageDeviceID.containsKey(shortCircuitDeviceId) && underVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(underVoltageColors));
                            }
                        }
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 0, 90)));
                    }

                    if (shortCircuitOverLoadDeviceID.containsKey(shortCircuitDeviceId) && overloadColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overloadColors));
                            }
                        }
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 0, 90)));
                    }

                    shortCircuitPreviousDevice = null;
                    shortCircuitDeviceType = null;
                    shortCircuitDeviceId = null;

                } else if (shortCircuitDeviceType.contains("14")) {
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                    shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 90)));
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                            secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (shortCircuitRatingDeviceID.containsKey(shortCircuitDeviceId) && ratingColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(ratingColors)), 0, 90)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(ratingColors));
                            }
                        }
                    }

                    if (shortCircuitOverVoltageDeviceID.containsKey(shortCircuitDeviceId) && overVoltageColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 0, 90)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overVoltageColors));
                            }
                        }
                    }

                    if (shortCircuitUnderVoltageDeviceID.containsKey(shortCircuitDeviceId) && underVoltageColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 0, 90)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(underVoltageColors));
                            }
                        }
                    }

                    if (shortCircuitOverLoadDeviceID.containsKey(shortCircuitDeviceId) && overloadColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 0, 90)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overloadColors));
                            }
                        }
                    }

                    shortCircuitPreviousDevice = null;
                    shortCircuitDeviceType = null;
                    shortCircuitDeviceId = null;

                } else if (shortCircuitDeviceType.contains("13")) {
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                    int paddingLeft = 0; // Left padding in pixels
                    int paddingTop = 0; // Top padding in pixels
                    int paddingRight = 0; // Right padding in pixels
                    int paddingBottom = 12; // Bottom padding in pixels
                    int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                    int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                    Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                    Canvas canva = new Canvas(paddedBitmap);
                    canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                    shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.BLACK), 90, 0)));
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                            secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (shortCircuitRatingDeviceID.containsKey(shortCircuitDeviceId) && ratingColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(ratingColors)), 90, 0)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(ratingColors));
                            }
                        }
                    }

                    if (shortCircuitOverVoltageDeviceID.containsKey(shortCircuitDeviceId) && overVoltageColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(overVoltageColors)), 90, 0)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overVoltageColors));
                            }
                        }
                    }

                    if (shortCircuitUnderVoltageDeviceID.containsKey(shortCircuitDeviceId) && underVoltageColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(underVoltageColors)), 90, 0)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(underVoltageColors));
                            }
                        }
                    }

                    if (shortCircuitOverLoadDeviceID.containsKey(shortCircuitDeviceId) && overloadColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(overloadColors)), 90, 0)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overloadColors));
                            }
                        }
                    }

                    shortCircuitPreviousDevice = null;
                    shortCircuitDeviceType = null;
                    shortCircuitDeviceId = null;

                } else if (shortCircuitDeviceType.contains("61")) {
                    if (secNodeSectionId.containsKey(shortCircuitDeviceId)) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.BLACK), 0, 0)));
                    } else {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.BLACK), 90, 0)));
                    }

                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                            secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (shortCircuitRatingDeviceID.containsKey(shortCircuitDeviceId) && ratingColors != null) {
                        if (secNodeSectionId.containsKey(shortCircuitDeviceId)) {
                            shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(ratingColors)), 0, 0)));
                        } else {
                            shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(ratingColors)), 90, 0)));
                        }

                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(ratingColors));
                            }
                        }
                    }

                    if (shortCircuitOverVoltageDeviceID.containsKey(shortCircuitDeviceId) && overVoltageColors != null) {
                        if (secNodeSectionId.containsKey(shortCircuitDeviceId)) {
                            shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overVoltageColors)), 0, 0)));
                        } else {
                            shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overVoltageColors)), 90, 0)));
                        }

                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overVoltageColors));
                            }
                        }
                    }

                    if (shortCircuitUnderVoltageDeviceID.containsKey(shortCircuitDeviceId) && underVoltageColors != null) {
                        if (secNodeSectionId.containsKey(shortCircuitDeviceId)) {
                            shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(underVoltageColors)), 0, 0)));
                        } else {
                            shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(underVoltageColors)), 90, 0)));
                        }

                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(underVoltageColors));
                            }
                        }
                    }

                    if (shortCircuitOverLoadDeviceID.containsKey(shortCircuitDeviceId) && overloadColors != null) {
                        if (secNodeSectionId.containsKey(shortCircuitDeviceId)) {
                            shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overloadColors)), 0, 0)));
                        } else {
                            shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overloadColors)), 90, 0)));
                        }

                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overloadColors));
                            }
                        }
                    }

                    shortCircuitPreviousDevice = null;
                    shortCircuitDeviceType = null;
                    shortCircuitDeviceId = null;

                } else if (shortCircuitDeviceType.contains("20")) {
                    int paddingPx = 0;
                    Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                            BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                    Canvas canvas1 = new Canvas(paddedBitmap);
                    canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                    shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.BLACK), 0, 70)));
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                            secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (shortCircuitRatingDeviceID.containsKey(shortCircuitDeviceId) && ratingColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(ratingColors)), 0, 70)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(ratingColors));
                            }
                        }
                    }

                    if (shortCircuitOverVoltageDeviceID.containsKey(shortCircuitDeviceId) && overVoltageColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(overVoltageColors)), 0, 70)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overVoltageColors));
                            }
                        }
                    }

                    if (shortCircuitUnderVoltageDeviceID.containsKey(shortCircuitDeviceId) && underVoltageColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(underVoltageColors)), 0, 70)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(underVoltageColors));
                            }
                        }
                    }

                    if (shortCircuitOverLoadDeviceID.containsKey(shortCircuitDeviceId) && overloadColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(overloadColors)), 0, 70)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overloadColors));
                            }
                        }
                    }

                    shortCircuitPreviousDevice = null;
                    shortCircuitDeviceType = null;
                    shortCircuitDeviceId = null;

                } else if (shortCircuitDeviceType.contains("99")) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.dot);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), changeBgTransparentBitmapColor(bitmap, Color.BLACK)));
                    shortCircuitPreviousDevice = null;
                    shortCircuitDeviceType = null;
                    shortCircuitDeviceId = null;
                }
            }

            if (shortCircuitPreviousSelectedSection != null && shortCircuitSectionId != null) {
                shortCircuitPreviousSelectedSection.closeInfoWindow();
                shortCircuitPreviousSelectedSection.setColor(Color.BLACK);

                if (shortCircuitRatingSectionID.containsKey(shortCircuitSectionId) && ratingColors != null) {
                    shortCircuitPreviousSelectedSection.setColor(Color.parseColor(ratingColors));
                }

                if (shortCircuitOverVoltageSectionID.containsKey(shortCircuitSectionId) && overVoltageColors != null) {
                    shortCircuitPreviousSelectedSection.setColor(Color.parseColor(overVoltageColors));
                }

                if (shortCircuitOverLoadSectionID.containsKey(shortCircuitSectionId) && overloadColors != null) {
                    shortCircuitPreviousSelectedSection.setColor(Color.parseColor(overloadColors));
                }

                if (shortCircuitUnderVoltageSectionID.containsKey(shortCircuitSectionId) && underVoltageColors != null) {
                    shortCircuitPreviousSelectedSection.setColor(Color.parseColor(underVoltageColors));
                }
            }

            if (type.contains("8")) {
                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                Bitmap bitmap = drawableToBitmap(drawable);
                if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                    if (secNodeSectionId.get(ID) != null) {
                        secNodeSectionId.get(ID).getPaint().setColor(Color.parseColor("#0ABDE3"));
                    }
                }
                marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#0ABDE3")), 90, 0)));
            } else if (type.contains("5")) {
                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                Bitmap bitmap = drawableToBitmap(drawable);
                if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                    if (secNodeSectionId.get(ID) != null) {
                        secNodeSectionId.get(ID).getPaint().setColor(Color.parseColor("#0ABDE3"));
                    }
                }
                marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#0ABDE3")), 0, 90)));
            } else if (type.contains("14")) {
                if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                    if (secNodeSectionId.get(ID) != null) {
                        secNodeSectionId.get(ID).getPaint().setColor(Color.parseColor("#0ABDE3"));
                    }
                }
                Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor("#0ABDE3")), 0, 90)));
            } else if (type.contains("13")) {
                Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                int paddingLeft = 0; // Left padding in pixels
                int paddingTop = 0; // Top padding in pixels
                int paddingRight = 0; // Right padding in pixels
                int paddingBottom = 12; // Bottom padding in pixels
                int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                Canvas canva = new Canvas(paddedBitmap);
                canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                    if (secNodeSectionId.get(ID) != null) {
                        secNodeSectionId.get(ID).getPaint().setColor(Color.parseColor("#0ABDE3"));
                    }
                }
                marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor("#0ABDE3")), 90, 0)));
            } else if (type.contains("61")) {
                if (secNodeSectionId.containsKey(ID)) {
                    marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor("#0ABDE3")), 0, 0)));
                } else {
                    marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor("#0ABDE3")), 90, 0)));
                }

                if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                    if (secNodeSectionId.get(ID) != null) {
                        secNodeSectionId.get(ID).getPaint().setColor(Color.parseColor("#0ABDE3"));
                    }
                }
            } else if (type.contains("20")) {
                int paddingPx = 0;
                Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                        BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                Canvas canvas1 = new Canvas(paddedBitmap);
                canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                    if (secNodeSectionId.get(ID) != null) {
                        secNodeSectionId.get(ID).getPaint().setColor(Color.parseColor("#0ABDE3"));
                    }
                }
                marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor("#0ABDE3")), 0, 70)));
            } else if (type.contains("99")) {
                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.node);
                Bitmap bitmap = drawableToBitmap(drawable);
                marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), changeBgTransparentBitmapColor(bitmap, Color.parseColor("#0ABDE3"))));
            }

            shortCircuitPreviousDevice = marker;
            shortCircuitDeviceType = type;
            shortCircuitDeviceId = ID;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void highlightShortCircuitSection(Polyline polyline, String sectionId) {
        try {
            if (shortCircuitPreviousSelectedSection != null && shortCircuitSectionId != null) {
                shortCircuitPreviousSelectedSection.closeInfoWindow();
                shortCircuitPreviousSelectedSection.setColor(Color.BLACK);

                if (shortCircuitRatingSectionID.containsKey(shortCircuitSectionId) && ratingColors != null) {
                    shortCircuitPreviousSelectedSection.setColor(Color.parseColor(ratingColors));
                }

                if (shortCircuitOverVoltageSectionID.containsKey(shortCircuitSectionId) && overVoltageColors != null) {
                    shortCircuitPreviousSelectedSection.setColor(Color.parseColor(overVoltageColors));
                }

                if (shortCircuitOverLoadSectionID.containsKey(shortCircuitSectionId) && overloadColors != null) {
                    shortCircuitPreviousSelectedSection.setColor(Color.parseColor(overloadColors));
                }

                if (shortCircuitUnderVoltageSectionID.containsKey(shortCircuitSectionId) && underVoltageColors != null) {
                    shortCircuitPreviousSelectedSection.setColor(Color.parseColor(underVoltageColors));
                }
            }

            if (shortCircuitPreviousDevice != null && shortCircuitDeviceType != null && shortCircuitDeviceId != null) {
                if (shortCircuitDeviceType.contains("8")) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.breaker);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                            secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }
                    shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 90, 0)));

                    if (shortCircuitRatingDeviceID.containsKey(shortCircuitDeviceId) && ratingColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(ratingColors));
                            }
                        }
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(ratingColors)), 90, 0)));
                    }

                    if (shortCircuitOverVoltageDeviceID.containsKey(shortCircuitDeviceId) && overVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overVoltageColors));
                            }
                        }
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 90, 0)));
                    }

                    if (shortCircuitUnderVoltageDeviceID.containsKey(shortCircuitDeviceId) && underVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(underVoltageColors));
                            }
                        }
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 90, 0)));
                    }

                    if (shortCircuitOverLoadDeviceID.containsKey(shortCircuitDeviceId) && overloadColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overloadColors));
                            }
                        }
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 90, 0)));
                    }

                    shortCircuitPreviousDevice = null;
                    shortCircuitDeviceType = null;
                    shortCircuitDeviceId = null;

                } else if (shortCircuitDeviceType.contains("5")) {
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.transformer);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                            secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }
                    shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 90)));

                    if (shortCircuitRatingDeviceID.containsKey(shortCircuitDeviceId) && ratingColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(ratingColors));
                            }
                        }
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(ratingColors)), 0, 90)));
                    }

                    if (shortCircuitOverVoltageDeviceID.containsKey(shortCircuitDeviceId) && overVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overVoltageColors));
                            }
                        }
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 0, 90)));
                    }

                    if (shortCircuitUnderVoltageDeviceID.containsKey(shortCircuitDeviceId) && underVoltageColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(underVoltageColors));
                            }
                        }
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 0, 90)));
                    }

                    if (shortCircuitOverLoadDeviceID.containsKey(shortCircuitDeviceId) && overloadColors != null) {
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overloadColors));
                            }
                        }
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 0, 90)));
                    }

                    shortCircuitPreviousDevice = null;
                    shortCircuitDeviceType = null;
                    shortCircuitDeviceId = null;

                } else if (shortCircuitDeviceType.contains("14")) {
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "L", 90f, Color.BLACK);
                    shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.BLACK), 0, 90)));
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                            secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (shortCircuitRatingDeviceID.containsKey(shortCircuitDeviceId) && ratingColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(ratingColors)), 0, 90)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(ratingColors));
                            }
                        }
                    }

                    if (shortCircuitOverVoltageDeviceID.containsKey(shortCircuitDeviceId) && overVoltageColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overVoltageColors)), 0, 90)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overVoltageColors));
                            }
                        }
                    }

                    if (shortCircuitUnderVoltageDeviceID.containsKey(shortCircuitDeviceId) && underVoltageColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(underVoltageColors)), 0, 90)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(underVoltageColors));
                            }
                        }
                    }

                    if (shortCircuitOverLoadDeviceID.containsKey(shortCircuitDeviceId) && overloadColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(bitmap, Color.parseColor(overloadColors)), 0, 90)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overloadColors));
                            }
                        }
                    }

                    shortCircuitPreviousDevice = null;
                    shortCircuitDeviceType = null;
                    shortCircuitDeviceId = null;

                } else if (shortCircuitDeviceType.contains("13")) {
                    Bitmap bitmap = OTFToBitmapConverter.convertOTFToBitmap(MapActivity.this, "D", 70f, Color.BLACK);
                    int paddingLeft = 0; // Left padding in pixels
                    int paddingTop = 0; // Top padding in pixels
                    int paddingRight = 0; // Right padding in pixels
                    int paddingBottom = 12; // Bottom padding in pixels
                    int newWidth = bitmap.getWidth() + paddingLeft + paddingRight;
                    int newHeight = bitmap.getHeight() + paddingTop + paddingBottom;
                    Bitmap paddedBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
                    Canvas canva = new Canvas(paddedBitmap);
                    canva.drawBitmap(bitmap, paddingLeft, paddingTop, null);
                    shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.BLACK), 90, 0)));
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                            secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (shortCircuitRatingDeviceID.containsKey(shortCircuitDeviceId) && ratingColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(ratingColors)), 90, 0)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(ratingColors));
                            }
                        }
                    }

                    if (shortCircuitOverVoltageDeviceID.containsKey(shortCircuitDeviceId) && overVoltageColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(overVoltageColors)), 90, 0)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overVoltageColors));
                            }
                        }
                    }

                    if (shortCircuitUnderVoltageDeviceID.containsKey(shortCircuitDeviceId) && underVoltageColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(underVoltageColors)), 90, 0)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(underVoltageColors));
                            }
                        }
                    }

                    if (shortCircuitOverLoadDeviceID.containsKey(shortCircuitDeviceId) && overloadColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBitmapColor(paddedBitmap, Color.parseColor(overloadColors)), 90, 0)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overloadColors));
                            }
                        }
                    }

                    shortCircuitPreviousDevice = null;
                    shortCircuitDeviceType = null;
                    shortCircuitDeviceId = null;

                } else if (shortCircuitDeviceType.contains("61")) {
                    if (secNodeSectionId.containsKey(shortCircuitDeviceId)) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.BLACK), 0, 0)));
                    } else {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.BLACK), 90, 0)));
                    }

                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                            secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (shortCircuitRatingDeviceID.containsKey(shortCircuitDeviceId) && ratingColors != null) {
                        if (secNodeSectionId.containsKey(shortCircuitDeviceId)) {
                            shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(ratingColors)), 0, 0)));
                        } else {
                            shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(ratingColors)), 90, 0)));
                        }

                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(ratingColors));
                            }
                        }
                    }

                    if (shortCircuitOverVoltageDeviceID.containsKey(shortCircuitDeviceId) && overVoltageColors != null) {
                        if (secNodeSectionId.containsKey(shortCircuitDeviceId)) {
                            shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overVoltageColors)), 0, 0)));
                        } else {
                            shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overVoltageColors)), 90, 0)));
                        }

                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overVoltageColors));
                            }
                        }
                    }

                    if (shortCircuitUnderVoltageDeviceID.containsKey(shortCircuitDeviceId) && underVoltageColors != null) {
                        if (secNodeSectionId.containsKey(shortCircuitDeviceId)) {
                            shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(underVoltageColors)), 0, 0)));
                        } else {
                            shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(underVoltageColors)), 90, 0)));
                        }

                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(underVoltageColors));
                            }
                        }
                    }

                    if (shortCircuitOverLoadDeviceID.containsKey(shortCircuitDeviceId) && overloadColors != null) {
                        if (secNodeSectionId.containsKey(shortCircuitDeviceId)) {
                            shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overloadColors)), 0, 0)));
                        } else {
                            shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(BitmapImg.Shuntcapacitor(), Color.parseColor(overloadColors)), 90, 0)));
                        }

                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overloadColors));
                            }
                        }
                    }

                    shortCircuitPreviousDevice = null;
                    shortCircuitDeviceType = null;
                    shortCircuitDeviceId = null;

                } else if (shortCircuitDeviceType.contains("20")) {
                    int paddingPx = 0;
                    Bitmap paddedBitmap = Bitmap.createBitmap(BitmapImg.Spotload().getWidth() + paddingPx * 2, // Add padding to both sides
                            BitmapImg.Spotload().getHeight() + paddingPx * 2, Bitmap.Config.ARGB_8888);
                    Canvas canvas1 = new Canvas(paddedBitmap);
                    canvas1.drawBitmap(BitmapImg.Spotload(), paddingPx, 10, null);
                    shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.BLACK), 0, 70)));
                    if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                        if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                            secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.BLACK);
                        }
                    }

                    if (shortCircuitRatingDeviceID.containsKey(shortCircuitDeviceId) && ratingColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(ratingColors)), 0, 70)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(ratingColors));
                            }
                        }
                    }

                    if (shortCircuitOverVoltageDeviceID.containsKey(shortCircuitDeviceId) && overVoltageColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(overVoltageColors)), 0, 70)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overVoltageColors));
                            }
                        }
                    }

                    if (shortCircuitUnderVoltageDeviceID.containsKey(shortCircuitDeviceId) && underVoltageColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(underVoltageColors)), 0, 70)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(underVoltageColors));
                            }
                        }
                    }

                    if (shortCircuitOverLoadDeviceID.containsKey(shortCircuitDeviceId) && overloadColors != null) {
                        shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), addPaddingToBitmap(changeBgTransparentBitmapColor(paddedBitmap, Color.parseColor(overloadColors)), 0, 70)));
                        if (secNodeSectionId != null && !secNodeSectionId.isEmpty()) {
                            if (secNodeSectionId.get(shortCircuitDeviceId) != null) {
                                secNodeSectionId.get(shortCircuitDeviceId).getPaint().setColor(Color.parseColor(overloadColors));
                            }
                        }
                    }

                    shortCircuitPreviousDevice = null;
                    shortCircuitDeviceType = null;
                    shortCircuitDeviceId = null;

                } else if (shortCircuitDeviceType.contains("99")) {
                  /*  @SuppressLint("UseCompatLoadingForDrawables")
                    Drawable drawable = getResources().getDrawable(R.drawable.dot);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    marker.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), changeBgTransparentBitmapColor(bitmap, Color.BLACK)));
                    shortCircuitPreviousDevice = null;
                    shortCircuitDeviceType = null;
                    shortCircuitDeviceId = null;*/
                    @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.dot);
                    Bitmap bitmap = drawableToBitmap(drawable);
                    shortCircuitPreviousDevice.setIcon(new BitmapDrawable(binding.map.getContext().getResources(), changeBgTransparentBitmapColor(bitmap, Color.BLACK)));

                    shortCircuitPreviousDevice = null;
                    shortCircuitDeviceType = null;
                    shortCircuitDeviceId = null;
                }
            }

            polyline.setColor(Color.parseColor("#0ABDE3"));
            shortCircuitPreviousSelectedSection = polyline;
            shortCircuitSectionId = sectionId;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class NodeSelected extends Dialog {

        private Context context;
        private String nodeID;
        private NodePopLayoutBinding binding;
        private Marker marker;

        public NodeSelected(@NonNull Context context, String nodeId, Marker marker) {
            super(context);
            this.context = context;
            this.nodeID = nodeId;
            this.marker = marker;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = NodePopLayoutBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            View MainLayoutBackGround = getWindow().getDecorView().getRootView();
            MainLayoutBackGround.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            MainLayoutBackGround.setBackgroundResource(R.drawable.pop_background);

            binding.btnConventional.setOnClickListener(v -> {
                shortCircuit = new ShortCircuit(MapActivity.this);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("Network", intent.getStringArrayListExtra("NetworkId"));
                bundle.putString("Index", "1");
                bundle.putString("NodeId", nodeID);
                shortCircuit.setArguments(bundle);
                shortCircuit.show(getSupportFragmentManager(), shortCircuit.getTag());
                @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = getResources().getDrawable(R.drawable.dot);
                Bitmap bitmap = drawableToBitmap(drawable);
                marker.setIcon(new BitmapDrawable(context.getResources(), bitmap));
                dismiss();
            });

            binding.cancelConvention.setOnClickListener(v -> {
                dismiss();
            });
        }

        @Override
        public void dismiss() {
            super.dismiss();
            binding = null;
        }
    }

    private void addNewSections(List<GeoPoint> geoPoint) {
        GeoPoint lastGeoPoint = new GeoPoint(geoPoint.get(geoPoint.size() - 1));
        Polyline line = new Polyline();
        line.setPoints(geoPoint);
        line.getOutlinePaint().setColor(Color.parseColor("#14C61B"));
        line.getOutlinePaint().setStrokeWidth(3);
        line.setWidth(3f);
        line.setColor(Color.parseColor("#14C61B"));
        binding.map.getOverlayManager().add(line);
        newPolyLineList.add(line);
        binding.map.invalidate();
        coordinateList.add(lastGeoPoint);
    }
    public static void setStatusBarTransparent(AppCompatActivity activity) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        );

        window.setStatusBarColor(Color.parseColor("#183883"));
    }

    private void removeFeeder(String feederId) {

        Log.d("REMOVE", "Removing feeder: " + feederId);

        List<Overlay> toRemove = new ArrayList<>();

        for (Overlay overlay : binding.map.getOverlays()) {

            if (overlay instanceof Polyline) {
                Polyline pl = (Polyline) overlay;

                Object rel = pl.getRelatedObject();
                if (rel instanceof KmlPlacemark) {
                    KmlPlacemark place = (KmlPlacemark) rel;
                    String networkId = place.getExtendedData("NetworkId");

                    if (feederId.equalsIgnoreCase(networkId)) {
                        toRemove.add(pl);
                    }
                }
            }

            else if (overlay instanceof Marker) {
                Marker mk = (Marker) overlay;

                Object rel = mk.getRelatedObject();
                if (rel instanceof KmlPlacemark) {
                    KmlPlacemark place = (KmlPlacemark) rel;
                    String networkId = place.getExtendedData("NetworkId");

                    if (feederId.equalsIgnoreCase(networkId)) {
                        toRemove.add(mk);
                    }
                }
            }

            else if (overlay instanceof Polygon) {
                Polygon pg = (Polygon) overlay;

                Object rel = pg.getRelatedObject();
                if (rel instanceof KmlPlacemark) {
                    KmlPlacemark place = (KmlPlacemark) rel;
                    String networkId = place.getExtendedData("NetworkId");

                    if (feederId.equalsIgnoreCase(networkId)) {
                        toRemove.add(pg);
                    }
                }
            }

            else if (overlay instanceof FolderOverlay) {
                FolderOverlay folder = (FolderOverlay) overlay;

                List<Overlay> childRemove = new ArrayList<>();

                for (Overlay child : folder.getItems()) {

                    if (child instanceof Polyline) {
                        Polyline pl = (Polyline) child;
                        Object rel = pl.getRelatedObject();
                        if (rel instanceof KmlPlacemark) {
                            KmlPlacemark place = (KmlPlacemark) rel;
                            if (feederId.equalsIgnoreCase(place.getExtendedData("NetworkId"))) {
                                childRemove.add(pl);
                            }
                        }
                    }

                    if (child instanceof Marker) {
                        Marker mk = (Marker) child;
                        Object rel = mk.getRelatedObject();
                        if (rel instanceof KmlPlacemark) {
                            KmlPlacemark place = (KmlPlacemark) rel;
                            if (feederId.equalsIgnoreCase(place.getExtendedData("NetworkId"))) {
                                childRemove.add(mk);
                            }
                        }
                    }

                    if (child instanceof Polygon) {
                        Polygon pg = (Polygon) child;
                        Object rel = pg.getRelatedObject();
                        if (rel instanceof KmlPlacemark) {
                            KmlPlacemark place = (KmlPlacemark) rel;
                            if (feederId.equalsIgnoreCase(place.getExtendedData("NetworkId"))) {
                                childRemove.add(pg);
                            }
                        }
                    }
                }

                folder.getItems().removeAll(childRemove);
            }
        }

        binding.map.getOverlays().removeAll(toRemove);
        Log.d("REMOVE", "Removed overlays = " + toRemove.size());

        binding.map.invalidate();
    }

}
