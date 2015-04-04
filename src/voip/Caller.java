package voip;

import java.net.InetAddress;

import online_management.OnlineUser;

/**
 * @author Noris
 * @date 2015/04/03
 */

public class Caller {

	private OnlineUser caller;
	private InetAddress callerIpAddress;
	private int callerPort;

	private InetAddress callee;
	private int calleePort;

	public Caller(OnlineUser caller, int callerPort, InetAddress callee) {

		this.caller = caller;
		this.callerPort = callerPort;
		this.callee = callee;
	}

	public void notifyCall() {
		// TODO
	}
}
