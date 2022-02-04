package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.pages.components.MyHeader;
import org.selenium.pom.pages.components.ProductThumbnail;

public class StorePage extends BasePage {
    private final By searchFld = By.id("woocommerce-product-search-field-0");
    private final By searchBtn = By.cssSelector(".woocommerce-product-search > button:nth-child(3)");
    private final By title = By.cssSelector(".woocommerce-products-header__title.page-title");
    //private final By addToCartBtn = (By.cssSelector("a[aria-label='Add “Blue Shoes” to your cart']"); old way, not good untuk dynamic UI

    private final By addBlueShoeBtn = By.cssSelector("button[value='1215']");
    private final By addSuccess = By.cssSelector("div[role='alert'] p");
    private final By searchNotFound = By.cssSelector(".woocommerce-info");

    private MyHeader myHeader;

    public MyHeader getMyHeader() {
        return myHeader;
    }

    public void setMyHeader(MyHeader myHeader) {
        this.myHeader = myHeader;
    }

    public ProductThumbnail getProductThumbnail() {
        return productThumbnail;
    }

    public void setProductThumbnail(ProductThumbnail productThumbnail) {
        this.productThumbnail = productThumbnail;
    }

    private ProductThumbnail productThumbnail;


    public StorePage(WebDriver driver) {
        super(driver);
        myHeader = new MyHeader(driver);
        productThumbnail = new ProductThumbnail(driver);
    }
    private StorePage enterTextInSearchFld(String txt){
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchFld)).sendKeys(txt);
        return this;
    }
    // no return karena gak pindah halaman

/*    public Boolean isLoaded(){
        return wait.until(ExpectedConditions.urlContains("/store"));
    }*/

    public StorePage load(){
        load("/store");
        return this;
    }

    public StorePage search(String txt) throws InterruptedException {
        enterTextInSearchFld(txt).clickSearchBtn();
        return this;
    }
    private StorePage clickSearchBtn() throws InterruptedException {
        //wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
        Thread.sleep(1000);
        driver.findElement(searchBtn).click();
        //work-around for Firefox
        return this;
    }
    public String getTitle(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(title)).getText();
    }

    public StorePage clickAddBlueShoeBtn() throws InterruptedException {
        //wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(addBlueShoeBtn)).click();
        //work-around for Firefox
        return this;
    }
    public String getSuccess(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(addSuccess)).getText();
    }
    public String getNotFound(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(searchNotFound)).getText();
    }

}
