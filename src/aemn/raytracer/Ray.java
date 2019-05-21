package aemn.raytracer;

/**
 *
 * @author Alan Maldonado
 */
public class Ray {

	
    private Vector3D origin;
    private Vector3D direction;

    /***
     * 
     * @param origin
     * @param direction
     */
    public Ray(Vector3D origin, Vector3D direction) {
        setOrigin(origin);
        setDirection(direction);
    }

    /***
     * Get Ray Origin
     * @return
     */
    public Vector3D getOrigin() {
        return origin;
    }

    /***
     * Set Ray Origin
     * @param origin
     */
    public void setOrigin(Vector3D origin) {
        this.origin = origin;
    }

    /***
     * Get Ray Direction
     * @return
     */
    public Vector3D getDirection() {
        return Vector3D.normalize(direction);
    }

    /***
     * Set Ray Direction
     * @param direction
     */
    public void setDirection(Vector3D direction) {
        this.direction = direction;
    }

}
