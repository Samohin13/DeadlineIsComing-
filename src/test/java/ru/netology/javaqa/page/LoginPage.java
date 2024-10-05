package ru.netology.javaqa.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.javaqa.data.DataHelper;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id =login] input");
    private final SelenideElement passwordField = $("[data-test-id = password] input");
    private final SelenideElement buttonLogin = $("[data-test-id =action-login]");
    private final SelenideElement errorNotification = $("[data-test-id = 'error-notification'] .notification__content");

    public void verifyErrorNotification(String expectedText) {
        errorNotification.shouldHave(text(expectedText)).shouldBe(visible);
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        buttonLogin.click();
        return new VerificationPage();

    }
}

