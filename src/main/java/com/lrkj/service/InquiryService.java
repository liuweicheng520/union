package com.lrkj.service;

import com.lrkj.model.Inquiry;

import java.util.List;
import java.util.Map;

/**
 * 调查表
 * 
 * @author lwc
 * @email 212425333@qq.com
 * @date 2019-12-09 09:46:42
 */
public interface InquiryService {
	
	Inquiry findById(Long id);

	Inquiry findByMap(Map<String, Object> map);
	
	List<Inquiry> list(Map<String, Object> map);
	
	int save(Inquiry inquiry);
	
	int update(Inquiry inquiry);

	int delete(Long id);

}
