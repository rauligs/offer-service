package uk.service.offer.resource;

import com.fasterxml.jackson.annotation.JsonCreator;

public class OfferDTO {

    private String description;

    @JsonCreator
    public OfferDTO(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
