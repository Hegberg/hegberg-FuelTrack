package com.hello.hegberg.hegberg_fueltrack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class EditEntry extends AppCompatActivity {
    private static final String FILENAME = "file.sav";
    private ArrayList<LogEntry> entries = new ArrayList<LogEntry>();
    private LogEntry entryDisplaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);

        //initialize buttons
        Button cancelEdit = (Button) findViewById(R.id.cancel_edit);
        Button doneEdit = (Button) findViewById(R.id.done_edit);

        loadFromFile();
        entryDisplaying = entries.get(ChooseEntry.entryChosen);
        //show proper date
        final TextView dateInfo = (TextView) findViewById(R.id.edit_date);
        dateInfo.setText(entryDisplaying.getDate());
        //show proper station
        final TextView stationInfo = (TextView) findViewById(R.id.edit_station);
        stationInfo.setText(entryDisplaying.getStation());
        //show proper odometer
        final TextView odometerInfo = (TextView) findViewById(R.id.edit_odometer);
        odometerInfo.setText(String.valueOf(entryDisplaying.getOdometer()));
        //show proper fuel grade
        final TextView fuelGradeInfo = (TextView) findViewById(R.id.edit_fuel_grade);
        fuelGradeInfo.setText(entryDisplaying.getFuelGrade());
        //show proper fuel amount
        final TextView fuelAmountInfo = (TextView) findViewById(R.id.edit_fuel_amount);
        fuelAmountInfo.setText(String.valueOf(entryDisplaying.getFuelAmount()));
        //show proper fuel unit cost
        final TextView fuelUnitCostInfo = (TextView) findViewById(R.id.edit_fuel_unit_cost);
        fuelUnitCostInfo.setText(String.valueOf(entryDisplaying.getFuelUnitCost()));
        //show proper fuel cost
        //TextView fuelCostInfo = (TextView) findViewById(R.id.fuel_cost_info);
        //fuelCostInfo.setText(String.valueOf(entryDisplaying.getFuelCost()));

        cancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        doneEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String date = dateInfo.getText().toString();
                    String station = stationInfo.getText().toString();
                    String odometerString = odometerInfo.getText().toString();
                    double odometer = Double.parseDouble(odometerString);
                    String fuelGrade = fuelGradeInfo.getText().toString();
                    String fuelAmountString = fuelAmountInfo.getText().toString();
                    double fuelAmount = Double.parseDouble(fuelAmountString);
                    String fuelUnitCostString = fuelUnitCostInfo.getText().toString();
                    double fuelUnitCost = Double.parseDouble(fuelUnitCostString);

                    if (date.equals("")){
                        Toast.makeText(EditEntry.this, "You need to enter a date", Toast.LENGTH_SHORT).show();
                    } else if (station.equals("")){
                        Toast.makeText(EditEntry.this, "You need to enter a station", Toast.LENGTH_SHORT).show();
                    } else if (fuelGrade.equals("")){
                        Toast.makeText(EditEntry.this, "You need to enter a fuel grade", Toast.LENGTH_SHORT).show();
                    } else {
                        //Double fuelCost = odometer * fuelAmount * (fuelUnitCost/100);
                        Double fuelCost = fuelAmount * (fuelUnitCost/100);
                        //set each
                        /*
                        entryDisplaying.setDate(date);
                        entryDisplaying.setStation(station);
                        entryDisplaying.setOdometer(odometer);
                        entryDisplaying.setFuelGrade(fuelGrade);
                        entryDisplaying.setFuelAmount(fuelAmount);
                        entryDisplaying.setFuelUnitCost(fuelUnitCost);
                        entryDisplaying.setFuelCost(fuelCost);
                        */
                        entryDisplaying.setAll(date, station, odometer, fuelGrade, fuelAmount, fuelUnitCost, fuelCost);
                        saveInFile();
                        finish();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(EditEntry.this, "Not all data present", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

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