package renderer.geometry;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import input.UserInput;
import renderer.Camera;
import renderer.geometry.ShapeBuilder;

public class WorldManager {

    private List<Entity> entities;
    private Camera camera;
    public UserInput user_input;

    public static DrawType draw_type;

    public WorldManager() {
        this.entities = new ArrayList<>();
        draw_type = DrawType.Fill_and_Line;

        this.camera = new Camera(90, 0.1, 1000);
        this.user_input = new UserInput(this.camera);
    }

    public void init() {
        entities.add(ShapeBuilder.createCube(10, 14, 10,  25, new Color(255,0,0,50)));
        entities.add(ShapeBuilder.createCube(30, -30, 0,  60, new Color(0,0,255,25)));
    }

    public void update() {
        this.user_input.update();
    }

    public void render(Graphics g) {
       for (Entity entity : this.entities) {
           entity.render(g);
       }
    }


}