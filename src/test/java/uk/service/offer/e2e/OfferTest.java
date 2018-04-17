package uk.service.offer.e2e;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import uk.service.offer.Application;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
/**
 * In order to test 'a user journey' maybe the name end to end is too much for this test but at least making
 * sure saving a request an Offer does not surprise us
 */
public class OfferTest {

    @LocalServerPort
    private int port;

    @Test
    public void retrieve_aCreatedOffer_shouldSucceed() {

        String anOffer = "{" +
                "\"description\":\"you should buy this cheap thing\"," +
                "\"currency\":\"GBP\"," +
                "\"amountInPence\":100000," +
                "\"startDate\":\"2018-04-01 06:24\"," +
                "\"endDate\":\"2018-04-18 06:24\"" +
                "}";

        given()
            .port(port)
            .header("Content-Type", "application/json")
            .body(anOffer)
            .post("/offers")
        .then()
            .statusCode(is(201))
            .header("Location", is(String.format("http://localhost:%s/offers/1", port)));

        given()
            .port(port)
            .header("Accept", "application/json")
            .get("/offers/1")
        .then()
            .statusCode(is(200))
            .body("description", is("you should buy this cheap thing"))
            .body("currency", is("GBP"))
            .body("amountInPence", is(100000))
            .body("startDate", is("2018-04-01 06:24"))
            .body("endDate", is("2018-04-18 06:24"));
    }
}