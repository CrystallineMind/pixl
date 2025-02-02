package com.xerxes.javafxtemplate.app.businesslogic;


import com.xerxes.javafxtemplate.app.supportingthreads.BuffTimer;
import com.xerxes.javafxtemplate.helpers.abstractsandenums.Routine;
import com.xerxes.javafxtemplate.helpers.services.CustomSceneHelper;
import com.xerxes.javafxtemplate.helpers.services.LoggingService;
import com.xerxes.javafxtemplate.helpers.services.RobotService;

import java.awt.*;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.xerxes.javafxtemplate.helpers.abstractsandenums.Buff.ABSORB;
import static com.xerxes.javafxtemplate.helpers.abstractsandenums.Buff.OVERLOAD;
import static com.xerxes.javafxtemplate.helpers.abstractsandenums.Coordinates.X;
import static com.xerxes.javafxtemplate.helpers.abstractsandenums.Coordinates.Y;


public class MainBotRoutine extends Routine {
    //This class is for NMZ
    //Based on 88 absorbs and 20 overloads. Changing to 80 absorbs and 28 overloads with 70+ def and full rune (0405_80def)
    RobotService bot = new RobotService();
    LoggingService log = new LoggingService();
    Random random = new Random();

    boolean shouldOverload = true;
    boolean shouldAbsorb = true;

    int absorbCounter = 4;
    int overloadCounter = 0;
    int overloadDoseCounter = 0;

    public MainBotRoutine() throws AWTException {

    }

    public void run() {
        log.appendToEventLogsFile("Starting bot routine in 3 seconds. ");

        System.out.println("Starting bot routine in 3 seconds. ");
        bot.delay(3000);

        synchronized (this) {
            try {
                disableStartButton();
                while (running) {
                    checkIfPausedOrStopped();
                    /** Start routine here.  **/
                    if (shouldOverload) {
                        setShouldOverload(false);
                        moveToOverload();
                        bot.delay(100);
                        System.out.println("Drinking overload");

                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        System.out.println("Current Date and Time: " + dtf.format(now));

                        drinkOverload();
                        BuffTimer overloadTimer = new BuffTimer(this, 300800, OVERLOAD); //300800
                        overloadTimer.setDaemon(true);
                        overloadTimer.start();
                    }

                    if (shouldAbsorb) {
                        setShouldAbsorb(false);
                        moveToAbsorb();
                        bot.delay(100);
                        System.out.println("Drinking absorb");

                        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime now2 = LocalDateTime.now();
                        System.out.println("Current Date and Time: " + dtf2.format(now2));

                        drinkAbsorb();
                        BuffTimer absorbTimer = new BuffTimer(this, 280000, ABSORB); //301000. Works with dhide at 302300, 500000 works for full rune at 45 def
                        absorbTimer.setDaemon(true);
                        absorbTimer.start();
                    }

                    moveToPrayButton();
                    flickPray();

                    Thread.sleep(random.nextInt(15000) + 10000); //HP goes up every minute, so have to make sure this runs around every 45 seconds or less.
                    /** End routine here. **/
                    checkIfPausedOrStopped();
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(MainBotRoutine.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainBotRoutine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //351
    @Override
    public void checkIfPausedOrStopped() throws InterruptedException {
        if (absorbCounter > 21) {
            System.out.println("Preparing to exit. ");
            //For waking up and preparing to sleep computer.
            Thread.sleep(200000);
            bot.mouseClick();
            bot.delay(1000);
            bot.mouseClick();

            Thread.sleep(200000);
            bot.mouseClick();
            bot.delay(1000);
            bot.mouseClick();

            Thread.sleep(200000);
            bot.mouseClick();
            bot.delay(1000);
            bot.mouseClick();

            //For sleeping computer.
            bot.delay(1000);
            bot.moveCursorTo(35, 1050);
            bot.delay(1000);
            bot.mouseClick();
            bot.delay(1000);
            bot.moveCursorTo(35, 985);
            bot.delay(1000);
            bot.mouseClick();
            bot.delay(1000);
            bot.moveCursorTo(35, 811);
            bot.delay(1000);
            bot.mouseClick();
            System.out.println("About to exit. ");

            System.exit(0);
            log.appendToEventLogsFile("Absorb counter over 21, about to exit. ");
            paused = true;
        }

        waitIfPaused();
        if (!running) {
            enableStartButton();
        }
    }

    private void disableStartButton() {
        CustomSceneHelper sceneHelper = new CustomSceneHelper();
        sceneHelper.getNodeById("startButton").setDisable(true);
    }

    private void enableStartButton() {
        CustomSceneHelper sceneHelper = new CustomSceneHelper();
        sceneHelper.getNodeById("startButton").setDisable(false);
    }

    /**This section below is for performing game related functions. . **/

    /** Laptop **/
    //Middle of HP icon is at 1176, 203
    //Middle of Prayer icon is at 1176, 254

    /**
     * PC
     **/
    //Middle of HP icon is at 
    //Middle of Prayer icon is at 

    //0-19 are absorbs, 20-26 are overloads, 27 is rock. 
    /* Default, raw coords from laptop screen. 
    int[][] invSlots = new int[][] 
	{
		{1203, 423},{1256, 423},{1309, 423},{1361, 423},
		{1203, 470},{1256 ,470},{1309 ,470},{1361, 470},
		{1203, 515},{1256, 515},{1309, 515},{1361, 515},
		{1203, 558},{1256, 558},{1309, 558},{1361, 558},
		{1203, 602},{1256, 602},{1309, 602},{1361, 602},
		{1203, 647},{1256, 647},{1309, 647},{1361, 647},
		{1203, 693},{1256, 693},{1309, 693},{1361, 693}
	};
	*/

    //Change this according to where Pray center is. 
    //int invXOffset = 1176; 
    //int invYOffset = 245;
    //In new fixed layout RL with exp tab open
    int invXOffset = 998;
    int invYOffset = 115;

    int prayButtonX = invXOffset;
    int prayButtonY = invYOffset;

    //int HPIconX = 1166; 
    //int HPIconY = 207;

    //int hpColor = 50;
	/*
	//After subtracting by reference point. 
	int[][] absorbSlots = new int[][] 
	{
		{27+invXOffset, 179+invYOffset},   {80+invXOffset, 179+invYOffset},   {131+invXOffset, 179+invYOffset},   {185+invXOffset, 179+invYOffset},
		{27+invXOffset, 223+invYOffset},   {80+invXOffset, 223+invYOffset},   {131+invXOffset, 223+invYOffset},   {185+invXOffset, 223+invYOffset},
		{27+invXOffset, 267+invYOffset},   {80+invXOffset, 267+invYOffset},   {131+invXOffset, 267+invYOffset},   {185+invXOffset, 223+invYOffset},
		{27+invXOffset, 312+invYOffset},   {80+invXOffset, 312+invYOffset},   {131+invXOffset, 312+invYOffset},   {185+invXOffset, 312+invYOffset},
		{27+invXOffset, 358+invYOffset},   {80+invXOffset, 358+invYOffset},   {131+invXOffset, 358+invYOffset},   {185+invXOffset, 358+invYOffset}
		//{27+invXOffset, 401+invYOffset},   {80+invXOffset, 401+invYOffset}
	};
	
	int [][] overloadSlots = new int[][]
	{
	    {27+invXOffset, 401+invYOffset},   {80+invXOffset, 401+invYOffset},   {131+invXOffset, 401+invYOffset},   {185+invXOffset, 401+invYOffset},
		{27+invXOffset, 448+invYOffset},   {80+invXOffset, 448+invYOffset},   {131+invXOffset, 448+invYOffset}
	};
	
	//Can make this regular array, but just keeping it 2d for consistency sake. 
	int [][] rockCakeSlot = new int[][]
		
	{
		{185+invXOffset, 448+invYOffset}
	};
        */

    //After subtracting by reference point. New vm + RL coords
    int[][] absorbSlots = new int[][]
            {
                    {21 + invXOffset, 142 + invYOffset}, {64 + invXOffset, 142 + invYOffset}, {105 + invXOffset, 142 + invYOffset}, {146 + invXOffset, 142 + invYOffset},
                    {21 + invXOffset, 179 + invYOffset}, {64 + invXOffset, 179 + invYOffset}, {105 + invXOffset, 179 + invYOffset}, {146 + invXOffset, 179 + invYOffset},
                    {21 + invXOffset, 213 + invYOffset}, {64 + invXOffset, 213 + invYOffset}, {105 + invXOffset, 213 + invYOffset}, {146 + invXOffset, 213 + invYOffset},
                    {21 + invXOffset, 250 + invYOffset}, {64 + invXOffset, 250 + invYOffset}, {105 + invXOffset, 250 + invYOffset}, {146 + invXOffset, 250 + invYOffset},
                    {21 + invXOffset, 287 + invYOffset}, {64 + invXOffset, 287 + invYOffset}, {105 + invXOffset, 287 + invYOffset}, {146 + invXOffset, 287 + invYOffset}
                    //{21+invXOffset, 324+invYOffset},   {64+invXOffset, 324+invYOffset}
            };

    int[][] overloadSlots = new int[][]
            {
                    {21 + invXOffset, 325 + invYOffset}, {64 + invXOffset, 325 + invYOffset}, {105 + invXOffset, 325 + invYOffset}, {146 + invXOffset, 325 + invYOffset},
                    //{21+invXOffset, 359+invYOffset},   {64+invXOffset, 359+invYOffset},   {105+invXOffset, 359+invYOffset}
            };

    //Can make this regular array, but just keeping it 2d for consistency sake.
    int[][] rockCakeSlot = new int[][]

            {
                    {146 + invXOffset, 324 + invYOffset}
            };

    public void moveToPrayButton() {
        //If mouse is already within 5 pixels of prayButton Coords, then skip moveCursor.
        if (!((bot.getCurrentMousePosition(X) >= prayButtonX && bot.getCurrentMousePosition(X) <= prayButtonX + 5) && (bot.getCurrentMousePosition(Y) >= prayButtonY && bot.getCurrentMousePosition(Y) <= prayButtonY + 5))) {
            bot.moveCursorTo(prayButtonX, prayButtonY);
            bot.delay(200);
        }
    }

    public synchronized void moveToAbsorb() throws FileNotFoundException {
        log.appendToEventLogsFile("Absorb counter: " + Integer.toString(absorbCounter));
        //if (absorbCounter < 21)
        //{
        log.appendToEventLogsFile("Moving to absorb slot: " + absorbSlots[absorbCounter][0] + ", " + absorbSlots[absorbCounter][1]);
        bot.moveCursorTo(absorbSlots[absorbCounter][0], absorbSlots[absorbCounter][1]);
        //}
    }

    public synchronized void moveToOverload() throws FileNotFoundException {
        if (overloadCounter < 12) {
            try {
                log.appendToEventLogsFile("Overload counter: " + Integer.toString(overloadCounter) + ", Overload dose counter: " + Integer.toString(overloadDoseCounter));
                log.appendToEventLogsFile("Moving to overload slot: " + overloadSlots[overloadCounter][0] + ", " + overloadSlots[overloadCounter][1]);
                bot.moveCursorTo(overloadSlots[overloadCounter][0], overloadSlots[overloadCounter][1]);
            } catch (Exception e) {
                System.out.println("Overload slot out of bounds: " + e);
            }
        }
        if (overloadCounter > 6) {
            System.out.println("Overload count is 12 or above. " + overloadCounter + "Exiting. ");
            System.exit(0);
        }
    }

    public void flickPray() {
        Random random = new Random();
        bot.mouseClick();
        bot.delay(random.nextInt(500) + 500);
        bot.mouseClick();
        bot.delay(500);

    }

    public synchronized void drinkAbsorb() {
        //if (absorbCounter < 21)
        //{
        Random random = new Random();

        bot.delay(350);

        for (int i = 0; i < 4; i++) {
            bot.mouseClick();
            bot.delay(random.nextInt(1250) + 750);
        }
        absorbCounter++;
        //}
    }

    public synchronized void drinkOverload() {
        Random random = new Random();

        bot.delay(300);
        bot.mouseClick();
        overloadDoseCounter++;

        if (overloadDoseCounter >= 4) {
            overloadDoseCounter = 0;
            overloadCounter++;
        }
    }

    public void setShouldOverload(boolean bool) throws FileNotFoundException {
        shouldOverload = bool;
        log.appendToEventLogsFile("ShouldOverload set to true. ");
    }

    public void setShouldAbsorb(boolean bool) throws FileNotFoundException {
        shouldAbsorb = bool;
        log.appendToEventLogsFile("ShouldAbsorb set to true. ");
    }

    public boolean getShouldOverload() {
        return shouldOverload;
    }

    public boolean getShouldAbsorb() {
        return shouldAbsorb;
    }

}
    