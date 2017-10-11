package com.arny.myapidemo.api;

import com.arny.arnylib.utils.Utility;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class AristosTransferResponse {

	@SerializedName("success")
	@Expose
	private Boolean success;
	@SerializedName("items")
	@Expose
	private List<Task> tasks = null;
	@SerializedName("users")
	@Expose
	private List<User> users = null;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return Utility.getFields(this);
	}
}
