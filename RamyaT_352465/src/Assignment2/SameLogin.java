package Assignment2;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.*; 

import Assignment1.BaseImplementation;

public class SameLogin extends BaseImplementation {

	WebDriver driver = null;
	String strReusableVar, strFileDir, strEmailId, strXpath, phoneNumber;
	Select select;
	WebElement webElement;
	boolean subscribe;
	
   @Test
  	public void setUpDriver() throws IOException, InterruptedException { 
  	   SameLogin ob= new SameLogin();
  	   WebDriver driver=ob.driver();
	   driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  	}
  	
   @Test
    public void olayWebsiteRegistrationLogin() throws InterruptedException {
		
		//Launch App URL
		launchApplication(driver, "Assignment2_URL");
		if(!driver.findElement(By.xpath(getElementLocator("OlayHomePageUK"))).isDisplayed())
			Assert.fail("Olay UK Home Page is Not Opened");
		
		webElement = driver.findElement(By.xpath(getElementLocator("AcceptAllCookiesUK")));
		if(webElement.isDisplayed())
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", webElement);
	    
		driver.findElement(By.xpath(getElementLocator("ClickRegisterUK"))).click();

		if(!driver.findElement(By.xpath(getElementLocator("CreateProfileOlay"))).isDisplayed())
			Assert.fail("Olay UK Register Page is Not Opened");
		//Enter Email Id
		strEmailId = getEmailId();
		driver.findElement(By.xpath(getElementLocator("EmailFieldOlay"))).sendKeys(strEmailId);
		driver.findElement(By.xpath(getElementLocator("PasswordOlay"))).sendKeys((String)jsonObject.get("PasswordUK"));
		driver.findElement(By.xpath(getElementLocator("ConfmPasswordOlay"))).sendKeys((String)jsonObject.get("PasswordUK"));
		//Select Birth Date
		select = new Select(driver.findElement(By.xpath(getElementLocator("SelectBirthDay"))));
		select.selectByValue((String)jsonObject.get("BirthDayUK"));
		select = new Select(driver.findElement(By.xpath(getElementLocator("SelectBirthMonth"))));
		select.selectByValue((String)jsonObject.get("BirthMonthUK"));
		select = new Select(driver.findElement(By.xpath(getElementLocator("SelectBirthYear"))));
		select.selectByValue((String)jsonObject.get("BirthYearUK"));
		driver.findElement(By.xpath(getElementLocator("CreateProfileOlay"))).click();
		try {
			subscribe = driver.findElement(By.xpath(getElementLocator("SignInPageOlay"))).isDisplayed();
		  } catch (NoSuchElementException e) {
			  subscribe = false;
		  }
		if(!subscribe) {
			driver.findElement(By.xpath(getElementLocator("FirstNameOlay"))).sendKeys((String)jsonObject.get("FirstNameUK"));
			driver.findElement(By.xpath(getElementLocator("LastNameOlay"))).sendKeys((String)jsonObject.get("LastNameUK"));
			driver.findElement(By.xpath(getElementLocator("StreetHomeAddress"))).sendKeys((String)jsonObject.get("StreeAndHouseUK"));
			driver.findElement(By.xpath(getElementLocator("CityInputAddress"))).sendKeys((String)jsonObject.get("CityUK"));
			driver.findElement(By.xpath(getElementLocator("PostalCodeAddress"))).sendKeys((String)jsonObject.get("PostalCodeUK"));
			driver.findElement(By.xpath(getElementLocator("AddToMyProfileUK"))).click();
			//Verify Registration Completed
			driver.findElement(By.xpath(getElementLocator("RegCompletedUK"))).isDisplayed();
		}
		
		//Validate InValid Password Verification
		driver.findElement(By.xpath(getElementLocator("SignInEmailOlay"))).sendKeys(strEmailId);
		driver.findElement(By.xpath(getElementLocator("SignInPasswordOlay"))).sendKeys("Qwerty@12345pL");
		driver.findElement(By.xpath(getElementLocator("ClickSignInOlay"))).click();
		//InvalidPasswordErrMsg
		strReusableVar = driver.findElement(By.xpath(getElementLocator("InvalidPasswordErr"))).getText();
		if(!strReusableVar.trim().equals(TestData.get("InvalidPasswordErrMsg")))
			Assert.fail("Error Message for Invalid Password is not displayed as Expected");
		//Click Forget Password Link
		driver.findElement(By.xpath(getElementLocator("ClickForgetPassword"))).click();
		driver.findElement(By.xpath(getElementLocator("ForgetPasswordPage"))).isDisplayed();
		driver.findElement(By.xpath(getElementLocator("SignInEmailOlay"))).sendKeys(strEmailId);
		driver.findElement(By.xpath(getElementLocator("NextForgetPassword"))).click();
		//PasswordRecoveryMail
		strReusableVar = driver.findElement(By.xpath(getElementLocator("RecoveryPasswordMsg"))).getText();
		System.out.println(strReusableVar);
		if(!strReusableVar.trim().contains(TestData.get("PasswordRecoveryMail")))
			Assert.fail("Password Recovery Message is not displayed as Expected");
		
		//ClickSignIn
		driver.findElement(By.xpath(getElementLocator("ClickSignInUK"))).click();
		//SignIn
		loginOlay(strEmailId, (String)jsonObject.get("PasswordUK"));
		//Verify SignIn is Success
		driver.findElement(By.xpath(getElementLocator("SignInSuccessUK"))).isDisplayed();
		
		
		
		//Navigate To Germany Website
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.close();
		driver.switchTo().window(tabs.get(0));
		driver.findElement(By.xpath(getElementLocator("ClickGermanySite"))).click();
		driver.switchTo().window(tabs.get(1));
		
		if(!driver.findElement(By.xpath(getElementLocator("OlayHPGermany"))).isDisplayed())
			Assert.fail("Olay Germany Home Page is Not Opened");
		
		webElement = driver.findElement(By.xpath(getElementLocator("AcceptCookieGermany")));
		if(webElement.isDisplayed())
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", webElement);
	    
		driver.findElement(By.xpath(getElementLocator("ClickRegGermany"))).click();

		if(!driver.findElement(By.xpath(getElementLocator("CreateProfileOlay"))).isDisplayed())
			Assert.fail("Olay Germany Register Page is Not Opened");
		
		driver.findElement(By.xpath(getElementLocator("ClickMaleGermany"))).click();
		driver.findElement(By.xpath(getElementLocator("FirstNameOlay"))).sendKeys((String)jsonObject.get("FirstNameGer"));
		driver.findElement(By.xpath(getElementLocator("LastNameOlay"))).sendKeys((String)jsonObject.get("LastNameGer"));
		//Enter Email Id
		strEmailId = getEmailId();
		driver.findElement(By.xpath(getElementLocator("EmailFieldOlay"))).sendKeys(strEmailId);
		driver.findElement(By.xpath(getElementLocator("PasswordOlay"))).sendKeys((String)jsonObject.get("PasswordGer"));
		driver.findElement(By.xpath(getElementLocator("ConfmPasswordOlay"))).sendKeys((String)jsonObject.get("PasswordGer"));
		//Select Birth Date
		select = new Select(driver.findElement(By.xpath(getElementLocator("SelectBirthDay"))));
		select.selectByValue((String)jsonObject.get("BirthDayGer"));
		select = new Select(driver.findElement(By.xpath(getElementLocator("SelectBirthMonth"))));
		select.selectByValue((String)jsonObject.get("BirthMonthGer"));
		select = new Select(driver.findElement(By.xpath(getElementLocator("SelectBirthYear"))));
		select.selectByValue((String)jsonObject.get("BirthYearGer"));
		//Select Country
		select = new Select(driver.findElement(By.xpath(getElementLocator("SelectCountryGer"))));
		select.selectByValue("DEU");
		driver.findElement(By.xpath(getElementLocator("StreetHomeAddress"))).sendKeys((String)jsonObject.get("StreeAndHouseGer"));
		driver.findElement(By.xpath(getElementLocator("CityInputAddress"))).sendKeys((String)jsonObject.get("LocationGer"));
		driver.findElement(By.xpath(getElementLocator("PostalCodeAddress"))).sendKeys((String)jsonObject.get("PostalCodeGer"));
		driver.findElement(By.xpath(getElementLocator("CreateProfileOlay"))).click();
		
		
		//Navigate To Spain Website
		driver.close();
		driver.switchTo().window(tabs.get(0));
		driver.findElement(By.xpath(getElementLocator("ClickSpanishSite"))).click();
		tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		
		if(!driver.findElement(By.xpath(getElementLocator("OlayHomePageUK"))).isDisplayed())
			Assert.fail("Olay Spain Home Page is Not Opened");
		
		webElement = driver.findElement(By.xpath(getElementLocator("AcceptCookieSpain")));
		if(webElement.isDisplayed())
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", webElement);
		
		driver.findElement(By.xpath(getElementLocator("ClickRegisterSpain"))).click();
		
		if(!driver.findElement(By.xpath(getElementLocator("CreateProfileOlay"))).isDisplayed())
			Assert.fail("Olay Spain Register Page is Not Opened");
		
		driver.findElement(By.xpath(getElementLocator("ClickMaleGermany"))).click();
		//Enter Email Id
		strEmailId = getEmailId();
		driver.findElement(By.xpath(getElementLocator("FirstNameOlay"))).sendKeys(TestData.get("FirstNameSpain"));
		driver.findElement(By.xpath(getElementLocator("LastNameOlay"))).sendKeys(TestData.get("LastNameSpain"));
		driver.findElement(By.xpath(getElementLocator("EmailFieldOlay"))).sendKeys(strEmailId);
		driver.findElement(By.xpath(getElementLocator("PasswordOlay"))).sendKeys(TestData.get("SpainPassword"));
		driver.findElement(By.xpath(getElementLocator("ConfmPasswordOlay"))).sendKeys(TestData.get("SpainPassword"));
		phoneNumber = String.valueOf(System.currentTimeMillis());
		phoneNumber = "+34" + phoneNumber.substring(0, 8);
		driver.findElement(By.xpath(getElementLocator("ChinaMobileInput"))).sendKeys(phoneNumber);
		//Select Birth Date
		select = new Select(driver.findElement(By.xpath(getElementLocator("SelectBirthDay"))));
		select.selectByValue(TestData.get("BirthDaySpain"));
		select = new Select(driver.findElement(By.xpath(getElementLocator("SelectBirthMonth"))));
		select.selectByValue(TestData.get("BirthMonthSpain"));
		select = new Select(driver.findElement(By.xpath(getElementLocator("SelectBirthYear"))));
		select.selectByValue(TestData.get("BirthYearSpain"));
		
		driver.findElement(By.xpath(getElementLocator("AgreeConditionSpain"))).click();
		driver.findElement(By.xpath(getElementLocator("CreateProfileOlay"))).click();
		
		if(!driver.findElement(By.xpath(getElementLocator("SignInPageSpain"))).isDisplayed())
			Assert.fail("Olay Spain SignIn Page is Not Opened");
		
		loginOlay(strEmailId, TestData.get("SpainPassword"));
		
		driver.findElement(By.xpath(getElementLocator("SignInSuccessSpain"))).isDisplayed();
	}

	public void loginOlay(String emailId, String strPassword) {
		//SignIn
		driver.findElement(By.xpath(getElementLocator("SignInEmailOlay"))).sendKeys(emailId);
		driver.findElement(By.xpath(getElementLocator("SignInPasswordOlay"))).sendKeys(strPassword);
		driver.findElement(By.xpath(getElementLocator("ClickSignInOlay"))).click();
	}
}
