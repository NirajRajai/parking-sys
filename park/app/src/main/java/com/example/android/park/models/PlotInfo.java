package com.example.android.park.models;

/**
 * Created by dell on 03-Dec-17.
 */

public class PlotInfo {
    private static String plotNo;
    private static String pin;

    public PlotInfo() {
    }



    public static String getPlotNo() {
        return plotNo;
    }

    public static void setPlotNo(String plotNo) {
        PlotInfo.plotNo = plotNo;
    }

    public static String getPin() {
        return pin;
    }

    public static void setPin(String pin) {
        PlotInfo.pin = pin;
    }




}
