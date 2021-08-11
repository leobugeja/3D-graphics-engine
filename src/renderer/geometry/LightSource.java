package renderer.geometry;

import renderer.geometry.primitives.Vec3d;

public class LightSource {

    private boolean is_point_source;
    private Vec3d vector;

    public LightSource(Vec3d vec, boolean isPoint) {
        this.is_point_source = isPoint;
        this.vector = vec;

        if (!this.is_point_source) {
            vec.normalize();
        }
    }

    public boolean isPointSource() {
        return this.is_point_source;
    }

    public double getX() {
        return this.vector.x;
    }
    public double getY() {
        return this.vector.y;
    }
    public double getZ() {
        return this.vector.z;
    }
}
