package game;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import online_management.OnlineManager;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import exceptions.UserNotOnlineException;

/**
 * 
 * Thread that periodically checks if a team won the game;
 * if there are any update all the player in the room end ends the game
 * 
 * @author Luca
 *
 */

public class RoomThread extends Thread {

	private static final int CHECKING_PERIOD = 1;
	private IWinChecher winChecker;
	private Timer timer;
	private Room room;

	/**
	 * 
	 * 
	 * 
	 * @param room : the room to be checked
	 * @param winChecker: an implementation of {@link IWinChecher}
	 */
	public RoomThread(Room room, IWinChecher winChecker) {
		super();
		this.room = room;
		this.winChecker = winChecker;
		timer = new Timer();
	}

	@Override
	public void run() {

		timer.scheduleAtFixedRate(new WinTask(), 0, CHECKING_PERIOD);

	}
	public void shutdown(){
		timer.cancel();
	}

	/**
	 * 
	 * Periodical checking task
	 * 
	 * @author Luca
	 *
	 */
	public class WinTask extends TimerTask {

		@Override
		public void run() {
			ArrayList<Team> winner = new ArrayList<>();

			for (Team team : room.getTeamGenerator().getTeams()) {
				if (winChecker.checkWin(team))
					winner.add(team);
			}
			if(winner.isEmpty())
				return;
			
			endGame(winner);

		}
	}

		private void endGame(ArrayList<Team> winner) {
			timer.cancel();
			ArrayList<Team> losers = new ArrayList<>();
			losers.addAll(room.getTeamGenerator().getTeams());
			losers.removeAll(winner);
			try {
				if (winner.size() > 1) {
					for (Team team : winner) {
						updatePlayers(team.getPlayers(), generateDrawMessage());						
					}
				}else{
					updatePlayers(winner.get(0).getPlayers(), generateWinMessage());
				}
				for (Team team : losers) {
					updatePlayers(team.getPlayers(), generateLoseMessage());
				}

			} catch (UserNotOnlineException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private JSONObject generateLoseMessage() {
			JSONObject loseMessage = new JSONObject();
			try {
				loseMessage.put(FieldsNames.SERVICE, FieldsNames.GAME);
				loseMessage.put(FieldsNames.SERVICE_TYPE, FieldsNames.GAME_STATUS);
				loseMessage.put(FieldsNames.GAME_END, FieldsNames.GAME_LOSE);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return loseMessage;
		}

		private JSONObject generateWinMessage() {
			JSONObject winMessage = new JSONObject();
			try {
				winMessage.put(FieldsNames.SERVICE, FieldsNames.GAME);
				winMessage.put(FieldsNames.SERVICE_TYPE, FieldsNames.GAME_STATUS);
				winMessage.put(FieldsNames.GAME_END, FieldsNames.GAME_WIN);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return winMessage;
		}

		private JSONObject generateDrawMessage() {
			JSONObject message = new JSONObject();
			try {

				message.put(FieldsNames.SERVICE, FieldsNames.GAME);
				message.put(FieldsNames.SERVICE_TYPE, FieldsNames.GAME_STATUS);
				message.put(FieldsNames.GAME_END, FieldsNames.GAME_DRAW);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return message;
		}

	private void updatePlayers(ArrayList<Player> players, JSONObject message)
			throws UserNotOnlineException {
		String strMessage = message.toString();

		for (Player player : players) {
			PrintWriter writer = OnlineManager.getInstance()
					.getOnlineUserByUsername(player.getUsername())
					.getOutStream();
			writer.println(strMessage);
		}
	}
}
