package raytracer.objects;

import java.awt.Color;
import java.util.ArrayList;

import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Vector3D;

public class Polygon extends Object3D {

	public ArrayList<Triangle> triInPol = new ArrayList<Triangle>();

	public Polygon(Vector3D position, Color color) {
		super(position, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Intersection getIntersection(Ray ray) {
		// TODO Auto-generated method stub
		Intersection intersection = null;
		Intersection closestIntersection = null;
		
		double dt = 0;

		Vector3D normal = Vector3D.ZERO();
		Vector3D position = Vector3D.ZERO();
		double epsilon = 0d;

		for (Triangle triangle : triInPol) {
			Vector3D Vector0 = triangle.getVector0();
			Vector3D Vector1 = triangle.getVector1();
			Vector3D Vector2 = triangle.getVector2();

			Vector3D v2v0 = Vector3D.substract(Vector2, Vector0);
			Vector3D v1v0 = Vector3D.substract(Vector1, Vector0);

			Vector3D O = ray.getOrigin();
			Vector3D D = ray.getDirection();
			Vector3D P = Vector3D.crossProduct(D, v1v0);

			double determinant = Vector3D.dotProduct(v2v0, P);
			double invDet = 1.0 / determinant;

			Vector3D T = Vector3D.substract(O, Vector0);
			double u = invDet * Vector3D.dotProduct(T, P);

			if (u < 0 || u > 1) {
				intersection = null;
			} else {
				Vector3D Q = Vector3D.crossProduct(T, v2v0);
				double v = invDet * Vector3D.dotProduct(D, Q);

				if (v < 0 || (u + v) > (1.0 + epsilon)) {
					intersection = null;
				} else {
					dt = invDet * Vector3D.dotProduct(Q, v1v0);
					position.setZ(dt);
					intersection = new Intersection(position, dt, normal, this);
				}
			}

			if (intersection != null) {
				double distance = intersection.getDistance();
				if (distance >= 0 && (closestIntersection == null) || distance < closestIntersection.getDistance()) {
					closestIntersection = intersection;
				}
			}
		}
		return closestIntersection;
	}

	public ArrayList<Triangle> getTriangles() {
		return triInPol;
	}

	public void AddTriangle(Triangle tempVar) {
		triInPol.add(tempVar);
	}
}
