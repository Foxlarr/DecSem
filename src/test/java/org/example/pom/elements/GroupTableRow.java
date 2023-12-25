package org.example.pom.elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;



public class GroupTableRow {

    private final SelenideElement root;

    public GroupTableRow(SelenideElement root) {
        this.root = root;
    }

    public String getTitle() {
        return root.$("td:nth-child(2)").getText();
    }

    public String getStatus() {
        return root.$("td:nth-child(3)").getText();
    }

    public void clickTrashIcon() {
        root.$$("td button").findBy(Condition.text("delete")).click();
        root.$$("td button").findBy(Condition.text("restore_from_trash")).shouldBe(Condition.visible);
    }

    public void clickRestoreFromTrashIcon() {
        root.$$("td button").findBy(Condition.text("restore_from_trash")).click();
        root.$$("td button").findBy(Condition.text("delete")).shouldBe(Condition.visible);
    }

    public void clickAddStudentsIcon() {
        root.$("td button i.material-icons").click();
    }

    public void clickZoomInIcon() {
        root.$$(".material-icons").stream()
                .filter(button -> button.getText().equals("zoom_in"))
                .findFirst()
                .ifPresent(SelenideElement::click);
    }



    public void waitStudentsCount(int expectedCount) {
        root.$("td:nth-child(4) span").shouldHave(Condition.text(Integer.toString(expectedCount))).shouldBe(Condition.visible);
    }
}
