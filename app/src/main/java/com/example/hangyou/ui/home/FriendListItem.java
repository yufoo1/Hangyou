package com.example.hangyou.ui.home;

public class FriendListItem {
    private String name;
    private String description;
    private String src;

    public FriendListItem(String name, String description, String src) {
        this.name = name;
        this.description = description;
        this.src = src;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
