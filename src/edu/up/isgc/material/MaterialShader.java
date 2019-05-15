/**
 * 
 */
package edu.up.isgc.material;

import java.awt.Color;

/**
 * @author User
 *
 */
public class MaterialShader {
	
	private Color color;
	private double diffuse;
	private double intensity;
	private double shininess;
	
	public MaterialShader(Color color, double intensity, double shininess, double diffuse) {
		setColor(color);
		setDiffuse(diffuse);
		setIntensity(intensity);
		setShininess(shininess);
	}

	public Color getColor() {
		return color;
	}

	private void setColor(Color color) {
		this.color = color;
	}

	public double getDiffuse() {
		return diffuse;
	}

	private void setDiffuse(double diffuse) {
		this.diffuse = diffuse;
	}

	public double getIntensity() {
		return intensity;
	}

	private void setIntensity(double instensity) {
		this.intensity = instensity;
	}

	public double getShininess() {
		return shininess;
	}

	private void setShininess(double shininess) {
		this.shininess = shininess;
	}
	
	
}
