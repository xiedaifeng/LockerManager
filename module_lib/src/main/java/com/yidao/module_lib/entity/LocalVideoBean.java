package com.yidao.module_lib.entity;

public class LocalVideoBean {

    private int id;
    private long duration;
    private String name;
    private String path;
    private String thumbPath;

    public LocalVideoBean() {
    }

    public LocalVideoBean(int id, String name, String path, long duration) {
        this.id = id;
        this.duration = duration;
        this.name = name;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    @Override
    public String toString() {
        return "LocalVideoBean{" +
                "id=" + id +
                ", duration=" + duration +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", thumbPath='" + thumbPath + '\'' +
                '}';
    }
}
