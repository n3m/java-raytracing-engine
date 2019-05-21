
package aemn.raytracer.lights;

import aemn.material.MaterialShader;
import aemn.raytracer.Intersection;
import aemn.raytracer.Ray;
import aemn.raytracer.Vector3D;
import aemn.raytracer.objects.Object3D;

/**
 *
 * @author Alan Maldonado
 */
public abstract class Light extends Object3D{
    
	/***
	 * 
	 * @param position
	 * @param shader
	 */
    public Light(Vector3D position, MaterialShader shader) {
        super(position, shader);
    }
    
    /***
     * Method that returns the Intersection
     */
    //@Override
    public Intersection getIntersection(Ray ray) {
        return new Intersection(Vector3D.ZERO(), -1, Vector3D.ZERO(), null);
    }
    
    public abstract float getNDotL(Intersection intersection);
}
