package com.study.service.solo.impl;

import com.study.entity.bo.HeadLine;
import com.study.entity.dto.Result;
import com.study.service.solo.HeadLineService;
import org.simpleframework.core.annotation.Service;

import java.util.List;

/**
 * @author jianghui
 * @date 2020-11-25 14:30
 */
@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Override
    public Result<Boolean> addHeadLine(HeadLine headLine) {
        return null;
    }

    @Override
    public Result<Boolean> removeHeadLine(int headLineId) {
        return null;
    }

    @Override
    public Result<Boolean> modifyHeadLine(HeadLine headLine) {
        return null;
    }

    @Override
    public Result<HeadLine> queryHeadLineById(int headLineId) {
        return null;
    }

    @Override
    public Result<List<HeadLine>> queryAllHeadLine(HeadLine headLine, int pageIndex, int pageSize) {
        return null;
    }
}
