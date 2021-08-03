package renderer;

import input.UserInput;
import renderer.geometry.WorldManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Display extends Canvas implements Runnable {

    private Thread thread;
    public static JFrame frame;
    private static String title = "3D-graphics-engine";
    public static final int WIDTH = 540;
    public static final int HEIGHT = 360;
    public static boolean running = false;
    public static int fps = 0;

    private WorldManager world_manager;

    public Display() {
        this.frame = new JFrame();

        Dimension size = new Dimension(WIDTH, HEIGHT);
        this.setPreferredSize(size);

        this.world_manager = new WorldManager();

        this.addMouseListener(this.world_manager.user_input.mouse);
        this.addMouseMotionListener(this.world_manager.user_input.mouse);
        this.addMouseWheelListener(this.world_manager.user_input.mouse);
        this.addKeyListener(this.world_manager.user_input.keyboard);



        // Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

        // Set the blank cursor to the JFrame.
        this.frame.getContentPane().setCursor(blankCursor);

    }

    public static void main(String[] args) {
        Display display = new Display();
        display.frame.setTitle(title);
        display.frame.add(display);
        display.frame.pack();
        display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.frame.setLocationRelativeTo(null);
        display.frame.setResizable(false);
        display.frame.setVisible(true);

        display.start();
    }

    public synchronized void start() {
        running = true;
        this.thread = new Thread(this, "rendering.renderer.Display");
        this.thread.start();
    }

    public synchronized void stop() {
        running = false;

        setVisible(false);
        frame.dispose();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60;
        double delta = 0;
        int frames = 0;

        this.world_manager.init();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                delta--;
//                render();
//                frames++;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                this.frame.setTitle(title + " | " + frames+ " fps");
                Display.fps = frames; // TODO change fps back to frames then have separate fps variable which doesn't dip down to zero
                frames = 0;
            }
        }
        stop();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2 = (Graphics2D) g;

        Rectangle clipShape = new Rectangle(0, 0, this.WIDTH, this.HEIGHT);
        g.setClip(clipShape);

        g.setColor(new Color(200, 255, 255));
        g.fillRect(0, 0, WIDTH, HEIGHT); // Set black background

        g.setColor(Color.BLACK);

        g2.setStroke(new BasicStroke(0));
        this.world_manager.render(g);

        g.dispose();
        bs.show();
    }

    private void update() {
        this.world_manager.update();
    }
}
