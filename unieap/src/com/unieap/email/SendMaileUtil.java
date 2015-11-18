package com.unieap.email;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.unieap.base.ServiceUtils;

public class SendMaileUtil {
	private static JavaMailSender javaMailSender;
	private static JavaMailSender newIntstance() {
        if (javaMailSender == null) {
        	javaMailSender = (JavaMailSender)ServiceUtils.getBean("mail");
        }
        return javaMailSender;
    }
	 /**
     * send email
     * 
     * @param to
     * @param mailSubject
     * @param mailBody
     */
    public static void sendTextMaile(String from,String[] tos, String mailSubject,
            String mailBody) {
        SimpleMailMessage[] mailMessages = new SimpleMailMessage[tos.length];
        for(int i = 0 ; i< tos.length ; i++){
        	SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(from);
            mail.setTo(tos[i]);
            mail.setSubject(mailSubject);
            mail.setSentDate(new Date());
            mail.setText(mailBody);
            mailMessages[i] = mail;
        }
        //SimpleMailMessage[] mailMessages = { mail1 };
        newIntstance().send(mailMessages);
    }
    /**
     * 以 HTML脚本形式邮件发送
     * 
     * @param to
     * @param mailSubject
     * @param mailBody
     * @throws Exception 
     */
    public static void sendHtmlMail(String from,String[] tos,String toName, String mailSubject,
            String mailBody) throws Exception {
        JavaMailSender mailSender = newIntstance();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        for(int i = 0 ; i< tos.length ; i++){
        	MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true,"UTF-8");
        	helper.setFrom(from);
        	helper.setTo(new InternetAddress("\""
        			+ MimeUtility.encodeText(toName) + "\" <" + tos[i] + ">"));// 发送者
        	helper.setSentDate(new Date());
        	helper.setReplyTo(new InternetAddress(from));
        	helper.setSubject(mailSubject);
        	helper.setText("<html><head></head><body>"+mailBody+"</body></html>",true);
        	mailSender.send(mimeMessage);
        }
    }
    /**
     * 以附件的形式发送邮件
     * 
     * @param to
     *            收件人eamil 地址
     * @param toName
     *            收件人昵称
     * @param mailSubject
     *            主题
     * @param mailBody
     *            内容体
     * @param files
     *            附件
     * @throws Exception 
     */
    public static void sendFileMail(String from,String[] tos, String toName,
            String mailSubject, String mailBody, File[] files) throws Exception {
        JavaMailSender mailSender = newIntstance();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        for(int i = 0 ; i< tos.length ; i++){
        	// 设置utf-8或GBK编码，否则邮件会有乱码
        	MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true,"UTF-8");
        	// 设置发送人名片
        	helper.setFrom(from);
        	// 设置收件人邮箱
        	helper.setTo(new InternetAddress("\""
        			+ MimeUtility.encodeText(toName) + "\" <" + tos[i] + ">"));
        	
        	// 设置回复地址
        	// helper.setReplyTo(new InternetAddress("@qq.com"));
        	
        	// 设置收件人抄送的名片和地址(相当于群发了)
        	// helper.setCc(InternetAddress.parse(MimeUtility.encodeText("邮箱001")
        	// + " <@163.com>," + MimeUtility.encodeText("邮箱002")
        	// + " <@foxmail.com>"));
        	// 主题
        	helper.setSubject(mailSubject);
        	// 邮件内容，注意加参数true，表示启用html格式
        	helper.setText(mailBody);
        	if (files != null && files.length > 0) {
        		for (int j = 0; j < files.length; j++)
        			// 加入附件
        			helper.addAttachment(MimeUtility.encodeText(files[j]
        					.getName()), files[j]);
        	}
        	// 加入插图
        	//helper.addInline(MimeUtility.encodeText("pic01"), new File(
        	//       "c:/temp/2dd24be463.jpg"));
        	// 发送
        	mailSender.send(mimeMessage);
        }
    }
     /*boolean auth = true;// 表示 是否需要验证
     String from;// 谁发？
     String to;// 发给谁？
     String username;// 发件箱用户名
     String password;// 发件箱密码
     String title;// 标题
     String content;// 内容
*/    public static void javaSendEmail() throws Exception{
    	String username = "caiqiufu@gmail.com";
    	String password = "caiqiuFU12!";
    	Properties props = System.getProperties();
    	   // 往126邮箱发
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.fallback", "false");
        // props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.socketFactory.class",
        "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.transport.protocol", "smtp");
        Session session = null;
        Authenticator author = new MyAuthenticator(username, password);
        session = Session.getDefaultInstance(props, author);
        session.setDebug(true);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("caiqiufu@gmail.com"));
        // 发哪去？
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(
        		"caiqiufu@gmail.com"));
        message.setSubject("test email");
        message.setText("caiqiufu@gmail.com");
        // 用来发送
        Transport.send(message);
    }
}
