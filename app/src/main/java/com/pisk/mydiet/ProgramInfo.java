package com.pisk.mydiet;

import android.widget.TextView;

public class ProgramInfo {

    String lName ;
    String lImage;
    String lColor;
    String lColorDay;
    String lLightColor;

    String lDescription;
    int countDays;
    int countMeal;

    public ProgramInfo(String lName, String lImage,
            String lColor,
            String lColorDay,
            String lLightColor,
            String lDescription,
            int countDays,
            int countMeal) {

        this.lName = lName;
        this.lColor = lColor;
        this.lImage = lImage;
        this.lColorDay = lColorDay;
        this.lLightColor = lLightColor;
        this.lDescription = lDescription;
        this.countDays = countDays;
        this.countMeal = countMeal;

    }

    public ProgramInfo() {


    }

}
