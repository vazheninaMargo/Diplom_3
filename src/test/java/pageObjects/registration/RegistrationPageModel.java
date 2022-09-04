package pageObjects.registration;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import pageObjects.login.LoginPageModel;

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

    //метод заполнения поля ввода Имя
    private void setName(String name) {
        nameField.setValue(name);
    }

    //метод заполнения поля ввода Email
    private void setEmail(String email) {
        emailField.setValue(email);
    }

    //метод заполнения поля ввода Пароль
    private void setPassword(String password) {
        passwordField.setValue(password);
    }

    //метод нажатия кнопки Зарегистрироваться
    private void clickRegistrationButton() {
        registrationButton.click();
    }

    //метод регистрации в приложении: объединяет заполнение полей ввода и нажатие кнопки Зарегистрироваться
    public void registration(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        clickRegistrationButton();
    }

    public void checkPasswordError() {
        errorParagraph.shouldBe(Condition.visible);
    }

    public void checkMainIsNotExisted() {
        main.shouldNot(Condition.exist);
    }

    public LoginPageModel clickLoginButton() {
        loginButton.click();
        return Selenide.open("https://stellarburgers.nomoreparties.site/login", LoginPageModel.class);
    }
}
