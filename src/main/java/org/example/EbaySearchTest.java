package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class EbaySearchTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://www.ebay.com");

            WebElement searchInput = driver.findElement(By.name("_nkw"));
            searchInput.sendKeys("iphone");

            WebElement searchButton = driver.findElement(By.id("gh-btn"));
            searchButton.click();

            Thread.sleep(5000);

            List<WebElement> products = driver.findElements(By.cssSelector(".s-item__info.clearfix"));

            try {
                FileWriter fileWriter = new FileWriter("ebaySearchResult.txt");
                PrintWriter printWriter = new PrintWriter(fileWriter);

                for (WebElement product : products) {
                    String title = "";
                    String price = "";

                    try {
                        title = product.findElement(By.cssSelector(".s-item__title")).getText();
                    } catch (Exception e) {
                        title = "Title not found";
                    }

                    try {
                        price = product.findElement(By.cssSelector(".s-item__price")).getText();
                    } catch (Exception e) {
                        price = "Price not found";
                    }

                    printWriter.println(title + " - " + price);
                }
                printWriter.close();
            } catch (Exception e) {
                System.out.println("An error occurred at writing to file: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("An error occurred at performing search: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }
}
