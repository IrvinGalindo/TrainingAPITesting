package org.automation.Nicko;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UpdateUser {

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    public void jobShouldBeUpdateJobToSeniorTester() {
        String originalJob = "senior tester";
        given()
                //.contentType("application/json")
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"name\":  \"morpheus\",\n" +
                        "    \"job\": \"" + originalJob + "\"\n" +
                        "}" )
                .when()
                .put("/users/2")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("job", equalTo(originalJob))
                .log().all();
    }
@Test
    public void nameUserShouldBeMatch(){
        String expectedName = "morpheus";
        given()
                //.contentType("application/json")
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"name\": \"" + expectedName + "\",\n" +
                        "    \"job\": \"senior tester\"\n" +
                        "}" )
                .when()
                .put("/users/2")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", equalTo(expectedName))
                .log().all();

    }
}
