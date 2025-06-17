package vanh.demo15.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vanh.demo15.model.ScheduledGiftEvent;
import vanh.demo15.repository.ScheduledGiftEventRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventSchedulerService {
    private final ScheduledGiftEventRepository scheduledEventRepository;

    @Scheduled(fixedRate = 30000)
    @Transactional
    public void activateScheduledEvents() {
        LocalDateTime now = LocalDateTime.now();
        List<ScheduledGiftEvent> events = scheduledEventRepository
                .findByStartTimeLessThanEqualAndActiveFalse(now);

        for (ScheduledGiftEvent event : events) {
            event.setActive(true);
            scheduledEventRepository.save(event);
            log.info("🚀 Event activated: {} for giftId: {}", event.getEventId(), event.getGiftId());
        }
    }
}