package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selenium.pom.base.BasePage;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.User;

import java.rmi.server.ExportException;
import java.time.Duration;
import java.util.List;

public class CheckoutPage extends BasePage {
        private final By billingFirstName  = By.id("billing_first_name");
        private final By billingLastName  = By.id("billing_last_name");
        private final By billingAddressLineOne  = By.id("billing_address_1");
        private final By billingCity  = By.id("billing_city");
        private final By billingPostCode  = By.id("billing_postcode");
        private final By billingEmail  = By.id("billing_email");
        private final By billingOrderBtn  = By.id("place_order");
        private final By successNotice = By.cssSelector(".woocommerce-notice");
        private final By loginBtn = By.cssSelector(".showlogin");
        private final By usernameFld = By.id("username");
        private final By passwordFld = By.id("password");
        private final By doLoginBtn = By.cssSelector("button[value='Login']");
        private final By overlay = By.cssSelector("blockUI.blockOverlay");

        private final By countryDropDown = By.id("billing_country");
        private final By alternateCountryDropDown = By.id("select2-billing_country-container");
        private final By stateDropDown = By.id("billing_state");
        private final By alternateStateDropDown = By.id("select2-billing_state-container");

        private final By directBankTransferRadioBtn = By.id("payment_method_bacs");

        private final By productName = By.cssSelector("td[class='product-name']");
        private final By loginFailNotice = By.cssSelector("div[class='woocommerce-notices-wrapper'] strong:nth-child(1)");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage load(){
        load("/checkout/");
        return this;

    }



    public CheckoutPage enterFirstName(String firstName){
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(billingFirstName));
        e.clear();
        e.sendKeys(firstName);
        return this;
    }
    public CheckoutPage enterLastName(String lastName){
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(billingLastName));
        e.clear();
        e.sendKeys(lastName);
        return this;
    }

    public CheckoutPage selectCountry(String countryName){
        //Select select = new Select(driver.findElement(countryDropDown));
        //select.selectByVisibleText(countryName);
        //alternate handling untuk Firefox karena bug
        wait.until(ExpectedConditions.elementToBeClickable(alternateCountryDropDown)).click();
        WebElement e = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[text()='" + countryName + "']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", e);
        e.click();

        return this;
    }
        // Now not working in both Chrome and Firefox. Maybe revert back to cut cupport for Firefox or try harder.
        // or maybe, tell Firefox to fix the bug that's been there for 1000 years
    public CheckoutPage selectState(String stateName){
/*        Select select = new Select(driver.findElement(stateDropDown));
        select.selectByVisibleText(stateName);*/
        // alternate handling untuk Firefox karena bug.
        wait.until(ExpectedConditions.elementToBeClickable(alternateStateDropDown)).click();
        WebElement e = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[text()='" + stateName + "']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", e);
        e.click();

        return this;
    }

    public CheckoutPage enterAddressLineOne(String addressLineOne){
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(billingAddressLineOne));
        e.clear();
        e.sendKeys(addressLineOne);
        return this;
    }
    public CheckoutPage enterCity(String cityName){
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(billingCity));
        e.clear();
        e.sendKeys(cityName);
        return this;
    }
    public CheckoutPage enterPostCode(String postCode){
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(billingPostCode));
        e.clear();
        e.sendKeys(postCode);
        return this;
    }
    public CheckoutPage enterEmailAddress(String emailAddress){
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(billingEmail));
        e.clear();
        e.sendKeys(emailAddress);
        return this;
    }

    public CheckoutPage setBillingAddress(BillingAddress billingAddress){
        return enterFirstName(billingAddress.getFirstName())
                .enterLastName(billingAddress.getLastName())
                .selectCountry(billingAddress.getCountry())
                .selectState(billingAddress.getState())
                .enterAddressLineOne(billingAddress.getAddressLineOne())
                .enterCity(billingAddress.getCity())
                .enterPostCode(billingAddress.getPostalCode())
                .enterEmailAddress(billingAddress.getEmail());
    }

    public CheckoutPage placeOrder(){
        waitForOverlaysToDisappear(overlay);
        driver.findElement(billingOrderBtn).click();
        return this;
    }
    public String getNotice(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successNotice)).getText();
        //return driver.findElement(successNotice).getText();

    }

    public String getFailNotice(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loginFailNotice)).getText();
        //return driver.findElement(successNotice).getText();

    }
    public CheckoutPage showLogin(){
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
        return this;
    }

    public CheckoutPage enterUsername(String username){
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameFld)).sendKeys(username);
        return this;
    }

    public CheckoutPage enterPassword(String password){
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordFld)).sendKeys(password);
        return this;
    }

    public CheckoutPage doLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(doLoginBtn)).click();
        return this;
    }

    public CheckoutPage setLoginDetail(User user){
        return enterUsername(user.getUsername())
                .enterPassword(user.getPassword())
                .doLogin();
    }

    public CheckoutPage selectDirectBankTransfer(){
        WebElement e = wait.until(ExpectedConditions.elementToBeClickable(directBankTransferRadioBtn));
        if(!e.isSelected()){
            e.click();
        }
        return this;
    }
    public String getProductName(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();

    }


}
