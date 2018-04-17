package uk.service.offer.fixture;

import uk.service.offer.persistence.model.Offer;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Offer cannot exist with all the data populated
 * so force OfferFixture to pre-populate an offer
 * that will be starting in a day from now and finishing in 2 days later.
 *
 * So to override values as needed, this implementation fits into an
 * offer that will be triggered in the future.
 */
public class OfferFixture {

    private String description = "A description";
    private String currency = "GBP";
    private long amountInPence = 12345L;
    private Date startDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
    private Date endDate = Date.from(Instant.now().plus(2, ChronoUnit.DAYS));

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

    public OfferFixture withStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public OfferFixture withEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public Offer build() {
        return new Offer(description, currency, amountInPence, startDate, endDate);
    }
}
