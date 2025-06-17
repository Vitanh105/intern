package vanh.urboxapiintegrate.service;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import vanh.urboxapiintegrate.dto.response.GiftResponse;

@Service
public class GiftService extends BaseUrboxService {

    public GiftService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public GiftResponse getGifts(Integer catId, Integer brandId, String field,
                                 String lang, Integer stock, String title,
                                 Integer perPage, Integer pageNo) {
        UriComponentsBuilder builder = buildBaseUri("/4.0/gift/lists");

        if (catId != null) {
            builder.queryParam("cat_id", catId);
        }
        if (brandId != null) {
            builder.queryParam("brand_id", brandId);
        }
        if (field != null && !field.isEmpty()) {
            builder.queryParam("field", field);
        }
        if (lang != null && !lang.isEmpty()) {
            builder.queryParam("lang", lang);
        }
        if (stock != null) {
            builder.queryParam("stock", stock);
        }
        if (title != null && !title.isEmpty()) {
            builder.queryParam("title", title);
        }
        if (perPage != null) {
            builder.queryParam("per_page", perPage);
        }
        if (pageNo != null) {
            builder.queryParam("page_no", pageNo);
        }

        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                createHttpEntity(),
                GiftResponse.class).getBody();
    }
}