package org.automation;

import POJOs.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapper;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;


public class JaimeTest {
    private ObjectMapper objectMapper = new ObjectMapper();
    private final int USER_ID = 2;

    @BeforeClass
    public void setUpRestAssured(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }
    @Test
    public void shouldICreateMyFirstAutomationTest() {
        //User user = new User();

            given()
                .pathParam( "userId", USER_ID)
                .when()
                .get( "/users/{userId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data.id", is(2))
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is("Weaver"))
                .body("data.email", is("janet.weaver@reqres.in"));



    }
    @Test
    public void shouldValidateUserWithTestNgAssertions() {

        Response jsonPath = given()
                .log().all()
                .pathParam("userId", USER_ID)
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

        assertEquals(jsonPath.statusCode(), HttpStatus.SC_OK, "Status should be 200");
        assertEquals(actualUserId, USER_ID, "User Id should be 2");
        assertEquals(actualFirstName, "Janet", "name should be Janet");
        assertEquals(actualLastName, "Weaver", "lastName should be Weaver");
        assertEquals(actualEmail, "janet.weaver@reqres.in", "email should be janet.weaver@reqres.in");
    }

    @Test
    public void shouldValidateUserWithMap() {
        HashMap User = new HashMap();
        User.put ("id", USER_ID);
        User.put ("first_name", "Janet");
        User.put ("last_name", "Weaver");
        User.put ("email", "janet.weaver@reqres.in");
        User.put ("avatar", "https://reqres.in/img/faces/2-image.jpg");

     given()
                .log().all()
                .pathParam( "userId", USER_ID)
                .when()
                .get( "/users/{userId}")
                .then()
                .log().all()
             .body("data", is(User));


    }

    @Test(priority = 0)
    public void shoulStatusCodeBe400WhenUserNotExist() throws JsonProcessingException {
        int nonExistingUser =900;

        Response response = given()
                .pathParam( "userId", nonExistingUser)
                .when()
                .get( "/users/{userId}")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .response();

        String jsonResponse = response.path(",").toString();
        assertEquals(jsonResponse, "{}", "response is not empty");


    }

}
