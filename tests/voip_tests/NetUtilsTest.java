package voip_tests;

import java.net.InetAddress;
import java.net.UnknownHostException;

import voip.NetUtils;

/**
 * @author Luca
 * @date 2015/05/03
 */

public class NetUtilsTest {

	public static void main(String[] args) throws UnknownHostException {

		for (int i = 0; i < 10; i++) {
			System.out.println("Porta libera: " + NetUtils.findFreePort());
		}
		
		InetAddress address = InetAddress.getByName("192.168.1.1");
		System.out.println(address.toString());
		System.out.println(NetUtils.getIpByInetAddress(address));
		System.out.println(NetUtils.getLocalIpAddress());
	}
	

}
