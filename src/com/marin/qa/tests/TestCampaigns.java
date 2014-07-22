package com.marin.qa.tests;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.marin.qa.pageObjects.ActivityLogPage;
import com.marin.qa.pageObjects.pages.CampaignsPage;
import com.marin.qa.pageObjects.pages.HomePage;
import com.marin.qa.pageObjects.pages.LoginPage;

public class TestCampaigns extends WebdriverBaseClass {

    static Logger log = Logger.getLogger(TestCampaigns.class);
    protected static String LOGIN = "auto_orca@marinsoftware.com";
    protected static String PASSWORD = "marin2007";
    protected static String START_URL = "http:///blue.marinsoftware.com";

    @Before
    public void testSetUp() {
        log.info("<--------- Start Setup Test --------->");
        driver.navigate().to(START_URL);
        log.info("Testing on:  URL: " + START_URL);
        log.info("<--------- End Setup Test --------->");
        testLoginSuccessful();
    }

    @After
    public void testLogout() {
        log.info("<--------- Start Logout Test --------->");
        HomePage homePage = HomePage.getInstance();
        homePage.click(driver, HomePage.Link.Logout);
        driver.quit();
        log.info("<--------- End Login Test --------->");
    }

    public void testLoginSuccessful() {
        log.info("<--------- Start Login Test --------->");
        LoginPage loginPage = LoginPage.getInstance();
        loginPage.login(driver, LOGIN, PASSWORD);
        log.info("<--------- End Login Test --------->");
    }

    @Test
    public void testCreateCampaign() {
        HomePage homePage = HomePage.getInstance();
        homePage.select(driver, HomePage.Tab.Campaigns);
        homePage.select(driver, HomePage.Tab.Groups);
        homePage.select(driver, HomePage.Tab.Keywords);
        homePage.select(driver, HomePage.Tab.Creatives);
        //CampaignsPage campaignsPage = CampaignsPage.getInstance();

        homePage.click(driver, HomePage.Link.Admin);
        ActivityLogPage activityLog = ActivityLogPage.getInstance();
        activityLog.click(driver, ActivityLogPage.Link.Campaigns);
        

    }

}
