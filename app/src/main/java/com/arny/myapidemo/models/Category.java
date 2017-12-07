package com.arny.myapidemo.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
@Entity(tableName = "category",foreignKeys = @ForeignKey(
        entity = Category.class,
        parentColumns = "_id",
        childColumns = "cat_id"))
public class Category {
    @PrimaryKey
    @ColumnInfo(name = "_id")
    private long id;
    private String cat_title;
    private long cat_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCat_title() {
        return cat_title;
    }

    public void setCat_title(String cat_title) {
        this.cat_title = cat_title;
    }

    public long getCat_id() {
        return cat_id;
    }

    public void setCat_id(long cat_id) {
        this.cat_id = cat_id;
    }
}
