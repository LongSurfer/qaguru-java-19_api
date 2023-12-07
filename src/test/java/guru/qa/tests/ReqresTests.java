package guru.qa.tests;

import guru.qa.TestBase;
import guru.qa.models.lombok.LoginBodyLombokModel;
import guru.qa.models.lombok.LoginResponseLombokModel;
import guru.qa.models.pojo.LoginBodyPojoModel;
import guru.qa.models.pojo.LoginResponsePojoModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqresTests extends TestBase {

    @Test
    void successLoginPojoTest() {
        LoginBodyPojoModel loginBody = new LoginBodyPojoModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        LoginResponsePojoModel loginResponse = given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(loginBody)
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponsePojoModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken());
    }


    @Test
    void successLoginLombokTest() {
        LoginBodyLombokModel loginBody = new LoginBodyLombokModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        LoginResponseLombokModel loginResponse = given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(loginBody)
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", loginResponse.getToken());
    }


    @DisplayName("You can create user with success fields name and job")
    @Test
    void successCreateUser() {
        String userBody = "{\n" +
                "    \"name\": \"Alex\",\n" +
                "    \"job\": \"tester\"\n" +
                "}";

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(userBody)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("Alex"))
                .body("job", is("tester"))
                .body("id", notNullValue());
    }

    @DisplayName("You can create user with empty fields name and job")
    @Test
    void createUserWithoutData() {
        String userBodyWithoutData = "{\n" +
                "    \"name\": \"\",\n" +
                "    \"job\": \"\"\n" +
                "}";

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(userBodyWithoutData)
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is(""))
                .body("job", is(""))
                .body("id", notNullValue());
    }

    @DisplayName("You can update existing user")
    @Test
    void updateUserData() {
        String updateUserBody = "{\n" +
                "    \"name\": \"Rick Sanchez\",\n" +
                "    \"job\": \"crazy scientist\"\n" +
                "}";

        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body(updateUserBody)
                .patch("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("Rick Sanchez"))
                .body("job", is("crazy scientist"))
                .body("updatedAt", notNullValue());
    }

    @DisplayName("You can get existing user via his system id")
    @Test
    void getExistUserById() {

        given()
                .log().uri()
                .log().body()
                .get("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.first_name", is("Janet"));
    }

    @DisplayName("You can't get any user via non existing id")
    @Test
    void getNotExistedUserById() {

        given()
                .log().all()
                .get("/users/0")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }


}
