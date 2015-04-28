package voip.audio_forwarder;

import java.io.IOException;

import voip.mixing.MixingPipedInputStream;

import com.biasedbit.efflux.session.SingleParticipantSession;

/**
 * Forward Audio data in a RTPSession mixing streams of other conversation Partecipants
 * 
 * @author Luca
 *
 */
public class Forwarder {

	public static final int DATA_LENTH = 160;

	private MixingPipedInputStream audioInputStream;
	private SingleParticipantSession session;

	public Forwarder(MixingPipedInputStream audioInputStream,
			SingleParticipantSession session) {
		super();
		this.audioInputStream = audioInputStream;
		this.session = session;
	}

	public void forwardData() {
		try {

			if (audioInputStream.available() >= DATA_LENTH) {
				byte[] data = new byte[DATA_LENTH];

				System.out.println("Reading..." );
				audioInputStream.read(data);
				System.out.println("Sending" + data);
				session.sendData(data, System.currentTimeMillis(), false);
				System.out.println("Sended" + data);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
