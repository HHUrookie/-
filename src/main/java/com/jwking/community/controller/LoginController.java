package com.jwking.community.controller;
import com.google.code.kaptcha.Producer;
import com.jwking.community.pojo.User;
import com.jwking.community.service.Impl.UserServiceImpl;
import com.jwking.community.service.LoginTicketService;
import com.jwking.community.utils.CommunityConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private LoginTicketService loginTicketService;
    @Autowired
    private Producer kaptchaProduce;

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
    @GetMapping("/kaptcha")
    public void getKaptcha(HttpServletResponse response , HttpSession session) {
        //生成验证码
        String text = kaptchaProduce.createText();
        BufferedImage image = kaptchaProduce.createImage(text);
        //将验证码存入session
        session.setAttribute("text",text);
        //将图片输出给浏览器
        response.setContentType("image/png");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image,"png",outputStream);
        } catch (IOException e) {
            log.error("响应验证码失败"+e.getMessage());
        }
    }

    @PostMapping("/login")
    public String login(Model model,String username,String password,String code,
                        Boolean rememberMe,HttpSession session,HttpServletResponse response) {
        String kaptcha = (String) session.getAttribute("text");
        //检查验证码
        if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equals(code)) {
            model.addAttribute("codeError", "验证码错误!");
            return "/site/login";
        }
        //检查账号，密码
        Integer expiredSeconds = rememberMe==null ? CommunityConstant.DEFAULT_EXPIRED_SECONDS : CommunityConstant.REMEMBER_EXPIRED_SECONDS;
        Map<String, Object> map = loginTicketService.login(username, password, expiredSeconds);
        if (map.containsKey("ticket")) {

            Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
            cookie.setPath("/community");
            cookie.setMaxAge(expiredSeconds);
            response.addCookie(cookie);
            return "redirect:index";
        } else {
            model.addAttribute("usernameError",map.get("usernameError"));
            model.addAttribute("passwordError",map.get("passwordError"));
            return "site/login";
        }

    }

    @GetMapping("/logout")
    public String logout(@CookieValue("ticket") String ticket) {
        loginTicketService.logout(ticket);
        return "redirect:login";
    }
}
