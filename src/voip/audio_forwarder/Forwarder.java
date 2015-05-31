package voip.audio_forwarder;

import java.io.IOException;

import voip.mixing.MixingPipedInputStream;

import com.biasedbit.efflux.session.SingleParticipantSession;

/**
 * Forward Audio data in a RTPSession mixing streams of other conversation
 * partecipants.
 * 
 * @author Micieli
 * @date 2015/04/28
 */

public class Forwarder {

	public static final int DATA_LENTH = 160;

	private MixingPipedInputStream audioInputStream;
	private SingleParticipantSession session;

	public Forwarder(MixingPipedInputStream audioInputStream, SingleParticipantSession session) {
		super();
		this.audioInputStream = audioInputStream;
		this.session = session;
	}

	public void forwardData() {

		byte[] data = new byte[DATA_LENTH];

		try {
			if (audioInputStream.read(data) > 0) {
				session.sendData(data, System.currentTimeMillis(), false);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
