package game.map_BETA;

import java.util.ArrayList;
import java.util.Random;

import org.json.JSONObject;

/**
 * @author Noris
 * @date 2015/04/23
 */

public class LinearMapGenerator implements MapGenerator {

	private static final int SCRAP = 2;

	private MapJSONizer mapJSONizer;
	private Dimension mapSize;

	private ArrayList<Double> coordinates;

	public LinearMapGenerator() {
		mapJSONizer = new MapJSONizer();
		mapSize = new Dimension(20, 20, 20);
		coordinates = new ArrayList<Double>();
	}

	public void setMapSize(Dimension mapSize) {
		this.mapSize = mapSize;
	}

	@Override
	public JSONObject generateMap() {
		// TODO
		return mapJSONizer.getJSONMap();
	}

	private void generateWallGrid() {

		int gridNumberWidth = getGridNumber((int) mapSize.getWidth());

		for (int i = 0; i < gridNumberWidth / 2; i++) {

			coordinates.add(-(i + 1) * mapSize.getWidth() / gridNumberWidth);

			if (i % 2 == 0)
				coordinates.add(mapSize.getWidth());
			else
				coordinates.add(-mapSize.getWidth());
		}

		for (int i = 0; i < gridNumberWidth / 2; i++) {

			coordinates.add((i + 1) * mapSize.getWidth() / gridNumberWidth);

			if (i % 2 == 0)
				coordinates.add(mapSize.getWidth());
			else
				coordinates.add(-mapSize.getWidth());
		}

		int gridNumberLength = getGridNumber((int) mapSize.getLength());

		// TODO

	}

	private int getGridNumber(int size) {
		return new Random().nextInt(size - 1 + (size + SCRAP / 2))
				+ (size + SCRAP / 2);
	}

	private void generateDoors() {
		// TODO
	}

	private void generateExit() {
		// TODO
	}

	private void generateSpawnLocations() {
		// TODO
	}

}
