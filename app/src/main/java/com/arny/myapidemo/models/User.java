
package com.arny.myapidemo.models;

import android.arch.persistence.room.*;
import com.arny.arnylib.utils.Utility;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "users",indices = {@Index(value = "login",unique = true)} )
public class User {
    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id",index = true)
    private long id;
    @SerializedName("login")
    @Expose
    @ColumnInfo(name = "login")
    private String login;
    @SerializedName("name")
    @Expose
    private String name;
    private boolean admin;
    private String email;
    private String token;
    private String avatar;

    public User(String login, String name) {
        this.login = login;
        this.name = name;
    }

    @Ignore
    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return Utility.getFields(this);
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
