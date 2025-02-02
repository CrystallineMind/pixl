package com.xerxes.javafxtemplate.app.businesslogic;

import com.xerxes.javafxtemplate.app.supportingthreads.AttackTimer;
import com.xerxes.javafxtemplate.app.verify.VerifyGrandExchange;
import com.xerxes.javafxtemplate.helpers.OCR.RSImageReader;
import com.xerxes.javafxtemplate.helpers.abstractsandenums.Routine;
import com.xerxes.javafxtemplate.helpers.coords.RSCoordinates;
import com.xerxes.javafxtemplate.helpers.services.CustomSceneHelper;
import com.xerxes.javafxtemplate.helpers.services.LoggingService;
import com.xerxes.javafxtemplate.helpers.services.RobotService;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.xerxes.javafxtemplate.helpers.abstractsandenums.Duration.MEDIUM;
import static com.xerxes.javafxtemplate.helpers.abstractsandenums.Duration.SHORT;


public class MeleeRoutine extends Routine {
    private RobotService bot = new RobotService();
    private LoggingService log = new LoggingService();
    private Random random = new Random();
    private RSCoordinates rsc = new RSCoordinates();
    private RSImageReader rsir = new RSImageReader();
    private VerifyGrandExchange verifyGE = new VerifyGrandExchange();
    private String pass = "";
    private Color hpArea = new Color(0, 0, 0);
    private int cursorMovementStyle = 5;
    private static boolean isResettingAggro = false;
    private boolean shouldResetAggro = true;

    public MeleeRoutine(String pass) throws AWTException {
        this.pass = pass;
    }

    public void run() {
        log.appendToEventLogsFile("Starting bot routine in 3 seconds. ");
        log.appendToApplicationLogsFile("Starting bot routine in 3 seconds. ");
        System.out.println("Starting bot routine in 3 seconds. ");
        bot.delay(3000);

        synchronized (this) {
            try {
                disableMeleeButton();
				/*
				initialize();
				verifyGE.loginScreenIsLoaded();
				chooseWorld();
				login();
				verifyGE.clickHereToPlayIsPresent();
				clickClickHereToPlayButton();
				Thread.sleep(5000); //Add verify chat box loaded
				//scrollIn();
				tiltScreenUp();
				//Types every once in a while to avoid timeout
				 */
                AttackTimer attackTimer = new AttackTimer(); //300800
                attackTimer.setDaemon(true);
                attackTimer.start();

                while (running) {
                    checkIfPausedOrStopped();
                    /** Start routine here. **/

                    //Check if hp is enough. Currently checking if halfway.
                    hpArea = bot.getPixelColor(rsc.middleHPX(), rsc.middleHPY()); //Halfway point for hp bar.
                    if (hpArea.getRed() < 40) // Red value is higher if the hp bar is filled, less than 40 is empty.
                    {
                        System.out.println("Red part in HP area not detected, stopping routine. ");
                        return;
                    }

                    isResettingAggro = true;
                    Thread.sleep(3000);
                    goLeftDown();
                    goLeftDown();
                    //goLeftDown();
                    openDoorOnLeft1();
                    Thread.sleep(1000);
                    goLeftDown();
                    Thread.sleep(1000);
                    openDoorOnLeft2();
                    answerQuestion();
                    goFarLeft();
                    Thread.sleep(8000);
                    goFarLeft();
                    Thread.sleep(10000);
                    goFarRight();
                    Thread.sleep(10000);
                    goFarRight(); //Maybe go medium right
                    Thread.sleep(8000);
                    openDoorOnRight1();
                    Thread.sleep(1000);
                    runRightDown();
                    Thread.sleep(1000);
                    openDoorOnRight2();
                    answerQuestion();
                    goTopRight();
                    isResettingAggro = false;

                    TimeUnit.MINUTES.sleep(10);
                    Thread.sleep(random.nextInt(10000) + 10000);
                    //Thread.sleep(random.nextInt(700000) + 100000);

                    if (attackTimer.getState() == Thread.State.TERMINATED) {
                        System.out.println("Attack timer has stopped. Exiting. ");
                        return;
                    }
                    checkIfPausedOrStopped();
                    /** End routine here. **/
                }
            } catch (Exception ex) {
                System.out.println("Bot could not complete routine: " + ex);
                Logger.getLogger(MainBotRoutine.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }
    }

    @Override
    public void checkIfPausedOrStopped() throws InterruptedException {
        try {
            waitIfPaused();
            if (!running) {
                enableMeleeButton();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void disableMeleeButton() {
        try {
            CustomSceneHelper sceneHelper = new CustomSceneHelper();
            sceneHelper.getNodeById("meleeButton").setDisable(true);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void enableMeleeButton() {
        try {
            CustomSceneHelper sceneHelper = new CustomSceneHelper();
            sceneHelper.getNodeById("meleeButton").setDisable(false);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void initialize() throws Exception {
        System.out.println("Initializing. ");
        try {
            int y = RSCoordinates.getInitialOffsetY();
            int x = RSCoordinates.getInitialOffsetX(y);
            RSCoordinates.setOffsetX(x);
            RSCoordinates.setOffsetY(y);
            log.appendToApplicationLogsFile("Detected game area, starting coordinates (x,y): " + x + ", " + y);
        } catch (Exception e) {
            System.out.println("Cannot initialize Melee routine: " + e);
            throw e;
        }
    }

    private void chooseWorld() {
        System.out.println("Choosing world. ");
        log.appendToApplicationLogsFile("Choosing world. ");
        try {
            bot.delay(500);
            bot.moveCursorTo(rsc.changeWorldButtonX(), rsc.changeWorldButtonY());
            bot.delay(500);
            bot.mouseClick();
            bot.delay(500);
            bot.moveCursorTo(rsc.freeWorldButtonX(), rsc.freeWorldButtonY());
            bot.delay(500);
            bot.mouseClick();
        } catch (Exception e) {
            System.out.println("Could not choose world Melee routine: " + e);
            log.appendToApplicationLogsFile("Could not choose world Melee routine: " + e);
            throw e;
        }
    }

    private void login() {
        System.out.println("Logging in. ");
        log.appendToApplicationLogsFile("Logging in. ");
        try {
            bot.delay(500);
            bot.moveCursorTo(rsc.existingUserButtonX(), rsc.existingUserButtonY());
            bot.mouseClick();
            bot.delay(500);
            bot.type(pass, random.nextInt(15) + 35);
            bot.delay(500);
            bot.keyPress(KeyEvent.VK_ENTER);
            bot.delay(SHORT);
            bot.keyRelease(KeyEvent.VK_ENTER);
        } catch (Exception e) {
            System.out.println("Could not login Melee routine: " + e);
            log.appendToApplicationLogsFile("Could not login Melee routine: " + e);
            throw e;
        }
    }

    private void clickClickHereToPlayButton() {
        System.out.println("Clicking here to play");
        log.appendToApplicationLogsFile("Clicking play button. ");
        try {
            bot.delay(500);
            bot.moveCursorTo(rsc.clickHereToPlayButtonX(), rsc.clickHereToPlayButtonY());
            bot.mouseClick();
            bot.delay(500);
        } catch (Exception e) {
            System.out.println("Could not click here to play Melee routine: " + e);
            log.appendToApplicationLogsFile("Could not click here to play Melee routine: " + e);
            throw e;
        }
    }

    /**
     * This function zooms in on the characters so that objects are easier to click.
     */
    private void scrollIn() {
        log.appendToApplicationLogsFile("Setting zoom... ");
        try {
            for (int i = 0; i < 10; i++) {
                bot.mouseWheel(-1);
                bot.delay(SHORT);
            }
            bot.delay(MEDIUM);
        } catch (Exception e) {
            System.out.println("Could not scroll in: " + e);
            log.appendToApplicationLogsFile("Could not scroll in: " + e);
            throw e;
        }
    }

    private void tiltScreenUp() {
        log.appendToApplicationLogsFile("Setting screen tilt... ");
        try {
            bot.delay(MEDIUM);
            bot.keyPress(KeyEvent.VK_UP);
            bot.delay(MEDIUM);
            bot.delay(MEDIUM);
            bot.keyRelease(KeyEvent.VK_UP);
            bot.delay(MEDIUM);
        } catch (Exception e) {
            System.out.println("Could not set screen tilt: " + e);
            log.appendToApplicationLogsFile("Could not set screen tilt: " + e);
            throw e;
        }
    }

    public static boolean getIsResettingAggro() {
        return isResettingAggro;
    }

    private void goLeftDown() throws InterruptedException {
        bot.accuratelyMoveCursor(634 + rsc.getOffsetX(), 73 + rsc.getOffsetY()); //down left 1213, 116
        Thread.sleep(750);
        bot.mouseClick();
        Thread.sleep(2000);
    }

    public void answerQuestion() throws Exception {
        log.appendToApplicationLogsFile("Answering question. ");
        Color black = new Color(0, 0, 0);
        String[] possibleAnswers1of2 = {"Talk to any banker.", "Tlothing, it's a fake.", "Through account settings on oldschool.runescape.com.", "Report the incident and do not click any fnks."};
        String[] possibleAnswers2of2 = {"ice and reset my passwi", "To.", "Report the incident and do not click any finks"};

        String[] possibleAnswers1of3 = {"Decline the offer and repore thar player.", "Me.", "rake my gold for your ownl Repovred!", "Ser up @ step authentication with my emafl provider.", "Don't give them the information and send an Abuse reporr'.", "Thvus scan my device then change muy password."};
        String[] possibleAnswers2of3 = {"vt the stream as a scam", "Report the player for phishing.", "Only on the Old School RuneSecape website", "type in my password backwards and report the player", "Authenticator and two-step login on my registered email", "To.", "on't give out your password to anyone. Tlot even close friend", "Tlo way! I'm veporti", "Don't give them my password.", "Don't share your information and report the player."};
        String[] possibleAnswers3of3 = {"Palitely tell them no and then use the", "Delece it - it's a fakel", "To, you should never allow anyone to level your account.", "not visit the website and vepove the player who messaged yor", "Don't cell them anyrhing and click", "Read the text and follow the advice given", "The bivehday of a famous person ov evene", "To, you should never buy an accounr.", "Use the Account Recovery System.", "Thobody."};

        Thread.sleep(2000);
        bot.type(" ");
        Thread.sleep(2000);
        bot.type(" ");
        Thread.sleep(2000);

        String answer1of3 = rsir.getRSQuestionsText(rsc.question1Of3(), black).trim();
        String answer2of3 = rsir.getRSQuestionsText(rsc.question2Of3(), black).trim();
        String answer3of3 = rsir.getRSQuestionsText(rsc.question3Of3(), black).trim();
        String answer1of2 = rsir.getRSQuestionsText(rsc.question1Of2(), black).trim();
        String answer2of2 = rsir.getRSQuestionsText(rsc.question2Of2(), black).trim();

        //Doing in this order because they are the most comon ones to have answers.
        if (stringContainsItemFromList(answer3of3, possibleAnswers3of3)) {
            bot.type("3");
        } else if (stringContainsItemFromList(answer2of3, possibleAnswers2of3)) {
            bot.type("2");
        } else if (stringContainsItemFromList(answer2of2, possibleAnswers2of2)) {
            bot.type("2");
        } else if (stringContainsItemFromList(answer1of2, possibleAnswers1of2)) {
            bot.type("1");
        } else if (stringContainsItemFromList(answer1of3, possibleAnswers1of3)) {
            bot.type("1");
        } else {
            log.appendToApplicationLogsFile("Could not answer question. Possible Answers:  ");
            log.appendToApplicationLogsFile(answer1of3);
            log.appendToApplicationLogsFile(answer2of3);
            log.appendToApplicationLogsFile(answer3of3);
            log.appendToApplicationLogsFile(answer1of2);
            log.appendToApplicationLogsFile(answer2of2);
        }
        Thread.sleep(2000);
        //Thread.sleep(2000);
        bot.type(" ");
        Thread.sleep(2000);
    }

    private void openDoorOnLeft1() throws InterruptedException {
        Thread.sleep(1500);
        bot.moveCursorTo(162 + rsc.getOffsetX(), 162 + rsc.getOffsetY()); //down left
        Thread.sleep(750);
        bot.mouseClick();
        Thread.sleep(1500);
    }

    private void openDoorOnLeft2() throws InterruptedException {
        Thread.sleep(1500);
        bot.moveCursorTo(223 + rsc.getOffsetX(), 181 + rsc.getOffsetY()); //down left
        Thread.sleep(750);
        bot.mouseClick();
        Thread.sleep(1500);
    }

    private void goFarLeft() throws InterruptedException {
        Thread.sleep(1500);
        bot.accuratelyMoveCursor(599 + rsc.getOffsetX(), 70 + rsc.getOffsetY()); //down left
        Thread.sleep(750);
        bot.mouseClick();
        Thread.sleep(1500);
    }

    private void goFarRight() throws InterruptedException {
        Thread.sleep(1500);
        bot.accuratelyMoveCursor(699 + rsc.getOffsetX(), 69 + rsc.getOffsetY()); //down left
        Thread.sleep(750);
        bot.mouseClick();
        Thread.sleep(1500);
    }

    private void openDoorOnRight1() throws InterruptedException {
        Thread.sleep(1500);
        bot.accuratelyMoveCursor(353 + rsc.getOffsetX(), 178 + rsc.getOffsetY()); //down left
        Thread.sleep(750);
        bot.mouseClick();
        Thread.sleep(1500);
    }

    private void openDoorOnRight2() throws InterruptedException {
        Thread.sleep(1500);
        bot.accuratelyMoveCursor(298 + rsc.getOffsetX(), 178 + rsc.getOffsetY()); //down left
        Thread.sleep(750);
        bot.mouseClick();
        Thread.sleep(1500);
    }

    private void runRightDown() throws InterruptedException {
        Thread.sleep(1500);
        bot.accuratelyMoveCursor(656 + rsc.getOffsetX(), 70 + rsc.getOffsetY()); //down left
        Thread.sleep(750);
        bot.mouseClick();
        Thread.sleep(1500);
    }

    private void goTopRight() throws InterruptedException {
        Thread.sleep(1500);
        bot.accuratelyMoveCursor(655 + rsc.getOffsetX(), 50 + rsc.getOffsetY()); //down left was 661 and 46
        Thread.sleep(750);
        bot.mouseClick();
        Thread.sleep(1500);
    }

    public void test() {
        try {
            goTopRight();
            Thread.sleep(3000);
            goLeftDown();
            goLeftDown();
            goLeftDown();
            openDoorOnLeft1();
            Thread.sleep(1000);
            goLeftDown();
            Thread.sleep(1000);
            openDoorOnLeft2();
            answerQuestion();
            goFarLeft();
            Thread.sleep(10000);
            goFarLeft();
            Thread.sleep(10000);
            goFarRight();
            Thread.sleep(10000);
            goFarRight(); //Maybe go medium right
            Thread.sleep(10000);
            openDoorOnRight1();
            Thread.sleep(1000);
            runRightDown();
            Thread.sleep(1000);
            openDoorOnRight2();
            answerQuestion();
            goTopRight();
        } catch (Exception e) {
            System.out.println("Testing failed: " + e);
        }
    }

    public static boolean stringContainsItemFromList(String inputStr, String[] items) {
        return Arrays.stream(items).parallel().anyMatch(inputStr::contains);
    }

    public void setShouldResetAggro(boolean bool) {
        shouldResetAggro = bool;
        log.appendToApplicationLogsFile("Setting should reset aggro to: " + bool);
    }
}
