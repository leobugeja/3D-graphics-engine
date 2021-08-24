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
        if (this.graphic_settings.isSelf_illuminated()) {
            this.drawHalo(g, this.graphic_settings);
        }
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

    private void drawHalo(Graphics g, EntityGraphicSettings settings) { // TODO halo radius does not change size with projected radius
        Vec3d[] projected_pos = Camera.projectTri2D(new Vec3d[]{this.pos, this.pos, this.pos});

        double cam_dist = Math.sqrt(pow(this.pos.x-Camera.getX(),2) + pow(this.pos.y-Camera.getY(),2) + pow(this.pos.z-Camera.getZ(),2));
        int diameter1 = (int) (1500/cam_dist);
        int diameter2 = (int) (2500/cam_dist);

        g.setColor(new Color(settings.faceColor().getRed(), settings.faceColor().getGreen(), settings.faceColor().getBlue(), 40));
        g.fillOval((int) projected_pos[0].x - diameter1/2, (int) projected_pos[0].y - diameter1/2, diameter1, diameter1);

        g.setColor(new Color(settings.faceColor().getRed(), settings.faceColor().getGreen(), settings.faceColor().getBlue(), 20));
        g.fillOval((int) projected_pos[0].x - diameter2/2, (int) projected_pos[0].y - diameter2/2, diameter2, diameter2);
    }
}
