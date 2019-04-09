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
	private double intensity = 1d;
	private Color lightColor = Color.WHITE;
	private Vector3D direction;
	
	public DirectionalLight(Vector3D direction, Color lightColor, double intensity) {
		this.setLightColor(lightColor);
		this.setIntensity(intensity);
		this.setDirection(direction);
	}
	
	public DirectionalLight(Vector3D direction) {
		this.setDirection(direction);
	}

	public Color getLightColor() {
		return lightColor;
	}

	public void setLightColor(Color lightColor) {
		this.lightColor = lightColor;
	}

	public Vector3D getDirection() {
		return direction;
	}

	public void setDirection(Vector3D direction) {
		this.direction = direction;
	}

	public double getIntensity() {
		return intensity;
	}

	public void setIntensity(double intensity) {
		this.intensity = intensity;
	}

}
