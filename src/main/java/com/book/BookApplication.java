package com.book;

import com.book.core.ApiRequest;
import com.book.core.ApiResponse;
import com.book.service.BaseUserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@MapperScan(basePackages = "com.book.dao")
public class BookApplication {

    @Autowired
    BaseUserService baseUserService;

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }


    @RequestMapping("/init")
    public ApiResponse init(ApiRequest apiRequest){
//        //初始化用户
//        BaseUser baseUser = new BaseUser();
//        baseUser.setAccount("xs1");
//        baseUser.setEmail("212425333@qq.com");
//        baseUser.setNickname("石泰铭");
//        baseUser.setPhone("18806");
//        baseUser.setPassword("123");
//        baseUser.setRoleid(1);
//        baseUserService.save(baseUser);
//
//        baseUser.setAccount("xs2");
//        baseUser.setEmail("212425333@qq.com");
//        baseUser.setNickname("石泰铭的爸爸");
//        baseUser.setPhone("18806");
//        baseUser.setPassword("123");
//        baseUser.setRoleid(2);
//
//        baseUserService.save(baseUser);
//
//
//        baseUser.setAccount("xs3");
//        baseUser.setEmail("212425333@qq.com");
//        baseUser.setNickname("石泰铭的妈妈");
//        baseUser.setPhone("18806");
//        baseUser.setPassword("123");
//        baseUser.setRoleid(3);
//
//        baseUserService.save(baseUser);
//
//
//        baseUser.setAccount("admin");
//        baseUser.setEmail("212425333@qq.com");
//        baseUser.setNickname("石泰铭的管理员");
//        baseUser.setPhone("18806");
//        baseUser.setPassword("123");
//        baseUser.setRoleid(4);
//
//        baseUserService.save(baseUser);


        return ApiResponse.getDefaultResponse(baseUserService.list(null));
    }
}
