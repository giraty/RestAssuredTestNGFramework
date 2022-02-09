package org.selenium.pom.tests;

import io.qameta.allure.*;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.dataproviders.MyDataProvider;
import org.selenium.pom.objects.Product;
import org.selenium.pom.pages.CartPage;
import org.selenium.pom.pages.HomePage;
import org.selenium.pom.pages.StorePage;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

@Epic("Selenium Test Framework")
@Feature("TestNG")
public class AddToCartTest extends BaseTest {

    @Story("Add products to cart")
    @Link("https://example.org")
    @Link(name = "allure", type = "mylink")
    @TmsLink("Ini TMS link")
    @Issue("99999")
    @Description("Menambahkan produk-produk dari keranjang saat di halaman toko.")

    @Test(description = "Add Product to Cart From Store Page")
    public void addToCartFromStorePage() throws IOException {
        Product product = new Product(1215);
        CartPage cartPage = new StorePage(getDriver())
                .load()
                .getProductThumbnail()
                .addToCart(product.getName())
                .viewCart();

        Assert.assertEquals(cartPage.getProductName(), product.getName());
    }

    @Story("Add products to cart")
    @Link("https://example.org")
    @Link(name = "allure", type = "mylink")
    @TmsLink("Ini TMS link")
    @Issue("29992")
    @Description("Menambahkan produk-produk dari keranjang saat di halaman toko.")

    @Test(description = "Add Featured Product to Cart From Home Page", dataProvider = "getFeaturedProducts", dataProviderClass = MyDataProvider.class)
    public void addToCartFeaturedProducts(Product product){
        CartPage cartPage = new HomePage(getDriver()).load()
                .getProductThumbnail()
                .addToCart(product.getName()).viewCart();
        Assert.assertEquals(cartPage.getProductName(), product.getName());

    }




}
