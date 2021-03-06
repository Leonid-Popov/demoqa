package com.demoqa;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import java.io.File;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static io.qameta.allure.Allure.step;

public class RegistrationFormTest extends BaseTest {

    @Test
    @DisplayName("Successful fill student registration form test")
    void fillStudentRegForm() {
        String name = "Leonid";
        String lastName = "Popov";
        String userEmail = "leo.popov666@gmail.com";
        String userNumber = "9139584699";
        String currentAddress = "Some address 123";

        step("Open registration form", () -> {
            open("/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
            executeJavaScript("$('footer').remove()");
            executeJavaScript("$('#fixedban').remove()");
        });

        step("Fill registration form", () -> {
            $("#firstName").setValue(name);
            $("#lastName").setValue(lastName);
            $("#userEmail").setValue(userEmail);
            $("#genterWrapper").$(byText("Male")).click();
            $("#userNumber").setValue(userNumber);

            //Выбираем дату рождения в календаре
            $("#dateOfBirthInput").click();
            $(".react-datepicker__year-select").selectOption("1992");
            $(".react-datepicker__month-select").selectOption("March");
            $(".react-datepicker__day--023").click();

            //Выбираем увлечения
            $("#subjectsInput").setValue("Math").pressEnter();
            $("#hobbiesWrapper").$(byText("Sports")).click();
            $("#uploadPicture").uploadFile(new File("/Users/gost/Desktop/QA GURU/pic.png"));

            $("#currentAddress").setValue(currentAddress);

            $("#state").scrollTo();
            $("#state").click();
            $("#stateCity-wrapper").$(byText("NCR")).click();
            $("#city").click();
            $("#stateCity-wrapper").$(byText("Delhi")).click();
        });

        step("Submit registration form", () -> {
            $("#submit").click();
        });

        //проверяем заполненную форму
        step("Verify registration data", () -> {
            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
            $x("//td[text() = 'Student Name']/../td[2]").shouldHave(text(name + " " + lastName));
            $x("//td[text() = 'Student Email']/../td[2]").shouldHave(text(userEmail));
            $x("//td[text() = 'Gender']/../td[2]").shouldHave(text("Male"));
            $x("//td[text() = 'Mobile']/../td[2]").shouldHave(text(userNumber));
            $x("//td[text() = 'Date of Birth']/../td[2]").shouldHave(text("23 March,1992"));
            $x("//td[text() = 'Subjects']/../td[2]").shouldHave(text("Math"));
            $x("//td[text() = 'Hobbies']/../td[2]").shouldHave(text("Sports"));
            $x("//td[text() = 'Address']/../td[2]").shouldHave(text(currentAddress));
            $x("//td[text() = 'State and City']/../td[2]").shouldHave(text("NCR Delhi"));
        });
    }
}
