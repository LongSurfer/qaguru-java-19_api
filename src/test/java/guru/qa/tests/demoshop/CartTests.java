package guru.qa.tests.demoshop;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class CartTests {

    String login = "ai@cmc.moscow",
            password = "123456";

    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "https://demowebshop.tricentis.com";
        RestAssured.baseURI = "https://demowebshop.tricentis.com";
    }


    @Test
    void saccessLoginWithExistEmailPassword() {
        given()
                .log().uri()
                .log().params()
                .formParam("Email", login)
                .formParam("Password", password)
                .when()
                .post("/login")
                .then()
                .log().all()
                .statusCode(302);
    }
}
