package demo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestCases {
    ChromeDriver driver;
    
    @BeforeClass(alwaysRun = true)
    public void createDriver() {
        System.out.println("Creating Driver and Starting Test Execution...");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(enabled = true)
    public  void testCase01(){
        System.out.println("Start Test case: testCase01");

        // Open the scrap website url
        WrapperMethods.wrap_openUrl(driver);
        
        // Navigate to Hockey Teams website by clicking on the appropriate link and wait until table is visible
        WrapperMethods.wrap_navigateToScrapWebsite(driver, By.partialLinkText("Hockey"), By.className("table"));

        // Get the details of Hockey Teams with Win % less than 40% (0.40) by iterating through 4 pages
        // Store the data extracted in the ArrayList<HashMap>
        ArrayList<HashMap<Object, Object>> hockeyTeamsDataList=WrapperMethods.wrap_tc01_getHockeyTeamsDetails(driver);

        // Create a JSON file and write the extracted data into the file using Jackson API.
        File jsonFile=WrapperMethods.wrap_convertToJSONFile(hockeyTeamsDataList, "hockey-team-data");

        // TestNG Assert to confirm the file is created and is not empty.
        WrapperMethods.wrap_assertFile(jsonFile);

        System.out.println("End Test case: testCase01");
    }

    @Test(enabled = true)
    public  void testCase02(){
        System.out.println("Start Test case: testCase02");
        
        // Open the scrap website url
        WrapperMethods.wrap_openUrl(driver);

        // Navigate to Oscar Winning Films website by clicking on the appropriate link and wait until 1st year link is visible
        WrapperMethods.wrap_navigateToScrapWebsite(driver, By.partialLinkText("Oscar"), By.cssSelector("a.year-link:nth-of-type(1)"));

        // Get the details of top 5 Oscar Winning Films from each year 
        // Store the data extracted in the ArrayList<HashMap>
        ArrayList<HashMap<Object,Object>> filmsDetailsList=WrapperMethods.wrap_tc02_getOscarFilmsDetails(driver);

        // Create a JSON file and write the extracted data into the file using Jackson API.
        File jsonFile=WrapperMethods.wrap_convertToJSONFile(filmsDetailsList, "oscar-winner-data");

        // TestNG Assert to confirm the file is created and is not empty.
        WrapperMethods.wrap_assertFile(jsonFile);

        System.out.println("End Test case: testCase02");
    }

    @AfterClass
    public void endTest() {
        System.out.println("Ending Test Execution and Closing the driver.");
        driver.quit();
    }
}
