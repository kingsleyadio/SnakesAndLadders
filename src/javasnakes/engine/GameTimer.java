/*
 * This source belongs to the Snake and Ladder project of GROUP 1
 * of CPE 316 (Artificial Intelligence) class of 2010/2011 session at the
 * department of computer science and engineering, Obafemi Awolowo University
 * Ile-Ife.
 * This source file should not be used out of this context and it remains an
 * property of both the department and the people involved.
 */

package javasnakes.engine;

import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * Keeps track of the time a user has spent playing a particular level.
 * This time is part of the scoring criteria.
 *
 * @author Group 1, CPE 316 (2010/2011)
 * @version 1.0
 */
public class GameTimer {
    private Timer timer = null;
    private final ActionListener actionListener;
    private long time;

    /**
     * The main constructor.
     *
     * @param actionListener the <code>ActionListener</code> to be notified of
     * time events.
     */
    public GameTimer(ActionListener actionListener) {
        this.actionListener = actionListener;
        this.timer = new Timer(0, actionListener);
        timer.setActionCommand(Long.toString(time));
        timer.setCoalesce(true);
    }

    /**
     * Starts the time.
     */
    public void start() {
        timer.start();
    }

    /**
     * Restarts the time.
     */
    public void restart() {
        timer.restart();
    }

    /**
     * Pauses the time.
     */
    public void pause() {
        timer.stop();
    }

    /**
     * Fetches the elapsed time.
     *
     * @return the elapsed time (in seconds).
     */
    public long getTime() {
        return time;
    }

    /**
     * Fetches the elapsed hours.
     *
     * @return the elapsed hours.
     */
    public long getHour() {
        return time/3600;
    }

    /**
     * Fetches the elapsed minute after the elapsed hours has been subtracted.
     * Please note that this will return a value
     * between <code>0</code> and <code>59</code>.
     *
     * @return the elapsed minutes.
     */
    public long getMinute() {
        return (time % 3600) / 60;
    }

    /**
     * Fetches the elapsed minute after the elapsed hours and minutes have
     * been subtracted. Please note that this will return a value
     * between <code>0</code> and <code>59</code>.
     *
     * @return the elapsed seconds.
     */
    public long getSeconds() {
        return time % 60;
    }
}
