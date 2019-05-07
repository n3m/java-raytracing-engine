/**
 * 
 */
package edu.up.isgc.raytracer.lights;

import java.awt.Color;

import edu.up.isgc.raytracer.Intersection;
import edu.up.isgc.raytracer.Vector3D;

/**
 * @author User
 *
 */

public class PointLight extends Light{
	private Vector3D direction = Vector3D.ZERO();
	public PointLight(Vector3D position, Color color, double intensity) {
		super(position, color, intensity);
		// TODO Auto-generated constructor stub
	}

	public Vector3D getDirection() {
		return direction;
	}

	public void setDirection(Vector3D direction) {
		this.direction = direction;
	}

	@Override
	public float getNDotL(Intersection intersection) {
		this.direction = Vector3D.substract(Vector3D.normalize(intersection.getPosition()), Vector3D.normalize(this.getPosition()));
		
		return (float) Math.max(Vector3D.dotProduct(intersection.getNormal(), Vector3D.scalarMultiplication(getDirection(), -1.0)), 0.0);
	}
	
	

}
