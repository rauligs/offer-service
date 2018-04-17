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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        when(mockOfferService.create(argThat(offer -> offer.getId() == null)))
                .thenReturn(savedOfferId);

        this.mockMvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"wow\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/offers/1"));
    }

    @Test
    public void getAnOffer_shouldReturnOk() throws Exception {

        long id = 123L;
        Offer existingOffer = new Offer();
        existingOffer.setId(id);
        existingOffer.setDescription("My description");

        when(mockOfferService.getOfferById(id))
                .thenReturn(existingOffer);

        this.mockMvc.perform(get("/offers/123"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"description\":\"My description\"}"));
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
