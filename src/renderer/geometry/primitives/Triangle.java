package renderer.geometry.primitives;

import renderer.Camera;
import renderer.geometry.DrawType;
import renderer.geometry.WorldManager;

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

    public void render(Graphics g, Color c) {
        Vec3d[] projected_points = Camera.projectTri2D(this.points);
        g.setColor(c);
        this.drawTriangle(g, projected_points);
    }

    public void drawTriangle(Graphics g, Vec3d[] point_arr) {
        int[] x_arr = new int[3];
        int[] y_arr = new int[3];
        for (int i = 0; i < 3; ++i) {
//            x_arr[i] = (int) point_arr[i].x;
//            y_arr[i] = (int) point_arr[i].y;
            x_arr[i] = (int) Math.round(point_arr[i].x);
            y_arr[i] = (int) Math.round(point_arr[i].y);
        }

        if (WorldManager.draw_type == DrawType.Fill || WorldManager.draw_type == DrawType.Fill_and_Line) {
            g.fillPolygon(x_arr, y_arr, 3);
        }
        if (WorldManager.draw_type == DrawType.Line || WorldManager.draw_type == DrawType.Fill_and_Line) {
            g.setColor(Color.BLACK);
            g.drawPolygon(x_arr, y_arr, 3);
        }
    }
}
