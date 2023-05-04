package org.automation.Nicko;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostCreateUser {
    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }
    @Test
    public void userShouldBeCreatedAndNameIsReturn(){
        String expectedName = "morpheus";
        given()
                //.contentType("application/json")
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"name\": \"" + expectedName + "\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}" )
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("name", equalTo(expectedName));

    }

    @Test
    public void userShouldBeCreatedAndJobIsReturn(){
        String expectedJob = "leader";
        given()
                //.contentType("application/json")
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"" + expectedJob + "\"\n" +
                        "}" )
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("job", equalTo(expectedJob));



    }
}
