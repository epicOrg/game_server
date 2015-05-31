package services;

import org.json.JSONException;
import org.json.JSONObject;

import voip.AudioData;
import voip.NetUtils;
import data_management.GameDataManager;
import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;
import fields_names.AudioFields;
import fields_names.CommonFields;
import fields_names.RoomFields;
import fields_names.ServicesFields;
import game.model.Player;

/**
 * Audio Service acquires audio data needed to initiate VOIP communication
 * between client end server. Gets back to the client the connection details and
 * reserves the necessary net resources necessary.
 * 
 * @author Micieli
 * @date 2015/05/03
 */

public class Audio implements IService {

	@Override
	public JSONObject start(JSONObject request) {

		GameDataManager dataManager = GameDataManager.getInstance();
		int localPort = NetUtils.findFreePort();

		try {

			String roomName = request.getString(RoomFields.ROOM_NAME.toString());
			String username = request.getString(CommonFields.USERNAME.toString());
			Player player = dataManager.getRoomByName(roomName).getPlayerByName(username);
			int playerAudioPort = request.getInt(AudioFields.AUDIO_PORT_CLIENT.toString());
			AudioData audioData = player.getAudioData();
			audioData.setLocalPort(localPort);
			int localControlPort = NetUtils.findFreePort();
			audioData.setLocalControlPort(localControlPort);
			audioData.setRemotePort(playerAudioPort);

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NoSuchPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchRoomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return generateResponse(localPort);
	}

	protected JSONObject generateResponse(int localPort) {

		JSONObject response = new JSONObject();

		try {
			response.put(ServicesFields.SERVICE.toString(), ServicesFields.AUDIO.toString());
			response.put(AudioFields.AUDIO_PORT_SERVER.toString(), localPort);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return response;
	}

	@Override
	public String getName() {
		return ServicesFields.AUDIO.toString();
	}
}
