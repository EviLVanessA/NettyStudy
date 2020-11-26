package com.study.service.solo;

import com.study.entity.bo.HeadLine;
import com.study.entity.dto.Result;

import java.util.List;

/**
 * @author jianghui
 * @date 2020-11-25 14:12
 */
public interface HeadLineService {
    /**
     * 新增头条
     * @param headLine 头条信息
     * @return true or false
     */
    Result<Boolean> addHeadLine(HeadLine headLine);

    /**
     *  根据Id删除头条
     * @param headLineId 头条id
     * @return true or false
     */
    Result<Boolean> removeHeadLine(int headLineId);

    /**
     * 修改头条信息
     * @param headLine 头条信息
     * @return true or false
     */
    Result<Boolean> modifyHeadLine(HeadLine headLine);

    /**
     * 根据ID查找头条信息
     * @param headLineId 头条ID
     * @return 符合查询结果的头条信息
     */
    Result<HeadLine> queryHeadLineById(int headLineId);

    /**
     * 获取全部的头条信息
     * @param headLine 查询条件
     * @param pageIndex 分页索引
     * @param pageSize  分页大小
     * @return 返回头条列表
     */
    Result<List<HeadLine>> queryAllHeadLine(HeadLine headLine,int pageIndex,int pageSize);


}
