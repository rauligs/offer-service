package uk.service.offer.cdc;

import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.service.offer.Application;

import javax.sql.DataSource;
import java.sql.Timestamp;

@RunWith(SpringRestPactRunner.class)
@Provider("OfferService")
@PactFolder("/Users/danielbeddoe/Documents/Projects/Personal/offer-service/src/test/resources")
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OfferContractTest {

    @TestTarget
    public final Target target = new SpringBootHttpTarget();

    @Autowired
    private DataSource dataSource;

    @State("I have an offer previously created")
    public void createOffer() {
        Timestamp startDate = Timestamp.valueOf("2018-09-01 22:10:00");
        Timestamp endDate = Timestamp.valueOf("2018-10-01 22:10:00");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("INSERT INTO OFFER " +
                "(ID, DESCRIPTION, CURRENCY, AMOUNTINPENCE, STARTDATE, ENDDATE, STATUS," +
                "ISCANCELLED) VALUES (5555, 'abc', 'GBP', 12345, ?, ?, 'CREATED', false)", startDate, endDate);
    }

}
