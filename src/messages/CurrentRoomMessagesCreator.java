package messages;

import fields_names.CommonFields;
import fields_names.RoomFields;
import fields_names.RoomsFields;
import fields_names.ServicesFields;
import game.model.Player;
import game.model.Room;
import game.model.Team;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import services.current_room.CurrentRoom;

/**
 * Generates messages related to the {@link CurrentRoom} service.
 * 
 * @author Micieli
 * @date 2015/05/20
 * @see CurrentRoom
 */

public class CurrentRoomMessagesCreator {

	/**
	 * Generates a players list, informing the client about the players who are
	 * in the {@link Room}.
	 * 
	 * @param room
	 *            the room from which take the players list
	 * 
	 * @return the message to send
	 */
	public JSONObject generatePlayersListMessage(Room room) {
		JSONObject response = new JSONObject();

		try {

			response.put(ServicesFields.SERVICE.toString(), ServicesFields.CURRENT_ROOM.toString());
			response.put(ServicesFields.SERVICE_TYPE.toString(), RoomFields.ROOM_PLAYER_LIST.toString());
			response.put(RoomsFields.ROOM_MAX_PLAYERS.toString(), room.getMaxPlayers());

			JSONArray teams = formatTeams(room);
			response.put(RoomFields.ROOM_TEAM.toString(), teams);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * Generates the response after an exit request from a {@link Player}.
	 * 
	 * @param result
	 *            true if the exit succeeded, false otherwise
	 * 
	 * @return the response to send
	 */
	public JSONObject generateExitResponse(boolean result) {
		JSONObject response = new JSONObject();

		try {

			response.put(ServicesFields.SERVICE.toString(), ServicesFields.CURRENT_ROOM.toString());
			response.put(ServicesFields.SERVICE_TYPE.toString(), RoomFields.ROOM_ACTIONS.toString());
			response.put(RoomFields.ROOM_ACTION.toString(), RoomFields.ROOM_EXIT.toString());
			response.put(CommonFields.NO_ERRORS.toString(), result);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return response;
	}

	private JSONArray formatTeams(Room room) throws JSONException {
		JSONArray teams = new JSONArray();

		for (Team t : room.getTeamGenerator().getTeams()) {
			JSONObject team = new JSONObject();
			JSONArray players = formatPlayers(t);
			team.put(RoomFields.ROOM_TEAM_COLOR.toString(), t.getTeamColor().getRGB());
			team.put(RoomFields.ROOM_NAME.toString(), t.getTeamName());
			team.put(CommonFields.LIST.toString(), players);
			teams.put(team);
		}
		return teams;
	}

	private JSONArray formatPlayers(Team t) throws JSONException {
		JSONArray players = new JSONArray();
		for (Player p : t.getPlayers()) {
			JSONObject jObject = new JSONObject();
			jObject.put(CommonFields.USERNAME.toString(), p.getUsername());
			players.put(jObject);
		}
		return players;
	}
}
