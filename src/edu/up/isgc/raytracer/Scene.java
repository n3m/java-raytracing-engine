package edu.up.isgc.raytracer;

import edu.up.isgc.raytracer.lights.Light;
import edu.up.isgc.raytracer.objects.Camera;
import edu.up.isgc.raytracer.objects.Object3D;
import java.util.ArrayList;

/**
 *
 * @author Alan Maldonado
 */
public class Scene {
    private Camera camera;
    private ArrayList<Light> lights;
    private ArrayList<Object3D> objects;

    /***
     * Scene Constructor
     */
    public Scene(){
        setObjects(new ArrayList<Object3D>());
        setLights(new ArrayList<Light>());
    }
    
    /***
     * Add Object to the Scene
     * @param object
     */
    public void addObject(Object3D object){
        getObjects().add(object);
    }
    
    /***
     * Get All Objects in Scene
     * @return
     */
    public ArrayList<Object3D> getObjects() {
        return objects;
    }

    /***
     * Set Objects in Scene
     * @param objects
     */
    public void setObjects(ArrayList<Object3D> objects) {
        this.objects = objects;
    }
    
    /***
     * Add a light to the Scene
     * @param light
     */
    public void addLight(Light light){
        getLights().add(light);
    }
    
    /***
     * Get all the lights in the scene
     * @return
     */
    public ArrayList<Light> getLights() {
        return lights;
    }

    /***
     * Set all the lights in the scene
     * @param lights
     */
    public void setLights(ArrayList<Light> lights) {
        this.lights = lights;
    }

    /***
     *  Get scene camera
     * @return
     */
    public Camera getCamera() {
        return camera;
    }

    /***
     * Get scene camera
     * @param camera
     */
    public void setCamera(Camera camera) {
        this.camera = camera;
    }
    
}
