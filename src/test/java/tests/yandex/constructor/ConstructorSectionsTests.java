package tests.yandex.constructor;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.junit4.DisplayName;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.constructor.ConstructorPageModel;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

@DisplayName("ConstructorSectionsTests - Yandex Browser")
public class ConstructorSectionsTests {

    private static ConstructorPageModel constructorPageModel;

    @BeforeClass
    public static void prepare() {
        // установка yandexdriver для тестирования в Yandex Browser
        Properties prop = System.getProperties();
        prop.setProperty("webdriver.chrome.driver", "/Users/margaritavazenina/documents/webdriver/bin/yandexdriver");
        System.setProperties(prop);
        ChromeOptions chromeOptions = new ChromeOptions();
        WebDriver webDriver = new ChromeDriver(chromeOptions);
        WebDriverRunner.setWebDriver(webDriver);

        // открытие и создание экземпляра страницы
        constructorPageModel = open("https://stellarburgers.nomoreparties.site", ConstructorPageModel.class);
    }

    @Test
    @DisplayName("Transition to buns section")
    public void testTransitionToBunSection() {
        constructorPageModel.checkMainIsExisted();

        // переход к разделу Булки
        constructorPageModel.transitionToBun();
    }

    @Test
    @DisplayName("Transition to sauces section")
    public void testTransitionToSaucesSection() {
        constructorPageModel.checkMainIsExisted();

        // переход к разделу Соусы
        constructorPageModel.transitionToSauces();
    }

    @Test
    @DisplayName("Transition to fillings section")
    public void testTransitionToFillingsSection() {
        constructorPageModel.checkMainIsExisted();

        // переход к разделу Начинки
        constructorPageModel.transitionToFillings();
    }

    @AfterClass
    public static void closeBrowser() {
        closeWebDriver();
    }
}
