package com.inquiry.service;

import com.inquiry.model.InquiryRecord;

import java.util.List;
import java.util.Map;

/**
 * 调查表-记录
 * 
 * @author lwc
 * @email 212425333@qq.com
 * @date 2019-12-09 09:46:43
 */
public interface InquiryRecordService {
	
	InquiryRecord findById(Long id);

	InquiryRecord findByMap(Map<String, Object> map);
	
	List<InquiryRecord> list(Map<String, Object> map);
	
	int save(InquiryRecord inquiryRecord);
	
	int update(InquiryRecord inquiryRecord);

	int delete(Long id);

}
