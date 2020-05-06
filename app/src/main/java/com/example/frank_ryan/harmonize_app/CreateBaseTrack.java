package com.example.frank_ryan.harmonize_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;

import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class CreateBaseTrack extends AppCompatActivity{

    /*
    m_backBtn -> button to go back to previous activity (choose_instrument.xml)
    m_recordBtn -> button to start/stop timer and record user click and release events of virtual keyboard
    m_addTrackBtn -> button to add recording of current instrument to main track, sends user to main_track_home.xml
    m_playAllBtn -> button to play recording of main track
    m_playAllBtn -> button to play recording of main track and also start timer to record user click and release events of virtual keyboard
    m_deleteRecordingBtn -> button to delete current recording if there is one
     */
    private Button m_backBtn, m_recordBtn, m_addTrackBtn, m_playAllBtn, m_playAllAndRecordBtn, m_deleteRecordingBtn;

    //SoundPool object used to load, play, and stop sounds of keys
    private SoundPool m_soundPool;

    /*  for the following m_noteSounds[], m_notesBtn, and m_noteTimes[]; the array indices go as follows:
            0   low C
            1   low C Sharp
            2   low D
            3   low D Sharp
            4   low E
            5   low F
            6   low F Sharp
            7   low G
            8   low G Sharp
            9   low A
            10  low A Sharp
            11  low B
            12  high C
            13  high C Sharp
            14  high D
            15  high D Sharp
            16  high E
            17  high F
            18  high F Sharp
            19  high G
            20  high G Sharp
            21  high A
            22  high A Sharp
            23  high B

       m_noteSounds[] -> stores the ID number of each SoundPool sound, specific for every key button
       m_notesBtn[] -> buttons for virtual keyboard
       m_noteTimes[] -> stores each time a key was pressed and release; placed as the value for each key in m_recordedPianoNotes HashMap
            For example, for note(key) "lowC", the value could be "22-50,60-66,". In this example, the key was pressed at 22 and released at 50. It was then pressed again at 60 and released at 66.
    */
    private int m_noteSounds[] = new int[26];

    private Button m_notesBtn[] = new Button[26];

    private String m_noteTimes[] = new String[26];

    //m_recordedPianoNotes -> HashMap that stores all recorded times of notes
    //The keys for the hashmap are each note on the keyboard as a string, example: "lowC" and "lowCSharp"
    private HashMap<String, String> m_recordedPianoNotes;

    //m_isRecording -> boolean that is true if the user clicked the record button and has not clicked it again to stop recording
    private boolean m_isRecording;

    //m_secondsPassed -> used inside the timer task function, it is increased by +1 every 10 milliseconds when the task is called
    private int m_secondsPassed = 0;

    /*
    m_instrumentTimeStamps -> holds current recording of instrument
    m_instrumentTimeStampsCopy -> holds copy of m_instrumentTimeStamps, used to play recording of m_instrumentTimeStamps
    m_playInstrument1 -> hold recording of first instrument from main track, if there is one
    m_playInstrument2 -> hold recording of second instrument from main track, if there is one
    m_playInstrument3 -> hold recording of fourth instrument from main track, if there is one
    m_playInstrument4 -> hold recording of fifth instrument from main track, if there is one
     */
    private InstrumentTimeStamps m_instrumentTimeStamps, m_instrumentTimeStampsCopy, m_playInstrument1, m_playInstrument2, m_playInstrument3, m_playInstrument4;

    /*
    m_numberOfInstruments -> holds number of instruments that are on the main track at this point
    m_type_OfInstrument -> holds the instrument ID, determines sound from keyboard. 0 -> piano, 1 -> guitar
    m_octaves_int -> hold the octave ID, determines sound from keyboard. 1-4 for piano, 1-2 for guitar.
     */
    private int m_numberOfInstruments, m_type_OfInstrument, m_octaves_int;


    //m_timer -> paired with m_task, used for recording of instrument
    private Timer m_timer = new Timer();

    //m_task -> member function used to schedule a timer task for recording of instrument
    private TimerTask m_task = new TimerTask()
    {

        public void run()
        {
            m_secondsPassed++;
        }
    };

    //member variables that retrieve the current volume of the user app
    private AudioManager m_audioManager;
    private float m_curVolume, m_maxVolume, m_volume;


    /*
    NAME

        onCreate (for the build_song.xml) - displays the virtual keyboard for selected instrument, contains recording functionality

    SYNOPSIS

        protected void onCreate(Bundle savedInstanceState)

            Bundle savedInstanceState  --> object that contains previous state of android activity, if there is one

    DESCRIPTION

            This onCreate method builds the build_song.xml file that displays a virtual keyboard, allowing the user to record a track.
            This is called from the ChooseInstrument.java file, which passes the following intent (data variables):
                                                                instrumentsNumber    --> (int) number of instruments on main track
                                                                TypeOfInstrument     --> (int) chosen instrument
                                                                OctaveChoice         --> (int) chosen octave for instrument
                                                                Instrument1          --> (InstrumentTimeStamps obj) first instrument object, if it exists
                                                                Instrument2          --> (InstrumentTimeStamps obj) second instrument object, if it exists
                                                                Instrument3          --> (InstrumentTimeStamps obj) third instrument object, if it exists
                                                                Instrument4          --> (InstrumentTimeStamps obj) fourth instrument object, if it exists
            All buttons are initialized through this method, as well as onClickListeners.
            The onClickListeners for buttons are as follows:
                m_backBtn       --> goes back to choose_instrument.xml activity
                m_playAllBtn    --> plays the main track using Instrument1, Instrument2, Instrument3, and Instrument4
                m_playAllAndRecordBtn     --> plays the main track, as well as records user click time events on the virtual keyboard
                m_addTrackBtn     --> adds the recorded piano notes to the main track, directs to main_track_home.xml
                m_recordBtn       --> records user click time events on virtual keyboard

    RETURNS

        void

    AUTHOR

        Frank Ryan


    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.build_song);

        //initialization of functionality buttons
        m_backBtn = (Button) findViewById(R.id.buttonBackToChoose);
        m_recordBtn = (Button) findViewById(R.id.buttonRecord);
        m_addTrackBtn = (Button) findViewById(R.id.buttonAddToMain);

        m_playAllAndRecordBtn  = (Button) findViewById(R.id.btn_playAllAndRecord);
        m_playAllBtn  = (Button) findViewById(R.id.btn_playAll);
        m_deleteRecordingBtn  = (Button) findViewById(R.id.btn_deleteRecording);

        //initialization of virtual keyboard buttons

        m_notesBtn[0] = findViewById(R.id.buttonC);
        m_notesBtn[1] = findViewById(R.id.buttonCSharp);
        m_notesBtn[2] = findViewById(R.id.buttonD);
        m_notesBtn[3] = findViewById(R.id.buttonDSharp);
        m_notesBtn[4] = findViewById(R.id.buttonE);
        m_notesBtn[5] = findViewById(R.id.buttonF);
        m_notesBtn[6] = findViewById(R.id.buttonFSharp);
        m_notesBtn[7] = findViewById(R.id.buttonG);
        m_notesBtn[8] = findViewById(R.id.buttonGSharp);
        m_notesBtn[9] = findViewById(R.id.buttonA);
        m_notesBtn[10] = findViewById(R.id.buttonASharp);
        m_notesBtn[11] = findViewById(R.id.buttonB);

        m_notesBtn[12] = findViewById(R.id.button_highC);
        m_notesBtn[13] = findViewById(R.id.button_highCSharp);
        m_notesBtn[14] = findViewById(R.id.button_highD);
        m_notesBtn[15] = findViewById(R.id.button_highDSharp);
        m_notesBtn[16] = findViewById(R.id.button_highE);
        m_notesBtn[17] = findViewById(R.id.button_highF);
        m_notesBtn[18] = findViewById(R.id.button_highFSharp);
        m_notesBtn[19] = findViewById(R.id.button_highG);
        m_notesBtn[20] = findViewById(R.id.button_highGSharp);
        m_notesBtn[21] = findViewById(R.id.button_highA);
        m_notesBtn[22] = findViewById(R.id.button_highASharp);
        m_notesBtn[23] = findViewById(R.id.button_highB);

        //Retreives current volume of app and stores inside m_volume
        m_audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        m_curVolume = (float)m_audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        m_maxVolume = (float)m_audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        m_volume = m_curVolume / m_maxVolume;


        //retrieves intent from ChooseInstrument.java. Determines sound from keyboard.
        Intent intent = getIntent();
        m_numberOfInstruments = intent.getIntExtra("instrumentsNumber", 0);
        m_type_OfInstrument = intent.getIntExtra("TypeOfInstrument", 0);
        m_octaves_int = intent.getIntExtra("OctaveChoice", 1);

        //retrieves previously recorded instruments from main track, if there are any. To be used from m_playAllBtn and m_playAllAndRecordBtn
        final InstrumentTimeStamps instrument1 = (InstrumentTimeStamps) intent.getSerializableExtra("Instrument1");
        final InstrumentTimeStamps instrument2 = (InstrumentTimeStamps) intent.getSerializableExtra("Instrument2");
        final InstrumentTimeStamps instrument3 = (InstrumentTimeStamps) intent.getSerializableExtra("Instrument3");
        final InstrumentTimeStamps instrument4 = (InstrumentTimeStamps) intent.getSerializableExtra("Instrument4");

        //initializes the virtual keyboard sounds through init_Instrument function call
        init_Instrument(m_type_OfInstrument, m_octaves_int);

        m_isRecording = false;

        //initializes m_noteTimes[] that will be concatenated after recording
        for(int i = 0; i < 24; i++)
        {
            m_noteTimes[i] = "";
        }

        m_addTrackBtn.setEnabled(false);
        m_deleteRecordingBtn.setEnabled(false);

        //back button listener, sends previously recorded PianoTimeStamp objects back to ChooseInstrument.java
        m_backBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent back_intent = new Intent(CreateBaseTrack.this, ChooseInstrument.class);

                //Sends number of instruments and only InstrumentTimeStamps objects that are recorded
                if(m_numberOfInstruments == 0)
                {

                    back_intent.putExtra("instrumentsNumber", 0);
                    back_intent.putExtra("LoadedSong", false);

                }
                else if (m_numberOfInstruments == 1)
                {
                    back_intent.putExtra("instrumentsNumber", 1);
                    back_intent.putExtra("Instrument1", instrument1);

                    back_intent.putExtra("LoadedSong", true);

                }
                else if (m_numberOfInstruments == 2)
                {
                    back_intent.putExtra("instrumentsNumber", 2);
                    back_intent.putExtra("Instrument1", instrument1);
                    back_intent.putExtra("Instrument2", instrument2);

                    back_intent.putExtra("LoadedSong", true);

                }
                else if (m_numberOfInstruments == 3)
                {
                    back_intent.putExtra("instrumentsNumber", 3);
                    back_intent.putExtra("Instrument1", instrument1);
                    back_intent.putExtra("Instrument2", instrument2);
                    back_intent.putExtra("Instrument3", instrument3);

                    back_intent.putExtra("LoadedSong", true);

                }
                else if (m_numberOfInstruments == 4)
                {
                    back_intent.putExtra("instrumentsNumber", 4);
                    back_intent.putExtra("Instrument1", instrument1);
                    back_intent.putExtra("Instrument2", instrument2);
                    back_intent.putExtra("Instrument3", instrument3);
                    back_intent.putExtra("Instrument4", instrument4);

                    back_intent.putExtra("LoadedSong", true);

                }

                //releases all resources from SoundPool object and sends user to choose_instrument.xml
                m_soundPool.release();

                startActivity(back_intent);

            }
        });

        //button that plays all main track instruments
        m_playAllBtn.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View view) {

                //executed if button is first pressed, plays all instruments from main track
                if(m_playAllBtn.getText().toString().equals("Play Main Track")) {

                    //Plays recordings from InstrumentTimeStamps objects from main track.
                    //A new HashMap is replicated from each recording in order to play a instrument through the InstrumentTimeStamps function playRecording().
                    //This is done because playRecording() erases the values of all keys once it is called and executed.
                    playMainTrack(instrument1, instrument2, instrument3, instrument4);
                    m_playAllBtn.setText("Stop/Reset Main Track");

                    m_playAllAndRecordBtn.setEnabled(false);
                    m_deleteRecordingBtn.setEnabled(false);
                    m_addTrackBtn.setEnabled(false);
                }
                //executed if this button was already pressed to play the main track recording
                //stops recordings if they are playing, or resets the track to play it again
                else if(m_playAllBtn.getText().toString().equals("Stop/Reset Main Track"))
                {
                    m_playAllAndRecordBtn.setEnabled(true);
                    m_deleteRecordingBtn.setEnabled(true);
                    m_addTrackBtn.setEnabled(true);
                    if (m_numberOfInstruments == 0) {


                    } else if (m_numberOfInstruments == 1) {

                        m_playInstrument1.stopPlaying();

                    } else if (m_numberOfInstruments == 2) {

                        m_playInstrument1.stopPlaying();
                        m_playInstrument2.stopPlaying();

                    } else if (m_numberOfInstruments == 3) {

                        m_playInstrument1.stopPlaying();
                        m_playInstrument2.stopPlaying();
                        m_playInstrument3.stopPlaying();

                    } else if (m_numberOfInstruments == 4) {

                        m_playInstrument1.stopPlaying();
                        m_playInstrument2.stopPlaying();
                        m_playInstrument3.stopPlaying();
                        m_playInstrument4.stopPlaying();

                    }

                    m_playAllBtn.setText("Play Main Track");
                }
            }
        });

        //button that plays all recordings of main track instruments, and also records user click time events on virtual keyboard
        m_playAllAndRecordBtn.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View view) {

                //executed if button is pressed for the first time
                if(m_playAllAndRecordBtn.getText().toString().equals("Play Main Track And Record")) {

                    //m_isRecording is used on each note button listeners, allows each function to log the time each button was pressed and released
                    m_isRecording = true;
                    m_playAllBtn.setEnabled(false);

                    m_timer = new Timer();
                    m_task = new TimerTask()
                    {

                        public void run()
                        {
                            m_secondsPassed++;
                        }
                    };
                    //calls the timer object for recording, m_secondsPassed is incremented every 10 milliseconds
                    m_timer.scheduleAtFixedRate(m_task, 10, 10);

                    //similar to m_playAllBtn, plays all recordings of previous instruments
                    playMainTrack(instrument1, instrument2, instrument3, instrument4);


                    if(m_recordBtn.getText().toString().equals("Record"))
                    {
                        m_recordBtn.setEnabled(false);
                        m_recordBtn.setText("Play");
                    }

                    m_playAllAndRecordBtn.setText("Stop Recording");

                }
                //executes if the button is pressed again, stops playing of instruments and recording of current instrument
                else if(m_playAllAndRecordBtn.getText().toString().equals("Stop Recording"))
                {

                    m_recordedPianoNotes = new HashMap<String, String>();
                    m_playAllBtn.setEnabled(true);
                    //adds ":" to end of all m_noteTimes, used for InstrumentTimeStamps playRecording() function to know when the recording is over for each note
                    for(int i = 0; i < 24; i ++)
                    {
                        m_noteTimes[i] += ":";
                    }


                    //logs each keyboard value of recorded click events to appropriate key
                    m_recordedPianoNotes.put("lowC", m_noteTimes[0]);
                    m_recordedPianoNotes.put("lowCSharp", m_noteTimes[1]);
                    m_recordedPianoNotes.put("lowD", m_noteTimes[2]);
                    m_recordedPianoNotes.put("lowDSharp", m_noteTimes[3]);
                    m_recordedPianoNotes.put("lowE", m_noteTimes[4]);
                    m_recordedPianoNotes.put("lowF", m_noteTimes[5]);
                    m_recordedPianoNotes.put("lowFSharp", m_noteTimes[6]);
                    m_recordedPianoNotes.put("lowG", m_noteTimes[7]);
                    m_recordedPianoNotes.put("lowGSharp", m_noteTimes[8]);
                    m_recordedPianoNotes.put("lowA", m_noteTimes[9]);
                    m_recordedPianoNotes.put("lowASharp", m_noteTimes[10]);
                    m_recordedPianoNotes.put("lowB", m_noteTimes[11]);

                    m_recordedPianoNotes.put("highC", m_noteTimes[12]);
                    m_recordedPianoNotes.put("highCSharp", m_noteTimes[13]);
                    m_recordedPianoNotes.put("highD", m_noteTimes[14]);
                    m_recordedPianoNotes.put("highDSharp", m_noteTimes[15]);
                    m_recordedPianoNotes.put("highE", m_noteTimes[16]);
                    m_recordedPianoNotes.put("highF", m_noteTimes[17]);
                    m_recordedPianoNotes.put("highFSharp", m_noteTimes[18]);
                    m_recordedPianoNotes.put("highG", m_noteTimes[19]);
                    m_recordedPianoNotes.put("highGSharp", m_noteTimes[20]);
                    m_recordedPianoNotes.put("highA", m_noteTimes[21]);
                    m_recordedPianoNotes.put("highASharp", m_noteTimes[22]);
                    m_recordedPianoNotes.put("highB", m_noteTimes[23]);


                    m_timer.cancel();
                    m_timer.purge();
                    //construction of m_instrumentTimeStamps object with recorded HashMap and IDs of instrument
                    m_instrumentTimeStamps = new InstrumentTimeStamps(CreateBaseTrack.this, m_recordedPianoNotes, m_type_OfInstrument, m_octaves_int, 1, m_volume);


                    //Stops all playing of main track instruments
                    if (m_numberOfInstruments == 0) {


                    } else if (m_numberOfInstruments == 1) {

                        m_playInstrument1.stopPlaying();

                    } else if (m_numberOfInstruments == 2) {

                        m_playInstrument1.stopPlaying();
                        m_playInstrument2.stopPlaying();

                    } else if (m_numberOfInstruments == 3) {

                        m_playInstrument1.stopPlaying();
                        m_playInstrument2.stopPlaying();
                        m_playInstrument3.stopPlaying();

                    } else if (m_numberOfInstruments == 4) {

                        m_playInstrument1.stopPlaying();
                        m_playInstrument2.stopPlaying();
                        m_playInstrument3.stopPlaying();
                        m_playInstrument4.stopPlaying();

                    }

                    m_addTrackBtn.setEnabled(true);
                    m_recordBtn.setEnabled(true);
                    m_deleteRecordingBtn.setEnabled(true);

                    m_playAllAndRecordBtn.setText("Play Main Track And Current Recording");
                }
                else if(m_playAllAndRecordBtn.getText().toString().equals("Play Main Track And Current Recording"))
                {




                    playMainTrack(instrument1, instrument2, instrument3, instrument4);
                    HashMap<String, String> notes = new HashMap<>(m_instrumentTimeStamps.getPianoNotes());

                    m_instrumentTimeStampsCopy = new InstrumentTimeStamps(CreateBaseTrack.this, notes, m_type_OfInstrument, m_octaves_int, 1, m_volume);

                    m_instrumentTimeStampsCopy.playInstrumentRecording();

                    m_recordBtn.setEnabled(false);
                    m_playAllBtn.setEnabled(false);
                    m_deleteRecordingBtn.setEnabled(false);
                    m_addTrackBtn.setEnabled(false);

                    m_playAllAndRecordBtn.setText("Stop Main Track And Current Recording");
                }
                else if(m_playAllAndRecordBtn.getText().toString().equals("Stop Main Track And Current Recording"))
                {

                    //Stops all playing of main track instruments
                    if (m_numberOfInstruments == 0) {


                    } else if (m_numberOfInstruments == 1) {

                        m_playInstrument1.stopPlaying();

                    } else if (m_numberOfInstruments == 2) {

                        m_playInstrument1.stopPlaying();
                        m_playInstrument2.stopPlaying();

                    } else if (m_numberOfInstruments == 3) {

                        m_playInstrument1.stopPlaying();
                        m_playInstrument2.stopPlaying();
                        m_playInstrument3.stopPlaying();

                    } else if (m_numberOfInstruments == 4) {

                        m_playInstrument1.stopPlaying();
                        m_playInstrument2.stopPlaying();
                        m_playInstrument3.stopPlaying();
                        m_playInstrument4.stopPlaying();

                    }

                    m_instrumentTimeStampsCopy.stopPlaying();

                    m_recordBtn.setEnabled(true);
                    m_playAllBtn.setEnabled(true);
                    m_deleteRecordingBtn.setEnabled(true);
                    m_addTrackBtn.setEnabled(true);


                    m_playAllAndRecordBtn.setText("Play Main Track And Current Recording");
                }
            }
        });



        //button that sends all recordings, including current recording, to main_track_home.xml
        m_addTrackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent track_intent = new Intent(CreateBaseTrack.this, MainTrackHome.class);

                m_instrumentTimeStamps = new InstrumentTimeStamps(m_recordedPianoNotes, m_type_OfInstrument, m_octaves_int, 1);

                //Puts all recordings of main track and current recording, depending on number of instruments
                if(m_numberOfInstruments == 0)
                {
                    track_intent.putExtra("Instrument1", m_instrumentTimeStamps);
                    track_intent.putExtra("instrumentsNumber", 1);
                    track_intent.putExtra("LoadedSong", true);

                }
                else if (m_numberOfInstruments == 1)
                {
                    track_intent.putExtra("Instrument1", instrument1);
                    track_intent.putExtra("Instrument2", m_instrumentTimeStamps);
                    track_intent.putExtra("instrumentsNumber", 2);
                    track_intent.putExtra("LoadedSong", true);

                }
                else if (m_numberOfInstruments == 2)
                {
                    track_intent.putExtra("Instrument1", instrument1);
                    track_intent.putExtra("Instrument2", instrument2);
                    track_intent.putExtra("Instrument3", m_instrumentTimeStamps);
                    track_intent.putExtra("instrumentsNumber", 3);
                    track_intent.putExtra("LoadedSong", true);

                }
                else if (m_numberOfInstruments == 3)
                {
                    track_intent.putExtra("Instrument1", instrument1);
                    track_intent.putExtra("Instrument2", instrument2);
                    track_intent.putExtra("Instrument3", instrument3);
                    track_intent.putExtra("Instrument4", m_instrumentTimeStamps);
                    track_intent.putExtra("instrumentsNumber", 4);
                    track_intent.putExtra("LoadedSong", true);

                }
                else if (m_numberOfInstruments == 4)
                {
                    track_intent.putExtra("Instrument1", instrument1);
                    track_intent.putExtra("Instrument2", instrument2);
                    track_intent.putExtra("Instrument3", instrument3);
                    track_intent.putExtra("Instrument4", instrument4);
                    track_intent.putExtra("Instrument5", m_instrumentTimeStamps);
                    track_intent.putExtra("instrumentsNumber", 5);
                    track_intent.putExtra("LoadedSong", true);

                }

                //Releases resources from SoundPool and sends user to MainTrackHome.java
                m_soundPool.release();

                startActivity(track_intent);
            }

        });







        //button listener for recording keyboard input
        m_recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //executes if button is pressed for first time
                if(m_recordBtn.getText().toString().equals("Record"))
                {
                    //timer is called for recording with m_isRecording set to true for keyboard button functions
                    m_isRecording = true;

                    m_timer = new Timer();
                    m_task = new TimerTask()
                    {

                        public void run()
                        {
                            m_secondsPassed++;
                        }
                    };
                    //calls the timer object for recording, m_secondsPassed is incremented every 10 milliseconds
                    m_timer.scheduleAtFixedRate(m_task, 10, 10);

                    if(m_playAllAndRecordBtn.getText().toString().equals("Play Main Track And Record"))
                    {
                        m_playAllAndRecordBtn.setEnabled(false);
                        m_playAllAndRecordBtn.setText("Play Main Track and Current Recording");
                    }

                    m_recordBtn.setText("Stop Recording");

                }
                //executes if button is pressed for second time
                else if(m_recordBtn.getText().toString().equals("Stop Recording"))
                {
                    m_playAllBtn.setEnabled(true);

                    //similar to m_playAllAndRecordBtn, HashMap is created for pianoTimeStamps and initialized with instrument IDs
                    m_recordedPianoNotes = new HashMap<String, String>();

                    for(int i = 0; i < 24; i ++)
                    {
                        m_noteTimes[i] += ":";
                    }


                    m_recordedPianoNotes.put("lowC", m_noteTimes[0]);
                    m_recordedPianoNotes.put("lowCSharp", m_noteTimes[1]);
                    m_recordedPianoNotes.put("lowD", m_noteTimes[2]);
                    m_recordedPianoNotes.put("lowDSharp", m_noteTimes[3]);
                    m_recordedPianoNotes.put("lowE", m_noteTimes[4]);
                    m_recordedPianoNotes.put("lowF", m_noteTimes[5]);
                    m_recordedPianoNotes.put("lowFSharp", m_noteTimes[6]);
                    m_recordedPianoNotes.put("lowG", m_noteTimes[7]);
                    m_recordedPianoNotes.put("lowGSharp", m_noteTimes[8]);
                    m_recordedPianoNotes.put("lowA", m_noteTimes[9]);
                    m_recordedPianoNotes.put("lowASharp", m_noteTimes[10]);
                    m_recordedPianoNotes.put("lowB", m_noteTimes[11]);

                    m_recordedPianoNotes.put("highC", m_noteTimes[12]);
                    m_recordedPianoNotes.put("highCSharp", m_noteTimes[13]);
                    m_recordedPianoNotes.put("highD", m_noteTimes[14]);
                    m_recordedPianoNotes.put("highDSharp", m_noteTimes[15]);
                    m_recordedPianoNotes.put("highE", m_noteTimes[16]);
                    m_recordedPianoNotes.put("highF", m_noteTimes[17]);
                    m_recordedPianoNotes.put("highFSharp", m_noteTimes[18]);
                    m_recordedPianoNotes.put("highG", m_noteTimes[19]);
                    m_recordedPianoNotes.put("highGSharp", m_noteTimes[20]);
                    m_recordedPianoNotes.put("highA", m_noteTimes[21]);
                    m_recordedPianoNotes.put("highASharp", m_noteTimes[22]);
                    m_recordedPianoNotes.put("highB", m_noteTimes[23]);


                    m_timer.cancel();
                    m_timer.purge();

                    m_instrumentTimeStamps = new InstrumentTimeStamps(CreateBaseTrack.this, m_recordedPianoNotes, m_type_OfInstrument, m_octaves_int, 1, m_volume);

                    m_addTrackBtn.setEnabled(true);
                    m_playAllAndRecordBtn.setEnabled(true);
                    m_deleteRecordingBtn.setEnabled(true);

                    m_recordBtn.setText("Play");
                }
                //executed if user already recorded, plays recording of current instrument
                else if(m_recordBtn.getText().toString().equals("Play"))
                {


                    HashMap<String, String> notes = new HashMap<>(m_instrumentTimeStamps.getPianoNotes());
                    m_instrumentTimeStampsCopy = new InstrumentTimeStamps(CreateBaseTrack.this, notes, m_type_OfInstrument, m_octaves_int, 1, m_volume);

                    m_instrumentTimeStampsCopy.playInstrumentRecording();

                    m_playAllAndRecordBtn.setEnabled(false);
                    m_playAllBtn.setEnabled(false);
                    m_deleteRecordingBtn.setEnabled(false);
                    m_addTrackBtn.setEnabled(false);


                    m_recordBtn.setText("Stop/Reset");
                }
                //executed if user already played current recording, resets recording to beginning
                else if(m_recordBtn.getText().toString().equals("Stop/Reset"))
                {
                    m_instrumentTimeStampsCopy.stopPlaying();

                    m_playAllAndRecordBtn.setEnabled(true);
                    m_playAllBtn.setEnabled(true);
                    m_deleteRecordingBtn.setEnabled(true);
                    m_addTrackBtn.setEnabled(true);

                    m_recordBtn.setText("Play");
                }


            }

        });

        //button that delete a current recording of the instrument if it exists
        m_deleteRecordingBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //creating AlertDialog to ask the user for confirmation
                final AlertDialog.Builder builder = new AlertDialog.Builder(CreateBaseTrack.this);
                builder.setMessage("Delete this current recording?");
                builder.setCancelable(true);
                builder.setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //If user says nevermind, cancel dialog message
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Resets all string values for HashMap to empty
                        for(int j = 0; j < 24; j++)
                        {
                            m_noteTimes[j] = "";
                        }
                        //Clearing HashMap values for instrument
                        m_recordedPianoNotes.clear();
                        m_addTrackBtn.setEnabled(false);

                        //Setting seconds back to 0 and resetting timer and task
                        m_secondsPassed = 0;

                        m_timer = new Timer();
                        m_task = new TimerTask()
                        {

                            public void run()
                            {
                                m_secondsPassed++;
                            }
                        };

                        m_recordBtn.setText("Record");
                        m_playAllAndRecordBtn.setText("Play Main Track and Record");

                        m_recordBtn.setEnabled(true);
                        m_playAllAndRecordBtn.setEnabled(true);
                        m_deleteRecordingBtn.setEnabled(false);

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }

        });



                //Following functions are called to initialize the keyboard buttons, also gives a sound ID to each soundPool note
        int temp_c_id = m_soundPool.play(m_noteSounds[0], 1, 1, 0, 0, 1);
        holding_C(temp_c_id);

        int temp_cSharp_id = m_soundPool.play(m_noteSounds[1], 1, 1, 0, 0, 1);
        holding_CSharp(temp_cSharp_id);

        int temp_d_id = m_soundPool.play(m_noteSounds[2], 1, 1, 0, 0, 1);
        holding_D(temp_d_id);

        int temp_dSharp_id = m_soundPool.play(m_noteSounds[3], 1, 1, 0, 0, 1);
        holding_DSharp(temp_dSharp_id);

        int temp_e_id = m_soundPool.play(m_noteSounds[4], 1, 1, 0, 0, 1);
        holding_E(temp_e_id);

        int temp_f_id = m_soundPool.play(m_noteSounds[5], 1, 1, 0, 0, 1);
        holding_F(temp_f_id);

        int temp_fSharp_id = m_soundPool.play(m_noteSounds[6], 1, 1, 0, 0, 1);
        holding_FSharp(temp_fSharp_id);

        int temp_g_id = m_soundPool.play(m_noteSounds[7], 1, 1, 0, 0, 1);
        holding_G(temp_g_id);

        int temp_gSharp_id = m_soundPool.play(m_noteSounds[8], 1, 1, 0, 0, 1);
        holding_GSharp(temp_gSharp_id);

        int temp_a_id = m_soundPool.play(m_noteSounds[9], 1, 1, 0, 0, 1);
        holding_A(temp_a_id);

        int temp_aSharp_id = m_soundPool.play(m_noteSounds[10], 1, 1, 0, 0, 1);
        holding_ASharp(temp_aSharp_id);

        int temp_b_id = m_soundPool.play(m_noteSounds[11], 1, 1, 0, 0, 1);
        holding_B(temp_b_id);

        int temp_highC_id = m_soundPool.play(m_noteSounds[12], 1, 1, 0, 0, 1);
        holding_highC(temp_highC_id);

        int temp_highCSharp_id = m_soundPool.play(m_noteSounds[13], 1, 1, 0, 0, 1);
        holding_highCSharp(temp_highCSharp_id);

        int temp_highD_id = m_soundPool.play(m_noteSounds[14], 1, 1, 0, 0, 1);
        holding_highD(temp_highD_id);

        int temp_highDSharp_id = m_soundPool.play(m_noteSounds[15], 1, 1, 0, 0, 1);
        holding_highDSharp(temp_highDSharp_id);

        int temp_highE_id = m_soundPool.play(m_noteSounds[16], 1, 1, 0, 0, 1);
        holding_highE(temp_highE_id);

        int temp_highF_id = m_soundPool.play(m_noteSounds[17], 1, 1, 0, 0, 1);
        holding_highF(temp_highF_id);

        int temp_highFSharp_id = m_soundPool.play(m_noteSounds[18], 1, 1, 0, 0, 1);
        holding_highFSharp(temp_highFSharp_id);

        int temp_highG_id = m_soundPool.play(m_noteSounds[19], 1, 1, 0, 0, 1);
        holding_highG(temp_highG_id);

        int temp_highGSharp_id = m_soundPool.play(m_noteSounds[20], 1, 1, 0, 0, 1);
        holding_highGSharp(temp_highGSharp_id);

        int temp_highA_id = m_soundPool.play(m_noteSounds[21], 1, 1, 0, 0, 1);
        holding_highA(temp_highA_id);

        int temp_highASharp_id = m_soundPool.play(m_noteSounds[22], 1, 1, 0, 0, 1);
        holding_highASharp(temp_highASharp_id);

        int temp_highB_id = m_soundPool.play(m_noteSounds[23], 1, 1, 0, 0, 1);
        holding_highB(temp_highB_id);

    }


    /*
        NAME

            init_Instrument  -->

        SYNOPSIS

            public void init_Instrument(int a_instrument, int a_octave)

                int a_instrument ---> integer that defines which instrument to load (0 for piano, 1 for guitar)
                int a_octave   -->  integer that defines which octaves to load (1-4 for piano, 1-2 for guitar)

        DESCRIPTION

                This method creates the SoundPool object and initializes the sounds for the virtual keyboard.
                After building the latest version of SoundPool, this function loads the .mp3 files into SoundPool.
                Depending on the selected instrument and octaves, this function also hides specific buttons on the keyboard if they cannot be played with desired octave.

        RETURNS

            void

        AUTHOR

            Frank Ryan


    */
    public void init_Instrument(int a_instrument, int a_octave)
    {
        //builds SoundPool object with most recent version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            m_soundPool = new SoundPool.Builder().setMaxStreams(8).build();
        }
        else
        {
            m_soundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
        }
        //executed if instrument is piano
        if(a_instrument == 0)
        {
            //executed if instrument is octaves 1-2
            //loads appropriate sounds and hides buttons if needed
            if(a_octave == 1)
            {
                m_noteSounds[0] = m_soundPool.load(this, R.raw.c1w, 1);
                m_noteSounds[1] = m_soundPool.load(this, R.raw.c_sharp1w, 1);
                m_noteSounds[2] = m_soundPool.load(this, R.raw.d1w, 1);
                m_noteSounds[3] = m_soundPool.load(this, R.raw.d_sharp1w, 1);
                m_noteSounds[4] = m_soundPool.load(this, R.raw.e1w, 1);
                m_noteSounds[5] = m_soundPool.load(this, R.raw.f1w, 1);
                m_noteSounds[6] = m_soundPool.load(this, R.raw.f_sharp1w, 1);
                m_noteSounds[7] = m_soundPool.load(this, R.raw.g1w, 1);
                m_noteSounds[8] = m_soundPool.load(this, R.raw.g_sharp1w, 1);
                m_noteSounds[9] = m_soundPool.load(this, R.raw.a1w, 1);
                m_noteSounds[10] = m_soundPool.load(this, R.raw.a_sharp1w, 1);
                m_noteSounds[11] = m_soundPool.load(this, R.raw.b1w, 1);


                m_noteSounds[12] = m_soundPool.load(this, R.raw.c2w, 1);
                m_noteSounds[13] = m_soundPool.load(this, R.raw.c_sharp2w, 1);
                m_noteSounds[14] = m_soundPool.load(this, R.raw.d2w, 1);
                m_noteSounds[15] = m_soundPool.load(this, R.raw.d_sharp2w, 1);
                m_noteSounds[16] = m_soundPool.load(this, R.raw.e2w, 1);
                m_noteSounds[17] = m_soundPool.load(this, R.raw.f2w, 1);
                m_noteSounds[18] = m_soundPool.load(this, R.raw.f_sharp2w, 1);
                m_noteSounds[19] = m_soundPool.load(this, R.raw.g2w, 1);
                m_noteSounds[20] = m_soundPool.load(this, R.raw.g_sharp2w, 1);
                m_noteSounds[21] = m_soundPool.load(this, R.raw.a2w, 1);
                m_noteSounds[22] = m_soundPool.load(this, R.raw.a_sharp2w, 1);
                m_noteSounds[23] = m_soundPool.load(this, R.raw.b2w, 1);

                for(int i = 0; i < 8; i++)
                {
                    m_notesBtn[i].setVisibility(View.INVISIBLE);
                }

            }
            //executed if instrument is octaves 3-4
            //loads appropriate sounds and hides buttons if needed
            else if (a_octave == 2)
            {
                m_noteSounds[0] = m_soundPool.load(this, R.raw.c3w, 1);
                m_noteSounds[1] = m_soundPool.load(this, R.raw.c_sharp3w, 1);
                m_noteSounds[2] = m_soundPool.load(this, R.raw.d3w, 1);
                m_noteSounds[3] = m_soundPool.load(this, R.raw.d_sharp3w, 1);
                m_noteSounds[4] = m_soundPool.load(this, R.raw.e3w, 1);
                m_noteSounds[5] = m_soundPool.load(this, R.raw.f3w, 1);
                m_noteSounds[6] = m_soundPool.load(this, R.raw.f_sharp3w, 1);
                m_noteSounds[7] = m_soundPool.load(this, R.raw.g3w, 1);
                m_noteSounds[8] = m_soundPool.load(this, R.raw.g_sharp3w, 1);
                m_noteSounds[9] = m_soundPool.load(this, R.raw.a3w, 1);
                m_noteSounds[10] = m_soundPool.load(this, R.raw.a_sharp3w, 1);
                m_noteSounds[11] = m_soundPool.load(this, R.raw.b3w, 1);


                m_noteSounds[12] = m_soundPool.load(this, R.raw.c4w, 1);
                m_noteSounds[13] = m_soundPool.load(this, R.raw.c_sharp4w, 1);
                m_noteSounds[14] = m_soundPool.load(this, R.raw.d4w, 1);
                m_noteSounds[15] = m_soundPool.load(this, R.raw.d_sharp4w, 1);
                m_noteSounds[16] = m_soundPool.load(this, R.raw.e4w, 1);
                m_noteSounds[17] = m_soundPool.load(this, R.raw.f4w, 1);
                m_noteSounds[18] = m_soundPool.load(this, R.raw.f_sharp4w, 1);
                m_noteSounds[19] = m_soundPool.load(this, R.raw.g4w, 1);
                m_noteSounds[20] = m_soundPool.load(this, R.raw.g_sharp4w, 1);
                m_noteSounds[21] = m_soundPool.load(this, R.raw.a4w, 1);
                m_noteSounds[22] = m_soundPool.load(this, R.raw.a_sharp4w, 1);
                m_noteSounds[23] = m_soundPool.load(this, R.raw.b4w, 1);
            }
            //executed if instrument is octaves 5-6
            //loads appropriate sounds and hides buttons if needed
            else if (a_octave == 3)
            {
                m_noteSounds[0] = m_soundPool.load(this, R.raw.c5w, 1);
                m_noteSounds[1] = m_soundPool.load(this, R.raw.c_sharp5w, 1);
                m_noteSounds[2] = m_soundPool.load(this, R.raw.d5w, 1);
                m_noteSounds[3] = m_soundPool.load(this, R.raw.d_sharp5w, 1);
                m_noteSounds[4] = m_soundPool.load(this, R.raw.e5w, 1);
                m_noteSounds[5] = m_soundPool.load(this, R.raw.f5w, 1);
                m_noteSounds[6] = m_soundPool.load(this, R.raw.f_sharp5w, 1);
                m_noteSounds[7] = m_soundPool.load(this, R.raw.g5w, 1);
                m_noteSounds[8] = m_soundPool.load(this, R.raw.g_sharp5w, 1);
                m_noteSounds[9] = m_soundPool.load(this, R.raw.a5w, 1);
                m_noteSounds[10] = m_soundPool.load(this, R.raw.a_sharp5w, 1);
                m_noteSounds[11] = m_soundPool.load(this, R.raw.b5w, 1);


                m_noteSounds[12] = m_soundPool.load(this, R.raw.c6w, 1);
                m_noteSounds[13] = m_soundPool.load(this, R.raw.c_sharp6w, 1);
                m_noteSounds[14] = m_soundPool.load(this, R.raw.d6w, 1);
                m_noteSounds[15] = m_soundPool.load(this, R.raw.d_sharp6w, 1);
                m_noteSounds[16] = m_soundPool.load(this, R.raw.e6w, 1);
                m_noteSounds[17] = m_soundPool.load(this, R.raw.f6w, 1);
                m_noteSounds[18] = m_soundPool.load(this, R.raw.f_sharp6w, 1);
                m_noteSounds[19] = m_soundPool.load(this, R.raw.g6w, 1);
                m_noteSounds[20] = m_soundPool.load(this, R.raw.g_sharp6w, 1);
                m_noteSounds[21] = m_soundPool.load(this, R.raw.a6w, 1);
                m_noteSounds[22] = m_soundPool.load(this, R.raw.a_sharp6w, 1);
                m_noteSounds[23] = m_soundPool.load(this, R.raw.b6w, 1);
            }
            //executed if instrument is octaves 7-8
            //loads appropriate sounds and hides buttons if needed
            else if (a_octave == 4)
            {
                m_noteSounds[0] = m_soundPool.load(this, R.raw.c7w, 1);
                m_noteSounds[1] = m_soundPool.load(this, R.raw.c_sharp7w, 1);
                m_noteSounds[2] = m_soundPool.load(this, R.raw.d7w, 1);
                m_noteSounds[3] = m_soundPool.load(this, R.raw.d_sharp7w, 1);
                m_noteSounds[4] = m_soundPool.load(this, R.raw.e7w, 1);
                m_noteSounds[5] = m_soundPool.load(this, R.raw.f7w, 1);
                m_noteSounds[6] = m_soundPool.load(this, R.raw.f_sharp7w, 1);
                m_noteSounds[7] = m_soundPool.load(this, R.raw.g7w, 1);
                m_noteSounds[8] = m_soundPool.load(this, R.raw.g_sharp7w, 1);
                m_noteSounds[9] = m_soundPool.load(this, R.raw.a7w, 1);
                m_noteSounds[10] = m_soundPool.load(this, R.raw.a_sharp7w, 1);
                m_noteSounds[11] = m_soundPool.load(this, R.raw.b7w, 1);


                m_noteSounds[12] = m_soundPool.load(this, R.raw.c1w, 1);
                m_noteSounds[13] = m_soundPool.load(this, R.raw.c_sharp1w, 1);
                m_noteSounds[14] = m_soundPool.load(this, R.raw.d1w, 1);
                m_noteSounds[15] = m_soundPool.load(this, R.raw.d_sharp1w, 1);
                m_noteSounds[16] = m_soundPool.load(this, R.raw.e1w, 1);
                m_noteSounds[17] = m_soundPool.load(this, R.raw.f1w, 1);
                m_noteSounds[18] = m_soundPool.load(this, R.raw.f_sharp1w, 1);
                m_noteSounds[19] = m_soundPool.load(this, R.raw.g1w, 1);
                m_noteSounds[20] = m_soundPool.load(this, R.raw.g_sharp1w, 1);
                m_noteSounds[21] = m_soundPool.load(this, R.raw.a1w, 1);
                m_noteSounds[22] = m_soundPool.load(this, R.raw.a_sharp1w, 1);
                m_noteSounds[23] = m_soundPool.load(this, R.raw.b1w, 1);

                for(int i = 12; i < 24; i++)
                {
                    m_notesBtn[i].setVisibility(View.INVISIBLE);
                }
            }
        }
        //executed if instrument is guitar
        else if (a_instrument == 1)
        {
            //executed if instrument is octaves 1-2
            //loads appropriate sounds and hides buttons if needed
            if(a_octave == 1)
            {

                m_noteSounds[0] = m_soundPool.load(this, R.raw.c1w_guitar, 1);
                m_noteSounds[1] = m_soundPool.load(this, R.raw.c_sharp1w_guitar, 1);
                m_noteSounds[2] = m_soundPool.load(this, R.raw.d1w_guitar, 1);
                m_noteSounds[3] = m_soundPool.load(this, R.raw.d_sharp1w_guitar, 1);
                m_noteSounds[4] = m_soundPool.load(this, R.raw.e1w_guitar, 1);
                m_noteSounds[5] = m_soundPool.load(this, R.raw.f1w_guitar, 1);
                m_noteSounds[6] = m_soundPool.load(this, R.raw.f_sharp1w_guitar, 1);
                m_noteSounds[7] = m_soundPool.load(this, R.raw.g1w_guitar, 1);
                m_noteSounds[8] = m_soundPool.load(this, R.raw.g_sharp1w_guitar, 1);
                m_noteSounds[9] = m_soundPool.load(this, R.raw.a1w_guitar, 1);
                m_noteSounds[10] = m_soundPool.load(this, R.raw.a_sharp1w_guitar, 1);
                m_noteSounds[11] = m_soundPool.load(this, R.raw.b1w_guitar, 1);


                m_noteSounds[12] = m_soundPool.load(this, R.raw.c1w_guitar, 1);
                m_noteSounds[13] = m_soundPool.load(this, R.raw.c_sharp1w_guitar, 1);
                m_noteSounds[14] = m_soundPool.load(this, R.raw.d1w_guitar, 1);
                m_noteSounds[15] = m_soundPool.load(this, R.raw.d_sharp1w_guitar, 1);
                m_noteSounds[16] = m_soundPool.load(this, R.raw.e2w_guitar, 1);
                m_noteSounds[17] = m_soundPool.load(this, R.raw.f2w_guitar, 1);
                m_noteSounds[18] = m_soundPool.load(this, R.raw.f_sharp2w_guitar, 1);
                m_noteSounds[19] = m_soundPool.load(this, R.raw.g2w_guitar, 1);
                m_noteSounds[20] = m_soundPool.load(this, R.raw.g_sharp2w_guitar, 1);
                m_noteSounds[21] = m_soundPool.load(this, R.raw.a2w_guitar, 1);
                m_noteSounds[22] = m_soundPool.load(this, R.raw.a_sharp2w_guitar, 1);
                m_noteSounds[23] = m_soundPool.load(this, R.raw.b2w_guitar, 1);

                for(int i = 0; i < 4; i++)
                {
                    m_notesBtn[i].setVisibility(View.INVISIBLE);
                }



            }
            //executed if instrument is octaves 2-3
            //loads appropriate sounds and hides buttons if needed
            else if (a_octave == 2)
            {
                m_noteSounds[0] = m_soundPool.load(this, R.raw.c1w_guitar, 1);
                m_noteSounds[1] = m_soundPool.load(this, R.raw.c_sharp1w_guitar, 1);
                m_noteSounds[2] = m_soundPool.load(this, R.raw.d1w_guitar, 1);
                m_noteSounds[3] = m_soundPool.load(this, R.raw.d_sharp1w_guitar, 1);
                m_noteSounds[4] = m_soundPool.load(this, R.raw.e2w_guitar, 1);
                m_noteSounds[5] = m_soundPool.load(this, R.raw.f2w_guitar, 1);
                m_noteSounds[6] = m_soundPool.load(this, R.raw.f_sharp2w_guitar, 1);
                m_noteSounds[7] = m_soundPool.load(this, R.raw.g2w_guitar, 1);
                m_noteSounds[8] = m_soundPool.load(this, R.raw.g_sharp2w_guitar, 1);
                m_noteSounds[9] = m_soundPool.load(this, R.raw.a2w_guitar, 1);
                m_noteSounds[10] = m_soundPool.load(this, R.raw.a_sharp2w_guitar, 1);
                m_noteSounds[11] = m_soundPool.load(this, R.raw.b2w_guitar, 1);


                m_noteSounds[12] = m_soundPool.load(this, R.raw.c2w_guitar, 1);
                m_noteSounds[13] = m_soundPool.load(this, R.raw.c_sharp2w_guitar, 1);
                m_noteSounds[14] = m_soundPool.load(this, R.raw.d2w_guitar, 1);
                m_noteSounds[15] = m_soundPool.load(this, R.raw.d_sharp2w_guitar, 1);
                m_noteSounds[16] = m_soundPool.load(this, R.raw.e3w_guitar, 1);
                m_noteSounds[17] = m_soundPool.load(this, R.raw.f3w_guitar, 1);
                m_noteSounds[18] = m_soundPool.load(this, R.raw.f_sharp3w_guitar, 1);
                m_noteSounds[19] = m_soundPool.load(this, R.raw.g3w_guitar, 1);
                m_noteSounds[20] = m_soundPool.load(this, R.raw.g_sharp3w_guitar, 1);
                m_noteSounds[21] = m_soundPool.load(this, R.raw.a3w_guitar, 1);
                m_noteSounds[22] = m_soundPool.load(this, R.raw.a_sharp3w_guitar, 1);
                m_noteSounds[23] = m_soundPool.load(this, R.raw.b3w_guitar, 1);
            }
        }
        //instrument is electric piano
        else if(a_instrument == 2)
        {
            if (a_octave == 1) {

                m_noteSounds[0] = m_soundPool.load(this, R.raw.c4w_electric, 1);
                m_noteSounds[1] = m_soundPool.load(this, R.raw.c_sharp4w_electric, 1);
                m_noteSounds[2] = m_soundPool.load(this, R.raw.d4w_electric, 1);
                m_noteSounds[3] = m_soundPool.load(this, R.raw.d_sharp4w_electric, 1);
                m_noteSounds[4] = m_soundPool.load(this, R.raw.e4w_electric, 1);
                m_noteSounds[5] = m_soundPool.load(this, R.raw.f4w_electric, 1);
                m_noteSounds[6] = m_soundPool.load(this, R.raw.f_sharp4w_electric, 1);
                m_noteSounds[7] = m_soundPool.load(this, R.raw.g4w_electric, 1);
                m_noteSounds[8] = m_soundPool.load(this, R.raw.g_sharp4w_electric, 1);
                m_noteSounds[9] = m_soundPool.load(this, R.raw.a4w_electric, 1);
                m_noteSounds[10] = m_soundPool.load(this, R.raw.a_sharp4w_electric, 1);
                m_noteSounds[11] = m_soundPool.load(this, R.raw.b4w_electric, 1);


                m_noteSounds[12] = m_soundPool.load(this, R.raw.c5w_electric, 1);
                m_noteSounds[13] = m_soundPool.load(this, R.raw.c_sharp5w_electric, 1);
                m_noteSounds[14] = m_soundPool.load(this, R.raw.d5w_electric, 1);
                m_noteSounds[15] = m_soundPool.load(this, R.raw.d_sharp5w_electric, 1);
                m_noteSounds[16] = m_soundPool.load(this, R.raw.e5w_electric, 1);
                m_noteSounds[17] = m_soundPool.load(this, R.raw.f5w_electric, 1);
                m_noteSounds[18] = m_soundPool.load(this, R.raw.f_sharp5w_electric, 1);
                m_noteSounds[19] = m_soundPool.load(this, R.raw.g5w_electric, 1);
                m_noteSounds[20] = m_soundPool.load(this, R.raw.g_sharp5w_electric, 1);
                m_noteSounds[21] = m_soundPool.load(this, R.raw.a5w_electric, 1);
                m_noteSounds[22] = m_soundPool.load(this, R.raw.a_sharp5w_electric, 1);
                m_noteSounds[23] = m_soundPool.load(this, R.raw.b5w_electric, 1);
            }
        }

    }
    /*
        NAME

            playMainTrack  -->

        SYNOPSIS

            public void playMainTrack(InstrumentTimeStamps a_instrument1, InstrumentTimeStamps a_instrument2, InstrumentTimeStamps a_instrument3, InstrumentTimeStamps a_instrument4)

                a_instrument1 -> holds instrument1 recording if it exists
                a_instrument2 -> holds instrument2 recording if it exists
                a_instrument3 -> holds instrument3 recording if it exists
                a_instrument4 -> holds instrument4 recording if it exists

        DESCRIPTION

                Plays all recordings that are found from the main track. Does so by replication the parameter instrument so that the HashMap is not deleted from playInstrumentRecording

        RETURNS

            void

        AUTHOR

            Frank Ryan


    */
    public void playMainTrack(InstrumentTimeStamps a_instrument1, InstrumentTimeStamps a_instrument2, InstrumentTimeStamps a_instrument3, InstrumentTimeStamps a_instrument4)
    {
        //Depending on the number of instruments, replicate the InstrumentTimeStamps objects and play the recording
        if (m_numberOfInstruments == 0) {

        } else if (m_numberOfInstruments == 1) {
            HashMap<String, String> recordedNotes1 = new HashMap<>(a_instrument1.getPianoNotes());
            m_playInstrument1 = new InstrumentTimeStamps(CreateBaseTrack.this, recordedNotes1, a_instrument1.getM_instrumentID(), a_instrument1.getM_octaves(), a_instrument1.getM_volumeSelection(), m_volume);

            m_playInstrument1.playInstrumentRecording();


        } else if (m_numberOfInstruments == 2) {

            Timer a_timer = new Timer();

            HashMap<String, String> recordedNotes1 = new HashMap<>(a_instrument1.getPianoNotes());
            m_playInstrument1 = new InstrumentTimeStamps(CreateBaseTrack.this, recordedNotes1, a_instrument1.getM_instrumentID(), a_instrument1.getM_octaves(), a_instrument1.getM_volumeSelection(), m_volume);
            m_playInstrument1.playInstrumentRecording(a_timer);

            HashMap<String, String> recordedNotes2 = new HashMap<>(a_instrument2.getPianoNotes());
            m_playInstrument2 = new InstrumentTimeStamps(CreateBaseTrack.this, recordedNotes2, a_instrument2.getM_instrumentID(), a_instrument2.getM_octaves(), a_instrument2.getM_volumeSelection(), m_volume);
            m_playInstrument2.playInstrumentRecording(a_timer);


        } else if (m_numberOfInstruments == 3) {

            Timer a_timer = new Timer();

            HashMap<String, String> recordedNotes1 = new HashMap<>(a_instrument1.getPianoNotes());
            m_playInstrument1 = new InstrumentTimeStamps(CreateBaseTrack.this, recordedNotes1, a_instrument1.getM_instrumentID(), a_instrument1.getM_octaves(), a_instrument1.getM_volumeSelection(), m_volume);
            m_playInstrument1.playInstrumentRecording(a_timer);

            HashMap<String, String> recordedNotes2 = new HashMap<>(a_instrument2.getPianoNotes());
            m_playInstrument2 = new InstrumentTimeStamps(CreateBaseTrack.this, recordedNotes2, a_instrument2.getM_instrumentID(), a_instrument2.getM_octaves(), a_instrument2.getM_volumeSelection(), m_volume);
            m_playInstrument2.playInstrumentRecording(a_timer);

            HashMap<String, String> recordedNotes3 = new HashMap<>(a_instrument3.getPianoNotes());
            m_playInstrument3 = new InstrumentTimeStamps(CreateBaseTrack.this, recordedNotes3, a_instrument3.getM_instrumentID(), a_instrument3.getM_octaves(), a_instrument3.getM_volumeSelection(), m_volume);
            m_playInstrument3.playInstrumentRecording(a_timer);

        } else if (m_numberOfInstruments == 4) {

            Timer a_timer = new Timer();

            HashMap<String, String> recordedNotes1 = new HashMap<>(a_instrument1.getPianoNotes());
            m_playInstrument1 = new InstrumentTimeStamps(CreateBaseTrack.this, recordedNotes1, a_instrument1.getM_instrumentID(), a_instrument1.getM_octaves(), a_instrument1.getM_volumeSelection(), m_volume);
            m_playInstrument1.playInstrumentRecording(a_timer);

            HashMap<String, String> recordedNotes2 = new HashMap<>(a_instrument2.getPianoNotes());
            m_playInstrument2 = new InstrumentTimeStamps(CreateBaseTrack.this, recordedNotes2, a_instrument2.getM_instrumentID(), a_instrument2.getM_octaves(), a_instrument2.getM_volumeSelection(), m_volume);
            m_playInstrument2.playInstrumentRecording(a_timer);

            HashMap<String, String> recordedNotes3 = new HashMap<>(a_instrument3.getPianoNotes());
            m_playInstrument3 = new InstrumentTimeStamps(CreateBaseTrack.this, recordedNotes3, a_instrument3.getM_instrumentID(), a_instrument3.getM_octaves(), a_instrument3.getM_volumeSelection(), m_volume);
            m_playInstrument3.playInstrumentRecording(a_timer);

            HashMap<String, String> recordedNotes4 = new HashMap<>(a_instrument4.getPianoNotes());
            m_playInstrument4 = new InstrumentTimeStamps(CreateBaseTrack.this, recordedNotes4, a_instrument4.getM_instrumentID(), a_instrument4.getM_octaves(), a_instrument4.getM_volumeSelection(), m_volume);
            m_playInstrument4.playInstrumentRecording(a_timer);
        }
    }



    /*
    NAME

        holding_C  -->

    SYNOPSIS

        public void holding_C(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing low C sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the low C button.

        When clicked, the SoundPool object plays the appropriate sound. Also, if the user is recording, the current secondsPassed integer is concatenated to the approriate string.
        For example, if secondsPassed equals 10, then this function concatenated "10-" to the appropriate string.
        The background of the key is set to the pressed_key_green.xmlen.xml drawable, and the function is recursively called with the new SoundPool id to listen for when the user lets go of the key.

        When the user stops holding the key, the SoundPool objects stop the appropriate sound. Also, if the user is recording, the current secondsPassed integer is concatenated to the appropriate string.
        For example, if secondsPassed equals 20, then this function concatenated "20," to the appropriate string.
        The background of the key is set to the button_border.xml drawable, and the function is recursively called with the null SoundPool id to listen for when the user presses the key again.


    RETURNS

        void

    AUTHOR

        Frank Ryan


    */
    public void holding_C(int a_soundID)
    {
        final int c_id = a_soundID;
        //Listens for keyboard button to be pressed or lifted up
        m_notesBtn[0].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {

                //in event of button being pressed
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    //if the user is recording instrument, log this time into appropriate m_noteTimes[], adding m_secondsPassed and a - , ex. "22-"
                    if(m_isRecording == true)
                    {
                        m_noteTimes[0] += m_secondsPassed + "-";
                    }
                    //a new sound ID is created to play the sound and recursively call function to listen for when the button is lifted up
                    int new_id = m_soundPool.play(m_noteSounds[0], 1, 1, 0, 0, 1);

                    //image of button is set to green
                    m_notesBtn[0].setBackgroundResource(R.drawable.pressed_key_green);

                    holding_C(new_id);
                }
                //in event of button being lifted up
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    //image of button is set back to default
                    m_notesBtn[0].setBackgroundResource(R.drawable.button_border);

                    //if the user is recording instrument, log the time again, ex. "55,"
                    if(m_isRecording == true)
                    {
                        m_noteTimes[0] += m_secondsPassed + ",";
                    }
                    //stops the sound of note and calls function to listen for when the button is pressed again
                    m_soundPool.stop(c_id);

                    holding_C(c_id);

                }


                return false;
            }
        });

    }

    /*
    NAME

        holding_CSharp -->

    SYNOPSIS

        public void holding_CSharp(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing low CSharp sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the low CSharp button.

        If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
        For more information on this and to avoid redundancy, look at the holding_C documentation.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void holding_CSharp(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int cSharp_id = a_soundID;
        m_notesBtn[1].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {




                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(m_isRecording == true)
                    {
                        m_noteTimes[1] += m_secondsPassed + "-";
                    }

                    int new_id = m_soundPool.play(m_noteSounds[1], 1, 1, 0, 0, 1);

                    m_notesBtn[1].setBackgroundResource(R.drawable.pressed_key_green);
                    holding_CSharp(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[1].setBackgroundResource(R.drawable.black_keys);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[1] += m_secondsPassed + ",";
                    }
                    m_soundPool.stop(cSharp_id);
                }


                return false;
            }
        });

    }
    /*
    NAME

        holding_D-->

    SYNOPSIS

        public void holding_D(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing low D sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the low D button.

        If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
        For more information on this and to avoid redundancy, look at the holding_C documentation.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void holding_D(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int d_id = a_soundID;
        m_notesBtn[2].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {



                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    m_notesBtn[2].setBackgroundResource(R.drawable.pressed_key_green);
                    if(m_isRecording == true)
                    {
                        m_noteTimes[2] += m_secondsPassed + "-";
                    }
                    int new_id = m_soundPool.play(m_noteSounds[2], 1, 1, 0, 0, 1);
                    holding_D(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[2].setBackgroundResource(R.drawable.button_border);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[2] += m_secondsPassed + ",";
                    }
                    m_soundPool.stop(d_id);
                }


                return false;
            }
        });

    }
    /*
    NAME

        holding_DSharp -->

    SYNOPSIS

        public void holding_DSharp(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing low DSharp sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the low DSharp button.

        If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
        For more information on this and to avoid redundancy, look at the holding_C documentation.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void holding_DSharp(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int dSharp_id = a_soundID;
        m_notesBtn[3].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    m_notesBtn[3].setBackgroundResource(R.drawable.pressed_key_green);
                    if(m_isRecording == true)
                    {
                        m_noteTimes[3] += m_secondsPassed + "-";
                    }
                    int new_id = m_soundPool.play(m_noteSounds[3], 1, 1, 0, 0, 1);
                    holding_DSharp(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[3].setBackgroundResource(R.drawable.black_keys);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[3] += m_secondsPassed + ",";
                    }
                    m_soundPool.stop(dSharp_id);
                }


                return false;
            }
        });

    }
    /*
    NAME

        holding_E  -->

    SYNOPSIS

        public void holding_E(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing low E sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the low E button.

        If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
        For more information on this and to avoid redundancy, look at the holding_C documentation.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void holding_E(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int e_id = a_soundID;
        m_notesBtn[4].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    m_notesBtn[4].setBackgroundResource(R.drawable.pressed_key_green);
                    if(m_isRecording == true)
                    {
                        m_noteTimes[4] += m_secondsPassed + "-";
                    }

                    int new_id = m_soundPool.play(m_noteSounds[4], 1, 1, 0, 0, 1);
                    holding_E(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[4].setBackgroundResource(R.drawable.button_border);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[4] += m_secondsPassed + ",";
                    }

                    m_soundPool.stop(e_id);
                }


                return false;
            }
        });

    }
    /*
    NAME

        holding_F -->

    SYNOPSIS

        public void holding_F(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing low F sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the low F button.

        If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
        For more information on this and to avoid redundancy, look at the holding_C documentation.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void holding_F(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int f_id = a_soundID;
        m_notesBtn[5].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    m_notesBtn[5].setBackgroundResource(R.drawable.pressed_key_green);
                    if(m_isRecording == true)
                    {
                        m_noteTimes[5] += m_secondsPassed + "-";
                    }
                    int new_id = m_soundPool.play(m_noteSounds[5], 1, 1, 0, 0, 1);
                    holding_F(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[5].setBackgroundResource(R.drawable.button_border);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[5] += m_secondsPassed + ",";
                    }
                    m_soundPool.stop(f_id);
                }


                return false;
            }
        });

    }
    /*
    NAME

        holding_FSharp -->

    SYNOPSIS

        public void holding_FSharp(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing low FSharp sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the low FSharp button.

        If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
        For more information on this and to avoid redundancy, look at the holding_C documentation.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void holding_FSharp(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int fSharp_id = a_soundID;
        m_notesBtn[6].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    m_notesBtn[6].setBackgroundResource(R.drawable.pressed_key_green);
                    if(m_isRecording == true)
                    {
                        m_noteTimes[6] += m_secondsPassed + "-";
                    }
                    int new_id = m_soundPool.play(m_noteSounds[6], 1, 1, 0, 0, 1);
                    holding_FSharp(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[6].setBackgroundResource(R.drawable.black_keys);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[6] += m_secondsPassed + ",";
                    }
                    m_soundPool.stop(fSharp_id);
                }


                return false;
            }
        });

    }
    /*
    NAME

        holding_G -->

    SYNOPSIS

        public void holding_G(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing low G sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the low G button.

        If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
        For more information on this and to avoid redundancy, look at the holding_C documentation.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void holding_G(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int g_id = a_soundID;
        m_notesBtn[7].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    m_notesBtn[7].setBackgroundResource(R.drawable.pressed_key_green);
                    if(m_isRecording == true)
                    {
                        m_noteTimes[7] += m_secondsPassed + "-";
                    }
                    int new_id = m_soundPool.play(m_noteSounds[7], 1, 1, 0, 0, 1);
                    holding_G(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[7].setBackgroundResource(R.drawable.button_border);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[7] += m_secondsPassed + ",";
                    }
                    m_soundPool.stop(g_id);
                }


                return false;
            }
        });

    }
    /*
    NAME

        holding_GSharp -->

    SYNOPSIS

        public void holding_GSharp(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing low GSharp sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the low GSharp button.

        If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
        For more information on this and to avoid redundancy, look at the holding_C documentation.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void holding_GSharp(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int gSharp_id = a_soundID;
        m_notesBtn[8].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    m_notesBtn[8].setBackgroundResource(R.drawable.pressed_key_green);
                    if(m_isRecording == true)
                    {
                        m_noteTimes[8] += m_secondsPassed + "-";
                    }
                    int new_id = m_soundPool.play(m_noteSounds[8], 1, 1, 0, 0, 1);
                    holding_GSharp(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[8].setBackgroundResource(R.drawable.black_keys);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[8] += m_secondsPassed + ",";
                    }
                    m_soundPool.stop(gSharp_id);
                }


                return false;
            }
        });

    }
    /*
    NAME

        holding_A -->

    SYNOPSIS

        public void holding_A(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing low A sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the low A button.

        If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
        For more information on this and to avoid redundancy, look at the holding_C documentation.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */

    public void holding_A(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int a_id = a_soundID;
        m_notesBtn[9].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    m_notesBtn[9].setBackgroundResource(R.drawable.pressed_key_green);
                    if(m_isRecording == true)
                    {
                        m_noteTimes[9] += m_secondsPassed + "-";
                    }
                    int new_id = m_soundPool.play(m_noteSounds[9], 1, 1, 0, 0, 1);
                    holding_A(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[9].setBackgroundResource(R.drawable.button_border);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[9] += m_secondsPassed + ",";
                    }
                    m_soundPool.stop(a_id);
                }


                return false;
            }
        });

    }
    /*
    NAME

        holding_ASharp -->

    SYNOPSIS

        public void holding_ASharp(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing low ASharp sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the low ASharp button.

        If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
        For more information on this and to avoid redundancy, look at the holding_C documentation.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */

    public void holding_ASharp(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int aSharp_id = a_soundID;
        m_notesBtn[10].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    m_notesBtn[10].setBackgroundResource(R.drawable.pressed_key_green);
                    if(m_isRecording == true)
                    {
                        m_noteTimes[10] += m_secondsPassed + "-";
                    }
                    int new_id = m_soundPool.play(m_noteSounds[10], 1, 1, 0, 0, 1);
                    holding_ASharp(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[10].setBackgroundResource(R.drawable.black_keys);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[10] += m_secondsPassed + ",";
                    }
                    m_soundPool.stop(aSharp_id);
                }


                return false;
            }
        });

    }
    /*
    NAME

        holding_B -->

    SYNOPSIS

        public void holding_B(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing low B sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the low B button.

        If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
        For more information on this and to avoid redundancy, look at the holding_C documentation.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */

    public void holding_B(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int b_id = a_soundID;
        m_notesBtn[11].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    m_notesBtn[11].setBackgroundResource(R.drawable.pressed_key_green);
                    if(m_isRecording == true)
                    {
                        m_noteTimes[11] += m_secondsPassed + "-";
                    }
                    int new_id = m_soundPool.play(m_noteSounds[11], 1, 1, 0, 0, 1);
                    holding_B(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[11].setBackgroundResource(R.drawable.button_border);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[11] += m_secondsPassed + ",";
                    }
                    m_soundPool.stop(b_id);
                }


                return false;
            }
        });

    }
    /*
    NAME

        holding_highC -->

    SYNOPSIS

        public void holding_highC(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing high C sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the high C button.

        If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
        For more information on this and to avoid redundancy, look at the holding_C documentation.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */


    public void holding_highC(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int highC_id = a_soundID;
        m_notesBtn[12].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    m_notesBtn[12].setBackgroundResource(R.drawable.pressed_key_green);
                    if(m_isRecording == true)
                    {
                        m_noteTimes[12] += m_secondsPassed + "-";
                    }
                    int new_id = m_soundPool.play(m_noteSounds[12], 1, 1, 0, 0, 1);
                    holding_highC(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[12].setBackgroundResource(R.drawable.button_border);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[12] += m_secondsPassed + ",";
                    }
                    m_soundPool.stop(highC_id);
                }


                return false;
            }
        });

    }
    /*
    NAME

        holding_highCSharp -->

    SYNOPSIS

        public void holding_highCSharp(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing high CSharp sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the high CSharp button.

        If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
        For more information on this and to avoid redundancy, look at the holding_C documentation.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */

    public void holding_highCSharp(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int cSharp_id = a_soundID;
        m_notesBtn[13].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    m_notesBtn[13].setBackgroundResource(R.drawable.pressed_key_green);
                    if(m_isRecording == true)
                    {
                        m_noteTimes[13] += m_secondsPassed + "-";
                    }

                    int new_id = m_soundPool.play(m_noteSounds[13], 1, 1, 0, 0, 1);
                    holding_highCSharp(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[13].setBackgroundResource(R.drawable.black_keys);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[13] += m_secondsPassed + ",";
                    }
                    m_soundPool.stop(cSharp_id);
                }


                return false;
            }
        });

    }
    /*
    NAME

        holding_highD -->

    SYNOPSIS

        public void holding_highD(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing high D sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the high D button.

        If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
        For more information on this and to avoid redundancy, look at the holding_C documentation.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void holding_highD(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int d_id = a_soundID;
        m_notesBtn[14].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {



                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    m_notesBtn[14].setBackgroundResource(R.drawable.pressed_key_green);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[14] += m_secondsPassed + "-";
                    }
                    int new_id = m_soundPool.play(m_noteSounds[14], 1, 1, 0, 0, 1);
                    holding_highD(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[14].setBackgroundResource(R.drawable.button_border);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[14] += m_secondsPassed + ",";
                    }
                    m_soundPool.stop(d_id);
                }


                return false;
            }
        });

    }
    /*
    NAME

        holding_highDSharp -->

    SYNOPSIS

        public void holding_highDSharp(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing high DSharp sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the high DSharp button.

        If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
        For more information on this and to avoid redundancy, look at the holding_C documentation.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void holding_highDSharp(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int dSharp_id = a_soundID;
        m_notesBtn[15].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    m_notesBtn[15].setBackgroundResource(R.drawable.pressed_key_green);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[15] += m_secondsPassed + "-";
                    }
                    int new_id = m_soundPool.play(m_noteSounds[15], 1, 1, 0, 0, 1);
                    holding_highDSharp(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[15].setBackgroundResource(R.drawable.black_keys);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[15] += m_secondsPassed + ",";
                    }
                    m_soundPool.stop(dSharp_id);
                }


                return false;
            }
        });

    }
    /*
    NAME

        holding_highE -->

    SYNOPSIS

        public void holding_highE(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing high E sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the high E button.

        If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
        For more information on this and to avoid redundancy, look at the holding_C documentation.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void holding_highE(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int e_id = a_soundID;
        m_notesBtn[16].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    m_notesBtn[16].setBackgroundResource(R.drawable.pressed_key_green);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[16] += m_secondsPassed + "-";
                    }

                    int new_id = m_soundPool.play(m_noteSounds[16], 1, 1, 0, 0, 1);
                    holding_highE(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[16].setBackgroundResource(R.drawable.button_border);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[16] += m_secondsPassed + ",";
                    }

                    m_soundPool.stop(e_id);
                }


                return false;
            }
        });

    }
    /*
        NAME

            holding_highF -->

        SYNOPSIS

            public void holding_highF(int a_soundID)

            int a_soundID ---> holds the sound identification integer of the currently playing high F sound.

        DESCRIPTION

            This method is called on the onCreate method. It listens for the user to click the high F button.

            If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
            For more information on this and to avoid redundancy, look at the holding_C documentation.

        RETURNS

            void

        AUTHOR

            Frank Ryan
        */
    public void holding_highF(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int f_id = a_soundID;
        m_notesBtn[17].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    m_notesBtn[17].setBackgroundResource(R.drawable.pressed_key_green);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[17] += m_secondsPassed + "-";
                    }
                    int new_id = m_soundPool.play(m_noteSounds[17], 1, 1, 0, 0, 1);
                    holding_highF(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[17].setBackgroundResource(R.drawable.button_border);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[17] += m_secondsPassed + ",";
                    }
                    m_soundPool.stop(f_id);
                }


                return false;
            }
        });

    }
    /*
    NAME

        holding_highFSharp -->

    SYNOPSIS

        public void holding_highFSharp(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing high FSharp sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the high FSharp button.

        If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
        For more information on this and to avoid redundancy, look at the holding_C documentation.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void holding_highFSharp(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int fSharp_id = a_soundID;
        m_notesBtn[18].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    m_notesBtn[18].setBackgroundResource(R.drawable.pressed_key_green);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[18] += m_secondsPassed + "-";
                    }
                    int new_id = m_soundPool.play(m_noteSounds[18], 1, 1, 0, 0, 1);
                    holding_highFSharp(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[18].setBackgroundResource(R.drawable.black_keys);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[18] += m_secondsPassed + ",";
                    }
                    m_soundPool.stop(fSharp_id);
                }


                return false;
            }
        });

    }
    /*
    NAME

        holding_highG -->

    SYNOPSIS

        public void holding_highG(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing high G sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the high G button.

        If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
        For more information on this and to avoid redundancy, look at the holding_C documentation.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void holding_highG(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int g_id = a_soundID;
        m_notesBtn[19].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    m_notesBtn[19].setBackgroundResource(R.drawable.pressed_key_green);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[19] += m_secondsPassed + "-";
                    }
                    int new_id = m_soundPool.play(m_noteSounds[19], 1, 1, 0, 0, 1);
                    holding_highG(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[19].setBackgroundResource(R.drawable.button_border);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[19] += m_secondsPassed + ",";
                    }
                    m_soundPool.stop(g_id);
                }


                return false;
            }
        });

    }
    /*
    NAME

        holding_highGSharp -->

    SYNOPSIS

        public void holding_highGSharp(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing high GSharp sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the high GSharp button.

        If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
        For more information on this and to avoid redundancy, look at the holding_C documentation.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void holding_highGSharp(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int gSharp_id = a_soundID;
        m_notesBtn[20].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    m_notesBtn[20].setBackgroundResource(R.drawable.pressed_key_green);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[20] += m_secondsPassed + "-";
                    }
                    int new_id = m_soundPool.play(m_noteSounds[20], 1, 1, 0, 0, 1);
                    holding_highGSharp(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[20].setBackgroundResource(R.drawable.black_keys);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[20] += m_secondsPassed + ",";
                    }
                    m_soundPool.stop(gSharp_id);
                }


                return false;
            }
        });

    }
    /*
    NAME

        holding_highA -->

    SYNOPSIS

        public void holding_highA(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing high A sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the high A button.

        If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
        For more information on this and to avoid redundancy, look at the holding_C documentation.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void holding_highA(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int a_id = a_soundID;
        m_notesBtn[21].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    m_notesBtn[21].setBackgroundResource(R.drawable.pressed_key_green);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[21] += m_secondsPassed + "-";
                    }
                    int new_id = m_soundPool.play(m_noteSounds[21], 1, 1, 0, 0, 1);
                    holding_highA(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[21].setBackgroundResource(R.drawable.button_border);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[21] += m_secondsPassed + ",";
                    }
                    m_soundPool.stop(a_id);
                }


                return false;
            }
        });

    }
    /*
    NAME

        holding_highASharp -->

    SYNOPSIS

        public void holding_highASharp(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing high ASharp sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the high ASharp button.

        If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
        For more information on this and to avoid redundancy, look at the holding_C documentation.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void holding_highASharp(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int aSharp_id = a_soundID;
        m_notesBtn[22].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    m_notesBtn[22].setBackgroundResource(R.drawable.pressed_key_green);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[22] += m_secondsPassed + "-";
                    }
                    int new_id = m_soundPool.play(m_noteSounds[22], 1, 1, 0, 0, 1);
                    holding_highASharp(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[22].setBackgroundResource(R.drawable.black_keys);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[22] += m_secondsPassed + ",";
                    }
                    m_soundPool.stop(aSharp_id);
                }


                return false;
            }
        });

    }
    /*
    NAME

        holding_highB-->

    SYNOPSIS

        public void holding_highB(int a_soundID)

        int a_soundID ---> holds the sound identification integer of the currently playing high B sound.

    DESCRIPTION

        This method is called on the onCreate method. It listens for the user to click the high B button.

        If the user is recording, this function also concatenates the time it was pressed and released to the appropriate string.
        For more information on this and to avoid redundancy, look at the holding_C documentation.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void holding_highB(int a_soundID)
    {
        //SIMILAR DOCUMENTATION TO holding_C
        final int b_id = a_soundID;
        m_notesBtn[23].setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    m_notesBtn[23].setBackgroundResource(R.drawable.pressed_key_green);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[23] += m_secondsPassed + "-";
                    }
                    int new_id = m_soundPool.play(m_noteSounds[23], 1, 1, 0, 0, 1);
                    holding_highB(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    m_notesBtn[23].setBackgroundResource(R.drawable.button_border);

                    if(m_isRecording == true)
                    {
                        m_noteTimes[23] += m_secondsPassed + ",";
                    }
                    m_soundPool.stop(b_id);
                }


                return false;
            }
        });

    }
}
