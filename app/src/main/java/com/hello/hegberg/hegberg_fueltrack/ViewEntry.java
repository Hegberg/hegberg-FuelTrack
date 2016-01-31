package com.hello.hegberg.hegberg_fueltrack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ViewEntry extends AppCompatActivity {
    //initialize variables
    private static final String FILENAME = "file.sav";
    private ArrayList<LogEntry> entries = new ArrayList<LogEntry>();
    private LogEntry entryDisplaying;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry);

        //initialize button
        Button returnFromViewing = (Button) findViewById(R.id.return_from_viewing);

        //initialize proper format for all decimal info, odometer and fuel unit cost overlap
        DecimalFormat odometerFormat = new DecimalFormat("#.#");
        DecimalFormat fuelAmountFormat = new DecimalFormat("#.###");
        DecimalFormat fuelCostFormat = new DecimalFormat("#.##");

        //load all entries
        loadFromFile();

        //set entry being displayed to the entry selected to view
        entryDisplaying = entries.get(ChooseEntry.entryChosen);

        //show proper date
        TextView dateInfo = (TextView) findViewById(R.id.date_info);
        dateInfo.setText(entryDisplaying.getDate());

        //show proper station
        TextView sationInfo = (TextView) findViewById(R.id.station_info);
        sationInfo.setText(entryDisplaying.getStation());

        //show proper odometer reading and set proper decimal amount
        TextView odometerInfo = (TextView) findViewById(R.id.odometer_info);
        odometerInfo.setText(String.valueOf(odometerFormat.format(entryDisplaying.getOdometer())));
        //show proper fuel grade

        TextView fuelGradeInfo = (TextView) findViewById(R.id.fuel_grade_info);
        fuelGradeInfo.setText(entryDisplaying.getFuelGrade());

        //show proper fuel amount and set proper decimal amount
        TextView fuelAmountInfo = (TextView) findViewById(R.id.fuel_amount_info);
        fuelAmountInfo.setText(String.valueOf(fuelAmountFormat.format(entryDisplaying.getFuelAmount())));

        //show proper fuel unit cost and set proper decimal amount
        TextView fuelUnitCostInfo = (TextView) findViewById(R.id.fuel_unit_cost_info);
        fuelUnitCostInfo.setText(String.valueOf(odometerFormat.format(entryDisplaying.getFuelUnitCost())));

        //show proper fuel cost and set proper decimal amount
        TextView fuelCostInfo = (TextView) findViewById(R.id.fuel_cost_info);
        fuelCostInfo.setText(String.valueOf(fuelCostFormat.format(entryDisplaying.getFuelCost())));

        //return to main menu
        returnFromViewing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadFromFile() {
        //Most of code from lab 3, edited to work with my application, loads all entrys into entries
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
}
