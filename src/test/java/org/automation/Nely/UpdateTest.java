package org.automation.Nely;

import POJOs.CreateUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UpdateTest {
    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }
    @Test
    public void userShouldBeUpdatedForJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        File requestBody = Paths.get("src/test/resources/Nely/CreateUser.json").toFile();
        CreateUser user = objectMapper.readValue(requestBody, CreateUser.class);
        user.setJob("zion resident");

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .patch("/users/2")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", equalTo(user.getName()))
                .body("job", equalTo(user.getJob()));

    }

}
