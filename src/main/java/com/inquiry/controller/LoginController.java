package com.inquiry.controller;

import com.inquiry.model.BaseUser;
import com.inquiry.service.BaseUserService;
import com.inquiry.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    BaseUserService baseUserService;

    /*
     *  登陆
     * @param username 用户名
     * @param password 密码
     * @param session
     * @return
     */
    @PostMapping("/login.do")
    public String login(String username, String password, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        map.put("account", username);
        map.put("password", MD5Utils.md5(password));

        BaseUser baseUser = baseUserService.findByMap(map); //从数据库查询用户

        if (baseUser == null) {
            return "login";
        } else {
            //将用户信息存入session中
            session.setAttribute("user", baseUser);
            if (baseUser.getRoleId() >= 2) {
                //判断用户角色,4.管理员跳管理系统页面,其他就跳用户界面
                return "detail_admin";
            }
            return "detail_user";
        }
    }

    @PostMapping("/register.do")
    public String login(BaseUser baseUser, HttpSession session, Model model) {

        Map<String, Object> map = new HashMap<>();
        map.put("account", baseUser.getAccount());

        BaseUser baseUser1 = baseUserService.findByMap(map); //从数据库查询用户

        if (baseUser1 != null) {
            model.addAttribute("msg", "用户名已存在！");
        } else {
            baseUser.setPassword(MD5Utils.md5(baseUser.getPassword()));
            baseUser.setRoleId(1);
            baseUserService.save(baseUser);
            model.addAttribute("msg", "注册成功！");
        }
        return "register";

    }
}
