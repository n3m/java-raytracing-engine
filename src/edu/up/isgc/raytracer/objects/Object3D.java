/**
 *  2019 - Universidad Panamericana 
 *  All Rights Reserved
 */
package edu.up.isgc.raytracer.objects;

import edu.up.isgc.material.MaterialShader;
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
    private MaterialShader shader;

    public Object3D(Vector3D position, MaterialShader shader){
        setPosition(position);
        setShader(shader);
    }
    
    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    
    public abstract Intersection getIntersection(Ray ray);

	public MaterialShader getShader() {
		return shader;
	}

	public void setShader(MaterialShader shader) {
		this.shader = shader;
	}
    
}
