package com.yx1.page;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

@Data
public class PageQueryResponse<T> {

    private int pageNo;
    private int pageSize;
    /** 总记录数 */
    private long recordsTotal;
    /** 总页数 */
    private int total;
    private List<T> rows;

    public static BaseResponse<PageQueryResponse> success(PageInfo pageInfo) {
        BaseResponse<PageQueryResponse> baseResponse = new BaseResponse<>();
        baseResponse.setMessage(BaseResponse.SUCCESS_MESSAGE);

        PageQueryResponse<List> pageQueryResponse = new PageQueryResponse();
        pageQueryResponse.setPageNo(pageInfo.getPageNum());
        pageQueryResponse.setPageSize(pageInfo.getPageSize());
        pageQueryResponse.setRecordsTotal(pageInfo.getTotal());
        pageQueryResponse.setTotal(pageInfo.getPages());
        pageQueryResponse.setRows(pageInfo.getList());

        baseResponse.setData(pageQueryResponse);
        return baseResponse;
    }

    public static BaseResponse<PageQueryResponse> success(Page pageInfo, List rows) {
        BaseResponse<PageQueryResponse> baseResponse = new BaseResponse<>();
        baseResponse.setMessage(BaseResponse.SUCCESS_MESSAGE);

        PageQueryResponse<List> pageQueryResponse = new PageQueryResponse();
        pageQueryResponse.setPageNo(pageInfo.getPageNum());
        pageQueryResponse.setPageSize(pageInfo.getPageSize());
        pageQueryResponse.setRecordsTotal(pageInfo.getTotal());
        pageQueryResponse.setTotal(pageInfo.getPages());
        pageQueryResponse.setRows(rows);

        baseResponse.setData(pageQueryResponse);
        return baseResponse;
    }

    public static BaseResponse<PageQueryResponse> success(int pageNo, int pageSize, long totalRecord, int totalPage, List rows) {
        BaseResponse<PageQueryResponse> baseResponse = new BaseResponse<>();
        baseResponse.setMessage(BaseResponse.SUCCESS_MESSAGE);

        PageQueryResponse<List> pageQueryResponse = new PageQueryResponse();
        pageQueryResponse.setPageNo(pageNo);
        pageQueryResponse.setPageSize(pageSize);
        pageQueryResponse.setRecordsTotal(totalRecord);
        pageQueryResponse.setTotal(totalPage);
        pageQueryResponse.setRows(rows);

        baseResponse.setData(pageQueryResponse);
        return baseResponse;
    }

}
