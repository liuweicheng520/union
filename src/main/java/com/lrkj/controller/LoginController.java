package com.lrkj.controller;

import com.lrkj.model.BaseUser;
import com.lrkj.service.BaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    BaseUserService baseUserService;

    @PostMapping("/login.do")
    public String login(String username, String password, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        map.put("account", username);
        map.put("password", password);

        BaseUser baseUser = baseUserService.findByMap(map);

        if (baseUser == null) {
            return "login";
        } else {
            session.setAttribute("user", baseUser);
            if(baseUser.getRoleid() == 4){
             return "detail_admin";
            }
            return "detail_user";
        }
    }
}
