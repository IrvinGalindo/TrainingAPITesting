package org.automation.Arturo;

import POJOs.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

public class ArturoTest {

    private final int USER_ID = 2;

    @BeforeClass
    public void setUpRestAssured(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }
    @Test
    public void shouldICreateMyFirstAutomationTest() {
        given()
                .pathParam( "userId", USER_ID)
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

        Response jsonPath = given()
                .log().all()
                .pathParam("userId",USER_ID)
                .when()
                .get("/users/{userId}")
                .then()
                .extract()
                .response();

        int userId = jsonPath.path("data.id");
        String actualFirstName = jsonPath.path("data.first_name");
        String actualLastName = jsonPath.path("data.last_name");
        String actualEmail = jsonPath.path("data.email");

        assertEquals(userId ,USER_ID, "User Id should be 2");
        assertEquals(actualFirstName ,"Janet", "Name should be Janet");
        assertEquals(actualLastName ,"Weaver", "Lastname should be Weaver");
        assertEquals(actualEmail ,"janet.weaver@reqres.in", "Email should be janet.weaver@reqres.in");
    }

    @Test
    public void shouldValidateUserWithMap() {
        HashMap user = new HashMap<>();
        user.put("first_name", "Janet");
        user.put("last_name", "Weaver");
        user.put("email", "janet.weaver@reqres.in");

        given()
                .log().all()
                .pathParam("userId",USER_ID)
                .when()
                .get("/users/{userId}")
                .then()
                .log().all()
                .body("data",is(user));
    }

    @Test
    public void shouldValidateUserPOJO() {

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
    public void shouldStatusCodeBe400WhenUserNotExist() throws JsonProcessingException {
        int nonExistingUserId = 900;

        Response response = given()
                .pathParam("userId",nonExistingUserId)
                .when()
                .get("/users/{userId}")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .extract()
                .response();

        String jsonResponse = response.path(".").toString();
        assertEquals(jsonResponse,"{}","response is not empty");
    }

}
