package com.demoqa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("#### Start tests ####");
        Configuration.baseUrl = "https://demoqa.com";
//        Configuration.browserSize = "1366x768";
        Configuration.browserSize = "1920x1080";
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
