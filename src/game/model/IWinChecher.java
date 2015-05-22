package game.model;

/**
 * Interface for the algorithms that checks for the winner team.
 * 
 * @author Micieli
 * @date 2015/05/05
 * @see Team
 * @see Room
 * @see RoomThread
 */

public interface IWinChecher {

	/**
	 * Checks if the team is a winner according to the algorithm.
	 * 
	 * @param team
	 *            the <code>Team</code> to be checked
	 * @return true if the team is a winner, false otherwise
	 */
	public boolean checkWin(Team team);
}
