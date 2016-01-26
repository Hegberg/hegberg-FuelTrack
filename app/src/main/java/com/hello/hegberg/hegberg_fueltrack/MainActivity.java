package com.hello.hegberg.hegberg_fueltrack;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<LogEntry> entries = new ArrayList<LogEntry>();
    private static final String FILENAME = "file.sav";
    private LogEntry currentEntry;
    private Double totalCost = 0.0;

    //happens once
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFromFile();
        Button logEntries = (Button) findViewById(R.id.LogEntries);
        Button addEntry = (Button) findViewById(R.id.AddEntry);

        logEntries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChooseEntry.class));
            }
        });

        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddEntry.class));

            }
        });
    }
    //happens every time menu opened/returned to
    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        /*
        TextView totalFuelAmount = (TextView) findViewById(R.id.TotalFuelAmount);
        totalCost = 0.0;
        //totalFuelAmount.setText(String.valueOf(totalCost));
        for (int i = 0; i < entries.size(); i++) {
            currentEntry = entries.get(i);
            totalCost = totalCost + currentEntry.getFuelCost();
        }
        DecimalFormat fuelCostFormat = new DecimalFormat("#.##");
        totalFuelAmount.setText(String.valueOf(fuelCostFormat.format(totalCost)));
        */
    }
    // Took from TA in Lab



    private void loadFromFile() {
        try {

            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<LogEntry>>() {}.getType();
            entries = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            entries = new ArrayList<LogEntry>();
            //throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
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