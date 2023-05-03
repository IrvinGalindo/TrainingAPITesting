package org.automation.Marcela;

import POJOs.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;


public class MarcelaTest {

    @BeforeClass
    public void setUpRestAssured(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }


    @Test
    public void shouldICreateMyFirstAutomationTest() {
        given()
                .pathParam("userId", 2)
                .when()
                .get("/users/{userId}")
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

        assertEquals(jsonPath.statusCode(), HttpStatus.SC_OK, "Status code should be 200");
        assertEquals(actualUserId, 2, "User Id should be 2");
        assertEquals(actualFirstName, "Janet", "First name should be Janet");
        assertEquals(actualLastName, "Weaver", "Last name should be Weaver");
        assertEquals(actualEmail, "janet.weaver@reqres.in", "Email should be janet.weaver@reqres.in");

    }
    @Test
    public void shouldValidateUserWithMap() {
        HashMap user=new HashMap();
        user.put("id",2);
        user.put("first_name","Janet");
        user.put("last_name","Weaver");
        user.put("email","janet.weaver@reqres.in");
        user.put("avatar","https://reqres.in/img/faces/2-image.jpg");

        given()
                .pathParam("userId", 2)
                .when()
                .get("/users/{userId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data",is(user));

           }
    @Test
    public void shouldStatusCodeBe400WhenUserNotExist() throws JsonProcessingException {
        int nonExistedUser=900;

        Response response= given()
                .pathParam("userId", 2)
                .when()
                .get("/users/{userId}")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .response();

        String jsonResponse=response.path(".").toString();
        assertEquals(jsonResponse,"{}","Response is not empty");

    }
}
