/**
 *  2019 - Universidad Panamericana 
 *  All Rights Reserved
 */
package raytracer;

import raytracer.objects.Camera;
import raytracer.objects.DirectionalLight;
import raytracer.objects.Object3D;
import java.util.ArrayList;

/**
 *
 * @author Jafet
 */
public class Scene {
    private Camera camera;
    private ArrayList<Object3D> objects;
    private DirectionalLight light;

    public Scene(){
        setObjects(new ArrayList<Object3D>());
    }
    
    public void addObject(Object3D object){
        getObjects().add(object);
    }
    
    public ArrayList<Object3D> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Object3D> objects) {
        this.objects = objects;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

	public DirectionalLight getLight() {
		return light;
	}

	public void setLight(DirectionalLight light) {
		this.light = light;
	}
    
}
