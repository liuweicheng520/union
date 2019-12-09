package com.lrkj.model;


import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * 调查表
 *
 * @author lwc
 * @email 212425333@qq.com
 * @date 2019-12-09 09:46:42
 */
@Getter
@Setter
public class Inquiry implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;	// 标识列

	private String inquiryName;	// 问卷名字

	private String topicName;	// 问卷题目

	private Long createTime;	// 创建时间

	private Long endTime;	// 结束时间

	private Integer isDel;	// 0未删除 1.删除


}
