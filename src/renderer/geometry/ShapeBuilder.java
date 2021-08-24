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

    public static Entity createSphere(double radius, int divisions , double x_center, double y_center, double z_center, Color color, boolean... settings) {
        int stack_count = divisions;
        int sector_count = (int) (divisions*1.5);

        int num_vertices = 2 + (stack_count-1)*sector_count;
        Vec3d[] sphere_vertices = new Vec3d[num_vertices];
        sphere_vertices[0] = new Vec3d(0, 0, radius); // first vertex on the top

        Mesh sphere_mesh = new Mesh();



        double stack_step = Math.PI/stack_count;
        double sector_step = 2*Math.PI/sector_count;

        double stack_angle, sector_angle, x, y, z, xy;

        // create sphere vertices
        for (int i = 1; i < stack_count; i++) {
            stack_angle = Math.PI/2.0 - i * stack_step;
            xy = radius * Math.cos(stack_angle);
            z = radius * Math.sin(stack_angle);

            for (int j = 0; j < sector_count; j++) {
                sector_angle = j * sector_step;

                x = xy * Math.cos(sector_angle);
                y = xy * Math.sin(sector_angle);

                sphere_vertices[1 + sector_count*(i-1) + j] = new Vec3d(x, y, z); // x=z y=x z=y
            }
        }

        sphere_vertices[num_vertices-1] = new Vec3d(0, 0, -radius); // last vertex on the bottom

        // create sphere mesh

        // top sector
        for (int j = 1; j < sector_count; j++) {
            sphere_mesh.addTri(sphere_vertices[0], sphere_vertices[j], sphere_vertices[j+1]);
        }
        sphere_mesh.addTri(sphere_vertices[0], sphere_vertices[sector_count], sphere_vertices[1]);

        // middle sector
        System.out.printf("\n");
        int k1, k2;
        for (int i = 0; i < stack_count-2; i++) {
            k1 = i * sector_count + 1;
            k2 = k1 + sector_count;

            for (int j = 0; j < sector_count - 1; j++) {
                sphere_mesh.addTri(sphere_vertices[k1], sphere_vertices[k2], sphere_vertices[k1 + 1]);
                sphere_mesh.addTri(sphere_vertices[k1 + 1], sphere_vertices[k2], sphere_vertices[k2 + 1]);

                k1 += 1;
                k2 += 1;

            }
            sphere_mesh.addTri(sphere_vertices[k1], sphere_vertices[k2], sphere_vertices[k1 + 1 - sector_count]);
            sphere_mesh.addTri(sphere_vertices[k1 + 1 - sector_count], sphere_vertices[k2], sphere_vertices[k2 + 1 - sector_count]);

        }

        // bottom sector
        for (int j = num_vertices - sector_count - 1; j < num_vertices - 1; j++) {
            sphere_mesh.addTri(sphere_vertices[j] , sphere_vertices[num_vertices-1], sphere_vertices[j+1]);
        }
        sphere_mesh.addTri(sphere_vertices[num_vertices-2] , sphere_vertices[num_vertices-1], sphere_vertices[num_vertices-sector_count-1]);

        return new Entity(new Vec3d(x_center, y_center, z_center), sphere_mesh, color, settings);
    }
}
