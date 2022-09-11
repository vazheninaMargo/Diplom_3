package tests.yandex.transitionToConstructor;

import com.codeborne.selenide.WebDriverRunner;
import utils.ApiClient;
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
import pages.constructor.ConstructorPageModel;
import pages.login.LoginPageModel;
import pages.profile.ProfilePageModel;
import api.client.LoginResponseModel;
import api.client.RegistrationModel;
import api.client.LoginModel;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

@DisplayName("TransitionToConstructorTests - Yandex Browser")
public class TransitionToConstructorTests {
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
        RegistrationModel userCreateModel = new RegistrationModel(email, password, name);
        ApiClient.sendPostCreateUser(userCreateModel);

        // авторизация
        LoginModel userLoginModel = new LoginModel(
                email,
                password
        );
        ApiClient.sendPostLoginUser(userLoginModel);
    }

    @Test
    @DisplayName("Transition to constructor page from personal account by logo")
    public void testTransitionToConstructor() {
        // авторизация в аккаунте
        LoginPageModel loginPageModel = open("https://stellarburgers.nomoreparties.site/login", LoginPageModel.class);

        // переход на страницу персонального аккаунта
        ProfilePageModel profilePageModel = loginPageModel.login(email, password).openAccountByPersonalAccountButton();

        // проверка существования страницы персонального аккаунта
        profilePageModel.checkMainIsExisted();

        // переход на страницу конструктора по клику на лого
        ConstructorPageModel constructorPageModel = profilePageModel.transitionToConstructor();

        // проверка существования страницы конструктора
        constructorPageModel.checkMainIsExisted();
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

    @AfterClass
    public static void closeBrowser() {
        closeWebDriver();
    }
}
