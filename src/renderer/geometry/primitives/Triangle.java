package renderer.geometry.primitives;

import renderer.Camera;

import renderer.geometry.EntityGraphicSettings;
import renderer.geometry.LightSource;

import java.awt.*;
import java.util.Objects;

public class Triangle {

    public Vec3d[] points;
    double lighting;

    public Triangle() {
        this.points = new Vec3d[]{new Vec3d(), new Vec3d(), new Vec3d()};
        this.lighting = 0;
    }

    public Triangle(Vec3d p1, Vec3d p2, Vec3d p3) {
        this.points = new Vec3d[]{p1, p2, p3};
        this.lighting = 0;
    }

    public Triangle(Vec3d[] points) {
        this.points = new Vec3d[]{points[0], points[1], points[2]};
        this.lighting = 0;
    }

    public void translate(double x, double y, double z) {
        for (Vec3d point : this.points) {
            point.translate(x, y, z);
        }
    }

    private Vec3d[] centerCoordinates(Vec3d center_pos) {
        Vec3d[] newCoords = new Vec3d[]{
                Vec3d.add(this.points[0], center_pos),
                Vec3d.add(this.points[1], center_pos),
                Vec3d.add(this.points[2], center_pos)};
        return newCoords;
    }

    public void render(Camera cam, LightSource light, Graphics g, EntityGraphicSettings settings, Vec3d pos) {

        Vec3d[] world_centered_points = centerCoordinates(pos);

        // Calculate Triangle Normal Vector
        Vec3d normal, line1, line2;
        line1 = Vec3d.subtract(world_centered_points[1], world_centered_points[0]);
        line2 = Vec3d.subtract(world_centered_points[2], world_centered_points[0]);
        normal = Vec3d.normal(line1, line2);
        double len = Vec3d.length(normal);
        normal.x /= len;
        normal.y /= len;
        normal.z /= len;

        Vec3d tri_mid_point = new Vec3d( (world_centered_points[0].x+world_centered_points[1].x+world_centered_points[2].x)/3, (world_centered_points[0].y+world_centered_points[1].y+world_centered_points[2].y)/3,(world_centered_points[0].z+world_centered_points[1].z+world_centered_points[2].z)/3 );
        Vec3d camera_to_tri_vector = Vec3d.subtract(tri_mid_point, cam.getPos());
        double cam_dot_prod = Vec3d.dotProduct(normal, camera_to_tri_vector);

        // Light and Color Calculations
        if (settings.isSelf_illuminated()) {
            g.setColor(settings.faceColor());
        }
        else if(!settings.isTransparent()) {

            double light_dot_prod;
            if (!light.isPointSource()) {
                light_dot_prod = Vec3d.dotProduct(normal, light.getPos()); //normal.x*light.getX() + normal.y*light.getY() + normal.z*light.getZ();
            }

            else {
                Vec3d light_norm = Vec3d.subtract(world_centered_points[0], light.getPos()); // new Vec3d(centered_points[0].x - light.getX(), centered_points[0].y - light.getY(), centered_points[0].z - light.getZ());
                light_norm.normalize();
                light_dot_prod = - Vec3d.dotProduct(normal, light_norm); //- normal.x*light_norm.x - normal.y*light_norm.y - normal.z*light_norm.z; // should be to center of triangle not the 0th point
            }
            g.setColor(lightShading(settings.faceColor(), light_dot_prod));
        }
        else {
            g.setColor(settings.faceColor());
        }

        if (cam_dot_prod <= 0 || settings.isTransparent()) {
            Triangle[] projected_points = Camera.projectTri2D(world_centered_points);

            if (Objects.nonNull(projected_points[0])) {
                this.drawTriangle(g, projected_points[0].points, settings);
            }
            if (Objects.nonNull(projected_points[1])) {
                this.drawTriangle(g, projected_points[1].points, settings);
            }


//            if (projected_points[0].z >= 0.1 && projected_points[1].z >= 0.1 && projected_points[2].z >= 0.1) {
//                this.drawTriangle(g, projected_points, settings);
//            }
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

    public static ClippingResultTri clipTriangleAgainstPlane(Vec3d plane_point, Vec3d plane_normal, Vec3d[] input_tri) {
        ClippingResultTri clipping_output;
        Vec3d[] out_tri1 = new Vec3d[] {new Vec3d(), new Vec3d(), new Vec3d()};
        Vec3d[] out_tri2 = new Vec3d[] {new Vec3d(), new Vec3d(), new Vec3d()};

        plane_normal = Vec3d.normalize(plane_normal);

        Vec3d[] inside_points = new Vec3d[3];
        Vec3d[] outside_points = new Vec3d[3];
        int num_in_points = 0;
        int num_out_points = 0;

        double d0 = Vec3d.pointToPlaneDistance(plane_point, plane_normal, input_tri[0]);
        double d1 = Vec3d.pointToPlaneDistance(plane_point, plane_normal, input_tri[1]);
        double d2 = Vec3d.pointToPlaneDistance(plane_point, plane_normal, input_tri[2]);

        if (d0 >= 0) {
            inside_points[num_in_points++] = input_tri[0];
        }
        else {
            outside_points[num_out_points++] = input_tri[0];
        }
        if (d1 >= 0) {
            inside_points[num_in_points++] = input_tri[1];
        }
        else {
            outside_points[num_out_points++] = input_tri[1];
        }
        if (d2 >= 0) {
            inside_points[num_in_points++] = input_tri[2];
        }
        else {
            outside_points[num_out_points++] = input_tri[2];
        }

        if (num_in_points == 0) {
            // All points are on the outside of the plane, so don't draw at all
            return new ClippingResultTri(num_in_points);
        }

        if (num_in_points == 3) {
            // All points are on the inside of the plane so draw as normal
            return new ClippingResultTri(num_in_points, input_tri);
        }

        if (num_in_points == 1 && num_out_points == 2) {
            // Triangle will get clipped as two points on the outside
            out_tri1[0] = inside_points[0]; // keep inside point as it is still valid

            // Two new points are created at the location where the triangle sides intersect with the plane
            out_tri1[1] = Vec3d.vectorPlaneIntersection(plane_point, plane_normal, inside_points[0], outside_points[0]);
            out_tri1[2] = Vec3d.vectorPlaneIntersection(plane_point, plane_normal, inside_points[0], outside_points[1]);


            return new ClippingResultTri(num_in_points, out_tri1);
        }
        if (num_in_points == 2 && num_out_points == 1) {
            // Triangle will get clipped as one points on the outside and two new triangles are created

            out_tri1[0] = inside_points[0];
            out_tri1[1] = inside_points[1];
            out_tri1[2] = Vec3d.vectorPlaneIntersection(plane_point, plane_normal, inside_points[0], outside_points[0]);

            out_tri1[0] = inside_points[1];
            out_tri1[1] = out_tri1[2];
            out_tri1[2] = Vec3d.vectorPlaneIntersection(plane_point, plane_normal, inside_points[1], outside_points[0]);

            return new ClippingResultTri(num_in_points, out_tri1, out_tri2);
        }
        return new ClippingResultTri(4); // throw error in clippingResultTri
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
