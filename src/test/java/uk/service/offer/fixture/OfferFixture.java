package uk.service.offer.fixture;

import uk.service.offer.persistence.model.Offer;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

/**
 * Offer cannot exist with all the data populated
 * so force OfferFixture to pre-populate an offer
 * that will be starting in a day from now and finishing in 2 days later.
 * <p>
 * So to override values as needed, this implementation fits into an
 * offer that will be triggered in the future.
 */
public class OfferFixture {

    private String description = "A description";
    private String currency = "GBP";
    private long amountInPence = 12345L;
    private Instant startDate = Instant.now().plus(1, ChronoUnit.DAYS);
    private Instant endDate = Instant.now().plus(2, ChronoUnit.DAYS);

    public static OfferFixture aValidOffer() {
        return new OfferFixture();
    }

    public OfferFixture withDescription(String description) {
        this.description = description;
        return this;
    }

    public OfferFixture withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public OfferFixture withAmountInPence(long amountInPence) {
        this.amountInPence = amountInPence;
        return this;
    }

    /**
     * Uses Data DateTimeFormatter.ISO_DATE_TIME up to seconds as String
     * Example "2018-04-15T06:24:00Z"
     * {@see java.time.format.DataTimeFormatter.ISO_DATE_TIME}
     */
    public OfferFixture withStartDate(String dateTime) {
        this.startDate = ZonedDateTime.from(ISO_DATE_TIME.parse(dateTime)).toInstant();
        return this;
    }

    /**
     * Uses Data DateTimeFormatter.ISO_DATE_TIME up to seconds as String
     * Example "2018-04-15T06:24:00Z"
     * {@see java.time.format.DataTimeFormatter.ISO_DATE_TIME}
     */
    public OfferFixture withEndDate(String dateTime) {
        this.endDate = ZonedDateTime.from(ISO_DATE_TIME.parse(dateTime)).toInstant();
        return this;
    }

    public Offer build() {
        return new Offer(description, currency, amountInPence, startDate, endDate);
    }
}
