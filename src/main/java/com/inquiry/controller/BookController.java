package com.inquiry.controller;

import com.inquiry.core.ApiResponse;
import com.inquiry.model.BaseUser;
import com.inquiry.model.Book;
import com.inquiry.model.BorrowingRecord;
import com.inquiry.service.BookService;
import com.inquiry.service.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/book")
@Controller
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    BorrowingRecordService borrowingRecordService;

    @PostMapping("/add.do")
    @ResponseBody
    /**
     * 添加图书
     */
    public ApiResponse add(@RequestBody Book book) {
        Map<String, Object> where = new HashMap<>();
        where.put("bookNo", book.getBookNo());
        Book book1 = bookService.findByMap(where);
        if (book1 != null) {
            return ApiResponse.getDefaultResponse("图书编号重复");
        }
        book.setStatus(0);
        bookService.save(book);
        return ApiResponse.getDefaultResponse("添加成功");
    }

    @GetMapping("/{id}/borrowing.do")
    public String borrowing(HttpSession httpSession, @PathVariable Long id) {
        BaseUser baseUser = (BaseUser) httpSession.getAttribute("user");
        if (baseUser == null) {
            return "login";
        }

        Map<String, Object> where = new HashMap<>();
        where.put("userId", baseUser.getId());
        where.put("status", 1);
        //先把用户当前正在借阅的图书查出来
        int borrowingNum = borrowingRecordService.list(where).size();
        where.put("status", 3);
        //把用户逾期的借阅查出来
        int overdueNum = borrowingRecordService.list(where).size();

        if (borrowingNum >= 3) {
            //每个人当前只能借三本书
            return "redirect:/user/bookList.do";
        }
        if (overdueNum > 0) {
            //有逾期的借阅,需要缴纳费用后,才可以借书
            return "redirect:/user/bookList.do";
        }

        //将图书状态改为已借阅
        Book book = bookService.findById(id);
        book.setStatus(1);
        bookService.update(book);

        //将借阅记录入库
        int day = 0; //天数根据角色不同 信管学生 30天  计算机 60天  180 天教师

        if (baseUser.getRoleId() == 1) {
            day = 30;
        } else if (baseUser.getRoleId() == 2) {
            day = 60;
        } else if (baseUser.getRoleId() == 3) {
            day = 180;
        }

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBookId(book.getId());
        borrowingRecord.setBorrowingNo(book.getBookNo());
        borrowingRecord.setCreateTime(System.currentTimeMillis() / 1000);
        borrowingRecord.setFineMoney(0L);
        borrowingRecord.setStatus(1);
        borrowingRecord.setEndTime(System.currentTimeMillis() / 1000 + 60 * 60 * 24 * day);
        borrowingRecord.setUserId(baseUser.getId());
        borrowingRecord.setBookName(book.getBookName());
        borrowingRecord.setRenewalNum(0);
        borrowingRecord.setUserName(baseUser.getNickname());

        borrowingRecordService.save(borrowingRecord);

        return "redirect:/user/bookList.do";
    }

}
