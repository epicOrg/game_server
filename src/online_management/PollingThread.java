package online_management;

import exceptions.UserNotOnlineException;

import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import server.ClientRequestThread;
import check_fields.FieldsNames;

/**
 * Thread that polls the user each polling-time to verify if the user is yet
 * online. It verify if the {@link OnlineUser} replied to the polling request,
 * else it sets the user offline and terminate the related {@link ClientRequestThread}
 * 
 * @author Micieli
 * @date 2015/05/11
 */

public class PollingThread extends Thread {

	private static final int POLLING_TIME = 5;

	private OnlineUser onlineUser;
	private Timer timer;

	public PollingThread(OnlineUser onlineUser) {
		super();
		this.onlineUser = onlineUser;
		this.timer = new Timer();
	}

	@Override
	public void run() {
		timer.scheduleAtFixedRate(new PollingTask(), 0, POLLING_TIME);
	}

	private String generatePollingMessage() {

		JSONObject pollingRequest = new JSONObject();
		try {
			pollingRequest.put(FieldsNames.SERVICE, FieldsNames.POLLING);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return pollingRequest.toString();
	}

	public class PollingTask extends TimerTask {

		@Override
		public void run() {

			PrintWriter printWriter = onlineUser.getOutStream();

			if (onlineUser.isPolled()) {
				printWriter.println(generatePollingMessage());
				onlineUser.setPolled(false);
			}

			else {
				OnlineManager onlineManager = OnlineManager.getInstance();

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
