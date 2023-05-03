package org.automation;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.Argument;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

public class GetListUsersP {
    @BeforeClass
    public void setUpRestAssured(){
        RestAssured.baseURI="https://reqres.in";
        RestAssured.basePath="/api";
    }

    @Test
    public void shouldValidatePageIs2 (){
        given() //to add path or query. where we're going to get the data.
                .queryParam("page", 2)//query param doesn't require to specify in the get.
                .when()
                .get("/users")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("page", is(2));
    }

    @Test
    public void shouldValidateTotalUsersPage2 (){
        Response jsonPath =
                given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response()
                ;

        int usersPerPage = jsonPath.path("per_page");
        int totalUsers = jsonPath.path("total");
        int totalPages = jsonPath.path("total_pages");

        assertEquals((usersPerPage*totalPages),totalUsers, "Total users is not equal to 12");

    }
    @Test
    public void shouldValidateNewUserCreation (){
        given()
                .when()
                .body()
                .
                ;
    }
}
