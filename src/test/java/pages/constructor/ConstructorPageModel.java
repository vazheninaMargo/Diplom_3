package pages.constructor;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import pages.login.LoginPageModel;
import pages.profile.ProfilePageModel;

import static com.codeborne.selenide.Selenide.page;

public class ConstructorPageModel {

    //локатор родительского контейнера
    @FindBy(how = How.XPATH, using = ".//main[@class='App_componentContainer__2JC2W']")
    private SelenideElement main;
    // локатор кнопки Войти в аккаунт
    @FindBy(how = How.XPATH, using = ".//button[text() = 'Войти в аккаунт']")
    private SelenideElement accountLoginButton;
    // локатор кнопки Личный Кабинет
    @FindBy(how = How.XPATH, using = ".//a[@class='AppHeader_header__link__3D_hX' and p[text() = 'Личный Кабинет']]")
    private SelenideElement personalAccountButton;
    //локатор таба Булки
    @FindBy(how = How.XPATH, using = ".//div[@style='display: flex;']/div[span/text()='Булки']")
    private SelenideElement tabBun;
    //локатор таба Соусы
    @FindBy(how = How.XPATH, using = ".//div[@style='display: flex;']/div[span/text()='Соусы']")
    private SelenideElement tabSauces;
    //локатор таба Начинки
    @FindBy(how = How.XPATH, using = ".//div[@style='display: flex;']/div[span/text()='Начинки']")
    private SelenideElement tabFillings;

    @Step("Переход на экран Входа по кнопке Войти в аккаунт")
    public LoginPageModel openAccountLoginByAccountLoginButton() {
        clickAccountLoginButton();
        return page(LoginPageModel.class);
    }

    @Step("Переход на экран Входа по кнопке Личный Кабинет")
    public LoginPageModel openAccountLoginByPersonalAccountButton() {
        clickPersonalAccountButton();
        return page(LoginPageModel.class);
    }

    @Step("Переход на экран Аккаунта по кнопке Личный Кабинет")
    public ProfilePageModel openAccountByPersonalAccountButton() {
        clickPersonalAccountButton();
        return page(ProfilePageModel.class);
    }

    @Step("Переход к разделу Булки")
    public void transitionToBun() {
        clickTabBun();
        tabBun.shouldBe(Condition.visible);
    }

    @Step("Переход к разделу Соусы")
    public void transitionToSauces() {
        clickTabSauces();
        tabSauces.shouldBe(Condition.visible);
    }

    @Step("Переход к разделу Начинки")
    public void transitionToFillings() {
        clickTabFillings();
        tabFillings.shouldBe(Condition.visible);
    }

    public void checkMainIsExisted() {
        main.shouldBe(Condition.exist);
    }

    public void checkMainIsNotExisted() {
        main.shouldNot(Condition.exist);
    }

    @Step("Нажатие кнопки Войти в аккаунт")
    private void clickAccountLoginButton() {
        accountLoginButton.click();
    }

    @Step("Нажатие кнопки Личный кабинет")
    private void clickPersonalAccountButton() {
        personalAccountButton.click();
    }

    @Step("Нажатие вкладки Булки")
    private void clickTabBun() {
        tabBun.click();
    }

    @Step("Нажатие вкладки Соусы")
    private void clickTabSauces() {
        tabSauces.click();
    }

    @Step("Нажатие вкладки Начинки")
    private void clickTabFillings() {
        tabFillings.click();
    }
}
