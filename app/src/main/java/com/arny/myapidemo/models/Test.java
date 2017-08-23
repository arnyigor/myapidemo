
package com.arny.myapidemo.models;

import com.arny.arnylib.database.DBProvider;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Test {

    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("guid")
    @Expose
    private String guid;
    @SerializedName("index")
    @Expose
    private Integer index;
    @SerializedName("_id")
    @Expose
    private String id;

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return DBProvider.getColumns(this);
    }
}