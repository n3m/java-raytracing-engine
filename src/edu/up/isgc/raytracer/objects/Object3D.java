/**
 *  2019 - Universidad Panamericana 
 *  All Rights Reserved
 */
package edu.up.isgc.raytracer.objects;

import edu.up.isgc.raytracer.Intersection;
import edu.up.isgc.raytracer.Ray;
import edu.up.isgc.raytracer.Vector3D;
import java.awt.Color;

/**
 *
 * @author Jafet
 */
public abstract class Object3D {  
    private Vector3D position;
    private Color color;

    public Object3D(Vector3D position, Color color){
        setPosition(position);
        setColor(color);
    }
    
    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    public abstract Intersection getIntersection(Ray ray);
    
}
