package vanh.urboxapiintegrate.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class GetByTransactionResponse {
    private int done;
    private String msg;
    private String microtime;
    private int status;
    private GetByTransactionData data;

    @Data
    public static class GetByTransactionData {
        private String id;
        private String campaign_code;
        private String linkCart;
        private String linkCombo;
        private String money_ship;
        private String money_total;
        private String created_timestamp;
        private String created;
        private String pay_time;
        private String pay_status;
        private int pay_status_code;
        private boolean customer;
        private GetByTransactionReceiver receiver;
        private int item_quantity;
        private List<GetByTransactionDetail> detail;

        @lombok.Data
        public static class GetByTransactionReceiver {
            private String email;
            private String phone;
            private String address;
        }

        @Data
        public static class GetByTransactionDetail {
            private String using_time;
            private String finish_time;
            private String link;
            private String delivery;
            private int deliveryCode;
            private String delivery_tracking;
            private String estimateDelivery;
            private String delivery_note;
            private String code;
            private List<Object> topup;
            private String gift_id;
            private String priceId;
            private String created_timestamp;
            private String expired;
            private String id;
            private String code_display;
            private int code_display_type;
        }
    }
}