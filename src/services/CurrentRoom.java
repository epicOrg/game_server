package services;

import game.Player;
import game.Room;
import game.Team;
import game.map.IMapGenerator;
import game.map.MapObject;
import game.map.SimpleMapGenerator;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import data_management.GameDataManager;
import exceptions.NoSuchRoomException;

/**
 * @author Torlaschi
 * @date 2015/04/18
 */

public class CurrentRoom implements Service {

	private JSONObject jsonRequest;
	private JSONObject jsonResponse;

	private GameDataManager gameDataManager;

	public CurrentRoom(JSONObject json) {
		jsonRequest = json;
		jsonResponse = new JSONObject();
		
		gameDataManager = GameDataManager.getInstance();
	}

	@Override
	public String start() {
		try {
			runService(jsonRequest.getString(FieldsNames.SERVICE_TYPE));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonResponse.toString();
	}

	private void runService(String serviceType) throws JSONException {
		switch (serviceType) {
		case FieldsNames.ROOM_PLAYER_LIST:	
			generatePlayerListResponse();
			break;
		}
	}

	private void generatePlayerListResponse(){		
		try {
			String roomName = jsonRequest.getString(FieldsNames.ROOM_NAME);
			Room room = gameDataManager.getRoomByName(roomName);
			jsonResponse.put(FieldsNames.SERVICE, FieldsNames.CURRENT_ROOM);
			jsonResponse.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOM_PLAYER_LIST);
			
			JSONArray teams = new JSONArray();
			for(Team t : room.getTeamGenerator().getTeams()){
				JSONObject team = new JSONObject();
				JSONArray players = new JSONArray();
				
				for(Map.Entry<String, Player> e : t.getPlayers().entrySet()){
					JSONObject jObject = new JSONObject();
					jObject.put(FieldsNames.NAME, e.getValue().getUsername());
					players.put(jObject);
				}
				
				team.put(FieldsNames.ROOM_TEAM_COLOR, t.getTeamColor());
				team.put(FieldsNames.ROOM_NAME, t.getTeamColor());
				team.put(FieldsNames.LIST, players);				
				teams.put(team);
			}
			jsonResponse.put(FieldsNames.ROOM_TEAM, teams);
		} catch (JSONException | NoSuchRoomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
