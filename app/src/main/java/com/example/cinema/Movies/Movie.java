package com.example.cinema.Movies;

import android.graphics.drawable.Drawable;

import com.example.cinema.MainActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie implements Serializable {
    int id;
    int duration;
    Drawable thumbnail;
    String title;
    String description;
    String category;
    String trailerUrl;
    String bookingUrl;
    String releaseDate;
    float ticketPrice;
    float rating;
    ArrayList<String> comment;
    boolean favorite;

    // Constructor
    public Movie(int id, Drawable thumbnail, String trailerUrlId, String bookingUrlId
            , String title, String description, float ticketPrice, float rating
            , ArrayList<String> comment, String category, int duration, String releaseDate, boolean favorite) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.duration = duration;
        this.trailerUrl = trailerUrlId;
        this.bookingUrl = bookingUrlId;
        this.title = title;
        this.description = description;
        this.ticketPrice = ticketPrice;
        this.rating = rating;
        this.comment = comment;
        this.category = category;
        this.releaseDate = releaseDate;
        this.favorite = favorite;
    }

    // All setter and getter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Drawable getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(Drawable thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrailerUrl() { return trailerUrl; }
    public void setTrailerUrl(String trailerUrl) { this.trailerUrl = trailerUrl; }

    public String getBookingUrl() {
        return bookingUrl;
    }
    public void setBookingUrl(String bookingUrl) {
        this.bookingUrl = bookingUrl;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public float getTicketPrice() {
        return ticketPrice;
    }
    public void setTicketPrice(float ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }

    public boolean isFavorite() {
        return favorite;
    }
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public ArrayList<String> getComment() {
        return comment;
    }
    public void setComment(ArrayList<String> comment) {
        this.comment = comment;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
}
