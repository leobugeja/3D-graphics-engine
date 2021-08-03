package input;

import renderer.Camera;
import renderer.Display;
import renderer.geometry.WorldManager;

public class UserInput {

    private Camera camera;
    public Mouse mouse;
    public Keyboard keyboard;
    private int initialX, initialY, mouseX, mouseY;

    public UserInput(Camera camera) {
        this.camera = camera;
        this.mouse = new Mouse();
        this.keyboard = new Keyboard();
    }

    public void update() {

        this.keyboard.update();

        mouseX = this.mouse.getX();
        mouseY = this.mouse.getY();
//        this.camera.yaw += (mouseX - initialX) / 2; // TODO change to Camera class and for fps variation
//        this.camera.pitch += (mouseY - initialY) / 2;
        this.camera.yaw += this.mouse.getDx()/2;
        this.camera.pitch += this.mouse.getDy()/2;


        initialX = mouseX;
        initialY = mouseY;

//        this.mouse.resetCursor();

        if (this.keyboard.getLeft()) {
            this.camera.move(Move.Left);
        }
        if (this.keyboard.getRight()) {
            this.camera.move(Move.Right);
        }
        if (this.keyboard.getForward()) {
            this.camera.move(Move.Forward);
        }
        if (this.keyboard.getBackward()) {
            this.camera.move(Move.Backward);
        }
        if (this.keyboard.getUp()) {
            this.camera.move(Move.Up);
        }
        if (this.keyboard.getDown()) {
            this.camera.move(Move.Down);
        }

        if (this.mouse.getButton() == ClickType.LeftClick) {
        }
        else if (this.mouse.getButton() == ClickType.RightClick) {
        }
        if (this.mouse.isScrollingUp()) {
        }
        else if (this.mouse.isScrollingDown()) {
        }

        if (this.keyboard.getEscape()) {
            Display.running = false;
        }

//        this.mouse.resetScroll();
//        if (mouseX > Display.WIDTH || mouseX < 0 || mouseY > Display.HEIGHT || mouseY < 0) {
//            this.mouse.resetCursor();
//        }
    }
}
