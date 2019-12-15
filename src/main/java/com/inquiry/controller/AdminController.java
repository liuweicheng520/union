package com.inquiry.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inquiry.core.ApiResponse;
import com.inquiry.model.BaseUser;
import com.inquiry.model.Inquiry;
import com.inquiry.service.BaseUserService;
import com.inquiry.service.InquiryService;
import com.inquiry.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 管理员
 * @Date: 2019-12-09 14:31
 */
@SuppressWarnings("all")
@Controller
public class AdminController {
    @Autowired
    InquiryService inquiryService;
    @Autowired
    BaseUserService baseUserService;


    /**
     * 管理员问卷列表
     *
     * @return
     */
    @RequestMapping("/admin/inquiry/list.do")
    public String inquiryList(HttpSession httpSession, String inquiryName, Integer page, Model model) {
        BaseUser baseUser = (BaseUser) httpSession.getAttribute("user");
        Map<String, Object> where = new HashMap<>();
        where.put("inquiryName", inquiryName);
        page = page == null ? 1 : page;
        PageHelper.startPage(page, 5);
        List<Inquiry> inquiryList = inquiryService.list(where);
        model.addAttribute("list", new PageInfo<Inquiry>(inquiryList));
        return "admin_list";
    }

    /**
     * 审核通过
     */
    @RequestMapping("/admin/inquiry/ok.do/{id}")
    @Transactional
    public String ok(@PathVariable Long id, HttpSession httpSession) {
        BaseUser baseUser = (BaseUser) httpSession.getAttribute("user");
        if (baseUser.getRoleId() < 2) {
            return "login";
        }
        Inquiry inquiry = inquiryService.findById(id);
        inquiry.setStatus(2);
        inquiryService.update(inquiry);

        return "redirect:/admin/inquiry/list.do";
    }

    /**
     * 审核拒绝
     */
    @RequestMapping("/admin/inquiry/refuse.do/{id}")
    public String refuse(@PathVariable Long id, HttpSession httpSession) {
        BaseUser baseUser = (BaseUser) httpSession.getAttribute("user");
        if (baseUser.getRoleId() < 2) {
            return "login";
        }
        Inquiry inquiry = inquiryService.findById(id);
        inquiry.setStatus(3);
        inquiryService.update(inquiry);

        return "redirect:/admin/inquiry/list.do";
    }


    /**
     * 用户列表
     *
     * @param model
     */
    @RequestMapping("/admin/user/list.do")
    public String userList(Model model, Integer page) {
        page = page == null ? 1 : page;
        PageHelper.startPage(page, 5);
        List<BaseUser> baseUserList = baseUserService.list(null);
        model.addAttribute("list", new PageInfo<BaseUser>(baseUserList));
        return "admin_user_list";
    }

    @PostMapping("/admin/user/add.do")
    @ResponseBody
    public ApiResponse addUser(Model model, @RequestBody BaseUser baseUser) {
        Map<String, Object> map = new HashMap<>();
        map.put("account", baseUser.getAccount());

        BaseUser baseUser1 = baseUserService.findByMap(map); //从数据库查询用户

        if (baseUser1 != null) {
            return ApiResponse.getDefaultResponse("用户名已存在");
        } else {
            baseUser.setPassword(MD5Utils.md5(baseUser.getPassword()));
            baseUser.setRoleId(2);
            baseUserService.save(baseUser);
        }
        return ApiResponse.getDefaultResponse("添加成功");

    }
}
