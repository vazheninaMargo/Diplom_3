package tests.chrome.account;

import utils.ApiClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.login.LoginPageModel;
import pages.constructor.ConstructorPageModel;
import pages.profile.ProfilePageModel;
import api.client.LoginResponseModel;
import api.client.RegistrationModel;
import api.client.LoginModel;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

@DisplayName("TransitionToAccountTests - Chrome Browser")
public class TransitionToAccountTests {

    private final static String email = "rat9@ratmail.rat";
    private final static String password = "123456";
    private final static String name = "Oleg";

    @Before
    public void prepare() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        RegistrationModel userCreateModel = new RegistrationModel(email, password, name);
        ApiClient.sendPostCreateUser(userCreateModel);
    }

    @Test
    @DisplayName("Test transition to personal account by Личный кабинет button")
    public void testTransitionToPersonalAccount() {
        // выполняем переход на страницу Входа
        LoginPageModel objcLoginPage = open("https://stellarburgers.nomoreparties.site/login", LoginPageModel.class);
        objcLoginPage.checkMainIsExisted();

        ConstructorPageModel constructorPageModel = objcLoginPage.login(email, password);
        constructorPageModel.checkMainIsExisted();

        ProfilePageModel profilePageModel = constructorPageModel.openAccountByPersonalAccountButton();
        profilePageModel.checkMainIsExisted();
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
