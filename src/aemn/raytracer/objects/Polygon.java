
package aemn.raytracer.objects;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import aemn.material.MaterialShader;
import aemn.raytracer.Intersection;
import aemn.raytracer.Ray;
import aemn.raytracer.Vector3D;
import aemn.raytracer.tools.Barycentric;

/**
 *
 * @author Alan Maldonado
 */
public class Polygon extends Object3D {

    public static final int AMOUNT_VERTICES = 3;

    private List<Triangle> triangles;

    /***
     * 
     * @param position
     * @param triangles
     * @param shader
     */
    public Polygon(Vector3D position, Triangle[] triangles, MaterialShader shader) {
        super(position, shader);
        setTriangles(triangles);
    }

    /***
     * Get all triangles in a polygon
     * @return
     */
    public List<Triangle> getTriangles() {
        return triangles;
    }

    /***
     * Set all the triangles in a Polygon
     * @param triangles
     */
    public void setTriangles(Triangle[] triangles) {
        Vector3D position = getPosition();
        Set<Vector3D> uniqueVertices = new HashSet<Vector3D>();

        for (Triangle triangle : triangles) {
            uniqueVertices.addAll(Arrays.asList(triangle.getVertices()));
        }

        for (Vector3D vertex : uniqueVertices) {
            vertex.setX(vertex.getX() + position.getX());
            vertex.setY(vertex.getY() + position.getY());
            vertex.setZ(vertex.getZ() + position.getZ());
        }

        this.triangles = Arrays.asList(triangles);
    }

    /***
     * Get each triangle intersecton
     */
    public Intersection getIntersection(Ray ray) {
        double distance = -1;
        Vector3D normal = Vector3D.ZERO();
        Vector3D position = Vector3D.ZERO();

        for (Triangle triangle : getTriangles()) {
        	double intersection = triangle.getIntersection(ray);

            if (intersection > 0 && (intersection < distance || distance < 0)) {
            	normal = Vector3D.ZERO();
            	distance = intersection;
                position = Vector3D.add(ray.getOrigin(), Vector3D.scalarMultiplication(ray.getDirection(), distance));
                double[] uVw = Barycentric.CalculateBarycentricCoordinates(position, triangle);
                
                Vector3D[] normals = triangle.getNormals();
                for(int i = 0; i < uVw.length; i++) {
                	normal = Vector3D.add(normal, Vector3D.scalarMultiplication(normals[i], uVw[i]));
                }
            }
        }

        if (distance == -1) {
            return null;
        }

        return new Intersection(position, distance, normal, this);
    }

}
