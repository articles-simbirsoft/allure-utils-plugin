package com.simbirsoft.page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public final class SimbirsoftMain {
    public final SelenideElement header = $(".iv-header h1");

    @Step("Открыть страницу.")
    public static SimbirsoftMain openPage(final String url){
        return open(url, SimbirsoftMain.class);
    }

}
