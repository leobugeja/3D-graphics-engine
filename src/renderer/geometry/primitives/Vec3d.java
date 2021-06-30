package renderer.geometry.primitives;

public class Vec3d {
    public double x, y, z;

    public Vec3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void translate(double x_trans, double y_trans, double z_trans) {
        this.x += x_trans;
        this.y += y_trans;
        this.z += z_trans;
    }

    public static Vec3d plus(Vec3d p1, Vec3d p2) {
        return new Vec3d(p1.x + p2.x, p1.y + p2.y, p1.z + p2.z);
    }
}
