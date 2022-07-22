package com.demoqa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;


public class WebTablesTests extends BaseTest {

    @BeforeEach
    void openPage() {
        step("Open webtable page", () -> {
            open("/webtables");
        });
    }

    Faker faker = new Faker();
    String name = faker.name().firstName();
    String lastName = faker.name().lastName();
    String userEmail = faker.name().lastName() + "@gmail.com";
    String department = faker.company().name();

    @Test
    @DisplayName("Successfully fill table test")
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
    @DisplayName("Successfully fill and edit table test")
    void textBoxTestEdit() {
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

        //Создаем новые данные
        Faker faker = new Faker(new Locale("ru"));
        String name = faker.name().firstName();
        String lastName = faker.name().lastName();
        String department = faker.company().name();

        //Редактируем нашего юзера
        step("Edit personal information", () -> {
            $x("//div[@class = 'rt-td'][text () = '" + userEmail + "']/..//" +
                    "descendant::span[contains(@id, 'edit-record')]").click();
            $(".modal-title").shouldHave(text("Registration Form"));
            $("#salary").setValue("100000");
            $("#firstName").setValue(name);
            $("#lastName").setValue(lastName);
            $("#department").setValue(department);
            $("#submit").click();

            //Проверяем результат в таблице
            step("Verify form data in the table", () -> {
                SelenideElement element = $$(".rt-tr-group").findBy(text(userEmail));
                element.shouldHave(text(name));
                element.shouldHave(text(lastName));
                element.shouldHave(text("30"));
                element.shouldHave(text(userEmail));
                element.shouldHave(text("100000"));
                element.shouldHave(text(department));
            });
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

    @Test
    @DisplayName("Validation email field test")
    void validationEmailFieldTest() {

        Faker faker = new Faker();
        String userEmail = faker.name().fullName() + "@gmail.com";

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
        step("Check warnings in the form", () -> {
            SelenideElement element = $$(".rt-tr-group").findBy(text(name));
            element.shouldHave(text(name));
            element.shouldHave(text(lastName));
            element.shouldHave(text("30"));

            //todo: дописать проверку цвета
//            $("#userEmail").shouldHave()
            element.shouldHave(text("50000"));
            element.shouldHave(text(department));
        });
    }
}






