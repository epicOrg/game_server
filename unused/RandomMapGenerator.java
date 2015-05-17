package game.map;

import game.map_BETA.Dimension;
import game.map_BETA.Item;
import game.map_BETA.Texture;

import java.util.ArrayList;
import java.util.Random;

/**
 * It generates a totally random map.
 * 
 * @author Noris
 * @date 2015/05/17
 */

public class RandomMapGenerator implements IMapGenerator {

	private Dimension mapSize;
	private int numObjects;

	private String actualObject;
	private String actualTexture;
	private String actualPosition;
	private String actualSize;

	public RandomMapGenerator(Dimension mapSize, int numObjects) {
		super();
		this.mapSize = mapSize;
		this.numObjects = numObjects;
	}

	@Override
	public ArrayList<MapObject> generate() {

		ArrayList<MapObject> mapObjects = new ArrayList<MapObject>();

		for (int i = 0; i < numObjects; i++) {
			setRandomObject();
			setRandomTexture();
			setRandomPosition();
			setRandomSize();

			MapObject mapObject = new MapObject(actualObject, actualTexture, actualPosition,
					actualSize);

			mapObjects.add(mapObject);
		}

		return mapObjects;
	}

	private void setRandomObject() {

		ArrayList<String> mapObjects = new ArrayList<String>();
		mapObjects.add(Item.WALL);
		mapObjects.add(Item.OBSTACLE);

		actualObject = mapObjects.get(new Random().nextInt(mapObjects.size()));

	}

	private void setRandomTexture() {

		ArrayList<String> textures = new ArrayList<String>();

		switch (actualObject) {
		case Item.WALL:
			textures.add(Texture.WALL3);
			textures.add(Texture.HEDGE3);
			break;
		case Item.OBSTACLE:
			textures.add(Texture.WOOD1);
			break;
		}

		actualTexture = textures.get(new Random().nextInt(textures.size()));
	}

	private void setRandomPosition() {

		Random random = new Random();

		actualPosition = new Dimension((-1 * mapSize.getWidth() / 2)
				+ (mapSize.getWidth() / 2 - (-1 * mapSize.getWidth() / 2)) * random.nextFloat(),
				-1, (-1 * mapSize.getHeight() / 2)
						+ (mapSize.getHeight() / 2 - (-1 * mapSize.getHeight() / 2))
						* random.nextFloat()).toString();

	}

	private void setRandomSize() {

		Random random = new Random();

		float third = 0;
		int resizing = 5;

		switch (actualObject) {
		case Item.WALL:
			third = ((float) (mapSize.getHeight() / resizing) * random.nextFloat());
			break;
		}

		actualSize = new Dimension((mapSize.getWidth() / resizing) * random.nextFloat(),
				(mapSize.getLength() / resizing) * random.nextFloat(), third).toString();

	}

	@Override
	public float getWidth() {
		return (float) mapSize.getWidth();
	}

	@Override
	public float getHeight() {
		return (float) mapSize.getHeight();
	}

}
