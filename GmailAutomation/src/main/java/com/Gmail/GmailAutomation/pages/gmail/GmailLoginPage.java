package com.Gmail.GmailAutomation.pages.gmail;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GmailLoginPage {

	public static final Logger log = Logger.getLogger(GmailLoginPage.class.getName());

	WebDriver driver;

	//@FindBy(xpath = "//input[@id='identifierId' and @type='email']")
	//WebElement gmail_username1;
	
	@FindBy(xpath = "//*[@id='identifierId']")
	WebElement gmail_username1;

	@FindBy(xpath = "//*[@id='identifierNext' and @role='button']")
	WebElement email_nextButton;

	@FindBy(xpath = "//*[@id='password']")
	WebElement gmail_password;

	@FindBy(xpath = "//*[@id='passwordNext' and @role='button']")
	WebElement pwd_nextButton;

	// In PageFactory, we always need to create one constructor of the class.
	// This constructor will initialize all our web elements
	public GmailLoginPage(WebDriver driver) {
		// If you not initialize this, then you can't access any functions like
		// driver.swithchTo etc. you will get NullPointerException.
		this.driver = driver;
		// Since this is a PageFactory, we need to initialize the Web elements.
		// Then only we can call the method of this web element otherwise, you
		// will get NullPointerException.
		PageFactory.initElements(driver, this);
		// Here 'this' refers to current class object
	}

	public void sendEmail(String email) throws InterruptedException {
		log.info("Entering email");
		Thread.sleep(5000);
		//gmail_username.clear();
		gmail_username1.click();
		Thread.sleep(2000);
		gmail_username1.sendKeys(email);
		Thread.sleep(2000);
		log.info("Clicking on Next button after email");
		email_nextButton.click();
	}

	public void sendBlankEmail() throws InterruptedException {
		log.info("Entering email");
		gmail_username1.clear();
		Thread.sleep(2000);
		email_nextButton.click();
	}

	public void sendPassword(String password) throws InterruptedException {
		log.info("Entering password");
		gmail_password.sendKeys(password);
		Thread.sleep(2000);
		log.info("Clicking on Next button after password");
		pwd_nextButton.click();
	}

	@FindBy(xpath = "//*[@id='view_container']/descendant::div[contains(text(),'Enter an email or phone number')]")
	WebElement enterEmailMsg;
	public String blankEmailError() {
		return enterEmailMsg.getText();
	}

	@FindBy(xpath = "//*[@id='view_container']/descendant::div[contains(text(),'find your Google Account')]")
	WebElement invalidAccount;
	public String accountNotFoundError() {
		return invalidAccount.getText();
	}
	
	@FindBy(xpath = "//*[@id='password']/descendant::div[contains(text(),'Wrong password. Try again or click Forgot password to reset it')]")
	WebElement invalidPassword;
	public String invalidPasswordError() {
		return invalidPassword.getText();
	}

	public void loginToGmail(String email, String password) throws InterruptedException {
		log.info("Entering email");
		gmail_username1.clear();
		gmail_username1.sendKeys(email);
		Thread.sleep(2000);
		log.info("Clicking on Next button after email");
		email_nextButton.click();
		log.info("Entering password");
		gmail_password.sendKeys(password);
		Thread.sleep(2000);
		log.info("Clicking on Next button after password");
		pwd_nextButton.click();
	}

}
