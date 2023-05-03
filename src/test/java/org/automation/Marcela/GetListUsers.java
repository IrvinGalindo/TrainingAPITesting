package org.automation.Marcela;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class GetListUsers {
    @BeforeClass
    public void setUpRestAssured(){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    public void shouldValidatePageIs2() {
       Response jsonPath= given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        int actualUsersPerPage=jsonPath.path("per_page");
        int actualTotalUsers=jsonPath.path("total");
        int actualTotalPages=jsonPath.path("total_pages");
        int expectedTotalUsers=actualTotalPages*actualUsersPerPage;

        assertEquals(expectedTotalUsers,actualTotalUsers,"Total users is not equal to 12");
    }

}
