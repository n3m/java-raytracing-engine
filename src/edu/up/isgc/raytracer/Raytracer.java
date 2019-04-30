/**
 *  2019 - Universidad Panamericana 
 *  All Rights Reserved
 */
package edu.up.isgc.raytracer;

import edu.up.isgc.raytracer.objects.Sphere;
import edu.up.isgc.raytracer.tools.OBJReader;
import edu.up.isgc.raytracer.lights.DirectionalLight;
import edu.up.isgc.raytracer.lights.Light;
import edu.up.isgc.raytracer.objects.Camera;
import edu.up.isgc.raytracer.objects.Object3D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.imageio.ImageIO;

/**
 *
 * @author Jafet
 */
public class Raytracer {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		float version = 0.5f;
		System.out.println("AEMN -> Raytracer v" + version);
		System.out.println(new Date());

		Scene scene01 = new Scene();
		scene01.setCamera(new Camera(new Vector3D(0, 0, -8), 160, 160, 800, 800, 8.2f, 50f));
		scene01.addLight(new DirectionalLight(Vector3D.ZERO(), new Vector3D(0.0, 0.0, 1.0), Color.WHITE, 1.6));
		
		scene01.addObject(new Sphere(new Vector3D(0f, 1f, 5f), 0.5f, Color.RED));
		scene01.addObject(new Sphere(new Vector3D(0.5f, 1f, 4.5f), 0.25f, new Color(200,255,0)));
		scene01.addObject(new Sphere(new Vector3D(0.35f, 1f, 4.5f), 0.3f, Color.BLUE));
		scene01.addObject(new Sphere(new Vector3D(4.85f, 1f, 4.5f), 0.3f, Color.PINK));
		scene01.addObject(new Sphere(new Vector3D(2.85f, 1f, 304.5f), 0.5f, Color.BLUE));
		scene01.addObject(OBJReader.GetPolygon("Cube.obj", new Vector3D(0f, -2.5f, 1f), Color.WHITE));
		scene01.addObject(OBJReader.GetPolygon("ring.obj", new Vector3D(2f, -1.0f, 1.5f), Color.blue));

		BufferedImage image = raytrace(scene01);
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

					if (distance >= 0 && (closestIntersection == null || distance < closestIntersection.getDistance()) && (clippingPlanes == null
							|| (intersection.getPosition().getZ() >= clippingPlanes[0] && intersection.getPosition().getZ() <= clippingPlanes[1]))) {
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
		BufferedImage image = new BufferedImage(mainCamera.getResolution()[0], mainCamera.getResolution()[1], BufferedImage.TYPE_INT_RGB);
		ArrayList<Object3D> objects = scene.getObjects();

		Vector3D[][] positionsToRaytrace = mainCamera.calculatePositionsToRay();
		for (int i = 0; i < positionsToRaytrace.length; i++) {
			for (int j = 0; j < positionsToRaytrace[i].length; j++) {
				double x = positionsToRaytrace[i][j].getX() + mainCamera.getPosition().getX();
				double y = positionsToRaytrace[i][j].getY() + mainCamera.getPosition().getY();
				double z = positionsToRaytrace[i][j].getZ() + mainCamera.getPosition().getZ();
				Ray ray = new Ray(mainCamera.getPosition(), new Vector3D(x, y, z));

				Intersection closestIntersection = raycast(ray, objects, null, new float[] {
						(float) mainCamera.getPosition().getZ() + nearFarPlanes[0], (float) mainCamera.getPosition().getZ() + nearFarPlanes[1] });

				// Background color
				Color pixelColor = Color.BLACK;
				if (closestIntersection != null) {
					pixelColor = Color.BLACK;
					for (Light light : lights) {
						float nDotL = light.getNDotL(closestIntersection);
						float intensity = (float) light.getIntensity() * nDotL;
						float[] colors = new float[] { closestIntersection.getObject().getColor().getRed() / 255.0f,
								closestIntersection.getObject().getColor().getGreen() / 255.0f,
								closestIntersection.getObject().getColor().getBlue() / 255.0f };
						colors[0] *= intensity * (light.getColor().getRed() / 255.0f);
						colors[1] *= intensity * (light.getColor().getGreen() / 255.0f);
						colors[2] *= intensity * (light.getColor().getBlue() / 255.0f);

						Color diffuse = new Color(clamp(colors[0], 0, 1), clamp(colors[1], 0, 1), clamp(colors[2], 0, 1));
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
