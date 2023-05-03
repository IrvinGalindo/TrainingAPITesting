package org.automation.Nicko;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class PostCreateUser {
    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    public void shouldCreateNameUser(){
        given()
                .log().all()
                .queryParam("id", 99)
                .when()
                .post("/user")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("name", is ("morpheus"))
                .body("job", is ("Tester Jr"));


    }
}
