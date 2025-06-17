package vanh.demo15.dto;

import lombok.Data;

@Data
public class GiftClaimRequest {
    private String requestId;
    private String giftId;
    private long timestamp;
}