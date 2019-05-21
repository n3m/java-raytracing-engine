package aemn.raytracer.lights;

import aemn.material.MaterialShader;
import aemn.raytracer.Intersection;
import aemn.raytracer.Vector3D;

/**
 *
 * @author Alan Maldonado
 */
public class PointLight extends Light {
	
	/***
	 * 
	 * @param position
	 * @param shader
	 */
	public PointLight(Vector3D position, MaterialShader shader) {
        super(position, shader);
    }

	/***
	 * Get Light NdotL
	 */
    public float getNDotL(Intersection intersection) {
        return (float) Math.max(Vector3D.dotProduct(intersection.getNormal(), Vector3D.normalize(Vector3D.substract(getPosition(), intersection.getPosition()))), 0.0);
    }
}
