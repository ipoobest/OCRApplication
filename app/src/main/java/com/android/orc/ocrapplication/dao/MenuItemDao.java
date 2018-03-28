package com.android.orc.ocrapplication.dao;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by j.poobest on 21/3/2018 AD.
 */


public class MenuItemDao implements Parcelable{

    //เผื่ออนาคตต้องสร้าง dao เปล่าๆ
    public MenuItemDao() {

    }

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nameThai")
    @Expose
    private String nameThai;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("ingredient")
    @Expose
    private String ingredient;
    @SerializedName("imgUrl")
    @Expose
    private String imgUrl;
    @SerializedName("__v")
    @Expose
    private Integer v;

    protected MenuItemDao(Parcel in) {
        id = in.readString();
        name = in.readString();
        nameThai = in.readString();
        description = in.readString();
        ingredient = in.readString();
        imgUrl = in.readString();
        if (in.readByte() == 0) {
            v = null;
        } else {
            v = in.readInt();
        }
    }

    public static final Creator<MenuItemDao> CREATOR = new Creator<MenuItemDao>() {
        @Override
        public MenuItemDao createFromParcel(Parcel in) {
            return new MenuItemDao(in);
        }

        @Override
        public MenuItemDao[] newArray(int size) {
            return new MenuItemDao[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameThai() {
        return nameThai;
    }

    public void setNameThai(String nameThai) {
        this.nameThai = nameThai;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(nameThai);
        dest.writeString(description);
        dest.writeString(ingredient);
        dest.writeString(imgUrl);
        if (v == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(v);
        }
    }
}
