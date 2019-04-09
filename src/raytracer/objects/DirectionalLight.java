/**
 * 
 */
package raytracer.objects;

import java.awt.Color;

import raytracer.Vector3D;

/**
 * @author User
 *
 */
public class DirectionalLight {

	/**
	 * @param args
	 */
	double intensity = 0d;
	Color lightColor = null;
	
	public DirectionalLight(Vector3D direction, Color lightColor, double intensity) {
		this.lightColor = lightColor;
		this.intensity = intensity;
	}

}
