package com.book.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.book.dao.AdminSettingMapper;
import com.book.model.AdminSetting;
import com.book.service.AdminSettingService;

@Service
public class AdminSettingServiceImpl implements AdminSettingService {
    @Autowired
    private AdminSettingMapper adminSettingMapper;

    @Override
    public AdminSetting findById(Long id) {
        return adminSettingMapper.findById(id);
    }

    @Override
    public AdminSetting findByMap(Map<String, Object> map) {
        return adminSettingMapper.findByMap(map);
    }

    @Override
    public List<AdminSetting> list(Map<String, Object> map) {
        return adminSettingMapper.list(map);
    }


    @Override
    public int save(AdminSetting adminSetting) {
        return adminSettingMapper.save(adminSetting);
    }

    @Override
    public int update(AdminSetting adminSetting) {
        return adminSettingMapper.update(adminSetting);
    }

    @Override
    public int delete(Long id) {
        return adminSettingMapper.delete(id);
    }



}
