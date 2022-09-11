package tests.chrome.login;

import utils.ApiClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.forgotPassword.ForgotPasswordPageModel;
import pages.login.LoginPageModel;
import pages.constructor.ConstructorPageModel;
import pages.registration.RegistrationPageModel;
import api.client.LoginResponseModel;
import api.client.RegistrationModel;
import api.client.LoginModel;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

@DisplayName("LoginTests - Chrome Browser")
public class LoginTests {

    private final static String email = "rat8@ratmail.rat";
    private final static String password = "123456";
    private final static String name = "Oleg";

    @Before
    public void prepare() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        RegistrationModel userCreateModel = new RegistrationModel(email, password, name);
        ApiClient.sendPostCreateUser(userCreateModel);
    }

    @Test
    @DisplayName("Test login by Войти в аккаунт from main page")
    public void testLoginByLoginToAccountButton() {
        // открывается страница и создаётся экземпляр класса страницы
        ConstructorPageModel objMainPage = open("https://stellarburgers.nomoreparties.site", ConstructorPageModel.class);

        // выполняем переход на страницу Входа
        LoginPageModel objcLoginPage = objMainPage.openAccountLoginByAccountLoginButton();
        objcLoginPage.checkMainIsExisted();

        // заполняем данные и осуществляем вход в аккаунт
        objcLoginPage.login(email, password);
        objcLoginPage.checkMainIsNotExisted();
    }

    @Test
    @DisplayName("Test login by Личный Кабинет from main page")
    public void testLoginByPersonalAccountButton() {
        //открывается страница и создаётся экземпляр класса страницы
        ConstructorPageModel objMainPage = open("https://stellarburgers.nomoreparties.site", ConstructorPageModel.class);

        // выполняем переход на страницу Входа
        LoginPageModel objcLoginPage = objMainPage.openAccountLoginByPersonalAccountButton();
        objcLoginPage.checkMainIsExisted();

        // заполняем данные и осуществляем вход в аккаунт
        objcLoginPage.login(email, password);
        objcLoginPage.checkMainIsNotExisted();
    }

    @Test
    @DisplayName("Test login by Войти from registration page")
    public void testLoginByRegistrationForm() {
        //открывается страница и создаётся экземпляр класса страницы
        RegistrationPageModel objRegistrationPage = open(
                "https://stellarburgers.nomoreparties.site/register",
                RegistrationPageModel.class
        );

        // выполняем переход на страницу Входа
        LoginPageModel objcLoginPage = objRegistrationPage.clickLoginButton();
        objcLoginPage.checkMainIsExisted();

        // заполняем данные и осуществляем вход в аккаунт
        objcLoginPage.login(email, password);
        objcLoginPage.checkMainIsNotExisted();
    }

    @Test
    @DisplayName("Test login by Войти from forgot password page")
    public void testLoginByForgotPasswordPage() {
        //открывается страница и создаётся экземпляр класса страницы
        ForgotPasswordPageModel objForgotPasswordPage = open(
                "https://stellarburgers.nomoreparties.site/forgot-password",
                ForgotPasswordPageModel.class
        );

        // выполняем переход на страницу Входа
        LoginPageModel objcLoginPage = objForgotPasswordPage.openLoginScreenByLoginButton();
        objcLoginPage.checkMainIsExisted();

        // заполняем данные и осуществляем вход в аккаунт
        objcLoginPage.login(email, password);
        objcLoginPage.checkMainIsNotExisted();
    }

    @After
    public void clear() {
        // Возвращение тестового окружения к исходному виду
        LoginModel userLoginModel = new LoginModel(
                email,
                password
        );
        Response loginResponse = ApiClient.sendPostLoginUser(userLoginModel);

        if (loginResponse.statusCode() != 200) {
            closeWebDriver();
            return;
        }

        String token = loginResponse.body().as(LoginResponseModel.class).getAccessToken();
        if (token != null) {
            ApiClient.sendDeleteCourier(token);
        }

        closeWebDriver();
    }
}
