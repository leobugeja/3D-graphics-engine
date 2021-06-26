package rendering.entity;

import rendering.Display;
import rendering.entity.builder.BasicEntityBuilder;
import rendering.input.ClickType;
import rendering.input.Mouse;
import rendering.point.DimensionConverter;
import rendering.point.vec3d;
import rendering.shapes.MyPolygon;
import rendering.shapes.Tetrahedron;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    private List<IEntity> entities;

    private int initialX, initialY, mouseX, mouseY;
    private ClickType prevMouse = ClickType.Unknown;
    private double mouseSensitivity = 2.0;


    public EntityManager() {
        this.entities = new ArrayList<IEntity>();
    }

    public void init() {
        this.entities.add(BasicEntityBuilder.createDiamond(Color.CYAN, 100, 0, 0, 0));
    }

    public void update(Mouse mouse) {

        mouseX = mouse.getX();
        mouseY = mouse.getY();
        if (mouse.getButton() == ClickType.LeftClick) {
            int xDif = mouseX - initialX;
            int yDif = mouseY - initialY;
            this.rotate(true, 0,-yDif/2.0,-xDif/2.0);

        } else if (mouse.getButton() == ClickType.RightClick) {
            double thetaDif = (Math.atan2(mouseY - Display.HEIGHT/2, mouseX - Display.WIDTH/2) - Math.atan2(initialY - Display.HEIGHT/2, initialX - Display.WIDTH/2))*(180/Math.PI);
            this.rotate(true, thetaDif,0,0);
        }

        if (mouse.isScrollingUp()) {
            DimensionConverter.zoomIn();
        } else if (mouse.isScrollingDown()) {
            DimensionConverter.zoomOut();
        }

        mouse.resetScroll();

        initialX = mouseX;
        initialY = mouseY;
    }

    public void render(Graphics g) {
       for (IEntity entity : this.entities) {
           entity.render(g);
       }
    }

    private void rotate(boolean direction, double xAngle, double yAngle, double zAngle) {
       for (IEntity entity : this.entities) {
           entity.rotate(direction, xAngle, yAngle, zAngle);
       }
    }
}
