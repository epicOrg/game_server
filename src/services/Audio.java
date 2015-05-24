package services;

import game.model.Player;

import org.json.JSONException;
import org.json.JSONObject;

import voip.AudioData;
import voip.NetUtils;
import check_fields.FieldsNames;
import data_management.GameDataManager;
import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;

/**
 * 
 * Audio Service acquires audio data needed to initiate Voip communication between client end server.
 * Gets back to the client the connection details and reserves the necessary net resources necessary
 * 
 * @author Luca
 * @date 2015/05/03
 */
public class Audio implements IService {

	@Override
	public JSONObject start(JSONObject request) {

		GameDataManager dataManager = GameDataManager.getInstance();
		int localPort = NetUtils.findFreePort();
		try {

			String roomName = request.getString(FieldsNames.ROOM_NAME);
			String username = request.getString(FieldsNames.USERNAME);
			Player player = dataManager.getRoomByName(roomName)
					.getPlayerByName(username);
			int playerAudioPort = request.getInt(FieldsNames.AUDIO_PORT_CLIENT);
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
		return generateResponse( localPort);
	}

	protected JSONObject generateResponse(int localPort){
		JSONObject response = new JSONObject();
		try {
			response.put(FieldsNames.SERVICE, FieldsNames.AUDIO);
			response.put(FieldsNames.AUDIO_PORT_SERVER, localPort);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public String getName() {
		return FieldsNames.AUDIO;
	}
}
