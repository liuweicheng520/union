package com.inquiry.controller;

import com.inquiry.core.ApiResponse;
import com.inquiry.model.BaseUser;
import com.inquiry.model.Inquiry;
import com.inquiry.model.InquiryRecord;
import com.inquiry.service.InquiryRecordService;
import com.inquiry.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
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
    @Autowired
    InquiryService inquiryService;

    /**
     * 问卷调查详情
     */
    @RequestMapping("/user/inquiryRecord/list.do")
    public String inquiryRecordList(Integer id, Model model) {
        Map<String, Object> where = new HashMap<>();
        where.put("inquiryId", id);
        List<InquiryRecord> inquiryRecordList = inquiryRecordService.list(where);
        model.addAttribute("list", inquiryRecordList);

        return "user_my_list_record";
    }

    @RequestMapping("/user/inquiryRecord/add.do")
    @ResponseBody
    public ApiResponse add(@RequestBody InquiryRecord inquiryRecord, HttpSession httpSession) {
        BaseUser baseUser = (BaseUser) httpSession.getAttribute("user");
        long time = System.currentTimeMillis() / 1000;

        Map<String, Object> where = new HashMap<>();
        where.put("inquiryId", inquiryRecord.getInquiryId());
        where.put("userId", baseUser.getId());

        Inquiry inquiry = inquiryService.findById(inquiryRecord.getInquiryId());

        if (inquiryRecordService.findByMap(where) != null) {
            return ApiResponse.getDefaultResponse("已经参与过了");
        }
        inquiryRecord.setCreateTime(time);
        inquiryRecord.setEndTime(time);
        inquiryRecord.setTopicName(inquiry.getTopicName());
        inquiryRecord.setInquiryName(inquiry.getInquiryName());
        inquiryRecord.setUserId(baseUser.getId());
        inquiryRecord.setUserName(baseUser.getNickname());
        inquiryRecordService.save(inquiryRecord);
        return ApiResponse.getDefaultResponse("参与成功");
    }


}
