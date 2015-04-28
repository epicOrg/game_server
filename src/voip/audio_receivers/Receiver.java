package voip.audio_receivers;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import com.biasedbit.efflux.packet.DataPacket;
import com.biasedbit.efflux.participant.RtpParticipantInfo;
import com.biasedbit.efflux.session.RtpSession;
import com.biasedbit.efflux.session.RtpSessionDataListener;


/**
 * Listen to a RTPSession and stores audio bytes in streams 
 * 
 * @author Luca
 *
 */
public class Receiver implements RtpSessionDataListener{
	
	private ArrayList<PipedOutputStream> pipedOutputStreams = new ArrayList<>() ;

	public Receiver(PipedOutputStream ... bytesStreams) {
		super();
		Collections.addAll(this.pipedOutputStreams, bytesStreams);
	}


	@Override
	public void dataPacketReceived(RtpSession arg0, RtpParticipantInfo arg1,
			DataPacket packet) {
		System.out.println("Received"+packet.getDataAsArray());
		for (PipedOutputStream pipedOutputStream : pipedOutputStreams) {
			try {
				pipedOutputStream.write(packet.getDataAsArray());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
