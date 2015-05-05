package email_confirmation;

import java.util.Scanner;

import javax.mail.Message.RecipientType;

import org.codemonkey.simplejavamail.Email;
import org.codemonkey.simplejavamail.Mailer;
import org.codemonkey.simplejavamail.TransportStrategy;

/**
 * @author Noris
 * @date 2015/05/
 */

public class EmailSender {

	private static EmailSender instance = new EmailSender();

	private static Mailer mailer;
	private static String server;
	private static int port;

	private static String senderName;
	private static String senderEmail;

	public static EmailSender getInstance() {
		return instance;
	}

	public static void setServer(String server, int port) {
		EmailSender.server = server;
		EmailSender.port = port;
	}

	public static void auth(String username, String password) {
		EmailSender.mailer = new Mailer(server, port, username, password,
				TransportStrategy.SMTP_TLS);

	}

	public static void setSender(String name, String email) {
		EmailSender.senderName = name;
		EmailSender.senderEmail = email;
	}

	public void sendEmail(String recipient, String subject, String text) {

		Scanner scanner = new Scanner(recipient);
		scanner.useDelimiter("@");

		sendEmail(scanner.next(), recipient, subject, text);

		scanner.close();
	}

	public void sendEmail(String recipientUsername, String recipientEmail,
			String subject, String text) {

		Email email = new Email();
		email.addRecipient(recipientUsername, recipientEmail, RecipientType.TO);
		email.setFromAddress(senderName, senderEmail);
		email.setSubject(subject);
		email.setText(text);

		mailer.sendMail(email);
	}

}
