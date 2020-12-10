package com.yx1.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yx1.mapper.ProductMapper;
import com.yx1.page.BaseResponse;
import com.yx1.page.PageQueryResponse;
import com.yx1.po.Product;
import com.yx1.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/12/2 0002 16:46
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product getProduct(long id) {
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateProduct(Product record) {
        return productMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteProduct(long id) {
        return productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public BaseResponse<PageQueryResponse> getProductList(int pageNo, int pageSize) {
        PageInfo pageInfo = PageHelper
                .startPage(pageNo, pageSize)
                .doSelectPageInfo(() -> productMapper.getProductPageList());
        return PageQueryResponse.success(pageInfo);
    }

    @Override
    public int addProduct(Product record) {
        return productMapper.insertSelective(record);
    }
}
