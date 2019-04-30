/**
 *  2019 - Universidad Panamericana 
 *  All Rights Reserved
 */
package edu.up.isgc.raytracer.tools;

import edu.up.isgc.raytracer.Vector3D;
import edu.up.isgc.raytracer.objects.Polygon;
import edu.up.isgc.raytracer.objects.Triangle;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jafet
 */
public class OBJReader {
    
    public static Polygon GetPolygon(String path, Vector3D origin, Color color) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            
            List<Triangle> triangles = new ArrayList<Triangle>();
            List<Vector3D> vertices = new ArrayList<Vector3D>();
            List<Vector3D> normals = new ArrayList<Vector3D>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("v ") || line.startsWith("vn ")) {
                    String[] vertexComponents = line.split("(\\s)+");
                    if (vertexComponents.length >= 4) {
                        double x = Double.parseDouble(vertexComponents[1]);
                        double y = Double.parseDouble(vertexComponents[2]);
                        double z = Double.parseDouble(vertexComponents[3]);
                        if (line.startsWith("v ")) {
                            vertices.add(new Vector3D(x, y, z));
                        } else {
                            normals.add(new Vector3D(x, y, z));
                        }
                    }
                } else if (line.startsWith("f ")) {
                    String[] faceComponents = line.split("(\\s)+");
                    List<Integer> faceVertex = new ArrayList<Integer>();
                    List<Integer> faceNormal = new ArrayList<Integer>();
                    
                    for (int i = 1; i < faceComponents.length; i++) {
                        String[] infoVertex = faceComponents[i].split("/");
                        if (infoVertex.length >= 3) {
                            int vertexIndex = Integer.parseInt(infoVertex[0]);
                            int normalIndex = Integer.parseInt(infoVertex[2]);
                            faceVertex.add(vertexIndex);
                            faceNormal.add(normalIndex);
                        }
                    }

                    if (faceVertex.size() >= 3) {
                        Vector3D[] triangleVertices = new Vector3D[faceVertex.size()];

                        for (int i = 0; i < faceVertex.size(); i++) {
                            triangleVertices[i] = (vertices.get(faceVertex.get(i) - 1));
                        }
                        //Vector3D[] verticesNormals = new Vector3D[]{normals.get(faceNormal.get(0) - 1)};
                        //triangles.add(new Triangle(triangleVertices, verticesNormals));
                        triangles.add(new Triangle(triangleVertices[1], triangleVertices[0], triangleVertices[2]));
                        if (faceVertex.size() == 4) {
                            triangles.add(new Triangle(triangleVertices[2], triangleVertices[0], triangleVertices[3]));
                        }
                    }
                }
            }
            
            reader.close();
            return new Polygon(origin, triangles.toArray(new Triangle[triangles.size()]), color);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OBJReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OBJReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
