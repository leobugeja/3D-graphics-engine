package renderer.geometry;

public class EntityGraphicSettings {

    private boolean transparent = false;
    private boolean edge_lines = true;
    private boolean fill_faces = true;

//    public EntityGraphicSettings() {
//        this.transparent = false;
//        this.edge_lines = true;
//        this.fill_faces = true;
//    }

    public EntityGraphicSettings(boolean... settings) {
        if (settings.length > 0) {
            // if settings contains elements override defaults
            this.transparent = settings[0];
            this.edge_lines = settings[1];
            this.fill_faces = settings[2];
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

}
