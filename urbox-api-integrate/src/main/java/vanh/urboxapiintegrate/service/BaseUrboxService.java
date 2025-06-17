package vanh.urboxapiintegrate.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public abstract class BaseUrboxService  {

    @Value("${urbox.api.url}")
    protected String urboxApiUrl;

    @Value("${urbox.app.id}")
    protected String appId;

    @Value("${urbox.app.secret}")
    protected String appSecret;

    protected final RestTemplate restTemplate;

    public BaseUrboxService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    protected HttpEntity<?> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(headers);
    }

    protected UriComponentsBuilder buildBaseUri(String endpoint) {
        return UriComponentsBuilder.fromHttpUrl(urboxApiUrl + endpoint)
                .queryParam("app_id", appId)
                .queryParam("app_secret", appSecret);
    }
}