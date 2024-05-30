package demo;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WrapperMethods {

    public static ArrayList<HashMap<Object, Object>> wrap_tc01_getHockeyTeamsDetails(WebDriver driver) {
        ArrayList<HashMap<Object, Object>> hockeyTeamsDataList=new ArrayList<>();
        int x=1;
        while(x<=5) {
            if(x==2) {
                wrap_clickElement(driver.findElement(By.cssSelector("ul.pagination li:nth-child("+x+") a")));
                wrap_advancedWait(driver, By.cssSelector("ul.pagination li:nth-child("+(x+1)+") a strong"));
                x++;
            }
            if(x>2) {
                wrap_clickElement(driver.findElement(By.cssSelector("ul.pagination li:nth-child("+x+") a")));
                wrap_advancedWait(driver, By.cssSelector("ul.pagination li:nth-child("+x+") a strong"));
            }
            List<WebElement> pageList=wrap_findElements(driver, By.cssSelector("table.table tr.team"));
            HashMap<Object, Object> dataMap=pageList
                .stream()
                .filter(e->Double.parseDouble(e.findElement(By.cssSelector("td.pct")).getText().trim()) < 0.40)
                .map(item -> {
                    String timestamp=Long.toString(Instant.now().toEpochMilli());
                    String teamName=item.findElement(By.cssSelector("td.name")).getText().trim();
                    String teamYear=item.findElement(By.cssSelector("td.year")).getText().trim();
                    String teamWinPercent=item.findElement(By.cssSelector("td.pct")).getText().trim();
                    String[] arr={teamName, teamYear, teamWinPercent};
                    ArrayList<String> teamDetails=new ArrayList<>();
                    teamDetails.addAll(Arrays.asList(arr));
                    HashMap<String, ArrayList<String>> map=new HashMap<>();
                    map.put(timestamp, teamDetails);
                    return map;
                })
                .collect(Collectors.toMap(
                    e-> e.entrySet().iterator().next().getKey(), 
                    e-> e.entrySet().iterator().next().getValue(),
                    (a,b) -> b,
                    HashMap::new
                ));
            hockeyTeamsDataList.add(dataMap);
            x++;
        }

        return hockeyTeamsDataList;
    }

    public static ArrayList<HashMap<Object,Object>> wrap_tc02_getOscarFilmsDetails(WebDriver driver) {
        ArrayList<HashMap<Object,Object>> filmsDetailsList=new ArrayList<>();
        List<WebElement> yearLinks=wrap_findElements(driver, By.cssSelector("a.year-link"));
        for(WebElement yearLink:yearLinks) {
            wrap_clickElement(yearLink);
            wrap_advancedWait(driver, By.cssSelector("table.table tr.film"));
            String year=yearLink.getText();
            List<WebElement> filmsList=wrap_findElements(driver, By.cssSelector("table.table tr.film"))
                .stream()
                .limit(5)
                .collect(Collectors.toList());
            HashMap<Object, Object> filmsDataMap=filmsList.stream()
                .map(item-> {
                    String timeStamp=Long.toString(Instant.now().toEpochMilli());
                    String title=item.findElement(By.cssSelector("td.film-title")).getText().trim();
                    String nominations=item.findElement(By.cssSelector("td.film-nominations")).getText().trim();
                    String awards=item.findElement(By.cssSelector("td.film-awards")).getText().trim();
                    boolean isWinner=item.findElement(By.cssSelector("td.film-best-picture")).findElements(By.xpath(".//*")).size()>0;
                    String[] arr={year, title, nominations, awards, String.valueOf(isWinner)};
                    ArrayList<String> list=new ArrayList<>();
                    list.addAll(Arrays.asList(arr));
                    HashMap<String, ArrayList<String>> map=new HashMap<>();
                    map.put(timeStamp, list);
                    return map;
                })
                .collect(Collectors.toMap(
                    e -> e.entrySet().iterator().next().getKey(), 
                    e -> e.entrySet().iterator().next().getValue(),
                    (a,b) -> b,
                    HashMap::new
                ));
            filmsDetailsList.add(filmsDataMap);
        }

        return filmsDetailsList;
    }
    
    public static File wrap_convertToJSONFile(ArrayList<HashMap<Object, Object>> list, String fileName) {
        ObjectMapper mapper=new ObjectMapper();
        File file=null;
        try {
            file=new File(System.getProperty("user.dir")+"\\src\\test\\resources\\"+fileName+".json");
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, list);
        } catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return file;
    }

    public static void wrap_navigateToScrapWebsite(WebDriver driver, By by1, By by2) {
        WebElement hockeyLink=wrap_findElement(driver, by1);
        wrap_clickElement(hockeyLink);
        wrap_advancedWait(driver, by2);
    }

    public static void wrap_assertFile(File jsonFile) {
        Assert.assertTrue(jsonFile.exists() && jsonFile.length()!=0);
    }
    
    public static void wrap_openUrl(WebDriver driver) {
        driver.get("https://www.scrapethissite.com/pages/");
    }

    public static WebElement wrap_findElement(WebDriver driver, By by) {
        WebElement element=driver.findElement(by);
        return element;
    }

    public static List<WebElement> wrap_findElements(WebDriver driver, By by) {
        List<WebElement> list=driver.findElements(by);
        return list;
    }

    public static void wrap_clickElement(WebElement element) {
        element.click();
    }

    public static void wrap_advancedWait(WebDriver driver, By by) {
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static void wrap_scrollToElement(WebDriver driver, WebElement element) {
        JavascriptExecutor js=(JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

}
