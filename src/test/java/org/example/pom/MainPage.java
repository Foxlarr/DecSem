package org.example.pom;

import org.example.pom.elements.GroupTableRow;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPage {

    private final WebDriverWait wait;
    private String errorMessage;

    @FindBy(css = "nav li.mdc-menu-surface--anchor a")
    private WebElement usernameLinkInNavBar;
    @FindBy(id = "create-btn")
    private WebElement createGroupButton;
    @FindBy(xpath = "//form//span[contains(text(), 'Group name')]/following-sibling::input")
    private WebElement groupNameField;
    @FindBy(css = "form div.submit button")
    private WebElement submitButtonOnModalWindow;
    @FindBy(xpath = "//span[text()='Creating Study Group']" +
            "//ancestor::div[contains(@class, 'form-modal-header')]//button")
    private WebElement closeCreateGroupIcon;
    @FindBy(xpath = "//table[@aria-label='Tutors list']/tbody/tr")
    private List<WebElement> rowsInGroupTable;
    @FindBy(xpath = "@FindBy(css = \"i.mdc-button__icon[aria-hidden=true]\")")
    private WebElement studentAddButton;
//    @FindBy(xpath = "/html/body/div/main/div/div/div[3]/div[2]/div/div[2]/div/form/div[1]/label/input")
//    private  WebElement buttonCountUp;
    @FindBy(css = ".text-span.svelte-b5t5jw")
    private WebElement accountsCount;


    @FindBy(css = ".mdc-data-table__row")
    private List<WebElement> students;

    @FindBy(css = ".material-icons.mdc-icon-button.mdc-icon-button--display-flex.smui-icon-button--size-button.mdc-icon-button--reduced-size.mdc-ripple-upgraded--unbounded.mdc-ripple-upgraded")
    private WebElement deleteRestoreButton;

    @FindBy(css = "input.mdc-text-field__input")
    private WebElement newNumberInput;

    public MainPage(WebDriver driver, WebDriverWait wait) {
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void waitGroupTitleByText(String title) {
        String xpath = String.format("//table[@aria-label='Tutors list']/tbody//td[text()='%s']", title);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public void createGroup(String groupName) {
        wait.until(ExpectedConditions.visibilityOf(createGroupButton)).click();
        wait.until(ExpectedConditions.visibilityOf(groupNameField)).sendKeys(groupName);
        wait.until(ExpectedConditions.textToBePresentInElementValue(groupNameField, groupName));
        submitButtonOnModalWindow.click();
        waitGroupTitleByText(groupName);
    }

    public void closeCreateGroupModalWindow() {
        closeCreateGroupIcon.click();
        wait.until(ExpectedConditions.invisibilityOf(closeCreateGroupIcon));
    }

    public String getUsernameLabelText() {
        return wait.until(ExpectedConditions.visibilityOf(usernameLinkInNavBar))
                .getText().replace("\n", " ");
    }

    public void clickTrashIconOnGroupWithTitle(String title) {
        getRowByTitle(title).clickTrashIcon();
    }

    public void clickRestoreFromTrashIconOnGroupWithTitle(String title) {
        getRowByTitle(title).clickRestoreFromTrashIcon();
    }

    public String getStatusOfGroupWithTitle(String title) {
        return getRowByTitle(title).getStatus();
    }

    private GroupTableRow getRowByTitle(String title) {
        return rowsInGroupTable.stream()
                .map(GroupTableRow::new)
                .filter(row -> row.getTitle().equals(title))
                .findFirst().orElseThrow();
    }


    public int getInitialValue() {
        return Integer.parseInt(wait.until(ExpectedConditions.visibilityOf(accountsCount)).getText());
    }

    public int getCurrentValue() {
        return Integer.parseInt(wait.until(ExpectedConditions.visibilityOf(accountsCount)).getText());
    }

    public void addOneStudentAndCheck() {
        int initialValue = getInitialValue();

        addOneStudent();

        int newValue = getCurrentValue();
        if (newValue != initialValue + 1) {
            String message = "Value did not increase by 1. Initial: " + initialValue + ", After: " + newValue;
            processErrorMessage(message);
        }
    }
    // устанавливаем значение в поле errorMessage
    public void processErrorMessage(String message) {
        this.errorMessage = message;
    }
    // геттер для errorMessage, если нужно в других частях кода
    public String getErrorMessage() {
        return errorMessage;
    }

    public void addOneStudent() {
        wait.until(ExpectedConditions.visibilityOf(studentAddButton)).click();
        wait.until(ExpectedConditions.visibilityOf(newNumberInput)).sendKeys(String.valueOf(1));
        submitButtonOnModalWindow.click();
    }

    public void performTask() {
        // Кликаем на иконку удаления для первого студента
        wait.until(ExpectedConditions.visibilityOf(deleteRestoreButton)).click();

        // Проверяем, как изменился его статус (должен смениться на block)
        String initialStatus = getStudentStatus(0);
        System.out.println("Initial status: " + initialStatus);

        // Проверяем, что статус после удаления совпадает с ожидаемым (например, block)
        Assertions.assertEquals("block", initialStatus, "Unexpected initial status after deletion");

        // Кликаем на иконку восстановления для той же строки
        wait.until(ExpectedConditions.visibilityOf(deleteRestoreButton)).click();

        // Проверяем, что статус снова поменялся (должен смениться на active)
        String newStatus = getStudentStatus(0);
        System.out.println("New status: " + newStatus);

        // Проверяем, что статус после восстановления совпадает с ожидаемым (например, active)
        Assertions.assertEquals("active", newStatus, "Unexpected new status after restoration");
    }

    private String getStudentStatus(int index) {
        // Проверяем, как изменился статус студента по индексу
        WebElement studentRow = students.get(index);
        return studentRow.findElement(By.cssSelector(".mdc-data-table__cell")).getText();
    }
}
