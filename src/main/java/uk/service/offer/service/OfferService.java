package uk.service.offer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.service.offer.exception.OfferNotFoundException;
import uk.service.offer.persistence.dao.OfferRepository;
import uk.service.offer.persistence.model.Offer;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    public Long create(Offer offer) {
        Offer savedOffer = offerRepository.save(offer);
        return savedOffer.getId();
    }

    public Offer getOfferById(Long offerId) {
        return offerRepository.findById(offerId)
                .orElseThrow(OfferNotFoundException::new);
    }
}
