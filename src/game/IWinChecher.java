package game;

/**
 * Interface for algorithms that checks for a winner team.
 * 
 * @see Team
 * @see Room
 * @see RoomThread
 * 
 * @author Luca
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
