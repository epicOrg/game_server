package voip;

import java.io.IOException;
import java.util.ArrayList;

import game.Room;
import game.Team;

/**
 * 
 * Define the audio conversation between each players of the same team in the
 * related room.
 * 
 * @author Luca
 * @date 2015/04/28
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
	public void prepare() throws IOException {

		for (Team team : room.getTeamGenerator().getTeams()) {
			if (team.getSize() > 1) {
				TeamAudioCall call = new TeamAudioCall(team);
				call.prepare();
				teamAudioCalls.add(call);
			}
		}
	}

	/**
	 * Starts data receiving and data transfer.
	 */
	public void startCall() {
		for (TeamAudioCall teamAudioCall : teamAudioCalls) {
			teamAudioCall.startCall();
		}
	}

	public void endCall() {
		System.out.println("Call ended");
		for (TeamAudioCall teamAudioCall : teamAudioCalls) {
			teamAudioCall.endCall();
		}
	}

	public Room getRoom() {
		return room;
	}
}
