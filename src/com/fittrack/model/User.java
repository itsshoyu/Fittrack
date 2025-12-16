package com.fittrack.model;

public class User {

    private int id;
    private String username;
    private int calorieTarget;

    public User(int id, String username, int calorieTarget) {
        this.id = id;
        this.username = username;
        this.calorieTarget = calorieTarget;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getCalorieTarget() {
        return calorieTarget;
    }

    public void setCalorieTarget(int calorieTarget) {
        this.calorieTarget = calorieTarget;
    }
}