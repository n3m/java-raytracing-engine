package edu.up.isgc.material;

import java.awt.Color;

import edu.up.isgc.raytracer.Intersection;
import edu.up.isgc.raytracer.Vector3D;
import edu.up.isgc.raytracer.lights.Light;
import edu.up.isgc.raytracer.lights.PointLight;
import edu.up.isgc.raytracer.objects.Camera;

/**
 * @author Alan Maldonado
 *
 */

public abstract class MaterialShader {
	
	private Color color;
	private double diffuse;
	private double intensity;
	private double shininess;
	private double refractionIndex;
	
	/***
	 * Material Constructor
	 * @param color
	 * @param intensity
	 * @param shininess
	 * @param diffuse
	 * @param refraction
	 */
	public MaterialShader(Color color, double intensity, double shininess, double diffuse) {
		setColor(color);
		setDiffuse(diffuse);
		setIntensity(intensity);
		setShininess(shininess);
		
	}

	/***
	 * Get Color
	 * @return
	 */
	public Color getColor() {
		return color;
	}

	/***
	 * Set Color
	 * @param color
	 */
	private void setColor(Color color) {
		this.color = color;
	}

	/***
	 * Get Diffuse Value
	 * @return
	 */
	public double getDiffuse() {
		return diffuse;
	}

	/***
	 * Set Diffuse Value
	 * @param diffuse
	 */
	private void setDiffuse(double diffuse) {
		this.diffuse = diffuse;
	}

	/***
	 * Get Intensity Value
	 * @return
	 */
	public double getIntensity() {
		return intensity;
	}

	/***
	 * Set Intensity Value
	 * @param instensity
	 */
	private void setIntensity(double instensity) {
		this.intensity = instensity;
	}

	/***
	 * Get Shininess Value
	 * @return
	 */
	public double getShininess() {
		return shininess;
	}

	/***
	 * Set Shininess Value
	 * @param shininess
	 */
	private void setShininess(double shininess) {
		this.shininess = shininess;
	}
	
	/***
	 * RGB Calculation Algorithm
	 * @param light
	 * @param intersection
	 * @param mainCamera
	 * @param ambient
	 * @param specular
	 * @param smooth
	 * @return
	 */
	public static float[] calculateNewColors(Light light, Intersection intersection, Camera mainCamera, float ambient, float specular, float smooth) {
		Color newCol = null;
		// Calculate again
		Vector3D Lr = Vector3D.substract(light.getPosition(), intersection.getPosition());
		Vector3D Vr = Vector3D.substract(mainCamera.getPosition(), intersection.getPosition());
		Vector3D Hr = Vector3D.normalize((Vector3D.add(Lr, Vr)));
		float nDotL = light.getNDotL(intersection);
		float intensity = (float) light.getShader().getIntensity() * nDotL;
		if (light instanceof PointLight) {
			intensity /= Math.pow(	
					Vector3D.magnitude(
							Vector3D.substract(intersection.getPosition(), light.getPosition())),
					2);
		}

		float smoothR = (float) intersection.getObject().getShader().getDiffuse();

		float[] colorsR = new float[] {
				intersection.getObject().getShader().getColor().getRed() / 255.0f,
				intersection.getObject().getShader().getColor().getGreen() / 255.0f,
				intersection.getObject().getShader().getColor().getBlue() / 255.0f };

		float[] newRGB_R = new float[] { 0.0f, 0.0f, 0.0f };

		newRGB_R[0] += colorsR[0] *= ambient;
		newRGB_R[1] += colorsR[1] *= ambient;
		newRGB_R[2] += colorsR[2] *= ambient;

		newRGB_R[0] += colorsR[0] *= intensity * (light.getShader().getColor().getRed() / 255.0f) * smoothR;
		newRGB_R[1] += colorsR[1] *= intensity * (light.getShader().getColor().getGreen() / 255.0f)
				* smoothR;
		newRGB_R[2] += colorsR[2] *= intensity * (light.getShader().getColor().getBlue() / 255.0f)
				* smoothR;

		specular *= (float) Math.pow(Vector3D.dotProduct(intersection.getNormal(), Hr),
				intersection.getObject().getShader().getShininess());

		newRGB_R[0] += colorsR[0] *= intensity * (light.getShader().getColor().getRed() / 255.0f)
				* specular;
		newRGB_R[1] += colorsR[1] *= intensity * (light.getShader().getColor().getGreen() / 255.0f)
				* specular;
		newRGB_R[2] += colorsR[2] *= intensity * (light.getShader().getColor().getBlue() / 255.0f)
				* specular;
		
		return newRGB_R;
	}
	
	
	
}
