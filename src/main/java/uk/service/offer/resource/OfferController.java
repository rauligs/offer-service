package uk.service.offer.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.service.offer.persistence.dao.OfferRepository;
import uk.service.offer.persistence.model.Offer;

import java.net.URI;

@RestController
public class OfferController {

    @Autowired
    private OfferRepository offerRepository;

    @RequestMapping(value = "/offers", method = RequestMethod.POST)
    public ResponseEntity createOffer() {

        Offer savedOffer = offerRepository.save(new Offer());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedOffer.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/offers/{id}", method = RequestMethod.GET)
    public ResponseEntity getOffer(@PathVariable long id) {
        return ResponseEntity.ok().build();
    }
}
