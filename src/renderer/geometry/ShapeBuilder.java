package renderer.geometry;

import renderer.geometry.primitives.Mesh;
import renderer.geometry.primitives.Vec3d;

import java.awt.*;

public class ShapeBuilder {
    public static void main(String[] args) {
        Entity a = ShapeBuilder.createCube(100, 0, 0, 0, new Color(0, 0,0));
    }
    public static Entity createCube(double size, double x_center, double y_center, double z_center, Color color, boolean... settings) {
        Mesh cubeMesh = new Mesh();
        // South
        cubeMesh.addTri(new Vec3d(0, 0, 0), new Vec3d(0, size, 0), new Vec3d(size, size, 0));
        cubeMesh.addTri(new Vec3d(0, 0, 0), new Vec3d(size, size, 0), new Vec3d(size, 0, 0));
        // East
        cubeMesh.addTri(new Vec3d(size, 0, 0), new Vec3d(size, size, 0), new Vec3d(size, size, size));
        cubeMesh.addTri(new Vec3d(size, 0, 0), new Vec3d(size, size, size), new Vec3d(size, 0, size));
        // North
        cubeMesh.addTri(new Vec3d(size, 0, size), new Vec3d(size, size, size), new Vec3d(0, size, size));
        cubeMesh.addTri(new Vec3d(size, 0, size), new Vec3d(0, size, size), new Vec3d(0, 0, size));
        // West
        cubeMesh.addTri(new Vec3d(0, 0, size), new Vec3d(0, size, size), new Vec3d(0, size, 0));
        cubeMesh.addTri(new Vec3d(0, 0, size), new Vec3d(0, size, 0), new Vec3d(0, 0, 0));
        // Top
        cubeMesh.addTri(new Vec3d(0, size, 0), new Vec3d(0, size, size), new Vec3d(size, size, size));
        cubeMesh.addTri(new Vec3d(0, size, 0), new Vec3d(size, size, size), new Vec3d(size, size, 0));
        // Bottom
        cubeMesh.addTri(new Vec3d(size, 0, size), new Vec3d(0, 0, size), new Vec3d(0, 0, 0));
        cubeMesh.addTri(new Vec3d(size, 0, size), new Vec3d(0, 0, 0), new Vec3d(size, 0, 0));

        //cubeMesh.translate(x_center-size/2, y_center-size/2, z_center-size/2);

        return new Entity(new Vec3d(x_center, y_center, z_center), cubeMesh, color, settings);
    }
}
