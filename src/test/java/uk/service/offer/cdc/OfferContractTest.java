package uk.service.offer.cdc;

import au.com.dius.pact.provider.junit.Consumer;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import uk.service.offer.Application;

import javax.sql.DataSource;
import java.sql.Timestamp;

@RunWith(SpringRestPactRunner.class)
@Provider("OfferService")
@Consumer("OfferClient")
@PactFolder("C:/Users/danielbeddoe/Documents/projects/workprojects/offer-service/src/test/resources/uk/service/offer/")
@SpringBootTest(classes = {
        Application.class, TestConfig.class
}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OfferContractTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @TestTarget
    public final Target target = new SpringBootHttpTarget();

    @State("I have an offer previously created")
    public void setupAnOffer() {
        final Timestamp startDateTimestamp = Timestamp.valueOf("2018-09-01 22:10:00");
        final Timestamp endDateTimestamp = Timestamp.valueOf("2018-10-01 22:10:00");
        jdbcTemplate.update(
                "INSERT INTO OFFER (ID, DESCRIPTION, CURRENCY, AMOUNTINPENCE, STARTDATE, ENDDATE, STATUS, ISCANCELLED) " +
                        "VALUES (5555, 'great offer for great people', 'GBP', 12345, ?, ?, 'CREATED', 'false')", startDateTimestamp, endDateTimestamp);
    }
}

@Configuration
@PropertySource("persistence.properties")
class TestConfig {

    @Autowired
    private Environment env;

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
