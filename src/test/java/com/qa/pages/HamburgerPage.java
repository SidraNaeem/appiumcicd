package com.qa.pages;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.utils.TestUtils;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class HamburgerPage extends BaseTest {
    TestUtils utils = new TestUtils();
    @AndroidFindBy (accessibility = "test-LOGOUT")private WebElement logoutBtn;
    public LoginPage clickLogoutBtn(){
        click(logoutBtn);
        utils.log().info("Click Logout Button");
        return new LoginPage();
    }
}
