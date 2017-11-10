/**
 * Created by hsx on 17-8-6.
 */
package com.example.hsx.data.models;

import android.graphics.Bitmap;

import java.util.Date;

public abstract class PrivMedia {

    private PrivMediaType type;
    private String dataBelongTo = null;
    private String picName;
    private String absolutePath = null;
    private int createdTimeStamp = 0;

    private int height = 0;
    private int width  = 0;
    private int size   = 0;


    public PrivMedia(PrivMediaType t) {
        switch (t) {
            case IMAGE:
                this.type = PrivMediaType.IMAGE;
                break;
            case VIDEO:
                this.type = PrivMediaType.VIDEO;
                break;
            case MUSIC:
                this.type = PrivMediaType.MUSIC;
                break;
            case DOC:
                this.type = PrivMediaType.DOC;
                break;
            default:
                this.type = PrivMediaType.NONE;
                break;
        }
    }

    /**
     * set picName
     * @param s pic name
     */
    public void setName(String s) {
        this.picName = s;
    }

    /**
     * get picName
     * @return picName
     */
    public String getName() {
        return this.picName;
    }


    public PrivMediaType getType() {
        return this.type;
    }

    public void setPath(String p) {
        this.absolutePath = p;
    }

    public String getPath() {
        return this.absolutePath;
    }

    public void setHeight(int h) {
        this.height = h;
    }

    public int getHeight() {
        return this.height;
    }


    public void setWidth (int w) {
        this.width = w;
    }

    public int getWidth() {
        return this.width;
    }

    public void setSize(int s) {
        this.size = s;
    }

    public int getSize() {
        return this.size;
    }

    public void setCreatedTimeStamp(int ts) {
        this.createdTimeStamp = ts;
    }

    public int getCreatedTimeStamp() {
        return this.createdTimeStamp;
    }

    public void setBelongTo(String t) {
        this.dataBelongTo = t;
    }

    public String getBelongTo() {
        return this.dataBelongTo;
    }

    public enum  PrivMediaType {
        IMAGE,
        VIDEO,
        MUSIC,
        DOC,

        NONE
    }
}
