package pageObjects.forgotPassword;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import pageObjects.login.LoginPageModel;

import static com.codeborne.selenide.Selenide.page;

public class ForgotPasswordPageModel {
    //локатор родительского контейнера
    @FindBy(how = How.XPATH, using = ".//main[@class='App_componentContainer__2JC2W' and div/h2[text() = 'Восстановление пароля']")
    private SelenideElement main;
    //локатор поля кнопки Войти
    @FindBy(how = How.XPATH, using = ".//a[text()='Войти']")
    private SelenideElement loginButton;

    //метод нажатия кнопки Войти
    public LoginPageModel clickLoginButton() {
        loginButton.click();
        return page(LoginPageModel.class);
    }

    public void checkMainIsNotExisted() {
        main.shouldNot(Condition.exist);
    }

    public void checkMainIsExisted() {
        main.shouldBe(Condition.exist);
    }
}
