package uk.service.offer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.service.offer.exception.InvalidOfferStateException;
import uk.service.offer.exception.OfferNotFoundException;
import uk.service.offer.persistence.dao.OfferRepository;
import uk.service.offer.persistence.model.Offer;

import javax.transaction.Transactional;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    public Long create(Offer offer) {
        Offer savedOffer = offerRepository.save(offer);
        return savedOffer.getId();
    }

    public Offer getOfferById(long offerId) {
        return offerRepository.findById(offerId)
                .orElseThrow(OfferNotFoundException::new);
    }

    @Transactional
    public void cancelOffer(long offerId) {
        Offer offer = getOfferById(offerId);
        if("EXPIRED".equals(offer.getStatus())) {
            throw new InvalidOfferStateException();
        }
        offer.cancel();
    }
}
