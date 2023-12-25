package org.example.pom;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.example.pom.elements.GroupTableRow;
import org.example.pom.elements.StudentTableRow;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    private final SelenideElement usernameLinkInNavBar = $("nav li.mdc-menu-surface--anchor a");
    private final SelenideElement profileLinkInNavBar = $x("//nav//li[contains(@class,'mdc-menu-surface--anchor')]//span[text()='Profile']");
    private final SelenideElement createGroupButton = $("#create-btn");
    private final SelenideElement groupNameField = $x("//form//span[contains(text(), 'Group name')]/following-sibling::input");
    private final SelenideElement submitButtonOnModalWindow = $("form div.submit button");
    private final SelenideElement closeCreateGroupIcon = $x("//span[text()='Creating Study Group']" +
            "//ancestor::div[contains(@class, 'form-modal-header')]//button");
    private final SelenideElement createStudentsFormInput = $("div#generateStudentsForm-content input");
    private final SelenideElement saveCreateStudentsForm = $("div#generateStudentsForm-content div.submit button");
    private final SelenideElement closeCreateStudentsFormIcon = $x("//h2[@id='generateStudentsForm-title']/../button");
    private final ElementsCollection rowsInGroupTable = $$x("//table[@aria-label='Tutors list']/tbody/tr");
    private final ElementsCollection rowsInStudentTable = $$x("//table[@aria-label='User list']/tbody/tr");

    public SelenideElement waitAndGetGroupTitleByText(String title) {
        return $x(String.format("//table[@aria-label='Tutors list']/tbody//td[text()='%s']", title)).shouldBe(Condition.visible);
    }

    public void createGroup(String groupName) {
        createGroupButton.shouldBe(Condition.visible).click();
        groupNameField.shouldBe(Condition.visible).setValue(groupName);
        submitButtonOnModalWindow.shouldBe(Condition.visible).click();
        waitAndGetGroupTitleByText(groupName);
    }

    public void closeCreateGroupModalWindow() {
        closeCreateGroupIcon.shouldBe(Condition.visible).click();
    }

    public void typeAmountOfStudentsInCreateStudentsForm(int amount) {
        createStudentsFormInput.shouldBe(Condition.visible).setValue(String.valueOf(amount));
    }

    public void clickSaveButtonOnCreateStudentsForm() {
        saveCreateStudentsForm.shouldBe(Condition.visible).click();
    }

    public void closeCreateStudentsModalWindow() {
        closeCreateStudentsFormIcon.click();
    }

    public void clickUsernameLabel() {
        usernameLinkInNavBar.shouldBe(Condition.visible).click();
    }

    public void clickProfileLink() {
        profileLinkInNavBar.shouldBe(Condition.visible).click();
    }

    public String getUsernameLabelText() {
        return usernameLinkInNavBar.shouldBe(Condition.visible).getText().replace("\n", " ");
    }

    // Group Table Section
    public void clickTrashIconOnGroupWithTitle(String title) {
        getGroupRowByTitle(title).clickTrashIcon();
    }

    public void clickRestoreFromTrashIconOnGroupWithTitle(String title) {
        getGroupRowByTitle(title).clickRestoreFromTrashIcon();
    }

    public void clickAddStudentsIconOnGroupWithTitle(String title) {
        getGroupRowByTitle(title).clickAddStudentsIcon();
    }

    public void clickZoomInIconOnGroupWithTitle(String title) {
        getGroupRowByTitle(title).clickZoomInIcon();
    }

    public String getStatusOfGroupWithTitle(String title) {
        return getGroupRowByTitle(title).getStatus();
    }

    public void waitStudentsCount(String groupTestName, int studentsCount) {
        getGroupRowByTitle(groupTestName).waitStudentsCount(studentsCount);
    }

    private GroupTableRow getGroupRowByTitle(String title) {
        return rowsInGroupTable.shouldHave(sizeGreaterThan(0))
                .asDynamicIterable().stream()
                .map(GroupTableRow::new)
                .filter(row -> row.getTitle().equals(title))
                .findFirst().orElseThrow();
    }

    // Students Table Section

    public void clickTrashIconOnStudentWithName(String name) {
        getStudentRowByName(name).clickTrashIcon();
    }

    public void clickRestoreFromTrashIconOnStudentWithName(String name) {
        getStudentRowByName(name).clickRestoreFromTrashIcon();
    }

    public String getStatusOfStudentWithName(String name) {
        return getStudentRowByName(name).getStatus();
    }

    public String getStudentNameByIndex(int index) {
        return rowsInStudentTable.shouldHave(sizeGreaterThan(0))
                .asDynamicIterable().stream()
                .map(StudentTableRow::new)
                .toList().get(index).getName();
    }

    private StudentTableRow getStudentRowByName(String name) {
        return rowsInStudentTable.shouldHave(sizeGreaterThan(0))
                .asDynamicIterable().stream()
                .map(StudentTableRow::new)
                .filter(row -> row.getName().equals(name))
                .findFirst().orElseThrow();
    }

}
