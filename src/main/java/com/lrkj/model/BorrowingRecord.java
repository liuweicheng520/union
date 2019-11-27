package com.lrkj.model;


import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * 图书借阅-记录
 *
 * @author lwc
 * @email 212425333@qq.com
 * @date 2019-11-27 16:56:05
 */
@Getter
@Setter
public class BorrowingRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;	// 标识列

	private String borrowingNo;	// 借阅编号

	private Long bookId;	// 图书id

	private Long userId;	// 用户id

	private Long fineMoney;	// 逾期金额

	private Integer status;	// 借阅状态 0.申请中 1.借阅中 2。已归还 3.续租 4.逾期 5.已拒绝

	private Long createTime;	// 创建时间

	private Long endTime;	// 借阅截止时间

	private Integer isDel;	// 0未删除 1.删除


}
