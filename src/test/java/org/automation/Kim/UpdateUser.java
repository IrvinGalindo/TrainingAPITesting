package org.automation.Kim;

import POJOs.CreateUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

public class UpdateUser {

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://reqres.in/";
        RestAssured.basePath = "/api";
    }

    @Test
    public void userNameShouldBeUpdatedAndNameAndJobAreReturned(){
        String updatedName = "Galletotota";
        String expectedJob = "zion resident";
        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"name\": \"" + updatedName + "\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .when()
                .put("/users/2")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", Matchers.equalTo(updatedName))
                .body("job", Matchers.equalTo(expectedJob));

        System.out.println(updatedName);


    }

}
