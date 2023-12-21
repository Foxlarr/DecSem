package org.example.tests;

import org.example.pom.LoginPage;
import org.example.pom.MainPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Пример использования самых базовых методов библиотеки Selenium.
 */
public class GeekBrainsStandTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private MainPage mainPage;

    private static final String USERNAME = "Student-12";
    private static final String PASSWORD = "31e146f2be";

    @BeforeAll
    public static void setupClass() {
              System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\geckodriver.exe");
//        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
    }

    @BeforeEach
    public void setupTest() {
        // Создаём экземпляр драйвера
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        // Растягиваем окно браузера на весь экран
//        driver.manage().window().maximize();
        // Навигация на https://test-stand.gb.ru/login
        driver.get("https://test-stand.gb.ru/login");
        // Объект созданного Page Object
        loginPage = new LoginPage(driver, wait);
    }

//    @Test
//    public void testAddingGroupOnMainPage() {
//        checkLogin();
//        // Создание группы. Даём ей уникальное имя, чтобы в каждом запуске была проверка нового имени
//        String groupTestName = "New Test Group " + System.currentTimeMillis();
//        mainPage.createGroup(groupTestName);
//    }
//
//    @Test
//    void testArchiveGroupOnMainPage() {
//        checkLogin();
//        String groupTestName = "New Test Group " + System.currentTimeMillis();
//        mainPage.createGroup(groupTestName);
//        // Требуется закрыть модальное окно
//        mainPage.closeCreateGroupModalWindow();
//        // Изменение созданной группы с проверками
//        assertEquals("active", mainPage.getStatusOfGroupWithTitle(groupTestName));
//        mainPage.clickTrashIconOnGroupWithTitle(groupTestName);
//        assertEquals("inactive", mainPage.getStatusOfGroupWithTitle(groupTestName));
//        mainPage.clickRestoreFromTrashIconOnGroupWithTitle(groupTestName);
//        assertEquals("active", mainPage.getStatusOfGroupWithTitle(groupTestName));
//    }



//    @Test
//    void testLoginWithoutCredentials() {
//        // Попытка входа без логина и пароля
//        loginPage.login("", "");
//
//        // Проверка текста сообщения об ошибке
//        String expectedErrorMessage = "401\n" +
//                "Invalid credentials.";
//        String actualErrorMessage = loginPage.getErrorMessage();
//        assertEquals(expectedErrorMessage, actualErrorMessage, "Incorrect error message");
//    }

    @Test
    void changeAmountOfStudents() {
        checkLogin();
        // Добавляем студента и проверяем, что количество студентов увеличилось на 1
        mainPage.addOneStudentAndCheck();
        String errorMessage = mainPage.getErrorMessage();

        // Проверяем, что errorMessage равно null для успешного теста
        Assertions.assertNull(errorMessage, "Error message: " + errorMessage);


        // кликаем на иконку корзины для первого студента и проверяем, как изменился его статус,
        // затем кликаем на иконку восстановления для той же строки и проверяем, что статус снова поменялся
        mainPage.performTask();
    }


    @Test
    private void checkLogin() {
        // Логин в систему с помощью метода из класса Page Object
        loginPage.login(USERNAME, PASSWORD);
        // Инициализация объекта класса MainPage
        mainPage = new MainPage(driver, wait);
        // Проверка, что логин прошёл успешно
        assertTrue(mainPage.getUsernameLabelText().contains(USERNAME));
    }


    @AfterEach
    public void teardown() {
        // Закрываем все окна браузера и процесс драйвера
        driver.quit();
    }



}

