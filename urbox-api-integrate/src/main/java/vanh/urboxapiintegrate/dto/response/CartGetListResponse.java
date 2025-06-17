package vanh.urboxapiintegrate.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CartGetListResponse {
    private int done;
    private String msg;
    private String microtime;
    private int status;
    private List<CartGetListData> data;
    private int totalPage;

    @Data
    public static class CartGetListData {
        private String id;
        private String site_id;
        private String linkCart;
        private String linkCombo;
        private String transaction_id;
        private String campaign_code;
        private String created;
        private String created_timestamp;
        private String pay_time;
        private String pay_status;
        private int pay_status_code;
        private List<CartGetListDetail> detail;
        private int item_quantity;

        @Data
        public static class CartGetListDetail {
            private String id;
            private String app_id;
            private String justGetOrder;
            private String link;
            private String type;
            private String valuex;
            private String usage_status;
            private int usage_status_code;
            private String using_time;
            private String gift_id;
            private String gift_detail_id;
            private String delivery;
            private int deliveryCode;
            private String code_image;
            private String delivery_required;
            private List<Object> topup;
            private String gift_title;
            private int usage_check;
            private String expired;
            private String code;
            private String serial;
            private String code_display;
            private int code_display_type;
            private String gift_detail_title;
            private String price;
            private String image;
            private String images_rectangle;
            private String brandId;
            private String brandTitle;
            private String brandImage;
        }
    }
}