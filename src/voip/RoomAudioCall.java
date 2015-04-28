package voip;

import game.Room;
import game.Team;

public class RoomAudioCall {
	
	private Room room;

	public RoomAudioCall(Room room) {
		super();
		this.room = room;
	}
	
	public void beginCall(){
		
		for(Team team: room.getTeamGenerator().getTeams()){
			TeamConversation conversation = new TeamConversation(team);
			conversation.beginConversation();
		}		
	}	
}
