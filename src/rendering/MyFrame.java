package rendering;

import javax.swing.*;

public class MyFrame extends JFrame {

    GraphicsDemo graphicDemo = new GraphicsDemo();

    public MyFrame() {
        this.setSize(420, 420);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(graphicDemo);
        this.setVisible(true);
    }

}
