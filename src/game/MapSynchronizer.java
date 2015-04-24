package game;

import java.util.ArrayList;

/**
 * @author Noris
 * @date 2015/04/19
 */

public class MapSynchronizer {

	private Room room;

	public MapSynchronizer(Room room) {
		this.room = room;
	}

	/**
	 * @param player
	 * @return the positions of all the user in the room except the position of
	 *         the player passed in this method.
	 */
	public ArrayList<PlayerStatus> getPositions(Player player) {
		ArrayList<PlayerStatus> positions = new ArrayList<PlayerStatus>();

		for (Team t : room.getTeamGenerator().getTeams()) {
			for (Player p : t.getPlayers()) {
				if (!player.getUsername().equals(p.getUsername()))
					positions.add(p.getPlayerStatus());
			}
		}

		return positions;
	}

	/**
	 * @return the positions of all the user in the room.
	 */
	public ArrayList<PlayerStatus> getAllPositions() {
		ArrayList<PlayerStatus> positions = new ArrayList<PlayerStatus>();

		for (Team t : room.getTeamGenerator().getTeams()) {
			for (Player p : t.getPlayers()) {
				positions.add(p.getPlayerStatus());
			}
		}

		return positions;
	}

}
