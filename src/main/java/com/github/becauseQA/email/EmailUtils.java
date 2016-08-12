package com.github.becauseQA.email;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.github.becauseQA.apache.commons.StringUtils;
import com.github.becauseQA.properties.PropertiesUtils;

/**
 * ClassName: EmailUtils  
 * Function: TODO ADD FUNCTION.  
 * Reason: TODO ADD REASON 
 * date: Apr 23, 2016 6:14:54 PM  
 * @author alterhu2020@gmail.com
 * @version 1.0.0
 * @since JDK 1.8
 */
public class EmailUtils {

	private static File emailfile;
	
	private MimeMultipart multipart;
	private BodyPart bodypart;



	public boolean sendEmail(String from, String to, String subject, String bodycontent) {

		PropertiesUtils.setResource(emailfile);
		
		String host = PropertiesUtils.getPropertyString("smtp.host");
		String port = PropertiesUtils.getPropertyString("smtp.port");
		String user = PropertiesUtils.getPropertyString("smtp.username");
		String password = PropertiesUtils.getPropertyString("smtp.password");

		// set these values into properties
		Properties prop = new Properties();
		// prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.host", host);
		prop.put("mail.smtp.port", port);
		// prop.put("mail.debug", "true");
		Session session = null;
		if (!StringUtils.isEmpty(user) || !StringUtils.isEmpty(password)) {
			prop.put("mail.smtp.auth", "true");
			// prop.put("mail.smtp.starttls.enable", "true");
			// prop.put("mail.smtp.ssl.enable","true");
			session = Session.getDefaultInstance(prop, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			});
		} else {
			session = Session.getDefaultInstance(prop);
		}

		try {
			MimeMessage mime = new MimeMessage(session);
			mime.setFrom(new InternetAddress(from));
			mime.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			mime.setSubject(subject);
			mime.setSentDate(new Date());

			// body content
			multipart = new MimeMultipart("related");
			bodypart = new MimeBodyPart();
			bodypart.setHeader("Content-Type", "text/html;charset=UTF-8");
			bodypart.setContent(bodycontent, "text/html;charset=UTF-8");
			multipart.addBodyPart(bodypart);
			System.out.println("complete parsing the html body content");

			// embed images

			// attachments file

			// send the email
			mime.setContent(multipart, "text/html;charset=UTF-8");
			Transport.send(mime);

			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@SuppressWarnings("unused")
	private void addEmailImages(File image,String imageid){
		bodypart = new MimeBodyPart();
		DataSource ds = new FileDataSource(image);
		try {
			bodypart.setDataHandler(new DataHandler(ds));
			bodypart.addHeader("Content-ID", "<"+imageid+">");
			bodypart.setDisposition("inline");
			// bodypart.setFileName("logo.png");
			this.multipart.addBodyPart(bodypart);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// be careful this content must contain with <>
		
	}
	@SuppressWarnings("unused")
	private void addEmailAttachements(File attachment){
		bodypart = new MimeBodyPart();
		DataSource source = new FileDataSource(attachment);
		try {
			bodypart.setDataHandler(new DataHandler(source));
			bodypart.setFileName(attachment.getName());
			multipart.addBodyPart(bodypart);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String receiveEmail(String filter) {

		return null;
	}


	public static void setEmailfile(File emailfile) {
		EmailUtils.emailfile = emailfile;
	}

}
