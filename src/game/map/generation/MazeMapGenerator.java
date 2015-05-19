package game.map.generation;

import game.map.Dimension;
import game.map.Item;
import game.map.MapJSONizer;
import game.map.MapObject;
import game.map.Texture;
import maze.Coordinate2D;
import maze.Maze;
import maze.Node;

import org.json.JSONObject;

/**
 * @author Hermann Tom
 * @date 2015/05/08
 */

public class MazeMapGenerator implements MapGenerator {

	private Dimension mapSize;
	private MapJSONizer mapJSONizer;

	private Dimension position;
	private Dimension size;

	private Maze maze = new Maze(4, 4);

	public MazeMapGenerator(Dimension mapSize) {
		super();
		this.mapSize = mapSize;
		mapJSONizer = new MapJSONizer();
	}

	@Override
	public JSONObject generateMap() {

		mapJSONizer.setMapSize(mapSize);
		constructBorders();

		for (int i = 0; i < maze.getxSize(); i++) {

			for (int j = 0; j < maze.getySize(); j++) {
				Coordinate2D c = new Coordinate2D(this.maze, i, j);
				Node n = maze.nodeAt(c);
				int x1 = 40 / 4 * i;
				float y1 = 38 / 4 * j;
				int x2 = x1 + (10);
				float y2 = y1 + (9);

				if (!n.isLinkedTo(n.minusY())) {
					if (x1 < x2) {

						position = new Dimension((x1 - 20) / 2 + (x2 - 20) / 2, -1, (y1 - 19) / 2
								+ (y2 - 19) / 2);
						size = new Dimension(10, 1, 1.5);

						mapJSONizer.addMapObject(new MapObject(Item.WALL, Texture.WALL3, position,
								size));
					}
				}

				if (!n.isLinkedTo(n.minusX())) {
					if (y1 < y2) {

						position = new Dimension((x1 - 20) / 2 + (x2 - 20) / 2, -1, (y1 - 19 / 2)
								+ (y2 - 19) / 2);
						size = new Dimension(1.5, 1, 9);

						mapJSONizer.addMapObject(new MapObject(Item.WALL, Texture.WALL3, position,
								size));

					}
				}
			}

		}

		return mapJSONizer.getJSONMap();
	}

	/**
	 * It constructs the borders of the map.
	 */
	private void constructBorders() {

		String bordersTexture = Texture.WALL3;

		double mapWidth = mapSize.getWidth();

		mapJSONizer.addMapObject(new MapObject(Item.WALL, bordersTexture, new Dimension(0, -1,
				mapWidth), new Dimension(mapWidth * 2, 2, 1)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, bordersTexture, new Dimension(0, -1,
				mapWidth * -1), new Dimension(mapWidth * 2, 2, 1)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, bordersTexture, new Dimension(mapWidth,
				-1, 0), new Dimension(1, 2, mapWidth * 2)));
		mapJSONizer.addMapObject(new MapObject(Item.WALL, bordersTexture, new Dimension(mapWidth
				* -1, -1, 0), new Dimension(1, 2, mapWidth * 2)));
	}

}
