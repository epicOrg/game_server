package services.rooms.subservices;

import exceptions.FullRoomException;
import exceptions.NoSuchRoomException;
import fields_names.CommonFields;
import fields_names.RoomFields;
import fields_names.RoomsFields;
import game.model.Player;
import game.model.Room;

import java.net.InetAddress;

import messages.RoomsMessagesCreator;
import online_management.OnlineManager;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
import voip.NetUtils;
import data_management.GameDataManager;

/**
 * @author Micieli
 * @date 2015/05/24
 */

public class JoinPlayer implements IService {

	private RoomsMessagesCreator messagesCreator;

	public JoinPlayer() {
		super();
		messagesCreator = new RoomsMessagesCreator();
	}

	@Override
	public JSONObject start(JSONObject request) {

		Player player = null;
		String roomName = null;

		try {

			player = new Player(request.getString(CommonFields.USERNAME.toString()));
			InetAddress address = OnlineManager.getInstance().getIpAddressByUsername(
					player.getUsername());
			player.getAudioData().setIp(NetUtils.getIpByInetAddress(address));
			roomName = request.getString(RoomFields.ROOM_NAME.toString());

		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {

			Room room = GameDataManager.getInstance().getRoomByName(roomName);
			room.addPlayer(player);
			return messagesCreator.generateJoinResponse(true, roomName);

		} catch (NoSuchRoomException | FullRoomException e) {
			e.printStackTrace();
			return messagesCreator.generateJoinResponse(false, roomName);
		}
	}

	@Override
	public String getName() {
		return RoomsFields.ROOM_JOIN.toString();
	}
}
