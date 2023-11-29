
package com.tutorialsninja_testbase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.tutorialsninja.utilities.Utilities;

public class TestBase {
	
	public WebDriver driver;
	public ChromeOptions options;
	public Properties prop;
	public Properties dataprop;
	public FileInputStream ip;
	
	
	
	public TestBase()  {
		 prop= new Properties();
		 try {
			ip = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\tutorialsninja_config\\config.properties");
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		 try {
			prop.load(ip);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		 
		 
		 
		 dataprop= new Properties();
		 try {
			ip = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\java\\com\\tutorialsninja_Testdata\\systemData.properties");
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		 try {
			dataprop.load(ip);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public WebDriver initializeBrowserAndOpenApplication(String browserName) {
		
		if(browserName.equals("chrome")) {
			options=new ChromeOptions();
			options.setPageLoadStrategy(PageLoadStrategy.EAGER);
			options.addArguments("--start-maximized");
			options.addArguments("--incognito");
			options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation","disable-infobars"));
			driver=new ChromeDriver(options);
			
		}else if(browserName.equals("firefox")) {
			driver=new FirefoxDriver();
			driver.manage().window().maximize();
			
		}else if(browserName.equals("edge")) {
			driver=new EdgeDriver();
			driver.manage().window().maximize();
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Utilities.IMPLICIT_WAIT));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Utilities.PAGE_LOAD_TIME));
		driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(Utilities.SCRIPT_TIME));
		driver.get(prop.getProperty("url"));
		return driver;
		
		
	}

}
