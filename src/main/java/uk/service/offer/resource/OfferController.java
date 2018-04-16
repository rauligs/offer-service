package uk.service.offer.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OfferController {

    @RequestMapping("/hi")
    public String sayHi() {
        return "Hi there!";
    }
}
