package uk.service.offer.resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import uk.service.offer.Application;

import static io.restassured.RestAssured.when;
import static org.hamcrest.core.Is.is;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OfferControllerTest {

    @LocalServerPort
    private int port;

    @Test
    public void shouldSayHi() {

        when()
            .get(String.format("http://localhost:%s/hi", port))
        .then()
            .statusCode(is(200))
            .body(is("Hi there!"));
    }
}