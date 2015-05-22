package game;

import game.map.MapDimension;

import java.util.ArrayList;

/**
 * @author Micieli
 * @date 2015/05/05
 */

public class WinCheckerTest implements IWinChecher {

	private ArrayList<MapDimension> winPoints;

	private float radius;

	public WinCheckerTest(float xWin, float yWin, float zWin, float ray) {

		winPoints = new ArrayList<MapDimension>();
		winPoints.add(new MapDimension((float) xWin, (float) yWin, (float) zWin));
		this.radius = ray;
	}

	public WinCheckerTest(MapDimension winPoint, float ray) {

		winPoints = new ArrayList<MapDimension>();
		winPoints.add(winPoint);
		this.radius = ray;
	}

	public WinCheckerTest(ArrayList<MapDimension> winPoints, float ray) {
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

		System.out.println("Winner: " + team.getTeamName());
		return true;

	}

	private boolean isInPosition(Player player) {

		PlayerStatus status = player.getPlayerStatus();
		float x = status.getxPosition();
		// float y = status.getyPosition();
		float z = status.getzPosition();

		for (MapDimension w : winPoints) {

			float xWin = (float) w.getWidth();
			// float yWin = (float) w.getHeight();
			float zWin = (float) w.getLength();

			if ((x - xWin) * (x - xWin) + (z - zWin) * (z - zWin) <= radius * radius) {
				System.out.println(player.getUsername() + " in win position.");
				return true;
			}
		}

		return false;
	}

}
