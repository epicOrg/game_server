package voip_tests;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import voip.audio_forwarder.Forwarder;
import voip.audio_forwarder.ForwardingTask;
import voip.audio_receivers.Receiver;
import voip.mixing.MixingPipedInputStream;

import com.biasedbit.efflux.participant.RtpParticipant;
import com.biasedbit.efflux.session.SingleParticipantSession;

public class ThreePeopleConversationTest {

	private static final String MY_IP = "10.42.0.80";
	private static final int DATA_LENTH = 160;
	private static final int BUFFER_SIZE = DATA_LENTH * 100;

	public static void main(String[] args) {
		try {
			int serverPort1 = 4000;
			int serverPort2 = 5000;
			int serverPort3 = 6000;
			System.out.println("Server");

			// da settare : quelle che ti da l'app
			int port1 = 55254;
			String ip1 = "10.42.0.29";
			int port2 = 44352;
			String ip2 = "10.42.0.19";
			int port3 = 41522;
			String ip3 = "10.42.0.22";

			// creo gli "utenti server"
			RtpParticipant server1 = RtpParticipant.createReceiver(MY_IP,
					serverPort1, 4001);
			RtpParticipant server2 = RtpParticipant.createReceiver(MY_IP,
					serverPort2, 4002);
			RtpParticipant server3 = RtpParticipant.createReceiver(MY_IP,
					serverPort3, 4002);

			// creo gli "utenti"
			RtpParticipant user1 = RtpParticipant.createReceiver(ip1, port1,
					4001);
			user1.getInfo().setName("user1");
			RtpParticipant user2 = RtpParticipant.createReceiver(ip2, port2,
					4001);
			user2.getInfo().setName("user2");
			RtpParticipant user3 = RtpParticipant.createReceiver(ip3, port3,
					4001);
			user3.getInfo().setName("user2");

			// creo le sessioni
			SingleParticipantSession user1Server1 = new SingleParticipantSession(
					"ForwardSession1", 0, server1, user1);
			SingleParticipantSession user2Server2 = new SingleParticipantSession(
					"ForwardSession2", 0, server2, user2);
			SingleParticipantSession user3Server3 = new SingleParticipantSession(
					"ForwardSession3", 0, server3, user3);

			// Creo gli stream dall'1
			PipedOutputStream bytesStream1to2 = new PipedOutputStream();
			PipedInputStream audioInputStream1to2 = new PipedInputStream(
					bytesStream1to2, BUFFER_SIZE);

			PipedOutputStream bytesStream1to3 = new PipedOutputStream();
			PipedInputStream audioInputStream1to3 = new PipedInputStream(
					bytesStream1to3, BUFFER_SIZE);
			Receiver receiver1 = new Receiver(bytesStream1to2, bytesStream1to3);
			user1Server1.addDataListener(receiver1);

			// Creo gli stream dall'2
			PipedOutputStream bytesStream2to1 = new PipedOutputStream();
			PipedInputStream audioInputStream2to1 = new PipedInputStream(
					bytesStream2to1, BUFFER_SIZE);

			PipedOutputStream bytesStream2to3 = new PipedOutputStream();
			PipedInputStream audioInputStream2to3 = new PipedInputStream(
					bytesStream2to3, BUFFER_SIZE);
			Receiver receiver2 = new Receiver(bytesStream2to1, bytesStream2to3);
			user2Server2.addDataListener(receiver2);

			// Creo gli stream dall'3
			PipedOutputStream bytesStream3to1 = new PipedOutputStream();
			PipedInputStream audioInputStream3to1 = new PipedInputStream(
					bytesStream3to1, BUFFER_SIZE);

			PipedOutputStream bytesStream3to2 = new PipedOutputStream();
			PipedInputStream audioInputStream3to2 = new PipedInputStream(
					bytesStream3to2, BUFFER_SIZE);
			Receiver receiver3 = new Receiver(bytesStream3to1, bytesStream3to2);
			user3Server3.addDataListener(receiver3);

			// creo le collezioni da mixare
			ArrayList<PipedInputStream> streamToUser1 = new ArrayList<>();
			streamToUser1.add(audioInputStream2to1);
			streamToUser1.add(audioInputStream3to1);
			ArrayList<PipedInputStream> streamToUser2 = new ArrayList<>();
			streamToUser2.add(audioInputStream1to2);
			streamToUser2.add(audioInputStream3to2);
			ArrayList<PipedInputStream> streamToUser3 = new ArrayList<>();
			streamToUser3.add(audioInputStream1to3);
			streamToUser3.add(audioInputStream2to3);

			// creo gli stream mizati
			MixingPipedInputStream mixingInputStream1 = new MixingPipedInputStream(
					streamToUser1);
			MixingPipedInputStream mixingInputStream2 = new MixingPipedInputStream(
					streamToUser2);
			MixingPipedInputStream mixingInputStream3 = new MixingPipedInputStream(
					streamToUser3);

			// creo le classi che inviano i pacchetti mixati
			final Forwarder forwarder1 = new Forwarder(mixingInputStream1,
					user1Server1);
			final Forwarder forwarder2 = new Forwarder(mixingInputStream2,
					user2Server2);
			final Forwarder forwarder3 = new Forwarder(mixingInputStream3,
					user3Server3);

			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					TimerTask forwardingTask = new ForwardingTask(forwarder1,
							forwarder2, forwarder3);
					Timer timer = new Timer();
					timer.scheduleAtFixedRate(forwardingTask, 0, 1);
				}
			});
			thread.setPriority(Thread.MAX_PRIORITY);
			thread.start();

			user1Server1.init();
			user2Server2.init();
			user3Server3.init();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
