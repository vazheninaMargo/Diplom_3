package tests.chrome.constructor;

import io.qameta.allure.junit4.DisplayName;
import org.junit.BeforeClass;
import org.junit.Test;
import pageObjects.constructor.ConstructorPageModel;

import static com.codeborne.selenide.Selenide.open;

@DisplayName("ConstructorSectionsTests - Chrome Browser")
public class ConstructorSectionsTests {

    static ConstructorPageModel constructorPageModel;

    @BeforeClass
    public static void prepare() {
        // открытие и создание экземпляра страницы
        constructorPageModel = open("https://stellarburgers.nomoreparties.site", ConstructorPageModel.class);
    }

    @Test
    @DisplayName("Transition to buns section")
    public void testTransitionToBunSection() {
        constructorPageModel.checkMainIsExisted();

        // переход к разделу Булки
        constructorPageModel.clickBun();
    }

    @Test
    @DisplayName("Transition to sauces section")
    public void testTransitionToSaucesSection() {
        constructorPageModel.checkMainIsExisted();

        // переход к разделу Соусы
        constructorPageModel.clickSauces();
    }

    @Test
    @DisplayName("Transition to fillings section")
    public void testTransitionToFillingsSection() {
        constructorPageModel.checkMainIsExisted();

        // переход к разделу Начинки
        constructorPageModel.clickFillings();
    }
}
