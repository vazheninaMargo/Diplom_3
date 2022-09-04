package tests.chrome.registration;

import helpers.UserTestsHelper;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import pageObjects.registration.RegistrationPageModel;
import praktikum.LoginUserResponseModel;
import praktikum.UserLoginModel;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

@DisplayName("RegistrationTests - Chrome Browser")
public class RegistrationTests {

    private final static String email = "rat8@ratmail.rat";
    private final static String password = "123456";
    private final static String incorrectPassword = "1234";
    private final static String name = "Oleg";

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
