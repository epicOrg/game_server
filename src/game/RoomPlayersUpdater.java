package game;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import messages.UpdatingMessagesCreator;
import online_management.OnlineManager;

import org.json.JSONObject;

import voip.RoomAudioCall;
import data_management.GameDataManager;
import exceptions.NoSuchRoomException;
import exceptions.UserNotOnlineException;
import game.map.MapJSONizer;
import game.model.Player;
import game.model.PlayerEventListener;
import game.model.Room;
import game.model.RoomEventListener;
import game.model.RoomThread;
import game.model.Team;

/**
 * Updates {@link Player}s about {@link Room} events, sending them messages in
 * real time, breaking the request/reply pattern provided by
 * <code>Service</code>.
 * 
 * @author Micieli
 * @date 2015/04/25
 * @see Room
 */

public class RoomPlayersUpdater implements RoomEventListener, PlayerEventListener {

	private static final int DELAY = 4000;

	private OnlineManager onlineManager;
	private Timer timer;

	private Room room;
	private HashMap<Player, PrintWriter> writers = new HashMap<>();
	private RoomThread roomThread;
	private UpdatingMessagesCreator messagesCreator;
	private boolean interrupted = false;

	public RoomPlayersUpdater(Room room) {
		super();
		this.room = room;
		this.onlineManager = OnlineManager.getInstance();
		messagesCreator = new UpdatingMessagesCreator();
	}

	@Override
	public void onNewPlayerAdded(Player player) {

		try {

			PrintWriter writer = onlineManager.getOnlineUserByUsername(player.getUsername())
					.getOutStream();
			writers.put(player, writer);
			player.setPlayerEventListener(this);

		} catch (UserNotOnlineException e) {
			e.printStackTrace();
		}

		updatePlayers(player, messagesCreator.generatePlayersListMessage(room));
	}

	@Override
	public void onRoomFull() {

		try {
			if (timer != null)
				timer.cancel();
			timer = new Timer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				updatePlayers(null, messagesCreator.generateStartMessage());
				room.setInPlay(true);
				GameDataManager.getInstance().newAudioCallForRoom(room);
			}
		}, DELAY);

		roomThread = new RoomThread(room, new CircleWinChecker(MapJSONizer.getAdaptedWinPoint(room
				.getMap().getWinPoints()), 2));

	}

	@Override
	public void onPlayerRemoved(Player player) {

		writers.remove(player);

		if (room.isInPlayng() && !room.areAllPlayersReady()) {
			this.interrupted = true;
			onPlayerStatusChanged();
		} else {

			try {
				if (timer != null)
					timer.cancel();
				timer = new Timer();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			updatePlayers(player, messagesCreator.generatePlayersListMessage(room));
		}
	}

	@Override
	public void onGameEnded() {
		writers = new HashMap<>();
		room.generateMap();
	}

	@Override
	public void onPlayerStatusChanged() {

		for (Team t : room.getTeamGenerator().getTeams()) {
			for (Player p : t.getPlayers()) {
				if (p.getStatus() != true)
					return;
			}
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (interrupted) {
			onExtingFromGame();
			return;
		}

		room.setAllPlayerReady(true);
		allPlayerReady();
	}

	private void allPlayerReady() {
		roomThread.start();
		updatePlayers(null, messagesCreator.generateGoMessage());
		startAudioConversation();
	}

	private void startAudioConversation() {

		RoomAudioCall roomAudioCall = null;

		try {
			roomAudioCall = GameDataManager.getInstance().getCallbyRoomName(room.getName());
			roomAudioCall.prepare();
			roomAudioCall.startCall();
		} catch (NoSuchRoomException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onExtingFromGame() {

		roomThread.shutdown();

		try {
			GameDataManager.getInstance().stopCallForRoom(room.getName());
		} catch (NoSuchRoomException e) {
			e.printStackTrace();
		}

		updatePlayers(null, messagesCreator.generateExitMessage());
		room.setInPlay(false);

	}

	private void updatePlayers(Player excludedPlayer, JSONObject message) {

		String strMessage = message.toString();

		for (Player p : writers.keySet()) {
			if (p != excludedPlayer) {
				writers.get(p).println(strMessage);
			}
		}
	}

}
