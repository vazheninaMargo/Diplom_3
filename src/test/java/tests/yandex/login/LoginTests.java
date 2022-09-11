package tests.yandex.login;

import com.codeborne.selenide.WebDriverRunner;
import utils.ApiClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.constructor.ConstructorPageModel;
import pages.forgotPassword.ForgotPasswordPageModel;
import pages.login.LoginPageModel;
import pages.registration.RegistrationPageModel;
import api.model.LoginUserResponseModel;
import api.model.UserCreateModel;
import api.model.UserLoginModel;

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
        UserLoginModel userLoginModel = new UserLoginModel(
                email,
                password
        );
        Response loginResponse = ApiClient.sendPostLoginUser(userLoginModel);

        if (loginResponse.statusCode() != 200) {
            closeWebDriver();
            return;
        }

        String token = loginResponse.body().as(LoginUserResponseModel.class).getAccessToken();
        if (token != null) {
            ApiClient.sendDeleteCourier(token);
        }

        closeWebDriver();
    }

    @AfterClass
    public static void closeBrowser() {
        closeWebDriver();
    }
}
