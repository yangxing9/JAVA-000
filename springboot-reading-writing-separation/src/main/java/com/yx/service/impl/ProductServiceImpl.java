package com.yx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yx.annotation.ReadOnlyConnection;
import com.yx.mapper.ProductMapper;
import com.yx.page.BaseResponse;
import com.yx.page.PageQueryResponse;
import com.yx.po.Product;
import com.yx.service.ProductService;
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
        log.info("未加注解，走的master");
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
    @ReadOnlyConnection
    public BaseResponse<PageQueryResponse> getProductList(int pageNo, int pageSize) {
        log.info("加注解，走的slave");
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
