package vanh.urboxapiintegrate.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class CategoryResponse {
    private int done;
    private String msg;
    private String microtime;
    private Integer status;
    private List<CategoryItem> data;

    @Data
    public static class CategoryItem {
        private String images;
        private String id;
        private String title;
    }
}