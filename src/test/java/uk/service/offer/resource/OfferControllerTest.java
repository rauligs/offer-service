package uk.service.offer.resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.service.offer.persistence.dao.OfferRepository;
import uk.service.offer.persistence.model.Offer;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OfferController.class)
public class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferRepository mockOfferRepository;

    @Test
    public void createAnOffer_shouldSaveANewOffer_returningExpectedLocation() throws Exception {

        Offer savedOffer = new Offer();
        savedOffer.setId(1);

        when(mockOfferRepository.save(argThat(offer -> offer.getId() == null)))
                .thenReturn(savedOffer);

        this.mockMvc.perform(post("/offers")).andDo(print()).andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/offers/1"));
    }

    @Test
    public void getAnOffer_shouldReturnOk() throws Exception {

        this.mockMvc.perform(get("/offers/1")).andDo(print())
                .andExpect(status().isOk());

        verifyZeroInteractions(mockOfferRepository);
    }
}
