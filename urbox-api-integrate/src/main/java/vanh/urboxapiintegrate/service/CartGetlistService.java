package vanh.urboxapiintegrate.service;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import vanh.urboxapiintegrate.dto.response.CartGetListResponse;

@Service
public class CartGetlistService extends BaseUrboxService {
    public CartGetlistService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public CartGetListResponse getCartList(String site_user_id, String campaign_code, String startDate,
                                           String endDate, String transaction_id) {
        UriComponentsBuilder builder = buildBaseUri("/2.0/cart/getlist");

        builder.queryParam("site_user_id", site_user_id);
        if (campaign_code != null) {
            builder.queryParam("campaign_code", campaign_code);
        }
        if (campaign_code != null) {
            builder.queryParam("startDate", startDate);
        }
        if (campaign_code != null) {
            builder.queryParam("endDate", endDate);
        }
        if (campaign_code != null) {
            builder.queryParam("transaction_id", transaction_id);
        }

        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                createHttpEntity(),
                CartGetListResponse.class).getBody();
    }
}
