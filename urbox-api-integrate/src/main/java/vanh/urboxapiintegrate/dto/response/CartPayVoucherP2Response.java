package vanh.urboxapiintegrate.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CartPayVoucherP2Response {
    private int done;
    private String msg;
    private String microtime;
    private int status;
    private CartPayVoucherP2Data Data;

    @Data
    public static class CartPayVoucherP2Data {
        private int pay;
        private String transaction_id;
        private String campaign_code;
        private String linkCart;
        private String linkCombo;
        private String linkShippingInfo;
        private String cart_created;
        private CartPayVoucherP2Cart Cart;

        @Data
        public static class CartPayVoucherP2Cart {
            private String id;
            private String cartNo;
            private String money_total;
            private String money_ship;
            private List<String> link_gift;
            private List<CartPayVoucherP2CodeLinkGift> code_link_gift;

            @Data
            public static class CartPayVoucherP2CodeLinkGift {
                private String cart_detail_id;
                private String code_display;
                private int code_display_type;
                private String link;
                private String code;
                private int card_id;
                private String pin;
                private String serial;
                private String priceId;
                private Integer price;
                private String token;
                private String expired;
                private String code_image;
                private String estimateDelivery;
                private String ttemail;
                private String ttphone;
                private Integer city_id;
                private Integer district_id;
                private Integer ward_id;
                private String ttaddress;
                private String delivery_note;
            }
        }
    }
}