package edu.x3m.kas.utils;


/**
 * Debugging tool for time measuring
 *
 * @author Hans
 */
public class Timer {


    private long time;



    public Timer () {
    }



    /**
     * Start timer
     */
    public void tic () {
        time = System.currentTimeMillis ();
    }



    /**
     * Restart timer and prints message and time in ms
     * {@code message+time+ms}
     *
     * @param message
     */
    public void tac (String message) {
        time = System.currentTimeMillis () - time;
        System.out.println (message + time + "ms");
        time = System.currentTimeMillis ();
    }



    /**
     * Restart timer and prints time in ms
     */
    public void tac () {
        tac ("");
    }
}
