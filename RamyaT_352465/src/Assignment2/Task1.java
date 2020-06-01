package Assignment2;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import Assignment1.BaseImplementation;

public class Task1 extends BaseImplementation{
    WebDriver driver=null;
    
    @DataProvider(name = "RegisterandLoginData")     
      public static Object[][] credentials() {     
           return new Object[][] { { "ramya28@gmail.com","Qwerty@1","Qwerty@1","11","5","1992"}};
    }
    @Test(dataProvider="RegisterandLoginData",priority=0)
    public void test1(String email,String pass,String confirmpass,String day,String month,String year) throws IOException, InterruptedException {
    logger = extent.startTest("Assignment2_test1");	  
    Task1 ob=new Task1();
    Properties obj=ob.ReadObjectRepo();
    driver=ob.driver();
    if(ob.ReadCountry().getProperty("country").equalsIgnoreCase("UK"))
    {
        driver.get("https://www.olay.co.uk/en-gb");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        ob.registerOlay(email, pass, confirmpass, day, month, year, driver);
        ob.signoutOlay(driver);
        ob.signinOlay(email, pass, driver);
        ob.signoutOlay(driver);
    }
    
    else if(ob.ReadCountry().getProperty("country").equalsIgnoreCase("Germany"))
    {
        driver.get("https://www.olaz.de/de-de");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        String[] od=ob.ReadOlayData("olayuser");
        String fn=od[1],sn=od[2],Email=od[3],Pass=od[4],ConfirmPass=od[5],Day=od[6],Month=od[7],Year=od[8],strabe=od[9],postle=od[10],ort=od[11];
        ob.registerOlayGermany(fn,sn,Email,Pass,ConfirmPass,Day,Month,Year,strabe,postle,ort, driver);
        ob.signoutOlay(driver);
        ob.signinOlay(Email, Pass, driver);
        ob.signoutOlay(driver);
    }
    
    else if(ob.ReadCountry().getProperty("country").equalsIgnoreCase("Spain")) 
    {
        driver.get("https://www.olay.es/es-es");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        //data will be taken from properties file and the method is implemented in baseimplementation class
        ob.registerOlaySpain(driver);
        Properties p=ob.ReadOlayProperties();
        String Email=p.getProperty("email"),Pass=p.getProperty("pass");
        ob.signinOlay(Email, Pass, driver);
        ob.signoutOlay(driver);
    }
    
    else
        System.out.println("Enter valid country, allowed countries are UK, Germany, Spain");
        logger.log(LogStatus.PASS, "test case passed");
    }
    
    
    @DataProvider(name = "LoginDetails")     
      public static Object[][] Details() {     
           return new Object[][] { { "ramya1@gmail.com","Qwerty@1"},{ "ramya1@gmail.com","Qwerty@2"}};
    }
    @Test(dataProvider="LoginDetails",priority=2)
    public void signinvalidation(String email,String pass) throws IOException
    {   
    	logger = extent.startTest("Assignment2_test2");	
    	Task1 ob=new Task1();
        driver.get("https://www.olay.co.uk/en-gb");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        driver.findElement(By.xpath(ob.ReadObjectRepo().getProperty("signin"))).click();
        driver.findElement(By.xpath(ob.ReadObjectRepo().getProperty("signinemail"))).sendKeys(email);
        driver.findElement(By.xpath(ob.ReadObjectRepo().getProperty("signinpassword"))).sendKeys(pass);
        driver.findElement(By.xpath(ob.ReadObjectRepo().getProperty("signinsubmit"))).click();
        logger.log(LogStatus.PASS, "test case passed");
    }   
    
      @AfterTest 
      public void driverclose()
      { 
          driver.close();
          
	        
      }
    

}