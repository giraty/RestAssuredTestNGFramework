package org.selenium.pom.tests;

import org.selenium.pom.api.actions.CartApi;
import org.selenium.pom.api.actions.SignUpApi;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.utils.FakerUtils;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class CheckoutTest extends BaseTest {

    @Test
    public void GuestCheckoutUsingDirectBankTransfer() throws IOException {
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        BillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);
        CartApi cartApi = new CartApi();
        cartApi.addToCart(1215, 1);
        injectCookiesToBrowser(cartApi.getCookies());
        checkoutPage.load()
                .setBillingAddress(billingAddress)
                .selectDirectBankTransfer()
                .placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }

    @Test
    public void LoginAndCheckoutUsingDirectBankTransfer() throws IOException, InterruptedException {
        String username = "demouser" + new FakerUtils().generateRandomNumber();
        BillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);
        User user = new User()
                .setUsername(username)
                .setPassword("pasaku")
                .setEmail(username + "@gmail.com");
        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);
        CartApi cartApi = new CartApi(signUpApi.getCookies());
        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 1);
        //inject cookies
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        Thread.sleep(5000);
        injectCookiesToBrowser(signUpApi.getCookies());
        checkoutPage.load()
                .setBillingAddress(billingAddress)
                .selectDirectBankTransfer()
                .placeOrder();
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");
    }

}
