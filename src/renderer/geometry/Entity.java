package renderer.geometry;

import renderer.geometry.primitives.Mesh;
import renderer.geometry.primitives.Triangle;
import renderer.geometry.primitives.Vec3d;

import java.awt.*;

public class Entity {

    private Mesh mesh;
    private Vec3d pos;
    private Color color;
    private EntityGraphicSettings graphic_settings;

    public Entity(Vec3d position, Mesh mesh, Color color, boolean... settings) {
        this.mesh = mesh;
        this.pos = position;
        this.color = color;
        this.graphic_settings = new EntityGraphicSettings(settings);

    }

    public void render(Graphics g) {
        this.mesh.render(g, this.color, this.graphic_settings);
    }
}
