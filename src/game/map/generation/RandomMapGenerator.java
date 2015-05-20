package game.map.generation;

import game.map.Dimension;
import game.map.Item;
import game.map.MapConstructor;
import game.map.MapObject;
import game.map.Texture;
import game.map.utils.MapDefault;

import java.util.ArrayList;
import java.util.Random;

/**
 * It generates a totally random map.
 * 
 * @author Noris
 * @date 2015/05/17
 */

public class RandomMapGenerator implements MapGenerator {

	private MapConstructor mapConstructor;

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
		mapConstructor = new MapConstructor();
	}

	@Override
	public MapConstructor generateMap() {

		mapConstructor.setMapSize(mapSize);
		MapDefault.constructBorders(mapConstructor, mapSize, Texture.HEDGE3);

		for (int i = 0; i < numObjects; i++) {

			setRandomObject();
			setRandomTexture();
			setRandomPosition();
			setRandomSize();

			MapObject mapObject = new MapObject(actualObject, actualTexture, actualPosition,
					actualSize);
			mapConstructor.addMapObject(mapObject);
		}

		return mapConstructor;
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

}
