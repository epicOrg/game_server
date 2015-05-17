package game.map;

import java.util.ArrayList;
import java.util.Random;

import org.json.JSONObject;

/**
 * It generates a totally random map.
 * 
 * @author Noris
 * @date 2015/05/17
 */

public class RandomMapGenerator implements MapGenerator {

	private MapJSONizer mapJSONizer;

	private Dimension mapSize;
	private int numObjects;

	private String actualObject;
	private String actualTexture;
	private Dimension actualPosition;
	private Dimension actualSize;

	public RandomMapGenerator(Dimension mapSize, int numObjects) {
		super();
		this.mapSize = mapSize;
		this.numObjects = numObjects;
		mapJSONizer = new MapJSONizer();
	}

	@Override
	public JSONObject generateMap() {

		mapJSONizer.setMapSize(mapSize);
		setBorders();

		for (int i = 0; i < numObjects; i++) {

			setRandomObject();
			setRandomTexture();
			setRandomPosition();
			setRandomSize();

			MapObject mapObject = new MapObject(actualObject, actualTexture, actualPosition,
					actualSize);
			mapJSONizer.addMapObject(mapObject);
		}

		return mapJSONizer.getJSONMap();
	}

	private void setRandomObject() {

		ArrayList<String> mapObjects = new ArrayList<String>();
		mapObjects.add(Item.WALL);
		mapObjects.add(Item.OBSTACLE);

		actualObject = mapObjects.get(new Random().nextInt(mapObjects.size()));

	}

	/**
	 * It select a random texture from a list of textures.
	 */
	private void setRandomTexture() {

		ArrayList<String> textures = new ArrayList<String>();

		textures.add(Texture.CERAMIC1);
		textures.add(Texture.HEDGE1);
		textures.add(Texture.HEDGE2);
		textures.add(Texture.HEDGE3);
		textures.add(Texture.WALL1);
		textures.add(Texture.WALL2);
		textures.add(Texture.WALL3);
		textures.add(Texture.WOOD1);

		/*
		 * Uncomment the following lines and remove the previous lines to enable
		 * the selective random texture choice.
		 */
		// switch (actualObject) {
		// case Item.WALL:
		// textures.add(Texture.WALL1);
		// textures.add(Texture.WALL2);
		// textures.add(Texture.WALL3);
		// textures.add(Texture.HEDGE1);
		// textures.add(Texture.HEDGE2);
		// textures.add(Texture.HEDGE3);
		// break;
		// case Item.OBSTACLE:
		// textures.add(Texture.OBSTACLE);
		// textures.add(Texture.CERAMIC1);
		// break;
		// }

		actualTexture = textures.get(new Random().nextInt(textures.size()));
	}

	private void setRandomPosition() {

		Random random = new Random();

		actualPosition = new Dimension((-1 * mapSize.getWidth() / 2)
				+ (mapSize.getWidth() / 2 - (-1 * mapSize.getWidth() / 2)) * random.nextDouble(),
				-1, (-1 * mapSize.getHeight() / 2)
						+ (mapSize.getHeight() / 2 - (-1 * mapSize.getHeight() / 2))
						* random.nextDouble());

	}

	private void setRandomSize() {

		Random random = new Random();

		double third = 0;
		int resizing = 6;

		switch (actualObject) {
		case Item.WALL:
			third = (mapSize.getHeight() / resizing) * random.nextDouble();
			break;
		}

		actualSize = new Dimension((mapSize.getWidth() / resizing) * random.nextDouble(),
				(mapSize.getLength() / resizing) * random.nextDouble(), third);

	}

	private void setBorders() {

		String bordersTexture = Texture.HEDGE3;

		double mapWidth = mapSize.getWidth();

		mapJSONizer.addMapObject(new MapObject(Item.WALL, bordersTexture, new Dimension(0, -1,
				mapWidth), new Dimension(mapWidth * 2, 2, 2)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, bordersTexture, new Dimension(0, -1,
				mapWidth * -1), new Dimension(mapWidth * 2, 2, 2)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, bordersTexture, new Dimension(mapWidth,
				-1, 0), new Dimension(2, 2, mapWidth * 2)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, bordersTexture, new Dimension(mapWidth
				* -1, -1, 0), new Dimension(2, 2, mapWidth * 2)));
	}

}
