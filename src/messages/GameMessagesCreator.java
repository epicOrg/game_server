package messages;

import game.map.MapJSONizer;
import game.model.Player;
import game.model.PlayerStatus;
import game.model.Room;
import game.model.Team;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import services.Game;
import check_fields.FieldsNames;

/**
 * 
 * Client message generator for the related <code>Service</code> {@link Game}
 * 
 * @author Luca
 *
 */
public class GameMessagesCreator {

	/**
	 * 
	 * Generates the message containing all the players position currently playing in a room.
	 * It exclude the receiver from the message
	 * 
	 * @param username 		the username of the client receiver
	 * @param room			the client current room from which he wants updates
	 * @return				complete message response ready to be sent
	 */
	public JSONObject generatePositionMessage(String username, Room room) {
		JSONObject response = new JSONObject();
		try {

			response.put(FieldsNames.SERVICE, FieldsNames.GAME);
			response.put(FieldsNames.SERVICE_TYPE, FieldsNames.GAME_POSITIONS);

			JSONArray jPlayers = new JSONArray();

			ArrayList<Player> players = new ArrayList<Player>();
			for (Team t : room.getTeamGenerator().getTeams()) {
				players.addAll(t.getPlayers());
			}

			for (Player p : players) {
				if (p.getUsername().equals(username))
					continue;		

				jPlayers.put(formatPlayer(p));
			}

			response.put(FieldsNames.GAME_PLAYERS, jPlayers);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return response;
	}
	
	private JSONObject formatPlayer(Player p){
		
		JSONObject jPlayer = new JSONObject();
		PlayerStatus status = p.getPlayerStatus();		
		
		try {
			JSONObject jObjectPos = formatPosition(status);
			JSONObject jObjectDir = formatDirection(status);
			jPlayer.put(FieldsNames.GAME_POSITION, jObjectPos);
			jPlayer.put(FieldsNames.GAME_DIRECTION, jObjectDir);
			jPlayer.put(FieldsNames.USERNAME, p.getUsername());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jPlayer;
	}

	private JSONObject formatDirection(PlayerStatus status)
			throws JSONException {
		JSONObject jObjectDir = new JSONObject();
		jObjectDir.put(FieldsNames.GAME_X, status.getxDirection());
		jObjectDir.put(FieldsNames.GAME_Y, status.getyDirection());
		jObjectDir.put(FieldsNames.GAME_Z, status.getzDirection());
		return jObjectDir;
	}

	private JSONObject formatPosition(PlayerStatus status) throws JSONException {
		JSONObject jObjectPos = new JSONObject();
		jObjectPos.put(FieldsNames.GAME_X, status.getxPosition());
		jObjectPos.put(FieldsNames.GAME_Y, status.getyPosition());
		jObjectPos.put(FieldsNames.GAME_Z, status.getzPosition());
		return jObjectPos;
	}
	/**
	 * 
	 * Creates a message containing the description of a map 
	 * end the point in which the player is at the beginning of the game.
	 * 
	 * @param map						a <code>JSONObject</code> containing the map
	 * @param playerInitialPosition		a <code>PlayerStatus</code> containing the initial position
	 * @return
	 * @see PlayerStatus
	 * @see MapJSONizer
	 */
	public JSONObject generateMapMessage(JSONObject map, PlayerStatus playerInitialPosition){
		JSONObject message = new JSONObject();
		
		try {
			message.put(FieldsNames.SERVICE, FieldsNames.GAME);
			message.put(FieldsNames.SERVICE_TYPE, FieldsNames.GAME_MAP);
			
			for (String key : JSONObject.getNames(map)) {
				message.put(key, map.get(key));
			}

			message.put(FieldsNames.GAME_PLAYER_POSITION, playerInitialPosition.toStringPosition());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return message;
	}
	
}
