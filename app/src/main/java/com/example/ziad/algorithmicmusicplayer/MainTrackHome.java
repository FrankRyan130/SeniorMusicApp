package com.example.ziad.algorithmicmusicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

public class MainTrackHome  extends AppCompatActivity implements View.OnClickListener {


    private int numberOfInstruments;

    private Button add_instrument, playTrackbtn, btn_instrument1, btn_instrument2, btn_instrument3, btn_instrument4, btn_instrument5, btn_instrument6;

    private PianoTimeStamps instrument1, instrument2, instrument3, instrument4, instrument5, instrument6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_track_home);


        btn_instrument1 = findViewById(R.id.btn_firstInstrument);
        btn_instrument2 = findViewById(R.id.btn_secondInstrument);
        btn_instrument3 = findViewById(R.id.btn_thirdInstrument);
        btn_instrument4 = findViewById(R.id.btn_fourthInstrument);
        btn_instrument5 = findViewById(R.id.btn_fifthInstrument);
        btn_instrument6 = findViewById(R.id.btn_sixthInstrument);

        add_instrument = findViewById(R.id.btn_addInstrument);
        playTrackbtn = findViewById(R.id.btn_playTrack);

        add_instrument.setOnClickListener(this);
        playTrackbtn.setOnClickListener(this);
        btn_instrument1.setOnClickListener(this);
        btn_instrument2.setOnClickListener(this);
        btn_instrument3.setOnClickListener(this);
        btn_instrument4.setOnClickListener(this);
        btn_instrument5.setOnClickListener(this);
        btn_instrument6.setOnClickListener(this);



        Intent intent = getIntent();
        boolean loaded = intent.getBooleanExtra("LoadedSong", false);

        if(loaded)
        {
            numberOfInstruments = intent.getIntExtra("numInstruments", 0);

            if(numberOfInstruments == 1)
            {
                instrument1 = (PianoTimeStamps) intent.getSerializableExtra("Instrument1");
                btn_instrument2.setEnabled(false);
                btn_instrument3.setEnabled(false);
                btn_instrument4.setEnabled(false);
                btn_instrument5.setEnabled(false);
                btn_instrument6.setEnabled(false);


                System.out.println("IT MADE IT");

            }
            else if(numberOfInstruments == 2)
            {
                instrument1 = (PianoTimeStamps) intent.getSerializableExtra("Instrument1");
                instrument2 = (PianoTimeStamps) intent.getSerializableExtra("Instrument2");

                btn_instrument3.setEnabled(false);
                btn_instrument4.setEnabled(false);
                btn_instrument5.setEnabled(false);
                btn_instrument6.setEnabled(false);
            }
            else if(numberOfInstruments == 3)
            {
                instrument1 = (PianoTimeStamps)intent.getSerializableExtra("Instrument1");
                instrument2 = (PianoTimeStamps)intent.getSerializableExtra("Instrument2");
                instrument3 = (PianoTimeStamps)intent.getSerializableExtra("Instrument3");
                btn_instrument4.setEnabled(false);
                btn_instrument5.setEnabled(false);
                btn_instrument6.setEnabled(false);
            }
            else if(numberOfInstruments == 4)
            {
                instrument1 = (PianoTimeStamps)intent.getSerializableExtra("Instrument1");
                instrument2 = (PianoTimeStamps)intent.getSerializableExtra("Instrument2");
                instrument3 = (PianoTimeStamps)intent.getSerializableExtra("Instrument3");
                instrument4 = (PianoTimeStamps)intent.getSerializableExtra("Instrument4");
                btn_instrument5.setEnabled(false);
                btn_instrument6.setEnabled(false);
            }
            else if(numberOfInstruments == 5)
            {
                instrument1 = (PianoTimeStamps)intent.getSerializableExtra("Instrument1");
                instrument2 = (PianoTimeStamps)intent.getSerializableExtra("Instrument2");
                instrument3 = (PianoTimeStamps)intent.getSerializableExtra("Instrument3");
                instrument4 = (PianoTimeStamps)intent.getSerializableExtra("Instrument4");
                instrument5 = (PianoTimeStamps)intent.getSerializableExtra("Instrument5");
                btn_instrument6.setEnabled(false);
            }
            else if(numberOfInstruments == 6)
            {
                instrument1 = (PianoTimeStamps)intent.getSerializableExtra("Instrument1");
                instrument2 = (PianoTimeStamps)intent.getSerializableExtra("Instrument2");
                instrument3 = (PianoTimeStamps)intent.getSerializableExtra("Instrument3");
                instrument4 = (PianoTimeStamps)intent.getSerializableExtra("Instrument4");
                instrument5 = (PianoTimeStamps)intent.getSerializableExtra("Instrument5");
                instrument6 = (PianoTimeStamps)intent.getSerializableExtra("Instrument6");
            }

        }
        else
        {
            numberOfInstruments = 0;
            btn_instrument1.setEnabled(false);
            btn_instrument2.setEnabled(false);
            btn_instrument3.setEnabled(false);
            btn_instrument4.setEnabled(false);
            btn_instrument5.setEnabled(false);
            btn_instrument6.setEnabled(false);
        }


        /*




        If this was loaded song, get intent
            set specific buttons to disabled/enabled

        If this was new song, coming back from adding an instrument, get intent from that instrument base song class
            pass intent from all instruments into create instrument activity in order to play it when user records
            when the user records that song, send that instument into the intent

        If this was new song, dont get intent
            set all buttons to disabled


         */






    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_addInstrument:


                Intent add_instrument = new Intent(this, CreateBaseSong.class);

                if(numberOfInstruments == 0)
                {
                    add_instrument.putExtra("instrumentsNumber", 0);
                    startActivity(add_instrument);
                }
                else if(numberOfInstruments == 1)
                {
                    add_instrument.putExtra("Instrument1", instrument1);
                    add_instrument.putExtra("instrumentsNumber", 1);
                    startActivity(add_instrument);
                }
                else if(numberOfInstruments == 2)
                {
                    add_instrument.putExtra("Instrument1", instrument1);
                    add_instrument.putExtra("Instrument2", instrument2);
                    add_instrument.putExtra("instrumentsNumber", 2);
                    startActivity(add_instrument);
                }
                else if(numberOfInstruments == 3)
                {
                    add_instrument.putExtra("Instrument1", instrument1);
                    add_instrument.putExtra("Instrument2", instrument2);
                    add_instrument.putExtra("Instrument3", instrument3);
                    add_instrument.putExtra("instrumentsNumber", 3);
                    startActivity(add_instrument);
                }
                else if(numberOfInstruments == 4)
                {
                    add_instrument.putExtra("Instrument1", instrument1);
                    add_instrument.putExtra("Instrument2", instrument2);
                    add_instrument.putExtra("Instrument3", instrument3);
                    add_instrument.putExtra("Instrument4", instrument4);
                    add_instrument.putExtra("instrumentsNumber", 4);
                    startActivity(add_instrument);
                }
                else if(numberOfInstruments == 5)
                {
                    add_instrument.putExtra("Instrument1", instrument1);
                    add_instrument.putExtra("Instrument2", instrument2);
                    add_instrument.putExtra("Instrument3", instrument3);
                    add_instrument.putExtra("Instrument4", instrument4);
                    add_instrument.putExtra("Instrument5", instrument5);
                    add_instrument.putExtra("instrumentsNumber", 5);
                    startActivity(add_instrument);
                }
                else if(numberOfInstruments == 6)
                {
                    //Cant do it
                }


                break;



            case R.id.btn_firstInstrument:

                System.out.println(instrument1.getPianoNotes());
                HashMap<String, String> pianoNotes = new HashMap<>(instrument1.getPianoNotes());

                PianoTimeStamps piano = new PianoTimeStamps(this, pianoNotes);
                piano.playPianoRecording();

                break;


            case R.id.btn_secondInstrument:

                HashMap<String, String> pianoNotes2 = new HashMap<>(instrument2.getPianoNotes());

                PianoTimeStamps piano2 = new PianoTimeStamps(this, pianoNotes2);
                piano2.playPianoRecording();

                break;


            case R.id.btn_thirdInstrument:

                HashMap<String, String> pianoNotes3 = new HashMap<>(instrument3.getPianoNotes());
                PianoTimeStamps piano3 = new PianoTimeStamps(this, pianoNotes3);
                piano3.playPianoRecording();

                break;

            case R.id.btn_fourthInstrument:

                HashMap<String, String> pianoNotes4 = new HashMap<>(instrument4.getPianoNotes());
                PianoTimeStamps piano4 = new PianoTimeStamps(this, pianoNotes4);
                piano4.playPianoRecording();

                break;

            case R.id.btn_fifthInstrument:

                HashMap<String, String> pianoNotes5 = new HashMap<>(instrument5.getPianoNotes());
                PianoTimeStamps piano5 = new PianoTimeStamps(this, pianoNotes5);
                piano5.playPianoRecording();

                break;

            case R.id.btn_sixthInstrument:

                HashMap<String, String> pianoNotes6 = new HashMap<>(instrument6.getPianoNotes());
                PianoTimeStamps piano6 = new PianoTimeStamps(this, pianoNotes6);
                piano6.playPianoRecording();

                break;
            case R.id.btn_playTrack:

                if(numberOfInstruments == 0)
                {
                    //cant do it
                }
                else if(numberOfInstruments == 1)
                {
                    HashMap<String, String> instrument1Copy = new HashMap<>(instrument1.getPianoNotes());
                    PianoTimeStamps piano1Copy = new PianoTimeStamps(this, instrument1Copy);

                    piano1Copy.playPianoRecording();
                }
                else if(numberOfInstruments == 2)
                {
                    HashMap<String, String> instrument1Copy = new HashMap<>(instrument1.getPianoNotes());
                    PianoTimeStamps piano1Copy = new PianoTimeStamps(this, instrument1Copy);

                    HashMap<String, String> instrument2Copy = new HashMap<>(instrument2.getPianoNotes());
                    PianoTimeStamps piano2Copy = new PianoTimeStamps(this, instrument2Copy);

                    piano1Copy.playPianoRecording();
                    piano2Copy.playPianoRecording();

                }
                else if(numberOfInstruments == 3)
                {
                    HashMap<String, String> instrument1Copy = new HashMap<>(instrument1.getPianoNotes());
                    PianoTimeStamps piano1Copy = new PianoTimeStamps(this, instrument1Copy);

                    HashMap<String, String> instrument2Copy = new HashMap<>(instrument2.getPianoNotes());
                    PianoTimeStamps piano2Copy = new PianoTimeStamps(this, instrument2Copy);

                    HashMap<String, String> instrument3Copy = new HashMap<>(instrument3.getPianoNotes());
                    PianoTimeStamps piano3Copy = new PianoTimeStamps(this, instrument3Copy);

                    piano1Copy.playPianoRecording();
                    piano2Copy.playPianoRecording();
                    piano3Copy.playPianoRecording();

                }
                else if(numberOfInstruments == 4)
                {
                    HashMap<String, String> instrument1Copy = new HashMap<>(instrument1.getPianoNotes());
                    PianoTimeStamps piano1Copy = new PianoTimeStamps(this, instrument1Copy);

                    HashMap<String, String> instrument2Copy = new HashMap<>(instrument2.getPianoNotes());
                    PianoTimeStamps piano2Copy = new PianoTimeStamps(this, instrument2Copy);

                    HashMap<String, String> instrument3Copy = new HashMap<>(instrument3.getPianoNotes());
                    PianoTimeStamps piano3Copy = new PianoTimeStamps(this, instrument3Copy);

                    HashMap<String, String> instrument4Copy = new HashMap<>(instrument4.getPianoNotes());
                    PianoTimeStamps piano4Copy = new PianoTimeStamps(this, instrument4Copy);

                    piano1Copy.playPianoRecording();
                    piano2Copy.playPianoRecording();
                    piano3Copy.playPianoRecording();
                    piano4Copy.playPianoRecording();

                }
                else if(numberOfInstruments == 5)
                {

                    HashMap<String, String> instrument1Copy = new HashMap<>(instrument1.getPianoNotes());
                    PianoTimeStamps piano1Copy = new PianoTimeStamps(this, instrument1Copy);

                    HashMap<String, String> instrument2Copy = new HashMap<>(instrument2.getPianoNotes());
                    PianoTimeStamps piano2Copy = new PianoTimeStamps(this, instrument2Copy);

                    HashMap<String, String> instrument3Copy = new HashMap<>(instrument3.getPianoNotes());
                    PianoTimeStamps piano3Copy = new PianoTimeStamps(this, instrument3Copy);

                    HashMap<String, String> instrument4Copy = new HashMap<>(instrument4.getPianoNotes());
                    PianoTimeStamps piano4Copy = new PianoTimeStamps(this, instrument4Copy);

                    HashMap<String, String> instrument5Copy = new HashMap<>(instrument5.getPianoNotes());
                    PianoTimeStamps piano5Copy = new PianoTimeStamps(this, instrument5Copy);

                    piano1Copy.playPianoRecording();
                    piano2Copy.playPianoRecording();
                    piano3Copy.playPianoRecording();
                    piano4Copy.playPianoRecording();
                    piano5Copy.playPianoRecording();

                }
                else if(numberOfInstruments == 6)
                {
                    HashMap<String, String> instrument1Copy = new HashMap<>(instrument1.getPianoNotes());
                    PianoTimeStamps piano1Copy = new PianoTimeStamps(this, instrument1Copy);


                    HashMap<String, String> instrument2Copy = new HashMap<>(instrument2.getPianoNotes());
                    PianoTimeStamps piano2Copy = new PianoTimeStamps(this, instrument2Copy);


                    HashMap<String, String> instrument3Copy = new HashMap<>(instrument3.getPianoNotes());
                    PianoTimeStamps piano3Copy = new PianoTimeStamps(this, instrument3Copy);


                    HashMap<String, String> instrument4Copy = new HashMap<>(instrument4.getPianoNotes());
                    PianoTimeStamps piano4Copy = new PianoTimeStamps(this, instrument4Copy);


                    HashMap<String, String> instrument5Copy = new HashMap<>(instrument5.getPianoNotes());
                    PianoTimeStamps piano5Copy = new PianoTimeStamps(this, instrument5Copy);

                    HashMap<String, String> instrument6Copy = new HashMap<>(instrument6.getPianoNotes());
                    PianoTimeStamps piano6Copy = new PianoTimeStamps(this, instrument6Copy);


                    piano1Copy.playPianoRecording();
                    piano2Copy.playPianoRecording();
                    piano3Copy.playPianoRecording();
                    piano4Copy.playPianoRecording();
                    piano5Copy.playPianoRecording();
                    piano6Copy.playPianoRecording();
                }
                break;

        }
    }


}
