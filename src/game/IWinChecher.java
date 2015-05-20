package game;

/**
 * Interface for the algorithms who checks for the winner team.
 * 
 * @see Team
 * @see Room
 * @see RoomThread
 * 
 * @author Micieli
 * @date 2015/05/05
 */

public interface IWinChecher {

	/**
	 * Checks if the team is a winner according to the algorithm.
	 * 
	 * @param team
	 * @return true if the team is a winner, false otherwise
	 */
	public boolean checkWin(Team team);
}
