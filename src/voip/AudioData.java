package voip;

public class AudioData {
	
	private int localPort;
	private int remotePort;
	private String ip;
	
	public AudioData(int localPort, int remotePort, String ip) {
		super();
		this.localPort = localPort;
		this.remotePort = remotePort;
		this.ip = ip;
	}

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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
