package voip_tests;

import voip.NetUtils;

public class NetUtilsTest {
	public static void main(String[] args) {
		
		for (int i = 0; i < 10; i++) {
			System.out.println("porta libera: " + NetUtils.findFreePort());
		}		
	}
}
