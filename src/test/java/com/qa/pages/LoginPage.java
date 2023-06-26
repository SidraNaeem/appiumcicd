package com.qa.pages;

import com.qa.BaseTest;

import com.qa.utils.TestUtils;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class LoginPage extends BaseTest {
    TestUtils utils = new TestUtils();
    @AndroidFindBy (accessibility = "test-Username")private WebElement usernamefield;
    @AndroidFindBy (accessibility = "test-Password")private WebElement passwordfield;
    @AndroidFindBy (accessibility = "test-LOGIN")private WebElement loginBtn;
    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]" +
            "/android.widget.TextView")private WebElement errTxt;
    public void enterUsername(String username){
        click(usernamefield);
        sendKeys(usernamefield, username, "login with " + username);

    }
    public void enterPassword(String password){
        click(passwordfield);
        sendKeys(passwordfield, password, "login with " + password);
    }
    public ProductsPage clickLoginBtn(){
        click(loginBtn, "press login button");
        return new ProductsPage();
    }
    public String getErrTxt(){
        String err = getText(errTxt , "Error text is ");
        return err;
    }
}
