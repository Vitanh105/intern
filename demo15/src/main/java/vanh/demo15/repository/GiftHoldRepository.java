package vanh.demo15.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vanh.demo15.model.GiftHold;

public interface GiftHoldRepository extends JpaRepository<GiftHold, String> {
}