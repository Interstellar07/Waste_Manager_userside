package com.zaidisam.moneymanager.activities;

import android.location.Location;

import com.airbnb.lottie.L;

import java.util.Date;

public class Data {

    String wastetype, time, data, id, location, wastenature, date,status,imgurl,S0;
    int amountwaste;
    int s1,s2,s3;

    public Data() {

    }


    public Data(String wastetype, String wastenature, String time, String data, String id, String location, int amountwaste,String status,String imgurl,int s1,int s2, int s3,String S0) {
        this.wastetype = wastetype;
        this.wastenature = wastenature;
        this.time = time;
        this.data = data;
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
       this.S0 = S0;
        this.id = id;
        this.location = location;
        this.amountwaste = amountwaste;
        this.date = date;
        this.status = status;
        this.imgurl = imgurl;
    }

    public String getWastetype() {
        return wastetype;
    }

    public void setWastetype(String wastetype) {
        this.wastetype = wastetype;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWastenature() {
        return wastenature;
    }

    public void setWastenature(String wastenature) {
        this.wastenature = wastenature;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getS0() {
        return S0;
    }

    public void setS0(String s0) {
        S0 = s0;
    }

    public int getAmountwaste() {
        return amountwaste;
    }

    public void setAmountwaste(int amountwaste) {
        this.amountwaste = amountwaste;
    }

    public int getS1() {
        return s1;
    }

    public void setS1(int s1) {
        this.s1 = s1;
    }

    public int getS2() {
        return s2;
    }

    public void setS2(int s2) {
        this.s2 = s2;
    }

    public int getS3() {
        return s3;
    }

    public void setS3(int s3) {
        this.s3 = s3;
    }
}
