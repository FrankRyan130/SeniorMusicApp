package com.example.frank_ryan.harmonize_app;

import android.widget.Button;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class HighlightKeys {

    //m_keyboard[] --> holds array of buttons in one minikeyboard
    private Button m_keyboard[] = new Button[24];
    //m_noteTimestamps --> Hashmap storing the recorded notes of instrument
    private HashMap<String, String> m_noteTimestamps;
    //m_colorID --> identification for color of nots
    private int m_colorID;
    //m_timer --> used to schedule task of highlight notes of recording
    private Timer m_timer;
    //m_highlightedSecondsPassed --> integer that iterates during the timer
    private int m_highlightedSecondsPassed = 0;

    public HighlightKeys()
    {

    }
    /*
       NAME

           HighlightKeys-->

       SYNOPSIS

           public HighlightKeys(Button a_highlightButtons[], HashMap<String, String> a_notes)

           a_highlights[] -> holds buttons corresponding to appropriate mini virtual keyboard for an instrument
           a_notes[] -> holds HashMap of recorded notes from instrument

       DESCRIPTION

           Constructor for class. Stores the recorded notes of an instrument, as well as the minikeyboard buttons associated with it to playback the highlighted button recording.

       RETURNS

           void

       AUTHOR

           Frank Ryan
       */
    public HighlightKeys(Button a_highlightButtons[], HashMap<String, String> a_notes, int a_colorID)
    {
        m_keyboard = a_highlightButtons;
        m_noteTimestamps = a_notes;
        m_colorID = a_colorID;

    }

    public void setM_colorID(int a_colorID) { m_colorID = a_colorID; }
    /*
       NAME

           playButtonHighlights-->

       SYNOPSIS

           public void playButtonHighlights(final Button a_highlights[], final HashMap<String, String> a_notes)

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
    public void playButtonHighlights(Timer a_timer)
    {
        m_timer = a_timer;
        final TimerTask task = new TimerTask()
        {

            public void run()
            {
                //m_highlightedSecondsPassed is incremented every 10 milliseconds
                m_highlightedSecondsPassed++;

                //All note functions are called to alter the color of the mini piano keyboard
                highlight_c(m_keyboard[0], m_noteTimestamps);
                highlight_cSharp(m_keyboard[1], m_noteTimestamps);
                highlight_d(m_keyboard[2], m_noteTimestamps);
                highlight_dSharp(m_keyboard[3], m_noteTimestamps);
                highlight_e(m_keyboard[4], m_noteTimestamps);
                highlight_f(m_keyboard[5], m_noteTimestamps);
                highlight_fSharp(m_keyboard[6], m_noteTimestamps);
                highlight_g(m_keyboard[7], m_noteTimestamps);
                highlight_gSharp(m_keyboard[8], m_noteTimestamps);
                highlight_a(m_keyboard[9], m_noteTimestamps);
                highlight_aSharp(m_keyboard[10], m_noteTimestamps);
                highlight_b(m_keyboard[11], m_noteTimestamps);

                highlight_highC(m_keyboard[12], m_noteTimestamps);
                highlight_highCSharp(m_keyboard[13], m_noteTimestamps);
                highlight_highD(m_keyboard[14], m_noteTimestamps);
                highlight_highDSharp(m_keyboard[15], m_noteTimestamps);
                highlight_highE(m_keyboard[16], m_noteTimestamps);
                highlight_highF(m_keyboard[17], m_noteTimestamps);
                highlight_highFSharp(m_keyboard[18], m_noteTimestamps);
                highlight_highG(m_keyboard[19], m_noteTimestamps);
                highlight_highGSharp(m_keyboard[20], m_noteTimestamps);
                highlight_highA(m_keyboard[21], m_noteTimestamps);
                highlight_highASharp(m_keyboard[22], m_noteTimestamps);
                highlight_highB(m_keyboard[23], m_noteTimestamps);



            }
        };

        m_timer.scheduleAtFixedRate(task, 10, 10);

    }
    /*
   NAME

       stopPlaying-->

   SYNOPSIS

       public void stopPlaying()

   DESCRIPTION

       Cancels timer for playButtonHighlights().

   RETURNS

       void

   AUTHOR

       Frank Ryan
   */
    public void stopPlaying()
    {
        m_timer.cancel();
        m_timer.purge();
    }
    /*
       NAME
           highlight_c-->

       SYNOPSIS
           public void highlight_c(Button a_pianoButton, HashMap<String, String> a_notes)
           a_pianoButton -> low C note button for mini keyboard
           a_notes[] -> HashMap of all recorded notes of one instrument

       DESCRIPTION
           Called in playButtonHighlights() throughout the timer object.
           If there are no notes in the HashMap for this note, the value will be ":".
           Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
           If so, the note will turn green if it was pressed, or default if it was released.

       RETURNS
           void

       AUTHOR
           Frank Ryan
       */
    public void highlight_c(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("lowC").equals(":"))
        {

        }
        else {
            mainNoteHighlight("lowC", a_pianoButton, a_notes);

        }

    }
    /*
    NAME
        highlight_cSharp-->

    SYNOPSIS
        public void highlight_cSharp(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> low C Sharp note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION
        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_cSharp(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("lowCSharp").equals(":"))
        {

        }
        else {
            mainNoteHighlight("lowCSharp", a_pianoButton, a_notes);

        }

    }
    /*
    NAME
        highlight_d-->

    SYNOPSIS
        public void highlight_d(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> low D note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION
        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_d(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("lowD").equals(":"))
        {

        }
        else {
            mainNoteHighlight("lowD", a_pianoButton, a_notes);
        }
    }
    /*
    NAME
        highlight_dSharp-->

    SYNOPSIS
        public void highlight_dSharp(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> low D Sharp note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION
        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_dSharp(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("lowDSharp").equals(":"))
        {

        }
        else {
            mainNoteHighlight("lowDSharp", a_pianoButton, a_notes);

        }

    }
    /*
    NAME
        highlight_e-->

    SYNOPSIS
        public void highlight_e(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> low E note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION
        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_e(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("lowE").equals(":"))
        {

        }
        else {

            mainNoteHighlight("lowE", a_pianoButton, a_notes);
        }
    }
    /*
    NAME
        highlight_f-->

    SYNOPSIS
        public void highlight_f(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> low F note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION
        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_f(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("lowF").equals(":"))
        {

        }
        else {

            mainNoteHighlight("lowF", a_pianoButton, a_notes);
        }
    }
    /*
    NAME
        highlight_fSharp-->

    SYNOPSIS
        public void highlight_fSharp(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> low F Sharp note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION
        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_fSharp(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("lowFSharp").equals(":"))
        {

        }
        else {
            mainNoteHighlight("lowFSharp", a_pianoButton, a_notes);

        }

    }
    /*
    NAME
        highlight_g-->

    SYNOPSIS
        public void highlight_g(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> low G note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION
        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_g(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("lowG").equals(":"))
        {

        }
        else {
            mainNoteHighlight("lowG", a_pianoButton, a_notes);

        }

    }
    /*
    NAME
        highlight_gSharp ->

    SYNOPSIS
        public void highlight_gSharp(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> low G Sharp note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION
        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_gSharp(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("lowGSharp").equals(":"))
        {

        }
        else {
            mainNoteHighlight("lowGSharp", a_pianoButton, a_notes);

        }

    }
    /*
    NAME
        highlight_a-->

    SYNOPSIS
        public void highlight_a(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> low A note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION
        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_a(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("lowA").equals(":"))
        {

        }
        else {
            mainNoteHighlight("lowA", a_pianoButton, a_notes);

        }

    }
    /*
    NAME
        highlight_aSharp-->

    SYNOPSIS
        public void highlight_aSharp(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> low A Sharp note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION
        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_aSharp(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("lowASharp").equals(":"))
        {

        }
        else {
            mainNoteHighlight("lowASharp", a_pianoButton, a_notes);

        }

    }
    /*
    NAME
        highlight_b-->

    SYNOPSIS
        public void highlight_b(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> low B note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION
        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_b(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("lowB").equals(":"))
        {

        }
        else {
            mainNoteHighlight("lowB", a_pianoButton, a_notes);

        }

    }
    /*
    NAME
        highlight_highC-->

    SYNOPSIS
        public void highlight_highC(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> high C note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION
        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_highC(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("highC").equals(":"))
        {

        }
        else {
            mainNoteHighlight("highC", a_pianoButton, a_notes);

        }

    }
    /*
    NAME
        highlight_highCSharp-->

    SYNOPSIS
        public void highlight_highCSharp(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> high C Sharp note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION
        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_highCSharp(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("highCSharp").equals(":"))
        {

        }
        else {
            mainNoteHighlight("highCSharp", a_pianoButton, a_notes);

        }

    }
    /*
    NAME
        highlight_highD ->

    SYNOPSIS
        public void highlight_highD(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> high D note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION
        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_highD(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("highD").equals(":"))
        {

        }
        else {
            mainNoteHighlight("highD", a_pianoButton, a_notes);
        }
    }
    /*
    NAME
        highlight_highDSharp-->

    SYNOPSIS
        public void highlight_highDSharp(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> high D Sharp note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION
        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_highDSharp(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("highDSharp").equals(":"))
        {

        }
        else {
            mainNoteHighlight("highDSharp", a_pianoButton, a_notes);

        }

    }
    /*
    NAME
        highlight_highE-->

    SYNOPSIS

        public void highlight_highE(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> high E note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION
        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_highE(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("highE").equals(":"))
        {

        }
        else {

            mainNoteHighlight("highE", a_pianoButton, a_notes);
        }
    }
    /*
    NAME
        highlight_highF-->

    SYNOPSIS
        public void highlight_highF(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> high F note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION
        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_highF(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("highF").equals(":"))
        {

        }
        else {

            mainNoteHighlight("highF", a_pianoButton, a_notes);
        }
    }
    /*
    NAME
        highlight_highFSharp-->

    SYNOPSIS

        public void highlight_highFSharp(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> high F Sharp note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION
        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_highFSharp(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("highFSharp").equals(":"))
        {

        }
        else {
            mainNoteHighlight("highFSharp", a_pianoButton, a_notes);

        }

    }
    /*
    NAME
        highlight_highG-->

    SYNOPSIS
        public void highlight_highG(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> high G note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION
        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_highG(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("highG").equals(":"))
        {

        }
        else {
            mainNoteHighlight("highG", a_pianoButton, a_notes);

        }

    }
    /*
    NAME
        highlight_highGSharp-->

    SYNOPSIS
        public void highlight_highGSharp(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> high G Sharp note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION
        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_highGSharp(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("highGSharp").equals(":"))
        {

        }
        else {
            mainNoteHighlight("highGSharp", a_pianoButton, a_notes);

        }

    }
    /*
    NAME
        highlight_highA-->

    SYNOPSIS

        public void highlight_highA(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> high A note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION
        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_highA(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("highA").equals(":"))
        {

        }
        else {
            mainNoteHighlight("highA", a_pianoButton, a_notes);

        }

    }
    /*
    NAME
        highlight_highASharp-->

    SYNOPSIS
        public void highlight_highASharp(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> high A Sharp note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION
        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_highASharp(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("highASharp").equals(":"))
        {

        }
        else {
            mainNoteHighlight("highASharp", a_pianoButton, a_notes);

        }

    }
    /*
    NAME

        highlight_highB-->

    SYNOPSIS

        public void highlight_highB(Button a_pianoButton, HashMap<String, String> a_notes)
        a_pianoButton -> high B note button for mini keyboard
        a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION

        Called in playButtonHighlights() throughout the timer object.
        If there are no notes in the HashMap for this note, the value will be ":".
        Else, it will call mainNoteHighlight() to see if the first number of the HashMap value equals m_highlightedSecondsPassed.
        If so, the note will turn green if it was pressed, or default if it was released.

    RETURNS
        void

    AUTHOR
        Frank Ryan
    */
    public void highlight_highB(Button a_pianoButton, HashMap<String, String> a_notes)
    {
        if(a_notes.get("highB").equals(":"))
        {

        }
        else {
            mainNoteHighlight("highB", a_pianoButton, a_notes);

        }

    }
    /*
    NAME

        mainNoteHighlight-->

    SYNOPSIS

        public void mainNoteHighlight(String a_hashNote, Button a_pianoButton, HashMap<String, String> a_notes)

        a_hashNote -> key for note

        a_pianoButton -> button for the appropriate key (note)

        a_notes[] -> a_notes[] -> HashMap of all recorded notes of one instrument

    DESCRIPTION

        Called from all notes during the playButtonHighlights() function. Takes the value of the hashNote key and parses it using .split of the character '-'.
        Using that split, it checks to see if the left string of the split is empty (the '=' was the first character of the hashNote value).
            If so, it then splits the right string of the previous split using the delimeter ','.
            Then, if the left string of the delimeter ',' split equals m_highlightedSecondsPassed, then that note was released at this time during this instrument recording, and that key visual is set to default.
        If the left string of the first delimeter split was not empty, and if the left splitted string equals m_highlightedSecondsPassed, then this note was pressed at this time, and the key changes to color green.

    RETURNS

        void

    AUTHOR

        Frank Ryan
    */
    public void mainNoteHighlight(String a_hashNote, Button a_pianoButton, HashMap<String, String> a_notes)
    {
        //retrieves the value of hashNote key and splits it once using the delimeter "-"
        String string = a_notes.get(a_hashNote);
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

            //If this left side split equals m_highlightedSecondsPassed, the note was previously pressed, and is being released at this time.
            if (Integer.parseInt(number) == m_highlightedSecondsPassed) {
                //Depending on which key, set the note back to its default piano background
                if(a_hashNote.equals("lowC"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.button_border);
                }
                else if(a_hashNote.equals("lowCSharp"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.black_keys);
                }
                else if(a_hashNote.equals("lowD"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.button_border);
                }
                else if(a_hashNote.equals("lowDSharp"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.black_keys);
                }
                else if(a_hashNote.equals("lowE"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.button_border);
                }
                else if(a_hashNote.equals("lowF"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.button_border);
                }
                else if(a_hashNote.equals("lowFSharp"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.black_keys);
                }
                else if(a_hashNote.equals("lowG"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.button_border);
                }
                else if(a_hashNote.equals("lowGSharp"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.black_keys);
                }
                else if(a_hashNote.equals("lowA"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.button_border);
                }
                else if(a_hashNote.equals("lowASharp"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.black_keys);
                }
                else if(a_hashNote.equals("lowB"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.button_border);
                }



                else if(a_hashNote.equals("highC"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.button_border);
                }
                else if(a_hashNote.equals("highCSharp"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.black_keys);
                }
                else if(a_hashNote.equals("highD"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.button_border);
                }
                else if(a_hashNote.equals("highDSharp"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.black_keys);
                }
                else if(a_hashNote.equals("highE"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.button_border);
                }
                else if(a_hashNote.equals("highF"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.button_border);
                }
                else if(a_hashNote.equals("highFSharp"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.black_keys);
                }
                else if(a_hashNote.equals("highG"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.button_border);
                }
                else if(a_hashNote.equals("highGSharp"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.black_keys);
                }
                else if(a_hashNote.equals("highA"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.button_border);
                }
                else if(a_hashNote.equals("highASharp"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.black_keys);
                }
                else if(a_hashNote.equals("highB"))
                {
                    a_pianoButton.setBackgroundResource(R.drawable.button_border);
                }
                a_notes.put(a_hashNote, rest);
            }
        }
        //A number was the first character of the HashMap String value, and it also equals m_highlightSecondsPassed, meaning this key was pressed at this time during the user's recording
        else if (Integer.parseInt(part1) == m_highlightedSecondsPassed) {


            //Depending on which key, set the note to the pressed_key_green drawable, add a "-" to the beginning of the hashNote value

            if(m_colorID == 0)
            {
                a_pianoButton.setBackgroundResource(R.drawable.pressed_key_green);
            }
            else if (m_colorID == 1)
            {
                a_pianoButton.setBackgroundResource(R.drawable.pressed_key_purple);
            }
            else if(m_colorID == 2)
            {
                a_pianoButton.setBackgroundResource(R.drawable.pressed_key_blue);
            }
            else if (m_colorID == 3)
            {
                a_pianoButton.setBackgroundResource(R.drawable.pressed_key_yellow);
            }
            else if(m_colorID == 4)
            {
                a_pianoButton.setBackgroundResource(R.drawable.pressed_key_red);
            }


            a_notes.put(a_hashNote, "-" + part2);


        }


    }


}
