package com.jwking.community.controller;


import com.jwking.community.pojo.User;
import com.jwking.community.service.Impl.UserServiceImpl;
import com.jwking.community.service.UserService;
import com.jwking.community.utils.CommunityConstant;
import com.jwking.community.utils.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/register")
    public String getRegisterPage() {


        return "site/register";
    }
    @GetMapping("/login")
    public String getLoginPage() {


        return "site/login";
    }

    @PostMapping("/register")
    public String register(Model model, User user) {
        Map<String, Object> msg = userService.register(user);
        if (msg==null || msg.isEmpty()) {
            model.addAttribute("msg", "注册成功，我们已经向您的邮箱发送了一封邮件，请尽快激活");
            model.addAttribute("url", "/index");
            return "site/operate-result";
        }
        model.addAttribute("usernameError", msg.get("usernameError"));
        model.addAttribute("emailError", msg.get("emailError"));
        model.addAttribute("passwordError", msg.get("passwordError"));
        return "site/register";
    }

    @GetMapping("/activation/{userId}/{activationCode}")
    public String activation(Model model,@PathVariable("userId") Integer userId,@PathVariable("activationCode") String activationCode) {
        Integer result = userService.activation(userId, activationCode);
        if (result == CommunityConstant.ACTIVATION_SUCCESS) {
            model.addAttribute("msg", "激活成功，您的账号已经可以正常使用");
            model.addAttribute("url", "/login");
        } else if(result == CommunityConstant.ACTIVATION_FAIL) {
            model.addAttribute("msg", "激活失败，激活码不正确");
            model.addAttribute("url", "/index");
        } else {
            model.addAttribute("msg", "无效操作，该账号已经激活过了");
            model.addAttribute("url", "/index");
        }
        return "site/operate-result";
    }
}
