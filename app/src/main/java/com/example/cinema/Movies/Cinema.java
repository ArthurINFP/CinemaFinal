package com.example.cinema.Movies;

import com.google.android.gms.maps.model.LatLng;

public class Cinema {
    public String name;
    public String address;
    public LatLng location;

    public Cinema(String name, String address, LatLng location) {
        this.name = name;
        this.address = address;
        this.location = location;
    }
}
