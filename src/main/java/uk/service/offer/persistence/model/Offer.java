package uk.service.offer.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import java.time.Instant;
import java.util.Objects;

@Entity
public class Offer {

    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private String currency;
    private long amountInPence;
    private Instant startDate;
    private Instant endDate;
    private String status;

    public Offer() {
        // JPA required
    }

    public Offer(String description, String currency, long amountInPence, Instant startDate, Instant endDate) {
        this.description = description;
        this.currency = currency;
        this.amountInPence = amountInPence;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getCurrency() {
        return currency;
    }

    public long getAmountInPence() {
        return amountInPence;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Offer)) return false;
        Offer offer = (Offer) o;
        return amountInPence == offer.amountInPence &&
                Objects.equals(description, offer.description) &&
                Objects.equals(currency, offer.currency) &&
                Objects.equals(startDate, offer.startDate) &&
                Objects.equals(endDate, offer.endDate) &&
                Objects.equals(status, offer.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(description, currency, amountInPence, startDate, endDate, status);
    }

    @PostLoad
    void onPostLoad() {
        if (Instant.now().isAfter(this.endDate)) {
            this.status = "EXPIRED";
        } else {
            this.status = "CREATED";
        }
    }
}
