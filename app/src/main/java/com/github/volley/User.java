package com.github.volley;

public class User {
    String name;
    String stats;
    String picture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public User(String name, String stats, String picture){
        this.name = name;
        this.stats = stats;
        this.picture = picture;
    }
}
