import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class GroupCreationTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String USERNAME = "Student-12";
    private static final String PASSWORD = "31e146f2be";

    @BeforeAll
    public static void setupClass(){
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
    }
    @BeforeEach
    public void setUp() {

        driver = new ChromeDriver();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    @Test
    public void testGroupCreation() throws InterruptedException {
        driver.get("https://test-stand.gb.ru/login");
        // Логин
        WebElement usernameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("form#login input[type='text']")));
        WebElement passwordInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("form#login input[type='password']")));

        // Ввод логина и пароля
        usernameInput.sendKeys(USERNAME);
        passwordInput.sendKeys(PASSWORD);

        //Нахождение и нажатие кнопки входа
        WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("form#login button")));
        loginButton.click();


        // Ожидание открытия модального окна
        By createButtonLocator = By.id("create-btn");
        wait.until(ExpectedConditions.elementToBeClickable(createButtonLocator)).click();





        // Ввод имени новой группы
        String group_Name = "New" + System.currentTimeMillis();
        WebElement groupNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//form//span[contains(text(), 'Group name')]/following-sibling::input")));



        // Нажатие кнопки SAVE
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".mdc-button--raised")));
        saveButton.click();



        // Проверка появления искомого элемента (title новой группы)
        WebElement createdGroupTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[text()='" + group_Name + "']")));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}