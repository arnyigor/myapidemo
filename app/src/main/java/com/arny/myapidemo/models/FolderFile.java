package com.arny.myapidemo.models;

import com.arny.arnylib.files.MediaFileInfo;
import com.arny.arnylib.utils.Utility;
import org.chalup.microorm.annotations.Column;
public class FolderFile extends MediaFileInfo {
	@Column("_id")
	private long id;
	@Column("size")
	private long size;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof FolderFile) {
			FolderFile o = (FolderFile) obj;
			return o.getFileName().equalsIgnoreCase(getFileName()) && o.getFilePath().equalsIgnoreCase(getFilePath());
		}else{
			return false;
		}
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return Utility.getFields(this);
	}
}
