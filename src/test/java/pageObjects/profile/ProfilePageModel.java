package pageObjects.profile;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import pageObjects.constructor.ConstructorPageModel;
import pageObjects.login.LoginPageModel;

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

    //метод нажатия кнопки Выйти
    private void clickLogoutButton() {
        logoutButton.click();
    }

    // метод выхода из аккаунта
    public LoginPageModel logout() {
        clickLogoutButton();
        return page(LoginPageModel.class);
    }

    public ConstructorPageModel clickByLogo() {
        logo.click();
        return page(ConstructorPageModel.class);
    }

    public void checkMainIsNotExisted() {
        main.shouldNot(Condition.exist);
    }

    public void checkMainIsExisted() {
        main.shouldBe(Condition.exist);
    }
}
