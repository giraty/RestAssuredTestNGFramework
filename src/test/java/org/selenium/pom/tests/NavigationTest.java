package org.selenium.pom.tests;

import io.qameta.allure.Description;
import org.selenium.pom.base.BaseTest;
import org.selenium.pom.pages.HomePage;
import org.selenium.pom.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NavigationTest  extends BaseTest {
    @Description("Ke halaman toko dari halaman utama")
    @Test(description = "Navigate From Home to Store Page Using Main Menu")
    public void navigateFromHomeToStoreUsingMainMenu(){
        StorePage storePage = new HomePage(getDriver())
                .load()
                .getMyHeader()
                .navigateToStoreUsingMenu();
        //storePage.isLoaded();
        //Assert.assertEquals(storePage.getTitle(), "Search results: “" + searchFor + "”"); versi testng
        Assert.assertEquals(storePage.getTitle(), "Store");
    }
}
