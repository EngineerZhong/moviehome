package com.dalididilo.moviehome.module.login.controller;


import com.dalididilo.moviehome.module.login.service.ILoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.dalididilo.moviehome.index.bean.Result;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {


    @Autowired
    ILoginService loginService;

    @RequestMapping(value = "/LoginProcess",method = RequestMethod.POST)
    public String LoginProcess(@RequestParam("name") String name, @RequestParam("password")String password, Model model){
        /**
         * 编写Shiro的逻辑认证
         */
//        1、获取Subject
        System.out.println(name  + ":::::" + password);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name,password);
        return getString(model, subject, token);
    }


    public static String getString(Model model, Subject subject, UsernamePasswordToken token) {
        try {
            subject.login(token);
            // 不报错 说明登录成功了
            return "redirect:../index";
        }catch (UnknownAccountException ex){
            // 登录失败，用户名不存在。
            model.addAttribute("msg","用户名不存在");
            return "/user/Login";
        }catch (IncorrectCredentialsException ex2){
            model.addAttribute("msg","密码错误");
            return "/user/Login";
        }
    }

}
