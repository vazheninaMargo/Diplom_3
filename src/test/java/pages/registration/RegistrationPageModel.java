package pages.registration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import pages.login.LoginPageModel;

import static com.codeborne.selenide.Selenide.page;

public class RegistrationPageModel {

    //локатор родительского контейнера
    @FindBy(how = How.XPATH, using = ".//main[@class='App_componentContainer__2JC2W' and div/h2[text()='Регистрация']]")
    private SelenideElement main;
    //локатор поля ввода Имя
    @FindBy(how = How.XPATH, using = ".//div[@class='input__container']/div[label/text()='Имя']/input")
    private SelenideElement nameField;
    //локатор поля ввода Email
    @FindBy(how = How.XPATH, using = ".//div[@class='input__container']/div[label/text()='Email']/input")
    private SelenideElement emailField;
    //локатор поля ввода Пароль
    @FindBy(how = How.XPATH, using = ".//div[@class='input__container']/div[label/text()='Пароль']/input")
    private SelenideElement passwordField;
    //локатор поля кнопки Зарегистрироваться
    @FindBy(how = How.XPATH, using = ".//button[text() = 'Зарегистрироваться']")
    private SelenideElement registrationButton;
    //локатор поля с ошибкой пароля
    @FindBy(how = How.XPATH, using = ".//p[text() = 'Некорректный пароль']")
    private SelenideElement errorParagraph;
    //локатор поля кнопки Войти
    @FindBy(how = How.XPATH, using = ".//a[text() = 'Войти']")
    private SelenideElement loginButton;

    @Step("Заполнение полей формы для регистрации, и переход на экран Входа по Зарегистрироваться ")
    public LoginPageModel registration(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        clickRegistrationButton();
        return page(LoginPageModel.class);
    }

    public void checkPasswordError() {
        errorParagraph.shouldBe(Condition.visible);
    }

    public void checkMainIsNotExisted() {
        main.shouldNot(Condition.exist);
    }

    @Step("Переход на экран Входа по кнопке Войти")
    public LoginPageModel clickLoginButton() {
        loginButton.click();
        return page(LoginPageModel.class);
    }

    @Step("Заполнение поля ввода Имя")
    private void setName(String name) {
        nameField.setValue(name);
    }

    @Step("Заполнение поля ввода Email")
    private void setEmail(String email) {
        emailField.setValue(email);
    }

    @Step("Заполнение поля ввода Пароль")
    private void setPassword(String password) {
        passwordField.setValue(password);
    }

    @Step("Нажатие кнопки Зарегистрироваться")
    private void clickRegistrationButton() {
        registrationButton.click();
    }
}
