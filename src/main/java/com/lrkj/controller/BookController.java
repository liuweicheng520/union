package com.lrkj.controller;

import com.lrkj.core.ApiRequest;
import com.lrkj.core.ApiResponse;
import com.lrkj.model.BaseUser;
import com.lrkj.model.Book;
import com.lrkj.model.BorrowingRecord;
import com.lrkj.service.BookService;
import com.lrkj.service.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequestMapping("/book")
@Controller
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    BorrowingRecordService borrowingRecordService;

    @PostMapping("/add.do")
    @ResponseBody
    public ApiResponse add(@RequestBody Book book) {
        book.setStatus(0);
        bookService.save(book);
        return ApiResponse.getDefaultResponse();
    }

    @GetMapping("/{id}/borrowing.do")
    public String borrowing(HttpSession httpSession, @PathVariable Long id) {
        BaseUser baseUser = (BaseUser) httpSession.getAttribute("user");
        if (baseUser == null) {
            return "login";
        }
        Book book = bookService.findById(id);
        book.setStatus(1);
        bookService.update(book);

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBookId(book.getId());
        borrowingRecord.setBorrowingNo(book.getBookNo());
        borrowingRecord.setCreateTime(System.currentTimeMillis() / 1000);
        borrowingRecord.setFineMoney(0L);
        borrowingRecord.setStatus(1);
        borrowingRecord.setEndTime(System.currentTimeMillis() / 1000 + 60 * 60 * 24 * 7);
        borrowingRecord.setUserId(baseUser.getId());
        borrowingRecord.setBookName(book.getBookName());
        borrowingRecord.setRenewalNum(0);
        borrowingRecord.setUserName(baseUser.getNickname());

        borrowingRecordService.save(borrowingRecord);

        return "redirect:/user/bookList.do";
    }

}
