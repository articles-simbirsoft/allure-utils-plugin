package com.simbirsoft.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeSuite;

public abstract class BaseTest {
    @BeforeSuite
    public void setUp() {
        Configuration.timeout = 20000;
        Configuration.pageLoadTimeout = 35000;
        Configuration.pageLoadStrategy = "eager";
        Configuration.remote = System.getenv("SELENOID_URL");
        Configuration.browserSize = "1920x1080";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        Configuration.browser = "chrome";
        Configuration.browserCapabilities = capabilities;
        Configuration.screenshots = false;
        Configuration.savePageSource = false;

        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(false)
        );
    }
}
