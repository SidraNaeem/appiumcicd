package com.qa;

import com.qa.pages.HamburgerPage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class MenuPage extends BaseTest{
    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Menu\"]" +
            "/android.view.ViewGroup/android.widget.ImageView")private WebElement hamburger;
    public HamburgerPage clickHamburger(){
        click(hamburger);
        return new HamburgerPage();
    }
}
