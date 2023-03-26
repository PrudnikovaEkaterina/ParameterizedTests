package ru.prudnikova.testData;

import java.util.Map;

public class BuildingData {
    private int id;
    private int parent_id;
    private Map<String,String> titles;
    private  String description;
    private String release_date;
    private String wall_material;
    private int rating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public Map<String, String> getTitles() {
        return titles;
    }

    public void setTitles(Map<String, String> titles) {
        this.titles = titles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
    public String getWall_material() {
        return wall_material;
    }

    public void setWall_material(String wall_material) {
        this.wall_material = wall_material;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
