package uk.service.offer.resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import uk.service.offer.Application;
import uk.service.offer.persistence.dao.OfferRepository;
import uk.service.offer.persistence.model.Offer;

import java.time.Instant;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static uk.service.offer.fixture.OfferFixture.aValidOffer;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OfferControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private OfferRepository offerRepository;

    @Test
    public void createAnOffer_shouldSucceed_andReturnExpectedLocationHeader() {

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
    }

    @Test
    public void retrieveOffer_shouldSucceed_whenAnOfferExists_withTheGivenId() {

        Offer offer = aValidOffer().withDescription("Greatest offer")
                .withStartDate(Date.from(Instant.parse("2100-04-17T06:24:57Z")))
                .withEndDate(Date.from(Instant.parse("2110-04-18T06:24:57Z"))).build();

        Offer existingSavedOffer = offerRepository.save(offer);

        given()
                .port(port)
                .header("Accept", "application/json")
                .get("/offers/" + existingSavedOffer.getId())
            .then()
                .statusCode(is(200))
                .body("description", is("Greatest offer"))
                .body("currency", is("GBP"))
                .body("amountInPence", is(12345))
                .body("startDate", is("2100-04-17 06:24"))
                .body("endDate", is("2110-04-18 06:24"));
    }

    @Test
    public void retrieveOffer_shouldFailAsNotFound_whenAnOfferDoesNotExist() {

        long nonExistingOfferId = 999;

        given()
                .port(port)
                .header("Accept", "application/json")
                .get("/offers/" + nonExistingOfferId)
            .then()
                .statusCode(is(404));
    }
}