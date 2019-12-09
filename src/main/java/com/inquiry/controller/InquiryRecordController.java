package com.inquiry.controller;

import com.inquiry.model.InquiryRecord;
import com.inquiry.service.InquiryRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 调查记录
 * @Auther: liuweicheng
 * @Date: 2019-12-09 14:56
 */
@SuppressWarnings("all")
@Controller
public class InquiryRecordController {
    @Autowired
    InquiryRecordService inquiryRecordService;


    /**
     * 问卷调查详情
     */
    @RequestMapping("/user/inquiryRecord/list.do")
    public String inquiryRecordList(Integer inquiryId, Model model) {
        Map<String, Object> where = new HashMap<>();
        where.put("inquiryId", inquiryId);
        List<InquiryRecord> inquiryRecordList = new ArrayList<>();

        model.addAttribute("list", inquiryRecordList);

        return "inquiryRecord_list";
    }



}
