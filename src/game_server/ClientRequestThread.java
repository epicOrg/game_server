package game_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

import services.Service;

/**
 * @author	Noris
 * @since	2015-03-26
 */

public class ClientRequestThread implements Runnable {
	
	private Socket socket;

	public ClientRequestThread(Socket socket) {
		super();
		this.socket = socket;
	}

	@Override
	public void run() {
		
		try {
		
			BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			
			String string = in.readLine();
			JSONObject json = new JSONObject(string);
			
			RequestElaborator requestElaborator = new RequestElaborator(json);
			
			OutputStreamWriter out = new OutputStreamWriter(
					socket.getOutputStream(), Charset.forName("UTF-8").newEncoder());
			
			Service service = requestElaborator.setService();
			out.write(service.start());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
