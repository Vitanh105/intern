package vanh.urboxapiintegrate.service;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import vanh.urboxapiintegrate.dto.response.BrandResponse;

@Service
public class BrandService extends BaseUrboxService {

    public BrandService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public BrandResponse getBrands(String catId, Integer pageNo, Integer perPage) {
        UriComponentsBuilder builder = buildBaseUri("/4.0/gift/brand");

        if (catId != null && !catId.isEmpty()) {
            builder.queryParam("cat_id", catId);
        }
        if (pageNo != null) {
            builder.queryParam("page_no", pageNo);
        }
        if (perPage != null) {
            builder.queryParam("per_page", perPage);
        }

        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                createHttpEntity(),
                BrandResponse.class).getBody();
    }
}