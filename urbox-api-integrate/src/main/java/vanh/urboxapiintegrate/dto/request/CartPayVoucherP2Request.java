package vanh.urboxapiintegrate.dto.request;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class CartPayVoucherP2Request {
    private String app_id;
    private String app_secret;
    private String campaign_code;
    @NonNull
    private String site_user_id;
    @NonNull
    private String ttphone;
    private String ttemail;
    @NonNull
    private String ttfullname;
    @NonNull
    private String transaction_id;
    private Integer shorten;
    @NonNull
    private Integer isSendSms;
    @NonNull
    private Integer shipping_info_available = 2;
    @NonNull
    private List<DataBuyItem> dataBuy;
    @NonNull
    private Integer city_id;
    @NonNull
    private Integer district_id;
    @NonNull
    private Integer ward_id;
    @NonNull
    private String ttaddress;
    private String delivery_note;

    @Data
    public static class DataBuyItem {
        @NonNull
        private String priceId;
        @NonNull
        private Integer quantity;
    }
}
