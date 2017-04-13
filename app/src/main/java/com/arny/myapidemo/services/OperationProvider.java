package com.arny.myapidemo.services;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.HashMap;

public class OperationProvider implements Parcelable {
    private int id,type;
    private boolean finished, success;
    private HashMap<String, Object> operationData;
    private String result;

    public OperationProvider(Bundle extras) {
        Log.i(OperationProvider.class.getSimpleName(), "OperationProvider: extras EXTRA_KEY_OPERATION_ID = " + extras.getInt(Operations.EXTRA_KEY_OPERATION_ID));
        Log.i(OperationProvider.class.getSimpleName(), "OperationProvider: extras EXTRA_KEY_OPERATION_FINISH = " + extras.getBoolean(Operations.EXTRA_KEY_OPERATION_FINISH));
        Log.i(OperationProvider.class.getSimpleName(), "OperationProvider: extras EXTRA_KEY_OPERATION_FINISH_SUCCESS = " + extras.getBoolean(Operations.EXTRA_KEY_OPERATION_FINISH_SUCCESS));
        Log.i(OperationProvider.class.getSimpleName(), "OperationProvider: extras EXTRA_KEY_OPERATION_DATA = " + extras.getParcelable(Operations.EXTRA_KEY_OPERATION_DATA));
        Log.i(OperationProvider.class.getSimpleName(), "OperationProvider: extras EXTRA_KEY_OPERATION_RESULT = " + extras.getString(Operations.EXTRA_KEY_OPERATION_RESULT));
        this.id = extras.getInt(Operations.EXTRA_KEY_OPERATION_ID);
        this.finished = extras.getBoolean(Operations.EXTRA_KEY_OPERATION_FINISH);
        this.success = extras.getBoolean(Operations.EXTRA_KEY_OPERATION_FINISH_SUCCESS);
//        this.operationData = extras.getParcelable(Operations.EXTRA_KEY_OPERATION_DATA);
        this.result = extras.getString(Operations.EXTRA_KEY_OPERATION_RESULT);
    }

    public OperationProvider(int id, int type, HashMap<String, Object> operationData) {
        this.id = id;
        this.operationData = operationData;
        this.type = type;
    }

    protected OperationProvider(Parcel in) {
        id = in.readInt();
        type = in.readInt();
        finished = in.readByte() != 0;
        success = in.readByte() != 0;
        result = in.readString();
        operationData = (HashMap<String, Object>) in.readSerializable();
    }

    public static final Creator<OperationProvider> CREATOR = new Creator<OperationProvider>() {
        @Override
        public OperationProvider createFromParcel(Parcel in) {
            return new OperationProvider(in);
        }

        @Override
        public OperationProvider[] newArray(int size) {
            return new OperationProvider[size];
        }
    };

    public void setOperationData(HashMap<String, Object> operationData) {
        this.operationData = operationData;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getId() {
        return id;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isSuccess() {
        return success;
    }

    public HashMap<String, Object> getOperationData() {
        return operationData;
    }

    public String getResult() {
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(type);
        dest.writeByte((byte) (finished ? 1 : 0));
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(result);
        dest.writeSerializable(this.operationData);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}