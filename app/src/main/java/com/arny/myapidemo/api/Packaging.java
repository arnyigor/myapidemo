
package com.arny.myapidemo.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Packaging {

    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("hub_id")
    @Expose
    private String hubId;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHubId() {
        return hubId;
    }

    public void setHubId(String hubId) {
        this.hubId = hubId;
    }

}
