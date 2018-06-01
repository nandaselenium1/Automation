package com.Gmail.GmailAutomation.facebook;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class SecurityValidation {
	
	public static WebDriver driver;
	
	public void launchInstance() throws InterruptedException{
		
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
		driver = new ChromeDriver();
		
		driver.get("https://www.vmware.com");
		Thread.sleep(10000);
		
	}
	
	public void checkHttpOnlyFlag(){
		Set<Cookie> cookies = driver.manage().getCookies();
		System.out.println(cookies.size());
		
		for(Cookie ck : cookies)	
			
			System.out.println("The cookie name is "+ck.getName()+" and HTTP flag is currently set to-->"+ck.isHttpOnly());
        /*{
			
			if(ck.getName().equals("BE_CLA3")){
			
				if(ck.isHttpOnly()==true){
				System.out.println("Please proceed your testing);
				}
			}
			
        }	*/
        
		
		/*Cookie cookie = getMyCookie("myCookieName");
		cookie.setHttpOnly(true);*/

	}	
		
	
	
	public void checkSecureFlag(){
		
		Set<Cookie> cookies = driver.manage().getCookies();
		System.out.println(cookies.size());
		
		for(Cookie ck : cookies)	
			
			System.out.println("The cookie name is "+ck.getName()+" and Secure flag is currently set to-->"+ck.isSecure());
        /*{
			
			if(ck.getName().equals("BE_CLA3")){
			
				if(ck.isSecure()==true){
				System.out.println("The webpage is secure, Please proceed your testing);
				}
			}
			
        }*/
        
	}		
	
	public void checkPath(){
		
		Set<Cookie> cookies = driver.manage().getCookies();
		System.out.println(cookies.size());
		
		for(Cookie ck : cookies)	
			
			
			
			System.out.println("The cookie name is "+ck.getName()+" and path flag is currently set to-->"+ck.getPath());
        /*{
			
			if(ck.getName().equals("BE_CLA3")){
			
				if(ck.getPath().length() > 1){
				System.out.println("Path is set please proceed);
				}
			}
			
        }*/
        
	}	
	
	public void checkServerInfo(){
		
		RestAssured.baseURI = "https://www.vmware.com";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get();
 
		//int statusCode = response.getStatusCode();
		//String line = response.getStatusLine();
		Headers options = response.getHeaders();
		if(options.getValue("Server").isEmpty()){
			
			System.out.println("The Server information is not present in the response header. Hence it is secure");						
		}else{
		String serverVersion = options.getValue("Server");
		
		//System.out.println("Status Code is "+statusCode);
		//System.out.println("Status Line is "+line);
		
		//System.out.println(options);
		
		System.out.println("The server name is "+serverVersion+ " Since this info is visible in response header, it is not secure");
		
		}
		         
	}
		
	public void closeInstance(){
		driver.close();
	}
	
	public void checkXframeOption(){
		
		RestAssured.baseURI = "https://www.vmware.com";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get();
 
		//int statusCode = response.getStatusCode();
		//String line = response.getStatusLine();
		Headers options = response.getHeaders();
		if(options.getValue("X-Frame-Options").isEmpty()){
			System.out.println("The Server information is not present in the response header. Hence it is secure");						
		}else{
		String frameOption = options.getValue("X-Frame-Options");
		
		//System.out.println("Status Code is "+statusCode);
		//System.out.println("Status Line is "+line);
		
		//System.out.println(options);
		
		System.out.println("The X-Frame-Options is "+frameOption+ " Since this info is visible in response header, it is not secure");
		
		}
		           
	}
		
	public static void main(String[] args) throws InterruptedException {
		
		SecurityValidation sv = new SecurityValidation();
		sv.launchInstance();
		sv.checkHttpOnlyFlag();
		sv.checkSecureFlag();
		sv.checkPath();
		sv.closeInstance();
		//sv.checkXframeOption();
		sv.checkServerInfo();
			
	}

}
	
	
