package uk.service.offer.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uk.service.offer.persistence.model.Offer;
import uk.service.offer.service.OfferService;

import java.net.URI;

@RestController
public class OfferController {

    @Autowired
    private OfferService offerService;

    @RequestMapping(value = "/offers", method = RequestMethod.POST)
    public ResponseEntity createOffer(@RequestBody OfferDTO offerDTO) {

        Long offerId = offerService.create(new Offer(offerDTO.getDescription(), offerDTO.getCurrency(), offerDTO.getAmountInPence(), offerDTO.getStartDate(), offerDTO.getEndDate()));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(offerId).toUri();

        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/offers/{id}", method = RequestMethod.GET)
    public ResponseEntity getOffer(@PathVariable long id) {
        Offer offer = offerService.getOfferById(id);
        OfferDTO responseDTO = new OfferDTO(offer.getDescription(), offer.getCurrency(), offer.getAmountInPence(), offer.getStartDate(), offer.getEndDate());
        responseDTO.setStatus(offer.getStatus());
        return ResponseEntity.ok(responseDTO);
    }

    @RequestMapping(value = "/offers/{id}/cancel", method = RequestMethod.POST)
    public ResponseEntity cancelOffer(@PathVariable long id) {
        offerService.cancelOffer(id);
        return ResponseEntity.ok().build();
    }
}
