package com.demoqa;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("#### Start tests ####");
    }

    @BeforeEach
    void openPage() {
        Selenide.open("https://demoqa.com/automation-practice-form");
    }

    @AfterEach
    void closePage() {
        WebDriverRunner.closeWindow();
    }

    @AfterAll
    static void afterAll(){
        System.out.println("#### Finish tests ####");
    }
}
