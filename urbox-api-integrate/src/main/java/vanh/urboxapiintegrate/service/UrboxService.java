package vanh.urboxapiintegrate.service;

import org.springframework.stereotype.Service;
import vanh.urboxapiintegrate.dto.response.*;

@Service
public class UrboxService {
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final GiftService giftService;
    private final GiftDetailService giftDetailService;
    private final CartGetlistService cartGetlistService;
    private final GetByTransactionService getByTransactionService;

    public UrboxService(BrandService brandService, CategoryService categoryService, GiftService giftService,
                        GiftDetailService giftDetailService, CartGetlistService cartGetlistService, GetByTransactionService getByTransactionService) {
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.giftService = giftService;
        this.giftDetailService = giftDetailService;
        this.cartGetlistService = cartGetlistService;
        this.getByTransactionService = getByTransactionService;
    }

    public BrandResponse getBrands(String catId, Integer pageNo, Integer perPage) {
        return brandService.getBrands(catId, pageNo, perPage);
    }

    public CategoryResponse getCategories(String parentId, String lang) {
        return categoryService.getCategories(parentId, lang);
    }

    public GiftResponse getGifts(Integer catId, Integer brandId, String field,
                                 String lang, Integer stock, String title,
                                 Integer perPage, Integer pageNo) {
        return giftService.getGifts(catId, brandId, field, lang, stock,
                title, perPage, pageNo);
    }

    public GiftDetailResponse getGiftDetail(String id, String lang) {
        return giftDetailService.getGiftDetail(id, lang);
    }

    public CartGetListResponse getCartGetList(String site_user_id, String campaign_code, String startDate,
                                              String endDate, String transaction_id) {
        return cartGetlistService.getCartList(site_user_id, campaign_code, startDate, endDate, transaction_id);
    }

    public GetByTransactionResponse getByTransaction(String transaction_id) {
        return getByTransactionService.getByTransaction(transaction_id);
    }
}