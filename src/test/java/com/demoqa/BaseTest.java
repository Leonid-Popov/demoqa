package com.demoqa;

import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        System.out.println("#### Start tests ####");
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1366x768";
//        Configuration.browserSize = "1920x1080";
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        WebDriverRunner.closeWindow();
    }

    @AfterAll
    static void afterAll() {
        System.out.println("#### Finish tests ####");
    }
}
