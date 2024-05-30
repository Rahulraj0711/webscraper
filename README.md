# QA Assignment - Web Scraper - Rahul Raj 

## Introduction:
Automate a website and fetch data to store in a JSON file format.

## Prerequisites:
1. Java
2. Java Data Handling (Using Hashmaps)
3. Selenium
4. Locators
5. Xpath
6. Buttons
7. Frames
8. Web Tables
9. Waits
10. Exceptions
11. TestNG
12. Jackson API
13. Apache POI

## Scenario:

### 1) Setup:
Integrated TestNG framework by
(i) Modifying Build.Gradle file
(ii) Introducing testng.xml file
(iii) Created WrapperMethods.java file which is used as a helper class to implement all actions in a test case.

### 2) TestCase 01:
1. Navigated to "https://www.scrapethissite.com/pages/".
2. Clicked on “Hockey Teams: Forms, Searching and Pagination”.
3. Iterated through the table and collected the Team Name, Year and Win % for the teams with Win % less than 40% (0.40).
4. Iterated through 4 pages of this data and stored it in a ArrayList<HashMap>.
5. Converted the ArrayList<HashMap> object to a JSON file named `hockey-team-data.json` with the help of Jackson API.
6. Each Hashmap object should contain:
   i. Epoch Time of Scrape
   ii. Team Name
   iii. Year
   iv. Win %
7. Applied an TestNG Assert to confirm that the file is present and not empty.

### 3) TestCase 02:
1. Navigated to "https://www.scrapethissite.com/pages/".
2. Clicked on “Oscar Winning Films”.
3. Clicked on each year present on the screen and found the top 5 movies on the list and stored in an ArrayList<HashMap>.
4. Created a Boolean variable “isWinner” which will be true only for the best picture winner of that year. 
5. Created a variable to maintain the year from which the data is scraped.
6. Converted the ArrayList<HashMap> object to a JSON file named `oscar-winner-data.json` with the help of Jackson API.
6. Each Hashmap object should contain:
   i. Epoch Time of Scrape
   ii. Year
   iii. Title
   iv. Nomination
   v. Awards
   vi. isWinner
8. Applied an TestNG Assert to confirm that the file is present and not empty.
