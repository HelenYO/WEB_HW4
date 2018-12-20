package ru.itmo.webmail.model.domain;

import java.io.Serializable;

public class News implements Serializable {
    private long id;
    private String text;

    public News(String text) {
        this.text = text;
    }
    public News(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
