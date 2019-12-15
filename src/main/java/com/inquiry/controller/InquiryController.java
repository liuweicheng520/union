package com.inquiry.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inquiry.core.ApiResponse;
import com.inquiry.model.BaseUser;
import com.inquiry.model.Inquiry;
import com.inquiry.model.InquiryRecord;
import com.inquiry.service.InquiryRecordService;
import com.inquiry.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Date: 2019-12-09 14:10
 */
@SuppressWarnings("all")
@Controller
public class InquiryController {
    @Autowired
    InquiryService inquiryService;
    @Autowired
    InquiryRecordService inquiryRecordService;

    /**
     * 问卷大厅
     */
    @RequestMapping("/user/inquiry/list.do")
    public String inquiryList(HttpSession httpSession, String inquiryName, Integer page, Model model) {
        BaseUser baseUser = (BaseUser) httpSession.getAttribute("user");
        Map<String, Object> where = new HashMap<>();
        where.put("status", 2);
        where.put("inquiryName", inquiryName);

        page = page == null ? 1 : page;

        PageHelper.startPage(page, 5);
        List<Inquiry> inquiryList = inquiryService.list(where);
        where.put("userId", baseUser.getId());
        //判断用户是否有没有参加过此问卷
        for (Inquiry inquiry : inquiryList) {
            where.put("inquiryId", inquiry.getId());
            InquiryRecord inquiryRecord = inquiryRecordService.findByMap(where);
            if (inquiryRecord == null) {
                //未参加状态
                inquiry.setUserStatus(2);
            } else {
                //已参加状态
                inquiry.setUserStatus(3);
            }
        }

        model.addAttribute("list", new PageInfo<Inquiry>(inquiryList));

        return "user_list";
    }

    /**
     * 问卷大厅
     */
    @RequestMapping("/user/inquiry/myList.do")
    public String myInquiryList(HttpSession httpSession, String inquiryName, Integer page, Model model) {
        BaseUser baseUser = (BaseUser) httpSession.getAttribute("user");
        Map<String, Object> where = new HashMap<>();
        where.put("inquiryName", inquiryName);

        page = page == null ? 1 : page;

        PageHelper.startPage(page, 5);
        List<Inquiry> inquiryList = inquiryService.list(where);

        model.addAttribute("list", new PageInfo<Inquiry>(inquiryList));

        return "user_my_list";
    }


    @PostMapping("/user/inquiry/add.do")
    @ResponseBody
    /**
     * 添加问卷
     */
    public ApiResponse add(@RequestBody Inquiry inquiry, HttpSession httpSession) {
        Map<String, Object> where = new HashMap<>();
        where.put("inquiryName", inquiry.getInquiryName());
        Inquiry inquiry1 = inquiryService.findByMap(where);

        BaseUser baseUser = (BaseUser) httpSession.getAttribute("user");

        if (inquiry1 != null) {
            return ApiResponse.getDefaultResponse("名字已存在");
        }
        inquiry.setStatus(1);
        inquiry.setUserId(baseUser.getId());
        inquiry.setUserName(baseUser.getNickname());
        System.out.println(inquiry);
        inquiryService.save(inquiry);
        return ApiResponse.getDefaultResponse("添加成功");
    }


    @RequestMapping("/user/inquiry/details.do")
    @ResponseBody
    public ApiResponse details(Long id) {
        return ApiResponse.getDefaultResponse(inquiryService.findById(id));
    }


}
