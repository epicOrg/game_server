package services.rooms.subservices;

import data_management.GameDataManager;
import exceptions.FullRoomException;
import exceptions.NoSuchRoomException;
import game.model.Player;
import game.model.Room;

import java.net.InetAddress;

import messages.RoomsMessagesCreator;
import online_management.OnlineManager;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import services.IService;
import voip.NetUtils;

public class JoinPlayer implements IService{
	
	private RoomsMessagesCreator messagesCreator;

	public JoinPlayer() {
		super();
		messagesCreator = new RoomsMessagesCreator();
	}

	@Override
	public JSONObject start(JSONObject request) {
		System.out.println(getName());
		Player player = null;
		String roomName = null;

		try {

			player = new Player(request.getString(FieldsNames.USERNAME));
			InetAddress address = OnlineManager.getInstance()
					.getIpAddressByUsername(player.getUsername());
			player.getAudioData().setIp(NetUtils.getIpByInetAddress(address));
			roomName = request.getString(FieldsNames.ROOM_NAME);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {

			Room room = GameDataManager.getInstance().getRoomByName(roomName);			
			room.addPlayer(player);
			return messagesCreator.generateJoinResponse(true, roomName);

		} catch (NoSuchRoomException | FullRoomException e ) {
			e.printStackTrace();
			return messagesCreator.generateJoinResponse(false, roomName);	
		} 
	}

	@Override
	public String getName() {
		return FieldsNames.ROOM_JOIN;
	}
}
