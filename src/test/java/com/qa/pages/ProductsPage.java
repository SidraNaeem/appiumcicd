package com.qa.pages;

import com.qa.BaseTest;
import com.qa.utils.TestUtils;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class ProductsPage extends HamburgerPage {
    TestUtils utils = new TestUtils();
    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]" +
            "/android.view.ViewGroup/android.widget.TextView")private WebElement title;
    @AndroidFindBy (xpath = "(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]")private WebElement SLBTitle;
    @AndroidFindBy (xpath = "(//android.widget.TextView[@content-desc=\"test-Price\"])[1]")private WebElement SLBPrice;
    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Menu\"]/android.view.ViewGroup/android.widget.ImageView")private WebElement hamburger;

    public String getTitle(){
        String productTitle = getText(title ,"The Product Title is ");
        return productTitle;
    }
    public String getSLBTitle(){
        String productTitle = getText(SLBTitle ,"The Product Title is ");
        return productTitle;
    }
    public String getSLBPrice(){
        String productPrice = getText(SLBPrice ,"The Product Price is ");
        return productPrice;
    }
    public ProductsDetailsPage clickSLBTitle(){
        click(SLBTitle, "The Title is ");
        return new ProductsDetailsPage();
    }
    public HamburgerPage clickHamburger(){
        click(hamburger);
        return new HamburgerPage();
    }
}
