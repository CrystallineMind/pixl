package main.java.com.timelessapps.javafxtemplate.app.supportingthreads;

import main.java.com.timelessapps.javafxtemplate.app.businesslogic.MainBotRoutine;
import main.java.com.timelessapps.javafxtemplate.app.businesslogic.MeleeRoutine;
import main.java.com.timelessapps.javafxtemplate.helpers.abstractsandenums.Buff;
import main.java.com.timelessapps.javafxtemplate.helpers.services.LoggingService;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuffTimer extends Thread {
    LoggingService log = new LoggingService();

    MainBotRoutine mainBotRoutine;
    MeleeRoutine meleeRoutine;
    int timeToWait;
    Buff buff;
    String mainBotState;
    String meleeState = "TIMED_WAITING";

    public BuffTimer(MainBotRoutine mainBotRoutine, int timeToWait, Buff buff) {
        this.mainBotRoutine = mainBotRoutine;
        this.timeToWait = timeToWait;
        this.buff = buff;
    }

    public BuffTimer(MeleeRoutine meleeRoutine, int timeToWait, Buff buff) {
        this.meleeRoutine = meleeRoutine;
        this.timeToWait = timeToWait;
        this.buff = buff;
    }

    @Override
    public void run() {

        log.appendToEventLogsFile("The timer for " + buff + " has started for " + timeToWait + " milliseconds. ");

        try {
            Thread.sleep(timeToWait);
        } catch (InterruptedException ex) {
            Logger.getLogger(BuffTimer.class.getName()).log(Level.SEVERE, null, ex);
        }

        log.appendToEventLogsFile("The timer for " + buff + " has expired. ");

        mainBotState = mainBotRoutine.getState().toString();
        try {
            meleeState = meleeRoutine.getState().toString();
        } catch (Exception xx) {
            System.out.println("No melee state");
        }


        switch (buff) {
            case ABSORB:
                while (!(mainBotState.equals("TIMED_WAITING"))) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BuffTimer.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    mainBotState = mainBotRoutine.getState().toString();
                }
            {
                try {
                    mainBotRoutine.setShouldAbsorb(true);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BuffTimer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            log.appendToEventLogsFile("Setting shouldAbsorb to true. Currently shouldAbsorb is: "
                    + mainBotRoutine.getShouldAbsorb() + " (Should be true)");

            break;

            case OVERLOAD:
                while (!(mainBotState.equals("TIMED_WAITING"))) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BuffTimer.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    mainBotState = mainBotRoutine.getState().toString();
                }
            {
                try {
                    mainBotRoutine.setShouldOverload(true);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BuffTimer.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }

            case AGGRO:
                while (!(meleeState.equals("TIMED_WAITING"))) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BuffTimer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        meleeState = meleeRoutine.getState().toString();
                    } catch (Exception exxx) {
                        System.out.println(exxx);
                    }
                }
            {
                meleeRoutine.setShouldResetAggro(true);

            }
            /*
             * try { log.
             * appendToEventLogsFile("Setting shouldAbsorb to true. Currently shouldOverload is: "
             * + mainBotRoutine.getShouldOverload() + " (Should be true)"); } catch
             * (FileNotFoundException ex)
             * {Logger.getLogger(BuffTimer.class.getName()).log(Level.SEVERE, null, ex);}
             */

            break;

            default:
                log.appendToEventLogsFile("The current buff (" + buff.toString() + ") could not be handled. ");
                break;
        }

    }
}
