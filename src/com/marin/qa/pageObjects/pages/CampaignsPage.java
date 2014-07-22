package com.marin.qa.pageObjects.pages;

import org.apache.log4j.Logger;

public class CampaignsPage extends AbstractPage{

	static Logger log = Logger.getLogger(CampaignsPage.class);
	private static CampaignsPage instance;

	/**
	  * Private constructor prevents construction outside this class.
	*/
	private CampaignsPage(){}

	public static synchronized CampaignsPage getInstance(){

		if (instance == null){
			instance = new CampaignsPage();
		}

		return instance;
	}
	
	/**
     * Link Element as list of all links on page
     * @version 2.00
     * @param locator as jQuery mapping for Link element locator 
     * @param spinner as jQuery mapping for spinner 
     * @param pageLoad as jQuery mapping for pageLoad 
     * @param description as description for Link element
     * @author mmadhusoodan
     */
	public static enum Link {
	    
	    
	    
	}

}
