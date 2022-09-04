package tests.chrome.account;

import helpers.UserTestsHelper;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageObjects.login.LoginPageModel;
import pageObjects.constructor.ConstructorPageModel;
import pageObjects.profile.ProfilePageModel;
import praktikum.LoginUserResponseModel;
import praktikum.UserCreateModel;
import praktikum.UserLoginModel;

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
}
