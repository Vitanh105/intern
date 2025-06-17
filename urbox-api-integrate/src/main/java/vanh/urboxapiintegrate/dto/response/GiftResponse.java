package vanh.urboxapiintegrate.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class GiftResponse {
    private int done;
    private String msg;
    private String microtime;
    private Integer status;
    private GiftData data;

    @Data
    public static class GiftData {
        private List<GiftItem> items;
        private Integer totalPage;
        private String totalResult;
    }

    @Data
    public static class GiftItem {
        private String id;
        private String brand;
        private String brand_id;
        private String cat_id;
        private String cat_title;
        private String gift_id;
        private String title;
        private String type;
        private String price;
        private String point;
        private String view;
        private String quantity;
        private Integer stock;
        private String image;
        private Object images;
        private Object images_rectangle;
        private String expire_duration;
        private String code_display;
        private Integer code_display_type;
        private Integer price_promo;
        private Integer start_promo;
        private Integer end_promo;
        private Integer is_promo;
        private String is_unfix;
        private String brandLogoLoyalty;
        private String brandImage;
        private String brand_name;
        private String brand_online;
        private String parent_cat_id;
        private Integer usage_check;
        private String code_quantity;
    }
}