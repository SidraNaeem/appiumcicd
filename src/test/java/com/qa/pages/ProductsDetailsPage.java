package com.qa.pages;

import com.qa.BaseTest;
import com.qa.utils.TestUtils;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class ProductsDetailsPage extends BaseTest {
    TestUtils utils = new TestUtils();
    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]" +
            "/android.widget.TextView[1]")private WebElement SLBTitle;
    @AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]")
    private WebElement SLBdescription;
    @AndroidFindBy (accessibility = "test-BACK TO PRODUCTS")private WebElement backToProd;
    @AndroidFindBy (accessibility = "test-Price")private WebElement DetailsPrice;

    public String getSLBTitle(){
        String title = getText(SLBTitle , "The Product Title is ");
        return title;
    }
    public String getSLBdescription(){
        String title = getText(SLBdescription , "The Product Title is ");
        return title;    }
    public ProductsPage clickBacktoProd(){
        click(backToProd , "The Title is ");
        return new ProductsPage();
    }
    public String getDetailsPrice(){
        String title = getText(DetailsPrice , "The Product Title is ");
        return title;
    }

}
