package org.automation;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

public class getListUsers {
    @BeforeClass
    public voic setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    public void shouldValidateThatThePageIs2() {
        RestAssured
                .given()
                .queryParam("page", 2)
                .when()
                .get("/users?page={page}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("page", is(2))
                .body("data.first_name", is("Janet", "Weaver"))
                .body("data.email", is("janet.weaver@reqres.in"));
    }
    @Test
    public void shouldValidateThatThePageIs212() {
        RestAssured
                .given()
                .queryParam("page", 2)
                .when()
                .get("/users?page={page}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("page", is(2))
                .extract()
                .response();
        int actualUsersPerPage = jsonPath.getInt("per_page");
        int actualTotalUsers = jsonPath.getInt("total");
        int actualTotalPages = jsonPath.getInt("total_pages");

        assertEquals(expectedUsersPerPage, actualUsersPerPage);
        assertEquals(expectedTotalUsers, actualTotalUsers);
        assertEquals(expectedTotalPages, actualTotalPages);

    }

}
}