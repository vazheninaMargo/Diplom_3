package tests.chrome.transitionToConstructor;

import helpers.UserTestsHelper;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageObjects.constructor.ConstructorPageModel;
import pageObjects.login.LoginPageModel;
import pageObjects.profile.ProfilePageModel;
import praktikum.LoginUserResponseModel;
import praktikum.UserCreateModel;
import praktikum.UserLoginModel;

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
        UserTestsHelper.sendPostCreateUser(userCreateModel);

        UserLoginModel userLoginModel = new UserLoginModel(
                email,
                password
        );
        UserTestsHelper.sendPostLoginUser(userLoginModel);
    }

    @Test
    @DisplayName("Transition to constructor page from personal account by logo")
    public void testTransitionToConstructor() {
        // авторизация в аккаунте
        LoginPageModel loginPageModel = open("https://stellarburgers.nomoreparties.site/login", LoginPageModel.class);

        // переход на страницу персонального аккаунта
        ProfilePageModel profilePageModel = loginPageModel.login(email, password).clickPersonalAccountForProfile();

        // проверка существования страницы персонального аккаунта
        profilePageModel.checkMainIsExisted();

        // переход на страницу конструктора по клику на лого
        ConstructorPageModel constructorPageModel = profilePageModel.clickByLogo();

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
}
