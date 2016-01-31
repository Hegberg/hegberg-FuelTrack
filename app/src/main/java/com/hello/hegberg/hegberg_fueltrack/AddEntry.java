package com.hello.hegberg.hegberg_fueltrack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddEntry extends AppCompatActivity {
    //initialize variables
    private EditText dateText;
    private EditText stationText;
    private EditText odometerText;
    private EditText fuelGradeText;
    private EditText fuelAmountText;
    private EditText fuelUnitCostText;
    private ArrayList<LogEntry> entries = new ArrayList<LogEntry>();
    private static final String FILENAME = "file.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        //initialize buttons and edit text fields
        Button cancelAdd = (Button) findViewById(R.id.cancel_add);
        Button doneAdd = (Button) findViewById(R.id.done_add);
        dateText = (EditText) findViewById(R.id.add_date);
        stationText = (EditText) findViewById(R.id.add_station);
        odometerText = (EditText) findViewById(R.id.add_odometer);
        fuelGradeText = (EditText) findViewById(R.id.add_fuel_grade);
        fuelAmountText = (EditText) findViewById(R.id.add_fuel_amount);
        fuelUnitCostText = (EditText) findViewById(R.id.add_fuel_unit_cost);

        //cancels adding current entry, all entered info lost
        cancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //saves entry to file, if entry entered properly
        doneAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    //gets all info and converts it to proper type
                    String date = dateText.getText().toString();
                    String station = stationText.getText().toString();
                    String odometerString = odometerText.getText().toString();
                    double odometer = Double.parseDouble(odometerString);
                    String fuelGrade = fuelGradeText.getText().toString();
                    String fuelAmountString = fuelAmountText.getText().toString();
                    double fuelAmount = Double.parseDouble(fuelAmountString);
                    String fuelUnitCostString = fuelUnitCostText.getText().toString();
                    double fuelUnitCost = Double.parseDouble(fuelUnitCostString);
                    //make sure no string fields are blank
                    if (date.equals("")){
                        Toast.makeText(AddEntry.this, "You need to enter a date", Toast.LENGTH_SHORT).show();
                    } else if (station.equals("")){
                        Toast.makeText(AddEntry.this, "You need to enter a station", Toast.LENGTH_SHORT).show();
                    } else if (fuelGrade.equals("")){
                        Toast.makeText(AddEntry.this, "You need to enter a fuel grade", Toast.LENGTH_SHORT).show();
                    } else {
                        //calculate fuel cost and add it to the current entries, then return to main menu
                        Double fuelCost = fuelAmount * (fuelUnitCost/100);
                        LogEntry latestEntry = new LogEntry(date, station, odometer, fuelGrade, fuelAmount, fuelUnitCost, fuelCost);
                        loadFromFile();
                        entries.add(latestEntry);
                        saveInFile();
                        finish();
                    }
                } catch (NumberFormatException e) {
                    //if any number blank or not a vaild number, that error is caught here
                    Toast.makeText(AddEntry.this, "Not all data present", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Most of code from lab 3, edited to work with my application, loads all entrys into entries
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<LogEntry>>() {}.getType();
            entries = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            entries = new ArrayList<>();
        }
    }

    private void saveInFile() {
        //Most of code from lab 3, edited to work with my application, saves all entrys in entries into a file
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(entries, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}