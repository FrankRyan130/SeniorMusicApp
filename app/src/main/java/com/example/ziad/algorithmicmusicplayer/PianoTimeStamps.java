package com.example.ziad.algorithmicmusicplayer;

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

public class PianoTimeStamps extends AppCompatActivity implements Serializable {

    private HashMap<String, String> pianoNotes;
    private SoundPool soundPool;
    private int c_sound, cSharp_sound, d_sound, dSharp_sound, e_sound, f_sound, fSharp_sound, g_sound, gSharp_sound, a_sound, aSharp_sound, b_sound,
            highC_sound, highCSharp_sound, highD_sound, highDSharp_sound, highE_sound, highF_sound, highFSharp_sound, highG_sound,
            highGSharp_sound, highA_Sound, highASharp_sound, highB_sound;

    private int c_id, cSharp_id, d_id, dSharp_id, e_id, f_id, fSharp_id, g_id, gSharp_id, a_id, aSharp_id, b_id,
                highC_id, highCSharp_id, highD_id, highDSharp_id, highE_id, highF_id, highFSharp_id, highG_id, highGSharp_id, highA_id, highASharp_id, highB_id;

    private int secondsPassed = 0;

    private boolean trackPlaying;

    public PianoTimeStamps(HashMap<String, String> recordedNotes)
    {
        pianoNotes = recordedNotes;



    }


    public PianoTimeStamps(Context context, HashMap<String, String> recordedNotes)
    {
        pianoNotes = recordedNotes;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder().setMaxStreams(8).build();
        }
        else
        {
            soundPool = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
        }



        c_sound = soundPool.load(context, R.raw.c5w, 1);
        cSharp_sound = soundPool.load(context, R.raw.c_sharp5w, 1);
        d_sound = soundPool.load(context, R.raw.d5w, 1);
        dSharp_sound = soundPool.load(context, R.raw.d_sharp5w, 1);
        e_sound = soundPool.load(context, R.raw.e5w, 1);
        f_sound = soundPool.load(context, R.raw.f5w, 1);
        fSharp_sound = soundPool.load(context, R.raw.f_sharp5w, 1);
        g_sound = soundPool.load(context, R.raw.g5w, 1);
        gSharp_sound = soundPool.load(context, R.raw.g_sharp5w, 1);
        a_sound = soundPool.load(context, R.raw.a5w, 1);
        aSharp_sound = soundPool.load(context, R.raw.a_sharp5w, 1);
        b_sound = soundPool.load(context, R.raw.b5w, 1);


        highC_sound = soundPool.load(context, R.raw.c6w, 1);
        highCSharp_sound = soundPool.load(context, R.raw.c_sharp6w, 1);
        highD_sound = soundPool.load(context, R.raw.d6w, 1);
        highDSharp_sound = soundPool.load(context, R.raw.d_sharp6w, 1);
        highE_sound = soundPool.load(context, R.raw.e6w, 1);
        highF_sound = soundPool.load(context, R.raw.f6w, 1);
        highFSharp_sound = soundPool.load(context, R.raw.f_sharp6w, 1);
        highG_sound = soundPool.load(context, R.raw.g6w, 1);
        highGSharp_sound = soundPool.load(context, R.raw.g_sharp6w, 1);
        highA_Sound = soundPool.load(context, R.raw.a6w, 1);
        highASharp_sound = soundPool.load(context, R.raw.a_sharp6w, 1);
        highB_sound = soundPool.load(context, R.raw.b6w, 1);

    }


    public HashMap<String, String> getPianoNotes()
    {
        return pianoNotes;
    }


    public void play_c()
    {
        if(pianoNotes.get("c5").equals(":"))
        {

        }
        else {
            mainNotePlay("c5", c_id, c_sound);

        }

    }
    public void play_cSharp()
    {
        if(pianoNotes.get("c#5").equals(":"))
        {

        }
        else {
            mainNotePlay("c#5", cSharp_id, cSharp_sound);

        }

    }


    public void play_d()
    {
        if(pianoNotes.get("d5").equals(":"))
        {

        }
        else {
            mainNotePlay("d5", d_id, d_sound);
        }
    }
    public void play_dSharp()
    {
        if(pianoNotes.get("d#5").equals(":"))
        {

        }
        else {
            mainNotePlay("d#5", dSharp_id, dSharp_sound);

        }

    }

    public void play_e()
    {
        if(pianoNotes.get("e5").equals(":"))
        {

        }
        else {

            mainNotePlay("e5", e_id, e_sound);
        }
    }

    public void play_f()
    {
        if(pianoNotes.get("f5").equals(":"))
        {

        }
        else {

            mainNotePlay("f5", f_id, f_sound);
        }
    }

    public void play_fSharp()
    {
        if(pianoNotes.get("f#5").equals(":"))
        {

        }
        else {
            mainNotePlay("f#5", fSharp_id, fSharp_sound);

        }

    }

    public void play_g()
    {
        if(pianoNotes.get("g5").equals(":"))
        {

        }
        else {
            mainNotePlay("g5", g_id, g_sound);

        }

    }

    public void play_gSharp()
    {
        if(pianoNotes.get("g#5").equals(":"))
        {

        }
        else {
            mainNotePlay("g#5", gSharp_id, gSharp_sound);

        }

    }


    public void play_a()
    {
        if(pianoNotes.get("a5").equals(":"))
        {

        }
        else {
            mainNotePlay("a5", a_id, a_sound);

        }

    }
    public void play_aSharp()
    {
        if(pianoNotes.get("a#5").equals(":"))
        {

        }
        else {
            mainNotePlay("a#5", aSharp_id, aSharp_sound);

        }

    }
    public void play_b()
    {
        if(pianoNotes.get("b5").equals(":"))
        {

        }
        else {
            mainNotePlay("b5", b_id, b_sound);

        }

    }
    public void play_highC()
    {
        if(pianoNotes.get("c6").equals(":"))
        {

        }
        else {
            mainNotePlay("c6", highC_id, highC_sound);

        }

    }
    public void play_highCSharp()
    {
        if(pianoNotes.get("c#6").equals(":"))
        {

        }
        else {
            mainNotePlay("c#6", highCSharp_id, highCSharp_sound);

        }

    }


    public void play_highD()
    {
        if(pianoNotes.get("d6").equals(":"))
        {

        }
        else {
            mainNotePlay("d6", highD_id, highD_sound);
        }
    }
    public void play_highDSharp()
    {
        if(pianoNotes.get("d#6").equals(":"))
        {

        }
        else {
            mainNotePlay("d#6", highDSharp_id, highDSharp_sound);

        }

    }

    public void play_highE()
    {
        if(pianoNotes.get("e6").equals(":"))
        {

        }
        else {

            mainNotePlay("e6", highE_id, highE_sound);
        }
    }

    public void play_highF()
    {
        if(pianoNotes.get("f6").equals(":"))
        {

        }
        else {

            mainNotePlay("f6", highF_id, highF_sound);
        }
    }

    public void play_highFSharp()
    {
        if(pianoNotes.get("f#6").equals(":"))
        {

        }
        else {
            mainNotePlay("f#6", highFSharp_id, highFSharp_sound);

        }

    }

    public void play_highG()
    {
        if(pianoNotes.get("g6").equals(":"))
        {

        }
        else {
            mainNotePlay("g6", highG_id, highG_sound);

        }

    }

    public void play_highGSharp()
    {
        if(pianoNotes.get("g#6").equals(":"))
        {

        }
        else {
            mainNotePlay("g#6", highGSharp_id, highG_sound);

        }

    }


    public void play_highA()
    {
        if(pianoNotes.get("a6").equals(":"))
        {

        }
        else {
            mainNotePlay("a6", highA_id, highA_Sound);

        }

    }
    public void play_highASharp()
    {
        if(pianoNotes.get("a#6").equals(":"))
        {

        }
        else {
            mainNotePlay("a#6", highASharp_id, highASharp_sound);

        }

    }
    public void play_highB()
    {
        if(pianoNotes.get("b6").equals(":"))
        {

        }
        else {
            mainNotePlay("b6", highB_id, highB_sound);

        }

    }









    public void mainNotePlay(String hashNote, int noteID, int noteSound)
    {

        String string = pianoNotes.get(hashNote);
        String[] parts = string.split("-", 2);
        String part1 = parts[0]; // 004
        String part2 = parts[1]; // 034556

        if (part1.isEmpty()) {

            String[] new_parts = part2.split(",", 2);
            String number = new_parts[0];
            String rest = new_parts[1];

            if (Integer.parseInt(number) == secondsPassed) {
                soundPool.stop(noteID);
                pianoNotes.put(hashNote, rest);
            }
        } else if (Integer.parseInt(part1) == secondsPassed) {


            if(noteID == c_id)
            {
                c_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }
            else if(noteID == cSharp_id)
            {
                cSharp_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }
            else if(noteID == d_id)
            {
                d_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }
            else if(noteID == dSharp_id)
            {
                dSharp_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }
            else if(noteID == e_id)
            {
                e_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }
            else if(noteID == f_id)
            {
                f_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }
            else if(noteID == fSharp_id)
            {
                fSharp_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }
            else if(noteID == g_id)
            {
                g_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }
            else if(noteID == gSharp_id)
            {
                gSharp_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }
            else if(noteID == a_id)
            {
                a_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }
            else if(noteID == aSharp_id)
            {
                aSharp_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }
            else if(noteID == b_id)
            {
                b_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }



            else if(noteID == highC_id)
            {
                highC_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }
            else if(noteID == highCSharp_id)
            {
                highCSharp_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }
            else if(noteID == highD_id)
            {
                highD_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }
            else if(noteID == highDSharp_id)
            {
                highDSharp_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }
            else if(noteID == highE_id)
            {
                highE_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }
            else if(noteID == highFSharp_id)
            {
                highFSharp_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }
            else if(noteID == highG_id)
            {
                highG_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }
            else if(noteID == highGSharp_id)
            {
                highGSharp_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }
            else if(noteID == highA_id)
            {
                highA_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }
            else if(noteID == highASharp_id)
            {
                highASharp_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }
            else if(noteID == highB_id)
            {
                highB_id = soundPool.play(noteSound, 1, 1, 0, 0, 1);
                pianoNotes.put(hashNote, "-" + part2);
            }

        }


    }


    public void playPianoRecording()
    {
        final Timer timer = new Timer();
        final TimerTask task = new TimerTask()
        {

            public void run()
            {
                secondsPassed++;

                play_c();
                play_cSharp();
                play_d();
                play_dSharp();
                play_e();
                play_f();
                play_fSharp();
                play_g();
                play_gSharp();
                play_a();
                play_aSharp();
                play_b();

                play_highC();
                play_highCSharp();
                play_highD();
                play_highDSharp();
                play_highE();
                play_highF();
                play_highFSharp();
                play_highG();
                play_highGSharp();
                play_highA();
                play_highASharp();
                play_highB();

                if(Collections.frequency(pianoNotes.values(), ":") == 24)
                {
                    soundPool.release();

                    timer.cancel();
                    timer.purge();

                    trackPlaying = false;
                }

            }
        };
        trackPlaying = true;
       timer.scheduleAtFixedRate(task, 10, 10);



    }


    public boolean isPlaying()
    {

        return trackPlaying;
    }


}
