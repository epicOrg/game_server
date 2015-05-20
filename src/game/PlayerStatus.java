package game;

import game.map.MapDimension;

import org.json.JSONException;
import org.json.JSONObject;

import check_fields.FieldsNames;

/**
 * @author Noris
 * @date 2015/04/19
 */

public class PlayerStatus {

	private float xPosition;
	private float yPosition;
	private float zPosition;

	private float xDirection;
	private float yDirection;
	private float zDirection;

	public PlayerStatus() {

		this.xPosition = 5;
		this.yPosition = 0.5f;
		this.zPosition = -7;

		this.xDirection = -1;
		this.yDirection = -0.25f;
		this.zDirection = 0;
	}

	public PlayerStatus(float xPosition, float yPosition, float zPosition, float xDirection,
			float yDirection, float zDirection) {

		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.zPosition = zPosition;

		this.xDirection = xDirection;
		this.yDirection = yDirection;
		this.zDirection = zDirection;
	}

	public PlayerStatus(double xPosition, double yPosition, double zPosition, double xDirection,
			double yDirection, double zDirection) {

		this.xPosition = (float) xPosition;
		this.yPosition = (float) yPosition;
		this.zPosition = (float) zPosition;

		this.xDirection = (float) xDirection;
		this.yDirection = (float) yDirection;
		this.zDirection = (float) zDirection;
	}

	public PlayerStatus(MapDimension position, MapDimension direction) {

		this.xPosition = (float) position.getWidth();
		this.yPosition = (float) position.getHeight();
		this.zPosition = (float) position.getLength();

		this.xPosition = (float) direction.getWidth();
		this.yPosition = (float) direction.getHeight();
		this.zPosition = (float) direction.getLength();
	}

	public void setPosition(float xPosition, float yPosition, float zPosition) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.zPosition = zPosition;
	}

	public void setPosition(MapDimension position) {
		this.xPosition = (float) position.getWidth();
		this.yPosition = (float) position.getHeight();
		this.zPosition = (float) position.getLength();
	}

	public float getxPosition() {
		return xPosition;
	}

	public void setxPosition(float xPosition) {
		this.xPosition = xPosition;
	}

	public float getyPosition() {
		return yPosition;
	}

	public void setyPosition(float yPosition) {
		this.yPosition = yPosition;
	}

	public float getzPosition() {
		return zPosition;
	}

	public void setzPosition(float zPosition) {
		this.zPosition = zPosition;
	}

	public void setDirection(float xDirection, float yDirection, float zDirection) {
		this.xDirection = xDirection;
		this.yDirection = yDirection;
		this.zDirection = zDirection;
	}

	public void setDirection(MapDimension direction) {
		this.xPosition = (float) direction.getWidth();
		this.yPosition = (float) direction.getHeight();
		this.zPosition = (float) direction.getLength();
	}

	public float getxDirection() {
		return xDirection;
	}

	public void setxDirection(float xDirection) {
		this.xDirection = xDirection;
	}

	public float getyDirection() {
		return yDirection;
	}

	public void setyDirection(float yDirection) {
		this.yDirection = yDirection;
	}

	public float getzDirection() {
		return zDirection;
	}

	public void setzDirection(float zDirection) {
		this.zDirection = zDirection;
	}

	public JSONObject toJSONObject() {

		JSONObject playerStatus = new JSONObject();

		try {

			JSONObject position = new JSONObject();
			position.put(FieldsNames.GAME_X, xPosition);
			position.put(FieldsNames.GAME_Y, yPosition);
			position.put(FieldsNames.GAME_Z, zPosition);

			JSONObject direction = new JSONObject();
			position.put(FieldsNames.GAME_X, xDirection);
			position.put(FieldsNames.GAME_Y, yDirection);
			position.put(FieldsNames.GAME_Z, zDirection);

			playerStatus.put(FieldsNames.GAME_POSITION, position);
			playerStatus.put(FieldsNames.GAME_DIRECTION, direction);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return playerStatus;
	}

	public String toStringPosition() {
		return xPosition + " " + yPosition + " " + zPosition;
	}

	public String toStringDirection() {
		return xDirection + " " + yDirection + " " + zDirection;
	}

}
