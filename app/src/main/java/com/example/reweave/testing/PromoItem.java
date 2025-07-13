package com.example.reweave.testing;

public class PromoItem {
    private int id;
    private String title;
    private String points;
    private int imageResource;

    public PromoItem(int id, String title, String points, int imageResource) {
        this.id = id;
        this.title = title;
        this.points = points;
        this.imageResource = imageResource;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoints() {
        return points;
    }

    public int getImageResource() {
        return imageResource;
    }
} 