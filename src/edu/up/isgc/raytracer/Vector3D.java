
package edu.up.isgc.raytracer;

/**
 *
 * @author Alan Maldonado
 */
public class Vector3D {

    private static final Vector3D ZERO = new Vector3D(0.0, 0.0, 0.0);
    private double x, y, z;

    /***
     * 
     * @param x
     * @param y
     * @param z
     */
    public Vector3D(double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    /***
     * Class Method that calculates the dot product of 2 vectors.
     * @param vectorA
     * @param vectorB
     * @return
     */
    public static double dotProduct(Vector3D vectorA, Vector3D vectorB) {
        return (vectorA.x * vectorB.x) + (vectorA.y * vectorB.y) + (vectorA.z * vectorB.z);
    }

    /***
     * Class method that calculates the cross product of 2 vectors
     * @param vectorA
     * @param vectorB
     * @return
     */
    public static Vector3D crossProduct(Vector3D vectorA, Vector3D vectorB) {
        return new Vector3D((vectorA.y * vectorB.z) - (vectorA.z * vectorB.y),
                (vectorA.z * vectorB.x) - (vectorA.x * vectorB.z),
                (vectorA.x * vectorB.y) - (vectorA.y * vectorB.x));
    }

    /***
     * Class method that calculates the magnitude of a single vector
     * @param vectorA
     * @return
     */
    public static double magnitude(Vector3D vectorA) {
        return Math.sqrt(dotProduct(vectorA, vectorA));
    }

    /***
     * Class method that adds two vectors
     * @param vectorA
     * @param vectorB
     * @return
     */
    public static Vector3D add(Vector3D vectorA, Vector3D vectorB) {
        return new Vector3D(vectorA.x + vectorB.x, vectorA.y + vectorB.y, vectorA.z + vectorB.z);
    }

    /***
     * Class method that substracts two vectors
     * @param vectorA
     * @param vectorB
     * @return
     */
    public static Vector3D substract(Vector3D vectorA, Vector3D vectorB) {
        return new Vector3D(vectorA.x - vectorB.x, vectorA.y - vectorB.y, vectorA.z - vectorB.z);
    }

    /***
     * Class method that normalizes a vector
     * @param vector
     * @return
     */
    public static Vector3D normalize(Vector3D vector) {
        double mag = Vector3D.magnitude(vector);
        return new Vector3D(vector.getX() / mag, vector.getY() / mag, vector.getZ() / mag);
    }

    /***
     * Class method that calculates the scalar multiplication between an scalar, and a vector
     * @param vector
     * @param scalar
     * @return
     */
    public static Vector3D scalarMultiplication(Vector3D vector, double scalar) {
        return new Vector3D(vector.getX() * scalar, vector.getY() * scalar, vector.getZ() * scalar);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ", " + getZ() + ")";
    }

    /***
     * Method that clones the current Vector
     */
    public Vector3D clone(){
        return new Vector3D(getX(), getY(), getZ());
    }
    
    /***
     * Class that returns a Vector initiliazed in ZERO
     * @return
     */
    public static Vector3D ZERO(){
        return ZERO.clone();
    }
}
