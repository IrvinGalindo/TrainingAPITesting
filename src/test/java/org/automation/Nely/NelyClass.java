package org.automation.Nely;

//import POJOs.User;
import POJOs.User;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

public class NelyClass {
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
                .log().all()
                .pathParam (  "userId",  USER_ID)
                .when()
                .get(  "/users/{userId}")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body ( "data.id", is ( USER_ID))
                .body ( "data.first_name", is ( "Janet"))
                .body ( "data.last_name", is ( "Weaver"))
                .body ( "data.email", is ( "janet.weaver@reqres.in"));
               // .contentType();
    }

    @Test
    public void shouldValidateUserWithTestNgAssertions(){

        Response jsonPath = given()
                .log().all()
                .pathParam("userId", USER_ID)
                .when()
                .get( "/users/{userId}")
                .then()
                .log().all()
                .extract()
                .response();

        //System.out.println(jsonPath.asPrettyString());
        int userId = jsonPath.path( "data.id");
        String actualFirstName = jsonPath.path("data.first_name");
        String actualLastName = jsonPath.path("data.last_name");
        String actualEmail = jsonPath.path("data.email");

        assertEquals(userId, USER_ID, "User Id should be 2"); //assertion de TestNg
        assertEquals(actualFirstName, "Janet", "Name should be Janet");
        assertEquals(actualLastName, "Weaver", "Last name should be Weaver");
        assertEquals(actualEmail, "janet.weaver@reqres.in", "Email should be janet.weaver@reqres.in");
    }

    @Test
    public void shouldValidateUserWithMap(){
        HashMap user = new HashMap();
        user.put("id", USER_ID);
        user.put("first_name", "Janet");
        user.put("last_name", "Weaver");
        user.put("email","janet.weaver@reqres.in");
        user.put("avatar","https://reqres.in/img/faces/2-image.jpg");

        given()
                .log().all()
                .pathParam("userId", USER_ID)
                .when()
                .get("/users/{userId}")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("data",is(user));

    }

    @Test
    public void shouldValidateUserWithNestedPojo() {
        User user = new User();

        given()
                .pathParam("userId", USER_ID)
                .when()
                .get("/users/{userId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data.id", equalTo(user.getId()))
                .body("data.email", equalTo(user.getEmail()))
                .body("data.first_name", equalTo(user.getFirstName()))
                .body("data.last_name", equalTo(user.getLastName()))
                .body("data.avatar", equalTo(user.getAvatar()));

    }

    @Test
    public void shouldStatusCodeBe400WhenUserNotExist() {
        int nonExistingUser = 900;

        Response response = given()
                .pathParam("userId", nonExistingUser)
                .when()
                .get("/users/{userId}")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .response();

        String jsonResponse = response.path(".").toString();
        assertEquals(jsonResponse, "{}", "response is not empty");
    }

}



