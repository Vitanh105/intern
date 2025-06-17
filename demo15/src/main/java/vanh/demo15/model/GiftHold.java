package vanh.demo15.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class GiftHold {
    @Id
    private String requestId;
    private String giftId;
    private String otp;
    private LocalDateTime expireTime;
    private boolean confirmed;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}