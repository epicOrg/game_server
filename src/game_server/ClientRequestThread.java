package game_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import online_management.OnlineManager;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
import check_fields.FieldsNames;
import connection_encryption.SecureConnectionApplicator;

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
			
			// TODO DEBUG: port
			System.out.println("LocalP1: " + socket.getLocalPort());
			
			onlineManager.addStream(socket.getLocalPort(), out);

			String request = in.readLine();

			while (!socket.isClosed()) {
				if (request == null)
					return;

				// TODO DEBUG: client request
				System.out.println("CLIENT: " + request);

				JSONObject jsonEncryptedRequest = new JSONObject(request);
				JSONObject jsonRequest = secureConnection.decrypt(jsonEncryptedRequest);

				InetSocketAddress inetSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
				jsonRequest.put(FieldsNames.IP_ADDRESS, inetSocketAddress.getHostName());
				jsonRequest.put(FieldsNames.LOCAL_PORT, socket.getLocalPort());

				IService service = requestElaborator.chooseService(jsonRequest);
				String response = secureConnection.encrypt(service.start()).toString();

				out.println(response);

				// TODO DEBUG: server response
				System.out.println("SERVER: " + response);

				request = in.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
