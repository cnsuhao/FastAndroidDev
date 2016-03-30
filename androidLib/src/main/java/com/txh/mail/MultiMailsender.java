package com.txh.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MultiMailsender {

	public boolean sendTextMail(MultiMailSenderInfo mailInfo) {

		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		Session sendMailSession = Session
				.getDefaultInstance(pro, authenticator);
		try {
			Message mailMessage = new MimeMessage(sendMailSession);
			Address from = new InternetAddress(mailInfo.getFromAddress());
			mailMessage.setFrom(from);
			Address[] tos = null;
			String[] receivers = mailInfo.getReceivers();
			if (receivers != null) {
				tos = new InternetAddress[receivers.length + 1];
				tos[0] = new InternetAddress(mailInfo.getToAddress());
				for (int i = 0; i < receivers.length; i++) {
					tos[i + 1] = new InternetAddress(receivers[i]);
				}
			} else {
				tos = new InternetAddress[1];
				tos[0] = new InternetAddress(mailInfo.getToAddress());
			}

			mailMessage.setRecipients(Message.RecipientType.TO, tos);
			mailMessage.setSubject(mailInfo.getSubject());
			mailMessage.setSentDate(new Date());
			String mailContent = mailInfo.getContent();
			mailMessage.setText(mailContent);
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static boolean sendMailtoMultiReceiver(MultiMailSenderInfo mailInfo) {
		MyAuthenticator authenticator = null;
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		Session sendMailSession = Session.getInstance(mailInfo.getProperties(),
				authenticator);
		try {
			Message mailMessage = new MimeMessage(sendMailSession);
			Address from = new InternetAddress(mailInfo.getFromAddress());
			mailMessage.setFrom(from);
			Address[] tos = null;
			String[] receivers = mailInfo.getReceivers();
			if (receivers != null) {
				tos = new InternetAddress[receivers.length + 1];
				tos[0] = new InternetAddress(mailInfo.getToAddress());
				for (int i = 0; i < receivers.length; i++) {
					tos[i + 1] = new InternetAddress(receivers[i]);
				}
			} else {
				tos = new InternetAddress[1];
				tos[0] = new InternetAddress(mailInfo.getToAddress());
			}
			mailMessage.setRecipients(Message.RecipientType.TO, tos);

			mailMessage.setSubject(mailInfo.getSubject());
			mailMessage.setSentDate(new Date());
			Multipart mainPart = new MimeMultipart();
			BodyPart html = new MimeBodyPart();
			html.setContent(mailInfo.getContent(), "text/html; charset=GBK");
			mainPart.addBodyPart(html);
			mailMessage.setContent(mainPart);
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static boolean sendMailtoMultiCC(MultiMailSenderInfo mailInfo) {
		MyAuthenticator authenticator = null;
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		Session sendMailSession = Session.getInstance(mailInfo.getProperties(),
				authenticator);
		try {
			Message mailMessage = new MimeMessage(sendMailSession);
			Address from = new InternetAddress(mailInfo.getFromAddress());
			mailMessage.setFrom(from);
			Address to = new InternetAddress(mailInfo.getToAddress());
			mailMessage.setRecipient(Message.RecipientType.TO, to);

			String[] ccs = mailInfo.getCcs();
			if (ccs != null) {
				Address[] ccAdresses = new InternetAddress[ccs.length];
				for (int i = 0; i < ccs.length; i++) {
					ccAdresses[i] = new InternetAddress(ccs[i]);
				}
				mailMessage.setRecipients(Message.RecipientType.CC, ccAdresses);
			}

			mailMessage.setSubject(mailInfo.getSubject());
			mailMessage.setSentDate(new Date());
			Multipart mainPart = new MimeMultipart();
			BodyPart html = new MimeBodyPart();
			html.setContent(mailInfo.getContent(), "text/html; charset=GBK");
			mainPart.addBodyPart(html);
			mailMessage.setContent(mainPart);
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static class MultiMailSenderInfo extends MailSenderInfo {
		private String[] receivers;
		private String[] ccs;

		public String[] getCcs() {
			return ccs;
		}

		public void setCcs(String[] ccs) {
			this.ccs = ccs;
		}

		public String[] getReceivers() {
			return receivers;
		}

		public void setReceivers(String[] receivers) {
			this.receivers = receivers;
		}
	}
}