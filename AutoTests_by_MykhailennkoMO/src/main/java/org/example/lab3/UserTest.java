package org.example.lab3;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Map;
import com.github.javafaker.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class UserTest {
    private static final String baseUrl = "https://petstore.swagger.io/v2";
    private static final String USER ="/user",
                                USER_USERNAME = USER + "/{username}",
                                USER_LOGIN = USER +"/login",
                                USER_LOGOUT = USER +"/logout",
                                PET = "/pet",
                                PET_UPDATE = PET,
                                PET_GET = PET+"/{petId}";
    private String username;
    private String firstName;
    @BeforeClass
    public void setup(){
        RestAssured.baseURI = baseUrl;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }
    //Тести з прикладів, що наведені в методичних вказівках

    //тестування можливості аутентифікації
    @Test
    public void verifyLoginAction(){
        Map<String, ?> body = Map.of(
                "username", "MariiaMykhailenko",
                "password", "122m-23-2.21"
        );
        Response response = given().body(body).get(USER_LOGIN);
        response.then().statusCode(HttpStatus.SC_OK);
        RestAssured.requestSpecification
                .sessionId(response.jsonPath()
                                    .get("message")
                                    .toString()
                                    .replaceAll("[^0-9]",""));
    }
    //тест на перевірку створення нового користувача у системі
    @Test(dependsOnMethods ="verifyLoginAction")
    public void verifyCreateAction(){
        username = Faker.instance().name().username();
        firstName = Faker.instance().harryPotter().character();
        Map<String, ?> body = Map.of(
                "username", username,
                "firstName", firstName,
                "lastName", Faker.instance().gameOfThrones().character(),
                "email", Faker.instance().internet().emailAddress(),
                "password", Faker.instance().internet().password(),
                "phone", Faker.instance().phoneNumber().phoneNumber(),
                "userStatus", Integer.valueOf("1")
        );
        given().body(body)
                .post(USER)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
//тест на перевірку отримання даних про користувача
    @Test (dependsOnMethods = "verifyCreateAction")
    public void verifyGetAction(){
        given().pathParam("username", username)
                .get(USER_USERNAME)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("firstName", equalTo(firstName));
    }
//тест на перевірку видалення користувача
    @Test(dependsOnMethods = "verifyGetAction")
    public void verifyDeleteAction(){
        given().pathParam("username", username)
                .delete(USER_USERNAME)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
    //тест на перевірку вихіду з аккаунту
    @Test(dependsOnMethods = "verifyLoginAction", priority = 1)
    public void verifyLogoutAction(){
        given().get(USER_LOGOUT)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

//Власні тести

    //POST
    @Test
    public void addNewPet(){
        Map <String, ?> category =Map.of(
                "id", 12223221,
                "name", "MariiaMykhailenko"
        );

        Map<String, ?> body = Map.of(
                "id", 12223221,
                "category", category,
                "name", "MariiaMykhailenko",
                "status", "available"
        );
        given().body(body)
                .post(PET)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
    //GET
    @Test (dependsOnMethods ="addNewPet")
    public void getPetById(){
        given().pathParam("petId", 12223221)
                .get(PET_GET)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
    //PUT
    @Test (dependsOnMethods = "addNewPet")
    public void updatePet(){
        Map<String, ?> body = Map.of(
                "id", 12223221,
                "name", "MariiaMykhailenko",
                "status", "sold"
        );
        given().body(body).put(PET_UPDATE).then().statusCode(HttpStatus.SC_OK);
    }
}
