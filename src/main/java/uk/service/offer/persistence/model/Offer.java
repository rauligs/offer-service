package uk.service.offer.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Offer {

    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private String currency;
    private long amountInPence;

    public Offer() {
        // JPA required
    }

    public Offer(String description, String currency, long amountInPence) {
        this.description = description;
        this.currency = currency;
        this.amountInPence = amountInPence;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Offer)) return false;
        Offer offer = (Offer) o;
        return amountInPence == offer.amountInPence &&
                Objects.equals(description, offer.description) &&
                Objects.equals(currency, offer.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, currency, amountInPence);
    }
}
