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
    //initialize variables
    private ArrayList<LogEntry> entries = new ArrayList<LogEntry>();
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

        //load all entries
        loadFromFile();

        //initialize buttons
        Button editEntry = (Button) findViewById(R.id.EditEntry);
        Button viewEntry = (Button) findViewById(R.id.ViewEntry);
        Button backFromView = (Button) findViewById(R.id.back_from_view);
        Button addEntry = (Button) findViewById(R.id.AddEntryInChoose);

        //set of radio group with proper amount of radio buttons
        AmountOfEntries = entries.size();
        radioGroup = new RadioButton[AmountOfEntries];
        addButtons(AmountOfEntries);
        RadioGroup radio = (RadioGroup) findViewById(R.id.Group);

        //set entry chosen to unselected
        entryChosen = -1;

        //Exit application button
        backFromView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //If a radio button clicked, sets entryChosen to proper entry
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

        //If an entry selected, go to edit entry activity with that entry
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

        //If an entry selected, go to view entry activity with that entry
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

        //Go to add entry activity
        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseEntry.this, AddEntry.class));

            }
        });
    }

    //Add proper amount of radio buttons, and set their text
    public void addButtons(int number) {
        for (int i = 1; i <= number; i++) {
            RadioButton btn = new RadioButton(this);
            btn.setId(1-1 + i); //1-1 because just using i raises an error
            btn.setText("Select Entry " + btn.getId() + " to view or edit");
            radioGroup[i-1] = btn;
            ((ViewGroup) findViewById(R.id.Group)).addView(radioGroup[i-1]);
        }
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
}