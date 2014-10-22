package pl.edu.agh.offerseeker.model;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class OfferTest {
    private static final String DESCRIPTION = "DESCRIPTION";

    private final Offer offer = new Offer(DESCRIPTION);

    @Test
    public void shouldCreateOfferWithGivenDescription() {
        //then
        assertThat(offer.getDescription()).isEqualTo(DESCRIPTION);
    }

    @Test
    public void shouldCreateOffersWithDifferentIDs() {
        //when
        Offer differentOffer = new Offer(DESCRIPTION);

        //then
        assertThat(offer.getId()).isNotEqualTo(differentOffer.getId());
    }
}