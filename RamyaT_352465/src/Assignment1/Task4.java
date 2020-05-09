package Assignment1;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class Task4 extends BaseImplementation{

	@Test
    public void test4() throws IOException {
        Task4 ob=new Task4();
        Properties obj=ob.ReadObjectRepo();
        WebDriver driver=ob.driver();
		driver.get("https://demoqa.com/datepicker/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		
		String dob="01/23/1995";
		WebElement element=driver.findElement(By.id(obj.getProperty("datepick")));
		element.sendKeys(dob);
		element.sendKeys(Keys.ENTER);
		System.out.println("Test Pass");
		driver.close();	
	}
}

