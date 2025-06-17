package vanh.demo15.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(indexes = @Index(name = "idx_start_time_active", columnList = "startTime, active"))
public class ScheduledGiftEvent {
    @Id
    private String eventId;
    private String giftId;
    private LocalDateTime startTime;
    private boolean active;
}