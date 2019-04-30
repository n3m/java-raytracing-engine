/**
 *  2019 - Universidad Panamericana 
 *  All Rights Reserved
 */
package edu.up.isgc.raytracer.lights;

import edu.up.isgc.raytracer.Intersection;
import edu.up.isgc.raytracer.Ray;
import edu.up.isgc.raytracer.Vector3D;
import edu.up.isgc.raytracer.objects.Object3D;
import java.awt.Color;

/**
 *
 * @author Jafet
 */
public abstract class Light extends Object3D{
    private double intensity;
    
    public Light(Vector3D position, Color color, double intensity) {
        super(position, color);
        setIntensity(intensity);
    }
    
    //@Override
    public Intersection getIntersection(Ray ray) {
        return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null);
    }
    
    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }
    
    public abstract float getNDotL(Intersection intersection);
}
