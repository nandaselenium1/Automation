package com.Gmail.GmailAutomation.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.Gmail.GmailAutomation.excelReader.Excel_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestBase {
	
	//TestBase is the parent class of all the classes. 
	//In this class we will have all reusable methods and all required configurations for your project
	//It is a common functionality (e.g. create object of browser, navigate to url etc.), which every script requires
	
	//After registering/initializing the log4j, then we need to add the below line, wherever we need to create logs
	//Now i want to log for the class 'TestBase' (We can pass any class as an argument for 'getLogger' method for which we want create the logs)
	public static final Logger log = Logger.getLogger(TestBase.class.getName());
	
	public static WebDriver driver;
	public Properties CONFIG;
	Excel_Reader excel;
	public static ExtentReports extent;
	public static ExtentTest test;
	public ITestResult result;
	
	static{
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		
		extent = new ExtentReports(System.getProperty("user.dir")+"/src/main/java/com/Gmail/GmailAutomation/report/test"+formater.format(calendar.getTime())+".html", false);
		
		
	}
	
	public void loadData() throws IOException {
		CONFIG = new Properties();
		File file = new File(System.getProperty("user.dir") + "/src/main/java/com/Gmail/GmailAutomation/config/config.properties");
		FileInputStream fi = new FileInputStream(file);
		CONFIG.load(fi);
	}
	
	public void launchGmailUrl() throws IOException{
		loadData();
		//extent = new ExtentReports(System.getProperty("user.dir")+"/src/main/java/com/Gmail/GmailAutomation/report/test.html", false);
		selectBrowser(CONFIG.getProperty("browser"));
		getUrl(CONFIG.getProperty("gmail_url"));
		
		//We use 'log4j' for writing the logs. Like what are the steps we have performed, what are the script have been started and finished.
		//Once we created 'log4j.properties', then we need to register/initialize that using below 2 lines
		String log4jConfPath = "log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
	}
	
	public void getUrl(String url) {
		log.info("Navigating to: "+url);
		driver.get(url);
		sleep(5000);
		//driver.manage().window().maximize();
		//driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	public void selectBrowser(String browser) {
		System.out.println(System.getProperty("os.name"));
		if (System.getProperty("os.name").contains("Window")) {
			
			if (browser.equals("firefox")) {
				driver = new FirefoxDriver();
			
			} else if (browser.equals("chrome")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
				log.info("Creating object of "+browser);
				
				//Please add below 5 lines of code to click 'Allow' on Show Notifications popup using Selenium Webdriver, during runtime. 
				//It won't affect other peace of code which doesn't have notifications

				//Create prefs map to store all preferences. 
	//			Map<String, Object> prefs = new HashMap<String, Object>();

				//Put this into prefs map to switch off browser notification
		//		prefs.put("profile.default_content_setting_values.notifications", 2);

				//Create chrome options to set this prefs
		//		ChromeOptions options = new ChromeOptions();
		//		options.setExperimentalOption("prefs", prefs);

				//Now initialize chrome driver with chrome options which will switch off this browser notification on the chrome browser
		//		driver = new ChromeDriver(options);
				driver = new ChromeDriver();
			} else if (browser.equals("ie")) {
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "/drivers/IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}
		} else if (System.getProperty("os.name").contains("Mac")) {
			if (browser.equals("chrome")) {
				System.out.println(System.getProperty("user.dir"));
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver");
				driver = new ChromeDriver();
			} else if (browser.equals("firefox")) {
				System.out.println(System.getProperty("user.dir"));
				System.setProperty("webdriver.firefox.marionette", System.getProperty("user.dir") + "/drivers/geckodriver");
				driver = new FirefoxDriver();
			}
		}
	}

	public String[][] getData(String excelName, String sheetName) {
		String path = System.getProperty("user.dir") + "/src/main/java/com/Gmail/GmailAutomation/data/" + excelName;
		excel = new Excel_Reader(path);
		String[][] data = excel.getDataFromSheet(sheetName, excelName);
		return data;
	}
	
	//We can make use of the below function in case of specific place where we want a screenshot. 
	//Since currently i am taking the screenshot automatically using Listener, so commenting this function
	/*public void getScreenShot(String name) {

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/src/main/java/com/test/automation/wrapperAutomation/screenshot/";
			File destFile = new File((String) reportDirectory + name + "_" + formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
			// This will help us to link the screen shot in testNG report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	public String getTimestamp(){
		SimpleDateFormat sdfDate = new SimpleDateFormat("hh:mm:ss");
	    Date now = new Date();
	    return sdfDate.format(now);
	}
	
	public void waitForElement(WebDriver driver,String path){
		WebDriverWait wait=new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(path)));
	}
	
	public static final void sleep(final int timeoutInMS){
		try { 
			Thread.sleep(timeoutInMS);
		} catch(Exception e) {}
	}
	
	public String captureScreen(String fileName) {
		
		if(fileName==""){
			
			fileName="blank";
			
		}
		
		File destFile = null;

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + "/src/main/java/com/Gmail/GmailAutomation/screenshots/";
			destFile = new File((String) reportDirectory + fileName + "_" + formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
			// This will help us to link the screen shot in testNG report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile.toString();
	}
	
	public void getResult(ITestResult result){
		
		if(result.getStatus()==ITestResult.SUCCESS){
			test.log(LogStatus.PASS, result.getName()+" test is pass");
		}
		else if(result.getStatus()==ITestResult.SKIP){
			test.log(LogStatus.SKIP, result.getName()+" test is sipped and skip reason is:-"+result.getThrowable());
		}
		else if(result.getStatus()==ITestResult.FAILURE){
			test.log(LogStatus.ERROR, result.getName()+" test is failed:-"+result.getThrowable());
			//test.log(LogStatus.FAIL, test.addScreenCapture(captureScreen("")));
			
			String screen = captureScreen("");
			test.log(LogStatus.FAIL, test.addScreenCapture(screen));
		}
		else if(result.getStatus()==ITestResult.STARTED){
			test.log(LogStatus.INFO, result.getName()+" test is started");
		}
	}
	
	public void closeBrowser(){
		driver.quit();
		log.info("Browser Closed");
		extent.endTest(test);
		extent.flush();
	}
	
	@AfterMethod()
	public void afterMethod(ITestResult result){
		getResult(result);
	}
	
	@BeforeMethod()
	public void beforeMethod(Method result){
		test= extent.startTest(result.getName());
		test.log(LogStatus.INFO, result.getName()+" test is started");
	}
	
	@AfterClass(alwaysRun=true)
	public void endTest(){
		closeBrowser();
	}
	
}	
