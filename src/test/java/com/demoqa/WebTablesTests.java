package com.demoqa;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class WebTablesTests extends BaseTest {

    @BeforeEach
    void openPage() {
        Selenide.open("/webtables");
    }

    @Test
    void textBoxTest() {
        String name = "Leonid";
        String lastName = "Popov";
        String userEmail = "leo.popov666@gmail.com";
        String department = "Some Department";

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

        //Проверяем результат
//        $("[id=output]").$("[id=name]").shouldHave(text(userName));
//        $("[id=output]").$("[id=email]").shouldHave(text(userEmail));
//        $("[id=output]").$("[id=currentAddress]").shouldHave(text(currentAddress));
//        $("[id=output]").$("[id=permanentAddress]").shouldHave(text(permanentAddress));

    }
}
