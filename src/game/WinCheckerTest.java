package game;

import java.io.PrintWriter;
import java.util.ArrayList;

import online_management.OnlineManager;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;
import exceptions.UserNotOnlineException;

public class WinCheckerTest implements IWinChecher {
	
	private float xWin = 0;
	private float yWin = -1;
	private float zWin = 17;
	private float ray  = 3;
	
	@Override
	public void checkWin(Team team) {
		ArrayList<Player> players = team.getPlayers();
		for (Player player : players) {
			if(!isInPosition(player))
				return;
		}
		try {
			updatePlayers(players, createWinMessage());
		} catch (UserNotOnlineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private JSONObject createWinMessage() {
		JSONObject winMessage = new JSONObject();
		try {
			winMessage.put(FieldsNames.SERVICE, FieldsNames.GAME);
			winMessage.put(FieldsNames.SERVICE_TYPE, FieldsNames.GAME_STATUS);
			winMessage.put(FieldsNames.GAME_WIN, true);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return winMessage;
	}

	private boolean isInPosition(Player player) {
		PlayerStatus status = player.getPlayerStatus();
		float x = status.getxPosition();
		float y = status.getyPosition();
		float z = status.getzPosition();
		
		if((x-xWin)*(x-xWin) + (y-yWin)*(y-yWin) <= ray*ray )
			return true;
		
		return false;
	}
	
	private void updatePlayers(ArrayList<Player> players, JSONObject message) throws UserNotOnlineException {
		String strMessage = message.toString();

		for (Player player : players ) {
				PrintWriter writer = OnlineManager.getInstance().getOnlineUserByUsername(
						player.getUsername()).getOutStream();
				writer.println(strMessage);			
		}
	}
}
