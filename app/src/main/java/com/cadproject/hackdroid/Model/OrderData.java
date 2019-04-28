package com.cadproject.hackdroid.Model;

public class OrderData {
/*

email:
"mdkhalidrazakhan@gmail.com"
height:
"30"
mobile:
"10"
requirement:
"requirements 1\nhdjdk"
user:
"+916204304229"
width:
"30"

 */
String email, height , mobile ,requirement , user  ,  width;

    public OrderData() {

    }

    public OrderData(String email, String height, String mobile, String requirement, String user, String width) {
        this.email = email;
        this.height = height;
        this.mobile = mobile;
        this.requirement = requirement;
        this.user = user;
        this.width = width;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
