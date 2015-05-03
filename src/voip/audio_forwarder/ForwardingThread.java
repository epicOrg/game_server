package voip.audio_forwarder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Task that try to forward data if there are any
 * 
 * @author Luca
 * @date 2015/04/28
 */
public class ForwardingThread extends Thread {

	private static final int RATE = 1;
	private static final int DELAY = 1;
	
	private ArrayList<Forwarder> forwarders = new ArrayList<>();
	private Timer timer;

	public ForwardingThread(ArrayList<Forwarder> forwarders) {
		super();
		this.forwarders = forwarders;
		this.setPriority(MAX_PRIORITY);
	}

	@Override
	public void run() {
		TimerTask forwardingTask = new TimerTask() {
			
			@Override
			public void run() {
				for (Forwarder forwarder : forwarders) {
					forwarder.forwardData();
				}				
			}
		};
		timer = new Timer();
		timer.scheduleAtFixedRate(forwardingTask, DELAY, RATE);
	}
	
	public void stopTask(){
		if(timer != null )
			timer.cancel();
	}
}
