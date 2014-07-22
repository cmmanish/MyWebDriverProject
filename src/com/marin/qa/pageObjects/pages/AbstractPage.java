package com.marin.qa.pageObjects.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.SeleniumException;

public class AbstractPage {

    static Logger log = Logger.getLogger(AbstractPage.class);
    public static final long LONG_PAGE_TIMEOUT = 121000;
    public static final String ELEMENT_TIMEOUT = "65000";
    public static final String AJAX_TIMEOUT = "40000";
    public static final String JQUERY_TIMEOUT = "6000";
    public static final String SPINNER_TIMEOUT = "110000";
    public static final String TASK_ID = "#tasksText";
    public static final String POST_NOW_ID = "#postNowText";
    public static final String POST_CHANGE_ID = "#postChangeText";
    public static final String ACCOUNT_SUCCESS = ".good a:contains(\"Account information successfully updated.\")";
    public static final String ACTION_SUCCESS = ".good a:contains(\"Activity Log\")";
    public static final String ACTION_QUEUED = ".good li:contains(\"Action has been queued up to be posted to publishers\")";
    public static final String BUBBLE_CONTAINER = "#bubble_container";
    public static final String PROGRESS_GRID_CONTAINER = "#progress_grid_container";
    public static final String SAVE_VIEW_CONTAINER_ID = "#addRemoveView_container_contents";

    synchronized public static void wait(int n) {

        try {
            Thread.sleep(n, 0);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (IllegalMonitorStateException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method waits for an Jquery loaded
     * Requires current selenium object to work with.
     *
     * @param selenium
     *        The selenium instances currently in work.
     */

    public void waitForJQuery(WebDriver driver) {

        try {
            String query = "return $.active == 0";
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
        }

        catch (Exception e) {
            log.error("Failed to load jQuery on page in " + JQUERY_TIMEOUT + " msec");
        }

    }

    /**
     * This method waits for page to be loaded.
     * Requires current selenium object to work with.
     *
     * @param selenium
     *        The selenium instances currently in work.
     */
    public void waitForPageToLoad(final WebDriver driver, final long timeout) {

        try {

            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("top_right_logout_link")));

        }
        catch (Exception e) {
            log.error("Failed to wait for page to be loaded in " + timeout + " msec");
        }
    }

    public void waitForElementToBePresent(WebDriver driver, String locator) {

        String query = locator + ".length > 0";
        waitForJQuery(driver);

        try {
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            // selenium.waitForCondition("selenium.browserbot.getCurrentWindow().$('" + locator + "').length > 0;", ELEMENT_TIMEOUT);
        }
        catch (Exception e) {
            log.error("Failed to wait for element appear on page in " + ELEMENT_TIMEOUT + " msec");
        }
    }

    /**
     * This method waits for an element not to be visible.
     * 
     * Requires current selenium object to work with.
     *
     * @param selenium
     *        The selenium instances currently in work.
     * @param locator
     *        The Element locator.
     */
    public void waitForElementNotToBeVisible(WebDriver driver, String locator) {
        String query = "$('" + locator + "').is(':visible') == false;, ELEMENT_TIMEOUT";
        waitForJQuery(driver);

        try {
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);

        }
        catch (Exception e) {
            log.error("Failed to wait for element visible on page in " + ELEMENT_TIMEOUT + " msec");
        }

    }

    /**
     * Verifies that the element is somewhere on the page.
     * 
     * @param selenium
     * @param locator
     * @return true if the element is present, false otherwise
     */
    public boolean isElementPresent(final WebDriver driver, final String locator) {

        String query = "return $('" + locator + "').length > 0";
        waitForJQuery(driver);
        JavascriptExecutor jse;
        boolean retval = false;
        try {
            if (driver instanceof JavascriptExecutor) {
                jse = (JavascriptExecutor) driver;
                retval = (Boolean) jse.executeScript(query);
            }
            return retval;
        }
        catch (SeleniumException se) {
            log.error("Failed to confirm presents of element on page");
            return false;
        }
    }

    /**
     * This Method set to change element background
     * 
     * @author mmadhusoodan
     * @param selenium
     * @param locator
     * @return void
     * 
     */
    public void changeElementBackground(WebDriver driver, String locator) {

        waitForJQuery(driver);
        String query = "$('" + locator + "').css('border', '2px solid red');";

        try {
            ((JavascriptExecutor) driver).executeScript(query);

        }
        catch (Exception e) {
            log.error("Failed to change border of element on page");
        }

    }

    /**
     * This Method set to remove element background by id
     * 
     * @author mmadhusoodan
     * @param selenium
     * @param locator
     * @return void
     * 
     */
    public void removeElementBackground(WebDriver driver, String locator) {
        waitForJQuery(driver);
        String query = "$('" + locator + "').css('border', '');";

        try {
            ((JavascriptExecutor) driver).executeScript(query);

        }
        catch (Exception e) {
            log.error("Failed to change border of element on page");
        }

    }

}
