/**
 * 
 */
package com.forms.prms.tool;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * @author form
 *
 */
public class EmailSender {

	String host = ""; // smtp主机
	String to = ""; // 收件人
	String from = ""; // 发件人
	String userName = ""; // 用户名
	String password = ""; // 密码
	String subject = ""; // 邮件主题
	String content = ""; // 邮件正文

	public void setHost(String host) {
		this.host = host;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public EmailSender() {

	}

	public EmailSender(String host, String to, String from, String userName,
			String password, String subject, String content) {
		this.host = host;
		this.to = to;
		this.from = from;
		this.userName = userName;
		this.password = password;
		this.subject = subject;
		this.content = content;
	}

	/**
	 * 发送邮件
	 * 
	 * @return 成功返回true，失败返回false
	 * @throws Exception
	 */
	public boolean sendMail() throws Exception {
		// 构造mail session
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", host);
		props.setProperty("mail.smtp.port", "25");
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.auth", "true");
		Session session = Session.getInstance(props, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});

		try {
			// 构造MimeMessage并设定基本的值，创建消息对象
			MimeMessage msg = new MimeMessage(session);

			// 设置消息内容
			msg.setFrom(new InternetAddress(from));
			// 把邮件地址映射到Internet地址上
			InternetAddress[] address = { new InternetAddress(to) };
			/**
			 * setRecipient（Message.RecipientType type, Address
			 * address），用于设置邮件的接收者。<br>
			 * 有两个参数，第一个参数是接收者的类型，第二个参数是接收者。<br>
			 * 接收者类型可以是Message.RecipientType .TO，Message
			 * .RecipientType.CC和Message.RecipientType.BCC，TO表示主要接收人，CC表示抄送人
			 * ，BCC表示秘密抄送人。接收者与发送者一样，通常使用InternetAddress的对象。
			 */
			msg.setRecipients(Message.RecipientType.TO, address);
			// 设置邮件的标题
			subject = transferChinese(subject);
			msg.setSubject(subject);

			// 向Multipart添加正文
			MimeBodyPart mbp = new MimeBodyPart();
			// 设置邮件内容(纯文本格式)
			// mbp.setText(content);
			// 设置邮件内容(HTML格式)
			mbp.setContent(content, "text/html;charset=utf-8");

			// 构造Multipart
			Multipart mp = new MimeMultipart();
			// 向MimeMessage添加（Multipart代表正文）
			mp.addBodyPart(mbp);

			// 向Multipart添加MimeMessage
			msg.setContent(mp);

			// 设置邮件发送的时间。
			msg.setSentDate(new Date());

			// 发送邮件
			Transport.send(msg);
		} catch (Exception e) {
			throw e;
		}
		return true;
	}

	/**
	 * 把主题转换为中文
	 * 
	 * @param strText
	 * @return
	 * @throws Exception
	 */
	public String transferChinese(String strText) throws Exception {

		try {
			strText = MimeUtility.encodeText(new String(strText.getBytes(),
					"GB2312"), "GB2312", "B");
		} catch (Exception e) {
			throw e;
		}

		return strText;
	}

}
