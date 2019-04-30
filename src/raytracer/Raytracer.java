/**
 *  2019 - Universidad Panamericana 
 *  All Rights Reserved
 */
package raytracer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;

import raytracer.objects.Camera;
import raytracer.objects.DirectionalLight;
import raytracer.objects.Object3D;
import raytracer.objects.Polygon;
import raytracer.objects.Sphere;
import raytracer.objects.Triangle;

/**
 *
 * @author Jafet
 */
public class Raytracer {

	/**
	 * @param args the command line arguments
	 */

	private static String version = "0.5";

	public static void main(String[] args) {
		System.out.println("AEMN -> Ray Tracer v" + version);
		System.out.println(new Date());
		
		Scene sceneRoot = new Scene();
		// Camera final values are MaxPlane, MinPlane
		sceneRoot.setCamera(new Camera(new Vector3D(0, 0, -8), 160, 160, 800, 800, 100f, -6f));
		sceneRoot.setLight(new DirectionalLight(new Vector3D(0f, 0f, 1f)));
		
		sceneRoot.addObject(new Sphere(new Vector3D(0f, 1f, 5f), 0.5f, Color.RED));
		sceneRoot.addObject(new Sphere(new Vector3D(-1.5f, 1.5f, 4.5f), 0.3f, Color.BLUE));
		sceneRoot.addObject(OBJReader.GetPolygon("Cube.obj", new Vector3D(0f, -2.5f, 1f), Color.CYAN));
		sceneRoot.addObject(OBJReader.GetPolygon("smallTeapot.obj", new Vector3D(2f, 2f, 1.5f), Color.ORANGE));
		sceneRoot.addObject(OBJReader.GetPolygon("smallTeapot.obj", new Vector3D(2f, -2f, 1.5f), Color.ORANGE));
		sceneRoot.addObject(OBJReader.GetPolygon("smallTeapot.obj", new Vector3D(-2f, -2f, 1.5f), Color.ORANGE));
		sceneRoot.addObject(OBJReader.GetPolygon("smallTeapot.obj", new Vector3D(-2f, 2.0f, 1.5f), Color.ORANGE));
		sceneRoot.addObject(OBJReader.GetPolygon("ring.obj", new Vector3D(2f, 0f, 1.5f), Color.blue));

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
					pixelColor = FlatShading(closestIntersection.getObject().getColor(), scene.getLight().getLightColor(), scene.getLight(), closestIntersection.getNormal());
				}
				image.setRGB(i, j, pixelColor.getRGB());
			}
		}

		return image;
	}
	
	 private static Color FlatShading(Color ObjectColor, Color LightColor, DirectionalLight light, Vector3D normal) {
	    	Color newColor = null;
	    	Vector3D lightDir = light.getDirection();
	    	Vector3D ObjectNormal = normal;
	    	
	    	double NxL = Vector3D.dotProduct(lightDir, ObjectNormal);
	    	float theMultiplier = (float)(NxL * light.getIntensity());

	    	float newRed = Math.abs((((ObjectColor.getRed() * LightColor.getRed())/255F) * theMultiplier) / 255f);
	    	float newGreen = Math.abs((((ObjectColor.getGreen() * LightColor.getGreen())/255f) * theMultiplier) / 255f);
	    	float newBlue = Math.abs((((ObjectColor.getBlue() * LightColor.getBlue())/255f) * theMultiplier) / 255f);
	    	
	    	if(newRed > 1) {
	    		newRed = 1;
	    	}
	    	if (newBlue > 1) {
	    		newBlue = 1;
	    	}
	    	if (newGreen > 1) {
	    		newGreen = 1;
	    	}
	    	newColor = new Color(newRed , newGreen, newBlue);
	    	
	    	return newColor;
	    }

}
