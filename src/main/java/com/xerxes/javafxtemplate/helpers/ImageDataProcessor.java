package com.xerxes.javafxtemplate.helpers;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageDataProcessor {
    private Robot robot;

    public ImageDataProcessor() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.err.println("Co ten robot wyprawia?!");
            e.printStackTrace();
        }
    }

    public void screenCapture() {
        //pobieramy rozmiar ekranu i tworzymy Rectangle
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle rectangle = new Rectangle(dimension);
        //robimy screenshot z utworzonego obszaru
        BufferedImage screen = robot.createScreenCapture(rectangle);
        try {
            ImageIO.write(screen, "jpg", new File("screenshot.jpg"));
        } catch (IOException e) {
            System.err.println("Błąd zapisu obrazu");
            e.printStackTrace();
        }
    }
}
