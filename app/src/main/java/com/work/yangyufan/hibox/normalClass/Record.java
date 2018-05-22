package com.work.yangyufan.hibox.normalClass;

import android.content.Intent;

public class Record {

    private Intent pid;

    private String pname;

    private String about;

    private String label;

    private String position;

    public void setPid(Intent pid) {
        this.pid = pid;
    }

    public Intent getPid() {
        return pid;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPname() {
        return pname;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAbout() {
        return about;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}


