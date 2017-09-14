package com.arny.myapidemo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class GoodItem {
	@SerializedName("price")
	@Expose
	private double price;
	@SerializedName("imgUrl")
	@Expose
	private String imgUrl;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("_id")
	@Expose
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
}
