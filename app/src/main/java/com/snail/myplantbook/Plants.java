package com.snail.myplantbook;

import android.net.Uri;

public class Plants {

    private int _id;
    private String _plantname;
    private String _plantinfo;
    private String _plantimage;

    public Plants() {

    }

    public Plants(String plantName, String plantInfo) {

        this._plantname = plantName;
        this._plantinfo = plantInfo;

    }

    public Plants(String plantName, String plantInfo, String plantImage) {

        this._plantname = plantName;
        this._plantinfo = plantInfo;
        this._plantimage = plantImage;

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id_) {
        this._id = _id_;
    }

    public String get_plantname() {
        return _plantname;
    }

    public void set_plantname(String _plant_name) {
        this._plantname = _plant_name;
    }

    public String get_plantinfo() {
        return _plantinfo;
    }

    public void set_plantinfo(String _plant_info) {
        this._plantinfo = _plant_info;
    }

    public String get_plantimage() {
        return _plantimage;
    }

    public void set_plantimage(String _plant_image) {
        this._plantimage = _plant_image;
    }

}
