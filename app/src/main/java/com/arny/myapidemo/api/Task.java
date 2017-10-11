
package com.arny.myapidemo.api;

import java.util.List;

import com.arny.arnylib.utils.Utility;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Task {

    @SerializedName("project")
    @Expose
    private String project;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("entity_id")
    @Expose
    private String entityId;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("prepaid")
    @Expose
    private Integer prepaid;
    @SerializedName("prepaid_reason")
    @Expose
    private String prepaidReason;
    @SerializedName("total_due")
    @Expose
    private Integer totalDue;
    @SerializedName("grand_total")
    @Expose
    private Integer grandTotal;
    @SerializedName("shipping_method")
    @Expose
    private String shippingMethod;
    @SerializedName("shipping_description")
    @Expose
    private String shippingDescription;
    @SerializedName("shipping_amount")
    @Expose
    private Integer shippingAmount;
    @SerializedName("shipping_at")
    @Expose
    private String shippingAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("return_code")
    @Expose
    private Integer returnCode;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("sale_address")
    @Expose
    private String saleAddress;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("customer_comment")
    @Expose
    private String customerComment;
    @SerializedName("service_comment")
    @Expose
    private String serviceComment;
    @SerializedName("package_comment")
    @Expose
    private String packageComment;
    @SerializedName("items")
    @Expose
    private List<TaskItem> items = null;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("packaging")
    @Expose
    private Packaging packaging;

	@Override
	public String toString() {
		return Utility.getFields(this);
	}

	public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getPrepaid() {
        return prepaid;
    }

    public void setPrepaid(Integer prepaid) {
        this.prepaid = prepaid;
    }

    public String getPrepaidReason() {
        return prepaidReason;
    }

    public void setPrepaidReason(String prepaidReason) {
        this.prepaidReason = prepaidReason;
    }

    public Integer getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(Integer totalDue) {
        this.totalDue = totalDue;
    }

    public Integer getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Integer grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getShippingDescription() {
        return shippingDescription;
    }

    public void setShippingDescription(String shippingDescription) {
        this.shippingDescription = shippingDescription;
    }

    public Integer getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(Integer shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public String getShippingAt() {
        return shippingAt;
    }

    public void setShippingAt(String shippingAt) {
        this.shippingAt = shippingAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSaleAddress() {
        return saleAddress;
    }

    public void setSaleAddress(String saleAddress) {
        this.saleAddress = saleAddress;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCustomerComment() {
        return customerComment;
    }

    public void setCustomerComment(String customerComment) {
        this.customerComment = customerComment;
    }

    public String getServiceComment() {
        return serviceComment;
    }

    public void setServiceComment(String serviceComment) {
        this.serviceComment = serviceComment;
    }

    public String getPackageComment() {
        return packageComment;
    }

    public void setPackageComment(String packageComment) {
        this.packageComment = packageComment;
    }

    public List<TaskItem> getItems() {
        return items;
    }

    public void setItems(List<TaskItem> items) {
        this.items = items;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Packaging getPackaging() {
        return packaging;
    }

    public void setPackaging(Packaging packaging) {
        this.packaging = packaging;
    }

}
