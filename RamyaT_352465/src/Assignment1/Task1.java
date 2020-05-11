package Assignment1;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class Task1 extends BaseImplementation{
    @Test(groups= {"mandatory","Assignment1"})
    public void test1() throws IOException {
        Task1 ob=new Task1();
        Properties obj=ob.ReadObjectRepo();
        WebDriver driver=ob.driver();	
        driver.get("https://demoqa.com/selectable/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		
		//List of items
		List<WebElement> list=driver.findElements(By.xpath(obj.getProperty("SelectableItem")));
		for (WebElement webElement : list) {
            String name = webElement.getText();
            System.out.println(name);
		}
		System.out.println("Test Pass");
		
		driver.close();
	}

}
