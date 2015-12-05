package com.unieap.email;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
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

import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;

public class MailUtils {
	private static JavaMailSender javaMailSender;

	private static JavaMailSender newIntstance() {
		if (javaMailSender == null) {
			javaMailSender = (JavaMailSender) ServiceUtils.getBean("mail");
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
	public static void sendTextMaile(String from, String[] tos, String subject, String mailBody) {
		SimpleMailMessage[] mailMessages = new SimpleMailMessage[tos.length];
		for (int i = 0; i < tos.length; i++) {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setFrom(from);
			mail.setTo(tos[i]);
			mail.setSubject(subject);
			mail.setSentDate(new Date());
			mail.setText(mailBody);
			mailMessages[i] = mail;
		}
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
	public static void sendHtmlMail(String from, String[] tos, String toName, String subject, String mailBody)
			throws Exception {
		JavaMailSender mailSender = newIntstance();
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		for (int i = 0; i < tos.length; i++) {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			helper.setFrom(from);
			helper.setTo(new InternetAddress("\"" + MimeUtility.encodeText(toName) + "\" <" + tos[i] + ">"));// 发送者
			helper.setSentDate(new Date());
			helper.setReplyTo(new InternetAddress(from));
			helper.setSubject(subject);
			helper.setText("<html><head></head><body>" + mailBody + "</body></html>", true);
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
	public static void sendFileMail(String from, String[] tos, String toName, String subject, String mailBody,
			File[] files) throws Exception {
		JavaMailSender mailSender = newIntstance();
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		for (int i = 0; i < tos.length; i++) {
			// 设置utf-8或GBK编码，否则邮件会有乱码
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			// 设置发送人名片
			helper.setFrom(from);
			// 设置收件人邮箱
			helper.setTo(new InternetAddress("\"" + MimeUtility.encodeText(toName) + "\" <" + tos[i] + ">"));
			// 设置回复地址
			// helper.setReplyTo(new InternetAddress("@qq.com"));
			// 设置收件人抄送的名片和地址(相当于群发了)
			// helper.setCc(InternetAddress.parse(MimeUtility.encodeText("邮箱001")
			// + " <@163.com>," + MimeUtility.encodeText("邮箱002")
			// + " <@foxmail.com>"));
			// 主题
			helper.setSubject(subject);
			// 邮件内容，注意加参数true，表示启用html格式
			helper.setText(mailBody);
			if (files != null && files.length > 0) {
				for (int j = 0; j < files.length; j++)
					// 加入附件
					helper.addAttachment(MimeUtility.encodeText(files[j].getName()), files[j]);
			}
			// 加入插图
			// helper.addInline(MimeUtility.encodeText("pic01"), new File(
			// "c:/temp/2dd24be463.jpg"));
			// 发送
			mailSender.send(mimeMessage);
		}
	}

	/*
	 * boolean auth = true;// 表示 是否需要验证 String from;// 谁发？ String to;// 发给谁？
	 * String username;// 发件箱用户名 String password;// 发件箱密码 String title;// 标题
	 * String content;// 内容
	 */
	public static void javaSendEmail(String fromBy, String[] sendTos, String subject, String text) throws Exception {
		String username = SYSConfig.getConfig().get("mail.username");
		String password = SYSConfig.getConfig().get("mail.password");
		Properties prop = new Properties();
		prop.put("mail.smtp.host", SYSConfig.getConfig().get("mail.host"));
		prop.put("mail.smtp.port", SYSConfig.getConfig().get("mail.port"));
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.socketFactory.fallback", "false");
		prop.put("mail.smtp.debug", "false");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.transport.protocol", "smtp");
		Authenticator author = new MyAuthenticator(username, password);

		Session session = Session.getDefaultInstance(prop, author);
		Transport ts = session.getTransport();
		ts.connect(SYSConfig.getConfig().get("mail.host"), username, password);
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(fromBy));
		Address[] addresss = new InternetAddress[sendTos.length];
		for (int i = 0; i < sendTos.length; i++) {
			addresss[i] = new InternetAddress(sendTos[i]);
		}
		message.addRecipients(Message.RecipientType.TO, addresss);
		if (subject == null) {
			message.setSubject("");
		} else {
			message.setSubject(subject);
		}
		if (text == null) {
			message.setText("");
		} else {
			message.setText(text);
		}
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
		// Transport.send(message);
	}
}
