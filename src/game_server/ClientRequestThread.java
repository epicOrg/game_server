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

			// TODO DEBUG: Client request
			System.out.println("CLIENT: " + request);

			while (!socket.isClosed()) {

				if (request == null)
					return;

				JSONObject jsonRequest = new JSONObject(request);

				// Add client IP to json service message
				jsonRequest.put(FieldsNames.IP_ADDRESS, socket.getInetAddress()
						.getHostAddress());

				PrintWriter out = new PrintWriter(socket.getOutputStream(),
						true);

				Service service = requestElaborator.chooseService(jsonRequest);

				String response = service.start();
				out.println(response);
				request = in.readLine();

				// TODO DEBUG: Server response
				System.out.println("SERVER : " + response);

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
