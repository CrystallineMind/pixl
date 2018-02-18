/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timelessapps.javafxtemplate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Max
 */
public class Main extends Application
{
    
    @Override
    public void start(Stage stage) throws Exception
    {
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("fxml/alwaysdisplayed/Main.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
    private static Stage primaryStage; // **Declare static Stage**

    private void setPrimaryStage(Stage stage) {
        Main.primaryStage = stage;
    }
    
    static public Stage getPrimaryStage() 
    {
        return Main.primaryStage;
    }
    
}
