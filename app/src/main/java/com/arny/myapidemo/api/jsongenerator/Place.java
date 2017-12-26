package com.arny.myapidemo.api.jsongenerator;

import android.arch.persistence.room.*;
import com.arny.arnylib.utils.Utility;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity(tableName = "places", indices = {@Index(value = "title", unique = true)})
public class Place {
    @SerializedName("_id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private long id;
    @SerializedName("longitude")
    @Expose
    @Ignore
    private double longitude;
    @SerializedName("latitude")
    @Expose
    @Ignore
    private double latitude;
    @SerializedName("imgUrl")
    @Expose
    @Ignore
    private String imgUrl;
    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "title")
    private String name;

    @Override
    public String toString() {
        return Utility.getFields(this);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
