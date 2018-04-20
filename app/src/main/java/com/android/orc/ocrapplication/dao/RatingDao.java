package com.android.orc.ocrapplication.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class RatingDao implements Parcelable {

    private String userName;
    private String comment;
    private Double rating;
    private Date dateTime;



    public RatingDao(){}

    public RatingDao(String userName, String comment, Double rating, Date dateTime) {
        this.userName = userName;
        this.comment = comment;
        this.rating = rating;
        this.dateTime = dateTime;
    }

    protected RatingDao(Parcel in) {
        userName = in.readString();
        comment = in.readString();
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readDouble();
        }
    }

    public static final Creator<RatingDao> CREATOR = new Creator<RatingDao>() {
        @Override
        public RatingDao createFromParcel(Parcel in) {
            return new RatingDao(in);
        }

        @Override
        public RatingDao[] newArray(int size) {
            return new RatingDao[size];
        }
    };

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(comment);
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rating);
        }
    }
}
