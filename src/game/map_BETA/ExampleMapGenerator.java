package game.map_BETA;

import org.json.JSONObject;

/**
 * Example.
 * 
 * @see game.map.SimpleMapGenerator
 * @date 2015/04/23
 */

public class ExampleMapGenerator implements MapGenerator {

	private MapJSONizer mapJSONizer;

	public ExampleMapGenerator() {
		mapJSONizer = new MapJSONizer();
	}

	@Override
	public JSONObject generateMap() {

		mapJSONizer.setMapSize(new Dimension(20, 20, 20));

		mapJSONizer.addMapObject(new MapObject("Wall", "wall_texture_03", new Dimension(-9.25, -1, -2.75), new Dimension(1.5, 2.5, 14.5)));
		mapJSONizer.addMapObject(new MapObject("Wall", "wall_texture_03", new Dimension(-6.75, -1, -4.25), new Dimension(3.5, 2.5, 1.5)));
		mapJSONizer.addMapObject(new MapObject("Wall", "wall_texture_03", new Dimension(-1, -1, -9.25), new Dimension(15, 2.5, 1.5)));
		mapJSONizer.addMapObject(new MapObject("Wall", "wall_texture_03", new Dimension(7.25, -1, -2.75), new Dimension(1.5, 2.5, 14.5)));
		mapJSONizer.addMapObject(new MapObject("Wall", "wall_texture_03", new Dimension(4, -1, -4.25), new Dimension(5, 2.5, 1.5)));

		// etc...

		return mapJSONizer.getJSONMap();
	}

}
