package com.lrkj.controller;

import com.lrkj.model.BaseUser;
import com.lrkj.model.Book;
import com.lrkj.model.BorrowingRecord;
import com.lrkj.service.BookService;
import com.lrkj.service.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/borrowing")
@Controller
public class BorrowingRecordController {
    @Autowired
    BorrowingRecordService borrowingRecordService;
    @Autowired
    BookService bookService;

    @RequestMapping("/user/list.do")
    public String borrowingRecord(Model model, HttpSession httpSession) {
        BaseUser baseUser = (BaseUser) httpSession.getAttribute("user");
        if (baseUser == null) {
            return "login";
        }
        Map<String, Object> where = new HashMap<>();
        where.put("userId", baseUser.getId());
        List<BorrowingRecord> list = borrowingRecordService.list(where);

        for (BorrowingRecord borrowingRecord : list) {
            if (borrowingRecord.getStatus() == 1 && borrowingRecord.overdueStatus() == 2) {
                borrowingRecord.setStatus(3);
                borrowingRecordService.update(borrowingRecord);
            }
        }

        model.addAttribute("list", list);


        return "user_borrow";
    }

    @RequestMapping("/user/returnBook.do/{id}")
    public String returnBook(HttpSession httpSession, @PathVariable Long id) {
        BaseUser baseUser = (BaseUser) httpSession.getAttribute("user");
        if (baseUser == null) {
            return "login";
        }
        //还书
        BorrowingRecord record = borrowingRecordService.findById(id);
        Book book = bookService.findById(record.getBookId());
        if (record.getUserId().equals(baseUser.getId())) {
            record.setStatus(2);
            book.setStatus(0);
            borrowingRecordService.update(record);
            bookService.update(book);
        }
        return "redirect:/borrowing/user/list.do";
    }

    @RequestMapping("/user/renewal.do/{id}")
    public String renewalBook(HttpSession httpSession, @PathVariable Long id) {
        BaseUser baseUser = (BaseUser) httpSession.getAttribute("user");
        if (baseUser == null) {
            return "login";
        }
        //续租
        BorrowingRecord record = borrowingRecordService.findById(id);
        if (record.getUserId().equals(baseUser.getId())) {
            if (record.getRenewalNum() < 2) {
                record.setEndTime(record.getEndTime() + 60 * 60 * 24 * 7); //续租7天
                record.setRenewalNum(record.getRenewalNum() + 1);
                borrowingRecordService.update(record);
            }
        }
        return "redirect:/borrowing/user/list.do";
    }
}
