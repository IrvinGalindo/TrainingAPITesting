package org.automation;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

public class getListUsers {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    public void shouldValidateThatThePageIs2() {
        RestAssured
                .given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("page", is(2))
                .body("data[0].first_name", is("Janet"))
                .body("data[0].last_name", is("Weaver"))
                .body("data[0].email", is("janet.weaver@reqres.in"));
    }

    @Test
    public void shouldValidateThatThePageIs212() {
        int expectedUsersPerPage = 6;
        int expectedTotalUsers = 12;
        int expectedTotalPages = 2;

        Response response = RestAssured
                .given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        int actualUsersPerPage = response.jsonPath().getInt("per_page");
        int actualTotalUsers = response.jsonPath().getInt("total");
        int actualTotalPages = response.jsonPath().getInt("total_pages");

        assertEquals(expectedUsersPerPage, actualUsersPerPage);
        assertEquals(expectedTotalUsers, actualTotalUsers);
        assertEquals(expectedTotalPages, actualTotalPages);
    }
}
