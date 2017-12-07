package com.arny.myapidemo.models;

import android.arch.persistence.room.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity(tableName = "gooditem",foreignKeys = @ForeignKey(
        entity = Category.class,
        parentColumns = "_id",
        childColumns = "cat_id"))
public class GoodItem {
    @PrimaryKey
    @ColumnInfo(name = "_id")
    private long dbID;
    private long cat_id;
    @SerializedName("price")
	@Expose
    @Ignore
	private double price;
	@SerializedName("imgUrl")
	@Expose
    @Ignore
	private String imgUrl;
	@SerializedName("name")
	@Expose
    @ColumnInfo(name = "good_title")
	private String name;
	@SerializedName("_id")
	@Expose
    @Ignore
	private String id;

	public GoodItem(double price, String name, String id) {
		this.price = price;
		this.name = name;
		this.id = id;
	}

	public GoodItem() {
	}

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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "\nid:" + getId() + " name:" + getName()  + " imgUrl:" + getImgUrl() + " price:" + getPrice();
    }

    public long getCat_id() {
        return cat_id;
    }

    public void setCat_id(long cat_id) {
        this.cat_id = cat_id;
    }

    public long getDbID() {
        return dbID;
    }

    public void setDbID(long dbID) {
        this.dbID = dbID;
    }
}
