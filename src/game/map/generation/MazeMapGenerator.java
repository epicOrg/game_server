package game.map.generation;

import game.map.MapDimension;
import game.map.Item;
import game.map.MapConstructor;
import game.map.MapObject;
import game.map.Texture;
import game.map.maze.Coordinate2D;
import game.map.maze.Maze;
import game.map.maze.Node;
import game.map.utils.MapDefault;

/**
 * @author Hermann Tom
 * @date 2015/05/08
 */

public class MazeMapGenerator implements MapGenerator {

	private MapDimension mapSize;
	private MapConstructor mapConstructor;

	private MapDimension position;
	private MapDimension size;

	private Maze maze = new Maze(4, 4);

	public MazeMapGenerator(MapDimension mapSize) {
		super();
		this.mapSize = mapSize;
		mapConstructor = new MapConstructor();
	}

	@Override
	public MapConstructor generateMap() {

		mapConstructor.setMapSize(mapSize);
		MapDefault.constructBorders(mapConstructor, mapSize, Texture.WALL3);

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

						position = new MapDimension((x1 - 20) / 2 + (x2 - 20) / 2, -1, (y1 - 19) / 2
								+ (y2 - 19) / 2);
						size = new MapDimension(10, 1, 1.5);

						mapConstructor.addMapObject(new MapObject(Item.WALL, Texture.WALL3, position,
								size));
					}
				}

				if (!n.isLinkedTo(n.minusX())) {
					if (y1 < y2) {

						position = new MapDimension((x1 - 20) / 2 + (x2 - 20) / 2, -1, (y1 - 19 / 2)
								+ (y2 - 19) / 2);
						size = new MapDimension(1.5, 1, 9);

						mapConstructor.addMapObject(new MapObject(Item.WALL, Texture.WALL3, position,
								size));

					}
				}
			}

		}

		return mapConstructor;
	}

}
