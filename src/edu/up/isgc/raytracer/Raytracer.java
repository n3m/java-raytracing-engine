/**
 *  2019 - Universidad Panamericana 
 *  All Rights Reserved
 */
package edu.up.isgc.raytracer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;

import edu.up.isgc.material.MaterialShader;
import edu.up.isgc.raytracer.lights.Light;
import edu.up.isgc.raytracer.lights.PointLight;
import edu.up.isgc.raytracer.objects.Camera;
import edu.up.isgc.raytracer.objects.Object3D;
import edu.up.isgc.raytracer.objects.Sphere;
import edu.up.isgc.raytracer.tools.OBJReader;

/**
 *
 * @author Alan
 */
public class Raytracer {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		float version = 0.9f;
		System.out.println("AEMN -> Raytracer v" + version);
		System.out.println(new Date());
		Scene sceneRoot = new Scene();
		sceneRoot.setCamera(new Camera(new Vector3D(0, 0, -8), 160, 160, 1200, 1200, 2f, 50f));
		sceneRoot.addLight(new PointLight(new Vector3D(2, 1.0, -2.0), new MaterialShader(Color.WHITE, 50, 0, 0)));
		sceneRoot.addLight(new PointLight(new Vector3D(-3.0, 1.0, 0.0), new MaterialShader(Color.WHITE, 50, 0, 0)));
		
		sceneRoot.addObject(new Sphere(new Vector3D(-2f, 0f, 1f), 0.3, new MaterialShader(Color.RED, 0, 10, 0.3)));
		sceneRoot.addObject(new Sphere(new Vector3D(0f, 0f, 1f), 0.3, new MaterialShader(Color.RED, 0, 15, 0.5)));
		sceneRoot.addObject(new Sphere(new Vector3D(2f, 0f, 1f), 0.3, new MaterialShader(Color.RED, 0, 5, 0.3)));
		sceneRoot.addObject(OBJReader.GetPolygon("Cube.obj", new Vector3D(2f, -2.2f, 2f),new MaterialShader(Color.ORANGE, 0, 15, 0.5)));
		sceneRoot.addObject(OBJReader.GetPolygon("smallTeapot.obj", new Vector3D(-2f, -2.2f, 2f),new MaterialShader(Color.BLUE, 0, 15, 0.5)));
		sceneRoot.addObject(OBJReader.GetPolygon("panel.obj", new Vector3D(0f, -2.5f, 1f),
				new MaterialShader(Color.GRAY, 0, 0, 1.0)));

		BufferedImage image = raytrace(sceneRoot);
		File outputImage = new File("image.png");
		try {
			ImageIO.write(image, "png", outputImage);
		} catch (IOException ex) {
			System.out.println("Something failed");
		}

		System.out.println(new Date());
	}

	public static Intersection raycast(Ray ray, ArrayList<Object3D> objects, Object3D caster, float[] clippingPlanes) {
		Intersection closestIntersection = null;

		for (int k = 0; k < objects.size(); k++) {
			Object3D currentObj = objects.get(k);

			if (caster == null || !currentObj.equals(caster)) {
				Intersection intersection = currentObj.getIntersection(ray);
				if (intersection != null) {
					double distance = intersection.getDistance();

					if (distance >= 0 && (closestIntersection == null || distance < closestIntersection.getDistance())
							&& (clippingPlanes == null || (intersection.getPosition().getZ() >= clippingPlanes[0]
									&& intersection.getPosition().getZ() <= clippingPlanes[1]))) {
						closestIntersection = intersection;
					}
				}
			}
		}

		return closestIntersection;
	}

	public static BufferedImage raytrace(Scene scene) {
		Camera mainCamera = scene.getCamera();
		ArrayList<Light> lights = scene.getLights();
		float[] nearFarPlanes = mainCamera.getNearFarPlanes();
		BufferedImage image = new BufferedImage(mainCamera.getResolution()[0], mainCamera.getResolution()[1],
				BufferedImage.TYPE_INT_RGB);
		ArrayList<Object3D> objects = scene.getObjects();

		Vector3D[][] positionsToRaytrace = mainCamera.calculatePositionsToRay();
		for (int i = 0; i < positionsToRaytrace.length; i++) {
			for (int j = 0; j < positionsToRaytrace[i].length; j++) {
				double x = positionsToRaytrace[i][j].getX() + mainCamera.getPosition().getX();
				double y = positionsToRaytrace[i][j].getY() + mainCamera.getPosition().getY();
				double z = positionsToRaytrace[i][j].getZ() + mainCamera.getPosition().getZ();
				Ray ray = new Ray(mainCamera.getPosition(), new Vector3D(x, y, z));

				Intersection closestIntersection = raycast(ray, objects, null,
						new float[] { (float) mainCamera.getPosition().getZ() + nearFarPlanes[0],
								(float) mainCamera.getPosition().getZ() + nearFarPlanes[1] });

				// Background color
				Color pixelColor = Color.BLACK;
				if (closestIntersection != null) {
					pixelColor = Color.BLACK;
					for (Light light : lights) {
						
						Vector3D L = Vector3D.substract(light.getPosition(), closestIntersection.getPosition());
						Vector3D V = Vector3D.substract(mainCamera.getPosition(), closestIntersection.getPosition());
						Vector3D H = Vector3D.normalize((Vector3D.add(L, V)));

						float nDotL = light.getNDotL(closestIntersection);
						float intensity = (float) light.getShader().getIntensity() * nDotL;
						if (light instanceof PointLight) {
							intensity /= Math.pow(
									Vector3D.magnitude(
											Vector3D.substract(closestIntersection.getPosition(), light.getPosition())),
									2);
						}
						
						float specular = 1f;
						float smooth = (float) closestIntersection.getObject().getShader().getDiffuse();
						float ambient = .1f;
						
						float[] colors = new float[] {
								closestIntersection.getObject().getShader().getColor().getRed() / 255.0f,
								closestIntersection.getObject().getShader().getColor().getGreen() / 255.0f,
								closestIntersection.getObject().getShader().getColor().getBlue() / 255.0f };
						
						float[] newRGB = new float[] {0.0f, 0.0f, 0.0f};
						
						newRGB[0] += colors[0] *= ambient;
						newRGB[1] += colors[1] *= ambient;
						newRGB[2] += colors[2] *= ambient;				
						
						newRGB[0] += colors[0] *= intensity * (light.getShader().getColor().getRed() / 255.0f) * smooth;
						newRGB[1] += colors[1] *= intensity * (light.getShader().getColor().getGreen() / 255.0f) * smooth;
						newRGB[2] += colors[2] *= intensity * (light.getShader().getColor().getBlue() / 255.0f) * smooth;
						
						specular *= (float) Math.pow(Vector3D.dotProduct(closestIntersection.getNormal(), H),
								closestIntersection.getObject().getShader().getShininess());
						
						newRGB[0] += colors[0] *= intensity * (light.getShader().getColor().getRed() / 255.0f) * specular;
						newRGB[1] += colors[1] *= intensity * (light.getShader().getColor().getGreen() / 255.0f) * specular;
						newRGB[2] += colors[2] *= intensity * (light.getShader().getColor().getBlue() / 255.0f) * specular;
						
						// Shadow
						Ray shadowRay = new Ray(closestIntersection.getPosition(), light.getPosition());
						Intersection shadowIntersection = raycast(shadowRay, objects, closestIntersection.getObject(),
								null);

						Color diffuse = Color.black;
						if (shadowIntersection == null) {
							diffuse = new Color(clamp(newRGB[0], 0, 1), clamp(newRGB[1], 0, 1), clamp(newRGB[2], 0, 1));
						}
						pixelColor = addColor(pixelColor, diffuse);
					}

				}
				image.setRGB(i, j, pixelColor.getRGB());
			}
		}

		return image;
	}

	public static float clamp(float value, float min, float max) {
		if (value < min) {
			return min;
		}

		if (value > max) {
			return max;
		}

		return value;
	}

	public static Color addColor(Color original, Color otherColor) {
		float red = clamp((original.getRed() / 255.0f) + (otherColor.getRed() / 255.0f), 0, 1);
		float green = clamp((original.getGreen() / 255.0f) + (otherColor.getGreen() / 255.0f), 0, 1);
		float blue = clamp((original.getBlue() / 255.0f) + (otherColor.getBlue() / 255.0f), 0, 1);
		return new Color(red, green, blue);
	}

}
