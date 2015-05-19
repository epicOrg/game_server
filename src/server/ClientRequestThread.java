package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
import services.Login;
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

			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			// TODO DEBUG: port
			System.out.println("Local Port: " + socket.getLocalPort());

			String request = in.readLine();

			while (!socket.isClosed()) {
				if (request == null)
					return;

				// TODO DEBUG: client request
				// System.out.println("CLIENT: " + request);

				JSONObject jsonEncryptedRequest = new JSONObject(request);
				JSONObject jsonRequest = secureConnection.decrypt(jsonEncryptedRequest);

				InetSocketAddress inetSocketAddress = (InetSocketAddress) socket
						.getRemoteSocketAddress();
				jsonRequest.put(FieldsNames.IP_ADDRESS, inetSocketAddress.getHostName());
				jsonRequest.put(FieldsNames.LOCAL_PORT, socket.getLocalPort());

				IService service;
				if (jsonRequest.getString(FieldsNames.SERVICE).equals(FieldsNames.LOGIN)) {
					service = new Login(out);
					service.setRequest(jsonRequest);
				} else {
					service = requestElaborator.chooseService(jsonRequest);
				}

				JSONObject jResponse;
				if ((jResponse = service.start()) != null) {
					String response = secureConnection.encrypt(jResponse).toString();

					out.println(response);

					// TODO DEBUG: server response
					// System.out.println("SERVER: " + response);
				} else {
					System.out.println("SERVER: " + "No response.");
				}

				request = in.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
