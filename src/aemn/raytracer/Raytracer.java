package aemn.raytracer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;

import aemn.material.LambertMat;
import aemn.material.MaterialShader;
import aemn.material.ReflectiveMat;
import aemn.material.RefractiveMat;
import aemn.raytracer.lights.Light;
import aemn.raytracer.lights.PointLight;
import aemn.raytracer.objects.Camera;
import aemn.raytracer.objects.Object3D;
import aemn.raytracer.objects.Sphere;
import aemn.raytracer.tools.OBJReader;

/**
 *
 * @author Alan Maldonado
 */
public class Raytracer {

	/**
	 * Main Algorithm
	 * 
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		float version = 1.0f;
		System.out.println("AEMN -> Raytracer v" + version);
		System.out.println(new Date());

		Scene sceneRoot = new Scene();

		/**************** Scene ****************/
		// Scene Configuration
		sceneRoot.setCamera(new Camera(new Vector3D(0, 0, -8), 160, 160, 800, 800, 0f, 50f));
		sceneRoot.addLight(new PointLight(new Vector3D(-3, 2.0, 0), new LambertMat(Color.WHITE, 500, 0, 0)));
		// Scene OBJs

		sceneRoot.addObject(OBJReader.GetPolygon("smallTeapot.obj", new Vector3D(0, -2.5, 1.5),
				new LambertMat(Color.ORANGE, 0, 5, 0.1f)));

		sceneRoot.addObject(OBJReader.GetPolygon("panel.obj", new Vector3D(0, -2.5, 1.5),
				new ReflectiveMat(Color.WHITE, 0, 5, 0.1f)));
		// Scene Objects
		sceneRoot.addObject(
				new Sphere(new Vector3D(-2.0, -2.0, 1.5), 0.5, new ReflectiveMat(Color.PINK, 0, 15, 0.5f)));
		sceneRoot.addObject(
				new Sphere(new Vector3D(2.0, -2.0, 1.5), 0.3, new ReflectiveMat(Color.WHITE, 0, 50, 0.5f)));

		sceneRoot.addObject(
				new Sphere(new Vector3D(0.3, -.8, -3), 0.4, new RefractiveMat(Color.WHITE, 0, 5, 0.5f, 1.5)));

		/****************** SCENE FINISH ****************/

		BufferedImage image = raytrace(sceneRoot);
		File outputImage = new File("image.png");
		try {
			ImageIO.write(image, "png", outputImage);
		} catch (IOException ex) {
			System.out.println("Something failed");
		}

		System.out.println(new Date());
	}

	/***
	 * Intersection tracing method.
	 * 
	 * @param ray
	 * @param objects
	 * @param caster
	 * @param clippingPlanes
	 * @return
	 */
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

	/***
	 * Scene raytracing method.
	 * 
	 * @param scene
	 * @return
	 */
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

				Color pixelColor = Color.BLACK; // Background color
				if (closestIntersection != null) {
					pixelColor = Color.BLACK;

					for (Light light : lights) {

						/***
						 * Blin-Phong Shading / Interpolation
						 */
						float specular = 1f;
						float smooth = (float) closestIntersection.getObject().getShader().getDiffuse();
						float ambient = .05f;
						float[] newRGB = MaterialShader.calculateNewColors(light, closestIntersection, mainCamera,
								ambient, specular, smooth);

						/***
						 * Reflection and Refraction
						 */
						if (closestIntersection.getObject().getShader() instanceof ReflectiveMat) {
							Intersection reflectionIntersection = reflection(closestIntersection, light, objects,
									mainCamera);

							if (reflectionIntersection != null) {

								newRGB = MaterialShader.calculateNewColors(light, reflectionIntersection, mainCamera,
										ambient, specular, smooth);

								Color newCol = new Color(clamp(newRGB[0], 0, 1), clamp(newRGB[1], 0, 1),
										clamp(newRGB[2], 0, 1));
								pixelColor = addColor(pixelColor, newCol);

							}

						} else if (closestIntersection.getObject().getShader() instanceof RefractiveMat) {
							Intersection refractedIntersection = refraction(closestIntersection, light, objects,
									mainCamera);
							if (refractedIntersection != null) {
								newRGB = MaterialShader.calculateNewColors(light, refractedIntersection, mainCamera,
										ambient, specular, smooth);
							}

							Color newCol = new Color(clamp(newRGB[0], 0, 1), clamp(newRGB[1], 0, 1),
									clamp(newRGB[2], 0, 1));
							pixelColor = addColor(pixelColor, newCol);

						}

						/***
						 * Shadow algorithm
						 */
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

	/***
	 * Reflection Method
	 * @param closestIntersection
	 * @param light
	 * @param objects
	 * @param mainCamera
	 * @return
	 */
	public static Intersection reflection(Intersection closestIntersection, Light light, ArrayList<Object3D> objects,
			Camera mainCamera) {
		Vector3D N = closestIntersection.getNormal();
		Vector3D I = Vector3D.substract(closestIntersection.getPosition(), mainCamera.getPosition());
		Vector3D R = Vector3D.add(I, Vector3D.scalarMultiplication(N, -2 * Vector3D.dotProduct(N, I)));

		// Recursiveness
		Vector3D reflectedVector = R;

		Ray reflectionRay = new Ray(closestIntersection.getPosition(), reflectedVector);
		Intersection reflectedIntersection = raycast(reflectionRay, objects, closestIntersection.getObject(), null);

		return reflectedIntersection;
	}

	/***
	 * Refraction Method
	 * @param closestIntersection
	 * @param light
	 * @param objects
	 * @param mainCamera
	 * @return
	 */
	public static Intersection refraction(Intersection closestIntersection, Light light, ArrayList<Object3D> objects,
			Camera mainCamera) {
		Intersection finalIntersection = null;
		
		double IndexOfRefraction = ((RefractiveMat) closestIntersection.getObject().getShader()).getRefractionIndex();
		Vector3D I = Vector3D.substract(closestIntersection.getPosition(), mainCamera.getPosition());
		Vector3D N = closestIntersection.getNormal();
		double IdotN = Vector3D.dotProduct(I, N);
		Vector3D T = null;

		double theta_I = 1;
		double theta_T = IndexOfRefraction;
		double cos_thetaI = clamp(-1.0f, 1.0f, (float) IdotN);
		
		Vector3D normalCpy = N.clone();
		
		if (cos_thetaI < 0) {
			cos_thetaI = -cos_thetaI;
		} else {
			double oldVal = theta_I;
			theta_I = theta_T;
			theta_T = oldVal;
			normalCpy = Vector3D.scalarMultiplication(N, -1.0);
		}
		
		double finalTheta = theta_I / theta_T;
		double finalConstant = 1 - Math.pow(finalTheta, 2) * (1 - Math.pow(cos_thetaI, 2));
		
		if (finalConstant <= 0) {
			T = Vector3D.ZERO();
		} else {
			T = Vector3D.add(Vector3D.scalarMultiplication(I, finalTheta),
					Vector3D.scalarMultiplication(normalCpy, ((finalTheta * cos_thetaI) - Math.sqrt(finalConstant))));
		}

		Vector3D refractedVector = T;

		Ray refractedRay = new Ray(closestIntersection.getPosition(), refractedVector);
		Intersection refractedIntersection = raycast(refractedRay, objects, closestIntersection.getObject(), null);
		finalIntersection = refractedIntersection;

		return finalIntersection;
	}

	/***
	 * Value Clamp Method
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */
	public static float clamp(float value, float min, float max) {
		if (value < min) {
			return min;
		}

		if (value > max) {
			return max;
		}

		return value;
	}

	/***
	 * Color per Pixel Clamping Method
	 * 
	 * @param original
	 * @param otherColor
	 * @return
	 */
	public static Color addColor(Color original, Color otherColor) {
		float red = clamp((original.getRed() / 255.0f) + (otherColor.getRed() / 255.0f), 0, 1);
		float green = clamp((original.getGreen() / 255.0f) + (otherColor.getGreen() / 255.0f), 0, 1);
		float blue = clamp((original.getBlue() / 255.0f) + (otherColor.getBlue() / 255.0f), 0, 1);
		return new Color(red, green, blue);
	}

}
