package messages;

import fields_names.CommonFields;
import fields_names.GameFields;
import fields_names.ServicesFields;
import game.map.MapJSONizer;
import game.model.Player;
import game.model.PlayerStatus;
import game.model.Room;
import game.model.Team;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import services.game.Game;

/**
 * Client message generator for the {@link Game} service.
 * 
 * @author Micieli
 * @date 2015/05/21
 */
public class GameMessagesCreator {

	/**
	 * Generates a message containing all the positions of currently playing
	 * players of a room. It excludes the receiver from the message.
	 * 
	 * @param username
	 *            the username of the receiver client
	 * @param room
	 *            the client current room from which he wants updates
	 * @return a response message ready to be sent
	 */
	public JSONObject generatePositionMessage(String username, Room room) {

		JSONObject response = new JSONObject();

		try {

			response.put(ServicesFields.SERVICE.toString(), ServicesFields.GAME.toString());
			response.put(ServicesFields.SERVICE_TYPE.toString(),
					GameFields.GAME_POSITIONS.toString());

			JSONArray jPlayers = createPositionsList(username, room);

			response.put(GameFields.GAME_PLAYERS.toString(), jPlayers);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return response;
	}

	private JSONArray createPositionsList(String username, Room room) {
		JSONArray jPlayers = new JSONArray();

		ArrayList<Player> players = new ArrayList<Player>();
		for (Team t : room.getTeamManager().getTeams()) {
			players.addAll(t.getPlayers());
		}

		for (Player p : players) {
			if (p.getUsername().equals(username))
				continue;

			jPlayers.put(formatPlayer(p));
		}
		
		return jPlayers;
	}

	private JSONObject formatPlayer(Player p) {

		JSONObject jPlayer = new JSONObject();
		PlayerStatus status = p.getPlayerStatus();

		try {

			JSONObject jsonPosition = formatPosition(status);
			JSONObject jsonDirection = formatDirection(status);
			jPlayer.put(GameFields.GAME_POSITION.toString(), jsonPosition);
			jPlayer.put(GameFields.GAME_DIRECTION.toString(), jsonDirection);
			jPlayer.put(CommonFields.USERNAME.toString(), p.getUsername());

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jPlayer;
	}

	private JSONObject formatDirection(PlayerStatus status) throws JSONException {
		JSONObject jsonDirection = new JSONObject();
		jsonDirection.put(GameFields.GAME_X.toString(), status.getxDirection());
		jsonDirection.put(GameFields.GAME_Y.toString(), status.getyDirection());
		jsonDirection.put(GameFields.GAME_Z.toString(), status.getzDirection());
		return jsonDirection;
	}

	private JSONObject formatPosition(PlayerStatus status) throws JSONException {
		JSONObject jsonPosition = new JSONObject();
		jsonPosition.put(GameFields.GAME_X.toString(), status.getxPosition());
		jsonPosition.put(GameFields.GAME_Y.toString(), status.getyPosition());
		jsonPosition.put(GameFields.GAME_Z.toString(), status.getzPosition());
		return jsonPosition;
	}

	/**
	 * Creates a message containing the description of a map and the spawn
	 * point.
	 * 
	 * @param map
	 *            a <code>JSONObject</code> containing the map
	 * @param spawnPoint
	 *            a <code>PlayerStatus</code> containing the initial position
	 * @see PlayerStatus
	 * @see MapJSONizer
	 */
	public JSONObject generateMapMessage(JSONObject map, PlayerStatus spawnPoint) {

		JSONObject message = new JSONObject();

		try {
			message.put(ServicesFields.SERVICE.toString(), ServicesFields.GAME.toString());
			message.put(ServicesFields.SERVICE_TYPE.toString(), GameFields.GAME_MAP.toString());

			for (String key : JSONObject.getNames(map)) {
				message.put(key, map.get(key));
			}

			message.put(GameFields.GAME_PLAYER_POSITION.toString(), spawnPoint.toStringPosition());

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return message;
	}

}
