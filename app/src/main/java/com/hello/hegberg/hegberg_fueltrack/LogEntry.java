package com.hello.hegberg.hegberg_fueltrack;

import java.util.ArrayList;

public class LogEntry {
    //initialize variables
    private String date;
    private String station;
    private Double odometer;
    private String fuelGrade;
    private Double fuelAmount;
    private Double fuelUnitCost;
    private Double fuelCost;
    private ArrayList<LogEntry> currentEntries = new ArrayList<>();
    private static final String FILENAME = "file.sav";

    //constructor for a Log Entry, gets all information and saves it in that instance of the class
    public LogEntry (String date, String station, Double odometer, String fuelGrade, Double fuelAmount, Double fuelUnitCost, Double fuelCost) {
        this.date = date;
        this.station = station;
        this.odometer = odometer;
        this.fuelGrade = fuelGrade;
        this.fuelAmount = fuelAmount;
        this.fuelUnitCost = fuelUnitCost;
        this.fuelCost = fuelCost;
    }

    //sets all information in the Log Entry, requires all info for that class
    public void setAll(String date, String station, Double odometer, String fuelGrade, Double fuelAmount, Double fuelUnitCost, Double fuelCost){
        this.date = date;
        this.station = station;
        this.odometer = odometer;
        this.fuelGrade = fuelGrade;
        this.fuelAmount = fuelAmount;
        this.fuelUnitCost = fuelUnitCost;
        this.fuelCost = fuelCost;
    }

    //returns the date of the instance of LogEntry being used
    public String getDate() {
        return date;
    }

    //returns the station of the instance of LogEntry being used
    public String getStation() {
        return station;
    }

    //returns the odometer reading of the instance of LogEntry being used
    public Double getOdometer() {
        return odometer;
    }

    //returns the fuel amount of the instance of LogEntry being used
    public Double getFuelAmount() {
        return fuelAmount;
    }

    //returns the fuel grade of the instance of LogEntry being used
    public String getFuelGrade() {
        return fuelGrade;
    }

    //returns the fuel unit cost of the instance of LogEntry being used
    public Double getFuelUnitCost() {
        return fuelUnitCost;
    }

    //returns the fuel cost of the instance of LogEntry being used
    public Double getFuelCost() {
        return fuelCost;
    }
}
