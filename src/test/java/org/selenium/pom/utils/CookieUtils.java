package org.selenium.pom.utils;

import io.restassured.http.Cookies;
import org.openqa.selenium.Cookie;

import java.util.ArrayList;
import java.util.List;

public class CookieUtils {

    public List<Cookie> convertRestAssuredCookiesToSeleniumCookies(Cookies cookies){
        List<io.restassured.http.Cookie> restAssuredCookies = new ArrayList<>(); // cookie restassured
        restAssuredCookies = cookies.asList();
        List<Cookie> seleniumCookies = new ArrayList<>();  //cookie selenium
        for(io.restassured.http.Cookie cookie : restAssuredCookies ){
            seleniumCookies.add(new Cookie(cookie.getName(), cookie.getValue(), cookie.getDomain(), cookie.getPath(), cookie.getExpiryDate(), cookie.isSecured(), cookie.isHttpOnly(), cookie.getSameSite()));
        }
        return seleniumCookies;
    }
}
