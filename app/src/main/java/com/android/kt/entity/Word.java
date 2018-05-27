package com.android.kt.entity;

public class Word {
    private String linked;
    private String spanish;
    private String tutunaku;

    public Word() {
    }

    public Word(String spanish, String tutunaku) {
        this.spanish = spanish;
        this.tutunaku = tutunaku;
    }

    public Word(String linked, String spanish, String tutunaku) {
        this.linked = linked;
        this.spanish = spanish;
        this.tutunaku = tutunaku;
    }

    public String getLinked() {
        return linked;
    }

    public void setLinked(String linked) {
        this.linked = linked;
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
