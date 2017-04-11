package com.example.seecloud.database;

/**
 * Created by monkeysmac on 2017/4/2.
 */

public class Attribute {
    private String name;
    private String instruction;
    private int imageId;

    private double number;

    public Attribute(String name, int ImageId) {
        this.name = name;
        this.imageId = ImageId;
    }

    public Attribute(String name, double number) {
        this.name = name;
        this.number = number;
    }

    public Attribute(String name, String instruction) {
        this.name = name;
        this.instruction = instruction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }
}
