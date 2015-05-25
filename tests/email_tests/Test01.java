package email_tests;

import email_confirmation.EmailSender;

/**
 * Send an email from Gmail server to Mailnesia server. Check here the sending
 * email: <http://mailnesia.com/mailbox/epicprova>.
 * 
 * @author Noris
 * @date 2015/05/05
 * @see EmailSender
 */

class Test01 {

	private static final String USERNAME = "INSERT A VALID GMAIL USERNAME";
	private static final String PASSWORD = "INSERT A VALID GMAIL PASSWORD";

	public static void main(String[] args) {

		EmailSender sender = EmailSender.getInstance();
		EmailSender.setServer("smtp.gmail.com", 587);
		EmailSender.auth(USERNAME, PASSWORD);
		EmailSender.setSender("EpicORG", USERNAME);

		String emailObject = "TROLL aus dieser Thatsache hervor!";

		String emailText = "Ein Gespenst geht um in Europa – das Gespenst des K"
				+ "ommunismus. Alle Mächte des alten Europa haben sich zu einer"
				+ "heiligen Hetzjagd gegen dies Gespenst verbündet, der Papst u"
				+ "nd der Czar, Metternich und Guizot, französische Radikale un"
				+ "d deutsche Polizisten.";

		sender.sendEmail("epicprova@mailnesia.com", emailObject, emailText);
	}
}
