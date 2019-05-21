package aemn.raytracer.objects;

import aemn.raytracer.Ray;
import aemn.raytracer.Vector3D;

/**
 *
 * @author Alan Maldonado
 */
public class Triangle {

    public static final double EPSILON = 0.000000001;

    private Vector3D[] vertices;
    private Vector3D[] normals;

    /***
     * 
     * @param vertex1
     * @param vertex2
     * @param vertex3
     */
    public Triangle(Vector3D vertex1, Vector3D vertex2, Vector3D vertex3) {
        setVertices(vertex1, vertex2, vertex3);
        setNormal(null);
    }

    /***
     * 
     * @param vertices
     */
    public Triangle(Vector3D[] vertices) {
        if (vertices.length == 3) {
            setVertices(vertices[0], vertices[1], vertices[2]);
        } else {
            setVertices(Vector3D.ZERO(), Vector3D.ZERO(), Vector3D.ZERO());
        }
        setNormal(null);
    }

    /***
     * 
     * @param vertices
     * @param normal
     */
    public Triangle(Vector3D[] vertices, Vector3D[] normal) {
        this(vertices);
        setNormal(normal);
    }

    /***
     * Get Vertices Array
     * @return
     */
    public Vector3D[] getVertices() {
        return vertices;
    }

    /***
     * Set the vertices array
     * @param vertex1
     * @param vertex2
     * @param vertex3
     */
    public void setVertices(Vector3D vertex1, Vector3D vertex2, Vector3D vertex3) {
        Vector3D[] vertices = new Vector3D[]{vertex1, vertex2, vertex3};
        this.vertices = vertices;
    }
    
    /***
     * Get Triangle Normal from a point
     * @param point
     * @return
     */
    public Vector3D getNormal(Vector3D point) {
        Vector3D normal = Vector3D.ZERO();
        
        Vector3D[] normals = this.normals;
        if (normals == null) {
            Vector3D[] vertices = getVertices();
            Vector3D v = Vector3D.substract(vertices[1], vertices[0]);
            Vector3D w = Vector3D.substract(vertices[2], vertices[0]);

            normal = Vector3D.scalarMultiplication(Vector3D.normalize(Vector3D.crossProduct(v, w)), -1.0);
        } else {
            for(int i = 0; i < normals.length; i++){
                normal.setX(normal.getX() + normals[i].getX());
                normal.setY(normal.getY() + normals[i].getY());
                normal.setZ(normal.getZ() + normals[i].getZ());
            }
            normal.setX(normal.getX() / normals.length);
            normal.setY(normal.getY() / normals.length);
            normal.setZ(normal.getZ() / normals.length);
        }
        
        return normal;
    }
    
    /***
     * Get triangle normal
     * @return
     */
    public Vector3D getNormal() {
        Vector3D normal = Vector3D.ZERO();
        
        Vector3D[] normals = this.normals;
        if (normals == null) {
            Vector3D[] vertices = getVertices();
            Vector3D v = Vector3D.substract(vertices[1], vertices[0]);
            Vector3D w = Vector3D.substract(vertices[2], vertices[0]);

            normal = Vector3D.scalarMultiplication(Vector3D.normalize(Vector3D.crossProduct(v, w)), -1.0);
        } else {
        	for(int i = 0; i < normals.length; i++){
                normal.setX(normal.getX() + normals[i].getX());
                normal.setY(normal.getY() + normals[i].getY());
                normal.setZ(normal.getZ() + normals[i].getZ());
            }
            normal.setX(normal.getX() / normals.length);
            normal.setY(normal.getY() / normals.length);
            normal.setZ(normal.getZ() / normals.length);
        }
        
        return normal;
    }

    /***
     * Get Triangle intersection
     * @param ray
     * @return
     */
    public double getIntersection(Ray ray) {
        Vector3D[] vertices = getVertices();
        Vector3D v2v0 = Vector3D.substract(vertices[2], vertices[0]);
        Vector3D v1v0 = Vector3D.substract(vertices[1], vertices[0]);
        Vector3D vectorP = Vector3D.crossProduct(ray.getDirection(), v1v0);
        double determinant = Vector3D.dotProduct(v2v0, vectorP);
        double invertedDeterminant = 1.0 / determinant;

        Vector3D vectorT = Vector3D.substract(ray.getOrigin(), vertices[0]);
        double u = Vector3D.dotProduct(vectorT, vectorP) * invertedDeterminant;
        if (u < 0 || u > 1) {
            return -1;
        }

        Vector3D vectorQ = Vector3D.crossProduct(vectorT, v2v0);
        double v = Vector3D.dotProduct(ray.getDirection(), vectorQ) * invertedDeterminant;
        if (v < 0 || (u + v) > (1.0 + EPSILON)) {
            return -1;
        }

        double t = Vector3D.dotProduct(vectorQ, v1v0) * invertedDeterminant;

        return t;
    }

    /***
     * Set triangle normal
     * @param normals
     */
    public void setNormal(Vector3D[] normals) {
        this.normals = normals;
    }
    
    /***
     * Get triangle normals
     * @return
     */
    public Vector3D[] getNormals() {
    	return this.normals;
    }

}
