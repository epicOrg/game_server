package services.game.subservices;

import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;
import game.model.Player;
import game.model.PlayerStatus;
import game.model.Room;
import messages.GameMessagesCreator;
import messages.fields_names.CommonFields;
import messages.fields_names.GameFields;
import messages.fields_names.RoomFields;

import org.json.JSONException;
import org.json.JSONObject;

import services.IService;
import services.game.Game;
import data_management.GameDataManager;

/**
 * A {@link Game} sub-service. The client has to send to the server, at a fixed
 * rate, his position in the map. This service updates his saved position in the
 * server and also updates the client about the other {@link Player}s positions.
 * 
 * @author Micieli
 * @date 2015/05/24
 * @see PlayerStatus
 */
public class GamePositions implements IService {

	private GameMessagesCreator messagesCreator;

	public GamePositions() {
		super();
		messagesCreator = new GameMessagesCreator();
	}

	@Override
	public JSONObject start(JSONObject request) {

		try {

			String roomName = request.getString(RoomFields.ROOM_NAME.toString());
			Room room = GameDataManager.getInstance().getRoomByName(roomName);
			String username = request.getString(CommonFields.USERNAME.toString());

			UpdatePlayerStatus(username, request, room);
			return messagesCreator.generatePositionMessage(username, room);

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NoSuchPlayerException e) {
			e.printStackTrace();
		} catch (NoSuchRoomException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void UpdatePlayerStatus(String username, JSONObject request, Room room)
			throws JSONException, NoSuchPlayerException {

		JSONObject posObject = request.getJSONObject(GameFields.GAME_POSITION.toString());
		JSONObject dirObject = request.getJSONObject(GameFields.GAME_DIRECTION.toString());
		float xPos = (float) posObject.getDouble(GameFields.GAME_X.toString());
		float yPos = (float) posObject.getDouble(GameFields.GAME_Y.toString());
		float zPos = (float) posObject.getDouble(GameFields.GAME_Z.toString());
		float xDir = (float) dirObject.getDouble(GameFields.GAME_X.toString());
		float yDir = (float) dirObject.getDouble(GameFields.GAME_Y.toString());
		float zDir = (float) dirObject.getDouble(GameFields.GAME_Z.toString());

		Player player = room.getPlayerByName(username);
		player.getPlayerStatus().setPosition(xPos, yPos, zPos);
		player.getPlayerStatus().setDirection(xDir, yDir, zDir);
	}

	@Override
	public String getName() {
		return GameFields.GAME_POSITIONS.toString();
	}
}
