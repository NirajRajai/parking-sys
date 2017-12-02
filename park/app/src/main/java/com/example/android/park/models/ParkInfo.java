package com.example.android.park.models;

/**
 * Created by dell on 02-Dec-17.
 */

public class ParkInfo {

    private String parkId;
    private String parkCity;
    private String parkAddress;
    private String avail;
    private String total;
    private String lat;
    private String lon;

    public String getAvail() {
        return avail;
    }

    public void setAvail(String avail) {
        this.avail = avail;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getParkId() {
        return parkId;
    }

    public void setParkId(String parkId) {
        this.parkId = parkId;
    }

    public String getParkCity() {
        return parkCity;
    }

    public void setParkCity(String parkCity) {
        this.parkCity = parkCity;
    }

    public String getParkAddress() {
        return parkAddress;
    }

    public void setParkAddress(String parkAddress) {
        this.parkAddress = parkAddress;
    }


    @Override
    public String toString() {
        return "ParkInfo{" +
                "parkId='" + parkId + '\'' +
                ", parkCity='" + parkCity + '\'' +
                ", parkAddress='" + parkAddress + '\'' +
                ", avail='" + avail + '\'' +
                ", total='" + total + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                '}';
    }

}
