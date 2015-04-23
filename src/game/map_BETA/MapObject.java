package game.map_BETA;

/**
 * @author Noris
 * @date 2015/04/23
 */

public class MapObject {

	private String objectName;
	private String textureName;

	private Dimension position;
	private Dimension size;

	public MapObject(String objectName, String textureName, Dimension position,
			Dimension size) {

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

	public Dimension getPosition() {
		return position;
	}

	public Dimension getSize() {
		return size;
	}

}
