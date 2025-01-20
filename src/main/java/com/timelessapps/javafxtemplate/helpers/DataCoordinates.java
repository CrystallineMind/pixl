package main.java.com.timelessapps.javafxtemplate.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DataCoordinates {

    private static final boolean AUTO_TOGGLE_DATATOCOLOR = false; // Set to true if needed
    private static final String BASE_DIR = ""; // Set your base directory path
    private static final Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public static CompletableFuture<Void> configureDataCoords(int metaData, boolean override) {
        //-16276166
        //-310544483
        if (override) {
            metaData = Integer.parseInt(getPixelColor(1, 1), 16); // Replace with actual pixel color reading method
        }

        final String metadataString = "501050";


        // Number of data frames we are going to be reading from.
        int dataFrames = Integer.parseInt(metadataString.substring(metadataString.length() - 2));
        // Number of frame rows
        int frameRows = 1;
                //Integer.parseInt(metadataString.substring(metadataString.length() - 4, metadataString.length() - 2));
        // Size of data frames (with multiplier correction)
        int frameSize = 5;
                //Integer.parseInt(metadataString.substring(metadataString.length() - 6, metadataString.length() - 4)) * 2;
        // Number of frame columns
        int frameCols = (int) Math.ceil((double) dataFrames / frameRows);
        // Bitmap dimensions
        int bitMapX1 = 0, bitMapY1 = 0, bitMapX2 = frameCols * frameSize, bitMapY2 = frameRows * frameSize;

        List<Map<String, Integer>> dataPointCoordinateArray = new ArrayList<>();
        // Assign the first coordinate
        Map<String, Integer> firstPoint = new HashMap<>();
        firstPoint.put("x", bitMapX1 + 1);
        firstPoint.put("y", bitMapY1 + 1);
        dataPointCoordinateArray.add(firstPoint);

        // Capture the screen bitmap
        BufferedImage dataBitmap = captureScreen(bitMapX1, bitMapY1, bitMapX2, bitMapY2);

        Rectangle rectangle = new Rectangle(bitMapX1, bitMapY1, bitMapX2, bitMapY2);
        //robimy screenshot z utworzonego obszaru
        BufferedImage screen = robot.createScreenCapture(rectangle);
        try {
            ImageIO.write(screen, "jpg", new File("ble.jpg"));
        } catch (IOException e) {
            System.err.println("Błąd zapisu obrazu");
            e.printStackTrace();
        }

        if (bitMapY2 > 1000 || bitMapX2 > 1000) {
            return CompletableFuture.failedFuture(new Exception("Bitmap size too large"));
        }

        for (int i = 1; i <= dataFrames; i++) {
            Map<String, Integer> temp = new HashMap<>();
            temp.put("x", k);
            temp.put("y", j);
            dataPointCoordinateArray.add(temp);
            // Breaking out of loops once we find the coordinate

        }

        // Loops through total number of data frames excluding the meta data frame
//        for (int i = 1; i <= dataFrames; i++) {
//            // Loops through every pixel of bitmap horizontally
//            for (int k = 0; k < bitMapX2; k++) {
//                // Loops through every pixel of bitmap vertically
//                for (int j = 0; j < bitMapY2; j++) {
//                    String colorHex = getColorAt(dataBitmap, k, j);
//                    int x = Integer.parseInt(colorHex, 16);
//                    if (x == i) {
//                        Map<String, Integer> temp = new HashMap<>();
//                        temp.put("x", k);
//                        temp.put("y", j);
//                        dataPointCoordinateArray.add(temp);
//                        // Breaking out of loops once we find the coordinate
//                        j = Integer.MAX_VALUE;
//                        k = Integer.MAX_VALUE;
//                    }
//
//                    // Check if bitmap is too large
//                    if (bitMapY2 > 1000 || bitMapX2 > 1000) {
//                        return CompletableFuture.failedFuture(new Exception("Bitmap size too large"));
//                    }
//                }
//            }
//        }

        // Save the data coordinates to a JSON file
        if (dataPointCoordinateArray.size() > 10) {
            try {
                writeJsonToFile(dataPointCoordinateArray);
                System.out.println("New frame coordinates saved.");

                if (AUTO_TOGGLE_DATATOCOLOR) {
                    toggleDataToColor();
                    System.out.println("DataToColor display initialized.");
                }
                return CompletableFuture.completedFuture(null);
            } catch (IOException e) {
                return CompletableFuture.failedFuture(e);
            }
        } else {
            System.err.println("ERROR: Game Client is not open, or configuration is incorrect.");
            return CompletableFuture.failedFuture(new Exception("Insufficient data points"));
        }
    }

    // Simulating reading a pixel color (actual implementation needed)
    private static String getPixelColor(int x, int y) {
        Color color = robot.getPixelColor(x, y);
        return String.valueOf(color.getRGB());
    }

    // Capture screen using Java Robot
    private static BufferedImage captureScreen(int x, int y, int width, int height) {
        try {
            Robot robot = new Robot();
            return robot.createScreenCapture(new Rectangle(x, y, width, height));
        } catch (AWTException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Convert pixel color to hex format (RGB to hex)
    private static String getColorAt(BufferedImage img, int x, int y) {
        int rgb = img.getRGB(x, y);
        return Integer.toHexString(rgb & 0xFFFFFF); // Ensure hex format without alpha channel
    }

    // Write the list of data points to a JSON file
    private static void writeJsonToFile(List<Map<String, Integer>> dataPointCoordinateArray) throws IOException {
        File file = new File(BASE_DIR + "/frameCoordinates.json");
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath())) {
            ObjectMapper objectMapper = new ObjectMapper();
            writer.write(objectMapper.writeValueAsString(dataPointCoordinateArray));
        }
    }

    // Simulate the DataToColor keypress commands
    private static void toggleDataToColor() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.keyPress(KeyEvent.VK_SLASH);  // For '/dc'
            robot.keyRelease(KeyEvent.VK_SLASH);
            robot.keyPress(KeyEvent.VK_D);
            robot.keyRelease(KeyEvent.VK_D);
            robot.keyPress(KeyEvent.VK_C);
            robot.keyRelease(KeyEvent.VK_C);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        configureDataCoords(123456789, true).join();
    }
}
