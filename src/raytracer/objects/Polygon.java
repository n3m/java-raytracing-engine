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
		double distance = -1;
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
			
			if(u < 0 || u > 1) {
				return null;
			} else {
				Vector3D Q = Vector3D.crossProduct(T, v2v0);
				double v = invDet * Vector3D.dotProduct(D, Q);
				
				if(v < 0 || (u + v) > (1.0 + epsilon)) {
					return null;
				} else {
					
					distance = invDet * Vector3D.dotProduct(Q, v1v0);
					/*System.out.println("=================================");	
					
					System.out.println("Vector0: " + Vector0.toString());
					System.out.println("Vector1: " + Vector1.toString());
					System.out.println("Vector2: " + Vector2.toString());
					
					System.out.println("Ray Origin: " + O.toString());
					System.out.println("Ray Direction: " + D.toString());
					
					System.out.println("v2v0: " + v2v0.toString());
					System.out.println("v1v0: " + v1v0.toString());
					System.out.println("P: " + P.toString());
					System.out.println("Determinant: " + determinant);
					System.out.println("InvDet: " + invDet);
					System.out.println("Q: " + Q.toString());
					
					System.out.println("v: " + v);
					System.out.println("T: " + T.toString());
					System.out.println("u: " + u);
					
					System.out.println("Intersection found: Last Calc -> " + triangle);
					System.out.println("InvDet: " + invDet);
					System.out.println("dotProduct:" + Vector3D.dotProduct(Q, v1v0));
					
					System.out.println("Distancia:" + distance);*/
					 position.setZ(distance);
					return new Intersection(position, distance, normal, this);
				}
			}
		}
		return null;
	}
	
	public ArrayList<Triangle> getTriangles() {
		return triInPol;
	}
	
	public void AddTriangle(Triangle tempVar) {
		triInPol.add(tempVar);
	}
}
