package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;

public class StorePage extends BasePage {
    private final By searchFld = By.id("woocommerce-product-search-field-0");
    private final By searchBtn = By.cssSelector("button[value='Search']");
    private final By title = By.cssSelector(".woocommerce-products-header__title.page-title");
    //private final By addToCartBtn = (By.cssSelector("a[aria-label='Add “Blue Shoes” to your cart']"); old way, not good untuk dynamic UI
    private final By viewCardLink = By.cssSelector("a[title='View cart']");


    public StorePage(WebDriver driver) {
        super(driver);
    }
    private StorePage enterTextInSearchFld(String txt){
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchFld)).sendKeys(txt);
        return this;
    }
    // no return karena gak pindah halaman

/*    public Boolean isLoaded(){
        return wait.until(ExpectedConditions.urlContains("/store"));
    }*/

    public StorePage search(String txt){
        enterTextInSearchFld(txt).clickSearchBtn();
        return this;
    }
    private StorePage clickSearchBtn(){
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
        return this;
    }
    public String getTitle(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(title)).getText();
    }

    private By getAddToCartBtnElement(String productName){
        return By.cssSelector("a[aria-label='Add “" + productName + "” to your cart']");
    }

    public StorePage addToCart(String productName){
        By addToCartBtn = getAddToCartBtnElement(productName);
        //
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
        return this;
    }
    public CartPage viewCart(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewCardLink)).click();
        //driver.findElement(viewCardLink).click();
        return new CartPage(driver);
    }

}
