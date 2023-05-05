package org.automation.Kim;

import POJOs.CreateUser;
import POJOs.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class CreateNewUser {

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    public void userShouldBeCreatedAndNameIsReturned(){
        String expectedName = "Galletita";
        given()
                .contentType("application/json") //Tambipén se puede con librería que es ContentType.JASON
                .body("{\n" +
                        "    \"name\": \"" + expectedName + "\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("name", Matchers.equalTo(expectedName));

    }

    @Test
    public void userShouldBeCreatedAndJobIsReturned(){
        String expectedJob = "zion resident";
        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"name\": \" Galletita\",\n" +
                        "    \"job\": \"" + expectedJob+ "\"\n" +
                        "}")
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("job", Matchers.equalTo(expectedJob));

    }

    @Test
    public void userShouldBeCreatedFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        File requestBody = Paths.get("src/test/resources/Kimberly/CreateUser.json").toFile();
        CreateUser user = objectMapper.readValue(requestBody, CreateUser.class);
        //Outra forma puede ser
        //user.setName("Kimberly")
        //user.setJob("CEO");

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("job", Matchers.equalTo(user.getName()))
                .body("job", Matchers.equalTo(user.getJob()));

    }

}
