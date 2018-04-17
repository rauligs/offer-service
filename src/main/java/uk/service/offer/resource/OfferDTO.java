package uk.service.offer.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class OfferDTO {

    private final String description;
    private final String currency;
    private final long amountInPence;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private final Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private final Date endDate;

    @JsonCreator
    public OfferDTO(@JsonProperty("description") String description,
                    @JsonProperty("currency") String currency,
                    @JsonProperty("amountInPence") long amountInPence,
                    @JsonProperty("startDate") Date startDate,
                    @JsonProperty("endDate") Date endDate) {
        this.description = description;
        this.currency = currency;
        this.amountInPence = amountInPence;
        this.startDate = startDate;
        this.endDate = endDate;
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
}
