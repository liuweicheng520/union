package com.inquiry.controller;

import com.inquiry.model.BaseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PageController {
    ;

    @RequestMapping("/login")
    public String index() {
        return "login";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }


    /**
     * 首页
     *
     * @param httpSession
     * @return
     */
    @RequestMapping("/home.do")
    public String home(HttpSession httpSession) {
        BaseUser baseUser = (BaseUser) httpSession.getAttribute("user");
        if (baseUser == null) {
            return "login";
        }

        if (baseUser.getRoleId() >= 2) {
            return "detail_admin";
        } else {
            return "detail_user";
        }
    }
}
