package game.map;

import game.model.PlayerStatus;

import java.util.ArrayList;
import java.util.LinkedList;

public interface IMap {

	public void setMapSize(MapDimension mapSize);

	public MapDimension getMapSize();

	public void addMapObject(MapObject item);

	public ArrayList<MapObject> getItems();

	public int getNumItems();

	/**
	 * Add multiple {@link MapObject} to the list of the objects.
	 * 
	 * @param items
	 *            an array of {@link MapObject}
	 */
	public void addMapObjects(MapObject... items);

	public void addSpawnPoint(PlayerStatus playerStatus);

	/**
	 * It gets all the spawn points. If there aren't spawn points it return a
	 * single default spawn point (set in {@link PlayerStatus}).
	 * 
	 * @return the spawn points
	 */
	public LinkedList<PlayerStatus> getSpawnPoints();

	/**
	 * It add a spawn point to the spawn points list. It also add the win point
	 * object to the objects list.
	 * 
	 * @param mapObject
	 *            the {@link MapObject} who represents a win point.
	 */
	public void addWinPoint(MapObject mapObject);

	public ArrayList<MapDimension> getWinPoints();

}