package org.automation.alejandro;

import POJOs.User;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;


public class TestAlejandro {

    private final int USER_ID = 2;

    @BeforeClass
    public void setUpRestAssured(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";

    }

    @Test
    public void shouldICreateMyFirstAutomationTest() {
                given().
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
        Response jsonPath = given()
                .log().all()
                .pathParam("userId", 2)
                .when()
                .get("/users/{userId}")
                .then()
                .log().all()
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

    @Test
    public void shouldIValidateUserWithMap(){
        HashMap user = new HashMap();
        user.put("id", USER_ID);
        user.put("first_name", "Janet");
        user.put("last_name", "Weaver");
        user.put("email", "janet.weaver@reqres.in");
        user.put("avatar", "https://reqres.in/img/faces/2-image.jpg");

        given()
                .log().all()
                .pathParam("userId", USER_ID)
                .when()
                .get("/users/{userId}")
                .then()
                .log().all()
                .statusCode(200)
                .body("data", is(user));

    }

    @Test
    public void shoulStatusCodeBe400WhenUserNotExists(){
        int nonExistedUserId = 900;


              Response response = given()
                .pathParam("userId", nonExistedUserId)
                .when()
                .get("/users/{userId}")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .response();

        String jsonResponse = response.path(".").toString();
        assertEquals(jsonResponse, "{}", "response is not empty");

    }
}
