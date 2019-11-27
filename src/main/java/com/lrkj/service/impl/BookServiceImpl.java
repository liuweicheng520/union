package com.lrkj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.lrkj.dao.BookMapper;
import com.lrkj.model.Book;
import com.lrkj.service.BookService;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public Book findById(Long id) {
        return bookMapper.findById(id);
    }

    @Override
    public Book findByMap(Map<String, Object> map) {
        return bookMapper.findByMap(map);
    }

    @Override
    public List<Book> list(Map<String, Object> map) {
        return bookMapper.list(map);
    }


    @Override
    public int save(Book book) {
        return bookMapper.save(book);
    }

    @Override
    public int update(Book book) {
        return bookMapper.update(book);
    }

    @Override
    public int delete(Long id) {
        return bookMapper.delete(id);
    }



}
