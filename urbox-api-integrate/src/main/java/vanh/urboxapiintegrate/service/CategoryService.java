package vanh.urboxapiintegrate.service;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import vanh.urboxapiintegrate.dto.response.CategoryResponse;

@Service
public class CategoryService extends BaseUrboxService {

    public CategoryService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public CategoryResponse getCategories(String parentId, String lang) {
        UriComponentsBuilder builder = buildBaseUri("/2.0/category/catbyparent");

        if (parentId != null && !parentId.isEmpty()) {
            builder.queryParam("parent_id", parentId);
        }
        if (lang != null && !lang.isEmpty()) {
            builder.queryParam("lang", lang);
        }

        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                createHttpEntity(),
                CategoryResponse.class).getBody();
    }
}