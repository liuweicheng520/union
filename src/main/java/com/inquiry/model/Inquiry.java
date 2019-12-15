package com.inquiry.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 调查表
 *
 * @author lwc
 * @email 212425333@qq.com
 * @date 2019-12-09 16:35:42
 */
@Getter
@Setter
public class Inquiry implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;    // 标识列

    private String inquiryName;    // 问卷名字

    private String topicName;    // 问卷题目

    private Long userId;    // 发布人id

    private String userName;    // 发布人名称

    private Integer status;    // 1.审核中 2.进行中 3.审核失败

    private Long createTime;    // 创建时间

    private Long endTime;    // 结束时间

    private Integer isDel;    // 0未删除 1.删除

    private Integer userStatus;//1.本人 2.未参加 3.已参加

    private Integer timeStatus; //1.未开始 2.已结束 0.进行中

    public void setCreateTime(String createTime) throws ParseException {
        long time = new SimpleDateFormat("yyyy-MM-dd").parse(createTime).getTime();
        this.createTime = time / 1000;
    }

    public void setCreateTime(Long createTime) throws ParseException {
        this.createTime = createTime;
    }

    public String getCreateTimeString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(this.createTime * 1000));
    }


    public void setEndTime(String endTime) throws ParseException {
        long time = new SimpleDateFormat("yyyy-MM-dd").parse(endTime).getTime();
        this.endTime = time / 1000;
    }

    public void setEndTime(Long endTime) throws ParseException {
        this.endTime = endTime;
    }

    public String getEndTimeString() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(this.endTime * 1000));
    }

    public Integer getTimeStatus() {
        long time = System.currentTimeMillis() / 1000;
        if (time < createTime) {
            return 1;
        }
        if (time > endTime) {
            return 2;
        }
        return 0;
    }

    public String statusString() {
        if (status == 1) {
            return "审核中";
        } else if (status == 2) {
            long time = System.currentTimeMillis() / 1000;
            if (time < createTime) {
                return "未开始";
            }
            if (time > endTime) {
                return "已结束";
            }
            return "进行中";

        } else if (status == 3) {
            return "审核失败";
        }
        return "";
    }

    @Override
    public String toString() {
        return "Inquiry{" +
                "id=" + id +
                ", inquiryName='" + inquiryName + '\'' +
                ", topicName='" + topicName + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", endTime=" + endTime +
                ", isDel=" + isDel +
                ", userStatus=" + userStatus +
                '}';
    }
}
