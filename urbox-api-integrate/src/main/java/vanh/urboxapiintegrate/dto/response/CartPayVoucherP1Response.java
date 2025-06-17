package vanh.urboxapiintegrate.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CartPayVoucherP1Response {
    private int done;
    private String msg;
    private String microtime;
    private int status;
    private CartPayVoucherP1Data data;

    @Data
    public static class CartPayVoucherP1Data {
        private int pay;
        private String transaction_id;
        private String linkCart;
        private String linkCombo;
        private String linkShippingInfo;
        private CartPayVoucherP1Cart cart;
    }

    @Data
    public static class CartPayVoucherP1Cart {
        private String id;
        private String cartNo;
        private String money_total;
        private String money_ship;
        private List<String> link_gift;
        private List<CartPayVoucherP1CodeLinkGift> code_link_gift;
    }

    @Data
    public static class CartPayVoucherP1CodeLinkGift {
        private String cart_detail_id;
        private String code_display;
        private int code_display_type;
        private String link;
        private String code;
        private int card_id;
        private String pin;
        private String priceId;
        private String token;
        private String expired;
        private String code_image;
        private String estimateDelivery;
        private String ttemail;
        private String ttphone;
        private Integer city_id;
        private Integer district_id;
        private Integer ward_id;
        private String delivery_note;
        private String ttaddress;
    }
}