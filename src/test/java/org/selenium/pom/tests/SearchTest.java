package org.selenium.pom.tests;

import org.selenium.pom.base.BaseTest;
import org.selenium.pom.pages.HomePage;
import org.selenium.pom.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {

    @Test
    public void searchWithPartialMatch() throws InterruptedException {
        String searchFor = "Blue";
        StorePage storePage = new StorePage(getDriver())
                .load()
                .search(searchFor);
        Assert.assertEquals(storePage.getTitle(), "Search results: “" + searchFor + "”");
        System.out.println("Tes tes tes invoke Jenkins");
    }

    @Test
    public void searchWithExactMatch() throws InterruptedException {
        String searchFor = "Blue Shoes";
        StorePage storePage = new StorePage(getDriver())
                .load()
                .search(searchFor)
                .clickAddBlueShoeBtn();
        Assert.assertEquals(storePage.getSuccess(), "”" +searchFor + "” has been added to your cart.");
        //Assert.assertEquals(storePage.getTitle(), "Search results: “" + searchFor + "”");
    }

    @Test
    public void searchNonExistingProduct() throws InterruptedException {
        String searchFor = "Red Herring";
        StorePage storePage = new StorePage(getDriver())
                .load()
                .search(searchFor);
        Assert.assertEquals(storePage.getNotFound(), "No products were found matching your selection.");
    }


}
