package org.selenium.pom.tests;

//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.*;
import org.selenium.pom.utils.ConfigLoader;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class MyFirstTestCase extends BaseTest {
//    @Test
    //@org.junit.Test
    //@Test
    public void guestCheckoutUsingDirectBankTransfer() throws IOException, InterruptedException {

        String searchFor = "Blue";
        BillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);
        Product product = new Product(1215);
        //User user = new User(ConfigLoader.getInstance().getUsername(), ConfigLoader.getInstance().getPassword());

        StorePage storePage = new HomePage(getDriver())
                .load()
                .getMyHeader()
                .navigateToStoreUsingMenu()
                .search(searchFor);
        //storePage.isLoaded();
        //Assert.assertEquals(storePage.getTitle(), "Search results: “" + searchFor + "”"); versi testng
        Assert.assertEquals(storePage.getTitle(), "Search results: “" + searchFor + "”");

        storePage.getProductThumbnail().addToCart(product.getName());


        CartPage cartPage = storePage.getProductThumbnail().viewCart();
        //cartPage.isLoaded();
        //Assert.assertEquals(cartPage.getProductName(), product.getName()); versi testng
        Assert.assertEquals(cartPage.getProductName(), product.getName());

        CheckoutPage checkoutPage = cartPage.checkout()
                .setBillingAddress(billingAddress)
                .selectDirectBankTransfer()
                .placeOrder();

        //Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received."); versi testng
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }

    //@org.junit.Test
    //@Test
    public void loginAndCheckoutUsingDirectBankTransfer() throws IOException, InterruptedException {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Acer\\Documents\\ChromeDriver\\firefoxdriver.exe");
        String searchFor = "Blue";
        BillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);
        Product product = new Product(1215);
        User user = new User(ConfigLoader.getInstance().getUsername(), ConfigLoader.getInstance().getPassword());

        StorePage storePage = new HomePage(getDriver())
                .load()
                .getMyHeader()
                .navigateToStoreUsingMenu()
                .search(searchFor);
        //storePage.isLoaded();
        //Assert.assertEquals(storePage.getTitle(), "Search results: “" + searchFor + "”");
        Assert.assertEquals(storePage.getTitle(), "Search results: “" + searchFor + "”");

        storePage.getProductThumbnail().addToCart(product.getName());


        CartPage cartPage = storePage.getProductThumbnail().viewCart();
        //cartPage.isLoaded();
        //Assert.assertEquals(cartPage.getProductName(), product.getName());

        Assert.assertEquals(cartPage.getProductName(), product.getName());

        //User user = JacksonUtils.deserializeJson("user.json", User.class);
        CheckoutPage checkoutPage = cartPage.checkout()
                .showLogin()
                .setLoginDetail(user)
                .setBillingAddress(billingAddress)
                .selectDirectBankTransfer()
                .placeOrder();

        //Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");

    }
}
