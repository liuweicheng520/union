package com.book.model;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 图书借阅-记录
 *
 * @author lwc
 * @email 212425333@qq.com
 * @date 2019-12-02 17:02:25
 */
@Getter
@Setter
public class BorrowingRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;    // 标识列

    private String borrowingNo;    // 借阅编号

    private Long bookId;    // 图书id

    private Long userId;    // 用户id

    private String userName;    // 借阅人

    private String bookName;    // 书名

    private Long fineMoney;    // 逾期金额

    private Integer renewalNum;    // 续租次数

    private Integer status;    // 借阅状态 0.申请中 1.借阅中 2。已归还  3.逾期

    private Long createTime;    // 创建时间

    private Long endTime;    // 借阅截止时间

    private Integer isDel;    // 0未删除 1.删除

    public String getCreateTimeString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(this.createTime * 1000));
    }


    public String getEndTimeString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(this.endTime * 1000));
    }

    public String statusString() {
        if (status == 1) {
            return "借阅中";
        } else if (status == 2) {
            return "已归还";
        } else if (status == 3) {
            return "逾期中";
        }
        return "默认";
    }

    /**
     * 检测快过期状态
     *
     * @return 1.快过期 2。已过期
     */
    public int overdueStatus() {
        long time = endTime - System.currentTimeMillis() / 1000;
        if (status != 2) {
            if (time < 0) {
                return 2;
            } else if (time < (60 * 60 * 24)) {
                return 1;
            }
        }

        return 0;
    }

    public Long fineMoney() {
        if (status == 3) {
            long time = System.currentTimeMillis() / 1000 - endTime;
            long num = time / (60 * 60 * 24);
            return num * 2;//
            // 2块钱一天
        }
        return 0L;
    }
}
