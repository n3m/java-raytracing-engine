package raytracer.objects;

import raytracer.Vector3D;

public class Triangle{
	
	private Vector3D Vector0 = null;
	private Vector3D Vector1 = null;
	private Vector3D Vector2 = null;
	
	public Triangle(Vector3D v0, Vector3D v1, Vector3D v2) {
		setVector0(v0);
		setVector1(v1);
		setVector2(v2);
	}
	
	public Vector3D getVector0() {
		return Vector0;
	}
	public void setVector0(Vector3D vector0) {
		Vector0 = vector0;
	}
	public Vector3D getVector1() {
		return Vector1;
	}
	public void setVector1(Vector3D vector1) {
		Vector1 = vector1;
	}
	public Vector3D getVector2() {
		return Vector2;
	}
	public void setVector2(Vector3D vector2) {
		Vector2 = vector2;
	}
	
}
