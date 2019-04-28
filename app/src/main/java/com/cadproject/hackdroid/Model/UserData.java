package com.cadproject.hackdroid.Model;

public class UserData {
    /*
    *  wishlist.put("user" , currentUser);
                wishlist.put("title" , planTitle);
                wishlist.put("image" , PlanImage);
                wishlist.put("dimen" ,PlanDimen);
                wishlist.put("size" , planPlotSize);
                wishlist.put("id" , planId);
    * */

    public UserData() {
    }

    String user , title , image , dimen , size , id;

    public UserData(String user, String title, String image, String dimen, String size, String id) {
        this.user = user;
        this.title = title;
        this.image = image;
        this.dimen = dimen;
        this.size = size;
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDimen() {
        return dimen;
    }

    public void setDimen(String dimen) {
        this.dimen = dimen;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
