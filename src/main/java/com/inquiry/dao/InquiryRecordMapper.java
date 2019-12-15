package com.inquiry.dao;

import com.inquiry.model.InquiryRecord;

import java.util.List;
import java.util.Map;


/**
 * 调查表-记录
 * @email 212425333@qq.com
 * @date 2019-12-09 16:46:03
 */
public interface InquiryRecordMapper {

    InquiryRecord findById(Long id);

    InquiryRecord findByMap(Map<String, Object> map);

    List<InquiryRecord> list(Map<String, Object> map);

    int save(InquiryRecord inquiryRecord);

    int update(InquiryRecord inquiryRecord);

    int delete(Long id);


}
