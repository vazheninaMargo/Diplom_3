package pageObjects.constructor;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import pageObjects.login.LoginPageModel;
import pageObjects.profile.ProfilePageModel;

import static com.codeborne.selenide.Selenide.page;

public class ConstructorPageModel {

    //локатор родительского контейнера
    @FindBy(how = How.XPATH, using = ".//main[@class='App_componentContainer__2JC2W']")
    public SelenideElement main;
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

    // метод нажатия кнопки Войти в аккаунт
    public LoginPageModel clickAccountLoginButton() {
        accountLoginButton.click();
        return page(LoginPageModel.class);
    }

    // метод нажатия кнопки Личный Кабинет для перехода на логин
    public LoginPageModel clickPersonalAccountButtonForLogin() {
        personalAccountButton.click();
        return page(LoginPageModel.class);
    }

    public ProfilePageModel clickPersonalAccountForProfile() {
        personalAccountButton.click();
        return page(ProfilePageModel.class);
    }

    public void clickBun() {
        tabBun.click();
        tabBun.shouldBe(Condition.visible);
    }

    public void clickSauces() {
        tabSauces.click();
        tabSauces.shouldBe(Condition.visible);
    }

    public void clickFillings() {
        tabFillings.click();
        tabFillings.shouldBe(Condition.visible);
    }

    public void checkMainIsExisted() {
        main.shouldBe(Condition.exist);
    }

    public void checkMainIsNotExisted() {
        main.shouldNot(Condition.exist);
    }
}
