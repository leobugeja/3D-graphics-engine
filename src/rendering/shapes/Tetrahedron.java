package rendering.shapes;

import java.awt.*;

public class Tetrahedron {
    private MyPolygon[] polygons;
    private Color color;

    public Tetrahedron(Color color, boolean decayColor, MyPolygon... polygons) {
        this.color = color;
        this.polygons = polygons;
        if (decayColor) {
            this.setDecayingPolygonColor();
        } else {
            this.setPolygonColor();
        }
    }

    public Tetrahedron(MyPolygon... polygons) {
        this.color = Color.WHITE;
        this.polygons = polygons;
    }

    public void render(Graphics g) {
        for (MyPolygon poly : this.polygons) {
            poly.render(g);
        }
    }

    public void rotate(boolean CW, double xDegrees, double yDegrees, double zDegrees) {
        for(MyPolygon p : this.polygons) {
            p.rotate(CW, xDegrees, yDegrees, zDegrees);
        }
        this.sortPolygons();
    }

    private void sortPolygons() {
        MyPolygon.sortPolygons(this.polygons);
    }

    private void setPolygonColor() {
        for (MyPolygon poly : this.polygons) {
            poly.setColor(this.color);
        }
    }

    private void setDecayingPolygonColor() {
        double decay_factor = 1 - 0.001/this.polygons.length;
        for (MyPolygon poly : this.polygons) {
            poly.setColor(this.color);
            int r = (int) (this.color.getRed() * decay_factor);
            int g = (int) (this.color.getGreen() * decay_factor);
            int b = (int) (this.color.getBlue() * decay_factor);
            this.color = new Color(r, g, b);
        }
    }
}
