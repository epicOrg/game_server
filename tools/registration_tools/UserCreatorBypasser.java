package registration_tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import data_management.password_encrypter.SHA512StringEncrypter;
import data_management.password_encrypter.StringEncrypter;
import database.Paths;

/**
 * Create user bypassing the checking function. Note: actually you can't login
 * with invalids fields in the client (it check the login fields like the
 * registration fields).
 * 
 * @author Noris
 * @date 2015/05/05
 */

class UserCreatorBypasser {

	public static void main(String[] args) throws IOException {

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.print("Username: ");
		String username = scanner.nextLine();

		System.out.print("Password: ");
		StringEncrypter encrypter = new SHA512StringEncrypter();
		String password = encrypter.encryptString(scanner.nextLine());

		System.out.print("Email: ");
		String email = scanner.nextLine();

		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
				Paths.getUsersPath() + username)));

		writer.write(username + " " + password + " " + email);
		writer.close();

	}
}
