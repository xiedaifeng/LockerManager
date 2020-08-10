package com.example.http_lib.response;

public class NoticeBean {


    /**
     * id : 1
     * enabled : 1
     * addtime : 1588442433
     * title : 谭小二app上线了
     * displayorder : 2
     */

    private String id;
    private String enabled;
    private String addtime;
    private String title;
    private String displayorder;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(String displayorder) {
        this.displayorder = displayorder;
    }
}
