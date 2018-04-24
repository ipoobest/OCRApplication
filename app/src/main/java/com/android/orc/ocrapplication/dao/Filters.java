package com.android.orc.ocrapplication.dao;

import android.os.Parcel;
import android.os.Parcelable;

public class Filters implements Parcelable {
    String filter;
    String sort;
    int limit;

    public Filters(){}

    protected Filters(Parcel in) {
        filter = in.readString();
        sort = in.readString();
        limit = in.readInt();
    }

    public static final Creator<Filters> CREATOR = new Creator<Filters>() {
        @Override
        public Filters createFromParcel(Parcel in) {
            return new Filters(in);
        }

        @Override
        public Filters[] newArray(int size) {
            return new Filters[size];
        }
    };

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(filter);
        dest.writeString(sort);
        dest.writeInt(limit);
    }
}
