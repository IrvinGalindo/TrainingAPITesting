package org.automation;


import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import io.restassured.RestAssured;

import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

public class NickClass {
    @Test
    public void shouldICreateMyFirstAutomationTest() {
      //Hi
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
        RestAssured
                .given()
                .pathParam("userId", 2)
                .when()
                .get("/users/{userId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data.id", is (2))
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is("Weaver"))
                .body("data.email", is ("janet.weaver@reqres.in"));
    }

    @Test
    public void shouldValidateUserWithTestNGAssertions(){
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

        //System.out.println(jsonpath.asPrettyString());
        int userId = jsonPath.path("data.id");
        String actualFirstName = jsonPath.path("data.first_name");
        String actualLastName = jsonPath.path ("data.last_name");
        String actualEmail = jsonPath.path("data.email");

        assertEquals(jsonPath.statusCode(), HttpStatus.SC_OK, "Status should be 200");
        assertEquals(userId, 2, "User Id should be 2");
        assertEquals(actualFirstName, "Janet", "Name should be Janet");
        assertEquals(actualLastName, "Weaver", "Last Name should be Weaver");
        assertEquals(actualEmail, "janet.weaver@reqres.in", "Email should be Janet");

    }
}
