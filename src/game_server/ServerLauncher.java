package game_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Noris
 * @date 2015/03/26
 */

public class ServerLauncher {

	public static final int PORT = 7007;

	public static void main(String[] args) {
		try {
			
			@SuppressWarnings("resource")
			ServerSocket ServerSocket = new ServerSocket(PORT);
			new ServerInitializer().initDataManager();

			while (true) {
				Socket socket = ServerSocket.accept();
				
				Thread thread = new Thread(new ClientRequestThread(socket));
				
				// TODO DEBUG: ID of the thread
				System.out.println("> Thread ID: " + thread.getId());

				thread.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}