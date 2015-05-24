package server;

import java.util.ArrayList;

import services.Audio;
import services.CurrentRoom;
import services.Encrypt;
import services.Game;
import services.IExtendedService;
import services.IService;
import services.Logout;
import services.Polling;
import services.Register;
import services.RoomService;
import services.Unknown;
import services.subservices.current_room.ListReceived;
import services.subservices.current_room.PlayerListService;
import services.subservices.current_room.RoomActions;
import services.subservices.current_room.RoomExit;

public class ServicesInitializer {
	
	private ArrayList<IService> services;
	
	
	
	public ServicesInitializer() {
		super();
		services = new ArrayList<>();
		init();
	}

	private void init(){
		services.add(new Encrypt());
		services.add(new Register());
		services.add(new RoomService());
		IExtendedService currentoom = new CurrentRoom();
		IService playerList = new PlayerListService();
		IExtendedService roomsActions = new RoomActions();
		IService roomExit = new RoomExit();
		IService playerListReceived = new ListReceived();
		roomsActions.addSubService(roomExit,playerListReceived);
		currentoom.addSubService(roomsActions, playerList);
		services.add(new CurrentRoom());
		services.add(new Game());
		services.add(new Audio());
		services.add(new Unknown());
		services.add(new Polling());
		services.add(new Logout());
	}
	
	public ArrayList<IService> getServices() {
		return services;
	}
}
