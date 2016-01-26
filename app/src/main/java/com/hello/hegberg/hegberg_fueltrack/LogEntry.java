package com.hello.hegberg.hegberg_fueltrack;

import java.util.ArrayList;



/**
 * Created by Chris on 1/23/2016.
 */
public class LogEntry {
    private String date;
    private String station;
    private Double odometer;
    private String fuelGrade;
    private Double fuelAmount;
    private Double fuelUnitCost;
    private Double fuelCost;
    private ArrayList<LogEntry> currentEntries = new ArrayList<>();
    private static final String FILENAME = "file.sav";

    public LogEntry (String date, String station, Double odometer, String fuelGrade, Double fuelAmount, Double fuelUnitCost, Double fuelCost) {
        this.date = date;
        this.station = station;
        this.odometer = odometer;
        this.fuelGrade = fuelGrade;
        this.fuelAmount = fuelAmount;
        this.fuelUnitCost = fuelUnitCost;
        this.fuelCost = fuelCost;
    }

    public void setAll(String date, String station, Double odometer, String fuelGrade, Double fuelAmount, Double fuelUnitCost, Double fuelCost){
        this.date = date;
        this.station = station;
        this.odometer = odometer;
        this.fuelGrade = fuelGrade;
        this.fuelAmount = fuelAmount;
        this.fuelUnitCost = fuelUnitCost;
        this.fuelCost = fuelCost;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Double getOdometer() {
        return odometer;
    }

    public void setOdometer(Double odometer) {
        this.odometer = odometer;
    }

    public Double getFuelAmount() {
        return fuelAmount;
    }

    public void setFuelAmount(Double fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

    public String getFuelGrade() {
        return fuelGrade;
    }

    public void setFuelGrade(String fuelGrade) {
        this.fuelGrade = fuelGrade;
    }

    public Double getFuelUnitCost() {
        return fuelUnitCost;
    }

    public void setFuelUnitCost(Double fuelUnitCost) {
        this.fuelUnitCost = fuelUnitCost;
    }

    public Double getFuelCost() {
        return fuelCost;
    }

    public void setFuelCost(Double fuelCost) {
        this.fuelCost = fuelCost;
    }
}
