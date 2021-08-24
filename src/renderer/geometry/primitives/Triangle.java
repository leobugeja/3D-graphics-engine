package renderer.geometry.primitives;

import renderer.Camera;

import renderer.geometry.EntityGraphicSettings;
import renderer.geometry.LightSource;

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

    private Vec3d[] centeredCoordinates(Vec3d center_pos) {
        Vec3d[] newCoords = new Vec3d[]{
                Vec3d.plus(this.points[0], center_pos),
                Vec3d.plus(this.points[1], center_pos),
                Vec3d.plus(this.points[2], center_pos)};
        return newCoords;
    }

    public void render(Camera cam, LightSource light, Graphics g, EntityGraphicSettings settings, Vec3d pos) {

        Vec3d[] centered_points = centeredCoordinates(pos);

        Vec3d normal = new Vec3d(), line1 = new Vec3d(), line2 = new Vec3d();
        line1.x = centered_points[1].x - centered_points[0].x;
        line1.y = centered_points[1].y - centered_points[0].y;
        line1.z = centered_points[1].z - centered_points[0].z;
        line2.x = centered_points[2].x - centered_points[0].x;
        line2.y = centered_points[2].y - centered_points[0].y;
        line2.z = centered_points[2].z - centered_points[0].z;

        normal.x = line1.y * line2.z - line1.z * line2.y;
        normal.y = line1.z * line2.x - line1.x * line2.z;
        normal.z = line1.x * line2.y - line1.y * line2.x;


        double len = Math.sqrt(normal.x*normal.x + normal.y*normal.y + normal.z*normal.z);
        normal.x /= len;
        normal.y /= len;
        normal.z /= len;

        double cam_dot_prod = normal.x*(centered_points[0].x - cam.getX()) + normal.y*(centered_points[0].y - cam.getY()) + normal.z*(centered_points[0].z - cam.getZ());

        if (settings.isSelf_illuminated()) {
            g.setColor(settings.faceColor());
        }
        else if(!settings.isTransparent()) {

            double light_dot_prod;
            if (!light.isPointSource()) {
                light_dot_prod = normal.x*light.getX() + normal.y*light.getY() + normal.z*light.getZ();
            }

            else {
                Vec3d light_norm = new Vec3d(centered_points[0].x - light.getX(), centered_points[0].y - light.getY(), centered_points[0].z - light.getZ());
                light_norm.normalize();
                light_dot_prod = - normal.x*light_norm.x - normal.y*light_norm.y - normal.z*light_norm.z; // should be to center of triangle not the 0th point
            }
            g.setColor(lightShading(settings.faceColor(), light_dot_prod));
        }
        else {
            g.setColor(settings.faceColor());
        }

        if (cam_dot_prod <= 0 || settings.isTransparent()) {
            Vec3d[] projected_points = Camera.projectTri2D(centered_points);

            if (projected_points[0].z >= 1 && projected_points[1].z >= 1 && projected_points[2].z >= 1) { // TODO change so that only stop drawing if whole object is behind z
                this.drawTriangle(g, projected_points, settings);
            }
        }

    }

    public void drawTriangle(Graphics g, Vec3d[] point_arr, EntityGraphicSettings settings) {
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

    private Color lightShading(Color base_color, double dot_prod) {
        double darkness_factor;
        int r = base_color.getRed();
        int g = base_color.getGreen();
        int b = base_color.getBlue();
        int a = base_color.getAlpha();

        if (dot_prod < 0) {
            darkness_factor = 0.5;
        }
        else {
            darkness_factor = 0.5 + 0.5*dot_prod;
        }
        r = (int) (r*darkness_factor);
        g = (int) (g*darkness_factor);
        b = (int) (b*darkness_factor);
        return new Color(r, g, b, a);
    }
}
