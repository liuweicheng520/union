package com.inquiry.controller;

import com.inquiry.model.BaseUser;
import com.inquiry.service.BookService;
import com.inquiry.service.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PageController {

    @Autowired
    BookService bookService;
    @Autowired
    BorrowingRecordService borrowingRecordService;

    @RequestMapping("/login")
    public String index() {
        return "login";
    }
    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 新增图书渲染
     *
     * @param httpSession
     * @param model
     * @return
     */
    @RequestMapping("/admin/addBook")
    public String addBook(HttpSession httpSession, Model model) {
        BaseUser baseUser = (BaseUser) httpSession.getAttribute("user");
        if (baseUser == null || baseUser.getRoleId() != 4) {
            return "login";
        }
        //查所有图书
        model.addAttribute("list", bookService.list(null));
        return "admin_addbook";
    }

    /**
     * 借阅页面渲染
     *
     * @param model
     * @return
     */
    @RequestMapping("/user/bookList.do")
    public String bookList(Model model, HttpSession httpSession,String bookName,Integer bookType) {
        BaseUser baseUser = (BaseUser) httpSession.getAttribute("user");
        if (baseUser == null) {
            return "login";
        }
        Map<String, Object> where = new HashMap<>();
        where.put("userId", baseUser.getId());
        where.put("status", 1);
        //查用户当前借阅中的记录
        model.addAttribute("borrowingNum", borrowingRecordService.list(where).size());
        where.put("status", 3);
        //查询用户的逾期的记录
        model.addAttribute("overdueNum", borrowingRecordService.list(where).size());
        //查询所有图书
        Map<String,Object> listWhere = new HashMap<>();
        listWhere.put("bookName",bookName);
        listWhere.put("bookType",bookType);
        model.addAttribute("list", bookService.list(listWhere));
        return "user_list";
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

        if (baseUser.getRoleId() == 4) {
            return "detail_admin";
        } else {
            return "detail_user";
        }
    }
}
