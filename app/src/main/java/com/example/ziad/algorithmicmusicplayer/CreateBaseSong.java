package com.example.ziad.algorithmicmusicplayer;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class CreateBaseSong extends AppCompatActivity {

    private Button recordButton, addTrackbtn, noteC, noteCSharp, noteD, noteDSharp, noteE, noteF, noteFSharp, noteG, noteGSharp, noteA, noteASharp, noteB,
            note_highC, note_highD, note_highE, note_highF, note_highG, note_highA, note_highB,
            note_highCSharp, note_highDSharp, note_highFSharp, note_highGSharp, note_highASharp;

    private SoundPool soundPool;
   private int newc_sound, c_sound, cSharp_sound, d_sound, dSharp_sound, e_sound, f_sound, fSharp_sound, g_sound, gSharp_sound, a_sound, aSharp_sound, b_sound,
           highC_sound, highCSharp_sound, highD_sound, highDSharp_sound, highE_sound, highF_sound, highFSharp_sound, highG_sound,
           highGSharp_sound, highA_Sound, highASharp_sound, highB_sound;

    private String FILE;
    private MediaRecorder record;
    private MediaPlayer player;

    private HashMap<String, String> recordedPianoNotes;

    private boolean isRecording;

    private int secondsPassed = 0;

    private String c_times, cSharp_times, d_times, dSharp_times, e_times, f_times, fSharp_times, g_times, gSharp_times, a_times, aSharp_times, b_times,
                highC_times, highCSharp_times, highD_times, highDSharp_times, highE_times, highF_times, highFSharp_times, highG_times, highGSharp_times,
                highA_times, highASharp_times, highB_times;

    private PianoTimeStamps pianoTimeStamps;

    private int numberOfInstruments;

    final private Timer timer = new Timer();
    final private TimerTask task = new TimerTask()
    {

        public void run()
        {
            secondsPassed++;
            //System.out.println("Seconds Passed: " + secondsPassed);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.build_song);

        FILE = Environment.getExternalStorageDirectory() + "/tempRecord.3gpp";

        recordButton = (Button) findViewById(R.id.buttonRecord);
        addTrackbtn = (Button) findViewById(R.id.buttonAddToMain);



        noteC = (Button) findViewById(R.id.buttonC);
        noteCSharp = (Button) findViewById(R.id.buttonCSharp);
        noteD = (Button) findViewById(R.id.buttonD);
        noteDSharp = (Button) findViewById(R.id.buttonDSharp);
        noteE = (Button) findViewById(R.id.buttonE);
        noteF = (Button) findViewById(R.id.buttonF);
        noteFSharp = (Button) findViewById(R.id.buttonFSharp);
        noteG = (Button) findViewById(R.id.buttonG);
        noteGSharp = (Button) findViewById(R.id.buttonGSharp);
        noteA = (Button) findViewById(R.id.buttonA);
        noteASharp = (Button) findViewById(R.id.buttonASharp);
        noteB = (Button) findViewById(R.id.buttonB);


        note_highC = (Button) findViewById(R.id.button_highC);
        note_highCSharp = (Button) findViewById(R.id.button_highCSharp);
        note_highD = (Button) findViewById(R.id.button_highD);
        note_highDSharp = (Button) findViewById(R.id.button_highDSharp);
        note_highE = (Button) findViewById(R.id.button_highE);
        note_highF = (Button) findViewById(R.id.button_highF);
        note_highFSharp = (Button) findViewById(R.id.button_highFSharp);
        note_highG = (Button) findViewById(R.id.button_highG);
        note_highGSharp = (Button) findViewById(R.id.button_highGSharp);
        note_highA = (Button) findViewById(R.id.button_highA);
        note_highASharp = (Button) findViewById(R.id.button_highASharp);
        note_highB = (Button) findViewById(R.id.button_highB);

        noteC.setBackgroundColor(Color.WHITE);  // From android.graphics.Color
        noteCSharp.setBackgroundColor(Color.BLACK);  // From android.graphics.Color
        noteD.setBackgroundColor(Color.WHITE);  // From android.graphics.Color
        noteDSharp.setBackgroundColor(Color.BLACK);  // From android.graphics.Color
        noteE.setBackgroundColor(Color.WHITE);  // From android.graphics.Color
        noteF.setBackgroundColor(Color.WHITE);  // From android.graphics.Color
        noteFSharp.setBackgroundColor(Color.BLACK);  // From android.graphics.Color
        noteG.setBackgroundColor(Color.WHITE);  // From android.graphics.Color
        noteGSharp.setBackgroundColor(Color.BLACK);  // From android.graphics.Color
        noteA.setBackgroundColor(Color.WHITE);  // From android.graphics.Color
        noteASharp.setBackgroundColor(Color.BLACK);  // From android.graphics.Color
        noteB.setBackgroundColor(Color.WHITE);  // From android.graphics.Color

        note_highC.setBackgroundColor(Color.WHITE);  // From android.graphics.Color
        note_highCSharp.setBackgroundColor(Color.BLACK);  // From android.graphics.Color
        note_highD.setBackgroundColor(Color.WHITE);  // From android.graphics.Color
        note_highDSharp.setBackgroundColor(Color.BLACK);  // From android.graphics.Color
        note_highE.setBackgroundColor(Color.WHITE);  // From android.graphics.Color
        note_highF.setBackgroundColor(Color.WHITE);  // From android.graphics.Color
        note_highFSharp.setBackgroundColor(Color.BLACK);  // From android.graphics.Color
        note_highG.setBackgroundColor(Color.WHITE);  // From android.graphics.Color
        note_highGSharp.setBackgroundColor(Color.BLACK);  // From android.graphics.Color
        note_highA.setBackgroundColor(Color.WHITE);  // From android.graphics.Color
        note_highASharp.setBackgroundColor(Color.BLACK);  // From android.graphics.Color
        note_highB.setBackgroundColor(Color.WHITE);  // From android.graphics.Color




        Intent intent = getIntent();
        numberOfInstruments = intent.getIntExtra("instrumentsNumber", 0);
        final PianoTimeStamps instrument1 = (PianoTimeStamps) intent.getSerializableExtra("Instrument1");
        final PianoTimeStamps instrument2 = (PianoTimeStamps) intent.getSerializableExtra("Instrument2");
        final PianoTimeStamps instrument3 = (PianoTimeStamps) intent.getSerializableExtra("Instrument3");
        final PianoTimeStamps instrument4 = (PianoTimeStamps) intent.getSerializableExtra("Instrument4");
        final PianoTimeStamps instrument5 = (PianoTimeStamps) intent.getSerializableExtra("Instrument5");

        isRecording = false;


        c_times = cSharp_times = d_times = dSharp_times = e_times = f_times = fSharp_times = g_times = gSharp_times = a_times = aSharp_times = b_times =
                highC_times = highCSharp_times = highD_times = highDSharp_times = highE_times = highF_times = highFSharp_times = highG_times = highGSharp_times =
                highA_times = highASharp_times = highB_times= "";

        System.out.println(c_times);


        addTrackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent track_intent = new Intent(CreateBaseSong.this, MainTrackHome.class);

                pianoTimeStamps = new PianoTimeStamps(recordedPianoNotes);

                if(numberOfInstruments == 0)
                {
                    track_intent.putExtra("Instrument1", pianoTimeStamps);
                    track_intent.putExtra("numInstruments", 1);
                    track_intent.putExtra("LoadedSong", true);

                }
                else if (numberOfInstruments == 1)
                {
                    track_intent.putExtra("Instrument1", instrument1);
                    track_intent.putExtra("Instrument2", pianoTimeStamps);
                    track_intent.putExtra("numInstruments", 2);
                    track_intent.putExtra("LoadedSong", true);

                }
                else if (numberOfInstruments == 2)
                {
                    track_intent.putExtra("Instrument1", instrument1);
                    track_intent.putExtra("Instrument2", instrument2);
                    track_intent.putExtra("Instrument3", pianoTimeStamps);
                    track_intent.putExtra("numInstruments", 3);
                    track_intent.putExtra("LoadedSong", true);

                }
                else if (numberOfInstruments == 3)
                {
                    track_intent.putExtra("Instrument1", instrument1);
                    track_intent.putExtra("Instrument2", instrument2);
                    track_intent.putExtra("Instrument3", instrument3);
                    track_intent.putExtra("Instrument4", pianoTimeStamps);
                    track_intent.putExtra("numInstruments", 4);
                    track_intent.putExtra("LoadedSong", true);

                }
                else if (numberOfInstruments == 4)
                {
                    track_intent.putExtra("Instrument1", instrument1);
                    track_intent.putExtra("Instrument2", instrument2);
                    track_intent.putExtra("Instrument3", instrument3);
                    track_intent.putExtra("Instrument4", instrument4);
                    track_intent.putExtra("Instrument5", pianoTimeStamps);
                    track_intent.putExtra("numInstruments", 5);
                    track_intent.putExtra("LoadedSong", true);

                }
                else if (numberOfInstruments == 5)
                {
                    track_intent.putExtra("Instrument1", instrument1);
                    track_intent.putExtra("Instrument2", instrument2);
                    track_intent.putExtra("Instrument3", instrument3);
                    track_intent.putExtra("Instrument4", instrument4);
                    track_intent.putExtra("Instrument5", instrument5);
                    track_intent.putExtra("Instrument6", pianoTimeStamps);
                    track_intent.putExtra("numInstruments", 6);
                    track_intent.putExtra("LoadedSong", true);

                }

                soundPool.release();

                startActivity(track_intent);
            }

        });



        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(recordButton.getText().toString().equals("Record"))
                {
                    isRecording = true;

                    timer.scheduleAtFixedRate(task, 10, 10);
                    recordButton.setText("Stop");

                }
                else if(recordButton.getText().toString().equals("Stop"))
                {


                    recordedPianoNotes = new HashMap<String, String>();

                    c_times += ":";
                    cSharp_times += ":";
                    d_times += ":";
                    dSharp_times += ":";
                    e_times += ":";
                    f_times += ":";
                    fSharp_times += ":";
                    g_times += ":";
                    gSharp_times += ":";
                    a_times += ":";
                    aSharp_times += ":";
                    b_times += ":";


                    highC_times += ":";
                    highCSharp_times += ":";
                    highD_times += ":";
                    highDSharp_times += ":";
                    highE_times += ":";
                    highF_times += ":";
                    highFSharp_times += ":";
                    highG_times += ":";
                    highGSharp_times += ":";
                    highA_times += ":";
                    highASharp_times += ":";
                    highB_times += ":";

                    recordedPianoNotes.put("c5", c_times);
                    recordedPianoNotes.put("c#5", cSharp_times);
                    recordedPianoNotes.put("d5", d_times);
                    recordedPianoNotes.put("d#5", dSharp_times);
                    recordedPianoNotes.put("e5", e_times);
                    recordedPianoNotes.put("f5", f_times);
                    recordedPianoNotes.put("f#5", fSharp_times);
                    recordedPianoNotes.put("g5", g_times);
                    recordedPianoNotes.put("g#5", gSharp_times);
                    recordedPianoNotes.put("a5", a_times);
                    recordedPianoNotes.put("a#5", aSharp_times);
                    recordedPianoNotes.put("b5", b_times);

                    recordedPianoNotes.put("c6", highC_times);
                    recordedPianoNotes.put("c#6", highCSharp_times);
                    recordedPianoNotes.put("d6", highD_times);
                    recordedPianoNotes.put("d#6", highDSharp_times);
                    recordedPianoNotes.put("e6", highE_times);
                    recordedPianoNotes.put("f6", highF_times);
                    recordedPianoNotes.put("f#6", highFSharp_times);
                    recordedPianoNotes.put("g6", highG_times);
                    recordedPianoNotes.put("g#6", highGSharp_times);
                    recordedPianoNotes.put("a6", highA_times);
                    recordedPianoNotes.put("a#6", highASharp_times);
                    recordedPianoNotes.put("b6", highB_times);


                    timer.cancel();



                    pianoTimeStamps = new PianoTimeStamps(CreateBaseSong.this, recordedPianoNotes);



                    recordButton.setText("Play");
                }
                else if(recordButton.getText().toString().equals("Play"))
                {


                    pianoTimeStamps.playPianoRecording();
                }

                /*if (recordButton.getText().toString().equals("Record")) {
                    try {
                        startRecord();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    recordButton.setText("End");
                } else if (recordButton.getText().toString().equals("End")) {
                    System.out.println("It Made it Here");
                    try {
                        stopRecord();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("It Made it Here2");

                    recordButton.setText("Play");
                } else if (recordButton.getText().toString().equals("Play")) {
                    try {
                        startPlayBack();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    recordButton.setText("Stop");
                } else {
                    try {
                        stopPlayBack();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    recordButton.setText("Record");

                }*/





            }

        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder().setMaxStreams(8).build();
        }
        else
        {
            soundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
        }

        c_sound = soundPool.load(this, R.raw.c5w, 1);
        cSharp_sound = soundPool.load(this, R.raw.c_sharp5w, 1);
        d_sound = soundPool.load(this, R.raw.d5w, 1);
        dSharp_sound = soundPool.load(this, R.raw.d_sharp5w, 1);
        e_sound = soundPool.load(this, R.raw.e5w, 1);
        f_sound = soundPool.load(this, R.raw.f5w, 1);
        fSharp_sound = soundPool.load(this, R.raw.f_sharp5w, 1);
        g_sound = soundPool.load(this, R.raw.g5w, 1);
        gSharp_sound = soundPool.load(this, R.raw.g_sharp5w, 1);
        a_sound = soundPool.load(this, R.raw.a5w, 1);
        aSharp_sound = soundPool.load(this, R.raw.a_sharp5w, 1);
        b_sound = soundPool.load(this, R.raw.b5w, 1);

        highC_sound = soundPool.load(this, R.raw.c6w, 1);
        highCSharp_sound = soundPool.load(this, R.raw.c_sharp6w, 1);
        highD_sound = soundPool.load(this, R.raw.d6w, 1);
        highDSharp_sound = soundPool.load(this, R.raw.d_sharp6w, 1);
        highE_sound = soundPool.load(this, R.raw.e6w, 1);
        highF_sound = soundPool.load(this, R.raw.f6w, 1);
        highFSharp_sound = soundPool.load(this, R.raw.f_sharp6w, 1);
        highG_sound = soundPool.load(this, R.raw.g6w, 1);
        highGSharp_sound = soundPool.load(this, R.raw.g_sharp6w, 1);
        highA_Sound = soundPool.load(this, R.raw.a6w, 1);
        highASharp_sound = soundPool.load(this, R.raw.a_sharp6w, 1);
        highB_sound = soundPool.load(this, R.raw.b6w, 1);


        newc_sound = soundPool.load(this, R.raw.c5i, 1);


        int temp_c_id = soundPool.play(c_sound, 1, 1, 0, 0, 1);
        holding_C(temp_c_id);


        int temp_cSharp_id = soundPool.play(cSharp_sound, 1, 1, 0, 0, 1);
        holding_CSharp(temp_cSharp_id);


        int temp_d_id = soundPool.play(d_sound, 1, 1, 0, 0, 1);
        holding_D(temp_d_id);

        int temp_dSharp_id = soundPool.play(dSharp_sound, 1, 1, 0, 0, 1);
        holding_DSharp(temp_dSharp_id);


        int temp_e_id = soundPool.play(e_sound, 1, 1, 0, 0, 1);
        holding_E(temp_e_id);

        int temp_f_id = soundPool.play(f_sound, 1, 1, 0, 0, 1);
        holding_F(temp_f_id);

        int temp_fSharp_id = soundPool.play(fSharp_sound, 1, 1, 0, 0, 1);
        holding_FSharp(temp_fSharp_id);

        int temp_g_id = soundPool.play(g_sound, 1, 1, 0, 0, 1);
        holding_G(temp_g_id);

        int temp_gSharp_id = soundPool.play(gSharp_sound, 1, 1, 0, 0, 1);
        holding_GSharp(temp_gSharp_id);

        int temp_a_id = soundPool.play(a_sound, 1, 1, 0, 0, 1);
        holding_A(temp_a_id);

        int temp_aSharp_id = soundPool.play(aSharp_sound, 1, 1, 0, 0, 1);
        holding_ASharp(temp_aSharp_id);

        int temp_b_id = soundPool.play(b_sound, 1, 1, 0, 0, 1);
        holding_B(temp_b_id);


         //////
        int temp_highC_id = soundPool.play(highC_sound, 1, 1, 0, 0, 1);
        holding_highC(temp_highC_id);


        int temp_highCSharp_id = soundPool.play(highCSharp_sound, 1, 1, 0, 0, 1);
        holding_highCSharp(temp_highCSharp_id);


        int temp_highD_id = soundPool.play(highD_sound, 1, 1, 0, 0, 1);
        holding_highD(temp_highD_id);

        int temp_highDSharp_id = soundPool.play(highDSharp_sound, 1, 1, 0, 0, 1);
        holding_highDSharp(temp_highDSharp_id);


        int temp_highE_id = soundPool.play(highE_sound, 1, 1, 0, 0, 1);
        holding_highE(temp_highE_id);

        int temp_highF_id = soundPool.play(highF_sound, 1, 1, 0, 0, 1);
        holding_highF(temp_highF_id);

        int temp_highFSharp_id = soundPool.play(highFSharp_sound, 1, 1, 0, 0, 1);
        holding_highFSharp(temp_highFSharp_id);

        int temp_highG_id = soundPool.play(highG_sound, 1, 1, 0, 0, 1);
        holding_highG(temp_highG_id);

        int temp_highGSharp_id = soundPool.play(highGSharp_sound, 1, 1, 0, 0, 1);
        holding_highGSharp(temp_highGSharp_id);

        int temp_highA_id = soundPool.play(highA_Sound, 1, 1, 0, 0, 1);
        holding_highA(temp_highA_id);

        int temp_highASharp_id = soundPool.play(highASharp_sound, 1, 1, 0, 0, 1);
        holding_highASharp(temp_highASharp_id);

        int temp_highB_id = soundPool.play(highB_sound, 1, 1, 0, 0, 1);
        holding_highB(temp_highB_id);

    }






    public void holding_C(int soundID)
    {
        final int c_id = soundID;
        noteC.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(isRecording == true)
                    {
                        c_times += secondsPassed + "-";
                    }
                    int new_id = soundPool.play(c_sound, 1, 1, 0, 0, 1);
                    System.out.println("C PRESSED");


                    holding_C(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {




                    if(isRecording == true)
                    {
                        c_times += secondsPassed + ",";
                    }



                    System.out.println("C STOPPED");


                    soundPool.stop(c_id);

                    holding_C(c_id);

                }


                return false;
            }
        });

    }

    public void holding_CSharp(int soundID)
    {
        final int cSharp_id = soundID;
        noteCSharp.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {




                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(isRecording == true)
                    {
                        cSharp_times += secondsPassed + "-";
                    }

                    int new_id = soundPool.play(cSharp_sound, 1, 1, 0, 0, 1);
                    holding_CSharp(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if(isRecording == true)
                    {
                        cSharp_times += secondsPassed + ",";
                    }
                    soundPool.stop(cSharp_id);
                }


                return false;
            }
        });

    }

    public void holding_D(int soundID)
    {
        final int d_id = soundID;
        noteD.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {



                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {

                    if(isRecording == true)
                    {
                        d_times += secondsPassed + "-";
                    }
                    int new_id = soundPool.play(d_sound, 1, 1, 0, 0, 1);
                    holding_D(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {

                    if(isRecording == true)
                    {
                        d_times += secondsPassed + ",";
                    }
                    soundPool.stop(d_id);
                }


                return false;
            }
        });

    }

    public void holding_DSharp(int soundID)
    {
        final int dSharp_id = soundID;
        noteDSharp.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {

                    if(isRecording == true)
                    {
                        dSharp_times += secondsPassed + "-";
                    }
                    int new_id = soundPool.play(dSharp_sound, 1, 1, 0, 0, 1);
                    holding_DSharp(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {

                    if(isRecording == true)
                    {
                        dSharp_times += secondsPassed + ",";
                    }
                    soundPool.stop(dSharp_id);
                }


                return false;
            }
        });

    }

    public void holding_E(int soundID)
    {
        final int e_id = soundID;
        noteE.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {

                    if(isRecording == true)
                    {
                        e_times += secondsPassed + "-";
                    }

                    int new_id = soundPool.play(e_sound, 1, 1, 0, 0, 1);
                    holding_E(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {

                    if(isRecording == true)
                    {
                        e_times += secondsPassed + ",";
                    }

                    soundPool.stop(e_id);
                }


                return false;
            }
        });

    }

    public void holding_F(int soundID)
    {
        final int f_id = soundID;
        noteF.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {

                    if(isRecording == true)
                    {
                        f_times += secondsPassed + "-";
                    }
                    int new_id = soundPool.play(f_sound, 1, 1, 0, 0, 1);
                    holding_F(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {

                    if(isRecording == true)
                    {
                        f_times += secondsPassed + ",";
                    }
                    soundPool.stop(f_id);
                }


                return false;
            }
        });

    }

    public void holding_FSharp(int soundID)
    {
        final int fSharp_id = soundID;
        noteFSharp.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(isRecording == true)
                    {
                        fSharp_times += secondsPassed + "-";
                    }
                    int new_id = soundPool.play(fSharp_sound, 1, 1, 0, 0, 1);
                    holding_FSharp(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if(isRecording == true)
                    {
                        fSharp_times += secondsPassed + ",";
                    }
                    soundPool.stop(fSharp_id);
                }


                return false;
            }
        });

    }

    public void holding_G(int soundID)
    {
        final int g_id = soundID;
        noteG.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(isRecording == true)
                    {
                        g_times += secondsPassed + "-";
                    }
                    int new_id = soundPool.play(g_sound, 1, 1, 0, 0, 1);
                    holding_G(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if(isRecording == true)
                    {
                        g_times += secondsPassed + ",";
                    }
                    soundPool.stop(g_id);
                }


                return false;
            }
        });

    }

    public void holding_GSharp(int soundID)
    {
        final int gSharp_id = soundID;
        noteGSharp.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(isRecording == true)
                    {
                        gSharp_times += secondsPassed + "-";
                    }
                    int new_id = soundPool.play(gSharp_sound, 1, 1, 0, 0, 1);
                    holding_GSharp(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if(isRecording == true)
                    {
                        gSharp_times += secondsPassed + ",";
                    }
                    soundPool.stop(gSharp_id);
                }


                return false;
            }
        });

    }

    public void holding_A(int soundID)
    {
        final int a_id = soundID;
        noteA.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(isRecording == true)
                    {
                        a_times += secondsPassed + "-";
                    }
                    int new_id = soundPool.play(a_sound, 1, 1, 0, 0, 1);
                    holding_A(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if(isRecording == true)
                    {
                        a_times += secondsPassed + ",";
                    }
                    soundPool.stop(a_id);
                }


                return false;
            }
        });

    }

    public void holding_ASharp(int soundID)
    {
        final int aSharp_id = soundID;
        noteASharp.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(isRecording == true)
                    {
                        aSharp_times += secondsPassed + "-";
                    }
                    int new_id = soundPool.play(aSharp_sound, 1, 1, 0, 0, 1);
                    holding_ASharp(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if(isRecording == true)
                    {
                        aSharp_times += secondsPassed + ",";
                    }
                    soundPool.stop(aSharp_id);
                }


                return false;
            }
        });

    }

    public void holding_B(int soundID)
    {
        final int b_id = soundID;
        noteB.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(isRecording == true)
                    {
                        b_times += secondsPassed + "-";
                    }
                    int new_id = soundPool.play(b_sound, 1, 1, 0, 0, 1);
                    holding_B(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if(isRecording == true)
                    {
                        b_times += secondsPassed + ",";
                    }
                    soundPool.stop(b_id);
                }


                return false;
            }
        });

    }


    public void holding_highC(int soundID)
    {
        final int highC_id = soundID;
        note_highC.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(isRecording == true)
                    {
                        highC_times += secondsPassed + "-";
                    }
                    int new_id = soundPool.play(highC_sound, 1, 1, 0, 0, 1);
                    holding_highC(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if(isRecording == true)
                    {
                        highC_times += secondsPassed + ",";
                    }
                    soundPool.stop(highC_id);
                }


                return false;
            }
        });

    }




    public void holding_highCSharp(int soundID)
    {
        final int cSharp_id = soundID;
        note_highCSharp.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(isRecording == true)
                    {
                        highCSharp_times += secondsPassed + "-";
                    }

                    int new_id = soundPool.play(highCSharp_sound, 1, 1, 0, 0, 1);
                    holding_highCSharp(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if(isRecording == true)
                    {
                        highCSharp_times += secondsPassed + ",";
                    }
                    soundPool.stop(cSharp_id);
                }


                return false;
            }
        });

    }

    public void holding_highD(int soundID)
    {
        final int d_id = soundID;
        note_highD.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {



                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {

                    if(isRecording == true)
                    {
                        highD_times += secondsPassed + "-";
                    }
                    int new_id = soundPool.play(highD_sound, 1, 1, 0, 0, 1);
                    holding_highD(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {

                    if(isRecording == true)
                    {
                        highD_times += secondsPassed + ",";
                    }
                    soundPool.stop(d_id);
                }


                return false;
            }
        });

    }

    public void holding_highDSharp(int soundID)
    {
        final int dSharp_id = soundID;
        note_highDSharp.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {

                    if(isRecording == true)
                    {
                        highDSharp_times += secondsPassed + "-";
                    }
                    int new_id = soundPool.play(highDSharp_sound, 1, 1, 0, 0, 1);
                    holding_highDSharp(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {

                    if(isRecording == true)
                    {
                        highDSharp_times += secondsPassed + ",";
                    }
                    soundPool.stop(dSharp_id);
                }


                return false;
            }
        });

    }

    public void holding_highE(int soundID)
    {
        final int e_id = soundID;
        note_highE.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {

                    if(isRecording == true)
                    {
                        highE_times += secondsPassed + "-";
                    }

                    int new_id = soundPool.play(highE_sound, 1, 1, 0, 0, 1);
                    holding_highE(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {

                    if(isRecording == true)
                    {
                        highE_times += secondsPassed + ",";
                    }

                    soundPool.stop(e_id);
                }


                return false;
            }
        });

    }

    public void holding_highF(int soundID)
    {
        final int f_id = soundID;
        note_highF.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {

                    if(isRecording == true)
                    {
                        highF_times += secondsPassed + "-";
                    }
                    int new_id = soundPool.play(highF_sound, 1, 1, 0, 0, 1);
                    holding_highF(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {

                    if(isRecording == true)
                    {
                        highF_times += secondsPassed + ",";
                    }
                    soundPool.stop(f_id);
                }


                return false;
            }
        });

    }

    public void holding_highFSharp(int soundID)
    {
        final int fSharp_id = soundID;
        note_highFSharp.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(isRecording == true)
                    {
                        highFSharp_times += secondsPassed + "-";
                    }
                    int new_id = soundPool.play(highFSharp_sound, 1, 1, 0, 0, 1);
                    holding_highFSharp(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if(isRecording == true)
                    {
                        highFSharp_times += secondsPassed + ",";
                    }
                    soundPool.stop(fSharp_id);
                }


                return false;
            }
        });

    }

    public void holding_highG(int soundID)
    {
        final int g_id = soundID;
        note_highG.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(isRecording == true)
                    {
                        highG_times += secondsPassed + "-";
                    }
                    int new_id = soundPool.play(highG_sound, 1, 1, 0, 0, 1);
                    holding_highG(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if(isRecording == true)
                    {
                        highG_times += secondsPassed + ",";
                    }
                    soundPool.stop(g_id);
                }


                return false;
            }
        });

    }

    public void holding_highGSharp(int soundID)
    {
        final int gSharp_id = soundID;
        note_highGSharp.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(isRecording == true)
                    {
                        highGSharp_times += secondsPassed + "-";
                    }
                    int new_id = soundPool.play(highGSharp_sound, 1, 1, 0, 0, 1);
                    holding_highGSharp(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if(isRecording == true)
                    {
                        highGSharp_times += secondsPassed + ",";
                    }
                    soundPool.stop(gSharp_id);
                }


                return false;
            }
        });

    }

    public void holding_highA(int soundID)
    {
        final int a_id = soundID;
        note_highA.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(isRecording == true)
                    {
                        highA_times += secondsPassed + "-";
                    }
                    int new_id = soundPool.play(highA_Sound, 1, 1, 0, 0, 1);
                    holding_highA(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if(isRecording == true)
                    {
                        highA_times += secondsPassed + ",";
                    }
                    soundPool.stop(a_id);
                }


                return false;
            }
        });

    }

    public void holding_highASharp(int soundID)
    {
        final int aSharp_id = soundID;
        note_highASharp.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(isRecording == true)
                    {
                        highASharp_times += secondsPassed + "-";
                    }
                    int new_id = soundPool.play(highASharp_sound, 1, 1, 0, 0, 1);
                    holding_highASharp(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if(isRecording == true)
                    {
                        highASharp_times += secondsPassed + ",";
                    }
                    soundPool.stop(aSharp_id);
                }


                return false;
            }
        });

    }

    public void holding_highB(int soundID)
    {
        final int b_id = soundID;
        note_highB.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(isRecording == true)
                    {
                        highB_times += secondsPassed + "-";
                    }
                    int new_id = soundPool.play(highB_sound, 1, 1, 0, 0, 1);
                    holding_highB(new_id);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    if(isRecording == true)
                    {
                        highB_times += secondsPassed + ",";
                    }
                    soundPool.stop(b_id);
                }


                return false;
            }
        });

    }




    public void startRecord() throws Exception
    {

        if(record!= null)
        {
            record.release();
        }

        File fileOut = new File(FILE);

        if(fileOut!=null)
        {
            fileOut.delete();
        }

        record = new MediaRecorder();
        record.setAudioSource(MediaRecorder.AudioSource.MIC);
        record.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        record.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        record.setOutputFile(FILE);

        try {
            record.prepare();
            record.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void stopRecord()
    {
        System.out.println("It Made it Here3");

        record.stop();
        System.out.println("It Made it Here4");

        record.release();
        System.out.println("It Made it Here5");

    }
    public void startPlayBack() throws Exception
    {
     //   if(player!=null)
     //   {
     //       player.stop();
      //      player.release();
        //}

        player = new MediaPlayer();
        player.setDataSource(FILE);
        player.prepare();
        player.start();
        player.setOnCompletionListener(new OnCompletionListener() {

            public void onCompletion(MediaPlayer player) {

                player.release();
            }
        });


    }
    public void stopPlayBack()
    {
        if(player!= null)
        {
            player.release();
        }

    }

}
