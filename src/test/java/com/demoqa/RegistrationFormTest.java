package com.demoqa;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.$;

public class RegistrationFormTest extends BaseTest {

    @BeforeEach
    void openPage() {
        Selenide.open("/automation-practice-form");
    }

    @Test
    void fillStudentRegForm() {
        String name = "Leonid";
        String lastName = "Popov";
        String userEmail = "leo.popov666@gmail.com";


        $("[id=firstName]").setValue(name);
        $("[id=lastName]").setValue(lastName);
        $("[id=userEmail]").setValue(userEmail);
        $("[name=gender][value=Male]").click();


    }

}
