package uk.service.offer.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import uk.service.offer.exception.OfferNotFoundException;
import uk.service.offer.persistence.dao.OfferRepository;
import uk.service.offer.persistence.model.Offer;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class OfferServiceTest {

    @TestConfiguration
    static class OfferServiceImplTestContextConfiguration {
        @Bean
        public OfferService offerService() {
            return new OfferService();
        }
    }

    @Autowired
    private OfferService offerService;

    @MockBean
    private OfferRepository mockOfferRepository;

    @Test
    public void createAnOffer_shouldSaveANewOffer_returningExpectedLocation() {

        Offer offerToCreate = new Offer();
        offerToCreate.setDescription("Interesting offer");

        Offer savedOffer = new Offer();
        long savedOfferId = 1L;
        savedOffer.setDescription("Interesting offer");
        savedOffer.setId(savedOfferId);

        when(mockOfferRepository.save(offerToCreate))
                .thenReturn(savedOffer);

        Long offerId = offerService.create(offerToCreate);

        assertThat(offerId, is(savedOfferId));
    }

    @Test
    public void getAnOffer_shouldReturnOk() {

        long id = 123L;
        Offer existingOffer = new Offer();
        existingOffer.setId(id);
        existingOffer.setDescription("My description");

        when(mockOfferRepository.findById(id))
                .thenReturn(Optional.of(existingOffer));

        Offer retrievedOffer = offerService.getOfferById(id);

        assertThat(retrievedOffer, is(existingOffer));
    }

    @Test(expected = OfferNotFoundException.class)
    public void getAnOffer_shouldThrowException_whenNotFound() {

        long id = 321L;
        Offer nonExistingOffer = new Offer();
        nonExistingOffer.setId(id);

        when(mockOfferRepository.findById(id))
                .thenReturn(Optional.empty());

        offerService.getOfferById(id);
    }
}
