/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.timelessapps.javafxtemplate;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.com.timelessapps.javafxtemplate.app.businesslogic.*;
import main.java.com.timelessapps.javafxtemplate.app.supportingthreads.GlobalKeyListener;
import main.java.com.timelessapps.javafxtemplate.helpers.ImageDataProcessor;
import main.java.com.timelessapps.javafxtemplate.helpers.OCR.RSImageReader;
import main.java.com.timelessapps.javafxtemplate.helpers.coords.RSCoordinates;

import java.awt.*;

public class Main extends Application {
    private static Stage stage;
    private static Scene scene;
    private final ImageDataProcessor imageDataProcessor = new ImageDataProcessor();

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.UNDECORATED);
        setPrimaryStage(stage);
        setPrimaryScene(scene);
        Parent root = FXMLLoader.load(getClass().getResource("view/fxml/alwaysdisplayed/Main.fxml"));

        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
        imageDataProcessor.screenCapture();

    }

    //If no argument, launch with JavaFX UI, else just do the routine. 
    public static void main(String[] args) {
        if (args.length == 0) {
            launch(args);
        } else {
            try {
                switch (args[0]) {
                    case "snmz": {
                        System.out.println("Starting SNMZ routine");
                        SimpleNMZRoutine simpleNMZRoutine = new SimpleNMZRoutine(); //pass and gp

                        simpleNMZRoutine.setDaemon(true);
                        simpleNMZRoutine.start();

                        GlobalKeyListener globalKeyListener = new GlobalKeyListener(simpleNMZRoutine);
                        globalKeyListener.setDaemon(true);
                        globalKeyListener.start();

                        simpleNMZRoutine.join();
                        System.out.println("Completed SNMZ routine, shutting down. ");
                        System.exit(0);
                    }
                    case "ge": {
                        System.out.println("Starting GE routine");
                        GrandExchangeRoutine grandExchangeRoutine = new GrandExchangeRoutine(args[1]); //pass and gp

                        grandExchangeRoutine.setDaemon(true);
                        grandExchangeRoutine.start();

                        GlobalKeyListener globalKeyListener = new GlobalKeyListener(grandExchangeRoutine);
                        globalKeyListener.setDaemon(true);
                        globalKeyListener.start();

                        grandExchangeRoutine.join();
                        System.out.println("Completed GE routine, shutting down. ");
                        System.exit(0);
                    }
                    case "melee": {
                        RSImageReader rsir = new RSImageReader();
                        RSCoordinates rsc = new RSCoordinates();
                        Color black = new Color(0, 0, 0);

	        		/*
	        		System.out.println("Question1 Text: " + rsir.getRSQuestionsText(rsc.question1Of2(), black));
	        		System.out.println("Question2 Text: " + rsir.getRSQuestionsText(rsc.question2Of2(), black));
	        		System.out.println("==");
	        		System.out.println("Question1 Text: " + rsir.getRSQuestionsText(rsc.question1Of3(), black));
	        		System.out.println("Question2 Text: " + rsir.getRSQuestionsText(rsc.question2Of3(), black));
	        		System.out.println("Question3 Text: " + rsir.getRSQuestionsText(rsc.question3Of3(), black));
	        		*/
                        //MeleeRoutine meleeRoutine = new MeleeRoutine(args[1]); //pass and gp
                        //meleeRoutine.test();

                        System.out.println("Starting melee routine");
                        MeleeRoutine meleeRoutine = new MeleeRoutine(args[1]); //pass and gp

                        meleeRoutine.setDaemon(true);
                        meleeRoutine.start();

                        GlobalKeyListener globalKeyListener = new GlobalKeyListener(meleeRoutine);
                        globalKeyListener.setDaemon(true);
                        globalKeyListener.start();

                        meleeRoutine.join();
                        System.out.println("Completed melee routine, shutting down. ");
                        System.exit(0);

                    }
                }
                if (args[0].contentEquals("patrol")) {
                    System.out.println("Starting patrol routine");
                    DC_PatrolRoutine patrolRoutine = new DC_PatrolRoutine(); //pass and gp
                    patrolRoutine.setDaemon(true);
                    patrolRoutine.start();

                    GlobalKeyListener globalKeyListener = new GlobalKeyListener(patrolRoutine);
                    globalKeyListener.setDaemon(true);
                    globalKeyListener.start();

                    patrolRoutine.join();
                    System.out.println("Completed splash routine, shutting down. ");
                    System.exit(0);
                }
                if (args[0].contentEquals("splash")) {
                    System.out.println("Starting splash routine");
                    SplashRoutine splashRoutine = new SplashRoutine(); //pass and gp
                    splashRoutine.setDaemon(true);
                    splashRoutine.start();

                    GlobalKeyListener globalKeyListener = new GlobalKeyListener(splashRoutine);
                    globalKeyListener.setDaemon(true);
                    globalKeyListener.start();

                    splashRoutine.join();
                    System.out.println("Completed splash routine, shutting down. ");
                    System.exit(0);
                }
            } catch (Exception e) {
                System.out.println("Could not complete routine normally, exiting: " + e);
                System.exit(1);
            }
        }
    }

    private void setPrimaryStage(Stage stage) {
        Main.stage = stage;
    }

    public static Stage getMainStage() {
        return Main.stage;
    }

    private void setPrimaryScene(Scene scene) {
        Main.scene = scene;
    }

    public static Scene getMainScene() {
        return Main.scene;
    }
}
