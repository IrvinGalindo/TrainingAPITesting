package org.automation.Kim;

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
    public void setUp(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test(dataProvider = "createUsers")
    public void userShouldBeCreatedFomJson(String name, String job) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        File requestBody = Paths.get("src/test/resources/Kimberly/CreateUser.json").toFile();
        CreateUser user = objectMapper.readValue(requestBody, CreateUser.class);

        user.setName(name);
        user.setJob(job);

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("name",equalTo(user.getName()))
                .body("job", equalTo(user.getJob()));

    }

    @DataProvider
    public Object[][] createUsers(){
        Object[][] users = new Object[5][2];

        users[0][0] = "Kim";
        users[0][1] = "CEO";
        users[1][0] = "Sam";
        users[1][1] = "SDET";
        users[2][0] = "Sanchez";
        users[2][1] = "SDET";
        users[3][0] = "Kim123";
        users[3][1] = "Job_1";

        return users;
    }

}
