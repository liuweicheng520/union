package com.lrkj.service;

import com.lrkj.model.BorrowingRecord;

import java.util.List;
import java.util.Map;

/**
 * 图书借阅-记录
 * 
 * @author lwc
 * @email 212425333@qq.com
 * @date 2019-11-27 16:56:05
 */
public interface BorrowingRecordService {
	
	BorrowingRecord findById(Long id);

	BorrowingRecord findByMap(Map<String, Object> map);
	
	List<BorrowingRecord> list(Map<String, Object> map);
	
	int save(BorrowingRecord borrowingRecord);
	
	int update(BorrowingRecord borrowingRecord);

	int delete(Long id);

}
