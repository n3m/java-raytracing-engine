/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracer.objects;

import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Vector3D;
import java.awt.Color;

/**
 *
 * @author Jafet
 */
public class Camera extends Object3D {
	// 0 is fovh
	// 1 is fovv
	private float[] fieldOfView = new float[2];
	private float defaultZ = 15f;
	private float maxPlane = 10f;
	private float minPlane = 2f;
	private int[] resolution;

	public Camera(Vector3D position, float fieldOfViewHorizontal, float fieldOfViewVertical, int widthResolution, int heightResolution, float maxPlane, float minPlane) {
		super(position, Color.black);
		setFieldOfViewHorizontal(fieldOfViewHorizontal);
		setFieldOfViewVertical(fieldOfViewVertical);
		setResolution(new int[] { widthResolution, heightResolution });
		setMaxPlane(maxPlane);
		setMinPlane(minPlane);
	}

	public float getDefaultZ() {
		return defaultZ;
	}

	public void setDefaultZ(float defaultZ) {
		this.defaultZ = defaultZ;
	}

	public int[] getResolution() {
		return resolution;
	}

	public void setResolution(int[] resolution) {
		this.resolution = resolution;
	}

	public float getFieldOfViewHorizontal() {
		return fieldOfView[0];
	}

	public float getFieldOfViewVertical() {
		return fieldOfView[1];
	}

	public void setFieldOfViewHorizontal(float fov) {
		fieldOfView[0] = fov;
	}

	public void setFieldOfViewVertical(float fov) {
		fieldOfView[1] = fov;
	}

	public float[] getFieldOfView() {
		return fieldOfView;
	}

	public Vector3D[][] calculatePositionsToRay() {
		float angleMaxX = 90 - (getFieldOfViewHorizontal() / 2f);
		float radiusMaxX = getDefaultZ() / (float) Math.cos(Math.toRadians(angleMaxX));

		float maxX = (float) Math.sin(Math.toRadians(angleMaxX)) * radiusMaxX;
		float minX = -maxX;

		float angleMaxY = 90 - (getFieldOfViewVertical() / 2f);
		float radiusMaxY = getDefaultZ() / (float) Math.cos(Math.toRadians(angleMaxY));

		float maxY = (float) Math.sin(Math.toRadians(angleMaxY)) * radiusMaxY;
		float minY = -maxY;

		Vector3D[][] positions = new Vector3D[getResolution()[0]][getResolution()[1]];
		for (int x = 0; x < positions.length; x++) {
			for (int y = 0; y < positions[x].length; y++) {
				float posX = minX + (((maxX - minX) / (float) positions.length) * x);
				float posY = maxY - (((maxY - minY) / (float) positions[x].length) * y);
				float posZ = getDefaultZ();
				positions[x][y] = new Vector3D(posX, posY, posZ);
			}
		}

		return positions;
	}

	@Override
	public Intersection getIntersection(Ray ray) {
		return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null);
	}

	public float getMaxPlane() {
		return maxPlane;
	}

	public void setMaxPlane(float maxPlane) {
		this.maxPlane = maxPlane;
	}

	public float getMinPlane() {
		return minPlane;
	}

	public void setMinPlane(float minPlane) {
		this.minPlane = minPlane;
	}
}
