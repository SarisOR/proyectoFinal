package com.example.marvelstudios.clases;

import java.util.List;

public class Superhero {
    private String name;
    private String id;
    private String image;
    private String desc;

    public Superhero(String name, String id, String image, String desc) {
        this.name = name;
        this.id = id;
        this.image = image;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String des) {
        this.desc = desc;
    }
}

