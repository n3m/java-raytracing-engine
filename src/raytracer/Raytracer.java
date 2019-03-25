/**
 *  2019 - Universidad Panamericana 
 *  All Rights Reserved
 */
package raytracer;

import raytracer.objects.Sphere;
import raytracer.objects.Camera;
import raytracer.objects.Object3D;
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
		System.out.println(new Date());

		Scene scene01 = new Scene();
		scene01.setCamera(new Camera(new Vector3D(0, 0, -8), 160, 160, 800, 800));
		scene01.addObject(new Sphere(new Vector3D(0f, 1f, 5f), 0.5f, Color.RED));
		scene01.addObject(new Sphere(new Vector3D(0.5f, 1f, 4.5f), 0.25f, Color.GREEN));
		scene01.addObject(new Sphere(new Vector3D(0.35f, 1f, 4.5f), 0.3f, Color.BLUE));
		scene01.addObject(new Sphere(new Vector3D(4.85f, 1f, 4.5f), 0.3f, Color.PINK));
		scene01.addObject(new Sphere(new Vector3D(2.85f, 1f, 304.5f), 0.5f, Color.BLUE));

		BufferedImage image = raytrace(scene01);
		File outputImage = new File("image.png");
		try {
			ImageIO.write(image, "png", outputImage);
		} catch (IOException ex) {
			System.out.println("Something failed");
		}

		System.out.println(new Date());
	}

	public static Intersection raycast(Ray ray, ArrayList<Object3D> objects, Object3D caster) {
		Intersection closestIntersection = null;

		for (int k = 0; k < objects.size(); k++) {
			Object3D currentObj = objects.get(k);

			if (caster == null || !currentObj.equals(caster)) {
				Intersection intersection = currentObj.getIntersection(ray);
				if (intersection != null) {
					double distance = intersection.getDistance();

					if (distance >= 0 && (closestIntersection == null || distance < closestIntersection.getDistance())) {
						closestIntersection = intersection;
					}
				}
			}
		}

		return closestIntersection;
	}

	public static BufferedImage raytrace(Scene scene) {
		Camera mainCamera = scene.getCamera();
		BufferedImage image = new BufferedImage(mainCamera.getResolution()[0], mainCamera.getResolution()[1], BufferedImage.TYPE_INT_RGB);
		ArrayList<Object3D> objects = scene.getObjects();

		Vector3D[][] positionsToRaytrace = mainCamera.calculatePositionsToRay();
		for (int i = 0; i < positionsToRaytrace.length; i++) {
			for (int j = 0; j < positionsToRaytrace[i].length; j++) {
				double x = positionsToRaytrace[i][j].getX() + mainCamera.getPosition().getX();
				double y = positionsToRaytrace[i][j].getY() + mainCamera.getPosition().getY();
				double z = positionsToRaytrace[i][j].getZ() + mainCamera.getPosition().getZ();
				Ray ray = new Ray(mainCamera.getPosition(), new Vector3D(x, y, z));

				Intersection closestIntersection = raycast(ray, objects, null);

				// Background color
				Color pixelColor = Color.WHITE;
				if (closestIntersection != null) {
					pixelColor = closestIntersection.getObject().getColor();
				}
				image.setRGB(i, j, pixelColor.getRGB());
			}
		}

		return image;
	}

}
