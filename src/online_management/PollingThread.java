package online_management;

import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import server.ClientRequestThread;
import data_management.GameDataManager;
import exceptions.NoSuchPlayerException;
import exceptions.UserNotOnlineException;
import fields_names.ServicesFields;

/**
 * Thread who polls the user each polling-time to check if the user is yet
 * online. It checks if the {@link OnlineUser} replied to the polling request,
 * else it sets the user offline and stop the related
 * {@link ClientRequestThread}.
 * 
 * @author Micieli
 * @date 2015/05/11
 */

public class PollingThread extends Thread {

	private static final int POLLING_TIME = 4000;
	private static final int POLLING_DELAY = 4000;

	private OnlineUser onlineUser;
	private Timer timer;

	public PollingThread(OnlineUser onlineUser) {
		super();
		this.onlineUser = onlineUser;
		this.timer = new Timer();
	}

	@Override
	public void run() {
		timer.scheduleAtFixedRate(new PollingTask(), POLLING_DELAY, POLLING_TIME);
	}

	public void shutdown() {
		timer.cancel();
	}

	private String generatePollingMessage() {

		JSONObject pollingRequest = new JSONObject();

		try {

			pollingRequest
					.put(ServicesFields.SERVICE.toString(), ServicesFields.POLLING.toString());

		} catch (JSONException e) {
			e.printStackTrace();
		}

		// TODO DEBUG: polling request
		System.out.println("SERVER: " + pollingRequest);

		return pollingRequest.toString();
	}

	public class PollingTask extends TimerTask {

		@Override
		public void run() {

			PrintWriter printWriter = onlineUser.getOutStream();

			if (onlineUser.isPolled()) {
				printWriter.println(generatePollingMessage());
				onlineUser.setPolled(false);

			} else {

				// TODO DEBUG PRINT
				System.out.println("Polling failed!");

				OnlineManager onlineManager = OnlineManager.getInstance();

				try {
					GameDataManager.getInstance()
							.removePlayerFromAnyRooms(onlineUser.getUsername());
				} catch (NoSuchPlayerException e1) {
					// if the user isn't in any room
					// e1.printStackTrace();
				}

				try {
					onlineManager.setOffline(onlineUser.getUsername(), onlineUser.hashCode());
				} catch (UserNotOnlineException e) {
					e.printStackTrace();
				}

				printWriter.close();
				timer.cancel();
			}
		}
	}
}
