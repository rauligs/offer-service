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
import static org.hamcrest.core.Is.is;


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

    @Test
    public void retrieveOffer_shouldSucceed_whenAnOfferExists_withTheGivenId() {

        Offer existingOffer = offerRepository.save(new Offer());

        given()
                .port(port)
                .header("Accept", "application/json")
                .get("/offers/" + existingOffer.getId())
            .then()
                .statusCode(is(200));
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