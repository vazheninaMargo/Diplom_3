package tests.yandex.login;

import com.codeborne.selenide.WebDriverRunner;
import helpers.UserTestsHelper;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pageObjects.constructor.ConstructorPageModel;
import pageObjects.forgotPassword.ForgotPasswordPageModel;
import pageObjects.login.LoginPageModel;
import pageObjects.registration.RegistrationPageModel;
import praktikum.LoginUserResponseModel;
import praktikum.UserCreateModel;
import praktikum.UserLoginModel;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

@DisplayName("LoginTests - Yandex Browser")
public class LoginTests {

    private final static String email = "rat8@ratmail.rat";
    private final static String password = "123456";
    private final static String name = "Oleg";

    @Before
    public void prepare() {
        // установка yandexdriver для тестирования в Yandex Browser
        Properties prop = System.getProperties();
        prop.setProperty("webdriver.chrome.driver", "/Users/margaritavazenina/documents/webdriver/bin/yandexdriver");
        System.setProperties(prop);
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriver webDriver = new ChromeDriver(chromeOptions);
        WebDriverRunner.setWebDriver(webDriver);

        // подготовка пользователя
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        UserCreateModel userCreateModel = new UserCreateModel(email, password, name);
        UserTestsHelper.sendPostCreateUser(userCreateModel);
    }

    @Test
    @DisplayName("Test login by Войти в аккаунт from main page")
    public void testLoginByLoginToAccountButton() {
        // открывается страница и создаётся экземпляр класса страницы
        ConstructorPageModel objMainPage = open("https://stellarburgers.nomoreparties.site", ConstructorPageModel.class);

        // выполняем переход на страницу Входа
        LoginPageModel objcLoginPage = objMainPage.clickAccountLoginButton();
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
        LoginPageModel objcLoginPage = objMainPage.clickPersonalAccountButtonForLogin();
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
        LoginPageModel objcLoginPage = objForgotPasswordPage.clickLoginButton();
        objcLoginPage.checkMainIsExisted();

        // заполняем данные и осуществляем вход в аккаунт
        objcLoginPage.login(email, password);
        objcLoginPage.checkMainIsNotExisted();
    }

    @After
    public void clear() {
        // Возвращение тестового окружения к исходному виду
        UserLoginModel userLoginModel = new UserLoginModel(
                email,
                password
        );
        Response loginResponse = UserTestsHelper.sendPostLoginUser(userLoginModel);

        if (loginResponse.statusCode() != 200) {
            closeWebDriver();
            return;
        }

        String token = loginResponse.body().as(LoginUserResponseModel.class).getAccessToken();
        if (token != null) {
            UserTestsHelper.sendDeleteCourier(token);
        }

        closeWebDriver();
    }

    @AfterClass
    public static void closeBrowser() {
        closeWebDriver();
    }
}
