package org.automation.Arturo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateTest {

    @BeforeClass
    public void setUpTestAssured(){
        RestAssured.baseURI = "https://reqres.in/" ;
        RestAssured.basePath = "/api" ;
    }

    @Test
    public void userShouldBeCreatedAndNameIsReturned(){
        String expectedName = "morpheus";
        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"name\": \""+ expectedName + "\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("name", equalTo(expectedName));
    }

    @Test
    public void userShouldBeCreatedAndJobIsReturned(){
        String expectedJob = "leader";
        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \""+expectedJob+"\"\n" +
                        "}")
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("job", equalTo(expectedJob));
    }
}
