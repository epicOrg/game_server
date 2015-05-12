package game;


/**
 * 
 * Interface for algorithms that check for a winner team
 * 
 * @author Luca
 * @see Team
 * @see Room
 * @see RoomThread
 */

public interface IWinChecher {
	
	/**
	 * 
	 * Checks if the team is a winner according to the algorithm
	 * 
	 * @param team
	 * @return true if the team is a winner
	 * 
	 */
	public boolean checkWin(Team team);
}
