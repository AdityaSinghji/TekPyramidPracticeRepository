package com.webdriverutility;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.Select;

public class WebdriverUtility {
	
	public void waitImplicit(WebDriver driver)
	{
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	public void select(WebElement element, String text)
	{
		Select sel = new Select(element);
		sel.selectByVisibleText(text);
	}
	
	public void select(WebElement element, int index)
	{
		Select sel = new Select(element);
		sel.selectByIndex(index);
	}
	
	public void takeScreenshotOfElement(WebElement element ,String destinationPath, String fileName ) throws IOException
	{
		Date d = new Date();
		String d1= d.toString().replace(":", "_");
		File temp = element.getScreenshotAs(OutputType.FILE);
		File perm = new File(destinationPath+d1+fileName+".png");
		
		FileHandler.copy(temp, perm);
	}
	
	public void takeScreenshotOfPage(WebDriver driver, String destinationPath, String fileName ) throws IOException
	{
		Date d = new Date();
		String newd = d.toString().replace(":", "_");
		TakesScreenshot ts = (TakesScreenshot) driver;
		File temp = ts.getScreenshotAs(OutputType.FILE);
		File perm = new File(destinationPath+"_"+newd+fileName+".png");
		
		FileHandler.copy(temp, perm);
	}
	

}
