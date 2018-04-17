package uk.service.offer.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class OfferDTO {

    private final String description;
    private final String currency;
    private final long amountInPence;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private final Instant startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private final Instant endDate;
    private String status;

    @JsonCreator
    public OfferDTO(@JsonProperty("description") String description,
                    @JsonProperty("currency") String currency,
                    @JsonProperty("amountInPence") long amountInPence,
                    @JsonProperty("startDate") Instant startDate,
                    @JsonProperty("endDate") Instant endDate) {
        this.description = description;
        this.currency = currency;
        this.amountInPence = amountInPence;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
