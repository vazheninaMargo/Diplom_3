package tests.yandex.account;

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
import pageObjects.login.LoginPageModel;
import pageObjects.profile.ProfilePageModel;
import praktikum.LoginUserResponseModel;
import praktikum.UserCreateModel;
import praktikum.UserLoginModel;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

@DisplayName("TransitionToAccountTests - Yandex Browser")
public class TransitionToAccountTests {

    private final static String email = "rat9@ratmail.rat";
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
    @DisplayName("Test transition to personal account by Личный кабинет button")
    public void testTransitionToPersonalAccount() {
        // выполняем переход на страницу Входа
        LoginPageModel objcLoginPage = open("https://stellarburgers.nomoreparties.site/login", LoginPageModel.class);
        objcLoginPage.checkMainIsExisted();

        ConstructorPageModel constructorPageModel = objcLoginPage.login(email, password);
        constructorPageModel.checkMainIsExisted();

        ProfilePageModel profilePageModel = constructorPageModel.clickPersonalAccountForProfile();
        profilePageModel.checkMainIsExisted();
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
