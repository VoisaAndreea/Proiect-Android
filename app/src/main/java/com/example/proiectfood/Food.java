package com.example.proiectfood;

import java.util.ArrayList;

public class Food {
    private String title;
    private String img;
    private String instructions;

    public Food(String title, String img, String instructions) {
        this.title = title;
        this.img = img;
        this.instructions = instructions;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public String getImg() {
        return img;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
