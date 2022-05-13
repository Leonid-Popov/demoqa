package com.demoqa;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class TextBoxTest extends BaseTest {

    @BeforeEach
    void openPage() {
        Selenide.open("/text-box");
    }

    @Test
    void textBoxTest() {
        String userName = "Leonid Popov";
        String userEmail = "leo.popov666@gmail.com";
        String currentAddress = "Some address 1";
        String permanentAddress = "Permanent address 2";

        //Заполняем форму
        $("[id=userName]").setValue(userName);
        $("[id=userEmail]").setValue(userEmail);
        $("[id=currentAddress]").setValue(currentAddress);
        $("[id=permanentAddress]").setValue(permanentAddress);
        $("[id=submit]").click();

        //Проверяем результат
        $("[id=output]").$("[id=name]").shouldHave(text(userName));
        $("[id=output]").$("[id=email]").shouldHave(text(userEmail));
        $("[id=output]").$("[id=currentAddress]").shouldHave(text(currentAddress));
        $("[id=output]").$("[id=permanentAddress]").shouldHave(text(permanentAddress));

    }
}
