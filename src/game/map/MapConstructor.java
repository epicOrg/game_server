package game.map;

import game.PlayerStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * It can be used to create a map, starting from single mapObjects (one, more,
 * or none), the size of the map, spawn points and win point(s).
 * 
 * @author Noris
 * @date 2015/04/23
 */

public class MapConstructor {

	private Dimension mapSize;

	private ArrayList<MapObject> items;
	private int numItems = 0;

	private LinkedList<PlayerStatus> spawnPoints;

	private ArrayList<Dimension> winPoints;

	public MapConstructor() {
		items = new ArrayList<MapObject>();
		spawnPoints = new LinkedList<PlayerStatus>();
		winPoints = new ArrayList<Dimension>();
	}

	public void setMapSize(Dimension mapSize) {
		this.mapSize = mapSize;
	}

	public Dimension getMapSize() {
		return mapSize;
	}

	public void addMapObject(MapObject item) {
		items.add(item);
		numItems++;
	}

	public ArrayList<MapObject> getItems() {
		return items;
	}

	public int getNumItems() {
		return numItems;
	}

	public void addMapObjects(MapObject... items) {
		Collections.addAll(this.items, items);
	}

	public void addSpawnPoint(PlayerStatus playerStatus) {
		spawnPoints.add(playerStatus);
	}

	public LinkedList<PlayerStatus> getSpawnPoints() {

		if (spawnPoints.isEmpty())
			spawnPoints.add(new PlayerStatus());

		return spawnPoints;
	}

	public void addWinPoint(MapObject mapObject) {
		winPoints.add(mapObject.getPosition());
		addMapObject(mapObject);
	}

	public ArrayList<Dimension> getWinPoints() {
		return winPoints;
	}

}
