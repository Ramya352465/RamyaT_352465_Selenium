package Assignment1;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import au.com.bytecode.opencsv.CSVReader;

public class BaseImplementation {
	
	public WebDriver driver() throws IOException
	{
		WebDriver driver = null;
		if(ReadBrowserProp().getProperty("browser").equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Selenium\\chromedriver.exe");
			driver=new ChromeDriver();
		}
		else if(ReadBrowserProp().getProperty("browser").equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "C:\\Program Files\\Selenium\\GeckoDriver.exe");
			driver=new FirefoxDriver();
		}
		return driver;
	}
	
	
	public String[] ReadData(String usertype) throws IOException {
		
		String[] str = null;
		CSVReader reader = new CSVReader(new FileReader(System.getProperty("user.dir")+"\\src\\Data\\data.csv"));

		List<String[]> list=reader.readAll();
		System.out.println("Total rows which we have is "+list.size());
		            
		// create Iterator reference
		Iterator<String[]>iterator= list.iterator();
		    
		// Iterate all values 
		while(iterator.hasNext()){
		     
		str=iterator.next();
		   
		if(str[0].equalsIgnoreCase(usertype))
		{
			 break;
		}
		}
		return str;
		
	}
	
	public Properties ReadCountry() throws IOException
    {
        Properties obj = new Properties();
        FileInputStream objfile = new FileInputStream(System.getProperty("user.dir")+"\\src\\Data\\country.properties");
        obj.load(objfile);
        return obj;
    }
	
	public void registerOlay(String email,String pass,String confirmpass,String day,String month,String year,WebDriver driver) throws IOException
    {
       
        driver.findElement(By.xpath(ReadObjectRepo().getProperty("Register"))).click();
        driver.findElement(By.xpath(ReadObjectRepo().getProperty("registeremail"))).sendKeys(email);
        driver.findElement(By.xpath(ReadObjectRepo().getProperty("registerpass"))).sendKeys(pass);
        driver.findElement(By.xpath(ReadObjectRepo().getProperty("confirmpass"))).sendKeys(confirmpass);
        Select day1=new Select(driver.findElement(By.xpath(ReadObjectRepo().getProperty("day"))));
        day1.selectByIndex(Integer.parseInt(day));
        Select month1=new Select(driver.findElement(By.xpath(ReadObjectRepo().getProperty("month"))));
        month1.selectByVisibleText(month);
        Select year1=new Select(driver.findElement(By.xpath(ReadObjectRepo().getProperty("year"))));
        year1.selectByValue((year));
        jsclick(driver,"createprofile");
        jsclick(driver,"skipthisstep");
        Assert.assertTrue(driver.findElement(By.xpath(ReadObjectRepo().getProperty("signout"))).isDisplayed());
        System.out.println("Registration completed successfully");  
    }

    public void signoutOlay(WebDriver driver) throws IOException
    {
        WebElement signout=driver.findElement(By.xpath(ReadObjectRepo().getProperty("signout")));
        signout.click();
        WebElement confirmsignout=driver.findElement(By.xpath(ReadObjectRepo().getProperty("signoutconfirm")));
        confirmsignout.click();
        if(driver.findElement(By.xpath(ReadObjectRepo().getProperty("welcometext"))).isDisplayed())
        {
            jsclick(driver,"signout");
            jsclick(driver,"signoutconfirm");
        }
        Assert.assertTrue(driver.findElement(By.xpath(ReadObjectRepo().getProperty("signin"))).isDisplayed());
    }
    
    public void signinOlay(String email,String pass,WebDriver driver) throws IOException
    {
        driver.findElement(By.xpath(ReadObjectRepo().getProperty("signin"))).click();
        driver.findElement(By.xpath(ReadObjectRepo().getProperty("signinemail"))).sendKeys(email);
        driver.findElement(By.xpath(ReadObjectRepo().getProperty("signinpassword"))).sendKeys(pass);
        driver.findElement(By.xpath(ReadObjectRepo().getProperty("signinsubmit"))).click();
        Assert.assertTrue(driver.findElement(By.xpath(ReadObjectRepo().getProperty("welcometext"))).isDisplayed());  
    }
    
    public String[] ReadOlayData(String usertype) throws IOException {
        
        String[] str = null;
        CSVReader reader = new CSVReader(new FileReader(System.getProperty("user.dir")+"\\src\\Data\\OlayData.csv"));
        
        List<String[]> list=reader.readAll();
        System.out.println("Total rows which we have is "+list.size());
                    
        // create Iterator reference
        Iterator<String[]>iterator= list.iterator();
            
        // Iterate all values 
        while(iterator.hasNext()){
             
        str=iterator.next();
           
        if(str[0].equalsIgnoreCase(usertype))
         {
             break;
         }
         }
        return str;
     }

     public void registerOlayGermany(String fn,String sn,String email,String pass,String confirmpass,String day,String month,String year,String strabe,String postle,String ort,WebDriver driver) throws IOException
     {
         driver.findElement(By.xpath(ReadObjectRepo().getProperty("Register"))).click();
         driver.findElement(By.xpath(ReadObjectRepo().getProperty("gender"))).click();
         driver.findElement(By.xpath(ReadObjectRepo().getProperty("vorname"))).sendKeys(fn);
         driver.findElement(By.xpath(ReadObjectRepo().getProperty("nachname"))).sendKeys(sn);
         driver.findElement(By.xpath(ReadObjectRepo().getProperty("registeremail"))).sendKeys(email);
         driver.findElement(By.xpath(ReadObjectRepo().getProperty("registerpass"))).sendKeys(pass);
         driver.findElement(By.xpath(ReadObjectRepo().getProperty("confirmpass"))).sendKeys(confirmpass);
         Select day1=new Select(driver.findElement(By.xpath(ReadObjectRepo().getProperty("day"))));
         day1.selectByIndex(Integer.parseInt(day));
         Select month1=new Select(driver.findElement(By.xpath(ReadObjectRepo().getProperty("month"))));
         month1.selectByIndex(Integer.parseInt(month));
         Select year1=new Select(driver.findElement(By.xpath(ReadObjectRepo().getProperty("year"))));
         year1.selectByValue((year));
         Select land=new Select(driver.findElement(By.xpath(ReadObjectRepo().getProperty("land"))));
         land.selectByIndex(1);
         driver.findElement(By.xpath(ReadObjectRepo().getProperty("strabe"))).sendKeys(strabe);
         driver.findElement(By.xpath(ReadObjectRepo().getProperty("postle"))).sendKeys(postle);
         driver.findElement(By.xpath(ReadObjectRepo().getProperty("ort"))).sendKeys(ort);
         jsclick(driver,"meinkonto");
         Assert.assertTrue(driver.findElement(By.xpath(ReadObjectRepo().getProperty("signout"))).isDisplayed());
         System.out.println("Registration completed successfully");
     }

     public Properties ReadOlayProperties() throws IOException
    {
         Properties obj = new Properties();
         FileInputStream objfile = new FileInputStream(System.getProperty("user.dir")+"\\src\\Data\\OlayData.properties");
         obj.load(objfile);
         return obj;
     }

     public void registerOlaySpain(WebDriver driver) throws IOException
     {
         driver.findElement(By.xpath(ReadObjectRepo().getProperty("Register"))).click();
         driver.findElement(By.xpath(ReadObjectRepo().getProperty("gender"))).click();
         driver.findElement(By.xpath(ReadObjectRepo().getProperty("vorname"))).sendKeys(ReadOlayProperties().getProperty("fn"));
         driver.findElement(By.xpath(ReadObjectRepo().getProperty("nachname"))).sendKeys(ReadOlayProperties().getProperty("sn"));
         driver.findElement(By.xpath(ReadObjectRepo().getProperty("registeremail"))).sendKeys(ReadOlayProperties().getProperty("email"));
         driver.findElement(By.xpath(ReadObjectRepo().getProperty("registerpass"))).sendKeys(ReadOlayProperties().getProperty("pass"));
         driver.findElement(By.xpath(ReadObjectRepo().getProperty("confirmpass"))).sendKeys(ReadOlayProperties().getProperty("confirmpass"));
         Select day1=new Select(driver.findElement(By.xpath(ReadObjectRepo().getProperty("day"))));
         day1.selectByIndex(Integer.parseInt(ReadOlayProperties().getProperty("day")));
         Select month1=new Select(driver.findElement(By.xpath(ReadObjectRepo().getProperty("month"))));
         month1.selectByVisibleText(ReadOlayProperties().getProperty("month"));
         Select year1=new Select(driver.findElement(By.xpath(ReadObjectRepo().getProperty("year"))));
         year1.selectByValue((ReadOlayProperties().getProperty("year")));
         jsclick(driver,"meinkonto");
         Assert.assertTrue(driver.findElement(By.xpath(ReadObjectRepo().getProperty("signinemail"))).isDisplayed());
    
     }
	
     public void jsclick(WebDriver driver,String value) throws IOException
     {
         WebElement element = driver.findElement(By.xpath(ReadObjectRepo().getProperty(value)));
         JavascriptExecutor executor = (JavascriptExecutor)driver;
         executor.executeScript("arguments[0].click();", element);
     } 

	 public Properties ReadObjectRepo() throws IOException
	 {
		 Properties obj = new Properties();
		 FileInputStream objfile = new FileInputStream(System.getProperty("user.dir")+"\\src\\ObjectRepository\\ObjectRepository.properties");
		 obj.load(objfile);
		 return obj;
	 }
	
	 public Properties ReadBrowserProp() throws IOException
	 {
		 Properties obj = new Properties();
		 FileInputStream objfile = new FileInputStream(System.getProperty("user.dir")+"\\src\\Data\\browser.properties");
		 obj.load(objfile);
		 return obj;
	 }

}
