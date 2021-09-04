package renderer.geometry.primitives;

import static java.lang.Math.sqrt;

public class Vec3d {
    public double x, y, z;

    public Vec3d() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

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

    public static Vec3d add(Vec3d p1, Vec3d p2) {
        return new Vec3d(p1.x + p2.x, p1.y + p2.y, p1.z + p2.z);
    }

    public static Vec3d subtract(Vec3d p1, Vec3d p2) {
        return new Vec3d(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
    }

    public static Vec3d normal(Vec3d p1, Vec3d p2) {
        return new Vec3d(p1.y*p2.z-p1.z*p2.y,p1.z*p2.x-p1.x*p2.z, p1.x*p2.y-p1.y*p2.x);
    }

    public static double length(Vec3d vec) {
        return Math.sqrt(vec.x*vec.x + vec.y*vec.y + vec.z*vec.z);
    }

    public static double dotProduct(Vec3d p1, Vec3d p2) {
        return p1.x*p2.x + p1.y*p2.y + p1.z*p2.z;
    }

    public void normalize() {
        double len = sqrt(this.x*this.x + this.y*this.y + this.z*this.z);
        this.x /= len;
        this.y /= len;
        this.z /= len;
    }

    public static Vec3d normalize(Vec3d vector) {
        double len = sqrt(vector.x*vector.x + vector.y*vector.y + vector.z*vector.z);
        return new Vec3d(vector.x/len, vector.y/len, vector.z/len);
    }

}
