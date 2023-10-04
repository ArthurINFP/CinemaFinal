package com.example.cinema.Movies;

import java.util.ArrayList;

public class Movie {
    int id, thumbnailID, trailerUrlId, bookingUrlId, duration;
    String title, description;
    float ticketPrice;
    ArrayList<String> comment, category;



    // Constructor
    public Movie(int id, int thumbnailID, int trailerUrlId, int bookingUrlId
            , String title, String description, float ticketPrice
            , ArrayList<String> comment, ArrayList<String> category, int duration) {
        this.id = id;
        this.duration = duration;
        this.thumbnailID = thumbnailID;
        this.trailerUrlId = trailerUrlId;
        this.bookingUrlId = bookingUrlId;
        this.title = title;
        this.description = description;
        this.ticketPrice = ticketPrice;
        this.comment = comment;
        this.category = category;
    }

    // All setter and getter
    public int getId() {
        return id;
    }

    public int getThumbnailID() {
        return thumbnailID;
    }

    public int getTrailerUrlId() {
        return trailerUrlId;
    }

    public int getBookingUrlId() {
        return bookingUrlId;
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

    public ArrayList<String> getCategory() {
        return category;
    }
    public int getDuration() {
        return duration;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setThumbnailID(int thumbnailID) {
        this.thumbnailID = thumbnailID;
    }

    public void setTrailerUrlId(int trailerUrlId) {
        this.trailerUrlId = trailerUrlId;
    }

    public void setBookingUrlId(int bookingUrlId) {
        this.bookingUrlId = bookingUrlId;
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

    public void setCategory(ArrayList<String> category) {
        this.category = category;
    }

    // Static method to initiate dumpy data
    public static ArrayList<Movie> initMovie() {
        ArrayList<Movie> movieList = new ArrayList<>();

        return movieList;
    }

}
