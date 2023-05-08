package org.automation.Nicko;

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

public class CreateUserPost {
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

    @Test
    public void userShouldBeCreatedFromJson() throws IOException {
        ObjectMapper objetMapper = new ObjectMapper();

        File requestBody = Paths.get("src/test/resources/Nico/CreateUser.json").toFile();
        CreateUser user = objetMapper.readValue(requestBody, CreateUser.class);
        //user.setName("Nico");
        //user.setJob("President");

        //String expectedJob = "zion resident";
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("job", equalTo(user.getJob()));


    }
}
