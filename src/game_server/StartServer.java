package game_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class StartServer {

	public static final int PORT = 7007;
	
	public static void main(String[] args) {
		
		try {
			
			@SuppressWarnings("resource")
			final ServerSocket ServerSocket = new ServerSocket(PORT);
			
			while(true) {
				
				final Socket socket = ServerSocket.accept();			
				
				Thread thread = new Thread(new ClientRequestThread(socket));
				thread.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}