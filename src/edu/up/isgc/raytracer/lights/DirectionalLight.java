/**
 *  2019 - Universidad Panamericana 
 *  All Rights Reserved
 */
package edu.up.isgc.raytracer.lights;

import edu.up.isgc.raytracer.Intersection;
import edu.up.isgc.raytracer.Vector3D;
import edu.up.isgc.raytracer.lights.Light;
import java.awt.Color;

/**
 *
 * @author Jafet
 */
public class DirectionalLight extends Light {
    private Vector3D direction;
    
    public DirectionalLight(Vector3D position, Vector3D direction, Color color, double intensity) {
        super(position, color, intensity);
        setDirection(Vector3D.normalize(direction));
    }

    public Vector3D getDirection() {
        return direction;
    }

    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }
    
    public float getNDotL(Intersection intersection){
    	return (float) Math.max(Vector3D.dotProduct(intersection.getNormal(), Vector3D.scalarMultiplication(getDirection(), -1.0)), 0.0);
    }
}
