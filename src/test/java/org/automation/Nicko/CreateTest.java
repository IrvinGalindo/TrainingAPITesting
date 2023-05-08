package org.automation.Nicko;

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
    public void userShouldBeCreatedAndNameIsReturned() {
        String expectedName = "Galletita";
        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"name\": \"" + expectedName + "\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("name", equalTo(expectedName));
    }

    @Test
    public void userShouldBeCreatedAndJobIsReturned() {
        String expectedJob = "zion resident";
        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"name\": \"Galletita\",\n" +
                        "    \"job\": \"" + expectedJob + "\"\n" +
                        "}")
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("job", equalTo(expectedJob));
    }

    @Test(dataProvider = "createUsers")
    public void userShouldBeCreatedFromJson(String name, String job) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        //File requestBody = Paths.get("src/test/resources/Nicko/CreateUser.json").toFile();
        File requestBody = Paths.get("src/test/resources/Nico/CreateUser.json").toFile();
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
                .body("name", equalTo(user.getName()))
                .body("job", equalTo(user.getJob()));
    }

    @DataProvider
    public Object[][] createUsers(){
        Object[][] users = new Object[5][2];

        users[0][0] = "Irvin";
        users[0][1] = "CEO";
        users[1][0] = "Omar";
        users[1][1] = "SDET";
        users[2][0] = "GALINDO";
        users[2][1] = "SDET";
        users[3][0] = "Irvin123";
        users[3][1] = "Job_1";

        return users;
    }

}
