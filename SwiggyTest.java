package com;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;

public class SwiggyTest {

    @Test
    public void openSwiggyAndSearchBangalore() throws InterruptedException {

        // ✅ Chrome 141 compatible setup (auto-manages driver)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--remote-allow-origins=*");

        // ✅ This automatically downloads & matches ChromeDriver version 141
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        // ✅ Step 1: Open Google
        driver.get("https://www.google.com");
        // ✅ Step 2: Search for Swiggy
        WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("q")));
        searchBox.sendKeys("Swiggy");
        searchBox.submit();
        // ✅ Step 3: Click first Swiggy link
        List<WebElement> results = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("a h3")));
        for (WebElement result : results) {
            if (result.getText().toLowerCase().contains("swiggy")) {
                result.click();
                break;
            }
        }
        // ✅ Step 4: Wait for Swiggy page to load
        wait.until(ExpectedConditions.urlContains("swiggy.com"));

        // ✅ Step 5: Enter “Bangalore” as location
        WebElement locationInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[contains(@placeholder,'Enter your delivery location')]")
        ));
        locationInput.sendKeys("Bangalore");

        // ✅ Step 6: Wait for location suggestion and click first one
        WebElement suggestion = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath( "//*[@id='root']/div/div[1]/div/div/div[2]/div[2]/div[2]/div/div[1]/div")));
        suggestion.click();

       // ✅ Search for Biryani
        WebElement searchFood = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//input[@placeholder='Search for restaurants and food']")));
        searchFood.sendKeys("Biryani");
        searchFood.sendKeys(Keys.ENTER);

        // ✅ Click first Biryani result (Restaurant or Item)
        WebElement firstResult = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("(//*[contains(text(),'Biryani')])[1]")));
        firstResult.click();

        // ✅ Wait for menu & click first Add button
        WebElement firstAddBtn = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("(//button[contains(text(),'Add')])[1]")));
        firstAddBtn.click();

        System.out.println("✅ Biryani added to cart!");


        Thread.sleep(5000);
        driver.quit();
    }
}
