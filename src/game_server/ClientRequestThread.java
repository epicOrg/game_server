package game_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
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
			System.out.println("CLIENT: " + request);

			while (!socket.isClosed()) {
				if (request == null)
					return;

				// TODO DEBUG: client request
				System.out.println("CLIENT: " + request);

				JSONObject jsonRequest = new JSONObject(request);
				jsonRequest.put(FieldsNames.IP_ADDRESS, socket.getInetAddress()
						.getHostAddress());

				PrintWriter out = new PrintWriter(socket.getOutputStream(),
						true);
				IService service = requestElaborator.chooseService(jsonRequest);

				String response = service.start();
				out.println(response);

				// TODO DEBUG: server response
				System.out.println("SERVER: " + response);

				request = in.readLine();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
		}
	}

}
