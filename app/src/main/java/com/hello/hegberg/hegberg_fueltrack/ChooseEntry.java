package com.hello.hegberg.hegberg_fueltrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChooseEntry extends AppCompatActivity {
    private ArrayList<LogEntry> entries = new ArrayList<LogEntry>();
    //private ArrayAdapter<LogEntry> adapter;
    //private ListView oldEntryList;
    private static final String FILENAME = "file.sav";
    private int AmountOfEntries;
    private RadioButton[] radioGroup;
    public static int entryChosen;
    private LogEntry currentEntry;
    private Double totalCost = 0.0;


    protected void onStart() {
        super.onStart();
        //loads all entries
        loadFromFile();
        //initializes text field
        TextView totalFuelAmount = (TextView) findViewById(R.id.total_fuel_cost);

        //calculates total cost of all the fuel
        totalCost = 0.0;
        for (int i = 0; i < entries.size(); i++) {
            currentEntry = entries.get(i);
            totalCost = totalCost + currentEntry.getFuelCost();
        }

        //sets the total cost to 2 decimals
        DecimalFormat fuelCostFormat = new DecimalFormat("#.##");

        //sets text field to the total fuel cost
        totalFuelAmount.setText(String.valueOf(fuelCostFormat.format(totalCost)));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_entry);
        loadFromFile();
        Button editEntry = (Button) findViewById(R.id.EditEntry);
        Button viewEntry = (Button) findViewById(R.id.ViewEntry);
        Button backFromView = (Button) findViewById(R.id.back_from_view);
        //oldEntryList = (ListView) findViewById(R.id.oldEntryList);

        AmountOfEntries = entries.size();
        radioGroup = new RadioButton[AmountOfEntries];
        addButtons(AmountOfEntries);
        RadioGroup radio = (RadioGroup) findViewById(R.id.Group);

        entryChosen = -1;

        backFromView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < AmountOfEntries; i++) {
                    if (checkedId == radioGroup[i].getId()) {
                        entryChosen = i;
                    }
                }
            }
        });

        editEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entryChosen != -1) {
                    startActivity(new Intent(ChooseEntry.this, EditEntry.class));
                } else {
                    Toast.makeText(ChooseEntry.this, "No Entry Chosen", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entryChosen != -1) {
                    startActivity(new Intent(ChooseEntry.this, ViewEntry.class));
                } else {
                    Toast.makeText(ChooseEntry.this, "No Entry Chosen", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addButtons(int number) {
        for (int i = 1; i <= number; i++) {
            RadioButton btn = new RadioButton(this);
            btn.setId(1-1 + i); //1-1 because just using i raises an error
            btn.setText("Select Entry " + btn.getId() + " to view or edit");
            radioGroup[i-1] = btn;
            ((ViewGroup) findViewById(R.id.Group)).addView(radioGroup[i-1]);
        }
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