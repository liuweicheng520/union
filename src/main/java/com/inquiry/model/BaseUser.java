package com.inquiry.model;


import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户表
 *
 * @author lwc
 * @email 212425333@qq.com
 */
@Getter
@Setter
public class BaseUser implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;	// 标识列

	private String nickname;	// 用户姓名

	private String account;	// 登陆账号

	private String password;	// 密码

	private Integer roleId;	// 角色id 1.用户 2.管理员 3.平台

	private String phone;	// 手机号码

	private Integer isDel;	// 0未删除 1.删除

}
