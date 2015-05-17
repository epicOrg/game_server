package voip;

import game.Player;
import game.Team;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import voip.audio_forwarder.Forwarder;
import voip.audio_forwarder.ForwardingThread;
import voip.audio_receivers.Receiver;
import voip.mixing.MixingPipedInputStream;

import com.biasedbit.efflux.participant.RtpParticipant;
import com.biasedbit.efflux.session.SingleParticipantSession;

/**
 * @author Luca
 * @date 2015/05/03
 */

public class TeamAudioCall {

	// TODO prendere la porta dal client
	// TODO gestire le porte vuote
	// TODO prendere l'ip dall'online user
	// TODO decidere dove preparare e iniziare la chiamata

	private static final int DATA_LENTH = 160;
	private static final int BUFFER_SIZE = DATA_LENTH * 200;

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
	 * Prepare data for Audio Conversation.
	 * 
	 * @throws IOException
	 */
	public void prepare() throws IOException {

		ArrayList<Player> players = team.getPlayers();
		System.out.println("players num:" + players.size());
		for (Player player : players) {
			SingleParticipantSession session = createSession(player);
			sessions.put(player, session);
			ArrayList<PipedOutputStream> outputStreams = createStreams(player);
			Receiver receiver = new Receiver(outputStreams);
			session.addDataListener(receiver);
		}

		prepareDataForwarding();
	}

	// initialize forwarders and the forwarding thread
	protected void prepareDataForwarding() {
		ArrayList<Forwarder> forwarders = new ArrayList<>();
		for (Player player : team.getPlayers()) {

			MixingPipedInputStream mixingPipedInputStream = new MixingPipedInputStream(
					streams.get(player));
			Forwarder forwarder = new Forwarder(mixingPipedInputStream,
					sessions.get(player));
			forwarders.add(forwarder);
		}

		thread = new ForwardingThread(forwarders);
	}

	// creates streams in which data are recorded and from which are read
	// for each player there is a stream for each other players
	private ArrayList<PipedOutputStream> createStreams(Player player)
			throws IOException {
		ArrayList<PipedOutputStream> outputStreams = new ArrayList<>();
		ArrayList<Player> players2 = team.getPlayers();
		for (Player player2 : players2) {
			if (!player2.equals(player)) {
				PipedOutputStream outputStream = new PipedOutputStream();
				PipedInputStream inputStream = new PipedInputStream(
						outputStream, BUFFER_SIZE);
				outputStreams.add(outputStream);
				streams.get(player2).add(inputStream);
			}
		}
		return outputStreams;
	}

	// creates RTP session from server to client
	private SingleParticipantSession createSession(Player player) {
		RtpParticipant server = RtpParticipant.createReceiver(NetUtils
				.getLocalIpAddress(), player.getAudioData().getLocalPort(),
				player.getAudioData().getLocalPort() + 1);
		RtpParticipant client = RtpParticipant.createReceiver(player
				.getAudioData().getIp(), player.getAudioData().getRemotePort(),
				player.getAudioData().getRemotePort() + 1);
		SingleParticipantSession session = new SingleParticipantSession(
				player.getUsername(), 0, server, client);
		System.out.println(player.getUsername() + " "
				+ player.getAudioData().getIp() + " "
				+ player.getAudioData().getLocalPort() + " "
				+ player.getAudioData().getRemotePort());
		return session;
	}

	/**
	 * Starts data transfer.
	 * 
	 */
	public void startCall() {

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

	public void endCall() {

		thread.stopTask();

		Collection<SingleParticipantSession> sessios = sessions.values();
		for (SingleParticipantSession singleParticipantSession : sessios) {
			singleParticipantSession.terminate();
		}
		for (Player player : team.getPlayers()) {
			NetUtils.releasePort(player.getAudioData().getRemotePort());
		}
	}
}
