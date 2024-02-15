package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class TargetSearchTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://www.target.com");

            WebElement searchInput = driver.findElement(By.id("search"));
            searchInput.sendKeys("iphone" + Keys.ENTER);

            Thread.sleep(5000);

            List<WebElement> products = driver.findElements(By.cssSelector("div[data-test='@web/ProductCard/body']"));

            try {
                FileWriter fileWriter = new FileWriter("targetSearchResult.txt");
                PrintWriter printWriter = new PrintWriter(fileWriter);

                for (WebElement product : products) {
                    String title = "";
                    String price = "";

                    try {
                        title = product.findElement(By.cssSelector("a[data-test='product-title']")).getText();
                    } catch (Exception e) {
                        title = "Title not found";
                    }

                    try {
                        price = product.findElement(By.cssSelector("span[data-test='current-price'] > span")).getText();
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
