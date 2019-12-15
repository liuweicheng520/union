package com.inquiry.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 调查表-记录
 *
 * @author lwc
 * @email 212425333@qq.com
 * @date 2019-12-10 11:38:05
 */
@Getter
@Setter
public class InquiryRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;	// 标识列

	private Long inquiryId;	// 调查id

	private String inquiryName;	// 问卷名字

	private String topicName;	// 问卷题目

	private String answer;	// 回答内容

	private Long userId;	// 参与人id

	private String userName;	// 参与人名称

	private Long createTime;	// 创建时间

	private Long endTime;	// 结束时间

	private Integer isDel;	// 0未删除 1.删除


	public void setCreateTime(String createTime) throws ParseException {
		long time = new SimpleDateFormat("yyyy-MM-dd").parse(createTime).getTime();
		this.createTime = time / 1000;
	}

	public void setCreateTime(Long createTime)	 {
		this.createTime = createTime;
	}

	public String getCreateTimeString() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date(this.createTime * 1000));
	}
}
