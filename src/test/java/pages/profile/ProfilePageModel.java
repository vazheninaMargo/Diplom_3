package pages.profile;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import pages.constructor.ConstructorPageModel;
import pages.login.LoginPageModel;

import static com.codeborne.selenide.Selenide.page;

public class ProfilePageModel {

    //локатор родительского контейнера
    @FindBy(how = How.XPATH, using = ".//main[@class='App_componentContainer__2JC2W' and div/@class='Account_account__vgk_w']")
    private SelenideElement main;
    //локатор логотипа
    @FindBy(how = How.CLASS_NAME, using = "AppHeader_header__logo__2D0X2")
    private SelenideElement logo;
    //локатор поля кнопки Выйти
    @FindBy(how = How.XPATH, using = ".//button[text()='Выход']")
    private SelenideElement logoutButton;

    @Step("Выход из аккаунта по кнопке Выход. Переход на экран Входа")
    public LoginPageModel logout() {
        clickLogoutButton();
        return page(LoginPageModel.class);
    }

    @Step("Переход на экран конструктора по нажатию на логотип")
    public ConstructorPageModel transitionToConstructor() {
        clickByLogo();
        return page(ConstructorPageModel.class);
    }

    public void checkMainIsNotExisted() {
        main.shouldNot(Condition.exist);
    }

    public void checkMainIsExisted() {
        main.shouldBe(Condition.exist);
    }

    @Step("Нажатие кнопки Выход")
    private void clickLogoutButton() {
        logoutButton.click();
    }

    @Step("Нажатие на логотип")
    private void clickByLogo() {
        logo.click();
    }
}
