package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import messages.fields_names.CommonFields;
import messages.fields_names.ServicesFields;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
import services.Login;
import check_fields.RequestFieldChecher;
import connection_encryption.SecureConnectionApplicator;

/**
 * A client dedicated thread that listen for client request and run server
 * Services for him.
 * 
 * @author Noris
 * @date 2015/03/26
 */

public class ClientRequestThread implements Runnable {

	private Socket socket;
	private SecureConnectionApplicator secureConnection;
	private ServiceChooser serviceChooser;
	private RequestFieldChecher cecker;
	private PrintWriter out;

	public ClientRequestThread(Socket socket) {
		super();

		this.socket = socket;
		secureConnection = new SecureConnectionApplicator();
		serviceChooser = new ServiceChooser();
		cecker = new RequestFieldChecher();
	}

	@Override
	public void run() {
		try {

			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			out = new PrintWriter(socket.getOutputStream(), true);

			String request = in.readLine();

			while (!socket.isClosed()) {
				if (request == null) {

					// TODO DEBUG: null client request
					System.out.println("CLIENT: null request");

					return;
				}

				// TODO DEBUG: encrypted client request
				// System.out.println("CLIENT: " + request);

				// JSONObject jsonRequest = new JSONObject(request);
				JSONObject jsonRequest = secureConnection.decrypt(request);

				// TODO DEBUG: not encrypted client request
				System.out.println("CLIENT: " + jsonRequest);

				JSONObject jsonResponse = null;

				if (cecker.checkRequest(jsonRequest)) {
					jsonResponse = elaborateRequest(jsonRequest);
				}

				if (jsonResponse != null) {

					// TODO DEBUG: not encrypted server response
					System.out.println("SERVER: " + jsonResponse);

					// String response = jsonResponse.toString();
					String response = secureConnection.encrypt(jsonResponse);

					out.println(response);

					// TODO DEBUG: encrypted server response
					// System.out.println("SERVER: " + response);

				} else {
					// System.out.println("SERVER: " + "no response");
				}

				request = in.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Connection closed by the client.");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private JSONObject elaborateRequest(JSONObject jsonRequest)
			throws JSONException {

		InetSocketAddress inetSocketAddress = (InetSocketAddress) socket
				.getRemoteSocketAddress();

		jsonRequest.put(CommonFields.IP_ADDRESS.toString(),
				inetSocketAddress.getHostName());
		jsonRequest.put(CommonFields.LOCAL_PORT.toString(),
				socket.getLocalPort());

		IService service;
		if (jsonRequest.getString(ServicesFields.SERVICE.toString()).equals(
				ServicesFields.LOGIN.toString())) {
			service = new Login(out);
		} else {
			service = serviceChooser.chooseService(jsonRequest);
		}

		JSONObject jResponse = service.start(jsonRequest);
		return jResponse;
	}

}
