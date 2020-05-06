package com.example.frank_ryan.harmonize_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Timer;

public class MainTrackHome  extends AppCompatActivity implements View.OnClickListener {

    //holds number of instruments on track
    private int m_numberOfInstruments;

    /*
    m_add_instrument -> button that proceeds to ChooseInstrument.java to add new instrument to track
    m_playTrackbtn -> button that plays all recordings on main track
    m_btn_instrument1_play -> button that plays recording of first instrument
    m_btn_instrument2_play -> button that plays recording of second instrument
    m_btn_instrument3_play -> button that plays recording of third instrument
    m_btn_instrument4_play -> button that plays recording of fourth instrument
    m_btn_instrument5_play -> button that plays recording of fifth instrument
    m_btn_instrument1_delete -> button that deletes recording of first instrument
    m_btn_instrument2_delete -> button that deletes recording of second instrument
    m_btn_instrument3_delete -> button that deletes recording of third instrument
    m_btn_instrument4_delete -> button that deletes recording of fourth instrument
    m_btn_instrument5_delete -> button that deletes recording of fifth instrument
    m_btn_instrument1_volume-> button that changes volume of first instrument
    m_btn_instrument2_volume -> button that changes volume  of second instrument
    m_btn_instrument3_volume -> button that changes volume  of third instrument
    m_btn_instrument4_volume -> button that changes volume  of fourth instrument
    m_btn_instrument5_volume -> button that changes volume  of fifth instrument

    Note: these buttons were not put inside an array for better view
    */
    private Button m_add_instrument, m_playTrackbtn, m_saveTrackBtn;

    private Button m_btn_instrumentPlay[] = new Button[5];
    private Button m_btn_instrumentDelete[] = new Button[5];
    private Button m_btn_instrumentVolume[] = new Button[5];

    private Button m_colorInstruments[] = new Button[6];

    private int m_colorID[] = new int[6];

    //Button arrays that hold mini virtual keyboards, m_firstNotes[] being the first instrument, m_secondsNotes[ being the second, and so on
    private Button m_firstNotes[] = new Button[26];
    private Button m_secondNotes[] = new Button[26];
    private Button m_thirdNotes[] = new Button[26];
    private Button m_fourthNotes[] = new Button[26];
    private Button m_fifthNotes[] = new Button[26];


    //TextView array that holds the icon images of the instruments
    private TextView m_instrumentIcons[] = new TextView[5];
    //TextView array that holds the text titles of the instruments
    private TextView m_instrumentText[] = new TextView[5];

    //String used for file name to save to
    private String m_fileName = "";

    /*
    m_instrument1 -> holds the first instrument recording and IDs as a InstrumentTimeStamps object, if there is one
    m_instrument2 -> holds the second instrument recording and IDs as a InstrumentTimeStamps object, if there is one
    m_instrument3 -> holds the third instrument recording and IDs as a InstrumentTimeStamps object, if there is one
    m_instrument4 -> holds the fourth instrument recording and IDs as a InstrumentTimeStamps object, if there is one
    m_instrument5 -> holds the fifth instrument recording and IDs as a InstrumentTimeStamps object, if there is one
    m_notes1 -> used to play recording of first instrument, required to note delete the recorded notes of m_instrument1
    m_notes2 -> used to play recording of second instrument, required to note delete the recorded notes of m_instrument2
    m_notes3 -> used to play recording of third instrument, required to note delete the recorded notes of m_instrument3
    m_notes4 -> used to play recording of fourth instrument, required to note delete the recorded notes of m_instrument4
    m_notes5 -> used to play recording of fifth instrument, required to note delete the recorded notes of m_instrument5
     */
    private InstrumentTimeStamps m_instrument1, m_instrument2, m_instrument3, m_instrument4, m_instrument5, m_notes1, m_notes2, m_notes3, m_notes4, m_notes5;

    private InstrumentTimeStamps piano1Copy, piano2Copy, piano3Copy, piano4Copy, piano5Copy;

    private AudioManager m_audioManager;
    private float m_curVolume, m_maxVolume, m_volume;

    private HighlightKeys m_minikeyboard1, m_minikeyboard2, m_minikeyboard3, m_minikeyboard4, m_minikeyboard5;


    public MainTrackHome()
    {

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_track_home);

        //method that sets all button IDs of all mini virtual keyboards
        initializePianoButtons();

        //sets all TextViews of all possible instruments for images
        m_instrumentIcons[0] = findViewById(R.id.firstIconInstrument);
        m_instrumentIcons[1] = findViewById(R.id.secondIconInstrument);
        m_instrumentIcons[2] = findViewById(R.id.thirdIconInstrument);
        m_instrumentIcons[3] = findViewById(R.id.fourthIconInstrument);
        m_instrumentIcons[4] = findViewById(R.id.fifthIconInstrument);

        //sets all TextView IDs of all possible instruments for titles
        m_instrumentText[0] = findViewById(R.id.textViewInstrument1);
        m_instrumentText[1] = findViewById(R.id.textViewInstrument2);
        m_instrumentText[2] = findViewById(R.id.textViewInstrument3);
        m_instrumentText[3] = findViewById(R.id.textViewInstrument4);
        m_instrumentText[4] = findViewById(R.id.textViewInstrument5);

        //Setting all button IDs of instrument recordings
        m_btn_instrumentPlay[0] = findViewById(R.id.btn_firstInstrument);
        m_btn_instrumentPlay[1] = findViewById(R.id.btn_secondInstrument);
        m_btn_instrumentPlay[2] = findViewById(R.id.btn_thirdInstrument);
        m_btn_instrumentPlay[3] = findViewById(R.id.btn_fourthInstrument);
        m_btn_instrumentPlay[4] = findViewById(R.id.btn_fifthInstrument);

        m_btn_instrumentDelete[0]  = findViewById(R.id.btn_delete_firstInstrument);
        m_btn_instrumentDelete[1]  = findViewById(R.id.btn_delete_secondInstrument);
        m_btn_instrumentDelete[2]  = findViewById(R.id.btn_delete_thirdInstrument);
        m_btn_instrumentDelete[3]  = findViewById(R.id.btn_delete_fourthInstrument);
        m_btn_instrumentDelete[4]  = findViewById(R.id.btn_delete_fifthInstrument);

        m_btn_instrumentVolume[0] = findViewById(R.id.btn_volume_firstInstrument);
        m_btn_instrumentVolume[1] = findViewById(R.id.btn_volume_secondInstrument);
        m_btn_instrumentVolume[2] = findViewById(R.id.btn_volume_thirdInstrument);
        m_btn_instrumentVolume[3] = findViewById(R.id.btn_volume_fourthInstrument);
        m_btn_instrumentVolume[4] = findViewById(R.id.btn_volume_fifthInstrument);

        m_colorInstruments[0] = findViewById(R.id.btn_color_firstInstrument);
        m_colorInstruments[1] = findViewById(R.id.btn_color_secondInstrument);
        m_colorInstruments[2] = findViewById(R.id.btn_color_thirdInstrument);
        m_colorInstruments[3] = findViewById(R.id.btn_color_fourthInstrument);
        m_colorInstruments[4] = findViewById(R.id.btn_color_fifthInstrument);




        //Setting color IDs to green
        for(int i= 0; i < 5; i++)
        {
            m_colorID[i] = 0;
        }

        m_minikeyboard1 = new HighlightKeys();
        m_minikeyboard2 = new HighlightKeys();
        m_minikeyboard3 = new HighlightKeys();
        m_minikeyboard4 = new HighlightKeys();
        m_minikeyboard5 = new HighlightKeys();

        m_add_instrument = findViewById(R.id.btn_addInstrument);
        m_playTrackbtn = findViewById(R.id.btn_playTrack);
        m_saveTrackBtn = findViewById(R.id.btn_saveTrack);

        //Setting all onClick listeners for buttons
        m_add_instrument.setOnClickListener(this);
        m_playTrackbtn.setOnClickListener(this);
        m_saveTrackBtn.setOnClickListener(this);

        for(int i = 0; i < 5; i ++)
        {
            m_btn_instrumentPlay[i].setOnClickListener(this);
            m_btn_instrumentDelete[i].setOnClickListener(this);
            m_btn_instrumentVolume[i].setOnClickListener(this);
        }


        for(int i = 0; i<5; i++)
        {
            m_colorInstruments[i].setOnClickListener(this);
        }

        m_audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        m_curVolume = (float)m_audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        m_maxVolume = (float)m_audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        m_volume = m_curVolume / m_maxVolume;



        //Gets intent, first checks to see if "LoadedSong" Extra is false
        Intent intent = getIntent();
        boolean loaded = intent.getBooleanExtra("LoadedSong", false);

        //If track is loaded, retrieve m_numberOfInstruments and all previous recorded instruments based on m_numberOfInstruments
        if(loaded)
        {
            m_numberOfInstruments = intent.getIntExtra("instrumentsNumber", 0);

            if(m_numberOfInstruments == 1)
            {
                m_instrument1 = (InstrumentTimeStamps) intent.getSerializableExtra("Instrument1");

                //hides all piano buttons besides first instrument
                hidePianoButtons(1);

                m_colorInstruments[0].setVisibility(View.VISIBLE);
                for(int i =1; i<5; i++)
                {
                    m_colorInstruments[i].setVisibility(View.INVISIBLE);
                    m_btn_instrumentPlay[i].setEnabled(false);
                }

            }
            else if(m_numberOfInstruments == 2)
            {
                m_instrument1 = (InstrumentTimeStamps) intent.getSerializableExtra("Instrument1");
                m_instrument2 = (InstrumentTimeStamps) intent.getSerializableExtra("Instrument2");


                //hides all piano buttons besides first and second instruments
                hidePianoButtons(2);

                m_colorInstruments[0].setVisibility(View.VISIBLE);
                m_colorInstruments[1].setVisibility(View.VISIBLE);
                for(int i =2; i<5; i++)
                {
                    m_colorInstruments[i].setVisibility(View.INVISIBLE);
                    m_btn_instrumentPlay[i].setEnabled(false);
                }
            }
            else if(m_numberOfInstruments == 3)
            {
                m_instrument1 = (InstrumentTimeStamps)intent.getSerializableExtra("Instrument1");
                m_instrument2 = (InstrumentTimeStamps)intent.getSerializableExtra("Instrument2");
                m_instrument3 = (InstrumentTimeStamps)intent.getSerializableExtra("Instrument3");

                //hides all piano buttons besides first, second, and third instruments
                hidePianoButtons(3);

                m_colorInstruments[0].setVisibility(View.VISIBLE);
                m_colorInstruments[1].setVisibility(View.VISIBLE);
                m_colorInstruments[2].setVisibility(View.VISIBLE);
                for(int i =3; i<5; i++)
                {
                    m_colorInstruments[i].setVisibility(View.INVISIBLE);
                    m_btn_instrumentPlay[i].setEnabled(false);
                }
            }
            else if(m_numberOfInstruments == 4)
            {
                m_instrument1 = (InstrumentTimeStamps)intent.getSerializableExtra("Instrument1");
                m_instrument2 = (InstrumentTimeStamps)intent.getSerializableExtra("Instrument2");
                m_instrument3 = (InstrumentTimeStamps)intent.getSerializableExtra("Instrument3");
                m_instrument4 = (InstrumentTimeStamps)intent.getSerializableExtra("Instrument4");
                //hides all piano buttons besides first, second, third, and fourth instruments
                hidePianoButtons(4);

                m_colorInstruments[0].setVisibility(View.VISIBLE);
                m_colorInstruments[1].setVisibility(View.VISIBLE);
                m_colorInstruments[2].setVisibility(View.VISIBLE);
                m_colorInstruments[3].setVisibility(View.VISIBLE);
                m_colorInstruments[4].setVisibility(View.INVISIBLE);

                m_btn_instrumentPlay[4].setEnabled(false);

            }
            else if(m_numberOfInstruments == 5)
            {
                m_instrument1 = (InstrumentTimeStamps)intent.getSerializableExtra("Instrument1");
                m_instrument2 = (InstrumentTimeStamps)intent.getSerializableExtra("Instrument2");
                m_instrument3 = (InstrumentTimeStamps)intent.getSerializableExtra("Instrument3");
                m_instrument4 = (InstrumentTimeStamps)intent.getSerializableExtra("Instrument4");
                m_instrument5 = (InstrumentTimeStamps)intent.getSerializableExtra("Instrument5");

                hidePianoButtons(5);

                m_colorInstruments[0].setVisibility(View.VISIBLE);
                m_colorInstruments[1].setVisibility(View.VISIBLE);
                m_colorInstruments[2].setVisibility(View.VISIBLE);
                m_colorInstruments[3].setVisibility(View.VISIBLE);
                m_colorInstruments[4].setVisibility(View.VISIBLE);
            }


        }
        //Else there were no recorded instruments to retrieve
        else
        {
            m_numberOfInstruments = 0;
            //hides all buttons for instruments
            hidePianoButtons(0);

            m_saveTrackBtn.setEnabled(false);

            for(int i =0; i<5; i++)
            {
                m_colorInstruments[i].setVisibility(View.INVISIBLE);
                m_btn_instrumentPlay[i].setEnabled(false);
            }

        }

        //Sets all instrument logos through function call, depending on number of instruments and ID of instruments
        initializeInstrumentLogos();
        //Sets all non-recorded instrument functionality buttons (such as play and delete) to invisible
        initializeButtonVisiblity();

        setVolumeText();

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            //listens for user to add an instrument
            case R.id.btn_addInstrument:
                //Intent will send number of instruments, and all recorded instruments thus far to ChooseInstrument.java
                Intent add_instrument = new Intent(this, ChooseInstrument.class);

                if(m_numberOfInstruments == 0)
                {
                    add_instrument.putExtra("instrumentsNumber", 0);

                    startActivity(add_instrument);
                }
                else if(m_numberOfInstruments == 1)
                {
                    add_instrument.putExtra("Instrument1", m_instrument1);
                    add_instrument.putExtra("instrumentsNumber", 1);
                    startActivity(add_instrument);
                }
                else if(m_numberOfInstruments == 2)
                {
                    add_instrument.putExtra("Instrument1", m_instrument1);
                    add_instrument.putExtra("Instrument2", m_instrument2);
                    add_instrument.putExtra("instrumentsNumber", 2);
                    startActivity(add_instrument);
                }
                else if(m_numberOfInstruments == 3)
                {
                    add_instrument.putExtra("Instrument1", m_instrument1);
                    add_instrument.putExtra("Instrument2", m_instrument2);
                    add_instrument.putExtra("Instrument3", m_instrument3);
                    add_instrument.putExtra("instrumentsNumber", 3);
                    startActivity(add_instrument);
                }
                else if(m_numberOfInstruments == 4)
                {
                    add_instrument.putExtra("Instrument1", m_instrument1);
                    add_instrument.putExtra("Instrument2", m_instrument2);
                    add_instrument.putExtra("Instrument3", m_instrument3);
                    add_instrument.putExtra("Instrument4", m_instrument4);
                    add_instrument.putExtra("instrumentsNumber", 4);
                    startActivity(add_instrument);
                }
                else if(m_numberOfInstruments == 5)
                {
                    //Cant do it, only 5 instruments allowed
                }

                break;

            //listener for first instrument's play button
            case R.id.btn_firstInstrument:

                if(m_btn_instrumentPlay[0].getText().toString().equals("Play"))
                {
                    //Creating replica of m_instrument1 in order to not delete HashMap values
                    HashMap<String, String> pianoNotes1 = new HashMap<>(m_instrument1.getPianoNotes());

                    //Plays recorded instrument with replica of m_instrument1
                    m_notes1 = new InstrumentTimeStamps(this, pianoNotes1, m_instrument1.getM_instrumentID(), m_instrument1.getM_octaves(), m_instrument1.getM_volumeSelection(), m_volume);

                    Timer a_timer = new Timer();
                    m_notes1.playInstrumentRecording(a_timer);

                    //Replicas m_instrument1 HashMap again to play highlights onto mini keyboard
                    HashMap<String, String> pianoNotes1_new = new HashMap<>(m_instrument1.getPianoNotes());
                    //m_highlights is created in order to have multiple instruments call playPianoHighlights()
                    m_minikeyboard1 = new HighlightKeys(m_firstNotes, pianoNotes1_new, m_colorID[0]);

                    m_minikeyboard1.playButtonHighlights(a_timer);

                    m_btn_instrumentPlay[0].setText("Stop/Reset");
                    m_btn_instrumentVolume[0].setEnabled(false);
                    m_btn_instrumentDelete[0].setEnabled(false);

                    m_saveTrackBtn.setEnabled(false);
                    m_playTrackbtn.setEnabled(false);
                    m_add_instrument.setEnabled(false);

                    m_btn_instrumentPlay[0].setBackgroundResource(R.drawable.stop_button);

                }

                else if(m_btn_instrumentPlay[0].getText().toString().equals("Stop/Reset"))
                {
                    //Recording of sounds is stopped as well as highlights of piano
                    m_notes1.stopPlaying();
                    m_minikeyboard1.stopPlaying();
                    //Piano visuals of mini keyboard is set to default with function call
                    setPianoDrawableDefault(m_firstNotes);

                    m_btn_instrumentPlay[0].setText("Play");
                    m_btn_instrumentVolume[0].setEnabled(true);
                    m_btn_instrumentDelete[0].setEnabled(true);

                    m_saveTrackBtn.setEnabled(true);
                    m_playTrackbtn.setEnabled(true);
                    m_add_instrument.setEnabled(true);

                    m_btn_instrumentPlay[0].setBackgroundResource(R.drawable.play_button);
                }


                break;

            //listener for second instrument's play button
            case R.id.btn_secondInstrument:
                if(m_btn_instrumentPlay[1].getText().toString().equals("Play")) {

                    HashMap<String, String> pianoNotes2 = new HashMap<>(m_instrument2.getPianoNotes());


                    m_notes2 = new InstrumentTimeStamps(this, pianoNotes2, m_instrument2.getM_instrumentID(), m_instrument2.getM_octaves(), m_instrument2.getM_volumeSelection(), m_volume);
                    Timer a_timer = new Timer();
                    m_notes2.playInstrumentRecording(a_timer);


                    HashMap<String, String> pianoNotes2_new = new HashMap<>(m_instrument2.getPianoNotes());

                    m_minikeyboard2 = new HighlightKeys(m_secondNotes, pianoNotes2_new, m_colorID[1]);

                    m_minikeyboard2.playButtonHighlights(a_timer);

                    m_btn_instrumentVolume[1].setEnabled(false);
                    m_btn_instrumentDelete[1].setEnabled(false);
                    m_saveTrackBtn.setEnabled(false);
                    m_playTrackbtn.setEnabled(false);
                    m_add_instrument.setEnabled(false);

                    m_btn_instrumentPlay[1].setText("Stop/Reset");
                    m_btn_instrumentPlay[1].setBackgroundResource(R.drawable.stop_button);
                }
                else if(m_btn_instrumentPlay[1].getText().toString().equals("Stop/Reset"))
                {
                    m_notes2.stopPlaying();
                    m_minikeyboard2.stopPlaying();
                    setPianoDrawableDefault(m_secondNotes);

                    m_btn_instrumentVolume[1].setEnabled(true);
                    m_btn_instrumentDelete[1].setEnabled(true);
                    m_saveTrackBtn.setEnabled(true);
                    m_playTrackbtn.setEnabled(true);
                    m_add_instrument.setEnabled(true);

                    m_btn_instrumentPlay[1].setText("Play");
                    m_btn_instrumentPlay[1].setBackgroundResource(R.drawable.play_button);
                }

                break;

            //listener for third instrument's play button
            case R.id.btn_thirdInstrument:

                if(m_btn_instrumentPlay[2].getText().toString().equals("Play")) {

                    HashMap<String, String> pianoNotes3 = new HashMap<>(m_instrument3.getPianoNotes());
                    m_notes3 = new InstrumentTimeStamps(this, pianoNotes3, m_instrument3.getM_instrumentID(), m_instrument3.getM_octaves(), m_instrument3.getM_volumeSelection(), m_volume);
                    Timer a_timer = new Timer();
                    m_notes3.playInstrumentRecording(a_timer);

                    HashMap<String, String> pianoNotes3_new = new HashMap<>(m_instrument3.getPianoNotes());

                    m_minikeyboard3 = new HighlightKeys(m_thirdNotes, pianoNotes3_new, m_colorID[2]);

                    m_minikeyboard3.playButtonHighlights(a_timer);

                    m_btn_instrumentVolume[2].setEnabled(false);
                    m_btn_instrumentDelete[2].setEnabled(false);
                    m_saveTrackBtn.setEnabled(false);
                    m_playTrackbtn.setEnabled(false);
                    m_add_instrument.setEnabled(false);

                    m_btn_instrumentPlay[2].setText("Stop/Reset");
                    m_btn_instrumentPlay[2].setBackgroundResource(R.drawable.stop_button);
                }
                else if(m_btn_instrumentPlay[2].getText().toString().equals("Stop/Reset"))
                {
                    m_notes3.stopPlaying();
                    m_minikeyboard3.stopPlaying();
                    setPianoDrawableDefault(m_thirdNotes);

                    m_btn_instrumentVolume[2].setEnabled(true);
                    m_btn_instrumentDelete[2].setEnabled(true);

                    m_saveTrackBtn.setEnabled(true);
                    m_playTrackbtn.setEnabled(true);
                    m_add_instrument.setEnabled(true);

                    m_btn_instrumentPlay[2].setText("Play");
                    m_btn_instrumentPlay[2].setBackgroundResource(R.drawable.play_button);
                }


                break;
            //listener for fourth instrument's play button
            case R.id.btn_fourthInstrument:

                if(m_btn_instrumentPlay[3].getText().toString().equals("Play")) {

                    HashMap<String, String> pianoNotes4 = new HashMap<>(m_instrument4.getPianoNotes());
                    m_notes4 = new InstrumentTimeStamps(this, pianoNotes4, m_instrument4.getM_instrumentID(), m_instrument4.getM_octaves(), m_instrument4.getM_volumeSelection(), m_volume);

                    Timer a_timer = new Timer();
                    m_notes4.playInstrumentRecording(a_timer);


                    HashMap<String, String> pianoNotes4_new = new HashMap<>(m_instrument4.getPianoNotes());
                    m_minikeyboard4 = new HighlightKeys(m_fourthNotes, pianoNotes4_new, m_colorID[3]);

                    m_minikeyboard4.playButtonHighlights(a_timer);

                    m_btn_instrumentVolume[3].setEnabled(false);
                    m_btn_instrumentDelete[3].setEnabled(false);


                    m_saveTrackBtn.setEnabled(false);
                    m_playTrackbtn.setEnabled(false);
                    m_add_instrument.setEnabled(false);

                    m_btn_instrumentPlay[3].setText("Stop/Reset");
                    m_btn_instrumentPlay[3].setBackgroundResource(R.drawable.stop_button);
                }
                else if(m_btn_instrumentPlay[3].getText().toString().equals("Stop/Reset"))
                {
                    m_notes4.stopPlaying();
                    m_minikeyboard4.stopPlaying();
                    setPianoDrawableDefault(m_fourthNotes);

                    m_btn_instrumentVolume[3].setEnabled(true);
                    m_btn_instrumentDelete[3].setEnabled(true);
                    m_saveTrackBtn.setEnabled(true);
                    m_playTrackbtn.setEnabled(true);
                    m_add_instrument.setEnabled(true);

                    m_btn_instrumentPlay[3].setText("Play");
                    m_btn_instrumentPlay[3].setBackgroundResource(R.drawable.play_button);
                }


                break;
            //listener for fifth instrument's play button
            case R.id.btn_fifthInstrument:

                if(m_btn_instrumentPlay[4].getText().toString().equals("Play")) {

                    HashMap<String, String> pianoNotes5 = new HashMap<>(m_instrument5.getPianoNotes());
                    m_notes5 = new InstrumentTimeStamps(this, pianoNotes5, m_instrument5.getM_instrumentID(), m_instrument5.getM_octaves(), m_instrument5.getM_volumeSelection(), m_volume);

                    Timer a_timer = new Timer();
                    m_notes5.playInstrumentRecording(a_timer);


                    HashMap<String, String> pianoNotes5_new = new HashMap<>(m_instrument5.getPianoNotes());
                    m_minikeyboard5 = new HighlightKeys(m_fifthNotes, pianoNotes5_new, m_colorID[4]);

                    m_minikeyboard5.playButtonHighlights(a_timer);

                    m_btn_instrumentVolume[4].setEnabled(false);
                    m_btn_instrumentDelete[4].setEnabled(false);
                    m_saveTrackBtn.setEnabled(false);
                    m_playTrackbtn.setEnabled(false);
                    m_add_instrument.setEnabled(false);

                    m_btn_instrumentPlay[4].setText("Stop/Reset");
                    m_btn_instrumentPlay[4].setBackgroundResource(R.drawable.stop_button);
                }
                else if(m_btn_instrumentPlay[4].getText().toString().equals("Stop/Reset"))
                {
                    m_notes5.stopPlaying();
                    m_minikeyboard5.stopPlaying();
                    setPianoDrawableDefault(m_fifthNotes);

                    m_btn_instrumentVolume[4].setEnabled(true);
                    m_btn_instrumentDelete[4].setEnabled(true);
                    m_saveTrackBtn.setEnabled(true);
                    m_playTrackbtn.setEnabled(true);
                    m_add_instrument.setEnabled(true);

                    m_btn_instrumentPlay[4].setText("Play");
                    m_btn_instrumentPlay[4].setBackgroundResource(R.drawable.play_button);
                }


                break;

            //listener for first instrument's delete button
            case R.id.btn_delete_firstInstrument:

                //creates AlertDialog to ask user to confirm the delete of this instrument
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainTrackHome.this);
                builder.setMessage("Delete this instrument?");
                builder.setCancelable(true);
                //If user selects "Nevermind", cancel AlertDialog
                builder.setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                            }
                });
                //If user selects "Yes", create new intent to MainTrackHome and send rest of instruments, and m_numberOfInstrument-1
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent delete_instrument = new Intent(MainTrackHome.this, MainTrackHome.class);

                        //sending to new main_track_home.xml with deleted instrument
                        if(m_numberOfInstruments == 1)
                        {
                            delete_instrument.putExtra("instrumentsNumber", 0);
                            delete_instrument.putExtra("LoadedSong", false);

                        }
                        else if (m_numberOfInstruments == 2)
                        {
                            delete_instrument.putExtra("instrumentsNumber", 1);

                            delete_instrument.putExtra("Instrument1", m_instrument2);
                            delete_instrument.putExtra("LoadedSong", true);

                        }
                        else if (m_numberOfInstruments == 3)
                        {
                            delete_instrument.putExtra("instrumentsNumber", 2);

                            delete_instrument.putExtra("Instrument1", m_instrument2);
                            delete_instrument.putExtra("Instrument2", m_instrument3);
                            delete_instrument.putExtra("LoadedSong", true);
                        }
                        else if (m_numberOfInstruments == 4)
                        {
                            delete_instrument.putExtra("instrumentsNumber", 3);

                            delete_instrument.putExtra("Instrument1", m_instrument2);
                            delete_instrument.putExtra("Instrument2", m_instrument3);
                            delete_instrument.putExtra("Instrument3", m_instrument4);
                            delete_instrument.putExtra("LoadedSong", true);
                        }
                        else if (m_numberOfInstruments == 5)
                        {
                            delete_instrument.putExtra("instrumentsNumber", 4);

                            delete_instrument.putExtra("Instrument1", m_instrument2);
                            delete_instrument.putExtra("Instrument2", m_instrument3);
                            delete_instrument.putExtra("Instrument3", m_instrument4);
                            delete_instrument.putExtra("Instrument4", m_instrument5);
                            delete_instrument.putExtra("LoadedSong", true);
                        }

                        startActivity(delete_instrument);
                    }
                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                break;

            //listener for second instrument's delete button
            case R.id.btn_delete_secondInstrument:

                final AlertDialog.Builder builder2 = new AlertDialog.Builder(MainTrackHome.this);
                builder2.setMessage("Delete this instrument?");
                builder2.setCancelable(true);
                builder2.setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent delete_instrument = new Intent(MainTrackHome.this, MainTrackHome.class);


                        if (m_numberOfInstruments == 2)
                        {
                            delete_instrument.putExtra("instrumentsNumber", 1);

                            delete_instrument.putExtra("Instrument1", m_instrument1);
                            delete_instrument.putExtra("LoadedSong", true);

                        }
                        else if (m_numberOfInstruments == 3)
                        {
                            delete_instrument.putExtra("instrumentsNumber", 2);

                            delete_instrument.putExtra("Instrument1", m_instrument1);
                            delete_instrument.putExtra("Instrument2", m_instrument3);
                            delete_instrument.putExtra("LoadedSong", true);
                        }
                        else if (m_numberOfInstruments == 4)
                        {
                            delete_instrument.putExtra("instrumentsNumber", 3);

                            delete_instrument.putExtra("Instrument1", m_instrument1);
                            delete_instrument.putExtra("Instrument2", m_instrument3);
                            delete_instrument.putExtra("Instrument3", m_instrument4);
                            delete_instrument.putExtra("LoadedSong", true);
                        }
                        else if (m_numberOfInstruments == 5)
                        {
                            delete_instrument.putExtra("instrumentsNumber", 4);

                            delete_instrument.putExtra("Instrument1", m_instrument1);
                            delete_instrument.putExtra("Instrument2", m_instrument3);
                            delete_instrument.putExtra("Instrument3", m_instrument4);
                            delete_instrument.putExtra("Instrument4", m_instrument5);
                            delete_instrument.putExtra("LoadedSong", true);
                        }

                        startActivity(delete_instrument);
                    }
                });
                AlertDialog alertDialog2 = builder2.create();
                alertDialog2.show();


                break;

            //listener for third instrument's delete button
            case R.id.btn_delete_thirdInstrument:

                final AlertDialog.Builder builder3 = new AlertDialog.Builder(MainTrackHome.this);
                builder3.setMessage("Delete this instrument?");
                builder3.setCancelable(true);
                builder3.setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder3.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent delete_instrument = new Intent(MainTrackHome.this, MainTrackHome.class);


                        if (m_numberOfInstruments == 3)
                        {
                            delete_instrument.putExtra("instrumentsNumber", 2);

                            delete_instrument.putExtra("Instrument1", m_instrument1);
                            delete_instrument.putExtra("Instrument2", m_instrument2);
                            delete_instrument.putExtra("LoadedSong", true);
                        }
                        else if (m_numberOfInstruments == 4)
                        {
                            delete_instrument.putExtra("instrumentsNumber", 3);

                            delete_instrument.putExtra("Instrument1", m_instrument1);
                            delete_instrument.putExtra("Instrument2", m_instrument2);
                            delete_instrument.putExtra("Instrument3", m_instrument4);
                            delete_instrument.putExtra("LoadedSong", true);
                        }
                        else if (m_numberOfInstruments == 5)
                        {
                            delete_instrument.putExtra("instrumentsNumber", 4);

                            delete_instrument.putExtra("Instrument1", m_instrument1);
                            delete_instrument.putExtra("Instrument2", m_instrument2);
                            delete_instrument.putExtra("Instrument3", m_instrument4);
                            delete_instrument.putExtra("Instrument4", m_instrument5);
                            delete_instrument.putExtra("LoadedSong", true);
                        }

                        startActivity(delete_instrument);
                    }
                });
                AlertDialog alertDialog3 = builder3.create();
                alertDialog3.show();




                break;
            //listener for fourth instrument's delete button
            case R.id.btn_delete_fourthInstrument:


                final AlertDialog.Builder builder4 = new AlertDialog.Builder(MainTrackHome.this);
                builder4.setMessage("Delete this instrument?");
                builder4.setCancelable(true);
                builder4.setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder4.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent delete_instrument = new Intent(MainTrackHome.this, MainTrackHome.class);



                        if (m_numberOfInstruments == 4)
                        {
                            delete_instrument.putExtra("instrumentsNumber", 3);

                            delete_instrument.putExtra("Instrument1", m_instrument1);
                            delete_instrument.putExtra("Instrument2", m_instrument2);
                            delete_instrument.putExtra("Instrument3", m_instrument3);
                            delete_instrument.putExtra("LoadedSong", true);
                        }
                        else if (m_numberOfInstruments == 5)
                        {
                            delete_instrument.putExtra("instrumentsNumber", 4);

                            delete_instrument.putExtra("Instrument1", m_instrument1);
                            delete_instrument.putExtra("Instrument2", m_instrument2);
                            delete_instrument.putExtra("Instrument3", m_instrument3);
                            delete_instrument.putExtra("Instrument4", m_instrument5);
                            delete_instrument.putExtra("LoadedSong", true);
                        }

                        startActivity(delete_instrument);
                    }
                });
                AlertDialog alertDialog4 = builder4.create();
                alertDialog4.show();




                break;

            //listener for fifth instrument's delete button
            case R.id.btn_delete_fifthInstrument:

                final AlertDialog.Builder builder5 = new AlertDialog.Builder(MainTrackHome.this);
                builder5.setMessage("Delete this instrument?");
                builder5.setCancelable(true);
                builder5.setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder5.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent delete_instrument = new Intent(MainTrackHome.this, MainTrackHome.class);


                        if (m_numberOfInstruments == 5)
                        {
                            delete_instrument.putExtra("instrumentsNumber", 4);

                            delete_instrument.putExtra("Instrument1", m_instrument1);
                            delete_instrument.putExtra("Instrument2", m_instrument2);
                            delete_instrument.putExtra("Instrument3", m_instrument3);
                            delete_instrument.putExtra("Instrument4", m_instrument4);
                            delete_instrument.putExtra("LoadedSong", true);
                        }

                        startActivity(delete_instrument);
                    }
                });
                AlertDialog alertDialog5 = builder5.create();
                alertDialog5.show();

                break;

            //listener for first volume button
            case R.id.btn_volume_firstInstrument:

                //setting volume to a higher integer decreases the volume of the instrument
                //Volume 3 is highest, followed by volume 2 and volume 1
                if(m_btn_instrumentVolume[0].getText().toString().equals("Volume: 3"))
                {
                    m_instrument1.setM_volumeSelection(3);
                    m_btn_instrumentVolume[0].setText("Volume: 2");
                }
                else if(m_btn_instrumentVolume[0].getText().toString().equals("Volume: 2"))
                {
                    m_instrument1.setM_volumeSelection(6);
                    m_btn_instrumentVolume[0].setText("Volume: 1");
                }
                else if(m_btn_instrumentVolume[0].getText().toString().equals("Volume: 1"))
                {
                    m_instrument1.setM_volumeSelection(1);
                    m_btn_instrumentVolume[0].setText("Volume: 3");
                }

                break;

            //listener for second volume button
            case R.id.btn_volume_secondInstrument:

                if(m_btn_instrumentVolume[1].getText().toString().equals("Volume: 3"))
                {
                    m_instrument2.setM_volumeSelection(3);
                    m_btn_instrumentVolume[1].setText("Volume: 2");
                }
                else if(m_btn_instrumentVolume[1].getText().toString().equals("Volume: 2"))
                {
                    m_instrument2.setM_volumeSelection(6);
                    m_btn_instrumentVolume[1].setText("Volume: 1");
                }
                else if(m_btn_instrumentVolume[1].getText().toString().equals("Volume: 1"))
                {
                    m_instrument2.setM_volumeSelection(1);
                    m_btn_instrumentVolume[1].setText("Volume: 3");
                }

                break;
            //listener for third volume button
            case R.id.btn_volume_thirdInstrument:

                if(m_btn_instrumentVolume[2].getText().toString().equals("Volume: 3"))
                {
                    m_instrument3.setM_volumeSelection(3);
                    m_btn_instrumentVolume[2].setText("Volume: 2");
                }
                else if(m_btn_instrumentVolume[2].getText().toString().equals("Volume: 2"))
                {
                    m_instrument1.setM_volumeSelection(6);
                    m_btn_instrumentVolume[2].setText("Volume: 1");
                }
                else if(m_btn_instrumentVolume[2].getText().toString().equals("Volume: 1"))
                {
                    m_instrument1.setM_volumeSelection(1);
                    m_btn_instrumentVolume[2].setText("Volume: 3");
                }
                break;
            //listener for fourth volume button
            case R.id.btn_volume_fourthInstrument:

                if(m_btn_instrumentVolume[3].getText().toString().equals("Volume: 3"))
                {
                    m_instrument4.setM_volumeSelection(3);
                    m_btn_instrumentVolume[3].setText("Volume: 2");
                }
                else if(m_btn_instrumentVolume[3].getText().toString().equals("Volume: 2"))
                {
                    m_instrument4.setM_volumeSelection(6);
                    m_btn_instrumentVolume[3].setText("Volume: 1");
                }
                else if(m_btn_instrumentVolume[3].getText().toString().equals("Volume: 1"))
                {
                    m_instrument4.setM_volumeSelection(1);
                    m_btn_instrumentVolume[3].setText("Volume: 3");
                }
                break;
            //listener for fifth volume button
            case R.id.btn_volume_fifthInstrument:

                if(m_btn_instrumentVolume[4].getText().toString().equals("Volume: 3"))
                {
                    m_instrument5.setM_volumeSelection(3);
                    m_btn_instrumentVolume[4].setText("Volume: 2");
                }
                else if(m_btn_instrumentVolume[4].getText().toString().equals("Volume: 2"))
                {
                    m_instrument5.setM_volumeSelection(6);
                    m_btn_instrumentVolume[4].setText("Volume: 1");
                }
                else if(m_btn_instrumentVolume[4].getText().toString().equals("Volume: 1"))
                {
                    m_instrument5.setM_volumeSelection(1);
                    m_btn_instrumentVolume[4].setText("Volume: 3");
                }
                break;

                //Color button listener for first instrument color, changes color of highlighted notes
            case R.id.btn_color_firstInstrument:

                        //If the color ID is already green, clicking changes to purple, and id is set to 1 in minikeyboard object
                        if(m_colorID[0] == 0)
                        {
                            m_colorID[0] = 1;
                            m_minikeyboard1.setM_colorID(1);
                            m_colorInstruments[0].setBackgroundResource(R.drawable.pressed_key_purple);
                        }
                        //If the color ID is purple, clicking changes to blue, and id is set to 2 in minikeyboard object
                        else if (m_colorID[0] == 1)
                        {
                            m_colorID[0] = 2;
                            m_minikeyboard1.setM_colorID(2);
                            m_colorInstruments[0].setBackgroundResource(R.drawable.pressed_key_blue);
                        }
                        //If the color ID is blue, clicking changes to yellow, and id is set to 3 in minikeyboard object
                        else if(m_colorID[0] == 2)
                        {
                            m_colorID[0] = 3;
                            m_minikeyboard1.setM_colorID(3);
                            m_colorInstruments[0].setBackgroundResource(R.drawable.pressed_key_yellow);
                        }
                        //If the color ID is yellow, clicking changes to red, and id is set to 4 in minikeyboard object
                        else if(m_colorID[0] == 3)
                        {
                            m_colorID[0] = 4;
                            m_minikeyboard1.setM_colorID(4);
                            m_colorInstruments[0].setBackgroundResource(R.drawable.pressed_key_red);
                        }
                        //If the color ID is red, clicking changes to green, and id is set to 1 in minikeyboard object
                        else if(m_colorID[0] == 4)
                        {
                            m_colorID[0] = 0;
                            m_minikeyboard1.setM_colorID(0);
                            m_colorInstruments[0].setBackgroundResource(R.drawable.pressed_key_green);
                        }

                break;
            //Color button listener for second instrument color, changes color of highlighted notes
            //Similar documentation as first listener
            case R.id.btn_color_secondInstrument:

                if(m_colorID[1] == 0)
                {
                    m_colorID[1] = 1;
                    m_minikeyboard2.setM_colorID(1);
                    m_colorInstruments[1].setBackgroundResource(R.drawable.pressed_key_purple);
                }
                else if (m_colorID[1] == 1)
                {
                    m_colorID[1] = 2;
                    m_minikeyboard2.setM_colorID(2);
                    m_colorInstruments[1].setBackgroundResource(R.drawable.pressed_key_blue);
                }
                else if(m_colorID[1] == 2)
                {
                    m_colorID[1] = 3;
                    m_minikeyboard2.setM_colorID(3);
                    m_colorInstruments[1].setBackgroundResource(R.drawable.pressed_key_yellow);
                }
                else if(m_colorID[1] == 3)
                {
                    m_colorID[1] = 4;
                    m_minikeyboard2.setM_colorID(4);
                    m_colorInstruments[1].setBackgroundResource(R.drawable.pressed_key_red);
                }
                else if(m_colorID[1] == 4)
                {
                    m_colorID[1] = 0;
                    m_minikeyboard2.setM_colorID(0);
                    m_colorInstruments[1].setBackgroundResource(R.drawable.pressed_key_green);
                }

                break;

            case R.id.btn_color_thirdInstrument:

                if(m_colorID[2] == 0)
                {
                    m_colorID[2] = 1;
                    m_minikeyboard3.setM_colorID(1);
                    m_colorInstruments[2].setBackgroundResource(R.drawable.pressed_key_purple);
                }
                else if (m_colorID[2] == 1)
                {
                    m_colorID[2] = 2;
                    m_minikeyboard3.setM_colorID(2);
                    m_colorInstruments[2].setBackgroundResource(R.drawable.pressed_key_blue);
                }
                else if(m_colorID[2] == 2)
                {
                    m_colorID[2] = 3;
                    m_minikeyboard3.setM_colorID(3);
                    m_colorInstruments[2].setBackgroundResource(R.drawable.pressed_key_yellow);
                }
                else if(m_colorID[2] == 3)
                {
                    m_colorID[2] = 4;
                    m_minikeyboard3.setM_colorID(4);
                    m_colorInstruments[2].setBackgroundResource(R.drawable.pressed_key_red);
                }
                else if(m_colorID[2] == 4)
                {
                    m_colorID[2] = 0;
                    m_minikeyboard3.setM_colorID(0);
                    m_colorInstruments[2].setBackgroundResource(R.drawable.pressed_key_green);
                }

                break;

            case R.id.btn_color_fourthInstrument:

                if(m_colorID[3] == 0)
                {
                    m_colorID[3] = 1;
                    m_minikeyboard4.setM_colorID(1);
                    m_colorInstruments[3].setBackgroundResource(R.drawable.pressed_key_purple);
                }
                else if (m_colorID[3] == 1)
                {
                    m_colorID[3] = 2;
                    m_minikeyboard4.setM_colorID(2);
                    m_colorInstruments[3].setBackgroundResource(R.drawable.pressed_key_blue);
                }
                else if(m_colorID[3] == 2)
                {
                    m_colorID[3] = 3;
                    m_minikeyboard4.setM_colorID(3);
                    m_colorInstruments[3].setBackgroundResource(R.drawable.pressed_key_yellow);
                }
                else if(m_colorID[3] == 3)
                {
                    m_colorID[3] = 4;
                    m_minikeyboard4.setM_colorID(4);
                    m_colorInstruments[3].setBackgroundResource(R.drawable.pressed_key_red);
                }
                else if(m_colorID[3] == 4)
                {
                    m_colorID[3] = 0;
                    m_minikeyboard4.setM_colorID(0);
                    m_colorInstruments[3].setBackgroundResource(R.drawable.pressed_key_green);
                }

                break;

            case R.id.btn_color_fifthInstrument:

                if(m_colorID[4] == 0)
                {
                    m_colorID[4] = 1;
                    m_minikeyboard5.setM_colorID(1);
                    m_colorInstruments[4].setBackgroundResource(R.drawable.pressed_key_purple);
                }
                else if (m_colorID[4] == 1)
                {
                    m_colorID[4] = 2;
                    m_minikeyboard5.setM_colorID(2);
                    m_colorInstruments[4].setBackgroundResource(R.drawable.pressed_key_blue);
                }
                else if(m_colorID[4] == 2)
                {
                    m_colorID[4] = 3;
                    m_minikeyboard5.setM_colorID(3);
                    m_colorInstruments[4].setBackgroundResource(R.drawable.pressed_key_yellow);
                }
                else if(m_colorID[4] == 3)
                {
                    m_colorID[4] = 4;
                    m_minikeyboard5.setM_colorID(4);
                    m_colorInstruments[4].setBackgroundResource(R.drawable.pressed_key_red);
                }
                else if(m_colorID[4] == 4)
                {
                    m_colorID[4] = 0;
                    m_minikeyboard5.setM_colorID(0);
                    m_colorInstruments[4].setBackgroundResource(R.drawable.pressed_key_green);
                }

                break;

            //Button that watches for save track
            case R.id.btn_saveTrack:

                AlertDialog.Builder builder6 = new AlertDialog.Builder(MainTrackHome.this);
                builder6.setTitle("File Name to Save:");

                // Set up the input
                final EditText input = new EditText(MainTrackHome.this);
                // Specify the type of input expected; this is simple text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder6.setView(input);

                // Set up the buttons for confirming the save
                builder6.setPositiveButton("Save Track", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_fileName = input.getText().toString();

                        //calls saveTrack to set up String text file of all instruments and their specifications
                        String file = saveTrack();

                        try
                        {
                            //sets file output stream to write to text file
                            FileOutputStream fos = openFileOutput(m_fileName + ".txt", MODE_PRIVATE);
                            //writes the file
                            fos.write(file.getBytes());
                            fos.close();

                            Toast.makeText(MainTrackHome.this, "Track Saved", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainTrackHome.this, MainActivity.class);

                            MainTrackHome.this.startActivity(intent);

                        }
                        catch (Exception e)
                        {
                            System.out.println(e);
                        }



                    }
                });
                //cancel button
                builder6.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog6 = builder6.create();
                alertDialog6.show();



                break;

                //listener for user to play all instruments
            case R.id.btn_playTrack:

                if(m_playTrackbtn.getText().toString().equals("Play Track")) {

                    //sets buttons to disabled while playing
                    m_saveTrackBtn.setEnabled(false);


                    setAllDisabled();

                    if (m_numberOfInstruments == 0) {
                        //cant do it
                    } else if (m_numberOfInstruments == 1) {
                        //Creates a copy of instrument to play it without deleting the HashMap
                        //Also plays the highlights of minikeyboard
                        HashMap<String, String> instrument1Copy = new HashMap<>(m_instrument1.getPianoNotes());
                        piano1Copy = new InstrumentTimeStamps(this, instrument1Copy, m_instrument1.getM_instrumentID(), m_instrument1.getM_octaves(), m_instrument1.getM_volumeSelection(), m_volume);
                        Timer a_timer = new Timer();

                        piano1Copy.playInstrumentRecording(a_timer);

                        HashMap<String, String> instrument1Copy_new = new HashMap<>(m_instrument1.getPianoNotes());

                        m_minikeyboard1 = new HighlightKeys(m_firstNotes, instrument1Copy_new, m_colorID[0]);

                        m_minikeyboard1.playButtonHighlights(a_timer);



                    } else if (m_numberOfInstruments == 2) {
                        //Creates a copy of instruments to play them without deleting their HashMap
                        //Also plays the highlights of mini keyboards
                        HashMap<String, String> instrument1Copy = new HashMap<>(m_instrument1.getPianoNotes());
                        piano1Copy = new InstrumentTimeStamps(this, instrument1Copy, m_instrument1.getM_instrumentID(), m_instrument1.getM_octaves(), m_instrument1.getM_volumeSelection(), m_volume);

                        HashMap<String, String> instrument2Copy = new HashMap<>(m_instrument2.getPianoNotes());
                        piano2Copy = new InstrumentTimeStamps(this, instrument2Copy, m_instrument2.getM_instrumentID(), m_instrument2.getM_octaves(), m_instrument2.getM_volumeSelection(), m_volume);

                        Timer a_timer = new Timer();

                        piano1Copy.playInstrumentRecording(a_timer);
                        piano2Copy.playInstrumentRecording(a_timer);

                        HashMap<String, String> instrument1Copy_new = new HashMap<>(m_instrument1.getPianoNotes());
                        HashMap<String, String> instrument2Copy_new = new HashMap<>(m_instrument2.getPianoNotes());


                        m_minikeyboard1 = new HighlightKeys(m_firstNotes, instrument1Copy_new, m_colorID[0]);
                        m_minikeyboard2 = new HighlightKeys(m_secondNotes, instrument2Copy_new, m_colorID[1]);



                        m_minikeyboard1.playButtonHighlights(a_timer);
                        m_minikeyboard2.playButtonHighlights(a_timer);



                    } else if (m_numberOfInstruments == 3) {
                        //Creates a copy of instruments to play them without deleting their HashMap
                        //Also plays the highlights of mini keyboards
                        HashMap<String, String> instrument1Copy = new HashMap<>(m_instrument1.getPianoNotes());
                        piano1Copy = new InstrumentTimeStamps(this, instrument1Copy, m_instrument1.getM_instrumentID(), m_instrument1.getM_octaves(), m_instrument1.getM_volumeSelection(), m_volume);

                        HashMap<String, String> instrument2Copy = new HashMap<>(m_instrument2.getPianoNotes());
                        piano2Copy = new InstrumentTimeStamps(this, instrument2Copy, m_instrument2.getM_instrumentID(), m_instrument2.getM_octaves(), m_instrument2.getM_volumeSelection(), m_volume);

                        HashMap<String, String> instrument3Copy = new HashMap<>(m_instrument3.getPianoNotes());
                        piano3Copy = new InstrumentTimeStamps(this, instrument3Copy, m_instrument3.getM_instrumentID(), m_instrument3.getM_octaves(), m_instrument3.getM_volumeSelection(), m_volume);

                        Timer a_timer = new Timer();

                        piano1Copy.playInstrumentRecording(a_timer);
                        piano2Copy.playInstrumentRecording(a_timer);
                        piano3Copy.playInstrumentRecording(a_timer);

                        HashMap<String, String> instrument1Copy_new = new HashMap<>(m_instrument1.getPianoNotes());
                        HashMap<String, String> instrument2Copy_new = new HashMap<>(m_instrument2.getPianoNotes());
                        HashMap<String, String>  instrument3Copy_new = new HashMap<>(m_instrument3.getPianoNotes());

                        m_minikeyboard1 = new HighlightKeys(m_firstNotes, instrument1Copy_new, m_colorID[0]);
                        m_minikeyboard2 = new HighlightKeys(m_secondNotes, instrument2Copy_new, m_colorID[1]);
                        m_minikeyboard3 = new HighlightKeys(m_thirdNotes, instrument3Copy_new, m_colorID[2]);



                        m_minikeyboard1.playButtonHighlights(a_timer);
                        m_minikeyboard2.playButtonHighlights(a_timer);
                        m_minikeyboard3.playButtonHighlights(a_timer);


                    } else if (m_numberOfInstruments == 4) {
                        //Creates a copy of instruments to play them without deleting their HashMap
                        //Also plays the highlights of mini keyboards
                        HashMap<String, String> instrument1Copy = new HashMap<>(m_instrument1.getPianoNotes());
                        piano1Copy = new InstrumentTimeStamps(this, instrument1Copy, m_instrument1.getM_instrumentID(), m_instrument1.getM_octaves(), m_instrument1.getM_volumeSelection(), m_volume);

                        HashMap<String, String> instrument2Copy = new HashMap<>(m_instrument2.getPianoNotes());
                        piano2Copy = new InstrumentTimeStamps(this, instrument2Copy, m_instrument2.getM_instrumentID(), m_instrument2.getM_octaves(), m_instrument2.getM_volumeSelection(), m_volume);

                        HashMap<String, String> instrument3Copy = new HashMap<>(m_instrument3.getPianoNotes());
                        piano3Copy = new InstrumentTimeStamps(this, instrument3Copy, m_instrument3.getM_instrumentID(), m_instrument3.getM_octaves(), m_instrument3.getM_volumeSelection(), m_volume);

                        HashMap<String, String> instrument4Copy = new HashMap<>(m_instrument4.getPianoNotes());
                        piano4Copy = new InstrumentTimeStamps(this, instrument4Copy, m_instrument4.getM_instrumentID(), m_instrument4.getM_octaves(), m_instrument4.getM_volumeSelection(), m_volume);

                        Timer a_timer = new Timer();

                        piano1Copy.playInstrumentRecording(a_timer);
                        piano2Copy.playInstrumentRecording(a_timer);
                        piano3Copy.playInstrumentRecording(a_timer);
                        piano4Copy.playInstrumentRecording(a_timer);


                        HashMap<String, String> instrument1Copy_new = new HashMap<>(m_instrument1.getPianoNotes());
                        HashMap<String, String> instrument2Copy_new = new HashMap<>(m_instrument2.getPianoNotes());
                        HashMap<String, String>  instrument3Copy_new = new HashMap<>(m_instrument3.getPianoNotes());
                        HashMap<String, String> instrument4Copy_new = new HashMap<>(m_instrument4.getPianoNotes());

                        m_minikeyboard1 = new HighlightKeys(m_firstNotes, instrument1Copy_new, m_colorID[0]);
                        m_minikeyboard2 = new HighlightKeys(m_secondNotes, instrument2Copy_new, m_colorID[1]);
                        m_minikeyboard3 = new HighlightKeys(m_thirdNotes, instrument3Copy_new, m_colorID[2]);
                        m_minikeyboard4 = new HighlightKeys(m_fourthNotes, instrument4Copy_new, m_colorID[3]);


                        m_minikeyboard1.playButtonHighlights(a_timer);
                        m_minikeyboard2.playButtonHighlights(a_timer);
                        m_minikeyboard3.playButtonHighlights(a_timer);
                        m_minikeyboard4.playButtonHighlights(a_timer);


                    } else if (m_numberOfInstruments == 5) {
                        //Creates a copy of instruments to play them without deleting their HashMap
                        //Also plays the highlights of mini keyboards


                        HashMap<String, String> instrument1Copy = new HashMap<>(m_instrument1.getPianoNotes());
                        piano1Copy = new InstrumentTimeStamps(this, instrument1Copy, m_instrument1.getM_instrumentID(), m_instrument1.getM_octaves(), m_instrument1.getM_volumeSelection(), m_volume);

                        HashMap<String, String> instrument2Copy = new HashMap<>(m_instrument2.getPianoNotes());
                        piano2Copy = new InstrumentTimeStamps(this, instrument2Copy, m_instrument2.getM_instrumentID(), m_instrument2.getM_octaves(), m_instrument2.getM_volumeSelection(), m_volume);

                        HashMap<String, String> instrument3Copy = new HashMap<>(m_instrument3.getPianoNotes());
                        piano3Copy = new InstrumentTimeStamps(this, instrument3Copy, m_instrument3.getM_instrumentID(), m_instrument3.getM_octaves(), m_instrument3.getM_volumeSelection(), m_volume);

                        HashMap<String, String> instrument4Copy = new HashMap<>(m_instrument4.getPianoNotes());
                        piano4Copy = new InstrumentTimeStamps(this, instrument4Copy, m_instrument4.getM_instrumentID(), m_instrument4.getM_octaves(), m_instrument4.getM_volumeSelection(), m_volume);

                        HashMap<String, String> instrument5Copy = new HashMap<>(m_instrument5.getPianoNotes());
                        piano5Copy = new InstrumentTimeStamps(this, instrument5Copy, m_instrument5.getM_instrumentID(), m_instrument5.getM_octaves(), m_instrument5.getM_volumeSelection(), m_volume);

                        Timer a_timer = new Timer();

                        piano1Copy.playInstrumentRecording(a_timer);
                        piano2Copy.playInstrumentRecording(a_timer);
                        piano3Copy.playInstrumentRecording(a_timer);
                        piano4Copy.playInstrumentRecording(a_timer);
                        piano5Copy.playInstrumentRecording(a_timer);


                        HashMap<String, String> instrument1Copy_new = new HashMap<>(m_instrument1.getPianoNotes());
                        HashMap<String, String> instrument2Copy_new = new HashMap<>(m_instrument2.getPianoNotes());
                        HashMap<String, String>  instrument3Copy_new = new HashMap<>(m_instrument3.getPianoNotes());
                        HashMap<String, String> instrument4Copy_new = new HashMap<>(m_instrument4.getPianoNotes());
                        HashMap<String, String> instrument5Copy_new = new HashMap<>(m_instrument5.getPianoNotes());



                        m_minikeyboard1 = new HighlightKeys(m_firstNotes, instrument1Copy_new, m_colorID[0]);
                        m_minikeyboard2 = new HighlightKeys(m_secondNotes, instrument2Copy_new, m_colorID[1]);
                        m_minikeyboard3 = new HighlightKeys(m_thirdNotes, instrument3Copy_new, m_colorID[2]);
                        m_minikeyboard4 = new HighlightKeys(m_fourthNotes, instrument4Copy_new, m_colorID[3]);
                        m_minikeyboard5 = new HighlightKeys(m_fifthNotes, instrument5Copy_new, m_colorID[4]);


                        m_minikeyboard1.playButtonHighlights(a_timer);
                        m_minikeyboard2.playButtonHighlights(a_timer);
                        m_minikeyboard3.playButtonHighlights(a_timer);
                        m_minikeyboard4.playButtonHighlights(a_timer);
                        m_minikeyboard5.playButtonHighlights(a_timer);




                    }
                    m_playTrackbtn.setText("Stop/Reset Track");
                }
                else if (m_playTrackbtn.getText().toString().equals("Stop/Reset Track"))
                {
                    m_saveTrackBtn.setEnabled(false);
                    //Stops all playing instruments depending on number of existing
                    //also stops all highlights of minikeyboard and sets drawables back to default
                    if(m_numberOfInstruments == 1)
                    {
                        piano1Copy.stopPlaying();

                        m_minikeyboard1.stopPlaying();

                        setPianoDrawableDefault(m_firstNotes);

                    }
                    else if(m_numberOfInstruments == 2)
                    {
                        piano1Copy.stopPlaying();
                        piano2Copy.stopPlaying();

                        m_minikeyboard1.stopPlaying();
                        m_minikeyboard2.stopPlaying();


                        setPianoDrawableDefault(m_firstNotes);
                        setPianoDrawableDefault(m_secondNotes);

                    }
                    else if(m_numberOfInstruments == 3)
                    {
                        piano1Copy.stopPlaying();
                        piano2Copy.stopPlaying();
                        piano3Copy.stopPlaying();

                        m_minikeyboard1.stopPlaying();
                        m_minikeyboard2.stopPlaying();
                        m_minikeyboard3.stopPlaying();

                        setPianoDrawableDefault(m_firstNotes);
                        setPianoDrawableDefault(m_secondNotes);
                        setPianoDrawableDefault(m_thirdNotes);

                    }
                    else if(m_numberOfInstruments == 4)
                    {
                        piano1Copy.stopPlaying();
                        piano2Copy.stopPlaying();
                        piano3Copy.stopPlaying();
                        piano4Copy.stopPlaying();

                        m_minikeyboard1.stopPlaying();
                        m_minikeyboard2.stopPlaying();
                        m_minikeyboard3.stopPlaying();
                        m_minikeyboard4.stopPlaying();

                        setPianoDrawableDefault(m_firstNotes);
                        setPianoDrawableDefault(m_secondNotes);
                        setPianoDrawableDefault(m_thirdNotes);
                        setPianoDrawableDefault(m_fourthNotes);

                    }
                    else if(m_numberOfInstruments == 5)
                    {
                        piano1Copy.stopPlaying();
                        piano2Copy.stopPlaying();
                        piano3Copy.stopPlaying();
                        piano4Copy.stopPlaying();
                        piano5Copy.stopPlaying();


                        m_minikeyboard1.stopPlaying();
                        m_minikeyboard2.stopPlaying();
                        m_minikeyboard3.stopPlaying();
                        m_minikeyboard4.stopPlaying();
                        m_minikeyboard5.stopPlaying();


                        setPianoDrawableDefault(m_firstNotes);
                        setPianoDrawableDefault(m_secondNotes);
                        setPianoDrawableDefault(m_thirdNotes);
                        setPianoDrawableDefault(m_fourthNotes);
                        setPianoDrawableDefault(m_fifthNotes);

                    }
                    m_playTrackbtn.setText("Play Track");
                    setAllEnabled();
                }

        }
    }



    /*
    NAME

        playPianoHighlights-->

    SYNOPSIS

        public void playPianoHighlights(final Button a_highlights[], final HashMap<String, String> a_notes)

        a_highlights[] -> holds buttons corresponding to appropriate mini virtual keyboard for an instrument
        a_notes[] -> holds HashMap of recorded notes from instrument

    DESCRIPTION

        Method used to highlight the mini virtual keyboards based on the instrument recordings of InstrumentTimeStamps objects.
        a_highlights[] and a_notes are declared final in order to be used inside TimerTask.
        Inside this function, a new timer is created. The TimerTask will run every 10 milliseconds.
        Through their functions, each note is called every 10 milliseconds.
        Inside these functions, their corresponding HashMap key-value pairs are checked to see if m_highlightedSecondsPassed equals the first number of their value String.
        If so, the corresponding piano note is highlighted green if it was pressed, and white/black if it was released.
        For example, for the HashMap key "lowC", the value may be:
                "22-26,33-78,"
            When m_highlightedSecondsPassed equals 22, the highlight_c() function will change the low C button to green.
            Along with this, "22" will be removed from the value. The function will continue getting called, and when m_highlightedSecondsPassed equals 26, the highlight_c() function will change the color to default.
            The same will happen to the "33-78" times that were recorded.
        Once all note values equal ":", the timer is cancelled and the function is done.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */

    /*
    NAME

        initializePianoButtons-->

    SYNOPSIS

        public void initializePianoButtons()

    DESCRIPTION

        Used to set the member button variables of the mini keyboards for each instrument to their corresponding notes.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void initializePianoButtons()
    {
        //Setting all appropriate button IDs of mini keyboards
        m_firstNotes[0] = findViewById(R.id.firstLowC);
        m_firstNotes[1] = findViewById(R.id.firstLowCSharp);
        m_firstNotes[2] = findViewById(R.id.firstLowD);
        m_firstNotes[3] = findViewById(R.id.firstLowDSharp);
        m_firstNotes[4] = findViewById(R.id.firstLowE);
        m_firstNotes[5] = findViewById(R.id.firstLowF);
        m_firstNotes[6] = findViewById(R.id.firstLowFSharp);
        m_firstNotes[7] = findViewById(R.id.firstLowG);
        m_firstNotes[8] = findViewById(R.id.firstLowGSharp);
        m_firstNotes[9] = findViewById(R.id.firstLowA);
        m_firstNotes[10] = findViewById(R.id.firstLowASharp);
        m_firstNotes[11] = findViewById(R.id.firstLowB);
        m_firstNotes[12] = findViewById(R.id.firstHighC);
        m_firstNotes[13] = findViewById(R.id.firstHighCSharp);
        m_firstNotes[14] = findViewById(R.id.firstHighD);
        m_firstNotes[15] = findViewById(R.id.firstHighDSharp);
        m_firstNotes[16] = findViewById(R.id.firstHighE);
        m_firstNotes[17] = findViewById(R.id.firstHighF);
        m_firstNotes[18] = findViewById(R.id.firstHighFSharp);
        m_firstNotes[19] = findViewById(R.id.firstHighG);
        m_firstNotes[20] = findViewById(R.id.firstHighGSharp);
        m_firstNotes[21] = findViewById(R.id.firstHighA);
        m_firstNotes[22] = findViewById(R.id.firstHighASharp);
        m_firstNotes[23] = findViewById(R.id.firstHighB);

        for(int i = 0; i < 24; i++)
        {
            m_firstNotes[i].setEnabled(false);
        }

        m_secondNotes[0] = findViewById(R.id.secondLowC);
        m_secondNotes[1] = findViewById(R.id.secondLowCSharp);
        m_secondNotes[2] = findViewById(R.id.secondLowD);
        m_secondNotes[3] = findViewById(R.id.secondLowDSharp);
        m_secondNotes[4] = findViewById(R.id.secondLowE);
        m_secondNotes[5] = findViewById(R.id.secondLowF);
        m_secondNotes[6] = findViewById(R.id.secondLowFSharp);
        m_secondNotes[7] = findViewById(R.id.secondLowG);
        m_secondNotes[8] = findViewById(R.id.secondLowGSharp);
        m_secondNotes[9] = findViewById(R.id.secondLowA);
        m_secondNotes[10] = findViewById(R.id.secondLowASharp);
        m_secondNotes[11] = findViewById(R.id.secondLowB);
        m_secondNotes[12] = findViewById(R.id.secondHighC);
        m_secondNotes[13] = findViewById(R.id.secondHighCSharp);
        m_secondNotes[14] = findViewById(R.id.secondHighD);
        m_secondNotes[15] = findViewById(R.id.secondHighDSharp);
        m_secondNotes[16] = findViewById(R.id.secondHighE);
        m_secondNotes[17] = findViewById(R.id.secondHighF);
        m_secondNotes[18] = findViewById(R.id.secondHighFSharp);
        m_secondNotes[19] = findViewById(R.id.secondHighG);
        m_secondNotes[20] = findViewById(R.id.secondHighGSharp);
        m_secondNotes[21] = findViewById(R.id.secondHighA);
        m_secondNotes[22] = findViewById(R.id.secondHighASharp);
        m_secondNotes[23] = findViewById(R.id.secondHighB);

        for(int i = 0; i < 24; i++)
        {
            m_secondNotes[i].setEnabled(false);
        }

        m_thirdNotes[0] = findViewById(R.id.thirdLowC);
        m_thirdNotes[1] = findViewById(R.id.thirdLowCSharp);
        m_thirdNotes[2] = findViewById(R.id.thirdLowD);
        m_thirdNotes[3] = findViewById(R.id.thirdLowDSharp);
        m_thirdNotes[4] = findViewById(R.id.thirdLowE);
        m_thirdNotes[5] = findViewById(R.id.thirdLowF);
        m_thirdNotes[6] = findViewById(R.id.thirdLowFSharp);
        m_thirdNotes[7] = findViewById(R.id.thirdLowG);
        m_thirdNotes[8] = findViewById(R.id.thirdLowGSharp);
        m_thirdNotes[9] = findViewById(R.id.thirdLowA);
        m_thirdNotes[10] = findViewById(R.id.thirdLowASharp);
        m_thirdNotes[11] = findViewById(R.id.thirdLowB);
        m_thirdNotes[12] = findViewById(R.id.thirdHighC);
        m_thirdNotes[13] = findViewById(R.id.thirdHighCSharp);
        m_thirdNotes[14] = findViewById(R.id.thirdHighD);
        m_thirdNotes[15] = findViewById(R.id.thirdHighDSharp);
        m_thirdNotes[16] = findViewById(R.id.thirdHighE);
        m_thirdNotes[17] = findViewById(R.id.thirdHighF);
        m_thirdNotes[18] = findViewById(R.id.thirdHighFSharp);
        m_thirdNotes[19] = findViewById(R.id.thirdHighG);
        m_thirdNotes[20] = findViewById(R.id.thirdHighGSharp);
        m_thirdNotes[21] = findViewById(R.id.thirdHighA);
        m_thirdNotes[22] = findViewById(R.id.thirdHighASharp);
        m_thirdNotes[23] = findViewById(R.id.thirdHighB);

        for(int i = 0; i < 24; i++)
        {
            m_thirdNotes[i].setEnabled(false);
        }

        m_fourthNotes[0] = findViewById(R.id.fourthLowC);
        m_fourthNotes[1] = findViewById(R.id.fourthLowCSharp);
        m_fourthNotes[2] = findViewById(R.id.fourthLowD);
        m_fourthNotes[3] = findViewById(R.id.fourthLowDSharp);
        m_fourthNotes[4] = findViewById(R.id.fourthLowE);
        m_fourthNotes[5] = findViewById(R.id.fourthLowF);
        m_fourthNotes[6] = findViewById(R.id.fourthLowFSharp);
        m_fourthNotes[7] = findViewById(R.id.fourthLowG);
        m_fourthNotes[8] = findViewById(R.id.fourthLowGSharp);
        m_fourthNotes[9] = findViewById(R.id.fourthLowA);
        m_fourthNotes[10] = findViewById(R.id.fourthLowASharp);
        m_fourthNotes[11] = findViewById(R.id.fourthLowB);
        m_fourthNotes[12] = findViewById(R.id.fourthHighC);
        m_fourthNotes[13] = findViewById(R.id.fourthHighCSharp);
        m_fourthNotes[14] = findViewById(R.id.fourthHighD);
        m_fourthNotes[15] = findViewById(R.id.fourthHighDSharp);
        m_fourthNotes[16] = findViewById(R.id.fourthHighE);
        m_fourthNotes[17] = findViewById(R.id.fourthHighF);
        m_fourthNotes[18] = findViewById(R.id.fourthHighFSharp);
        m_fourthNotes[19] = findViewById(R.id.fourthHighG);
        m_fourthNotes[20] = findViewById(R.id.fourthHighGSharp);
        m_fourthNotes[21] = findViewById(R.id.fourthHighA);
        m_fourthNotes[22] = findViewById(R.id.fourthHighASharp);
        m_fourthNotes[23] = findViewById(R.id.fourthHighB);

        for(int i = 0; i < 24; i++)
        {
            m_fourthNotes[i].setEnabled(false);
        }

        m_fifthNotes[0] = findViewById(R.id.fifthLowC);
        m_fifthNotes[1] = findViewById(R.id.fifthLowCSharp);
        m_fifthNotes[2] = findViewById(R.id.fifthLowD);
        m_fifthNotes[3] = findViewById(R.id.fifthLowDSharp);
        m_fifthNotes[4] = findViewById(R.id.fifthLowE);
        m_fifthNotes[5] = findViewById(R.id.fifthLowF);
        m_fifthNotes[6] = findViewById(R.id.fifthLowFSharp);
        m_fifthNotes[7] = findViewById(R.id.fifthLowG);
        m_fifthNotes[8] = findViewById(R.id.fifthLowGSharp);
        m_fifthNotes[9] = findViewById(R.id.fifthLowA);
        m_fifthNotes[10] = findViewById(R.id.fifthLowASharp);
        m_fifthNotes[11] = findViewById(R.id.fifthLowB);
        m_fifthNotes[12] = findViewById(R.id.fifthHighC);
        m_fifthNotes[13] = findViewById(R.id.fifthHighCSharp);
        m_fifthNotes[14] = findViewById(R.id.fifthHighD);
        m_fifthNotes[15] = findViewById(R.id.fifthHighDSharp);
        m_fifthNotes[16] = findViewById(R.id.fifthHighE);
        m_fifthNotes[17] = findViewById(R.id.fifthHighF);
        m_fifthNotes[18] = findViewById(R.id.fifthHighFSharp);
        m_fifthNotes[19] = findViewById(R.id.fifthHighG);
        m_fifthNotes[20] = findViewById(R.id.fifthHighGSharp);
        m_fifthNotes[21] = findViewById(R.id.fifthHighA);
        m_fifthNotes[22] = findViewById(R.id.fifthHighASharp);
        m_fifthNotes[23] = findViewById(R.id.fifthHighB);

        for(int i = 0; i < 24; i++)
        {
            m_fifthNotes[i].setEnabled(false);
        }

    }
    /*
    NAME

        initializeButtonVisiblity-->

    SYNOPSIS

        public void initializeButtonVisiblity()

    DESCRIPTION

        Used to set the button functionality to visible or invisible depending on the number of instruments recorded.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void setAllDisabled()
    {
        for(int i =0; i < 5; i++)
        {
            m_btn_instrumentPlay[i].setEnabled(false);
            m_btn_instrumentDelete[i].setEnabled(false);
            m_btn_instrumentDelete[i].setEnabled(false);

        }



        m_add_instrument.setEnabled(false);

    }
    public void setAllEnabled()
    {
        for(int i =0; i < 5; i++)
        {
            m_btn_instrumentPlay[i].setEnabled(true);
            m_btn_instrumentDelete[i].setEnabled(true);
            m_btn_instrumentVolume[i].setEnabled(true);
        }


        m_add_instrument.setEnabled(true);

    }

    public void initializeButtonVisiblity()
    {
        //Setting visiblilty of instrument functionality buttons depending on number of instruments.
        if(m_numberOfInstruments == 0)
        {
            for(int i =0; i < 5; i++)
            {
                m_btn_instrumentPlay[i].setVisibility(View.INVISIBLE);
                m_btn_instrumentDelete[i].setVisibility(View.INVISIBLE);
                m_btn_instrumentVolume[i].setVisibility(View.INVISIBLE);
            }





        }
        else if(m_numberOfInstruments == 1)
        {

            for(int i =1; i < 5; i++)
            {
                m_btn_instrumentPlay[i].setVisibility(View.INVISIBLE);
                m_btn_instrumentDelete[i].setVisibility(View.INVISIBLE);
                m_btn_instrumentVolume[i].setVisibility(View.INVISIBLE);
            }



        }
        else if(m_numberOfInstruments == 2)
        {

            for(int i =2; i < 5; i++)
            {
                m_btn_instrumentPlay[i].setVisibility(View.INVISIBLE);
                m_btn_instrumentDelete[i].setVisibility(View.INVISIBLE);
                m_btn_instrumentVolume[i].setVisibility(View.INVISIBLE);
            }


        }
        else if(m_numberOfInstruments == 3)
        {

            for(int i =3; i < 5; i++)
            {
                m_btn_instrumentPlay[i].setVisibility(View.INVISIBLE);
                m_btn_instrumentDelete[i].setVisibility(View.INVISIBLE);
                m_btn_instrumentVolume[i].setVisibility(View.INVISIBLE);
            }


        }
        else if(m_numberOfInstruments == 4)
        {

            m_btn_instrumentPlay[4].setVisibility(View.INVISIBLE);

            m_btn_instrumentDelete[4].setVisibility(View.INVISIBLE);

            m_btn_instrumentVolume[4].setVisibility(View.INVISIBLE);

        }
        else if(m_numberOfInstruments == 5)
        {

        }
    }


    /*
    NAME

        initializeInstrumentLogos-->

    SYNOPSIS

        public void initializeInstrumentLogos()

    DESCRIPTION

        Used to set the images of the logos of each recorded instrument based on their instrument ID, as well as the number of instruments.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void initializeInstrumentLogos() {

        //To recall, instrument IDs go as follows: 0 -> piano, 1 -> guitar, 2 -electric piano
        //Setting all backgrounds of m_instrumentIcons based on their instruments, as well as m_instrumentText
        if (m_numberOfInstruments > 0)
        {
            if (m_instrument1.getM_instrumentID() == 0)
            {
                m_instrumentIcons[0].setBackgroundResource(R.drawable.piano_icon);
                m_instrumentText[0].setText("Piano");
            }
            else if (m_instrument1.getM_instrumentID() == 1)
            {
                m_instrumentIcons[0].setBackgroundResource(R.drawable.guitar_icon);
                m_instrumentText[0].setText("Guitar");
            }
            else if (m_instrument1.getM_instrumentID() == 2)
            {
                m_instrumentIcons[0].setBackgroundResource(R.drawable.electric_icon);
                m_instrumentText[0].setText("Electric");
            }
            if (m_numberOfInstruments > 1) {

                if (m_instrument2.getM_instrumentID() == 0)
                {
                    m_instrumentIcons[1].setBackgroundResource(R.drawable.piano_icon);
                    m_instrumentText[1].setText("Piano");
                }
                else if (m_instrument2.getM_instrumentID() == 1)
                {
                    m_instrumentIcons[1].setBackgroundResource(R.drawable.guitar_icon);
                    m_instrumentText[1].setText("Guitar");
                }
                else if (m_instrument2.getM_instrumentID() == 2)
                {
                    m_instrumentIcons[1].setBackgroundResource(R.drawable.electric_icon);
                    m_instrumentText[1].setText("Electric");
                }

                if (m_numberOfInstruments > 2)
                {
                    if (m_instrument3.getM_instrumentID() == 0)
                    {
                        m_instrumentIcons[2].setBackgroundResource(R.drawable.piano_icon);
                        m_instrumentText[2].setText("Piano");
                    }
                    else if (m_instrument3.getM_instrumentID() == 1)
                    {
                        m_instrumentIcons[2].setBackgroundResource(R.drawable.guitar_icon);
                        m_instrumentText[2].setText("Guitar");
                    }
                    else if (m_instrument3.getM_instrumentID() == 2)
                    {
                        m_instrumentIcons[2].setBackgroundResource(R.drawable.electric_icon);
                        m_instrumentText[2].setText("Electric");
                    }

                    if (m_numberOfInstruments > 3)
                    {
                        if (m_instrument4.getM_instrumentID() == 0)
                        {
                            m_instrumentIcons[3].setBackgroundResource(R.drawable.piano_icon);
                            m_instrumentText[3].setText("Piano");
                        }
                        else if (m_instrument4.getM_instrumentID() == 1)
                        {
                            m_instrumentIcons[3].setBackgroundResource(R.drawable.guitar_icon);
                            m_instrumentText[3].setText("Guitar");
                        }

                        else if (m_instrument4.getM_instrumentID() == 2)
                        {
                            m_instrumentIcons[3].setBackgroundResource(R.drawable.electric_icon);
                            m_instrumentText[3].setText("Electric");
                        }

                        if (m_numberOfInstruments > 4)
                        {
                            if (m_instrument5.getM_instrumentID() == 0)
                            {
                                m_instrumentIcons[4].setBackgroundResource(R.drawable.piano_icon);
                                m_instrumentText[4].setText("Piano");
                            }
                            else if (m_instrument5.getM_instrumentID() == 1)
                            {
                                m_instrumentIcons[4].setBackgroundResource(R.drawable.guitar_icon);
                                m_instrumentText[4].setText("Guitar");
                            }
                            else if (m_instrument5.getM_instrumentID() == 2)
                            {
                                m_instrumentIcons[4].setBackgroundResource(R.drawable.electric_icon);
                                m_instrumentText[4].setText("Electric");
                            }
                        }

                    }

                }
            }
        }

    }
    /*
    NAME

        hidePianoButtons-->

    SYNOPSIS

        public void hidePianoButtons(int a_numOf_Instruments)

        a_numOf_Instruments -> holds the number of instruments current recorded

    DESCRIPTION

        Hides the virtual mini keyboards based on the amount of existing instruments.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void hidePianoButtons(int a_numOf_Instruments)
    {
        //Based on number of instruments existing, sets keyboard buttons to invisible
        if(a_numOf_Instruments == 0)
        {
            for(int i = 0; i < 24; i++)
            {
                m_firstNotes[i].setVisibility(View.INVISIBLE);
                m_secondNotes[i].setVisibility(View.INVISIBLE);
                m_thirdNotes[i].setVisibility(View.INVISIBLE);
                m_fourthNotes[i].setVisibility(View.INVISIBLE);
                m_fifthNotes[i].setVisibility(View.INVISIBLE);
            }
        }
        else if(a_numOf_Instruments == 1)
        {
            for(int i = 0; i < 24; i++)
            {
                m_secondNotes[i].setVisibility(View.INVISIBLE);
                m_thirdNotes[i].setVisibility(View.INVISIBLE);
                m_fourthNotes[i].setVisibility(View.INVISIBLE);
                m_fifthNotes[i].setVisibility(View.INVISIBLE);
            }
        }
        else if(a_numOf_Instruments == 2)
        {
            for(int i = 0; i < 24; i++)
            {

                m_thirdNotes[i].setVisibility(View.INVISIBLE);
                m_fourthNotes[i].setVisibility(View.INVISIBLE);
                m_fifthNotes[i].setVisibility(View.INVISIBLE);
            }
        }
        else if(a_numOf_Instruments == 3)
        {
            for(int i = 0; i < 24; i++)
            {

                m_fourthNotes[i].setVisibility(View.INVISIBLE);
                m_fifthNotes[i].setVisibility(View.INVISIBLE);
            }
        }
        else if(a_numOf_Instruments == 4)
        {
            for(int i = 0; i < 24; i++)
            {

                m_fifthNotes[i].setVisibility(View.INVISIBLE);
            }
        }
        else if(a_numOf_Instruments == 5)
        {

        }
    }


    public void setVolumeText()
    {
        if (m_numberOfInstruments > 0)
        {
            if(m_instrument1.getM_volumeSelection() == 1)
            {
                m_btn_instrumentVolume[0].setText("Volume: 3");
            }
            else if (m_instrument1.getM_volumeSelection() == 3)
            {
                m_btn_instrumentVolume[0].setText("Volume: 2");
            }
            else if (m_instrument1.getM_volumeSelection() == 6)
            {
                m_btn_instrumentVolume[0].setText("Volume: 3");
            }


            if (m_numberOfInstruments > 1) {

                if(m_instrument2.getM_volumeSelection() == 1)
                {
                    m_btn_instrumentVolume[1].setText("Volume: 3");
                }
                else if (m_instrument2.getM_volumeSelection() == 3)
                {
                    m_btn_instrumentVolume[1].setText("Volume: 2");
                }
                else if (m_instrument2.getM_volumeSelection() == 6)
                {
                    m_btn_instrumentVolume[1].setText("Volume: 3");
                }


                if (m_numberOfInstruments > 2)
                {
                    if(m_instrument3.getM_volumeSelection() == 1)
                    {
                        m_btn_instrumentVolume[2].setText("Volume: 3");
                    }
                    else if (m_instrument3.getM_volumeSelection() == 3)
                    {
                        m_btn_instrumentVolume[2].setText("Volume: 2");
                    }
                    else if (m_instrument3.getM_volumeSelection() == 6)
                    {
                        m_btn_instrumentVolume[2].setText("Volume: 3");
                    }


                    if (m_numberOfInstruments > 3)
                    {
                        if(m_instrument4.getM_volumeSelection() == 1)
                        {
                            m_btn_instrumentVolume[3].setText("Volume: 3");
                        }
                        else if (m_instrument4.getM_volumeSelection() == 3)
                        {
                            m_btn_instrumentVolume[3].setText("Volume: 2");
                        }
                        else if (m_instrument4.getM_volumeSelection() == 6)
                        {
                            m_btn_instrumentVolume[3].setText("Volume: 3");
                        }


                        if (m_numberOfInstruments > 4)
                        {

                            if(m_instrument5.getM_volumeSelection() == 1)
                            {
                                m_btn_instrumentVolume[4].setText("Volume: 3");
                            }
                            else if (m_instrument5.getM_volumeSelection() == 3)
                            {
                                m_btn_instrumentVolume[4].setText("Volume: 2");
                            }
                            else if (m_instrument5.getM_volumeSelection() == 6)
                            {
                                m_btn_instrumentVolume[4].setText("Volume: 3");
                            }
                        }

                    }

                }
            }
        }


    }
    /*
    NAME

        setPianoDrawableDefault-->

    SYNOPSIS

        public void setPianoDrawableDefault(Button a_notes[])

        a_notes[] -> Button array that contains the mini virtual keyboard buttons

    DESCRIPTION

        Used to set one mini keyboard visuals back to default. Used when the user stops a playing instrument mid-recording.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void setPianoDrawableDefault(Button a_notes[])
    {
        //Setting defaulting visuals to a_notes[] keyboard
        a_notes[0].setBackgroundResource(R.drawable.button_border);
        a_notes[1].setBackgroundResource(R.drawable.black_keys);
        a_notes[2].setBackgroundResource(R.drawable.button_border);
        a_notes[3].setBackgroundResource(R.drawable.black_keys);
        a_notes[4].setBackgroundResource(R.drawable.button_border);
        a_notes[5].setBackgroundResource(R.drawable.button_border);
        a_notes[6].setBackgroundResource(R.drawable.black_keys);
        a_notes[7].setBackgroundResource(R.drawable.button_border);
        a_notes[8].setBackgroundResource(R.drawable.black_keys);
        a_notes[9].setBackgroundResource(R.drawable.button_border);
        a_notes[10].setBackgroundResource(R.drawable.black_keys);
        a_notes[11].setBackgroundResource(R.drawable.button_border);
        a_notes[12].setBackgroundResource(R.drawable.button_border);
        a_notes[13].setBackgroundResource(R.drawable.black_keys);
        a_notes[14].setBackgroundResource(R.drawable.button_border);
        a_notes[15].setBackgroundResource(R.drawable.black_keys);
        a_notes[16].setBackgroundResource(R.drawable.button_border);
        a_notes[17].setBackgroundResource(R.drawable.button_border);
        a_notes[18].setBackgroundResource(R.drawable.black_keys);
        a_notes[19].setBackgroundResource(R.drawable.button_border);
        a_notes[20].setBackgroundResource(R.drawable.black_keys);
        a_notes[21].setBackgroundResource(R.drawable.button_border);
        a_notes[22].setBackgroundResource(R.drawable.black_keys);
        a_notes[23].setBackgroundResource(R.drawable.button_border);


    }
    /*
    NAME

        saveTrack -->

    SYNOPSIS

        public String saveTrack()


    DESCRIPTION

        Returns a string that will be used to write to text file for saving.

    RETURNS

        String

    AUTHOR

        Frank Ryan
    */
    public String saveTrack()
    {
        String savedTextFile = "";

        //Depending on number of instruments, write all specification of instruments to string
        if(m_numberOfInstruments == 1)
        {
            //Retrieving all needed instrument data
            HashMap<String, String> first_recorded_notes = m_instrument1.getPianoNotes();
            int first_ID = m_instrument1.getM_instrumentID();
            int first_octaves = m_instrument1.getM_octaves();
            int first_volumeSelect = m_instrument1.getM_volumeSelection();

            //Displaying number of instrument, then hashmap, instrumentID, octaves, and volume
            savedTextFile += "Instrument1 " + "\n";

            savedTextFile += recordedNotes_toTextFile(first_recorded_notes);

            savedTextFile += "InstrumentID " + first_ID + "\n";
            savedTextFile += "InstrumentOctaves " + first_octaves + "\n";
            savedTextFile += "InstrumentVolume " + first_volumeSelect + "\n";


        }
        //Similar documentation as above
        else if (m_numberOfInstruments == 2)
        {
            HashMap<String, String> first_recorded_notes = m_instrument1.getPianoNotes();
            int first_ID = m_instrument1.getM_instrumentID();
            int first_octaves = m_instrument1.getM_octaves();
            int first_volumeSelect = m_instrument1.getM_volumeSelection();

            savedTextFile += "Instrument1 " + "\n";

            savedTextFile += recordedNotes_toTextFile(first_recorded_notes);

            savedTextFile += "InstrumentID " + first_ID + "\n";
            savedTextFile += "InstrumentOctaves " + first_octaves + "\n";
            savedTextFile += "InstrumentVolume " + first_volumeSelect + "\n";



            HashMap<String, String> second_recorded_notes = m_instrument2.getPianoNotes();
            int second_ID = m_instrument2.getM_instrumentID();
            int second_octaves = m_instrument2.getM_octaves();
            int second_volumeSelect = m_instrument2.getM_volumeSelection();

            savedTextFile += "Instrument2 " + "\n";

            savedTextFile += recordedNotes_toTextFile(second_recorded_notes);

            savedTextFile += "InstrumentID " + second_ID + "\n";
            savedTextFile += "InstrumentOctaves " + second_octaves + "\n";
            savedTextFile += "InstrumentVolume " + second_volumeSelect + "\n";



        }
        else if (m_numberOfInstruments == 3)
        {
            HashMap<String, String> first_recorded_notes = m_instrument1.getPianoNotes();
            int first_ID = m_instrument1.getM_instrumentID();
            int first_octaves = m_instrument1.getM_octaves();
            int first_volumeSelect = m_instrument1.getM_volumeSelection();

            savedTextFile += "Instrument1 " + "\n";

            savedTextFile += recordedNotes_toTextFile(first_recorded_notes);

            savedTextFile += "InstrumentID " + first_ID + "\n";
            savedTextFile += "InstrumentOctaves " + first_octaves + "\n";
            savedTextFile += "InstrumentVolume " + first_volumeSelect + "\n";



            HashMap<String, String> second_recorded_notes = m_instrument2.getPianoNotes();
            int second_ID = m_instrument2.getM_instrumentID();
            int second_octaves = m_instrument2.getM_octaves();
            int second_volumeSelect = m_instrument2.getM_volumeSelection();

            savedTextFile += "Instrument2 " + "\n";

            savedTextFile += recordedNotes_toTextFile(second_recorded_notes);

            savedTextFile += "InstrumentID " + second_ID + "\n";
            savedTextFile += "InstrumentOctaves " + second_octaves + "\n";
            savedTextFile += "InstrumentVolume " + second_volumeSelect + "\n";

            HashMap<String, String> third_recorded_notes = m_instrument3.getPianoNotes();
            int third_ID = m_instrument3.getM_instrumentID();
            int third_octaves = m_instrument3.getM_octaves();
            int third_volumeSelect = m_instrument3.getM_volumeSelection();

            savedTextFile += "Instrument3 " + "\n";

            savedTextFile += recordedNotes_toTextFile(third_recorded_notes);

            savedTextFile += "InstrumentID " + third_ID + "\n";
            savedTextFile += "InstrumentOctaves " + third_octaves + "\n";
            savedTextFile += "InstrumentVolume " + third_volumeSelect + "\n";

        }
        else if (m_numberOfInstruments == 4)
        {

            HashMap<String, String> first_recorded_notes = m_instrument1.getPianoNotes();
            int first_ID = m_instrument1.getM_instrumentID();
            int first_octaves = m_instrument1.getM_octaves();
            int first_volumeSelect = m_instrument1.getM_volumeSelection();

            savedTextFile += "Instrument1 " + "\n";

            savedTextFile += recordedNotes_toTextFile(first_recorded_notes);

            savedTextFile += "InstrumentID " + first_ID + "\n";
            savedTextFile += "InstrumentOctaves " + first_octaves + "\n";
            savedTextFile += "InstrumentVolume " + first_volumeSelect + "\n";



            HashMap<String, String> second_recorded_notes = m_instrument2.getPianoNotes();
            int second_ID = m_instrument2.getM_instrumentID();
            int second_octaves = m_instrument2.getM_octaves();
            int second_volumeSelect = m_instrument2.getM_volumeSelection();

            savedTextFile += "Instrument2 " + "\n";

            savedTextFile += recordedNotes_toTextFile(second_recorded_notes);

            savedTextFile += "InstrumentID " + second_ID + "\n";
            savedTextFile += "InstrumentOctaves " + second_octaves + "\n";
            savedTextFile += "InstrumentVolume " + second_volumeSelect + "\n";

            HashMap<String, String> third_recorded_notes = m_instrument3.getPianoNotes();
            int third_ID = m_instrument3.getM_instrumentID();
            int third_octaves = m_instrument3.getM_octaves();
            int third_volumeSelect = m_instrument3.getM_volumeSelection();

            savedTextFile += "Instrument3 " + "\n";

            savedTextFile += recordedNotes_toTextFile(third_recorded_notes);

            savedTextFile += "InstrumentID " + third_ID + "\n";
            savedTextFile += "InstrumentOctaves " + third_octaves + "\n";
            savedTextFile += "InstrumentVolume " + third_volumeSelect + "\n";

            HashMap<String, String> fourth_recorded_notes = m_instrument4.getPianoNotes();
            int fourth_ID = m_instrument4.getM_instrumentID();
            int fourth_octaves = m_instrument4.getM_octaves();
            int fourth_volumeSelect = m_instrument4.getM_volumeSelection();

            savedTextFile += "Instrument4 " + "\n";

            savedTextFile += recordedNotes_toTextFile(fourth_recorded_notes);

            savedTextFile += "InstrumentID " + fourth_ID + "\n";
            savedTextFile += "InstrumentOctaves " + fourth_octaves + "\n";
            savedTextFile += "InstrumentVolume " + fourth_volumeSelect + "\n";
        }
        else if (m_numberOfInstruments == 5)
        {
            HashMap<String, String> first_recorded_notes = m_instrument1.getPianoNotes();
            int first_ID = m_instrument1.getM_instrumentID();
            int first_octaves = m_instrument1.getM_octaves();
            int first_volumeSelect = m_instrument1.getM_volumeSelection();

            savedTextFile += "Instrument1 " + "\n";

            savedTextFile += recordedNotes_toTextFile(first_recorded_notes);

            savedTextFile += "InstrumentID " + first_ID + "\n";
            savedTextFile += "InstrumentOctaves " + first_octaves + "\n";
            savedTextFile += "InstrumentVolume " + first_volumeSelect + "\n";



            HashMap<String, String> second_recorded_notes = m_instrument2.getPianoNotes();
            int second_ID = m_instrument2.getM_instrumentID();
            int second_octaves = m_instrument2.getM_octaves();
            int second_volumeSelect = m_instrument2.getM_volumeSelection();

            savedTextFile += "Instrument2 " + "\n";

            savedTextFile += recordedNotes_toTextFile(second_recorded_notes);

            savedTextFile += "InstrumentID " + second_ID + "\n";
            savedTextFile += "InstrumentOctaves " + second_octaves + "\n";
            savedTextFile += "InstrumentVolume " + second_volumeSelect + "\n";

            HashMap<String, String> third_recorded_notes = m_instrument3.getPianoNotes();
            int third_ID = m_instrument3.getM_instrumentID();
            int third_octaves = m_instrument3.getM_octaves();
            int third_volumeSelect = m_instrument3.getM_volumeSelection();

            savedTextFile += "Instrument3 " + "\n";

            savedTextFile += recordedNotes_toTextFile(third_recorded_notes);

            savedTextFile += "InstrumentID " + third_ID + "\n";
            savedTextFile += "InstrumentOctaves " + third_octaves + "\n";
            savedTextFile += "InstrumentVolume " + third_volumeSelect + "\n";

            HashMap<String, String> fourth_recorded_notes = m_instrument4.getPianoNotes();
            int fourth_ID = m_instrument4.getM_instrumentID();
            int fourth_octaves = m_instrument4.getM_octaves();
            int fourth_volumeSelect = m_instrument4.getM_volumeSelection();

            savedTextFile += "Instrument4 " + "\n";

            savedTextFile += recordedNotes_toTextFile(fourth_recorded_notes);

            savedTextFile += "InstrumentID " + fourth_ID + "\n";
            savedTextFile += "InstrumentOctaves " + fourth_octaves + "\n";
            savedTextFile += "InstrumentVolume " + fourth_volumeSelect + "\n";

            HashMap<String, String> fifth_recorded_notes = m_instrument5.getPianoNotes();
            int fifth_ID = m_instrument5.getM_instrumentID();
            int fifth_octaves = m_instrument5.getM_octaves();
            int fifth_volumeSelect = m_instrument5.getM_volumeSelection();

            savedTextFile += "Instrument5 " + "\n";

            savedTextFile += recordedNotes_toTextFile(fifth_recorded_notes);

            savedTextFile += "InstrumentID " + fifth_ID + "\n";
            savedTextFile += "InstrumentOctaves " + fifth_octaves + "\n";
            savedTextFile += "InstrumentVolume " + fifth_volumeSelect + "\n";
        }

        return savedTextFile;

    }
    /*
        NAME

            recordedNotes_toTextFile-->

        SYNOPSIS

            public String recordedNotes_toTextFile(HashMap<String, String> a_hashMap)

            a_hashMap --> holds hashmap of recorded notes in instrument

        DESCRIPTION

            Convert a hashmap to a string used for saving track. Each key is given a new line.

        RETURNS

            String

        AUTHOR

            Frank Ryan
        */
    public String recordedNotes_toTextFile(HashMap<String, String> a_hashMap)
    {
        //place each key of hashmap into string
        String convertedHashMap = "";
        convertedHashMap += "lowC " + a_hashMap.get("lowC") + "\n";
        convertedHashMap += "lowCSharp " + a_hashMap.get("lowCSharp") + "\n";
        convertedHashMap += "lowD " + a_hashMap.get("lowD") + "\n";
        convertedHashMap += "lowDSharp " + a_hashMap.get("lowDSharp") + "\n";
        convertedHashMap += "lowE " + a_hashMap.get("lowE") + "\n";
        convertedHashMap += "lowF " + a_hashMap.get("lowF") + "\n";
        convertedHashMap += "lowFSharp " + a_hashMap.get("lowFSharp") + "\n";
        convertedHashMap += "lowG " + a_hashMap.get("lowG") + "\n";
        convertedHashMap += "lowGSharp " + a_hashMap.get("lowGSharp") + "\n";
        convertedHashMap += "lowA " + a_hashMap.get("lowA") + "\n";
        convertedHashMap += "lowASharp " + a_hashMap.get("lowASharp") + "\n";
        convertedHashMap += "lowB " + a_hashMap.get("lowB") + "\n";

        convertedHashMap += "highC " + a_hashMap.get("highC") + "\n";
        convertedHashMap += "highCSharp " + a_hashMap.get("highCSharp") + "\n";
        convertedHashMap += "highD " + a_hashMap.get("highD") + "\n";
        convertedHashMap += "highDSharp " + a_hashMap.get("highDSharp") + "\n";
        convertedHashMap += "highE " + a_hashMap.get("highE") + "\n";
        convertedHashMap += "highF " + a_hashMap.get("highF") + "\n";
        convertedHashMap += "highFSharp " + a_hashMap.get("highFSharp") + "\n";
        convertedHashMap += "highG " + a_hashMap.get("highG") + "\n";
        convertedHashMap += "highGSharp " + a_hashMap.get("highGSharp") + "\n";
        convertedHashMap += "highA " + a_hashMap.get("highA") + "\n";
        convertedHashMap += "highASharp " + a_hashMap.get("highASharp") + "\n";
        convertedHashMap += "highB " + a_hashMap.get("highB") + "\n";

        return convertedHashMap;
    }



}
