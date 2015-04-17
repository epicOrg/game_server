package game_server;

import data_management.RegisteredUser;
import writer.ILineFormatter;

public class EmailFormatter implements ILineFormatter {

	@Override
	public String formatLine(RegisteredUser user) {
		return user.getEmail() + "\n";
	}
}
