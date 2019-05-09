package edu.up.isgc.raytracer.tools;

import edu.up.isgc.raytracer.Vector3D;
import edu.up.isgc.raytracer.objects.Triangle;

public class Barycentric {

	private Barycentric() {
	}

	// Based on Christer Ericson's Real-Time Collision Detection
	public static double[] CalculateBarycentricCoordinates(Vector3D point, Triangle triangle) {
		double u, v, w;
		Vector3D[] vertices = triangle.getVertices();
		Vector3D a = vertices[0];
		Vector3D b = vertices[1];
		Vector3D c = vertices[2];
		
		Vector3D v0 = Vector3D.substract(b, a);
		Vector3D v1 = Vector3D.substract(c, a);
		Vector3D v2 = Vector3D.substract(point, a);
		double d00 = Vector3D.dotProduct(v0, v0);
		double d01 = Vector3D.dotProduct(v0, v1);
		double d11 = Vector3D.dotProduct(v1, v1);
		double d20 = Vector3D.dotProduct(v2, v0);
		double d21 = Vector3D.dotProduct(v2, v1);
		double denom = d00 * d11 - d01 * d01;
	    v = (d11 * d20 - d01 * d21) / denom;
	    w = (d00 * d21 - d01 * d20) / denom;
	    u = 1.0f - v - w;
	    
	    return new double[] {u,v,w};
	}

}
