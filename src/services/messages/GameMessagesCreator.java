package services.messages;

import game.Player;
import game.Room;
import game.Team;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;

public class GameMessagesCreator {

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
		JSONObject jObjectPos = new JSONObject();
		
		try {
			jObjectPos.put(FieldsNames.GAME_X, p.getPlayerStatus().getxPosition());
			jObjectPos.put(FieldsNames.GAME_Y, p.getPlayerStatus().getyPosition());
			jObjectPos.put(FieldsNames.GAME_Z, p.getPlayerStatus().getzPosition());
			JSONObject jObjectDir = new JSONObject();
			jObjectDir.put(FieldsNames.GAME_X, p.getPlayerStatus().getxDirection());
			jObjectDir.put(FieldsNames.GAME_Y, p.getPlayerStatus().getyDirection());
			jObjectDir.put(FieldsNames.GAME_Z, p.getPlayerStatus().getzDirection());
			jPlayer.put(FieldsNames.GAME_POSITION, jObjectPos);
			jPlayer.put(FieldsNames.GAME_DIRECTION, jObjectDir);
			jPlayer.put(FieldsNames.USERNAME, p.getUsername());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jPlayer;
	}
}
