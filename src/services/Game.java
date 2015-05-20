package services;

import game.Player;
import game.Room;
import game.Team;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import data_management.GameDataManager;
import exceptions.MissingFieldException;
import exceptions.NoSuchPlayerException;
import exceptions.NoSuchRoomException;

/**
 * @author Torlaschi
 * @date 2015/04/18
 */

public class Game implements IService {

	private JSONObject jsonRequest;
	private JSONObject jsonResponse;

	private GameDataManager gameDataManager;
	private Room room;

	public Game() {
		gameDataManager = GameDataManager.getInstance();
	}

	@Override
	public JSONObject start() {

		try {
			runService(jsonRequest.getString(FieldsNames.SERVICE_TYPE));
		} catch (JSONException e) {
			return new MissingFieldException().getMissingFieldError();
		}

		return jsonResponse;
	}

	private void runService(String serviceType) {

		switch (serviceType) {
		case FieldsNames.GAME_STATUS:
			generateStatusResponse();
			break;
		case FieldsNames.GAME_MAP:
			generateMapResponse();
			break;
		case FieldsNames.GAME_POSITIONS:
			generatePositionsResponse();
			break;
		}
	}

	private void generateStatusResponse() {
		if (jsonRequest.has(FieldsNames.GAME_READY)) {
			playerReady();
		} else if (jsonRequest.has(FieldsNames.GAME_EXIT)) {
			removePlayer();
			jsonResponse = null;
		}

	}

	private void removePlayer() {

		try {
			String roomName = jsonRequest.getString(FieldsNames.ROOM_NAME);
			String username = jsonRequest.getString(FieldsNames.USERNAME);
			Player player = gameDataManager.getRoomByName(roomName).getPlayerByName(username);

			Room room = gameDataManager.getRoomByName(roomName);
			room.removePlayer(player);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchRoomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void playerReady() {
		try {
			String roomName = jsonRequest.getString(FieldsNames.ROOM_NAME);
			String username = jsonRequest.getString(FieldsNames.USERNAME);
			Player player = gameDataManager.getRoomByName(roomName).getPlayerByName(username);

			player.setStatus(jsonRequest.getBoolean(FieldsNames.GAME_READY));
			jsonResponse = null;
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NoSuchPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchRoomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void generatePositionsResponse() {
		try {

			String roomName = jsonRequest.getString(FieldsNames.ROOM_NAME);

			try {
				room = gameDataManager.getRoomByName(roomName);
			} catch (NoSuchRoomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			JSONObject posObject = jsonRequest.getJSONObject(FieldsNames.GAME_POSITION);
			JSONObject dirObject = jsonRequest.getJSONObject(FieldsNames.GAME_DIRECTION);
			float xPos = (float) posObject.getDouble(FieldsNames.GAME_X);
			float yPos = (float) posObject.getDouble(FieldsNames.GAME_Y);
			float zPos = (float) posObject.getDouble(FieldsNames.GAME_Z);
			float xDir = (float) dirObject.getDouble(FieldsNames.GAME_X);
			float yDir = (float) dirObject.getDouble(FieldsNames.GAME_Y);
			float zDir = (float) dirObject.getDouble(FieldsNames.GAME_Z);

			String username = jsonRequest.getString(FieldsNames.USERNAME);

			Player player = room.getPlayerByName(username);
			player.getPlayerStatus().setPosition(xPos, yPos, zPos);
			player.getPlayerStatus().setDirection(xDir, yDir, zDir);
			jsonResponse.put(FieldsNames.SERVICE, FieldsNames.GAME);
			jsonResponse.put(FieldsNames.SERVICE_TYPE, FieldsNames.GAME_POSITIONS);

			JSONArray jPlayers = new JSONArray();

			ArrayList<Player> players = new ArrayList<Player>();
			for (Team t : room.getTeamGenerator().getTeams()) {
				players.addAll(t.getPlayers());
			}

			for (Player p : players) {
				if (p.getUsername().equals(username))
					continue;

				JSONObject jPlayer = new JSONObject();
				JSONObject jObjectPos = new JSONObject();
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

				jPlayers.put(jPlayer);
			}

			jsonResponse.put(FieldsNames.GAME_PLAYERS, jPlayers);
		} catch (JSONException e) {
			e.printStackTrace();
			jsonResponse = null;
		} catch (NoSuchPlayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonResponse = null;
		}
	}

	private void generateMapResponse() {

		try {

			jsonResponse.put(FieldsNames.SERVICE, FieldsNames.GAME);
			jsonResponse.put(FieldsNames.SERVICE_TYPE, FieldsNames.GAME_MAP);

			jsonResponse = new JSONObject(jsonResponse, JSONObject.getNames(jsonResponse));

			try {
				room = gameDataManager.getRoomByName(jsonRequest.getString(FieldsNames.ROOM_NAME));
			} catch (NoSuchRoomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			JSONObject map = room.getRoomMapSelector().getMap();
			for (String key : JSONObject.getNames(map)) {
				jsonResponse.put(key, map.get(key));
			}

			jsonResponse.put(FieldsNames.GAME_PLAYER_POSITION, room.getRoomMapSelector()
					.getSpawnPoint().toStringPosition());

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setRequest(JSONObject request) {
		this.jsonRequest = request;
		jsonResponse = new JSONObject();
	}

}
