package com.inquiry.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.inquiry.dao.BorrowingRecordMapper;
import com.inquiry.model.BorrowingRecord;
import com.inquiry.service.BorrowingRecordService;

@Service
public class BorrowingRecordServiceImpl implements BorrowingRecordService {
    @Autowired
    private BorrowingRecordMapper borrowingRecordMapper;

    @Override
    public BorrowingRecord findById(Long id) {
        return borrowingRecordMapper.findById(id);
    }

    @Override
    public BorrowingRecord findByMap(Map<String, Object> map) {
        return borrowingRecordMapper.findByMap(map);
    }

    @Override
    public List<BorrowingRecord> list(Map<String, Object> map) {
        return borrowingRecordMapper.list(map);
    }


    @Override
    public int save(BorrowingRecord borrowingRecord) {
        return borrowingRecordMapper.save(borrowingRecord);
    }

    @Override
    public int update(BorrowingRecord borrowingRecord) {
        return borrowingRecordMapper.update(borrowingRecord);
    }

    @Override
    public int delete(Long id) {
        return borrowingRecordMapper.delete(id);
    }



}
