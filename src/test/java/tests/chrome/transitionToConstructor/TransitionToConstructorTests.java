package tests.chrome.transitionToConstructor;

import utils.ApiClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.constructor.ConstructorPageModel;
import pages.login.LoginPageModel;
import pages.profile.ProfilePageModel;
import api.model.LoginUserResponseModel;
import api.model.UserCreateModel;
import api.model.UserLoginModel;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

@DisplayName("TransitionToConstructorTests - Chrome Browser")
public class TransitionToConstructorTests {
    private final static String email = "rat8@ratmail.rat";
    private final static String password = "123456";
    private final static String name = "Oleg";

    @Before
    public void prepare() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        UserCreateModel userCreateModel = new UserCreateModel(email, password, name);
        ApiClient.sendPostCreateUser(userCreateModel);

        UserLoginModel userLoginModel = new UserLoginModel(
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
}
