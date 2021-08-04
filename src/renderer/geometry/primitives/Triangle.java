package renderer.geometry.primitives;

import renderer.Camera;

import renderer.geometry.EntityGraphicSettings;

import java.awt.*;

public class Triangle {

    Vec3d[] points;
    double lighting;

    public Triangle(Vec3d p1, Vec3d p2, Vec3d p3) {
        this.points = new Vec3d[]{p1, p2, p3};
        this.lighting = 0;
    }

    public void translate(double x, double y, double z) {
        for (Vec3d point : this.points) {
            point.translate(x, y, z);
        }
    }

    public void render(Graphics g, Color c, EntityGraphicSettings settings) {
        Vec3d[] projected_points = Camera.projectTri2D(this.points);
        g.setColor(c);
        this.drawTriangle(g, projected_points, settings);
    }

    public void drawTriangle(Graphics g, Vec3d[] point_arr, EntityGraphicSettings settings) {

        if (point_arr[0].z >= 1 && point_arr[1].z >= 1 && point_arr[2].z >= 1) { // TODO change so that only stop drawing if whole object is behind z
//            if(point_arr[0].x < WIDTH+200 && point_arr[0].x > -200 && point_arr[1].x < WIDTH+200 && point_arr[1].x > -200 && point_arr[2].x < WIDTH+200 && point_arr[2].x > -200) {
            int[] x_arr = new int[3];
            int[] y_arr = new int[3];
            for (int i = 0; i < 3; ++i) {
                //            x_arr[i] = (int) point_arr[i].x;
                //            y_arr[i] = (int) point_arr[i].y;
                x_arr[i] = (int) Math.round(point_arr[i].x);
                y_arr[i] = (int) Math.round(point_arr[i].y);
            }

            if (settings.fillFaces()) {
                g.fillPolygon(x_arr, y_arr, 3);
            }
            if (settings.drawEdges()) {
                g.setColor(Color.BLACK);
                g.drawPolygon(x_arr, y_arr, 3);
            }
        }
    }
}
