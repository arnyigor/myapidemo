package com.arny.myapidemo.models;

import com.arny.arnylib.database.DBProvider;
import org.chalup.microorm.annotations.Column;

import java.io.Serializable;
import java.util.ArrayList;


public class TestObject implements Serializable {
    @Column("id")
    private String id;
    private long dbId;
    @Column("title")
    private String name;

    private ArrayList<GoodItem> goodItems;

	public TestObject(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return DBProvider.getColumns(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDbId() {
        return dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
