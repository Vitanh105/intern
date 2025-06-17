package vanh.urboxapiintegrate.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class BrandResponse {
    private int done;
    private String msg;
    private String microtime;
    private Integer status;
    private BrandData data;

    @Data
    public static class BrandData {
        private List<BrandItem> items;
        private String textTitle;
        private Integer brand_count;
        private Integer totalPage;

        @Data
        public static class BrandItem {
            private String banner;
            private String description;
            private String images;
            private String id;
            private String cat_id;
            private String title;
            private Integer gift_count;
            private String cat_title;
            private String parent_cat_id;
        }
    }
}