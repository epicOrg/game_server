package game_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import services.Service;
import check_fields.FieldsNames;

/**
 * @author Noris
 * @date 2015/03/26
 */

public class ClientRequestThread implements Runnable {

	private Socket socket;
	private RequestElaborator requestElaborator;

	public ClientRequestThread(Socket socket) {
		super();
		this.socket = socket;
		requestElaborator = new RequestElaborator();
	}

	@Override
	public void run() {

		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			String request = in.readLine();

			// TODO REMOVE (debug print)
			// System.out.println(json.toString());

			while (!socket.isClosed()) {
				if (request != null) {
					JSONObject json = new JSONObject(request);

					// TODO REMOVE (debug print)
					System.out.println(request);

					// Add client IP to json service message
					json.put(FieldsNames.IP_ADDRESS, socket.getInetAddress()
							.getHostAddress());

					PrintWriter out = new PrintWriter(socket.getOutputStream(),
							true);

					Service service = requestElaborator.chooseService(json);
					out.println(service.start());
					request = in.readLine();

					// TODO REMOVE (debug print)
					System.out.println("Reading");
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
