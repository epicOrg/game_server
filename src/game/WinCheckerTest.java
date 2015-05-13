package game;

import java.util.ArrayList;

public class WinCheckerTest implements IWinChecher {
	
	private float xWin = 0;
	private float yWin = -1;
	private float zWin = 17;
	private float ray  = 3;
	
	@Override
	public boolean checkWin(Team team) {
		
		ArrayList<Player> players = team.getPlayers();
		for (Player player : players) {
			if(!isInPosition(player))
				return false;
		}
		
		return true;
		
	}	

	private boolean isInPosition(Player player) {
		PlayerStatus status = player.getPlayerStatus();
		float x = status.getxPosition();
		float y = status.getyPosition();
		float z = status.getzPosition();
		
		if((x-xWin)*(x-xWin) + (z-zWin)*(z-zWin) <= ray*ray ){
			System.out.println(player.getUsername() + " in position");
			return true;
		}
		
		return false;
	}	
}
