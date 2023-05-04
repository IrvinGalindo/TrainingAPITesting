package org.automation;

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


public class JorgeTest {

    private final int USER_ID = 2;

    @BeforeClass
    public void setUpRestAssured() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    public void shouldICreateMyFirstAutomationTest() {
        given()
                .pathParam("userId", USER_ID)
                .when()
                .get("/users/{userId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data.id", is(USER_ID))
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is("Weaver"))
                .body("data.email", is("janet.weaver@reqres.in"));
    }

    @Test
    public void shouldValidateUserWithTestNgAssertions() {

        Response response = given()
                .pathParam("userId", USER_ID)
                .when()
                .get("/users/{userId}");

        int actualUserId = response.jsonPath().getInt("data.id");
        String actualFirstName = response.jsonPath().getString("data.first_name");
        String actualLastName = response.jsonPath().getString("data.last_name");
        String actualEmail = response.jsonPath().getString("data.email");

        assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Status should be 200");
        assertEquals(actualUserId, USER_ID, "User Id should be 2");
        assertEquals(actualFirstName, "Janet", "name should be Janet");
        assertEquals(actualLastName, "Weaver", "lastName should be Weaver");
        assertEquals(actualEmail, "janet.weaver@reqres.in", "email should be janet.weaver@reqres.in");
    }

    @Test
    public void shouldValidateUserWithMap() {
        HashMap<String, String> user = new HashMap<>();
        user.put("id", String.valueOf(USER_ID));
        user.put("first_name", "Janet");
        user.put("last_name", "Weaver");
        user.put("email", "janet.weaver@reqres.in");
        user.put("avatar", "https://reqres.in/img/faces/2-image.jpg");

        given()
                .pathParam("userId", USER_ID)
                .when()
                .get("/users/{userId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data", equalTo(user));
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
        expectedUser.setId(USER_ID);
        expectedUser.setFirstName("Janet");
        expectedUser.setLastName("Weaver");
        expectedUser.setEmail("janet.weaver@reqres.in");
        expectedUser.setAvatar("https://reqres.in/img/faces/2-image.jpg");

        assertEquals(actualUser, expectedUser, "users are not equals");
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
        assertEquals("{}", jsonResponse, "response is not empty");

    }

    public static class RegisterTest {
        @BeforeClass
        public static void setUp() {
            RestAssured.baseURI = "https://reqres.in";
            RestAssured.basePath = "/api";
        }

        @Test
        public void userShouldBeCreatedAndNameBeReturned() {
            String expectedName = "Galletita";
            given()
                    .contentType("application/json")
                    .body("{\"name\": \"" + expectedName + "\",\"zion resident\": \"leader\"}")
                    .when()
                    .post("/users")
                    .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .body("name", equalTo(expectedName));
        }
    }
}
