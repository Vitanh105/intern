package vanh.demo15.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import vanh.demo15.model.Gift;

public interface GiftRepository extends JpaRepository<Gift, String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT g FROM Gift g WHERE g.giftId = :giftId")
    Gift findByIdWithLock(String giftId);
}