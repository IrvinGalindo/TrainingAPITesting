package org.automation.alejandro;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class PostCreateNewUser {

    @BeforeClass
    public void setUp (){
        RestAssured.baseURI= "https://reqres.in";
        RestAssured.basePath= "/api/users";
    }

    @Test
    public void shouldIValidateNewUserIsRegistered (){

        given()
                .when()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
                .then()
                .statusCode(HttpStatus.SC_CREATED);

    }

    @Test
    public void shouldIValidateUserNameIsMorpheus (){

        given()
                .when()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("name", is("morpheus"));

    }

    @Test
    public void shouldIValidateUserJobIsLeadeer (){

        given()
                .when()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("job", is("leader"));

    }


}
