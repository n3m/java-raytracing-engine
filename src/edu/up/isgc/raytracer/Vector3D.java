/**
 *  2019 - Universidad Panamericana 
 *  All Rights Reserved
 */
package edu.up.isgc.raytracer;

/**
 *
 * @author Jafet
 */
public class Vector3D {

    private static final Vector3D ZERO = new Vector3D(0.0, 0.0, 0.0);
    private double x, y, z;

    public Vector3D(double x, double y, double z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    public static double dotProduct(Vector3D vectorA, Vector3D vectorB) {
        return (vectorA.x * vectorB.x) + (vectorA.y * vectorB.y) + (vectorA.z * vectorB.z);
    }

    public static Vector3D crossProduct(Vector3D vectorA, Vector3D vectorB) {
        return new Vector3D((vectorA.y * vectorB.z) - (vectorA.z * vectorB.y),
                (vectorA.z * vectorB.x) - (vectorA.x * vectorB.z),
                (vectorA.x * vectorB.y) - (vectorA.y * vectorB.x));
    }

    public static double magnitude(Vector3D vectorA) {
        return Math.sqrt(dotProduct(vectorA, vectorA));
    }

    public static Vector3D add(Vector3D vectorA, Vector3D vectorB) {
        return new Vector3D(vectorA.x + vectorB.x, vectorA.y + vectorB.y, vectorA.z + vectorB.z);
    }

    public static Vector3D substract(Vector3D vectorA, Vector3D vectorB) {
        return new Vector3D(vectorA.x - vectorB.x, vectorA.y - vectorB.y, vectorA.z - vectorB.z);
    }

    public static Vector3D normalize(Vector3D vector) {
        double mag = Vector3D.magnitude(vector);
        return new Vector3D(vector.getX() / mag, vector.getY() / mag, vector.getZ() / mag);
    }

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

    /*@Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Vector3D)) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        Vector3D other = (Vector3D) obj;
        return this.getX() == other.getX() && this.getY() == other.getY() && this.getZ() == other.getZ();
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
    */
    
    public Vector3D clone(){
        return new Vector3D(getX(), getY(), getZ());
    }
    
    public static Vector3D ZERO(){
        return ZERO.clone();
    }
}
