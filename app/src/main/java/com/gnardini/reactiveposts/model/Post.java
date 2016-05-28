package com.gnardini.reactiveposts.model;

public class Post {

    private String owner;
    private String text;

    public Post() {
    }

    public Post(String owner, String text) {
        this.owner = owner;
        this.text = text;
    }

    public String getOwner() {
        return owner;
    }

    public String getText() {
        return text;
    }

}
