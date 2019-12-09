package com.inquiry.dao;

import com.inquiry.model.BaseUser;

import java.util.List;
import java.util.Map;


/**
 * 用户表
 * @author lwc
 * @email 212425333@qq.com
 * @date 2019-12-02 18:52:46
 */
public interface BaseUserMapper {

    BaseUser findById(Long id);

    BaseUser findByMap(Map<String, Object> map);

    List<BaseUser> list(Map<String, Object> map);

    int save(BaseUser baseUser);

    int update(BaseUser baseUser);

    int delete(Long id);


}
