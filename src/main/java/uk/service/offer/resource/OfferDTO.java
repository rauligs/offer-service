package uk.service.offer.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OfferDTO {

    private final String description;
    private final String currency;
    private final long amountInPence;

    @JsonCreator
    public OfferDTO(@JsonProperty("description") String description,
                    @JsonProperty("currency") String currency,
                    @JsonProperty("amountInPence") long amountInPence) {
        this.description = description;
        this.currency = currency;
        this.amountInPence = amountInPence;
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
}
