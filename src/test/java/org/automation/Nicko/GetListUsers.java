package org.automation.Nicko;

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
    public void setUp(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }
    @Test
    public void shouldValidatePageIs2(){

        given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(HttpStatus.SC_OK)
               .body("page" , is(2));
    }

    public void shouldValidateTotalUsersInPage2IS12(){

       Response jsonPath = given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

       int actualUserPerPage = jsonPath.path("per_page");
       int actualTotalUser = jsonPath.path ("total");
       int actualTotalPages = jsonPath.path("total_pages");

       assertEquals((actualUserPerPage*actualTotalPages),actualTotalUser, "Total User is not Equal to 12");
    }
}
