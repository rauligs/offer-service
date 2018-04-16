package uk.service.offer.persistence.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import uk.service.offer.persistence.model.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long> {
}
