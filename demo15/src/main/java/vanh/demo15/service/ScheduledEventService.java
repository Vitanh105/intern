package vanh.demo15.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduledEventService {
//    private final ScheduledGiftEventRepository scheduledEventRepository;
//
//    // Chạy mỗi phút (có thể điều chỉnh cron expression)
//    @Scheduled(cron = "0 * * * * *")
//    @Transactional
//    public void activateScheduledEvents() {
//        LocalDateTime now = LocalDateTime.now();
//        List<ScheduledGiftEvent> events = scheduledEventRepository
//                .findByStartTimeLessThanEqualAndIsActiveFalse(now);
//
//        events.forEach(event -> {
//            event.setActive(true);
//            scheduledEventRepository.save(event);
//            log.info("🚀 Event [{}] đã bắt đầu lúc {} cho giftId: {}",
//                    event.getEventId(),
//                    event.getStartTime(),
//                    event.getGiftId()
//            );
//        });
//    }
}