
package edu.up.isgc.raytracer.lights;

import edu.up.isgc.material.MaterialShader;
import edu.up.isgc.raytracer.Intersection;
import edu.up.isgc.raytracer.Vector3D;
import edu.up.isgc.raytracer.lights.Light;

import java.awt.Color;

/**
 *
 * @author Alan Maldonado
 */
public class DirectionalLight extends Light {
    private Vector3D direction;
    
    /***
     * 
     * @param position
     * @param direction
     * @param shader
     */
    public DirectionalLight(Vector3D position, Vector3D direction, MaterialShader shader) {
        super(position, shader);
        setDirection(Vector3D.normalize(direction));
    }

    /***
     * Get the vector direction
     * @return
     */
    public Vector3D getDirection() {
        return direction;
    }

    /***
     * Set the vector direction
     * @param direction
     */
    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }
    
    /***
     * Get the dot Product between the Intersection Normal, and it's direction.
     */
    public float getNDotL(Intersection intersection){
    	return (float) Math.max(Vector3D.dotProduct(intersection.getNormal(), Vector3D.scalarMultiplication(getDirection(), -1.0)), 0.0);
    }
}
