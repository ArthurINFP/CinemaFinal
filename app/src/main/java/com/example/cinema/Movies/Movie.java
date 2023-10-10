package com.example.cinema.Movies;

import android.graphics.drawable.Drawable;

import com.example.cinema.MainActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie implements Serializable {
    int id, duration;
    Drawable thumbnail;
    String title, description,category,trailerUrl, bookingUrl,releaseDate;
    float ticketPrice;
    ArrayList<String> comment;

    boolean favorite;


    public Drawable getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Drawable thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getBookingUrl() {
        return bookingUrl;
    }

    public void setBookingUrl(String bookingUrl) {
        this.bookingUrl = bookingUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    // Constructor
    public Movie(int id, Drawable thumbnail, String trailerUrlId, String bookingUrlId
            , String title, String description, float ticketPrice
            , ArrayList<String> comment, String category, int duration,String releaseDate,boolean favorite) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.duration = duration;
        this.trailerUrl = trailerUrlId;
        this.bookingUrl = bookingUrlId;
        this.title = title;
        this.description = description;
        this.ticketPrice = ticketPrice;
        this.comment = comment;
        this.category = category;
        this.releaseDate = releaseDate;
        this.favorite = favorite;
    }

    // All setter and getter
    public int getId() {
        return id;
    }


    public String getTrailerUrlId() {
        return trailerUrl;
    }

    public String getBookingUrlId() {
        return bookingUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public float getTicketPrice() {
        return ticketPrice;
    }

    public ArrayList<String> getComment() {
        return comment;
    }

    public String getCategory() {
        return category;
    }
    public int getDuration() {
        return duration;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTrailerUrlId(String trailerUrlId) {
        this.trailerUrl = trailerUrlId;
    }

    public void setBookingUrlId(String bookingUrlId) {
        this.bookingUrl = bookingUrlId;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTicketPrice(float ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setComment(ArrayList<String> comment) {
        this.comment = comment;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
