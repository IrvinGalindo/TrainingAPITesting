package org.automation;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;


import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;


public class NelyClass {

    @Test
    public void shouldICreateMyFirstAutomationTest() {

        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
        RestAssured
                .given()
                .pathParam (  "userId",  2)
                .when()
                .get(  "/users/{userId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body ( "data.id", is ( 2))
                .body ( "data.first_name", is ( "Janet"))
                .body ( "data.last_name", is ( "Weaver"))
                .body ( "data.email", is ( "janet.weaver@reqres.in"));
               // .contentType();
    }

    @Test
    public void shouldValidateUserWithTestNgAssertions(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
        Response jsonPath = RestAssured
                .given()
                .pathParam("userId", 2)
                .when()
                .get( "/users/{userId}")
                .then()
                .extract()
                .response();

        //System.out.println(jsonPath.asPrettyString());
        int actualuserId = jsonPath.path( "data.id");
        String actualFirstName = jsonPath.path("data.first_name");
        String actualLastName = jsonPath.path("data.last_name");
        String actualEmail = jsonPath.path("data.email");

        assertEquals(actualuserId, 2, "User Id should be 2"); //assertion de TestNg
        assertEquals(actualFirstName, "Janet", "Name should be Janet");
        assertEquals(actualLastName, "Weaver", "Last name should be Waever");
        assertEquals(actualEmail, "janet.weaver@reqres.in", "Email should be janet.weaver@reqres.in");


    }

}
