package vanh.urboxapiintegrate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vanh.urboxapiintegrate.dto.request.CartPayVoucherP1Request;
import vanh.urboxapiintegrate.dto.request.CartPayVoucherP2Request;
import vanh.urboxapiintegrate.dto.request.CartPayVoucherRequest;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

@Service
public class SignatureService {
    @Value("${urbox.app.id}")
    protected String appId;

    @Value("${urbox.app.secret}")
    protected String appSecret;

    @Value("${urbox.campaign.code}")
    private String campaignCode;

    private final ObjectMapper objectMapper;

    public SignatureService() {
        this.objectMapper = new ObjectMapper();

        this.objectMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
    }

    public String generateRSASignature(CartPayVoucherRequest request, String privateKeyPEM) throws Exception {
        Map<String, Object> sortedParams = new TreeMap<>();
        sortedParams.put("app_id", appId);
        sortedParams.put("app_secret", appSecret);
        sortedParams.put("campaign_code", campaignCode);
        if (request.getDataBuy() != null) {
            sortedParams.put("dataBuy", request.getDataBuy());
        }
        if (request.getIsSendSms() != null) {
            sortedParams.put("isSendSms", request.getIsSendSms());
        }
        if (request.getSite_user_id() != null) {
            sortedParams.put("site_user_id", request.getSite_user_id());
        }
        if (request.getTransaction_id() != null) {
            sortedParams.put("transaction_id", request.getTransaction_id());
        }

        String jsonData = convertToJson(sortedParams);
        System.out.println("JSON to sign: " + jsonData);

        PrivateKey privateKey = parsePrivateKey(privateKeyPEM);

        String signature = signWithRSA(jsonData, privateKey);
        System.out.println("Generated Signature: " + signature);

        return signature;
    }

    public String generateRSASignature(CartPayVoucherP1Request request, String privateKeyPEM) throws Exception {
        Map<String, Object> sortedParams = new TreeMap<>();
        sortedParams.put("app_id", appId);
        sortedParams.put("app_secret", appSecret);
        sortedParams.put("campaign_code", campaignCode);
        if (request.getDataBuy() != null) {
            sortedParams.put("dataBuy", request.getDataBuy());
        }
        if (request.getIsSendSms() != null) {
            sortedParams.put("isSendSms", request.getIsSendSms());
        }
        if (request.getSite_user_id() != null) {
            sortedParams.put("site_user_id", request.getSite_user_id());
        }
        if (request.getTransaction_id() != null) {
            sortedParams.put("transaction_id", request.getTransaction_id());
        }

        String jsonData = convertToJson(sortedParams);
        System.out.println("JSON to sign: " + jsonData);

        PrivateKey privateKey = parsePrivateKey(privateKeyPEM);

        String signature = signWithRSA(jsonData, privateKey);
        System.out.println("Generated Signature: " + signature);

        return signature;
    }

    public String generateRSASignature(CartPayVoucherP2Request request, String privateKeyPEM) throws Exception {
        Map<String, Object> sortedParams = new TreeMap<>();
        sortedParams.put("app_id", appId);
        sortedParams.put("app_secret", appSecret);
        sortedParams.put("campaign_code", campaignCode);
        if (request.getDataBuy() != null) {
            sortedParams.put("dataBuy", request.getDataBuy());
        }
        if (request.getIsSendSms() != null) {
            sortedParams.put("isSendSms", request.getIsSendSms());
        }
        if (request.getSite_user_id() != null) {
            sortedParams.put("site_user_id", request.getSite_user_id());
        }
        if (request.getTransaction_id() != null) {
            sortedParams.put("transaction_id", request.getTransaction_id());
        }

        String jsonData = convertToJson(sortedParams);
        System.out.println("JSON to sign: " + jsonData);

        PrivateKey privateKey = parsePrivateKey(privateKeyPEM);

        String signature = signWithRSA(jsonData, privateKey);
        System.out.println("Generated Signature: " + signature);

        return signature;
    }

    private String convertToJson(Map<String, Object> data) throws JsonProcessingException {
        return objectMapper.writeValueAsString(data);
    }

    private PrivateKey parsePrivateKey(String privateKeyPEM) throws Exception {
        privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encoded));
    }

    private String signWithRSA(String data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes(StandardCharsets.UTF_8));
        byte[] signatureBytes = signature.sign();
        return Base64.getEncoder().encodeToString(signatureBytes);
    }
}