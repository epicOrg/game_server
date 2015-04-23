package game;

import java.util.ArrayList;

/**
 * @author Noris
 * @date 2015/04/20
 */

public class TeamGenerator {

	public static final int NUMBER_OF_TEAMS = 2;

	private ArrayList<Team> teams = new ArrayList<Team>();

	public TeamGenerator() {
		generateTeams();
	}

	/**
	 * Team generator: this method generate two team with different colors.
	 */
	private void generateTeams() {
		for(int i = 0; i < NUMBER_OF_TEAMS; i++){
			Team team = new Team();
			team.setTeamName("Team " + (teams.size()+1));
			team.setRandomTeamColor();
			teams.add(team);
		}
	}

	/**
	 * Select a team for the incoming player.
	 * 
	 * @return the team with less players.
	 */
	public Team getRandomTeam() {
		Team t;
		int min;

		t = teams.get(0);
		min = teams.get(0).getPlayers().size();

		for(int i = 1; i < teams.size(); i++){
			Team temp = teams.get(i);

			if(temp.getPlayers().size() < min){
				t = temp;
				min = t.getPlayers().size();
			}
		}
		return t;
	}

	/**
	 * @return an ArrayList containing the two teams.
	 */
	public ArrayList<Team> getTeams() {
		return teams;
	}

}
