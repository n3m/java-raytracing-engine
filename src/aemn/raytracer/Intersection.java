package aemn.raytracer;

import aemn.raytracer.objects.Object3D;

/**
 *
 * @author Alan Maldonado
 */

public class Intersection {
    private double distance;
    private Vector3D normal;
    private Vector3D position;
    private Object3D object;
    
    /***
     * 
     * @param position
     * @param distance
     * @param normal
     * @param object
     */
    
    public Intersection(Vector3D position, double distance, Vector3D normal, Object3D object){
        setPosition(position);
        setDistance(distance);
        setNormal(normal);
        setObject(object);
    }

    /***
     * Get Object3D
     * @return
     */
    public Object3D getObject() {
        return object;
    }

    /***
     * Set Object3D
     * @param object
     */
    private void setObject(Object3D object) {
        this.object = object;
    }
    
    /***
     * Get Object Position
     * @return
     */
    public Vector3D getPosition() {
        return position;
    }

    /***
     * Set Object Position
     * @param position
     */
    private void setPosition(Vector3D position) {
        this.position = position;
    }
    
    /***
     * Get Object Distance
     * @return
     */
    public double getDistance() {
        return distance;
    }
    
    /***
     * Set Object Distance
     * @param distance
     */
    private void setDistance(double distance) {
        this.distance = distance;
    }
    
    /***
     * Get Object Normal
     * @return
     */
    public Vector3D getNormal() {
        return normal;
    }

    /***
     * Set Object Normal
     * @param normal
     */
    private void setNormal(Vector3D normal) {
        this.normal = normal;
    }
}
