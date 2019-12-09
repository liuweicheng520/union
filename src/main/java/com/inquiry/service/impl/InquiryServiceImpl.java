package com.inquiry.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inquiry.dao.InquiryMapper;
import com.inquiry.model.Inquiry;
import com.inquiry.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.inquiry.dao.InquiryMapper;
import com.inquiry.model.Inquiry;
import com.inquiry.service.InquiryService;

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

    @Override
    public List<Inquiry> list(Map<String, Object> map, int page, int pageSize) {
        return inquiryMapper.list(map);
    }
}
