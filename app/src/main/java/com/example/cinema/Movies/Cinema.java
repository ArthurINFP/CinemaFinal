package com.example.cinema.Movies;

import com.google.android.gms.maps.model.LatLng;

public class Cinema {
    public String description;
    public LatLng location;

    public Cinema(String description, LatLng location) {
        this.description = description;
        this.location = location;
    }
}
