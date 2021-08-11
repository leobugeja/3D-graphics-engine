package renderer.geometry;

import java.awt.*;

public class EntityGraphicSettings {

    private boolean transparent = false;
    private boolean edge_lines = false;
    private boolean fill_faces = true;
    private Color face_color = Color.blue;


    public EntityGraphicSettings(Color color, boolean... settings) {
        this.face_color = color;
        if (settings.length > 0) {
            // if settings contains elements override defaults
            this.transparent = settings[0];
            this.edge_lines = settings[1];
            this.fill_faces = settings[2];
        }

        if (this.transparent == true) {
            this.face_color = new Color(this.face_color.getRed(), this.face_color.getGreen(), this.face_color.getBlue(),100);
        }
    }

    public boolean isTransparent() {
        return this.transparent;
    }

    public boolean drawEdges() {
        return this.edge_lines;
    }

    public boolean fillFaces() {
        return this.fill_faces;
    }

    public Color faceColor() { return this.face_color; }

}
