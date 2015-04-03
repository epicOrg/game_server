package voip;

import java.net.InetAddress;

/**
 * @author Noris
 * @date 2015/04/03
 */

public class Caller {

	private InetAddress callerIpAddress;
	private int callerPort;

	private InetAddress callee;
	private int calleePort;

	public Caller(InetAddress callerIpAddress, int callerPort,
			InetAddress callee) {

		this.callerIpAddress = callerIpAddress;
		this.callerPort = callerPort;
		this.callee = callee;
	}

	// TODO
}
