
package com.ayrines.models;

public class Task {
    private int userId;
    private int id;
    private String title;
    public boolean isComplete;

    public Task(int userId, int id, String title, boolean isComplete) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.isComplete = isComplete;
    }

    public Task() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }
        @Override 
        public String toString(){ 
            return String.format("Task #%d: %s [Status: %s], submitted bu user#%d", id, title, isComplete? "Completed": "To do", userId );
        }
    

}
