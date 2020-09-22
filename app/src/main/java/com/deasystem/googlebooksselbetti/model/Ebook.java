package com.deasystem.googlebooksselbetti.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Ebook implements Serializable {

    private String mId;
    private String mTitle;
    private String mDescription;
    private String foto;

    public Ebook() {

    }

    public Ebook(String mTitle, String mDescription) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
    }

    public Ebook(String mId, String mTitle, String mDescription, String foto) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.foto = foto;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Ebook{" +
                "mTitle='" + mTitle + '\'' +
                ", mDescription='" + mDescription + '\'' +
                '}';
    }

}
