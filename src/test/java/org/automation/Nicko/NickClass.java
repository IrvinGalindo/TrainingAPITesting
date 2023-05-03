package org.automation.Nicko;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

public class NickClass {

    private final int USER_ID = 2;

    @BeforeClass
    public void setUPRestAssured(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }


    @Test
    public void shouldICreateMyFirstAutomationTest() {
      //Hi
       // RestAssured.baseURI = "https://reqres.in";
       // RestAssured.basePath = "/api";
        given()
                .log().all()
                .pathParam("userId", 2)
                .when()
                .get("/users/{userId}")
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("data.id", is (2))
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is("Weaver"))
                .body("data.email", is ("janet.weaver@reqres.in"));
    }

    @Test
    public void shouldValidateUserWithTestNGAssertions(){
        //RestAssured.baseURI = "https://reqres.in";
        //RestAssured.basePath = "/api";
        Response jsonPath = given()
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
    public void shouldStatusCodeBe400WhenUserNotExist() throws JsonProcessingException {
        int nonExistedUserId = 900;

         given()
                .log().all()
                .pathParam("userId", nonExistedUserId)
                .when()
                .get("/users/{userId}")
                .then()
                .statusCode( HttpStatus.SC_NOT_FOUND)
                 .extract()
                 .response();

         //String jsonResponse = response.path(".").toString();
         //assertEquals(jsonResponse, "{}", "response is not empty");


    }
}
