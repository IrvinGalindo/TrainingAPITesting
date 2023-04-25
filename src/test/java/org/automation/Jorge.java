package org.automation;

import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.testng.Assert.assertEquals;

public class Jorge {

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
                .statusCode(HttpStatus.SC_OK)
                .body("data.id", is(2))
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is("Weaver"))
                .body("data.email", is("janet.weaver@reqres.in"))
                .body("data.email", containsString("janet.weaver@reqres.in"));
    }

    @Test
    public void shouldIValidateUserWithTestNgAssertions() {
        RestAssured.baseURI = "https://reqres.in/";
        RestAssured.basePath = "/api";
        io.restassured.response.Response jsonPath = RestAssured
                .given()
                .pathParam("userId", 2)
                .when()
                .get("/users/{userId}")
                .then()
                .extract()
                .response();
        String actualUserId = jsonPath.jsonPath().getString("data.id");
        String actualFirstName = jsonPath.jsonPath().getString("data.first_name");
        String actualLastName = jsonPath.jsonPath().getString("data.last_name");
        String actualEmail = jsonPath.jsonPath().getString("data.email");

        assertEquals(jsonPath.getStatusCode(), 200, "Status should be 200");
        assertEquals(actualUserId, "2", "User id should be 2");
        assertEquals(actualFirstName, "Janet", "Name should be Janet");
        assertEquals(actualLastName, "Weaver", "Last name should be Weaver");
        assertEquals(actualEmail, "janet.weaver@reqres.in", "email should be janet.weaver@reqres.in");
    }
}
