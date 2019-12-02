package com.lrkj.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 图书表
 *
 * @author lwc
 * @email 212425333@qq.com
 * @date 2019-11-28 16:43:44
 */
@Getter
@Setter
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;    // 标识列

    private String bookNo;    // 图书编号

    private String bookName;    // 图书名字

    private Integer bookType;    // 图书分类 1.文学 2.科幻 3.漫画

    private String pressName;    // 出版社名字

    private String authorName;    // 作者名字

    private Integer status;    // 借阅状态 o.未借阅 1。借阅中

    private Long createTime;    // 创建时间

    private Integer isDel;    // 0未删除 1.删除


    public void setCreateTime(String createTime) throws ParseException {
        long time = new SimpleDateFormat("yyyy-MM-dd").parse(createTime).getTime();
        this.createTime = time / 1000;
    }
	public void setCreateTime(Long createTime) throws ParseException {
		this.createTime = createTime;
	}



	public String getCreateTimeString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(this.createTime*1000));
    }

    public String getBookTypeName() {
        if (this.bookType == 1) {
            return "文学";
        }
        if (this.bookType == 2) {
            return "科幻";
        }
        if (this.bookType == 3) {
            return "漫画";
        }
        return "普通";
    }
}
