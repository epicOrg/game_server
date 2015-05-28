package voip_tests;

import java.net.InetAddress;
import java.net.UnknownHostException;

import fields_names.CommonFields;
import voip.NetUtils;

/**
 * @author Micieli
 * @date 2015/05/03
 * @see NetUtils
 */

public class NetUtilsTest {

	public static void main(String[] args) throws UnknownHostException {

		for (int i = 0; i < 10; i++) {
			System.out.println("Free port: " + NetUtils.findFreePort());
		}
		
		InetAddress address = InetAddress.getByName("192.168.1.1");
		System.out.println(address.toString());
		System.out.println(NetUtils.getIpByInetAddress(address));
		System.out.println(NetUtils.getLocalIpAddress());
		
		System.out.println(CommonFields.PASSWORD.toString());
	}
	

}
