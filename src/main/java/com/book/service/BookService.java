package com.book.service;

import com.book.model.Book;

import java.util.List;
import java.util.Map;

/**
 * 图书表
 * 
 * @author lwc
 * @email 212425333@qq.com
 * @date 2019-11-28 16:43:44
 */
public interface BookService {
	
	Book findById(Long id);

	Book findByMap(Map<String, Object> map);
	
	List<Book> list(Map<String, Object> map);
	
	int save(Book book);
	
	int update(Book book);

	int delete(Long id);

}
