package com.marin.qa.selenium;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.marin.qa.selenium.campaigns.TestCampaigns1;

@RunWith(Suite.class)
@SuiteClasses({ TestCampaigns1.class })
public class CampaignTests {

}   