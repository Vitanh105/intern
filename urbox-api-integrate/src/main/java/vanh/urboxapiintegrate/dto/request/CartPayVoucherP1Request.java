package vanh.urboxapiintegrate.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class CartPayVoucherP1Request {
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
    private Integer shorten;
    @NonNull
    private Integer isSendSms;
    @NonNull
    private Integer shipping_info_available = 1;
    @NonNull
    private List<DataBuyItem> dataBuy;

    @Data
    public static class DataBuyItem {
        @NonNull
        private String priceId;
        @NotNull
        @Min(value = 1)
        private Integer quantity;
    }
}
