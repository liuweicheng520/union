package com.book.model;


import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 后台设置表
 *
 * @author lwc
 * @email 212425333@qq.com
 * @date 2019-11-27 16:56:05
 */
@Getter
@Setter
public class AdminSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;	// 标识列

	private String code;	// code码

	private String value;	// 值

	private Long createTime;	// 创建时间

	private Long updateTime;	// 修改时间

	private String updateAdmin;	// 修改人

	private Integer isDel;	// 0未删除 1.删除


}
