package com.qa.tests;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;
import com.qa.utils.TestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Method;

public class LoginTests extends BaseTest {
    TestUtils utils = new TestUtils();
    LoginPage loginPage;
    ProductsPage productsPage;

    JSONObject loginUsers;
    @BeforeClass
    public void beforeClass() throws IOException {
        InputStream datais = null;
        try {
            String dataFileName = "data/loginUsers.json";
            datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
            JSONTokener tokener = new JSONTokener(datais);
            loginUsers = new JSONObject(tokener);
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {
            if (datais != null){
                datais.close();
            }
        }
        closeApp();
        launchApp();
    }
    @BeforeMethod
    public void beforeMethod(Method m) {

        loginPage = new LoginPage();
        utils.log().info("\n" + "**********Starting test " + m.getName() + "**********" + "\n");
    }
    @Test
    public void invalidUserName(){
        loginPage.enterUsername(loginUsers.getJSONObject("invalidUser").getString("username"));
        loginPage.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password"));
        loginPage.clickLoginBtn();

        String actualErrTxt = loginPage.getErrTxt();
        String expectedErrTxt = getStrings().get("err_invalid_username_password");
        utils.log().info(actualErrTxt + expectedErrTxt);
        Assert.assertEquals(actualErrTxt , expectedErrTxt);
    }

    @Test
    public void invalidPassword(){
        loginPage.enterUsername(loginUsers.getJSONObject("invalidPassword").getString("username"));
        loginPage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
        loginPage.clickLoginBtn();

        String actualErrTxt = loginPage.getErrTxt();
        String expectedErrTxt = getStrings().get("err_invalid_username_password");
        utils.log().info(actualErrTxt + expectedErrTxt);
        Assert.assertEquals(actualErrTxt , expectedErrTxt);
    }

    @Test
    public void validLogin(){
        loginPage.enterUsername(loginUsers.getJSONObject("validLogin").getString("username"));
        loginPage.enterPassword(loginUsers.getJSONObject("validLogin").getString("password"));
        productsPage = loginPage.clickLoginBtn();

        String actualproductTitle = productsPage.getTitle();
        String expectedproductTitle = getStrings().get("product_title");
        utils.log().info(actualproductTitle + expectedproductTitle);
        Assert.assertEquals(actualproductTitle , expectedproductTitle);
    }


}
