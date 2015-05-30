package game;

import game.map.MapDimension;
import game.map.utils.MapConst;
import game.model.IWinChecher;
import game.model.Player;
import game.model.PlayerStatus;
import game.model.Team;

import java.util.ArrayList;

/**
 * Defines a circle area in which all the players of a team must get in for win
 * the game.
 * 
 * @author Micieli
 * @date 2015/05/05
 */

public class CircleWinChecker implements IWinChecher {

	private ArrayList<MapDimension> winPoints;

	private float radius;

	public CircleWinChecker(ArrayList<MapDimension> winPoints) {
		this(winPoints, (float) MapConst.PLAYER_SIZE);
	}

	public CircleWinChecker(ArrayList<MapDimension> winPoints, float ray) {
		this.winPoints = winPoints;
		this.radius = ray;
	}

	@Override
	public boolean checkWin(Team team) {

		ArrayList<Player> players = team.getPlayers();
		for (Player player : players) {
			if (!isInPosition(player))
				return false;
		}

		return true;

	}

	private boolean isInPosition(Player player) {

		PlayerStatus status = player.getPlayerStatus();
		float x = status.getxPosition();
		float z = status.getzPosition();

		for (MapDimension w : winPoints) {
			float xWin = (float) w.getWidth();
			float zWin = (float) w.getLength();
			
			if ((x - xWin) * (x - xWin) + (z - zWin) * (z - zWin) <= radius * radius) {
				return true;
			}
		}

		return false;
	}
}
