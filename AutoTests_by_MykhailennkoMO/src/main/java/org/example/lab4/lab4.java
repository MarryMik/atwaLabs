package org.example.lab4;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
public class lab4 {
    private static final String baseUrl ="https://ddf5bcbc-022e-4b60-917d-52cfcadd038a.mock.pstmn.io";
    private static final String GET_SUCCESS ="/ownerName",
                                GET_UNSUCCESS = "/ownerName/unsuccess",
                                CREATE = "/createSomething",
                                CREATE_WITH_PERMISSION ="createSomething?permission=yes",
                                UPDATE  ="/updateMe",
                                DELETE = "/deleteWorld";
    @BeforeClass
    public void setup(){
        RestAssured.baseURI = baseUrl;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }
    //GET
    @Test
    public void getOwnerName(){
        given().get(GET_SUCCESS).then().statusCode(HttpStatus.SC_OK);
    }
    @Test
    public void getOwnerNameError(){
        given().get(GET_UNSUCCESS).then().statusCode(HttpStatus.SC_FORBIDDEN);
    }
    //POST
    @Test
    public void createNothing(){
        given().post(CREATE_WITH_PERMISSION).then().statusCode(HttpStatus.SC_OK);
    }
    @Test
    public void createNothingError(){
        given().post(CREATE).then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }
    //PUT
    @Test
    public void updateData(){
        given().put(UPDATE).then().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
    //DELETE
    @Test
    public void deleteData(){
        given().delete(DELETE).then().statusCode(HttpStatus.SC_GONE);
    }
}
