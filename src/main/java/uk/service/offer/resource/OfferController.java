package uk.service.offer.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController("/offers")
public class OfferController {

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createOffer() {

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(1).toUri();

        return ResponseEntity.created(location).build();
    }
}
