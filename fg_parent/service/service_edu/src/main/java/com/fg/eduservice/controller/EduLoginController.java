package com.fg.eduservice.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.fg.commonutils.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduLoginController {
    //login
    @PostMapping("/login")
    public Result login() {
        //return Result.success().data("token","admin");
        return Result.success().data("token","admin");
    }
    //info
    @GetMapping("/info")
    public Result info() {
        return Result.success().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
