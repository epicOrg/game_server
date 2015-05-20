package game.map;

/**
 * MapObject describes a map item.
 * 
 * @author Noris
 * @date 2015/04/23
 */

public class MapObject implements Comparable<MapObject> {

	private String objectName;
	private String textureName;

	private MapDimension position;
	private MapDimension size;

	public MapObject(String objectName, String textureName, MapDimension position, MapDimension size) {

		this.objectName = objectName;
		this.textureName = textureName;
		this.position = position;
		this.size = size;
	}

	public String getObjectName() {
		return objectName;
	}

	public String getTextureName() {
		return textureName;
	}

	public MapDimension getPosition() {
		return position;
	}

	public MapDimension getSize() {
		return size;
	}

	@Override
	public int compareTo(MapObject other) {
		return objectName.compareTo(other.objectName) + textureName.compareTo(other.textureName)
				+ position.compareTo(other.position) + size.compareTo(other.size);
	}

}
