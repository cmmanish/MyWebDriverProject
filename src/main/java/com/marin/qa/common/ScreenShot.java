package com.marin.qa.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShot {
    static Logger log = Logger.getLogger(ScreenShot.class);

    private WebDriver driver;
    private final String IMAGE_SUFFIX = ".png";
    private Class<?> testSuiteClass;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH-mm-ss-SSS");

    /*
     * Since this object requires a driver object, I don't want to create it without one. So create a private no
     * argument constructor to block direct creation.
     */
    @SuppressWarnings("unused")
    private ScreenShot() {

    }

    public ScreenShot(WebDriver driver) {
        log.debug("Initialize: " + this.getClass().getName());
        this.driver = driver;
    }

    public void captureScreenShot(Description description) {
        //SeleniumFrameworkProperties.isScreenShotEnabled()
        boolean isScreenShotEnabled = true;
        if (isScreenShotEnabled) {
            FileOutputStream fileOutputStream = null;
            try {
                File file;
                File parent;

                String screenShotFileName = getFolderPath(description);
                log.debug("Capture screenshot: {}" + screenShotFileName);
                // the screenshot will be created under your test project folder
                file = new File(screenShotFileName);
                parent = file.getParentFile();

                // if file doesn't exists, then create it
                if (!file.exists()) {
                    parent.mkdirs();
                    file.createNewFile();
                }

                fileOutputStream = new FileOutputStream(file);

                // get the content in bytes
                byte[] contentInBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

                fileOutputStream.write(contentInBytes);
                fileOutputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                IOUtils.closeQuietly(fileOutputStream);
            }
        }
    }

    public void setTestSuiteClass(Class<?> testSuiteClass) {
        this.testSuiteClass = testSuiteClass;
    }

    public Class<?> getTestSuiteClass() {
        return testSuiteClass;
    }

    /**
     * Generates the file/folder path String to be used for storing a screenshot
     * 
     * @param description
     * @return the full path to the screenshot file as a String
     */
    private String getFolderPath(Description description) {
        
        String folderPath = ""; 
//        String testSuiteName = description.getTestClass().getSimpleName();
//        String testName = description.getMethodName();
//        String fileName = dateFormat.format(new Date());
//
//        StringBuilder folderPath = new StringBuilder()
//                .append(temporaryFile.getRoot().getParent())
//                .append(File.separator)
//                .append("screenshots")
//                .append(File.separator)
//                .append(testSuiteName)
//                .append(File.separator)
//                .append(testName)
//                .append(File.separator)
//                .append(fileName)
//                .append(IMAGE_SUFFIX);
        
        return folderPath.toString();
    }
}