/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timelessapps.javafxtemplate.controllers.contentarea;

import com.timelessapps.javafxtemplate.services.RobotService;
import java.awt.AWTException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

public class HomePageController implements Initializable
{
    RobotService bot;
    
    public HomePageController()
    {
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        
    }    
    
    @FXML
    public void startApplication(MouseEvent event) 
    {
        Runnable startTask = new Runnable()
        {
            public void run() 
            {
                try {bot = new RobotService();} catch (AWTException ex) {Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);}
                
                bot.delay(2000);
                bot.type("Test test TEST 123 !@#", 0);
            }
        };
        
        Thread t1 = new Thread(startTask);
        t1.start();

    }
    
}
