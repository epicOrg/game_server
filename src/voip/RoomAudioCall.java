package voip;

import java.io.IOException;
import java.util.ArrayList;

import game.Room;
import game.Team;

/**
 * 
 * Define the audio conversation between each players of the same team in the related room
 * 
 * @author Luca
 *
 */

public class RoomAudioCall {
	
	private Room room;
	private ArrayList<TeamAudioCall> teamAudioCalls = new ArrayList<>();

	public RoomAudioCall(Room room) {
		super();
		this.room = room;
	}
	/**
	 * 
	 * Prepare audio conversation for each teams in the room
	 * 
	 * @throws IOException
	 */
	public void prepare() throws IOException{
		
		for(Team team: room.getTeamGenerator().getTeams()){
			TeamAudioCall call = new TeamAudioCall(team);
			call.prepare();
			teamAudioCalls.add(call);
		}		
	}	

	/**
	 * Starts data receiving and transfering
	 * 
	 */
	public void startCall(){
		for (TeamAudioCall teamAudioCall : teamAudioCalls) {
			teamAudioCall.startCall();
		}
	}
	
	public Room getRoom() {
		return room;
	}
}
