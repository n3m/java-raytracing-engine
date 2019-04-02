/**
 *  2019 - Universidad Panamericana 
 *  All Rights Reserved
 */
package raytracer;

import raytracer.objects.Sphere;
import raytracer.objects.Triangle;
import raytracer.objects.Camera;
import raytracer.objects.Object3D;
import raytracer.objects.Polygon;

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

	private static String version = "0.3";

	public static void main(String[] args) {
		System.out.println("AEMN -> Ray Tracer v" + version);
		System.out.println(new Date());

		Scene sceneRoot = new Scene();
		// Camera final values are MaxPlane, MinPlane
		sceneRoot.setCamera(new Camera(new Vector3D(0, 0, -8), 160, 160, 800, 800, 100f, 2f));
		sceneRoot.addObject(new Sphere(new Vector3D(0f, 1f, 5f), 0.5f, Color.RED));
		sceneRoot.addObject(new Sphere(new Vector3D(0.5f, 1f, 4.5f), 0.25f, Color.GREEN));
		sceneRoot.addObject(new Sphere(new Vector3D(0.35f, 1f, 4.5f), 0.3f, Color.BLUE));
		sceneRoot.addObject(new Sphere(new Vector3D(2.85f, 1f, 304.5f), 0.5f, Color.BLUE));
		//TEST
		sceneRoot.addObject(new Sphere(new Vector3D(0.5, 0.5, 2f), 0.9f, Color.YELLOW));
		sceneRoot.addObject(new Sphere(new Vector3D(3f, 3f, 50f), 2f, Color.BLACK));
		Polygon testPoly = new Polygon(Vector3D.ZERO(), Color.MAGENTA);
		Triangle tr1 = new Triangle(new Vector3D(-1, -1, 39.02868899114471), new Vector3D(0, -1, 14.070863778868507), new Vector3D(1, 1, 31.500551943697843));
		testPoly.AddTriangle(tr1);
		sceneRoot.addObject(testPoly);

		BufferedImage image = raytrace(sceneRoot);
		File outputImage = new File("image.png");
		try {
			ImageIO.write(image, "png", outputImage);
		} catch (IOException ex) {
			System.out.println("Something failed");
		}

		System.out.println(new Date());
	}

	public static Intersection raycast(Ray ray, ArrayList<Object3D> objects, Object3D caster, Camera cam) {
		Intersection closestIntersection = null;

		for (int k = 0; k < objects.size(); k++) {
			Object3D currentObj = objects.get(k);
			if (caster == null || !currentObj.equals(caster)) {
					Intersection intersection = currentObj.getIntersection(ray);
					if (intersection != null) {
						double distance = intersection.getDistance();
						if(Double.isNaN(distance)) {
							continue;
						}
						if (distance >= 0 && (closestIntersection == null) || distance < closestIntersection.getDistance()) {
							if (intersection.getPosition().getZ() <= cam.getMaxPlane() && intersection.getPosition().getZ() >= cam.getMinPlane()) {
								closestIntersection = intersection;
							}
						}
					}
				}
			}

	return closestIntersection;

	}

	public static BufferedImage raytrace(Scene scene) {
		Camera mainCamera = scene.getCamera();
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

				Intersection closestIntersection = raycast(ray, objects, null, mainCamera);

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
