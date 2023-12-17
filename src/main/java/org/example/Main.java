package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class Main {
    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", "src\\main\\resources\\geckodriver.exe");

        WebDriver driver = new FirefoxDriver();
        try {
            driver.get("https://test-stand.gb.ru/login");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("q")));

            // Теперь продолжайте работать с элементом...
        } finally {
            driver.quit(); // Закрываем браузер после использования
        }
    }
}
