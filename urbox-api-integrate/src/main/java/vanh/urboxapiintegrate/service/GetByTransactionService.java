package vanh.urboxapiintegrate.service;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import vanh.urboxapiintegrate.dto.response.GetByTransactionResponse;

@Service
public class GetByTransactionService extends BaseUrboxService {
    public GetByTransactionService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public GetByTransactionResponse getByTransaction(String transaction_id) {
        UriComponentsBuilder builder = buildBaseUri("/2.0/cart/getByTransaction");

        builder.queryParam("transaction_id", transaction_id);

        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                createHttpEntity(),
                GetByTransactionResponse.class).getBody();
    }
}
