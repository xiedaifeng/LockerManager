package com.yidao.module_lib.entity;

public class LocalMusicBean {

    private long duration;
    private String name;
    private String path;


    public LocalMusicBean( String name, String path, long duration) {
        this.duration = duration;
        this.name = name;
        this.path = path;
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

    @Override
    public String toString() {
        return "LocalVideoBean{" +
                ", duration=" + duration +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
