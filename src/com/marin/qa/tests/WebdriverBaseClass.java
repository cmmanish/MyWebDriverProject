package com.marin.qa.tests;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.marin.qa.utils.UtilFunctions;

public abstract class WebdriverBaseClass {

    static Logger log = Logger.getLogger(WebdriverBaseClass.class);
    public static final WebDriver driver = UtilFunctions.getWebDriver();

    protected static String LOGIN = "auto_fb_pricing@marinsoftware.com";
    protected static String PASSWORD = "marin2007";
    protected static String START_URL = "https:///truffle.marinsoftware.com";

    public WebdriverBaseClass() {

    }

}
