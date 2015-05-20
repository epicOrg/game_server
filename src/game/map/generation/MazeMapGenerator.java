package game.map.generation;

import game.map.Dimension;
import game.map.Item;
import game.map.MapConstructor;
import game.map.MapObject;
import game.map.Texture;
import game.map.utils.MapDefault;
import maze.Coordinate2D;
import maze.Maze;
import maze.Node;

/**
 * @author Hermann Tom
 * @date 2015/05/08
 */

public class MazeMapGenerator implements MapGenerator {

	private Dimension mapSize;
	private MapConstructor mapConstructor;

	private Dimension position;
	private Dimension size;

	private Maze maze = new Maze(4, 4);

	public MazeMapGenerator(Dimension mapSize) {
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

						position = new Dimension((x1 - 20) / 2 + (x2 - 20) / 2, -1, (y1 - 19) / 2
								+ (y2 - 19) / 2);
						size = new Dimension(10, 1, 1.5);

						mapConstructor.addMapObject(new MapObject(Item.WALL, Texture.WALL3, position,
								size));
					}
				}

				if (!n.isLinkedTo(n.minusX())) {
					if (y1 < y2) {

						position = new Dimension((x1 - 20) / 2 + (x2 - 20) / 2, -1, (y1 - 19 / 2)
								+ (y2 - 19) / 2);
						size = new Dimension(1.5, 1, 9);

						mapConstructor.addMapObject(new MapObject(Item.WALL, Texture.WALL3, position,
								size));

					}
				}
			}

		}

		return mapConstructor;
	}

}
