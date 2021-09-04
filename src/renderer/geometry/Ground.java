package renderer.geometry;

import renderer.Camera;
import renderer.geometry.primitives.Mesh;
import renderer.geometry.primitives.Vec3d;

import java.awt.*;

public class Ground {
    private Mesh mesh;
    private EntityGraphicSettings graphic_settings;

    public Ground(Color color) {
        double horizon = 1000;
        this.mesh = new Mesh();
        // Triangle constructed in anticlockwise direction
        this.mesh.addTri(new Vec3d(-horizon, 30, -horizon),new Vec3d(horizon, 30, -horizon),new Vec3d(-horizon, 30, horizon));
        this.mesh.addTri(new Vec3d(-horizon, 30, horizon),new Vec3d(horizon, 30, -horizon),new Vec3d(horizon, 30, horizon));

        this.graphic_settings = new EntityGraphicSettings(color, false, false, true, false);
        this.graphic_settings.setToDrawBehind();

    }

    public void render(Camera cam, LightSource light, Graphics g) {
        this.mesh.render(cam, light, g, this.graphic_settings, new Vec3d(0,0,0));
    }


}
