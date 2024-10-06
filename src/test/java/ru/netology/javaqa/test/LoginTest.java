package ru.netology.javaqa.test;


import org.junit.jupiter.api.*;

import ru.netology.javaqa.data.DataHelper;
import ru.netology.javaqa.data.SQLHelper;
import ru.netology.javaqa.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.javaqa.data.SQLHelper.cleanAuthCodes;
import static ru.netology.javaqa.data.SQLHelper.cleanDatabase;


public class LoginTest {
    LoginPage loginPage;

    @AfterEach
    void tearDown() {
        cleanAuthCodes();

    }

    @AfterAll
    static void tearDownAll() {
        cleanDatabase();
    }

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999", LoginPage.class);

    }

    @Test
    @DisplayName(" should Success ful valid Login")
    void shouldSuccessfulLogin() {
        var authInfo = DataHelper.getAuthInfoTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisible();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode);

    }

    @Test
    @DisplayName("should Get Error Notification Login Random User")
    void shouldGetErrorNotificationLoginRandomUser() {
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotification("Ошибка! Неверно указан логин или пароль");

    }

    @Test
    @DisplayName("Valid login and password not valid code verivication")
    void shouldValidLoginAndPasswordNotValidCodeVerification() {
        var authInfo = DataHelper.getAuthInfoTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisible();
        var verificationCode = DataHelper.generateRandomVerification();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotification("Ошибка! Неверно указан код! Попробуйте ещё раз.");
    }
}
