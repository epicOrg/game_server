package voip.audio_receivers;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.biasedbit.efflux.packet.DataPacket;
import com.biasedbit.efflux.participant.RtpParticipantInfo;
import com.biasedbit.efflux.session.RtpSession;
import com.biasedbit.efflux.session.RtpSessionDataListener;

/**
 * Listen to a {@link RtpSession} and stores audio bytes in streams.
 * 
 * @author Micieli
 * @date 2015/04/28
 * @see PipedOutputStream
 * @see ExecutorService
 * @see RtpSession
 */

public class Receiver implements RtpSessionDataListener {

	private ArrayList<PipedOutputStream> pipedOutputStreams;

	private ExecutorService executor;

	public Receiver(ArrayList<PipedOutputStream> pipedOutputStreams) {
		super();
		this.pipedOutputStreams = pipedOutputStreams;

		executor = Executors.newFixedThreadPool(pipedOutputStreams.size());
	}

	@Override
	public void dataPacketReceived(RtpSession arg0, RtpParticipantInfo arg1,
			final DataPacket packet) {

		// TODO DEBUG: audio packets
		// System.out.println("Received Audio: " + packet.getDataAsArray());

		executor.execute(new Runnable() {
			@Override
			public void run() {
				for (PipedOutputStream pipedOutputStream : pipedOutputStreams) {
					try {
						pipedOutputStream.write(packet.getDataAsArray());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}

}
