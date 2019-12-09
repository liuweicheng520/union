package com.inquiry;

import com.inquiry.core.ApiRequest;
import com.inquiry.core.ApiResponse;
import com.inquiry.model.BaseUser;
import com.inquiry.service.BaseUserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@MapperScan(basePackages = "com.inquiry.dao")
public class InquiryApplication {

    @Autowired
    BaseUserService baseUserService;

    public static void main(String[] args) {
        SpringApplication.run(InquiryApplication.class, args);
    }


    @RequestMapping("/init")
    public ApiResponse init(ApiRequest apiRequest){
        //初始化用户
        BaseUser baseUser = new BaseUser();
        baseUser.setAccount("xs1");
        baseUser.setNickname("石泰铭");
        baseUser.setPhone("18806");
        baseUser.setPassword("123");
        baseUser.setRoleId(1);
        baseUserService.save(baseUser);

        baseUser.setAccount("admin");
        baseUser.setNickname("石泰铭的爸爸");
        baseUser.setPhone("18806");
        baseUser.setPassword("123");
        baseUser.setRoleId(2);


        baseUserService.save(baseUser);


        baseUser.setAccount("admin-pt");
        baseUser.setNickname("石泰铭的妈妈");
        baseUser.setPhone("18806");
        baseUser.setPassword("123");
        baseUser.setRoleId(3);
        baseUserService.save(baseUser);

        baseUserService.save(baseUser);


        return ApiResponse.getDefaultResponse(baseUserService.list(null));
    }
}
