package voip.audio_forwarder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TimerTask;

/**
 * Task that try to forward data if there are any
 * 
 * @author Luca
 *
 */
public class ForwardingTask extends TimerTask {

	private ArrayList<Forwarder> forwarders = new ArrayList<>();

	public ForwardingTask(Forwarder... forwarders) {
		super();
		Collections.addAll(this.forwarders, forwarders);
	}

	@Override
	public void run() {
		for (Forwarder forwarder : forwarders) {
			forwarder.forwardData();
		}
	}
}
