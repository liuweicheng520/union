package com.lrkj.controller;

import com.lrkj.model.BaseUser;
import com.lrkj.service.BookService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class PageController {

    @Autowired
    BookService bookService;

    @RequestMapping("/login")
    public String index(){
        return "login";
    }


    @RequestMapping("/admin/addBook")
    public String addBook(HttpSession httpSession, Model model){
        BaseUser baseUser = (BaseUser) httpSession.getAttribute("user");
        if(baseUser == null || baseUser.getRoleid() != 4){
            return "login";
        }
        model.addAttribute("list",bookService.list(null));
        return "admin_addbook";
    }

    @RequestMapping("/user/bookList.do")
    public String bookList( Model model){
        model.addAttribute("list",bookService.list(null));
        return "user_booklist";
    }


    @RequestMapping("/home.do")
    public String home(HttpSession httpSession){
        BaseUser baseUser = (BaseUser) httpSession.getAttribute("user");
        if(baseUser == null){
            return "login";
        }

        if(baseUser.getRoleid() == 4){
            return "detail_admin";
        }else{
            return "detail_user";
        }
    }
}
