package voip;

/**
 * Contains audio data for each player. Server and client must interchange
 * communication port and the IP for sending audio packets.
 * 
 * @author Micieli
 * @date 2015/05/03
 */

public class AudioData {

	private int localPort;
	private int remotePort;
	private int localControlPort;
	private int remoteControlPort;
	private String ip;

	public AudioData() {
		super();
	}

	public int getLocalPort() {
		return localPort;
	}

	public void setLocalPort(int localPort) {
		this.localPort = localPort;
	}

	public int getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	public int getLocalControlPort() {
		return localControlPort;
	}

	public void setLocalControlPort(int localControlPort) {
		this.localControlPort = localControlPort;
	}

	public int getRemoteControlPort() {
		return remoteControlPort;
	}

	public void setRemoteControlPort(int remoteControlPort) {
		this.remoteControlPort = remoteControlPort;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
