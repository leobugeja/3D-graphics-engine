package renderer.geometry;

import renderer.geometry.primitives.Vec3d;

import java.awt.*;

public class LightSource {

    private boolean is_point_source;
    private Vec3d vector;
    private Entity representation;

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

    public void moveSequence(double seconds) {
        if (this.is_point_source) {
            this.vector.x = 20*Math.cos(seconds) + 30;
            this.vector.y = -15*Math.cos(seconds) - 10;
            this.vector.z = 20*Math.sin(seconds) + 60;
            this.representation.setPos(this.vector.x, this.vector.y, this.vector.z);
        }
        else {
            System.out.println("Error non point source cannot move");
        }
    }

    public void setEntity(Entity entity) {
        this.representation = entity;
    }
}
