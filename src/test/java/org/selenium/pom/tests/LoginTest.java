package org.selenium.pom.tests;

import org.selenium.pom.api.actions.CartApi;
import org.selenium.pom.api.actions.SignUpApi;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.utils.FakerUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.selenium.pom.base.BaseTest;
import java.io.IOException;

public class LoginTest extends BaseTest {

    @Test
    public void loginDuringCheckout() throws IOException, InterruptedException {
        String username = "demouser" + new FakerUtils().generateRandomNumber();
        User user = new User()
                .setUsername(username)
                .setPassword("pasaku")
                .setEmail(username + "@gmail.com");
        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);
        CartApi cartApi = new CartApi();
        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 1);
        //inject cookies
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        Thread.sleep(4000);
        injectCookiesToBrowser(cartApi.getCookies());

        checkoutPage.load();
        Thread.sleep(4000);
        checkoutPage.showLogin().setLoginDetail(user);
        Thread.sleep(4000);
        Assert.assertTrue(checkoutPage.getProductName().contains(product.getName()));

    }

    @Test
    public void loginFails() throws IOException, InterruptedException {
        String username = "failedlogin";
        User user = new User()
                .setUsername(username)
                .setPassword("willfail")
                .setEmail(username + "@gmail.com");
        //SignUpApi signUpApi = new SignUpApi();
        //signUpApi.register(user);
        CartApi cartApi = new CartApi();
        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 1);
        //inject cookies
        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        Thread.sleep(5000);
        injectCookiesToBrowser(cartApi.getCookies());

        checkoutPage.load();
        Thread.sleep(4000);
        checkoutPage.showLogin().setLoginDetail(user);
        Thread.sleep(4000);
        Assert.assertTrue(checkoutPage.getProductName().contains(product.getName()));
        Assert.assertEquals(checkoutPage.getFailNotice(), "Error");

    }
}
