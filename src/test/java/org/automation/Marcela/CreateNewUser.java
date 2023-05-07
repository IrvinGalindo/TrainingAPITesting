package org.automation.Marcela;

import POJOs.CreateUser;
import POJOs.User;
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

public class CreateNewUser {
    @BeforeClass
    public void setUpRestAssured(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }
@Test
    public void userShouldBeCreatedAndNameIsReturned(){
    String expectedName="morpheus";

    given()
            .contentType(ContentType.JSON)
            .body("{\n" +
                    "  \"name\":  \""+expectedName+"\",\n" +
                    "  \"job\":  \"leader\"\n" +
                    "}")
            .when()
            .post("/users")
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .body("name",equalTo(expectedName));

    }

    @Test
    public void userShouldBeCreatedAndJobIsReturned(){
        String expectedJob="leader";

        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"name\":  \"morpheus\",\n" +
                        "  \"job\":  \""+expectedJob+"\"\n" +
                        "}")
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("job",equalTo(expectedJob));
    }
    @Test
    public void userShouldBeCreatedFromJson() throws IOException {
        ObjectMapper objectMapper= new ObjectMapper();

        File requestBody= Paths.get("src/test/resources/Marcela/CreateUser.json").toFile();
        CreateUser user=objectMapper.readValue(requestBody,CreateUser.class);

        String expectedJob="zion resident";
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("job",equalTo(user.getJob()))
                .body("name",equalTo(user.getName()));
    }
}