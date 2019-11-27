package com.lrkj.dao;

import com.lrkj.model.BaseUser;

import java.util.List;
import java.util.Map;


/**
 * 用户表
 * @author lwc
 * @email 212425333@qq.com
 * @date 2019-11-27 16:56:05
 */
public interface BaseUserMapper {

    BaseUser findById(Long id);

    BaseUser findByMap(Map<String, Object> map);

    List<BaseUser> list(Map<String, Object> map);

    int save(BaseUser baseUser);

    int update(BaseUser baseUser);

    int delete(Long id);


}
