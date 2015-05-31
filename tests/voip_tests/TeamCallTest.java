package voip_tests;

import java.io.IOException;

import voip.RoomAudioCall;
import data_management.GameDataManager;
import exceptions.FullRoomException;
import exceptions.NoSuchRoomException;
import exceptions.RoomCancelledException;
import game.model.Player;
import game.model.Room;

/**
 * @author Micieli
 * @date 2015/05/05
 * @see RoomAudioCall
 */

class TeamCallTest {

	public static void main(String[] args) throws IOException, NoSuchRoomException {

		Room room = new Room("ciao");
		GameDataManager.getInstance().newAudioCallForRoom(room);
		Player fabio = new Player("Fabio");
		fabio.getAudioData().setIp("10.42.0.20");
		fabio.getAudioData().setLocalPort(4000);
		fabio.getAudioData().setRemotePort(33620);

		Player andrea = new Player("Andrea");
		andrea.getAudioData().setIp("10.42.0.79");
		andrea.getAudioData().setLocalPort(5000);
		andrea.getAudioData().setRemotePort(8516);

		Player luca = new Player("Luca");
		luca.getAudioData().setIp("10.42.0.23");
		luca.getAudioData().setLocalPort(6000);
		luca.getAudioData().setRemotePort(52016);

		try {

			room.addPlayer(fabio);
			room.addPlayer(andrea);
			room.addPlayer(luca);

		} catch (FullRoomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RoomCancelledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RoomAudioCall audioCall = GameDataManager.getInstance().getCallbyRoomName("ciao");
		audioCall.prepare();
		audioCall.startCall();

	}

}
