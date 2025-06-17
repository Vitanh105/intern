package vanh.urboxapiintegrate.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vanh.urboxapiintegrate.dto.request.CartPayVoucherP2Request;
import vanh.urboxapiintegrate.dto.response.CartPayVoucherP2Response;

@Service
public class CartPayVoucherP2Service extends BaseUrboxService {

    private final SignatureService signatureService;

    @Value("${urbox.private.key}")
    private String privateKey;

    @Value("${urbox.campaign.code}")
    private String campaignCode;

    public CartPayVoucherP2Service(RestTemplate restTemplate, SignatureService signatureService) {
        super(restTemplate);
        this.signatureService = signatureService;
    }

    public CartPayVoucherP2Response createVoucherOrder(CartPayVoucherP2Request request) throws Exception {
        request.setApp_id(appId);
        request.setApp_secret(appSecret);
        request.setCampaign_code(campaignCode);

        String signature = signatureService.generateRSASignature(request, privateKey);
        System.out.println("Final Signature: " + signature);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Signature", signature);

        HttpEntity<CartPayVoucherP2Request> entity = new HttpEntity<>(request, headers);
        return restTemplate.exchange(
                urboxApiUrl + "/2.0/cart/cartPayVoucher",
                HttpMethod.POST,
                entity,
                CartPayVoucherP2Response.class
        ).getBody();
    }
}