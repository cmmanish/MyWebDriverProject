package com.marin.qa.selenium.campaigns;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import com.marin.qa.selenium.pageObjects.bubble.Filter;
import com.marin.qa.selenium.pageObjects.pages.ActivityLogPage;
import com.marin.qa.selenium.pageObjects.pages.CampaignSettingsPage;
import com.marin.qa.selenium.pageObjects.pages.CampaignsPage;
import com.marin.qa.selenium.pageObjects.pages.HomePage;
import com.marin.qa.selenium.pageObjects.pages.NewCampaignPage;
import com.marin.qa.selenium.pageObjects.pages.NewGoogleCampaignPage;
import com.marin.qa.selenium.pageObjects.pages.NewGoogleCampaignPage.CampaignPriority;
import com.marin.qa.selenium.pageObjects.pages.NewGoogleCampaignPage.CampaignStatus;
import com.marin.qa.selenium.pageObjects.pages.NewGoogleCampaignPage.CampaignType;
import com.marin.qa.selenium.pageObjects.pages.NewGoogleCampaignPage.CountryOfSale;
import com.marin.qa.selenium.pageObjects.pages.SingleCampaignPage;
import com.marin.qa.selenium.resources.QaRandom;

public class TestSingleCreateCampaigns extends WebdriverBaseClass {

    @Rule
    static Logger log = Logger.getLogger(TestSingleCreateCampaigns.class);

    @Rule
    public QaRandom random = QaRandom.getInstance();

    @BeforeClass
    public static void testSetUp() {
        log.info("<--------- Start Setup Test --------->");
        LoginSuccessful();
        clearAllPendingChanges(driver);
        log.info("<--------- End Setup Test --------->");
    }

    @AfterClass
    public static void cleanup() {
        log.info("<--------- Start Logout Test --------->");
        clearAllPendingChanges(driver);
        logoutSuccessful();
        driver.close();
        log.info("<--------- End Logout Test --------->");
    }

    @Test
    public void T1SingleCreateGoogleShoppingCampaignNonUS() throws Exception {

        String campaignName = random.getRandomString("CampaignName", 5);

        String merchantId = "100543509";
        String budget = "1.11";
        String successLabel = "Campaign successfully created. See Activity Log for details.";

        calendar.setTime(Calendar.getInstance().getTime());
        final String startDate = groupFormaterDate.format(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        final String endDate = groupFormaterDate.format(calendar.getTime());

        HomePage homePage = HomePage.getInstance();
        homePage.select(driver, HomePage.Tab.Campaigns);
        CampaignsPage campaignsPage = CampaignsPage.getInstance();
        campaignsPage.click(driver, CampaignsPage.Button.Create);

        NewCampaignPage newCampaignsPage = NewCampaignPage.getInstance();
        newCampaignsPage.typeInput(driver, NewCampaignPage.TextInput.CampaignName, campaignName);
        newCampaignsPage.select(driver, NewCampaignPage.DropDownMenu.PublisherAccount, PUBLISHER + UNICODE_DOT_SMALL + GOOGLE_ACCOUNT);
        newCampaignsPage.clickButton(driver, NewCampaignPage.Button.NextStep);

        NewGoogleCampaignPage newGoogleCampaignsPage = NewGoogleCampaignPage.getInstance();

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.StateDate, startDate);
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Radio.SetEndDateYes);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.EndDate, endDate);
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.Status, CampaignStatus.ACTIVE.toString());
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CampaignType, CampaignType.SHOPPING.toString());

        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CountryOfSale, CountryOfSale.India.toString());
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CampaignPriority, CampaignPriority.High.toString());

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.MerchantId, merchantId);
        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.Budget, budget);

        newGoogleCampaignsPage.clickButton(driver, NewGoogleCampaignPage.Button.Save);

        assertEquals("Campaign not created Something went wrong ", successLabel, campaignsPage.getInfo(driver, CampaignsPage.Label.Success));

        campaignsPage.select(driver, CampaignsPage.DropDownMenu.Views, CampaignsPage.CAMPAIGN_VIEW);

        // Open the Campaign via clicking the link and go to Settings tab

        assertEquals("Campaign " + campaignName + " couldn't be opened ", true, campaignsPage.open(driver, campaignName));

        SingleCampaignPage singleCampaignPage = SingleCampaignPage.getInstance();
        singleCampaignPage.select(driver, SingleCampaignPage.Tab.Settings);

        CampaignSettingsPage campaignSettingsPage = CampaignSettingsPage.getInstance();
        assertEquals("Campaign Name in the Settings Page don't match ", campaignName, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.CampaignName));
        assertEquals("Campaign Status in the Settings Page don't match ", CampaignStatus.ACTIVE.toString(), campaignSettingsPage.getSelected(driver, CampaignSettingsPage.DropDownMenu.Status));
        assertEquals("Campaign Start Date in the Settings Page don't match ", startDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.StateDate));
        assertEquals("Campaign End Date in the Settings Page don't match ", endDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.EndDate));
        assertEquals("Campaign budget in the Settings Page don't match ", budget, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.Budget));

        homePage.click(driver, HomePage.Link.Admin);
        ActivityLogPage activityLog = ActivityLogPage.getInstance();

        String postCount = activityLog.getInfo(driver, ActivityLogPage.Label.PostCount);
        while ("0".equalsIgnoreCase(postCount)) {
            homePage.click(driver, HomePage.Link.Admin);
            postCount = activityLog.getInfo(driver, ActivityLogPage.Label.PostCount);
        }

    }

    @Test
    public void T2SingleCreateGoogleShoppingCampaignUSShoppingChannelBoth() throws Exception {

        String campaignName = random.getRandomString("CampaignName", 5);
        String merchantId = "100543509";
        String budget = "1.11";
        String successLabel = "Campaign successfully created. See Activity Log for details.";

        calendar.setTime(Calendar.getInstance().getTime());
        final String startDate = groupFormaterDate.format(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        final String endDate = groupFormaterDate.format(calendar.getTime());

        HomePage homePage = HomePage.getInstance();
        homePage.select(driver, HomePage.Tab.Campaigns);
        CampaignsPage campaignsPage = CampaignsPage.getInstance();
        campaignsPage.click(driver, CampaignsPage.Button.Create);

        NewCampaignPage newCampaignsPage = NewCampaignPage.getInstance();
        newCampaignsPage.typeInput(driver, NewCampaignPage.TextInput.CampaignName, campaignName);
        newCampaignsPage.select(driver, NewCampaignPage.DropDownMenu.PublisherAccount, PUBLISHER + UNICODE_DOT_SMALL + GOOGLE_ACCOUNT);
        newCampaignsPage.clickButton(driver, NewCampaignPage.Button.NextStep);

        NewGoogleCampaignPage newGoogleCampaignsPage = NewGoogleCampaignPage.getInstance();

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.StateDate, startDate);
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Radio.SetEndDateYes);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.EndDate, endDate);
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.Status, CampaignStatus.ACTIVE.toString());
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CampaignType, CampaignType.SHOPPING.toString());

        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CountryOfSale, CountryOfSale.UnitedStates.toString());
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CampaignPriority, CampaignPriority.High.toString());
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.Online);
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.Local);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.MerchantId, merchantId);
        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.Budget, budget);

        newGoogleCampaignsPage.clickButton(driver, NewGoogleCampaignPage.Button.Save);

        assertEquals("Campaign not created Something went wrong ", successLabel, campaignsPage.getInfo(driver, CampaignsPage.Label.Success));

        campaignsPage.select(driver, CampaignsPage.DropDownMenu.Views, CampaignsPage.CAMPAIGN_VIEW);

        Filter filter = Filter.getInstance();
        filter.apply(driver, Filter.Column.Campaign, Filter.Menu.Contains, campaignName);

        // Open the Campaign via clicking the link and go to Settings tab

        assertEquals("Campaign " + campaignName + " couldn't be opened ", true, campaignsPage.open(driver, campaignName));

        SingleCampaignPage singleCampaignPage = SingleCampaignPage.getInstance();
        singleCampaignPage.select(driver, SingleCampaignPage.Tab.Settings);

        CampaignSettingsPage campaignSettingsPage = CampaignSettingsPage.getInstance();
        assertEquals("Campaign Name in the Settings Page don't match ", campaignName, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.CampaignName));
        assertEquals("Campaign Status in the Settings Page don't match ", CampaignStatus.ACTIVE.toString(), campaignSettingsPage.getSelected(driver, CampaignSettingsPage.DropDownMenu.Status));
        assertEquals("Campaign Start Date in the Settings Page don't match ", startDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.StateDate));
        assertEquals("Campaign End Date in the Settings Page don't match ", endDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.EndDate));

        assertEquals("Shopping Channel Online didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.Online));
        assertEquals("Shopping Channel Local didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.Local));

        assertEquals("Campaign budget in the Settings Page don't match ", budget, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.Budget));

        homePage.click(driver, HomePage.Link.Admin);
        ActivityLogPage activityLog = ActivityLogPage.getInstance();

        String postCount = activityLog.getInfo(driver, ActivityLogPage.Label.PostCount);
        while ("0".equalsIgnoreCase(postCount)) {
            homePage.click(driver, HomePage.Link.Admin);
            postCount = activityLog.getInfo(driver, ActivityLogPage.Label.PostCount);
        }

    }

    @Test
    public void T3SingleCreateGoogleShoppingCampaignUSShoppingChannelOnline() throws Exception {

        String campaignName = random.getRandomString("CampaignName", 5);
        String merchantId = "100543509";
        String budget = "1.11";
        String successLabel = "Campaign successfully created. See Activity Log for details.";

        calendar.setTime(Calendar.getInstance().getTime());
        final String startDate = groupFormaterDate.format(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        final String endDate = groupFormaterDate.format(calendar.getTime());

        HomePage homePage = HomePage.getInstance();
        homePage.select(driver, HomePage.Tab.Campaigns);
        CampaignsPage campaignsPage = CampaignsPage.getInstance();
        campaignsPage.click(driver, CampaignsPage.Button.Create);

        NewCampaignPage newCampaignsPage = NewCampaignPage.getInstance();
        newCampaignsPage.typeInput(driver, NewCampaignPage.TextInput.CampaignName, campaignName);
        newCampaignsPage.select(driver, NewCampaignPage.DropDownMenu.PublisherAccount, PUBLISHER + UNICODE_DOT_SMALL + GOOGLE_ACCOUNT);
        newCampaignsPage.clickButton(driver, NewCampaignPage.Button.NextStep);

        NewGoogleCampaignPage newGoogleCampaignsPage = NewGoogleCampaignPage.getInstance();

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.StateDate, startDate);
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Radio.SetEndDateYes);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.EndDate, endDate);
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.Status, CampaignStatus.ACTIVE.toString());
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CampaignType, CampaignType.SHOPPING.toString());

        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CountryOfSale, CountryOfSale.UnitedStates.toString());
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CampaignPriority, CampaignPriority.High.toString());
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.Online);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.MerchantId, merchantId);
        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.Budget, budget);

        newGoogleCampaignsPage.clickButton(driver, NewGoogleCampaignPage.Button.Save);

        assertEquals("Campaign not created Something went wrong ", successLabel, campaignsPage.getInfo(driver, CampaignsPage.Label.Success));

        campaignsPage.select(driver, CampaignsPage.DropDownMenu.Views, CampaignsPage.CAMPAIGN_VIEW);

        Filter filter = Filter.getInstance();
        filter.apply(driver, Filter.Column.Campaign, Filter.Menu.Contains, campaignName);

        // Open the Campaign via clicking the link and go to Settings tab

        assertEquals("Campaign " + campaignName + " couldn't be opened ", true, campaignsPage.open(driver, campaignName));

        SingleCampaignPage singleCampaignPage = SingleCampaignPage.getInstance();
        singleCampaignPage.select(driver, SingleCampaignPage.Tab.Settings);

        CampaignSettingsPage campaignSettingsPage = CampaignSettingsPage.getInstance();
        assertEquals("Campaign Name in the Settings Page don't match ", campaignName, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.CampaignName));
        assertEquals("Campaign Status in the Settings Page don't match ", CampaignStatus.ACTIVE.toString(), campaignSettingsPage.getSelected(driver, CampaignSettingsPage.DropDownMenu.Status));
        assertEquals("Campaign Start Date in the Settings Page don't match ", startDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.StateDate));
        assertEquals("Campaign End Date in the Settings Page don't match ", endDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.EndDate));

        assertEquals("Shopping Channel Online didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.Online));
        assertEquals("Shopping Channel Local didn't match ", false, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.Local));

        assertEquals("Campaign budget in the Settings Page don't match ", budget, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.Budget));

        homePage.click(driver, HomePage.Link.Admin);
        ActivityLogPage activityLog = ActivityLogPage.getInstance();

        String postCount = activityLog.getInfo(driver, ActivityLogPage.Label.PostCount);
        while ("0".equalsIgnoreCase(postCount)) {
            homePage.click(driver, HomePage.Link.Admin);
            postCount = activityLog.getInfo(driver, ActivityLogPage.Label.PostCount);
        }

    }

    @Test
    public void T4SingleCreateGoogleShoppingCampaignUSShoppingChannelLocal() throws Exception {

        String campaignName = random.getRandomString("CampaignName", 5);
        String merchantId = "100543509";
        String budget = "1.11";
        String successLabel = "Campaign successfully created. See Activity Log for details.";

        calendar.setTime(Calendar.getInstance().getTime());
        final String startDate = groupFormaterDate.format(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        final String endDate = groupFormaterDate.format(calendar.getTime());

        HomePage homePage = HomePage.getInstance();
        homePage.select(driver, HomePage.Tab.Campaigns);
        CampaignsPage campaignsPage = CampaignsPage.getInstance();
        campaignsPage.click(driver, CampaignsPage.Button.Create);

        NewCampaignPage newCampaignsPage = NewCampaignPage.getInstance();
        newCampaignsPage.typeInput(driver, NewCampaignPage.TextInput.CampaignName, campaignName);
        newCampaignsPage.select(driver, NewCampaignPage.DropDownMenu.PublisherAccount, PUBLISHER + UNICODE_DOT_SMALL + GOOGLE_ACCOUNT);
        newCampaignsPage.clickButton(driver, NewCampaignPage.Button.NextStep);

        NewGoogleCampaignPage newGoogleCampaignsPage = NewGoogleCampaignPage.getInstance();

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.StateDate, startDate);
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Radio.SetEndDateYes);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.EndDate, endDate);
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.Status, CampaignStatus.ACTIVE.toString());
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CampaignType, CampaignType.SHOPPING.toString());

        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CountryOfSale, CountryOfSale.UnitedStates.toString());
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CampaignPriority, CampaignPriority.High.toString());
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.Local);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.MerchantId, merchantId);
        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.Budget, budget);

        newGoogleCampaignsPage.clickButton(driver, NewGoogleCampaignPage.Button.Save);

        assertEquals("Campaign not created Something went wrong ", successLabel, campaignsPage.getInfo(driver, CampaignsPage.Label.Success));

        campaignsPage.select(driver, CampaignsPage.DropDownMenu.Views, CampaignsPage.CAMPAIGN_VIEW);

        Filter filter = Filter.getInstance();
        filter.apply(driver, Filter.Column.Campaign, Filter.Menu.Contains, campaignName);

        // Open the Campaign via clicking the link and go to Settings tab

        assertEquals("Campaign " + campaignName + " couldn't be opened ", true, campaignsPage.open(driver, campaignName));

        SingleCampaignPage singleCampaignPage = SingleCampaignPage.getInstance();
        singleCampaignPage.select(driver, SingleCampaignPage.Tab.Settings);

        CampaignSettingsPage campaignSettingsPage = CampaignSettingsPage.getInstance();
        assertEquals("Campaign Name in the Settings Page don't match ", campaignName, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.CampaignName));
        assertEquals("Campaign Status in the Settings Page don't match ", CampaignStatus.ACTIVE.toString(), campaignSettingsPage.getSelected(driver, CampaignSettingsPage.DropDownMenu.Status));
        assertEquals("Campaign Start Date in the Settings Page don't match ", startDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.StateDate));
        assertEquals("Campaign End Date in the Settings Page don't match ", endDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.EndDate));

        assertEquals("Shopping Channel Online didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.Online));
        assertEquals("Shopping Channel Local didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.Local));

        assertEquals("Campaign budget in the Settings Page don't match ", budget, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.Budget));

        homePage.click(driver, HomePage.Link.Admin);
        ActivityLogPage activityLog = ActivityLogPage.getInstance();

        String postCount = activityLog.getInfo(driver, ActivityLogPage.Label.PostCount);
        while ("0".equalsIgnoreCase(postCount)) {
            homePage.click(driver, HomePage.Link.Admin);
            postCount = activityLog.getInfo(driver, ActivityLogPage.Label.PostCount);
        }

    }

    @Test
    public void T5SingleCreateGoogleSearchNetworkCampaignSearchPartnerDistribution() throws Exception {

        String campaignName = random.getRandomString("CampaignName", 5);

        String budget = "1.11";
        String successLabel = "Campaign successfully created. See Activity Log for details.";

        calendar.setTime(Calendar.getInstance().getTime());
        final String startDate = groupFormaterDate.format(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        final String endDate = groupFormaterDate.format(calendar.getTime());

        HomePage homePage = HomePage.getInstance();
        homePage.select(driver, HomePage.Tab.Campaigns);
        CampaignsPage campaignsPage = CampaignsPage.getInstance();
        campaignsPage.click(driver, CampaignsPage.Button.Create);

        NewCampaignPage newCampaignsPage = NewCampaignPage.getInstance();
        newCampaignsPage.typeInput(driver, NewCampaignPage.TextInput.CampaignName, campaignName);
        newCampaignsPage.select(driver, NewCampaignPage.DropDownMenu.PublisherAccount, PUBLISHER + UNICODE_DOT_SMALL + GOOGLE_ACCOUNT);
        newCampaignsPage.clickButton(driver, NewCampaignPage.Button.NextStep);

        NewGoogleCampaignPage newGoogleCampaignsPage = NewGoogleCampaignPage.getInstance();

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.StateDate, startDate);
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Radio.SetEndDateYes);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.EndDate, endDate);
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.Status, CampaignStatus.ACTIVE.toString());
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CampaignType, CampaignType.SEARCHNETWORK.toString());

        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.SearchPartners);
        newGoogleCampaignsPage.uncheck(driver, NewGoogleCampaignPage.Checkbox.DisplaySelect);

        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.KeywordMatching);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.Budget, budget);

        newGoogleCampaignsPage.clickButton(driver, NewGoogleCampaignPage.Button.Save);

        assertEquals("Campaign not created Something went wrong ", successLabel, campaignsPage.getInfo(driver, CampaignsPage.Label.Success));

        campaignsPage.select(driver, CampaignsPage.DropDownMenu.Views, CampaignsPage.CAMPAIGN_VIEW);

        // Open the Campaign via clicking the link and go to Settings tab

        assertEquals("Campaign " + campaignName + " couldn't be opened ", true, campaignsPage.open(driver, campaignName));

        SingleCampaignPage singleCampaignPage = SingleCampaignPage.getInstance();
        singleCampaignPage.select(driver, SingleCampaignPage.Tab.Settings);

        CampaignSettingsPage campaignSettingsPage = CampaignSettingsPage.getInstance();
        assertEquals("Campaign Name in the Settings Page don't match ", campaignName, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.CampaignName));
        assertEquals("Campaign Status in the Settings Page don't match ", CampaignStatus.ACTIVE.toString(), campaignSettingsPage.getSelected(driver, CampaignSettingsPage.DropDownMenu.Status));
        assertEquals("Campaign Start Date in the Settings Page don't match ", startDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.StateDate));
        assertEquals("Campaign End Date in the Settings Page don't match ", endDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.EndDate));

        assertEquals("Search Partners Distribution didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.SearchPartners));
        assertEquals("Diplay Partners Distribution didn't match ", false, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.DisplaySelect));
        assertEquals("KeywordMatching didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.KeywordMatching));

        assertEquals("Campaign budget in the Settings Page don't match ", budget, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.Budget));

        homePage.click(driver, HomePage.Link.Admin);
        ActivityLogPage activityLog = ActivityLogPage.getInstance();

        String postCount = activityLog.getInfo(driver, ActivityLogPage.Label.PostCount);
        while ("0".equalsIgnoreCase(postCount)) {
            homePage.click(driver, HomePage.Link.Admin);
            postCount = activityLog.getInfo(driver, ActivityLogPage.Label.PostCount);
        }

    }

    @Test
    public void T6SingleCreateGoogleSearchNetworkCampaignDisplaySelectDistribution() throws Exception {

        String campaignName = random.getRandomString("CampaignName", 5);

        String budget = "1.11";
        String successLabel = "Campaign successfully created. See Activity Log for details.";

        calendar.setTime(Calendar.getInstance().getTime());
        final String startDate = groupFormaterDate.format(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        final String endDate = groupFormaterDate.format(calendar.getTime());

        HomePage homePage = HomePage.getInstance();
        homePage.select(driver, HomePage.Tab.Campaigns);
        CampaignsPage campaignsPage = CampaignsPage.getInstance();
        campaignsPage.click(driver, CampaignsPage.Button.Create);

        NewCampaignPage newCampaignsPage = NewCampaignPage.getInstance();
        newCampaignsPage.typeInput(driver, NewCampaignPage.TextInput.CampaignName, campaignName);
        newCampaignsPage.select(driver, NewCampaignPage.DropDownMenu.PublisherAccount, PUBLISHER + UNICODE_DOT_SMALL + GOOGLE_ACCOUNT);
        newCampaignsPage.clickButton(driver, NewCampaignPage.Button.NextStep);

        NewGoogleCampaignPage newGoogleCampaignsPage = NewGoogleCampaignPage.getInstance();

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.StateDate, startDate);
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Radio.SetEndDateYes);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.EndDate, endDate);
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.Status, CampaignStatus.ACTIVE.toString());
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CampaignType, CampaignType.SEARCHNETWORK.toString());

        newGoogleCampaignsPage.uncheck(driver, NewGoogleCampaignPage.Checkbox.SearchPartners);
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.DisplaySelect);

        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.KeywordMatching);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.Budget, budget);

        newGoogleCampaignsPage.clickButton(driver, NewGoogleCampaignPage.Button.Save);

        assertEquals("Campaign not created Something went wrong ", successLabel, campaignsPage.getInfo(driver, CampaignsPage.Label.Success));

        campaignsPage.select(driver, CampaignsPage.DropDownMenu.Views, CampaignsPage.CAMPAIGN_VIEW);

        // Open the Campaign via clicking the link and go to Settings tab

        assertEquals("Campaign " + campaignName + " couldn't be opened ", true, campaignsPage.open(driver, campaignName));

        SingleCampaignPage singleCampaignPage = SingleCampaignPage.getInstance();
        singleCampaignPage.select(driver, SingleCampaignPage.Tab.Settings);

        CampaignSettingsPage campaignSettingsPage = CampaignSettingsPage.getInstance();
        assertEquals("Campaign Name in the Settings Page don't match ", campaignName, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.CampaignName));
        assertEquals("Campaign Status in the Settings Page don't match ", CampaignStatus.ACTIVE.toString(), campaignSettingsPage.getSelected(driver, CampaignSettingsPage.DropDownMenu.Status));
        assertEquals("Campaign Start Date in the Settings Page don't match ", startDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.StateDate));
        assertEquals("Campaign End Date in the Settings Page don't match ", endDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.EndDate));

        assertEquals("Search Partners Distribution didn't match ", false, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.SearchPartners));
        assertEquals("Diplay Partners Distribution didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.DisplaySelect));
        assertEquals("KeywordMatching didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.KeywordMatching));

        assertEquals("Campaign budget in the Settings Page don't match ", budget, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.Budget));

        homePage.click(driver, HomePage.Link.Admin);
        ActivityLogPage activityLog = ActivityLogPage.getInstance();

        String postCount = activityLog.getInfo(driver, ActivityLogPage.Label.PostCount);
        while ("0".equalsIgnoreCase(postCount)) {
            homePage.click(driver, HomePage.Link.Admin);
            postCount = activityLog.getInfo(driver, ActivityLogPage.Label.PostCount);
        }

    }

    @Test
    public void T7SingleCreateGoogleSearchNetworkCampaignAllDistribution() throws Exception {

        String campaignName = random.getRandomString("CampaignName", 5);

        String budget = "1.11";
        String successLabel = "Campaign successfully created. See Activity Log for details.";

        calendar.setTime(Calendar.getInstance().getTime());
        final String startDate = groupFormaterDate.format(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        final String endDate = groupFormaterDate.format(calendar.getTime());

        HomePage homePage = HomePage.getInstance();
        homePage.select(driver, HomePage.Tab.Campaigns);
        CampaignsPage campaignsPage = CampaignsPage.getInstance();
        campaignsPage.click(driver, CampaignsPage.Button.Create);

        NewCampaignPage newCampaignsPage = NewCampaignPage.getInstance();
        newCampaignsPage.typeInput(driver, NewCampaignPage.TextInput.CampaignName, campaignName);
        newCampaignsPage.select(driver, NewCampaignPage.DropDownMenu.PublisherAccount, PUBLISHER + UNICODE_DOT_SMALL + GOOGLE_ACCOUNT);
        newCampaignsPage.clickButton(driver, NewCampaignPage.Button.NextStep);

        NewGoogleCampaignPage newGoogleCampaignsPage = NewGoogleCampaignPage.getInstance();

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.StateDate, startDate);
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Radio.SetEndDateYes);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.EndDate, endDate);
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.Status, CampaignStatus.ACTIVE.toString());
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CampaignType, CampaignType.SEARCHNETWORK.toString());

        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.SearchPartners);
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.DisplaySelect);

        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.KeywordMatching);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.Budget, budget);

        newGoogleCampaignsPage.clickButton(driver, NewGoogleCampaignPage.Button.Save);

        assertEquals("Campaign not created Something went wrong ", successLabel, campaignsPage.getInfo(driver, CampaignsPage.Label.Success));

        campaignsPage.select(driver, CampaignsPage.DropDownMenu.Views, CampaignsPage.CAMPAIGN_VIEW);

        // Open the Campaign via clicking the link and go to Settings tab

        assertEquals("Campaign " + campaignName + " couldn't be opened ", true, campaignsPage.open(driver, campaignName));

        SingleCampaignPage singleCampaignPage = SingleCampaignPage.getInstance();
        singleCampaignPage.select(driver, SingleCampaignPage.Tab.Settings);

        CampaignSettingsPage campaignSettingsPage = CampaignSettingsPage.getInstance();
        assertEquals("Campaign Name in the Settings Page don't match ", campaignName, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.CampaignName));
        assertEquals("Campaign Status in the Settings Page don't match ", CampaignStatus.ACTIVE.toString(), campaignSettingsPage.getSelected(driver, CampaignSettingsPage.DropDownMenu.Status));
        assertEquals("Campaign Start Date in the Settings Page don't match ", startDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.StateDate));
        assertEquals("Campaign End Date in the Settings Page don't match ", endDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.EndDate));

        assertEquals("Search Partners Distribution didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.SearchPartners));
        assertEquals("Diplay Partners Distribution didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.DisplaySelect));
        assertEquals("KeywordMatching didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.KeywordMatching));

        assertEquals("Campaign budget in the Settings Page don't match ", budget, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.Budget));

        homePage.click(driver, HomePage.Link.Admin);
        ActivityLogPage activityLog = ActivityLogPage.getInstance();

        String postCount = activityLog.getInfo(driver, ActivityLogPage.Label.PostCount);
        while ("0".equalsIgnoreCase(postCount)) {
            homePage.click(driver, HomePage.Link.Admin);
            postCount = activityLog.getInfo(driver, ActivityLogPage.Label.PostCount);
        }

    }

    @Test
    public void T8SingleCreateGoogleDisplayNetworkOnlyCampaign() throws Exception {

        String campaignName = random.getRandomString("CampaignName", 5);

        String budget = "1.11";
        String successLabel = "Campaign successfully created. See Activity Log for details.";

        calendar.setTime(Calendar.getInstance().getTime());
        final String startDate = groupFormaterDate.format(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        final String endDate = groupFormaterDate.format(calendar.getTime());

        HomePage homePage = HomePage.getInstance();
        homePage.select(driver, HomePage.Tab.Campaigns);
        CampaignsPage campaignsPage = CampaignsPage.getInstance();
        campaignsPage.click(driver, CampaignsPage.Button.Create);

        NewCampaignPage newCampaignsPage = NewCampaignPage.getInstance();
        newCampaignsPage.typeInput(driver, NewCampaignPage.TextInput.CampaignName, campaignName);
        newCampaignsPage.select(driver, NewCampaignPage.DropDownMenu.PublisherAccount, PUBLISHER + UNICODE_DOT_SMALL + GOOGLE_ACCOUNT);
        newCampaignsPage.clickButton(driver, NewCampaignPage.Button.NextStep);

        NewGoogleCampaignPage newGoogleCampaignsPage = NewGoogleCampaignPage.getInstance();

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.StateDate, startDate);
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Radio.SetEndDateYes);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.EndDate, endDate);
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.Status, CampaignStatus.ACTIVE.toString());
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CampaignType, CampaignType.DISPLAYNETWORKONLY.toString());

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.Budget, budget);

        newGoogleCampaignsPage.clickButton(driver, NewGoogleCampaignPage.Button.Save);

        assertEquals("Campaign not created Something went wrong ", successLabel, campaignsPage.getInfo(driver, CampaignsPage.Label.Success));

        campaignsPage.select(driver, CampaignsPage.DropDownMenu.Views, CampaignsPage.CAMPAIGN_VIEW);

        // Open the Campaign via clicking the link and go to Settings tab

        assertEquals("Campaign " + campaignName + " couldn't be opened ", true, campaignsPage.open(driver, campaignName));

        SingleCampaignPage singleCampaignPage = SingleCampaignPage.getInstance();
        singleCampaignPage.select(driver, SingleCampaignPage.Tab.Settings);

        CampaignSettingsPage campaignSettingsPage = CampaignSettingsPage.getInstance();
        assertEquals("Campaign Name in the Settings Page don't match ", campaignName, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.CampaignName));
        assertEquals("Campaign Status in the Settings Page don't match ", CampaignStatus.ACTIVE.toString(), campaignSettingsPage.getSelected(driver, CampaignSettingsPage.DropDownMenu.Status));
        assertEquals("Campaign Start Date in the Settings Page don't match ", startDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.StateDate));
        assertEquals("Campaign End Date in the Settings Page don't match ", endDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.EndDate));

        assertEquals("Campaign budget in the Settings Page don't match ", budget, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.Budget));

        homePage.click(driver, HomePage.Link.Admin);
        ActivityLogPage activityLog = ActivityLogPage.getInstance();

        String postCount = activityLog.getInfo(driver, ActivityLogPage.Label.PostCount);
        while ("0".equalsIgnoreCase(postCount)) {
            homePage.click(driver, HomePage.Link.Admin);
            postCount = activityLog.getInfo(driver, ActivityLogPage.Label.PostCount);
        }

    }

    public void testLogout() {
        log.info("<--------- Start Logout Test --------->");
        HomePage homePage = HomePage.getInstance();
        clearAllPendingChanges(driver);
        homePage.click(driver, HomePage.Link.Admin);
        homePage.click(driver, HomePage.Link.Logout);
        driver.close();
        log.info("<--------- End Login Test --------->");
    }

}
