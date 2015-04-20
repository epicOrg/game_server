package game;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Noris
 * @date 2015/04/20
 */

public class TeamGenerator {
	
	private Team teamA;
	private Team teamB;
	
	public TeamGenerator() {
		generateTeams();
	}

	/**
	 * Team generator: this method generate two team with different colors.
	 */
	private void generateTeams() {

		teamA = new Team();
		teamB = new Team();

		while (teamA.getTeamColor().equals(teamB.getTeamColor())) {
			teamB.setRandomTeamColorFromList();
		}
	}

	/**
	 * Select a team for the incoming player.
	 * 
	 * @return the team with less players or, if the teams has the same number
	 *         of players, a random team.
	 */
	public Team getRandomTeam() {

		if (teamA.getSize() < teamB.getSize())
			return teamA;

		else if (teamA.getSize() > teamB.getSize())
			return teamB;

		if (new Random().nextBoolean())
			return teamA;

		return teamB;
	}

	/**
	 * @return an ArrayList containing the two teams.
	 */
	public ArrayList<Team> getTeams() {

		ArrayList<Team> teams = new ArrayList<Team>(2);
		teams.add(teamA);
		teams.add(teamB);
		return teams;
	}

}
