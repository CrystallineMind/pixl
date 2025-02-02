package com.xerxes.javafxtemplate.controllers.alwaysdisplayed;

import com.xerxes.javafxtemplate.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TitleBarController implements Initializable {
    @FXML
    private AnchorPane titleBar;

    @FXML
    private ImageView closeButton, minimizeButton, closeButtonFilled, minimizeButtonFilled;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private void titleBarClicked(MouseEvent event) {

    }

    @FXML
    private void titleBarPressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    private void titleBarDragged(MouseEvent event) throws IOException {
        //Makes the title bar movable when dragging it. 
        Stage stage = Main.getMainStage();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    @FXML
    private void closeButtonClicked(MouseEvent event) {
        System.exit(0);
    }

    //Adds highlighting effect for close button at top right. 
    @FXML
    private void closeButtonEntered(MouseEvent event) {
        closeButtonFilled.setVisible(true);
    }

    @FXML
    public void closeButtonExited(MouseEvent event) {
        closeButtonFilled.setVisible(false);
    }

    @FXML
    private void minimizeButtonClicked(MouseEvent event) {
        Stage stage = Main.getMainStage();
        stage.setIconified(true);
    }

    //Adds highlighting effect for minimize button at top right. 
    @FXML
    private void minimizeButtonEntered(MouseEvent event) {
        minimizeButtonFilled.setVisible(true);
    }

    @FXML
    public void minimizeButtonExited(MouseEvent event) {
        minimizeButtonFilled.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
