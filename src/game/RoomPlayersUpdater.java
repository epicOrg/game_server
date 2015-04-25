package game;

import exceptions.UserNotOnlineException;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;

import online_management.OnlineManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;

public class RoomPlayersUpdater implements RoomEventListener {

	private OnlineManager onlineManager;

	private Room room;
	private HashMap<Player, PrintWriter> writers = new HashMap<>();

	public RoomPlayersUpdater(Room room) {
		super();
		this.room = room;
		this.onlineManager = OnlineManager.getInstance();
	}

	@Override
	public void onNewPlayerAdded(Player player) {

		try {
			PrintWriter writer = onlineManager.getOnlineUserByUsername(
					player.getUsername()).getOuStream();
			writers.put(player, writer);
		} catch (UserNotOnlineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONObject message = generatePlayerList();
		updatePlayers(message);

		if(room.isFull())
			onRoomFull();
	}
	@Override
	public void onRoomFull() {
		JSONObject message = generateStarMessage();
		updatePlayers(message);		
	}

	@Override
	public void onPlayerRemoved(Player player) {
		writers.remove(player);
		JSONObject message = generatePlayerList();
		updatePlayers(message);
	}

	private JSONObject generateStarMessage() {
		JSONObject message = new JSONObject();
		
		try {
			message.put(FieldsNames.SERVICE, FieldsNames.CURRENT_ROOM);
			message.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOM_ACTIONS);
			message.put(FieldsNames.ROOM_ACTION, FieldsNames.ROOM_START);
			message.put(FieldsNames.NO_ERRORS, true);
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		return message;
	}

	private JSONObject generatePlayerList() {

		JSONObject message = new JSONObject();

		try {
			message.put(FieldsNames.SERVICE, FieldsNames.CURRENT_ROOM);
			message.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOM_PLAYER_LIST);

			message.put(FieldsNames.ROOM_MAX_PLAYERS, Room.MAX_PLAYERS);

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

			message.put(FieldsNames.ROOM_TEAM, teams);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return message;
	}

	private void updatePlayers(JSONObject message) {
		
		Collection<PrintWriter> writers = this.writers.values();
		String strMessage = message.toString();
		for (PrintWriter writer : writers) {
			writer.println(strMessage);
		}		
	}
}
