package game;

import java.util.ArrayList;
import java.util.Map;

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

		for (Map.Entry<String, Player> entry : room.getPlayers().entrySet()) {
			if (player != entry.getValue())
				positions.add(entry.getValue().getPlayerStatus());
		}

		return positions;
	}

	/**
	 * @return the positions of all the user in the room.
	 */
	public ArrayList<PlayerStatus> getAllPositions() {

		ArrayList<PlayerStatus> positions = new ArrayList<PlayerStatus>();

		for (Map.Entry<String, Player> entry : room.getPlayers().entrySet()) {
			positions.add(entry.getValue().getPlayerStatus());
		}

		return positions;
	}

}
