package vanh.urboxapiintegrate.dto.request;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class CartPayVoucherRequest {
    private String app_id;
    private String app_secret;
    private String campaign_code;
    @NonNull
    private String site_user_id;
    @NonNull
    private String ttphone;
    private String ttemail;
    private String ttfullname;
    @NonNull
    private String transaction_id;
    private String client_purchase_order;
    private Integer shorten;
    @NonNull
    private Integer isSendSms;
    private String pin;
    @NonNull
    private List<DataBuyItem> dataBuy;

    @Data
    public static class DataBuyItem {
        @NonNull
        private String priceId;
        @NonNull
        private Integer quantity;
        private Integer amount;
    }
}