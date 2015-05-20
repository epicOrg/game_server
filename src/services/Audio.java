package services;

import game.Player;

import org.json.JSONException;
import org.json.JSONObject;

import voip.AudioData;
import voip.NetUtils;
import check_fields.FieldsNames;
import data_management.GameDataManager;
import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;

/**
 * @author Luca
 * @date 2015/05/03
 */

public class Audio implements IService {

	private JSONObject request;

	@Override
	public JSONObject start(JSONObject request) {
		this.request = request;
		
		JSONObject response = new JSONObject();

		GameDataManager dataManager = GameDataManager.getInstance();

		try {

			String roomName = request.getString(FieldsNames.ROOM_NAME);
			String username = request.getString(FieldsNames.USERNAME);
			Player player = dataManager.getRoomByName(roomName)
					.getPlayerByName(username);
			int playerAudioPort = request.getInt(FieldsNames.AUDIO_PORT_CLIENT);
			AudioData audioData = player.getAudioData();
			int localPort = NetUtils.findFreePort();
			audioData.setLocalPort(localPort);
			int localControlPort = NetUtils.findFreePort();
			audioData.setLocalControlPort(localControlPort);
			audioData.setRemotePort(playerAudioPort);

			response.put(FieldsNames.SERVICE, FieldsNames.AUDIO);
			response.put(FieldsNames.AUDIO_PORT_SERVER, localPort);

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NoSuchPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchRoomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
}
