package com.marin.qa.tests;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import com.marin.qa.common.QaRandom;
import com.marin.qa.pageObjects.pages.HomePage;

public class TestCampaigns1 extends WebdriverBaseClass {

    @Rule
    static Logger log = Logger.getLogger(TestCampaigns1.class);

    @Rule
    public QaRandom random = QaRandom.getInstance();

    @BeforeClass
    public static void testSetUp() {
        log.info("<--------- Start Setup Test --------->");    
        testLoginSuccessful();
        clearAllPendingChanges(driver);
        log.info("<--------- End Setup Test --------->");
    }

    @AfterClass
    public static void cleanup() {
        log.info("<--------- Start Logout Test --------->");
        clearAllPendingChanges(driver);
        HomePage homePage = HomePage.getInstance();
        homePage.click(driver, HomePage.Link.Admin);
        homePage.click(driver, HomePage.Link.Logout);
        driver.close();
        log.info("<--------- End Logout Test --------->");
    }

    @Test
    public void E1testSingleCreateGoogleShoppingCampaignUS() throws Exception {
        log.info("E1");
    }

    @Test
    public void E2testSingleCreateGoogleShoppingCampaignNonUS() throws Exception {
        log.info("E2");
    }

    @Test
    public void E3testSingleCreateGoogleShoppingCampaignNonUS() throws Exception {
        log.info("E3");
    }

}
