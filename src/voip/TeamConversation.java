package voip;

import java.net.InetAddress;
import java.util.ArrayList;

import com.biasedbit.efflux.participant.RtpParticipant;

import game.Team;

public class TeamConversation {
	
	private Team team;

	public TeamConversation(Team team) {
		super();
		this.team = team;
	}
	
	public void beginConversation(){
		
		for (int i = 0; i < team.getSize(); i++) {
			ArrayList<RtpParticipant> participants = new ArrayList<RtpParticipant>();
			//recupero inetaddres dei 
			RtpParticipant participantPlayer = RtpParticipant.createReceiver(null, NetUtils.findFreePort(), NetUtils.findFreePort());
			RtpParticipant participantServer = RtpParticipant.createReceiver(NetUtils.MY_IP, NetUtils.findFreePort(), NetUtils.findFreePort());
			
			
		}
	}
}
