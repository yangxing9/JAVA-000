package com.yx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yx.mapper.ProductMapper;
import com.yx.page.BaseResponse;
import com.yx.page.PageQueryResponse;
import com.yx.po.Product;
import com.yx.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.infra.hint.HintManager;
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
        log.info("走的slave，轮询");
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
        log.info("强制路由，走的master");
        HintManager hintManager = HintManager.getInstance() ;

        hintManager.setPrimaryRouteOnly();
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
