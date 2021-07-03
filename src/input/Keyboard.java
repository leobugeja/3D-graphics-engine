package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    private final boolean[] keys = new boolean[66568];
    private boolean left, right, up, down, forward, backward, escape;

    public void update() {
        this.left = this.keys[KeyEvent.VK_LEFT] || this.keys[KeyEvent.VK_A];
        this.right = this.keys[KeyEvent.VK_RIGHT] || this.keys[KeyEvent.VK_D];
        this.forward = this.keys[KeyEvent.VK_UP] || this.keys[KeyEvent.VK_W];
        this.backward = this.keys[KeyEvent.VK_DOWN] || this.keys[KeyEvent.VK_S];
        this.up = this.keys[KeyEvent.VK_SHIFT];
        this.down = this.keys[KeyEvent.VK_CONTROL];
        this.escape = this.keys[KeyEvent.VK_ESCAPE];
    }

    public boolean getLeft() {
        return this.left;
    }

    public boolean getRight() {
        return this.right;
    }

    public boolean getUp() {
        return this.up;
    }

    public boolean getDown() {
        return this.down;
    }

    public boolean getForward() {
        return this.forward;
    }

    public boolean getBackward() {
        return this.backward;
    }

    public boolean getEscape() { return this.escape; }

    public boolean[] getKeys() {
        return this.keys;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        keys[event.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent event) {
        keys[event.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent event) {

    }
}
