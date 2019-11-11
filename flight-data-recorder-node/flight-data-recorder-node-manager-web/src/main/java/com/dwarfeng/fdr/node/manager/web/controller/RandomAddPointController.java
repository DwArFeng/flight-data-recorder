package com.dwarfeng.fdr.node.manager.web.controller;

import com.dwarfeng.dutil.basic.str.UUIDUtil;
import com.dwarfeng.fdr.node.manager.web.bean.entity.PointImpl;
import com.dwarfeng.fdr.node.manager.web.bean.key.UuidKeyImpl;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
@Controller
public class RandomAddPointController {

    @Autowired
    PointMaintainService pointMaintainService;

    @RequestMapping("random-add-point")
    public String randomAddPoint() throws ServiceException {
        Point point = new PointImpl(
                UuidKeyImpl.of(UUIDUtil.toDenseString(UUID.randomUUID())),
                "test-point",
                "String",
                true,
                "这是用于测试的数据点"
        );
        pointMaintainService.add(point);
        return "success";
    }
}
