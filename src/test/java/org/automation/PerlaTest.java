package org.automation;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

public class PerlaTest {
private final int USER_ID = 2;


    @BeforeClass    //Method goes before every test or method.
                    //Class foes before all the methods in the class.
                    //Test goes before all the classes.
    public void setUpRestAssured(){
                    //User user = new User(); RestAssured doesn't need it.
        RestAssured.baseURI ="https://reqres.in";
        RestAssured.basePath ="/api";
}

    @Test
    public void shouldICreateMyFirstAutomationTest(){

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
    public void shouldValidateUserWithTestNgAssertions(){

        Response jsonPath = given()
                .pathParam("userId", 2)
                .when()
                .get("/users/{userId}")
                .then()
                .log().all()
                .extract()
                .response();

        System.out.println(jsonPath.asPrettyString());
        int actualUserId = jsonPath.path("data.id");
        String actualFirstName = jsonPath.path("data.first_name");
        String actualLastName = jsonPath.path("data.last_name");
        String actualEmail = jsonPath.path("data.email");
    
        assertEquals(jsonPath.statusCode(),HttpStatus.SC_OK, "Status should be 200");
        assertEquals(actualUserId, 2, "User Id should be 2");
        assertEquals(actualFirstName, "Janet", "Name should be Janet");
        assertEquals(actualLastName, "Weaver", "Last name should be Weaver");
        assertEquals(actualEmail, "janet.weaver@reqres.in", "Email should be janet.weaver@reqres.in");
    }
@Test
    public void shouldStatusCodeBe400WhenUserDoesNotExist(){
       int nonExistingUser = 900;

       Response response = given()
               .pathParam("userId", nonExistingUser)
               .when()
               .get("users2{userId}")
               .then()
               .statusCode(HttpStatus.SC_NOT_FOUND)
               .extract()
               .response();

       String jsonResponse = response.path(".").toString();
       assertEquals(jsonResponse, "{}", "response is not empty");
}

}
