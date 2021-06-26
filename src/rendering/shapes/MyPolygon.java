package rendering.shapes;

import rendering.point.DimensionConverter;
import rendering.point.vec3d;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MyPolygon {

    private Color color;
    private vec3d[] points;

    public MyPolygon(Color color, vec3d... points) {
        this.color = color;
        this.points = new vec3d[points.length];
        for (int i = 0; i < points.length; i++) {
            vec3d p = points[i];
            this.points[i] = new vec3d(p.x, p.y, p.z);
        }
    }

    public MyPolygon(vec3d... points) {
        this.color = Color.BLUE;
        this.points = new vec3d[points.length];
        for (int i = 0; i < points.length; i++) {
            vec3d p = points[i];
            this.points[i] = new vec3d(p.x, p.y, p.z);
        }
    }

    public void render(Graphics g) {
        Polygon poly = new Polygon();
        for(int i = 0; i < this.points.length; i++) {
            Point p = DimensionConverter.convertPoint(points[i]);
            poly.addPoint(p.x, p.y);
        }
        g.setColor(this.color);
        g.fillPolygon(poly);
    }

    public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees) {
        for (vec3d p : points) {
            DimensionConverter.rotateAxisX(p, CW, xDegrees);
            DimensionConverter.rotateAxisY(p, CW, yDegrees);
            DimensionConverter.rotateAxisZ(p, CW, zDegrees);
        }
    }

    public double getAverageX() {
        double sum = 0;
        for (vec3d p : this.points) {
            sum += p.x;
        }

        return sum / this.points.length;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public static MyPolygon[] sortPolygons(MyPolygon[] polygons) {
        ArrayList<MyPolygon> polygons_list = new ArrayList<MyPolygon>();

        for (MyPolygon poly : polygons) {
            polygons_list.add(poly);
        }
        Collections.sort(polygons_list, new Comparator<MyPolygon>() {
            @Override
            public int compare(MyPolygon p1, MyPolygon p2) {
                return p2.getAverageX() - p1.getAverageX() < 0 ? 1 : -1;
            }
        });

        for (int i = 0; i < polygons.length; i++) {
            polygons[i] = polygons_list.get(i);
        }

        return polygons;
    }
}

