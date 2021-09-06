package renderer.geometry;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


import input.UserInput;

import renderer.Camera;
import renderer.geometry.ShapeBuilder;
import renderer.geometry.primitives.Vec3d;

public class WorldManager {

    private List<Entity> entities;
    private Camera camera;
    private LightSource light_source;
    private Ground ground;
    private CameraDistanceComparator cam_distance_comparator;

    public UserInput user_input;

    private double startTime;
    private double seconds;



    public WorldManager() {
        this.entities = new ArrayList<>();
        this.light_source = new LightSource(new Vec3d(0.2,-1,-0.2), true);

        this.camera = new Camera(90, 0.1, 1000);
        this.user_input = new UserInput(this.camera);

        this.cam_distance_comparator = new CameraDistanceComparator();
    }

    public void init() {
        this.startTime = System.nanoTime();

        this.entities.add(ShapeBuilder.createCube(10, 14, 10,  25, new Color(232, 139, 139)));

        this.entities.add(ShapeBuilder.createCube(30, -30, 0,  60, new Color(136, 180, 255)));

        this.entities.add(ShapeBuilder.createSphere(10, 20,30, -10,  60, new Color(140, 255, 136), false, false, true, false));

//        this.ground = new Ground(new Color(0,0,0));

        Entity light_entity = ShapeBuilder.createSphere(2, 8,0, 0,  0, new Color(255, 245, 158, 255), false, false, true, true);

        this.entities.add(light_entity);
        this.light_source.setEntity(light_entity);

    }
    public void update() {
        seconds = (System.nanoTime() - this.startTime)/1000000000.0;
        this.user_input.update();

        this.light_source.moveSequence(seconds);

    }

    public void render(Graphics g) {
        this.camera.updateViewSpaceMatrix();
        this.entities.sort(this.cam_distance_comparator);

//        this.ground.render(this.camera, this.light_source, g);

        for (Entity entity : this.entities) {
           entity.render(this.camera, this.light_source, g);
        }


    }


}