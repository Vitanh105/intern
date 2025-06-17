package vanh.urboxapiintegrate.service;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import vanh.urboxapiintegrate.dto.response.GiftDetailResponse;

@Service
public class GiftDetailService extends BaseUrboxService {

    public GiftDetailService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public GiftDetailResponse getGiftDetail(String id, String lang) {
        UriComponentsBuilder builder = buildBaseUri("/4.0/gift/detail")
                .queryParam("id", id);

        if (lang != null && !lang.isEmpty()) {
            builder.queryParam("lang", lang);
        }

        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                createHttpEntity(),
                GiftDetailResponse.class).getBody();
    }
}