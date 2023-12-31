package com.example.cinema.Fragment;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cinema.MainActivity;
import com.example.cinema.Movies.Cinema;
import com.example.cinema.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback{
    public static final int FINE_PERMISSION_CODE = 1;
    public static ArrayList<Cinema> cinemas;
    public MainActivity mainActivity;
    public SupportMapFragment map;
    public GoogleMap googleMap;

    //region Views
    private Button nextBtn;
    private TextView cinemaNameView;
    private TextView cinemaAddressView;
    private TextView distanceView;
    private Button refreshBtn;
    //endregion

    public Location currentLocation;
    public Cinema targetCinema;
    public Marker targetLocationMarker;

    public MapFragment() { }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        cinemaInit();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static void cinemaInit() {
        cinemas = new ArrayList<>();
        cinemas.add(new Cinema(
                "CGV Hoàng Văn Thụ",
                "Hoàng Văn Thụ, Phường 2, Tân Bình, Ho Chi Minh City",
                new LatLng(10.798789338946088, 106.66014809534515)));
        cinemas.add(new Cinema(
                "CGV Lý Chính Thắng",
                "Lý Chính Thắng, Phường 8, District 3, Ho Chi Minh City",
                new LatLng(10.789259177041657, 106.68559665116729)));
        cinemas.add(new Cinema(
                "CGV Giga Mall Thủ Đức",
                "Phạm Văn Đồng, Hiep Binh Chanh, Thủ Đức, Ho Chi Minh City",
                new LatLng(10.827768894550449, 106.72125611068755)));
        cinemas.add(new Cinema(
                "CGV Pearl Plaza",
                "Đường Điện Biên Phủ, Phường 25, Bình Thạnh, Ho Chi Minh City",
                new LatLng(10.800165126490995, 106.71848496650956)));
        cinemas.add(new Cinema(
                "CGV Vincom Center Landmark 81",
                "Vinhomes Central Park, Phường 22, Bình Thạnh, Ho Chi Minh City",
                new LatLng(10.79525421800316, 106.72139729534507)));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cinemaNameView = view.findViewById(R.id.cinema_name);
        cinemaAddressView = view.findViewById(R.id.cinema_address);
        distanceView = view.findViewById(R.id.distance);
        nextBtn = view.findViewById(R.id.next_btn);
        nextBtn.setOnClickListener(nextBtnView -> nextLocation());
        refreshBtn = view.findViewById(R.id.refresh_btn);
        refreshBtn.setOnClickListener(refBtnView -> refreshMap());
        ((MainActivity) getActivity()).getLocation();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                .title("You are here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        setTargetCinema(getNearestCinema());
    }

    public void refreshMap() {
        mainActivity.getLocation();
    }

    public void setTargetCinema(Cinema cinema) {
        targetCinema = cinema;
        if (targetLocationMarker != null) {
            targetLocationMarker.remove();
        }
        targetLocationMarker = googleMap.addMarker(new MarkerOptions().position(cinema.location).title(cinema.name));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cinema.location, 13f));
        cinemaNameView.setText("Cinema: " + cinema.name);
        cinemaAddressView.setText("Address: " + cinema.address);
        DecimalFormat formatter = new DecimalFormat("#.##");
        formatter.setRoundingMode(RoundingMode.CEILING);
        distanceView.setText("Distance: \n>" +
                formatter.format(getDistance(cinema.location.latitude, cinema.location.longitude) / 1000) + " km");
    }

    public Cinema getNearestCinema() {
        float nearestDst = (float) Double.POSITIVE_INFINITY;
        int nearestId = -1;
        float distance;
        for(int i = 0; i < cinemas.size(); i++) {
            distance = getDistance(cinemas.get(i).location.latitude, cinemas.get(i).location.longitude);
            if (distance < nearestDst) {
                nearestDst = distance;
                nearestId = i;
            }
        }
        if (nearestId > -1) {
            return cinemas.get(nearestId);
        }
        return null;
    }

    public void nextLocation() {
        if (googleMap == null) {
            Toast.makeText(mainActivity, "Please turn on Location Service and grant permission, then Refresh to find cinema location.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (targetCinema == null) {
            refreshMap();
            return;
        }
        int currentCinemaIndex = cinemas.indexOf(targetCinema);
        if (currentCinemaIndex + 1 < cinemas.size()) {
            setTargetCinema(cinemas.get(currentCinemaIndex + 1));
        }
        else {
            setTargetCinema(cinemas.get(0));
        }
    }

    private float getDistance(double latitude, double longitude) {
        if (currentLocation == null) return 0;
        float[] results = new float[1];
        Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(),
                latitude, longitude, results);
        return results[0];
    }
}