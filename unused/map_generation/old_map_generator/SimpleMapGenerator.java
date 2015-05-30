package game.map.generation;

import game.map.IMap;
import game.map.MapDimension;
import game.map.Item;
import game.map.Map;
import game.map.MapObject;
import game.map.Texture;
import game.map.utils.MapDefault;
import game.model.PlayerStatus;

/**
 * It generate an example map.
 *
 * @author Noris
 * @author Torlaschi
 * @date 2015/04/23
 */

public class SimpleMapGenerator implements MapGenerator {

	private IMap mapConstructor;

	public SimpleMapGenerator() {
		mapConstructor = new Map();
	}

	@Override
	public IMap generateMap() {
		
		MapDimension mapSize = new MapDimension(20, 20, 20);

		mapConstructor.setMapSize(mapSize);
		
		MapDefault.constructBorders(mapConstructor, mapSize, 2.0, Texture.HEDGE4);

		mapConstructor.addMapObject(new MapObject(Item.WALL, Texture.WALL3, new MapDimension(-8.25, -1, -1.75), new MapDimension(1.5, 2.5, 14.5)));
		mapConstructor.addMapObject(new MapObject(Item.WALL, Texture.WALL3, new MapDimension(-5.75, -1, -3.25), new MapDimension(3.5, 2.5, 1.5)));
		mapConstructor.addMapObject(new MapObject(Item.WALL, Texture.WALL3, new MapDimension(0, -1, -8.25), new MapDimension(15, 2.5, 1.5)));
		mapConstructor.addMapObject(new MapObject(Item.WALL, Texture.WALL3, new MapDimension(8.25, -1, -1.75), new MapDimension(1.5, 2.5, 14.5)));
		mapConstructor.addMapObject(new MapObject(Item.WALL, Texture.WALL3, new MapDimension(5, -1, -3.25), new MapDimension(5, 2.5, 1.5)));
		mapConstructor.addMapObject(new MapObject(Item.OBSTACLE, Texture.WOOD1, new MapDimension(1.7, -1, -3.25), new MapDimension(0.3, 2.5, 0)));
		mapConstructor.addMapObject(new MapObject(Item.OBSTACLE, Texture.WOOD1, new MapDimension(-1.9, -1, -3.25), new MapDimension(0.3, 2.5, 0)));
		mapConstructor.addMapObject(new MapObject(Item.WALL, Texture.WALL3, new MapDimension(4.75, -1, 4.75), new MapDimension(5.5, 2.5, 1.5)));
		mapConstructor.addMapObject(new MapObject(Item.WALL, Texture.WALL3, new MapDimension(-3.75, -1, 4.75), new MapDimension(7.5, 2.5, 1.5)));
		mapConstructor.addMapObject(new MapObject(Item.WALL, Texture.WALL3, new MapDimension(0, -1, 1.25), new MapDimension(10, 2.5, 1.5)));

		mapConstructor.addSpawnPoint(new PlayerStatus(5, 0.5f, -7, -1, -0.25f, 0));
		mapConstructor.addWinPoint(new MapObject(Item.VASE, Texture.CERAMIC1, new MapDimension(0, -1, 18), new MapDimension(0.5, 1, 0)));

		return mapConstructor;
	}

}
