/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.up.isgc.raytracer.objects;

import edu.up.isgc.material.MaterialShader;
import edu.up.isgc.raytracer.Intersection;
import edu.up.isgc.raytracer.Ray;
import edu.up.isgc.raytracer.Vector3D;
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
	private int[] resolution;
	private float[] nearFarPlanes = new float[2];
	private static MaterialShader def = new MaterialShader(Color.black, 0, 0, 0, 0);

	public Camera(Vector3D position, float fieldOfViewHorizontal, float fieldOfViewVertical, int widthResolution, int heightResolution, float nearPlane, float farPlane){
		super(position, def);
		setFieldOfViewHorizontal(fieldOfViewHorizontal);
		setFieldOfViewVertical(fieldOfViewVertical);
		setResolution(new int[] { widthResolution, heightResolution });
		setNearFarPlanes(new float[]{nearPlane, farPlane});
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
	
	public float[] getNearFarPlanes() {
        return nearFarPlanes;
    }

    public void setNearFarPlanes(float[] nearFarPlanes) {
        this.nearFarPlanes = nearFarPlanes;
    }

	@Override
	public Intersection getIntersection(Ray ray) {
		return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null);
	}
}
