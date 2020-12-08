package com.yx.service;

import com.yx.page.BaseResponse;
import com.yx.page.PageQueryResponse;
import com.yx.po.Product;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/12/2 0002 16:30
 */
public interface ProductService {

    Product getProduct(long id);

    int updateProduct(Product record);

    int deleteProduct(long id);

    int addProduct(Product record);

    BaseResponse<PageQueryResponse> getProductList(int pageNo, int pageSize);
}
