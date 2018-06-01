package com.Gmail.GmailAutomation.gmail.loginTests;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.Gmail.GmailAutomation.pages.gmail.GmailLoginPage;
import com.Gmail.GmailAutomation.testBase.TestBase;

public class TC_01_InvalidEmail extends TestBase{
	
	public static final Logger log = Logger.getLogger(TC_01_InvalidEmail.class.getName());
	GmailLoginPage gmailLoginPage;
	
	
	@BeforeClass
	public void setUp() throws IOException{
		launchGmailUrl();
	}
	
	/*@Parameters({"gmail_username", "gmail_password"})
	@Test(priority=1, description="To verify invalid account/email, which doesnot exist in DB")
	public void testInvalidEmail(String gmail_username, String gmail_password) throws InterruptedException{
		log.info("=========testInvalidEmail is Starting=========");
		System.out.println("nanda");
		gmailLoginPage = new GmailLoginPage(driver);
		System.out.println("nanda12");
		gmailLoginPage.sendEmail(gmail_username);
		String errMessage = gmailLoginPage.accountNotFoundError();
		Assert.assertTrue(errMessage.equals("Couldn't find your Google Account"), "Invalid account error message is not displayed on Login Page");
		log.info("=========testInvalidEmail is ending=========");
	}*/ 	

	@Parameters({"gmail_username"})
	@Test(priority=1, description="To verify invalid account/email, which doesnot exist in DB")
	public void testInvalidEmail(String gmail_username) throws InterruptedException{
		log.info("=========testInvalidEmail is Starting=========");
		System.out.println("nanda");
		gmailLoginPage = new GmailLoginPage(driver);
		System.out.println("nanda12");
		gmailLoginPage.sendEmail(gmail_username);
		String errMessage = gmailLoginPage.accountNotFoundError();
		Assert.assertTrue(errMessage.equals("Couldn't find your Google Account"), "Invalid account error message is not displayed on Login Page");
		log.info("=========testInvalidEmail is ending=========");
	}
}

