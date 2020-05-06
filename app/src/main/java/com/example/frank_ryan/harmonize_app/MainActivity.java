package com.example.frank_ryan.harmonize_app;

import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //buttons for create new track and load track
    private Button m_MainTrack, m_loadButton;
    //text to input file name to load
    private EditText m_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //retrieving xml
        m_MainTrack = findViewById(R.id.btn_MainTrack);
        m_loadButton = findViewById(R.id.btn_loadTrack);
        m_textView = findViewById(R.id.textView);

        m_textView.setVisibility(View.INVISIBLE);
        m_MainTrack.setOnClickListener(this);

        m_loadButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            //button that watches for loading track
            case R.id.btn_loadTrack:


                if(m_loadButton.getText().toString().equals("Load Track"))
                {

                    m_textView.setVisibility(View.VISIBLE);

                    m_MainTrack.setText("Back");
                    m_loadButton.setText("Continue");
                }

                else if(m_loadButton.getText().toString().equals("Continue")) {


                    //buffer used to read the file
                    BufferedReader buff;
                    //used to open the file if load is from device
                    FileInputStream fis;
                    //used to read the file
                    InputStreamReader isr;

                    String fileName = m_textView.getText().toString();

                    m_textView.setVisibility(View.INVISIBLE);


                    try {
                        //holds the line being parsed
                        String line;

                        //opening buffer stream to read from
                        fis = openFileInput(fileName);
                        isr = new InputStreamReader(fis);
                        buff = new BufferedReader(isr);

                        //boolean that are true if the instrument exists
                        boolean instrument1_exists = false;
                        boolean instrument2_exists = false;
                        boolean instrument3_exists = false;
                        boolean instrument4_exists = false;
                        boolean instrument5_exists = false;

                        //All possible data from instruments that can be read
                        HashMap<String, String> instrument1_notes = new HashMap<>();
                        HashMap<String, String> instrument2_notes = new HashMap<>();
                        HashMap<String, String> instrument3_notes = new HashMap<>();
                        HashMap<String, String> instrument4_notes = new HashMap<>();
                        HashMap<String, String> instrument5_notes = new HashMap<>();

                        int instrument1_ID = 0;
                        int instrument2_ID = 0;
                        int instrument3_ID = 0;
                        int instrument4_ID = 0;
                        int instrument5_ID = 0;

                        int instrument1_octaves = 0;
                        int instrument2_octaves = 0;
                        int instrument3_octaves = 0;
                        int instrument4_octaves = 0;
                        int instrument5_octaves = 0;

                        int instrument1_volume = 0;
                        int instrument2_volume = 0;
                        int instrument3_volume = 0;
                        int instrument4_volume = 0;
                        int instrument5_volume = 0;

                        //Reads the text file line by line
                        while ((line = buff.readLine()) != null) {
                            //if the line is blank, continue
                            if (line.isEmpty()) {
                                continue;
                            }

                            //trim the whitespace and split the line
                            line = line.trim();
                            String[] lineContents = line.split(" ");


                            //If Instrument1 is found
                            if (lineContents[0].equals("Instrument1")) {
                                instrument1_exists = true;

                                //retrives all hashmap recorded notes
                                for (int i = 0; i < 24; i++) {
                                    line = buff.readLine();
                                    line = line.trim();

                                    lineContents = line.split(" ");

                                    instrument1_notes.put(lineContents[0], lineContents[1]);


                                }
                                //retrieves next line
                                line = buff.readLine();
                                line = line.trim();

                                lineContents = line.split(" ");

                                //retrieving instrument ID, octaves, and volume selection
                                instrument1_ID = Integer.parseInt(lineContents[1]);

                                line = buff.readLine();
                                line = line.trim();

                                lineContents = line.split(" ");


                                instrument1_octaves = Integer.parseInt(lineContents[1]);

                                line = buff.readLine();
                                line = line.trim();

                                lineContents = line.split(" ");

                                instrument1_volume = Integer.parseInt(lineContents[1]);



                            }
                            //All remaining instruments have similar documentation as above
                            //If Instrument2 is found
                            else if (lineContents[0].equals("Instrument2")) {
                                instrument2_exists = true;

                                for (int i = 0; i < 24; i++) {
                                    line = buff.readLine();
                                    line = line.trim();

                                    lineContents = line.split(" ");

                                    instrument2_notes.put(lineContents[0], lineContents[1]);


                                }
                                line = buff.readLine();
                                line = line.trim();

                                lineContents = line.split(" ");

                                instrument2_ID = Integer.parseInt(lineContents[1]);

                                line = buff.readLine();
                                line = line.trim();

                                lineContents = line.split(" ");


                                instrument2_octaves = Integer.parseInt(lineContents[1]);

                                line = buff.readLine();
                                line = line.trim();

                                lineContents = line.split(" ");

                                instrument2_volume = Integer.parseInt(lineContents[1]);

                            }
                            //If Instrument3 is found
                            else if (lineContents[0].equals("Instrument3")) {
                                instrument3_exists = true;

                                for (int i = 0; i < 24; i++) {
                                    line = buff.readLine();
                                    line = line.trim();

                                    lineContents = line.split(" ");

                                    instrument3_notes.put(lineContents[0], lineContents[1]);


                                }
                                line = buff.readLine();
                                line = line.trim();

                                lineContents = line.split(" ");

                                instrument3_ID = Integer.parseInt(lineContents[1]);

                                line = buff.readLine();
                                line = line.trim();

                                lineContents = line.split(" ");


                                instrument3_octaves = Integer.parseInt(lineContents[1]);

                                line = buff.readLine();
                                line = line.trim();

                                lineContents = line.split(" ");

                                instrument3_volume = Integer.parseInt(lineContents[1]);

                            }
                            //If Instrument4 is found
                            else if (lineContents[0].equals("Instrument4")) {
                                instrument4_exists = true;

                                for (int i = 0; i < 24; i++) {
                                    line = buff.readLine();
                                    line = line.trim();

                                    lineContents = line.split(" ");

                                    instrument4_notes.put(lineContents[0], lineContents[1]);


                                }
                                line = buff.readLine();
                                line = line.trim();

                                lineContents = line.split(" ");

                                instrument4_ID = Integer.parseInt(lineContents[1]);

                                line = buff.readLine();
                                line = line.trim();

                                lineContents = line.split(" ");


                                instrument4_octaves = Integer.parseInt(lineContents[1]);

                                line = buff.readLine();
                                line = line.trim();

                                lineContents = line.split(" ");

                                instrument4_volume = Integer.parseInt(lineContents[1]);

                            }
                            //If Instrument5 is found
                            else if (lineContents[0].equals("Instrument5")) {
                                instrument5_exists = true;
                                for (int i = 0; i < 24; i++) {
                                    line = buff.readLine();
                                    line = line.trim();

                                    lineContents = line.split(" ");

                                    instrument5_notes.put(lineContents[0], lineContents[1]);


                                }
                                line = buff.readLine();
                                line = line.trim();

                                lineContents = line.split(" ");

                                instrument5_ID = Integer.parseInt(lineContents[1]);

                                line = buff.readLine();
                                line = line.trim();

                                lineContents = line.split(" ");


                                instrument5_octaves = Integer.parseInt(lineContents[1]);

                                line = buff.readLine();
                                line = line.trim();

                                lineContents = line.split(" ");

                                instrument5_volume = Integer.parseInt(lineContents[1]);

                            }
                            //if the files format is un recognized
                            else {
                                Toast.makeText(MainActivity.this, "Unrecognized File Format: " + lineContents[0], Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }

                        //Creating intent that obtains all info from loaded text file to send to main track home
                        Intent intent = new Intent(MainActivity.this, MainTrackHome.class);

                        //putting instrument extra variables into intent if they exist
                        if (instrument5_exists) {
                            intent.putExtra("instrumentsNumber", 5);

                            InstrumentTimeStamps instrument1 = new InstrumentTimeStamps(instrument1_notes, instrument1_ID, instrument1_octaves, instrument1_volume);
                            intent.putExtra("Instrument1", instrument1);

                            InstrumentTimeStamps instrument2 = new InstrumentTimeStamps(instrument2_notes, instrument2_ID, instrument2_octaves, instrument2_volume);
                            intent.putExtra("Instrument2", instrument2);

                            InstrumentTimeStamps instrument3 = new InstrumentTimeStamps(instrument3_notes, instrument3_ID, instrument3_octaves, instrument3_volume);
                            intent.putExtra("Instrument3", instrument3);

                            InstrumentTimeStamps instrument4 = new InstrumentTimeStamps(instrument4_notes, instrument4_ID, instrument4_octaves, instrument4_volume);
                            intent.putExtra("Instrument4", instrument4);

                            InstrumentTimeStamps instrument5 = new InstrumentTimeStamps(instrument5_notes, instrument5_ID, instrument5_octaves, instrument5_volume);
                            intent.putExtra("Instrument5", instrument5);


                        } else if (instrument4_exists) {
                            intent.putExtra("instrumentsNumber", 4);

                            InstrumentTimeStamps instrument1 = new InstrumentTimeStamps(instrument1_notes, instrument1_ID, instrument1_octaves, instrument1_volume);
                            intent.putExtra("Instrument1", instrument1);

                            InstrumentTimeStamps instrument2 = new InstrumentTimeStamps(instrument2_notes, instrument2_ID, instrument2_octaves, instrument2_volume);
                            intent.putExtra("Instrument2", instrument2);

                            InstrumentTimeStamps instrument3 = new InstrumentTimeStamps(instrument3_notes, instrument3_ID, instrument3_octaves, instrument3_volume);
                            intent.putExtra("Instrument3", instrument3);

                            InstrumentTimeStamps instrument4 = new InstrumentTimeStamps(instrument4_notes, instrument4_ID, instrument4_octaves, instrument4_volume);
                            intent.putExtra("Instrument4", instrument4);
                        } else if (instrument3_exists) {
                            intent.putExtra("instrumentsNumber", 3);

                            InstrumentTimeStamps instrument1 = new InstrumentTimeStamps(instrument1_notes, instrument1_ID, instrument1_octaves, instrument1_volume);
                            intent.putExtra("Instrument1", instrument1);

                            InstrumentTimeStamps instrument2 = new InstrumentTimeStamps(instrument2_notes, instrument2_ID, instrument2_octaves, instrument2_volume);
                            intent.putExtra("Instrument2", instrument2);

                            InstrumentTimeStamps instrument3 = new InstrumentTimeStamps(instrument3_notes, instrument3_ID, instrument3_octaves, instrument3_volume);
                            intent.putExtra("Instrument3", instrument3);

                        } else if (instrument2_exists) {
                            intent.putExtra("instrumentsNumber", 2);

                            InstrumentTimeStamps instrument1 = new InstrumentTimeStamps(instrument1_notes, instrument1_ID, instrument1_octaves, instrument1_volume);
                            intent.putExtra("Instrument1", instrument1);

                            InstrumentTimeStamps instrument2 = new InstrumentTimeStamps(instrument2_notes, instrument2_ID, instrument2_octaves, instrument2_volume);
                            intent.putExtra("Instrument2", instrument2);
                        } else if (instrument1_exists) {
                            intent.putExtra("instrumentsNumber", 1);

                            InstrumentTimeStamps instrument1 = new InstrumentTimeStamps(instrument1_notes, instrument1_ID, instrument1_octaves, instrument1_volume);
                            intent.putExtra("Instrument1", instrument1);

                        }

                        //loads text file data and sends user to maintrackhome
                        intent.putExtra("LoadedSong", true);
                        startActivity(intent);


                    } catch (Exception e) {
                        //Catches if the file was not found
                        System.out.println(e);
                        Toast.makeText(MainActivity.this, "File Did Not Exist", Toast.LENGTH_SHORT).show();
                        m_textView.setVisibility(View.VISIBLE);
                        m_textView.setText("Enter File Name");
                        return;
                    }
                }


                break;
            //button listener that sends user to maintrackhome to create new track
            case R.id.btn_MainTrack:

                if(m_MainTrack.getText().toString().equals("Create New Track"))
                {
                    Intent track_intent = new Intent(this, MainTrackHome.class);

                    startActivity(track_intent);
                }
                else if(m_MainTrack.getText().toString().equals("Back"))
                {
                    m_textView.setVisibility(View.INVISIBLE);
                    m_loadButton.setText("Load Track");
                    m_MainTrack.setText("Create New Track");
                }

                break;

        }
    }

}