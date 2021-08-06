package renderer.geometry;

import renderer.Camera;
import renderer.geometry.primitives.Mesh;
import renderer.geometry.primitives.Triangle;
import renderer.geometry.primitives.Vec3d;

import java.awt.*;

public class Entity {

    private Mesh mesh;
    private Vec3d pos;
    private EntityGraphicSettings graphic_settings;

    public Entity(Vec3d position, Mesh mesh, Color color, boolean... settings) {
        this.mesh = mesh;
        this.pos = position;
        this.graphic_settings = new EntityGraphicSettings(color, settings);

    }

    public void render(Camera cam, Graphics g) {
        this.mesh.render(cam, g, this.graphic_settings);
    }
}
