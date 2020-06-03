package Assignment1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import au.com.bytecode.opencsv.CSVReader;

import org.apache.commons.io.FileUtils;



public class BaseImplementation {

    public static ExtentReports extent;
    public static ExtentTest logger;
    public static WebDriver driver = null;
    //for extent report generation    
    @BeforeSuite
    public void setUp() {
        
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        extent = new ExtentReports(System.getProperty("user.dir") + "\\ExtentOutput\\Extentreports.html", true);
        extent.addSystemInfo("Host Name", "Ramya").addSystemInfo("Environment", "Automation Testing")
                .addSystemInfo("User Name", "Ramya");
        extent.loadConfig(new File(System.getProperty("user.dir") + "\\src\\ExtentReports\\extent-config.xml"));
    }
    
    @AfterMethod
    public void getResult(ITestResult result) throws IOException, Exception {
        logger.log(LogStatus.INFO, "checking whether test is passed or not, if test is failed attaching the screenshot");
        if (result.getStatus() == ITestResult.FAILURE) {
        logger.log(LogStatus.FAIL, "Testcase failed is: " + result.getName());
        logger.log(LogStatus.FAIL, "Testcase failed is: " + result.getThrowable());
        // To capture screenshot path and store the path of the screenshot in the string
        // "screenshotPath"
        // We do pass the path captured by this method in to the extent reports using
         //"logger.addScreenCapture" method.
        //String screenshotPath = BaseImplementation.getScreenshot(driver, result.getName());
        // To add it in the extent report
        //logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath));
        } else if (result.getStatus() == ITestResult.SKIP) {
        logger.log(LogStatus.SKIP, "testcase skipped is: " + result.getName());

        }
    }

 

    @AfterSuite
    public void testReport() {
        logger.log(LogStatus.INFO, "flushing entire extent data");
        extent.flush();
    }
    
	/*public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
        // below line is just to append the date format with the screenshot name to
        // avoid duplicate names
        logger.log(LogStatus.INFO, "taking screenshot");
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        // after execution, you could see a folder "FailedTestsScreenshots" under src
        // folder
        String destination = System.getProperty("user.dir") + "\\FailedTestsScreenshots\\" + screenshotName + dateName
                + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }*/
    
    
	public WebDriver driver() throws IOException
	{
		WebDriver driver = null;
		if(ReadBrowserProp().getProperty("browser").equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Selenium\\chrome\\chromedriver.exe");
			driver=new ChromeDriver();
		}
		else if(ReadBrowserProp().getProperty("browser").equalsIgnoreCase("firefox"))
		{
			try {
			System.setProperty("webdriver.gecko.driver", "C:\\Program Files\\Selenium\\firefox\\GeckoDriver.exe");
			driver=new FirefoxDriver();
			}
			catch(Exception e){
				System.setProperty("webdriver.gecko.driver", "C:\\Program Files\\Selenium\\firefox\\GeckoDriver.exe");
				driver=new FirefoxDriver();	
			}
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
