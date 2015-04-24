package services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import data_management.GameDataManager;
import exceptions.MissingFieldException;
import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;
import game.Player;
import game.Room;
import game.Team;

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
		} catch (JSONException | MissingFieldException e) {
			return new MissingFieldException().getMissingFieldError();
		}

		return jsonResponse.toString();
	}

	private void runService(String serviceType) throws MissingFieldException {

		switch (serviceType) {
		case FieldsNames.ROOM_PLAYER_LIST:
			generatePlayerListResponse();
			break;
		case FieldsNames.ROOM_ACTIONS:
			generateActionsResponse();
			break;
		}

	}

	private void generatePlayerListResponse() throws MissingFieldException {

		String roomName;
		try {
			roomName = jsonRequest.getString(FieldsNames.ROOM_NAME);
		} catch (JSONException e) {
			throw new MissingFieldException();
		}

		Room room = null;
		try {
			room = gameDataManager.getRoomByName(roomName);
		} catch (NoSuchRoomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			jsonResponse.put(FieldsNames.SERVICE, FieldsNames.CURRENT_ROOM);
			jsonResponse.put(FieldsNames.SERVICE_TYPE,
					FieldsNames.ROOM_PLAYER_LIST);

			jsonResponse.put(FieldsNames.ROOM_MAX_PLAYERS, Room.MAX_PLAYERS);

			JSONArray teams = new JSONArray();

			for (Team t : room.getTeamGenerator().getTeams()) {
				JSONObject team = new JSONObject();
				JSONArray players = new JSONArray();

				for (Player p : t.getPlayers()) {
					JSONObject jObject = new JSONObject();
					jObject.put(FieldsNames.USERNAME, p.getUsername());
					players.put(jObject);
				}

				team.put(FieldsNames.ROOM_TEAM_COLOR, t.getTeamColor().getRGB());
				team.put(FieldsNames.ROOM_NAME, t.getTeamName());
				team.put(FieldsNames.LIST, players);
				teams.put(team);
			}

			jsonResponse.put(FieldsNames.ROOM_TEAM, teams);

		} catch (JSONException e) {
		}
	}

	private void generateActionsResponse() throws MissingFieldException {

		try {

			jsonResponse.put(FieldsNames.SERVICE, FieldsNames.CURRENT_ROOM);
			jsonResponse
					.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOM_ACTIONS);

			String roomAction = null;
			try {
				roomAction = jsonRequest.getString(FieldsNames.ROOM_ACTION);
			} catch (JSONException e) {
				throw new MissingFieldException();
			}

			switch (roomAction) {
			case FieldsNames.ROOM_EXIT:
				generateActionExitResponse();
				break;
			}

		} catch (JSONException e) {
		}
	}

	private void generateActionExitResponse() throws MissingFieldException {

		try {
			jsonResponse.put(FieldsNames.ROOM_ACTION, FieldsNames.ROOM_EXIT);

			try {

				String playerName = null;
				String roomName = null;
				try {
					playerName = jsonRequest.getString(FieldsNames.USERNAME);
					roomName = jsonRequest.getString(FieldsNames.ROOM_NAME);
				} catch (JSONException e) {
					throw new MissingFieldException();
				}

				Room room = gameDataManager.getRoomByName(roomName);

				room.removePlayer(room.getPlayerByName(playerName));
				jsonResponse.put(FieldsNames.NO_ERRORS, true);

			} catch (NoSuchRoomException | NoSuchPlayerException e) {
				jsonResponse.put(FieldsNames.NO_ERRORS, false);
			}

		} catch (JSONException e) {
		}
	}

}
