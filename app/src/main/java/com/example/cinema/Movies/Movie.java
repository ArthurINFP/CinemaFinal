package com.example.cinema.Movies;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie implements Serializable, Parcelable {
    int id;
    int duration;
//    Drawable thumbnail;
    String title;
    String description;
    String category;
    String trailerUrl;
    String bookingUrl;
    String releaseDate;
    float ticketPrice;
    float rating;



    ArrayList<Comment> comments;
    boolean favorite;

    String base64Image;

    // Constructor
    public Movie(int id, String base64Image, String trailerUrlId, String bookingUrlId
            , String title, String description, float ticketPrice, float rating
            , ArrayList<Comment> comments, String category, int duration, String releaseDate, boolean favorite) {
        this.id = id;
        this.base64Image = base64Image;
        this.duration = duration;
        this.trailerUrl = trailerUrlId;
        this.bookingUrl = bookingUrlId;
        this.title = title;
        this.description = description;
        this.ticketPrice = ticketPrice;
        this.rating = rating;
        this.comments = comments;
        this.category = category;
        this.releaseDate = releaseDate;
        this.favorite = favorite;
    }
    // This no argument constructor is required for retrieving data from firebase
    public Movie() {
    }

    // All setter and getter
    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.duration);
        dest.writeString(this.base64Image);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.category);
        dest.writeString(this.trailerUrl);
        dest.writeString(this.bookingUrl);
        dest.writeString(this.releaseDate);
        dest.writeFloat(this.ticketPrice);
        dest.writeFloat(this.rating);
        dest.writeList(this.comments);
        dest.writeByte(this.favorite ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.duration = source.readInt();
        this.base64Image = source.readString();
        this.title = source.readString();
        this.description = source.readString();
        this.category = source.readString();
        this.trailerUrl = source.readString();
        this.bookingUrl = source.readString();
        this.releaseDate = source.readString();
        this.ticketPrice = source.readFloat();
        this.rating = source.readFloat();
        this.comments = new ArrayList<Comment>();
        source.readList(this.comments, Comment.class.getClassLoader());
        this.favorite = source.readByte() != 0;
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.duration = in.readInt();
        this.base64Image = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.category = in.readString();
        this.trailerUrl = in.readString();
        this.bookingUrl = in.readString();
        this.releaseDate = in.readString();
        this.ticketPrice = in.readFloat();
        this.rating = in.readFloat();
        this.comments = new ArrayList<Comment>();
        in.readList(this.comments, Comment.class.getClassLoader());
        this.favorite = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
