package com.arny.myapidemo.models;

import com.arny.arnylib.utils.Utility;
public class Option {
    private String parent;
    private long size;
    private boolean isDirectory;
    private String name;
    private String path;

    public Option(String name, String path, long size, boolean isDirectory, String parent) {
        this.name = name;
        this.path = path;
        this.size = size;
        this.isDirectory = isDirectory;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return Utility.getFields(this);
    }
}
