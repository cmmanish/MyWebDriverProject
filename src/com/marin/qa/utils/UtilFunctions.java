package com.marin.qa.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.net.UnknownHostException;
import java.net.InetAddress;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class UtilFunctions {

    static Logger log = Logger.getLogger(UtilFunctions.class);
    static String WEBDRIVER_CHROME_PROPERTY = "webdriver.chrome.driver";

    public static WebDriver getWebDriver() {
        // Allows switching the browser uses at a global level.
        log.info("By default, we used FireFox broswer");
        return new FirefoxDriver();
    }

    static RandomAccessFile fl = null;

    public static String setTodayDate() {
        DateFormat dateFormat1 = new SimpleDateFormat("M/d/yy");
        Date date = new Date();

        return dateFormat1.format(date);
    }

    public static String setDateTimeId() {
        DateFormat dateFormat2 = new SimpleDateFormat("MMddyyyyHHmm");
        Date date = new Date();

        return dateFormat2.format(date);
    }

    public static void createFile() {
        try {
            String dirName = "C:\\niraj\\SeleniumTestResults";
            File dir = new File(dirName);
            if (!dir.exists()) {
                new File("C:\\niraj\\SeleniumTestResults").mkdir();
            }
            else {
                System.out.println("Directory exists");
            }
            fl = new RandomAccessFile(dirName + "\\TestSEMWizardsResult-" + setDateTimeId() + ".html", "rw");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openHTML() {
        try {
            fl.writeBytes("<HTML>");
            fl.writeBytes("<TITLE>Test results</TITLE>");
            fl.writeBytes("<BODY>");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void closeHTML() {
        try {
            fl.writeBytes("</BODY>");
            fl.writeBytes("</HTML>");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void openTable() {
        try {
            fl.writeBytes("<TABLE align = \"center\" border=2 cellspacing=\"3\">");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void createScreenshot(String outFileName) throws Exception {
        try {
            // determine current screen size
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension screenSize = toolkit.getScreenSize();
            Rectangle screenRect = new Rectangle(screenSize);
            // create screen shot
            Robot robot = new Robot();
            BufferedImage image = robot.createScreenCapture(screenRect);
            // save captured image to PNG file
            ImageIO.write(image, "jpeg", new File(outFileName + ".jpeg"));
            // give feedback
            System.out.println("Saved screen shot (" + image.getWidth() + " x " + image.getHeight() + " pixels) to file \"" + outFileName + "\".");
            addScreenshotToHTML(outFileName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeTable() {
        try {
            fl.writeBytes("</TABLE>");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void writeData(String result, String color) {
        try {
            // fl.writeBytes(result + "\r\n");
            fl.writeBytes("<TR>");
            fl.writeBytes("<TD width=\"200\">" + result + "</TD>");
            fl.writeBytes("<TD  width=\"200\" bgcolor=\"" + color + "\"></TD>");
            fl.writeBytes("</TR>");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void addScreenshotToHTML(String filename) {
        try {
            // fl.writeBytes(result + "\r\n");
            fl.writeBytes("<TR>");
            fl.writeBytes("<TD width=\"200\">Screenshot</TD>");
            fl.writeBytes("<TD  width=\"200\" ><A HREF=\"" + filename + "\"><IMG SRC=\"" + filename + "\" width=\"200\" ALT=\"Idocs Guide to HTML\"></A></TD>");
            fl.writeBytes("</TR>");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void writeText(String text) {
        try {
            // fl.writeBytes(result + "\r\n");
            fl.writeBytes("<H3 align=\"center\"><FONT FACE=\"arial\"  COLOR=\"blue\">" + text + "</FONT></H3><br><br>");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void closeFile() {
        try {
            fl.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // Used by Globals to determine host-specific properties
    public static String getThisHostname() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String hostname = addr.getHostName();
            return hostname;
        }
        catch (UnknownHostException e) {
            System.err.println(e);
            return "unset - error";
        }
    }
}
