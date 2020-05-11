package Assignment1;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class Task5 extends BaseImplementation{
	@Test(groups= {"mandatory","Assignment1"})
    public void test5() throws IOException {
        Task5 ob=new Task5();
        Properties obj=ob.ReadObjectRepo();
        WebDriver driver=ob.driver();
        driver.get("https://demoqa.com/selectmenu/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        WebElement speed=driver.findElement(By.xpath(obj.getProperty("selectspeed")));
        speed.click();
        List<WebElement> l=driver.findElements(By.xpath(obj.getProperty("speeddropdownselect")));
        for(WebElement e:l)
        {
            if(e.getText().equalsIgnoreCase("Fast"))
                e.click();
        }
        System.out.println(speed.getText());
        Assert.assertTrue(speed.getText().equalsIgnoreCase("FAST"));
        
        WebElement file=driver.findElement(By.xpath(obj.getProperty("selectfile")));
        file.click();
        List<WebElement> fl=driver.findElements(By.xpath(obj.getProperty("filedropdownselect")));
        for(WebElement e:fl)
        {
            if(e.getText().equalsIgnoreCase("ui.jQuery.js"))
                e.click();
        }
        System.out.println(file.getText());
        Assert.assertTrue(file.getText().equalsIgnoreCase("ui.jQuery.js"));
        
        WebElement no=driver.findElement(By.xpath(obj.getProperty("selectnumber")));
        no.click();
        List<WebElement> nl=driver.findElements(By.xpath(obj.getProperty("numberdropdownselect")));
        for(WebElement e:nl)
        {
            if(e.getText().equalsIgnoreCase("3"))
                e.click();
        }
        System.out.println(no.getText());
        Assert.assertTrue(no.getText().equalsIgnoreCase("3"));
        WebElement title=driver.findElement(By.xpath(obj.getProperty("selecttitle")));
        title.click();
        List<WebElement> tl=driver.findElements(By.xpath(obj.getProperty("titledropdownselect")));
        for(WebElement e:tl)
        {
            if(e.getText().equalsIgnoreCase("Mr."))
                e.click();
        }
        System.out.println(title.getText());
        Assert.assertTrue(title.getText().equalsIgnoreCase("Mr."));
        System.out.println("Test Pass");
        driver.close();
    }
}		
		



