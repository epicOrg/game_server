package game.map;

/**
 * @author Torlaschi
 * @date 2015/04/22
 */

public class MapObject {
	public String object, texture, position, size;

	public MapObject(String object, String texture, String position, String size) {
		this.object = object;
		this.texture = texture;
		this.position = position;
		this.size = size;
	}
}
