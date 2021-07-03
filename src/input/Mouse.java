package input;

import renderer.Display;

import java.awt.*;
import java.awt.event.*;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

    private int mouseX = -1;
    private int mouseY = -1;
    private int mouseB = -1;
    private int scroll = -1;
    private Robot robot;
    public static boolean robotIsMoving = false;

    public Mouse() {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public int getX() {
        return this.mouseX;
    }

    public int getY() {
        return this.mouseY;
    }

    public ClickType getButton() {
        switch (this.mouseB) {
            case 1:
                return ClickType.LeftClick;
            case 2:
                return ClickType.ScrollClick;
            case 3:
                return ClickType.RightClick;
            case 4:
                return ClickType.ForwardPage;
            case 5:
                return ClickType.BackwardPage;
            default:
                return ClickType.Unknown;
        }
    }

    public void resetButton() {
        this.mouseB = -1;
    }

    public boolean isScrollingUp() {
        return this.scroll == -1;
    }

    public boolean isScrollingDown() {
        return this.scroll == 1;
    }

    public void resetScroll() {
        this.scroll = 0;
    }

    public void resetCursor() {
        this.robotIsMoving = true;
        robot.mouseMove((int) Display.frame.getX() + Display.WIDTH/2,(int) Display.frame.getY() + Display.HEIGHT/2);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        this.scroll = event.getWheelRotation();
    }

    @Override
    public void mouseDragged(MouseEvent event) {
//        this.mouseX = event.getX();
//        this.mouseY = event.getY();
    }

    @Override
    public void mouseMoved(MouseEvent event) {

        if (event.getX() == Display.WIDTH/2 && event.getY() == Display.HEIGHT/2) {
            return;
        }
//        this.mouseX = event.getX(); // TODO sort out mouse outside J Frame
//        this.mouseY = event.getY();
        this.mouseX = (int) MouseInfo.getPointerInfo().getLocation().getX();
        this.mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY();


    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent event) {
        this.mouseB = event.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.mouseB = -1;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.resetCursor();
    }




}
