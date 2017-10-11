
package com.arny.myapidemo.api;

import com.arny.arnylib.utils.Utility;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TaskItem {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("order_item_id")
    @Expose
    private String orderItemId;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("row_total")
    @Expose
    private Double rowTotal;
    @SerializedName("discount")
    @Expose
    private Double discount;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("qty_check_sell")
    @Expose
    private Integer qtyCheckSell;
    @SerializedName("qty_check_return")
    @Expose
    private Integer qtyCheckReturn;
    @SerializedName("check_pay_type")
    @Expose
    private Integer checkPayType;
    @SerializedName("nds")
    @Expose
    private Integer nds;
    @SerializedName("image")
    @Expose
    private String image;

	@Override
	public String toString() {
		return Utility.getFields(this);
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRowTotal() {
        return rowTotal;
    }

    public void setRowTotal(Double rowTotal) {
        this.rowTotal = rowTotal;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Integer getQtyCheckSell() {
        return qtyCheckSell;
    }

    public void setQtyCheckSell(Integer qtyCheckSell) {
        this.qtyCheckSell = qtyCheckSell;
    }

    public Integer getQtyCheckReturn() {
        return qtyCheckReturn;
    }

    public void setQtyCheckReturn(Integer qtyCheckReturn) {
        this.qtyCheckReturn = qtyCheckReturn;
    }

    public Integer getCheckPayType() {
        return checkPayType;
    }

    public void setCheckPayType(Integer checkPayType) {
        this.checkPayType = checkPayType;
    }

    public Integer getNds() {
        return nds;
    }

    public void setNds(Integer nds) {
        this.nds = nds;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
