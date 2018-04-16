package uk.service.offer.persistence.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import uk.service.offer.DatabaseConfig;
import uk.service.offer.persistence.model.Offer;

import javax.annotation.Resource;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {DatabaseConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class OfferRepositoryInMemoryDBTest {

    @Resource
    private OfferRepository offerRepository;

    /**
     * Note: I don't consider a very good practice using the same
     * repository to test the data we have just inserted.
     * I would create different way to query the state of the database
     * so repository tests would be tested in isolation
     */
    @Test
    public void saveOffer_whenIsSaved_itShouldBeFound() {

        Offer offer = new Offer();
        Offer savedOffer = offerRepository.save(offer);

        // Note: I don't consider a very good practice using the same
        // repository to test the data we have just inserted.
        // I would create different way to query the state of the database
        // so repository tests would be tested in isolation

        Offer foundOffer = offerRepository.getOne(savedOffer.getId());

        assertThat(foundOffer, is(notNullValue()));
        assertThat(foundOffer.getId(), is(savedOffer.getId()));
    }
}
