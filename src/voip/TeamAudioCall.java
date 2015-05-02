package voip;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import voip.audio_forwarder.Forwarder;
import voip.audio_forwarder.ForwardingThread;
import voip.audio_receivers.Receiver;
import voip.mixing.EmptyCollectionException;
import voip.mixing.MixingPipedInputStream;

import com.biasedbit.efflux.participant.RtpParticipant;
import com.biasedbit.efflux.session.SingleParticipantSession;

import game.Player;
import game.PlayerStatus;
import game.Team;

public class TeamAudioCall {
	
	//TODO prendere la porta dal client
	//TODO gestire le porte vuote
	//TODO prendere l'ip dall'online user
	//TODO decidere dove preparare e iniziare la chiamata
	
	private static final int DATA_LENTH = 160;
	private static final int BUFFER_SIZE = DATA_LENTH * 100;
	
	private Team team;
	private HashMap<Player, SingleParticipantSession> sessions = new HashMap<>();
	private HashMap<Player, ArrayList<PipedInputStream>> streams = new HashMap<>();
	private ForwardingThread thread;
	
	public TeamAudioCall(Team team) {
		super();
		this.team = team;
		initMap();
	}
	
	/**
	 * Prepare data for Audio Conversation
	 * 
	 * @throws IOException
	 */
	public void prepare() throws IOException{		
		
		for (int i = 0; i < team.getSize(); i++) {
			ArrayList<Player> players = team.getPlayers();
			for (Player player : players) {
				SingleParticipantSession session = createSession(player);
				sessions.put(player, session);
				ArrayList<PipedOutputStream> outputStreams = createStreams(player);
				Receiver receiver = new Receiver(outputStreams);
				session.addDataListener(receiver);				
			}			
		}
		prepareDataForwarding();		
	}
	// initialize forwarders and the forwarding thread
	protected void prepareDataForwarding() {
		ArrayList<Forwarder> forwarders = new  ArrayList<>();
		for (Player player : team.getPlayers()) {
			try {
				MixingPipedInputStream mixingPipedInputStream = new MixingPipedInputStream(streams.get(player));
				Forwarder forwarder = new Forwarder(mixingPipedInputStream, sessions.get(player));
				forwarders.add(forwarder);
			} catch (EmptyCollectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		thread = new ForwardingThread(forwarders);
	}
	//creates streams in which data are recorded and from which are read
	// for each player there is a stream for each other players
	private ArrayList<PipedOutputStream> createStreams(Player player)
			throws IOException {
		ArrayList<PipedOutputStream> outputStreams = new ArrayList<>();
		ArrayList<Player> players2 = team.getPlayers();
		for (Player player2 : players2) {
			if(!player2.equals(player)){
				PipedOutputStream outputStream = new PipedOutputStream();
				PipedInputStream inputStream = new PipedInputStream(outputStream, BUFFER_SIZE);
				outputStreams.add(outputStream);	
				streams.get(player2).add(inputStream);
			}
		}
		return outputStreams;
	}
	//creates RTP session from server to client
	private SingleParticipantSession createSession(Player player) {
		int localPort = NetUtils.findFreePort();
		player.getAudioData().setLocalPort(localPort);
		RtpParticipant server = RtpParticipant.createReceiver(NetUtils.MY_IP, localPort, 0);
		RtpParticipant client = RtpParticipant.createReceiver(player.getAudioData().getIp(), player.getAudioData().getRemotePort(), 0);
		SingleParticipantSession session = new SingleParticipantSession(player.getUsername(), 0, server, client);
		return session;
	}
	
	/**
	 * starts data transfering
	 * 
	 */
	public void startCall(){
		
		thread.start();
		
		Collection<SingleParticipantSession> sessios = sessions.values();
		for (SingleParticipantSession singleParticipantSession : sessios) {
			singleParticipantSession.init();
		}
	}

	private void initMap() {
		for (Player player : team.getPlayers()) {
			streams.put(player, new ArrayList<PipedInputStream>());
		}
	}
}
