package org.automation.Arturo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UpdateUser {

    private final int USER_ID = 2;

    @BeforeClass
    public void setUpTestAssured(){
        RestAssured.baseURI = "https://reqres.in/" ;
        RestAssured.basePath = "/api" ;
    }

    @Test
    public void userShouldBeUpdated(){
        String newName = "Arturo";
        String newJob = "Piloto de F1";
        File requestBody = Paths.get("src/test/resources/Arturo/CreateUser.json").toFile();
        given()
                .pathParam( "userId", USER_ID)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"name\": \""+newName+"\",\n" +
                        "    \"job\": \""+newJob+"\"\n" +
                        "}")
                .when()
                .put("/users/{userId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name",equalTo(newName))
                .body("job",equalTo(newJob));
    }
}
