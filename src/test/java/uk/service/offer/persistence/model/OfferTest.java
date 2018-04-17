package uk.service.offer.persistence.model;

import org.junit.Test;
import uk.service.offer.fixture.OfferFixture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class OfferTest {

    @Test
    public void onPostLoad_shouldSetStatusAs_CREATED_whenEndDateIsAfterNow() {
        Offer offer = OfferFixture.aValidOffer().build();

        assertThat(offer.getStatus(), is(nullValue()));
        offer.onPostLoad();
        assertThat(offer.getStatus(), is("CREATED"));
    }

    @Test
    public void onPostLoad_shouldSetStatusAs_EXPIRED_whenEndDateIsInThePast() {
        Offer offer = OfferFixture.aValidOffer()
                .withEndDate("2017-12-17T06:24:57Z")
                .build();

        assertThat(offer.getStatus(), is(nullValue()));
        offer.onPostLoad();
        assertThat(offer.getStatus(), is("EXPIRED"));
    }
}
