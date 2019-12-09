package com.inquiry.service;

import com.github.pagehelper.PageInfo;
import com.inquiry.model.Inquiry;

import java.util.List;
import java.util.Map;

/**
 * 调查表
 * 
 * @author lwc
 * @email 212425333@qq.com
 * @date 2019-12-09 14:28:36
 */
public interface InquiryService {
	
	Inquiry findById(Long id);

	Inquiry findByMap(Map<String, Object> map);
	
	List<Inquiry> list(Map<String, Object> map);
	
	int save(Inquiry inquiry);
	
	int update(Inquiry inquiry);

	int delete(Long id);

	List<Inquiry> list(Map<String, Object> map, int page, int pageSize);


}
