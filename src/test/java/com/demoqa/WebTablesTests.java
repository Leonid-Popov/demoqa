package com.demoqa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WebTablesTests extends BaseTest {

    @BeforeEach
    void openPage() {
        Selenide.open("/webtables");
    }

    String name = "Leonid";
    String lastName = "Popov";
    String userEmail = "leo.popov666@gmail.com";
    String department = "Some Department";

    @Test
    void textBoxTest() {
        //Заполняем форму
        $("#addNewRecordButton").click();
        $(".modal-title").shouldHave(text("Registration Form"));
        $("#firstName").setValue(name);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(userEmail);
        $("#age").setValue("30");
        $("#salary").setValue("50000");
        $("#department").setValue(department);
        $("#submit").click();

        //Проверяем результат в таблице
        SelenideElement element = $$(".rt-tr-group").findBy(text(name));
        element.shouldHave(text(name));
        element.shouldHave(text(lastName));
        element.shouldHave(text("30"));
        element.shouldHave(text(userEmail));
        element.shouldHave(text("50000"));
        element.shouldHave(text(department));
    }

    @Test
    void searchFieldTest() {
        //Заполняем форму
        $("#addNewRecordButton").click();
        $(".modal-title").shouldHave(text("Registration Form"));
        $("#firstName").setValue(name);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(userEmail);
        $("#age").setValue("30");
        $("#salary").setValue("50000");
        $("#department").setValue(department);
        $("#submit").click();

        //Ищем юзера по email
        $("#searchBox").click();
        $("#searchBox").setValue(userEmail);

        //Проверяем результат фильтрации
        ElementsCollection tableValues = $$(".rt-tr-group");
        for (int i = 1; i < tableValues.size(); i++) {
            //Проверяем результат поиска в первой строке
            SelenideElement element = $$(".rt-tr-group").get(0);
            element.shouldHave(text(name), text(lastName), text("30"), text(userEmail), text("50000"), text(department));

            //Проверяем что остальные строки пустые
            SelenideElement emptyRow = $$(".rt-tr-group").get(i);
            emptyRow.shouldBe(Condition.empty);
        }



        // сортировки (алфавит, возраст)
    }
}
