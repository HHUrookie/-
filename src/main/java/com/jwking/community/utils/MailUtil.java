package com.jwking.community.utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 模拟邮箱的客户端，发送邮件
 */
@Component
@Slf4j
public class MailUtil {

    // private static final Logger log = LoggerFactory.getLogger(MailUtil.class);

    @Autowired
    private JavaMailSender mailSender;
    //发件人
    @Value("${spring.mail.username}")
    private String addressor;

    /**
     *
     * @param recipient 收件人
     * @param subject 主题
     * @param content 内容
     */
    public void sendMail(String recipient, String subject,String content) {
        MimeMessage message ;
        MimeMessageHelper messageHelper ;
        try {
            message = mailSender.createMimeMessage();
            messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom(addressor);
            messageHelper.setTo(recipient);
            messageHelper.setSubject(subject);
            //第二个参数说明支持html邮件
            messageHelper.setText(content,true);
            mailSender.send(messageHelper.getMimeMessage());
        } catch (MessagingException e) {
            log.error("发送邮件失败："+e.getMessage());
        }
    }
}
