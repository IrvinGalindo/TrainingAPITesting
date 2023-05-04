package org.automation.Nely;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

public class UpdateTest {
    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }
    @Test
    public void userShouldBeCreatedAndNameIsReturned(){
        String expectedName = "morpheus";
        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "\"name\": \""+ expectedName + "\",\n" +
                        "\"job\": \"zion resident\"\n" +
                        "}"
                )
                .when()
                .put("/users")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", equalTo(expectedName));

    }

    @Test
    public void userShouldBeCreatedAndJobIsReturned(){
        String expectedJob = "zion resident";
        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "\"name\": \"Galletita\",\n" +
                        "\"job\": \""+ expectedJob + "\"\n" +
                        "}")
                .when()
                .put("/users")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("job", equalTo(expectedJob));
    }

}
