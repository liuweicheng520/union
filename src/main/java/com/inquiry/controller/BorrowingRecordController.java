package com.inquiry.controller;

import com.inquiry.model.BaseUser;
import com.inquiry.model.Book;
import com.inquiry.model.BorrowingRecord;
import com.inquiry.service.BookService;
import com.inquiry.service.BorrowingRecordService;
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

    /**
     * 用户借阅记录渲染
     */
    @RequestMapping("/user/list.do")
    public String borrowingRecord(Model model, HttpSession httpSession) {
        BaseUser baseUser = (BaseUser) httpSession.getAttribute("user");
        if (baseUser == null) {
            return "login";
        }
        Map<String, Object> where = new HashMap<>();
        //查询用户的借阅记录
        where.put("userId", baseUser.getId());
        List<BorrowingRecord> list = borrowingRecordService.list(where);

        //用户进入借阅列表时,查询记录, 记录逾期的借阅记录和计算逾期费用更新到数据库中
        for (BorrowingRecord borrowingRecord : list) {
            if (borrowingRecord.getStatus() == 1 && borrowingRecord.overdueStatus() == 2) {
                borrowingRecord.setStatus(3);
                borrowingRecord.setFineMoney(borrowingRecord.fineMoney());
                borrowingRecordService.update(borrowingRecord);
            }
        }

        model.addAttribute("list", list);


        return "user_borrow";
    }

    /**
     * 用户还书
     */
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
            //还书更改状态为已归还,并把逾期费用结算了。
            record.setStatus(2);
            record.setFineMoney(0L);

            //把图书的借阅状态改为未借阅。
            book.setStatus(0);

            borrowingRecordService.update(record);
            bookService.update(book);
        }
        return "redirect:/borrowing/user/list.do";
    }

    /**
     * 续租
     */
    @RequestMapping("/user/renewal.do/{id}")
    public String renewalBook(HttpSession httpSession, @PathVariable Long id) {
        BaseUser baseUser = (BaseUser) httpSession.getAttribute("user");
        if (baseUser == null) {
            return "login";
        }
        //续租
        BorrowingRecord record = borrowingRecordService.findById(id);
        if (record.getUserId().equals(baseUser.getId())) {
            //续租次数少于2次
            if (record.getRenewalNum() < 2) {
                record.setEndTime(record.getEndTime() + 60 * 60 * 24 * 7); //续租7天
                //续租次数+1
                record.setRenewalNum(record.getRenewalNum() + 1);
                borrowingRecordService.update(record);
            }
        }
        return "redirect:/borrowing/user/list.do";
    }
}
