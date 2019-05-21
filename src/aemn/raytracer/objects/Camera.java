package aemn.raytracer.objects;

import java.awt.Color;

import aemn.material.LambertMat;
import aemn.material.MaterialShader;
import aemn.raytracer.Intersection;
import aemn.raytracer.Ray;
import aemn.raytracer.Vector3D;

/**
 *
 * @author Alan Maldonado
 */
public class Camera extends Object3D {
	// 0 is fovh
	// 1 is fovv
	private float[] fieldOfView = new float[2];
	private float defaultZ = 15f;
	private int[] resolution;
	private float[] nearFarPlanes = new float[2];
	private static MaterialShader def = new LambertMat(Color.black, 0, 0, 0);

	/***
	 * 
	 * @param position
	 * @param fieldOfViewHorizontal
	 * @param fieldOfViewVertical
	 * @param widthResolution
	 * @param heightResolution
	 * @param nearPlane
	 * @param farPlane
	 */
	public Camera(Vector3D position, float fieldOfViewHorizontal, float fieldOfViewVertical, int widthResolution, int heightResolution, float nearPlane, float farPlane){
		super(position, def);
		setFieldOfViewHorizontal(fieldOfViewHorizontal);
		setFieldOfViewVertical(fieldOfViewVertical);
		setResolution(new int[] { widthResolution, heightResolution });
		setNearFarPlanes(new float[]{nearPlane, farPlane});
	}

	/***
	 * Get camera Z
	 * @return
	 */
	public float getDefaultZ() {
		return defaultZ;
	}

	/***
	 * Set camera Z
	 * @param defaultZ
	 */
	public void setDefaultZ(float defaultZ) {
		this.defaultZ = defaultZ;
	}

	/***
	 * Get camera resolution
	 * @return
	 */
	public int[] getResolution() {
		return resolution;
	}

	/***
	 * Set camera resolution
	 * @param resolution
	 */
	public void setResolution(int[] resolution) {
		this.resolution = resolution;
	}

	/***
	 * Get field of view horizontal values
	 * @return
	 */
	public float getFieldOfViewHorizontal() {
		return fieldOfView[0];
	}

	/***
	 * Get field of view vertical values
	 * @return
	 */
	public float getFieldOfViewVertical() {
		return fieldOfView[1];
	}

	/***
	 * Set field of view horizontal values
	 * @param fov
	 */
	public void setFieldOfViewHorizontal(float fov) {
		fieldOfView[0] = fov;
	}

	/***
	 * Set vertical field of view values
	 * @param fov
	 */
	public void setFieldOfViewVertical(float fov) {
		fieldOfView[1] = fov;
	}

	/***
	 * Get Field of View
	 * @return
	 */
	public float[] getFieldOfView() {
		return fieldOfView;
	}

	/***
	 * Class method that generates the 3D environment in a 2D Plane
	 * @return
	 */
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
	
	/***
	 * Method that returns the clipping planes
	 * @return
	 */
	public float[] getNearFarPlanes() {
        return nearFarPlanes;
    }

	/***
	 * Method that sets the clipping planes
	 * @param nearFarPlanes
	 */
    public void setNearFarPlanes(float[] nearFarPlanes) {
        this.nearFarPlanes = nearFarPlanes;
    }

	@Override
	public Intersection getIntersection(Ray ray) {
		return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null);
	}
}
