package com.lrkj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.lrkj.dao.InquiryMapper;
import com.lrkj.model.Inquiry;
import com.lrkj.service.InquiryService;

@Service
public class InquiryServiceImpl implements InquiryService {
    @Autowired
    private InquiryMapper inquiryMapper;

    @Override
    public Inquiry findById(Long id) {
        return inquiryMapper.findById(id);
    }

    @Override
    public Inquiry findByMap(Map<String, Object> map) {
        return inquiryMapper.findByMap(map);
    }

    @Override
    public List<Inquiry> list(Map<String, Object> map) {
        return inquiryMapper.list(map);
    }


    @Override
    public int save(Inquiry inquiry) {
        return inquiryMapper.save(inquiry);
    }

    @Override
    public int update(Inquiry inquiry) {
        return inquiryMapper.update(inquiry);
    }

    @Override
    public int delete(Long id) {
        return inquiryMapper.delete(id);
    }



}
