package renderer.geometry;

import renderer.Camera;
import renderer.geometry.primitives.Mesh;
import renderer.geometry.primitives.Triangle;
import renderer.geometry.primitives.Vec3d;

import java.awt.*;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Entity {

    private Mesh mesh;
    private Vec3d pos;
    private EntityGraphicSettings graphic_settings;

    public Entity(Vec3d position, Mesh mesh, Color color, boolean... settings) {
        this.mesh = mesh;
        this.pos = position;
        this.graphic_settings = new EntityGraphicSettings(color, settings);

    }

    public void render(Camera cam, LightSource light, Graphics g) {
        this.mesh.render(cam, light, g, this.graphic_settings, this.pos);
    }

    public double distanceToCamera() {
        return sqrt(pow((Camera.getX() - this.pos.x),2) + pow((Camera.getY() - this.pos.y),2) + pow((Camera.getZ() - this.pos.z),2));
    }

    public void setPos(double x, double y, double z) {
        this.pos.x = x;
        this.pos.y = y;
        this.pos.z = z;
    }
}
