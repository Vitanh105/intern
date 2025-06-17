package vanh.urboxapiintegrate.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vanh.urboxapiintegrate.dto.request.CartPayVoucherRequest;
import vanh.urboxapiintegrate.dto.response.CartPayVoucherResponse;

@Service
public class CartPayVoucherService extends BaseUrboxService {

    private final SignatureService signatureService;

    @Value("${urbox.private.key}")
    private String privateKey;

    @Value("${urbox.campaign.code}")
    private String campaignCode;

    public CartPayVoucherService(RestTemplate restTemplate, SignatureService signatureService) {
        super(restTemplate);
        this.signatureService = signatureService;
    }

    public CartPayVoucherResponse createVoucherOrder(CartPayVoucherRequest request) throws Exception {
        request.setApp_id(appId);
        request.setApp_secret(appSecret);
        request.setCampaign_code(campaignCode);

        String signature = signatureService.generateRSASignature(request, privateKey);
        System.out.println("Final Signature: " + signature);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Signature", signature);

        HttpEntity<CartPayVoucherRequest> entity = new HttpEntity<>(request, headers);
        return restTemplate.exchange(
                urboxApiUrl + "/2.0/cart/cartPayVoucher",
                HttpMethod.POST,
                entity,
                CartPayVoucherResponse.class
        ).getBody();
    }
}