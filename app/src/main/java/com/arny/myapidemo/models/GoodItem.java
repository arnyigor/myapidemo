package com.arny.myapidemo.models;

import android.arch.persistence.room.*;
import com.arny.arnylib.utils.Utility;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity(tableName = "gooditem",foreignKeys = @ForeignKey(
        entity = Category.class,
        parentColumns = "_id",
        childColumns = "parentId"))
public class GoodItem {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private long dbID;
    @ColumnInfo(index = true)
    private long parentId;
    @SerializedName("price")
	@Expose
	private double price;
	@SerializedName("image")
	@Expose
    @Ignore
	private String image;
	@SerializedName("name")
	@Expose
    @ColumnInfo(name = "good_title")
	private String title;
	@SerializedName("_id")
	@Expose
    @Ignore
	private String id;
    private String description;

	public GoodItem(double price, String name) {
		this.price = price;
		this.title = name;
	}

	public GoodItem() {
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return Utility.getFields(this);
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getDbID() {
        return dbID;
    }

    public void setDbID(long dbID) {
        this.dbID = dbID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
