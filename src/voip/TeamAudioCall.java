package voip;

import game.model.Player;
import game.model.Team;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import voip.audio_forwarder.Forwarder;
import voip.audio_forwarder.ForwardingThread;
import voip.audio_receivers.Receiver;
import voip.mixing.MixingPipedInputStream;

import com.biasedbit.efflux.participant.RtpParticipant;
import com.biasedbit.efflux.session.SingleParticipantSession;

/**
 * Team Audio Call defines RTP audio conversation between server and clients.
 * Client must send its audio data to the granted port and the server mixes all
 * other players audio and sends back it to the client.
 * 
 * The supported audio format is the Payload type 0 of the RFC 2198. A PCMU
 * (u-law) format at 8kHz and sending period of 20 ms according to Android
 * audioStream.
 * 
 * @author Micieli
 * @date 2015/05/03
 */

public class TeamAudioCall {

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
			Forwarder forwarder = new Forwarder(mixingPipedInputStream, sessions.get(player));
			forwarders.add(forwarder);
		}

		thread = new ForwardingThread(forwarders);
	}

	// creates streams in which data are recorded and from which are read
	// for each player there is a stream for each other players
	private ArrayList<PipedOutputStream> createStreams(Player player) throws IOException {
		ArrayList<PipedOutputStream> outputStreams = new ArrayList<>();
		ArrayList<Player> players2 = team.getPlayers();
		for (Player player2 : players2) {
			if (!player2.equals(player)) {
				PipedOutputStream outputStream = new PipedOutputStream();
				PipedInputStream inputStream = new PipedInputStream(outputStream, BUFFER_SIZE);
				outputStreams.add(outputStream);
				streams.get(player2).add(inputStream);
			}
		}
		return outputStreams;
	}

	// creates RTP session from server to client
	private SingleParticipantSession createSession(Player player) {
		RtpParticipant server = RtpParticipant.createReceiver(NetUtils.getLocalIpAddress(), player
				.getAudioData().getLocalPort(), player.getAudioData().getLocalControlPort());
		RtpParticipant client = RtpParticipant.createReceiver(player.getAudioData().getIp(), player
				.getAudioData().getRemotePort(), player.getAudioData().getRemotePort() + 1);
		SingleParticipantSession session = new SingleParticipantSession(player.getUsername(), 0,
				server, client);
		System.out.println(player.getUsername() + " " + player.getAudioData().getIp() + " "
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

		ExecutorService executor = Executors.newFixedThreadPool(sessions.size());

		Collection<SingleParticipantSession> tmp = sessions.values();
		for (final SingleParticipantSession singleParticipantSession : tmp) {
			executor.execute(new Runnable() {

				@Override
				public void run() {
					singleParticipantSession.init();
				}
			});
		}
	}

	private void initMap() {
		for (Player player : team.getPlayers()) {
			streams.put(player, new ArrayList<PipedInputStream>());
		}
	}

	/**
	 * 
	 * Ends call stopping receiving packets and the forwarding thread. Frees
	 * resources: buffers and srver port
	 * 
	 */
	public void endCall() {

		thread.stopTask();

		Collection<SingleParticipantSession> sessios = sessions.values();
		for (SingleParticipantSession singleParticipantSession : sessios) {
			singleParticipantSession.terminate();
		}

		for (Player player : team.getPlayers()) {
			NetUtils.releasePort(player.getAudioData().getLocalPort());
			NetUtils.releasePort(player.getAudioData().getLocalControlPort());
		}
	}
}
