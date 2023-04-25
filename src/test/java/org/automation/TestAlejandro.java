package org.automation;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;


public class TestAlejandro {
    @Test
    public void shouldICreateMyFirstAutomationTest() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
        RestAssured
                .given().
                pathParam( "userId", 2)
                .when()
                .get("/users/{userId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body( "data.id", is(2))
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is ("Weaver"))
                .body("data.email", is("janet.weaver@reqres.in"));
    }

    @Test
    public void shouldIValidateUserWithTestNgAssertations(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
        Response jsonPath = RestAssured
                .given()
                .pathParam("userId", 2)
                .when()
                .get("/users/{userId}")
                .then()
                .extract()
                .response();

        int actualUserId = jsonPath.path("data.id");
        String actualFirstName = jsonPath.path("data.first_name");
        String actualLastName = jsonPath.path("data.last_name");
        String actualEmail = jsonPath.path("data.email");

        assertEquals(actualUserId, 2, "User Id should be 2");
        assertEquals(actualFirstName, "Janet", "Last Name should be Janet");
        assertEquals(actualLastName, "Weaver", "Last Name should be Weaver");
        assertEquals(actualEmail, "janet.weaver@reqres.in", "Last Name should be janet.weaver@reqres.in");


    }
}
