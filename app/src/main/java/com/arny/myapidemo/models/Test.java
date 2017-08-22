
package com.arny.myapidemo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Test {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("index")
    @Expose
    private Integer index;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "id:" + id + " index: "  + index;
    }
}
