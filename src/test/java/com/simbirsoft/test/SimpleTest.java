package com.simbirsoft.test;

import com.codeborne.selenide.Condition;
import com.simbirsoft.page.SimbirsoftMain;
import io.qameta.allure.Epic;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.simbirsoft.data.DataClass.MAIN_PAGE_HEADER;
import static com.simbirsoft.data.DataClass.MAIN_PAGE_URL;

public final class SimpleTest extends BaseTest {
    @BeforeClass
    public void beforeClass() {
        page = SimbirsoftMain.openPage(MAIN_PAGE_URL);
    }

    private SimbirsoftMain page;

    @Epic(MAIN_PAGE_URL)
    @Test(description = "Проверка заголовка главной страницы")
    public void testHeader() {
        page.header
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text(MAIN_PAGE_HEADER));
    }
}
