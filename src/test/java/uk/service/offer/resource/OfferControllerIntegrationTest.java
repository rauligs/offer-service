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

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
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
                "\"startDate\":\"2018-04-01T06:24:00Z\"," +
                "\"endDate\":\"2018-04-18T06:24:00Z\"" +
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
                .withStartDate("2100-04-17T06:24:57Z")
                .withEndDate("2110-04-17T06:24:57Z").build();

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
                .body("startDate", is("2100-04-17T06:24:57Z"))
                .body("endDate", is("2110-04-17T06:24:57Z"))
                .body("status", is("CREATED"));
    }

    @Test
    public void retrieveOffer_shouldBeExpired_whenAnOfferExists_withTheGivenId_butEndDateIsInThePast() {

        Offer offer = aValidOffer().withDescription("Greatest offer")
                .withStartDate("2018-04-15T06:24:00Z")
                .withEndDate("2018-04-16T06:24:00Z").build();

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
                .body("startDate", is("2018-04-15T06:24:00Z"))
                .body("endDate", is("2018-04-16T06:24:00Z"))
                .body("status", is("EXPIRED"));
    }

    @Test
    public void cancelOffer_shouldReturnOk_whenAnOfferExists() {

        Offer existingSavedOffer = offerRepository.save(aValidOffer()
                .withDescription("Greatest offer")
                .withStartDate("2018-04-15T06:24:00Z")
                .withEndDate("2100-04-16T06:24:00Z").build());

        given()
                .port(port)
                .post("/offers/" + existingSavedOffer.getId() + "/cancel")
            .then()
                .statusCode(is(200));
    }

    @Test
    public void cancelOffer_shouldReturnNotFound_whenAnOfferDoesNotExist() {

        given()
                .port(port)
                .header("Accept", "application/json")
                .post("/offers/555/cancel")
                .then()
                .statusCode(is(404));
    }

    @Test
    public void cancelOffer_shouldReturnConflict_whenOfferIsExpired() {

        Offer expiredOffer = offerRepository.save(aValidOffer()
                .withDescription("Greatest offer")
                .withStartDate("2017-04-15T06:24:00Z")
                .withEndDate("2017-04-16T06:24:00Z").build());

        given()
                .port(port)
                .post("/offers/" + expiredOffer.getId() + "/cancel")
                .then()
                .statusCode(is(409));
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