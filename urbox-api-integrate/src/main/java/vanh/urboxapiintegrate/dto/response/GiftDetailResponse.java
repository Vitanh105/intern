package vanh.urboxapiintegrate.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class GiftDetailResponse {
    private int done;
    private String msg;
    private String microtime;
    private Integer status;
    private GiftDetailData data;

    @Data
    public static class GiftDetailData {
        private String id;
        private String brand;
        private String brand_id;
        private String code_display;
        private Integer code_display_type;
        private String cat_id;
        private String gift_id;
        private String title;
        private String type;
        private String price;
        private String point;
        private String valuex;
        private String weight;
        private String justGetOrder;
        private String view;
        private String quantity;
        private Integer usage_check;
        private String image;

        private Object images;
        private Object images_rectangle;

        private String expire_duration;
        private Integer price_promo;
        private Integer start_promo;
        private Integer end_promo;
        private Integer is_promo;
        private String is_unfix;
        private String parent_cat_id;
        private String brand_online;
        private String brandImage;
        private String content;
        private String note;
        private List<Office> office;

        @Data
        public static class Office {
            private String address;
            private String address_en;
            private String city_id;
            private String latitude;
            private String longitude;
            private String brand_id;
            private String district_id;
            private String ward_id;
            private String code;
            private String number;
            private String phone;
            private String geo;
            private String isApply;
            private String id;
            private String title_city;
        }
    }
}