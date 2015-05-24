package server;

import java.util.ArrayList;

import services.Audio;
import services.Encrypt;
import services.Game;
import services.IExtendedService;
import services.IService;
import services.Logout;
import services.Polling;
import services.Register;
import services.Unknown;
import services.current_room.CurrentRoom;
import services.current_room.subservices.ListReceived;
import services.current_room.subservices.PlayerListService;
import services.current_room.subservices.RoomActions;
import services.current_room.subservices.RoomExit;
import services.rooms.Rooms;
import services.rooms.subservices.CreateRoom;
import services.rooms.subservices.JoinPlayer;
import services.rooms.subservices.RoomsList;

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
		IExtendedService rooms = initRooms();		
		services.add(rooms);
		IExtendedService currentoom = initCurrentRoom();
		services.add(currentoom);
		services.add(new Game());
		services.add(new Audio());
		services.add(new Unknown());
		services.add(new Polling());
		services.add(new Logout());
	}

	private IExtendedService initCurrentRoom() {
		IExtendedService currentoom = new CurrentRoom();
		IService playerList = new PlayerListService();
		IExtendedService roomsActions = new RoomActions();
		IService roomExit = new RoomExit();
		IService playerListReceived = new ListReceived();
		roomsActions.addSubService(roomExit,playerListReceived);
		currentoom.addSubService(roomsActions, playerList);
		return currentoom;
	}

	private IExtendedService initRooms() {
		IExtendedService rooms = new Rooms();
		rooms.addSubService(new JoinPlayer());
		rooms.addSubService(new CreateRoom());
		rooms.addSubService(new RoomsList());
		return rooms;
	}
	
	public ArrayList<IService> getServices() {
		return services;
	}
}
