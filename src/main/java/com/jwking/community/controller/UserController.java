package com.jwking.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jwking.community.annotation.LoginRequired;
import com.jwking.community.pojo.User;
import com.jwking.community.service.Impl.UserServiceImpl;
import com.jwking.community.service.UserService;
import com.jwking.community.utils.CommunityUtil;
import com.jwking.community.utils.HostHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class UserController {

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private HostHolder hostHolder;
    @LoginRequired
    @GetMapping("/user/setting")
    public String getSettingPage() {

        return "site/setting";
    }

    /**
     * 文件上传,MultipartFile对象,form表单提交方式必须为post,
     */
    @LoginRequired
    @PostMapping("/user/upload")
    public String uploadHeader(MultipartFile headerImage, Model model) {

        if (headerImage == null) {
            model.addAttribute("error", "您还没有选择图片");
            return "site/setting";
        }
        String filename = headerImage.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("error", "文件格式不正确");
            return "site/setting";
        }
        //生成随机文件名
        filename = CommunityUtil.getRandomString() + suffix;
        //确定文件的存放位置
        File file = new File(uploadPath+"/"+filename);
        try {
            headerImage.transferTo(file);
        } catch (IOException e) {
            log.error("上传文件失败"+e.getMessage());
            throw new RuntimeException("上传文件失败，服务器异常");
        }
        User user = hostHolder.getUser();
        String url = domain + contextPath + "/user/header/" + filename;
        userService.updateHeader(user.getId(),url);
        return "redirect:/index";
    }

    /**
     * 获取头像
     */
    @GetMapping("/user/header/{filename}")
    public void getHeaderImage(@PathVariable("filename") String filename, HttpServletResponse response) {
        //服务器存放的路径
        filename = uploadPath + "/" +filename;
        //文件的后缀
        String suffix = filename.substring(filename.lastIndexOf("."));
        //响应图片
        response.setContentType("image/"+suffix);

        //jdk7的语法，放入一些含有close方法的，在结束后会自动在finally中添加close方法
        try(
                OutputStream outputStream = response.getOutputStream();
                FileInputStream fileInputStream = new FileInputStream(filename);
                ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer,0,b);
            }
        } catch (IOException e) {
            log.error("读取图像失败！" + e.getMessage());
        }
    }

    /**
     *
     */
    @LoginRequired
    @PutMapping("/user/update")
    //@RequestParam("old-password") @RequestParam("new-password")
    public String updatePassword(@RequestParam("old-password")String oldPassword ,@RequestParam("new-password") String newPassword,Model model) {
        User user = hostHolder.getUser();
        if (!user.getPassword().equals(CommunityUtil.getMD5(oldPassword + user.getSalt()))) {
            model.addAttribute("oldPasswordError","原密码不正确!");
            model.addAttribute("newPassword",newPassword);
            return "site/setting";
        }
        if (newPassword.length() < 6) {
            model.addAttribute("newPasswordError","密码长度不能小于6！");
            model.addAttribute("oldPassword", oldPassword);
            return "site/setting";
        }
        newPassword = CommunityUtil.getMD5(newPassword + user.getSalt());
        userService.update(new UpdateWrapper<User>().eq("id", user.getId()).set("password", newPassword));
        return "redirect:/index";
    }

}
