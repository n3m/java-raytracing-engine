package raytracer;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import raytracer.objects.Polygon;
import raytracer.objects.Triangle;

public class OBJReader {
	protected static String ext = ".obj";
	protected static ArrayList<Vector3D> vertices = new ArrayList<Vector3D>();
	protected static ArrayList<Vector3D> vertNormal = new ArrayList<Vector3D>();
	protected static ArrayList<String> faces = new ArrayList<String>();
	protected static ArrayList<Triangle> tri = new ArrayList<Triangle>();
	protected static Vector3D def_pos;
	protected static Color def_col;
	
	public static Polygon getPolyFromOBJ(String filename, Vector3D position, Color color) {
		def_pos = position;
		def_col = color;
		ArrayList<String> objContent = new ArrayList<String>();
		Polygon newTemp = new Polygon(position, color);
		openFile(filename + ext, objContent);
		analyzeContent(objContent, newTemp);
		System.out.println("Loaded: " + filename + ".obj" );
		return newTemp;
	}
	
	private static void analyzeContent(ArrayList<String> objContent, Polygon newTemp) {
		getVectorsAndNormalsFromOBJ(objContent);
		getFaceFromOBJ(objContent);
		for (String curStr : faces) {
			if (curStr.startsWith("#")) {
				continue;
			}
			String[] faces = curStr.split(" ");
			if(faces.length == 3) {
				ArrayList<Integer> item = new ArrayList<Integer>();
				for (String str : faces) {
					String[] codes = null;
					if(str.contains("//")) {
						codes = str.split("//");
					} else {
						codes = str.split("/");
					}
					item.add(Integer.parseInt(codes[0]));
				}
				
				Vector3D v1 = vertices.get(item.get(0)-1);
				v1.setX(v1.getX() + def_pos.getX());
				v1.setY(v1.getY() + def_pos.getY());
				v1.setZ(v1.getZ() + def_pos.getZ());
				Vector3D v2 = vertices.get(item.get(1)-1);
				v2.setX(v2.getX() + def_pos.getX());
				v2.setY(v2.getY() + def_pos.getY());
				v2.setZ(v2.getZ() + def_pos.getZ());
				Vector3D v3 = vertices.get(item.get(2)-1);
				v3.setX(v3.getX() + def_pos.getX());
				v3.setY(v3.getY() + def_pos.getY());
				v3.setZ(v3.getZ() + def_pos.getZ());
				
				tri.add(new Triangle(v1, v2, v3));
			} else if (faces.length == 4) {
				ArrayList<Integer> item = new ArrayList<Integer>();
				for (String str : faces) {
					String[] codes = null;
					if(str.contains("//")) {
						codes = str.split("//");
					} else {
						codes = str.split("/");
					}
					item.add(Integer.parseInt(codes[0]));
				}
				
				Vector3D v1 = vertices.get(item.get(0)-1);
				v1.setX(v1.getX() + def_pos.getX());
				v1.setY(v1.getY() + def_pos.getY());
				v1.setZ(v1.getZ() + def_pos.getZ());
				Vector3D v2 = vertices.get(item.get(1)-1);
				v2.setX(v2.getX() + def_pos.getX());
				v2.setY(v2.getY() + def_pos.getY());
				v2.setZ(v2.getZ() + def_pos.getZ());
				Vector3D v3 = vertices.get(item.get(2)-1);
				v3.setX(v3.getX() + def_pos.getX());
				v3.setY(v3.getY() + def_pos.getY());
				v3.setZ(v3.getZ() + def_pos.getZ());
				Vector3D v4 = vertices.get(item.get(3)-1);
				v4.setX(v4.getX() + def_pos.getX());
				v4.setY(v4.getY() + def_pos.getY());
				v4.setZ(v4.getZ() + def_pos.getZ());
				tri.add(new Triangle(v1, v2, v3));
				tri.add(new Triangle(v2, v3, v4));
			}
		}
		
		for (Triangle t : tri) {
			newTemp.AddTriangle(t);
		}
	}
	
	private static void getFaceFromOBJ(ArrayList<String> objContent) {
		for (String curStr : objContent) {
			if(curStr.startsWith("f ")) {
				if(curStr.contains("f  ")) {
					curStr = curStr.replace("f  ", "");
				} else if(curStr.contains("f ")){
					curStr = curStr.replace("f ", "");
				}
				faces.add(curStr);
			}
		}
	}
	
	private static void getVectorsAndNormalsFromOBJ(ArrayList<String> objContent) {
		for (String curStr : objContent) {
			if (curStr.startsWith("#") || curStr.startsWith("vt") || curStr.startsWith("o") || curStr.startsWith("g") || curStr.startsWith("s")) {
				continue;
			}
			
			if (curStr.startsWith("v ")) {
				if(curStr.contains("v  ")) {
					curStr = curStr.replace("v  ", "");
				} else if(curStr.contains("v ")){
					curStr = curStr.replace("v ", "");
				}
				
				String[] vertice = curStr.split(" ");
				Vector3D temp = new Vector3D(Double.parseDouble(vertice[0]), Double.parseDouble(vertice[1]), Double.parseDouble(vertice[2]));
				vertices.add(temp);
			}
			if (curStr.startsWith("vn ")) {
				if(curStr.contains("vn  ")) {
					curStr = curStr.replace("vn  ", "");
				} else if(curStr.contains("vn ")){
					curStr = curStr.replace("vn ", "");
				}
				String[] vertice = curStr.split(" ");
				Vector3D temp = new Vector3D(Double.parseDouble(vertice[0]), Double.parseDouble(vertice[1]), Double.parseDouble(vertice[2]));
				vertNormal.add(temp);
			}
		}
	}
	
	private static void openFile(String filename, ArrayList<String> objContent) {
		File srcFile = new File(filename);
		  try
		  {
		    BufferedReader reader = new BufferedReader(new FileReader(srcFile));
		    String line;
		    while ((line = reader.readLine()) != null)
		    {
		      objContent.add(line);
		    }
		    reader.close();
		  }
		  catch (Exception e)
		  {
		    System.err.format("Exception occurred trying to read '%s'.", filename);
		    e.printStackTrace();
		  }
	}
}
