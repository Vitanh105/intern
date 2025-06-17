package vanh.urboxapiintegrate.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CartPayVoucherResponse {
    private int done;
    private String msg;
    private String microtime;
    private Integer status;
    private CartPayVoucherData data;

    @Data
    public static class CartPayVoucherData {
        private Integer pay;
        private String transaction_id;
        private String cart_created;
        private String linkCart;
        private String linkCombo;
        private String linkShippingInfo;
        private Cart cart;
    }

    @Data
    public static class Cart {
        private String id;
        private String cartNo;
        private Integer money_total;
        private Integer money_ship;
        private List<String> link_gift;
        private List<CodeLinkGift> code_link_gift;
    }

    @Data
    public static class CodeLinkGift {
        private String cart_detail_id;
        private String code_display;
        private Integer code_display_type;
        private String link;
        private String code;
        private Integer card_id;
        private String pin;
        private String serial;
        private String priceId;
        private String gift_id;
        private String token;
        private String expired;
        private Long expired_time;
        private String code_image;
        private String estimateDelivery;
        private String ttemail;
        private String ttphone;
        private String receive_code;
        private Integer city_id;
        private Integer district_id;
        private Integer ward_id;
        private String delivery_note;
        private String ttaddress;
        private Integer deliveryCode;
        private Integer type;
        private Integer urcard_id;
        private Integer price;
    }
}