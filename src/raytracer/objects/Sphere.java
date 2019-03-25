/**
 *  2019 - Universidad Panamericana 
 *  All Rights Reserved
 */
package raytracer.objects;

import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Vector3D;
import java.awt.Color;

/**
 *
 * @author Jafet
 */
public class Sphere extends Object3D {

    private float radius;

    public Sphere(Vector3D center, float radius, Color color) {
        super(center, color);
        setRadius(radius);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Intersection getIntersection(Ray ray) {
        double distance = -1;
        Vector3D normal = Vector3D.ZERO();
        Vector3D position = Vector3D.ZERO();
        
        Vector3D directionSphereRay = Vector3D.substract(ray.getOrigin(), getPosition());
        double firstP = Vector3D.dotProduct(ray.getDirection(), directionSphereRay);
        double secondP = Math.pow(Vector3D.magnitude(directionSphereRay), 2);
        double intersection = Math.pow(firstP, 2) - secondP + Math.pow(getRadius(), 2);

        if (intersection >= 0) {
        	double sqrtIntersection = Math.sqrt(intersection);
        	
            double part1 = -firstP + sqrtIntersection;
            double part2 = -firstP - sqrtIntersection;
            
            distance = Math.min(part1, part2);
            position = Vector3D.add(ray.getOrigin(), Vector3D.scalarMultiplication(ray.getDirection(), distance));
            normal = Vector3D.normalize(Vector3D.substract(position, getPosition()));
        } else {
            return null;
        }
        
        return new Intersection(position, distance, normal, this);
    }
}
