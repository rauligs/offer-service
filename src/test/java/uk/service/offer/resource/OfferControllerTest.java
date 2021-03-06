package uk.service.offer.resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.service.offer.exception.OfferNotFoundException;
import uk.service.offer.persistence.model.Offer;
import uk.service.offer.service.OfferService;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.service.offer.fixture.OfferFixture.aValidOffer;

@RunWith(SpringRunner.class)
@WebMvcTest(OfferController.class)
public class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferService mockOfferService;

    @Test
    public void createAnOffer_shouldSaveANewOffer_returningExpectedLocation() throws Exception {

        Long savedOfferId = 1L;
        when(mockOfferService.create(argThat(offer ->
                offer.getId() == null &&
                        "wow".equals(offer.getDescription()) &&
                        "GBP".equals(offer.getCurrency()) &&
                        offer.getAmountInPence() == 12345L)))
                .thenReturn(savedOfferId);

        this.mockMvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"description\":\"wow\"," +
                        "\"currency\":\"GBP\"," +
                        "\"amountInPence\":12345" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/offers/1"));
    }

    @Test
    public void getAnOffer_shouldReturnOk_withContentSetInOffer() throws Exception {

        long id = 123L;
        Offer existingOffer = aValidOffer()
                .withDescription("My description")
                .withCurrency("GBP")
                .withAmountInPence(1L)
                .withStartDate("2018-04-01T06:24:00Z")
                .withEndDate("2018-04-18T06:24:55Z")
                .build();

        when(mockOfferService.getOfferById(id))
                .thenReturn(existingOffer);

        this.mockMvc.perform(get("/offers/123"))
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "\"description\":\"My description\"," +
                        "\"currency\":\"GBP\"," +
                        "\"amountInPence\":1," +
                        "\"startDate\":\"2018-04-01T06:24:00Z\"," +
                        "\"endDate\":\"2018-04-18T06:24:55Z\"" +
                        "}"));
    }

    @Test
    public void cancelOffer_shouldCallOfferRepository_withTheGivenId() throws Exception {

        this.mockMvc.perform(post("/offers/123/cancel"))
                .andExpect(status().isOk());

        verify(mockOfferService).cancelOffer(123L);
    }

    @Test
    public void getAnOffer_shouldReturnNotFound() throws Exception {

        long nonFoundOfferId = 321L;

        when(mockOfferService.getOfferById(nonFoundOfferId))
                .thenThrow(new OfferNotFoundException());

        this.mockMvc.perform(get("/offers/" + nonFoundOfferId))
                .andExpect(status().isNotFound());
    }
}
