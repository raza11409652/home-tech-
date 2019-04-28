package com.cadproject.hackdroid.Model;

public class PlanData {
    /*
    * PlanDimen:
"6000"
PlanFeatured:
"false"
PlanFloor:
"10"
PlanImage:
"https://firebasestorage.googleapis.com/v0/b/cad..."
PlanImageSec:
"https://firebasestorage.googleapis.com/v0/b/cad..."
PlanImageThird:
"https://firebasestorage.googleapis.com/v0/b/cad..."
PlanPlotSize:
"10 x 10"
PlanTitle:
    * */

   private String PlanDimen , PlanFeatured , PlanFloor ,PlanImage , PlanImageSec,PlanImageThird ,PlanPlotSize ,PlanTitle;

    public PlanData() {
    }

    public PlanData(String planDimen, String planFeatured, String planFloor, String planImage, String planImageSec, String planImageThird, String planPlotSize, String planTitle) {
        PlanDimen = planDimen;
        PlanFeatured = planFeatured;
        PlanFloor = planFloor;
        PlanImage = planImage;
        PlanImageSec = planImageSec;
        PlanImageThird = planImageThird;
        PlanPlotSize = planPlotSize;
        PlanTitle = planTitle;
    }

    public String getPlanDimen() {
        return PlanDimen;
    }

    public void setPlanDimen(String planDimen) {
        PlanDimen = planDimen;
    }

    public String getPlanFeatured() {
        return PlanFeatured;
    }

    public void setPlanFeatured(String planFeatured) {
        PlanFeatured = planFeatured;
    }

    public String getPlanFloor() {
        return PlanFloor;
    }

    public void setPlanFloor(String planFloor) {
        PlanFloor = planFloor;
    }

    public String getPlanImage() {
        return PlanImage;
    }

    public void setPlanImage(String planImage) {
        PlanImage = planImage;
    }

    public String getPlanImageSec() {
        return PlanImageSec;
    }

    public void setPlanImageSec(String planImageSec) {
        PlanImageSec = planImageSec;
    }

    public String getPlanImageThird() {
        return PlanImageThird;
    }

    public void setPlanImageThird(String planImageThird) {
        PlanImageThird = planImageThird;
    }

    public String getPlanPlotSize() {
        return PlanPlotSize;
    }

    public void setPlanPlotSize(String planPlotSize) {
        PlanPlotSize = planPlotSize;
    }

    public String getPlanTitle() {
        return PlanTitle;
    }

    public void setPlanTitle(String planTitle) {
        PlanTitle = planTitle;
    }
}
