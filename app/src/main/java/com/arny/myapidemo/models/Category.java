package com.arny.myapidemo.models;

import android.arch.persistence.room.*;
import com.arny.arnylib.utils.Utility;
@Entity(tableName = "category",foreignKeys = @ForeignKey(
        entity = Category.class,
        parentColumns = "_id",
        childColumns = "parentId"))
public class Category {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private long id;
    @ColumnInfo(name = "title")
    private String title;
    private String image;
    private String description;
    @ColumnInfo(index = true)
    private long parentId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return Utility.getFields(this);
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
