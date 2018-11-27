package com.safediz.ui.utils;

import java.io.IOException;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class MailUtility {

	private static final String SENDGRID_APIKEY = "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";

	private SendGrid sendgrid = new SendGrid(SENDGRID_APIKEY);

	private String fromMail = "safediz.tech@gmail.com";
	private String fromName = "SAFEDIZ";
	private String sujet = "Notification email from " + fromName;

	public int sendEmail(final String fromEmail, final String fromNames, final String mailTo, final String subject,
			final String bodyMessage) {
		fromMail = fromEmail == null ? fromMail : fromEmail;
		fromName = fromNames == null ? fromName : fromNames;
		sujet = subject == null ? subject : subject;

		Email from = new Email(fromMail, fromName);
		Email to = new Email(mailTo);
		Content content = new Content("text/html", bodyMessage);
		Mail mail = new Mail(from, sujet, to, content);

		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sendgrid.api(request);
			return response.getStatusCode();
		} catch (IOException ex) {
			ExceptionUtils.printRootCauseStackTrace(ex);
		}
		return 0;
	}
}
