package org.automation.Nicko;

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

public class NickClass {

    private final int USER_ID = 2;

    @BeforeClass
    public void setUPRestAssured(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    public void shouldICreateMyFirstAutomationTest() {
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
    public void shouldValidateUserWithPojo() {

        User actualUser = given()
                .pathParam("userId", USER_ID)
                .when()
                .get("/users/{userId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .jsonPath()
                .getObject("data", User.class);

        User expectedUser = new User();
        assertEquals(actualUser.toString(), expectedUser.toString(), "users are not equals");

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
