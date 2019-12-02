package com.book.dao;

import com.book.model.AdminSetting;

import java.util.List;
import java.util.Map;


/**
 * 后台设置表
 * @author lwc
 * @email 212425333@qq.com
 * @date 2019-11-27 16:56:05
 */
public interface AdminSettingMapper {

    AdminSetting findById(Long id);

    AdminSetting findByMap(Map<String, Object> map);

    List<AdminSetting> list(Map<String, Object> map);

    int save(AdminSetting adminSetting);

    int update(AdminSetting adminSetting);

    int delete(Long id);


}
