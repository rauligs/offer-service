package uk.service.offer.persistence.model;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class OfferTest {

    @Test
    public void onPostLoad_shouldSetStatusAs_CREATED() {
        Offer offer = new Offer();
        assertThat(offer.getStatus(), is(nullValue()));
        offer.onPostLoad();
        assertThat(offer.getStatus(), is("CREATED"));
    }
}
