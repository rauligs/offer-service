package uk.service.offer.resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import uk.service.offer.Application;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OfferControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Test
    public void createAnOffer_shouldSucceed_andReturnExpectedLocationHeader() {

        String anOffer = "{\"description\":\"you should buy this cheap thing\"}";

        given()
            .port(port)
            .header("Content-Type", "application/json")
            .body(anOffer)
            .post("/offers")
        .then()
            .statusCode(is(201))
            .header("Location", is(String.format("http://localhost:%s/offers/1", port)));
    }
}