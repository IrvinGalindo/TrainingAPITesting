package org.automation;

import POJOs.CreateUser;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;


import static org.hamcrest.Matchers.equalTo;

public class CreateTest {
    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    public void userShouldBeCreatedAndNameIsReturn() {
        String expectedName = "Galletita";
        given()
                .contentType(ContentType.JSON) //Hay varios tipos de contentType, se despliegan al escribir el comando
                .body("{\n" +
                        "\t\"name\": \"" + expectedName + "\",\n" + // "\t\"name\": \"galletita\",\n" +
                        "\t\"job\" : \"zion resident\"\n" +
                        "\t\n" +
                        "}")
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("name", equalTo(expectedName));
    }

    @Test
    public void userShouldBeCreatedAndJobIsReturn() {
        String expectedJob = "zion resident";
        given()
                .contentType(ContentType.JSON) //Hay varios tipos de contentType, se despliegan al escribir el comando
                .body("{\n" +
                        "\t\"name\": \"Galletita\",\n" +
                        "\t\"job\" : \"" + expectedJob + "\"\n" + // "\t\"job\" : \"zion resident\"\n" +
                        "\t\n" +
                        "}")
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("job", equalTo(expectedJob));
    }

    @Test
    public void userShouldBeCreatedFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File requestBody = Paths.get("src/test/resources/Claudia/CreateUser.json").toFile();
        CreateUser user = objectMapper.readValue(requestBody, CreateUser.class);

        String expectedJob = "zion resident";
        given()
                .contentType(ContentType.JSON) //Hay varios tipos de contentType, se despliegan al escribir el comando
                .body(user)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("job", equalTo(user.getJob()));
    }






    }



