package ru.mirea.feofanov.mireaproject.ui.map;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import ru.mirea.feofanov.mireaproject.R;
import ru.mirea.feofanov.mireaproject.databinding.FragmentMapBinding;

public class MapFragment extends Fragment {
    private MapView mapView = null;
    private FragmentMapBinding binding;
    boolean isWork;
    private static final int REQUEST_CODE_PERMISSION = 100;
    MyLocationNewOverlay locationNewOverlay;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentMapBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());
        if (ContextCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {
            ActivityCompat.requestPermissions(this.getActivity(), new String[] {android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_PERMISSION);
        }
        Configuration.getInstance().load(inflater.getContext(),
                PreferenceManager.getDefaultSharedPreferences(inflater.getContext()));

        mapView = binding.mapView;
        mapView.setZoomRounding(true);
        mapView.setMultiTouchControls(true);
        IMapController mapController = mapView.getController();
        mapController.setZoom(15.0);
        GeoPoint startPoint = new GeoPoint(55.794229, 37.700772);
        mapController.setCenter(startPoint);

        locationNewOverlay = new MyLocationNewOverlay(new
                GpsMyLocationProvider(inflater.getContext()),mapView);
        locationNewOverlay.enableMyLocation();
        mapView.getOverlays().add(this.locationNewOverlay);

        CompassOverlay compassOverlay = new CompassOverlay(inflater.getContext(), new
                InternalCompassOrientationProvider(inflater.getContext()), mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);

        final Context context = this.getContext();
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(mapView);
        scaleBarOverlay.setCentred(true);
        scaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        mapView.getOverlays().add(scaleBarOverlay);

        drawIcon("МИРЭА - Российский техногический университет",
                R.drawable.mirea, R.drawable.mirea_ico, 55.794229, 37.700772);
        drawIcon("Большой театр - Государственный академический Большой театр России",
                R.drawable.theatre, R.drawable.theatre_ico, 55.760221, 37.618561);


        Marker marker = new Marker(mapView);
        marker.setPosition(new GeoPoint(55.794229, 37.700772));
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Toast.makeText(inflater.getContext(),"Click",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mapView.getOverlays().add(marker);

        marker.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));

        marker.setTitle("Title");
        return binding.getRoot();
    }
    public void drawIcon(String text, Integer drawable, Integer icon, Double aLatitude, Double aLongitude){
        Marker marker = new Marker(mapView);
        marker.setPosition(new GeoPoint(aLatitude, aLongitude));
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
                binding.imageView.setImageResource(drawable);
                return true;
            }
        });
        mapView.getOverlays().add(marker);

        marker.setIcon(ResourcesCompat.getDrawable(getResources(),
                icon, null));
    }
    @Override
    public void onResume() {
        super.onResume();
        Configuration.getInstance().load(getContext(),
                PreferenceManager.getDefaultSharedPreferences(getContext()));
        if (mapView != null) {
            mapView.onResume();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        Configuration.getInstance().save(getContext(),
                PreferenceManager.getDefaultSharedPreferences(getContext()));
        if (mapView != null) {
            mapView.onPause();
        }
    }
}