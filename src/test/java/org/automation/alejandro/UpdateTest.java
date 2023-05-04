package org.automation.alejandro;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UpdateTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    public void userShouldBeUpdatedAndNameIsReturned(){
        String expectedName = "Morpheus";
        given().
                contentType(ContentType.JSON)
                .pathParam("user", 2)
                .body("{\n" +
                        "    \"name\": \""+ expectedName+"\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .when()
                .put("/users/{user}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", equalTo(expectedName));
    }

    @Test
    public void userShouldBeUpdatedAndJobIsReturned(){
        String expectedJob = "zion resident";
        given().
                contentType(ContentType.JSON)
                .pathParam("user", 2)
                .body("{\n" +
                        "    \"name\": \"Morpheus\",\n" +
                        "    \"job\": \""+expectedJob+"\"\n" +
                        "}")
                .when()
                .put("/users/{user}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("job", equalTo(expectedJob));
    }

}
