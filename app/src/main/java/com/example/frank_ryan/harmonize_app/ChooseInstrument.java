package com.example.frank_ryan.harmonize_app;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import static android.graphics.Color.WHITE;


public class ChooseInstrument extends AppCompatActivity implements View.OnClickListener  {

    /*
    m_guitar_btn -> button that chooses guitar for instrument
    m_piano_btn -> button that chooses piano for instrument
    m_electric_btn -> button that chooses electric for instrument
    m_proceed_btn -> button that leads user to build_song.xml for virtual keyboard to record instrument
    m_back_btn -> button that leads user to back to main_track_home.xml
     */
    private Button m_guitar_btn, m_piano_btn, m_electric_btn, m_proceed_btn, m_back_btn;

    /*
    m_octave_1 -> radiobutton used to select first octaves
    m_octave_2 -> radiobutton used to select second octaves
    m_octave_3 -> radiobutton used to select third octaves
    m_octave_4 -> radiobutton used to select fourth octaves
     */
    private RadioButton m_octave_1, m_octave_2, m_octave_3, m_octave_4;

    //stores number of instruments on main track
    private int m_numberOfInstruments;
    //stores instrument selected by user
    private int m_instrument_choice = 0;
    //stores octaves selected by user
    private int m_octave_choice = 1;

    //stores layout from choose_instrument.xml
    private RelativeLayout m_relativeLayout;
    /*
    m_instrument1 -> InstrumentTimeStamps object that hold recording of instrument1 from main track, if there is one
    m_instrument2 -> InstrumentTimeStamps object that hold recording of instrument2 from main track, if there is one
    m_instrument3 -> InstrumentTimeStamps object that hold recording of instrument3 from main track, if there is one
    m_instrument4 -> InstrumentTimeStamps object that hold recording of instrument4 from main track, if there is one
     */
    private InstrumentTimeStamps m_instrument1, m_instrument2, m_instrument3, m_instrument4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_instrument);

        //retrieves intent from MainTrackHome.java
        //includes number of instruments, and InstrumentTimeStamps objects from main track
        Intent intent = getIntent();
        m_numberOfInstruments = intent.getIntExtra("instrumentsNumber", 0);
        m_instrument1 = (InstrumentTimeStamps) intent.getSerializableExtra("Instrument1");
        m_instrument2 = (InstrumentTimeStamps) intent.getSerializableExtra("Instrument2");
        m_instrument3 = (InstrumentTimeStamps) intent.getSerializableExtra("Instrument3");
        m_instrument4 = (InstrumentTimeStamps) intent.getSerializableExtra("Instrument4");

        //retrives xml widgets
        m_relativeLayout = findViewById(R.id.relativeLayout);
        m_guitar_btn = (Button) findViewById(R.id.btn_Guitar);
        m_piano_btn = (Button) findViewById(R.id.btn_Piano);
        m_electric_btn = (Button) findViewById(R.id.btn_electricPiano);

        m_proceed_btn = (Button) findViewById(R.id.btn_Back);
        m_back_btn = (Button) findViewById(R.id.btn_Proceed);

        m_octave_1 = findViewById(R.id.radio_octave1);
        m_octave_2 = findViewById(R.id.radio_octave2);
        m_octave_3 = findViewById(R.id.radio_octave3);
        m_octave_4 = findViewById(R.id.radio_octave4);

        //Setting style and click listeners to this .xml
        m_octave_1.setTextColor(WHITE);
        m_octave_2.setTextColor(WHITE);
        m_octave_3.setTextColor(WHITE);
        m_octave_4.setTextColor(WHITE);

        m_guitar_btn.setOnClickListener(this);
        m_piano_btn.setOnClickListener(this);
        m_electric_btn.setOnClickListener(this);

        m_proceed_btn.setOnClickListener(this);
        m_back_btn.setOnClickListener(this);

        m_octave_1.setOnClickListener(this);
        m_octave_2.setOnClickListener(this);
        m_octave_3.setOnClickListener(this);
        m_octave_4.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //click listener for piano selected button
            case R.id.btn_Piano:

                //All octaves can be selected, instrument ID is now 0
                m_octave_1.setVisibility(View.VISIBLE);
                m_octave_2.setVisibility(View.VISIBLE);
                m_octave_3.setVisibility(View.VISIBLE);
                m_octave_4.setVisibility(View.VISIBLE);

                m_octave_1.setText("Octaves: 1 to 2");
                m_octave_2.setText("Octaves: 3 to 4");

                m_instrument_choice = 0;

                //sets piano background to layout
                m_relativeLayout.setBackgroundResource(R.drawable.piano_back);
                m_piano_btn.setBackgroundResource(R.drawable.play_button);
                m_electric_btn.setBackgroundResource(R.drawable.volume_button);
                m_guitar_btn.setBackgroundResource(R.drawable.volume_button);

                m_piano_btn.setClickable(false);
                m_piano_btn.setActivated(true);
                m_guitar_btn.setClickable(true);

                return;
            //click listener for guitar selected button
            case R.id.btn_Guitar:
                //Only first two octaves can be selected, instrument ID is now 1
                m_octave_1.setVisibility(View.VISIBLE);
                m_octave_2.setVisibility(View.VISIBLE);
                m_octave_3.setVisibility(View.INVISIBLE);
                m_octave_4.setVisibility(View.INVISIBLE);

                m_octave_1.setText("Octaves: 1 to 2");
                m_octave_2.setText("Octaves: 2 to 3");

                m_instrument_choice = 1;

                //sets guitar background to layout
                m_relativeLayout.setBackgroundResource(R.drawable.guitar_back);
                m_piano_btn.setBackgroundResource(R.drawable.volume_button);
                m_electric_btn.setBackgroundResource(R.drawable.volume_button);
                m_guitar_btn.setBackgroundResource(R.drawable.play_button);

                m_guitar_btn.setClickable(false);
                m_guitar_btn.setActivated(true);
                m_piano_btn.setClickable(true);
                return;

            case R.id.btn_electricPiano:

                //All octaves can be selected, instrument ID is now 0
                m_octave_1.setVisibility(View.VISIBLE);
                m_octave_2.setVisibility(View.INVISIBLE);
                m_octave_3.setVisibility(View.INVISIBLE);
                m_octave_4.setVisibility(View.INVISIBLE);

                m_octave_1.setText("Octaves: 3 to 4");


                m_instrument_choice = 2;

                //sets piano background to layout
                m_relativeLayout.setBackgroundResource(R.drawable.electric_background);
                m_piano_btn.setBackgroundResource(R.drawable.volume_button);
                m_electric_btn.setBackgroundResource(R.drawable.play_button);
                m_guitar_btn.setBackgroundResource(R.drawable.volume_button);

                m_piano_btn.setClickable(true);
                m_guitar_btn.setClickable(true);

                return;
            //listens for first octave, sets choice to 1
            case R.id.radio_octave1:

                m_octave_2.setChecked(false);
                m_octave_3.setChecked(false);
                m_octave_4.setChecked(false);

                m_octave_choice = 1;

                return;
            //listens for second octave, sets choice to 2
            case R.id.radio_octave2:
                m_octave_1.setChecked(false);
                m_octave_3.setChecked(false);
                m_octave_4.setChecked(false);

                m_octave_choice = 2;

                return;
            //listens for third octave, sets choice to 3
            case R.id.radio_octave3:

                m_octave_1.setChecked(false);
                m_octave_2.setChecked(false);
                m_octave_4.setChecked(false);

                m_octave_choice = 3;

                return;
            //listens for fourth octave, sets choice to 4
            case R.id.radio_octave4:

                m_octave_1.setChecked(false);
                m_octave_2.setChecked(false);
                m_octave_3.setChecked(false);

                m_octave_choice = 4;

                return;


            //listens for back button event
            case R.id.btn_Back:
                //sends user back to main_track_home.xml
                Intent back = new Intent(this, MainTrackHome.class);
                //Puts InstrumentTimeStamps objects as extras, depending on number of instruments
                if(m_numberOfInstruments == 0)
                {

                    back.putExtra("instrumentsNumber", 0);
                    back.putExtra("LoadedSong", false);


                    startActivity(back);

                }
                else if(m_numberOfInstruments == 1)
                {
                    back.putExtra("instrumentsNumber", 1);

                    back.putExtra("Instrument1", m_instrument1);

                    back.putExtra("LoadedSong", true);

                    startActivity(back);
                }
                else if(m_numberOfInstruments == 2)
                {
                    back.putExtra("instrumentsNumber", 2);

                    back.putExtra("Instrument1", m_instrument1);
                    back.putExtra("Instrument2", m_instrument2);
                    back.putExtra("LoadedSong", true);

                    startActivity(back);
                }
                else if(m_numberOfInstruments == 3)
                {
                    back.putExtra("instrumentsNumber", 3);

                    back.putExtra("Instrument1", m_instrument1);
                    back.putExtra("Instrument2", m_instrument2);
                    back.putExtra("Instrument3", m_instrument3);
                    back.putExtra("LoadedSong", true);

                    startActivity(back);
                }
                else if(m_numberOfInstruments == 4)
                {
                    back.putExtra("instrumentsNumber", 4);

                    back.putExtra("Instrument1", m_instrument1);
                    back.putExtra("Instrument2", m_instrument2);
                    back.putExtra("Instrument3", m_instrument3);
                    back.putExtra("Instrument4", m_instrument4);
                    back.putExtra("LoadedSong", true);

                    startActivity(back);
                }


                return;


            //button listener for proceed
            case R.id.btn_Proceed:
                //sends user to build_song.xml
                Intent add_instrument = new Intent(this, CreateBaseTrack.class);
                //Puts instrument choice, instrument number, and octave choice as intent for CreateBaseTrack.java
                //Also sends previous recorded instruments from main track
                if(m_numberOfInstruments == 0)
                {

                    add_instrument.putExtra("instrumentsNumber", 0);
                    add_instrument.putExtra("TypeOfInstrument", m_instrument_choice);
                    add_instrument.putExtra("OctaveChoice", m_octave_choice);

                    startActivity(add_instrument);

                }
                else if(m_numberOfInstruments == 1)
                {
                    add_instrument.putExtra("instrumentsNumber", 1);
                    add_instrument.putExtra("TypeOfInstrument", m_instrument_choice);
                    add_instrument.putExtra("OctaveChoice", m_octave_choice);
                    add_instrument.putExtra("Instrument1", m_instrument1);

                    startActivity(add_instrument);
                }
                else if(m_numberOfInstruments == 2)
                {
                    add_instrument.putExtra("instrumentsNumber", 2);
                    add_instrument.putExtra("TypeOfInstrument", m_instrument_choice);
                    add_instrument.putExtra("OctaveChoice", m_octave_choice);
                    add_instrument.putExtra("Instrument1", m_instrument1);
                    add_instrument.putExtra("Instrument2", m_instrument2);

                    startActivity(add_instrument);
                }
                else if(m_numberOfInstruments == 3)
                {
                    add_instrument.putExtra("instrumentsNumber", 3);
                    add_instrument.putExtra("TypeOfInstrument", m_instrument_choice);
                    add_instrument.putExtra("OctaveChoice", m_octave_choice);
                    add_instrument.putExtra("Instrument1", m_instrument1);
                    add_instrument.putExtra("Instrument2", m_instrument2);
                    add_instrument.putExtra("Instrument3", m_instrument3);

                    startActivity(add_instrument);
                }
                else if(m_numberOfInstruments == 4)
                {
                    add_instrument.putExtra("instrumentsNumber", 4);
                    add_instrument.putExtra("TypeOfInstrument", m_instrument_choice);
                    add_instrument.putExtra("OctaveChoice", m_octave_choice);
                    add_instrument.putExtra("Instrument1", m_instrument1);
                    add_instrument.putExtra("Instrument2", m_instrument2);
                    add_instrument.putExtra("Instrument3", m_instrument3);
                    add_instrument.putExtra("Instrument4", m_instrument4);

                    startActivity(add_instrument);
                }

                return;
        }

    }



}
