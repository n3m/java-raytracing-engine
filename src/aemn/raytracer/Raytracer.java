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
import aemn.raytracer.lights.DirectionalLight;
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
		float version = 1.1f;
		System.out.println("AEMN -> Raytracer v" + version);
		// System.out.println(new Date());
		int def_size = 1200;

		/** Scene 01 **/
		Scene scene01 = new Scene();
		scene01.setCamera(new Camera(new Vector3D(0, 0, -8), 160, 160, def_size, def_size, 0f, 50f));
		scene01.addLight(new PointLight(new Vector3D(-3, 2.0, 0), new LambertMat(Color.WHITE, 500, 0, 0)));
		scene01.addLight(new PointLight(new Vector3D(5, 2.0, 0), new LambertMat(Color.WHITE, 500, 0, 0)));
		// Scene OBJs
		scene01.addObject(OBJReader.GetPolygon("panel.obj", new Vector3D(0, -2.5, 1.0),
				new ReflectiveMat(Color.WHITE, 0, 5, 0.1f)));
		scene01.addObject(OBJReader.GetPolygon("panel.obj", new Vector3D(0, -2.5, 5.55),
				new ReflectiveMat(Color.WHITE, 0, 5, 0.1f)));
		scene01.addObject(OBJReader.GetPolygon("panel.obj", new Vector3D(4.55, -2.5, 1),
				new ReflectiveMat(Color.WHITE, 0, 5, 0.1f)));
		scene01.addObject(OBJReader.GetPolygon("panel.obj", new Vector3D(4.55, -2.5, 5.55),
				new ReflectiveMat(Color.WHITE, 0, 5, 0.1f)));
		scene01.addObject(OBJReader.GetPolygon("panel.obj", new Vector3D(-4.55, -2.5, 1),
				new ReflectiveMat(Color.WHITE, 0, 5, 0.1f)));
		scene01.addObject(OBJReader.GetPolygon("panel.obj", new Vector3D(-4.55, -2.5, 5.55),
				new ReflectiveMat(Color.WHITE, 0, 5, 0.1f)));

		scene01.addObject(OBJReader.GetPolygon("smallTeapot.obj", new Vector3D(0, -2.5, 1.5),
				new ReflectiveMat(Color.ORANGE, 0, 5, 0.1f)));
		scene01.addObject(OBJReader.GetPolygon("venus.obj", new Vector3D(1, -2.5, 4),
				new RefractiveMat(Color.WHITE, 0, 150, 0.1f, 1.5)));

		scene01.addObject(OBJReader.GetPolygon("vertPanel.obj", new Vector3D(0, -2.5, 10),
				new ReflectiveMat(Color.CYAN, 0, 75, 0.01f)));
		/* Scene Objects */
		scene01.addObject(new Sphere(new Vector3D(-4.0, 0.0, 4), 2.5, new ReflectiveMat(Color.PINK, 0, 1000, 0.5f)));
		scene01.addObject(new Sphere(new Vector3D(2.0, -2.0, 1.5), 0.3, new ReflectiveMat(Color.RED, 0, 50, 0.5f)));
		/****************** SCENE FINISH ****************/

		/**************** Scene 02 ****************/
		// Scene Configuration

		Scene scene02 = new Scene();
		scene02.setCamera(new Camera(new Vector3D(0, 0, -8), 160, 160, def_size, def_size, 0f, 50f));

		// Scene OBJs
		scene02.addObject(
				OBJReader.GetPolygon("panel.obj", new Vector3D(0, -2.5, 1), new LambertMat(Color.WHITE, 0, 5, 0.1f)));
		scene02.addObject(
				OBJReader.GetPolygon("panel.obj", new Vector3D(0, -2.5, 5.4), new LambertMat(Color.WHITE, 0, 5, 0.1f)));

		/** First Showroom **/
		scene02.addLight(new PointLight(new Vector3D(-1.5, 1, 0), new LambertMat(Color.WHITE, 200, 0, 0)));
		scene02.addObject(OBJReader.GetPolygon("Cube.obj", new Vector3D(-1.5, -2.4, 0),
				new ReflectiveMat(Color.YELLOW, 0.0, 5.0, 0.1f)));
		scene02.addObject(OBJReader.GetPolygon("smallTeapot.obj", new Vector3D(-1.5, -1.4, 0),
				new RefractiveMat(Color.WHITE, 0.0, 10.0, 0.05f, 1.5)));

		/** Second Showroom **/
		scene02.addLight(new PointLight(new Vector3D(1.5, 1.0, 0.0), new LambertMat(Color.WHITE, 200, 0, 0)));
		scene02.addObject(OBJReader.GetPolygon("Cube.obj", new Vector3D(1.5, -2.4, 0),
				new RefractiveMat(Color.WHITE, 0, 5.0, 0.1f, 1.5)));
		scene02.addObject(
				new Sphere(new Vector3D(1.5, -0.8, 0), 0.6, new RefractiveMat(Color.WHITE, 0, 150, 0.05f, 1.5)));

		/** Third Showroom **/
		scene02.addLight(new PointLight(new Vector3D(1.5, 1.0, 5), new LambertMat(Color.WHITE, 200, 0, 0)));
		scene02.addObject(OBJReader.GetPolygon("Cube.obj", new Vector3D(1.5, -2.4, 5),
				new LambertMat(Color.ORANGE, 0, 5.0, 0.1f)));
		scene02.addObject(
				OBJReader.GetPolygon("ring.obj", new Vector3D(1.5, -1.3, 5), new LambertMat(Color.BLUE, 0, 5.0, 0.1f)));

		/** Fourth Showroom **/
		scene02.addLight(new PointLight(new Vector3D(-1.5, 1, 5), new LambertMat(Color.WHITE, 200, 0, 0)));
		scene02.addObject(OBJReader.GetPolygon("Cube.obj", new Vector3D(-1.5, -2.4, 5),
				new LambertMat(Color.ORANGE, 0.0, 5.0, 0.1f)));
		scene02.addObject(OBJReader.GetPolygon("plant.obj", new Vector3D(-1.5, -1.45, 5),
				new ReflectiveMat(Color.GREEN, 0.0, 5.0, 0.1f)));

		/****************** SCENE FINISH ****************/

		/**************** Scene 03 ****************/
		// Scene Configuration
		Scene scene03 = new Scene();
		scene03.setCamera(new Camera(new Vector3D(0, 0, -8), 160, 160, def_size, def_size, 0f, 50f));
		scene03.addLight(new PointLight(new Vector3D(0, 1, 0), new LambertMat(Color.WHITE, 300, 0, 0)));
		scene03.addLight(new PointLight(new Vector3D(0, 5, 10), new LambertMat(Color.WHITE, 300, 0, 0)));

		// Scene OBJs
		scene03.addObject(OBJReader.GetPolygon("panel.obj", new Vector3D(0.0, -2.5, 1.0),
				new LambertMat(Color.WHITE, 0, 5, 0.1f)));
		scene03.addObject(OBJReader.GetPolygon("panel.obj", new Vector3D(0, -2.5, 5.55),
				new LambertMat(Color.WHITE, 0, 5, 0.1f)));
		scene03.addObject(OBJReader.GetPolygon("panel.obj", new Vector3D(4.55, -2.5, 1),
				new LambertMat(Color.WHITE, 0, 5, 0.1f)));
		scene03.addObject(OBJReader.GetPolygon("panel.obj", new Vector3D(4.55, -2.5, 5.55),
				new LambertMat(Color.WHITE, 0, 5, 0.1f)));
		scene03.addObject(OBJReader.GetPolygon("panel.obj", new Vector3D(-4.55, -2.5, 1),
				new LambertMat(Color.WHITE, 0, 5, 0.1f)));
		scene03.addObject(OBJReader.GetPolygon("panel.obj", new Vector3D(-4.55, -2.5, 5.55),
				new LambertMat(Color.WHITE, 0, 5, 0.1f)));

		scene03.addObject(OBJReader.GetPolygon("bigCube.obj", new Vector3D(0, -2.4, 1),
				new RefractiveMat(Color.WHITE, 0, 75, 0.01f, 1.5)));
		scene03.addObject(OBJReader.GetPolygon("bigBunny.obj", new Vector3D(0, -2.5, 1),
				new LambertMat(Color.PINK, 0, 150, 0.1f)));

		scene03.addObject(new Sphere(new Vector3D(-4, 0, 0), 2.0, new ReflectiveMat(Color.RED, 0, 25, 0.1f)));
		scene03.addObject(new Sphere(new Vector3D(-3.7, 0, 4), 2.0, new ReflectiveMat(Color.PINK, 0, 25, 0.1f)));
		scene03.addObject(new Sphere(new Vector3D(4, 0, 0), 2.0, new ReflectiveMat(Color.BLUE, 0, 25, 0.1f)));
		scene03.addObject(new Sphere(new Vector3D(3.7, 0, 4), 2.0, new ReflectiveMat(Color.GREEN, 0, 25, 0.1f)));
		scene03.addObject(new Sphere(new Vector3D(0, 0, 6), 2.0, new ReflectiveMat(Color.YELLOW, 0, 25, 0.05f)));

		/****************** SCENE FINISH ****************/

		/****************** SCENE 04 ********************/
		Scene scene04 = new Scene();
		scene04.setCamera(new Camera(new Vector3D(0, 0, -8), 160, 160, def_size, def_size, 0f, 50f));
		scene04.addLight(new PointLight(new Vector3D(-2, 1, 0), new LambertMat(Color.WHITE, 300, 0, 0)));
		scene04.addLight(new PointLight(new Vector3D(2, 1, 0), new LambertMat(Color.WHITE, 300, 0, 0)));

		scene04.addObject(OBJReader.GetPolygon("panel.obj", new Vector3D(0.0, -2.5, 1.0),
				new ReflectiveMat(Color.WHITE, 0, 5, 0.1f)));
		scene04.addObject(OBJReader.GetPolygon("panel.obj", new Vector3D(0, -2.5, 5.55),
				new ReflectiveMat(Color.WHITE, 0, 5, 0.1f)));
		/*scene04.addObject(OBJReader.GetPolygon("panel.obj", new Vector3D(4.55, -2.5, 1),
				new ReflectiveMat(Color.WHITE, 0, 5, 0.1f)));
		scene04.addObject(OBJReader.GetPolygon("panel.obj", new Vector3D(4.55, -2.5, 5.55),
				new ReflectiveMat(Color.WHITE, 0, 5, 0.1f)));

		scene04.addObject(OBJReader.GetPolygon("panel.obj", new Vector3D(-4.55, -2.5, 1),
				new ReflectiveMat(Color.WHITE, 0, 5, 0.1f)));
		scene04.addObject(OBJReader.GetPolygon("panel.obj", new Vector3D(-4.55, -2.5, 5.55),
				new ReflectiveMat(Color.WHITE, 0, 5, 0.1f)));
		/*
		 * scene04.addObject(OBJReader.GetPolygon("atenea.obj", new Vector3D(0, -2.4,
		 * 1), new RefractiveMat(Color.WHITE, 0, 75, 0.01f, 1.5)));
		 */
		scene04.addObject(OBJReader.GetPolygon("bigBar.obj", new Vector3D(0, -2, 6),
				new ReflectiveMat(Color.GREEN, 0, 75, 0.01f)));
		scene04.addObject(OBJReader.GetPolygon("bigBar.obj", new Vector3D(0, -1, 6),
				new ReflectiveMat(Color.MAGENTA, 0, 75, 0.01f)));
		scene04.addObject(OBJReader.GetPolygon("bigBar.obj", new Vector3D(0, -0, 6),
				new ReflectiveMat(Color.YELLOW, 0, 75, 0.01f)));
		scene04.addObject(OBJReader.GetPolygon("bigBar.obj", new Vector3D(0, 1, 6),
				new ReflectiveMat(Color.LIGHT_GRAY, 0, 75, 0.01f)));
		scene04.addObject(
				OBJReader.GetPolygon("bigBar.obj", new Vector3D(0, 2.0, 6.0), new ReflectiveMat(Color.CYAN, 0, 75, 0.01f)));

		scene04.addObject(new Sphere(new Vector3D(-2, -1, 0), 0.4, new ReflectiveMat(Color.RED, 0, 25, 0.1f)));
		scene04.addObject(new Sphere(new Vector3D(2, -1, 0), 0.4, new ReflectiveMat(Color.PINK, 0, 25, 0.1f)));
		/****************** SCENE FINISH ****************/
		
		
		/***************** SCENE 05 *********************/
		Scene scene05 = new Scene();
		scene05.setCamera(new Camera(new Vector3D(0, 0, -8), 160, 160, def_size, def_size, 0f, 50f));
		scene05.addLight(new PointLight(new Vector3D(0, 2, 0), new LambertMat(Color.WHITE, 300, 0, 0)));
		scene05.addLight(new PointLight(new Vector3D(4, 2, 3), new LambertMat(Color.WHITE, 400, 0, 0)));
		scene05.addLight(new PointLight(new Vector3D(-4, 2, 3), new LambertMat(Color.WHITE, 400, 0, 0)));
		scene05.addObject(OBJReader.GetPolygon("vertPanel2.obj", new Vector3D(0.0, -1, 1.0),
				new ReflectiveMat(Color.CYAN, 0, 5, 0.1f)));
		scene05.addObject(OBJReader.GetPolygon("panel.obj", new Vector3D(0.0, -2.5, 1.0),
				new LambertMat(Color.WHITE, 0, 5, 0.1f)));
		scene05.addObject(OBJReader.GetPolygon("tree.obj", new Vector3D(2.0, -2.4, 3.0),
				new RefractiveMat(Color.WHITE, 0, 5, 0.1f, 1.5)));
		scene05.addObject(OBJReader.GetPolygon("tree.obj", new Vector3D(-2.0, -2.4, 3.0),
				new RefractiveMat(Color.WHITE, 0, 5, 0.1f, 1.5)));
		scene05.addObject(OBJReader.GetPolygon("bigCube.obj", new Vector3D(0, -2.4, 1.0),
				new RefractiveMat(Color.WHITE, 0, 75, 0.01f, 1.5)));
		scene05.addObject(OBJReader.GetPolygon("sword.obj", new Vector3D(0, -2.4, 1.0),
				new ReflectiveMat(Color.WHITE, 0, 75, 0.01f)));
		
		/****************** SCENE FINISH ****************/

		System.out.println(new Date());
		BufferedImage image = raytrace(scene05);
		File outputImage = new File("scene05_testVersion.png");
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
						if (closestIntersection.getObject().getShader() instanceof ReflectiveMat) { // REFLECTION START

							Intersection resultIntersection = reflection(closestIntersection, light, objects,
									mainCamera);
							Light templight = null;
							Light secondLight = null;

							if (resultIntersection != null) {

								if (light instanceof PointLight) {
									templight = new PointLight(light.getPosition(),
											new LambertMat(closestIntersection.getObject().getShader().getColor(),
													light.getShader().getIntensity(), 0, 0));
								} else if (light instanceof DirectionalLight) {
									templight = new DirectionalLight(light.getPosition(),
											((DirectionalLight) light).getDirection(),
											new LambertMat(closestIntersection.getObject().getShader().getColor(),
													light.getShader().getIntensity(), 0, 0));
								}

								Intersection secondREC = null;
								if (resultIntersection.getObject().getShader() instanceof ReflectiveMat) {
									secondREC = reflection(resultIntersection, templight, objects, mainCamera);
								} else if (resultIntersection.getObject().getShader() instanceof RefractiveMat) {
									secondREC = refraction(resultIntersection, templight, objects, mainCamera);
								}

								if (secondREC != null) {
									if (light instanceof PointLight) {
										secondLight = new PointLight(light.getPosition(),
												new LambertMat(resultIntersection.getObject().getShader().getColor(),
														light.getShader().getIntensity(), 0, 0));
									} else if (light instanceof DirectionalLight) {
										secondLight = new DirectionalLight(light.getPosition(),
												((DirectionalLight) light).getDirection(),
												new LambertMat(resultIntersection.getObject().getShader().getColor(),
														light.getShader().getIntensity(), 0, 0));
									}
									newRGB = MaterialShader.calculateNewColors(secondLight, secondREC, mainCamera,
											ambient, specular, smooth);

									// TEMP ADD
									Ray shadowRay = new Ray(secondREC.getPosition(), secondLight.getPosition());
									Intersection shadowIntersection = raycast(shadowRay, objects, secondREC.getObject(),
											null);

									Color diffuse = Color.black;
									if (shadowIntersection == null) {
										diffuse = new Color(clamp(newRGB[0], 0, 1), clamp(newRGB[1], 0, 1),
												clamp(newRGB[2], 0, 1));
									}
									pixelColor = addColor(pixelColor, diffuse);
									//
								} else {
									newRGB = MaterialShader.calculateNewColors(templight, resultIntersection,
											mainCamera, ambient, specular, smooth);

									// TEMP ADd
									Ray shadowRay = new Ray(resultIntersection.getPosition(), templight.getPosition());
									Intersection shadowIntersection = raycast(shadowRay, objects,
											resultIntersection.getObject(), null);

									Color diffuse = Color.black;
									if (shadowIntersection == null) {
										diffuse = new Color(clamp(newRGB[0], 0, 1), clamp(newRGB[1], 0, 1),
												clamp(newRGB[2], 0, 1));
									}
									pixelColor = addColor(pixelColor, diffuse);
									//
								}

							}

							Color newCol = new Color(clamp(newRGB[0], 0, 1), clamp(newRGB[1], 0, 1),
									clamp(newRGB[2], 0, 1));
							pixelColor = addColor(pixelColor, newCol);

						} else if (closestIntersection.getObject().getShader() instanceof RefractiveMat) { // REFRACTION
																											// START
							Intersection resultIntersection = refraction(closestIntersection, light, objects,
									mainCamera);
							Light templight = null;
							Light secondLight = null;

							if (resultIntersection != null) {

								if (light instanceof PointLight) {
									templight = new PointLight(light.getPosition(),
											new LambertMat(closestIntersection.getObject().getShader().getColor(),
													light.getShader().getIntensity(), 0, 0));
								} else if (light instanceof DirectionalLight) {
									templight = new DirectionalLight(light.getPosition(),
											((DirectionalLight) light).getDirection(),
											new LambertMat(closestIntersection.getObject().getShader().getColor(),
													light.getShader().getIntensity(), 0, 0));
								}

								Intersection secondREC = null;
								if (resultIntersection.getObject().getShader() instanceof ReflectiveMat) {
									secondREC = reflection(resultIntersection, templight, objects, mainCamera);
								} else if (resultIntersection.getObject().getShader() instanceof RefractiveMat) {
									secondREC = refraction(resultIntersection, templight, objects, mainCamera);
								}

								if (secondREC != null) {
									if (light instanceof PointLight) {
										secondLight = new PointLight(light.getPosition(),
												new LambertMat(resultIntersection.getObject().getShader().getColor(),
														light.getShader().getIntensity(), 0, 0));
									} else if (light instanceof DirectionalLight) {
										secondLight = new DirectionalLight(light.getPosition(),
												((DirectionalLight) light).getDirection(),
												new LambertMat(resultIntersection.getObject().getShader().getColor(),
														light.getShader().getIntensity(), 0, 0));
									}
									newRGB = MaterialShader.calculateNewColors(secondLight, secondREC, mainCamera,
											ambient, specular, smooth);

									// TEMP ADD
									Ray shadowRay = new Ray(secondREC.getPosition(), secondLight.getPosition());
									Intersection shadowIntersection = raycast(shadowRay, objects, secondREC.getObject(),
											null);

									Color diffuse = Color.black;
									if (shadowIntersection == null) {
										diffuse = new Color(clamp(newRGB[0], 0, 1), clamp(newRGB[1], 0, 1),
												clamp(newRGB[2], 0, 1));
									}
									pixelColor = addColor(pixelColor, diffuse);
									//
								} else {
									newRGB = MaterialShader.calculateNewColors(templight, resultIntersection,
											mainCamera, ambient, specular, smooth);
									// TEMP ADd
									Ray shadowRay = new Ray(resultIntersection.getPosition(), templight.getPosition());
									Intersection shadowIntersection = raycast(shadowRay, objects,
											resultIntersection.getObject(), null);

									Color diffuse = Color.black;
									if (shadowIntersection == null) {
										diffuse = new Color(clamp(newRGB[0], 0, 1), clamp(newRGB[1], 0, 1),
												clamp(newRGB[2], 0, 1));
									}
									pixelColor = addColor(pixelColor, diffuse);
									//
								}

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
	 * 
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
	 * 
	 * @param closestIntersection
	 * @param light
	 * @param objects
	 * @param mainCamera
	 * @return
	 */
	public static Intersection refraction(Intersection closestIntersection, Light light, ArrayList<Object3D> objects,
			Camera mainCamera) {
		Intersection finalIntersection = null;
		Vector3D N = closestIntersection.getNormal();
		Vector3D intersectionNewPos = Vector3D.ZERO();
		double IndexOfRefraction = ((RefractiveMat) closestIntersection.getObject().getShader()).getRefractionIndex();
		if (closestIntersection.getObject() instanceof Sphere) {
			double b = 1.5;
			Vector3D bias = Vector3D.scalarMultiplication(N, b);
			double fresnel = Math.pow((IndexOfRefraction - 1), 2) / Math.pow((IndexOfRefraction + 1), 2);
			if (fresnel > 0) {
				intersectionNewPos = Vector3D.add(closestIntersection.getPosition(), bias);
			} else {
				intersectionNewPos = Vector3D.substract(closestIntersection.getPosition(), bias);
			}
		} else {
			intersectionNewPos = closestIntersection.getPosition();
		}

		Vector3D I = Vector3D.substract(intersectionNewPos, mainCamera.getPosition());

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

		Ray refractedRay = new Ray(intersectionNewPos, refractedVector);
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
