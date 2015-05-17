package game;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import online_management.OnlineManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import voip.RoomAudioCall;
import check_fields.FieldsNames;
import data_management.GameDataManager;
import exceptions.NoSuchRoomException;
import exceptions.UserNotOnlineException;

/**
 * @author Micieli
 * @date 2015/04/25
 */

public class RoomPlayersUpdater implements RoomEventListener, PlayerEventListener {

	private OnlineManager onlineManager;

	private Room room;
	private HashMap<Player, PrintWriter> writers = new HashMap<>();
	private RoomThread roomThread ;

	public RoomPlayersUpdater(Room room) {
		super();
		this.room = room;
		this.onlineManager = OnlineManager.getInstance();
	}

	@Override
	public void onNewPlayerAdded(Player player) {

		try {

			PrintWriter writer = onlineManager.getOnlineUserByUsername(
					player.getUsername()).getOutStream();
			writers.put(player, writer);

			player.setPlayerEventListener(this);

		} catch (UserNotOnlineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONObject message = generatePlayerList();
		updatePlayers(player, message);

	}

	@Override
	public void onRoomFull() {
		JSONObject message = generateStartMessage();
		updatePlayers(null, message);
		room.setInPlay(true);
		GameDataManager.getInstance().newAudioCallForRoom(room);
	}

	@Override
	public void onPlayerRemoved(Player player) {
		writers.remove(player);
		JSONObject message = generatePlayerList();
		updatePlayers(player, message);
	}

	private JSONObject generateStartMessage() {

		JSONObject message = new JSONObject();

		try {

			message.put(FieldsNames.SERVICE, FieldsNames.CURRENT_ROOM);
			message.put(FieldsNames.SERVICE_TYPE, FieldsNames.ROOM_ACTIONS);
			message.put(FieldsNames.ROOM_ACTION, FieldsNames.ROOM_START);
			message.put(FieldsNames.NO_ERRORS, true);

		} catch (JSONException e) {
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

	private void updatePlayers(Player excludedPlayer, JSONObject message) {
		String strMessage = message.toString();

		for (Player p : writers.keySet()) {
			if (p != excludedPlayer) {
				writers.get(p).println(strMessage);
			}
		}
	}

	@Override
	public void onPlayerStatusChanged() {
		System.out.println("Player Ready");
		for(Team t : room.getTeamGenerator().getTeams()){
			for(Player p : t.getPlayers()){
				if(p.getStatus() != true)
					return;
			}
		}
		
		roomThread = new RoomThread(room, new WinCheckerTest());
		roomThread.start();

		
		JSONObject message = new JSONObject();
		try {
			message.put(FieldsNames.SERVICE, FieldsNames.GAME);
			message.put(FieldsNames.SERVICE_TYPE, FieldsNames.GAME_STATUS);
			message.put(FieldsNames.GAME_GO, true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		updatePlayers(null, message);

		RoomAudioCall roomAudioCall = null;
		try {
			roomAudioCall = GameDataManager.getInstance().getCallbyRoomName(room.getName());
		} catch (NoSuchRoomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			roomAudioCall.prepare();				
			roomAudioCall.startCall();
			System.out.println("Call Started");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onExtingFromGame() {
		roomThread.shutdown();
		try {
			GameDataManager.getInstance().stopCallForRoom(room.getName());
		} catch (NoSuchRoomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject message = generateExitMessage();
		updatePlayers(null, message);		
	}

	private JSONObject generateExitMessage() {
		JSONObject message = new JSONObject();
		try {
			message.put(FieldsNames.SERVICE, FieldsNames.GAME);
			message.put(FieldsNames.SERVICE_TYPE, FieldsNames.GAME_STATUS);
			message.put(FieldsNames.GAME_END, FieldsNames.GAME_INTERRUPTED);
		} catch (JSONException e) {
			e.printStackTrace();
		}		
		return message;
	}
}
