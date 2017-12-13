package com.arny.myapidemo.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arny.myapidemo.R;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
/**
 * A simple {@link Fragment} subclass.
 */
public class OsmandMapFragment extends Fragment {

    private Context context;
    private MapView mMapView;
    private MyLocationNewOverlay mLocationOverlay;
    private CompassOverlay mCompassOverlay;

    public OsmandMapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = container.getContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        return inflater.inflate(R.layout.fragment_osmand_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = view.findViewById(R.id.map);
        mMapView.setTileSource(TileSourceFactory.MAPNIK);
        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);
        IMapController mapController = mMapView.getController();
        mapController.setZoom(9);
        GeoPoint startPoint = new GeoPoint(48.0, 57.0);
        mapController.setCenter(startPoint);
        this.mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(context),mMapView);
        this.mLocationOverlay.enableMyLocation();
        mMapView.getOverlays().add(this.mLocationOverlay);
        this.mCompassOverlay = new CompassOverlay(context, new InternalCompassOrientationProvider(context), mMapView);
        this.mCompassOverlay.enableCompass();
        mMapView.getOverlays().add(this.mCompassOverlay);
    }

    @Override
    public void onResume() {
        super.onResume();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
    }
}
