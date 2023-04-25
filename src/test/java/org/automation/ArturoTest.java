package org.automation;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

public class ArturoTest {
    @Test
    public void shouldICreateMyFirstAutomationTest() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
        RestAssured
                .given()
                .pathParam( "userId", 2)
                .when()
                .get("/users/{userId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data.id",is(2))
                .body("data.first_name",is("Janet"))
                .body("data.last_name",is("Weaver"))
                .body("data.email", is("janet.weaver@reqres.in"));
    }

    @Test
    public void shouldValidateUserWithTestNGAssertions(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
        Response jsonPath = RestAssured
                .given()
                .pathParam("userId",2)
                .when()
                .get("/users/{userId}")
                .then()
                .extract()
                .response();

        int userId = jsonPath.path("data.id");
        String actualFirstName = jsonPath.path("data.first_name");
        String actualLastName = jsonPath.path("data.last_name");
        String actualEmail = jsonPath.path("data.email");

        assertEquals(userId ,2, "User Id should be 2");
        assertEquals(actualFirstName ,"Janet", "Name should be Janet");
        assertEquals(actualLastName ,"Weaver", "Lastname should be Weaver");
        assertEquals(actualEmail ,"janet.weaver@reqres.in", "Email should be janet.weaver@reqres.in");
    }
}
