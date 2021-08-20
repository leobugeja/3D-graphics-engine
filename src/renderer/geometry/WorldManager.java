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

    public UserInput user_input;

    private double startTime;
    private double seconds;



    public WorldManager() {
        this.entities = new ArrayList<>();
        this.light_source = new LightSource(new Vec3d(0.2,-1,-0.2), true);

        this.camera = new Camera(90, 0.1, 1000);
        this.user_input = new UserInput(this.camera);
    }

    public void init() {
        this.startTime = System.nanoTime();

        this.entities.add(ShapeBuilder.createCube(10, 14, 10,  25, new Color(232, 139, 139)));

        this.entities.add(ShapeBuilder.createCube(30, -30, 0,  60, new Color(136, 180, 255), true, true, true));

        Entity light_entity = ShapeBuilder.createCube(1, 0, 0,  0, new Color(255, 229, 155), true, true, true);

        this.entities.add(light_entity);
        this.light_source.setEntity(light_entity);

    }
    public void update() {
        seconds = (System.nanoTime() - this.startTime)/1000000000.0;
        this.user_input.update();

        this.light_source.moveSequence(seconds);

    }

    public void render(Graphics g) {
        this.entities.sort(new CameraDistanceComparator());

        for (Entity entity : this.entities) {
           entity.render(this.camera, this.light_source, g);
        }


    }


}