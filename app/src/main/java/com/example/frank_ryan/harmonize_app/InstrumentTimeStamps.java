package com.example.frank_ryan.harmonize_app;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class InstrumentTimeStamps extends AppCompatActivity implements Serializable {

    //m_instrumentNotes[] -> holds recorded instrument recording time stamps
    private HashMap<String, String> m_instrumentNotes;

    //holds sound pool object to load, play, and stop sounds
    private SoundPool m_soundPool;

    //m_instrumentSounds[] -> holds loaded instrument sounds from soundPool object
    private int m_instrumentSounds[] = new int[26];

    //m_soundID[] -> holds sound ID for each instrument sound from soundPool object
    private int m_soundID[] = new int[26];

    //m_secondsPassed -> used inside the timer task function, it is increased by +1 every 10 milliseconds when the task is called
    private int m_secondsPassed = 0;

    //m_octaves -> holds octave ID for instrument, 1-4 for piano, 1-2 for guitar, 1 for electric piano
    private int m_octaves, m_instrumentID, m_volumeSelection;

    //m_trackPlaying -> holds true if playInstrumentRecording() is called
    private boolean m_trackPlaying;
    //m_timer -> holds timer object when playInstrumentRecording() is called
    private Timer m_timer;

    //m_volume --> holds current volume of app
    private float m_volume;

    /*
    NAME

        InstrumentTimeStamps-->

    SYNOPSIS

        public InstrumentTimeStamps(HashMap<String, String> a_recordedNotes, int a_instrument_ID, int a_instrument_Octave, int a_volumeSelection)

        a_recordedNotes -> holds HashMap timestamps of recorded instrument
        a_instrument_ID -> holds instrument ID of one object, 0 for piano, 1 for guitar, 2 for electric piano
        a_instrument_Octave -> hold octave ID of once object, 1-4 for piano, 1-2 for guitar, 1 for electric piano
        a_volumeSelection --> holds selected volume of instrument, 1 3 or 6

    DESCRIPTION

        Overloaded constructor for InstrumentTimeStamps object. Sets m_instrumentNotes, m_instrumentID,  m_octaves. and m_volumeSelection.
        Used only when playInstrumentRecording() does not need to be called, as does not contain SoundPool functionalities.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public InstrumentTimeStamps(HashMap<String, String> a_recordedNotes, int a_instrument_ID, int a_instrument_Octave, int a_volumeSelection)
    {
        m_instrumentNotes = a_recordedNotes;

        m_instrumentID = a_instrument_ID;
        m_octaves = a_instrument_Octave;

        m_volumeSelection = a_volumeSelection;
    }

    /*
    NAME

        InstrumentTimeStamps-->

    SYNOPSIS

        public InstrumentTimeStamps(Context a_context, HashMap<String, String> a_recordedNotes, int a_instrument_ID, int a_instrument_Octave, int a_volumeSelection, float a_volume)

        a_context -> holds context/java class from which this constructor is called
        a_recordedNotes -> holds HashMap timestamps of recorded instrument
        a_instrument_ID -> holds instrument ID of one object, 0 for piano, 1 for guitar, 2 for electric piano
        a_instrument_Octave -> hold octave ID of once object, 1-4 for piano, 1-2 for guitar, 1 for electric piano
        a_volumeSelection -> holds volume for selected instrument, 1 3 or 6
        a_volume -> holds current volume of app

    DESCRIPTION

        Overloaded constructor for InstrumentTimeStamps object. Sets m_instrumentNotes, m_instrumentID, and m_octaves.
        Primarily used to call playInstrumentRecording().

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public InstrumentTimeStamps(Context a_context, HashMap<String, String> a_recordedNotes, int a_instrument_ID, int a_instrument_Octave, int a_volumeSelection, float a_volume)
    {
        //setting private members
        m_instrumentNotes = a_recordedNotes;
        m_instrumentID = a_instrument_ID;
        m_octaves = a_instrument_Octave;
        m_volumeSelection = a_volumeSelection;
        m_volume = a_volume;

        //creating soundpool object
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            m_soundPool = new SoundPool.Builder().setMaxStreams(8).build();
        }
        else
        {
            m_soundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
        }




        //The following will load the .mp3 files into each corresponding note, depending on the instrument and octaves
        //Instrument is piano
        if(a_instrument_ID == 0)
        {


            //Octaves 1-2
            if(a_instrument_Octave == 1)
            {


                m_instrumentSounds[0] = m_soundPool.load(a_context, R.raw.c1w, 1);
                m_instrumentSounds[1] = m_soundPool.load(a_context, R.raw.c_sharp1w, 1);
                m_instrumentSounds[2] = m_soundPool.load(a_context, R.raw.d1w, 1);
                m_instrumentSounds[3] = m_soundPool.load(a_context, R.raw.d_sharp1w, 1);
                m_instrumentSounds[4] = m_soundPool.load(a_context, R.raw.e1w, 1);
                m_instrumentSounds[5] = m_soundPool.load(a_context, R.raw.f1w, 1);
                m_instrumentSounds[6] = m_soundPool.load(a_context, R.raw.f_sharp1w, 1);
                m_instrumentSounds[7] = m_soundPool.load(a_context, R.raw.g1w, 1);
                m_instrumentSounds[8] = m_soundPool.load(a_context, R.raw.g_sharp1w, 1);
                m_instrumentSounds[9] = m_soundPool.load(a_context, R.raw.a1w, 1);
                m_instrumentSounds[10] = m_soundPool.load(a_context, R.raw.a_sharp1w, 1);
                m_instrumentSounds[11] = m_soundPool.load(a_context, R.raw.b1w, 1);


                m_instrumentSounds[12] = m_soundPool.load(a_context, R.raw.c2w, 1);
                m_instrumentSounds[13] = m_soundPool.load(a_context, R.raw.c_sharp2w, 1);
                m_instrumentSounds[14] = m_soundPool.load(a_context, R.raw.d2w, 1);
                m_instrumentSounds[15] = m_soundPool.load(a_context, R.raw.d_sharp2w, 1);
                m_instrumentSounds[16] = m_soundPool.load(a_context, R.raw.e2w, 1);
                m_instrumentSounds[17] = m_soundPool.load(a_context, R.raw.f2w, 1);
                m_instrumentSounds[18] = m_soundPool.load(a_context, R.raw.f_sharp2w, 1);
                m_instrumentSounds[19] = m_soundPool.load(a_context, R.raw.g2w, 1);
                m_instrumentSounds[20] = m_soundPool.load(a_context, R.raw.g_sharp2w, 1);
                m_instrumentSounds[21] = m_soundPool.load(a_context, R.raw.a2w, 1);
                m_instrumentSounds[22] = m_soundPool.load(a_context, R.raw.a_sharp2w, 1);
                m_instrumentSounds[23] = m_soundPool.load(a_context, R.raw.b2w, 1);
            }
            //Octaves 3-4
            else if(a_instrument_Octave == 2)
            {
                m_instrumentSounds[0] = m_soundPool.load(a_context, R.raw.c3w, 1);
                m_instrumentSounds[1] = m_soundPool.load(a_context, R.raw.c_sharp3w, 1);
                m_instrumentSounds[2] = m_soundPool.load(a_context, R.raw.d3w, 1);
                m_instrumentSounds[3] = m_soundPool.load(a_context, R.raw.d_sharp3w, 1);
                m_instrumentSounds[4] = m_soundPool.load(a_context, R.raw.e3w, 1);
                m_instrumentSounds[5] = m_soundPool.load(a_context, R.raw.f3w, 1);
                m_instrumentSounds[6] = m_soundPool.load(a_context, R.raw.f_sharp3w, 1);
                m_instrumentSounds[7] = m_soundPool.load(a_context, R.raw.g3w, 1);
                m_instrumentSounds[8] = m_soundPool.load(a_context, R.raw.g_sharp3w, 1);
                m_instrumentSounds[9] = m_soundPool.load(a_context, R.raw.a3w, 1);
                m_instrumentSounds[10] = m_soundPool.load(a_context, R.raw.a_sharp3w, 1);
                m_instrumentSounds[11] = m_soundPool.load(a_context, R.raw.b3w, 1);


                m_instrumentSounds[12] = m_soundPool.load(a_context, R.raw.c4w, 1);
                m_instrumentSounds[13] = m_soundPool.load(a_context, R.raw.c_sharp4w, 1);
                m_instrumentSounds[14] = m_soundPool.load(a_context, R.raw.d4w, 1);
                m_instrumentSounds[15] = m_soundPool.load(a_context, R.raw.d_sharp4w, 1);
                m_instrumentSounds[16] = m_soundPool.load(a_context, R.raw.e4w, 1);
                m_instrumentSounds[17] = m_soundPool.load(a_context, R.raw.f4w, 1);
                m_instrumentSounds[18] = m_soundPool.load(a_context, R.raw.f_sharp4w, 1);
                m_instrumentSounds[19] = m_soundPool.load(a_context, R.raw.g4w, 1);
                m_instrumentSounds[20] = m_soundPool.load(a_context, R.raw.g_sharp4w, 1);
                m_instrumentSounds[21] = m_soundPool.load(a_context, R.raw.a4w, 1);
                m_instrumentSounds[22] = m_soundPool.load(a_context, R.raw.a_sharp4w, 1);
                m_instrumentSounds[23] = m_soundPool.load(a_context, R.raw.b4w, 1);
            }
            //Octaves 5-6
            else if(a_instrument_Octave == 3)
            {
                m_instrumentSounds[0] = m_soundPool.load(a_context, R.raw.c5w, 1);
                m_instrumentSounds[1] = m_soundPool.load(a_context, R.raw.c_sharp5w, 1);
                m_instrumentSounds[2] = m_soundPool.load(a_context, R.raw.d5w, 1);
                m_instrumentSounds[3] = m_soundPool.load(a_context, R.raw.d_sharp5w, 1);
                m_instrumentSounds[4] = m_soundPool.load(a_context, R.raw.e5w, 1);
                m_instrumentSounds[5] = m_soundPool.load(a_context, R.raw.f5w, 1);
                m_instrumentSounds[6] = m_soundPool.load(a_context, R.raw.f_sharp5w, 1);
                m_instrumentSounds[7] = m_soundPool.load(a_context, R.raw.g5w, 1);
                m_instrumentSounds[8] = m_soundPool.load(a_context, R.raw.g_sharp5w, 1);
                m_instrumentSounds[9] = m_soundPool.load(a_context, R.raw.a5w, 1);
                m_instrumentSounds[10] = m_soundPool.load(a_context, R.raw.a_sharp5w, 1);
                m_instrumentSounds[11] = m_soundPool.load(a_context, R.raw.b5w, 1);


                m_instrumentSounds[12] = m_soundPool.load(a_context, R.raw.c6w, 1);
                m_instrumentSounds[13] = m_soundPool.load(a_context, R.raw.c_sharp6w, 1);
                m_instrumentSounds[14] = m_soundPool.load(a_context, R.raw.d6w, 1);
                m_instrumentSounds[15] = m_soundPool.load(a_context, R.raw.d_sharp6w, 1);
                m_instrumentSounds[16] = m_soundPool.load(a_context, R.raw.e6w, 1);
                m_instrumentSounds[17] = m_soundPool.load(a_context, R.raw.f6w, 1);
                m_instrumentSounds[18] = m_soundPool.load(a_context, R.raw.f_sharp6w, 1);
                m_instrumentSounds[19] = m_soundPool.load(a_context, R.raw.g6w, 1);
                m_instrumentSounds[20] = m_soundPool.load(a_context, R.raw.g_sharp6w, 1);
                m_instrumentSounds[21] = m_soundPool.load(a_context, R.raw.a6w, 1);
                m_instrumentSounds[22] = m_soundPool.load(a_context, R.raw.a_sharp6w, 1);
                m_instrumentSounds[23] = m_soundPool.load(a_context, R.raw.b6w, 1);
            }
            //Octaves 7-8
            else if(a_instrument_Octave == 4)
            {
                m_instrumentSounds[0] = m_soundPool.load(a_context, R.raw.c7w, 1);
                m_instrumentSounds[1] = m_soundPool.load(a_context, R.raw.c_sharp7w, 1);
                m_instrumentSounds[2] = m_soundPool.load(a_context, R.raw.d7w, 1);
                m_instrumentSounds[3] = m_soundPool.load(a_context, R.raw.d_sharp7w, 1);
                m_instrumentSounds[4] = m_soundPool.load(a_context, R.raw.e7w, 1);
                m_instrumentSounds[5] = m_soundPool.load(a_context, R.raw.f7w, 1);
                m_instrumentSounds[6] = m_soundPool.load(a_context, R.raw.f_sharp7w, 1);
                m_instrumentSounds[7] = m_soundPool.load(a_context, R.raw.g7w, 1);
                m_instrumentSounds[8] = m_soundPool.load(a_context, R.raw.g_sharp7w, 1);
                m_instrumentSounds[9] = m_soundPool.load(a_context, R.raw.a7w, 1);
                m_instrumentSounds[10] = m_soundPool.load(a_context, R.raw.a_sharp7w, 1);
                m_instrumentSounds[11] = m_soundPool.load(a_context, R.raw.b7w, 1);


                m_instrumentSounds[12] = m_soundPool.load(a_context, R.raw.c1w, 1);
                m_instrumentSounds[13] = m_soundPool.load(a_context, R.raw.c_sharp1w, 1);
                m_instrumentSounds[14] = m_soundPool.load(a_context, R.raw.d1w, 1);
                m_instrumentSounds[15] = m_soundPool.load(a_context, R.raw.d_sharp1w, 1);
                m_instrumentSounds[16] = m_soundPool.load(a_context, R.raw.e1w, 1);
                m_instrumentSounds[17] = m_soundPool.load(a_context, R.raw.f1w, 1);
                m_instrumentSounds[18] = m_soundPool.load(a_context, R.raw.f_sharp1w, 1);
                m_instrumentSounds[19] = m_soundPool.load(a_context, R.raw.g1w, 1);
                m_instrumentSounds[20] = m_soundPool.load(a_context, R.raw.g_sharp1w, 1);
                m_instrumentSounds[21] = m_soundPool.load(a_context, R.raw.a1w, 1);
                m_instrumentSounds[22] = m_soundPool.load(a_context, R.raw.a_sharp1w, 1);
                m_instrumentSounds[23] = m_soundPool.load(a_context, R.raw.b1w, 1);
            }



        }
        //Instrument is guitar
        else if (a_instrument_ID == 1)
        {
            //Octaves 1-2
            if(a_instrument_Octave == 1)
            {
                m_instrumentSounds[0] = m_soundPool.load(a_context, R.raw.c1w_guitar, 1);
                m_instrumentSounds[1] = m_soundPool.load(a_context, R.raw.c_sharp1w_guitar, 1);
                m_instrumentSounds[2] = m_soundPool.load(a_context, R.raw.d1w_guitar, 1);
                m_instrumentSounds[3] = m_soundPool.load(a_context, R.raw.d_sharp1w_guitar, 1);
                m_instrumentSounds[4] = m_soundPool.load(a_context, R.raw.e1w_guitar, 1);
                m_instrumentSounds[5] = m_soundPool.load(a_context, R.raw.f1w_guitar, 1);
                m_instrumentSounds[6] = m_soundPool.load(a_context, R.raw.f_sharp1w_guitar, 1);
                m_instrumentSounds[7] = m_soundPool.load(a_context, R.raw.g1w_guitar, 1);
                m_instrumentSounds[8] = m_soundPool.load(a_context, R.raw.g_sharp1w_guitar, 1);
                m_instrumentSounds[9] = m_soundPool.load(a_context, R.raw.a1w_guitar, 1);
                m_instrumentSounds[10] = m_soundPool.load(a_context, R.raw.a_sharp1w_guitar, 1);
                m_instrumentSounds[11] = m_soundPool.load(a_context, R.raw.b1w_guitar, 1);


                m_instrumentSounds[12] = m_soundPool.load(a_context, R.raw.c1w_guitar, 1);
                m_instrumentSounds[13] = m_soundPool.load(a_context, R.raw.c_sharp1w_guitar, 1);
                m_instrumentSounds[14] = m_soundPool.load(a_context, R.raw.d1w_guitar, 1);
                m_instrumentSounds[15] = m_soundPool.load(a_context, R.raw.d_sharp1w_guitar, 1);
                m_instrumentSounds[16] = m_soundPool.load(a_context, R.raw.e2w_guitar, 1);
                m_instrumentSounds[17] = m_soundPool.load(a_context, R.raw.f2w_guitar, 1);
                m_instrumentSounds[18] = m_soundPool.load(a_context, R.raw.f_sharp2w_guitar, 1);
                m_instrumentSounds[19] = m_soundPool.load(a_context, R.raw.g2w_guitar, 1);
                m_instrumentSounds[20] = m_soundPool.load(a_context, R.raw.g_sharp2w_guitar, 1);
                m_instrumentSounds[21] = m_soundPool.load(a_context, R.raw.a2w_guitar, 1);
                m_instrumentSounds[22] = m_soundPool.load(a_context, R.raw.a_sharp2w_guitar, 1);
                m_instrumentSounds[23] = m_soundPool.load(a_context, R.raw.b2w_guitar, 1);

            }
            //Octaves 2-3
            else if (a_instrument_Octave == 2)
            {
                m_instrumentSounds[0] = m_soundPool.load(a_context, R.raw.c1w_guitar, 1);
                m_instrumentSounds[1] = m_soundPool.load(a_context, R.raw.c_sharp1w_guitar, 1);
                m_instrumentSounds[2] = m_soundPool.load(a_context, R.raw.d1w_guitar, 1);
                m_instrumentSounds[3] = m_soundPool.load(a_context, R.raw.d_sharp1w_guitar, 1);
                m_instrumentSounds[4] = m_soundPool.load(a_context, R.raw.e2w_guitar, 1);
                m_instrumentSounds[5] = m_soundPool.load(a_context, R.raw.f2w_guitar, 1);
                m_instrumentSounds[6] = m_soundPool.load(a_context, R.raw.f_sharp2w_guitar, 1);
                m_instrumentSounds[7] = m_soundPool.load(a_context, R.raw.g2w_guitar, 1);
                m_instrumentSounds[8] = m_soundPool.load(a_context, R.raw.g_sharp2w_guitar, 1);
                m_instrumentSounds[9] = m_soundPool.load(a_context, R.raw.a2w_guitar, 1);
                m_instrumentSounds[10] = m_soundPool.load(a_context, R.raw.a_sharp2w_guitar, 1);
                m_instrumentSounds[11] = m_soundPool.load(a_context, R.raw.b2w_guitar, 1);


                m_instrumentSounds[12] = m_soundPool.load(a_context, R.raw.c2w_guitar, 1);
                m_instrumentSounds[13] = m_soundPool.load(a_context, R.raw.c_sharp2w_guitar, 1);
                m_instrumentSounds[14] = m_soundPool.load(a_context, R.raw.d2w_guitar, 1);
                m_instrumentSounds[15] = m_soundPool.load(a_context, R.raw.d_sharp2w_guitar, 1);
                m_instrumentSounds[16] = m_soundPool.load(a_context, R.raw.e3w_guitar, 1);
                m_instrumentSounds[17] = m_soundPool.load(a_context, R.raw.f3w_guitar, 1);
                m_instrumentSounds[18] = m_soundPool.load(a_context, R.raw.f_sharp3w_guitar, 1);
                m_instrumentSounds[19] = m_soundPool.load(a_context, R.raw.g3w_guitar, 1);
                m_instrumentSounds[20] = m_soundPool.load(a_context, R.raw.g_sharp3w_guitar, 1);
                m_instrumentSounds[21] = m_soundPool.load(a_context, R.raw.a3w_guitar, 1);
                m_instrumentSounds[22] = m_soundPool.load(a_context, R.raw.a_sharp3w_guitar, 1);
                m_instrumentSounds[23] = m_soundPool.load(a_context, R.raw.b3w_guitar, 1);
            }
        }
        //instrument is electric piano
        else if (a_instrument_ID == 2)
        {
            //octaves 4 and 5
            if(a_instrument_Octave == 1)
            {
                m_instrumentSounds[0] = m_soundPool.load(a_context, R.raw.c4w_electric, 1);
                m_instrumentSounds[1] = m_soundPool.load(a_context, R.raw.c_sharp4w_electric, 1);
                m_instrumentSounds[2] = m_soundPool.load(a_context, R.raw.d4w_electric, 1);
                m_instrumentSounds[3] = m_soundPool.load(a_context, R.raw.d_sharp4w_electric, 1);
                m_instrumentSounds[4] = m_soundPool.load(a_context, R.raw.e4w_electric, 1);
                m_instrumentSounds[5] = m_soundPool.load(a_context, R.raw.f4w_electric, 1);
                m_instrumentSounds[6] = m_soundPool.load(a_context, R.raw.f_sharp4w_electric, 1);
                m_instrumentSounds[7] = m_soundPool.load(a_context, R.raw.g4w_electric, 1);
                m_instrumentSounds[8] = m_soundPool.load(a_context, R.raw.g_sharp4w_electric, 1);
                m_instrumentSounds[9] = m_soundPool.load(a_context, R.raw.a4w_electric, 1);
                m_instrumentSounds[10] = m_soundPool.load(a_context, R.raw.a_sharp4w_electric, 1);
                m_instrumentSounds[11] = m_soundPool.load(a_context, R.raw.b4w_electric, 1);


                m_instrumentSounds[12] = m_soundPool.load(a_context, R.raw.c5w_electric, 1);
                m_instrumentSounds[13] = m_soundPool.load(a_context, R.raw.c_sharp5w_electric, 1);
                m_instrumentSounds[14] = m_soundPool.load(a_context, R.raw.d5w_electric, 1);
                m_instrumentSounds[15] = m_soundPool.load(a_context, R.raw.d_sharp5w_electric, 1);
                m_instrumentSounds[16] = m_soundPool.load(a_context, R.raw.e5w_electric, 1);
                m_instrumentSounds[17] = m_soundPool.load(a_context, R.raw.f5w_electric, 1);
                m_instrumentSounds[18] = m_soundPool.load(a_context, R.raw.f_sharp5w_electric, 1);
                m_instrumentSounds[19] = m_soundPool.load(a_context, R.raw.g5w_electric, 1);
                m_instrumentSounds[20] = m_soundPool.load(a_context, R.raw.g_sharp5w_electric, 1);
                m_instrumentSounds[21] = m_soundPool.load(a_context, R.raw.a5w_electric, 1);
                m_instrumentSounds[22] = m_soundPool.load(a_context, R.raw.a_sharp5w_electric, 1);
                m_instrumentSounds[23] = m_soundPool.load(a_context, R.raw.b5w_electric, 1);
            }
        }

        //Initializes sounds by setting them to a sound ID, m_soundID[]
        m_soundID[0] = m_soundPool.play(m_instrumentSounds[0], m_volume, m_volume, 0, 0, 1);
        m_soundID[1] = m_soundPool.play(m_instrumentSounds[1], m_volume, m_volume, 0, 0, 1);
        m_soundID[2] = m_soundPool.play(m_instrumentSounds[2], m_volume, m_volume, 0, 0, 1);
        m_soundID[3] = m_soundPool.play(m_instrumentSounds[3], m_volume, m_volume, 0, 0, 1);
        m_soundID[4] = m_soundPool.play(m_instrumentSounds[4], m_volume, m_volume, 0, 0, 1);
        m_soundID[5] = m_soundPool.play(m_instrumentSounds[5], m_volume, m_volume, 0, 0, 1);
        m_soundID[6] = m_soundPool.play(m_instrumentSounds[6], m_volume, m_volume, 0, 0, 1);
        m_soundID[7] = m_soundPool.play(m_instrumentSounds[7], m_volume, m_volume, 0, 0, 1);
        m_soundID[8] = m_soundPool.play(m_instrumentSounds[8], m_volume, m_volume, 0, 0, 1);
        m_soundID[9] = m_soundPool.play(m_instrumentSounds[9], m_volume, m_volume, 0, 0, 1);
        m_soundID[10] = m_soundPool.play(m_instrumentSounds[10], m_volume, m_volume, 0, 0, 1);
        m_soundID[11] = m_soundPool.play(m_instrumentSounds[11], m_volume, m_volume, 0, 0, 1);


        m_soundID[12] = m_soundPool.play(m_instrumentSounds[12], m_volume, m_volume, 0, 0, 1);
        m_soundID[13] = m_soundPool.play(m_instrumentSounds[13], m_volume, m_volume, 0, 0, 1);
        m_soundID[14] = m_soundPool.play(m_instrumentSounds[14], m_volume, m_volume, 0, 0, 1);
        m_soundID[15] = m_soundPool.play(m_instrumentSounds[15], m_volume, m_volume, 0, 0, 1);
        m_soundID[16] = m_soundPool.play(m_instrumentSounds[16], m_volume, m_volume, 0, 0, 1);
        m_soundID[17] = m_soundPool.play(m_instrumentSounds[17], m_volume, m_volume, 0, 0, 1);
        m_soundID[18] = m_soundPool.play(m_instrumentSounds[18], m_volume, m_volume, 0, 0, 1);
        m_soundID[19] = m_soundPool.play(m_instrumentSounds[19], m_volume, m_volume, 0, 0, 1);
        m_soundID[20] = m_soundPool.play(m_instrumentSounds[20], m_volume, m_volume, 0, 0, 1);
        m_soundID[21] = m_soundPool.play(m_instrumentSounds[21], m_volume, m_volume, 0, 0, 1);
        m_soundID[22] = m_soundPool.play(m_instrumentSounds[22], m_volume, m_volume, 0, 0, 1);
        m_soundID[23] = m_soundPool.play(m_instrumentSounds[23], m_volume, m_volume, 0, 0, 1);


    }

    //Setters and getters
    public HashMap<String, String> getPianoNotes()
    {
        return m_instrumentNotes;
    }

    public void setM_instrumentNotes(HashMap<String, String> a_notes) {  m_instrumentNotes = a_notes; }

    public int getM_instrumentID()
    {
        return m_instrumentID;
    }

    public int getM_octaves()
    {
        return m_octaves;
    }

    public int getM_volumeSelection() { return m_volumeSelection; }

    public float getM_volume() { return m_volume; }

    public void setM_volumeSelection(int a_volume) { m_volumeSelection = a_volume; }
    /*
    NAME

        play_c-->

    SYNOPSIS

        public void play_c()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. Used to play and stop the low C sound of the instrument using the timestamps from the m_instrumentNotes HashMap.
        Calls mainNotePlay() if there are timestamps for the note.
        Else, the key's value will be ":".

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_c()
    {
        if(m_instrumentNotes.get("lowC").equals(":"))
        {

        }
        else {
            mainNotePlay("lowC", m_soundID[0], m_instrumentSounds[0]);

        }

    }
    /*
    NAME

        play_cSharp-->

    SYNOPSIS

        public void play_cSharp()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_cSharp()
    {
        if(m_instrumentNotes.get("lowCSharp").equals(":"))
        {

        }
        else {
            mainNotePlay("lowCSharp", m_soundID[1], m_instrumentSounds[1]);

        }

    }
    /*
    NAME

        play_d-->

    SYNOPSIS

        public void play_d()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_d()
    {
        if(m_instrumentNotes.get("lowD").equals(":"))
        {

        }
        else {
            mainNotePlay("lowD", m_soundID[2], m_instrumentSounds[2]);
        }
    }
    /*
    NAME

        play_dSharp-->

    SYNOPSIS

        public void play_dSharp()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_dSharp()
    {
        if(m_instrumentNotes.get("lowDSharp").equals(":"))
        {

        }
        else {
            mainNotePlay("lowDSharp", m_soundID[3], m_instrumentSounds[3]);

        }

    }
    /*
    NAME

        play_e-->

    SYNOPSIS

        public void play_e()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_e()
    {
        if(m_instrumentNotes.get("lowE").equals(":"))
        {

        }
        else {

            mainNotePlay("lowE", m_soundID[4], m_instrumentSounds[4]);
        }
    }
    /*
    NAME

        play_f-->

    SYNOPSIS

        public void play_f()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_f()
    {
        if(m_instrumentNotes.get("lowF").equals(":"))
        {

        }
        else {

            mainNotePlay("lowF", m_soundID[5], m_instrumentSounds[5]);
        }
    }
    /*
    NAME

        play_fSharp-->

    SYNOPSIS

        public void play_fSharp()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_fSharp()
    {
        if(m_instrumentNotes.get("lowFSharp").equals(":"))
        {

        }
        else {
            mainNotePlay("lowFSharp", m_soundID[6], m_instrumentSounds[6]);

        }

    }
    /*
    NAME

        play_g-->

    SYNOPSIS

        public void play_g()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_g()
    {
        if(m_instrumentNotes.get("lowG").equals(":"))
        {

        }
        else {
            mainNotePlay("lowG", m_soundID[7], m_instrumentSounds[7]);

        }

    }
    /*
    NAME

        play_gSharp-->

    SYNOPSIS

        public void play_gSharp()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_gSharp()
    {
        if(m_instrumentNotes.get("lowGSharp").equals(":"))
        {

        }
        else {
            mainNotePlay("lowGSharp", m_soundID[8], m_instrumentSounds[8]);

        }

    }
    /*
    NAME

        play_a-->

    SYNOPSIS

        public void play_a()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_a()
    {
        if(m_instrumentNotes.get("lowA").equals(":"))
        {

        }
        else {
            mainNotePlay("lowA", m_soundID[9], m_instrumentSounds[9]);

        }

    }
    /*
    NAME

        play_aSharp-->

    SYNOPSIS

        public void play_aSharp()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_aSharp()
    {
        if(m_instrumentNotes.get("lowASharp").equals(":"))
        {

        }
        else {
            mainNotePlay("lowASharp", m_soundID[10], m_instrumentSounds[10]);

        }

    }
    /*
    NAME

        play_b-->

    SYNOPSIS

        public void play_b()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_b()
    {
        if(m_instrumentNotes.get("lowB").equals(":"))
        {

        }
        else {
            mainNotePlay("lowB", m_soundID[11], m_instrumentSounds[11]);

        }

    }
    /*
    NAME

        play_highC-->

    SYNOPSIS

        public void play_highC()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_highC()
    {
        if(m_instrumentNotes.get("highC").equals(":"))
        {

        }
        else {
            mainNotePlay("highC", m_soundID[12], m_instrumentSounds[12]);

        }

    }
    /*
    NAME

        play_highCSharp-->

    SYNOPSIS

        public void play_highCSharp()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_highCSharp()
    {
        if(m_instrumentNotes.get("highCSharp").equals(":"))
        {

        }
        else {
            mainNotePlay("highCSharp", m_soundID[13], m_instrumentSounds[13]);

        }

    }
    /*
    NAME

        play_highD-->

    SYNOPSIS

        public void play_highD()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_highD()
    {
        if(m_instrumentNotes.get("highD").equals(":"))
        {

        }
        else {
            mainNotePlay("highD", m_soundID[14], m_instrumentSounds[14]);
        }
    }
    /*
    NAME

        play_highDSharp-->

    SYNOPSIS

        public void play_highDSharp()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_highDSharp()
    {
        if(m_instrumentNotes.get("highDSharp").equals(":"))
        {

        }
        else {
            mainNotePlay("highDSharp", m_soundID[15], m_instrumentSounds[15]);

        }

    }
    /*
    NAME

        play_highE-->

    SYNOPSIS

        public void play_highE()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_highE()
    {
        if(m_instrumentNotes.get("highE").equals(":"))
        {

        }
        else {

            mainNotePlay("highE", m_soundID[16], m_instrumentSounds[16]);
        }
    }
    /*
    NAME

        play_highF-->

    SYNOPSIS

        public void play_highF()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_highF()
    {
        if(m_instrumentNotes.get("highF").equals(":"))
        {

        }
        else {

            mainNotePlay("highF", m_soundID[17], m_instrumentSounds[17]);
        }
    }
    /*
    NAME

        play_highFSharp-->

    SYNOPSIS

        public void play_highFSharp()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_highFSharp()
    {
        if(m_instrumentNotes.get("highFSharp").equals(":"))
        {

        }
        else {
            mainNotePlay("highFSharp", m_soundID[18], m_instrumentSounds[18]);

        }

    }
    /*
    NAME

        play_highG-->

    SYNOPSIS

        public void play_highG()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_highG()
    {
        if(m_instrumentNotes.get("highG").equals(":"))
        {

        }
        else {
            mainNotePlay("highG", m_soundID[19], m_instrumentSounds[19]);

        }

    }
    /*
    NAME

        play_highGSharp-->

    SYNOPSIS

        public void play_highGSharp()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_highGSharp()
    {
        if(m_instrumentNotes.get("highGSharp").equals(":"))
        {

        }
        else {
            mainNotePlay("highGSharp", m_soundID[20], m_instrumentSounds[20]);

        }

    }
    /*
    NAME

        play_highA-->

    SYNOPSIS

        public void play_highA()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_highA()
    {
        if(m_instrumentNotes.get("highA").equals(":"))
        {

        }
        else {
            mainNotePlay("highA", m_soundID[21], m_instrumentSounds[21]);

        }

    }
    /*
    NAME

        play_highASharp-->

    SYNOPSIS

        public void play_highASharp()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_highASharp()
    {
        if(m_instrumentNotes.get("highASharp").equals(":"))
        {

        }
        else {
            mainNotePlay("highASharp", m_soundID[22], m_instrumentSounds[22]);

        }

    }
    /*
    NAME

        play_highB-->

    SYNOPSIS

        public void play_highB()

    DESCRIPTION

        Called continously inside playInstrumentRecording() function. See play_c for more info.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void play_highB()
    {
        if(m_instrumentNotes.get("highB").equals(":"))
        {

        }
        else {
            mainNotePlay("highB", m_soundID[23], m_instrumentSounds[23]);

        }

    }
    /*
    NAME

        mainNotePlay-->

    SYNOPSIS

        public void mainNotePlay(String a_hashNote, int a_noteID, int a_noteSound)

        a_hashNote -> holds key of HashMap for each note
        a_noteID -> holds sound ID of each note from SoundPool
        a_noteSound -> holds sound of note from SoundPool

    DESCRIPTION

        Called from all notes during the playInstrumentRecording() function. Takes the value of the hashNote key and parses it using .split of the character '-'.
        Using that split, it checks to see if the left string of the split is empty (the '=' was the first character of the hashNote value).
            If so, it then splits the right string of the previous split using the delimeter ','.
            Then, if the left string of the delimeter ',' split equals m_secondsPassed, then that note was released at this time during this instrument recording, and that note sound is stopped through SoundPool.

        If the left string of the first delimeter split was not empty, and if the left splitted string equals m_secondsPassed, then this note was pressed at this time, and that note sound is played through SoundPool.
        With this, "-" is also added as the first character to the value of the key.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void mainNotePlay(String a_hashNote, int a_noteID, int a_noteSound)
    {
        //retrieves the value of hashNote key and splits it once using the delimeter "-"
        final int soundId = a_noteID;
        String string = m_instrumentNotes.get(a_hashNote);
        String[] parts = string.split("-", 2);

        //contains the left side of the split
        String part1 = parts[0];
        //contains the right side of the split
        String part2 = parts[1];

        //the "-" was the first character of the hashNote value
        if (part1.isEmpty()) {

            //splits the right side of the previous delimeter split only once, using ","
            String[] new_parts = part2.split(",", 2);
            //contains left side of split
            String number = new_parts[0];
            //contains right side of split (rest of hashNote value)
            String rest = new_parts[1];


            //If this left side split equals m_secondsPassed, the note was previously pressed, and is being released at this time.
            if (Integer.parseInt(number) == m_secondsPassed) {


                //Depending on which key, stop the corresponding sound that is playing

                    m_soundPool.stop(soundId);



                m_instrumentNotes.put(a_hashNote, rest);
            }
        }
        //A number was the first character of the HashMap String value, and it also equals m_secondsPassed, meaning this key was pressed at this time during the user's recording
        else if (Integer.parseInt(part1) == m_secondsPassed) {


            //Depending on which key, play the sound of that corresponding note, and add a "-" to the beginning of the hashNote value
            //volume is m_volume / m_volumeSelection, where m_volume is the current volume of the app, and m_volumeSelection is the user selected volume
            //As m_volumeSelection increases, the volume of the instrument recording decreases
            if(a_noteID == m_soundID[0])
            {
                m_soundID[0] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);

            }
            else if(a_noteID == m_soundID[1])
            {
                m_soundID[1] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);
            }
            else if(a_noteID == m_soundID[2])
            {
                m_soundID[2] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);
            }
            else if(a_noteID == m_soundID[3])
            {
                m_soundID[3] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);
            }
            else if(a_noteID == m_soundID[4])
            {
                m_soundID[4] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);
            }
            else if(a_noteID == m_soundID[5])
            {
                m_soundID[5] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);
            }
            else if(a_noteID == m_soundID[6])
            {
                m_soundID[6] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);
            }
            else if(a_noteID == m_soundID[7])
            {
                m_soundID[7] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);
            }
            else if(a_noteID == m_soundID[8])
            {
                m_soundID[8] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);
            }
            else if(a_noteID == m_soundID[9])
            {
                m_soundID[9] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);
            }
            else if(a_noteID == m_soundID[10])
            {
                m_soundID[10] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);
            }
            else if(a_noteID == m_soundID[11])
            {
                m_soundID[11] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);
            }



            else if(a_noteID == m_soundID[12])
            {
                m_soundID[12] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);
            }
            else if(a_noteID == m_soundID[13])
            {
                m_soundID[14] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);
            }
            else if(a_noteID == m_soundID[14])
            {
                m_soundID[15] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);
            }
            else if(a_noteID == m_soundID[15])
            {
                m_soundID[17] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);
            }
            else if(a_noteID == m_soundID[16])
            {
                m_soundID[18] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);

            }
            else if(a_noteID == m_soundID[17]) {
                m_soundID[19] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);
            }
            else if(a_noteID == m_soundID[18])
            {
                m_soundID[20] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);
            }
            else if(a_noteID == m_soundID[19])
            {
                m_soundID[21] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);
            }
            else if(a_noteID == m_soundID[20])
            {
                m_soundID[22] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);
            }
            else if(a_noteID == m_soundID[21])
            {
                m_soundID[21] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);
            }
            else if(a_noteID == m_soundID[22])
            {
                m_soundID[22] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);
            }
            else if(a_noteID == m_soundID[23])
            {
                m_soundID[23] = m_soundPool.play(a_noteSound, getM_volume()/getM_volumeSelection(), getM_volume()/getM_volumeSelection(), 0, 0, 1);
            }
            m_instrumentNotes.put(a_hashNote, "-" + part2);

        }


    }
    /*
    NAME

        playInstrumentRecording-->

    SYNOPSIS

        public void playInstrumentRecording()

    DESCRIPTION

        Method used to play instrument sounds based on the recordings of InstrumentTimeStamps objects.
        Inside this function, a new timer is created. The TimerTask will run every 10 milliseconds.
        Through their functions, each note is called every 10 milliseconds.
        Inside these functions, their corresponding HashMap key-value pairs are checked to see if m_secondsPassed equals the first number of their value String.
        If so, the corresponding instrument note sound is played if it was pressed, and stopped if it was released.
        For example, for the HashMap key "lowC", the value may be:
                "22-26,33-78,"
            When m_secondsPassed equals 22, the play_c() function will play the low C sound of that instrument.
            Along with this, "22" will be removed from the value. The function will continue getting called, and when m_secondsPassed equals 26, the play_c() function will stop that sound that was played.
            The same will happen to the "33-78" times that were recorded.
        Once all note values equal ":", the timer is cancelled and the function is done.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void playInstrumentRecording()
    {

        m_timer = new Timer();
        final TimerTask task = new TimerTask()
        {

            public void run()
            {
                //m_secondsPassed is incremented every 10 milliseconds
                m_secondsPassed++;


                //All note functions are called to play and stop the sounds of recorded time stamps inside the HashMap m_instrumentNotes

                playAllNotes();

                //Once all values of HashMap notes are empty, cancel the task
                if(Collections.frequency(m_instrumentNotes.values(), ":") == 24)
                {
                    m_soundPool.release();

                    m_timer.cancel();
                    m_timer.purge();


                    m_trackPlaying = false;
                }

            }
        };
        m_trackPlaying = true;
       m_timer.scheduleAtFixedRate(task, 10, 10);



    }


    public void playInstrumentRecording(Timer a_timer)
    {

        m_timer = a_timer;
        final TimerTask task = new TimerTask()
        {

            public void run()
            {
                //m_secondsPassed is incremented every 10 milliseconds
                m_secondsPassed++;


                //All note functions are called to play and stop the sounds of recorded time stamps inside the HashMap m_instrumentNotes

                playAllNotes();



            }
        };
        m_trackPlaying = true;
        m_timer.scheduleAtFixedRate(task, 10, 10);



    }
    /*
    NAME

        playAllNotes-->

    SYNOPSIS

        public void playAllNotes()

    DESCRIPTION

        Calls all notes of recorded instrument keys if there are recorded time stamps left in the values.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void playAllNotes()
    {
        //If the value is not a :, then a timestamp still exists in the value
        if(m_instrumentNotes.get("lowC") != ":")
        {
            play_c();
        }
        if(m_instrumentNotes.get("lowCSharp") != ":")
        {
            play_cSharp();
        }
        if(m_instrumentNotes.get("lowD") != ":")
        {
            play_d();
        }
        if(m_instrumentNotes.get("lowDSharp") != ":")
        {
            play_dSharp();
        }
        if(m_instrumentNotes.get("lowE") != ":")
        {
            play_e();
        }
        if(m_instrumentNotes.get("lowF") != ":")
        {
            play_f();
        }
        if(m_instrumentNotes.get("lowFSharp") != ":")
        {
            play_fSharp();
        }
        if(m_instrumentNotes.get("lowG") != ":")
        {
            play_g();
        }
        if(m_instrumentNotes.get("lowGSharp") != ":")
        {
            play_gSharp();
        }
        if(m_instrumentNotes.get("lowA") != ":")
        {
            play_a();
        }
        if(m_instrumentNotes.get("lowASharp") != ":")
        {
            play_aSharp();
        }
        if(m_instrumentNotes.get("lowB") != ":")
        {
            play_b();
        }


        if(m_instrumentNotes.get("highC") != ":")
        {
            play_highC();
        }
        if(m_instrumentNotes.get("highCSharp") != ":")
        {
            play_highCSharp();
        }
        if(m_instrumentNotes.get("highD") != ":")
        {
            play_highD();
        }
        if(m_instrumentNotes.get("highDSharp") != ":")
        {
            play_highDSharp();
        }
        if(m_instrumentNotes.get("highE") != ":")
        {
            play_highE();
        }
        if(m_instrumentNotes.get("highF") != ":")
        {
            play_highF();
        }
        if(m_instrumentNotes.get("highFSharp") != ":")
        {
            play_highFSharp();
        }
        if(m_instrumentNotes.get("highG") != ":")
        {
            play_highG();
        }
        if(m_instrumentNotes.get("highGSharp") != ":")
        {
            play_highGSharp();
        }
        if(m_instrumentNotes.get("highA") != ":")
        {
            play_highA();
        }
        if(m_instrumentNotes.get("highASharp") != ":")
        {
            play_highASharp();
        }
        if(m_instrumentNotes.get("highB") != ":")
        {
            play_highB();
        }

    }


    /*
    NAME

        stopPlaying-->

    SYNOPSIS

        public void stopPlaying()

    DESCRIPTION

        Called to stop playing an instrument recording by cancelling the timer object.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void stopPlaying()
    {

        m_timer.cancel();
        m_timer.purge();
        m_trackPlaying = false;
    }


    public boolean isPlaying()
    {

        return m_trackPlaying;
    }




}
