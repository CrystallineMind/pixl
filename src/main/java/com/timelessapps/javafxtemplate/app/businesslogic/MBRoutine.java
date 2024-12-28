package main.java.com.timelessapps.javafxtemplate.app.businesslogic;

import main.java.com.timelessapps.javafxtemplate.helpers.abstractsandenums.Routine;
import main.java.com.timelessapps.javafxtemplate.helpers.services.CustomSceneHelper;
import main.java.com.timelessapps.javafxtemplate.helpers.services.LoggingService;
import main.java.com.timelessapps.javafxtemplate.helpers.services.RobotService;

import java.awt.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MBRoutine extends Routine {
    RobotService bot = new RobotService();
    LoggingService log = new LoggingService();
    Random random = new Random();

    // For arrows, remember to include more in inv than stated number, otherwise
    // stack will shrink and pixel detector may be off.
    int numberToAlch = 5000;
    int alchX = 1369;
    int alchY = 546;

    int equippedArrowSlotX = 1334;
    int equippedArrowSlotY = 463;

    int blankInvSlotX = 1165;
    int blankInvSlotY = 416;

    int firstInvSlotX = 0;
    int firstInvSlotY = 0;

    // Superheat item red part.
    int bookIndicatorX = 1313;
    int bookIndicatorY = 518;

    // Last inventory slot should have an item like fire rune to indicate whether you died or not
    int lastInvX = 1587;
    int lastInvY = 986;

    volatile Boolean bookStillLoading = true;
    int counter = 0;

    public MBRoutine() throws AWTException {

    }

    public void run() {
        log.appendToEventLogsFile("Starting bot routine in 3 seconds. ");

        System.out.println("Starting bot routine in 3 seconds. ");
        bot.delay(3000);

        synchronized (this) {
            try {
                disableMBButton();
                Random random = new Random();

                while (running) {
                    checkIfPausedOrStopped();
                    /** Start routine here. **/

                    //1587 986 rgb 146 17 9 with fire rune with runelite exp tab open
                    //1587 986 rgb 64 56 45 empty with runelite exp tab open
                    //Make sure fire rune is in last inventory slot
                    //Check if fire rune is still there. If not, then probably got pked
                    int currentRed = bot.getPixelColor(lastInvX, lastInvY).getRed();
                    int currentGreen = bot.getPixelColor(lastInvX, lastInvY).getGreen();
                    int currentBlue = bot.getPixelColor(lastInvX, lastInvY).getBlue();

                    if (currentRed < 100) {
                        System.out.println("Cannot detect item in last inv slot. Stopping. ");
                        Thread.sleep(25000);
                        break;
                    }
                    //Random sleeps for breaks
                    int randomNumber = random.nextInt(27);
                    if (randomNumber > 23) {
                        Thread.sleep(random.nextInt(30000) + 10000);
                    }

                    bot.mouseClick();
                    bot.delay(random.nextInt(200) + 500);

                    bot.mouseClick();
                    bot.delay(random.nextInt(300) + 300);

                    bot.mouseClick();
                    bot.delay(random.nextInt(240) + 800);

                    bot.mouseClick();
                    bot.delay(random.nextInt(120) + 700);

                    bot.mouseClick();
                    bot.delay(random.nextInt(250) + 240);

                    if (randomNumber > 10) {
                        bot.mouseClick();
                        bot.delay(random.nextInt(240) + 400);
                    }

                    if (randomNumber > 10) {
                        bot.mouseClick();
                        bot.delay(random.nextInt(350) + 600);
                    }


                    if (randomNumber > 10) {
                        bot.mouseClick();
                        bot.delay(random.nextInt(400) + 750);
                    }

                    System.out.println("Clicked " + counter);
                    counter++;
                    checkIfPausedOrStopped();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(MainBotRoutine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void checkIfPausedOrStopped() throws InterruptedException {
        if (counter > 400) {
            System.out.println("Counter is: " + counter + ". Stopping routine. ");
            running = false;
        }

        waitIfPaused();
        if (!running) {
            enableMBButton();
        }

    }

    private void disableMBButton() {
        try {
            CustomSceneHelper sceneHelper = new CustomSceneHelper();
            sceneHelper.getNodeById("mbButton").setDisable(true);
        } catch (Exception e) {
            System.out.println("Could not disable mb button: " + e);
        }
    }

    private void enableMBButton() {
        try {
            CustomSceneHelper sceneHelper = new CustomSceneHelper();
            sceneHelper.getNodeById("mbButton").setDisable(false);
        } catch (Exception e) {
            System.out.println("Could not enable mb button: " + e);
        }
    }

    private String getRandomLetter(int x) {
        String letter = "0";
        switch (x) {
            case 0:
                letter = "1";
                break;
            case 1:
                letter = "a";
                break;
            case 2:
                letter = "b";
                break;
            case 3:
                letter = "c";
                break;
            case 4:
                letter = "d";
                break;
            case 5:
                letter = "e";
                break;
            case 6:
                letter = "f";
                break;
            case 7:
                letter = "g";
                break;
            case 8:
                letter = "h";
                break;
            case 9:
                letter = "i";
                break;
            case 10:
                letter = "j";
                break;
            case 11:
                letter = "a";
                break;
            case 12:
                letter = "k";
                break;
            case 13:
                letter = "l";
                break;
            case 14:
                letter = "m";
                break;
            case 15:
                letter = "n";
                break;
            case 16:
                letter = "o";
                break;
            case 17:
                letter = "p";
                break;
            case 18:
                letter = "q";
                break;
            case 19:
                letter = "r";
                break;
            case 20:
                letter = "s";
                break;
            case 21:
                letter = "t";
                break;
            case 22:
                letter = "u";
                break;
            case 23:
                letter = "v";
                break;
            case 24:
                letter = "w";
                break;
            case 25:
                letter = "x";
                break;
            case 26:
                letter = "y";
                break;
            case 27:
                letter = "z";
                break;
            default:
                letter = " ";
                break;
        }
        return letter;
    }
}