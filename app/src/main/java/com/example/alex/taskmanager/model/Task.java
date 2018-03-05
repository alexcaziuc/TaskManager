package com.example.alex.taskmanager.model;

public class Task {

    private long id;
    private String note;
    private int priority;
    private String tag;
    private String date;

    public Task() {
    }

    public Task(String note, int priority, String tag, String date) {
        this.note = note;
        this.priority = priority;
        this.tag = tag;
        this.date = date;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
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

}
