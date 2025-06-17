package vanh.urboxapiintegrate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vanh.urboxapiintegrate.dto.request.CartPayVoucherP1Request;
import vanh.urboxapiintegrate.dto.request.CartPayVoucherP2Request;
import vanh.urboxapiintegrate.dto.request.CartPayVoucherRequest;
import vanh.urboxapiintegrate.dto.response.*;
import vanh.urboxapiintegrate.service.CartPayVoucherP1Service;
import vanh.urboxapiintegrate.service.CartPayVoucherP2Service;
import vanh.urboxapiintegrate.service.CartPayVoucherService;
import vanh.urboxapiintegrate.service.UrboxService;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UrboxController {

    private final UrboxService urboxService;
    private final CartPayVoucherService cartPayVoucherService;
    private final CartPayVoucherP1Service cartPayVoucherP1Service;
    private final CartPayVoucherP2Service cartPayVoucherP2Service;

    public UrboxController(UrboxService urboxService,
                           CartPayVoucherService cartPayVoucherService,
                           CartPayVoucherP1Service cartPayVoucherP1Service,
                           CartPayVoucherP2Service cartPayVoucherP2Service) {
        this.urboxService = urboxService;
        this.cartPayVoucherService = cartPayVoucherService;
        this.cartPayVoucherP1Service = cartPayVoucherP1Service;
        this.cartPayVoucherP2Service = cartPayVoucherP2Service;
    }

//    API Lấy danh sách thương hiệu
    @GetMapping("/brands")
    public BrandResponse getBrands(
            @RequestParam(required = false) String catId,
            @RequestParam(required = false) Integer pageNo,
            @RequestParam(required = false) Integer perPage) {
        return urboxService.getBrands(catId, pageNo, perPage);
    }

//    API Lấy danh sách danh mục
    @GetMapping("/categories")
    public CategoryResponse getCategories(
            @RequestParam(required = false) String parentId,
            @RequestParam(required = false) String lang) {
        return urboxService.getCategories(parentId, lang);
    }

//    API Lấy danh sách quà tặng từ kho quà UrBox
    @GetMapping("/gifts")
    public GiftResponse getGifts(
            @RequestParam(required = false) Integer catId,
            @RequestParam(required = false) Integer brandId,
            @RequestParam(required = false) String field,
            @RequestParam(required = false) String lang,
            @RequestParam(required = false) Integer stock,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer perPage,
            @RequestParam(required = false) Integer pageNo) {

        return urboxService.getGifts(catId, brandId, field, lang, stock, title, perPage, pageNo);
    }

//    API Lấy chi tiết 1 quà tặng
    @GetMapping("/gifts/{id}")
    public GiftDetailResponse getGiftDetail(
            @PathVariable String id,
            @RequestParam(required = false) String lang) {
        return urboxService.getGiftDetail(id, lang);
    }

//    API Tạo yêu cầu đổi quà đến UrBox - Quà eVoucher
    @PostMapping("/cart/evoucher")
    public ResponseEntity<?> createVoucherOrder(@RequestBody CartPayVoucherRequest request) {
        try {
            CartPayVoucherResponse response = cartPayVoucherService.createVoucherOrder(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    Map.of("error", "Lỗi khi tạo đơn đổi quà", "details", e.getMessage())
            );
        }
    }

//    API Tạo yêu cầu đổi quà đến UrBox - Quà vật lý - Cách 1: Đối tác gửi yêu cầu đặt hàng cho UrBox
    @PostMapping("/cart/voucher-v1")
    public ResponseEntity<?> createVoucherOrder(@RequestBody CartPayVoucherP1Request request) {
        try {
            CartPayVoucherP1Response response = cartPayVoucherP1Service.createVoucherOrder(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    Map.of("error", "Lỗi khi tạo đơn đổi quà", "details", e.getMessage())
            );
        }
    }

//    API Tạo yêu cầu đổi quà tới UrBox - Quà vật lý - Cách 2: Đối tác gửi thông tin giao nhận cho UrBox
    @PostMapping("/cart/voucher-v2")
    public ResponseEntity<?> createVoucherOrder(@RequestBody CartPayVoucherP2Request request) {
        try {
            CartPayVoucherP2Response response = cartPayVoucherP2Service.createVoucherOrder(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    Map.of("error", "Lỗi khi tạo đơn đổi quà", "details", e.getMessage())
            );
        }
    }

//    API Tra cứu theo mã user và thời gian
    @GetMapping("/cart/getlist")
    public CartGetListResponse getCartList(
            @RequestParam String site_user_id,
            @RequestParam(required = false) String campaign_code,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false)  String transaction_id) {
        return urboxService.getCartGetList(site_user_id, campaign_code, startDate, endDate, transaction_id);
    }

//    API Tra cứu theo chi tiết đơn hàng
    @GetMapping("/cart/getByTransaction")
    public GetByTransactionResponse getByTransaction(@RequestParam String transaction_id) {
        return urboxService.getByTransaction(transaction_id);
    }
}