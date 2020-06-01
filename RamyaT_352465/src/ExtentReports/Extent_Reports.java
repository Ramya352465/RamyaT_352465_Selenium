package ExtentReports;

 

import java.io.File;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

 

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

 

import Assignment1.Task1;

 

public class Extent_Reports {

 

    ExtentReports extent;
    ExtentTest logger;

 

    @BeforeSuite
    public void startTest() {
        extent = new ExtentReports(System.getProperty("user.dir") + "\\src\\ExtentOutput\\Extentreports.html", true);
        extent.addSystemInfo("Host Name", "Karthik").addSystemInfo("Environment", "Automation Testing")
                .addSystemInfo("User Name", "Ramya");

 

        extent.loadConfig(new File(System.getProperty("user.dir") + "\\src\\ExtentReports\\extent-config.xml"));

 

    }

 

    @Test
    public void passTest() {
        logger = extent.startTest("Assignment_Task1");
        Assert.assertTrue(true);
        logger.log(LogStatus.PASS, "test case passed");
    }

 

    @Test
    public void failTest() {
        logger = extent.startTest("failTest");
        Assert.assertTrue(true);
        logger.log(LogStatus.FAIL, "test case failing");
    }

 

    @Test
    public void skipTest() {
        logger = extent.startTest("skipTest");
        throw new SkipException("skipping");

 

    }

 

    @AfterMethod
    public void getResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.log(LogStatus.FAIL, "Testcase failed is: " + result.getName());
            logger.log(LogStatus.FAIL, "Testcase failed is: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.log(LogStatus.SKIP, "testcase skipped is: " + result.getName());

 

        }
        extent.endTest(logger);
    }

 

    @AfterSuite
    public void testReport() {
        extent.flush();
        extent.close();
    }

 

}
 