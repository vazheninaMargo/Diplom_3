package pages.login;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import pages.constructor.ConstructorPageModel;

import static com.codeborne.selenide.Selenide.page;

public class LoginPageModel {

    //локатор родительского контейнера
    @FindBy(how = How.XPATH, using = ".//main[@class='App_componentContainer__2JC2W' and div/h2/text()='Вход']")
    private SelenideElement main;
    //локатор поля ввода Email
    @FindBy(how = How.XPATH, using = ".//div[@class='input__container']/div[label/text()='Email']/input")
    private SelenideElement emailField;
    //локатор поля ввода Пароль
    @FindBy(how = How.XPATH, using = ".//div[@class='input__container']/div[label/text()='Пароль']/input")
    private SelenideElement passwordField;
    //локатор поля кнопки Войти
    @FindBy(how = How.XPATH, using = ".//button[text() = 'Войти']")
    private SelenideElement loginButton;

    @Step("Заполнение полей формы для входа в аккаунт, и переход на главный экран по кнопке Войти")
    public ConstructorPageModel login(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLoginButton();
        return page(ConstructorPageModel.class);
    }

    public void checkMainIsNotExisted() {
        main.shouldNot(Condition.exist);
    }

    public void checkMainIsExisted() {
        main.shouldBe(Condition.exist);
    }

    @Step("Заполнение поля ввода Email")
    private void setEmail(String email) {
        emailField.setValue(email);
    }

    @Step("Заполнение поля ввода Пароль")
    private void setPassword(String password) {
        passwordField.setValue(password);
    }

    @Step("Нажатие кнопки Войти")
    private void clickLoginButton() {
        loginButton.click();
    }
}
