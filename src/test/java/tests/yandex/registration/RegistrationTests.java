package tests.yandex.registration;

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
import pages.registration.RegistrationPageModel;
import api.client.LoginResponseModel;
import api.client.LoginModel;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

@DisplayName("RegistrationTests - Yandex Browser")
public class RegistrationTests {

    private final static String email = "rat8@ratmail.rat";
    private final static String password = "123456";
    private final static String incorrectPassword = "1234";
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
    }

    @Test
    @DisplayName("Test with correct data")
    public void testOfSuccessRegistration() {
        //открывается страница и создаётся экземпляр класса страницы
        RegistrationPageModel objRegistrationPage = open(
                "https://stellarburgers.nomoreparties.site/register",
                RegistrationPageModel.class
        );

        // выполняем регистрацию
        objRegistrationPage.registration(name, email, password);
        objRegistrationPage.checkMainIsNotExisted();
    }

    @Test
    @DisplayName("Test with incorrect password")
    public void testOfFailureRegistration() {
        //открывается страница и создаётся экземпляр класса страницы
        RegistrationPageModel objRegistrationPage = open(
                "https://stellarburgers.nomoreparties.site/register",
                RegistrationPageModel.class
        );

        // выполняем регистрацию
        objRegistrationPage.registration(name, email, incorrectPassword);
        // проверяем что отобразилось поле с ошибкой пароля
        objRegistrationPage.checkPasswordError();
    }

    @After
    public void clear() {
        // Возвращение тестового окружения к исходному виду
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";

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
