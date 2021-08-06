package renderer.geometry.primitives;

import renderer.Camera;
import renderer.geometry.EntityGraphicSettings;

import java.awt.*;
import java.util.ArrayList;

public class Mesh {

    private ArrayList<Triangle> triangles;

    public Mesh() {
        triangles = new ArrayList<>();
    }

    public void addTri(Vec3d p1, Vec3d p2, Vec3d p3) {
        this.triangles.add(new Triangle(p1, p2, p3));
    }

    public void translate(double x, double y, double z) {
        for (Triangle tri : this.triangles) {
            tri.translate(x, y, z);
        }
    }

    public ArrayList<Triangle> getTris() {
        return this.triangles;
    }

    public void render(Camera cam, Graphics g, EntityGraphicSettings settings) {
        for (Triangle tri : this.triangles) {
            tri.render(cam, g, settings);
        }
    }
}
