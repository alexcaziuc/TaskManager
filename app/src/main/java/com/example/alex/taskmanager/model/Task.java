package com.example.alex.taskmanager.model;


public class Task {

    private long id;
    private String note;
    private String priority;
    private String tag;
    private String date;
    private int radio;

    public Task() {
    }

    public Task(String note, String priority, String tag) {
        this.note = note;
        this.priority = priority;
        this.tag = tag;

    }

    public Task(String note, String priority, String tag, String date) {
        this.note = note;
        this.priority = priority;
        this.tag = tag;
        this.date = date;
    }

    public Task(String note, String priority, String tag, String date, int radio) {
        this.note = note;
        this.priority = priority;
        this.tag = tag;
        this.date = date;
        this.radio =radio;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRadio() {
        return radio;
    }

    public void setRadio(int radio) {
        this.radio = radio;
    }
}
