package com.xerxes.javafxtemplate.controllers.alwaysdisplayed;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class TopMenuPaneController implements Initializable {

    @FXML
    private Label pageNameLabel;

    @FXML
    public void changePageNameLabel(String pageName) {
        pageNameLabel.setText(pageName);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}