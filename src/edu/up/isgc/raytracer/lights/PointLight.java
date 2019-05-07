/**
 * 
 */
package edu.up.isgc.raytracer.lights;

import java.awt.Color;

import edu.up.isgc.raytracer.Intersection;
import edu.up.isgc.raytracer.Ray;
import edu.up.isgc.raytracer.Vector3D;
import edu.up.isgc.raytracer.objects.Object3D;

/**
 * @author User
 *
 */
public class PointLight extends Object3D{
	private Vector3D direction;

	public PointLight(Vector3D position, Color color) {
		super(position, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Intersection getIntersection(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
