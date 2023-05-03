package org.automation.alejandro;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import static org.testng.Assert.assertEquals;

public class GetListUsers {

    @BeforeClass
    public void setUp (){
        RestAssured.baseURI= "https://reqres.in";
        RestAssured.basePath= "/api";
    }

    @Test
    public void shouldIValidatePageIs2 (){

        given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("page", is(2));

    }

    @Test
    public void shouldIValidateTotalUsersInPage2Is12 (){

        Response jsonPath = given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        int actualUsersPerPage = jsonPath.path("per_page");
        int actualTotalUsers = jsonPath.path("total");
        int actualTotalPages = jsonPath.path("total_pages");

                assertEquals((actualTotalPages*actualUsersPerPage),actualTotalUsers, "Total users is not equal to 12");

    }

}
