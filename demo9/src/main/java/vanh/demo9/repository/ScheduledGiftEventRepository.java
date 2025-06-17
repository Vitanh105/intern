package vanh.demo9.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vanh.demo9.model.ScheduledGiftEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduledGiftEventRepository extends JpaRepository<ScheduledGiftEvent, String> {
    Optional<ScheduledGiftEvent> findByGiftId(String giftId);

    List<ScheduledGiftEvent> findByStartTimeLessThanEqualAndIsActiveFalse(LocalDateTime now);
}