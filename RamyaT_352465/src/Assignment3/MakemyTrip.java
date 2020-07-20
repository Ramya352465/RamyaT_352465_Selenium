package Assignment3;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.LogStatus;

import Assignment1.BaseImplementation;


public class MakemyTrip extends BaseImplementation {
	    
	    @Test(groups= {"mandatory","Assignmen3"})
	    public void test1() throws IOException, InterruptedException {
	    logger = extent.startTest("Assignment3_test1");	    
	    MakemyTrip ob=new MakemyTrip();
	    Properties obj=ob.ReadObjectRepo();
	    WebDriver driver=ob.driver();
	    driver.get("https://www.makemytrip.com/");
	    driver.manage().window().maximize();
	    driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
	    //selecting trip round trip option
	    driver.findElement(By.xpath(obj.getProperty("tripoption"))).click();
	    driver.findElement(By.id(obj.getProperty("FromCity"))).click();
	    driver.findElement(By.xpath(obj.getProperty("FirstOptioninfromcity"))).click();
	    driver.findElement(By.xpath(obj.getProperty("ToCity"))).click();
	    driver.findElement(By.xpath(obj.getProperty("secondoptionintocity"))).click();
	    driver.findElement(By.xpath(obj.getProperty("Departure"))).click();
	    driver.findElement(By.xpath(obj.getProperty("SelectDate"))).click();
	    String DepartDate="5";
	    String ReturnDate="10";
	    String DepartMonth="August2020";
	    while(true)
	        {
	        String month=driver.findElement(By.xpath(obj.getProperty("SelectDate"))).getText();
	        System.out.println(month);
	        if(month.equalsIgnoreCase(DepartMonth)) 
	        {
	              break;
	        }
	        else
	        {
	        driver.findElement(By.xpath(obj.getProperty("NavigateIcon"))).click();
	        }
	        }
	         List<WebElement> date= driver.findElements(By.xpath(obj.getProperty("datevalue")));
	         //for selecting departure date
	         for(WebElement e:date)
	         {
	             if(e.getText().contains(DepartDate)) {
	                 e.click();
	                 break;
	             }
	         }
	         //for selecting return date.
	         for(WebElement e:date)
	         {
	             if(e.getText().contains(ReturnDate)) {
	                e.click();
	                break;
	            }
	         }
	         driver.findElement(By.xpath(obj.getProperty("SearchButton"))).click();
	         String pricesort1= driver.findElement(By.xpath(obj.getProperty("upwardsort"))).getText();
		     String pricesort2= driver.findElement(By.xpath(obj.getProperty("returnsort"))).getText();
		     Assert.assertTrue(((pricesort1.contains("Price"))&&pricesort2.contains("Price")));
		     try
		     {
		     driver.findElement(By.xpath(obj.getProperty("Viewfare"))).click();
		     }
		     catch(ElementClickInterceptedException e)
		     {
		     driver.findElement(By.xpath(obj.getProperty("Viewfare"))).click(); 
		     }
		     jsclick(driver,"BookButton");
		     jsclick(driver,"continuebookingbutton");
		     ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		     System.out.println("No. of tabs: " + tabs.size());
		     if(tabs.size()>1)
		     System.out.println("review page is opened!");
		     else
		     System.out.println("review page is not opened!");
		     driver.quit();   
		     logger.log(LogStatus.PASS, "test case passed");
	        
	}
}