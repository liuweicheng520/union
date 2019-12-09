package com.inquiry.model;


import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * 调查表-记录
 *
 * @author lwc
 * @email 212425333@qq.com
 * @date 2019-12-09 16:46:03
 */
@Getter
@Setter
public class InquiryRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;	// 标识列

	private Long inquiryId;	// 调查id

	private String answer;	// 回答内容

	private Long userId;	// 参与人id

	private String userName;	// 参与人名称

	private Long createTime;	// 创建时间

	private Long endTime;	// 结束时间

	private Integer isDel;	// 0未删除 1.删除


}
