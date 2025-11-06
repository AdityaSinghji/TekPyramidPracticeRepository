package com.ninzahrm.projects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fileutility.ExcelUtility;
import com.fileutility.FileUtility;
import com.javautility.JavaUtility;
import com.webdriverutility.WebdriverUtility;

public class NinzaHrmMockScript {
	
	@Test
	public void script() throws Throwable
	{
        ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("profile.password_manager_leak_detection", false);	
		options.setExperimentalOption("prefs", prefs);
		
		WebDriver driver = new ChromeDriver(options);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		
		WebdriverUtility w = new WebdriverUtility();
		FileUtility f = new FileUtility();
		JavaUtility j = new JavaUtility();
		ExcelUtility e = new ExcelUtility();
		
		
		w.waitImplicit(driver);
		driver.manage().window().maximize();
		js.executeScript("document.body.style.zoom='80%'");
		
		
		//Login
		driver.get(f.getDataFromPropertiesFile("url"));
		driver.findElement(By.id("username")).sendKeys(f.getDataFromPropertiesFile("username"));;
		driver.findElement(By.id("inputPassword")).sendKeys(f.getDataFromPropertiesFile("password"));
		driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();
		
		
		//Creating a project
		driver.findElement(By.linkText("Projects")).click();
		driver.findElement(By.xpath("//button[@class=\"btn btn-success\"]")).click();
		
		String projectName = e.getDataFromExcel("Projects", 1, 0)+j.getRandomText();
		
		driver.findElement(By.name("projectName")).sendKeys(projectName);
		driver.findElement(By.name("createdBy")).sendKeys(e.getDataFromExcel("Projects", 1, 1));
		
		WebElement ele = driver.findElement(By.xpath("//label[.='Project Status* ' ]/..//select"));
		String status = e.getDataFromExcel("Projects", 1, 2);
		
		w.select(ele, status);
		driver.findElement(By.xpath("//input[@type=\"submit\"]")).click();
		
		//Verify if the project is created
		
		w.select(driver.findElement(By.xpath("//div[@class=\"col-sm-6\"]//select")), e.getDataFromExcel("Projects", 1, 3));
		driver.findElement(By.xpath("//input[@placeholder=\"Search by Project Name\"]")).sendKeys(projectName);
		
		List<WebElement> pNames = driver.findElements(By.xpath("//table[@class='table table-striped table-hover']//tbody/tr"));
		
		boolean flag = false;
		
		for(WebElement pName : pNames )
		{
			if(pName.getText().contains(projectName))
			{
				flag = true;
			}
		}
		
		if(flag==true)
		{
			System.out.println("Project created successfully");
			w.takeScreenshotOfPage(driver, "./Screenshots/Pro",projectName );
		}
			
		else
		{
			System.out.println("Project is not created");
			Assert.fail();
		}
			
		//Creating employee with the same project
		
		driver.findElement(By.linkText("Employees")).click();
		driver.findElement(By.xpath("//span[text()='Add New Employee']")).click();
	
		String empName = e.getDataFromExcel("Employees", 1, 0)+j.getRandomText();
		driver.findElement(By.xpath("//label[text()='Name*']/following-sibling::input")).sendKeys(empName);
		driver.findElement(By.xpath("//label[text()='Email*']/following-sibling::input")).sendKeys(e.getDataFromExcel("Employees", 1, 1));
		driver.findElement(By.xpath("//label[text()='Phone*']/following-sibling::input")).sendKeys(e.getDataFromExcel("Employees", 1, 2));
		driver.findElement(By.xpath("//label[text()='Username*']/following-sibling::input")).sendKeys(e.getDataFromExcel("Employees", 1, 3)+j.getRandomText());
		driver.findElement(By.xpath("//label[text()='Designation*']/following-sibling::input")).sendKeys(e.getDataFromExcel("Employees", 1, 4));
		driver.findElement(By.xpath("//label[text()='Experience*']/following-sibling::input")).sendKeys(e.getDataFromExcel("Employees", 1, 5));
		w.select(driver.findElement(By.xpath("//label[text()=' Project*']/following-sibling::select")), projectName);
		
		
		
		WebElement addBtn = driver.findElement(By.xpath("//label[text()=' Project*']/ancestor::div[@class='modal-body']/following-sibling::div/input[@value='Add']"));
		
		js.executeScript("arguments[0].click()", addBtn);
//		System.out.println("Employee created");
		
		//verify if the Employee is created
		
//		driver.findElement(By.xpath("//input[@placeholder=\"Search by Employee Name\"]")).sendKeys(empName);
//		driver.findElement(By.xpath("//a[@aria-label=\"Go to next page\"]")).click();
		Thread.sleep(3000);
		List<WebElement> eNames = driver.findElements(By.xpath("//table[@class=\"table table-striped table-hover\"]/tbody/tr"));
		
		boolean flag1 = false;
		
		for(WebElement eName : eNames )
		{
			if(eName.getText().contains(empName))
			{
				flag1 = true;
			}
		}
		
		if(flag1==true)
		{
			System.out.println("Employee created successfully");
			w.takeScreenshotOfPage(driver, "./Screenshots/Emp",empName );
			driver.quit();
		}
			
		else
		{
			System.out.println("Employee is not created");
			Assert.fail();
		}
		
		
	}

}
