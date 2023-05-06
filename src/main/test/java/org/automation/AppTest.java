package org.automation;

import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import io.restassured.RestAssured;


public class AppTest {

    @Test
    public void shouldICreateMyFirstAutomationTest() {

        RestAssured.baseURI = "https://reqres.in/";
        RestAssured.basePath = "/api";

        RestAssured
                .given()
                .pathParam("userId", 2)
                .when()
                .get("/users/{userId}")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}