package org.selenium.pom.api.actions;

import com.google.common.collect.HashBiMap;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.selenium.pom.objects.User;
import org.selenium.pom.utils.ConfigLoader;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class SignUpApi {
    private Cookies cookies;

    public Cookies getCookies(){
        return cookies;
    }

    private String fetchRegisterNonceValueUsingGroovy(){
        Response response = getAccount();
        //Groovy method
        return response.htmlPath().getString("**.findAll { it.@name == 'woocommerce-register-nonce'}.@value");

    }

    private String fetchRegisterNonceValueUsingJSoup(){
        Response response = getAccount();
        //JSoup method
        Document doc = Jsoup.parse(response.body().prettyPrint());
        Element element = doc.selectFirst("#woocommerce-register-nonce");
        return element.attr("value");
    }

    private Response getAccount() { //nanti balikin ke private setelah cek
        Cookies cookies = new Cookies();
        Response response = given()
                .baseUri(ConfigLoader
                        .getInstance()
                        .getBaseUrl())
                .cookies(cookies)
                .log().all()
                .when()
                    .get("/account")
                .then()
                    .log().all()
                    .extract().response();
        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Failed to fetch account, HTTP Status Code: " + response.getStatusCode());
        }
        return response;
    }

    public Response register(User user) { //nanti balikin ke private setelah cek
        Cookies cookies = new Cookies();

        Header header = new Header("content-type","application/x-www-form-urlencoded" );
        Headers headers = new Headers(header);
        HashMap<String, String> formParams = new HashMap<>();
        formParams.put("username", user.getUsername());
        formParams.put("email", user.getEmail());
        formParams.put("password", user.getPassword());
        formParams.put("woocommerce-register-nonce", fetchRegisterNonceValueUsingJSoup());
        formParams.put("register", "Register");

        Response response = given()
                .baseUri(ConfigLoader
                        .getInstance()
                        .getBaseUrl())
                .headers(headers)
                .formParams(formParams)
                .cookies(cookies)
                .log().all()
                .when()
                .post("/account")
                .then()
                .log().all()
                .extract().response();
        if (response.getStatusCode() != 302) {
            throw new RuntimeException("Failed to register account, HTTP Status Code: " + response.getStatusCode());
        }
        this.cookies = response.getDetailedCookies();
        return response;
    }
}
