package rendering.entity;

import rendering.shapes.MyPolygon;

import java.awt.*;

public interface IEntity {

    void render(Graphics g);

    void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees);

}
