package org.example.pom.elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class StudentTableRow {

    private final SelenideElement root;

    public StudentTableRow(SelenideElement root) {
        this.root = root;
    }

    public String getName() {
        return root.$("td:nth-child(2)").getText();
    }

    public String getStatus() {
        return root.$("td:nth-child(4)").getText();
    }

    public void clickTrashIcon() {
        root.$$("td button").findBy(Condition.text("delete")).click();
        root.$$("td button").findBy(Condition.text("restore_from_trash")).shouldBe(Condition.visible);
    }

    public void clickRestoreFromTrashIcon() {
        root.$$("td button").findBy(Condition.text("restore_from_trash")).click();
        root.$$("td button").findBy(Condition.text("delete")).shouldBe(Condition.visible);
    }

}
