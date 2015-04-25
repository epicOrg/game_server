package game_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import online_management.OnlineManager;

import org.json.JSONException;
import org.json.JSONObject;

import connection_encryption.SecureConnectionApplicator;
import services.IService;
import check_fields.FieldsNames;

/**
 * @author Noris
 * @date 2015/03/26
 */

public class ClientRequestThread implements Runnable {

	private Socket socket;
	private SecureConnectionApplicator secureConnection;
	private RequestElaborator requestElaborator;

	public ClientRequestThread(Socket socket) {
		super();
		this.socket = socket;
		secureConnection = new SecureConnectionApplicator();
		requestElaborator = new RequestElaborator();
	}

	@Override
	public void run() {
		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			OnlineManager onlineManager = OnlineManager.getInstance();
			onlineManager.addStream(socket.getLocalPort(), out);

			String request = in.readLine();

			while (!socket.isClosed()) {
				if (request == null)
					return;

				// TODO DEBUG: client request
				System.out.println("CLIENT: " + request);

				JSONObject jsonEncryptedRequest = new JSONObject(request);
				JSONObject jsonRequest = new JSONObject(
						secureConnection.decrypt(jsonEncryptedRequest));

				jsonRequest.put(FieldsNames.IP_ADDRESS, socket.getInetAddress()
						.getHostAddress());
				jsonRequest.put(FieldsNames.LOCAL_PORT, socket.getInetAddress()
						.getHostAddress());

				IService service = requestElaborator.chooseService(jsonRequest);

				String response = secureConnection.encrypt(service.start())
						.toString();

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
