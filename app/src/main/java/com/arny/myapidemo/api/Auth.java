
package com.arny.myapidemo.api;

import com.arny.arnylib.database.DBProvider;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Auth {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("session")
    @Expose
    private String session;
    @SerializedName("entity_id")
    @Expose
    private Integer entityId;
    @SerializedName("expire_at")
    @Expose
    private String expireAt;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("profile")
    @Expose
    private Profile profile;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public String getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(String expireAt) {
        this.expireAt = expireAt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

	@Override
	public String toString() {
		return DBProvider.getColumns(this);
	}
}
