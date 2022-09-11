package tests.chrome.logout;

import utils.ApiClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.login.LoginPageModel;
import pages.profile.ProfilePageModel;
import api.model.LoginUserResponseModel;
import api.model.UserCreateModel;
import api.model.UserLoginModel;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

@DisplayName("LogoutTests - Chrome Browser")
public class LogoutTests {
    private final static String email = "rat8@ratmail.rat";
    private final static String password = "123456";
    private final static String name = "Oleg";

    @Before
    public void prepare() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        UserCreateModel userCreateModel = new UserCreateModel(email, password, name);
        ApiClient.sendPostCreateUser(userCreateModel);
    }

    @Test
    @DisplayName("Logout from personal account by logout button")
    public void testLogout() {
        // авторизация в аккаунте
        LoginPageModel loginPageModel = open("https://stellarburgers.nomoreparties.site/login", LoginPageModel.class);

        // переход на страницу персонального аккаунта
        ProfilePageModel profilePageModel = loginPageModel.login(email, password).openAccountByPersonalAccountButton();

        // проверка существования страницы персонального аккаунта
        profilePageModel.checkMainIsExisted();
        profilePageModel.logout();
        profilePageModel.checkMainIsNotExisted();
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
