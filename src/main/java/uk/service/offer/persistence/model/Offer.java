package uk.service.offer.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import java.util.Date;
import java.util.Objects;

@Entity
public class Offer {

    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private String currency;
    private long amountInPence;
    private Date startDate;
    private Date endDate;
    private String status;

    public Offer() {
        // JPA required
    }

    public Offer(String description, String currency, long amountInPence, Date startDate, Date endDate) {
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

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
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
        this.status = "CREATED";
    }
}
