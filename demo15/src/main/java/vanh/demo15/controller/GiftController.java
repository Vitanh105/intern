package vanh.demo15.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vanh.demo15.dto.GiftClaimRequest;
import vanh.demo15.model.ScheduledGiftEvent;
import vanh.demo15.repository.ScheduledGiftEventRepository;
import vanh.demo15.service.GiftService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gift")
public class GiftController {
    private final GiftService giftService;
    private final ScheduledGiftEventRepository scheduledEventRepository;

    @PostMapping("/claim")
    public ResponseEntity<String> claimGift(@RequestBody GiftClaimRequest giftClaimRequest) {
        giftService.processRequest(giftClaimRequest);
        return ResponseEntity.accepted().body("Request received and is being processed");
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmGift(@RequestParam String requestId, @RequestParam String otp) {
        boolean confirmed = giftService.confirmGift(requestId, otp);
        if (confirmed) {
            return ResponseEntity.ok("Gift confirmed successfully");
        }
        return ResponseEntity.badRequest().body("Invalid OTP or request expired");
    }

    @PostMapping("/schedule-event")
    public ResponseEntity<String> scheduleEvent(@RequestBody ScheduledGiftEvent event) {
        event.setActive(false);
        scheduledEventRepository.save(event);
        return ResponseEntity.ok("Event scheduled successfully");
    }

    @GetMapping("/check-event/{giftId}")
    public ResponseEntity<?> checkEventStatus(@PathVariable String giftId) {
        Optional<ScheduledGiftEvent> event = scheduledEventRepository.findByGiftId(giftId);
        if (event.isPresent()) {
            return ResponseEntity.ok(event.get());
        }
        return ResponseEntity.notFound().build();
    }
}