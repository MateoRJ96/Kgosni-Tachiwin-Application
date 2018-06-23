package com.android.kt.entity;

public class Word {
    private String spanish;
    private String tutunaku;
    private String url;

    public Word() {
    }

    public Word(String spanish, String tutunaku, String url) {
        this.spanish = spanish;
        this.tutunaku = tutunaku;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSpanish() {
        return spanish;
    }

    public void setSpanish(String spanish) {
        this.spanish = spanish;
    }

    public String getTutunaku() {
        return tutunaku;
    }

    public void setTutunaku(String tutunaku) {
        this.tutunaku = tutunaku;
    }
}
