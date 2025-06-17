package vanh.demo15.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftClaimResponse {
    private String requestId;
    private String otp;
    private String status;
    private long timestamp;
}