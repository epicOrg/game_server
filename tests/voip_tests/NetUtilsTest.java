package voip_tests;

import voip.NetUtils;

/**
 * @author Luca
 * @date 2015/05/03
 */

public class NetUtilsTest {

	public static void main(String[] args) {

		for (int i = 0; i < 10; i++) {
			System.out.println("Porta libera: " + NetUtils.findFreePort());
		}
	}

}
