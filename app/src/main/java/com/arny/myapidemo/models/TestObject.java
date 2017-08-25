package com.arny.myapidemo.models;

import android.database.Cursor;
import com.arny.arnylib.database.DBProvider;
import com.arny.arnylib.utils.Utility;
import org.chalup.microorm.MicroOrm;
import org.chalup.microorm.annotations.Column;

import java.io.Serializable;
import java.util.ArrayList;


public class TestObject implements Serializable {
	@Column("id")
	private String id;
    private long dbID;
	@Column("title")
    private String name;
    private ArrayList<GoodItem> goodItems;

	public TestObject() {
	}

	public TestObject(String id,String name) {
		this.id = id;
		this.name = name;
	}

	public long getDbID() {
        return dbID;
    }

    public void setDbID(long dbID) {
        this.dbID = dbID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestObject getFromCursor(Cursor c){
	    MicroOrm uOrm = new MicroOrm();
	    return uOrm.fromCursor(c, this.getClass());
    }

    @Override
    public String toString() {
        return DBProvider.getColumns(this);
    }

    public ArrayList<GoodItem> getGoodItems() {
        return goodItems;
    }

    public void setGoodItems(ArrayList<GoodItem> goodItems) {
        this.goodItems = goodItems;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
