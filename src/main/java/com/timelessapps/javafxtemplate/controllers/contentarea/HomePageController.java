package main.java.com.timelessapps.javafxtemplate.controllers.contentarea;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import main.java.com.timelessapps.javafxtemplate.app.businesslogic.*;
import main.java.com.timelessapps.javafxtemplate.app.supportingthreads.GlobalKeyListener;
import main.java.com.timelessapps.javafxtemplate.helpers.DataCoordinates;
import main.java.com.timelessapps.javafxtemplate.helpers.services.LoggingService;
import main.java.com.timelessapps.javafxtemplate.helpers.services.RobotService;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {
    LoggingService log = new LoggingService();
    RobotService bot;
    private static boolean hasStarted = false;

    public HomePageController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!hasStarted) {
            log.appendToEventLogsFile("ApplicationStarted. ");
            log.appendToApplicationLogsFile("ApplicationStarted. ");
        }
        hasStarted = true;
    }

    //For NMZ
    @FXML
    public void startApplication() {
        DataCoordinates.configureDataCoords(123, true);
    }

    @FXML
    public void startAlchRoutine(MouseEvent event) throws InterruptedException, AWTException {
        HighAlchRoutine highAlchRoutine = new HighAlchRoutine();
        highAlchRoutine.setDaemon(true);
        highAlchRoutine.start();

        GlobalKeyListener globalKeyListener = new GlobalKeyListener(highAlchRoutine);
        globalKeyListener.setDaemon(true);
        globalKeyListener.start();

        // botRoutine.join();
        // System.out.println("Remember to re-active button. ");
    }

    @FXML
    public void startSplashRoutine(MouseEvent event) throws InterruptedException, AWTException {
        SplashRoutine splashRoutine = new SplashRoutine();
        splashRoutine.setDaemon(true);
        splashRoutine.start();

        GlobalKeyListener globalKeyListener = new GlobalKeyListener(splashRoutine);
        globalKeyListener.setDaemon(true);
        globalKeyListener.start();

        // botRoutine.join();
        // System.out.println("Remember to re-active button. ");
    }

    @FXML
    public void startMBRoutine(MouseEvent event) throws InterruptedException, AWTException {
        MBRoutine mbRoutine = new MBRoutine();
        mbRoutine.setDaemon(true);
        mbRoutine.start();

        GlobalKeyListener globalKeyListener = new GlobalKeyListener(mbRoutine);
        globalKeyListener.setDaemon(true);
        globalKeyListener.start();

        // botRoutine.join();
        // System.out.println("Remember to re-active button. ");
    }

    @FXML
    public void startHumidifyRoutine(MouseEvent event) throws InterruptedException, AWTException {
        HumidifyRoutine humidifyRoutine = new HumidifyRoutine();
        humidifyRoutine.setDaemon(true);
        humidifyRoutine.start();

        GlobalKeyListener globalKeyListener = new GlobalKeyListener(humidifyRoutine);
        globalKeyListener.setDaemon(true);
        globalKeyListener.start();

        // botRoutine.join();
        // System.out.println("Remember to re-active button. ");
    }

    @FXML
    public void startGrandExchangeRoutine(MouseEvent event) throws InterruptedException, AWTException {
        GrandExchangeRoutine grandExchangeRoutine = new GrandExchangeRoutine("");
        grandExchangeRoutine.setDaemon(true);
        grandExchangeRoutine.start();

        GlobalKeyListener globalKeyListener = new GlobalKeyListener(grandExchangeRoutine);
        globalKeyListener.setDaemon(true);
        globalKeyListener.start();

        // botRoutine.join();
        // System.out.println("Remember to re-active button. ");
    }

    @FXML
    public void startMeleeRoutine(MouseEvent event) throws InterruptedException, AWTException {
        MeleeRoutine meleeRoutine = new MeleeRoutine("");
        meleeRoutine.setDaemon(true);
        meleeRoutine.start();

        GlobalKeyListener globalKeyListener = new GlobalKeyListener(meleeRoutine);
        globalKeyListener.setDaemon(true);
        globalKeyListener.start();

        // botRoutine.join();
        // System.out.println("Remember to re-active button. ");
    }

    @FXML
    public void startPatrolRoutine(MouseEvent event) throws InterruptedException, AWTException {
        DC_PatrolRoutine patrolRoutine = new DC_PatrolRoutine();
        patrolRoutine.setDaemon(true);
        patrolRoutine.start();

        GlobalKeyListener globalKeyListener = new GlobalKeyListener(patrolRoutine);
        globalKeyListener.setDaemon(true);
        globalKeyListener.start();

        // botRoutine.join();
        // System.out.println("Remember to re-active button. ");
    }
}
