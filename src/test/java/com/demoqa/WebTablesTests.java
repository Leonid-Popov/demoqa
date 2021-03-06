package com.demoqa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Tag("demoqa")
public class WebTablesTests extends BaseTest {

    @BeforeEach
    void openPage() {
        step("Open webtable page", () -> {
            open("/webtables");
        });
    }

    String name = "Leonid";
    String lastName = "Popov";
    String userEmail = "leo.popov666@gmail.com";
    String department = "Some Department";

    @Test
    @DisplayName("Successfull fill table test")
    void textBoxTest() {
        //Заполняем форму
        step("Fill form", () -> {
            $("#addNewRecordButton").click();
            $(".modal-title").shouldHave(text("Registration Form"));
            $("#firstName").setValue(name);
            $("#lastName").setValue(lastName);
            $("#userEmail").setValue(userEmail);
            $("#age").setValue("30");
            $("#salary").setValue("50000");
            $("#department").setValue(department);
            $("#submit").click();
        });

        //Проверяем результат в таблице
        step("Verify form data in the table", () -> {
            SelenideElement element = $$(".rt-tr-group").findBy(text(name));
            element.shouldHave(text(name));
            element.shouldHave(text(lastName));
            element.shouldHave(text("30"));
            element.shouldHave(text(userEmail));
            element.shouldHave(text("50000"));
            element.shouldHave(text(department));
        });
    }

    @Test
    @DisplayName("Search user by email test")
    void searchFieldTest() {
        //Заполняем форму
        step("Fill form", () -> {
            $("#addNewRecordButton").click();
            $(".modal-title").shouldHave(text("Registration Form"));
            $("#firstName").setValue(name);
            $("#lastName").setValue(lastName);
            $("#userEmail").setValue(userEmail);
            $("#age").setValue("30");
            $("#salary").setValue("50000");
            $("#department").setValue(department);
            $("#submit").click();
        });

        //Ищем юзера по email
        step("Search user by Email", () -> {
            $("#searchBox").click();
            $("#searchBox").setValue(userEmail);
        });

        //Проверяем результат фильтрации
        step("Verify filtration result", () -> {
            ElementsCollection tableValues = $$(".rt-tr-group");
            for (int i = 1; i < tableValues.size(); i++) {
                //Проверяем результат поиска в первой строке
                SelenideElement element = $$(".rt-tr-group").get(0);
                element.shouldHave(text(name), text(lastName), text("30"), text(userEmail)
                        , text("50000"), text(department));

                //Проверяем что остальные строки пустые
                SelenideElement emptyRow = $$(".rt-tr-group").get(i);
                emptyRow.shouldBe(Condition.empty);
            }
        });
    }
}





