package vanh.demo9.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vanh.demo9.dto.GiftClaimRequest;
import vanh.demo9.model.ScheduledGiftEvent;
import vanh.demo9.repository.ScheduledGiftEventRepository;
import vanh.demo9.service.GiftService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gift")
public class GiftController {
    private final GiftService giftService;
    private final ScheduledGiftEventRepository scheduledEventRepository;

    @PostMapping("/claim")
    public ResponseEntity<?> claimGift(@RequestBody GiftClaimRequest giftClaimRequest) {
        String otp = giftService.processRequest(giftClaimRequest);
        if (otp != null) {
            return ResponseEntity.accepted().body(Map.of("otp", otp));
        } else {
            return ResponseEntity.badRequest().body("Request rejected");
        }
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmGift(@RequestParam String requestId, @RequestParam String otp) {
        boolean confirmed = giftService.confirmGift(requestId, otp);
        if (confirmed) {
            return ResponseEntity.ok("Gift confirmed");
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP or request expired");
        }
    }

    @PostMapping("/schedule")
    public ResponseEntity<String> scheduleGiftEvent(@RequestBody ScheduledGiftEvent event) {
        event.setActive(false);
        scheduledEventRepository.save(event);
        return ResponseEntity.ok("Event đã được lên lịch");
    }

    @GetMapping("/check-schedule/{giftId}")
    public ResponseEntity<?> checkSchedule(@PathVariable String giftId) {
        Optional<ScheduledGiftEvent> event = scheduledEventRepository.findByGiftId(giftId);
        if (event.isPresent()) {
            return ResponseEntity.ok(event.get());
        }
        return ResponseEntity.notFound().build();
    }
}