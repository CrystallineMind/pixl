package main.java.com.timelessapps.javafxtemplate.app.businesslogic;

import main.java.com.timelessapps.javafxtemplate.helpers.abstractsandenums.Routine;
import main.java.com.timelessapps.javafxtemplate.helpers.abstractsandenums.Slots;
import main.java.com.timelessapps.javafxtemplate.helpers.services.CustomSceneHelper;
import main.java.com.timelessapps.javafxtemplate.helpers.services.LoggingService;
import main.java.com.timelessapps.javafxtemplate.helpers.services.RobotService;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalcifiedRoutine extends Routine {
    RobotService bot = new RobotService();
    LoggingService log = new LoggingService();
    Random random = new Random();

    // Run in main monitor, runelite exp tab showing.
    int numberToAlch = 179;
    int alchX = 1372; // 1369
    int alchY = 520; // 546

    int equippedArrowSlotX = 1334;
    int equippedArrowSlotY = 463;

    int blankInvSlotX = 1165;
    int blankInvSlotY = 416;

    int firstInvSlotX = 0;
    int firstInvSlotY = 0;

    // Superheat item red part.
    int bookIndicatorX = 1313;
    int bookIndicatorY = 518;

    volatile Boolean bookStillLoading = true;

    public CalcifiedRoutine() throws AWTException {

    }

    public void run() {
        log.appendToEventLogsFile("Starting bot routine in 3 seconds. ");

        System.out.println("Starting bot routine in 5 seconds. ");
        bot.delay(5000);

        synchronized (this) {
            try {
                disableAlchButton();
                while (running) {
                    checkIfPausedOrStopped();

                    //if (isMining()) {
                    //  if (!veinIsDepleted()) {
                    //      veinIsDepleted = veinIsDepleted();
                    //  {
                    //{ else {
                    //
                    //{
                    bot.mouseClick();
                    bot.delay(random.nextInt(754) + 643);
                    bot.mouseClick();
                    bot.delay(random.nextInt(800) + 550);

                    numberToAlch--;


                    checkIfPausedOrStopped();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(MainBotRoutine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void checkIfPausedOrStopped() throws InterruptedException {
        if (numberToAlch <= 0) {
            System.out.println("Preparing to shut down. ");
            running = false;
                        /*
			// For sleeping computer.
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
                        */
        }

        waitIfPaused();
        if (!running) {
            enableAlchButton();
        }

    }

    private void disableAlchButton() {
        CustomSceneHelper sceneHelper = new CustomSceneHelper();
        sceneHelper.getNodeById("alchButton").setDisable(true);
    }

    private void enableAlchButton() {
        CustomSceneHelper sceneHelper = new CustomSceneHelper();
        sceneHelper.getNodeById("alchButton").setDisable(false);
    }

    private void moveToAlchSpot() {

    }

    private void checkIfOnMagicScreen() {

    }

    private void checkIfArrowIsInPlace() {

    }

    private void unequiptArrowAndPutBackInPlace() {

    }

    private void switchTo(Slots slot) {
        switch (slot) {
            case INV:
                bot.keyPress(KeyEvent.VK_F5);
                bot.delay(random.nextInt(20) + 10);
                bot.keyRelease(KeyEvent.VK_F5);
                break;
            case EQUIP:
                bot.keyPress(KeyEvent.VK_F6);
                bot.delay(random.nextInt(20) + 10);
                bot.keyRelease(KeyEvent.VK_F6);
                break;
            case BOOK:
                bot.keyPress(KeyEvent.VK_F7);
                bot.delay(random.nextInt(20) + 10);
                bot.keyRelease(KeyEvent.VK_F7);
        }
    }

    private void checkIfStillCasting() {
        while (bookStillLoading) {
            if (bot.getPixelColor(bookIndicatorX, bookIndicatorY).getBlue() < 45) {
                // System.out.println("Detected. Superheat item blue value is: " +
                // bot.getPixelColor(bookIndicatorX, bookIndicatorY).getBlue());
                bookStillLoading = false;
            } else {
                // System.out.println("Not detected. Superheat item blue value is: " +
                // bot.getPixelColor(bookIndicatorX, bookIndicatorY).getBlue());
                bot.delay(500);
            }
        }
    }
}
