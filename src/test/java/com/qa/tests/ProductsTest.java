package com.qa.tests;

import com.qa.BaseTest;
import com.qa.pages.HamburgerPage;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsDetailsPage;
import com.qa.pages.ProductsPage;
import com.qa.utils.TestUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class ProductsTest extends BaseTest {
    TestUtils utils = new TestUtils();
    LoginPage loginPage;
    ProductsPage productsPage;

    ProductsDetailsPage productsDetailsPage;
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
        }
        finally {
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
        loginPage.enterUsername(loginUsers.getJSONObject("validLogin").getString("username"));
        loginPage.enterPassword(loginUsers.getJSONObject("validLogin").getString("password"));
    }
    @AfterMethod
    public void afterMethod(){
        closeApp();
        launchApp();
    }
    @Test
    public void productsTitle(){
        SoftAssert sa = new SoftAssert();

        productsPage = loginPage.clickLoginBtn();
        String SLBtitle = productsPage.getSLBTitle();
        sa.assertEquals(SLBtitle , getStrings().get("SLB_Title"));
        utils.log().info(SLBtitle);
        String SLBPrice = productsPage.getSLBPrice();
        sa.assertEquals(SLBPrice,getStrings().get("SLB_Price"));

        sa.assertAll();
    }
    @Test
    public void productsOnDetailsPage(){
        SoftAssert sa = new SoftAssert();


        productsPage = loginPage.clickLoginBtn();
        productsDetailsPage = productsPage.clickSLBTitle();
        String SLBtitle = productsDetailsPage.getSLBTitle();
        sa.assertEquals(SLBtitle , getStrings().get("products_details_SLB_Title"));
        String SLBDesc = productsDetailsPage.getSLBdescription();
        sa.assertEquals(SLBDesc , getStrings().get("products_details_SLB_descrip"));
        productsPage = productsDetailsPage.clickBacktoProd();
        sa.assertAll();
    }
    @Test
    public void scrollOnDetailsPage(){
        SoftAssert sa = new SoftAssert();


        productsPage = loginPage.clickLoginBtn();
        productsDetailsPage = productsPage.clickSLBTitle();
        String SLBtitle = productsDetailsPage.getSLBTitle();
        sa.assertEquals(SLBtitle , getStrings().get("products_details_SLB_Title"));
        String SLBDesc = productsDetailsPage.getSLBdescription();
        sa.assertEquals(SLBDesc , getStrings().get("products_details_SLB_descrip"));
//        productsPage = productsDetailsPage.clickBacktoProd();
        scrollScreen();
        String SlbPrice = productsPage.getSLBPrice();
        sa.assertEquals(SlbPrice , getStrings().get("SLB_price"));
        sa.assertAll();
    }


}
