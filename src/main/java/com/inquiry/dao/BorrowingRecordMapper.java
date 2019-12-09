package com.inquiry.dao;

import com.inquiry.model.BorrowingRecord;

import java.util.List;
import java.util.Map;


/**
 * 图书借阅-记录
 * @author lwc
 * @email 212425333@qq.com
 * @date 2019-12-02 17:02:25
 */
public interface BorrowingRecordMapper {

    BorrowingRecord findById(Long id);

    BorrowingRecord findByMap(Map<String, Object> map);

    List<BorrowingRecord> list(Map<String, Object> map);

    int save(BorrowingRecord borrowingRecord);

    int update(BorrowingRecord borrowingRecord);

    int delete(Long id);


}
